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

import com.example.repairservice.Araclar.DinamikTipler;
import com.example.repairservice.Araclar.Okuyucu;
import com.example.repairservice.Araclar.SabitDegiskenler;
import com.example.repairservice.Araclar.Yazici;
import com.example.repairservice.Tanimlamalar.Degerİkilisi;
import com.example.repairservice.Tanimlamalar.DropDownAdapter;
import com.example.repairservice.Tanimlamalar.Fonksiyonlar;

public class TipTanimlama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_tanimlama);

        Yazici yzc= new Yazici(TipTanimlama.this);
        Fonksiyonlar fnk=new Fonksiyonlar(TipTanimlama.this);

        Intent intt=getIntent();
        int tiptipi = intt.getIntExtra("tiptipi",0);

        Okuyucu okc=new Okuyucu(TipTanimlama.this);

        String adi, aciklama;

        if(tiptipi== SabitDegiskenler.ANDROİD_SURUM_TİP){

            Spinner mevcutTiplerSpinner=findViewById(R.id.mevcuttiplerspinner);
            DropDownAdapter tiplistesiadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.ANDROİD_SURUM_TİP));
            mevcutTiplerSpinner.setAdapter(tiplistesiadapter);

            mevcutTiplerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    int secilenTipid=((Degerİkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    if(secilenTipid!=-1) {
                        ((EditText) findViewById(R.id.tip_adi_edit)).setText(((Degerİkilisi) mevcutTiplerSpinner.getSelectedItem()).getText());
                        DinamikTipler secilenTip = okc.idyegoretipver(secilenTipid);
                        fnk.spinnerSelector(mevcutTiplerSpinner, secilenTip.getId());
                        ((EditText) findViewById(R.id.tip_aciklama_edit)).setText(secilenTip.getAciklama());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ((TextView) findViewById(R.id.titleTextView)).setText("Android Sürüm Tanımlama");
            ((EditText) findViewById(R.id.tip_adi_edit)).setHint("Android Sürüm Adı Giriniz: ");
            ((EditText) findViewById(R.id.tip_aciklama_edit)).setHint("Açıklama Giriniz: ");

            ((Button)findViewById(R.id.tip_kaydet_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String adi, aciklama;
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi)){
                        Toast.makeText(TipTanimlama.this, "Bu Android sürümü kayıtlı!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tipYaz(adi, aciklama,SabitDegiskenler.ANDROİD_SURUM_TİP);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ((Button)findViewById(R.id.tip_guncelle_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String adi, aciklama;
                    int secilenTipId=((Degerİkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();


                    if (okc.aynitipadieditvarmi(adi,secilenTipId)){
                        Toast.makeText(TipTanimlama.this, "Bu android sürüm no kullanıldı!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tiptanimlamaGuncelle(adi,aciklama,secilenTipId);
                        Toast.makeText(TipTanimlama.this, "Güncelleme Başarılı", Toast.LENGTH_SHORT).show();
                    }

                    //kaydedince güncelleme yapması için

                    startActivity(new Intent(TipTanimlama.this, TipTanimlama.class));
                }
            });

            ((Button)findViewById(R.id.tip_sil_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int curTip= ((Degerİkilisi)mevcutTiplerSpinner.getSelectedItem()).getId();

                    AlertDialog.Builder silmeDialog = new AlertDialog.Builder(TipTanimlama.this);
                    silmeDialog.setMessage("Android Sürüm numarasını silmek istediğinizden emin misiniz ?")
                            .setTitle("Android Sürüm numarası silinecek")
                            .setCancelable(false)
                            .setIcon(R.drawable.silme_24)
                            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //Silme işlemi bu kısma kodlanacak
                                    yzc.tiptanimlamaSil(curTip);
                                    Toast.makeText(TipTanimlama.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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

        else if (tiptipi== SabitDegiskenler.EKRAN_BOYUT_TİP) {
            Spinner mevcutTiplerSpinner=findViewById(R.id.mevcuttiplerspinner);

            DropDownAdapter tiplistesiadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.EKRAN_BOYUT_TİP));
            mevcutTiplerSpinner.setAdapter(tiplistesiadapter);

            ((TextView) findViewById(R.id.titleTextView)).setText("Ekran Boyutu Tanımlama");
            ((EditText) findViewById(R.id.tip_adi_edit)).setHint("Ekran Boyutu Giriniz: ");
            ((EditText) findViewById(R.id.tip_aciklama_edit)).setHint("Açıklama Giriniz: ");

            ((Button)findViewById(R.id.tip_kaydet_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String adi, aciklama;
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi)){
                        Toast.makeText(TipTanimlama.this, "Bu Ekran numarası kayıtlı!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tipYaz(adi, aciklama,SabitDegiskenler.EKRAN_BOYUT_TİP);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            ((Button)findViewById(R.id.tip_guncelle_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String adi, aciklama;
                    int secilenTipId=((Degerİkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi)){
                        Toast.makeText(TipTanimlama.this, "Bu Ekran numarası kayıtlı!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tiptanimlamaGuncelle(adi,aciklama,secilenTipId);
                        Toast.makeText(TipTanimlama.this, "güncelleme Başarılı", Toast.LENGTH_SHORT).show();
                    }

                }

            });

            ((Button)findViewById(R.id.tip_sil_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int curTip= ((Degerİkilisi)mevcutTiplerSpinner.getSelectedItem()).getId();

                    AlertDialog.Builder silmeDialog = new AlertDialog.Builder(TipTanimlama.this);
                    silmeDialog.setMessage("Ekran numarasını silmek istediğinizden emin misiniz ?")
                            .setTitle("Ekran numarası silinecek")
                            .setCancelable(false)
                            .setIcon(R.drawable.silme_24)
                            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //Silme işlemi bu kısma kodlanacak
                                    yzc.tiptanimlamaSil(curTip);
                                    Toast.makeText(TipTanimlama.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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

        else if (tiptipi== SabitDegiskenler.ARIZA_TİP) {

            Spinner mevcutTiplerSpinner=findViewById(R.id.mevcuttiplerspinner);
            DropDownAdapter tiplistesiadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.ARIZA_TİP));
            mevcutTiplerSpinner.setAdapter(tiplistesiadapter);

            ((TextView) findViewById(R.id.titleTextView)).setText("Arıza Tipi Tanımlama");
            ((EditText) findViewById(R.id.tip_adi_edit)).setHint("Arıza Tipi Giriniz: ");
            ((EditText) findViewById(R.id.tip_aciklama_edit)).setHint("Açıklama Giriniz: ");


            ((Button)findViewById(R.id.tip_kaydet_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String adi, aciklama;
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi)){
                        Toast.makeText(TipTanimlama.this, "Bu arıza tipi kayıtlı!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tipYaz(adi, aciklama,SabitDegiskenler.ARIZA_TİP);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            ((Button)findViewById(R.id.tip_guncelle_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String adi, aciklama;
                    int secilenTipId=((Degerİkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi, secilenTipId)){
                        Toast.makeText(TipTanimlama.this, "Bu arıza tipi kayıtlı!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tiptanimlamaGuncelle(adi,aciklama,secilenTipId);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();

                    }

                    startActivity(new Intent(TipTanimlama.this, TipTanimlama.class));
                }
            });

            ((Button)findViewById(R.id.tip_sil_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int curTip= ((Degerİkilisi)mevcutTiplerSpinner.getSelectedItem()).getId();

                    AlertDialog.Builder silmeDialog = new AlertDialog.Builder(TipTanimlama.this);
                    AlertDialog silDialog;
                    silmeDialog.setMessage("Arıza tipini silmek istediğinizden emin misisniz ?")
                            .setTitle("Arıza Tip silinecek")
                            .setCancelable(false)
                            .setIcon(R.drawable.silme_24)
                            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //Silme işlemi bu kısma kodlanacak
                                    yzc.tiptanimlamaSil(curTip);
                                    Toast.makeText(TipTanimlama.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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

        else if (tiptipi== SabitDegiskenler.ONARIM_DURUM_TİP) {

            Spinner mevcutTiplerSpinner=findViewById(R.id.mevcuttiplerspinner);
            DropDownAdapter tiplistesiadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.ONARIM_DURUM_TİP));
            mevcutTiplerSpinner.setAdapter(tiplistesiadapter);

            ((TextView) findViewById(R.id.titleTextView)).setText("Onarım Durum Tipi Tanımlama");
            ((EditText) findViewById(R.id.tip_adi_edit)).setHint("Onarım Durum Tipi Giriniz: ");
            ((EditText) findViewById(R.id.tip_aciklama_edit)).setHint("Açıklama Giriniz: ");

            ((Button)findViewById(R.id.tip_kaydet_button)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String adi, aciklama;
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi)){
                        Toast.makeText(TipTanimlama.this, "Bu onarim tipi kayıtlı!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tipYaz(adi, aciklama,SabitDegiskenler.ONARIM_DURUM_TİP);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            ((Button)findViewById(R.id.tip_guncelle_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String adi, aciklama;
                    int secilenTipId=((Degerİkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi, secilenTipId)){
                        Toast.makeText(TipTanimlama.this, "Bu onarım tipi kayıtlı!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tiptanimlamaGuncelle(adi,aciklama,secilenTipId);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();

                    }

                    startActivity(new Intent(TipTanimlama.this, TipTanimlama.class));
                }
            });

            ((Button)findViewById(R.id.tip_sil_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int curTip= ((Degerİkilisi)mevcutTiplerSpinner.getSelectedItem()).getId();

                    AlertDialog.Builder silmeDialog = new AlertDialog.Builder(TipTanimlama.this);
                    AlertDialog silDialog;
                    silmeDialog.setMessage("Onarım tipini silmek istediğinizden emin misiniz ?")
                            .setTitle("Yapılan Onarım silinecek")
                            .setCancelable(false)
                            .setIcon(R.drawable.silme_24)
                            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //Silme işlemi bu kısma kodlanacak
                                    yzc.tiptanimlamaSil(curTip);
                                    Toast.makeText(TipTanimlama.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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
        else if (tiptipi== SabitDegiskenler.YEDEK_PARCA_TİP) {

            Spinner mevcutTiplerSpinner=findViewById(R.id.mevcuttiplerspinner);
            DropDownAdapter tiplistesiadapter=new DropDownAdapter(this,okc.tipgrubunaGoreTipleriver(SabitDegiskenler.YEDEK_PARCA_TİP));
            mevcutTiplerSpinner.setAdapter(tiplistesiadapter);

            mevcutTiplerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    int secilenTipid=((Degerİkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    if(secilenTipid!=-1) {
                        ((EditText) findViewById(R.id.tip_adi_edit)).setText(((Degerİkilisi) mevcutTiplerSpinner.getSelectedItem()).getText());
                        DinamikTipler secilenTip = okc.idyegoretipver(secilenTipid);
                        fnk.spinnerSelector(mevcutTiplerSpinner, secilenTip.getId());
                        ((EditText) findViewById(R.id.tip_aciklama_edit)).setText(secilenTip.getAciklama());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            ((TextView) findViewById(R.id.titleTextView)).setText("Yedek Parça Tipi Tanımlama");
            ((EditText) findViewById(R.id.tip_adi_edit)).setHint("Yedek Parça Tipi Giriniz: ");
            ((EditText) findViewById(R.id.tip_aciklama_edit)).setHint("Açıklama Giriniz: ");

            ((Button)findViewById(R.id.tip_kaydet_button)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String adi, aciklama;
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi)){
                        Toast.makeText(TipTanimlama.this, "Bu yedekparça tipi kayıtlı!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tipYaz(adi, aciklama,SabitDegiskenler.YEDEK_PARCA_TİP);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            ((Button)findViewById(R.id.tip_guncelle_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String adi, aciklama;
                    int secilenTipId=((Degerİkilisi) mevcutTiplerSpinner.getSelectedItem()).getId();
                    adi=((EditText) findViewById(R.id.tip_adi_edit)).getText().toString();
                    aciklama=((EditText) findViewById(R.id.tip_aciklama_edit)).getText().toString();

                    if (okc.aynitipadieditvarmi(adi, secilenTipId)){
                        Toast.makeText(TipTanimlama.this, "Bu yedekparça tipi kayıtlı!", Toast.LENGTH_SHORT).show();
                    }else {
                        yzc.tiptanimlamaGuncelle(adi,aciklama,secilenTipId);
                        Toast.makeText(TipTanimlama.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();

                    }

                    startActivity(new Intent(TipTanimlama.this, TipTanimlama.class));
                }
            });

            ((Button)findViewById(R.id.tip_sil_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int curTip= ((Degerİkilisi)mevcutTiplerSpinner.getSelectedItem()).getId();

                    AlertDialog.Builder silmeDialog = new AlertDialog.Builder(TipTanimlama.this);
                    AlertDialog silDialog;
                    silmeDialog.setMessage("yedek parça tipini silmek istediğinizden emin misiniz ?")
                            .setTitle("Yedek Parça silinecek")
                            .setCancelable(false)
                            .setIcon(R.drawable.silme_24)
                            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //Silme işlemi bu kısma kodlanacak
                                    yzc.tiptanimlamaSil(curTip);
                                    Toast.makeText(TipTanimlama.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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
}