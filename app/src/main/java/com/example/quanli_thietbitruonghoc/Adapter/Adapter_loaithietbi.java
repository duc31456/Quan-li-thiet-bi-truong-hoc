package com.example.quanli_thietbitruonghoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanli_thietbitruonghoc.Activity.Activity_loaithietbi;
import com.example.quanli_thietbitruonghoc.R;
import com.example.quanli_thietbitruonghoc.Class.class_loaithietbi;

import java.util.ArrayList;

public class Adapter_loaithietbi extends BaseAdapter {

    private Activity_loaithietbi context;
    private int layout;
    ArrayList<class_loaithietbi> loai_thietbi;

    public Adapter_loaithietbi(Activity_loaithietbi context, int layout, ArrayList<class_loaithietbi> loai_thietbi) {
        this.context = context;
        this.layout = layout;
        this.loai_thietbi = loai_thietbi;
    }

    @Override
    public int getCount() {
        return loai_thietbi.size();
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
        ImageView imgedit, imgdelete, imgnext;
        TextView itemmaloai, itemtenloai;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View_holer view_holer;

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            view_holer = new View_holer();

            view_holer.imgedit = view.findViewById(R.id.imgedit);
            view_holer.imgdelete = view.findViewById(R.id.imgdelete);
            view_holer.itemmaloai = view.findViewById(R.id.itemmaloai);
            view_holer.itemtenloai = view.findViewById(R.id.itemtenloai);
            view_holer.imgnext = view.findViewById(R.id.imgnext);

            view.setTag(view_holer);
        }
        else
        {
            view_holer = (View_holer) view.getTag();
        }

        class_loaithietbi loaitb = loai_thietbi.get(i);

        view_holer.itemmaloai.setText(loaitb.getMaloai());
        view_holer.itemtenloai.setText(loaitb.getTenloai());

        view_holer.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.delete_loaithietbi(loaitb.getMaloai());
            }
        });

        view_holer.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.edit_loaithietbi(loaitb.getMaloai(), loaitb.getTenloai());
            }
        });

        view_holer.imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.nexttothietbi(loaitb.getMaloai(), loaitb.getTenloai());
            }
        });
        return view;
    }

}
