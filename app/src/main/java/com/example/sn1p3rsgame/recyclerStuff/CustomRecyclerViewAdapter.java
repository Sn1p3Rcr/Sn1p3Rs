package com.example.sn1p3rsgame.recyclerStuff;

import static com.example.sn1p3rsgame.activities.ChoseOfDeckActivity.USER_MAX_CARDS;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sn1p3rsgame.R;
import com.example.sn1p3rsgame.activities.BattleFieldActivity;
import com.example.sn1p3rsgame.activities.ChoseOfDeckActivity;
import com.example.sn1p3rsgame.cardStuff.BasicCard;
import com.example.sn1p3rsgame.cardStuff.CardView;
import com.example.sn1p3rsgame.fragment.CardInformationFragment;

import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CardViewHolder> {
    public static final String KEY_TO_FRAGMENT = "basicCard";
    public static final String KEY_TO_FRAGMENT1 = "basicCard1";
    private List<BasicCard> cards;
    private final Context context;
    private LayoutInflater myLayOutInflater;


    public CustomRecyclerViewAdapter(Context context, List<BasicCard> cards) {
        this.cards = cards;
        this.context = context;
        this.myLayOutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View recyclerViewItem = new CardView(parent.getContext());

        View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(view);
                view.startDrag(data, myShadow, view, 0);
                return true;
            }
        };
        recyclerViewItem.setOnLongClickListener(longClickListener);

        return new CardViewHolder(recyclerViewItem);
    }

    public void onBindViewHolder(CardViewHolder holder, int position) {

        BasicCard card = this.cards.get(position);

        int imageResId = this.getDrawableResIdByName(card.getImage());

        holder.cardImageView.setImageResource(imageResId);
        holder.nameView.setText(card.getName());
        holder.nameView.setTextColor(Color.BLACK);
        holder.fractionView.setText(card.getFraction());
        holder.fractionView.setTextColor(Color.BLACK);
        holder.attackPointsView.setText(card.getAttackPoints() + "");
        holder.attackPointsView.setTextColor(Color.BLACK);
        holder.healthPointsView.setText(card.getHealthPoints() + "");
        holder.healthPointsView.setTextColor(Color.BLACK);
        holder.getCardView().setBasicCard(cards.get(position));
        holder.getCardView().setListPosition(position);
        CardView cardView = holder.getCardView();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (context instanceof ChoseOfDeckActivity) {
                    CardInformationFragment cardInformationFragment = new CardInformationFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_TO_FRAGMENT, card);
                    cardInformationFragment.setArguments(bundle);
                    ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                            .add(R.id.fl_chose, cardInformationFragment).commit();
                } else if (context instanceof BattleFieldActivity){
                    CardInformationFragment cardInformationFragment = new CardInformationFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_TO_FRAGMENT, card);
                    cardInformationFragment.setArguments(bundle);
                    ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                            .add(R.id.main_layout, cardInformationFragment).commit();
                }
            }
        });

        cardView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent = event.getAction();

                switch (dragEvent) {

                    case DragEvent.ACTION_DROP:
                        CardView vw = (CardView) event.getLocalState();
                        if (cards.size() == USER_MAX_CARDS && context instanceof ChoseOfDeckActivity) {
                            ChoseOfDeckActivity chose = (ChoseOfDeckActivity) context;
                            int userPosition = cardView.getListPosition();
                            int fromPosition = vw.getListPosition();
                            List<BasicCard> userCards = chose.getUserCardsList();
                            List<BasicCard> allCards = chose.getAllCardsList();
                            userCards.set(userPosition, vw.getBasicCard());
                            allCards.set(fromPosition, cardView.getBasicCard());
                            chose.getAllCardsAdapter().notifyDataSetChanged();
                            chose.getUserCardsAdapter().notifyDataSetChanged();

                        }
                }
                return true;
            }
        });


    }

    public int getItemCount() {
        return this.cards.size();
    }

    public int getDrawableResIdByName(String resName) {
        String pkgName = context.getPackageName();

        int resID = context.getResources().getIdentifier(resName, "drawable", pkgName);
        Log.i(BattleFieldActivity.LOG_TAG, "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }


}