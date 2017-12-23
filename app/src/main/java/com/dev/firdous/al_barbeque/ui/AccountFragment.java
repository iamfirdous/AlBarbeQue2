package com.dev.firdous.al_barbeque.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dev.firdous.al_barbeque.R;
import com.dev.firdous.al_barbeque.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private TextView userName, emailAndPhone, edit;
    private ImageView myAccountImageView;
    private ConstraintLayout myAccountConstraintLayout, logoutConstraintLayout;
    private ListView myAccountListView;

    private DatabaseReference mReference;
    private String myAccountList[];
    private ArrayAdapter<String> myAccAdapter;
    private boolean visible;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_account, container, false);

        userName = view.findViewById(R.id.textView_userName);
        emailAndPhone = view.findViewById(R.id.textView_emailAndPhone);
        edit = view.findViewById(R.id.textView_edit);
        myAccountImageView = view.findViewById(R.id.imageView_myAccount);
        myAccountConstraintLayout = view.findViewById(R.id.constraintLayout_myAccount);
        logoutConstraintLayout = view.findViewById(R.id.constraintLayout_logout);
        myAccountListView = view.findViewById(R.id.listView_myAccount);

        myAccountList = getResources().getStringArray(R.array.myAccount);
        myAccAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_my_account_items, R.id.textView_myAccountItem, myAccountList);
        myAccountListView.setAdapter(myAccAdapter);
        visible = false;

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(uid != null) {
            mReference = FirebaseDatabase.getInstance().getReference("Users/" + uid);

            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                    userName.setText(user.getUsername());
                    emailAndPhone.setText(user.getEmailId() + "  -  " + user.getPhoneNumber());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        else {
            Log.wtf("Errrror: ", "Null at UID");
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Yet to write code for editing the user details
            }
        });

        myAccountConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!visible){
                    myAccountImageView.setImageResource(R.drawable.ic_arrow_up_black_24dp);
                    myAccountListView.setVisibility(View.VISIBLE);
                    visible = true;
                }
                else {
                    myAccountImageView.setImageResource(R.drawable.ic_arrow_down_black_24dp);
                    myAccountListView.setVisibility(View.GONE);
                    visible = false;
                }
            }
        });

        logoutConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent login = new Intent(getContext(), LoginActivity.class);
                startActivity(login);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }
}
