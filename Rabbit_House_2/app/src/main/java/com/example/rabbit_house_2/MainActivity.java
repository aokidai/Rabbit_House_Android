package com.example.rabbit_house_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnBanHang, btnThemKH, btnMon;
    TextView tvSignout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBanHang = (Button) findViewById(R.id.btnSelf);
        btnThemKH = (Button) findViewById(R.id.btnCostmermng);
        btnMon = (Button) findViewById(R.id.btnQLMon);
        tvSignout = (TextView) findViewById(R.id.btnSignout);
        btnBanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent banhang = new Intent(MainActivity.this, mhHoaDong.class);
                startActivity(banhang);
            }
        });
        btnThemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent khachhang = new Intent(MainActivity.this, mhKhachHang.class);
                startActivity(khachhang);
            }
        });
        btnMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mon = new Intent(MainActivity.this, mhMon.class);
                startActivity(mon);
            }
        });
        tvSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signout = new Intent(MainActivity.this, mhLogin.class);
                startActivity(signout);
            }
        });
    }
}