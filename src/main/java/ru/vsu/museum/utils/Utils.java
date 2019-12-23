package ru.vsu.museum.utils;

import ru.vsu.museum.domain.Exponent;

import java.util.List;

public class Utils {
    public static boolean contains(List<Exponent> exponentList, Exponent exponentItem)
    {
        for (Exponent exponent: exponentList) {
            if (exponent.getExponentId().equals(exponentItem.getExponentId()))
                return true;
        }
        return false;
    }
}
