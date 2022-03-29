package presidentasshole.cards;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import presidentasshole.PresidentGame;
import R;

import java.util.Arrays;

/**
 * @author Max Woods
 *
 * Visual representation of a card object on the screen.
 */
public class CardImage extends androidx.appcompat.widget.AppCompatImageButton implements View.OnClickListener {

    private Card card_model;
    private PresidentGame game;

    public CardImage(@NonNull Context context, Card card_model,
                     PresidentGame game, int id,
                     boolean collapse) {
        super(context);
        /**
         * External Citation
         *  Date: 16 March 2022
         *  Problem: Couldn't figure out how to adjust the attributes
         *  of a custom view
         *
         * Resource:
         *      https://stackoverflow.com/questions/12728255/
         *      in-android-how-do-i-set-margins-in-dp-programmatically
         *
         * Solution: I used the LayoutParams class in the post
         */
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        // TODO might wanna replace this with display pixels
        this.card_model = card_model;
        this.game = game;
        this.setId(id);

        int margins = 5;

        // This && check is to prevent the card from going out of bounds
        // TODO fix this
        if (collapse && id > 1) {
            margins = -45;
        }

        // TODO Fix bug where the first two cards are on top of each other
        // The ID is used for aligning cards to be next to each other in the RelativeLayout
        if ((id - 1) >= 0) {
            params.addRule(RelativeLayout.RIGHT_OF, (id - 1));
            Log.i("Debug","" + (id - 1));
        } else {
            params.addRule(RelativeLayout.RIGHT_OF, R.id.PlayerCardScrollViewLayout);
        }
        params.setMargins(margins,5,margins,5);

        this.setAdjustViewBounds(true);
        this.setLayoutParams(params);
        this.setImageResource(this.card_model.toResourceID());
        this.setWillNotDraw(false);
        this.setOnClickListener(this);

        if (this.card_model.isSelected()) {
            this.setAlpha(0.5f);
        }
    }

    public void delete() {
        LinearLayout parent = (LinearLayout) this.getParent();
        parent.removeView(this);
        parent.invalidate();
    }

    public void setImage(int res) {
        this.setImageResource(res);
        this.invalidate();
    }

    @Override
    public void onClick(View view) {
        Log.i("CardClick",card_model.toString());
        if (!this.card_model.isSelected()) {
            this.game.getGameState().getPlayerFromTurn().selectCard(this.card_model);
        } else {
            this.game.getGameState().getPlayerFromTurn().deselectCard(this.card_model);
        }
    }
}
