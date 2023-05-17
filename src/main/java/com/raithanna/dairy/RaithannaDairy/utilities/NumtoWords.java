package com.raithanna.dairy.RaithannaDairy.utilities;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NumtoWords {
    public String convert(String a) {

        String word = "";
        int b = a.length();
        String str[] = {"1", "One", "2", "Two", "3", "Three", "4", "Four", "5", "Five", "6", "Six", "7", "Seven", "8",
                "Eight", "9", "Nine", "10", "Ten", "11", "Eleven", "12", "Twelve", "13", "Thirteen", "14", "Forteen",
                "15", "Fifteen", "16", "Sixteen", "17", "Seventeen", "18", "Eighteen", "19", "Nineteen", "20", "Twenty",
                "30", "Thirty", "40", "Fourty", "50", "Fifty", "60", "Sixty", "70", "Seventy", "80", "Eighty", "90",
                "Ninty", "100", "Hundred"};
        System.out.println("");
        if (b == 9) {
            String s1 = a.substring(0, 1);
            String s2 = a.substring(1, 2);
            String s3 = a.substring(2, 3);
            String s4 = a.substring(3, 4);
            String s5 = a.substring(4, 5);
            String s6 = a.substring(5, 6);
            String s7 = a.substring(6, 7);
            String s8 = a.substring(7, 8);
            String s9 = a.substring(8, 9);
            String s10 = a.substring(0, 2);
            String s11 = a.substring(2, 4);
            String s12 = a.substring(4, 6);
            String s14 = a.substring(7, 9);
            {
                if (s10.equals("00"))
                    word = word + ("");
                else if (s1.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s10))
                            word = word + "\n" + str[r + 1] + " Crore ";
                } else {
                    {
                        for (int i = 0; i <= 40; i++)
                            if (str[i].equals(s1))
                                word = word + "\n" + str[i + 37] + " ";
                    }
                    {
                        if (s2.equals("0")) {
                            word = word + ("Crore ");
                        } else
                            for (int j = 0; j <= 40; j++) {
                                if (str[j].equals(s2))
                                    word = word + str[j + 1] + " Crore ";
                            }
                    }
                }
            }
            {
                if (s11.equals("00"))
                    word = word + ("");
                else if (s3.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s11))
                            word = word + (str[r + 1] + " Lacks ");
                } else {
                    {
                        for (int k = 0; k <= 38; k++)
                            if (str[k].equals(s3))
                                word = word + (str[k + 37] + " ");
                    }
                    {
                        if (s4.equals("0")) {
                            word = word + ("Lacks ");
                        } else
                            for (int l = 0; l <= 38; l++) {
                                if (str[l].equals(s4))
                                    word = word + (str[l + 1] + " Lacks ");
                            }
                    }
                }
            }
            {
                if (s12.equals("00"))
                    word = word + ("");
                else if (s5.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s12))
                            word = word + (str[r + 1] + " Thousand ");
                } else {
                    {
                        for (int m = 0; m <= 38; m++)
                            if (str[m].equals(s5))
                                word = word + (str[m + 37] + " ");
                    }
                    {
                        if (s6.equals("0")) {
                            word = word + ("Thousand ");
                        } else
                            for (int n = 0; n <= 38; n++) {
                                if (str[n].equals(s6))
                                    word = word + (str[n + 1] + " Thousand ");
                            }
                    }
                }
            }
            {
                for (int o = 0; o <= 40; o++)
                    if (str[o].equals(s7))
                        word = word + (str[o + 1] + " Hundred ");
            }
            {
                if (s14.equals("00"))
                    word = word + ("");
                else if (s8.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s14))
                            word = word + (str[r + 1]);
                    word = word + ("\n");
                } else {
                    for (int p = 0; p <= 40; p++)
                        if (str[p].equals(s8))
                            word = word + (str[p + 37]);
                    for (int q = 0; q <= 40; q++) {
                        if (str[q].equals(s9))
                            word = word + (" " + str[q + 1]);
                    }
                }
                word = word + ("\n");
            }
        } else if (b == 8) {
            String s1 = a.substring(0, 1);
            String s2 = a.substring(1, 2);
            String s3 = a.substring(2, 3);
            String s4 = a.substring(3, 4);
            String s5 = a.substring(4, 5);
            String s6 = a.substring(5, 6);
            String s7 = a.substring(6, 7);
            String s8 = a.substring(7, 8);
            String s10 = a.substring(1, 3);
            String s11 = a.substring(3, 5);
            String s12 = a.substring(6, 8);
            {
                if (s1.equals("0"))
                    word = word + ("");
                else
                    for (int i = 0; i <= 40; i++)
                        if (str[i].equals(s1))
                            word = word + ("\n" + str[i + 1] + " Crore ");
            }
            {
                if (s10.equals("00"))
                    word = word + ("");
                else if (s2.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s10))
                            word = word + (str[r + 1] + " Lacks ");
                } else {
                    {
                        for (int k = 0; k <= 38; k++)
                            if (str[k].equals(s2))
                                word = word + (str[k + 37] + " ");
                    }
                    {
                        if (s3.equals("0")) {
                            word = word + ("Lacks ");
                        } else
                            for (int l = 0; l <= 38; l++) {
                                if (str[l].equals(s3))
                                    word = word + (str[l + 1] + " Lacks ");
                            }
                    }
                }
            }
            {
                if (s11.equals("00"))
                    word = word + ("");
                else if (s4.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s11))
                            word = word + (str[r + 1] + " Thousand ");
                } else {
                    {
                        for (int m = 0; m <= 38; m++)
                            if (str[m].equals(s4))
                                word = word + (str[m + 37] + " ");
                    }
                    {
                        if (s5.equals("0")) {
                            word = word + ("Thousand ");
                        } else
                            for (int n = 0; n <= 38; n++) {
                                if (str[n].equals(s5))
                                    word = word + (str[n + 1] + " Thousand ");
                            }
                    }
                }
            }
            {
                for (int o = 0; o <= 40; o++)
                    if (str[o].equals(s6))
                        word = word + (str[o + 1] + " Hundred ");
            }
            {
                if (s12.equals("00"))
                    word = word + ("");
                else if (s7.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s12))
                            word = word + (str[r + 1]);
                    word = word + ("\n");
                } else {
                    for (int p = 0; p <= 40; p++)
                        if (str[p].equals(s7))
                            word = word + (str[p + 37]);
                    for (int q = 0; q <= 40; q++) {
                        if (str[q].equals(s8))
                            word = word + (" " + str[q + 1]);
                    }
                }
                word = word + ("\n");
            }
        } else if (b == 7) {
            String s1 = a.substring(0, 1);
            String s2 = a.substring(1, 2);
            String s3 = a.substring(2, 3);
            String s4 = a.substring(3, 4);
            String s5 = a.substring(4, 5);
            String s6 = a.substring(5, 6);
            String s7 = a.substring(6, 7);
            String s10 = a.substring(0, 2);
            String s11 = a.substring(2, 4);
            String s12 = a.substring(5, 7);
            {
                if (s10.equals("00"))
                    word = word + ("");
                else if (s1.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s10))
                            word = word + (str[r + 1] + " Lacks ");
                } else {
                    {
                        for (int k = 0; k <= 38; k++)
                            if (str[k].equals(s1))
                                word = word + (str[k + 37] + " ");
                    }
                    {
                        if (s2.equals("0")) {
                            word = word + ("Lacks ");
                        } else
                            for (int l = 0; l <= 38; l++) {
                                if (str[l].equals(s2))
                                    word = word + (str[l + 1] + " Lacks ");
                            }
                    }
                }
            }
            {
                if (s11.equals("00"))
                    word = word + ("");
                else if (s3.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s11))
                            word = word + (str[r + 1] + " Thousand ");
                } else {
                    {
                        for (int m = 0; m <= 38; m++)
                            if (str[m].equals(s3))
                                word = word + (str[m + 37] + " ");
                    }
                    {
                        if (s4.equals("0")) {
                            word = word + ("Thousand ");
                        } else
                            for (int n = 0; n <= 38; n++) {
                                if (str[n].equals(s4))
                                    word = word + (str[n + 1] + " Thousand ");
                            }
                    }
                }
            }
            {
                for (int o = 0; o <= 40; o++)
                    if (str[o].equals(s5))
                        word = word + (str[o + 1] + " Hundred ");
            }
            {
                if (s12.equals("00"))
                    word = word + ("");
                else if (s6.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s12))
                            word = word + (str[r + 1]);
                    word = word + ("\n");
                } else {
                    for (int p = 0; p <= 40; p++)
                        if (str[p].equals(s6))
                            word = word + (str[p + 37]);
                    for (int q = 0; q <= 40; q++) {
                        if (str[q].equals(s7))
                            word = word + (" " + str[q + 1]);
                    }
                }
                word = word + ("\n");
            }
        } else if (b == 6) {
            String s1 = a.substring(0, 1);
            String s2 = a.substring(1, 2);
            String s3 = a.substring(2, 3);
            String s4 = a.substring(3, 4);
            String s5 = a.substring(4, 5);
            String s6 = a.substring(5, 6);
            String s10 = a.substring(1, 3);
            String s11 = a.substring(4, 6);

            {
                if (s1.equals("0"))
                    word = word + ("");
                else {
                    for (int j = 0; j <= 40; j++)
                        if (str[j].equals(s1))
                            word = word + (str[j + 1] + " Lacks ");
                }
            }
            {
                if (s10.equals("00"))
                    word = word + ("");
                else if (s2.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s10))
                            word = word + (str[r + 1] + " Thousand ");
                } else {
                    {
                        for (int m = 0; m <= 40; m++)
                            if (str[m].equals(s2))
                                word = word + (str[m + 37] + " ");
                    }
                    {
                        if (s3.equals("0")) {
                            word = word + ("Thousand ");
                        } else
                            for (int n = 0; n <= 38; n++) {
                                if (str[n].equals(s3))
                                    word = word + (str[n + 1] + " Thousand ");
                            }
                    }
                }
            }
            {
                if (s4.equals("0"))
                    word = word + ("");
                else {
                    for (int o = 0; o <= 40; o++)
                        if (str[o].equals(s4))
                            word = word + (str[o + 1] + " Hundred ");
                }
            }
            {
                if (s11.equals("00"))
                    word = word + ("");
                else if (s5.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s11))
                            word = word + (str[r + 1]);
                    word = word + ("\n");
                } else {
                    for (int p = 0; p <= 40; p++)
                        if (str[p].equals(s5))
                            word = word + (str[p + 37]);
                    for (int q = 0; q <= 40; q++) {
                        if (str[q].equals(s6))
                            word = word + (" " + str[q + 1]);
                    }
                }
                word = word + ("\n");
            }
        } else if (b == 5) {
            String s1 = a.substring(0, 1);
            String s2 = a.substring(1, 2);
            String s3 = a.substring(2, 3);
            String s4 = a.substring(3, 4);
            String s5 = a.substring(4, 5);
            String s10 = a.substring(0, 2);
            String s11 = a.substring(3, 5);
            {
                if (s10.equals("00"))
                    word = word + ("");
                else if (s1.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s10))
                            word = word + (str[r + 1] + " Thousand ");
                } else {
                    {
                        for (int m = 0; m <= 38; m++)
                            if (str[m].equals(s1))
                                word = word + (str[m + 37] + " ");
                    }
                    {
                        if (s2.equals("0")) {
                            word = word + ("Thousand ");
                        } else
                            for (int n = 0; n <= 38; n++) {
                                if (str[n].equals(s2))
                                    word = word + (str[n + 1] + " Thousand ");
                            }
                    }
                }
            }
            {
                for (int o = 0; o <= 40; o++)
                    if (str[o].equals(s3))
                        word = word + (str[o + 1] + " Hundred ");
            }
            {
                if (s11.equals("00"))
                    word = word + ("");
                else if (s4.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s11))
                            word = word + (str[r + 1]);
                    word = word + ("\n");
                } else {
                    for (int p = 0; p <= 40; p++)
                        if (str[p].equals(s4))
                            word = word + (str[p + 37]);
                    for (int q = 0; q <= 40; q++) {
                        if (str[q].equals(s5))
                            word = word + (" " + str[q + 1]);
                    }
                }
                word = word + ("\n");
            }
        } else if (b == 4) {
            String s1 = a.substring(0, 1);
            String s2 = a.substring(1, 2);
            String s3 = a.substring(2, 3);
            String s4 = a.substring(3, 4);
            String s10 = a.substring(2, 4);
            {
                if (s1.equals("0"))
                    word = word + ("");
                else {
                    for (int j = 0; j <= 40; j++)
                        if (str[j].equals(s1))
                            word = word + (str[j + 1] + " Thousand ");
                }
            }
            {
                if (s2.equals("0"))
                    word = word + ("");
                else {
                    for (int o = 0; o <= 40; o++)
                        if (str[o].equals(s2))
                            word = word + (str[o + 1] + " Hundred ");
                }
            }
            {
                if (s10.equals("00"))
                    word = word + ("");
                else if (s3.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s10))
                            word = word + (str[r + 1]);
                    word = word + ("\n");
                } else {
                    for (int p = 0; p <= 40; p++)
                        if (str[p].equals(s3))
                            word = word + (str[p + 37]);
                    for (int q = 0; q <= 40; q++) {
                        if (str[q].equals(s4))
                            word = word + (" " + str[q + 1]);
                    }
                }
                word = word + ("\n");
            }
        } else if (b == 3) {
            String s1 = a.substring(0, 1);
            String s2 = a.substring(1, 2);
            String s3 = a.substring(2, 3);
            String s10 = a.substring(1, 3);
            {
                if (s1.equals("0"))
                    word = word + ("");
                else {
                    for (int o = 0; o <= 40; o++)
                        if (str[o].equals(s1))
                            word = word + (str[o + 1] + " Hundred ");
                }
            }
            {
                if (s10.equals("00"))
                    word = word + ("");
                else if (s2.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s10))
                            word = word + (str[r + 1]);
                    word = word + ("\n");
                } else {
                    for (int p = 0; p <= 40; p++)
                        if (str[p].equals(s2))
                            word = word + (str[p + 37]);
                    for (int q = 0; q <= 40; q++) {
                        if (str[q].equals(s3))
                            word = word + (" " + str[q + 1]);
                    }
                }
                word = word + ("\n");
            }
        } else if (b == 2) {
            String s1 = a.substring(0, 1);
            String s2 = a.substring(1, 2);
            String s10 = a.substring(0, 2);
            {
                if (s10.equals("00"))
                    word = word + ("");
                else if (s1.equals("1")) {
                    for (int r = 0; r <= 40; r++)
                        if (str[r].equals(s10))
                            word = word + (str[r + 1]);
                    word = word + ("\n");
                } else {
                    for (int p = 0; p <= 40; p++)
                        if (str[p].equals(s1))
                            word = word + (str[p + 37]);
                    for (int q = 0; q <= 40; q++) {
                        if (str[q].equals(s2))
                            word = word + (" " + str[q + 1]);
                    }
                }
                word = word + ("\n");
            }
        } else if (b == 1) {
            String s1 = a.substring(0, 1);
            for (int q = 0; q <= 40; q++)
                if (str[q].equals(s1))
                    word = word + (" " + str[q + 1]);
        }
        System.out.println(word);
        return word;
    }
}