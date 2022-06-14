package com.example.sn1p3rsgame.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sn1p3rsgame.R;
import com.example.sn1p3rsgame.cardStuff.BasicCard;
import com.example.sn1p3rsgame.recyclerStuff.CustomRecyclerViewAdapter;


public class CardInformationFragment extends Fragment {
    TextView tv_name,tv_hp,tv_ap,tv_fraction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_information, container,
                false);

        BasicCard basicCard = ((BasicCard) getArguments().getSerializable
                (CustomRecyclerViewAdapter.KEY_TO_FRAGMENT));
        tv_name = view.findViewById(R.id.tv_name);
        tv_ap = view.findViewById(R.id.tv_ap);
        tv_hp = view.findViewById(R.id.tv_hp);
        tv_fraction = view.findViewById(R.id.tv_fraction);


        tv_name.setText(basicCard.getName());
        tv_fraction.setText(basicCard.getFraction());
        tv_hp.setText(basicCard.getHealthPoints() + "");
        tv_ap.setText(basicCard.getAttackPoints() + "");

        return view;
    }
}