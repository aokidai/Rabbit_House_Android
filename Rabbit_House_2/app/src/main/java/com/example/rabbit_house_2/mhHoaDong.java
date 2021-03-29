package com.example.rabbit_house_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class mhHoaDong extends Activity {
    ListView lstHoaDon;
    Button btnThem;
    SQLiteDatabase db;
    ArrayList<HoaDon> dsHoaDon = new ArrayList<HoaDon>();
    MyAdapterHD adapter;
    int posselected = -1;
    public static final int OPEN_HD = 113;
    public static final int EDIT_HD = 114;
    public static final int SAVE_HD = 115;
    private void getDSHoaDon(){
        dsHoaDon.add(new HoaDon("Khách", "Món", "Số lượng"));
        db = openOrCreateDatabase(mhLogin.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor c = db.rawQuery("select tblKhachhang.TenKH, tbmon.TenMon, tblhoadon.SoLuong from tblKhachhang, tbmon, tblhoadon where tblKhachhang.idKH = tblhoadon.idKH and tbmon.idMon = tblhoadon.idMon", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            dsHoaDon.add(new HoaDon(c.getString(0).toString(), c.getString(1).toString(),c.getString(2).toString()));
            c.moveToNext();
        }
        adapter = new MyAdapterHD(this, android.R.layout.simple_list_item_1, dsHoaDon);
        lstHoaDon.setAdapter(adapter);
    }
    public void comfirmDelete(){
        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Xác nhận để xóa...");
        alertDialogBuilder.setIcon(R.drawable.question);
        alertDialogBuilder.setMessage("Bạn có chắc xóa?");
        alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db = openOrCreateDatabase(mhLogin.DATABASE_NAME, MODE_PRIVATE, null);
                String idHoadon = dsHoaDon.get(posselected).getIdKH();
                if (db.delete("tblHoadon", "idHoadon=?", new String[]{idHoadon})!=-1);{
                    dsHoaDon.remove(posselected);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplication(), "Xoá hóa đơn thành công!", Toast.LENGTH_LONG).show();
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnu2, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuudelete:
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
            case mhHoaDong.OPEN_HD:
                if (mhHoaDong.SAVE_HD==requestCode){
                    Bundle bundle = data.getBundleExtra("data");
                    HoaDon hoadon = (HoaDon)bundle.getSerializable("hoadon");
                    dsHoaDon.add(hoadon);
                    adapter.notifyDataSetChanged();
                }
                break;
            case mhHoaDong.EDIT_HD:
                Bundle bundle = data.getBundleExtra("data");
                HoaDon hoadon = (HoaDon) bundle.getSerializable("hoadon");
                dsHoaDon.set(posselected, hoadon);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mh_hoa_dong);
        btnThem = (Button) findViewById(R.id.btnThemHD);
        lstHoaDon = (ListView) findViewById(R.id.lvHD);
        getDSHoaDon();
        registerForContextMenu(lstHoaDon);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mhHoaDong.this, InsertHoaDon.class);
                startActivityForResult(intent, mhHoaDong.OPEN_HD);
            }
        });
        lstHoaDon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                posselected = position;
                return false;
            }
        });
    }
}