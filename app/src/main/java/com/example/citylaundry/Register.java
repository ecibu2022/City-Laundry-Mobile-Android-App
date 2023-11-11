package com.example.citylaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private TextView login;
    private EditText firstName, lastName, email, phone, address, username, password, kin, userNO;
    private Button register;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private String Gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName=findViewById(R.id.fname);
        lastName=findViewById(R.id.lname);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        address=findViewById(R.id.address);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        kin=findViewById(R.id.next);
        userNO=findViewById(R.id.no);
        register=findViewById(R.id.submit_button);
        login=findViewById(R.id.login);
        radioGroup=findViewById(R.id.radioGroup);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference("users");
        progressDialog=new ProgressDialog(Register.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(checkedId);
                Gender=radioButton.getText().toString();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FirstName=firstName.getText().toString().trim();
                String LastName=lastName.getText().toString().trim();
                String Email=email.getText().toString().trim();
                String Phone=phone.getText().toString().trim();
                String Address=address.getText().toString().trim();
                String Username=username.getText().toString().trim();
                String Password=password.getText().toString().trim();
                String Kin=kin.getText().toString().trim();
                String UserNO=userNO.getText().toString().trim();

                String Role="user";

                progressDialog.setTitle("Registration in progress.. .. ..");
                progressDialog.setMessage("Please wait....");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UserRegistration user=new UserRegistration(FirstName, LastName, Email, Phone, Address, Username, Kin, UserNO, Gender, Role);
                            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        startActivity(new Intent(Register.this, Login.class));
                                        finish();
                                    }else{
                                        Toast.makeText(Register.this, "Error try again", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this, "Error try again", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });
                        }else{
                            Toast.makeText(Register.this, "Error try again later", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Registration failed try again", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });

    }

}