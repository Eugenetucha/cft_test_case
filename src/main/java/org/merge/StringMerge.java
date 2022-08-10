package org.merge;

import java.util.Scanner;

public class StringMerge extends FileMerge {
    @Override
    public boolean has_next(Scanner scanner) {
        return scanner.hasNextLine();
    }

    @Override
    public String take_next(Scanner scanner) {
        return scanner.nextLine().replaceAll("\\s+", "");
    }
}
