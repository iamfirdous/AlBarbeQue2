package com.dev.firdous.al_barbeque.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev.firdous.al_barbeque.R;
import com.dev.firdous.al_barbeque.models.FoodItem;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private ListView mCartListView;
    private Button mProceedToPay;
    private TextView mDeliverTo, mTotalAmount, mAddAddress;

    private FirebaseListAdapter<FoodItem> mFoodItemAdapter;
    private DatabaseReference mReference;

    private String mUid;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        mCartListView = view.findViewById(R.id.listView_cart);
        mProceedToPay = view.findViewById(R.id.button_proceedToPay);
        mDeliverTo = view.findViewById(R.id.textView_deliverTo);
        mTotalAmount = view.findViewById(R.id.textView_totalAmount);
        mAddAddress = view.findViewById(R.id.textView_addAddress);

        mCartListView.setEmptyView(view.findViewById(R.id.ifEmptyView));

        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mReference = FirebaseDatabase.getInstance().getReference("Cart").child(mUid);
        mTotalAmount.setText("Rs. 0.0");

        mFoodItemAdapter = new FirebaseListAdapter<FoodItem>(getContext(), FoodItem.class, R.layout.item_cart, mReference) {
            @Override
            protected void populateView(View v, final FoodItem foodItem, int position) {
                ImageView foodImageView = v.findViewById(R.id.imageView_foodImageCart);
                TextView foodName = v.findViewById(R.id.textView_foodNameCart);
                TextView foodPrice = v.findViewById(R.id.textView_foodPriceCart);
                TextView foodQuant = v.findViewById(R.id.textView_foodQuantCart);
                ImageView subQuant = v.findViewById(R.id.imageView_subFoodQuant);
                ImageView addQuant = v.findViewById(R.id.imageView_addFoodQuant);

                final DatabaseReference reference = mReference.child(foodItem.getItemId()).child("quantity");
                int quant = foodItem.getQuantity();

                foodName.setText(foodItem.getItemName());
                foodPrice.setText("Rs. " + foodItem.getTotalPrice());
                foodQuant.setText("" + quant);

                if (quant == 0){
                    mReference.child(foodItem.getItemId()).removeValue();
                }

                if (quant == 20)
                    addQuant.setEnabled(false);
                else
                    addQuant.setEnabled(true);

                addQuant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reference.setValue(foodItem.getQuantity() + 1);
                    }
                });

                subQuant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reference.setValue(foodItem.getQuantity() - 1);
                    }
                });

                StorageReference storageReference = FirebaseStorage.getInstance().getReference(foodItem.getPhotoLocation());

                Glide.with(getContext())
                        .using(new FirebaseImageLoader())
                        .load(storageReference)
                        .into(foodImageView);
            }
        };

        mCartListView.setAdapter(mFoodItemAdapter);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float total = 0;
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    total = total + d.getValue(FoodItem.class).getTotalPrice();
                }
                mTotalAmount.setText("Rs. " + total);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }
}
