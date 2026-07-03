package org.filesearch;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a keyword item used in predicate expressions.
 * This class simulates the structure `keywordItems[X].getKeyWord()` from the JavaScript example.
 */
//class KeywordItem {
//    private String keyword;
//
//    public KeywordItem(String keyword) {
//        this.keyword = keyword;
//    }
//
//    public String getKeyWord() {
//        return keyword;
//    }
//
//    // Optional: Override toString for easier debugging
//    @Override
//    public String toString() {
//        return "KeywordItem{keyword='" + keyword + "'}";
//    }
//}

/**
 * A utility class to convert predicate code strings into human-readable descriptions.
 */
public class PredicateConverter {

    /**
     * Converts a predicate code string into a more human-readable description.
     * It identifies `contents.toLowerCase().contains(keywordItems[X].getKeyWord().toLowerCase())`
     * patterns and replaces them with descriptive "Contains 'keyword'" phrases,
     * and converts logical operators `&&` and `||` to "AND" and "OR".
     * It also now handles negation `!`.
     *
     * @param predicateCode The original predicate code string.
     * @param keywordItems A list of `KeywordItem` objects, where each object
     * is expected to contain the actual keyword string.
     * @return The descriptive string.
     */
    public static String convertPredicateToDescription(String predicateCode, List<KeyWordItem> keywordItems) {
        String description = predicateCode;

        // Regex to find and replace individual `contains` conditions.
        // It now optionally captures a leading '!' for negation.
        // Group 1: optional '!'
        // Group 2: index within keywordItems
        Pattern conditionPattern = Pattern.compile(
                "(!?)\\s*contents\\.toLowerCase\\(\\)\\.contains\\(keywordItems\\[(\\d+)\\]\\.getKeyWord\\(\\)\\.toLowerCase\\(\\)\\)"
        );
        Matcher matcher = conditionPattern.matcher(description);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String negation = matcher.group(1); // Will be "!" if present, or ""
            int keywordIndex = Integer.parseInt(matcher.group(2)); // Get the captured digit (index)

            String descriptivePart;
            // Check if keywordItems is valid and the index exists.
            if (keywordItems != null && keywordIndex < keywordItems.size()) {
                // Get the actual keyword from the KeywordItem object.
                String keyword = keywordItems.get(keywordIndex).getKeyWord();
                descriptivePart = "Contains \"" + keyword + "\"";
            } else {
                // Fallback for cases where the keyword cannot be resolved.
                descriptivePart = "UNKNOWN_KEYWORD_" + keywordIndex;
            }

            // Prepend "NOT " if there was a negation operator
            if (!negation.isEmpty()) {
                descriptivePart = "NOT " + descriptivePart;
            }

            // Replace the matched part with the descriptive phrase.
            matcher.appendReplacement(sb, Matcher.quoteReplacement(descriptivePart));
        }
        matcher.appendTail(sb); // Append the rest of the string after the last match

        description = sb.toString();

        // Replace logical AND operator.
        description = description.replace("&&", " AND ");
        // Replace logical OR operator.
        description = description.replace("||", " OR ");

        // Clean up spacing around parentheses and remove extra spaces.
        description = description.replaceAll("\\s*\\(\\s*", " ("); // Space before opening parenthesis
        description = description.replaceAll("\\s*\\)\\s*", ") "); // Space after closing parenthesis
        description = description.replaceAll("\\s{2,}", " ").trim(); // Replace multiple spaces with single space and trim

        return description;
    }

    // Example Usage (main method for demonstration)
    public static void main(String[] args) {
        String predicateCode = "contents.toLowerCase().contains(keywordItems[0].getKeyWord().toLowerCase()) && " +
                "( contents.toLowerCase().contains(keywordItems[1].getKeyWord().toLowerCase()) || " +
                "contents.toLowerCase().contains(keywordItems[2].getKeyWord().toLowerCase()) )";

        List<KeyWordItem> keywordItems = List.of(
                new KeyWordItem("apple"),
                new KeyWordItem("red"),
                new KeyWordItem("fruit")
        );

        String description = convertPredicateToDescription(predicateCode, keywordItems);
        System.out.println("Original Predicate: " + predicateCode);
        System.out.println("Descriptive String: " + description);

        // Demonstrating how to convert an array to a List
        KeyWordItem[] keywordArray = {
                new KeyWordItem("java"),
                new KeyWordItem("programming")
        };
        List<KeyWordItem> keywordItemsFromArray = Arrays.asList(keywordArray);

        String predicateCode2 = "contents.toLowerCase().contains(keywordItems[0].getKeyWord().toLowerCase()) || contents.toLowerCase().contains(keywordItems[1].getKeyWord().toLowerCase())";
        String description2 = convertPredicateToDescription(predicateCode2, keywordItemsFromArray);
        System.out.println("\nOriginal Predicate: " + predicateCode2);
        System.out.println("Descriptive String (from array): " + description2);

        // Example with missing keyword (will show UNKNOWN_KEYWORD_X)
        String predicateCode3 = "contents.toLowerCase().contains(keywordItems[0].getKeyWord().toLowerCase()) && contents.toLowerCase().contains(keywordItems[99].getKeyWord().toLowerCase())";
        List<KeyWordItem> keywordItems3 = List.of(
                new KeyWordItem("test")
        );
        String description3 = convertPredicateToDescription(predicateCode3, keywordItems3);
        System.out.println("\nOriginal Predicate: " + predicateCode3);
        System.out.println("Descriptive String: " + description3);

        // NEW EXAMPLE with negation (!)
        String predicateCode4 = "!contents.toLowerCase().contains(keywordItems[0].getKeyWord().toLowerCase()) && " +
                "( contents.toLowerCase().contains(keywordItems[1].getKeyWord().toLowerCase()) || " +
                "contents.toLowerCase().contains(keywordItems[2].getKeyWord().toLowerCase()) )";
        List<KeyWordItem> keywordItems4 = List.of(
                new KeyWordItem("exclude"),
                new KeyWordItem("includeA"),
                new KeyWordItem("includeB")
        );
        String description4 = convertPredicateToDescription(predicateCode4, keywordItems4);
        System.out.println("\nOriginal Predicate: " + predicateCode4);
        System.out.println("Descriptive String (with negation): " + description4);

        String predicateCode5 = "!contents.toLowerCase().contains(keywordItems[0].getKeyWord().toLowerCase()) && " +
                "( contents.toLowerCase().contains(keywordItems[1].getKeyWord().toLowerCase()) || " +
                "contents.toLowerCase().contains(keywordItems[2].getKeyWord().toLowerCase()) )";

        List<KeyWordItem> keywordItems5 = List.of(
                new KeyWordItem("apple"),
                new KeyWordItem("red"),
                new KeyWordItem("fruit")
        );
        String description5 = convertPredicateToDescription(predicateCode5, keywordItems5);
        System.out.println("\nOriginal Predicate: " + predicateCode5);
        System.out.println("Descriptive String (with negation): " + description5);
    }
}
