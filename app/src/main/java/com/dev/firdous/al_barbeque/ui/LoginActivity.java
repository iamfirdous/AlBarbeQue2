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
import android.widget.TextView;
import android.widget.Toast;

import com.dev.firdous.al_barbeque.MainActivity;
import com.dev.firdous.al_barbeque.R;
import com.dev.firdous.al_barbeque.models.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by firdous on 13/9/17.
 */

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserReference;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private List<AuthUI.IdpConfig> mProviders;

    private EditText etEmail, etPassword;
    private Button signIn;
    private TextView tvSignUp;
    private String mVerificationId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        etEmail = findViewById(R.id.editText_loginEmail);
        etPassword = findViewById(R.id.editText_loginPassword);
        signIn = findViewById(R.id.button_signIn);
        tvSignUp = findViewById(R.id.textView_signUp);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                boolean notEmpty = true;

                if(email.equals("") || email == null){
                    etEmail.setError("Enter your email address");
                    notEmpty = false;
                }
                if(password.equals("") || password == null){
                    etPassword.setError("Enter your password");
                    notEmpty = false;
                }
                if(notEmpty){
                    signInWithEmailAndPassword(email, password);
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
                finish();
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

    private void signInWithEmailAndPassword(String email, String password){
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Signed in successfully", Toast.LENGTH_LONG).show();
                            Intent restaurantIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            restaurantIntent.putExtra("fragmentToLoad", 2);
                            startActivity(restaurantIntent);
                            finish();
                        }
                        else {
                            try {
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidUserException e){
                                etEmail.setError("Invalid email");
                            }
                            catch (FirebaseAuthInvalidCredentialsException e){
                                etPassword.setError("Incorrect password");
                            }
                            catch (Exception e) {
                                Log.wtf("Errror: ", e.toString());
                            }
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Log.e("Error: ", "Invalid verification: " +task.getException().toString());
                                Toast.makeText(LoginActivity.this, "Invalid verification", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
