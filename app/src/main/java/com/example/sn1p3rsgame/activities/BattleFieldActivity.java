package com.example.sn1p3rsgame.activities;


import static com.example.sn1p3rsgame.activities.ChoseOfDeckActivity.PLAYER_MAX_CARDS;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sn1p3rsgame.CardsForDeck;
import com.example.sn1p3rsgame.MyLinearLayout;
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
    private int lvl, line;
    private Context context;
    private List<BasicCard> cards;
    private View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();
            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundResource(R.drawable.customborder1);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundResource(R.drawable.customborder);
                    break;
                case DragEvent.ACTION_DROP:
                    CardView draggedCard = (CardView) event.getLocalState();
                    CardView newCardView = new CardView(draggedCard.getContext());
                    newCardView.setBasicCard(BasicCard.copy(draggedCard.getBasicCard()));
                    newCardView.setOnClickListener(CardView.FRAGMENT_LISTENER);
//                    ViewGroup owner = (ViewGroup) draggedCard.getParent();
//                    owner.removeView(draggedCard);
                    MyLinearLayout container = (MyLinearLayout) v;
                    container.addView(newCardView);
                    line = container.line;
                    userCardQueue.offer(draggedCard.getBasicCard());
                    cards.remove(draggedCard.getListPosition());
                    cards.add(BasicCard.copy(userCardQueue.poll()));
                    recyclerView.getAdapter().notifyDataSetChanged();
                    for (int i = 0; i < 4; i++) {
                        attackByLine(i);
                    }
                    sleep();
                    for (int i = 0; i < 4; i++) {
                        removeEnemyDiedCards(i);
                    }
                    setEnemyCard(line);
                    for (int i = 0; i < 4; i++) {
                        enemyAttackByLine(i);
                    }
                    sleep();
                    for (int i = 0; i < 4; i++) {
                        removeUserDiedCards(i);
                    }
                    enemyCharTv.setText(enemyChar.getHp() + "");
                    myCharTv.setText(myChar.getHp() + "");
                    if (enemyChar.isDead()) {
                        Intent i = getIntent();
                        setResult(RESULT_OK, i);
                        finish();
                    } else if (myChar.isDead()) {
                        Intent i = getIntent();
                        setResult(RESULT_CANCELED, i);
                        finish();
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundResource(R.drawable.customborder);
                    break;
            }
            return true;
        }
    };

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
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
            int diff = (int) (4 + Math.random() * 2);
            for (int i = 0; i < PLAYER_MAX_CARDS; i++) {
                if (i <= diff) {
                    enemyCardsList.add(allEnemyCards.remove(allEnemyCards.size() - 1));
                } else {
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
        cards = getListData();
        recyclerView = this.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new CustomRecyclerViewAdapter(this, cards));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RelativeLayout layout = findViewById(R.id.main_layout);
        View v = null;
        for (int i = 0; i < 12; i++) {
            v = layout.getChildAt(i);
            if (v instanceof MyLinearLayout) {
                v.setOnDragListener(dragListener);
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

    private void removeUserDiedCards(int line) {
        RelativeLayout layout = findViewById(R.id.main_layout);
        for (int i = 0 + line * 3; i < 3 + line * 3; i++) {
            LinearLayout user = (MyLinearLayout) layout.getChildAt(i);
            CardView userCardView = (CardView) user.getChildAt(0);
            if (null == userCardView) continue;
            if (userCardView.getBasicCard().getHealthPoints() <= 0) {
                user.removeView(userCardView);
            }
        }
    }

    private void removeEnemyDiedCards(int line) {
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

    private void attackByLine(int line) {
        RelativeLayout layout = findViewById(R.id.main_layout);
        for (int i = 0 + line * 3; i < 3 + line * 3; i++) {
            LinearLayout user = (MyLinearLayout) layout.getChildAt(i);
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

    private void enemyAttackByLine(int line) {
        RelativeLayout layout = findViewById(R.id.main_layout);
        for (int i = 12 + line * 3; i < 15 + line * 3; i++) {
            LinearLayout enemy = (LinearLayout) layout.getChildAt(i);
            CardView enemyCardView = (CardView) enemy.getChildAt(0);
            if (null == enemyCardView) continue;
            for (int j = 0; j < 3; j++) {
                LinearLayout user = (MyLinearLayout) layout.getChildAt(line * 3 + j);
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

    private void setEnemyCard(int line) {
        RelativeLayout layout = findViewById(R.id.main_layout);
        List<BasicCard> enemyDeckList = new ArrayList<>();

        if (lvl < 2) {
            Collections.shuffle(enemyCardsList);
            enemyDeckList.add(BasicCard.copy(enemyCardsList.get(0)));
            int randomLine = (int) (Math.random() * 4);
            int randomLayout = (int) (Math.random() * 3);

            LinearLayout enemy = (LinearLayout) layout.getChildAt(12 + randomLine * 3 + randomLayout);
            CardView cardView = new CardView(this);
            cardView.setOnClickListener(CardView.FRAGMENT_LISTENER);
            cardView.setBasicCard(enemyDeckList.get(0));
            if (enemy.getChildCount() > 0) {
                setEnemyCard(line);
                return;
            }
            enemy.addView(cardView);

        } else {
            Collections.shuffle(enemyCardsList);
            enemyDeckList.add(BasicCard.copy(enemyCardsList.get(0)));
            int randomLayoutNumber = (int) (Math.random() * 3);

            LinearLayout enemy = (LinearLayout) layout.getChildAt(12 + line * 3 + randomLayoutNumber);
            CardView cardView = new CardView(this);
            cardView.setBasicCard(enemyDeckList.remove(0));
            cardView.setOnClickListener(CardView.FRAGMENT_LISTENER);


            int count = childCounter(line);
            if (enemy.getChildCount() > 0 && count < 3) {
                setEnemyCard(line);
                return;
            }
            if (count == 3) {
                for (int i = 0; i < 4; i++) {
                    if (childCounter(i) == 3) continue;
                    enemy = (LinearLayout) layout.getChildAt(12 + i * 3 + randomLayoutNumber);
                    if (enemy.getChildCount() > 0) {
                        setEnemyCard(i);
                        break;
                    }
                }
            }
            enemy.addView(cardView);

        }


    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        int size = fragmentList.size();
        if (size > 0) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragmentList.get(size - 1))
                    .commit();
        } else {
            finish();
        }


    }

    public int childCounter(int line) {
        RelativeLayout layout = findViewById(R.id.main_layout);
        int childCounter = 0;
        for (int i = 0; i < 3; i++) {
            LinearLayout enemyCheck = (LinearLayout) layout.getChildAt(12 + line * 3 + i);
            if (enemyCheck.getChildCount() > 0) {
                childCounter++;
            }
        }
        return childCounter;
    }


}