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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanli_thietbitruonghoc.Adapter.Adapter_baoloithietbi;
import com.example.quanli_thietbitruonghoc.Adapter.Adapter_thietbi;
import com.example.quanli_thietbitruonghoc.Class.class_baoloithietbi;
import com.example.quanli_thietbitruonghoc.Class.class_thietbi;
import com.example.quanli_thietbitruonghoc.R;
import com.example.quanli_thietbitruonghoc.SQL;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Activity_baoloithietbi extends AppCompatActivity{
    Adapter_baoloithietbi adapter;
    ArrayList<class_baoloithietbi> baoloitb;
    ListView list;

    Spinner spinnermatb, spinnertinhtrang;
    ArrayAdapter<String> spinner_matb, spinner_tinhtrang;
    ArrayList<String> array_matb,array_tinhtrang, array_tentb;
    String tempmatb, temptinhtrang;

    public static SQL sql;

    Cursor cursor5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoloithietbi);

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 102400 * 1024); //the 102400 is the new size added
        } catch (Exception e) {
            e.printStackTrace();
        }
        list = findViewById(R.id.listtinhtrang);
        baoloitb = new ArrayList<>();

        sql = new SQL(Activity_baoloithietbi.this, "Database", null, 1);
        adapter = new Adapter_baoloithietbi(Activity_baoloithietbi.this, R.layout.layout_itembaoloithietbi, baoloitb);
        list.setAdapter(adapter);

        sql.query_data("CREATE TABLE IF NOT EXISTS TINHTRANG(MATB varchar(20) PRIMARY KEY, " +
                "TENTB NVARCHAR(50), TRANGTHAI NVARCHAR(20))");

        select_tinhtrangtb();
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Activity_baoloithietbi.this);
                alert.setTitle("Th??ng b??o");
                alert.setMessage("B???n c?? x??c nh???n mang ??i s???a thi???t b??? n??y?");
                alert.setPositiveButton("C??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index) {
                        if(!baoloitb.get(i).getTinhtrang().trim().equals("??ang s???a ch???a")) {
                            try {
                                sql.query_data("UPDATE TINHTRANG SET TRANGTHAI = '??ang s???a ch???a' WHERE MATB = '" + baoloitb.get(i).getMatb() + "'");
                                select_tinhtrangtb();
                                dialogInterface.dismiss();
                            } catch (Exception e) {
                                Toast.makeText(Activity_baoloithietbi.this, ""+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Activity_baoloithietbi.this, "Thi???t b??? n??y ??ang ??? tr???ng th??i s???a ch???a!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
                return false;
            }
        });
    }

    //l???y danh s??ch t??nh trang thi???t b???
    public void select_tinhtrangtb()
    {
        //select table congviec
        Cursor cursor = sql.select_data("select * from TINHTRANG");
        baoloitb.clear();
        //duy???t d??? li???u khi ch??a null
        while(cursor.moveToNext())
        {
            String matb = cursor.getString(0);
            String tentb = cursor.getString(1);
            String tinhtrang = cursor.getString(2);
            baoloitb.add(new class_baoloithietbi(matb, tentb, tinhtrang));
            adapter.notifyDataSetChanged();
        }
        cursor.close();
    }

    public void edit_tinhtrangtb(String matb)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(Activity_baoloithietbi.this);
        alert.setTitle("Th??ng b??o");
        alert.setMessage("X??c nh???n mang thi???t b??? ho???t ?????ng tr??? l???i?");
        alert.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index) {
                    try {
                        sql.query_data("UPDATE TINHTRANG SET TRANGTHAI = 'S???a ch???a th??nh c??ng' where MATB = '" + matb + "'");
                        select_tinhtrangtb();
                        dialogInterface.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(Activity_baoloithietbi.this, ""+e, Toast.LENGTH_SHORT).show();
                    }
                }
        });
        alert.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }
    //x??a t??nh tr???ng thi???t b???
    public void delete_tinhtrangtb(String matb)
    {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(Activity_baoloithietbi.this);
        dialog.setTitle("TH??NG B??O");
        dialog.setMessage("B???n c?? mu???n x??a thi???t b??? ??ang ch???n n??y?");
        dialog.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sql.query_data("delete from TINHTRANG where MATB = '" + matb + "'");
                select_tinhtrangtb();
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

    private void load_spinner()
    {
        array_matb = new ArrayList<>();
        array_tinhtrang = new ArrayList<>();
        array_tentb = new ArrayList<>();
        array_matb.clear();
        array_tinhtrang.clear();
        array_tentb.clear();
        try {
            cursor5 = Activity_thietbi.sql.select_data("select MATB, TENTB from THIETBI");
            while (cursor5.moveToNext()) {
                String matb = cursor5.getString(0);
                String tentb = cursor5.getString(1);
                array_matb.add(matb);
                array_tentb.add(tentb);
            }
            cursor5.close();
        }catch (Exception e)
        {}
            array_tinhtrang.add("B??o h???ng");
            array_tinhtrang.add("??ang s???a ch???a");
            array_tinhtrang.add("S???a ch???a th??nh c??ng");
            spinner_matb = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, array_matb);
            spinner_tinhtrang = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, array_tinhtrang);


            spinnermatb.setAdapter(spinner_matb);
            spinnertinhtrang.setAdapter(spinner_tinhtrang);
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
                    Intent intent = new Intent(Activity_baoloithietbi.this, MainActivity.class);
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
            Intent intent = new Intent(Activity_baoloithietbi.this, Thongtincanhan.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.btnadd)
        {
            Dialog dialog = new Dialog(Activity_baoloithietbi.this);
            dialog.setContentView(R.layout.dialog_themtinhtrangtb);
            dialog.show();

            spinnermatb = dialog.findViewById(R.id.spinnermatb);
           TextView txttentb = dialog.findViewById(R.id.spinnertentb);
            spinnertinhtrang = dialog.findViewById(R.id.spinnertinhtrang);
            Button addtinhtrangtb = dialog.findViewById(R.id.addtinhtrangtb);
            Button exittinhtrangtb = dialog.findViewById(R.id.exittinhtrangtb);

            txttentb.setEnabled(false);
            spinnertinhtrang.setEnabled(false);
            load_spinner();
            spinnermatb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    tempmatb = array_matb.get(i);
                    txttentb.setText(array_tentb.get(i));
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnertinhtrang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    temptinhtrang = array_tinhtrang.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            addtinhtrangtb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        try {
                            sql.query_data("INSERT INTO TINHTRANG VALUES ('" + tempmatb +
                                    "', '" + txttentb.getText().toString().trim() +
                                    "', '" + temptinhtrang + "')");
                            select_tinhtrangtb();
                            dialog.dismiss();
                        }catch (Exception e) {
                            Toast.makeText(Activity_baoloithietbi.this, "Tr??ng m?? thi???t b???!!\n Vui l??ng ki???m tra l???i", Toast.LENGTH_SHORT).show();
                        }
                }
            });


            exittinhtrangtb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
}