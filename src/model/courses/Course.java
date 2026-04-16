package model.courses;

import model.enums.LessonType;
import model.users.Teacher;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing a course in the university
 */
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String courseId;
    private String courseName;
    private String description;
    private int credits;
    private String major;
    private int yearRequired; // Year of study required to take this course
    private List<String> instructorIds; // IDs of teachers instructing this course
    private List<Lesson> lessons;
    private int maxStudents;
    private List<String> registeredStudentIds;
    
    public Course(String courseId, String courseName, String description, int credits, 
                  String major, int yearRequired, int maxStudents) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
        this.credits = credits;
        this.major = major;
        this.yearRequired = yearRequired;
        this.maxStudents = maxStudents;
        this.instructorIds = new ArrayList<>();
        this.lessons = new ArrayList<>();
        this.registeredStudentIds = new ArrayList<>();
    }
    
    // Getters and setters
    public String getCourseId() {
        return courseId;
    }
    
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getCredits() {
        return credits;
    }
    
    public void setCredits(int credits) {
        this.credits = credits;
    }
    
    public String getMajor() {
        return major;
    }
    
    public void setMajor(String major) {
        this.major = major;
    }
    
    public int getYearRequired() {
        return yearRequired;
    }
    
    public void setYearRequired(int yearRequired) {
        this.yearRequired = yearRequired;
    }
    
    public List<String> getInstructorIds() {
        return new ArrayList<>(instructorIds);
    }
    
    public void setInstructorIds(List<String> instructorIds) {
        this.instructorIds = new ArrayList<>(instructorIds);
    }
    
    public void addInstructor(String teacherId) {
        if (!instructorIds.contains(teacherId)) {
            instructorIds.add(teacherId);
        }
    }
    
    public void removeInstructor(String teacherId) {
        instructorIds.remove(teacherId);
    }
    
    public List<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }
    
    public void setLessons(List<Lesson> lessons) {
        this.lessons = new ArrayList<>(lessons);
    }
    
    public void addLesson(Lesson lesson) {
        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }
    
    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }
    
    public int getMaxStudents() {
        return maxStudents;
    }
    
    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }
    
    public List<String> getRegisteredStudentIds() {
        return new ArrayList<>(registeredStudentIds);
    }
    
    public void setRegisteredStudentIds(List<String> registeredStudentIds) {
        this.registeredStudentIds = new ArrayList<>(registeredStudentIds);
    }
    
    /**
     * Register a student for this course
     * @param studentId ID of student to register
     * @return true if registration successful, false if course is full
     */
    public boolean registerStudent(String studentId) {
        // TODO: Implement registration logic.
        // Rules:
        // 1) Return false if course is full.
        // 2) Add student only if not already registered.
        // 3) Return true only when a new registration is added.
        return false;
    }
    
    /**
     * Drop a student from this course
     */
    public boolean dropStudent(String studentId) {
        return registeredStudentIds.remove(studentId);
    }
    
    /**
     * Check if course has available spots
     */
    public boolean hasAvailableSpots() {
        return registeredStudentIds.size() < maxStudents;
    }
    
    /**
     * Get number of available spots
     */
    public int getAvailableSpots() {
        return maxStudents - registeredStudentIds.size();
    }
    
    /**
     * Check if a student is registered for this course
     */
    public boolean isStudentRegistered(String studentId) {
        return registeredStudentIds.contains(studentId);
    }
    
    /**
     * Check if a teacher is instructing this course
     */
    public boolean isTeacherInstructor(String teacherId) {
        return instructorIds.contains(teacherId);
    }
    
    /**
     * Get total lesson hours for this course
     */
    public int getTotalLessonHours() {
        // TODO: Sum lesson durations in hours across all lessons.
        return 0;
    }
    
    /**
     * Get course information as formatted string
     */
    public String getCourseInfo() {
        // TODO: Return a multi-line summary of this course.
        // Include core metadata, counts (instructors/lessons/students), available spots, and total lesson hours.
        return "";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }
    
    @Override
    public String toString() {
        // TODO: Return a compact single-line representation of Course.
        // Example: "Course{id=CS101, name=OOP, credits=5, students=22/30}"
        return "";
    }
}