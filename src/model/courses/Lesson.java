package model.courses;

import model.enums.LessonType;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Class representing a lesson (lecture or practice session) for a course
 */
public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String lessonId;
    private LessonType type;
    private String topic;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;
    private String instructorId; // ID of teacher conducting this lesson
    
    public Lesson(String lessonId, LessonType type, String topic, DayOfWeek dayOfWeek, 
                  LocalTime startTime, LocalTime endTime, String room, String instructorId) {
        this.lessonId = lessonId;
        this.type = type;
        this.topic = topic;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.instructorId = instructorId;
    }
    
    // Getters and setters
    public String getLessonId() {
        return lessonId;
    }
    
    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }
    
    public LessonType getType() {
        return type;
    }
    
    public void setType(LessonType type) {
        this.type = type;
    }
    
    public String getTopic() {
        return topic;
    }
    
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    public LocalTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    public String getRoom() {
        return room;
    }
    
    public void setRoom(String room) {
        this.room = room;
    }
    
    public String getInstructorId() {
        return instructorId;
    }
    
    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }
    
    /**
     * Get duration of lesson in hours
     */
    public int getDurationHours() {
        if (startTime == null || endTime == null) return 0;
        return (int) java.time.Duration.between(startTime, endTime).toHours();
    }
    
    /**
     * Get duration of lesson in minutes
     */
    public int getDurationMinutes() {
        if (startTime == null || endTime == null) return 0;
        return (int) java.time.Duration.between(startTime, endTime).toMinutes();
    }
    
    /**
     * Check if this lesson overlaps with another lesson
     */
    public boolean overlapsWith(Lesson other) {
        // TODO: Return true when two lessons are on the same day and their time intervals overlap.
        // Handle null day/time values safely and return false when comparison is impossible.
        return false;
    }
    
    /**
     * Get lesson schedule as formatted string
     */
    public String getScheduleString() {
        // TODO: Return schedule in format "DAY HH:mm - HH:mm in ROOM".
        // Use "N/A" when start/end times are missing.
        return "";
    }
    
    /**
     * Get detailed lesson information
     */
    public String getLessonDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lesson ID: ").append(lessonId).append("\n");
        sb.append("Type: ").append(type).append("\n");
        sb.append("Topic: ").append(topic).append("\n");
        sb.append("Schedule: ").append(getScheduleString()).append("\n");
        sb.append("Duration: ").append(getDurationHours()).append(" hours (").append(getDurationMinutes()).append(" minutes)\n");
        sb.append("Room: ").append(room).append("\n");
        sb.append("Instructor ID: ").append(instructorId).append("\n");
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(lessonId, lesson.lessonId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(lessonId);
    }
    
    @Override
    public String toString() {
        // TODO: Return a compact string representation of the lesson.
        // Example: "Lesson{id=L001, type=LECTURE, topic=Math, day=MONDAY, time=10:00-12:00, room=201}"
        return "";
    }
}