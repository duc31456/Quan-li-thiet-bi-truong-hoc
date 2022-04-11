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
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanli_thietbitruonghoc.R;

public class Trang_chu extends AppCompatActivity {

    Button btnthongtin, btnthietbi, btnphonghoc, btnmuon_tra, btnbaoloitb, btngopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        btnthongtin = findViewById(R.id.btn1);
        btnthietbi = findViewById(R.id.btn2);
        btnphonghoc = findViewById(R.id.btn3);
        btnmuon_tra = findViewById(R.id.btn4);
        btnbaoloitb = findViewById(R.id.btn5);
        btngopy = findViewById(R.id.btn6);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        //animation
        animation();

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
                Toast.makeText(Trang_chu.this, "Hãy góp ý cho ứng dụng của chúng tôi để cải thiện tốt hơn^^\n Cảm ơn bạn!", Toast.LENGTH_SHORT).show();
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
    }

    public void animation()
    {
        Animation animation_thongtin = AnimationUtils.loadAnimation(this, R.anim.custom_trangchuleft);
        Animation animation_phonghoc = AnimationUtils.loadAnimation(this, R.anim.custom_trangchuleft);
        Animation animation_baoloitb = AnimationUtils.loadAnimation(this, R.anim.custom_trangchuleft);
        Animation animation_thietbi = AnimationUtils.loadAnimation(this, R.anim.custom_trangchuright);
        Animation animation_muontra = AnimationUtils.loadAnimation(this, R.anim.custom_trangchuright);
        Animation animation_gopy = AnimationUtils.loadAnimation(this, R.anim.custom_trangchuright);

        btnthongtin.startAnimation(animation_thongtin);
        btnphonghoc.startAnimation(animation_phonghoc);
        btnthietbi.startAnimation(animation_thietbi);
        btnmuon_tra.startAnimation(animation_muontra);
        btnbaoloitb.startAnimation(animation_baoloitb);
        btngopy.startAnimation(animation_gopy);
    }

    private void sendEmail() {
        String[] TO = {"ngodung972000@gmail.com"};
        //String[] CC = {};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
      //  emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tiêu đề");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Điền nội dung của bạn vào đây");

        try {
            startActivity(Intent.createChooser(emailIntent, "Gửi mail..."));
            return;
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Trang_chu.this,
                    "Lỗi mail!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
    }
}