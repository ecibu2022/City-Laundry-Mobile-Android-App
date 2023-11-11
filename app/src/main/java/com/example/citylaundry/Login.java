package com.example.citylaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private TextView register, forgot;
    private EditText email, pwd;
    private Button login;
    private FirebaseAuth mAuth;
    private ProgressDialog login_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register=findViewById(R.id.register);
        email=findViewById(R.id.email);
        pwd=findViewById(R.id.password);
        login=findViewById(R.id.login);
        forgot=findViewById(R.id.forgot);

        mAuth = FirebaseAuth.getInstance();

        login_dialog = new ProgressDialog(this);
        login_dialog.setCancelable(false);
        login_dialog.setMessage("Logging In...");

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString().trim();
                String Password = pwd.getText().toString().trim();

                if (Email.isEmpty()) {
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    email.setError("Enter a valid email address");
                    email.requestFocus();
                    return;
                } else if (Password.isEmpty()) {
                    pwd.setError("Password is required");
                    pwd.requestFocus();
                    return;
                } else if (Password.length() < 4) {
                    pwd.setError("Password should be at least 4 characters long");
                    pwd.requestFocus();
                    return;
                }

                login_dialog.show();

                mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String role = dataSnapshot.child("role").getValue(String.class);
                                    if (role.equals("admin")) {
                                        Toast.makeText(Login.this, "Admin Login Successful", Toast.LENGTH_SHORT).show();

                                    } else if (role.equals("user")) {
                                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, UserDashboard.class));
                                        finish();
                                    } else {
                                        Toast.makeText(Login.this, "Login Failed. Please try again", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(Login.this, "Login Failed. Please try again", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(Login.this, "Login Failed. Please try again", Toast.LENGTH_SHORT).show();
                        }
                        login_dialog.dismiss();
                    }
                });
            }
        });

    }
}