package com.IAU.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import android.util.Log;

public class MainActivity extends AppCompatActivity {


    private EditText emailEditText, passwordEditText;
    private Button loginButton, signUpButton;
    private String email,password;
    private TextView successTextView;
    private FirebaseAuth mAuth;
    private static final String TAG = "YourActivityOrClass"; // Declare TAG here


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String uid = user.getUid();
            Log.d(TAG, "User UID: " + uid);
        } else {
            Log.d(TAG, "No user signed in");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        successTextView = findViewById(R.id.successTextView);
        signUpButton = findViewById(R.id.signUpButton);
        //BURADA LOGIN KISMINI ATLADIM...
        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
        startActivity(intent);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if (validateUserCreditentialsForLogin(email, password)) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Login kısımı buranın altında
                            //Login olunursa yapılacaklar
                            if (task.isSuccessful()) {
                                //Kullanıcı tokenini alıyor
                                //Equals || Geliştirmek için adminler array olarak konulup döngü kullanılabilir
                                if ("oIeWLy5blBOq3HYBIkQxyYW7qGR2".equals(user.getUid())) {
                                    // Login Doğrulama ve Navigasyon
                                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                                    startActivity(intent);
                                } else {
                                    // Login Doğrulama ve Navigasyon
                                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                //Login olunamazsa yapılacaklar
                                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if (validateUserCreditentials(email, password)) {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "You Are Successfully Registered.", Toast.LENGTH_SHORT).show();
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                }
            }
        });

    }

    public boolean validateUserCreditentials(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(MainActivity.this, "Email Cant Be Empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(MainActivity.this, "Email Already Registered.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Password Cant Be Empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(MainActivity.this, "Password Should Be Longer Than 6 Characters.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean validateUserCreditentialsForLogin(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(MainActivity.this, "Email Cant Be Empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Password Cant Be Empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(MainActivity.this, "Password Should Be Longer Than 6 Characters.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
