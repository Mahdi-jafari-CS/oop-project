package model.comparators;

import model.research.ResearchPaper;
import java.util.Comparator;

/**
 * Factory class providing different comparators for ResearchPaper objects
 * Implements Strategy pattern for different sorting strategies
 */
public class ResearchPaperComparators {
    
    /**
     * Comparator for sorting by publication date (most recent first)
     */
    public static class ByDateComparator implements Comparator<ResearchPaper> {
        @Override
        public int compare(ResearchPaper p1, ResearchPaper p2) {
            // TODO: Compare by publication date (most recent first).
            // Handle null dates explicitly so comparator remains null-safe.
            return 0;
        }
    }
    
    /**
     * Comparator for sorting by citations (most cited first)
     */
    public static class ByCitationsComparator implements Comparator<ResearchPaper> {
        @Override
        public int compare(ResearchPaper p1, ResearchPaper p2) {
            return Integer.compare(p2.getCitations(), p1.getCitations()); // Descending
        }
    }
    
    /**
     * Comparator for sorting by number of pages (longest first)
     */
    public static class ByPagesComparator implements Comparator<ResearchPaper> {
        @Override
        public int compare(ResearchPaper p1, ResearchPaper p2) {
            return Integer.compare(p2.getPages(), p1.getPages()); // Descending
        }
    }
    
    /**
     * Comparator for sorting by title alphabetically
     */
    public static class ByTitleComparator implements Comparator<ResearchPaper> {
        @Override
        public int compare(ResearchPaper p1, ResearchPaper p2) {
            return p1.getTitle().compareToIgnoreCase(p2.getTitle());
        }
    }
    
    /**
     * Comparator for sorting by impact factor (highest first)
     */
    public static class ByImpactFactorComparator implements Comparator<ResearchPaper> {
        @Override
        public int compare(ResearchPaper p1, ResearchPaper p2) {
            return Double.compare(p2.calculateImpactFactor(), p1.calculateImpactFactor());
        }
    }
    
    /**
     * Get comparator by type name
     */
    public static Comparator<ResearchPaper> getComparator(String type) {
        // TODO: Return the comparator strategy based on `type`.
        // Supported keys: date, citations, pages, title, impact.
        // Default strategy should be ByDateComparator.
        return new ByDateComparator();
    }
    
    /**
     * Print available comparator types
     */
    public static void printAvailableComparators() {
        System.out.println("Available sorting options:");
        System.out.println("1. date - Sort by publication date (most recent first)");
        System.out.println("2. citations - Sort by number of citations (most cited first)");
        System.out.println("3. pages - Sort by number of pages (longest first)");
        System.out.println("4. title - Sort alphabetically by title");
        System.out.println("5. impact - Sort by impact factor (highest first)");
    }
}