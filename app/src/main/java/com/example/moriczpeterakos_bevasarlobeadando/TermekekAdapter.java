package com.example.moriczpeterakos_bevasarlobeadando;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TermekekAdapter extends BaseAdapter {

    private List<Termekek> termekekList;
    private Context context;

    public TermekekAdapter(List<Termekek> termekekList, Context context) {
        this.termekekList = termekekList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return termekekList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.termeklist, viewGroup, false);
        TextView nameTextWiew = view.findViewById(R.id.nameTextView);
        TextView countTextView = view.findViewById(R.id.countTextView);
        TextView priceTextView = view.findViewById(R.id.priceTextView);

        TextView bruttonPriceTextView = view.findViewById(R.id.buttonPriceTextView);
        Termekek termekek = termekekList.get(i);
        nameTextWiew.setText(termekek.getName());
        priceTextView.setText(String.valueOf(termekek.getPrice()));
        countTextView.setText(String.format("%s %s", termekek.getCount(), termekek.getMeasure()));
        bruttonPriceTextView.setText(String.format("%s Ft", termekek.getBruttoPrice()));


        return view;
    }
}