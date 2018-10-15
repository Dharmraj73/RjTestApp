package com.example.test.dharmrajmachinetest.ui.fragment;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.test.dharmrajmachinetest.MyApplication;
import com.example.test.dharmrajmachinetest.R;
import com.example.test.dharmrajmachinetest.data.roomdb.model.Category;
import com.example.test.dharmrajmachinetest.data.roomdb.LocalDatabase;
import com.example.test.dharmrajmachinetest.data.roomdb.model.Product;
import com.example.test.dharmrajmachinetest.ui.base.BaseFragment;
import com.example.test.dharmrajmachinetest.utility.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddProductFragment extends BaseFragment implements View.OnClickListener{

    private EditText edItemName, edPrice, edQuantity, edManufactureDate, edExpiryDate;
    private Button btnSaveItem;
    private Spinner spCategories;
    private CheckBox checkBoxImported;

    private List<Category> categories;
    private ArrayAdapter<Category> itemsAdapter;

    Product product;


    public AddProductFragment() {
        // Required empty public constructor
    }

    public static AddProductFragment newInstance(Bundle args) {
        AddProductFragment fragment = new AddProductFragment();
        if(args!=null)
            fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){

            if(bundle.containsKey(Constants.PRODUCT))
                product =  (Product) bundle.getSerializable(Constants.PRODUCT);
        }



    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVariables(view);

        new GetCategoryTask().execute();

        if(product!=null){
            edItemName.setText(product.name);
            edPrice.setText(String.valueOf(product.price));
            edQuantity.setText(String.valueOf(product.quantity));
            checkBoxImported.setChecked(product.isImported);
            edManufactureDate.setText(product.getManufactureDate());
            edExpiryDate.setText(product.getExprieyDate());
            btnSaveItem.setText(getString(R.string.update));
        }
    }

    private void initVariables(View v){

        categories = new ArrayList<>();
        edItemName =  v.findViewById(R.id.edItemName);
        spCategories =  v.findViewById(R.id.spCategories);
        edPrice =  v.findViewById(R.id.edPrice);
        edQuantity =  v.findViewById(R.id.edQuantity);
        checkBoxImported =  v.findViewById(R.id.checkBoxImported);
        edManufactureDate =  v.findViewById(R.id.edManufactureDate);
        edExpiryDate =  v.findViewById(R.id.edExpiryDate);
        btnSaveItem = v.findViewById(R.id.btnSaveItem);
        v.findViewById(R.id.rootView).setOnClickListener(this);

        edManufactureDate.setOnClickListener(this);
        edExpiryDate.setOnClickListener(this);
        btnSaveItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnSaveItem:
                validateInputData();
                break;

            case R.id.edManufactureDate:
                showPickerDialog(edManufactureDate);
                break;

            case R.id.edExpiryDate:
                showPickerDialog(edExpiryDate);
                break;

        }
    }

    private void validateInputData(){
        //get input values
        String itemName = edItemName.getText().toString();
        String manufactureDate = edManufactureDate.getText().toString();
        String expiryDate = edExpiryDate.getText().toString();
        String price = edPrice.getText().toString();
        String quantity = edQuantity.getText().toString();

        if(TextUtils.isEmpty(itemName)){
            edItemName.requestFocus();
            edItemName.setError("Please fill item name");
            listener.showToast("Please fill item name");

        }
        else  if(TextUtils.isEmpty(manufactureDate)){
            edPrice.requestFocus();
            edPrice.setError("Please enter manufacture date");
            //listener.showToast("Please enter price");

        }

        else  if(TextUtils.isEmpty(expiryDate)){
            edPrice.requestFocus();
            edPrice.setError("Please enter expiry date");
            //listener.showToast("Please enter price");

        }


        else  if(TextUtils.isEmpty(price)){
            edPrice.requestFocus();
            edPrice.setError("Please enter price");
            listener.showToast("Please enter price");

        }else  if(TextUtils.isEmpty(quantity)){
            edQuantity.requestFocus();
            edQuantity.setError("Please enter quantity");
            listener.showToast("Please enter quantity");

        }else if(spCategories.getSelectedItem()==null){
            listener.showToast("Please Add Category first.");

        }else {

            try{
                Product product = new Product();
                product.name = itemName;
                product.manufactureDate = Constants.getStringToDate(manufactureDate);
                product.expiryDate = Constants.getStringToDate(expiryDate);
                product.price = Double.parseDouble(price);
                product.quantity = Integer.parseInt(quantity);
                product.categoryId = categories.get(spCategories.getSelectedItemPosition()).id;
                product.isImported = checkBoxImported.isChecked();
                // insert data on background thread (Worker thread)
                new AddProductTask().execute(product);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    private int mYear, mMonth, mDay, mHour, mMinute;

    private void showPickerDialog(final EditText editText){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }



    private class GetCategoryTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            // get room database instance refrance
            LocalDatabase db = MyApplication.getDatabase(mContext);
            // get all category
            categories = db.categoryDAO().getAllCategory();
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            itemsAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, categories);
            spCategories.setAdapter(itemsAdapter);
            itemsAdapter.notifyDataSetChanged();
        }
    }


    private class AddProductTask extends AsyncTask<Product, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Product... params) {
            // get room database instance refrance
            LocalDatabase db = MyApplication.getDatabase(mContext);
            long id;
            if(product!=null){
                // perform update operation
                params[0].productId = product.productId;
                id = db.productDAO().update(params[0]);
            }else {
                // perform insert operation
                id = db.productDAO().insert(params[0]);
            }


            //if product inserted then return true otherwise return false
            return id!=-1;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);

            if(bool){
                edItemName.setText("");
                edPrice.setText("");
                edQuantity.setText("");
                edManufactureDate.setText("");
                edExpiryDate.setText("");
                checkBoxImported.setChecked(false);
                listener.showToast("Product successfully saved.");
            }

            if(product!=null){
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.EditableFragment, true);
                listener.replaceFragment(GenerateBillFragment.newInstance(bundle), false);
            }
        }
    }
}
