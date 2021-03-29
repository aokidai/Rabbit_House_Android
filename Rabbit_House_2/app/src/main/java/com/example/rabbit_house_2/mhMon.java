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

public class mhMon extends Activity {
    ListView lstMon;
    Button btnThem;
    ArrayList<Mon> dsMon = new ArrayList<Mon>();
    MyAdapterMon adapter;
    SQLiteDatabase db;
    int posselected = -1;
    public static final int OPEN_MON = 113;
    public static final int EDIT_MON = 114;
    public static final int SAVE_MON = 115;
    private void getDSMon(){
        try{
            dsMon.add(new Mon("Tên Món", "Loại", "Giá"));
            db = openOrCreateDatabase(mhLogin.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.query("tbmon", null, null,null,null,null,null);
            c.moveToFirst();
            while (!c.isAfterLast()){
                dsMon.add(new Mon(c.getInt(0)+"", c.getString(1).toString(), c.getString(2).toString(), c.getString(3).toString()));
                c.moveToNext();
            }
            adapter = new MyAdapterMon(this, android.R.layout.simple_list_item_1, dsMon);
            lstMon.setAdapter(adapter);
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
                String idKH = dsMon.get(posselected).getId_Mon();
                if(db.delete("tbmon", "idMon=?", new String[]{idKH})!=-1){
                    dsMon.remove(posselected);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplication(), "Xóa món thành công!", Toast.LENGTH_LONG).show();
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
                Mon mon = dsMon.get(posselected);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(mhMon.this, EditMon.class);
                bundle.putSerializable("mon", (Serializable) mon);
                intent.putExtra("data", bundle);
                startActivityForResult(intent, mhMon.EDIT_MON);
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

            case mhMon.EDIT_MON:
                if (requestCode == mhMon.SAVE_MON){
                    Bundle bundle = data.getBundleExtra("data");
                    Mon mon = (Mon)bundle.getSerializable("mon");
                    dsMon.set(posselected, mon);
                    adapter.notifyDataSetChanged();
                }
                break;
            case mhMon.OPEN_MON:
                if(resultCode == mhMon.SAVE_MON){
                    Bundle bundle = data.getBundleExtra("data");
                    Mon mon = (Mon)bundle.getSerializable("mon");
                    dsMon.add(mon);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mh_mon);
        lstMon = (ListView) findViewById(R.id.lvMon);
        btnThem = (Button) findViewById(R.id.btnThemMon);
        getDSMon();
        registerForContextMenu(lstMon);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mhMon.this, InsertMon.class);
                startActivityForResult(intent, mhKhachHang.OPEN_KH);
            }
        });
        lstMon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                posselected = position;
                return false;
            }
        });
    }
}