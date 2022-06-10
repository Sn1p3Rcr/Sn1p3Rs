package com.example.sn1p3rsgame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


import com.example.sn1p3rsgame.CardsForDeck;
import com.example.sn1p3rsgame.R;
import com.example.sn1p3rsgame.cardStuff.BasicCard;
import com.example.sn1p3rsgame.cardStuff.CardView;
import com.example.sn1p3rsgame.fragment.DefeatFragment;
import com.example.sn1p3rsgame.fragment.GameLvlFragment;
import com.example.sn1p3rsgame.fragment.GameOverFragment;
import com.example.sn1p3rsgame.recyclerStuff.CustomRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChoseOfDeckActivity extends AppCompatActivity {
    public static final String LOG_TAG = "AndroidExample";
    protected RecyclerView allCardsRv, userCardsRv;
    public final static String TO_WIN_FRAGMENT = "win";

    protected CustomRecyclerViewAdapter allCardsAdapter, userCardsAdapter;
    List<BasicCard> userCardsList, allCardsList  = new ArrayList<>();
    Button battleButton;
    public static final int PLAYER_MAX_CARDS = 4;
    CardsForDeck cardsForDeck = new CardsForDeck();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chose_of_deck);

        battleButton = findViewById(R.id.battleButton);
        this.allCardsRv = (RecyclerView) this.findViewById(R.id.recyclerViewFromDeck);
        allCardsAdapter = new CustomRecyclerViewAdapter(this, getListDataFromDeck());
        allCardsRv.setAdapter(allCardsAdapter);
        LinearLayoutManager linearLayoutManagerFromDeck = new LinearLayoutManager
                (this, LinearLayoutManager.HORIZONTAL, false);
        allCardsRv.setLayoutManager(linearLayoutManagerFromDeck);


        this.userCardsRv = this.findViewById(R.id.recyclerViewToDeck);
        List<BasicCard> cardsToDeck = getListDataToDeck();
        userCardsAdapter = new CustomRecyclerViewAdapter(this, cardsToDeck);
        userCardsRv.setAdapter(userCardsAdapter);

        LinearLayoutManager linearLayoutManagerToDeck = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        userCardsRv.setLayoutManager(linearLayoutManagerToDeck);

        View.OnDragListener dragListener = new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent = event.getAction();

                switch (dragEvent) {

                    case DragEvent.ACTION_DRAG_ENTERED:
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        break;

                    case DragEvent.ACTION_DROP:
                        CardView vw = (CardView) event.getLocalState();
                        ViewGroup owner = (ViewGroup) vw.getParent();
                        owner.removeView(vw);
                        RecyclerView container = (RecyclerView) v;
                        container.addView(vw);

                        if (userCardsList.size() < PLAYER_MAX_CARDS) {
                            userCardsList.add(vw.getBasicCard());
                            allCardsList.remove(vw.getBasicCard());
                            allCardsRv.getAdapter().notifyDataSetChanged();
                            userCardsRv.getAdapter().notifyDataSetChanged();
                        }

                        return true;
                }
                return true;
            }
        };

        userCardsRv.setOnDragListener(dragListener);

        battleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userCardsList.size() < PLAYER_MAX_CARDS) {
                    Toast.makeText(getApplicationContext(), "Выберите 8 карт", Toast.LENGTH_SHORT).show();
                } else {


                    Fragment fragment = new GameLvlFragment();
                    FragmentManager fm =getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.add(R.id.fl_chose,fragment);
                    ft.commit();

                }
            }
        });


    }


    private List<BasicCard> getListDataFromDeck() {

        allCardsList = cardsForDeck.cardsForGame();
        return allCardsList;
    }

    private List<BasicCard> getListDataToDeck() {

        userCardsList = new ArrayList<BasicCard>();
        return userCardsList;
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


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BattleFieldActivity.YOU_WIN && resultCode == RESULT_OK) {
            Fragment fragment = new GameOverFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fl_chose, fragment);
            ft.commit();
        } else if (requestCode == BattleFieldActivity.YOU_WIN && resultCode == RESULT_CANCELED) {
            Fragment fragment = new DefeatFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fl_chose, fragment);
            ft.commit();
        }
    }


    public List<BasicCard> getUserCardsList() {
        return userCardsList;
    }

    public List<BasicCard> getAllCardsList() {
        return allCardsList;
    }

    public CustomRecyclerViewAdapter getAllCardsAdapter() {
        return allCardsAdapter;
    }

    public void setAllCardsAdapter(CustomRecyclerViewAdapter allCardsAdapter) {
        this.allCardsAdapter = allCardsAdapter;
    }

    public CustomRecyclerViewAdapter getUserCardsAdapter() {
        return userCardsAdapter;
    }

    public void setUserCardsAdapter(CustomRecyclerViewAdapter userCardsAdapter) {
        this.userCardsAdapter = userCardsAdapter;
    }
}