package model.research;

import exceptions.NotResearcherException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing a research project in the university
 */
public class ResearchProject implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String projectId;
    private String topic;
    private String description;
    private String fundingAgency;
    private double budget;
    private List<ResearchPaper> publishedPapers;
    private List<String> participantIds; // IDs of researchers participating
    
    public ResearchProject(String projectId, String topic, String description, 
                          String fundingAgency, double budget) {
        this.projectId = projectId;
        this.topic = topic;
        this.description = description;
        this.fundingAgency = fundingAgency;
        this.budget = budget;
        this.publishedPapers = new ArrayList<>();
        this.participantIds = new ArrayList<>();
    }
    
    // Getters and setters
    public String getProjectId() {
        return projectId;
    }
    
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    
    public String getTopic() {
        return topic;
    }
    
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getFundingAgency() {
        return fundingAgency;
    }
    
    public void setFundingAgency(String fundingAgency) {
        this.fundingAgency = fundingAgency;
    }
    
    public double getBudget() {
        return budget;
    }
    
    public void setBudget(double budget) {
        this.budget = budget;
    }
    
    public List<ResearchPaper> getPublishedPapers() {
        return new ArrayList<>(publishedPapers);
    }
    
    public void setPublishedPapers(List<ResearchPaper> publishedPapers) {
        this.publishedPapers = new ArrayList<>(publishedPapers);
    }
    
    public void addPublishedPaper(ResearchPaper paper) {
        if (!publishedPapers.contains(paper)) {
            publishedPapers.add(paper);
        }
    }
    
    public void removePublishedPaper(ResearchPaper paper) {
        publishedPapers.remove(paper);
    }
    
    public List<String> getParticipantIds() {
        return new ArrayList<>(participantIds);
    }
    
    public void setParticipantIds(List<String> participantIds) {
        this.participantIds = new ArrayList<>(participantIds);
    }
    
    /**
     * Add a researcher to the project
     * @param researcherId ID of the researcher to add
     * @param isResearcher Boolean indicating if the person is a researcher
     * @throws NotResearcherException if the person is not a researcher
     */
    public void addParticipant(String researcherId, boolean isResearcher) throws NotResearcherException {
        if (!isResearcher) {
            throw new NotResearcherException(researcherId, "Non-Researcher");
        }
        
        if (!participantIds.contains(researcherId)) {
            participantIds.add(researcherId);
        }
    }
    
    /**
     * Remove a participant from the project
     */
    public void removeParticipant(String researcherId) {
        participantIds.remove(researcherId);
    }
    
    /**
     * Check if a researcher is participating in this project
     */
    public boolean hasParticipant(String researcherId) {
        return participantIds.contains(researcherId);
    }
    
    /**
     * Calculate total citations from all published papers
     */
    public int getTotalCitations() {
        return publishedPapers.stream()
                .mapToInt(ResearchPaper::getCitations)
                .sum();
    }
    
    /**
     * Calculate average citations per paper
     */
    public double getAverageCitationsPerPaper() {
        if (publishedPapers.isEmpty()) return 0.0;
        return (double) getTotalCitations() / publishedPapers.size();
    }
    
    /**
     * Get project status based on publications
     */
    public String getProjectStatus() {
        int paperCount = publishedPapers.size();
        if (paperCount == 0) return "Planning Phase";
        if (paperCount < 3) return "Active - Early Stage";
        if (paperCount < 10) return "Active - Productive";
        return "Active - Highly Productive";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResearchProject that = (ResearchProject) o;
        return Objects.equals(projectId, that.projectId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(projectId);
    }
    
    @Override
    public String toString() {
        return "ResearchProject{" +
                "projectId='" + projectId + '\'' +
                ", topic='" + topic + '\'' +
                ", participants=" + participantIds.size() +
                ", papers=" + publishedPapers.size() +
                ", totalCitations=" + getTotalCitations() +
                ", budget=" + budget +
                '}';
    }
    
    /**
     * Get detailed project report
     */
    public String getProjectReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Research Project Report\n");
        sb.append("=======================\n");
        sb.append("Project ID: ").append(projectId).append("\n");
        sb.append("Topic: ").append(topic).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Funding Agency: ").append(fundingAgency).append("\n");
        sb.append("Budget: $").append(String.format("%,.2f", budget)).append("\n");
        sb.append("Status: ").append(getProjectStatus()).append("\n");
        sb.append("Participants: ").append(participantIds.size()).append("\n");
        sb.append("Published Papers: ").append(publishedPapers.size()).append("\n");
        sb.append("Total Citations: ").append(getTotalCitations()).append("\n");
        sb.append("Average Citations per Paper: ").append(String.format("%.2f", getAverageCitationsPerPaper())).append("\n");
        
        if (!publishedPapers.isEmpty()) {
            sb.append("\nPublished Papers:\n");
            for (int i = 0; i < publishedPapers.size(); i++) {
                ResearchPaper paper = publishedPapers.get(i);
                sb.append(i + 1).append(". ").append(paper.getTitle())
                  .append(" (").append(paper.getCitations()).append(" citations)\n");
            }
        }
        
        return sb.toString();
    }
}