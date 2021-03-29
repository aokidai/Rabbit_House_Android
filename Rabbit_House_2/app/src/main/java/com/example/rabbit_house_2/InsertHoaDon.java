package com.example.rabbit_house_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class InsertHoaDon extends Activity {
    Button btnSave, btnDel, btnExit;
    Spinner spTenKH, spMon;
    EditText edtSoLuong;
    SQLiteDatabase db;
    ArrayList<Khach> dsKhachHang = new ArrayList<Khach>();
    ArrayAdapter<Khach> adapter;
    ArrayList<Mon> dsMon = new ArrayList<Mon>();
    ArrayAdapter<Mon> adapter2;
    int posSpinner = -1;
    int posSpinner2 = -1;
    private void initWidget(){
        btnSave = (Button) findViewById(R.id.btnLuuInsertHD);
        btnDel = (Button) findViewById(R.id.btnXoaInsertHD);
        btnExit = (Button) findViewById(R.id.btnDongInsertHD);
        spTenKH = (Spinner) findViewById(R.id.spTenKH);
        spMon = (Spinner) findViewById(R.id.spMon);
        edtSoLuong = (EditText) findViewById(R.id.txtHDSoLuong);
    }
    private void getKhachHang(){
        try{
            db = openOrCreateDatabase(mhLogin.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.query("tblKhachhang", null, null, null, null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()){
                dsKhachHang.add(new Khach(c.getInt(0)+"", c.getString(1).toString(), c.getString(2).toString()));
                c.moveToNext();
            }
            adapter = new ArrayAdapter<Khach>(this, android.R.layout.simple_spinner_item, dsKhachHang);
            adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            spTenKH.setAdapter(adapter);
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Lỗi "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void getMon(){
        try{
            db = openOrCreateDatabase(mhLogin.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.query("tbmon", null, null, null, null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()){
                dsMon.add(new Mon(c.getInt(0)+"", c.getString(1).toString(), c.getString(2).toString(), c.getString(3).toString()));
                c.moveToNext();
            }
            adapter2 = new ArrayAdapter<Mon>(this, android.R.layout.simple_spinner_item, dsMon);
            adapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            spMon.setAdapter(adapter2);
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Lỗi "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private long luuHoaDon(){
        db = openOrCreateDatabase(mhLogin.DATABASE_NAME, MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        try{
            values.put("idKH", dsKhachHang.get(posSpinner).getIdKH());
            values.put("idMon", dsMon.get(posSpinner2).getId_Mon());
            values.put("SoLuong", edtSoLuong.getText().toString());
            return (db.insert("tblHoadon", null, values));
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Lỗi "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return -1;
    }
    private void xoaHoaDon(){
        edtSoLuong.setText("");
        spTenKH.setSelection(0);
        spMon.setSelection(0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_hoa_don);
        initWidget();
        getKhachHang();
        getMon();
        spTenKH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posSpinner = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spMon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posSpinner2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify.exit(InsertHoaDon.this);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = luuHoaDon();
                if (id!=-1){
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    HoaDon hoadon = new HoaDon(dsKhachHang.get(posSpinner).getIdKH(), dsMon.get(posSpinner2).getId_Mon(), edtSoLuong.getText().toString());
                    bundle.putSerializable("hoadon", hoadon);
                    intent.putExtra("data", bundle);
                    setResult(mhHoaDong.SAVE_HD, intent);
                    Toast.makeText(getApplication(), "Thêm hóa đơn thành công!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaHoaDon();
            }
        });
    }
}