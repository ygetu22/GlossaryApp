import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;

/**
 * Tests for the GlossaryClass methods.
 *
 * @author Yakob Getu
 */
public class GlossaryClassTest {

    /**
     * Test cases for removeMinimumValue method.
     */

    /**
     * Challenging: Tests removing the minimum value from a queue with duplicate
     * values.
     */
    @Test
    public void testRemoveMinimumValueDuplicateValues() {
        Queue<String> q = new Queue1L<>();
        q.enqueue("Apple");
        q.enqueue("Build");
        q.enqueue("Build");
        String min = GlossaryClass.removeMinimumValue(q,
                new Comparator<String>() {
                    @Override
                    public int compare(String str1, String str2) {
                        return str1.compareTo(str2);
                    }
                });
        assertEquals("Apple", min);
    }

    /**
     * Routine: Tests removing the minimum value from a queue with unique
     * values.
     */

    @Test
    public void routineRemoveMinimumValueUniqueValues() {
        Queue<String> q = new Queue1L<>();
        q.enqueue("Algorithm");
        q.enqueue("Binary");
        q.enqueue("Compile");
        String min = GlossaryClass.removeMinimumValue(q,
                new Comparator<String>() {
                    @Override
                    public int compare(String str1, String str2) {
                        return str1.compareTo(str2);
                    }
                });
        assertEquals("Algorithm", min);
    }

    /**
     * Edge: Tests the removal of the minimum value from a queue with unique
     * strings.
     */

    @Test
    public void testRemoveMinimumValueSingleElement() {
        Queue<String> q = new Queue1L<>();
        q.enqueue("Science");
        String min = GlossaryClass.removeMinimumValue(q,
                new Comparator<String>() {
                    @Override
                    public int compare(String str1, String str2) {
                        return str1.compareTo(str2);
                    }
                });
        assertEquals("Science", min);
    }

    /**
     * Test cases for sortQueue method.
     */

