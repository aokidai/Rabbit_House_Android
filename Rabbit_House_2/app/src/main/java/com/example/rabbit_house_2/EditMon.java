package com.example.rabbit_house_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class EditMon extends Activity {
    Button btnSave, btnDel, btnExit;
    EditText edtTenMon, edtLoai, edtGia;
    String id_Mon;
    private void initWidget(){
        btnSave = (Button) findViewById(R.id.btnLuuEditMon);
        btnDel = (Button) findViewById(R.id.btnXoaEditMon);
        btnExit = (Button) findViewById(R.id.btnDongEditMon);
        edtTenMon = (EditText) findViewById(R.id.txtEditTenMon);
        edtLoai = (EditText) findViewById(R.id.txtEditLoai);
        edtGia = (EditText) findViewById(R.id.txtEditGia);
    }
    private void getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        Mon mon = (Mon) bundle.getSerializable("mon");
        id_Mon = mon.getId_Mon();
        edtTenMon.setText(mon.getTenMon());
        edtLoai.setText(mon.getLoai());
        edtGia.setText(mon.getGia());
    }
    private boolean luuMon(){
        try{
            SQLiteDatabase db = openOrCreateDatabase(mhLogin.DATABASE_NAME, MODE_PRIVATE, null);
            ContentValues values = new ContentValues();
            values.put("TenMon", edtTenMon.getText().toString());
            values.put("Loai", edtLoai.getText().toString());
            values.put("Gia", edtGia.getText().toString());
            if (db.update("tbmon", values, "idMon=?", new String[]{id_Mon})!=-1){
                return true;
            }
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Cập nhật Món thành công!", Toast.LENGTH_LONG).show();
        }
        return false;
    }
    private void xoaMon(){
        edtTenMon.setText("");
        edtLoai.setText("");
        edtGia.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mon);
        initWidget();
        getData();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = getIntent();
                if (luuMon()){
                    Mon mon = new Mon(id_Mon, edtTenMon.getText().toString(), edtLoai.getText().toString(), edtGia.getText().toString());
                    bundle.putSerializable("mon", (Serializable) mon);
                    intent.putExtra("data", bundle);
                    //startActivityForResult(intent, mhMon.SAVE_MON);
                    setResult(mhMon.SAVE_MON, intent);
                    Toast.makeText(getApplication(), "Cập nhật thông tin thành công", Toast.LENGTH_LONG).show();
                    Intent abc = new Intent(EditMon.this, mhMon.class);
                    startActivity(abc);
                    //finish();
                }
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaMon();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify.exit(EditMon.this);
            }
        });
    }
}