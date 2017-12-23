package com.dev.firdous.al_barbeque.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.SnackbarContentLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dev.firdous.al_barbeque.R;
import com.dev.firdous.al_barbeque.models.FoodItem;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by firdous on 13/9/17.
 */

public class SelectFoodActivity extends AppCompatActivity {

    private GridView mFoodGridView;
    private TextView mFoodName, mFoodPrice;
    private Button addToCart;
    private ImageView foodImage;
    private CoordinatorLayout coordinatorLayout;

    private DatabaseReference mFoodReference, mCartReference;
    private String mUid, mRestaurantId;

    private FirebaseListAdapter<FoodItem> mFoodItemAdapter;

    private Snackbar snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_food);

        mFoodGridView = findViewById(R.id.gridView_foodItems);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

//        snackbar = Snackbar.make(coordinatorLayout, "Added to cart", Snackbar.LENGTH_INDEFINITE)
//                            .setAction("VIEW CART", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent cartIntent = new Intent(SelectFoodActivity.this, HomeActivity.class);
//                                    cartIntent.putExtra("fragmentToLoad", 3);
//                                    startActivity(cartIntent);
//                                    HomeActivity.getInstance().finish();
//                                    finish();
//                                }
//                            });

        mRestaurantId = getIntent().getStringExtra("restaurantId");
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mFoodReference = FirebaseDatabase.getInstance().getReference("FoodItems/" + mRestaurantId);
        mCartReference = FirebaseDatabase.getInstance().getReference("Cart").child(mUid);

        mFoodItemAdapter  = new FirebaseListAdapter<FoodItem>(this, FoodItem.class, R.layout.item_food, mFoodReference) {
            @Override
            protected void populateView(View v, final FoodItem foodItem, int position) {
                mFoodName = v.findViewById(R.id.textView_foodName);
                mFoodPrice = v.findViewById(R.id.textView_foodPrice);
                addToCart = v.findViewById(R.id.button_addToCart);
                foodImage = v.findViewById(R.id.imageView_foodImage);

                mFoodName.setText(foodItem.getItemName());
                mFoodPrice.setText("Rs. " + foodItem.getPrice());

                StorageReference storageReference = FirebaseStorage.getInstance().getReference(foodItem.getPhotoLocation());

                Glide.with(SelectFoodActivity.this)
                        .using(new FirebaseImageLoader())
                        .load(storageReference)
                        .into(foodImage);

                addToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        foodItem.setUid(mUid);
                        foodItem.setQuantity(1);
                        mCartReference.child(foodItem.getItemId()).setValue(foodItem);
                        Snackbar.make(coordinatorLayout, "Added to cart", Snackbar.LENGTH_INDEFINITE)
                                .setAction("VIEW CART", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent cartIntent = new Intent(SelectFoodActivity.this, HomeActivity.class);
                                        cartIntent.putExtra("fragmentToLoad", 3);
                                        startActivity(cartIntent);
                                        HomeActivity.getInstance().finish();
                                        finish();
                                    }
                                }).show();
                    }
                });
            }
        };

        mFoodGridView.setAdapter(mFoodItemAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
