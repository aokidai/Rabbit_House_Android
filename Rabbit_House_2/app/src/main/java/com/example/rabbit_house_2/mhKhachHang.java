package com.example.rabbit_house_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class mhKhachHang extends Activity {
    ListView lstKhachHang;
    Button btnThem;
    ArrayList<Khach> dsKhachHang = new ArrayList<Khach>();
    MyAdapterKH adapter;
    SQLiteDatabase db;
    int posselected = -1;
    public static final int OPEN_KH = 113;
    public static final int EDIT_KH = 114;
    public static final int SAVE_KH = 115;
    private void getDSKhachHang(){
        try{
            dsKhachHang.add(new Khach("Tên KH", "Số ĐT"));
            db = openOrCreateDatabase(mhLogin.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.query("tblKhachhang", null, null,null,null,null,null);
            c.moveToFirst();
            while (!c.isAfterLast()){
                dsKhachHang.add(new Khach(c.getInt(0) + "", c.getString(1).toString(), c.getString(2).toString()));
                c.moveToNext();
            }
            adapter = new MyAdapterKH(this, android.R.layout.simple_list_item_1, dsKhachHang);
            lstKhachHang.setAdapter(adapter);
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Lỗi "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void comfirmDelete(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Xác nhận để xóa...");
        alertDialogBuilder.setIcon(R.drawable.question);
        alertDialogBuilder.setMessage("Bạn có chắc xóa?");
        alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db = openOrCreateDatabase(mhLogin.DATABASE_NAME, MODE_PRIVATE, null);
                String idKH = dsKhachHang.get(posselected).getIdKH();
                if(db.delete("tblKhachhang", "idKH=?", new String[]{idKH})!=-1){
                    dsKhachHang.remove(posselected);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplication(), "Xóa khách hàng thành công!", Toast.LENGTH_LONG).show();
                }
            }
        });
        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucontext, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuedit:
                Khach khach = dsKhachHang.get(posselected);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(mhKhachHang.this, EditKhachHang.class);
                bundle.putSerializable("khach", (Serializable) khach);
                intent.putExtra("data", bundle);
                startActivityForResult(intent, mhKhachHang.EDIT_KH);
                return true;
            case R.id.mnudelete:
                comfirmDelete();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case mhKhachHang.OPEN_KH:
                if(resultCode == mhKhachHang.SAVE_KH){
                    Bundle bundle = data.getBundleExtra("data");
                    Khach khach = (Khach)bundle.getSerializable("khach");
                    dsKhachHang.add(khach);
                    adapter.notifyDataSetChanged();
                }
                break;
            case mhKhachHang.EDIT_KH:
                if (requestCode == mhKhachHang.SAVE_KH){
                    Bundle bundle = data.getBundleExtra("data");
                    Khach khach = (Khach)bundle.getSerializable("khach");
                    dsKhachHang.set(posselected, khach);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mh_khach_hang);
        lstKhachHang = (ListView) findViewById(R.id.lvKH);
        btnThem = (Button) findViewById(R.id.btnThemKH);
        getDSKhachHang();
        registerForContextMenu(lstKhachHang);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mhKhachHang.this, InsertKhachHang.class);
                startActivityForResult(intent, mhKhachHang.OPEN_KH);
            }
        });
        lstKhachHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                posselected = position;
                return false;
            }
        });
    }
}