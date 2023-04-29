package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    LinearLayout layout;
    ScrollView scrollView;

    String recieverId;
    DatabaseReference databaseReferenceSender, databaseReferenceReciever;
    String senderRoom, recieverRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recieverId = getIntent().getStringExtra("id");

        layout = findViewById(R.id.layout1);
        scrollView = findViewById(R.id.scrollView);

        senderRoom =FirebaseAuth.getInstance().getUid() + recieverId;
        recieverRoom = recieverId+FirebaseAuth.getInstance().getUid();

        databaseReferenceSender = FirebaseDatabase.getInstance().getReference("Chats").child(senderRoom);
        databaseReferenceReciever = FirebaseDatabase.getInstance().getReference("Chats").child(recieverRoom);

        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                layout.removeAllViews();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                        if(messageModel.getSenderId().equals(FirebaseAuth.getInstance().getUid()))
                            addMessageBox(messageModel.getMessage(), 2);
                        else
                            addMessageBox(messageModel.getMessage(), 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(ChatActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 7.0f;
        textView.setPadding(10,10,10,10);
        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
            lp2.setMargins(0,10,200,50);
            textView.setPadding(60,10,10,10);
            textView.setBackgroundResource(R.drawable.bubble_in);
            textView.setTextSize(20);

        }
        else{
            lp2.setMargins(200,50,0,50);
            lp2.gravity = Gravity.RIGHT;
            textView.setPadding(10,10,60,10);
            textView.setBackgroundResource(R.drawable.bubble_out);
            textView.setTextSize(20);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);

        scrollView.fullScroll(View.FOCUS_DOWN);
    }


    public void send(View view)
    {
        EditText ed = findViewById(R.id.messageEd);
        String message = ed.getText().toString();
        ed.setText("");
        if(!message.trim().equals("")){
            sendMessage(message);
        }
    }

    private void sendMessage(String message) {
//        String messageId = UUID.randomUUID().toString();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
//        Toast.makeText(this, timeStamp, Toast.LENGTH_SHORT).show();
//        addMessageBox(message, 2);
        MessageModel messageModel = new MessageModel(timeStamp,FirebaseAuth.getInstance().getUid(),message);
        databaseReferenceSender.child(timeStamp).setValue(messageModel);
        databaseReferenceReciever.child(timeStamp).setValue(messageModel);

    }

}






























//    LinearLayout layout;
//    RelativeLayout layout_2;
//    ImageView sendButton;
//    EditText messageArea;
//    ScrollView scrollView;
//    String ID;
//    String user="", chat="";
//
//    UserDetails ud = new UserDetails();
//
//    DatabaseReference reference, reference2;
//    FirebaseAuth auth;
//    String key;
//    String Mnum="";




//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//
//        Intent intent = getIntent();
//        ID = intent.getStringExtra("ID");
//
//
//        layout = findViewById(R.id.layout1);
//        layout_2 = findViewById(R.id.layout2);
//        sendButton = findViewById(R.id.sendButton);
//        messageArea = findViewById(R.id.messageArea);
//        scrollView = findViewById(R.id.scrollView);
//
//        auth = FirebaseAuth.getInstance();
//        key = auth.getUid();
//
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String messageText = messageArea.getText().toString();
//                if(!messageText.equals(""))
//                {
//                    int n = Integer.parseInt(Mnum.substring(1));
//                    Mnum = "M"+ String.valueOf(++n);
//                    DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Chats").child(ID);
//                    dr.child(Mnum).child("User").setValue(key);
//                    dr.child(Mnum).child("Chat").setValue(messageText);
//                }
//            }
//        });
//
//        reference = FirebaseDatabase.getInstance().getReference("Chats").child(ID);
//        reference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
////                Toast.makeText(ChatActivity.this, snapshot.getKey(), Toast.LENGTH_SHORT).show();
//                Mnum = snapshot.getKey();
//
//                for(int x=0;x<10000;x++);
//
//                user = snapshot.child("User").getValue(String.class);
//                chat = snapshot.child("Chat").getValue(String.class);
////                Toast.makeText(ChatActivity.this, chat, Toast.LENGTH_SHORT).show();
//                if(user == null || chat == null)
//                {
//                    DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Users").child(ID);
//                    dr.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            user = snapshot.child(Mnum).child("User").getValue(String.class);
//                            chat = snapshot.child(Mnum).child("Chat").getValue(String.class);
//                            if (user.equals(key))
//                                addMessageBox(chat, 2);
//                            else
//                                addMessageBox(chat, 1);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }else
//                    if (user.equals(key))
//                        addMessageBox(chat, 2);
//                    else
//                        addMessageBox(chat, 1);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
//
//    public void addMessageBox(String message, int type){
//        TextView textView = new TextView(ChatActivity.this);
//        textView.setText(message);
//
//        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp2.weight = 7.0f;
//        textView.setPadding(10,10,10,10);
//        if(type == 1) {
//            lp2.gravity = Gravity.LEFT;
//            lp2.setMargins(0,10,200,50);
//            textView.setPadding(60,10,10,10);
//            textView.setBackgroundResource(R.drawable.bubble_in);
//            textView.setTextSize(20);
//
//        }
//        else{
//            lp2.setMargins(200,50,0,50);
//            lp2.gravity = Gravity.RIGHT;
//            textView.setPadding(10,10,60,10);
//            textView.setBackgroundResource(R.drawable.bubble_out);
//            textView.setTextSize(20);
//        }
//        textView.setLayoutParams(lp2);
//        layout.addView(textView);
//        scrollView.fullScroll(View.FOCUS_DOWN);
//    }
//}