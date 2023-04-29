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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText ed1, ed2;

    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        ed1 = findViewById(R.id.userId);
        ed2 = findViewById(R.id.Password);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, UsersActivity.class));
            finish();
        }
    }

    public void Login(View v)
    {
        String a1 = ed1.getText().toString();
        String a2 = ed2.getText().toString();
        auth.signInWithEmailAndPassword(a1,a2).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startActivity(new Intent(LoginActivity.this, UsersActivity.class));
                finish();
            }
        });
    }

    public void SignUp(View v)
    {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }
}