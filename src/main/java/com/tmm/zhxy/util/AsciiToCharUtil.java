package com.tmm.zhxy.util;

import java.util.Scanner;

public class AsciiToCharUtil {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ascii = scanner.nextLine();
        String[] split = ascii.split(",");
        for (String s : split) {
            int i = Integer.parseInt(s);
            char a = (char) i;
            System.out.print(a);
        }
    }
}
