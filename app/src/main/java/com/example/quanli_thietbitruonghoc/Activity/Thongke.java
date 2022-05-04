package com.example.quanli_thietbitruonghoc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWindow;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanli_thietbitruonghoc.Adapter.Adapter_thongke;
import com.example.quanli_thietbitruonghoc.Class.class_baoloithietbi;
import com.example.quanli_thietbitruonghoc.Class.class_thongke;
import com.example.quanli_thietbitruonghoc.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Thongke extends AppCompatActivity {
    Adapter_thongke adapter;
    ArrayList<class_thongke> thongke;
    ListView list;
    //load spinner
    Spinner spinnerthongke;
    ArrayAdapter<String> spinner_ngaymuon;
    ArrayList<String> array_ngaymuon;

    Button btninthongtin;

    String tempngaymuon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);

        list = findViewById(R.id.listthongke);
        spinnerthongke = findViewById(R.id.spinnerthongke);
        btninthongtin = findViewById(R.id.btninthongtin);
        thongke = new ArrayList<>();

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 102400 * 1024); //the 102400 is the new size added
        } catch (Exception e) {
            e.printStackTrace();
        }

        //load spinner
        load_spinner();

        spinnerthongke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempngaymuon = array_ngaymuon.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btninthongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load_list();
                adapter = new Adapter_thongke(Thongke.this, R.layout.layout_itemthongke, thongke);
                list.setAdapter(adapter);
                Toast.makeText(Thongke.this, "In thông tin thành công!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void load_spinner()
    {
        array_ngaymuon = new ArrayList<>();
        array_ngaymuon.clear();
        try {
            Cursor cursor = Activity_chitietsudung.sql.select_data("select DISTINCT NGAYMUON from MUONTRA");

            while(cursor.moveToNext()) {
                String ngaymuon = cursor.getString(0);
                array_ngaymuon.add(ngaymuon);
            }
            cursor.close();
        }catch (Exception e)
        {}
        spinner_ngaymuon = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, array_ngaymuon);
        spinnerthongke.setAdapter(spinner_ngaymuon);


    }

    public void load_list()
    {

        thongke.clear();
        try {
            Cursor cursor = Activity_chitietsudung.sql.select_data("select MATB, SOLUONG, NGAYTRA from MUONTRA where NGAYMUON = '"+tempngaymuon+"'");
            while(cursor.moveToNext())
            {
                String matb = cursor.getString(0);
                String tentb = "";
                Cursor cursortentb = Activity_thietbi.sql.select_data("select TENTB from THIETBI where MATB = '"+matb+"'");
                while(cursortentb.moveToNext())
                {
                    tentb = cursortentb.getString(0);
                }
                String ngaytra = cursor.getString(2);
                Integer soluong = cursor.getInt(1);
                thongke.add(new class_thongke(matb, tentb, tempngaymuon, soluong, ngaytra));
            }
            cursor.close();
        }
        catch (Exception e)
        { }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.thongtin_canhan, menu);
        //ẩn item menu
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.btnadd).setVisible(false);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btndangxuat)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Thông báo");
            alert.setMessage("Bạn có muốn đăng xuất không?");
            alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Thongke.this, MainActivity.class);
                    startActivity(intent);
                    dialogInterface.dismiss();
                }
            });
            alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alert.show();
        }
        if (item.getItemId() == R.id.btnthongtin)
        {
            Intent intent = new Intent(Thongke.this, Thongtincanhan.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}