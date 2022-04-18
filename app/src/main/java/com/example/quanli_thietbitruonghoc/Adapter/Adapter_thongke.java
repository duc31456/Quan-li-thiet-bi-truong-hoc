package com.example.quanli_thietbitruonghoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanli_thietbitruonghoc.Activity.Activity_loaithietbi;
import com.example.quanli_thietbitruonghoc.Activity.Thongke;
import com.example.quanli_thietbitruonghoc.Class.class_loaithietbi;
import com.example.quanli_thietbitruonghoc.Class.class_thongke;
import com.example.quanli_thietbitruonghoc.R;

import java.util.ArrayList;

public class Adapter_thongke extends BaseAdapter{

    private Thongke context;
    private int layout;
    ArrayList<class_thongke> thongkes;

    public Adapter_thongke(Thongke context, int layout, ArrayList<class_thongke> thongkes) {
        this.context = context;
        this.layout = layout;
        this.thongkes = thongkes;
    }

    @Override
    public int getCount() {
        return thongkes.size();
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
        TextView itemmatb, itemtentb, itemngaytra, itemsoluong;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View_holer view_holer;

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            view_holer = new View_holer();

            view_holer.itemmatb = view.findViewById(R.id.itemthongkematb);
            view_holer.itemtentb = view.findViewById(R.id.itemthongketentb);
            view_holer.itemngaytra = view.findViewById(R.id.itemthongkengaytra);
            view_holer.itemsoluong = view.findViewById(R.id.itemthongkesoluong);

            view.setTag(view_holer);
        }
        else
        {
            view_holer = (View_holer) view.getTag();
        }

        class_thongke tk = thongkes.get(i);

        view_holer.itemmatb.setText(tk.getMatb());
        view_holer.itemtentb.setText(tk.getTentb());
        view_holer.itemsoluong.setText(String.valueOf(tk.getSoluong()));
        view_holer.itemngaytra.setText(tk.getNgaytra());

        return view;
    }

}
