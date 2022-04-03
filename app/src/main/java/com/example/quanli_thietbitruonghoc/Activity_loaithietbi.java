package com.example.quanli_thietbitruonghoc;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_loaithietbi extends AppCompatActivity {

    Adapter_loaithietbi adapter;
    ArrayList<class_loaithietbi> loaithietbi;
    ListView list;
    SQL sql;
    Button btnadd1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaithietbi2);

        list = findViewById(R.id.list1);
        btnadd1 = findViewById(R.id.btnadd1);

        loaithietbi = new ArrayList<>();
        sql = new SQL(Activity_loaithietbi.this, "Database", null, 1);
        adapter = new Adapter_loaithietbi(Activity_loaithietbi.this, R.layout.layout_itemloaithietbi, loaithietbi);
        list.setAdapter(adapter);

        sql.query_data("CREATE TABLE IF NOT EXISTS LOAITHIETBI(MALOAI varchar(20) PRIMARY KEY, TENLOAI NVARCHAR(50))");
       // sql.query_data("INSERT INTO LOAITHIETBI VALUES ('DH', 'Điều hòa')");
        select_loaithietbi();

        btnadd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(Activity_loaithietbi.this);
                dialog.setContentView(R.layout.dialog_editloaithietbi);
                dialog.show();

                EditText editmaloai = dialog.findViewById(R.id.editmaloai);
                EditText edittenloai = dialog.findViewById(R.id.edittenloai);
                Button buttonedit = dialog.findViewById(R.id.buttonedit);
                Button buttonexit = dialog.findViewById(R.id.buttonexit);
                TextView title = dialog.findViewById(R.id.titleedit);

                title.setText("Thêm loại thiết bị");
                buttonedit.setText("Thêm");

                buttonedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!edittenloai.getText().toString().trim().equals("") && !editmaloai.getText().toString().trim().equals("")) {

                            try {
                                sql.query_data("INSERT INTO LOAITHIETBI VALUES ('" + editmaloai.getText().toString().trim() +
                                        "', '" + edittenloai.getText().toString().trim() + "')");
                                select_loaithietbi();
                                dialog.dismiss();
                            }catch (Exception e) {
                                Toast.makeText(Activity_loaithietbi.this, "Trùng mã loại!!\n Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(Activity_loaithietbi.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    //lấy loại thiết bị
    public void select_loaithietbi()
    {
        //select table congviec
        Cursor cursor = sql.select_data("select * from LOAITHIETBI");
        loaithietbi.clear();
        //duyệt dữ liệu khi chưa null
        while(cursor.moveToNext())
        {
            String maloai = cursor.getString(0);
            String tenloai = cursor.getString(1);
            loaithietbi.add(new class_loaithietbi(maloai, tenloai));
            adapter.notifyDataSetChanged();
            // Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
    }

    //xóa thiết bị
    public void delete_loaithietbi(String maloai)
    {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(Activity_loaithietbi.this);
        dialog.setTitle("THÔNG BÁO");
        dialog.setMessage("Bạn có muốn xóa loại thiết bị này?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sql.query_data("delete from LOAITHIETBI where MALOAI = '" + maloai + "'");
                select_loaithietbi();
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

    //chỉnh sửa loại thiết bị
    public void edit_loaithietbi(String maloai, String tenloai)
    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_editloaithietbi);
        dialog.show();

        EditText editmaloai = dialog.findViewById(R.id.editmaloai);
        EditText edittenloai = dialog.findViewById(R.id.edittenloai);
        Button buttonedit = dialog.findViewById(R.id.buttonedit);
        Button buttonexit = dialog.findViewById(R.id.buttonexit);
        TextView title = dialog.findViewById(R.id.titleedit);

        editmaloai.setEnabled(false);
        title.setText("Chỉnh sửa");
        buttonedit.setText("Sửa");

        editmaloai.setText(maloai);
        edittenloai.setText(tenloai);

        buttonedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!edittenloai.getText().toString().trim().equals("")) {
                    sql.query_data("UPDATE LOAITHIETBI SET TENLOAI = '" + edittenloai.getText().toString() + "' WHERE MALOAI = '" + editmaloai.getText().toString() + "'");
                    select_loaithietbi();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(Activity_loaithietbi.this, "Không được để trống", Toast.LENGTH_SHORT).show();
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

    //chuyển sang thiết bị
    public void nexttothietbi(String maloai, String tenloai)
    {
        Intent intent = new Intent(this, Activity_thietbi.class);
        overridePendingTransition(R.anim.custom_intentloaithietbi, R.anim.custom_intentthietbi);

        Bundle bundle = new Bundle();
        bundle.putString("ma_loai", maloai);
        bundle.putString("ten_loai", tenloai);
        intent.putExtras(bundle);
        startActivity(intent);
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
                    Intent intent = new Intent(Activity_loaithietbi.this, MainActivity.class);
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
            
        }

        return super.onOptionsItemSelected(item);
    }
}