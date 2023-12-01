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
import com.example.repairservice.Araclar.Yazici;
import com.example.repairservice.Tanimlamalar.Degerİkilisi;
import com.example.repairservice.Tanimlamalar.DropDownAdapter;
import com.example.repairservice.Tanimlamalar.Fonksiyonlar;

public class ModelTanimlama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_tanimlama);

        Okuyucu okc=new Okuyucu(ModelTanimlama.this);
        Fonksiyonlar fnk=new Fonksiyonlar(ModelTanimlama.this);
        Yazici yzc=new Yazici(ModelTanimlama.this);

        ((TextView) findViewById(R.id.titleTextView)).setText("Model Sayfası");

        Spinner mevcutMarkaSpinner=findViewById(R.id.spn_marka);
        DropDownAdapter markaadapter=new DropDownAdapter(this,okc.markaver());
        mevcutMarkaSpinner.setAdapter(markaadapter);

        Spinner mevcutModelSpinner=findViewById(R.id.spn_model);


        mevcutMarkaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenMarkaID = ((Degerİkilisi) mevcutMarkaSpinner.getSelectedItem()).getId();
                if (secilenMarkaID != -1){

                    DropDownAdapter modeladapter=new DropDownAdapter(ModelTanimlama.this,
                            okc.markaIdyegoreModelListever(secilenMarkaID));

                    mevcutModelSpinner.setAdapter(modeladapter);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mevcutModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int secilenModelId=((Degerİkilisi) mevcutModelSpinner.getSelectedItem()).getId();

                if(secilenModelId != -1) {

                    ((EditText) findViewById(R.id.model_adi_edit)).setText(((Degerİkilisi) mevcutModelSpinner.getSelectedItem()).getText());
                    Model secilenModel = okc.idyegoremodelver(secilenModelId);
                    fnk.spinnerSelector(mevcutMarkaSpinner, secilenModel.getMarka_id());
                    ((EditText) findViewById(R.id.model_aciklama_edit)).setText(secilenModel.getAciklama());

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
                adi=((EditText) findViewById(R.id.model_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.model_aciklama_edit)).getText().toString();
                int markaid= ((Degerİkilisi)mevcutMarkaSpinner.getSelectedItem()).getId();

                if (okc.aynimodelvarmi(adi)){
                    Toast.makeText(ModelTanimlama.this, "Bu model kullanıldı!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.modelYaz(adi, aciklama,markaid);
                    Toast.makeText(ModelTanimlama.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((Button)findViewById(R.id.guncelle_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String adi, aciklama;
                int secilenModelId=((Degerİkilisi) mevcutModelSpinner.getSelectedItem()).getId();
                adi=((EditText) findViewById(R.id.model_adi_edit)).getText().toString();
                aciklama=((EditText) findViewById(R.id.model_aciklama_edit)).getText().toString();
                int markaid= ((Degerİkilisi)mevcutMarkaSpinner.getSelectedItem()).getId();

                if (okc.aynimodelvarmi(adi,secilenModelId)){
                    Toast.makeText(ModelTanimlama.this, "Bu maodel kullanıldı!", Toast.LENGTH_SHORT).show();
                }else {
                    yzc.modelGuncelle(adi,aciklama,markaid,secilenModelId);
                    Toast.makeText(ModelTanimlama.this, "Güncelleme Başarılı", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(ModelTanimlama.this, ModelTanimlama.class));


            }
        });

        ((Button)findViewById(R.id.sil_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int curModel= ((Degerİkilisi)mevcutModelSpinner.getSelectedItem()).getId();

                AlertDialog.Builder silmeDialog = new AlertDialog.Builder(ModelTanimlama.this);
                AlertDialog silDialog;
                silmeDialog.setMessage("Cihazı silmek istediğinizden emin misisniz ?")
                        .setTitle("Cihaz silinecek")
                        .setCancelable(false)
                        .setIcon(R.drawable.silme_24)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Silme işlemi bu kısma kodlanacak
                                yzc.modelSil(curModel);
                                Toast.makeText(ModelTanimlama.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
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