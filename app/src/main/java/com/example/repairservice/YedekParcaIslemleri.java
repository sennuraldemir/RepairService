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

import com.example.repairservice.Araclar.Model;
import com.example.repairservice.Araclar.Okuyucu;
import com.example.repairservice.Araclar.SabitDegiskenler;
import com.example.repairservice.Araclar.Yazici;
import com.example.repairservice.Araclar.YedekParca;
import com.example.repairservice.Tanimlamalar.Degerİkilisi;
import com.example.repairservice.Tanimlamalar.DropDownAdapter;
import com.example.repairservice.Tanimlamalar.Fonksiyonlar;

public class YedekParcaIslemleri extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yedek_parca_islemleri);

        ((TextView) findViewById(R.id.titleTextView)).setText("Yedek Parça Kayıt Sayfası");


        Yazici yzc= new Yazici(YedekParcaIslemleri.this);
        Okuyucu okc=new Okuyucu(YedekParcaIslemleri.this);
        Fonksiyonlar fnk=new Fonksiyonlar(YedekParcaIslemleri.this);

        Spinner mevcutYedekParcaTiplerSpinner=findViewById(R.id.spn_yedekparcatip);
        DropDownAdapter tiplistesiadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.YEDEK_PARCA_TİP));
        mevcutYedekParcaTiplerSpinner.setAdapter(tiplistesiadapter);


        Spinner mevcutYedekParcaSpinner=findViewById(R.id.spn_yedekparca);


        Spinner mevcutMarkaSpinner=findViewById(R.id.spn_marka);
        DropDownAdapter markaadapter=new DropDownAdapter(this,okc.markaver());
        mevcutMarkaSpinner.setAdapter(markaadapter);

        Spinner mevcutModelSpinner=findViewById(R.id.spn_model);


        mevcutMarkaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenMarkaID = ((Degerİkilisi) mevcutMarkaSpinner.getSelectedItem()).getId();
                if (secilenMarkaID != -1){

                    DropDownAdapter modeladapter=new DropDownAdapter(YedekParcaIslemleri.this,
                            okc.markaIdyegoreModelListever(secilenMarkaID));

                    mevcutModelSpinner.setAdapter(modeladapter);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mevcutYedekParcaTiplerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                int secilenTipId=((Degerİkilisi) mevcutYedekParcaTiplerSpinner.getSelectedItem()).getId();

                if(secilenTipId!=-1) {

                    //YedekParca secilenYedekParca = okc.idyegoreYedekParcaver(secilenTipId);

                    DropDownAdapter yedekparcaadapter=new DropDownAdapter(YedekParcaIslemleri.this
                            ,okc.tipegoreYedekParcaListever(secilenTipId));
                    mevcutYedekParcaSpinner.setAdapter(yedekparcaadapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        mevcutYedekParcaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenParcaId=((Degerİkilisi) mevcutYedekParcaSpinner.getSelectedItem()).getId();

                if(secilenParcaId!=-1) {

                    ((EditText) findViewById(R.id.parca_adi_edit)).setText(((Degerİkilisi) mevcutYedekParcaSpinner.getSelectedItem()).getText());

                    YedekParca secilenYedekParca = okc.idyegoreYedekParcaver(secilenParcaId);
                    fnk.spinnerSelector(mevcutMarkaSpinner, secilenYedekParca.getMarka_id());

                    DropDownAdapter modeladapter=new DropDownAdapter(YedekParcaIslemleri.this,
                            okc.markaIdyegoreModelListever(secilenYedekParca.getMarka_id()));

                    mevcutModelSpinner.setAdapter(modeladapter);

                    fnk.spinnerSelector(mevcutModelSpinner, secilenYedekParca.getModel_id());

                    ((EditText) findViewById(R.id.yedekparcaaciklama)).setText(secilenYedekParca.getAciklama());
                    ((EditText) findViewById(R.id.editTextfiyat)).setText(String.valueOf(secilenYedekParca.getFiyat()));
                    ((EditText) findViewById(R.id.editTextstok)).setText(String.valueOf(secilenYedekParca.getStok_miktar()));
                    ((EditText) findViewById(R.id.editTextstokmin)).setText(String.valueOf(secilenYedekParca.getStokmin()));

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
                double fiyat;
                int stok_miktar,stok_min;


                int secilenTipID=((Degerİkilisi)mevcutYedekParcaTiplerSpinner.getSelectedItem()).getId();
                adi=((EditText) findViewById(R.id.parca_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.yedekparcaaciklama)).getText().toString();
                fiyat = Double.parseDouble(((EditText)findViewById(R.id.editTextfiyat)).getText().toString());
                stok_miktar=Integer.parseInt(((EditText)findViewById(R.id.editTextstok)).getText().toString());
                stok_min=Integer.parseInt(((EditText)findViewById(R.id.editTextstokmin)).getText().toString());

                int markaid=((Degerİkilisi) mevcutMarkaSpinner.getSelectedItem()).getId();
                int modelid=((Degerİkilisi) mevcutModelSpinner.getSelectedItem()).getId();

                if (okc.ayniyedekparcavarmi(adi)){
                    Toast.makeText(YedekParcaIslemleri.this, "Bu yedek parça kullanıldı!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.yedekParcaYaz(markaid, modelid, stok_min,stok_miktar,fiyat,adi,aciklama,secilenTipID);
                    Toast.makeText(YedekParcaIslemleri.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((Button)findViewById(R.id.guncelle_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String adi, aciklama;
                double fiyat;
                int stok_miktar,stok_min;

                int secilenTipID=((Degerİkilisi)mevcutYedekParcaTiplerSpinner.getSelectedItem()).getId();
                int secilenParcaID=((Degerİkilisi)mevcutYedekParcaSpinner.getSelectedItem()).getId();

                adi=((EditText) findViewById(R.id.parca_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.yedekparcaaciklama)).getText().toString();
                fiyat = Double.parseDouble(((EditText)findViewById(R.id.editTextfiyat)).getText().toString());
                stok_miktar=Integer.parseInt(((EditText)findViewById(R.id.editTextstok)).getText().toString());
                stok_min=Integer.parseInt(((EditText)findViewById(R.id.editTextstokmin)).getText().toString());

                int markaid=((Degerİkilisi) mevcutMarkaSpinner.getSelectedItem()).getId();
                int modelid=((Degerİkilisi) mevcutModelSpinner.getSelectedItem()).getId();

                if (okc.ayniyedekparcavarmi(adi,secilenParcaID)){
                    Toast.makeText(YedekParcaIslemleri.this, "Bu yedek parça kullanıldı!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.yedekParcaGuncelle(secilenParcaID, markaid, modelid,stok_min,stok_miktar,fiyat,adi,aciklama,secilenTipID);
                    Toast.makeText(YedekParcaIslemleri.this, "Güncelleme Başarılı", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ((Button)findViewById(R.id.sil_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int curYedekparca= ((Degerİkilisi)mevcutYedekParcaSpinner.getSelectedItem()).getId();

                AlertDialog.Builder silmeDialog = new AlertDialog.Builder(YedekParcaIslemleri.this);
                AlertDialog silDialog;
                silmeDialog.setMessage("Yedek Parçayı silmek istediğinizden emin misiniz ?")
                        .setTitle("Yedek Parça silinecek")
                        .setCancelable(false)
                        .setIcon(R.drawable.silme_24)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Silme işlemi bu kısma kodlanacak
                                yzc.yedekParcaSil(curYedekparca);
                                Toast.makeText(YedekParcaIslemleri.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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