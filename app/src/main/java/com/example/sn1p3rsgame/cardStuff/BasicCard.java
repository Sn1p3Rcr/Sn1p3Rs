package com.example.sn1p3rsgame.cardStuff;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class BasicCard implements Parcelable, Serializable, Comparable<BasicCard>{
    protected String name,fraction,imageName;
    protected int healthPoints ,attackPoints ;
    Bitmap bitmap;

    public  BasicCard(String name, String fraction, int ap , int hp,String image){
        this.name = name;
        this.fraction = fraction;
        this.healthPoints = hp;
        this.attackPoints = ap;
        this.imageName = image;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFraction() {
        return fraction;
    }



    public int getHealthPoints() {
        return healthPoints;
    }


    public int getAttackPoints() {
        return attackPoints;
    }


    public String getImage() {
        return imageName;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int arg1) {
        dest.writeInt(healthPoints);
        dest.writeInt(attackPoints);
        dest.writeString(name);
        dest.writeString(fraction);
        dest.writeString(imageName);
    }

    public BasicCard(Parcel in){
        healthPoints = in.readInt();
        attackPoints = in.readInt();
        name = in.readString();
        fraction = in.readString();
        imageName = in.readString();
    }

    public static final Parcelable.Creator<BasicCard> CREATOR = new Parcelable.Creator<BasicCard>(){

        public BasicCard createFromParcel(Parcel in){
            return new BasicCard(in);
        }
        public BasicCard[] newArray(int size){
            return new BasicCard[size];
        }
    };

    public void damage(int ap){
        healthPoints -= ap;
    }


    @NonNull
    @Override
    public String toString() {
        return "Card:" +
                "name= " + name +
                "; fraction= " + fraction +
                "; healthPoints= " + healthPoints +
                "; attackPoints=" + attackPoints;
    }



    public  static BasicCard copy(BasicCard c) {
        return new BasicCard(c.getName(), c.getFraction(), c.getAttackPoints(), c.getHealthPoints(), c.getImage());
    }

    @Override
    public int compareTo(BasicCard o) {
        int c1 = this.getAttackPoints() + this.getHealthPoints();
        int c2 = o.getAttackPoints() + o.getHealthPoints();
        return c1 - c2;
    }
}