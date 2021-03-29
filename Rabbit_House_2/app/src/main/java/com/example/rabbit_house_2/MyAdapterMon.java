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

public class MyAdapterMon extends ArrayAdapter<Mon> {
    ArrayList <Mon> dsMon = new ArrayList<Mon>();
    public MyAdapterMon(Context context, int resource, ArrayList<Mon> object) {
        super(context, resource, object);
        dsMon = object;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.mymon, null);
        TextView txtTenMon = (TextView) v.findViewById(R.id.tvTenMon);
        TextView txtLoai  = (TextView) v.findViewById(R.id.tvLoai);
        TextView txtGia  = (TextView) v.findViewById(R.id.tvGia);
//        if (position == 0){
//            txtTenKH.setBackground(Color.WHITE);
//            txtSoDT.setBackground(Color.WHITE);
//        }
        txtTenMon.setText(dsMon.get(position).getTenMon());
        txtLoai.setText(dsMon.get(position).getLoai());
        txtGia.setText(dsMon.get(position).getGia());
        return v;
    }

}
