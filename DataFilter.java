package org.example;

import java.util.*;
import java.util.stream.*;

public class DataFilter {
    private static final String INTEGER_FILE = "integers.txt";
    private static final String FLOAT_FILE = "floats.txt";
    private static final String STRING_FILE = "strings.txt";

    private static boolean appendMode = false;
    private static String outputPath = "";
    private static boolean fullStats = false;

    public static void main(String[] args) {
        List<String> inputFiles = new ArrayList<>();
        parseArguments(args, inputFiles);

        List<Integer> integers = new ArrayList<>();
        List<Double> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        // Заглушка вместо обработки файлов
        System.out.println("Обработка файлов пока не реализована.");

        printStatistics(integers, floats, strings);
    }

    private static void parseArguments(String[] args, List<String> inputFiles) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-a":
                    appendMode = true;
                    break;
                case "-o":
                    if (i + 1 < args.length) outputPath = args[++i] + "/";
                    break;
                case "-f":
                    fullStats = true;
                    break;
                default:
                    inputFiles.add(args[i]);
            }
        }
    }

    private static void classifyData(String line, List<Integer> integers, List<Double> floats, List<String> strings) {
        try {
            integers.add(Integer.parseInt(line));
        } catch (NumberFormatException e1) {
            try {
                floats.add(Double.parseDouble(line));
            } catch (NumberFormatException e2) {
                strings.add(line);
            }
        }
    }

    private static void printStatistics(List<Integer> integers, List<Double> floats, List<String> strings) {
        System.out.println("Статистика:");
        printStatNum("Целые числа", integers);
        printStatNum("Вещественные числа", floats);
        printStatStr("Строки", strings);
    }

    private static <T extends Number> void printStatNum(String label, List<T> numbers) {
        System.out.println(label + ": " + numbers.size());
        if (fullStats && !numbers.isEmpty()) {
            DoubleSummaryStatistics stats = numbers.stream().mapToDouble(Number::doubleValue).summaryStatistics();
            System.out.printf("Мин: %.2f, Макс: %.2f, Сумма: %.2f, Среднее: %.2f%n", stats.getMin(), stats.getMax(), stats.getSum(), stats.getAverage());
        }
    }

    private static void printStatStr(String label, List<String> strings) {
        System.out.println(label + ": " + strings.size());
        if (fullStats && !strings.isEmpty()) {
            int minLen = strings.stream().mapToInt(String::length).min().orElse(0);
            int maxLen = strings.stream().mapToInt(String::length).max().orElse(0);
            System.out.println("Мин. длина: " + minLen + ", Макс. длина: " + maxLen);
        }
    }
}

