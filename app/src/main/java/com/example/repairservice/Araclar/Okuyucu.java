package com.example.repairservice.Araclar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.repairservice.Tanimlamalar.Degerİkilisi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

// Okuyucu class'ı ile veritabanında kayıtlı olan verileri görürüz

public class Okuyucu {
    SQLiteDatabase db ;
    DateFormat tcdd_DateFormat;
    public Okuyucu(Context cntx) {
        TabanHelper t1=new TabanHelper(cntx);
        db=t1.getWritableDatabase();
        tcdd_DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    // android sürüm, ekran boyutu, yedek parça vs için kullanılan fonksiyon
    @SuppressLint("Range")
    public ArrayList<Degerİkilisi> tipgrubunaGoreTipleriver(int tipgrupid){
        ArrayList<Degerİkilisi> sonuc =new ArrayList<>();
        if(tipgrupid==SabitDegiskenler.ANDROİD_SURUM_TİP){
            Degerİkilisi seciniz= new Degerİkilisi(-1,"Android Sürüm Girin!");
            sonuc.add(seciniz);
        }else if(tipgrupid==SabitDegiskenler.EKRAN_BOYUT_TİP){
            Degerİkilisi seciniz= new Degerİkilisi(-1,"Ekran Boyutu Girin!");
            sonuc.add(seciniz);
        }else if(tipgrupid==SabitDegiskenler.YEDEK_PARCA_TİP){
            Degerİkilisi seciniz= new Degerİkilisi(-1,"Yedek Parça Tipi Girin!");
            sonuc.add(seciniz);
        }
        else if(tipgrupid==SabitDegiskenler.ARIZA_TİP){
            Degerİkilisi seciniz= new Degerİkilisi(-1,"Arıza Tipi Girin!");
            sonuc.add(seciniz);
        }



        Cursor cr=db.rawQuery("Select * from DINAMIK_TIPLER where silindi = 0 and tipgrupid = "+tipgrupid,null);
        int id;
        String adi;

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                @SuppressLint("Range") Degerİkilisi curTip = new Degerİkilisi(cr.getInt((cr.getColumnIndex("id"))),cr.getString(cr.getColumnIndex("adi")));


                sonuc.add(curTip);
            }
        }

