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

public class RPR_Ariza_Onr_Msrf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpr_ariza_onr_msrf);

        ((TextView)findViewById(R.id.titleTextView)).setText("Arıza Toplam Onarım Sayfası");


        BarChart barChart = (BarChart) findViewById(R.id.top_OnrSayi);
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        Okuyucu okc = new Okuyucu(RPR_Ariza_Onr_Msrf.this);

        ArrayList<DinamikTipler> arizaListesi=okc.arizaListesiVer();
        ArrayList<Onarim>onarimliste;


        double toplamMaliyet=0;
        double ustToplamMaliyet=0;

        for(int i=0;i<arizaListesi.size();i++){
            toplamMaliyet=0;
          int curArizaId=arizaListesi.get(i).getId();
          onarimliste=okc.arizatipiIdyegoreOnarimListesiVer(curArizaId);

          for(int j=0; j<onarimliste.size(); j++){
              Onarim curOnarim=okc.idyegoreOnarimver(onarimliste.get(j).getId());
              toplamMaliyet=curOnarim.getToplam_maliyet();
          }


            ustToplamMaliyet+=toplamMaliyet;
            barEntries.add(new BarEntry((float)i,(float)toplamMaliyet));

        }

        ((TextView)findViewById(R.id.arzOnrMsrfTextView)).setText("Arıza Toplam Maliyet : "+ustToplamMaliyet);


        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightColor(Color.RED);
        barDataSet.setValueTextSize(16f);
        barDataSet.setValueTextColor(Color.BLACK);

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