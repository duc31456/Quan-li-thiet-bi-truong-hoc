package com.example.quanli_thietbitruonghoc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class SQL extends SQLiteOpenHelper {
    public SQL(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void query_data(String sql)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
    }

    public Cursor select_data(String sql)
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql, null);
    }

    //insert có image
    public void insert_data(String taikhoan, String sdt , String matkhau, byte[] avatar)
    {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "Insert into LOGIN_ADMIN values (null,?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, taikhoan);
        statement.bindString(2, sdt);
        statement.bindString(3, matkhau);
        statement.bindBlob(4, avatar);

        statement.executeInsert();
    }

    //update có image
    public void update_data( String sdt , String matkhau, byte[] avatar, String taikhoan)
    {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "Update LOGIN_ADMIN set SDT = ?, MATKHAU = ?, AVATAR = ? where TAIKHOAN = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, sdt);
        statement.bindString(2, matkhau);
        statement.bindBlob(3, avatar);
        statement.bindString(4, taikhoan);

        statement.executeInsert();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
