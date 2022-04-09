package com.example.quanli_thietbitruonghoc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanli_thietbitruonghoc.Adapter.Adapter_phonghoc;
import com.example.quanli_thietbitruonghoc.R;
import com.example.quanli_thietbitruonghoc.SQL;
import com.example.quanli_thietbitruonghoc.Class.class_phonghoc;

import java.util.ArrayList;

public class Activity_phonghoc extends AppCompatActivity {

    ListView listphonghoc;
    ArrayList<class_phonghoc> phonghoc;
    Adapter_phonghoc adapter;

    public static SQL sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonghoc);

        listphonghoc = findViewById(R.id.list2);

        phonghoc = new ArrayList<>();

        sql = new SQL(Activity_phonghoc.this, "Database", null, 1);
        adapter = new Adapter_phonghoc(Activity_phonghoc.this, R.layout.layout_itemphonghoc, phonghoc);
        listphonghoc.setAdapter(adapter);

        sql.query_data("CREATE TABLE IF NOT EXISTS PHONGHOC(MAPHONG varchar(20) PRIMARY KEY, TENPHONG NVARCHAR(50), SOTANG Integer)");

    //    sql.query_data("INSERT INTO PHONGHOC VALUES ('LT01', 'Phòng lý thuyết 1', 2)");
        select_phonghoc();
    }

    public void select_phonghoc()
    {
       Cursor cursor = sql.select_data("select * from PHONGHOC");
        phonghoc.clear();
        //duyệt dữ liệu khi chưa null
        while(cursor.moveToNext())
        {
            String maphong = cursor.getString(0);
            String tenphong = cursor.getString(1);
            Integer sotang = cursor.getInt(2);
            phonghoc.add(new class_phonghoc(maphong, tenphong, sotang));
            adapter.notifyDataSetChanged();

        }
    }

    //xóa phòng học
    public void delete_phonghoc(String maphong)
    {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(Activity_phonghoc.this);
        dialog.setTitle("THÔNG BÁO");
        dialog.setMessage("Bạn có muốn xóa loại thiết bị này?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sql.query_data("delete from PHONGHOC where MAPHONG = '" + maphong + "'");
                select_phonghoc();
            }
        });
        dialog.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        dialog.show();
    }

    //chỉnh sửa phonghoc
    public void edit_phonghoc(String maphong, String tenphong, Integer sotang)
    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_phonghoc);
        dialog.show();

        EditText editmaphong = dialog.findViewById(R.id.editmaphong);
        EditText edittenphong = dialog.findViewById(R.id.edittenphong);
        EditText editsotang = dialog.findViewById(R.id.editsotang);
        Button buttonedit = dialog.findViewById(R.id.buttonedit3);
        Button buttonexit = dialog.findViewById(R.id.buttonexit3);
        TextView title = dialog.findViewById(R.id.titleedit3);

        editmaphong.setEnabled(false);
        title.setText("Chỉnh sửa");
        buttonedit.setText("Sửa");

        editmaphong.setText(maphong);
        edittenphong.setText(tenphong);
        editsotang.setText(String.valueOf(sotang));

        buttonedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!edittenphong.getText().toString().trim().equals("") ||
                        !editsotang.getText().toString().trim().equals("")) {
                    sql.query_data("UPDATE PHONGHOC SET TENPHONG = '" + edittenphong.getText().toString().trim() +
                            "', SOTANG = "+ Integer.parseInt(editsotang.getText().toString().trim())+" WHERE MAPHONG = '" +
                            editmaphong.getText().toString() + "'");
                    select_phonghoc();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(Activity_phonghoc.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.thongtin_canhan, menu);
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
                    Intent intent = new Intent(Activity_phonghoc.this, MainActivity.class);
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
            Intent intent = new Intent(Activity_phonghoc.this, Thongtincanhan.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.btnadd)
        {
            Dialog dialog = new Dialog(Activity_phonghoc.this);
            dialog.setContentView(R.layout.dialog_phonghoc);
            dialog.show();

            EditText editmaphong = dialog.findViewById(R.id.editmaphong);
            EditText edittenphong = dialog.findViewById(R.id.edittenphong);
            EditText editsotang = dialog.findViewById(R.id.editsotang);
            Button buttonedit = dialog.findViewById(R.id.buttonedit3);
            Button buttonexit = dialog.findViewById(R.id.buttonexit3);
            TextView title = dialog.findViewById(R.id.titleedit3);

            title.setText("Thêm phòng học");
            buttonedit.setText("Thêm");

            buttonedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!edittenphong.getText().toString().trim().equals("") ||
                            !editmaphong.getText().toString().trim().equals("") ||
                            !editsotang.getText().toString().trim().equals("")) {

                        try {
                            sql.query_data("INSERT INTO PHONGHOC VALUES ('" + editmaphong.getText().toString().trim() +
                                    "', '" + edittenphong.getText().toString().trim() +
                                    "', '" + Integer.parseInt(editsotang.getText().toString().trim()) + "')");
                            select_phonghoc();
                            dialog.dismiss();
                        }catch (Exception e) {
                            Toast.makeText(Activity_phonghoc.this, "Trùng mã phòng!!\n Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(Activity_phonghoc.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            buttonexit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


        }

        return super.onOptionsItemSelected(item);
    }
}