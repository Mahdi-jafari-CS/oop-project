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
        List<Integer> citations = papers.stream()
                .map(ResearchPaper::getCitations)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        int calculatedHIndex = 0;
        for (int i = 0; i < citations.size(); i++) {
            int rank = i + 1;
            if (citations.get(i) >= rank) {
                calculatedHIndex = rank;
            } else {
                break;
            }
        }
        this.hIndex = calculatedHIndex;
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
        return papers.stream()
                .max(Comparator.comparingInt(ResearchPaper::getCitations))
                .orElse(null);
    }
    
    @Override
    public boolean qualifiesAsSupervisor() {
        return hIndex >= 3;
    }
    
    @Override
    public String getResearcherStatistics() {
        return String.format(
                "Researcher: %s | h-index: %d | Papers: %d",
                getResearcherName(),
                getHIndex(),
                papers.size()
        );
    }
}