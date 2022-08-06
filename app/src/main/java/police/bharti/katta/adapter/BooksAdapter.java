package police.bharti.katta.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import police.bharti.katta.R;
import police.bharti.katta.model.BookModel;
import police.bharti.katta.model.ChaluGhadamodiModel;
import police.bharti.katta.util.Constants;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.view.bhartidetails.BhartiDetailsList;
import police.bharti.katta.view.pdfviewer.PDFFileViewer;
import police.bharti.katta.view.pdfviewer.PDFViewForEBook;

public class BooksAdapter extends  RecyclerView.Adapter<BooksAdapter.DataObjectHolder> {
    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<BookModel> bhartiModelArrayList=null;

    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public BooksAdapter(ArrayList<BookModel> productModels, Context context) {

        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;

    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_books_kharedi, parent, false);

        return new DataObjectHolder(view);
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
             return bhartiModelArrayList.size();
    }
    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            BookModel bookModel=bhartiModelArrayList.get(position);
            holder.price.setText(" नाव : "+bookModel.getTitle());
            holder.txt_details.setText(Html.fromHtml(bookModel.getDetails()));
            holder.txt_writer.setText(Html.fromHtml("<b>लेखक :</b>"+bookModel.getWriter()));
            holder.txt_price.setText(Html.fromHtml("<b>किंमत :</b>"+bookModel.getPrice()));
            holder.txt_pages.setText(Html.fromHtml("<b>एकूण पाने  :</b>"+bookModel.getPages()));
            holder.txt_discountprice.setText(Html.fromHtml("<b>Discount Price :</b>"+bookModel.getDiscountprize()));
            String url= Constants.BASE_URL+bookModel.getImagepath();
            String serverpath = Constants.BASE_URL+"no-image.png";
            Log.i("DefaultPath",url);
            String data = "<html><head><meta name=\"viewport\"\"content=\"width='100%', initial-scale=0.65 \" /></head>";
            data = data + "<body><center>" +
                    "<img width=\"100%\" height='110px' src=\"" + url + "\" " +
                    "onerror=\"this.onerror=null; this.src='" + serverpath + "'\"/>" +
                    "" +
                    "</center></body></html>";
            holder.img_web.getSettings().setJavaScriptEnabled(true);
            holder.img_web.loadData(data, "text/html", "UTF-8");

            holder.btn_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bookModel.getLink()!=null) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bookModel.getLink()));
                        context.startActivity(browserIntent);
                    }else
                    {
                        Toast.makeText(context, "Link not available.", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            /*      Preferences.save(context,Preferences.SELECTEDCHALUGHADAMODIID,bookModel.getId());
                    Intent intent=new Intent(context, BhartiDetailsList.class);
                    context.startActivity(intent);*/
                    Dialog dialog=new Dialog(context);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.popup_showbookdetails);

                    TextView textView=dialog.findViewById(R.id.txt_details);


                    ImageView btn_close=dialog.findViewById(R.id.close);
                    Button btn_purchase=dialog.findViewById(R.id.btn_purchase);
                    btn_purchase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(bookModel.getLink()!=null) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bookModel.getLink()));
                                context.startActivity(browserIntent);
                            }else
                            {
                                Toast.makeText(context, "Link not available.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    btn_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    textView.setText(Html.fromHtml("<pre>"+bookModel.getDetails()+"</pre>"));
                    dialog.show();
                }
            });

           // holder.txt_title2.setTextColor(Color.RED);
            holder.txt_title2.setText("View Sample Book");
            holder.txt_title2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fn=bookModel.getFname();
                    String d=fn.substring(fn.lastIndexOf("/")+1,fn.length());
                    if(d!=null) {

                        if(d.trim().equals(""))
                        {
                            Toast.makeText(context, "File Missing.", Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent = new Intent(context, PDFFileViewer.class);
                            intent.putExtra("fname", bookModel.getFname());
                            context.startActivity(intent);
                        }
                    }else
                    {
                        Toast.makeText(context, "File Missing.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }catch(Exception e)
        {
            Log.i("Error ",e.getMessage());
        }
    }





    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView totalQuantity, price, txt_details;
        ImageView imageView;
        LinearLayout ll;
        WebView img_web;
        TextView txt_price,txt_writer,txt_discountprice,txt_pages;
        TextView txt_title2;
        Button btn_buy;
        public DataObjectHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.txt_title);
            txt_details = (TextView) itemView.findViewById(R.id.txt_details);
            imageView=(ImageView)itemView.findViewById(R.id.img_bhartilogo);
            ll=(LinearLayout)itemView.findViewById(R.id.ll);
            img_web=(WebView) itemView.findViewById(R.id.img_web);
            txt_price=itemView.findViewById(R.id.txt_price);
            txt_writer=itemView.findViewById(R.id.txt_writer);
            txt_discountprice=itemView.findViewById(R.id.txt_discountprize);
            txt_pages=itemView.findViewById(R.id.txt_pages);
            txt_title2=itemView.findViewById(R.id.txt_title2);
            btn_buy=itemView.findViewById(R.id.btn_buy);


        }
    }

}
