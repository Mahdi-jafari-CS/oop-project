// Assigned to: Каналхан Б. Н.
package model.research;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing a research paper with fields from IEEE standards
 */
public class ResearchPaper implements Serializable, Comparable<ResearchPaper> {
    private static final long serialVersionUID = 1L;
    
    private String title;
    private List<String> authors;
    private String journal;
    private LocalDate publicationDate;
    private int citations;
    private int pages;
    private String doi;
    private String abstractText;
    private List<String> keywords;
    
    public ResearchPaper(String title, List<String> authors, String journal, 
                        LocalDate publicationDate, int citations, int pages, 
                        String doi, String abstractText, List<String> keywords) {
        this.title = title;
        this.authors = new ArrayList<>(authors);
        this.journal = journal;
        this.publicationDate = publicationDate;
        this.citations = citations;
        this.pages = pages;
        this.doi = doi;
        this.abstractText = abstractText;
        this.keywords = new ArrayList<>(keywords);
    }
    
    // Getters and setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public List<String> getAuthors() {
        return new ArrayList<>(authors);
    }
    
    public void setAuthors(List<String> authors) {
        this.authors = new ArrayList<>(authors);
    }
    
    public void addAuthor(String author) {
        if (!authors.contains(author)) {
            authors.add(author);
        }
    }
    
    public String getJournal() {
        return journal;
    }
    
    public void setJournal(String journal) {
        this.journal = journal;
    }
    
    public LocalDate getPublicationDate() {
        return publicationDate;
    }
    
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
    
    public int getCitations() {
        return citations;
    }
    
    public void setCitations(int citations) {
        this.citations = citations;
    }
    
    public int getPages() {
        return pages;
    }
    
    public void setPages(int pages) {
        this.pages = pages;
    }
    
    public String getDoi() {
        return doi;
    }
    
    public void setDoi(String doi) {
        this.doi = doi;
    }
    
    public String getAbstractText() {
        return abstractText;
    }
    
    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }
    
    public List<String> getKeywords() {
        return new ArrayList<>(keywords);
    }
    
    public void setKeywords(List<String> keywords) {
        this.keywords = new ArrayList<>(keywords);
    }
    
    public void addKeyword(String keyword) {
        if (!keywords.contains(keyword)) {
            keywords.add(keyword);
        }
    }
    
    // Calculate impact factor based on citations and publication date
    public double calculateImpactFactor() {
        // TODO: Compute impact factor using citations and publication age.
        // Suggested rule: citations / yearsSincePublication, with safe handling for null/recent dates.
        return 0.0;
    }
    
    // Check if paper is recent (published within last 2 years)
    public boolean isRecent() {
        if (publicationDate == null) return false;
        return publicationDate.isAfter(LocalDate.now().minusYears(2));
    }
    
    @Override
    public int compareTo(ResearchPaper other) {
        // TODO: Compare papers by publication date (most recent first), null-safe.
        return 0;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResearchPaper that = (ResearchPaper) o;
        return Objects.equals(doi, that.doi) || Objects.equals(title, that.title);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(doi, title);
    }
    
    @Override
    public String toString() {
        // TODO: Return a compact one-line description of the paper.
        // Include title, journal, year/date, citations, pages, and DOI when available.
        return "";
    }
    
    /**
     * Get formatted citation in APA style
     */
    public String getFormattedCitation() {
        // TODO: Build an APA-like citation string.
        // Include up to first 3 authors (+ "et al." if more), year, title, journal, and DOI URL.
        return "";
    }
}