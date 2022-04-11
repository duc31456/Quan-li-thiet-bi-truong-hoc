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

import com.example.quanli_thietbitruonghoc.Activity.Activity_chitietsudung;
import com.example.quanli_thietbitruonghoc.Class.class_muontratb;
import com.example.quanli_thietbitruonghoc.Class.class_phonghoc;
import com.example.quanli_thietbitruonghoc.R;

import java.util.ArrayList;

public class Adapter_muontratb extends BaseAdapter implements Filterable {
    private Activity_chitietsudung context;
    private int layout;
    ArrayList<class_muontratb> muontratb, muontratbsearch;

    public Adapter_muontratb(Activity_chitietsudung context, int layout, ArrayList<class_muontratb> muontratb) {
        this.context = context;
        this.layout = layout;
        this.muontratb = muontratb;
        this.muontratbsearch = muontratb;
    }

    @Override
    public int getCount() {
        return muontratb.size();
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
                    muontratb = muontratbsearch;
                }
                else
                {
                    ArrayList<class_muontratb> list = new ArrayList<>();
                    for (class_muontratb tempitem: muontratbsearch)
                    {
                        if (tempitem.getMatb().toLowerCase().contains(srsearch.toLowerCase())
                                ||tempitem.getMaphong().toLowerCase().contains(srsearch.toLowerCase())
                                ||tempitem.getNgaymuon().toLowerCase().contains(srsearch.toLowerCase())
                                ||tempitem.getNgaytra().toLowerCase().contains(srsearch.toLowerCase())
                                ||tempitem.getNguoimuon().toLowerCase().contains(srsearch.toLowerCase())
                                ||tempitem.getSdtnguoimuon().toLowerCase().contains(srsearch.toLowerCase()))
                        {
                            list.add(tempitem);
                        }
                    }
                    muontratb = list;
                }
                FilterResults results =  new FilterResults();
                results.values = muontratb;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                muontratb = (ArrayList<class_muontratb>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    private class View_holer
    {
        ImageView item_edit, item_delete;
        TextView item_matb, item_maphong, item_soluong,
                item_ngaymuon, item_ngaytra,
        item_nguoimuon, item_sdtnguoimuon;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       View_holer view_holer;

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            view_holer = new View_holer();

            view_holer.item_edit = view.findViewById(R.id.item_edit);
            view_holer.item_delete = view.findViewById(R.id.item_delete);
            view_holer.item_matb = view.findViewById(R.id.item_matb);
            view_holer.item_maphong = view.findViewById(R.id.item_maphong);
            view_holer.item_nguoimuon = view.findViewById(R.id.item_nguoimuon);
            view_holer.item_sdtnguoimuon = view.findViewById(R.id.item_sdtnguoimuon);
            view_holer.item_soluong = view.findViewById(R.id.item_soluong);
            view_holer.item_ngaymuon = view.findViewById(R.id.item_ngaymuon);
            view_holer.item_ngaytra = view.findViewById(R.id.item_ngaytra);

            view.setTag(view_holer);
        }
        else
        {
            view_holer = (View_holer) view.getTag();
        }

        class_muontratb tb = muontratb.get(i);

        view_holer.item_matb.setText(tb.getMatb());
        view_holer.item_maphong.setText(tb.getMaphong());
        view_holer.item_nguoimuon.setText("Người mượn:"+tb.getNguoimuon());
        view_holer.item_sdtnguoimuon.setText("SĐT::"+tb.getSdtnguoimuon());
        view_holer.item_soluong.setText("Số lượng mượn:"+String.valueOf(tb.getSoluong()));
        view_holer.item_ngaymuon.setText("Ngày mượn:"+tb.getNgaymuon());
        view_holer.item_ngaytra.setText("Ngày trả:"+tb.getNgaytra());

        view_holer.item_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.edit_muontratb(tb.getMatb(), tb.getMaphong(), tb.getNguoimuon(), tb.getSdtnguoimuon()
                , tb.getNgaymuon(), tb.getSoluong() , tb.getNgaytra());
            }
        });
        view_holer.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.delete_muontratb(tb.getMatb(), tb.getMaphong());
            }
        });
        return view;
    }
}
