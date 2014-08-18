package com.example.sirmc.uoweme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.SimpleTimeZone;

/**
 * Created by sirmc on 10/08/14.
 */
public class MyAdapter extends ArrayAdapter<Debt> {

    public MyAdapter(Context context, List<Debt> debts) {
        super(context, R.layout.row_layout_debts, debts);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.row_layout_debts, parent, false);

        String name = getItem(position).name;
        double amount = getItem(position).amount;

        TextView nameView = (TextView)view.findViewById(R.id.debts_row_debt_name);
        TextView amountView = (TextView) view.findViewById(R.id.debts_row_amount);

        nameView.setText(name);
        amountView.setText(String.format("%.2f", amount));

        return view;
    }


}
