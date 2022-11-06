package com.example.ecommerce_app.Adapters;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_app.model.imageModel;
import com.example.ecommerce_app.R;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    Context context;
    LayoutInflater layoutInflater;
    RecyclerViewItemClickListeners listener;
    private final List<imageModel> items;
    RecyclerView recyclerView;
    private final int mNumberOfRows;
    private int mRowHeightInPx = 0;
    boolean itemHeightCalculationCompleted = false;

    public ImageAdapter(Context context, List<imageModel> items, RecyclerViewItemClickListeners listener, RecyclerView rv, int rows) {
        super();
        this.context = context;
        this.items = items;
        this.listener = listener;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.recyclerView = rv;
        this.mNumberOfRows = rows;

        if (this.mNumberOfRows > 0) {
            this.recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onGlobalLayout() {
                    if (recyclerView.getMeasuredHeight() > 0) {
                        recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        setRowHeightInPx(recyclerView.getMeasuredHeight() / mNumberOfRows);
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
        return mRowHeightInPx;
    }

    public void setRowHeightInPx(int mRowHeightInPx) {
        this.mRowHeightInPx = mRowHeightInPx;
    }

    @Override
    public int getItemCount() {
        if (this.items != null && this.itemHeightCalculationCompleted)
            return this.items.size();
        else
            return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        imageModel page = items.get(position);


        ((GeneralViewHolder) holder).getTitle().setText(page.getImage_name());
        ((GeneralViewHolder) holder).getImg().setImageResource(page.getImage());
        ((GeneralViewHolder) holder).getPrice().setText(String.format("%d",page.getId()));
        ((GeneralViewHolder) holder).getView().setOnClickListener(this);
        ((GeneralViewHolder) holder).getView().setTag(position);
    }


    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }


    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        this.listener.onRecyclerViewItemClick(this.items, position);
    }

    public static class GeneralViewHolder extends RecyclerView.ViewHolder {

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
