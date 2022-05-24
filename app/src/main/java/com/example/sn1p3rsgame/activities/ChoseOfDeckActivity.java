package com.example.sn1p3rsgame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.sn1p3rsgame.CardsForDeck;
import com.example.sn1p3rsgame.R;
import com.example.sn1p3rsgame.cardStuff.BasicCard;
import com.example.sn1p3rsgame.cardStuff.CardView;
import com.example.sn1p3rsgame.recyclerStuff.CustomRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChoseOfDeckActivity extends AppCompatActivity {
    public static final String LOG_TAG = "AndroidExample";
    protected RecyclerView allCardsRv, userCardsRv;


    protected CustomRecyclerViewAdapter allCardsAdapter, userCardsAdapter;
    List<BasicCard> userCardsList, allCardsList;
    Button battleButton;
    public static final int USER_MAX_CARDS = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chose_of_deck);

        battleButton = findViewById(R.id.battleButton);


        List<BasicCard> cards = getListDataFromDeck();
        this.allCardsRv = (RecyclerView) this.findViewById(R.id.recyclerViewFromDeck);
        allCardsAdapter = new CustomRecyclerViewAdapter(this, cards);
        allCardsRv.setAdapter(allCardsAdapter);
        // RecyclerView scroll vertical
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

                        if (userCardsList.size() < USER_MAX_CARDS) {
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
                if (userCardsList.size() < USER_MAX_CARDS) {
                    Toast.makeText(getApplicationContext(), "Выберите 8 карт", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ChoseOfDeckActivity.this, BattleFieldActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("deckList", (ArrayList<? extends Parcelable>) userCardsList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }


    private List<BasicCard> getListDataFromDeck() {
        CardsForDeck cards = new CardsForDeck();
        allCardsList = new ArrayList<BasicCard>();
        allCardsList = cards.returnCards();

//        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.sprites);
//        int w = b.getWidth()/4;
//        int h = b.getHeight()/4;
//
//        evil.setBitmap(Bitmap.createBitmap(b, 0,0, w,h));
//        evil1.setBitmap(Bitmap.createBitmap(b, 0,0, w,h));
//        evil2.setBitmap(Bitmap.createBitmap(b, 0,0, w,h));
//        evil3.setBitmap(Bitmap.createBitmap(b, 0,0, w,h));
//        legioner.setBitmap(Bitmap.createBitmap(b, 0,0, w,h));
//        evil4.setBitmap(Bitmap.createBitmap(b, 0,0, w,h));
//        evil5.setBitmap(Bitmap.createBitmap(b, 0,0, w,h));
//        evil6.setBitmap(Bitmap.createBitmap(b, 0,0, w,h));
//        evil7.setBitmap(Bitmap.createBitmap(b, 0,0, w,h));

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