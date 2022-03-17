package com.example.presidentasshole.cards;

/**
 * @author Margo Brown
 * @author Claire Porter
 * @author Renn Torigoe
 * @author Max Woods
 *
 * Translations of integer card ranks to string values.
 */
public enum CardValues {
    THREE(3, "THREE"),
    FOUR(4, "FOUR"),
    FIVE(5, "FIVE"),
    SIX(6, "SIX"),
    SEVEN(7, "SEVEN"),
    EIGHT(8, "EIGHT"),
    NINE(9, "NINE"),
    TEN(10, "TEN"),
    JACK(11, "JACK"),
    QUEEN(12, "QUEEN"),
    KING(13, "KING"),
    ACE(14, "ACE"),
    TWO(15, "TWO");

    public final int value;
    public final String cardValue;
    CardValues(int value, String cardValue){
        this.value = value;
        this.cardValue = cardValue;
    }

    public static String getCardValue(int n) {
        String cardValue = null;
        switch (n) {
            case 3:
                cardValue = "THREE";
                break;
            case 4:
                cardValue = "FOUR";
                break;
            case 5:
                cardValue = "FIVE";
                break;
            case 6:
                cardValue = "SIX";
                break;
            case 7:
                cardValue = "SEVEN";
                break;
            case 8:
                cardValue = "EIGHT";
                break;
            case 9:
                cardValue = "NINE";
                break;
            case 10:
                cardValue = "TEN";
                break;
            case 11:
                cardValue = "JACK";
                break;
            case 12:
                cardValue = "QUEEN";
                break;
            case 13:
                cardValue = "KING";
                break;
            case 14:
                cardValue = "ACE";
                break;
            case 15:
                cardValue = "TWO";
                break;
        }
        return cardValue;
    }
}