        return sonuc;


    }

    @SuppressLint("Range")
    public ArrayList<Degerİkilisi> isyerleriver(){
        ArrayList<Degerİkilisi> sonuc =new ArrayList<>();

        // Spinner da yazacak olan yazı
        Degerİkilisi seciniz= new Degerİkilisi(-1,"İşyeri seçiniz:");
        sonuc.add(seciniz);

        // silindi=1 olursa o veri veritabanından silinmiş olur o yüzden silindi=0 olanları istedik
        Cursor cr=db.rawQuery("Select * from ISYERI where silindi = 0",null);
        int id;
        String adi;

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                @SuppressLint("Range") Degerİkilisi curIsyeri = new Degerİkilisi(cr.getInt((cr.getColumnIndex("id"))),cr.getString(cr.getColumnIndex("isyeri_adi")));


                sonuc.add(curIsyeri);
            }
        }

        return sonuc;

    }

    @SuppressLint("Range")
    public ArrayList<Degerİkilisi> markaver(){
        ArrayList<Degerİkilisi> sonuc =new ArrayList<>();
        Degerİkilisi seciniz= new Degerİkilisi(-1,"Marka seçiniz:");
        sonuc.add(seciniz);


        Cursor cr=db.rawQuery("Select * from MARKA where silindi = 0",null);
        int id;
        String adi;

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                @SuppressLint("Range") Degerİkilisi curMarka = new Degerİkilisi(cr.getInt((cr.getColumnIndex("id"))),cr.getString(cr.getColumnIndex("marka_adi")));


                sonuc.add(curMarka);
            }
        }

        return sonuc;

    }

    @SuppressLint("Range")
    public ArrayList<Degerİkilisi> modelver(){
        ArrayList<Degerİkilisi> sonuc =new ArrayList<>();
        Degerİkilisi seciniz= new Degerİkilisi(-1,"Model seçiniz:");
        sonuc.add(seciniz);


        Cursor cr=db.rawQuery("Select * from MODEL where silindi = 0",null);
        int id;
        String adi;

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                @SuppressLint("Range") Degerİkilisi curModel = new Degerİkilisi(cr.getInt((cr.getColumnIndex("id"))),cr.getString(cr.getColumnIndex("model_adi")));


                sonuc.add(curModel);
            }
        }

        return sonuc;

    }

    @SuppressLint("Range")
    public ArrayList<Degerİkilisi> yedekParcaver(){
        ArrayList<Degerİkilisi> sonuc =new ArrayList<>();

        // Spinner da yazacak olan yazı
        Degerİkilisi seciniz= new Degerİkilisi(-1,"Yedek Parça Seçiniz:");
        sonuc.add(seciniz);

        // silindi=1 olursa o veri veritabanından silinmiş olur o yüzden silindi=0 olanları istedik
        Cursor cr=db.rawQuery("Select * from YEDEKPARCA where silindi = 0",null);
        int id;
        String adi;

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                @SuppressLint("Range") Degerİkilisi curYedekParca = new Degerİkilisi(cr.getInt((cr.getColumnIndex("id"))),cr.getString(cr.getColumnIndex("parca_adi")));


                sonuc.add(curYedekParca);
            }
        }

        return sonuc;

    }
    @SuppressLint("Range")
    public Model idyegoremodelver(int modelid){
        ArrayList<Model> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from MODEL where silindi = 0 and id="+modelid,null);
        int id;
        String adi;

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Model curModel=new Model();
                curModel.setId(cr.getInt((cr.getColumnIndex("id"))));
                curModel.setMarka_id(cr.getInt((cr.getColumnIndex("marka_id"))));
                curModel.setModel_adi(cr.getString(cr.getColumnIndex("model_adi")));
                curModel.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curModel);
            }
        }

        return sonuc.get(0);

    }

    @SuppressLint("Range")
    public YedekParca idyegoreYedekParcaver(int parcaid){
        ArrayList<YedekParca> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from YEDEKPARCA where id="+parcaid,null);
        int id;
        String adi;

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                YedekParca curYedekParca=new YedekParca();
                curYedekParca.setId(cr.getInt((cr.getColumnIndex("id"))));
                curYedekParca.setYedekparcatipi_id(cr.getInt((cr.getColumnIndex("yedekparcatipi_id"))));
                curYedekParca.setMarka_id(cr.getInt((cr.getColumnIndex("marka_id"))));
                curYedekParca.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curYedekParca.setParca_adi(cr.getString((cr.getColumnIndex("parca_adi"))));
                curYedekParca.setFiyat(cr.getFloat(cr.getColumnIndex("fiyat")));
                curYedekParca.setStokmin(cr.getInt((cr.getColumnIndex("stokmin"))));
                curYedekParca.setStok_miktar(cr.getInt((cr.getColumnIndex("stok_miktar"))));
                curYedekParca.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));


                sonuc.add(curYedekParca);
            }
        }

        return sonuc.get(0);

    }

    @SuppressLint("Range")
    public ArrayList<Degerİkilisi> tipegoreYedekParcaListever(int parcatipid){
        ArrayList<Degerİkilisi> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from YEDEKPARCA where silindi = 0 and yedekparcatipi_id="+parcatipid,null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Degerİkilisi curYedekParca=new Degerİkilisi(cr.getInt((cr.getColumnIndex("id"))),
                        cr.getString((cr.getColumnIndex("parca_adi"))));



                sonuc.add(curYedekParca);
            }
        }

        return sonuc;

    }

    public boolean ayniyedekparcavarmi(String parca_adi) {

        Cursor cr = db.rawQuery("Select * from YEDEKPARCA where silindi=0 and parca_adi='"+parca_adi+"'", null);

        if(cr.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }
    public boolean ayniyedekparcavarmi(String parca_adi,int id) {

        Cursor cr = db.rawQuery("Select * from YEDEKPARCA where silindi=0 and parca_adi='"+parca_adi+"' and id!="+id, null);

        if(cr.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }

    public boolean acikOnarimvarmi(int cihaz_id) {

        Cursor cr = db.rawQuery("Select * from ONARIM where silindi=0 and onarim_bitti=0 and cihaz_id= " + cihaz_id , null);

        if(cr.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }



    @SuppressLint("Range")
    public Isyeri idyegoreisyeriver(int isyeriid){
        ArrayList<Isyeri> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from ISYERI where silindi = 0 and id="+isyeriid,null);
        int id;
        String adi;

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Isyeri curIsyeri=new Isyeri();
                curIsyeri.setId(cr.getInt((cr.getColumnIndex("id"))));
                curIsyeri.setIsyeri_adi(cr.getString(cr.getColumnIndex("isyeri_adi")));
                curIsyeri.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curIsyeri);
            }
        }

        return sonuc.get(0);

    }

    @SuppressLint("Range")
    public ArrayList<Cihaz>  bilgiver(String envnoparcasi){
        ArrayList<Cihaz> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from CIHAZ where silindi = 0 and envno like '%"+envnoparcasi+"%'",null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Cihaz curCihaz=new Cihaz();
                curCihaz.setId(cr.getInt(cr.getColumnIndex("id")));
                curCihaz.setEnvno(cr.getString(cr.getColumnIndex("envno")));
                curCihaz.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curCihaz.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curCihaz.setAndroidsurum_id(cr.getInt(cr.getColumnIndex("androidsurum_id")));
                curCihaz.setIsyeri_id(cr.getInt(cr.getColumnIndex("isyeri_id")));
                curCihaz.setGsm_no(cr.getString(cr.getColumnIndex("gsm_no")));
                curCihaz.setEkranboyutu_id(cr.getInt(cr.getColumnIndex("ekranboyutu_id")));
                curCihaz.setImei_no(cr.getString(cr.getColumnIndex("imei_no")));
                curCihaz.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curCihaz);
            }
        }

        return sonuc;

    }

    @SuppressLint("Range")
    public ArrayList <DinamikTipler> arizaListesiVer(){
        ArrayList<DinamikTipler> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from DINAMIK_TIPLER where silindi = 0 and tipgrupid= " + SabitDegiskenler.ARIZA_TİP,null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                DinamikTipler curTip = new DinamikTipler();
                curTip.setId(cr.getInt(cr.getColumnIndex("id")));
                curTip.setTipgrup_id(cr.getInt(cr.getColumnIndex("tipgrupid")));
                curTip.setAdi(cr.getString(cr.getColumnIndex("adi")));
                curTip.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));
                sonuc.add(curTip);
            }
        }

        return sonuc;

    }

    //Raporlar için
    @SuppressLint("Range")
    public ArrayList<Isyeri>  IsyeriListesiver(){
        ArrayList<Isyeri> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from ISYERI where silindi = 0 ",null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Isyeri curIsyeri=new Isyeri();
                curIsyeri.setId(cr.getInt(cr.getColumnIndex("id")));
                curIsyeri.setIsyeri_adi(cr.getString(cr.getColumnIndex("isyeri_adi")));
                curIsyeri.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curIsyeri);
            }
        }

        return sonuc;

    }
    @SuppressLint("Range")
    public ArrayList<Cihaz>  isyeriIdyeGoreCihazListesiVer(int isyeri_id){
        ArrayList<Cihaz> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from CIHAZ where silindi = 0 and isyeri_id = "+isyeri_id,null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Cihaz curCihaz=new Cihaz();
                curCihaz.setId(cr.getInt(cr.getColumnIndex("id")));
                curCihaz.setEnvno(cr.getString(cr.getColumnIndex("envno")));
                curCihaz.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curCihaz.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curCihaz.setAndroidsurum_id(cr.getInt(cr.getColumnIndex("androidsurum_id")));
                curCihaz.setIsyeri_id(cr.getInt(cr.getColumnIndex("isyeri_id")));
                curCihaz.setGsm_no(cr.getString(cr.getColumnIndex("gsm_no")));
                curCihaz.setEkranboyutu_id(cr.getInt(cr.getColumnIndex("ekranboyutu_id")));
                curCihaz.setImei_no(cr.getString(cr.getColumnIndex("imei_no")));
                curCihaz.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curCihaz);
            }
        }

        return sonuc;

    }

    @SuppressLint("Range")
    public ArrayList<Onarim>  isyeriIdyeGoreOnarimListesiVer(int isyeri_id){
        ArrayList<Onarim> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from ONARIM where silindi = 0 and isyeri_id = "+isyeri_id,null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Onarim curOnarim=new Onarim();
                curOnarim.setId(cr.getInt(cr.getColumnIndex("id")));
                curOnarim.setCihaz_id(cr.getInt(cr.getColumnIndex("cihaz_id")));
                curOnarim.setIsyeri_id(cr.getInt(cr.getColumnIndex("isyeri_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setOnarimdurum_id(cr.getInt(cr.getColumnIndex("onarimdurum_id")));
                curOnarim.setToplam_sure(cr.getInt(cr.getColumnIndex("toplam_sure")));
                curOnarim.setToplam_maliyet(cr.getFloat(cr.getColumnIndex("toplam_maliyet")));
                curOnarim.setAriza_tipi_id(cr.getInt(cr.getColumnIndex("ariza_tipi_id")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setOnarim_bitti(cr.getInt(cr.getColumnIndex("onarim_bitti")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));


                String bastarihtext=cr.getString(cr.getColumnIndex("baslama_tarihi"));
                if(bastarihtext!=null) {
                    try {
                        curOnarim.setBaslama_tarihi(tcdd_DateFormat.parse(bastarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                String bitistarihtext=cr.getString(cr.getColumnIndex("bitis_tarihi"));
                if(bitistarihtext!=null) {
                    try {
                        curOnarim.setBitis_tarihi(tcdd_DateFormat.parse(bitistarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                sonuc.add(curOnarim);
            }
        }

        return sonuc;

    }

    @SuppressLint("Range")
    public ArrayList<Onarim>  markaIdyeGoreOnarimListesiVer(int marka_id){
        ArrayList<Onarim> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from ONARIM where silindi = 0 and marka_id = "+marka_id,null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Onarim curOnarim=new Onarim();
                curOnarim.setId(cr.getInt(cr.getColumnIndex("id")));
                curOnarim.setCihaz_id(cr.getInt(cr.getColumnIndex("cihaz_id")));
                curOnarim.setIsyeri_id(cr.getInt(cr.getColumnIndex("isyeri_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setOnarimdurum_id(cr.getInt(cr.getColumnIndex("onarimdurum_id")));
                curOnarim.setToplam_sure(cr.getInt(cr.getColumnIndex("toplam_sure")));
                curOnarim.setToplam_maliyet(cr.getFloat(cr.getColumnIndex("toplam_maliyet")));
                curOnarim.setAriza_tipi_id(cr.getInt(cr.getColumnIndex("ariza_tipi_id")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setOnarim_bitti(cr.getInt(cr.getColumnIndex("onarim_bitti")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));


                String bastarihtext=cr.getString(cr.getColumnIndex("baslama_tarihi"));
                if(bastarihtext!=null) {
                    try {
                        curOnarim.setBaslama_tarihi(tcdd_DateFormat.parse(bastarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                String bitistarihtext=cr.getString(cr.getColumnIndex("bitis_tarihi"));
                if(bitistarihtext!=null) {
                    try {
                        curOnarim.setBitis_tarihi(tcdd_DateFormat.parse(bitistarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                sonuc.add(curOnarim);
            }
        }

        return sonuc;

    }

    @SuppressLint("Range")
    public ArrayList<Onarim>  onarimListesiver(){
        ArrayList<Onarim> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from ONARIM where silindi = 0 ",null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Onarim curOnarim=new Onarim();
                curOnarim.setId(cr.getInt(cr.getColumnIndex("id")));
                curOnarim.setCihaz_id(cr.getInt(cr.getColumnIndex("cihaz_id")));
                curOnarim.setIsyeri_id(cr.getInt(cr.getColumnIndex("isyeri_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setOnarimdurum_id(cr.getInt(cr.getColumnIndex("onarimdurum_id")));
                curOnarim.setToplam_sure(cr.getInt(cr.getColumnIndex("toplam_sure")));
                curOnarim.setToplam_maliyet(cr.getFloat(cr.getColumnIndex("toplam_maliyet")));
                curOnarim.setAriza_tipi_id(cr.getInt(cr.getColumnIndex("ariza_tipi_id")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setOnarim_bitti(cr.getInt(cr.getColumnIndex("onarim_bitti")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));
                sonuc.add(curOnarim);

                String bastarihtext=cr.getString(cr.getColumnIndex("baslama_tarihi"));
                if(bastarihtext!=null) {
                    try {
                        curOnarim.setBaslama_tarihi(tcdd_DateFormat.parse(bastarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                String bitistarihtext=cr.getString(cr.getColumnIndex("bitis_tarihi"));
                if(bitistarihtext!=null) {
                    try {
                        curOnarim.setBitis_tarihi(tcdd_DateFormat.parse(bitistarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                sonuc.add(curOnarim);
            }
        }


        return sonuc;

    }

    @SuppressLint("Range")
    public ArrayList<Onarim>  bitmisOnarimListesiver(){
        ArrayList<Onarim> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from ONARIM where silindi = 0 and onarim_bitti = 1",null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Onarim curOnarim=new Onarim();
                curOnarim.setId(cr.getInt(cr.getColumnIndex("id")));
                curOnarim.setCihaz_id(cr.getInt(cr.getColumnIndex("cihaz_id")));
                curOnarim.setIsyeri_id(cr.getInt(cr.getColumnIndex("isyeri_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setOnarimdurum_id(cr.getInt(cr.getColumnIndex("onarimdurum_id")));
                curOnarim.setToplam_sure(cr.getInt(cr.getColumnIndex("toplam_sure")));
                curOnarim.setToplam_maliyet(cr.getFloat(cr.getColumnIndex("toplam_maliyet")));
                curOnarim.setAriza_tipi_id(cr.getInt(cr.getColumnIndex("ariza_tipi_id")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setOnarim_bitti(cr.getInt(cr.getColumnIndex("onarim_bitti")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));
                sonuc.add(curOnarim);

                String bastarihtext=cr.getString(cr.getColumnIndex("baslama_tarihi"));
                if(bastarihtext!=null) {
                    try {
                        curOnarim.setBaslama_tarihi(tcdd_DateFormat.parse(bastarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                String bitistarihtext=cr.getString(cr.getColumnIndex("bitis_tarihi"));
                if(bitistarihtext!=null) {
                    try {
                        curOnarim.setBitis_tarihi(tcdd_DateFormat.parse(bitistarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                sonuc.add(curOnarim);
            }
        }


        return sonuc;

    }
    @SuppressLint("Range")
    public ArrayList<Onarim>  markaModeleGoreonarimListesiver(int markaID,int modelId){
        ArrayList<Onarim> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from ONARIM where silindi = 0 and marka_id ="+markaID+" and model_id ="+modelId,null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Onarim curOnarim=new Onarim();
                curOnarim.setId(cr.getInt(cr.getColumnIndex("id")));
                curOnarim.setCihaz_id(cr.getInt(cr.getColumnIndex("cihaz_id")));
                curOnarim.setIsyeri_id(cr.getInt(cr.getColumnIndex("isyeri_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setOnarimdurum_id(cr.getInt(cr.getColumnIndex("onarimdurum_id")));
                curOnarim.setToplam_sure(cr.getInt(cr.getColumnIndex("toplam_sure")));
                curOnarim.setToplam_maliyet(cr.getFloat(cr.getColumnIndex("toplam_maliyet")));
                curOnarim.setAriza_tipi_id(cr.getInt(cr.getColumnIndex("ariza_tipi_id")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setOnarim_bitti(cr.getInt(cr.getColumnIndex("onarim_bitti")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));
                sonuc.add(curOnarim);

                String bastarihtext=cr.getString(cr.getColumnIndex("baslama_tarihi"));
                if(bastarihtext!=null) {
                    try {
                        curOnarim.setBaslama_tarihi(tcdd_DateFormat.parse(bastarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                String bitistarihtext=cr.getString(cr.getColumnIndex("bitis_tarihi"));
                if(bitistarihtext!=null) {
                    try {
                        curOnarim.setBitis_tarihi(tcdd_DateFormat.parse(bitistarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                sonuc.add(curOnarim);
            }
        }


        return sonuc;

    }
    @SuppressLint("Range")
    public ArrayList<OnarimHareket>  OnarimIdyeGoreOnrHrkListesiVer(int onarim_id){
        ArrayList<OnarimHareket> sonuc = new ArrayList<>();

        Cursor cr=db.rawQuery("Select * from ONARIMHAREKET where silindi = 0 and id = " + onarim_id,null);

        while (cr.moveToNext()){

            if (cr.getCount()>0){

                OnarimHareket curOnarimHareket= new OnarimHareket();
                curOnarimHareket.setId(cr.getInt(cr.getColumnIndex("id")));
                curOnarimHareket.setOnarim_id(cr.getInt(cr.getColumnIndex("onarim_id")));
                curOnarimHareket.setOnarim_hareket_tipi_id(cr.getInt(cr.getColumnIndex("onarim_hareket_tipi_id")));
                curOnarimHareket.setHarcananyedekparca_id(cr.getInt(cr.getColumnIndex("harcananyedekparca_id")));

                String hareket_tarihitext=cr.getString(cr.getColumnIndex("hareket_tarihi"));
                if(hareket_tarihitext!=null) {
                    try {
                        curOnarimHareket.setHareket_tarihi(tcdd_DateFormat.parse(hareket_tarihitext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                curOnarimHareket.setParcaadet(cr.getInt(cr.getColumnIndex("parcaadet")));
                curOnarimHareket.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curOnarimHareket);


            }

        }
        return sonuc;
    }


    @SuppressLint("Range")
    public ArrayList<Degerİkilisi> markaIdyegoreModelListever(int marka_id){
        ArrayList<Degerİkilisi> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from MODEL where silindi = 0 and marka_id="+marka_id,null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Degerİkilisi curMarka = new Degerİkilisi(cr.getInt((cr.getColumnIndex("id"))),
                        cr.getString(cr.getColumnIndex("model_adi")));


                sonuc.add(curMarka);
            }
        }

        return sonuc;

    }

    @SuppressLint("Range")
    public ArrayList<Marka>  markaListesiver(){
        ArrayList<Marka> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from MARKA where silindi = 0 ",null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Marka curMarka=new Marka();
                curMarka.setId(cr.getInt(cr.getColumnIndex("id")));
                curMarka.setMarka_adi(cr.getString(cr.getColumnIndex("marka_adi")));
                curMarka.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curMarka);
            }
        }

        return sonuc;

    }

    //kaydet ve ara butonu için
    public boolean envnovarmi(String envno) {

        Cursor cr = db.rawQuery("Select * from CIHAZ where silindi=0 and envno= '"+envno+"'", null);

        if(cr.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }

    //guncelle butonu için
    public boolean envnovarmi(String envno, int cihazid) {

        Cursor cr = db.rawQuery("Select * from CIHAZ where silindi = 0 and envno='" + envno + "' and id!= " +cihazid, null);

        if(cr.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }

    public boolean aynimarkavarmi(String marka_adi) {

        Cursor cr = db.rawQuery("Select * from MARKA where silindi=0 and marka_adi='"+marka_adi+"'", null);

        if(cr.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }
    public boolean aynimarkavarmi(String marka_adi,int id) {

        Cursor cr = db.rawQuery("Select * from MARKA where silindi=0 and marka_adi='"+marka_adi+"' and id!="+id, null);

        if(cr.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }

    public boolean aynimodelvarmi(String model_adi) {

        Cursor cr = db.rawQuery("Select * from MODEL where silindi=0 and model_adi='"+model_adi+"'", null);

        if(cr.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }

    public boolean aynimodelvarmi(String model_adi,int id) {

        Cursor cr = db.rawQuery("Select * from MODEL where silindi=0 and model_adi='"+model_adi+"' and id!="+id , null);

        if(cr.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }



    public boolean ayniisyerivarmi(String isyeri_adi) {

        Cursor cr = db.rawQuery("Select * from ISYERI where silindi=0 and isyeri_adi='"+isyeri_adi+"'", null);

        if(cr.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }

    public boolean ayniisyerivarmi(String isyeri_adi,int id) {

        Cursor cr = db.rawQuery("Select * from ISYERI where silindi=0 and isyeri_adi='"+isyeri_adi+"' and id!="+id, null);

        if(cr.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }

    public boolean aynitipadieditvarmi(String adi) {

        Cursor cr = db.rawQuery("Select * from DINAMIK_TIPLER where silindi=0 and adi='"+adi+"'", null);

        if(cr.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public boolean aynitipadieditvarmi(String adi,int id) {

        Cursor cr = db.rawQuery("Select * from DINAMIK_TIPLER where silindi=0 and adi='"+adi+"' and id!="+id, null);

        if(cr.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }





    public boolean cihazmarkavarmi(int marka_id) {

        Cursor cr = db.rawQuery("Select * from CIHAZ where silindi=0 and marka_id="+marka_id, null);

        if(cr.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }






    @SuppressLint("Range")
    public Marka idyegoremarkaver(int markaid){
        ArrayList<Marka> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from MARKA where silindi = 0 and id="+markaid,null);
        int id;
        String adi;

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Marka curMarka=new Marka();
                curMarka.setId(cr.getInt((cr.getColumnIndex("id"))));
                curMarka.setMarka_adi(cr.getString(cr.getColumnIndex("marka_adi")));
                curMarka.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curMarka);
            }
        }

        return sonuc.get(0);

    }
    @SuppressLint("Range")
    public Cihaz idyegoreCihazver(int cihazId){
        ArrayList<Cihaz> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from CIHAZ where silindi = 0 and id="+cihazId,null);
        int id;
        String adi;

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Cihaz curCihaz=new Cihaz();
                curCihaz.setId(cr.getInt(cr.getColumnIndex("id")));
                curCihaz.setEnvno(cr.getString(cr.getColumnIndex("envno")));
                curCihaz.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curCihaz.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curCihaz.setAndroidsurum_id(cr.getInt(cr.getColumnIndex("androidsurum_id")));
                curCihaz.setIsyeri_id(cr.getInt(cr.getColumnIndex("isyeri_id")));
                curCihaz.setGsm_no(cr.getString(cr.getColumnIndex("gsm_no")));
                curCihaz.setEkranboyutu_id(cr.getInt(cr.getColumnIndex("ekranboyutu_id")));
                curCihaz.setImei_no(cr.getString(cr.getColumnIndex("imei_no")));
                curCihaz.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curCihaz);
            }
        }

        return sonuc.get(0);

    }

    @SuppressLint("Range")
    public DinamikTipler idyegoretipver(int secilenid){
        ArrayList<DinamikTipler> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from DINAMIK_TIPLER where silindi = 0 and id="+secilenid,null);
        int id;
        String adi;

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                DinamikTipler curTip=new DinamikTipler();
                curTip.setId(cr.getInt((cr.getColumnIndex("id"))));
                curTip.setAdi(cr.getString(cr.getColumnIndex("adi")));
                curTip.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curTip);
            }
        }

        return sonuc.get(0);

    }

    @SuppressLint("Range")
    public Cihaz envgoreCihazver(String envno){
        ArrayList<Cihaz> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from CIHAZ where silindi = 0 and envno='"+envno+"'",null);

        while(cr.moveToNext()){

            if(cr.getCount()>0) {

                Cihaz curCihaz=new Cihaz();
                curCihaz.setId(cr.getInt(cr.getColumnIndex("id")));
                curCihaz.setEnvno(cr.getString(cr.getColumnIndex("envno")));
                curCihaz.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curCihaz.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curCihaz.setAndroidsurum_id(cr.getInt(cr.getColumnIndex("androidsurum_id")));
                curCihaz.setIsyeri_id(cr.getInt(cr.getColumnIndex("isyeri_id")));
                curCihaz.setGsm_no(cr.getString(cr.getColumnIndex("gsm_no")));
                curCihaz.setEkranboyutu_id(cr.getInt(cr.getColumnIndex("ekranboyutu_id")));
                curCihaz.setImei_no(cr.getString(cr.getColumnIndex("imei_no")));
                curCihaz.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curCihaz);
            }
        }

        return sonuc.get(0);

    }

    @SuppressLint("Range")
    public ArrayList<Onarim> cihazIDyegoreOnarimver(int cihazid){
        ArrayList<Onarim> sonuc =new ArrayList<>();


        Cursor cr=db.rawQuery("Select * from ONARIM where silindi = 0 and cihaz_id="+cihazid,null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Onarim curOnarim=new Onarim();
                curOnarim.setId(cr.getInt(cr.getColumnIndex("id")));
                curOnarim.setCihaz_id(cr.getInt(cr.getColumnIndex("cihaz_id")));
                curOnarim.setIsyeri_id(cr.getInt(cr.getColumnIndex("isyeri_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setOnarimdurum_id(cr.getInt(cr.getColumnIndex("onarimdurum_id")));
                curOnarim.setToplam_sure(cr.getInt(cr.getColumnIndex("toplam_sure")));
                curOnarim.setToplam_maliyet(cr.getFloat(cr.getColumnIndex("toplam_maliyet")));
                curOnarim.setAriza_tipi_id(cr.getInt(cr.getColumnIndex("ariza_tipi_id")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setOnarim_bitti(cr.getInt(cr.getColumnIndex("onarim_bitti")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));


                String bastarihtext=cr.getString(cr.getColumnIndex("baslama_tarihi"));
                if(bastarihtext!=null) {
                    try {
                        curOnarim.setBaslama_tarihi(tcdd_DateFormat.parse(bastarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                String bitistarihtext=cr.getString(cr.getColumnIndex("bitis_tarihi"));
                if(bitistarihtext!=null) {
                    try {
                        curOnarim.setBitis_tarihi(tcdd_DateFormat.parse(bitistarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                sonuc.add(curOnarim);
            }
        }

        return sonuc;

    }

    @SuppressLint("Range")
    public ArrayList<OnarimHareket> onarimIDyegoreOzetver(int onarim_id){

        ArrayList<OnarimHareket> sonuc = new ArrayList<>();

        Cursor cr=db.rawQuery("Select * from ONARIMHAREKET where silindi = 0 and onarim_id = " + onarim_id + " order by hareket_tarihi",null);

        while (cr.moveToNext()){

            if (cr.getCount()>0){

                OnarimHareket curOnarimHareket= new OnarimHareket();
                curOnarimHareket.setId(cr.getInt(cr.getColumnIndex("id")));
                curOnarimHareket.setOnarim_id(cr.getInt(cr.getColumnIndex("onarim_id")));
                curOnarimHareket.setOnarim_hareket_tipi_id(cr.getInt(cr.getColumnIndex("onarim_hareket_tipi_id")));
                curOnarimHareket.setHarcananyedekparca_id(cr.getInt(cr.getColumnIndex("harcananyedekparca_id")));

                String hareket_tarihitext=cr.getString(cr.getColumnIndex("hareket_tarihi"));
                if(hareket_tarihitext!=null) {
                    try {
                        curOnarimHareket.setHareket_tarihi(tcdd_DateFormat.parse(hareket_tarihitext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                curOnarimHareket.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));
                curOnarimHareket.setParcaadet(cr.getInt(cr.getColumnIndex("parcaadet")));

                sonuc.add(curOnarimHareket);


            }

        }
        return sonuc;
    }


    @SuppressLint("Range")
    public OnarimHareket onarimHareketIDyegoreHareketVer(int onarimHrk_id){

        ArrayList<OnarimHareket> sonuc = new ArrayList<>();

        Cursor cr=db.rawQuery("Select * from ONARIMHAREKET where silindi = 0 and id = " + onarimHrk_id,null);

        while (cr.moveToNext()){

            if (cr.getCount()>0){

                OnarimHareket curOnarimHareket= new OnarimHareket();
                curOnarimHareket.setId(cr.getInt(cr.getColumnIndex("id")));
                curOnarimHareket.setOnarim_id(cr.getInt(cr.getColumnIndex("onarim_id")));
                curOnarimHareket.setOnarim_hareket_tipi_id(cr.getInt(cr.getColumnIndex("onarim_hareket_tipi_id")));
                curOnarimHareket.setHarcananyedekparca_id(cr.getInt(cr.getColumnIndex("harcananyedekparca_id")));

                String hareket_tarihitext=cr.getString(cr.getColumnIndex("hareket_tarihi"));
                if(hareket_tarihitext!=null) {
                    try {
                        curOnarimHareket.setHareket_tarihi(tcdd_DateFormat.parse(hareket_tarihitext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                curOnarimHareket.setParcaadet(cr.getInt(cr.getColumnIndex("parcaadet")));
                curOnarimHareket.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curOnarimHareket);


            }

        }
        return sonuc.get(0);
    }


    @SuppressLint("Range")
    public Onarim idyegoreOnarimver(int onarimid) {
        ArrayList<Onarim> sonuc = new ArrayList<>();

        Cursor cr=db.rawQuery("Select * from ONARIM where silindi = 0 and id = " + onarimid,null);

        while (cr.moveToNext()){

            if (cr.getCount()>0) {

                Onarim curOnarim= new Onarim();
                curOnarim.setId(cr.getInt(cr.getColumnIndex("id")));
                curOnarim.setCihaz_id(cr.getInt(cr.getColumnIndex("cihaz_id")));
                curOnarim.setIsyeri_id(cr.getInt(cr.getColumnIndex("isyeri_id")));
                curOnarim.setOnarimdurum_id(cr.getInt(cr.getColumnIndex("onarimdurum_id")));
                curOnarim.setToplam_sure(cr.getInt(cr.getColumnIndex("toplam_sure")));
                curOnarim.setToplam_maliyet(cr.getFloat(cr.getColumnIndex("toplam_maliyet")));

                String bastarihtext=cr.getString(cr.getColumnIndex("baslama_tarihi"));
                if(bastarihtext!=null) {
                    try {
                        curOnarim.setBaslama_tarihi(tcdd_DateFormat.parse(bastarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                String bitistarihtext=cr.getString(cr.getColumnIndex("bitis_tarihi"));
                if(bitistarihtext!=null) {
                    try {
                        curOnarim.setBitis_tarihi(tcdd_DateFormat.parse(bitistarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                curOnarim.setAriza_tipi_id(cr.getInt(cr.getColumnIndex("ariza_tipi_id")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curOnarim);

            }

        }

        return sonuc.get(0);


    }

    @SuppressLint("Range")
    public ArrayList<Onarim> arizatipiIdyegoreOnarimListesiVer(int ariza_tipi_id){
        ArrayList<Onarim> sonuc =new ArrayList<>();

        Cursor cr=db.rawQuery("Select * from ONARIM where silindi = 0 and ariza_tipi_id= "+ariza_tipi_id,null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Onarim curOnarim= new Onarim();
                curOnarim.setId(cr.getInt(cr.getColumnIndex("id")));
                curOnarim.setCihaz_id(cr.getInt(cr.getColumnIndex("cihaz_id")));
                curOnarim.setIsyeri_id(cr.getInt(cr.getColumnIndex("isyeri_id")));
                curOnarim.setOnarimdurum_id(cr.getInt(cr.getColumnIndex("onarimdurum_id")));
                curOnarim.setToplam_sure(cr.getInt(cr.getColumnIndex("toplam_sure")));
                curOnarim.setToplam_maliyet(cr.getFloat(cr.getColumnIndex("toplam_maliyet")));

                String bastarihtext=cr.getString(cr.getColumnIndex("baslama_tarihi"));
                if(bastarihtext!=null) {
                    try {
                        curOnarim.setBaslama_tarihi(tcdd_DateFormat.parse(bastarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                String bitistarihtext=cr.getString(cr.getColumnIndex("bitis_tarihi"));
                if(bitistarihtext!=null) {
                    try {
                        curOnarim.setBitis_tarihi(tcdd_DateFormat.parse(bitistarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                curOnarim.setAriza_tipi_id(cr.getInt(cr.getColumnIndex("ariza_tipi_id")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));
                curOnarim.setOnarim_bitti(cr.getInt(cr.getColumnIndex("onarim_bitti")));

                sonuc.add(curOnarim);
            }
        }

        return sonuc;

    }

    @SuppressLint("Range")
    public ArrayList<Onarim> cihazIdyegoreOnarimListesiVer(int cihaz_id){
        ArrayList<Onarim> sonuc =new ArrayList<>();

        Cursor cr=db.rawQuery("Select * from ONARIM where silindi = 0 and cihaz_id= "+cihaz_id,null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Onarim curOnarim= new Onarim();
                curOnarim.setId(cr.getInt(cr.getColumnIndex("id")));
                curOnarim.setCihaz_id(cr.getInt(cr.getColumnIndex("cihaz_id")));
                curOnarim.setIsyeri_id(cr.getInt(cr.getColumnIndex("isyeri_id")));
                curOnarim.setOnarimdurum_id(cr.getInt(cr.getColumnIndex("onarimdurum_id")));
                curOnarim.setToplam_sure(cr.getInt(cr.getColumnIndex("toplam_sure")));
                curOnarim.setToplam_maliyet(cr.getFloat(cr.getColumnIndex("toplam_maliyet")));

                String bastarihtext=cr.getString(cr.getColumnIndex("baslama_tarihi"));
                if(bastarihtext!=null) {
                    try {
                        curOnarim.setBaslama_tarihi(tcdd_DateFormat.parse(bastarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                String bitistarihtext=cr.getString(cr.getColumnIndex("bitis_tarihi"));
                if(bitistarihtext!=null) {
                    try {
                        curOnarim.setBitis_tarihi(tcdd_DateFormat.parse(bitistarihtext));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                curOnarim.setAriza_tipi_id(cr.getInt(cr.getColumnIndex("ariza_tipi_id")));
                curOnarim.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curOnarim.setModel_id(cr.getInt(cr.getColumnIndex("model_id")));
                curOnarim.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));
                curOnarim.setOnarim_bitti(cr.getInt(cr.getColumnIndex("onarim_bitti")));

                sonuc.add(curOnarim);
            }
        }

        return sonuc;

    }

    @SuppressLint("Range")
    public ArrayList<Model> markaIdyegoreModelListesiVer(int marka_id){
        ArrayList<Model> sonuc =new ArrayList<>();

        Cursor cr=db.rawQuery("Select * from MODEL where silindi = 0 and marka_id= "+marka_id,null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                Model curModel = new Model();
                curModel.setId(cr.getInt(cr.getColumnIndex("id")));
                curModel.setModel_adi(cr.getString(cr.getColumnIndex("model_adi")));
                curModel.setMarka_id(cr.getInt(cr.getColumnIndex("marka_id")));
                curModel.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));
                sonuc.add(curModel);

            }
        }

        return sonuc;

    }
    @SuppressLint("Range")
    public ArrayList<YedekParca> mrkMdlTipeGoreYedekParcaListesiVer(int tipId,int markaId,int modelId){
        ArrayList<YedekParca> sonuc =new ArrayList<>();

        Cursor cr=db.rawQuery("Select * from YEDEKPARCA where yedekparcatipi_id="+tipId+" and marka_id ="+markaId+
                " and model_id="+modelId,null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                YedekParca curYedekParca = new YedekParca();
                curYedekParca.setId(cr.getInt((cr.getColumnIndex("id"))));
                curYedekParca.setYedekparcatipi_id(cr.getInt(cr.getColumnIndex("yedekparcatipi_id")));
                curYedekParca.setMarka_id(cr.getInt((cr.getColumnIndex("marka_id"))));
                curYedekParca.setModel_id(cr.getInt((cr.getColumnIndex("model_id"))));
                curYedekParca.setParca_adi(cr.getString(cr.getColumnIndex("parca_adi")));
                curYedekParca.setFiyat((float) cr.getDouble(cr.getColumnIndex("fiyat")));
                curYedekParca.setStokmin(cr.getInt(cr.getColumnIndex("stokmin")));
                curYedekParca.setStok_miktar(cr.getInt(cr.getColumnIndex("stok_miktar")));
                curYedekParca.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curYedekParca);
            }
        }

        return sonuc;

    }

    @SuppressLint("Range")
    public ArrayList<YedekParca> YedekParcaListever(){
        ArrayList<YedekParca> sonuc =new ArrayList<>();

// silindi=0 ı sildik RPR_Genelortsure için

        Cursor cr=db.rawQuery("Select * from YEDEKPARCA where silindi =0",null);

        while(cr.moveToNext()){
            if(cr.getCount()>0) {

                YedekParca curYedekParca = new YedekParca();
                curYedekParca.setId(cr.getInt((cr.getColumnIndex("id"))));
                curYedekParca.setYedekparcatipi_id(cr.getInt(cr.getColumnIndex("yedekparcatipi_id")));
                curYedekParca.setMarka_id(cr.getInt((cr.getColumnIndex("marka_id"))));
                curYedekParca.setModel_id(cr.getInt((cr.getColumnIndex("model_id"))));
                curYedekParca.setParca_adi(cr.getString(cr.getColumnIndex("parca_adi")));
                curYedekParca.setFiyat((float) cr.getDouble(cr.getColumnIndex("fiyat")));
                curYedekParca.setStokmin(cr.getInt(cr.getColumnIndex("stokmin")));
                curYedekParca.setStok_miktar(cr.getInt(cr.getColumnIndex("stok_miktar")));
                curYedekParca.setAciklama(cr.getString(cr.getColumnIndex("aciklama")));

                sonuc.add(curYedekParca);
            }
        }

        return sonuc;

    }


}
