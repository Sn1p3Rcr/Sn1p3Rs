package com.example.sn1p3rsgame.recyclerStuff;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sn1p3rsgame.cardStuff.CardView;
import com.example.sn1p3rsgame.R;

public class CardViewHolder extends RecyclerView.ViewHolder {
    CardView cardView;
    ImageView cardImageView;
    TextView nameView,fractionView, healthPointsView,attackPointsView;
    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = (CardView) itemView;

        this.cardImageView = (ImageView) itemView.findViewById(R.id.cardImage);
        this.nameView = (TextView) itemView.findViewById(R.id.name);
        this.fractionView = (TextView) itemView.findViewById(R.id.fraction);
        this.attackPointsView = (TextView) itemView.findViewById(R.id.attackPoints);
        this.healthPointsView = (TextView) itemView.findViewById(R.id.healthPoints);
    }


    public CardView getCardView() {
        return cardView;
    }
}
