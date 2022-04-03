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
import android.database.CursorWindow;
import java.lang.reflect.Field;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btndangnhap;
    EditText editname, editpass;
    TextView btndangky;
    Integer REQUEST_CODE_DANG_KY = 123;
    public static SQL sql;
    public static ArrayList<class_admin> admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //bộ nhớ con trỏ
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //100MB
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }

        btndangky = findViewById(R.id.btndangky);
        btndangnhap = findViewById(R.id.btndangnhap);
        editname = findViewById(R.id.editname);
        editpass = findViewById(R.id.editpass);
        btndangky.setPaintFlags(btndangky.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);//gạch chân text
        //animation
        animation();

        admin = new ArrayList<>();
        sql = new SQL(this, "Database", null, 1);

        sql.query_data("create table if not exists LOGIN(ID Integer primary key autoincrement,Taikhoan varchar(20), SDT varchar(20), MatKhau varchar(20), Avatar blob)");

       // sql.query_data("insert into LOGIN values (null, 'cun123', '12345', 'cun123', null)");
        sql.query_data("delete from LOGIN where Taikhoan = ''");
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
                Intent intent = new Intent(MainActivity.this, Activity_loaithietbi.class);
                for(int i = 0; i < admin.size(); i++) {
                    if (admin.get(i).getTaikhoan().equals(editname.getText().toString().trim()) && admin.get(i).getMatkhau().equals(editpass.getText().toString().trim()))
                    {
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }
                Toast.makeText(MainActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
        selectadmin();
    }

    public void selectadmin()
    {
        //select data
        Cursor cursor = sql.select_data("select * from LOGIN");
      //  admin.clear();
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(0);
                String taikhoan = cursor.getString(1);
                String sdt = cursor.getString(2);
                String matkhau = cursor.getString(3);
                byte[] avatar = cursor.getBlob(4);
                admin.add(new class_admin(id, taikhoan, sdt, matkhau, avatar));
            //    Toast.makeText(MainActivity.this, id +"\n"+taikhoan+"\n" + matkhau , Toast.LENGTH_SHORT).show();

            }
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

        super.onActivityResult(requestCode, resultCode, data);
    }
}