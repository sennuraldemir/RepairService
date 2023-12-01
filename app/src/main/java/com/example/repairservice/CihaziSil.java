package com.example.repairservice;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repairservice.Araclar.Cihaz;
import com.example.repairservice.Araclar.Okuyucu;
import com.example.repairservice.Araclar.Yazici;

import java.util.ArrayList;

public class CihaziSil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cihazi_sil);

        ((TextView) findViewById(R.id.titleTextView)).setText("Cihazı Silme Sayfası");

        Yazici yzc=new Yazici(CihaziSil.this);
        Okuyucu okc=new Okuyucu(CihaziSil.this);
        Button ara_Button = findViewById(R.id.ara_Button);

        LinearLayout anaLayout=(LinearLayout) findViewById(R.id.layout_sil);

        LinearLayout.LayoutParams hlayouParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams buttonParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,3);
        LinearLayout.LayoutParams textviewParametreleri=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,1);

        ara_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String envnoText= ((EditText)findViewById(R.id.cihazi_sil_edit)).getText().toString();
                ArrayList<Cihaz> cihazlistesi=okc.bilgiver(envnoText);
                anaLayout.removeAllViews();
                for(int i=0; i<cihazlistesi.size() && i<30; i++){

                    Cihaz curCihaz=cihazlistesi.get(i);

                    LinearLayout araLayout=new LinearLayout(CihaziSil.this);
                    araLayout.setOrientation(LinearLayout.HORIZONTAL);  //yatay layout
                    araLayout.setLayoutParams(hlayouParametreleri);

                    TextView ozetTextView=new TextView(CihaziSil.this);

                    ozetTextView.setLayoutParams(textviewParametreleri);
                    ozetTextView.setTextSize(30);
                    ozetTextView.setTextColor(WHITE);

                    ImageButton silButon=new ImageButton(CihaziSil.this);


                    silButon.setImageDrawable(getDrawable(R.drawable.baseline_delete));
                    silButon.setBackgroundColor(BLACK);


                    ozetTextView.setText(curCihaz.getEnvno()+" "+okc.idyegoremarkaver(curCihaz.getMarka_id()).getMarka_adi()+" "
                            +okc.idyegoremodelver(curCihaz.getModel_id()).getModel_adi()+" "
                            +curCihaz.getGsm_no()+" "+curCihaz.getImei_no()+" "+curCihaz.getAciklama());

                    silButon.setLayoutParams(buttonParametreleri);
                    ozetTextView.setLayoutParams(textviewParametreleri);
                    ozetTextView.setTextSize(15);

                    araLayout.addView(ozetTextView);
                    araLayout.addView(silButon);
                    anaLayout.addView(araLayout);

                    silButon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            // Uyarı mesajı!
                            AlertDialog.Builder silmeDialog = new AlertDialog.Builder(CihaziSil.this);
                            AlertDialog silDialog;
                            silmeDialog.setMessage("Cihazı silmek istediğinizden emin misisniz ?")
                                    .setTitle("Cihaz silinecek")
                                    .setCancelable(false)
                                    .setIcon(R.drawable.baseline_delete)
                                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            //Silme işlemi bu kısma kodlanacak
                                            yzc.cihazSil(curCihaz.getId());
                                            Toast.makeText(CihaziSil.this, "Silme İşlemi Başarılı!", Toast.LENGTH_SHORT).show();
                                            ara_Button.callOnClick();
                                            //Bu kısım silme işlemi sonrası listenin güncel halini göstermek için eklendi
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
        });

    }


}