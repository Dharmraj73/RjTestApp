package com.example.test.dharmrajmachinetest.ui.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.test.dharmrajmachinetest.MyApplication;
import com.example.test.dharmrajmachinetest.R;
import com.example.test.dharmrajmachinetest.data.roomdb.model.Category;
import com.example.test.dharmrajmachinetest.data.roomdb.LocalDatabase;
import com.example.test.dharmrajmachinetest.ui.base.BaseFragment;


public class AddCategoryFragment extends BaseFragment implements View.OnClickListener{

    private EditText edCategoryName;

    public AddCategoryFragment() {
        // Required empty public constructor
    }

    public static AddCategoryFragment newInstance() {
        AddCategoryFragment fragment = new AddCategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_category, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edCategoryName = view.findViewById(R.id.edCategoryName);
        view.findViewById(R.id.btnAddCategory).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnAddCategory:
                String categoryName = edCategoryName.getText().toString();

                if(!TextUtils.isEmpty(categoryName))
                    new AddCategoryTask().execute(categoryName);
                else
                    listener.showToast("Please enter category name first");

                break;

        }
    }

    class AddCategoryTask extends AsyncTask<String, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            LocalDatabase dataBase = MyApplication.getDatabase(mContext);
            long id = dataBase.categoryDAO().insert(new Category(params[0]));
            return id!=-1;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            if(aVoid){
                edCategoryName.setText("");
                listener.showToast("Successfully Save");
            }
        }
    }
}
