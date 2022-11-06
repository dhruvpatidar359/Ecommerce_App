package com.example.ecommerce_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ecommerce_app.Adapters.ImageAdapter;
import com.example.ecommerce_app.R;
import com.example.ecommerce_app.database.DBHelper;
import com.example.ecommerce_app.databinding.ActivityOrdersBinding;
import com.example.ecommerce_app.model.imageModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity implements ImageAdapter.RecyclerViewItemClickListeners{
ActivityOrdersBinding viewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding  = ActivityOrdersBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        RecyclerView recyclerView = viewBinding.activityOrdersRecycleView;
        ArrayList<imageModel> items ;

        DBHelper dbHelper = new DBHelper(this);
        items = dbHelper.getOrders();

        ImageAdapter imageAdapter = new ImageAdapter(this, items, this, recyclerView, 2);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(imageAdapter);






    }

    @Override
    public void onRecyclerViewItemClick(List<imageModel> items, int position) {

    }
}