package police.bharti.katta.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import police.bharti.katta.R;
import police.bharti.katta.model.BhartiModel;
import police.bharti.katta.util.Constants;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.view.bhartidetails.BhartiDetailsList;
import police.bharti.katta.view.pdfviewer.ContentViewer;
import police.bharti.katta.view.pdfviewer.PDFFileViewer;

public class BhartiAdapter extends  RecyclerView.Adapter<BhartiAdapter.DataObjectHolder>{


    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<BhartiModel> bhartiModelArrayList=null;

    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public BhartiAdapter(ArrayList<BhartiModel> productModels, Context context) {

        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;

    }

    @NonNull
    @Override
    public BhartiAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_listofexams, parent, false);

        return new BhartiAdapter.DataObjectHolder(view);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        //  if (mSellerProductlist.size() > 0) {
        return bhartiModelArrayList.size();
        //} else {
        //  return 0;
        // }
    }




    @Override
    public void onBindViewHolder(final BhartiAdapter.DataObjectHolder holder, final int position) {
        try {
            BhartiModel bhartiModel=bhartiModelArrayList.get(position);
            holder.price.setText(bhartiModel.getTitle());

            String url= Constants.BASE_URL+bhartiModel.getImagepath();
            String serverpath = Constants.BASE_URL+"no-image.png";
            // Log.i("DefaultPath",serverpath);
            String data = "<html><head><meta name=\"viewport\"\"content=\"width='100%', initial-scale=0.65 \" /></head>";
            data = data + "<body><center>" +
                    "<img width=\"100%\" height='150px' src=\"" + url + "\" " +
                    "onerror=\"this.onerror=null; this.src='" + serverpath + "'\"/>" +
                    "" +
                    "</center></body></html>";
            //  Log.i("WebViewData","====="+data);
            holder.img_web.getSettings().setJavaScriptEnabled(true);
            holder.img_web.loadData(data, "text/html", "UTF-8");

            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fn=bhartiModel.getFilepath();
                    String d=fn.substring(fn.lastIndexOf("/")+1,fn.length());
                  //  Toast.makeText(context, ""+d.trim(), Toast.LENGTH_SHORT).show();
d="1";
                    if(d!=null) {

                        if (d.trim().equals("")) {
                            Toast.makeText(context, "File Missing.", Toast.LENGTH_SHORT).show();
                        } else {


                         //   Preferences.save(context, Preferences.SELECTEDEXAMID, bhartiModel.getId());
                           // Intent intent = new Intent(context, PDFFileViewer.class);
                            Intent intent = new Intent(context, ContentViewer.class);
                            intent.putExtra("fname", bhartiModel.getFilepath());
                            intent.putExtra("data", bhartiModel.getDetails());
                            context.startActivity(intent);
                        }
                    }else
                    {
                        Toast.makeText(context, "File Missing", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }catch(Exception e)
        {
            Log.i("Error ",e.getMessage());
        }
    }





    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView totalQuantity, price, txtQtyUnit;
        ImageView imageView;
        LinearLayout ll;
        WebView img_web;
        public DataObjectHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.txt_title);
            imageView=(ImageView)itemView.findViewById(R.id.img_bhartilogo);
            ll=(LinearLayout)itemView.findViewById(R.id.ll);
            img_web=(WebView) itemView.findViewById(R.id.img_web);
        }
    }


}
