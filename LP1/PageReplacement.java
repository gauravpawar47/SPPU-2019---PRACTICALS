import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;

public class PageReplacement {

    static int pageFrames = 0;

    // Least Recently Used (LRU) Page Replacement Algorithm
    static int lru(int referenceString[]) {
        // This array list will contain all the pages that are currently in memory
        ArrayList<Integer> pages = new ArrayList<>(pageFrames);

        // This hashmap will store least recently used indexes of the pages
        HashMap<Integer, Integer> indexes = new HashMap<>();

        // Start from initial page
        int page_faults = 0, n = referenceString.length, curPage;

        for (int i = 0; i < n; i++) {
            curPage = referenceString[i];

            // If page is not present in memory
            if (!pages.contains(curPage)) {
                if (pages.size() < pageFrames) {
                    pages.add(curPage); // Add the current page to memory
                } else {
                    // Find the page with the least recently used index
                    int lruIndex = Integer.MAX_VALUE, pageToBeReplaced = -1;
                    for (int j = 0; j < pages.size(); j++) {
                        int temp = pages.get(j);
                        if (indexes.get(temp) < lruIndex) {
                            lruIndex = indexes.get(temp);
                            pageToBeReplaced = j;
                        }
                    }
                    pages.set(pageToBeReplaced, curPage);
                }

                // Increment page faults
                page_faults++;
                displayPageFrames(pages, page_faults);
            }
            // Update the least recently used index for the current page
            indexes.put(curPage, i);
        }
        return page_faults;
    }

    // Optimal Page Replacement Algorithm
    static int optimal(int referenceString[]) {
        // This array list will contain all the pages that are currently in memory
        ArrayList<Integer> pages = new ArrayList<>(pageFrames);

        // This hashmap will store future use indexes of the pages
        HashMap<Integer, Integer> indexes = new HashMap<>();

        // Start from initial page
        int page_faults = 0, curPage, n = referenceString.length;

        for (int i = 0; i < n; i++) {
            curPage = referenceString[i];

            // If page is not present in memory
            if (!pages.contains(curPage)) {
                if (pages.size() < pageFrames) {
                    pages.add(curPage); // Add the current page to memory
                } else {
                    // Find the page with the farthest future use
                    int optimal = -1, pageToBeReplaced = -1;
                    for (int j = 0; j < pages.size(); j++) {
                        int temp = pages.get(j);
                        if (indexes.get(temp) > optimal) {
                            optimal = indexes.get(temp);
                            pageToBeReplaced = j;
                        }
                    }
                    indexes.remove(pages.get(pageToBeReplaced));
                    pages.set(pageToBeReplaced, curPage);
                }

                // Increment page faults
                page_faults++;
                displayPageFrames(pages, page_faults);
            }

            // Update the current page index
            indexes.put(curPage, findNextIndex(curPage, i, referenceString));
        }
        return page_faults;
    }

    // Find the next index of the current page in the future
    static int findNextIndex(int curPage, int curIndex, int pages[]) {
        // Starting at the current index, find the index of future use of the page
        int i;
        for (i = curIndex + 1; i < pages.length; i++) {
            if (pages[i] == curPage) {
                break;
            }
        }
        return i;
    }

    // Display the current page frames and page faults
    static void displayPageFrames(ArrayList<Integer> pages, int page_faults) {
        System.out.print("At PageFault- " + page_faults + " :: Pages- ");
        for (int i = 0; i < pages.size(); i++) {
            System.out.print(" " + pages.get(i));
        }
        System.out.print("\n");
    }

    // Driver method
    public static void main(String args[]) {
        int pages[] = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1};
        pageFrames = 3;

        int pageFaults;

        System.out.println("--- Implementing Least Recently Used Page Replacement Algorithm -----");
        pageFaults = lru(pages);
        System.out.println("Number of page faults = " + pageFaults);

        System.out.print("\n");

        System.out.println("--- Implementing Optimal Page Replacement Algorithm -----");
        pageFaults = optimal(pages);
        System.out.println("Number of page faults = " + pageFaults);
    }
}
