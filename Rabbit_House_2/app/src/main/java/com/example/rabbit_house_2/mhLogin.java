package com.example.rabbit_house_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class mhLogin extends AppCompatActivity {
    public static final String DATABASE_NAME = "Rabbit_House.db";
    SQLiteDatabase db;
    Button btnDangNhap, btnThoat;
    TextView tvForgotpass, tvSaipass;
    EditText edtUsername, edtPass;

    private void initDB(){
        db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        String sql;
        try{
            if(!isTableExists(db, "tbluser")){
                sql = "CREATE TABLE tbluser (idUser TEXT NOT NULL PRIMARY KEY,";
                sql += "Username TEXT NOT NULL,";
                sql += "Password TEXT NOT NULL);";
                db.execSQL(sql);
                sql = "insert into tbluser(idUser, Username, Password) values ('NV0001', 'admin', '123')";
                db.execSQL(sql);
            }
            if (!isTableExists(db, "tbmon")){
                sql = "CREATE TABLE tbmon (idMon INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql += "TenMon TEXT NOT NULL,";
                sql += "Loai TEXT NOT NULL,";
                sql += "Gia TEXT NOT NULL);";
                db.execSQL(sql);
                sql = "insert into tbmon(TenMon, Loai, Gia) values ('Cafe', 'Cafe', '20000')";
                db.execSQL(sql);
                sql = "insert into tbmon(TenMon, Loai, Gia) values ('Mira', 'Ăn Vặt', '15000')";
                db.execSQL(sql);
                sql = "insert into tbmon(TenMon, Loai, Gia) values ('Coca', 'Nước Ngọt', '20000')";
                db.execSQL(sql);
            }
            if (!isTableExists(db, "tblKhachhang")){
                sql = "CREATE TABLE tblKhachhang (idKH INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql += "TenKH TEXT NOT NULL,";
                sql += "SDT TEXT);";
                db.execSQL(sql);
                sql = "insert into tblKhachhang(TenKH, SDT) values ('Trần Tiến Đú', '0123456789')";
                db.execSQL(sql);
                sql = "insert into tblKhachhang(TenKH, SDT) values ('Nguyễn Anh Hùng', '0685739480')";
                db.execSQL(sql);
                sql = "insert into tblKhachhang(TenKH, SDT) values ('Nguyễn Anh Quân', '0086542480')";
                db.execSQL(sql);
            }
            if (!isTableExists(db, "tblhoadon")){
                sql = "CREATE TABLE tblHoadon (idHoadon INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql += "idKH INTERGER NOT NULL,";
                sql += "idMon INTERGER NOT NULL,";
                sql += "SoLuong TEXT NOT NULL);";
                db.execSQL(sql);
                sql = "insert into tblhoadon(idKH, idMon, SoLuong) values ('1', '2', '1')";
                db.execSQL(sql);
                sql = "insert into tblhoadon(idKH, idMon, SoLuong) values ('3', '3', '1')";
                db.execSQL(sql);
            }
        }catch (Exception ex){
            Toast.makeText(this, "Khởi tạo dữ liệu lỗi", Toast.LENGTH_LONG).show();
        }
    }
    private boolean isTableExists(SQLiteDatabase database, String tableName){
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name "+"= '"+tableName+"'", null);
        if (cursor != null){
            if(cursor.getCount()>0){
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
    private boolean isUser(String username, String password){
        try {
            db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.rawQuery("select * from tbluser where username=? and password=?", new String[] {username, password});
            c.moveToFirst();
            if (c.getCount()>0)
                return true;
        }catch (Exception ex){
            Toast.makeText(this, "Lỗi hệ thống", Toast.LENGTH_LONG).show();
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mh_login);
        btnDangNhap = (Button) findViewById(R.id.btnLogin);
        btnThoat = (Button) findViewById(R.id.btnExit);
        tvSaipass = (TextView) findViewById(R.id.tvSaipass);
        tvForgotpass = (TextView) findViewById(R.id.btnForgot);
        edtUsername = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);
        initDB();

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPass.getText().toString();
                tvSaipass.setText("");
                if (username.isEmpty())
                {
                    Toast.makeText(getApplication(), "Xin vui lòng nhập tài khoản", Toast.LENGTH_LONG).show();
                    edtUsername.requestFocus();
                }
                else if(password.isEmpty())
                {
                    Toast.makeText(getApplication(), "Xin vui lòng nhập tài khoản", Toast.LENGTH_LONG).show();
                    edtPass.requestFocus();
                }
                else if(isUser(edtUsername.getText().toString(), edtPass.getText().toString())){
                    Intent intent = new Intent(mhLogin.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplication(), "Sai Thông tin đăng nhập!", Toast.LENGTH_LONG).show();
                    tvSaipass.setText("Sai Thông tin ĐN!");
                }
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvForgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent forgot = new Intent(mhLogin.this, mhForgotPass.class);
//                startActivity(forgot);
                tvSaipass.setText("Liên hệ Admin");
                Toast.makeText(getApplication(), "Liên hệ Admin để được giải quyết!", Toast.LENGTH_LONG).show();
            }
        });
    }
}