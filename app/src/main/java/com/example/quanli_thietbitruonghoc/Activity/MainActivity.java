package com.example.quanli_thietbitruonghoc.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.CursorWindow;

import com.example.quanli_thietbitruonghoc.BuildConfig;
import com.example.quanli_thietbitruonghoc.R;
import com.example.quanli_thietbitruonghoc.SQL;
import com.example.quanli_thietbitruonghoc.Class.class_admin;

import java.io.Serializable;
import java.lang.reflect.Field;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btndangnhap;
    EditText editname, editpass;
    TextView btndangky;
    CheckBox cb;
    Integer REQUEST_CODE_DANG_KY = 123;
    public static SQL sql;
    public static ArrayList<class_admin> admin;

    public static String username = "";
    public static String password = "";
    public static String sdt ="";
    public static byte[] hinhanh;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        //bộ nhớ con trỏ từ 2MB lên 100MB
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
        cb= findViewById(R.id.cb);
        btndangky.setPaintFlags(btndangky.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);//gạch chân text
        //animation
        animation();

        admin = new ArrayList<>();
        sql = new SQL(this, "Database", null, 1);

        sql.query_data("create table if not exists LOGIN_ADMIN(ID INTEGER, TAIKHOAN varchar(20) UNIQUE NOT NULL, SDT varchar(20), " +
                "MATKHAU varchar(20), AVATAR blob, PRIMARY KEY (TAIKHOAN, ID))");

       // sql.query_data("insert into LOGIN_ADMIN values ('cun123', '12345', 'cun123', null)");
       // sql.query_data("delete from LOGIN_ADMIN where TAIKHOAN= 'cun123'");
        //sql.query_data("drop table LOGIN_ADMIN");
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity_dangky.class);
                startActivityForResult(intent, REQUEST_CODE_DANG_KY);
            }
        });

        luudangnhap();
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Trang_chu.class);
               // selectadmin();
                for(int i = 0; i < admin.size(); i++) {
                    if (admin.get(i).getTaikhoan().equals(editname.getText().toString().trim()) && admin.get(i).getMatkhau().equals(editpass.getText().toString().trim()))
                    {
                        username = editname.getText().toString().trim();
                        password = editpass.getText().toString().trim();
                        sdt = admin.get(i).getSdt();
                        hinhanh = admin.get(i).getAvatar();
                        if(cb.isChecked())
                        {
                            //truyền giá trị vào share để lưu đăng nhập
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("taikhoan", username);
                            editor.putString("matkhau", password);
                            editor.putBoolean("luudangnhap", true);
                            editor.commit();
                        }
                        else
                        {
                            //loại bỏ lưu giá trị khi hủy lưu đăng nhập
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("matkhau");
                            editor.remove("luudangnhap");
                            editor.commit();
                        }
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        return;
                    }
                }
                Toast.makeText(MainActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
        selectadmin();
    }

    public static void selectadmin()
    {
        //select data
        Cursor cursor = sql.select_data("select * from LOGIN_ADMIN");
        admin.clear();
            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(0);
                String taikhoan = cursor.getString(1);
                String sdt = cursor.getString(2);
                String matkhau = cursor.getString(3);
                byte[] avatar = cursor.getBlob(4);
                admin.add(new class_admin(id,taikhoan, sdt, matkhau, avatar));
            //   Toast.makeText(MainActivity.this, taikhoan +"\n"+ matkhau , Toast.LENGTH_SHORT).show();
            }
    }

    public void luudangnhap()
    {
        sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);

        editname.setText(sharedPreferences.getString("taikhoan", ""));
        editpass.setText(sharedPreferences.getString("matkhau", ""));
        cb.setChecked(sharedPreferences.getBoolean("luudangnhap", false));
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