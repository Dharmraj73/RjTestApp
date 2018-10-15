package com.example.test.dharmrajmachinetest.ui.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.test.dharmrajmachinetest.MyApplication;
import com.example.test.dharmrajmachinetest.R;
import com.example.test.dharmrajmachinetest.data.local.db.DataBaseHelper;
import com.example.test.dharmrajmachinetest.data.model.Item;
import com.example.test.dharmrajmachinetest.data.roomdb.model.Category;
import com.example.test.dharmrajmachinetest.data.roomdb.LocalDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    EditText edItemName, edPrice, edQuantity;
    Spinner spCategories;
    CheckBox checkBoxImported;
    private DataBaseHelper db;
    List<Category> categories;
    ArrayAdapter<Category> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        db = new DataBaseHelper(this);
        initVariables();


        findViewById(R.id.btnSaveItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = edItemName.getText().toString();
                String price = edPrice.getText().toString();
                String quantity = edQuantity.getText().toString();
                boolean isChecked = checkBoxImported.isChecked();

                if(TextUtils.isEmpty(itemName)){
                    edItemName.requestFocus();
                    edItemName.setError("Please fill item name");
                    Toast.makeText(AddItemActivity.this, "Please fill item name", Toast.LENGTH_SHORT).show();
                }else  if(TextUtils.isEmpty(price)){
                    edPrice.requestFocus();
                    edPrice.setError("Please enter price");
                    Toast.makeText(AddItemActivity.this, "Please enter price", Toast.LENGTH_SHORT).show();
                }else  if(TextUtils.isEmpty(quantity)){
                    edQuantity.requestFocus();
                    edQuantity.setError("Please enter quantity");
                    Toast.makeText(AddItemActivity.this, "Please enter quantity", Toast.LENGTH_SHORT).show();
                }else if(spCategories.getSelectedItem()==null){
                    Toast.makeText(AddItemActivity.this, "Please Add Category", Toast.LENGTH_SHORT).show();
                }else {

                    try{
                        /*Item iteam = new Item();
                        iteam.name = itemName;
                        iteam.price = Double.parseDouble(price);
                        iteam.quantity = Integer.parseInt(quantity);
                        iteam.category_id = spCategories.getSelectedItemPosition();
                        iteam.isImported = checkBoxImported.isChecked();*/

                        Item product = new Item();
                        product.name = itemName;
                        product.price = Double.parseDouble(price);
                        product.quantity = Integer.parseInt(quantity);
                        product.categoryId = categories.get(spCategories.getSelectedItemPosition()).id;
                        // spCategories.getSelectedItemPosition();
                        product.isImported = checkBoxImported.isChecked();

                        new AddItemTask(product).execute();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }


            }
        });
    }

    private void initVariables(){
        categories = new ArrayList<>();
        edItemName =  findViewById(R.id.edItemName);
        spCategories =  findViewById(R.id.spCategories);
        edPrice =  findViewById(R.id.edPrice);
        edQuantity =  findViewById(R.id.edQuantity);
        checkBoxImported =  findViewById(R.id.checkBoxImported);
        new GetCategoryTask().execute();

        // Creating adapter for spinner
        /*itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        spCategories.setAdapter(itemsAdapter);*/
}

    class GetCategoryTask extends AsyncTask<String, Boolean, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            //categories = db.getAllCategoryT();
            LocalDatabase dataBase = MyApplication.getDatabase(AddItemActivity.this);
            categories = dataBase.categoryDAO().getAllCategory();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            //String items = android.text.TextUtils.join(",", categories);
           /* List<String> list = new ArrayList<>();
            for(Category tmp : categories)
                list.add(tmp.name);*/

            itemsAdapter = new ArrayAdapter<>(AddItemActivity.this, android.R.layout.simple_list_item_1, categories);
            spCategories.setAdapter(itemsAdapter);
            itemsAdapter.notifyDataSetChanged();
        }
    }


    class AddItemTask extends AsyncTask<String, Boolean, Boolean> {

        private Item iteam;

        AddItemTask(Item iteam){
            this.iteam = iteam;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            /*LocalDatabase db = MyApplication.getDatabase(AddItemActivity.this);
            long id = db.productDAO().insert(iteam);
            return id!=-1;*/
            return db.insertItem(iteam)==-1;

        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);

            if(aVoid){
                edItemName.setText("");
                edPrice.setText("");
                edQuantity.setText("");
                checkBoxImported.setChecked(false);
                Toast.makeText(AddItemActivity.this, "Item successfully save", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
