package com.example.sn1p3rsgame.cardStuff;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.sn1p3rsgame.R;
import com.example.sn1p3rsgame.cardStuff.BasicCard;

public class CardView extends RelativeLayout {
    private BasicCard basicCard;
    private int listPosition;


    public CardView(Context context) {
        this(context, null);
    }

    public CardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(getContext(), R.layout.cardview, this);

    }

    public void setBasicCard(BasicCard newBasicCard) {
        basicCard = newBasicCard;
    }

    public BasicCard getBasicCard() {
        return basicCard;
    }

    public int getListPosition() {
        return listPosition;
    }

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }


}
