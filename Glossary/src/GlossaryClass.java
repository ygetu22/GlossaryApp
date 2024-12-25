import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Glossary Project.
 *
 * @author Yakob Getu
 *
 */
public final class GlossaryClass {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private GlossaryClass() {
    }

    /**
     * Compare {@code String}s in lexicographic order.
     */

    /**
     * Custom comparator for String objects to be used for sorting.
     */
    public static class StringComparator implements Comparator<String> {
        /**
         * Compares two strings .
         *
         * @param str1
         *            the first string to be compared
         * @param str2
         *            the second string to be compared
         * @return a negative integer, zero, or a positive integer as the first
         *         argument is less than, equal to, or greater than the second
         */
        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }

    /**
     * Removes and returns the minimum value from {@code q} according to the
     * ordering provided by the {@code compare} method from {@code order}.
     *
     * @param q
     *            the queue
     * @param order
     *            ordering by which to compare entries
     * @return the minimum value from {@code q}
     * @updates q
     * @requires <pre>
     * q /= empty_string  and
     *  [the relation computed by order.compare is a total preorder]
     * </pre>
     * @ensures <pre>
     * perms(q * <removeMin>, #q)  and
     *  for all x: string of character
     *      where (x is in entries (q))
     *    ([relation computed by order.compare method](removeMin, x))
     * </pre>
     */
    static String removeMinimumValue(Queue<String> q,
            Comparator<String> order) {
        String minVal = q.front();
        // Loop through each element in the queue to find the minimum value
        for (String str : q) {
            if (order.compare(str, minVal) < 0) {
                minVal = str + "";
            }
        }
        boolean removed = false;
        // Iterate through the queue to remove the minimum value
        for (int i = 0; i <= q.length(); i++) {
            String currentVal = q.dequeue();
            if (order.compare(minVal, currentVal) == 0 && !removed) {
                removed = true;
            } else {
                q.enqueue(currentVal);
            }
        }
        return minVal;
    }

