package com.example.quanli_thietbitruonghoc.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorWindow;
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

import com.example.quanli_thietbitruonghoc.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class Activity_dangky extends AppCompatActivity {
    Button btncamera, btnfolder, btndangky2;
    EditText txtsdt, txttk, txtmk, txtconfirm;
    ImageView image;
    Integer REQUEST_CAMERA = 111;
    Integer REQUEST_FOLDER = 121;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 102400 * 1024); //the 102400 is the new size added
        } catch (Exception e) {
            e.printStackTrace();
        }

        btncamera = findViewById(R.id.camera);
        btnfolder = findViewById(R.id.folder);
        btndangky2 = findViewById(R.id.nutthaydoi);
        txtsdt = findViewById(R.id.thaydoisdt);
        txttk = findViewById(R.id.thaydoitk);
        txtmk = findViewById(R.id.thaydoimk);
        txtconfirm = findViewById(R.id.xacnhanmk);
        image = findViewById(R.id.thaydoiimage);

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
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayInputStream);
                byte[] avatar = byteArrayInputStream.toByteArray();

                if(!txttk.getText().toString().trim().equals("") || !txtmk.getText().toString().trim().equals("") || !txtsdt.getText().toString().trim().equals("")) {
                    if (txtmk.getText().toString().equals(txtconfirm.getText().toString())) {
                        try {
                            MainActivity.sql.insert_data(txttk.getText().toString().trim(), txtsdt.getText().toString().trim()
                                    ,txtmk.getText().toString().trim(), avatar);
                            MainActivity.selectadmin();
                            Toast.makeText(Activity_dangky.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                        }catch (Exception e) {
                     //       Toast.makeText(Activity_dangky.this, e + "", Toast.LENGTH_LONG).show();
                            Toast.makeText(Activity_dangky.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(Activity_dangky.this, "Mật khẩu xác nhận sai!", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(Activity_dangky.this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                }
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