package com.dev.firdous.al_barbeque.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev.firdous.al_barbeque.R;
import com.dev.firdous.al_barbeque.models.Restaurant;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectRestaurantFragment extends Fragment {

    private ListView mRestaurantListView;

    private DatabaseReference mReference;
    private FirebaseListAdapter<Restaurant> mRestaurantAdapter;

    private Uri uri;

    public SelectRestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_restaurant, container, false);

        mRestaurantListView = view.findViewById(R.id.listView_restaurant);

        mReference = FirebaseDatabase.getInstance().getReference("Restaurants");

        mRestaurantAdapter = new FirebaseListAdapter<Restaurant>(getContext(), Restaurant.class, R.layout.item_restaurant, mReference) {
            @Override
            protected void populateView(View v, Restaurant restaurant, int position) {
                TextView restaurantName = v.findViewById(R.id.textView_restaurantName);
                TextView restaurantAddress = v.findViewById(R.id.textView_restaurantAddress);
                ImageView restaurantImage = v.findViewById(R.id.imageView_restaurantImage);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference(restaurant.getPhotoLocation());

                restaurantAddress.setText(restaurant.getAddress().getAddress());
                restaurantName.setText(restaurant.getRestaurantName());

                Glide.with(getContext())
                        .using(new FirebaseImageLoader())
                        .load(storageReference)
                        .into(restaurantImage);
            }
        };
        mRestaurantListView.setAdapter(mRestaurantAdapter);

        mRestaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent foodIntent = new Intent(getContext(), SelectFoodActivity.class);
                foodIntent.putExtra("restaurantId", mRestaurantAdapter.getItem(i).getRestaurantId());
                startActivity(foodIntent);
            }
        });

        return view;
    }

}
