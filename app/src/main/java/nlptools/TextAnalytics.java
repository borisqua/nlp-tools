package nlptools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextAnalytics {
    
    final String text;
    
    final long sentencesCount;
    final long wordsCount;
    final long charactersCount;
    final long lettersCount;
    final long syllablesCount;
    final long polysyllableCount;
    final long charsPerHundredWords;
    final long sentencesPerHundredWords;
    final char[] chars;
    final char[] letters;
    final String[] sentences;
    final String[] words;
    
    TextAnalytics(String text) {
        this.text = text;
        text = text.toLowerCase();
        sentences = text.split("[.?!]\\s+");
        words = text.split("[ .?!]\\s*");
        sentencesCount = sentences.length;
        chars = text.replaceAll("\\s", "").toCharArray();
        letters = text.replaceAll("[!?,.:;\\s]", "").toCharArray();
        charactersCount = chars.length;
        lettersCount = letters.length;
        wordsCount = words.length;
        syllablesCount = getAllSyllablesInATextCount();
        polysyllableCount = getPolysyllableWordsCount();
        charsPerHundredWords = 100 * charactersCount / wordsCount;
        sentencesPerHundredWords = 100 * sentencesCount / wordsCount;
    }
    
    @SuppressWarnings("unused")
    private boolean isVowel(char c) {
        return "aeiouy".contains(Character.toString(c));
    }
    
    private long getSyllablesInAWordCount(char[] word) {
        
        long counter = 0;
        //  yet another syllables pattern (?!e\\b)[aeiouy]{1,}|\\b[0-9qwrtplkjhgfdszxcvbnm]+\\b|\\bthe\\b
        // yet another syllables pattern ([aeiouyAEIOUY]+[^e.\s])|([aiouyAEIOUY]+\b)|(\b[^aeiouy0-9.']+e\b)
        Pattern syllables = Pattern.compile("([aeiouyAEIOUY]+[^e.\\s])|([aiouyAEIOUY]+\\b)|(\\b[^aeiouy0-9.']+e\\b)\n");
        Matcher matcher = syllables.matcher(new String(word));
        while (matcher.find()) {
            counter++;
        }
        
        return counter == 0 ? 1 : counter;
    }
    
    private long getAllSyllablesInATextCount() {
        long counter = 0;
        for (String word : words) {
            counter += getSyllablesInAWordCount(word.toCharArray());
        }
        return counter;
    }
    
    private long getPolysyllableWordsCount() {
        long counter = 0;
        for (String word : words) {
            if (getSyllablesInAWordCount(word.toCharArray()) > 2) {
                counter++;
            }
        }
        return counter;
    }
    
    @Override
    public String toString() {
        return "The text is:\n" + text.trim() +
            "\n\nWords: " + wordsCount +
            "\nSentences: " + sentencesCount +
            "\nCharacters: " + charactersCount +
            (syllablesCount > 0 ? "\nSyllables: " + syllablesCount : "") +
            (polysyllableCount > 0 ? "\nPolysyllables: " + polysyllableCount : "");
    }
}
