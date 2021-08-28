package police.bharti.katta.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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
import police.bharti.katta.model.ImportantNotesMenuModel;
import police.bharti.katta.util.Constants;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.view.mahatvacyanotes.ImportantItemList;
import police.bharti.katta.view.saravmenu.SaravQuestions;

public class ImportantNotesMenuAdapter extends  RecyclerView.Adapter<ImportantNotesMenuAdapter.DataObjectHolder> {


    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<ImportantNotesMenuModel> bhartiModelArrayList=null;

    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public ImportantNotesMenuAdapter(ArrayList<ImportantNotesMenuModel> productModels, Context context) {

        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;

    }

    @NonNull
    @Override
    public ImportantNotesMenuAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_listofexams, parent, false);

        return new ImportantNotesMenuAdapter.DataObjectHolder(view);
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
    public void onBindViewHolder(final ImportantNotesMenuAdapter.DataObjectHolder holder, final int position) {
        try {
            ImportantNotesMenuModel ImportantNotesMenuModel=bhartiModelArrayList.get(position);
            holder.price.setText(ImportantNotesMenuModel.getTitle());
            //holder.txt_details.setText(ImportantNotesMenuModel.get());
            String url= Constants.BASE_URL+ImportantNotesMenuModel.getImagepath();
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

                    Toast.makeText(context, "ImportantItemList", Toast.LENGTH_SHORT).show();
                    Preferences.save(context,Preferences.SELECTEDIMPMENU,ImportantNotesMenuModel.getId());
                    Intent intent=new Intent(context, ImportantItemList.class);
                    context.startActivity(intent);

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
        public DataObjectHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.txt_title);
            imageView=(ImageView)itemView.findViewById(R.id.img_bhartilogo);
            txt_details= (TextView) itemView.findViewById(R.id.txt_details);
            ll=(LinearLayout)itemView.findViewById(R.id.ll);
            img_web=(WebView) itemView.findViewById(R.id.img_web);
        }
    }



}
