package org.merge;


import java.io.*;
import java.util.*;

public abstract class FileMerge {
    public boolean has_next(Scanner scanner) {
        return true;
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
        List<String> result = new ArrayList<>();
        for (int i = 1; i < Main.files.size(); i++) {
            scanners.put(i - 1, new Scanner(new BufferedReader(new FileReader(Main.files.get(i)))));
        }

        HashMap<Integer, String> symbols = new HashMap<>();
        while (true) {
            if (index == -1) {
                for (int i = 0; i < scanners.size(); i++) {
                    if (has_next(scanners.get(i))) {
                        String take = take_next(scanners.get(i));
                        if(!take.isEmpty()) {
                            symbols.put(i, take);
                        }
                    }
                }
            }  else {
                if (has_next(scanners.get(index))) {
                    String take = take_next(scanners.get(index));
                    if (!take.isEmpty()) {
                        symbols.put(index, take);
                    }else{
                        continue;
                    }
                }else {
                    flags.set(index, true);
                    scanners.remove(index);
                }
            }
            int min = 0;
            while (!symbols.containsKey(min)) {
                min++;
                if (flags.stream().filter(x -> x.equals(true)).count() >= flags.size()) {
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Main.files.get(0))))) {
                        for (String string : result) {
                            writer.write(string);
                            writer.write("\n");
                        }
                    }
                    return;
                }
            }
            for (int i = 0; i < Main.files.size(); i++) {
                if (symbols.containsKey(i)) {
                    if (Main.SYM_NUM == Args.SYMBOL) {
                        if(Main.UP_DOWN == Args.UP){
                            if (symbols.get(i).compareTo(symbols.get(min)) <= 0) {
                                min = i;
                            }
                        }else{
                            if (symbols.get(i).compareTo(symbols.get(min)) >= 0) {
                                min = i;
                            }
                        }
                    } else {
                        if(Main.UP_DOWN == Args.UP) {
                            if (Integer.parseInt(symbols.get(i)) <= Integer.parseInt(symbols.get(min))) {
                                min = i;
                            }
                        }else{
                            if (Integer.parseInt(symbols.get(i)) >= Integer.parseInt(symbols.get(min))) {
                                min = i;
                            }
                        }
                    }
                }
            }
            result.add(symbols.get(min));
            index = min;
            symbols.remove(min);
        }
    }

}
