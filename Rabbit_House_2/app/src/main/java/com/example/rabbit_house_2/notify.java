package com.example.rabbit_house_2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class notify {
    public static void exit (Context context){
        AlertDialog.Builder alterDialogBuilder = new AlertDialog.Builder(context);
        alterDialogBuilder.setTitle("Xác nhận thoát?");
        //alterDialogBuilder.setIcon(R.drawable.question);
        alterDialogBuilder.setMessage("Ban có muốn thoát?");
        alterDialogBuilder.setCancelable(false);
        alterDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                System.exit(1);
            }
        });
        alterDialogBuilder.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alterDialogBuilder.create();
        alertDialog.show();
    }
}
