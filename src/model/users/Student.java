// Assigned to: Мамедов Н. Т.
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
        Double previousMark = courseMarks.get(courseId);
        if (previousMark != null && previousMark < 50 && mark >= 50) {
            failedCoursesCount = Math.max(0, failedCoursesCount - 1);
        }
        if (mark < 50 && (previousMark == null || previousMark >= 50)) {
            failedCoursesCount++;
        }
        if (failedCoursesCount > MAX_FAILED_COURSES) {
            throw new FailLimitException(getFullName(), failedCoursesCount, MAX_FAILED_COURSES);
        }
        courseMarks.put(courseId, mark);
        calculateGPA();
    }
    
    /**
     * Calculate GPA based on all course marks
     */
    private void calculateGPA() {
        double totalWeighted = 0.0;
        int totalCreditHours = 0;
        for (Map.Entry<String, Double> entry : courseMarks.entrySet()) {
            int credits = courseCredits.getOrDefault(entry.getKey(), 0);
            if (credits > 0) {
                totalWeighted += entry.getValue() * credits;
                totalCreditHours += credits;
            }
        }
        gpa = totalCreditHours == 0 ? 0.0 : totalWeighted / totalCreditHours;
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
        sb.append("Transcript for ").append(getFullName())
          .append(" (").append(studentId).append(")").append("\n");
        sb.append("Major: ").append(major).append("\n");
        sb.append("Year: ").append(year).append("\n");
        sb.append(String.format("GPA: %.2f, Credits: %d, Failed Courses: %d", gpa, totalCredits, failedCoursesCount)).append("\n");
        sb.append("Courses:\n");
        if (registeredCourses.isEmpty()) {
            sb.append("  None\n");
        } else {
            for (String courseId : registeredCourses) {
                int credits = courseCredits.getOrDefault(courseId, 0);
                double mark = courseMarks.getOrDefault(courseId, 0.0);
                sb.append(String.format("  %s - %d credits, Mark: %.1f, %s\n",
                        courseId,
                        credits,
                        mark,
                        mark >= 50 ? "Pass" : "Fail"));
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getUserType() {
        return "Student";
    }
    
    @Override
    public String toString() {
        return String.format("%s, studentId=%s, year=%s, major=%s, GPA=%.2f, credits=%d, failed=%d", 
                             super.toString(), studentId, year, major, gpa, totalCredits, failedCoursesCount);
    }
}