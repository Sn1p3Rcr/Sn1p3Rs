package com.example.sn1p3rsgame.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sn1p3rsgame.R;
import com.example.sn1p3rsgame.activities.BattleFieldActivity;
import com.example.sn1p3rsgame.activities.ChoseOfDeckActivity;

import java.util.ArrayList;


public class GameLvlFragment extends Fragment implements View.OnClickListener {
    Button easyLvl,hardLvl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_lvl, container, false);
        easyLvl = (Button) view.findViewById(R.id.easyLvl);
        hardLvl = (Button) view.findViewById(R.id.hardLvl);

        easyLvl.setOnClickListener(this);

        hardLvl.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), BattleFieldActivity.class);
        intent.putExtra("lvl",v.getId() == R.id.easyLvl ? 1 : 10);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("deckList", (ArrayList<? extends Parcelable>) ( (ChoseOfDeckActivity) getActivity()).getUserCardsList());
        intent.putExtras(bundle);
        getActivity().startActivityForResult(intent, BattleFieldActivity.YOU_WIN);
    }
}