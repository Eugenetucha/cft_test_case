package org.merge;


import java.io.*;
import java.util.*;

public abstract class FileMerge {
    public boolean has_next(Scanner scanner) {
        return true;
    }

    public int compare_to(String one, String two) {
        return 1;
    }

    public String take_next(Scanner scanner) {
        return null;
    }

    public void merge() throws IOException {
        int index = -1;
        List<Boolean> flags = new ArrayList<>();
        for (int i = 1; i < Main.files.size(); i++) {
            flags.add(false);
        }
        HashMap<Integer, Scanner> scanners = new HashMap<>();
        for (int i = 1; i < Main.files.size(); i++) {
            scanners.put(i - 1, new Scanner(new BufferedReader(new FileReader(Main.files.get(i)))));
        }
        HashMap<Integer, LinkedList<String>> check = new HashMap<>();
        HashMap<Integer, String> symbols = new HashMap<>();
        for (int i = 0; i < scanners.size(); i++) {
            check.put(i, new LinkedList<>());
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Main.files.get(0))))) {
            while (true) {
                if (index == -1) {
                    for (int i = 0; i < scanners.size(); i++) {
                        if (has_next(scanners.get(i))) {
                            String take = take_next(scanners.get(i));
                            if (!take.isEmpty()) {
                                if (check.get(i).size() > 0) {
                                    switch (Main.UP_DOWN) {
                                        case UP -> {
                                            if (compare_to(check.get(i).peekLast(), take) > 0) {
                                                check_valid(symbols, check, i, scanners.get(i));
                                            } else {
                                                check.get(i).add(take);
                                                symbols.put(i, take);
                                            }
                                        }
                                        case DOWN -> {
                                            if (compare_to(check.get(i).peekLast(), take) < 0) {
                                                check_valid(symbols, check, i, scanners.get(i));
                                            } else {
                                                check.get(i).add(take);
                                                symbols.put(i, take);
                                            }
                                        }
                                    }
                                } else {
                                    check.get(i).add(take);
                                    symbols.put(i, take);
                                }
                            }
                        }
                    }
                } else {
                    if (has_next(scanners.get(index))) {
                        String take = take_next(scanners.get(index));
                        if (!take.isEmpty()) {
                            switch (Main.UP_DOWN) {
                                case UP -> {
                                    if (compare_to(check.get(index).peekLast(), take) > 0) {
                                        check_valid(symbols, check, index, scanners.get(index));
                                    } else {
                                        check.get(index).add(take);
                                        symbols.put(index, take);
                                    }
                                }
                                case DOWN -> {
                                    if (compare_to(check.get(index).peekLast(), take) < 0) {
                                        check_valid(symbols, check, index, scanners.get(index));
                                    } else {
                                        check.get(index).add(take);
                                        symbols.put(index, take);
                                    }
                                }
                            }
                        } else {
                            continue;
                        }
                    } else {
                        flags.set(index, true);
                        scanners.remove(index);
                    }
                }
                int min = 0;
                while (!symbols.containsKey(min)) {
                    min++;
                    if (flags.stream().filter(x -> x.equals(true)).count() >= flags.size() || symbols.isEmpty()) {
                        return;
                    }
                }
                for (int i = 0; i < Main.files.size(); i++) {
                    if (symbols.containsKey(i)) {
                        if (Main.UP_DOWN == Args.UP) {
                            if (compare_to(symbols.get(i), symbols.get(min)) <= 0) {
                                min = i;
                            }
                        } else {
                            if (compare_to(symbols.get(i), symbols.get(min)) >= 0) {
                                min = i;
                            }
                        }
                    }
                }
                writer.write(symbols.get(min));
                writer.write("\n");
                index = min;
                symbols.remove(min);
            }
        }
    }

    public void check_valid(HashMap<Integer, String> symbols, HashMap<Integer, LinkedList<String>> list, int count, Scanner scanner) {
        String remove = has_next(scanner) ? take_next(scanner) : "";
        while (has_next(scanner) || remove.equals("")) {
            if (Objects.equals(remove, "")) {
                break;
            }
            if (Main.UP_DOWN == Args.UP && compare_to(list.get(count).peekLast(), (remove)) <= 0) {
                list.get(count).add(remove);
                symbols.put(count, remove);
                break;
            } else if (Main.UP_DOWN == Args.DOWN && compare_to(list.get(count).peekLast(), (remove)) >= 0) {
                list.get(count).add(remove);
                symbols.put(count, remove);
                break;
            } else {
                if (has_next(scanner)) {
                    remove = take_next(scanner);
                } else {
                    break;
                }
            }
        }

    }
}
