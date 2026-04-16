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
        // TODO: Validate credit limit before registration.
        // If totalCredits + credits exceeds MAX_CREDITS, throw CreditLimitException.
        // TODO: If not already registered, add courseId, store credits, and update totalCredits.
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
        // TODO: If mark is below passing threshold, increment failedCoursesCount.
        // If failedCoursesCount exceeds MAX_FAILED_COURSES, throw FailLimitException.
        // TODO: Save the mark and recalculate GPA.
    }
    
    /**
     * Calculate GPA based on all course marks
     */
    private void calculateGPA() {
        // TODO: Recalculate GPA as weighted average using courseMarks and courseCredits.
        // Formula: sum(mark * credits) / sum(credits)
        // Handle empty/no-valid-credit cases by setting gpa to 0.0.
        gpa = 0.0;
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
        // TODO: Build and return a formatted transcript string.
        // Include student header info (name/id/major/year/GPA/credits) and per-course mark + credits.
        return "";
    }
    
    @Override
    public String getUserType() {
        return "Student";
    }
    
    @Override
    public String toString() {
        // TODO: Extend User.toString() with studentId, year, major, GPA, and credit summary.
        return "";
    }
}