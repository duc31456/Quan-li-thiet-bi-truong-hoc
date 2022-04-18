package com.example.quanli_thietbitruonghoc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanli_thietbitruonghoc.Adapter.Adapter_muontratb;
import com.example.quanli_thietbitruonghoc.Class.class_muontratb;
import com.example.quanli_thietbitruonghoc.R;
import com.example.quanli_thietbitruonghoc.SQL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Activity_chitietsudung extends AppCompatActivity {
    public static SQL sql;
    ListView list;
    ArrayList<class_muontratb> muontratb;
    Adapter_muontratb adapter;

//dùng để load spinner
    Spinner add_matb;
    ArrayAdapter<String> spinner_matb;
    ArrayList<String> array_matb;

    String temp_matb, temp_maphong;

    Spinner add_maphong;
    ArrayAdapter<String> spinner_maphong;
    ArrayList<String> array_maphong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsudung);


        list = findViewById(R.id.list);
        muontratb = new ArrayList<>();
        sql = new SQL(Activity_chitietsudung.this, "Database", null, 1);
        adapter = new Adapter_muontratb(Activity_chitietsudung.this, R.layout.layout_itemmuontrathietbi, muontratb);
        list.setAdapter(adapter);
        sql.query_data("CREATE TABLE IF NOT EXISTS MUONTRA(MATB varchar(20) NOT NULL, MAPHONG VARCHAR(20) NOT NULL, " +
                "NGUOIMUON nvarchar(50), SDT varchar(20), NGAYMUON varchar(50), SOLUONG integer, NGAYTRA varchar(50), " +
                "PRIMARY KEY (MATB, MAPHONG))");

      //  sql.query_data("DELETE from MUONTRA where MATB = 'CS01'");
        select_muontratb();
    }

    //lấy thông tin sử dụng
    public void select_muontratb()
    {
        //select table MUONTRA
        Cursor cursor = sql.select_data("select * from MUONTRA");
        muontratb.clear();
        //duyệt dữ liệu khi chưa null
        while(cursor.moveToNext())
        {
            String matb = cursor.getString(0);
            String maphong = cursor.getString(1);
            String nguoimuon = cursor.getString(2);
            String sdt = cursor.getString(3);
            String ngaymuon = cursor.getString(4);
            Integer soluong = cursor.getInt(5);
            String ngaytra = cursor.getString(6);
            muontratb.add(new class_muontratb(matb, maphong, nguoimuon, sdt, ngaymuon, soluong, ngaytra));
            adapter.notifyDataSetChanged();
        }
    }

    //xóa thông tin sử dụng
    public void delete_muontratb(String matb, String maphong)
    {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(Activity_chitietsudung.this);
        dialog.setTitle("THÔNG BÁO");
        dialog.setMessage("Bạn có muốn xóa thông tin sử dụng này?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sql.query_data("delete from MUONTRA where MATB = '" + matb + "' and MAPHONG = '" + maphong +"'");
                select_muontratb();
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

    //chỉnh sửa thông tin sử dụng
    public void edit_muontratb(String matb, String maphong, String nguoimuon, String sdt, String ngaymuon, Integer soluong, String ngaytra)
    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_muontratb);
        dialog.show();

        TextView edit_matb = dialog.findViewById(R.id.edit_matb);
        TextView edit_maphong = dialog.findViewById(R.id.edit_maphong);
        EditText edit_nguoimuon = dialog.findViewById(R.id.edit_nguoimuon);
        EditText edit_sdt = dialog.findViewById(R.id.edit_sdt);
        EditText edit_ngaymuon = dialog.findViewById(R.id.edit_ngaymuon);
        EditText edit_soluong = dialog.findViewById(R.id.edit_soluong);
        EditText edit_ngaytra = dialog.findViewById(R.id.edit_ngaytra);
        Button edit_muontra = dialog.findViewById(R.id.edit_muontra);
        Button exit_muontratb = dialog.findViewById(R.id.exit_muontratb);


        edit_matb.setText(matb);
        edit_maphong.setText(maphong);
        edit_matb.setEnabled(false);
        edit_maphong.setEnabled(false);
        edit_nguoimuon.setText(nguoimuon);
        edit_sdt.setText(sdt);
        edit_ngaymuon.setText(ngaymuon);
        edit_soluong.setText(String.valueOf(soluong));
        edit_ngaytra.setText(ngaytra);

        SimpleDateFormat dateFormat3,dateFormat4;
        dateFormat3 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateFormat4 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Calendar calendar3 = Calendar.getInstance();
        Calendar calendar4 = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date_editngaymuon = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar3.set(Calendar.YEAR, year);
                calendar3.set(Calendar.MONTH,month);
                calendar3.set(Calendar.DAY_OF_MONTH,day);
                edit_ngaymuon.setText(dateFormat3.format(calendar3.getTime()));
            }
        };
        DatePickerDialog.OnDateSetListener date_editngaytra = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar4.set(Calendar.YEAR, year);
                calendar4.set(Calendar.MONTH,month);
                calendar4.set(Calendar.DAY_OF_MONTH,day);
                if (dateFormat4.format(calendar4.getTime()).compareTo(dateFormat3.format(calendar3.getTime())) < 0) {
                    Toast.makeText(dialog.getContext(), "Ngày trả không thể nhỏ hơn ngày mượn!\n Bạn vui lòng chọn lại ngày^^", Toast.LENGTH_SHORT).show();
                    edit_ngaytra.setText("");
                }
                else
                {
                    edit_ngaytra.setText(dateFormat4.format(calendar4.getTime()));
                }
            }
        };
        edit_ngaymuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(dialog.getContext(),date_editngaymuon,calendar3.get(Calendar.YEAR),
                        calendar3.get(Calendar.MONTH),calendar3.get(Calendar.DAY_OF_MONTH)).show();
                edit_ngaytra.setText("");
            }
        });
        edit_ngaytra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(dialog.getContext(),date_editngaytra,calendar4.get(Calendar.YEAR),
                        calendar4.get(Calendar.MONTH),calendar4.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edit_muontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edit_nguoimuon.getText().toString().trim().equals("") || !edit_sdt.getText().toString().trim().equals("") ||
                        !edit_ngaymuon.getText().toString().trim().equals("") || !edit_soluong.getText().toString().trim().equals("") ||
                        !edit_ngaytra.getText().toString().trim().equals("")) {

                    try {
                        sql.query_data("UPDATE MUONTRA SET NGUOIMUON = '" + edit_nguoimuon.getText().toString() +
                                "', SDT = '" + edit_sdt.getText().toString().trim() +
                                "', NGAYMUON = '" + edit_ngaymuon.getText().toString() +
                                "', SOLUONG = '" + Integer.parseInt(edit_soluong.getText().toString().trim()) +
                                "', NGAYTRA = '" + edit_ngaytra.getText().toString() +
                                "' WHERE MATB = '" + matb + "' and MAPHONG= '" + maphong + "'");
                        select_muontratb();
                        dialog.dismiss();
                    }catch (Exception e) {
                        Toast.makeText(Activity_chitietsudung.this, "Lỗi chỉnh sửa thông tin!!\n Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Activity_chitietsudung.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }
            }
        });

        exit_muontratb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void load_matb()
    {
        array_matb = new ArrayList<>();
        array_matb.clear();
        try {
            Cursor cursor = Activity_thietbi.sql.select_data("select * from THIETBI");
            while(cursor.moveToNext()) {
                String matb = cursor.getString(0);
                array_matb.add(matb);
            }
            Cursor delete_cursor = Activity_baoloithietbi.sql.select_data("select MATB from TINHTRANG where TRANGTHAI = 'Đang sữa chữa'" +
                    "or TRANGTHAI = 'Báo hỏng'");
            while(delete_cursor.moveToNext()) {
                String deletematb = delete_cursor.getString(0);
                array_matb.remove(deletematb);
            }
        }catch (Exception e)
        {}

       spinner_matb = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, array_matb);
        add_matb.setAdapter(spinner_matb);
    }

    public void load_maphong()
    {
        array_maphong = new ArrayList<>();
        array_maphong.clear();
        try{
            Cursor cursor = Activity_phonghoc.sql.select_data("select * from PHONGHOC");

            while(cursor.moveToNext()) {
                String maphong = cursor.getString(0);
                array_maphong.add(maphong);
            }
        }catch (Exception e)
        {

        }
        spinner_maphong = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, array_maphong);
        add_maphong.setAdapter(spinner_maphong);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.thongtin_canhan, menu);

        //tìm kiếm
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
            alert.setTitle("Thông báo");
            alert.setMessage("Bạn có muốn đăng xuất không?");
            alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Activity_chitietsudung.this, MainActivity.class);
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
            Intent intent = new Intent(Activity_chitietsudung.this, Thongtincanhan.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.btnadd)
        {
            Dialog dialog = new Dialog(Activity_chitietsudung.this);
            dialog.setContentView(R.layout.dialog_muontrathietbi);
            dialog.show();

            add_matb = dialog.findViewById(R.id.add_matb);
            add_maphong = dialog.findViewById(R.id.add_maphong);
            EditText add_nguoimuon = dialog.findViewById(R.id.edit_nguoimuon);
            EditText add_sdt = dialog.findViewById(R.id.edit_sdt);
             EditText add_ngaymuon = dialog.findViewById(R.id.edit_ngaymuon);
            EditText add_soluong = dialog.findViewById(R.id.edit_soluong);
             EditText add_ngaytra = dialog.findViewById(R.id.edit_ngaytra);
            Button add_muontra = dialog.findViewById(R.id.edit_muontra);
            Button exit_muontra = dialog.findViewById(R.id.exit_muontratb);
            TextView title_addmuontra = dialog.findViewById(R.id.title_editmuontra);


            SimpleDateFormat dateFormat1,dateFormat2;
            dateFormat1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dateFormat2= new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();

            add_ngaytra.setEnabled(false);
            DatePickerDialog.OnDateSetListener date_ngaymuon = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    calendar1.set(Calendar.YEAR, year);
                    calendar1.set(Calendar.MONTH,month);
                    calendar1.set(Calendar.DAY_OF_MONTH,day);
                    add_ngaymuon.setText(dateFormat1.format(calendar1.getTime()));
                }
            };
            DatePickerDialog.OnDateSetListener date_ngaytra = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    calendar2.set(Calendar.YEAR, year);
                    calendar2.set(Calendar.MONTH,month);
                    calendar2.set(Calendar.DAY_OF_MONTH,day);
                    if (dateFormat2.format(calendar2.getTime()).compareTo(dateFormat1.format(calendar1.getTime())) < 0) {
                        Toast.makeText(dialog.getContext(), "Ngày trả không thể nhỏ hơn ngày mượn!\n Bạn vui lòng chọn lại ngày^^", Toast.LENGTH_SHORT).show();
                        add_ngaytra.setText("");
                    }
                    else
                    {
                        add_ngaytra.setText(dateFormat2.format(calendar2.getTime()));
                    }
                }
            };
            add_ngaymuon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(dialog.getContext(),date_ngaymuon,calendar1.get(Calendar.YEAR),
                            calendar1.get(Calendar.MONTH),calendar1.get(Calendar.DAY_OF_MONTH)).show();
                    add_ngaytra.setEnabled(true);
                    add_ngaytra.setText("");
                }
            });

            add_ngaytra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(dialog.getContext(),date_ngaytra,calendar2.get(Calendar.YEAR),
                            calendar2.get(Calendar.MONTH),calendar2.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            title_addmuontra.setText("Mượn thiết bị");
            add_muontra.setText("Thêm");
            load_matb();
            load_maphong();

            add_matb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    temp_matb = array_matb.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            add_maphong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    temp_maphong = array_maphong.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            add_muontra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!add_nguoimuon.getText().toString().trim().equals("") || !add_sdt.getText().toString().trim().equals("") ||
                     !add_ngaymuon.getText().toString().trim().equals("") || !add_soluong.getText().toString().trim().equals("") ||
                     !add_ngaytra.getText().toString().trim().equals("")) {

                        try {
                            sql.query_data("INSERT INTO MUONTRA VALUES ('" + temp_matb.trim() +
                                    "', '" + temp_maphong.trim() +
                                    "', '" + add_nguoimuon.getText().toString().trim() +
                                    "', '" + add_sdt.getText().toString().trim() +
                                    "', '" + add_ngaymuon.getText().toString() +
                                    "', '" + Integer.parseInt(add_soluong.getText().toString().trim()) +
                                    "', '" + add_ngaytra.getText().toString().trim() +"')");
                            select_muontratb();
                            dialog.dismiss();
                        }catch (Exception e) {
                            Toast.makeText(Activity_chitietsudung.this, "Trùng mã thiết bị hoặc mã phòng!!\n Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(Activity_chitietsudung.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            exit_muontra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}