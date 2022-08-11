package org.merge;

import java.util.Scanner;

public class IntegerMerge extends FileMerge {
    @Override
    public boolean has_next(Scanner scanner) {
        return scanner.hasNextInt();
    }

    @Override
    public String take_next(Scanner scanner) {
        return scanner.nextLine().replaceAll("\\s+", "");
    }
    @Override
    public int compare_to(String one,String two) {
        return Integer.parseInt(one)-Integer.parseInt(two);
    }
}
