package com.example.sn1p3rsgame.activities;


import static com.example.sn1p3rsgame.activities.ChoseOfDeckActivity.PLAYER_MAX_CARDS;

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


public class BattleFieldActivity extends AppCompatActivity {

    public static final String LOG_TAG = "AndroidExample";

    private RecyclerView recyclerView;
    private Queue<BasicCard> userCardQueue;
    private List<BasicCard> enemyCardsList = new ArrayList<>();
    private TextView myCharTv, enemyCharTv;
    private MainCharacter myChar, enemyChar;
    public static final int YOU_WIN = 1;
    private int lvl;







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

        lvl = getIntent().getIntExtra("lvl", 10);
        List<BasicCard> allEnemyCards = CardsForDeck.cardsForGame();

        if (lvl < 2) {
            for (int i = 0; i < PLAYER_MAX_CARDS; i++) {
                enemyCardsList.add(allEnemyCards.remove(0));
            }
        } else {
            int diff = (int) (3 + Math.random() * 3);
            for (int i = 0; i < PLAYER_MAX_CARDS; i++) {
                if (i <= diff){
                    enemyCardsList.add(allEnemyCards.remove(allEnemyCards.size()-1));
                }
                else{
                    Collections.shuffle(allEnemyCards);
                    enemyCardsList.add(allEnemyCards.remove(0));
                }
            }
        }

        userCardQueue = new LinkedList<BasicCard>();
        Bundle bundle = getIntent().getExtras();
        ArrayList<BasicCard> deckList = bundle.getParcelableArrayList("deckList");
        for (BasicCard c : deckList) {
            userCardQueue.add(c);
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
                        CardView vw2 = new CardView(vw.getContext());
                        vw2.setBasicCard(BasicCard.copy(vw.getBasicCard()));




                        ViewGroup owner = (ViewGroup) vw.getParent();

                        owner.removeView(vw);

                        LinearLayout container = (LinearLayout) v;

                        userCardQueue.offer(vw.getBasicCard());

                        cards.remove(vw.getListPosition());

                        container.addView(vw2);

                        BasicCard n = userCardQueue.poll();
                        BasicCard n2 = BasicCard.copy(n);

                        cards.add(n2);



                        recyclerView.getAdapter().notifyDataSetChanged();

                        for (int i = 0; i < 4; i++) {
                            removeUserCards(i);
                        }



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

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < 4; i++) {
                            removeUserCards(i);
                            removeEnemyCards(i);
                        }

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
        for (int i = 0; i < 12; i++) {
            v = layout.getChildAt(i);
            if (v instanceof LinearLayout) { //  && v.getId() !== R.id. если появится новый layout, которому не нужен drag
                v.setOnDragListener(dragListener);
//                TextView child = new TextView(this);
//                child.setText(i+"");
//                ((LinearLayout) v).addView(child);
            }
        }



    }



    private List<BasicCard> getListData() {
        ArrayList<BasicCard> deckList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            BasicCard last = (userCardQueue.poll());
            deckList.add(last);
        }


        return deckList;
    }
    private void removeUserCards(int line) {
        RelativeLayout layout = findViewById(R.id.main_layout);
        for (int i = 0 + line * 3; i < 3 + line * 3; i++) {
            LinearLayout user = (LinearLayout) layout.getChildAt(i);
            CardView userCardView = (CardView) user.getChildAt(0);
            if (null == userCardView) continue;
            if (userCardView.getBasicCard().getHealthPoints() <= 0) {
                user.removeView(userCardView);

            }
        }
    }

    private void removeEnemyCards(int line) {
        RelativeLayout layout = findViewById(R.id.main_layout);
        for (int i = 12 + line * 3; i < 15 + line * 3; i++) {
            LinearLayout user = (LinearLayout) layout.getChildAt(i);
            CardView enemyCardView = (CardView) user.getChildAt(0);
            if (null == enemyCardView) continue;
            if (enemyCardView.getBasicCard().getHealthPoints() <= 0) {
                user.removeView(enemyCardView);

            }
        }
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
        for (int i = 12 + line * 3; i < 15 + line * 3; i++) {
            LinearLayout enemy = (LinearLayout) layout.getChildAt(i);
            CardView enemyCardView = (CardView) enemy.getChildAt(0);
            if (null == enemyCardView) continue;
            for (int j = 0; j < 3 ; j++) {
                LinearLayout user = (LinearLayout) layout.getChildAt( line * 3 + j);
                CardView userCardView = (CardView) user.getChildAt(0);

                if (userCardView != null && enemyCardView != null) {
                    userCardView.getBasicCard().damage(enemyCardView.getBasicCard().getAttackPoints());
                    break;
                } else if (enemyCardView != null && userCardView == null && j == 2) {
                    myChar.damage(enemyCardView.getBasicCard().getAttackPoints());
                    break;
                }


            }


        }

    }

    private void setEnemyCard() {

        List<BasicCard> enemyDeckList = new ArrayList<>();

        Collections.shuffle(enemyCardsList);
        enemyDeckList.add(BasicCard.copy(enemyCardsList.get(0)));
//        enemyDeckList.remove(0);
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