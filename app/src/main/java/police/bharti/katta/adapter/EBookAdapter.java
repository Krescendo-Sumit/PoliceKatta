package police.bharti.katta.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import police.bharti.katta.R;
import police.bharti.katta.model.BookModel;
import police.bharti.katta.model.EBookModel;
import police.bharti.katta.util.Constants;
import police.bharti.katta.view.pdfviewer.PDFFileViewer;
import police.bharti.katta.view.pdfviewer.PDFViewForEBook;

public class EBookAdapter  extends  RecyclerView.Adapter<EBookAdapter.DataObjectHolder>{
    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<EBookModel> bhartiModelArrayList=null;

    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public EBookAdapter(ArrayList<EBookModel> productModels, Context context) {

        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;

    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_books, parent, false);

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
        //  if (mSellerProductlist.size() > 0) {
        return bhartiModelArrayList.size();
        //} else {
        //  return 0;
        // }
    }




    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            EBookModel bookModel=bhartiModelArrayList.get(position);
            holder.price.setText(bookModel.getTitle());

            String url= Constants.BASE_URL+bookModel.getImagepath();
            String serverpath = Constants.BASE_URL+"no-image.png";
             Log.i("DefaultPath",url);
            String data = "<html><head><meta name=\"viewport\"\"content=\"width='100%', initial-scale=0.65 \" /></head>";
            data = data + "<body><center>" +
                    "<img width=\"100%\" height='150px' src=\"" + url + "\" " +
                    "onerror=\"this.onerror=null; this.src='" + serverpath + "'\"/>" +
                    "" +
                    "</center></body></html>";
            holder.img_web.getSettings().setJavaScriptEnabled(true);
            holder.img_web.loadData(data, "text/html", "UTF-8");
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            /*        Preferences.save(context,Preferences.SELECTEDCHALUGHADAMODIID,bookModel.getId());
                    Intent intent=new Intent(context, BhartiDetailsList.class);
                    context.startActivity(intent);*/
                    Intent intent=new Intent(context, PDFViewForEBook.class);
                    intent.putExtra("fname",bookModel.getFilepath());
                    context.startActivity(intent);

                }
            });
            holder.txt_rate.setText(""+bookModel.getRate());
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
        TextView txt_rate;
        public DataObjectHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.txt_title);
            txt_rate = (TextView) itemView.findViewById(R.id.txt_details);
            imageView=(ImageView)itemView.findViewById(R.id.img_bhartilogo);
            ll=(LinearLayout)itemView.findViewById(R.id.ll);
            img_web=(WebView) itemView.findViewById(R.id.img_web);
        }
    }

}