    /**
     * Sorts {@code q} according to the ordering provided by the {@code compare}
     * method from {@code order}.
     *
     * @param q
     *            the queue
     * @param order
     *            ordering by which to sort
     * @updates q
     * @requires [the relation computed by order.compare is a total preorder]
     * @ensures q = [#q ordered by the relation computed by order.compare]
     */
    public static void sortQueue(Queue<String> q, Comparator<String> order) {
        // Create a temporary queue to store sorted elements
        Queue<String> tempQueue = new Queue1L<String>();
        int lengthOfInputQueue = q.length();
        // Iterate through the input queue to sort its elements
        for (int i = 0; i < lengthOfInputQueue; i++) {
            tempQueue.enqueue(removeMinimumValue(q, order));
        }
        q.transferFrom(tempQueue);
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    static void generateAndAddElements(String str,
            // Clear the existing elements in the character set
            Set<Character> charSet) {
        charSet.clear();
        // Iterate through each character in the string
        for (Character character : str.toCharArray()) {
            if (!charSet.contains(character)) {
                charSet.add(character);
            }
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    public static String findNextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        String result = "";
        int currentIndex = position;

        // Check if the character at the given position is a separator
        if (position < text.length()
                && separators.contains(text.charAt(position))) {
            while (separators.contains(text.charAt(currentIndex))) {
                currentIndex++;
            }
            result = text.substring(position, currentIndex);

        } else {
            // Move the current index forward until a separator character is found
            while (position < text.length()
                    && !separators.contains(text.charAt(currentIndex))) {
                currentIndex++;
            }
            result = text.substring(position, currentIndex);
        }

        return result;
    }

    /**
     * Takes the the term and a map containing all terms and definitions and the
     * output folder. Outputs an html file with the term and definition in the
     * output folder.
     *
     * @param term
     *            the term for which the page is being created
     * @param m
     *            the map containing all the terms and definitions
     * @param folder
     *            the output folder of all the html files
     * @updates out.content
     * @requires [term has no spaces] and out.is_open
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML page with the term and definition]
     * </pre>
     */
    public static void generatePage(String term, Map<String, String> m,
            // Create a writer for the HTML file corresponding to the term
            String folder) {
        SimpleWriter writer = new SimpleWriter1L(
                folder + "//" + term + ".html");

        // Prints header
        writer.println(
                "<html>\n<head>\n\t<title>" + term + "</title>\n</head>");

        // Prints term
        writer.println("<body>\n<h2><b><i><font color=\"red\">" + term
                + "</font></i></b></h2>\n");
        writer.print("<blockquote>");

        int currentPosition = 0;

        String definition = m.value(term);
        // Create a set of separators
        Set<Character> separators = new Set1L<Character>();
        generateAndAddElements(" \t,.", separators);
        // Loop through the definition to extract tokens
        while (currentPosition < definition.length() - 1) {
            String token = findNextWordOrSeparator(definition, currentPosition,
                    separators);
            if (m.hasKey(token)) {
                writer.println(
                        "<a href=\"" + token + ".html\">" + token + "</a>");
            } else {
                writer.println(token);
            }
            currentPosition += token.length();
        }

        writer.print("</blockquote>\n");

        // Prints footer
        writer.println(
                "<hr />\r\n<p>Return to <a href=\"index.html\">index</a>."
                        + "</p>\r\n</body>\r\n</html>");

        writer.close();
    }

    /**
     * Takes the queue of terms and an output folder, and creates an html file
     * in the output folder with a list of all the terms hyperlinked.
     *
     * @param terms
     *            the queue with all the terms
     * @param folder
     *            the output folder of all the html files
     * @updates out.content
     * @requires out.is_open
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML page with the terms]
     * </pre>
     */
    public static void generateIndexPage(Queue<String> terms, String folder) {
        // Create a writer for the index.html file
        SimpleWriter writer = new SimpleWriter1L(folder + "//index.html");

        // Print HTML header and body header
        writer.println("<html>\r\n" + "<head>\r\n"
                + "<title>Glossary</title>\r\n" + "</head>");
        writer.println("<body>\r\n" + "<h2>Glossary</h2>\r\n" + "<hr />\r\n"
                + "<h3>Index</h3>\r\n" + "<ul>");

        // Print term page links
        Queue<String> temporaryQueue = new Queue1L<String>();
        while (terms.length() > 0) {
            String term = terms.dequeue();
            writer.println(
                    "<li><a href=\"" + term + ".html\">" + term + "</a></li>");
            temporaryQueue.enqueue(term);
        }
        terms.transferFrom(temporaryQueue);

        // Print HTML footer and close the writer
        writer.println("</ul>\r\n" + "</body>\r\n" + "</html>");
        writer.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        // Create reader and writer for input and output
        SimpleReader reader = new SimpleReader1L();
        SimpleWriter writer = new SimpleWriter1L();

        // Prompt user for input file name and output folder name
        writer.println("Enter the input file name:");
        String inputFileName = reader.nextLine();
        writer.println("Enter the output folder name:");
        String outputFolderName = reader.nextLine();

        // Read terms and definitions from input file
        Queue<String> termQueue = new Queue1L<String>();
        Map<String, String> glossaryMap = new Map1L<>();
        SimpleReader fileReader = new SimpleReader1L(inputFileName);
        while (!fileReader.atEOS()) {
            String term = fileReader.nextLine();
            termQueue.enqueue(term);
            String definition = "";
            String line = "";
            do {
                line = fileReader.nextLine();
                definition += " " + line;
            } while (!line.equals(""));
            glossaryMap.add(term, definition);
        }

        // Sort the terms alphabetically
        Comparator<String> stringComparator = new StringComparator();
        sortQueue(termQueue, stringComparator);

        // Create index page
        generateIndexPage(termQueue, outputFolderName);

        // Create page for each term
        Queue<String> tempQueue = new Queue1L<String>();
        while (termQueue.length() > 0) {
            String term = termQueue.dequeue();
            generatePage(term, glossaryMap, outputFolderName);
            tempQueue.enqueue(term);
        }
        termQueue.transferFrom(tempQueue);

        // Close input and output streams
        fileReader.close();
        reader.close();
        writer.close();
    }

}
