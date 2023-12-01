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

import com.example.repairservice.Araclar.Isyeri;
import com.example.repairservice.Araclar.Marka;
import com.example.repairservice.Araclar.Okuyucu;
import com.example.repairservice.Araclar.Yazici;
import com.example.repairservice.Tanimlamalar.Degerİkilisi;
import com.example.repairservice.Tanimlamalar.DropDownAdapter;
import com.example.repairservice.Tanimlamalar.Fonksiyonlar;


public class IsyeriIslemleri extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isyeri_islemleri);

        ((TextView) findViewById(R.id.titleTextView)).setText("İşyeri Sayfası");

        // Burada Yazıcı ve Okuyucu class'larındaki fonksiyonları kullanacağız.

        Yazici yzc= new Yazici(IsyeriIslemleri.this);
        Intent intt=getIntent();
        Okuyucu okc=new Okuyucu(IsyeriIslemleri.this);
        Fonksiyonlar fnk=new Fonksiyonlar(IsyeriIslemleri.this);

        String adi, aciklama;

        //
        Spinner mevcutIsyeriSpinner=findViewById(R.id.spn_isyeri);
        DropDownAdapter isyeriadapter=new DropDownAdapter(this,okc.isyerleriver());
        mevcutIsyeriSpinner.setAdapter(isyeriadapter);


        mevcutIsyeriSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenIsyeriid=((Degerİkilisi) mevcutIsyeriSpinner.getSelectedItem()).getId();
                if(secilenIsyeriid!=-1) {
                    ((EditText) findViewById(R.id.isyeri_adi_edit)).setText(((Degerİkilisi) mevcutIsyeriSpinner.getSelectedItem()).getText());
                    Isyeri secilenIsyeri = okc.idyegoreisyeriver(secilenIsyeriid);
                    fnk.spinnerSelector(mevcutIsyeriSpinner, secilenIsyeri.getId());
                    ((EditText) findViewById(R.id.isyeri_aciklama_edit)).setText(secilenIsyeri.getAciklama());

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
                adi=((EditText) findViewById(R.id.isyeri_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.isyeri_aciklama_edit)).getText().toString();


                if (okc.ayniisyerivarmi(adi)){
                    Toast.makeText(IsyeriIslemleri.this, "Bu isyeri adı kullanıldı!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.isyeriYaz(adi, aciklama);
                    Toast.makeText(IsyeriIslemleri.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();


                }
            }
        });

        ((Button)findViewById(R.id.guncelle_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String adi, aciklama;
                int secilenIsyeriId=((Degerİkilisi) mevcutIsyeriSpinner.getSelectedItem()).getId();
                adi=((EditText) findViewById(R.id.isyeri_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.isyeri_aciklama_edit)).getText().toString();

                if (okc.ayniisyerivarmi(adi,secilenIsyeriId)){
                    Toast.makeText(IsyeriIslemleri.this, "Bu maodel kullanıldı!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.isyeriGuncelle(adi,aciklama,secilenIsyeriId);
                    Toast.makeText(IsyeriIslemleri.this, "Güncelleme Başarılı", Toast.LENGTH_SHORT).show();
                }

                //kaydedince güncelleme yapması için

                startActivity(new Intent(IsyeriIslemleri.this, IsyeriIslemleri.class));
            }
        });

        ((Button)findViewById(R.id.sil_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int curIsyeri= ((Degerİkilisi)mevcutIsyeriSpinner.getSelectedItem()).getId();

                AlertDialog.Builder silmeDialog = new AlertDialog.Builder(IsyeriIslemleri.this);
                AlertDialog silDialog;
                silmeDialog.setMessage("Cihazı silmek istediğinizden emin misisniz ?")
                        .setTitle("Cihaz silinecek")
                        .setCancelable(false)
                        .setIcon(R.drawable.silme_24)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Silme işlemi bu kısma kodlanacak
                                yzc.isyeriSil(curIsyeri);
                                Toast.makeText(IsyeriIslemleri.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

            }
        });


    }
}

