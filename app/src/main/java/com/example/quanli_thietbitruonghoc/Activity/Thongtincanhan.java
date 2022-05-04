package com.example.quanli_thietbitruonghoc.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanli_thietbitruonghoc.BuildConfig;
import com.example.quanli_thietbitruonghoc.Class.class_admin;
import com.example.quanli_thietbitruonghoc.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class Thongtincanhan extends AppCompatActivity {
    ImageView image, thaydoiimage;
    TextView txtsdt, txttk, txtmk;
    Button btnedit, btnexit, nutthaydoi, camera, folder;
    EditText thaydoisdt, thaydoitk, thaydoimk, xacnhanmk;

    Integer CAMERA = 4321;
    Integer FOLDER = 1234;
    String tempsdt, temptk, tempmk;
    Bitmap bitmapimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtincanhan);

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 102400 * 1024); //the 102400 is the new size added
        } catch (Exception e) {
            e.printStackTrace();
        }
        image = findViewById(R.id.thaydoiimage);
        txtsdt = findViewById(R.id.sdt);
        txttk = findViewById(R.id.tk);
        txtmk = findViewById(R.id.mk);
        btnedit = findViewById(R.id.thaydoi);
        btnexit = findViewById(R.id.thoat);

        try {
            bitmapimage = BitmapFactory.decodeByteArray(MainActivity.hinhanh, 0, MainActivity.hinhanh.length);
            image.setImageBitmap(bitmapimage);
        }catch (Exception e)
        {
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
        txtsdt.setText(MainActivity.sdt);
        txttk.setText(MainActivity.username);
        txtmk.setText(MainActivity.password);

        tempsdt = txtsdt.getText().toString();
        temptk = txttk.getText().toString();
        tempmk = txtmk.getText().toString();

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(Thongtincanhan.this);
                dialog.setContentView(R.layout.dialog_thaydoimatkhau);
                dialog.show();

                camera = dialog.findViewById(R.id.camera);
                folder = dialog.findViewById(R.id.folder);
                nutthaydoi = dialog.findViewById(R.id.nutthaydoi);
                thaydoisdt = dialog.findViewById(R.id.thaydoisdt);
                thaydoitk = dialog.findViewById(R.id.thaydoitk);
                thaydoimk = dialog.findViewById(R.id.thaydoimk);
                xacnhanmk = dialog.findViewById(R.id.xacnhanmk);
                thaydoiimage = dialog.findViewById(R.id.thaydoiimage);

               thaydoisdt.setText(tempsdt);
                thaydoitk.setText(temptk);
                thaydoitk.setEnabled(false);
                thaydoimk.setText(tempmk);
                xacnhanmk.setText(tempmk);

                try {
                    bitmapimage = BitmapFactory.decodeByteArray(MainActivity.hinhanh, 0, MainActivity.hinhanh.length);
                    thaydoiimage.setImageBitmap(bitmapimage);
                }catch (Exception e)
                {  Toast.makeText(dialog.getContext(), ""+e, Toast.LENGTH_SHORT).show();}
                nutthaydoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) thaydoiimage.getDrawable();
                            Bitmap bitmap_avatar = bitmapDrawable.getBitmap();
                            ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
                            bitmap_avatar.compress(Bitmap.CompressFormat.PNG, 100, byteArrayInputStream);
                            byte[] avatar_convert = byteArrayInputStream.toByteArray();
                        if (!thaydoisdt.getText().toString().trim().equals("") || !thaydoimk.getText().toString().trim().equals("")) {
                            if (thaydoimk.getText().toString().equals(xacnhanmk.getText().toString())) {
                                try {
                                    MainActivity.sql.update_data(thaydoisdt.getText().toString(), thaydoimk.getText().toString().trim(),
                                            avatar_convert, thaydoitk.getText().toString().trim());
                                    Toast.makeText(dialog.getContext(), "Thay đổi thành công!", Toast.LENGTH_SHORT).show();
                                    txtsdt.setText(thaydoisdt.getText().toString());
                                    txtmk.setText(thaydoimk.getText().toString());
                                    try {
                                        Bitmap tempbitmap = BitmapFactory.decodeByteArray(avatar_convert, 0, avatar_convert.length);
                                        image.setImageBitmap(tempbitmap);
                                        MainActivity.hinhanh = avatar_convert;
                                    }catch (Exception e)
                                    { }
                                    dialog.dismiss();
                                }catch (Exception e) {
                                    Toast.makeText(dialog.getContext(), "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(dialog.getContext(), "Mật khẩu xác nhận sai!", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(dialog.getContext(), "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA);
                    }
                });
                folder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, FOLDER);
                    }
                });

            }
        });
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CAMERA && resultCode == RESULT_OK && data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            thaydoiimage.setImageBitmap(bitmap);
        }

        if(requestCode == FOLDER && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                thaydoiimage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.thongtin_canhan, menu);
        //ẩn item menu
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.btnadd).setVisible(false);
        menu.findItem(R.id.btnthongtin).setVisible(false);
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
                    Intent intent = new Intent(Thongtincanhan.this, MainActivity.class);
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

        return super.onOptionsItemSelected(item);
    }

}