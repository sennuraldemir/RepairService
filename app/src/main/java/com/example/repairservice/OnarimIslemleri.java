package com.example.repairservice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repairservice.Araclar.Cihaz;
import com.example.repairservice.Araclar.Okuyucu;
import com.example.repairservice.Araclar.Onarim;
import com.example.repairservice.Araclar.SabitDegiskenler;
import com.example.repairservice.Araclar.Yazici;
import com.example.repairservice.Tanimlamalar.Fonksiyonlar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OnarimIslemleri extends AppCompatActivity {


    Okuyucu okc;
    Yazici yzc;
    LinearLayout anaLayout;
    LinearLayout.LayoutParams olayouParametreleri;

    Button yeni_onarim_Button;
    Button ara_Button;

    Fonksiyonlar fnk;

    Onarim curOnarim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onarim_islemleri);

        yzc=new Yazici(OnarimIslemleri.this);
        okc=new Okuyucu(OnarimIslemleri.this);
        fnk=new Fonksiyonlar(OnarimIslemleri.this);

        ((TextView) findViewById(R.id.titleTextView)).setText("Onarım İşlemleri Sayfası");

        // Burada kendimiz layout oluşturup içine edittext ve button ekliyoruz
        // edittext'lerde kaydedilmis cihazlarin bilgileri yazar
        // button'larda ise güncelle yazar ve tıklandığında GuncelleKayıt sayfasına yönlendirir

        anaLayout=(LinearLayout) findViewById(R.id.layout_onarim);

        // layout'ların ozelliklerini buradaki değişkende topladık
        olayouParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        //layout'un içindeki edittext ve button'ların ozelliklerini burada topladık
        //agirligi fazla olan az yer kaplar
        LinearLayout.LayoutParams buttonParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,5);
        LinearLayout.LayoutParams textviewParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,1);

        yeni_onarim_Button = findViewById(R.id.yeni_onarim_Button);
        yeni_onarim_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String envText= ((EditText)findViewById(R.id.onarim_islemleri_edit)).getText().toString();
                Cihaz chz=okc.envgoreCihazver(envText);

                //TODO bu cihaza ait daha önce acık bir onarım işlemi var mı? kontrol et

                if(chz!=null && okc.acikOnarimvarmi(chz.getId())){
                    Toast.makeText(OnarimIslemleri.this,"Aynı Anda 2 Açık Onarım Olamaz",Toast.LENGTH_SHORT).show();
                }else{
                    yzc.onarimKaydet(chz.getId(),chz.getIsyeri_id(), SabitDegiskenler.ONRDRM_ACIK,0,0,-1,
                            chz.getMarka_id(), chz.getModel_id(),0,"");

                    onarimListesiGetir();

                }





            }
        });
        //onarım kısmında ara butonuna basınca bilgiler gelsin diye
        ara_Button=findViewById(R.id.ara_Button);
        ara_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String envText=((EditText)findViewById(R.id.onarim_islemleri_edit)).getText().toString();
                Cihaz chz=okc.envgoreCihazver(envText);

                onarimListesiGetir();
            }
        });



    }
    public  void onarimListesiGetir(){

        olayouParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        //layout'un içindeki edittext ve button'ların ozelliklerini burada topladık
        //agirligi fazla olan az yer kaplar
        LinearLayout.LayoutParams buttonParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,3);
        LinearLayout.LayoutParams textviewParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,(float)1.5);

        String envText= ((EditText)findViewById(R.id.onarim_islemleri_edit)).getText().toString();
        Cihaz chz=okc.envgoreCihazver(envText);




        ArrayList<Onarim> onarimlistesi=okc.cihazIDyegoreOnarimver(chz.getId());

        // kaydedilmiş ilk 30 cihaz bilgisini verir

        anaLayout.removeAllViews();  //layoutu yeniler
        for(int i=0; i<onarimlistesi.size() && i<30; i++){

            Onarim curOnarim=onarimlistesi.get(i);

            //ara layout olusturduk ve içine bilgilerin yazacağı edittext ve guncelle butonunu yazdık
            LinearLayout araLayout=new LinearLayout(OnarimIslemleri.this);
            araLayout.setOrientation(LinearLayout.HORIZONTAL);  //yatay layout
            araLayout.setLayoutParams(olayouParametreleri);
            araLayout.setPadding(0,10,0,40);


            if(curOnarim.getOnarimdurum_id()==SabitDegiskenler.ONRDRM_ACIK){

                araLayout.setBackgroundColor(getColor(R.color.orange));
            } else if(curOnarim.getOnarimdurum_id()==SabitDegiskenler.ONRDRM_YENIDEN_BASLATILDI){

                araLayout.setBackgroundColor(getColor(R.color.mor));
            }  else if(curOnarim.getOnarimdurum_id()==SabitDegiskenler.ONRDRM_DURDURULDU){

                araLayout.setBackgroundColor(getColor(R.color.green));
            }
            else if(curOnarim.getOnarimdurum_id()==SabitDegiskenler.ONRDRM_SONLANDIRILDI){

                araLayout.setBackgroundColor(getColor(R.color.red));
            }

            TextView ozetTextView=new TextView(OnarimIslemleri.this);
            Button onarButon=new Button(OnarimIslemleri.this);
            Button silButon=new Button(OnarimIslemleri.this);

            onarButon.setText("Onar");
            silButon.setText("Sil");
            //TODO guncelleButon.setBackground(drawbale girilecek);

            String bastartext=" ";
            Calendar calbas=null;
            Date bastar=curOnarim.getBaslama_tarihi();
            if(bastar!=null){
                calbas=Calendar.getInstance();
                calbas.setTime(bastar);
                int bastar_ay=calbas.get(Calendar.MONTH)+1;
                bastartext=calbas.get(Calendar.DAY_OF_MONTH)+ "/" +bastar_ay+"/"+calbas.get(Calendar.YEAR);
            }

            String bitirtartext=" ";
            Calendar calbit=null;
            Date bitirtar=curOnarim.getBitis_tarihi();
            if(bitirtar!=null){
                calbit=Calendar.getInstance();
                calbit.setTime(bitirtar);
                int bitirtar_ay=calbit.get(Calendar.MONTH)+1;
                bitirtartext=calbit.get(Calendar.DAY_OF_MONTH)+ "/" +bitirtar_ay+"/"+calbit.get(Calendar.YEAR);
            }

            String toplamSure=fnk.onarimSureHesapla(curOnarim.getId()).getText();

            ozetTextView.setText(chz.getEnvno()+" "+okc.idyegoreisyeriver(curOnarim.getIsyeri_id()).getIsyeri_adi()+" "+
                    okc.idyegoremarkaver(curOnarim.getMarka_id()).getMarka_adi()+ " "+
                    okc.idyegoremodelver(curOnarim.getModel_id()).getModel_adi()+" "+bastartext+ " "+ bitirtartext
                    + " maliyet: " +curOnarim.getToplam_maliyet() + " toplam süre: " + toplamSure);

            onarButon.setLayoutParams(buttonParametreleri);
            silButon.setLayoutParams(buttonParametreleri);
            ozetTextView.setLayoutParams(textviewParametreleri);
            ozetTextView.setTextSize(15);
            ozetTextView.setPadding(5,0,5,50);

            //sayfamıza layout ve içindeki edittext ile button'u ekliyoruz
            araLayout.addView(ozetTextView);
            araLayout.addView(onarButon);
            araLayout.addView(silButon);
            anaLayout.addView(araLayout);

            onarButon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OnarimIslemleri.this, CihazBilgiOzeti.class);
                    intent.putExtra("onarim_id", curOnarim.getId());
                    startActivity(intent);

                }
            });

            silButon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder silmeDialog = new AlertDialog.Builder(OnarimIslemleri.this);
                    silmeDialog.setMessage("Onarımı silmek istediğinizden emin misiniz ?")
                            .setTitle("Onarım silinecek")
                            .setCancelable(false)
                            .setIcon(R.drawable.silme_24)
                            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //Silme işlemi bu kısma kodlanacak
                                    yzc.onarimSil(curOnarim.getId());
                                    Toast.makeText(OnarimIslemleri.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
                                    onarimListesiGetir();
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