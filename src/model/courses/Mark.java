// Assigned to: Тайкпанов Р. Р.
package model.courses;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class representing a student's mark for a course
 * Mark consists of first attestation (0-30), second attestation (0-30), and final exam (0-40)
 */
public class Mark implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String studentId;
    private String courseId;
    private double firstAttestation; // 0-30
    private double secondAttestation; // 0-30
    private double finalExam; // 0-40
    private double totalMark; // Calculated total (0-100)
    private String grade; // Letter grade (A, B, C, D, F)
    
    public Mark(String studentId, String courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.firstAttestation = 0.0;
        this.secondAttestation = 0.0;
        this.finalExam = 0.0;
        this.totalMark = 0.0;
        this.grade = "F";
    }
    
    public Mark(String studentId, String courseId, double firstAttestation, 
                double secondAttestation, double finalExam) {
        this.studentId = studentId;
        this.courseId = courseId;
        setFirstAttestation(firstAttestation);
        setSecondAttestation(secondAttestation);
        setFinalExam(finalExam);
        calculateTotalMark();
        calculateGrade();
    }
    
    // Getters and setters with validation
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public String getCourseId() {
        return courseId;
    }
    
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    
    public double getFirstAttestation() {
        return firstAttestation;
    }
    
    public void setFirstAttestation(double firstAttestation) {
        if (firstAttestation < 0) {
            this.firstAttestation = 0;
        } else if (firstAttestation > 30) {
            this.firstAttestation = 30;
        } else {
            this.firstAttestation = firstAttestation;
        }
        calculateTotalMark();
        calculateGrade();
    }
    
    public double getSecondAttestation() {
        return secondAttestation;
    }
    
    public void setSecondAttestation(double secondAttestation) {
        if (secondAttestation < 0) {
            this.secondAttestation = 0;
        } else if (secondAttestation > 30) {
            this.secondAttestation = 30;
        } else {
            this.secondAttestation = secondAttestation;
        }
        calculateTotalMark();
        calculateGrade();
    }
    
    public double getFinalExam() {
        return finalExam;
    }
    
    public void setFinalExam(double finalExam) {
        if (finalExam < 0) {
            this.finalExam = 0;
        } else if (finalExam > 40) {
            this.finalExam = 40;
        } else {
            this.finalExam = finalExam;
        }
        calculateTotalMark();
        calculateGrade();
    }
    
    public double getTotalMark() {
        return totalMark;
    }
    
    public String getGrade() {
        return grade;
    }
    
    /**
     * Calculate total mark from all components
     */
    private void calculateTotalMark() {
        this.totalMark = firstAttestation + secondAttestation + finalExam;
    }
    
    /**
     * Calculate letter grade based on total mark
     */
    private void calculateGrade() {
        // TODO: Map totalMark to a letter grade using the existing thresholds.
        // Keep the final else branch for failing grade "F".
        grade = "F";
    }
    
    /**
     * Check if the mark is a passing grade (>= 50)
     */
    public boolean isPassing() {
        return totalMark >= 50;
    }
    
    /**
     * Check if the mark is excellent (>= 85)
     */
    public boolean isExcellent() {
        return totalMark >= 85;
    }
    
    /**
     * Check if the mark is good (>= 70)
     */
    public boolean isGood() {
        return totalMark >= 70;
    }
    
    /**
     * Check if the mark is satisfactory (>= 50)
     */
    public boolean isSatisfactory() {
        return totalMark >= 50;
    }
    
    /**
     * Get grade points (4.0 scale)
     */
    public double getGradePoints() {
        // TODO: Return grade points on a 4.0 scale based on letter grade.
        // Use switch-case and return 0.0 for unknown grades.
        return 0.0;
    }
    
    /**
     * Set all marks at once
     */
    public void setAllMarks(double firstAttestation, double secondAttestation, double finalExam) {
        setFirstAttestation(firstAttestation);
        setSecondAttestation(secondAttestation);
        setFinalExam(finalExam);
    }
    
    /**
     * Get mark breakdown as formatted string
     */
    public String getMarkBreakdown() {
        // TODO: Build and return a formatted multi-line mark report.
        // Include attestation components, final exam, total, letter grade, grade points, and pass/fail status.
        return "";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark = (Mark) o;
        return Objects.equals(studentId, mark.studentId) && 
               Objects.equals(courseId, mark.courseId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
    
    @Override
    public String toString() {
        // TODO: Return a compact summary string with studentId, courseId, totalMark, and grade.
        return "";
    }
}