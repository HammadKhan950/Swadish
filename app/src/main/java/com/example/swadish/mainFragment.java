package com.example.swadish;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class mainFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView newItemRecView, popItemRecView, sugItemRecView;
    private GroceryAdapter newItemAdapter, popularItemAdapter, suggestedItemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        initBottomNavView();
      //  Utils.clearSharedPreferences(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecView();
    }

    private void initRecView() {
        newItemAdapter = new GroceryAdapter(getActivity());
        newItemRecView.setAdapter(newItemAdapter);
        newItemRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        popularItemAdapter = new GroceryAdapter(getActivity());
        popItemRecView.setAdapter(popularItemAdapter);
        popItemRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        suggestedItemAdapter = new GroceryAdapter(getActivity());
        sugItemRecView.setAdapter(suggestedItemAdapter);
        sugItemRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        ArrayList<GroceryItem> newItems = Utils.getAllItems(getActivity());
        if (null != newItems) {
            newItemAdapter.setItems(newItems);

            final Comparator<GroceryItem> newItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {

                    return o1.getId() - o2.getId();
                }
            };
            Comparator<GroceryItem> reverseComparator = Collections.reverseOrder(newItemsComparator);
            Collections.sort(newItems, reverseComparator);
            newItemAdapter.setItems(newItems);
    }
        ArrayList<GroceryItem> popularItems = Utils.getAllItems(getActivity());
        if (null != newItems) {
            final Comparator<GroceryItem> popularItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {

                    return o1.getPopularityPoint() - o2.getPopularityPoint();
                }
            };
            Collections.sort(popularItems, Collections.<GroceryItem>reverseOrder(popularItemsComparator));
            popularItemAdapter.setItems(popularItems);

        }
        ArrayList<GroceryItem> suggestedItems = Utils.getAllItems(getActivity());
        if (null != newItems) {

            final Comparator<GroceryItem> suggestedItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {

                    return o1.getUserPoint() - o2.getUserPoint();
                }
            };
            Collections.sort(suggestedItems, Collections.<GroceryItem>reverseOrder(suggestedItemsComparator));
            suggestedItemAdapter.setItems(suggestedItems);
        }
    }


            private void initView(View view) {
        bottomNavigationView = view.findViewById(R.id.bottomnavigationView);
        newItemRecView = view.findViewById(R.id.newItemRecView);
        popItemRecView = view.findViewById(R.id.popularRecView);
        sugItemRecView = view.findViewById(R.id.suggestedRecView);

    }

    private void initBottomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        break;
                    case R.id.search:
                        Intent intent=new Intent(getActivity(),SettingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.cart:
                        Intent cartIntent=new Intent(getActivity(),CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
