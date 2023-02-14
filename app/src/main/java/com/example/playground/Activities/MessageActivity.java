package com.example.playground.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.playground.Classes.Chat;
import com.example.playground.Classes.MessageAdapter;
import com.example.playground.Classes.User;
import com.example.playground.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    RecyclerView recyclerView;
    MessageAdapter msgAdapter;
    List<Chat> chats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        findViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessageActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        String userId = intent.getStringExtra("userid");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        databaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default"))
                    profile_image.setImageResource(R.drawable.default_profile);
                else
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
                readMessages(firebaseUser.getUid(), userId, user.getImageURL());
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
        recyclerView = findViewById(R.id.message_rv);
        intent = getIntent();
    }

    private void sendMessage(String sender, String receiver, String msg) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", msg);

        reference.child("Chats").push().setValue(hashMap);

        //update friends lists of the sender & receiver
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, String> temp = new HashMap<>();
                User uSender = null;
                User uReceiver = null;
           //     String senderName = "";  //We always know the sender name since its the current user
             //   String receiverName = "";    // but we still save it for an "easier" code understanding
    /*          Problem: we can't get both sender and receiver id & name at one iteration
                Solution: we update pre initiated temps with all the values and update the firebase
                after the loop

                Example:
                if (user.getId().equals(sender) && !user.getFriends().containsValue(receiver)) {
                        user.addFriend(receiver, "x"); -> //in this part of the code we have only
                                                        // the receiver id but not his name
                    }

                    if (user.getId().equals(receiver) && !user.getFriends().containsValue(sender))
                        friendsToAdd.put(sender, "x"); -> //same goes for here only with sender
                    userFriends.updateChildren(friendsToAdd);
    */
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User user = snap.getValue(User.class);
                    HashMap<String, Object> friendsToAdd = new HashMap<>();

                    //update if sender doesn't have receiver as friend
                    if (user.getId().equals(sender) && !user.getFriends().containsValue(receiver))
                        uSender = user;
                    //update if receiver doesn't have sender as friend
                    if (user.getId().equals(receiver) && !user.getFriends().containsValue(sender))
                        uReceiver = user;
                }
                //update the current(sender) user
                uSender.addFriend(uReceiver.getId(), uReceiver.getUsername());

                DatabaseReference userFriends = FirebaseDatabase
                        .getInstance()
                        .getReference("Users")
                        .child(uSender.getId())
                        .child("friends");
                userFriends.updateChildren(uSender.getFriends());

                //update receiver
                uReceiver.addFriend(uSender.getId(), uSender.getUsername());
                userFriends = FirebaseDatabase
                        .getInstance()
                        .getReference("Users")
                        .child(uReceiver.getId())
                        .child("friends");
                userFriends.updateChildren(uReceiver.getFriends());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readMessages(final String myid, final String userid, String imageurl) {
        chats = new ArrayList<>();

        databaseRef = FirebaseDatabase.getInstance().getReference("Chats");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                for (DataSnapshot dataSnap : snapshot.getChildren()) {
                    Chat chat = dataSnap.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid))
                        chats.add(chat);
                }
                msgAdapter = new MessageAdapter(MessageActivity.this, chats, imageurl);
                recyclerView.setAdapter(msgAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void status(String status) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        databaseRef.updateChildren(hashMap);
    }

    @Override
    public void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    public void onPause() {
        super.onPause();
        status("offline");
    }

}