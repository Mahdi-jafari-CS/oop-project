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
        if (registeredStudentIds.size() >= maxStudents) {
            return false; // Course is full
        }
        
        if (!registeredStudentIds.contains(studentId)) {
            registeredStudentIds.add(studentId);
            return true;
        }
        
        return false; // Student already registered
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
        return lessons.stream()
                .mapToInt(Lesson::getDurationHours)
                .sum();
    }
    
    /**
     * Get course information as formatted string
     */
    public String getCourseInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Course ID: ").append(courseId).append("\n");
        sb.append("Course Name: ").append(courseName).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Credits: ").append(credits).append("\n");
        sb.append("Major: ").append(major).append("\n");
        sb.append("Year Required: ").append(yearRequired).append("\n");
        sb.append("Instructors: ").append(instructorIds.size()).append("\n");
        sb.append("Lessons: ").append(lessons.size()).append("\n");
        sb.append("Registered Students: ").append(registeredStudentIds.size()).append("/").append(maxStudents).append("\n");
        sb.append("Available Spots: ").append(getAvailableSpots()).append("\n");
        sb.append("Total Lesson Hours: ").append(getTotalLessonHours()).append("\n");
        
        return sb.toString();
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
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credits=" + credits +
                ", major='" + major + '\'' +
                ", instructors=" + instructorIds.size() +
                ", students=" + registeredStudentIds.size() + "/" + maxStudents +
                '}';
    }
}