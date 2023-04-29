package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    DatabaseReference reference;

    EditText ed1, ed2,ed3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed1 = findViewById(R.id.userId);
        ed2 = findViewById(R.id.Password);
        ed3 = findViewById(R.id.name);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");
    }

    public void Register(View v)
    {
        String a1 = ed1.getText().toString();
        String a2 = ed2.getText().toString();
        String a3 = ed3.getText().toString();
        auth.createUserWithEmailAndPassword(a1,a2).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(a3).build();
            FirebaseUser firebaseUser =  FirebaseAuth.getInstance().getCurrentUser();
            firebaseUser.updateProfile(userProfileChangeRequest);
            UserModel userModel = new UserModel(a1,FirebaseAuth.getInstance().getUid(),a3,a2);
            reference.child(FirebaseAuth.getInstance().getUid()).setValue(userModel);
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
             }
        });

    }
}