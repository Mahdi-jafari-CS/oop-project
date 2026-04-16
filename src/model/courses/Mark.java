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
        if (totalMark >= 95) {
            grade = "A+";
        } else if (totalMark >= 90) {
            grade = "A";
        } else if (totalMark >= 85) {
            grade = "A-";
        } else if (totalMark >= 80) {
            grade = "B+";
        } else if (totalMark >= 75) {
            grade = "B";
        } else if (totalMark >= 70) {
            grade = "B-";
        } else if (totalMark >= 65) {
            grade = "C+";
        } else if (totalMark >= 60) {
            grade = "C";
        } else if (totalMark >= 55) {
            grade = "C-";
        } else if (totalMark >= 50) {
            grade = "D";
        } else {
            grade = "F";
        }
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
        switch (grade) {
            case "A+": return 4.0;
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "C-": return 1.7;
            case "D": return 1.0;
            case "F": return 0.0;
            default: return 0.0;
        }
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
        StringBuilder sb = new StringBuilder();
        sb.append("Mark Breakdown for Student ").append(studentId).append(" in Course ").append(courseId).append(":\n");
        sb.append("  First Attestation: ").append(String.format("%.1f", firstAttestation)).append("/30\n");
        sb.append("  Second Attestation: ").append(String.format("%.1f", secondAttestation)).append("/30\n");
        sb.append("  Final Exam: ").append(String.format("%.1f", finalExam)).append("/40\n");
        sb.append("  Total Mark: ").append(String.format("%.1f", totalMark)).append("/100\n");
        sb.append("  Grade: ").append(grade).append(" (Grade Points: ").append(String.format("%.1f", getGradePoints())).append(")\n");
        sb.append("  Status: ").append(isPassing() ? "PASS" : "FAIL").append("\n");
        return sb.toString();
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
        return "Mark{" +
                "studentId='" + studentId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", totalMark=" + String.format("%.1f", totalMark) +
                ", grade='" + grade + '\'' +
                '}';
    }
}