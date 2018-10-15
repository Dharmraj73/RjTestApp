package com.example.test.dharmrajmachinetest.ui.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.dharmrajmachinetest.R;
import com.example.test.dharmrajmachinetest.data.local.db.DataBaseHelper;
import com.example.test.dharmrajmachinetest.data.model.Item;
import com.example.test.dharmrajmachinetest.ui.adapter.ItemsAdapter;

import java.util.ArrayList;
import java.util.List;

public class GenerateBillActivity extends AppCompatActivity {

    private TextView tvSalesTax, tvTotal, tvImported;
    private LinearLayout ll_Imtax;
    private ItemsAdapter mAdapter;

    private List<Item> itemList = new ArrayList<>();
    private DataBaseHelper db;
    AddCategoryTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_bill);

        initVariables();

        db = new DataBaseHelper(this);
        task = new AddCategoryTask();
        task.execute();
    }

    private void initVariables(){

        tvTotal =  findViewById(R.id.tvTotal);
        ll_Imtax =  findViewById(R.id.ll_Imtax);
        tvSalesTax =  findViewById(R.id.tvSalesTax);
        tvImported =  findViewById(R.id.tvImported);
        RecyclerView recycleView = findViewById(R.id.recycleView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);

        //mAdapter = new ItemsAdapter(this, itemList);
        recycleView.setAdapter(mAdapter);
    }


    class AddCategoryTask extends AsyncTask<String, Boolean, Boolean> {

        double totalAmount = 0.0d;
        double totalSalesTax = 0.0d;
        double totalImportedTax = 0.0d;

        AddCategoryTask(){

        }

        @Override
        protected Boolean doInBackground(String... params) {
            //clear old data from list.
            itemList.clear();

            //get all items from database.
            itemList.addAll(db.getAllItems());

            for (Item tmp : itemList){
                totalAmount = totalAmount + tmp.getTotal();
                totalSalesTax = totalSalesTax + tmp.getSalesTax();
                totalImportedTax = totalImportedTax + tmp.getImportedTax();
            }

            totalAmount = totalAmount + totalSalesTax + totalImportedTax;
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);

            mAdapter.notifyDataSetChanged();

            tvImported.setText(String.valueOf(totalImportedTax));
            ll_Imtax.setVisibility(totalImportedTax==0?View.VISIBLE:View.GONE);
            tvSalesTax.setText(String.valueOf(totalSalesTax));
            tvTotal.setText(String.valueOf((float)Math.round(totalAmount * 100) / 100));

            task = null;
        }
    }
}
