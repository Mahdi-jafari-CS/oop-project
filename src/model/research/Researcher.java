package model.research;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * Interface defining the contract for researchers in the university system
 * Using Decorator pattern to add researcher functionality to existing User objects
 */
public interface Researcher extends Serializable {
    
    /**
     * Get the researcher's unique ID
     */
    String getResearcherId();
    
    /**
     * Get the researcher's name
     */
    String getResearcherName();
    
    /**
     * Get the researcher's h-index
     */
    int getHIndex();
    
    /**
     * Calculate and update the h-index based on published papers
     */
    void calculateHIndex();
    
    /**
     * Get all research papers by this researcher
     */
    List<ResearchPaper> getResearchPapers();
    
    /**
     * Add a research paper to the researcher's publications
     */
    void addResearchPaper(ResearchPaper paper);
    
    /**
     * Get all research projects the researcher is participating in
     */
    List<ResearchProject> getResearchProjects();
    
    /**
     * Add a research project to the researcher's portfolio
     */
    void addResearchProject(ResearchProject project);
    
    /**
     * Print research papers sorted by the given comparator
     * @param comparator Comparator to define sorting order (by date, citations, pages)
     */
    void printPapers(Comparator<ResearchPaper> comparator);
    
    /**
     * Get total citations across all papers
     */
    int getTotalCitations();
    
    /**
     * Get average citations per paper
     */
    double getAverageCitationsPerPaper();
    
    /**
     * Get the most cited paper
     */
    ResearchPaper getMostCitedPaper();
    
    /**
     * Check if researcher qualifies as a supervisor (h-index >= 3)
     */
    boolean qualifiesAsSupervisor();
    
    /**
     * Get researcher statistics as a formatted string
     */
    String getResearcherStatistics();
}