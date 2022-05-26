package com.example.sn1p3rsgame.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sn1p3rsgame.CardsForDeck;
import com.example.sn1p3rsgame.R;

import com.example.sn1p3rsgame.cardStuff.BasicCard;
import com.example.sn1p3rsgame.cardStuff.CardView;
import com.example.sn1p3rsgame.game.MainCharacter;
import com.example.sn1p3rsgame.recyclerStuff.CustomRecyclerViewAdapter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;


public class BattleFieldActivity extends AppCompatActivity {

    public static final String LOG_TAG = "AndroidExample";

    private RecyclerView recyclerView;
    private Queue<BasicCard> cardsQueue;
    private TextView myCharTv, enemyCharTv;
    private MainCharacter myChar, enemyChar;
    public static final int YOU_WIN = 1;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_battle_field);
        myCharTv = findViewById(R.id.my_char_tv);
        enemyCharTv = findViewById(R.id.enemy_char_tv);
        myChar = new MainCharacter(30);
        enemyChar = new MainCharacter(30);
        myCharTv.setText(myChar.getHp() + "");
        enemyCharTv.setText(enemyChar.getHp() + "");


        cardsQueue = new LinkedList<BasicCard>();
        Bundle bundle = getIntent().getExtras();
        ArrayList<BasicCard> deckList = bundle.getParcelableArrayList("deckList");
        for (BasicCard c : deckList) {
            cardsQueue.add(c);
        }
        List<BasicCard> cards = getListData();
        this.recyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);

        recyclerView.setAdapter(new CustomRecyclerViewAdapter(this, cards));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        View.OnDragListener dragListener = new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

                int dragEvent = event.getAction();

                switch (dragEvent) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                        v.setBackgroundResource(R.drawable.customborder1);
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        v.setBackgroundResource(R.drawable.customborder);
                        break;

                    case DragEvent.ACTION_DROP:

                        CardView vw = (CardView) event.getLocalState();

                        ViewGroup owner = (ViewGroup) vw.getParent();
                        owner.removeView(vw);
                        LinearLayout container = (LinearLayout) v;
                        cardsQueue.offer(vw.getBasicCard());
                        cards.remove(vw.getListPosition());
                        container.addView(vw);
                        BasicCard n = cardsQueue.poll();
                        BasicCard n2 = new BasicCard(n.getName(),n.getFraction(),n.getAttackPoints(),
                                n.getHealthPoints(),n.getImage());
                        cards.add(n2);
                        vw.setVisibility(View.VISIBLE);

                        recyclerView.getAdapter().notifyDataSetChanged();


                        for (int i = 0; i < 4; i++) {
                            checkLine(i);
                        };

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        setEnemyCard();




                        for (int i = 0; i < 4; i++) {
                            checkLineFromEnemy(i);
                        };

                        enemyCharTv.setText(enemyChar.getHp() + "");

                        myCharTv.setText(myChar.getHp() + "");

                        if (enemyChar.isDead()){
                            Intent i = getIntent();
                            setResult(RESULT_OK,i);
                            finish();
                        } else if (myChar.isDead()){
                            Intent i = getIntent();
                            setResult(RESULT_CANCELED,i);
                            finish();
                        }

                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        v.setBackgroundResource(R.drawable.customborder);


                        return true;


                }

                return true;
            }
        };


        RelativeLayout layout = findViewById(R.id.main_layout);
        View v = null;
        for (int i = 23; i >= 0; i--) {  //чтобы клетки шли справа сверзу, а не снизу слева
            v = layout.getChildAt(i);
            if (v instanceof LinearLayout) { //  && v.getId() !== R.id. если появится новый layout, которому не нужен drag
                v.setOnDragListener(dragListener);
                TextView child = new TextView(this);
                child.setText(i+"");
                ((LinearLayout) v).addView(child);
            }
        }


    }


    private List<BasicCard> getListData() {
        ArrayList<BasicCard> deckList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            BasicCard last = (cardsQueue.poll());
            deckList.add(last);
        }


        return deckList;
    }

    private void checkLine(int line) {
        RelativeLayout layout = findViewById(R.id.main_layout);
        for (int i = 0 + line * 3; i < 3 + line * 3; i++) {
            LinearLayout user = (LinearLayout) layout.getChildAt(i);
            CardView userCardView = (CardView) user.getChildAt(0);
            if (null == userCardView) continue;
            for (int j = 0; j < 3; j++) {
                LinearLayout enemy = (LinearLayout) layout.getChildAt(12 + line * 3 + j);
                CardView enemyCardView = (CardView) enemy.getChildAt(0);

                if (enemyCardView != null && userCardView != null) {
                    enemyCardView.getBasicCard().damage(userCardView.getBasicCard().getAttackPoints());
                    break;
                } else if (userCardView != null && enemyCardView == null && j == 2) {
                    enemyChar.damage(userCardView.getBasicCard().getAttackPoints());
                    break;
                }


            }


        }

    }

    private void checkLineFromEnemy(int line) {
        RelativeLayout layout = findViewById(R.id.main_layout);
        for (int i = 14 + line * 3; i >= 12 + line * 3; i--) {
            LinearLayout enemy = (LinearLayout) layout.getChildAt(i);
            CardView enemyCardView = (CardView) enemy.getChildAt(0);
            if (null == enemyCardView) continue;
            for (int j = 2; j >= 0 ; j--) {
                LinearLayout user = (LinearLayout) layout.getChildAt( line * 3 + j);
                CardView userCardView = (CardView) user.getChildAt(0);

                if (userCardView != null && enemyCardView != null) {
                    userCardView.getBasicCard().damage(enemyCardView.getBasicCard().getAttackPoints());
                    break;
                } else if (enemyCardView != null && userCardView == null && j == 0) {
                    myChar.damage(enemyCardView.getBasicCard().getAttackPoints());
                    break;
                }


            }


        }

    }

    private void setEnemyCard() {



        List<BasicCard> enemyDeckList = new ArrayList<>();
        List<BasicCard> allCards = new ArrayList<>(CardsForDeck.returnCards());
        Collections.shuffle(allCards);
        enemyDeckList.add(allCards.get(0));
        allCards.remove(0);
        int randomLine = (int) (Math.random() * 4);
        int randomLayout = (int) (Math.random() * 3);
        RelativeLayout layout = findViewById(R.id.main_layout);
        LinearLayout enemy = (LinearLayout) layout.getChildAt(12 + randomLine * 3 + randomLayout);
        CardView cardView = new CardView(this);
        cardView.setBasicCard(enemyDeckList.get(0));
        if (enemy.getChildCount() > 0) {
            setEnemyCard();
            return;
        }
        enemy.addView(cardView);


    }
    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        int size = fragmentList.size();
        if (size > 0) {

            getSupportFragmentManager().beginTransaction().remove(fragmentList.get(size - 1))
                    .commit();

        } else {
            finish();
        }


    }


}