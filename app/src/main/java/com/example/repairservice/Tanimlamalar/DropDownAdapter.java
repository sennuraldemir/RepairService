package com.example.repairservice.Tanimlamalar;


import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;


import com.example.repairservice.R;

import java.util.ArrayList;

public class DropDownAdapter extends BaseAdapter {
    private Context context;
    ArrayList<Degerİkilisi> degerListesi;

    public DropDownAdapter(Context context, ArrayList<Degerİkilisi> degerListesi) {
        this.context = context;
        this.degerListesi = degerListesi;
    }

    @Override
    public int getCount() {
        if(degerListesi!=null){
            return degerListesi.size();
        }
        else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return degerListesi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return degerListesi.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView= LayoutInflater.from(context).inflate(R.layout.dropdownlayout,viewGroup,false);
        TextView textView=rootView.findViewById(R.id.drtextview);

        //ImageView img=rootView.findViewById(R.id.drimgview2);

        textView.setText(degerListesi.get(i).getText());

        return rootView;


    }
}