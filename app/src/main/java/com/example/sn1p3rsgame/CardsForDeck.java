package com.example.sn1p3rsgame;

import com.example.sn1p3rsgame.cardStuff.BasicCard;

import java.util.ArrayList;
import java.util.List;

public class CardsForDeck {
    private static List<BasicCard> cards = new ArrayList<>();

      BasicCard hero1 = new BasicCard("Legioner", "light", 2, 5, "card");
     BasicCard evil1 = new BasicCard("Demon", "dark", 4, 2, "card");
      BasicCard neutral1 = new BasicCard("Beast", "neutral", 3, 3, "card");
     BasicCard hero2 = new BasicCard("Archer", "light", 5, 1, "card");
      BasicCard evil2 = new BasicCard("Succubus", "dark", 3, 7, "card");
     BasicCard neutral2 = new BasicCard("Wolf", "neutral", 7, 4, "card");
      BasicCard hero3 = new BasicCard("Peasant", "light", 1, 3, "card");
      BasicCard evil3 = new BasicCard("Sinner", "dark", 6, 3, "card");
      BasicCard neutral3 = new BasicCard("Bird", "neutral", 4, 5, "card");


    public  List<BasicCard> returnCards() {
        cards.add(hero1);
        cards.add(evil1);
        cards.add(neutral1);
        cards.add(hero2);
        cards.add(evil2);
        cards.add(neutral2);
        cards.add(hero3);
        cards.add(evil3);
        cards.add(neutral3);

        return cards;
    }



}
