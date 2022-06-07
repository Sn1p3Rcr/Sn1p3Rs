package com.example.sn1p3rsgame.comparator;

import com.example.sn1p3rsgame.cardStuff.BasicCard;

public class BasicCardReverseComparator implements java.util.Comparator<BasicCard> {

    @Override
    public int compare(BasicCard o1, BasicCard o2) {

        int c1 = o1.getAttackPoints() + o2.getHealthPoints();
        int c2 = o2.getAttackPoints() + o2.getHealthPoints();

        return c2 - c1;

    }


}

