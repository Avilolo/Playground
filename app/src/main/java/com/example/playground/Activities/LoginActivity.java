package com.example.playground.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.playground.Classes.User;
import com.example.playground.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

    AppCompatButton registerBtn, loginBtn, createBtn;
    TextInputEditText username_txt, email_txt, password_txt;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseRef;

    // TODO REMOVE FOR AUTO LOGIN. PUT ON NOTE IF YOU DELETE FIREBASE DATA WITHOUT LOGOUT FIRST :)
    @Override
    protected void onStart() {
        //auto login
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        findViews();
        firebaseAuth = firebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username_txt.setVisibility(View.VISIBLE);
                createBtn.setVisibility(View.VISIBLE);
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_txt.getText().toString();
                String email = email_txt.getText().toString();
                String pass = password_txt.getText().toString();

                if (username.isEmpty() || email.isEmpty() || pass.isEmpty())
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                else if (pass.length() < 6 || username.length() > 11 || username.length() < 2) {
                    Toast.makeText(LoginActivity.this, "Password must contain at least 6 letters", Toast.LENGTH_SHORT)
                            .show();
                }
                else
                    register(username, email, pass);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username_txt.getVisibility() == view.VISIBLE) {
                    username_txt.setVisibility(View.INVISIBLE);
                    createBtn.setVisibility(View.INVISIBLE);
                }
                String email = email_txt.getText().toString();
                String pass = password_txt.getText().toString();

                if (email.isEmpty() || pass.isEmpty())
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                else {
                    firebaseAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
            }
        });

    }

    private void findViews() {
        registerBtn = findViewById(R.id.register_btn);
        loginBtn = findViewById(R.id.login_btn);
        createBtn = findViewById(R.id.create_account_btn);
        username_txt = findViewById(R.id.user_name_etv);
        email_txt = findViewById(R.id.email_etv);
        password_txt = findViewById(R.id.password_etv);
    }

    private void register(String username, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            databaseRef = FirebaseDatabase
                                    .getInstance()
                                    .getReference("Users")
                                    .child(userId);

                            //firebase wont save empty lists so we initiate with a false parameter
                            //username length cant be under 2 (no name with one latter) so false parameter wont ever match
                            HashMap<String, Object> friends = new HashMap<>();
                            friends.put("x", "x");
                            ArrayList<String> games = new ArrayList<String>();
                            //this list will be override once user will set games at his profile
                            //therefor game "x" will not affect the app
                            games.add("x");

                            User user = new User(userId, "default", username,"offline",
                                    friends, games);

                            databaseRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Register complete", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Unable to register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}