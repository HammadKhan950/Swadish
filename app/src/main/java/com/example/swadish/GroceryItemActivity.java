package com.example.swadish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview {
    public static final String TAG = "GrocerryItemActivity";
    public static final String GROCERRY_ITEM_KEY = "incoming_item";
    private RecyclerView recyclerView;
    private TextView txtName, txtPrice, txtDescription, txtAddreview;
    private ImageView itemimage, firstEmptyStar, firstFilledStar, secondFilledStar, secondEmtyStar, thirdFilledStar, thirdEmptyStar;
    private Button btnAddToCart;
    private RelativeLayout firstRelativeLayout, secondRelativeLayout, thirdRelativeLayout;
    private GroceryItem incomingItem;
    private MaterialToolbar toolbar;
    private ReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);
        initView();
        setSupportActionBar(toolbar);
        adapter = new ReviewAdapter();
        final Intent intent = getIntent();
        if (null != intent) {
            incomingItem = intent.getParcelableExtra(GROCERRY_ITEM_KEY);
            if (null != incomingItem) {
                txtName.setText(incomingItem.getName());
                txtPrice.setText(String.valueOf(incomingItem.getPrice()) + "$");
                txtDescription.setText(incomingItem.getDescription());
                Glide.with(this)
                        .asBitmap()
                        .load(incomingItem.getImageUrl())
                        .into(itemimage);
                ArrayList<Review> reviews = Utils.getReviewsByID(this, incomingItem.getId());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                if (null != reviews) {
                    if (reviews.size() > 0) {
                        adapter.setReviews(reviews);
                    }
                }
                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.addItemToCart(GroceryItemActivity.this, incomingItem);
                        Intent cartIntent = new Intent(GroceryItemActivity.this, CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);

                    }
                });
                txtAddreview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(GroceryItemActivity.this, "yes", Toast.LENGTH_SHORT).show();
                        AddReviewDialog dialog = new AddReviewDialog();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(GROCERRY_ITEM_KEY, incomingItem);
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "add review");
                    }
                });
                handleRating();
            }
        }
    }

    private void handleRating() {
        switch (incomingItem.getRate()) {
            case 0:
                firstEmptyStar.setVisibility(View.VISIBLE);
                firstFilledStar.setVisibility(View.GONE);
                secondEmtyStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;
            case 1:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmtyStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;
            case 2:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmtyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;
            case 3:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmtyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
        firstRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 1) {
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 1);
                    incomingItem.setRate(1);
                    handleRating();

                }
            }
        });
        secondRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getId() != 2) {
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 2);
                    incomingItem.setRate(2);
                    handleRating();
                }
            }
        });
        thirdRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getId() != 3) {
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 3);
                    incomingItem.setRate(3);
                    handleRating();
                }
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        txtName = findViewById(R.id.textname);
        txtDescription = findViewById(R.id.textDescription);
        txtPrice = findViewById(R.id.textprice);
        txtAddreview = findViewById(R.id.textaddreview);
        itemimage = findViewById(R.id.itemImage);
        firstEmptyStar = findViewById(R.id.firsemptystar);
        firstFilledStar = findViewById(R.id.firsfieldstar);
        secondEmtyStar = findViewById(R.id.secondemptystar);
        secondFilledStar = findViewById(R.id.secondfieldstar);
        thirdEmptyStar = findViewById(R.id.thirdemptystar);
        thirdFilledStar = findViewById(R.id.thirdfieldstar);
        btnAddToCart = findViewById(R.id.addtocart);
        recyclerView = findViewById(R.id.reviewsRecylerview);
        firstRelativeLayout = findViewById(R.id.firststarrellayout);
        secondRelativeLayout = findViewById(R.id.secondstarrellayout);
        thirdRelativeLayout = findViewById(R.id.thirdstarrellayout);
    }

    @Override
    public void onAddReviewResult(Review review) {
        Log.d(TAG, "onAddjkdofdhddh" + review);
        Utils.addReview(this, review);
        ArrayList<Review> reviews = Utils.getReviewsByID(this, review.getGroceryItemId());
        if (null != reviews) {
            adapter.setReviews(reviews);
        }
    }

}
