package com.example.sn1p3rsgame;

import com.example.sn1p3rsgame.cardStuff.BasicCard;

import java.util.ArrayList;
import java.util.List;

public class CardsForDeck {
    private List<BasicCard> cards = new ArrayList<>();
    BasicCard legioner = new BasicCard("Legioner", "light", 1, 2, "card");
    BasicCard evil = new BasicCard("Evil", "dark", 3, 4, "card");
    BasicCard evil1 = new BasicCard("Netrual", "no", 5, 6, "card");
    BasicCard evil2 = new BasicCard("Evil", "dark", 7, 8, "card");
    BasicCard evil3 = new BasicCard("Evil", "dark", 9, 10, "card");
    BasicCard evil4 = new BasicCard("Evil", "dark", 13, 14, "card");
    BasicCard evil5 = new BasicCard("Evil", "dark", 15, 16, "card");
    BasicCard evil6 = new BasicCard("Evil", "dark", 11, 12, "card");
    BasicCard evil7 = new BasicCard("Evil", "dark", 6, 10, "card");


    public List<BasicCard> returnCards() {
        cards.add(legioner);
        cards.add(evil);
        cards.add(evil1);
        cards.add(evil2);
        cards.add(evil3);
        cards.add(evil4);
        cards.add(evil5);
        cards.add(evil6);
        cards.add(evil7);

        return cards;
    }
}
