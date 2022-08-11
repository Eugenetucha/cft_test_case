package org.merge;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    static Args UP_DOWN = Args.UP;
    static Args SYM_NUM;

    static List<String> files;

    public static void main(String[] args) throws Exception {
        if (Arrays.stream(args).filter(x -> x.contains(".txt")).count() < 2) {
            throw new Exception("Неправильные входные аргументы");
        }
        files = Arrays.stream(args).filter(x -> x.contains(".txt")).collect(Collectors.toList());
        for (String arg : args) {
            switch (arg) {
                case "-a" -> UP_DOWN = Args.UP;
                case "-d" -> UP_DOWN = Args.DOWN;
                case "-s" -> SYM_NUM = Args.SYMBOL;
                case "-i" -> SYM_NUM = Args.NUMBER;
            }
        }
        if (SYM_NUM == Args.NUMBER) {
            IntegerMerge integerMerge = new IntegerMerge();
            integerMerge.merge();
        } else {
            StringMerge stringMerge = new StringMerge();
            stringMerge.merge();
        }
    }
}