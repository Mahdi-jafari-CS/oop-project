// Assigned to: Каналхан Б. Н.
package model.research;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic implementation of Researcher interface for use in decorator pattern
 */
public class BasicResearcher implements Researcher {
    private String id;
    private String name;
    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;
    private int hIndex;
    
    public BasicResearcher(String id, String name) {
        this.id = id;
        this.name = name;
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.hIndex = 0;
    }
    
    @Override
    public String getResearcherId() {
        return id;
    }
    
    @Override
    public String getResearcherName() {
        return name;
    }
    
    @Override
    public int getHIndex() {
        return hIndex;
    }
    
    @Override
    public void calculateHIndex() {
        // TODO: Implement h-index calculation.
        // Steps:
        // 1) Collect citations and sort descending.
        // 2) Find maximum h where at least h papers have >= h citations.
        // 3) Store result in this.hIndex.
        this.hIndex = 0;
    }
    
    @Override
    public List<ResearchPaper> getResearchPapers() {
        return new ArrayList<>(papers);
    }
    
    @Override
    public void addResearchPaper(ResearchPaper paper) {
        if (!papers.contains(paper)) {
            papers.add(paper);
            calculateHIndex();
        }
    }
    
    @Override
    public List<ResearchProject> getResearchProjects() {
        return new ArrayList<>(projects);
    }
    
    @Override
    public void addResearchProject(ResearchProject project) {
        if (!projects.contains(project)) {
            projects.add(project);
        }
    }
    
    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> sorted = new ArrayList<>(papers);
        sorted.sort(comparator);
        for (ResearchPaper paper : sorted) {
            System.out.println(paper.getFormattedCitation());
        }
    }
    
    @Override
    public int getTotalCitations() {
        return papers.stream().mapToInt(ResearchPaper::getCitations).sum();
    }
    
    @Override
    public double getAverageCitationsPerPaper() {
        if (papers.isEmpty()) return 0.0;
        return (double) getTotalCitations() / papers.size();
    }
    
    @Override
    public ResearchPaper getMostCitedPaper() {
        // TODO: Return the paper with highest citations, or null when no papers exist.
        return null;
    }
    
    @Override
    public boolean qualifiesAsSupervisor() {
        return hIndex >= 3;
    }
    
    @Override
    public String getResearcherStatistics() {
        // TODO: Return a concise statistics string with name, h-index, and paper count.
        return "";
    }
}