package com.hllbr.sqliteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.sql.SQLData;

public class MainActivity extends AppCompatActivity {
/*
SQLİTE SQL'in daha basit bir versionu
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Veritabanı oluşturuyorum fakat tüm işlemlerimi try-catch içerisinde yapıyorum
        try{
            //database ile çalışırken android/java/kotlin vb kodlarından çıkıyoruz.SQL kod ile yazıyoruz bunlar String gibi yazıldığı için bunun farkedilememe ihtimali var
            //genellikle veritabanlarıyla çalışırken try-catch ile işlemlerimizi yapmamız daha uygun oluyor.
            //veritabanını oluştururken içinde bulunduğumuz contextten oluşturuyoruz
            SQLiteDatabase database = this.openOrCreateDatabase("Musicians",MODE_PRIVATE,null);
            //veritabanım oluştu
            database.execSQL("CREATE TABLE IF NOT EXISTS musicians(id INTEGER PRIMARY KEY,name VARCHAR,age INT)");
            //bir tablo oluşturdum içerisinde hangi verilerin yer alması gerektiğini ifade ettim
            //SQLite verileri herkesin kendi kişisel telefonunda saklanıyor.

            //Veri Eklemek

           // database.execSQL("INSERT INTO musicians (name,age) VALUES('JAYCE',18)");//INSERT INTO birşeyin içerisine yerleştir oalrak ifade edebiliriz.
           // database.execSQL("INSERT INTO musicians(name,age) VALUES('LARA',22)");
           // database.execSQL("INSERT INTO musicians(name,age) VALUES ('BAR',2)");



            //VERİ GÜNCELLEMEK = Güncelleme yaparken UPDATE kullanıyoruz .
            database.execSQL("UPDATE musicians SET age = 59 WHERE name = 'LARS'");
            database.execSQL("UPDATE musicians SET name ='Kirk Hammet' WHERE id = 1");

            //VERİ SİLMEK = DELETE KULLANILARAK GERÇEKLEŞTİRDİĞİMİZ VERİ TEMİZLEME İŞLEMİ
            //database silmemek için dikkatli olmak gerekiyor.
            database.execSQL("DELETE FROM musicians WHERE id = 2");






            //Veri Okuma = bu işlem için cursor(imleç) gibi bir ifadeye ihtiyacımız var.Cursor hücrelerin içersinde tek tek gezip verilerimizi okuyor.ve bildiriyor.

           Cursor cursor = database.rawQuery("SELECT * FROM musicians",null);
            //Cursor cursor = database.rawQuery("SELECT * FROM musicians WHERE id = 2",null);//Filtreleme işlemi
            //yapılmış olan filtreleme işleminde id'si 2 olan ifadeyi getir başka hiçbir ifadey getirme şeklinde bir istekte bulunduk
            //Cursor cursor = database.rawQuery("SELECT * FROM musicians WHERE age >25",null);
            //AGE ifadesi 25 ten byükü olanların listelenmesini istedim.
           // Cursor cursor = database.rawQuery("SELECT * FROM musicians WHERE name = 'LARA'",null);
            //name ifadesine karşılık LARA gelen ifadeleri getirmesini istedim.
            //rawQuery = bir sorgu yapmak bir veri tabanından sorgulama yapmak demek sadece sqlite için değil diğer veri tabanlarındada kullanılan bir yöntem
            //Cursor cursor = database.rawQuery("SELECT * FROM musicians WHERE name LIKE '%s'",null);//%s son ifadesi s olan değerleri istediğimiz ifade etmiş oluruz
            //Cursor cursor = database.rawQuery("SELECT * FROM musicians WHERE name LIKE 'K%'",null);//Başında K olan ifadeleri istediğimi belirtiyorum
            int nameIx  = cursor.getColumnIndex("name");
            int ageIx = cursor.getColumnIndex("age");
            int ıdIx = cursor.getColumnIndex("id");

            while(cursor.moveToNext()){
                //cursor.moveToNext ilerleyebildiği kadar gitsin.
                System.out.println("name "+cursor.getString(nameIx));
                System.out.println("age "+cursor.getInt(ageIx));
                System.out.println("id "+cursor.getInt(ıdIx));
            }
            cursor.close();//cursor yani imleç kapatıldı tüm işlemlerimi bitirdiğim için.

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}