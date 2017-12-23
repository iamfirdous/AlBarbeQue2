package com.dev.firdous.al_barbeque.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.firdous.al_barbeque.R;
import com.dev.firdous.al_barbeque.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by firdous on 13/9/17.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText etPhone, etName, etEmail, etNewPassword, etReenterPassword;
    private Button register;
    private String phone, name, email, newPassword, reenteredPassword;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserReference;

    private boolean registered = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();

        etPhone = findViewById(R.id.editText_registerPhone);
        etName = findViewById(R.id.editText_name);
        etEmail = findViewById(R.id.editText_email);
        etNewPassword = findViewById(R.id.editText_newPassword);
        etReenterPassword = findViewById(R.id.editText_reenterNewPassword);
        register = findViewById(R.id.button_signUp);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    phone = etPhone.getText().toString().trim();
                    name = etName.getText().toString().trim();
                    email = etEmail.getText().toString().trim();
                    newPassword = etNewPassword.getText().toString().trim();
                    reenteredPassword = etReenterPassword.getText().toString().trim();
                    boolean notEmpty = true;
                    boolean passwordMatch = true;

                    if(phone.equals("") || phone == null){
                        etPhone.setError("Enter your phone number");
                        notEmpty = false;
                    }
                    if(name.equals("") || name == null){
                        etName.setError("Enter your full name to continue");
                        notEmpty = false;
                    }
                    if(email.equals("") || email == null){
                        etEmail.setError("Enter your email address");
                        notEmpty = false;
                    }
                    else if(!isValidEmailAddress(email)){
                        etEmail.setError("Enter a valid email address");
                        notEmpty = false;
                    }
                    if(newPassword.equals("") || newPassword == null){
                        etNewPassword.setError("Enter your new password");
                        notEmpty = false;
                    }
                    if(reenteredPassword.equals("") || reenteredPassword == null){
                        etReenterPassword.setError("Re-enter your new password");
                        notEmpty = false;
                    }
                    if(!newPassword.equals(reenteredPassword)){
                        etNewPassword.setError("Passwords do not match");
                        etReenterPassword.setError("Passwords do not match");
                        passwordMatch = false;
                    }
                    if(notEmpty && passwordMatch) {
                        createUserWithEmailAndPassword(email, newPassword);
                    }
                }
                catch (Exception e){
                    Log.wtf("Errror: ", "Exception " +e.toString());
                }
            }
        });
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void createUserWithEmailAndPassword(String email, String password){
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            addUserToDatabase();
                            Toast.makeText(RegisterActivity.this, "Signed in successfully", Toast.LENGTH_LONG).show();
                            Intent restaurantIntent = new Intent(RegisterActivity.this, HomeActivity.class);
                            restaurantIntent.putExtra("fragmentToLoad", 2);
                            startActivity(restaurantIntent);
                            finish();
                        }
                        else {
                            registered = false;
                            try {
                                throw task.getException();
                            }
                            catch (FirebaseAuthWeakPasswordException e){
                                etNewPassword.setError("Password is not strong");
                            }
                            catch (FirebaseAuthInvalidCredentialsException e){
                                etEmail.setError("Invalid email");
                            }
                            catch (FirebaseAuthUserCollisionException e){
                                etEmail.setError("User already exists");
                            }
                            catch (Exception e) {
                                Log.wtf("Errror: ", e.toString());
                            }
                        }
                    }
                });
    }

    public void addUserToDatabase(){
        mUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserReference = mFirebaseDatabase.getReference("Users/" + mUser.getUid());

        final User user = new User();
        user.setUid(mUser.getUid());
        user.setUsername(name);
        user.setPhoneNumber(phone);
        user.setEmailId(email);

        mUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserReference.setValue(user);
                registered = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
