package com.example.ecommerce_app.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_app.Adapters.ImageAdapter;
import com.example.ecommerce_app.R;
import com.example.ecommerce_app.database.DBHelper;
import com.example.ecommerce_app.databinding.AcitivtyEcommercehomeBinding;
import com.example.ecommerce_app.model.imageModel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EcommerceHome extends AppCompatActivity  implements ImageAdapter.RecyclerViewItemClickListeners  {

  protected AcitivtyEcommercehomeBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = AcitivtyEcommercehomeBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        List<imageModel> items = new ArrayList<>();

        items.add(new imageModel(R.drawable.camera,112,"Camera"));
        items.add(new imageModel(R.drawable.nuts,132,"Nuts"));
        items.add(new imageModel(R.drawable.maggi,124,"Maggi"));
        items.add(new imageModel(R.drawable.cashews,142,"Cashews"));
        items.add(new imageModel(R.drawable.popcon,112,"Popcorn"));

        RecyclerView recyclerView = viewBinding.ecomRecyclerView;

        ImageAdapter imageAdapter = new ImageAdapter(this, items, this, recyclerView, 2);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(imageAdapter);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_order_btn) {
            Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,OrdersActivity.class);
            startActivity(intent);

        } else {
            throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecyclerViewItemClick(List<imageModel> items, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to add this item to the cart 😀 ?");

        // Set Alert Title
        builder.setTitle("ADD TO CART 🛒");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

            DBHelper dbHelper = new DBHelper(this);

            try{
                dbHelper.insertFuction(items.get(position).getImage(),items.get(position).getId(),items.get(position).getImage_name());


                Toast.makeText(EcommerceHome.this,"Success",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(EcommerceHome.this, "Error while uploading", Toast.LENGTH_SHORT).show();
            }

        });



        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();




    }

}
