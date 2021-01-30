package projects.medium.readability;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public class ReadabilityIndex {
    
    public static final Map<Long, String> difficulties = Map.ofEntries( // maps index to range of ages
        Map.entry(1L, "5-6"),
        Map.entry(2L, "6-7"),
        Map.entry(3L, "7-9"),
        Map.entry(4L, "9-10"),
        Map.entry(5L, "10-11"),
        Map.entry(6L, "11-12"),
        Map.entry(7L, "12-13"),
        Map.entry(8L, "13-14"),
        Map.entry(9L, "14-15"),
        Map.entry(10L, "15-16"),
        Map.entry(11L, "16-17"),
        Map.entry(12L, "17-18"),
        Map.entry(13L, "18-24"),
        Map.entry(14L, "24+")
    );
    
    public static int[] getAgeRange(double indexValue) {
        
        return Arrays.stream(
            ReadabilityIndex.difficulties.get(Math.round(indexValue))
                .replaceAll("\\+", "").split("-")
        ).mapToInt(Integer::parseInt).toArray();
    }
    
    public static final Function<TextAnalytics, Double> automatedReadability = a ->
        4.71 * ((double) a.charactersCount / a.wordsCount)
            + 0.5 * ((double) a.wordsCount / a.sentencesCount) - 21.43;
    
    public static final Function<TextAnalytics, Double> fleschKincaid = a ->
        0.39 * ((float) a.wordsCount / a.sentencesCount)
            + 11.8 * ((double) a.syllablesCount / a.wordsCount) - 15.59;
    
    public static final Function<TextAnalytics, Double> simpleMeasureOfGobbledygook = a ->
        1.043 * Math.sqrt((double) a.polysyllableCount * 30 / a.sentencesCount) + 3.1291;
    
    public static final Function<TextAnalytics, Double> colemanLiau = a ->
        0.0588 * a.charsPerHundredWords
            - 0.296 * a.sentencesPerHundredWords - 15.8;
    
}
