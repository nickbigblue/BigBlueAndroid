package com.bigblueocean.nick.bigblueocean.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bigblueocean.nick.bigblueocean.R;
import com.bigblueocean.nick.bigblueocean.adapters.PreviousOrderAdapter;
import com.bigblueocean.nick.bigblueocean.model.OrderHeader;

import java.util.ArrayList;
import java.util.Calendar;

public class PreviousOrderActivity extends AppCompatActivity {

    private ArrayList<OrderHeader> previousOrderHeaders = new ArrayList<OrderHeader>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_order);

        ArrayList<String> dummyProds1 = new ArrayList<>();
        dummyProds1.add("TUNA");
        dummyProds1.add("MAHI");
        dummyProds1.add("GROUPER");

        ArrayList<String> dummyProds2 = new ArrayList<>();
        dummyProds2.add("SWORD");
        dummyProds2.add("WAHOO");
        dummyProds2.add("SALMON");


        ArrayList<String> dummyProds3 = new ArrayList<>();
        dummyProds3.add("TUNA");
        dummyProds3.add("SWORD");
        dummyProds3.add("MAHI");
        dummyProds3.add("WAHOO");
        dummyProds3.add("GROUPER");
        dummyProds3.add("SALMON");

        OrderHeader dummyOrderHeader = new OrderHeader(Calendar.getInstance().getTime().toString(), "123", dummyProds1);
        previousOrderHeaders.add(dummyOrderHeader);
        dummyOrderHeader = new OrderHeader(Calendar.getInstance().getTime().toString(), "124", dummyProds2);
        previousOrderHeaders.add(dummyOrderHeader);
        dummyOrderHeader = new OrderHeader(Calendar.getInstance().getTime().toString(), "125", dummyProds3);
        previousOrderHeaders.add(dummyOrderHeader);


        RecyclerView previousOrdersRecycler = findViewById(R.id.previous_order_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        previousOrdersRecycler.setLayoutManager(llm);
        previousOrdersRecycler.setAdapter(new PreviousOrderAdapter(previousOrderHeaders));

        //HomeActivity.getCurrentOrder().addAll(order);
    }

    @Override
    public void onBackPressed(){
        PreviousOrderActivity.this.finish();
        startActivity(new Intent(PreviousOrderActivity.this, HomeActivity.class));
    }
}
