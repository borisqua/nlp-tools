package nlptools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class App {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public String getGreeting() {
        return "Hello ny world!";
    }
    
    public static void main(String[] args) throws IOException {
        
        if (args.length == 0) {
            System.out.println("Please, provide file name as a command line argument");
            return;
        }

        Path fileName = Path.of(args[0]);
        String text = new String(Files.readAllBytes(fileName));
        
        TextAnalytics textAnalytics = new TextAnalytics(text);
        System.out.println(textAnalytics.toString());
        
        String selection;
        while (true) {
            
            System.out.println();
            System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all) or 'exit' to stop application: \n");
            selection = scanner.next().toUpperCase();
            System.out.println();
            
            double avgAge = 0;
            double age = 0;
            boolean inputError = false;
            double indexValue;
            int[] ageRange;
            switch (selection) {
                case "ALL":
                case "ARI":
                    indexValue = ReadabilityIndex.automatedReadability.apply(textAnalytics);
                    ageRange = ReadabilityIndex.getAgeRange(indexValue);
                    age = ageRange[ageRange.length - 1];
                    avgAge += age;
                    System.out.printf("Automated Readability Index: %.2f (about %.1f year olds in average).\n",
                        indexValue, age);
                    if (!"ALL".equals(selection)) {
                        break;
                    }
                case "FK":
                    indexValue = ReadabilityIndex.fleschKincaid.apply(textAnalytics);
                    ageRange = ReadabilityIndex.getAgeRange(indexValue);
                    age = ageRange[ageRange.length - 1];
                    avgAge += age;
                    System.out.printf("Flesch" + (char) 8211 + "Kincaid readability tests: %.2f (about %.1f year olds).\n",
                        indexValue, age);
                    if (!"ALL".equals(selection)) {
                        break;
                    }
                case "SMOG":
                    indexValue = ReadabilityIndex.simpleMeasureOfGobbledygook.apply(textAnalytics);
                    ageRange = ReadabilityIndex.getAgeRange(indexValue);
                    age = ageRange[ageRange.length - 1];
                    avgAge += age;
                    System.out.printf("Simple Measure of Gobbledygook: %.2f (about %.1f year olds).\n",
                        indexValue, age);
                    if (!"ALL".equals(selection)) {
                        break;
                    }
                case "CL":
                    indexValue = ReadabilityIndex.colemanLiau.apply(textAnalytics);
                    ageRange = ReadabilityIndex.getAgeRange(indexValue);
                    age = ageRange[ageRange.length - 1];
                    avgAge += age;
                    System.out.printf("Coleman–Liau index: %.2f (about %.1f year olds).\n",
                        indexValue, age);
                    if (!"ALL".equals(selection)) {
                        break;
                    }
                    avgAge /= 4;
                    break;
                case "EXIT":
                    break;
                default:
                    inputError = true;
            }
            
            if ("EXIT".equals(selection)) {
                System.out.println("Bye-bye.\n");
                break;
            }
            if (!inputError) {
                System.out.printf("\nThis text should be understood in average by %.1f year olds.\n", avgAge);
            } else {
                System.out.println("Input error. Maybe some mistyping. Try again.");
            }
        }
        
    }
    
}

