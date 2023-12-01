package com.example.repairservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Raporlar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raporlamalar);

        ((TextView)findViewById(R.id.titleTextView)).setText("RAPORLAMALAR");


        Button toplam_onarim_sayisi = findViewById(R.id.toplam_onarim_sayisi);
        toplam_onarim_sayisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Raporlar.this, RPR_ArizaToplam_Onr_Sayisi.class);
                startActivity(intent);
            }
        });

        Button ort_onr_masrafi = findViewById(R.id.ort_onr_masrafi);
        ort_onr_masrafi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Raporlar.this, RPR_Genel_Ort_Onr_masrafi.class);
                startActivity(intent);
            }
        });

        Button ort_onr_suresi = findViewById(R.id.ort_onr_suresi);
        ort_onr_suresi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Raporlar.this, RPR_Genel_Ort_Onr_Suresi.class);
                startActivity(intent);
            }
        });

        Button ariza_onr_masraf = findViewById(R.id.ariza_onr_masraf);
        ariza_onr_masraf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Raporlar.this, RPR_Ariza_Onr_Msrf.class);
                startActivity(intent);
            }
        });



        Button ariza_onr_sure = findViewById(R.id.ariza_onr_sure);
        ariza_onr_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Raporlar.this, RPR_Ariza_Onr_Sure.class);
                startActivity(intent);
            }
        });

        Button marka_rapor = findViewById(R.id.marka_rapor);
        marka_rapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Raporlar.this, RPR_MarkaModelRaporu.class);
                startActivity(intent);
            }
        });

        Button isyeri_rapor = findViewById(R.id.isyeri_rapor);
        isyeri_rapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Raporlar.this, RPR_IsyeriCihazRaporu.class);
                startActivity(intent);
            }
        });

        Button isyeri_onarim_rapor = findViewById(R.id.isyeri_onarim_rapor);
        isyeri_onarim_rapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Raporlar.this, RPR_IsyeriOnarimRapor.class);
                startActivity(intent);
            }
        });

        Button chz_tum_rapor = findViewById(R.id.chz_tum_rapor);
        chz_tum_rapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Raporlar.this, RPR_Cihaz_OnrMasrafveSure.class);
                startActivity(intent);
            }
        });

        Button toplam_yedekparca_rapor = findViewById(R.id.toplam_yedekparca_rapor);
        toplam_yedekparca_rapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Raporlar.this, RPR_YedekParca.class);
                startActivity(intent);
            }
        });

        //TODO hangi sayfaya gidileceğini güncelle


    }
}