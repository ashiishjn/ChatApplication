package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;

    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);



        userAdapter = new UserAdapter(this);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userAdapter.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String uid = dataSnapshot.getKey();
//                    Toast.makeText(UsersActivity.this, uid, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(UsersActivity.this, FirebaseAuth.getInstance().getUid(), Toast.LENGTH_SHORT).show();
                    if(!uid.equals(FirebaseAuth.getInstance().getUid())){
//                        String a1 = dataSnapshot.child("userEmail").getValue().toString();
//                        String a2 = dataSnapshot.child("userName").getValue().toString();
//                        String a3 = dataSnapshot.child("userId").getValue().toString();
//                        String a4 = dataSnapshot.child("userPassword").getValue().toString();
////                        Toast.makeText(UsersActivity.this, a1+" "+a2+" "+a3+" "+a4, Toast.LENGTH_SHORT).show();
//                        UserModel userModel = new UserModel(a1,a3,a2,a4);
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        userAdapter.add(userModel);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem)
    {
        if(menuItem.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(UsersActivity.this, LoginActivity.class));
            finish();
            return true;
        }
        return false;
    }

}








//
//
//    ListView usersList;
//    TextView noUsersText;
//    List<String> al = new ArrayList<>();
//    ProgressDialog pd;
//
//    UserDetails ud = new UserDetails();
//
//    String users[] = new String[100];
//    String ids[] = new String[100];
//    int counter=0;
//
//    DatabaseReference reference;
//    FirebaseAuth auth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_users);
//
//        auth = FirebaseAuth.getInstance();
//        String key = auth.getUid();
//        reference = FirebaseDatabase.getInstance().getReference("Chats");
//
//        ud.username_ID = key;
//        usersList = (ListView)findViewById(R.id.usersList);
//        noUsersText = (TextView)findViewById(R.id.noUsersText);
//
//        pd = new ProgressDialog(UsersActivity.this);
//        pd.setMessage("Loading...");
//        pd.show();
//
//        DatabaseReference references = FirebaseDatabase.getInstance().getReference("Users").child(key);
//        references.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ud.username = snapshot.child("Name").getValue(String.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                al.clear();
//                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
//                    String id = datasnapshot.getKey();
//                    if(id.indexOf(key) != -1)
//                    {
//                        int n = id.indexOf(key);
//                        String s;
//                        if(n == 0)
//                            s= id.substring(id.indexOf('_')+1);
//                        else
//                            s = id.substring(0,id.indexOf('_'));
//
//
//                        DatabaseReference references = FirebaseDatabase.getInstance().getReference("Users").child(s);
//                        references.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                ids[counter] = id;
//                                users[counter++] = snapshot.child("Name").getValue(String.class);
//                                onSuccess();
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//
//
//    public void onSuccess() {
//        String users1[] = new String[counter];
//        for(int i=0;i<counter;i++)
//            users1[i] = users[i];
//        usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users1));
//        usersList.setOnItemClickListener(new ListView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ud.chatWith = users1[position];
//                ud.chatWith_ID = ids[position];
//                Intent intent = new Intent(UsersActivity.this, ChatActivity.class);
//                intent.putExtra("ID", ids[position]);
//                startActivity(intent);
//
////                Toast.makeText(UsersActivity.this, ud.chatWith_ID, Toast.LENGTH_LONG).show();
//            }
//        });
//        pd.dismiss();
//    }
//}