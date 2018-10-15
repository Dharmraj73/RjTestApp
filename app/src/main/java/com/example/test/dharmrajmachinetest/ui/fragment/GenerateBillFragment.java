package com.example.test.dharmrajmachinetest.ui.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.dharmrajmachinetest.MyApplication;
import com.example.test.dharmrajmachinetest.R;
import com.example.test.dharmrajmachinetest.data.roomdb.LocalDatabase;
import com.example.test.dharmrajmachinetest.data.roomdb.model.Product;
import com.example.test.dharmrajmachinetest.ui.adapter.ItemsAdapter;
import com.example.test.dharmrajmachinetest.ui.base.BaseFragment;
import com.example.test.dharmrajmachinetest.utility.Constants;

import java.util.ArrayList;
import java.util.List;


public class GenerateBillFragment extends BaseFragment implements View.OnClickListener,
    ItemsAdapter.Listener{

    private TextView tvSalesTax, tvTotal, tvImported;
    private LinearLayout ll_Imtax;
    private LinearLayout ll_bottom, ll_noItemsAvailable;

    private ItemsAdapter mAdapter;
    private List<Product> itemList = new ArrayList<>();
    private boolean isEditableFragment;

    public GenerateBillFragment() {
        // Required empty public constructor
    }

    public static GenerateBillFragment newInstance(Bundle args) {
        GenerateBillFragment fragment = new GenerateBillFragment();
        if(args!=null)
            fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle!=null){

            if(bundle.containsKey(Constants.EditableFragment))
                isEditableFragment =  bundle.getBoolean(Constants.EditableFragment, false);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genetate_bill, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVariables(view);

        new GenerateBillTask().execute();
    }


    private void initVariables(View v){

        tvTotal =  v.findViewById(R.id.tvTotal);
        ll_Imtax =  v.findViewById(R.id.ll_Imtax);
        tvSalesTax =  v.findViewById(R.id.tvSalesTax);
        tvImported =  v.findViewById(R.id.tvImported);

        ll_bottom = v.findViewById(R.id.ll_bottom);
        ll_noItemsAvailable = v.findViewById(R.id.ll_noItemsAvailable);
        v.findViewById(R.id.btnAddProducts).setOnClickListener(this);
        v.findViewById(R.id.rootView).setOnClickListener(this);

        RecyclerView recycleView = v.findViewById(R.id.recycleView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recycleView.setLayoutManager(mLayoutManager);
        // create adapter object
        mAdapter = new ItemsAdapter(mContext, itemList);
        //set adapter into recycleview.
        recycleView.setAdapter(mAdapter);

        if(isEditableFragment)
            mAdapter.setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnAddProducts:
                listener.replaceFragment(AddCategoryFragment.newInstance(), false);
                break;
        }
    }

    @Override
    public void onItemClick(Product product, int position) {

        if(product!=null){
            Bundle bundle = getArguments();
            bundle.putSerializable(Constants.PRODUCT, product);
            bundle.putInt(Constants.POSITION, position);
            listener.addFragment(AddProductFragment.newInstance(bundle), false);
        }
    }

    class GenerateBillTask extends AsyncTask<String, Boolean, Boolean> {

        double totalAmount = 0.0d;
        double totalSalesTax = 0.0d;
        double totalImportedTax = 0.0d;

        @Override
        protected Boolean doInBackground(String... params) {
            //clear old data from list.
            itemList.clear();

            //get all items from database.
            LocalDatabase db = MyApplication.getDatabase(mContext);
            itemList.addAll(db.productDAO().getAllProducts());
            //itemList.addAll(db.getAllItems());

            for (Product tmp : itemList){
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

            int size = itemList.size();
            mAdapter.notifyDataSetChanged();

            //update ui
            ll_noItemsAvailable.setVisibility(size>0?View.GONE : View.VISIBLE);
            ll_bottom.setVisibility(size>0?View.VISIBLE : View.GONE);

            ll_Imtax.setVisibility(totalImportedTax==0?View.VISIBLE:View.GONE);

            tvImported.setText(String.valueOf(totalImportedTax));
            tvSalesTax.setText(String.valueOf(totalSalesTax));
            tvTotal.setText(String.valueOf((float)Math.round(totalAmount * 100) / 100));
        }
    }
}
