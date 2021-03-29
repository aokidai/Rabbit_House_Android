package com.example.rabbit_house_2;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyAdapterHD extends ArrayAdapter<HoaDon> {
    ArrayList<HoaDon> dsHoaDon = new ArrayList<HoaDon>();

    public MyAdapterHD(@NonNull Context context, int resource, ArrayList<HoaDon> object) {
        super(context, resource, object);
        dsHoaDon = object;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.myhoadon, null);
        TextView txtTenKH =(TextView) v.findViewById(R.id.tvTenKHHD);
        TextView txtMon =(TextView) v.findViewById(R.id.tvMonHD);
        TextView txtSLHD =(TextView) v.findViewById(R.id.tvSLHD);
        if (position == 0){
//            txtTenKH.setBackground(Color.WHITE);
//            txtMon.setBackground(Color.WHITE);
//            txtSLHD.setBackground(Color.WHITE);
//            txtThanhTien.setBackground(Color.WHITE);
        }
        txtTenKH.setText(dsHoaDon.get(position).getIdKH());
        txtMon.setText(dsHoaDon.get(position).getId_Mon());
        txtSLHD.setText(dsHoaDon.get(position).getSoLuongHD());
        return v;
    }
}
