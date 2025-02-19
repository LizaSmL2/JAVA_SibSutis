package org.example.Homework1;

import java.io.*;
import java.nio.file.*;
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

        for (String file : inputFiles) {
            processFile(file, integers, floats, strings);
        }

        writeFile(INTEGER_FILE, integers.stream().map(String::valueOf).collect(Collectors.toList()));
        writeFile(FLOAT_FILE, floats.stream().map(String::valueOf).collect(Collectors.toList()));
        writeFile(STRING_FILE, strings);

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

    private static void processFile(String fileName, List<Integer> integers, List<Double> floats, List<String> strings) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                classifyData(line, integers, floats, strings);
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + fileName);
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

    private static void writeFile(String fileName, List<String> data) {
        if (data.isEmpty()) return;
        Path path = Paths.get(outputPath + fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path, appendMode ? StandardOpenOption.APPEND : StandardOpenOption.CREATE)) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + fileName);
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

