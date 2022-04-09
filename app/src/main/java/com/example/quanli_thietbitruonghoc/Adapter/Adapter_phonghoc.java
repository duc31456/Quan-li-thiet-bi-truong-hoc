package com.example.quanli_thietbitruonghoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanli_thietbitruonghoc.Activity.Activity_phonghoc;
import com.example.quanli_thietbitruonghoc.R;
import com.example.quanli_thietbitruonghoc.Class.class_phonghoc;

import java.util.ArrayList;

public class Adapter_phonghoc extends BaseAdapter {
    private Activity_phonghoc context;
    private int layout;
    ArrayList<class_phonghoc> phonghoc;

    public Adapter_phonghoc(Activity_phonghoc context, int layout, ArrayList<class_phonghoc> phonghoc) {
        this.context = context;
        this.layout = layout;
        this.phonghoc = phonghoc;
    }

    @Override
    public int getCount() {
        return phonghoc.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class View_holer
    {
        ImageView imgedit, imgdelete;
        TextView itemmaphong, itemtenphong, itemsotang;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Adapter_phonghoc.View_holer view_holer;

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            view_holer = new View_holer();

            view_holer.imgedit = view.findViewById(R.id.imgedit3);
            view_holer.imgdelete = view.findViewById(R.id.imgdelete3);
            view_holer.itemmaphong = view.findViewById(R.id.itemmaphong);
            view_holer.itemtenphong = view.findViewById(R.id.itemtenphong);
            view_holer.itemsotang = view.findViewById(R.id.itemsotang);

            view.setTag(view_holer);
        }
        else
        {
            view_holer = (Adapter_phonghoc.View_holer) view.getTag();
        }

        class_phonghoc room = phonghoc.get(i);

        view_holer.itemmaphong.setText(room.getMaphong());
        view_holer.itemtenphong.setText(room.getTenphong());
        view_holer.itemsotang.setText(String.valueOf(room.getSotang()));

        view_holer.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.delete_phonghoc(room.getMaphong());
            }
        });

        view_holer.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.edit_phonghoc(room.getMaphong(), room.getTenphong(), room.getSotang());
            }
        });

        return view;
    }
}
