package com.example.test.dharmrajmachinetest.ui.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.test.dharmrajmachinetest.R;
import com.example.test.dharmrajmachinetest.data.local.db.DataBaseHelper;
import com.example.test.dharmrajmachinetest.data.roomdb.model.Category;

public class AddCategoryActivity extends AppCompatActivity {

    private EditText edCategoryName;
    private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcategory);

        edCategoryName = (EditText) findViewById(R.id.edCategoryName);
        db = new DataBaseHelper(this);

        findViewById(R.id.btnAddCategory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = edCategoryName.getText().toString();
                if(TextUtils.isEmpty(categoryName))
                    Toast.makeText(AddCategoryActivity.this, "Please enter category name first", Toast.LENGTH_SHORT).show();
                else {
                    AddCategoryTask task = new AddCategoryTask(new Category(categoryName));
                    task.execute();
                    edCategoryName.setText("");
                }
            }
        });
    }


    class AddCategoryTask extends AsyncTask<String, Boolean, Boolean>{
        Category category;
        AddCategoryTask( Category category){
            this.category = category;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return (db.insertCategory(category)==-1);
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            if(aVoid){
                edCategoryName.setText("");
                Toast.makeText(AddCategoryActivity.this, "Successfully Save", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
