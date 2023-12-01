package com.example.repairservice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.repairservice.Araclar.Isyeri;
import com.example.repairservice.Araclar.Okuyucu;
import com.example.repairservice.Araclar.Onarim;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class RPR_Genel_Ort_Onr_masrafi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpr_genel_ort_onr_masrafi);

        ((TextView)findViewById(R.id.titleTextView)).setText("Yapılan Onarımların Ortalamasına Ait Raporlar Sayfası");


        BarChart barChart = (BarChart) findViewById(R.id.ort_OnrSayi);
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        Okuyucu okc = new Okuyucu(RPR_Genel_Ort_Onr_masrafi.this);

        ArrayList<Onarim> onarimListesi=okc.bitmisOnarimListesiver();


        double toplaMaliyet=0;
        for(int i=0;i<onarimListesi.size();i++){
            toplaMaliyet+=onarimListesi.get(i).getToplam_maliyet();

        }
        ((TextView)findViewById(R.id.GnlOrtMsrfTextView)).setText("Toplam Onarım Maliyet: " + toplaMaliyet);

        ((TextView)findViewById(R.id.GnlOrtMsrfTextView2)).setText("Toplam Onarım Sayısı: " + onarimListesi.size());

        double ortalamaMaliyet=toplaMaliyet/onarimListesi.size();

        barEntries.add(new BarEntry((float)0.0,(float)ortalamaMaliyet));

        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightColor(Color.RED);
        barDataSet.setValueTextSize(16f);
        barDataSet.setValueTextColor(Color.WHITE);

        BarData barData = new BarData(barDataSet);

        barChart.getDescription().setText("");
        barChart.getDescription().setTextSize(12);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setTextSize(16f);
        barChart.getXAxis().setTextColor(Color.WHITE);

        ArrayList<String> labels = new ArrayList<String> ();

        labels.add("Ortalam Onarım Maliyeti (TL)");


        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.animateY(1000);

        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getXAxis().setGranularity(1.0f);
        barChart.getXAxis().setLabelCount(barDataSet.getEntryCount());
        barChart.setData(barData);


    }
}