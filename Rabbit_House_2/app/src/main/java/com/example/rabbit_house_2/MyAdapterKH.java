package com.example.rabbit_house_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapterKH extends ArrayAdapter<Khach> {
    ArrayList <Khach> dsKhachHang = new ArrayList<Khach>();
    public MyAdapterKH(Context context, int resource, ArrayList<Khach> object) {
        super(context, resource, object);
        dsKhachHang = object;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.mykhachhang, null);
        TextView txtTenKH = (TextView) v.findViewById(R.id.tvTenKH);
        TextView txtSoDT = (TextView) v.findViewById(R.id.tvSoDT);
//        if (position == 0){
//            txtTenKH.setBackground(Color.WHITE);
//            txtSoDT.setBackground(Color.WHITE);
//        }
        txtTenKH.setText(dsKhachHang.get(position).getTen());
        txtSoDT.setText(dsKhachHang.get(position).getSdt());
        return v;
    }

}
