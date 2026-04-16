package model.users;

import model.enums.StudentYear;
import model.research.Researcher;
import exceptions.CreditLimitException;
import exceptions.FailLimitException;
import exceptions.HIndexTooLowException;
import storage.DataStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a student in the university system
 */
public class Student extends User {
    private static final long serialVersionUID = 1L;
    
    private String studentId;
    private StudentYear year;
    private String major;
    private double gpa;
    private int totalCredits;
    private int failedCoursesCount;
    private String researchSupervisorId; // ID of researcher supervising this student (for 4th year)
    private List<String> registeredCourses; // Course IDs
    private Map<String, Double> courseMarks; // Course ID -> final mark
    private Map<String, Integer> courseCredits; // Course ID -> credits
    
    private static final int MAX_CREDITS = 21;
    private static final int MAX_FAILED_COURSES = 3;
    
    public Student(String username, String password, String firstName, String lastName, 
                  String email, String studentId, StudentYear year, String major) {
        super(username, password, firstName, lastName, email);
        this.studentId = studentId;
        this.year = year;
        this.major = major;
        this.gpa = 0.0;
        this.totalCredits = 0;
        this.failedCoursesCount = 0;
        this.researchSupervisorId = null;
        this.registeredCourses = new ArrayList<>();
        this.courseMarks = new HashMap<>();
        this.courseCredits = new HashMap<>();
    }
    
    // Getters and setters
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public StudentYear getYear() {
        return year;
    }
    
    public void setYear(StudentYear year) {
        this.year = year;
    }
    
    public String getMajor() {
        return major;
    }
    
    public void setMajor(String major) {
        this.major = major;
    }
    
    public double getGpa() {
        return gpa;
    }
    
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    
    public int getTotalCredits() {
        return totalCredits;
    }
    
    public int getFailedCoursesCount() {
        return failedCoursesCount;
    }
    
    public String getResearchSupervisorId() {
        return researchSupervisorId;
    }
    
    public void setResearchSupervisorId(String researchSupervisorId) throws HIndexTooLowException {
        // Validate supervisor h-index if ID is provided
        if (researchSupervisorId != null && !researchSupervisorId.isEmpty()) {
            DataStorage storage = DataStorage.getInstance();
            Researcher supervisor = storage.getResearcher(researchSupervisorId);
            if (supervisor == null) {
                throw new IllegalArgumentException("Researcher with ID " + researchSupervisorId + " not found");
            }
            if (!supervisor.qualifiesAsSupervisor()) {
                throw new HIndexTooLowException(supervisor.getResearcherName(), supervisor.getHIndex());
            }
        }
        this.researchSupervisorId = researchSupervisorId;
    }
    
    /**
     * Set research supervisor with validation (alternative method)
     */
    public void setResearchSupervisor(Researcher supervisor) throws HIndexTooLowException {
        if (supervisor == null) {
            this.researchSupervisorId = null;
            return;
        }
        if (!supervisor.qualifiesAsSupervisor()) {
            throw new HIndexTooLowException(supervisor.getResearcherName(), supervisor.getHIndex());
        }
        this.researchSupervisorId = supervisor.getResearcherId();
    }
    
    public List<String> getRegisteredCourses() {
        return new ArrayList<>(registeredCourses);
    }
    
    public Map<String, Double> getCourseMarks() {
        return new HashMap<>(courseMarks);
    }
    
    public Map<String, Integer> getCourseCredits() {
        return new HashMap<>(courseCredits);
    }
    
    // Business logic methods
    
    /**
     * Register for a course with credit validation
     */
    public void registerForCourse(String courseId, int credits) throws CreditLimitException {
        if (totalCredits + credits > MAX_CREDITS) {
            throw new CreditLimitException(getFullName(), totalCredits + credits, MAX_CREDITS);
        }
        
        if (!registeredCourses.contains(courseId)) {
            registeredCourses.add(courseId);
            courseCredits.put(courseId, credits);
            totalCredits += credits;
        }
    }
    
    /**
     * Drop a course
     */
    public void dropCourse(String courseId) {
        if (registeredCourses.contains(courseId)) {
            registeredCourses.remove(courseId);
            Integer credits = courseCredits.remove(courseId);
            if (credits != null) {
                totalCredits -= credits;
            }
            courseMarks.remove(courseId);
        }
    }
    
    /**
     * Add a mark for a course and update GPA/failed courses count
     */
    public void addCourseMark(String courseId, double mark) throws FailLimitException {
        if (mark < 50) {
            failedCoursesCount++;
            if (failedCoursesCount > MAX_FAILED_COURSES) {
                throw new FailLimitException(getFullName(), failedCoursesCount, MAX_FAILED_COURSES);
            }
        }
        
        courseMarks.put(courseId, mark);
        calculateGPA();
    }
    
    /**
     * Calculate GPA based on all course marks
     */
    private void calculateGPA() {
        if (courseMarks.isEmpty()) {
            gpa = 0.0;
            return;
        }
        
        double totalWeighted = 0.0;
        int totalCreditsForGPA = 0;
        
        for (Map.Entry<String, Double> entry : courseMarks.entrySet()) {
            String courseId = entry.getKey();
            Double mark = entry.getValue();
            Integer credits = courseCredits.get(courseId);
            
            if (credits != null && mark != null) {
                totalWeighted += mark * credits;
                totalCreditsForGPA += credits;
            }
        }
        
        if (totalCreditsForGPA > 0) {
            gpa = totalWeighted / totalCreditsForGPA;
        } else {
            gpa = 0.0;
        }
    }
    
    /**
     * Check if student is in 4th year and needs a research supervisor
     */
    public boolean needsResearchSupervisor() {
        return year == StudentYear.FOURTH && researchSupervisorId == null;
    }
    
    /**
     * Get transcript as formatted string
     */
    public String getTranscript() {
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for ").append(getFullName()).append("\n");
        sb.append("Student ID: ").append(studentId).append("\n");
        sb.append("Major: ").append(major).append("\n");
        sb.append("Year: ").append(year).append("\n");
        sb.append("GPA: ").append(String.format("%.2f", gpa)).append("\n");
        sb.append("Total Credits: ").append(totalCredits).append("\n\n");
        sb.append("Courses:\n");
        
        for (String courseId : registeredCourses) {
            Double mark = courseMarks.get(courseId);
            Integer credits = courseCredits.get(courseId);
            sb.append("  ").append(courseId).append(": ");
            if (mark != null) {
                sb.append(String.format("%.1f", mark)).append(" / ");
            } else {
                sb.append("No grade / ");
            }
            sb.append(credits != null ? credits : "0").append(" credits\n");
        }
        
        return sb.toString();
    }
    
    @Override
    public String getUserType() {
        return "Student";
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Student ID: " + studentId + ", Year: " + year + 
               ", Major: " + major + ", GPA: " + String.format("%.2f", gpa) + 
               ", Credits: " + totalCredits + "/" + MAX_CREDITS;
    }
}