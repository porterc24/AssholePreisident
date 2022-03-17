package com.example.presidentasshole.cards;

import com.example.presidentasshole.R;

import java.lang.reflect.Field;

/**
 * @author Margo Brown
 * @author Claire Porter
 * @author Renn Torigoe
 * @author Max Woods
 *
 * Pretty simply class. Represents card data (rank and suite).
 */
public class Card {
    private final int rank;
    private final int suite;

    public Card(int rank, int suite) {
        this.rank = rank;
        this.suite = suite;
    }

    // Copy ctor for Card class
    public Card(Card orig) {
        this.rank = orig.rank;
        this.suite = orig.suite;;
    }

    /**
     * Outputs the integer resource ID of the PNG file associated with this card.
     *
     * Card should always be valid!! (1 <= suite <= 4, 3 <= value <= 15)
     * Otherwise terrible things might happen...
     * @return res ID
     */
    public int toResourceID() {
        String suite_name = CardSuites.getSuiteName(suite).toLowerCase();
        String rank_name = CardValues.getCardValuePNG(this.rank);

        return getResId("c_" + rank_name + "_of_" + suite_name);
    }

    private int getResId(String resName) {

        try {
            Field idField = R.drawable.class.getField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getRank() {
        return rank;
    }

    public int getSuite() { return suite; }

    public boolean cardEquals(Card that) {
        return that.getRank() == this.rank && that.getSuite() == this.suite;
    }

}
