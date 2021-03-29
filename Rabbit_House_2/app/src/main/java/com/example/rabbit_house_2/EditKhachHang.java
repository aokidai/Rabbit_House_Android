package com.example.rabbit_house_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class EditKhachHang extends Activity {
    Button btnSave, btnDel, btnExit;
    EditText edtTenKH, edtSoDT;
    String id_KH;
    private void initWidget(){
        btnSave = (Button) findViewById(R.id.btnLuuEditKH);
        btnDel = (Button) findViewById(R.id.btnXoaEdittKH);
        btnExit = (Button) findViewById(R.id.btnDongEditKH);
        edtTenKH = (EditText) findViewById(R.id.txtEditTenKH);
        edtSoDT = (EditText) findViewById(R.id.txtEditSoDT);
    }
    private void getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        Khach khach = (Khach) bundle.getSerializable("khach");
        id_KH = khach.getIdKH();
        edtTenKH.setText(khach.getTen());
        edtSoDT.setText(khach.getSdt());
    }
    private boolean luuKH(){
        try{
            SQLiteDatabase db = openOrCreateDatabase(mhLogin.DATABASE_NAME, MODE_PRIVATE, null);
            ContentValues values = new ContentValues();
            values.put("TenKH", edtTenKH.getText().toString());
            values.put("SDT", edtSoDT.getText().toString());
            if (db.update("tblKhachhang", values, "idKH=?", new String[]{id_KH})!=-1){
                return true;
            }
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Cập nhật Khách hàng thành công!", Toast.LENGTH_LONG).show();
        }
        return false;
    }
    private void xoaKH(){
        edtTenKH.setText("");
        edtSoDT.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_khach_hang);
        initWidget();
        getData();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = getIntent();
                if (luuKH()){
                    Khach kh = new Khach(id_KH, edtTenKH.getText().toString(), edtSoDT.getText().toString());
                    bundle.putSerializable("khach", (Serializable) kh);
                    intent.putExtra("data", bundle);
                    setResult(mhKhachHang.SAVE_KH, intent);
                    Toast.makeText(getApplication(), "Cập nhật thông tin thành công", Toast.LENGTH_LONG).show();
                    //finish();
                    Intent abc = new Intent(EditKhachHang.this, mhMon.class);
                    startActivity(abc);
                }
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaKH();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify.exit(EditKhachHang.this);
            }
        });
    }
}