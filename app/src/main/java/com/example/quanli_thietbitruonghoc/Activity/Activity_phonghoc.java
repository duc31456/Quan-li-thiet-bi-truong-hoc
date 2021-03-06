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

import com.example.quanli_thietbitruonghoc.Adapter.Adapter_phonghoc;
import com.example.quanli_thietbitruonghoc.R;
import com.example.quanli_thietbitruonghoc.SQL;
import com.example.quanli_thietbitruonghoc.Class.class_phonghoc;

import java.lang.reflect.Field;
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

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 102400 * 1024); //the 102400 is the new size added
        } catch (Exception e) {
            e.printStackTrace();
        }

        listphonghoc = findViewById(R.id.listtinhtrang);

        phonghoc = new ArrayList<>();

        sql = new SQL(Activity_phonghoc.this, "Database", null, 1);
        adapter = new Adapter_phonghoc(Activity_phonghoc.this, R.layout.layout_itemphonghoc, phonghoc);
        listphonghoc.setAdapter(adapter);

        sql.query_data("CREATE TABLE IF NOT EXISTS PHONGHOC(MAPHONG varchar(20) PRIMARY KEY, TENPHONG NVARCHAR(50), SOTANG Integer)");

    //    sql.query_data("INSERT INTO PHONGHOC VALUES ('LT01', 'Ph??ng l?? thuy???t 1', 2)");
        select_phonghoc();
    }

    public void select_phonghoc()
    {
       Cursor cursor = sql.select_data("select * from PHONGHOC");
        phonghoc.clear();
        //duy???t d??? li???u khi ch??a null
        while(cursor.moveToNext())
        {
            String maphong = cursor.getString(0);
            String tenphong = cursor.getString(1);
            Integer sotang = cursor.getInt(2);
            phonghoc.add(new class_phonghoc(maphong, tenphong, sotang));
            adapter.notifyDataSetChanged();
        }
        cursor.close();
    }

    //x??a ph??ng h???c
    public void delete_phonghoc(String maphong)
    {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(Activity_phonghoc.this);
        dialog.setTitle("TH??NG B??O");
        dialog.setMessage("B???n c?? mu???n x??a ph??ng h???c n??y?");
        dialog.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sql.query_data("delete from PHONGHOC where MAPHONG = '" + maphong + "'");
                select_phonghoc();
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

    //ch???nh s???a phonghoc
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
        title.setText("Ch???nh s???a");
        buttonedit.setText("S???a");

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
                    Toast.makeText(Activity_phonghoc.this, "Kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
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
                    Intent intent = new Intent(Activity_phonghoc.this, MainActivity.class);
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

            title.setText("Th??m ph??ng h???c");
            buttonedit.setText("Th??m");

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
                            Toast.makeText(Activity_phonghoc.this, "Tr??ng m?? ph??ng!!\n Vui l??ng ki???m tra l???i", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(Activity_phonghoc.this, "Kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
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