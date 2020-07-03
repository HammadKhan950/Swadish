package com.example.swadish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.GuardedObject;
import java.util.ArrayList;

import static com.example.swadish.AllCategoriesDialog.ALL_CATEGORIES;
import static com.example.swadish.AllCategoriesDialog.CALLING_ACTIVITY;

public class SettingActivity extends AppCompatActivity implements AllCategoriesDialog.GetCategory {
    private static final String TAG = "SearchActivity";
    private MaterialToolbar toolbar;
    private EditText searchBox;
    private ImageView btnSearch;
    private TextView firstCAt, secondCat, thirdCat, txtAllCategories;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private GroceryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        setSupportActionBar(toolbar);
        initBottomNavView();
        adapter = new GroceryAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        Intent intent=getIntent();
        String category =intent.getStringExtra("category");
        if(null!=category){
            ArrayList<GroceryItem> items=Utils.getItemsByCategory(this,category);
            adapter.setItems(items);

        }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSearch();

            }
        });
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initSearch();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ArrayList<String> categories = Utils.getCategory(this);
        if (null != categories) {
            if (categories.size() > 0) {
                if (categories.size() == 1) {
                    showCategories(categories, 1);
                } else if (categories.size() == 2) {
                    showCategories(categories, 2);
                } else {
                    showCategories(categories, 3);

                }
            }
        }
    }

    private void showCategories(final ArrayList<String> categories, int i) {
        switch (i) {
            case 1:
                firstCAt.setVisibility(View.VISIBLE);
                firstCAt.setText(categories.get(0));
                secondCat.setVisibility(View.GONE);
                thirdCat.setVisibility(View.GONE);
                firstCAt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SettingActivity.this, categories.get(0));
                        if (null != items) {
                            adapter.setItems(items);
                        }
                    }
                });
                break;
            case 2:
                firstCAt.setVisibility(View.VISIBLE);
                firstCAt.setText(categories.get(0));
                secondCat.setVisibility(View.VISIBLE);
                secondCat.setText(categories.get(1));
                thirdCat.setVisibility(View.GONE);
                secondCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SettingActivity.this, categories.get(1));
                        if (null != items) {
                            adapter.setItems(items);
                        }

                    }
                });
                firstCAt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SettingActivity.this, categories.get(0));
                        if (null != items) {
                            adapter.setItems(items);
                        }
                    }
                });
                break;
            case 3:
                thirdCat.setVisibility(View.VISIBLE);
                firstCAt.setText(categories.get(0));
                secondCat.setVisibility(View.VISIBLE);
                firstCAt.setVisibility(View.VISIBLE);
                secondCat.setText(categories.get(1));
                thirdCat.setText(categories.get(2));
                thirdCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SettingActivity.this, categories.get(2));
                        if (null != items) {
                            adapter.setItems(items);
                        }

                    }
                });
                firstCAt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SettingActivity.this, categories.get(0));
                        if (null != items) {
                            adapter.setItems(items);
                        }
                    }
                });
                secondCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SettingActivity.this, categories.get(1));
                        if (null != items) {
                            adapter.setItems(items);
                        }

                    }
                });
                break;
            default:
                firstCAt.setVisibility(View.GONE);
                secondCat.setVisibility(View.GONE);
                thirdCat.setVisibility(View.GONE);
                break;

        }
        txtAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllCategoriesDialog dialog=new AllCategoriesDialog();
                Bundle bundle=new Bundle();
                bundle.putStringArrayList(ALL_CATEGORIES,Utils.getCategory(SettingActivity.this));
                bundle.putString(CALLING_ACTIVITY,"setting_activity");
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(),"all categories dialog");
            }
        });

    }

    public void initSearch() {
        if (!searchBox.getText().toString().equals("")) {
            String name = searchBox.getText().toString();
            ArrayList<GroceryItem> items = Utils.searchForItems(this, name);
            adapter.setItems(items);
        }
    }

    private void initBottomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    case R.id.search:

                        break;
                    case R.id.cart:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        searchBox = findViewById(R.id.searchbox);
        btnSearch = findViewById(R.id.btnSearch);
        firstCAt = findViewById(R.id.txtFirstCat);
        secondCat = findViewById(R.id.txtSecondCat);
        thirdCat = findViewById(R.id.txtThirdCat);
        txtAllCategories = findViewById(R.id.txtAllCategories);
        bottomNavigationView = findViewById(R.id.bottomnavigationView);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void onGetCategoryResult(String category) {
        ArrayList<GroceryItem> items = Utils.getItemsByCategory(this, category);
        if (null != items) {
            adapter.setItems(items);
        }
    }
}
