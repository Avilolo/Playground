package com.example.playground.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.playground.Classes.User;
import com.example.playground.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;
    EditText send_text;
    ImageButton send_btn;
    Toolbar toolbar;
    Intent intent;
    FirebaseUser firebaseUser;
    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        findViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String userId = intent.getStringExtra("userid");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        databaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default"))
                    profile_image.setImageResource(R.drawable.default_profile);
                else
                    Glide.with(MessageActivity.this).load(user.getImageURL()).into(profile_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = send_text.getText().toString();
                if (!msg.equals(""))
                    sendMessage(firebaseUser.getUid(), userId, msg);
                send_text.setText("");// reset the text
            }
        });

    }

    private void findViews() {
        toolbar = findViewById(R.id.message_toolbar);
        profile_image = findViewById(R.id.message_profile_iv);
        send_text = findViewById(R.id.text_send);
        send_btn = findViewById(R.id.send_btn);
        username = findViewById(R.id.message_name);
        intent = getIntent();
    }

    private void sendMessage(String sender, String receiver, String msg) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", msg);

        reference.child("Chats").push().setValue(hashMap);
    }





























}