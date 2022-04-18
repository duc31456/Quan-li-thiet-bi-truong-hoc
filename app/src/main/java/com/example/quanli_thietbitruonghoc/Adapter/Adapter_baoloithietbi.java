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
import android.widget.Toast;

import com.example.quanli_thietbitruonghoc.Activity.Activity_baoloithietbi;
import com.example.quanli_thietbitruonghoc.Class.class_baoloithietbi;
import com.example.quanli_thietbitruonghoc.R;

import java.util.ArrayList;

public class Adapter_baoloithietbi extends BaseAdapter implements Filterable {
    private Activity_baoloithietbi context;
    private int layout;
    ArrayList<class_baoloithietbi> tinhtrangtb, tinhtrangtbsearch;

    public Adapter_baoloithietbi(Activity_baoloithietbi context, int layout, ArrayList<class_baoloithietbi> tinhtrangtb) {
        this.context = context;
        this.layout = layout;
        this.tinhtrangtb = tinhtrangtb;
        this.tinhtrangtbsearch = tinhtrangtb;
    }

    @Override
    public int getCount() {
        return tinhtrangtb.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String srsearch = charSequence.toString();
                if(srsearch.isEmpty())
                {
                    tinhtrangtb = tinhtrangtbsearch;
                }
                else
                {
                    ArrayList<class_baoloithietbi> list = new ArrayList<>();
                    for (class_baoloithietbi tempitem: tinhtrangtbsearch)
                    {
                        if (tempitem.getMatb().toLowerCase().contains(srsearch.toLowerCase())
                                ||tempitem.getTentb().toLowerCase().contains(srsearch.toLowerCase())
                                ||tempitem.getTinhtrang().toLowerCase().contains(srsearch.toLowerCase()))
                        {
                            list.add(tempitem);
                        }
                    }
                    tinhtrangtb = list;
                }
                FilterResults results =  new FilterResults();
                results.values = tinhtrangtb;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                tinhtrangtb = (ArrayList<class_baoloithietbi>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private class View_holer
    {
        ImageView imgedit, imgdelete;
        TextView itemmatb, itemtentb, itemtinhtrang;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Adapter_baoloithietbi.View_holer view_holer;

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            view_holer = new Adapter_baoloithietbi.View_holer();

            view_holer.imgedit = view.findViewById(R.id.edittinhtrang);
            view_holer.imgdelete = view.findViewById(R.id.deletetinhtrang);
            view_holer.itemmatb = view.findViewById(R.id.itemthongkematb);
            view_holer.itemtentb = view.findViewById(R.id.itemthongketentb);
           view_holer.itemtinhtrang = view.findViewById(R.id.itemthongkesoluong);

            view.setTag(view_holer);
        }
        else
        {
            view_holer = (Adapter_baoloithietbi.View_holer) view.getTag();
        }

        class_baoloithietbi baoloi = tinhtrangtb.get(i);

        view_holer.itemmatb.setText(baoloi.getMatb());
        view_holer.itemtentb.setText(baoloi.getTentb());
        view_holer.itemtinhtrang.setText(baoloi.getTinhtrang());

        view_holer.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(baoloi.getTinhtrang().equals("Đang sữa chữa")) {
                    context.edit_tinhtrangtb(baoloi.getMatb());
                }
                else
                {
                    Toast.makeText(context, "Thiết bị này chưa đem đi sửa chữa\nVui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view_holer.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.delete_tinhtrangtb(baoloi.getMatb());
            }
        });

        return view;
    }
}
