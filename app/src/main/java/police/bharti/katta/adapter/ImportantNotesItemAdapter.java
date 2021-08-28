package police.bharti.katta.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import police.bharti.katta.model.ImportantNoteItemModel;
import police.bharti.katta.model.ImportantNoteItemModel;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.view.pdfviewer.PDFFileViewer;
import police.bharti.katta.view.saravmenu.SaravQuestions;

public class ImportantNotesItemAdapter extends  RecyclerView.Adapter<ImportantNotesItemAdapter.DataObjectHolder> {


    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<ImportantNoteItemModel> bhartiModelArrayList=null;

    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public ImportantNotesItemAdapter(ArrayList<ImportantNoteItemModel> productModels, Context context) {

        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;

    }

    @NonNull
    @Override
    public ImportantNotesItemAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new ImportantNotesItemAdapter.DataObjectHolder(view);
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
    public void onBindViewHolder(final ImportantNotesItemAdapter.DataObjectHolder holder, final int position) {
        try {
            ImportantNoteItemModel ImportantNoteItemModel=bhartiModelArrayList.get(position);
            holder.price.setText(ImportantNoteItemModel.getTitle());
             holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Preferences.save(context,Preferences.SELECTEDSARAVID,ImportantNoteItemModel.getId());
                    Intent intent=new Intent(context, PDFFileViewer.class);
                    intent.putExtra("fname",ImportantNoteItemModel.getFilepath());
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

        LinearLayout ll;
        public DataObjectHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.txt_title);

            txt_details= (TextView) itemView.findViewById(R.id.txt_details);
            ll=(LinearLayout)itemView.findViewById(R.id.ll);

        }
    }



}
