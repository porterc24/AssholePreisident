package com.example.myapplication;

public enum CardSuites {
    DIAMONDS(1),
    CLUBS(2),
    HEARTS(3),
    SPADES(4);

    public final int suite;
    CardSuites(int suite) {
        this.suite = suite;
    }

    /**
     * Returns the assosciated name of a suite with its number.
     * @param n the number of the suite
     * @return the name of the suite
     */
    public static String getSuiteName(int n) {
        switch (n) {
            case 1:
                return "DIAMONDS";
            case 2:
                return "CLUBS";
            case 3:
                return "HEARTS";
            case 4:
                return "SPADES";
        }
        return null;
    }
}
