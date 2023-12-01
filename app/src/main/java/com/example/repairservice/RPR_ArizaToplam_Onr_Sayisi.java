package com.example.repairservice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.repairservice.Araclar.DinamikTipler;
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

public class RPR_ArizaToplam_Onr_Sayisi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpr_ariza_toplam_onr_sayisi);

        ((TextView)findViewById(R.id.titleTextView)).setText("Arıza Toplam Onarım Sayfası");


        BarChart barChart = (BarChart) findViewById(R.id.top_msrfSayi);
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        Okuyucu okc = new Okuyucu(RPR_ArizaToplam_Onr_Sayisi.this);

        ArrayList<DinamikTipler> arizaListesi=okc.arizaListesiVer();


        int toplamOnarim=0;
        int onarimSayisi=0;
        for(int i=0;i<arizaListesi.size();i++){

            onarimSayisi=okc.arizatipiIdyegoreOnarimListesiVer(arizaListesi.get(i).getId()).size();
            barEntries.add(new BarEntry(i,onarimSayisi ));
            toplamOnarim+=onarimSayisi;

        }


        ((TextView)findViewById(R.id.arztopOnrSureTextView)).setText("Toplam Onarım sayısı: " + toplamOnarim);



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
        for(int i=0;i<arizaListesi.size();i++){
            labels.add(arizaListesi.get(i).getAdi());
        }



        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.animateY(1000);

        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getXAxis().setGranularity(1.0f);
        barChart.getXAxis().setLabelCount(barDataSet.getEntryCount());
        barChart.setData(barData);

    }
}