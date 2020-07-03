package com.example.swadish;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.swadish.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FragmentSecondCart extends Fragment {
    public static final String ORDER_KEY="order";
    private EditText edtTxtAddress, edtTxtZipCode, edtTxtPhoneNumber, edtTxtEmail;
    private Button btnNext, btnBack;
    private TextView txtWarning;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_second, container, false);
        initViews(view);

        Bundle bundle=getArguments();
        if(null!=bundle){
            String jsonOrder=bundle.getString(ORDER_KEY);
            Log.i("Abcdef",jsonOrder);
            if(null!=jsonOrder){
                Gson gson = new Gson();
                Type type = new TypeToken<Order>() {
                }.getType();
                Order order=gson.fromJson(jsonOrder,type);
                if(null!=order){
                    edtTxtAddress.setText(order.getAddress());
                    edtTxtPhoneNumber.setText(order.getPhoneNumber());
                    edtTxtZipCode.setText(order.getZipCode());
                    edtTxtEmail.setText(order.getEmail());
                }
            }
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new FragmentFirstCart());
                transaction.commit();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateData()) {
                    txtWarning.setVisibility(View.GONE);
                    ArrayList<GroceryItem> cartItem = Utils.getCartItem(getActivity());
                    if (null != cartItem) {
                        Order order = new Order();
                        order.setItems(cartItem);
                        order.setAddress(edtTxtAddress.getText().toString());
                        order.setPhoneNumber(edtTxtPhoneNumber.getText().toString());
                        order.setZipCode(edtTxtZipCode.getText().toString());
                        order.setEmail(edtTxtEmail.getText().toString());
                        order.setTotalPrice(calculateTotalPrice(cartItem));
                        Gson gson=new Gson();
                        String jsonOrder=gson.toJson(order);
                        Log.i("Abcd",jsonOrder);
                        Bundle bundle=new Bundle();
                        bundle.putString(ORDER_KEY,jsonOrder);
                        FragmentThirdCart fragmentThirdCart=new FragmentThirdCart();
                        fragmentThirdCart.setArguments(bundle);
                        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container,fragmentThirdCart);
                        transaction.commit();


                    }
                } else {
                    txtWarning.setVisibility(View.VISIBLE);
                    txtWarning.setText("Please Fill In The Blanks");
                }
            }
        });


        return view;
    }

    private double calculateTotalPrice(ArrayList<GroceryItem> items) {
        double price = 0;
        for (GroceryItem item : items) {
            price += item.getPrice();
        }
        price = Math.round(price * 100.0) / 100.0;

        return price;
    }

    private boolean ValidateData() {
        if (edtTxtAddress.getText().toString().equals("") || edtTxtPhoneNumber.getText().toString().equals("") ||
                edtTxtEmail.getText().toString().equals("") || edtTxtZipCode.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    private void initViews(View view) {
        edtTxtAddress = view.findViewById(R.id.editTxtAddress);
        edtTxtZipCode = view.findViewById(R.id.editTxtZipCode);
        edtTxtPhoneNumber = view.findViewById(R.id.editTxtPhoneNumber);
        edtTxtEmail = view.findViewById(R.id.editTxtEmail);
        btnNext = view.findViewById(R.id.btnNext);
        btnBack = view.findViewById(R.id.btnBack);
        txtWarning = view.findViewById(R.id.txtWarning);
    }

}
