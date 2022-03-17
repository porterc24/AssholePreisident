package com.example.presidentasshole.cards;

/**
 * @author Margo Brown
 * @author Claire Porter
 * @author Renn Torigoe
 * @author Max Woods
 *
 * Translation of integer card suites to string card values.
 */
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

        String suiteName = "NULL";

        switch (n) {
            case 1:
                suiteName = "DIAMONDS";
                break;
            case 2:
                suiteName = "CLUBS";
                break;
            case 3:
                suiteName = "HEARTS";
                break;
            case 4:
                suiteName = "SPADES";
                break;
        }
        return suiteName;
    }
}