    /**
     * Challenging: Tests sorting a queue with duplicate values.
     */
    @Test
    public void testSortQueueDuplicateValues() {
        Queue<String> q = new Queue1L<>();
        q.enqueue("Soccer");
        q.enqueue("Football");
        q.enqueue("Football");
        GlossaryClass.sortQueue(q, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });
        assertEquals("Football", q.front());
    }

    /**
     * Routine: Tests sorting a queue with different values.
     */
    @Test
    public void routineSortQueueUniqueValues() {
        Queue<String> q = new Queue1L<>();
        q.enqueue("Basketball");
        q.enqueue("Tennis");
        q.enqueue("Soccer");
        GlossaryClass.sortQueue(q, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });
        assertEquals("Basketball", q.front());
    }

    /**
     * Edge: Tests sorting an empty queue.
     */
    @Test
    public void edgeSortQueueEmptyQueue() {
        Queue<String> q = new Queue1L<>();
        GlossaryClass.sortQueue(q, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });
        assertEquals(0, q.length());
    }

    /**
     * Test cases for generateAndAddElements method.
     */

    /**
     * Edge: Tests generating elements from an empty string.
     */
    @Test
    public void edgeGenerateAndAddElementsEmptyString() {
        Set<Character> charSet = new Set1L<>();
        GlossaryClass.generateAndAddElements("", charSet);
        assertEquals(0, charSet.size());
    }

    /**
     * Boundary: Tests generating elements with single character.
     */
    @Test
    public void boundaryGenerateAndAddElementsSingleCharacter() {
        Set<Character> charSet = new Set1L<>();
        GlossaryClass.generateAndAddElements("A", charSet);
        assertEquals(1, charSet.size());
    }

    /**
     * Normal: Tests generating elements with a mix of characters and numbers.
     */
    @Test
    public void normalGenerateAndAddElementsMixCharactersAndNumbers() {
        final int expectedSize = 7;
        Set<Character> charSet = new Set1L<>();
        GlossaryClass.generateAndAddElements("Hello123", charSet);
        assertEquals(expectedSize, charSet.size());
    }

    /**
     * Test cases for findNextWordOrSeparator method.
     */

    /**
     * Empty: Tests finding the next word or separator with an empty string.
     */
    @Test
    public void testFindNextWordOrSeparatorEmpty() {
        Set1L<Character> separators = new Set1L<>();
        separators.add('.');
        separators.add(',');
        String result = GlossaryClass.findNextWordOrSeparator("", 0,
                separators);
        assertEquals("Method should return null for empty input", "", result);
    }

    /**
     * Double Separators at Start: Tests input starting with back-to-back
     * separators.
     */
    @Test
    public void testFindNextWordOrSeparatorDoubleSeparatorsStart() {
        Set1L<Character> separators = new Set1L<>();
        separators.add('.');
        separators.add(',');
        String result = GlossaryClass.findNextWordOrSeparator(",Hello, world.",
                0, separators);
        assertEquals("Method should return the first separator", ",", result);
    }

    /**
     * Challenging: Tests finding the next word or separator with mixed
     * characters.
     */

    @Test
    public void testFindNextWordOrSeparatorMixedCharacters() {
        Set<Character> separators = new Set1L<>();
        separators.add('.');
        separators.add(',');
        String result = GlossaryClass.findNextWordOrSeparator("Buckeye,Banana.",
                0, separators);
        assertEquals("Buckeye", result);
    }

    /**
     * Normal: Tests finding the next word or separator with typical input.
     */
    @Test
    public void testFindNextWordOrSeparatorNormal() {
        Set<Character> separators = new Set1L<>();
        separators.add('.');
        separators.add(',');
        String result = GlossaryClass.findNextWordOrSeparator("Hello,World.", 0,
                separators);
        assertEquals("Hello", result);
    }

    //empty check

    //another test case method, start with a comma, hello world.double
    //seperators back to back start from the begiing

    /**
     * Boundary: Tests finding the next word or separator at the boundary.
     */
    @Test
    public void testFindNextWordOrSeparatorBoundary() {
        Set<Character> separators = new Set1L<>();
        separators.add('.');
        separators.add(',');
        String result = GlossaryClass.findNextWordOrSeparator("One.", 0,
                separators);
        assertEquals("One", result);
    }

    /**
     * Routine: Tests finding the next word or separator with routine input.
     */
    @Test
    public void testFindNextWordOrSeparatorRoutine() {
        Set<Character> separators = new Set1L<>();
        separators.add('.');
        separators.add(',');
        String result = GlossaryClass.findNextWordOrSeparator("Three,Four.", 0,
                separators);
        assertEquals("Three", result);
    }

    /**
     * Test cases for generatePage method.
     */

    private static final String TEST_DIRECTORY = "test_pages";
    /**
     * Normal: Tests generating a page with typical input.
     */
    private File testFolder;

    /**
     * Sets up a test directory for file operations.
     *
     * @throws IOException
     *             if the directory cannot be created.
     */
    @Before
    public void setUp() throws IOException {
        this.testFolder = new File(TEST_DIRECTORY);
        if (!this.testFolder.exists()) {
            Files.createDirectories(Paths.get(TEST_DIRECTORY));
        }
    }

    /**
     * Deletes the test directory and its files after testing.
     */
    @After
    public void tearDown() {
        for (File file : this.testFolder.listFiles()) {
            file.delete();
        }
        this.testFolder.delete();
    }

    /**
     * Normal: Tests generating a page with typical input.
     */
    @Test
    public void testGeneratePageNormal() {
        Map<String, String> glossary = new Map1L<>();
        glossary.add("Term", "This is the definition of the term.");
        GlossaryClass.generatePage("Term", glossary, TEST_DIRECTORY);

        // Assert that the file is generated
        assertTrue(new File(TEST_DIRECTORY + "/Term.html").exists());
    }

    /**
     * Test cases for generateIndexPage method.
     */

    /**
     * Routine: Test case to verify the generation of index page with non-empty
     * queue.
     */
    @Test
    public void testGenerateIndexPageWithNonEmptyQueue() {
        String folder = "test_output";
        Queue<String> terms = new Queue1L<>();
        terms.enqueue("Term1");
        terms.enqueue("Term2");
        terms.enqueue("Term3");
        GlossaryClass.generateIndexPage(terms, folder);
        File indexFile = new File(folder + "/index.html");
        assertTrue("Index file should exist", indexFile.exists());
        assertTrue("Index file should not be empty", indexFile.length() > 0);
    }

}
