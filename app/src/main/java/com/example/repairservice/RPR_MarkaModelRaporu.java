package com.example.repairservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.repairservice.Araclar.Marka;
import com.example.repairservice.Araclar.Okuyucu;
import com.example.repairservice.Araclar.Yazici;
import com.example.repairservice.Tanimlamalar.Degerİkilisi;
import com.example.repairservice.Tanimlamalar.Fonksiyonlar;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class RPR_MarkaModelRaporu extends AppCompatActivity {

    Okuyucu okc;
    Yazici yzc;
    Fonksiyonlar fnk;
    LinearLayout anaLayout;
    LinearLayout.LayoutParams olayouParametreleri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpr_marka_model_raporu);

        ((TextView)findViewById(R.id.titleTextView)).setText("Marka Modele Göre Raporlar Sayfası");


        yzc= new Yazici(RPR_MarkaModelRaporu.this);
        okc=new Okuyucu(RPR_MarkaModelRaporu.this);
        fnk=new Fonksiyonlar(RPR_MarkaModelRaporu.this);

        Button marka_raporbuton = findViewById(R.id.marka_raporbuton);
        marka_raporbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RPR_MarkaModelRaporu.this, RPR_MarkaRaporu.class);
                startActivity(intent);
            }
        });
        anaLayout=(LinearLayout) findViewById(R.id.markaListeLayout);

        // layout'ların ozelliklerini buradaki değişkende topladık
        olayouParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams buttonParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,5);

        ArrayList<Marka> markaListesi=okc.markaListesiver();
        anaLayout.removeAllViews();
        for(int i=0;i<markaListesi.size();i++){
            Marka curmarka=markaListesi.get(i);

            Button curButton=new Button(RPR_MarkaModelRaporu.this);
            curButton.setLayoutParams(buttonParametreleri);
            curButton.setText(markaListesi.get(i).getMarka_adi());

            curButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RPR_MarkaModelRaporu.this, RPR_MarkaModelOnarimSayiRaporu.class);
                    intent.putExtra("marka_id", curmarka.getId());
                    startActivity(intent);
                }
            });

            anaLayout.addView(curButton);


        }


    }


}
