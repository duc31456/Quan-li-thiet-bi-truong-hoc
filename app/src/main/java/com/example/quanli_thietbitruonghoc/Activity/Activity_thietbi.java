package com.example.quanli_thietbitruonghoc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWindow;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanli_thietbitruonghoc.Adapter.Adapter_thietbi;
import com.example.quanli_thietbitruonghoc.R;
import com.example.quanli_thietbitruonghoc.SQL;
import com.example.quanli_thietbitruonghoc.Class.class_thietbi;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Activity_thietbi extends AppCompatActivity {
    ListView list1;
    TextView titlethietbi;

    ArrayList<class_thietbi> thietbi;
    Adapter_thietbi adapter;

    String maloai;
    public static SQL sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thietbi);
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 102400 * 1024); //the 102400 is the new size added
        } catch (Exception e) {
            e.printStackTrace();
        }


        list1 = findViewById(R.id.listtinhtrang);
        titlethietbi = findViewById(R.id.titlethietbi);

         Bundle bundle = getIntent().getExtras();
           maloai = bundle.getString("ma_loaitb");
           titlethietbi.append("\n"+bundle.getString("ten_loaitb"));


        thietbi = new ArrayList<>();

        sql = new SQL(Activity_thietbi.this, "Database", null, 1);
        adapter = new Adapter_thietbi(Activity_thietbi.this, R.layout.layout_itemthietbi, thietbi);

        list1.setAdapter(adapter);

        sql.query_data("CREATE TABLE IF NOT EXISTS THIETBI(MATB varchar(20) PRIMARY KEY, TENTB NVARCHAR(50), XUATXU nvarchar(20), MALOAI VARCHAR(20))");
         //sql.query_data("INSERT INTO THIETBI VALUES ('CS01', '????n ??i???n quang 1.2M', 'Vi???t Nam', 'CS')");
       // sql.query_data("INSERT INTO THIETBI VALUES ('CS03', '????n ??i???n quang 0.6M', 'Vi???t Nam', 'CS')");
        select_thietbi();


    }

    //l???y lo???i thi???t b???
    public void select_thietbi()
    {
        //select table congviec
        Cursor cursor = sql.select_data("select * from THIETBI where MALOAI = '"+maloai+"'");
        thietbi.clear();
        //duy???t d??? li???u khi ch??a null
        while(cursor.moveToNext())
        {
            String matb = cursor.getString(0);
            String tentb = cursor.getString(1);
            String xuatxu = cursor.getString(2);
            thietbi.add(new class_thietbi(matb, tentb, xuatxu, maloai));
            adapter.notifyDataSetChanged();
        }
        cursor.close();
    }

    //x??a thi???t b???
    public void delete_thietbi(String matb)
    {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(Activity_thietbi.this);
        dialog.setTitle("TH??NG B??O");
        dialog.setMessage("B???n c?? mu???n x??a thi???t b??? n??y?");
        dialog.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sql.query_data("delete from THIETBI where MATB = '" + matb + "'");
                select_thietbi();
            }
        });
        dialog.setNegativeButton("Tho??t", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        dialog.show();
    }

    //ch???nh s???a thi???t b???
    public void edit_thietbi(String matb, String tentb, String xuatxu)
    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_thietbi);
        dialog.show();

        EditText editmatb = dialog.findViewById(R.id.editmaphong);
        EditText edittentb = dialog.findViewById(R.id.edittenphong);
        EditText editxuatxu = dialog.findViewById(R.id.editsotang);
        Button buttonedit = dialog.findViewById(R.id.buttonedit3);
        Button buttonexit = dialog.findViewById(R.id.buttonexit3);


        editmatb.setEnabled(false);

        editmatb.setText(matb);
        edittentb.setText(tentb);
        editxuatxu.setText(xuatxu);

        buttonedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!edittentb.getText().toString().trim().equals("") || !editxuatxu.getText().toString().trim().equals("")) {
                    sql.query_data("UPDATE THIETBI SET TENTB = '" + edittentb.getText().toString() +
                            "', XUATXU = '" + editxuatxu.getText().toString() +
                            "' WHERE MATB = '" + editmatb.getText().toString() + "'");
                    select_thietbi();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(Activity_thietbi.this, "Kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
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
        //t??m ki???m
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                   adapter.getFilter().filter(s);
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String s) {
                    adapter.getFilter().filter(s);
                    return false;
                }
            });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btndangxuat)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Th??ng b??o");
            alert.setMessage("B???n c?? mu???n ????ng xu???t kh??ng?");
            alert.setPositiveButton("C??", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Activity_thietbi.this, MainActivity.class);
                    startActivity(intent);
                    dialogInterface.dismiss();
                }
            });
            alert.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alert.show();
        }
        if (item.getItemId() == R.id.btnthongtin)
        {
            Intent intent = new Intent(Activity_thietbi.this, Thongtincanhan.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.btnadd)
        {
            Dialog dialog = new Dialog(Activity_thietbi.this);
            dialog.setContentView(R.layout.dialog_themthietbi);
            dialog.show();


            EditText addtentb = dialog.findViewById(R.id.addtentb);
            EditText addxuatxu = dialog.findViewById(R.id.addxuatxu);
            Button btnaddtb = dialog.findViewById(R.id.addtinhtrangtb);
            Button btnexit = dialog.findViewById(R.id.exittinhtrangtb);
            TextView addma1 = dialog.findViewById(R.id.addma1);
            EditText addma2 = dialog.findViewById(R.id.addma2);

            addma1.setText(maloai);
            addma1.setEnabled(false);
            btnaddtb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!addtentb.getText().toString().trim().equals("") ||
                            !addxuatxu.getText().toString().trim().equals("") ||
                            !addma2.getText().toString().trim().equals("")) {

                        try {
                            String temp = addma1.getText().toString().trim() + addma2.getText().toString().trim();
                            sql.query_data("INSERT INTO THIETBI VALUES ('" + temp +
                                    "', '" + addtentb.getText().toString().trim() +
                                    "', '" +addxuatxu.getText().toString().trim() +
                                    "', '" +maloai +"')");
                            select_thietbi();
                            dialog.dismiss();
                        }catch (Exception e) {
                            Toast.makeText(Activity_thietbi.this, "Tr??ng m?? thi???t b???!!\n Vui l??ng ki???m tra l???i", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(Activity_thietbi.this, "Kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnexit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
}
