package com.example.sn1p3rsgame;

import com.example.sn1p3rsgame.cardStuff.BasicCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardsForDeck {
    private static List<BasicCard> cards = new ArrayList<>();

      static BasicCard hero1 = new BasicCard("Legioner", "light", 2, 5, "card");
     static BasicCard evil1 = new BasicCard("Demon", "dark", 4, 2, "card");
      static BasicCard neutral1 = new BasicCard("Beast", "neutral", 3, 3, "card");
     static BasicCard hero2 = new BasicCard("Archer", "light", 5, 1, "card");
      static BasicCard evil2 = new BasicCard("Succubus", "dark", 3, 7, "card");
     static BasicCard neutral2 = new BasicCard("Wolf", "neutral", 7, 4, "card");
      static BasicCard hero3 = new BasicCard("Peasant", "light", 1, 3, "card");
      static BasicCard evil3 = new BasicCard("Sinner", "dark", 6, 3, "card");
      static BasicCard neutral3 = new BasicCard("Bird", "neutral", 4, 5, "card");
      static BasicCard hero4 = new BasicCard("Knight", "light", 6, 5, "card");
      static BasicCard evil4 = new BasicCard("Cerberus", "dark", 8, 3, "card");
    static BasicCard neutral4 = new BasicCard("Tree", "neutral", 2, 12, "card");
    static BasicCard hero5 = new BasicCard("King", "hero", 10, 4, "card");
    static BasicCard evil5 = new BasicCard("Dark", "dark",0 , 20, "card");
    static BasicCard neutral5 = new BasicCard("Phoenix", "neutral", 5, 5, "card");


    public  static  List<BasicCard> cardsForGame() {
        cards = new ArrayList<>();
        cards.add(hero1);
        cards.add(evil1);
        cards.add(neutral1);
        cards.add(hero2);
        cards.add(evil2);
        cards.add(neutral2);
        cards.add(hero3);
        cards.add(evil3);
        cards.add(neutral3);
        cards.add(hero4);
        cards.add(evil4);
        cards.add(neutral4);
        cards.add(hero5);
        cards.add(evil5);
        cards.add(neutral5);

        Collections.sort(cards);
        return cards;
    }



}
