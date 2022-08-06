package police.bharti.katta.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import police.bharti.katta.R;

public class InfoDialog {
    static Context context;
    static Dialog dialog;
    public InfoDialog(Context context)
    {
        this.context=context;
    }
    public  void showDialog(String title,String message,int s)
    {
        dialog=new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_information);
        dialog.setTitle(title);
        TextView textView=dialog.findViewById(R.id.txt_details);
        TextView textTitle=dialog.findViewById(R.id.txt_title);
        ImageView btn_close=dialog.findViewById(R.id.close);
        Button btn_purchase=dialog.findViewById(R.id.btn_purchase);
        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                if(s==1)
                {
                    ((Activity)context).finish();
                }
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        textView.setText(message);
        textTitle.setText(title);
        dialog.show();
    }
}
