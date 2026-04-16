package model.research;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract decorator class implementing Researcher interface
 * Uses Decorator pattern to add researcher functionality to User objects
 */
public abstract class ResearcherDecorator implements Researcher {
    
    protected Researcher decoratedResearcher;
    protected List<ResearchPaper> researchPapers;
    protected List<ResearchProject> researchProjects;
    protected int hIndex;
    
    public ResearcherDecorator(Researcher decoratedResearcher) {
        this.decoratedResearcher = decoratedResearcher;
        this.researchPapers = new ArrayList<>();
        this.researchProjects = new ArrayList<>();
        this.hIndex = 0;
    }
    
    // Default implementations that delegate to decorated researcher
    
    @Override
    public String getResearcherId() {
        return decoratedResearcher.getResearcherId();
    }
    
    @Override
    public String getResearcherName() {
        return decoratedResearcher.getResearcherName();
    }
    
    @Override
    public int getHIndex() {
        return hIndex;
    }
    
    @Override
    public void calculateHIndex() {
        // TODO: Recompute hIndex from researchPapers using standard h-index algorithm.
        this.hIndex = 0;
    }
    
    @Override
    public List<ResearchPaper> getResearchPapers() {
        return new ArrayList<>(researchPapers);
    }
    
    @Override
    public void addResearchPaper(ResearchPaper paper) {
        if (!researchPapers.contains(paper)) {
            researchPapers.add(paper);
            calculateHIndex(); // Recalculate h-index when new paper added
        }
    }
    
    @Override
    public List<ResearchProject> getResearchProjects() {
        return new ArrayList<>(researchProjects);
    }
    
    @Override
    public void addResearchProject(ResearchProject project) {
        if (!researchProjects.contains(project)) {
            researchProjects.add(project);
        }
    }
    
    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        // TODO: Sort researchPapers using comparator and print a structured numbered list.
        // Include at least title, journal, publication date, citations, pages, and DOI for each paper.
    }
    
    @Override
    public int getTotalCitations() {
        return researchPapers.stream()
                .mapToInt(ResearchPaper::getCitations)
                .sum();
    }
    
    @Override
    public double getAverageCitationsPerPaper() {
        if (researchPapers.isEmpty()) return 0.0;
        return (double) getTotalCitations() / researchPapers.size();
    }
    
    @Override
    public ResearchPaper getMostCitedPaper() {
        if (researchPapers.isEmpty()) return null;
        
        return researchPapers.stream()
                .max(Comparator.comparingInt(ResearchPaper::getCitations))
                .orElse(null);
    }
    
    @Override
    public boolean qualifiesAsSupervisor() {
        return hIndex >= 3;
    }
    
    @Override
    public String getResearcherStatistics() {
        // TODO: Return a detailed multi-line statistics report.
        // Include identity, h-index, publication/project counts, citation metrics,
        // supervisor eligibility, and optionally most cited paper details.
        return "";
    }
    
    /**
     * Get formatted string of all papers
     */
    public String getPapersFormatted(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> sortedPapers = new ArrayList<>(researchPapers);
        sortedPapers.sort(comparator);
        
        StringBuilder sb = new StringBuilder();
        sb.append("Research Papers for ").append(getResearcherName()).append(":\n");
        sb.append("==============================================\n");
        for (int i = 0; i < sortedPapers.size(); i++) {
            ResearchPaper paper = sortedPapers.get(i);
            sb.append(i + 1).append(". ").append(paper.getFormattedCitation()).append("\n");
            sb.append("   Citations: ").append(paper.getCitations()).append("\n");
            sb.append("   Pages: ").append(paper.getPages()).append("\n");
            sb.append("   Impact Factor: ").append(String.format("%.2f", paper.calculateImpactFactor())).append("\n\n");
        }
        return sb.toString();
    }
}