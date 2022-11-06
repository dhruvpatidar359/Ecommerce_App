package com.example.ecommerce_app.Adapters;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_app.database.DBHelper;
import com.example.ecommerce_app.model.imageModel;
import com.example.ecommerce_app.R;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    public static final int NUMBER_OF_ROWS_AUTO = -1;

    Context context;
    LayoutInflater layoutInflater;
    RecyclerViewItemClickListeners listener;
    List<imageModel> items;
    RecyclerView recyclerView;
    int numberOfRows;
    int rowHeightInPx = 0;
    boolean itemHeightCalculationCompleted = false;

    public ImageAdapter(Context context, List<imageModel> items, RecyclerViewItemClickListeners listener, RecyclerView rv, int rows) {
        super();
        this.context = context;
        this.items = items;
        this.listener = listener;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.recyclerView = rv;
        this.numberOfRows = rows;

        if (this.numberOfRows > 0) {
            this.recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (recyclerView.getMeasuredHeight() > 0) {
                        recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        setRowHeightInPx(recyclerView.getMeasuredHeight() / numberOfRows);
                        itemHeightCalculationCompleted = true;
                        notifyDataSetChanged();
                    }
                }
            });
        } else {
            itemHeightCalculationCompleted = true;
        }

    }

    public int getRowHeightInPx() {
        return rowHeightInPx;
    }

    public void setRowHeightInPx(int rowHeightInPx) {
        this.rowHeightInPx = rowHeightInPx;
    }

    @Override
    public int getItemCount() {
        if (this.items != null && this.itemHeightCalculationCompleted)
            return this.items.size();
        else
            return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View view = this.layoutInflater.inflate(R.layout.item_product_card, parent, false);
        if (getRowHeightInPx() > 0) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            layoutParams.height = getRowHeightInPx();
            layoutParams.width = MATCH_PARENT;
            view.setLayoutParams(layoutParams);
        }
        vh = new GeneralViewHolder(view);
        return vh;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        imageModel page = items.get(position);

        CardView view = ((CardView) ((GeneralViewHolder) holder).getView());

        ((GeneralViewHolder) holder).getTitle().setText(page.getImage_name());
        ((GeneralViewHolder) holder).getImg().setImageResource(page.getImage());
        ((GeneralViewHolder) holder).getPrice().setText(String.format("%d",page.getId()));
        ((GeneralViewHolder) holder).getView().setOnClickListener(this);
        ((GeneralViewHolder) holder).getView().setTag(position);
    }


    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }


    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        this.listener.onRecyclerViewItemClick(this.items, position);
    }

    public class GeneralViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView title;
        ImageView img;


        TextView price;

        public GeneralViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = itemView.findViewById(R.id.item_product_card_pname);
            img = itemView.findViewById(R.id.item_product_card_pimage);
            price = itemView.findViewById(R.id.item_product_card_money);
        }


        public View getView() {
            return view;
        }

        public TextView getPrice() {
            return price;
        }


        public TextView getTitle() {
            return title;
        }

        public ImageView getImg() {
            return img;
        }

    }

    public interface RecyclerViewItemClickListeners {
        void onRecyclerViewItemClick(List<imageModel> items, int position);
    }
}
