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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_thietbi extends AppCompatActivity {
    ListView list1;
    TextView titlethietbi;
    ArrayList<class_thietbi> thietbi;
    Adapter_thietbi adapter;

    String maloai;
    SQL sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thietbi);

        overridePendingTransition(R.anim.custom_intentloaithietbi, R.anim.custom_intentthietbi);


        list1 = findViewById(R.id.list1);
        titlethietbi = findViewById(R.id.titlethietbi);

            Bundle bundle = getIntent().getExtras();
           maloai = bundle.getString("ma_loai");
           titlethietbi.append("\n"+bundle.getString("ten_loai"));


        thietbi = new ArrayList<>();
        sql = new SQL(Activity_thietbi.this, "Database", null, 1);
        adapter = new Adapter_thietbi(Activity_thietbi.this, R.layout.layout_itemthietbi, thietbi);
        list1.setAdapter(adapter);

        sql.query_data("CREATE TABLE IF NOT EXISTS THIETBI(MATB varchar(20) PRIMARY KEY, TENTB NVARCHAR(50), XUATXU nvarchar(20), MALOAI VARCHAR(20))");
         //sql.query_data("INSERT INTO THIETBI VALUES ('CS01', 'Đèn điện quang 1.2M', 'Việt Nam', 'CS')");
       // sql.query_data("INSERT INTO THIETBI VALUES ('CS03', 'Đèn điện quang 0.6M', 'Việt Nam', 'CS')");
        select_thietbi();
    }

    //lấy loại thiết bị
    public void select_thietbi()
    {
        //select table congviec
        Cursor cursor = sql.select_data("select * from THIETBI where MALOAI = '"+maloai+"'");
        thietbi.clear();
        //duyệt dữ liệu khi chưa null
        while(cursor.moveToNext())
        {
            String matb = cursor.getString(0);
            String tentb = cursor.getString(1);
            String xuatxu = cursor.getString(2);
            thietbi.add(new class_thietbi(matb, tentb, xuatxu, maloai));
            adapter.notifyDataSetChanged();
            // Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
    }

    //xóa thiết bị
    public void delete_thietbi(String matb)
    {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(Activity_thietbi.this);
        dialog.setTitle("THÔNG BÁO");
        dialog.setMessage("Bạn có muốn xóa thiết bị này?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sql.query_data("delete from THIETBI where MATB = '" + matb + "'");
                select_thietbi();
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

    //chỉnh sửa thiết bị
   /* public void edit_thietbi(String matb, String tentb, String xuatxu)
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
*/

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
                    Intent intent = new Intent(Activity_thietbi.this, MainActivity.class);
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
