package com.example.sn1p3rsgame.cardStuff;

import static com.example.sn1p3rsgame.recyclerStuff.CustomRecyclerViewAdapter.KEY_TO_FRAGMENT;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.sn1p3rsgame.R;
import com.example.sn1p3rsgame.activities.BattleFieldActivity;
import com.example.sn1p3rsgame.activities.ChoseOfDeckActivity;
import com.example.sn1p3rsgame.cardStuff.BasicCard;
import com.example.sn1p3rsgame.fragment.CardInformationFragment;

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

    public static final OnClickListener FRAGMENT_LISTENER = new OnClickListener() {
        @Override
        public void onClick(View view) {
            BasicCard card = ((CardView) view).getBasicCard();
            CardInformationFragment cardInformationFragment = new CardInformationFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_TO_FRAGMENT, card);
            cardInformationFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = ((AppCompatActivity) view.getContext())
                    .getSupportFragmentManager().beginTransaction();
            if (view.getContext() instanceof ChoseOfDeckActivity) {
                fragmentTransaction.add(R.id.fl_chose, cardInformationFragment)
                        .commit();
            } else if (view.getContext() instanceof BattleFieldActivity) {
                fragmentTransaction.add(R.id.main_layout, cardInformationFragment)
                        .commit();
            }
        }
    };


}
