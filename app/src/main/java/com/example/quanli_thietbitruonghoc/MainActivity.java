package com.example.quanli_thietbitruonghoc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btndangnhap;
    EditText editname, editpass;
    TextView btndangky;
    Integer REQUEST_CODE_DANG_KY = 123;
    public static SQL sql;
    ArrayList<class_admin> admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btndangky = findViewById(R.id.btndangky);
        btndangnhap = findViewById(R.id.btndangnhap);
        editname = findViewById(R.id.editname);
        editpass = findViewById(R.id.editpass);
        btndangky.setPaintFlags(btndangky.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);//gạch chân text
        //animation
        animation();

        admin = new ArrayList<>();
        sql = new SQL(this, "dbQuanLi", null, 1);

        sql.query_data("create table if not exists ADMIN(taikhoan varchar(20) primary key, sdt Integer, matkhau varchar(20), avatar blob)");

        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity_dangky.class);
                startActivityForResult(intent, REQUEST_CODE_DANG_KY);

            }
        });

        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectadmin();
                for(int i = 0; i < admin.size(); i++)
                {
                    if(admin.get(i).getTaikhoan() == editname.getText().toString().trim() && admin.get(i).getMatkhau() == editpass.getText().toString().trim())
                    {
                        Intent intent = new Intent(MainActivity.this, Activity_loaithietbi.class);
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }
                    Toast.makeText(MainActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void selectadmin()
    {
        //select data
        Cursor cursor = sql.select_data("select * from ADMIN");
        admin.clear();
        while (cursor.moveToNext())
        {
            String taikhoan = cursor.getString(0);
            Integer sdt = cursor.getInt(1);
            String matkhau = cursor.getString(2);
            byte[] avatar = cursor.getBlob(3);
            admin.add(new class_admin(taikhoan, sdt, matkhau, avatar));
        }

    }

    public void animation()
    {
        Animation animation_editname = AnimationUtils.loadAnimation(MainActivity.this, R.anim.custom_editname);
        Animation animation_editpass = AnimationUtils.loadAnimation(MainActivity.this, R.anim.custom_editpass);
        Animation animation_btndangnhap = AnimationUtils.loadAnimation(MainActivity.this, R.anim.custom_btndangnhap);

        editname.startAnimation(animation_editname);
        editpass.startAnimation(animation_editpass);
        btndangnhap.startAnimation(animation_btndangnhap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_DANG_KY && resultCode == RESULT_OK && data != null)
        {
            Bundle bundle = data.getExtras();
            Toast.makeText(this, bundle.getString("dangky"), Toast.LENGTH_SHORT).show();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}