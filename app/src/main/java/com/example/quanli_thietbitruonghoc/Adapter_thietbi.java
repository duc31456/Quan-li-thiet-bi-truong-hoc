package com.example.quanli_thietbitruonghoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_thietbi extends BaseAdapter {
    private Activity_thietbi context;
    private int layout;
    ArrayList<class_thietbi> thietbi;

    public Adapter_thietbi(Activity_thietbi context, int layout, ArrayList<class_thietbi> thietbi) {
        this.context = context;
        this.layout = layout;
        this.thietbi = thietbi;
    }

    @Override
    public int getCount() {
        return thietbi.size();
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
        TextView itemmatb, itemtentb, itemxuatxu;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View_holer view_holer;

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            view_holer = new Adapter_thietbi.View_holer();

            view_holer.imgedit = view.findViewById(R.id.imgedit2);
            view_holer.imgdelete = view.findViewById(R.id.imgdelete2);
            view_holer.itemmatb = view.findViewById(R.id.itemmatb);
            view_holer.itemtentb = view.findViewById(R.id.itemtentb);
            view_holer.itemxuatxu = view.findViewById(R.id.itemxuatxu);

            view.setTag(view_holer);
        }
        else
        {
            view_holer = (View_holer) view.getTag();
        }

        class_thietbi tb = thietbi.get(i);

        view_holer.itemmatb.setText(tb.getMatb());
        view_holer.itemtentb.setText(tb.getTentb());
        view_holer.itemxuatxu.setText(tb.getXuatxu());

        return view;
    }
}
