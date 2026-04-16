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
        if (!this.dayOfWeek.equals(other.dayOfWeek)) return false;
        if (this.startTime == null || this.endTime == null || 
            other.startTime == null || other.endTime == null) return false;
        
        return !(this.endTime.isBefore(other.startTime) || this.startTime.isAfter(other.endTime));
    }
    
    /**
     * Get lesson schedule as formatted string
     */
    public String getScheduleString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dayOfWeek.toString()).append(" ");
        sb.append(startTime != null ? startTime.toString() : "N/A");
        sb.append(" - ");
        sb.append(endTime != null ? endTime.toString() : "N/A");
        sb.append(" in ").append(room);
        return sb.toString();
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
        return "Lesson{" +
                "lessonId='" + lessonId + '\'' +
                ", type=" + type +
                ", topic='" + topic + '\'' +
                ", day=" + dayOfWeek +
                ", time=" + (startTime != null ? startTime.toString() : "N/A") + 
                "-" + (endTime != null ? endTime.toString() : "N/A") +
                ", room='" + room + '\'' +
                '}';
    }
}