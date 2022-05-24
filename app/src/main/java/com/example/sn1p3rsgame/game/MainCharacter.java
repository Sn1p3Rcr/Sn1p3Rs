package com.example.sn1p3rsgame.game;

public class MainCharacter {
    private int hp;

    public MainCharacter(int hp){
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void damage(int ap){
        hp -= ap;
    }

    public boolean isDead(){
        return hp<=0;
    }

}
