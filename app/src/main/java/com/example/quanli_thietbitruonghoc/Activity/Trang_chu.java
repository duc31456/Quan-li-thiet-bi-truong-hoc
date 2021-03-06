package com.example.quanli_thietbitruonghoc.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanli_thietbitruonghoc.R;

public class Trang_chu extends AppCompatActivity {

    ImageView btnthongtin, btnthietbi, btnphonghoc, btnmuon_tra, btnbaoloitb, btnthongke;
    TextView btngopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        btnthongtin = findViewById(R.id.btn1);
        btnthietbi = findViewById(R.id.btn2);
        btnphonghoc = findViewById(R.id.btn3);
        btnmuon_tra = findViewById(R.id.btn4);
        btnbaoloitb = findViewById(R.id.btn5);
        btnthongke = findViewById(R.id.btn6);
        btngopy = findViewById(R.id.btn7);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btngopy.setPaintFlags(btngopy.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        btnthongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Trang_chu.this, Thongtincanhan.class);
                startActivity(intent);
            }
        });

        btnthietbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Trang_chu.this, Activity_loaithietbi.class);
                startActivity(intent);
            }
        });

        btnphonghoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Trang_chu.this, Activity_phonghoc.class);
                startActivity(intent);
            }
        });

        btnmuon_tra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Trang_chu.this, Activity_chitietsudung.class);
                startActivity(intent);
            }
        });

        btngopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Trang_chu.this, "H??y g??p ?? cho ???ng d???ng c???a ch??ng t??i ????? c???i thi???n t???t h??n^^\n C???m ??n b???n!", Toast.LENGTH_SHORT).show();
                sendEmail();
            }
        });

        btnbaoloitb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Trang_chu.this, Activity_baoloithietbi.class);
                startActivity(intent);
            }
        });

        btnthongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Trang_chu.this, Thongke.class);
                startActivity(intent);
            }
        });
    }


    private void sendEmail() {
        String[] TO = {"ngodung972000@gmail.com"};
        //String[] CC = {};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
      //  emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ti??u ?????");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "??i???n n???i dung c???a b???n v??o ????y");

        try {
            startActivity(Intent.createChooser(emailIntent, "G???i mail..."));
            return;
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Trang_chu.this,
                    "L???i mail!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
    }
}