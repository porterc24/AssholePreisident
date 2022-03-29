package presidentasshole.cards;

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

    /**
     * This is pretty much only used by the toResourceID() method in the Card class.
     * The converted names need to be a bit different in order to match the card PNG filenames.
     */
    public static String getCardValuePNG(int n) {
        String cardValue = null;
        switch (n) {
            case 3:
                cardValue = "3";
                break;
            case 4:
                cardValue = "4";
                break;
            case 5:
                cardValue = "5";
                break;
            case 6:
                cardValue = "6";
                break;
            case 7:
                cardValue = "7";
                break;
            case 8:
                cardValue = "8";
                break;
            case 9:
                cardValue = "9";
                break;
            case 10:
                cardValue = "10";
                break;
            case 11:
                cardValue = "jack";
                break;
            case 12:
                cardValue = "queen";
                break;
            case 13:
                cardValue = "king";
                break;
            case 14:
                cardValue = "ace";
                break;
            case 15:
                cardValue = "2";
                break;
        }
        return cardValue;
    }
}
