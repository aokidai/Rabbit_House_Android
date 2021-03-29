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

public class InsertMon extends Activity {
    Button btnSave, btnDel, btnExit;
    EditText edtTenMon, edtLoai, edtGia;
    SQLiteDatabase db;
    private void initWidget(){
        btnSave = (Button) findViewById(R.id.btnLuuInsertMon);
        btnDel = (Button) findViewById(R.id.btnXoaInsertMon);
        btnExit = (Button) findViewById(R.id.btnDongInsertMon);
        edtTenMon = (EditText) findViewById(R.id.txtTenMon);
        edtLoai = (EditText) findViewById(R.id.txtLoai);
        edtGia = (EditText) findViewById(R.id.txtGia);
    }
    private long saveMon(){
        try{
            db = openOrCreateDatabase(mhLogin.DATABASE_NAME, MODE_PRIVATE, null);
            ContentValues values = new ContentValues();
            values.put("TenMon", edtTenMon.getText().toString());
            values.put("Loai", edtLoai.getText().toString());
            values.put("Gia", edtGia.getText().toString());
            long id = db.insert("tbmon", null, values);
            if (id!=-1){
                return id;
            }
        }catch (Exception ex){
            Toast.makeText(this, "Thêm món lỗi!", Toast.LENGTH_LONG).show();
        }
        return -1;
    }
    private void delMon(){
        edtTenMon.setText("");
        edtLoai.setText("");
        edtGia.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_mon);
        initWidget();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = saveMon();
                Bundle bundle = new Bundle();
                Intent intent = getIntent();
                if(id!=-1){
                    Mon mon = new Mon(id + "", edtTenMon.getText().toString(), edtLoai.getText().toString(), edtGia.getText().toString());
                    bundle.putSerializable("mon", (Serializable) mon);
                    intent.putExtra("data", bundle);
                    setResult(mhMon.SAVE_MON, intent);
                    Toast.makeText(getApplication(), "Thêm Món thành công", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delMon();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify.exit(InsertMon.this);
            }
        });
    }
}