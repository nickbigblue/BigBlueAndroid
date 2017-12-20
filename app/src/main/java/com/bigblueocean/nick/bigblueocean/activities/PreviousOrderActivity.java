package com.bigblueocean.nick.bigblueocean.activities;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bigblueocean.nick.bigblueocean.R;
import com.bigblueocean.nick.bigblueocean.adapters.PreviousOrderAdapter;
import com.bigblueocean.nick.bigblueocean.fragments.OrderFragment;
import com.bigblueocean.nick.bigblueocean.model.Category;
import com.bigblueocean.nick.bigblueocean.model.Order;
import com.bigblueocean.nick.bigblueocean.model.Product;

import java.util.ArrayList;
import java.util.Calendar;

public class PreviousOrderActivity extends AppCompatActivity {

    private ArrayList<Order> previousOrders = new ArrayList<Order>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_order);

        Product dummyProd1 = new Product(Category.TUNA, "Big Eye", "Carribbean", "1+", "60", "100", "7.25");
        Product dummyProd2 = new Product(Category.MAHI, "", "", "", "100", "10", "10.25");
        Product dummyProd3 = new Product(Category.WAHOO, "", "", "", "40", "30", "8.50");

        ArrayList<Product> dummyProds = new ArrayList<>();
        dummyProds.add(dummyProd1);
        dummyProds.add(dummyProd2);
        dummyProds.add(dummyProd3);

        Order dummyOrder = new Order(Calendar.getInstance().getTime().toString(), "Nick", dummyProds);
        previousOrders.add(dummyOrder);
        previousOrders.add(dummyOrder);
        previousOrders.add(dummyOrder);


        RecyclerView previousOrdersRecycler = findViewById(R.id.previous_order_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        previousOrdersRecycler.setLayoutManager(llm);
        previousOrdersRecycler.setAdapter(new PreviousOrderAdapter(previousOrders));

        //HomeActivity.getCurrentOrder().addAll(order);
    }

    @Override
    public void onBackPressed(){
        PreviousOrderActivity.this.finish();
        startActivity(new Intent(PreviousOrderActivity.this, HomeActivity.class));
    }
}
