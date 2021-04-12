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

public class InsertKhachHang extends Activity {
    Button btnSave, btnDel, btnExit;
    EditText edtTenKH, edtSoDT;
    SQLiteDatabase db;
    private void initWidget(){
        btnSave = (Button) findViewById(R.id.btnLuuInsertKH);
        btnDel = (Button) findViewById(R.id.btnXoaInsertKH);
        btnExit = (Button) findViewById(R.id.btnDongInsertKH);
        edtTenKH = (EditText) findViewById(R.id.txtTenKH);
        edtSoDT = (EditText) findViewById(R.id.txtSoDT);
    }
    private long saveKH(){
        try{
            db = openOrCreateDatabase(mhLogin.DATABASE_NAME, MODE_PRIVATE, null);
            ContentValues values = new ContentValues();
            values.put("TenKH", edtTenKH.getText().toString());
            values.put("SDT", edtSoDT.getText().toString());
            long id = db.insert("tblKhachhang", null, values);
            if (id!=-1){
                return id;
            }
        }catch (Exception ex){
            Toast.makeText(this, "Thêm khách hàng lỗi!", Toast.LENGTH_LONG).show();
        }
        return -1;
    }
    private void delKH(){
        edtTenKH.setText("");
        edtSoDT.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_khach_hang);
        initWidget();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = saveKH();
                Bundle bundle = new Bundle();
                Intent intent = getIntent();
                if(id!=-1){
                    Khach kh = new Khach(id + "", edtTenKH.getText().toString(), edtSoDT.getText().toString());
                    bundle.putSerializable("khach", (Serializable) kh);
                    intent.putExtra("data", bundle);
                    setResult(mhKhachHang.SAVE_KH, intent);
                    Toast.makeText(getApplication(), "Thêm Khách hàng thành công", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delKH();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify.exit(InsertKhachHang.this);
            }
        });
    }
}