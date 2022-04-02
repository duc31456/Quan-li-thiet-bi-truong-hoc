package com.example.quanli_thietbitruonghoc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Activity_dangky extends AppCompatActivity {
    Button btncamera, btnfolder, btndangky2;
    EditText txtsdt, txttk, txtmk, txtconfirm;
    ImageView image;
    Integer REQUEST_CAMERA = 111;
    Integer REQUEST_FOLDER = 121;
    SQL sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);


        btncamera = findViewById(R.id.btncamera);
        btnfolder = findViewById(R.id.btnfolder);
        btndangky2 = findViewById(R.id.btndangky2);
        txtsdt = findViewById(R.id.btnsdt);
        txttk = findViewById(R.id.btntk);
        txtmk = findViewById(R.id.btnmk);
        txtconfirm = findViewById(R.id.btnconfirm);
        image = findViewById(R.id.image);

        //animation
        animation();
        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
        btnfolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_FOLDER);
            }
        });

        btndangky2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,byteArrayInputStream);
                byte[] avatar = byteArrayInputStream.toByteArray();

/*                Cursor cursor = sql.select_data("select * from ADMIN");
                while (cursor.moveToNext())
                {
                    String taikhoan = cursor.getString(0);
                    Integer sdt = cursor.getInt(1);
                    String matkhau = cursor.getString(2);
                    if(taikhoan != txttk.getText().toString().trim() && matkhau != txtmk.getText().toString().trim() && txtmk.getText().toString().trim() == txtconfirm.getText().toString().trim())
                    {*/

                sql.insert_data(txttk.getText().toString().trim(), Integer.valueOf(txtsdt.getText().toString().trim()), txtmk.getText().toString().trim(), avatar);
                        final Intent data = new Intent();
                        data.putExtra("dangky", "Đăng ký thành công!");
                        setResult(RESULT_OK, data);
                        finish();
               //     }
              //  }
              //      Toast.makeText(Activity_dangky.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void animation()
    {
        Animation animation_editname = AnimationUtils.loadAnimation(Activity_dangky.this, R.anim.custom_editname);
        Animation animation_editpass = AnimationUtils.loadAnimation(Activity_dangky.this, R.anim.custom_editpass);
        Animation animation_btndangky= AnimationUtils.loadAnimation(Activity_dangky.this, R.anim.custom_btndangky);

        txtsdt.startAnimation(animation_editname);
        txttk.startAnimation(animation_editpass);
        txtmk.startAnimation(animation_editname);
        txtconfirm.startAnimation(animation_editpass);
        btndangky2.startAnimation(animation_btndangky);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && data != null)
       {
           Bitmap bitmap = (Bitmap) data.getExtras().get("data");
           image.setImageBitmap(bitmap);
       }

        if(requestCode == REQUEST_FOLDER && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}