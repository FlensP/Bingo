package fr.flens.bingo.utils;

import java.util.ArrayList;

public class InventoryUtils {

    public static ArrayList<Integer> slot(int nbr) {
        int l, c;
        c = nbr % 9;
        l = (nbr - c) / 9;
        ArrayList<Integer> cl = new ArrayList<>();
        cl.add(l);
        cl.add(c);
        return cl;
    }
}
