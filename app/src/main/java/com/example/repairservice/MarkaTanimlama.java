package com.example.repairservice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repairservice.Araclar.Marka;
import com.example.repairservice.Araclar.Model;
import com.example.repairservice.Araclar.Okuyucu;
import com.example.repairservice.Araclar.Yazici;
import com.example.repairservice.Tanimlamalar.Degerİkilisi;
import com.example.repairservice.Tanimlamalar.DropDownAdapter;
import com.example.repairservice.Tanimlamalar.Fonksiyonlar;

public class MarkaTanimlama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marka_tanimlama);

        ((TextView) findViewById(R.id.titleTextView)).setText("Marka Sayfası");

        Yazici yzc= new Yazici(MarkaTanimlama.this);
        Intent intt=getIntent();
        Okuyucu okc=new Okuyucu(MarkaTanimlama.this);
        Fonksiyonlar fnk=new Fonksiyonlar(MarkaTanimlama.this);
        String adi, aciklama;

        Spinner mevcutMarkaSpinner=findViewById(R.id.spn_marka);
        DropDownAdapter markaadapter=new DropDownAdapter(this,okc.markaver());
        mevcutMarkaSpinner.setAdapter(markaadapter);

        mevcutMarkaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenMarkaid=((Degerİkilisi) mevcutMarkaSpinner.getSelectedItem()).getId();
                if(secilenMarkaid!=-1) {
                    ((EditText) findViewById(R.id.marka_adi_edit)).setText(((Degerİkilisi) mevcutMarkaSpinner.getSelectedItem()).getText());
                    Marka secilenMarka = okc.idyegoremarkaver(secilenMarkaid);
                    fnk.spinnerSelector(mevcutMarkaSpinner, secilenMarka.getId());
                    ((EditText) findViewById(R.id.marka_aciklama_edit)).setText(secilenMarka.getAciklama());

                    //açıklama kısmını yaz
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((Button)findViewById(R.id.kaydet_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adi, aciklama;
                adi=((EditText) findViewById(R.id.marka_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.marka_aciklama_edit)).getText().toString();

                if (okc.aynimarkavarmi(adi)){
                    Toast.makeText(MarkaTanimlama.this, "Bu marka kullanıldı!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.markaYaz(adi, aciklama);
                    Toast.makeText(MarkaTanimlama.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MarkaTanimlama.this, YeniCihazKayit.class);
                }

                startActivity(new Intent(MarkaTanimlama.this, MarkaTanimlama.class));

            }
        });

        ((Button)findViewById(R.id.guncelle_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String adi, aciklama;
                int secilenMarkaId=((Degerİkilisi) mevcutMarkaSpinner.getSelectedItem()).getId();
                adi=((EditText) findViewById(R.id.marka_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.marka_aciklama_edit)).getText().toString();

                if (okc.aynimarkavarmi(adi,secilenMarkaId)){
                    Toast.makeText(MarkaTanimlama.this, "Bu marka kullanıldı!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.markaGuncelle(adi,aciklama,secilenMarkaId);
                    Toast.makeText(MarkaTanimlama.this, "Güncelleme Başarılı", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(MarkaTanimlama.this, MarkaTanimlama.class));

            }
        });

        ((Button)findViewById(R.id.sil_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int curMarka= ((Degerİkilisi)mevcutMarkaSpinner.getSelectedItem()).getId();

                AlertDialog.Builder silmeDialog = new AlertDialog.Builder(MarkaTanimlama.this);
                AlertDialog silDialog;
                silmeDialog.setMessage("Cihazı silmek istediğinizden emin misiniz ?")
                        .setTitle("Cihaz silinecek")
                        .setCancelable(false)
                        .setIcon(R.drawable.silme_24)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Silme işlemi bu kısma kodlanacak
                                yzc.markaSil(curMarka);
                                Toast.makeText(MarkaTanimlama.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
//TODO Tip,Marka, İşyeri,Cihaz,Model,Yedekparça,Onarım kayıtları silinmeden önce diğrer tablolarda kullanılıp kullanılmadığı tespit edilip silinecek.
            }
        });


    }
}