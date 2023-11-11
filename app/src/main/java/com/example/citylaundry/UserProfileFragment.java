package com.example.citylaundry;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileFragment extends Fragment {
    private View formContainer;
    private Button editProfileButton, update_profile;
    private EditText editEmail, editPhone, editAddress;
    private TextView full_name, email, phone, address;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        currentUser = mAuth.getCurrentUser();
        full_name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        phone=view.findViewById(R.id.phone);
        address=view.findViewById(R.id.address);

        editEmail=view.findViewById(R.id.editEmail);
        editPhone=view.findViewById(R.id.editPhone);
        editAddress=view.findViewById(R.id.editAddress);
        formContainer = view.findViewById(R.id.formContainer);
        update_profile=view.findViewById(R.id.update_profile);
        editProfileButton=view.findViewById(R.id.editProfile);

//        Showing Form For Editing Profile When Edit Profile Button Is Clicked
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formContainer.getVisibility() == View.VISIBLE) {
                    formContainer.setVisibility(View.GONE);
                } else {
                    formContainer.setVisibility(View.VISIBLE);
                    // Retrieve the user data from TextViews
                    String Email = email.getText().toString();
                    String Phone = phone.getText().toString();
                    String Address = address.getText().toString();

//                            Setting into Text Fields
                    editEmail.setText(Email);
                    editPhone.setText(Phone);
                    editAddress.setText(Address);
                }
            }
        });
//        End of the Form

        // Retrieve the user's profile data from Firebase
        mDatabase.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Get the user data from the snapshot
                String fName = snapshot.child("firstName").getValue(String.class);
                String lName = snapshot.child("lastName").getValue(String.class);
                String Email=snapshot.child("email").getValue(String.class);
                String Phone = snapshot.child("phone").getValue(String.class);
                String Address = snapshot.child("address").getValue(String.class);

                // Set the TextViews with the user data
                full_name.setText(fName+ "  " +lName);
                email.setText(Email);
                phone.setText(Phone);
                address.setText(Address);
                email.setText(Email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });


        // Update Profile
        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the user data from EditTexts
                String EditEmail = editEmail.getText().toString();
                String EditPhone = editPhone.getText().toString();
                String EditAddress = editAddress.getText().toString();

                // Update the user data in the Firebase database
                mDatabase.child(currentUser.getUid()).child("email").setValue(EditEmail);
                mDatabase.child(currentUser.getUid()).child("phone").setValue(EditPhone);
                mDatabase.child(currentUser.getUid()).child("address").setValue(EditAddress);

                // Update the email in Firebase Authentication
                currentUser.updateEmail(EditEmail)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Email update failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Setting new values updated
                // Retrieve the user's profile data from Firebase
                mDatabase.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Get the user data from the snapshot
                        String fName = snapshot.child("firstName").getValue(String.class);
                        String lName = snapshot.child("lastName").getValue(String.class);
                        String Email=snapshot.child("email").getValue(String.class);
                        String Phone = snapshot.child("phone").getValue(String.class);
                        String Address = snapshot.child("address").getValue(String.class);

                        // Set the TextViews with the user data
                        full_name.setText(fName+ "  " +lName);
                        email.setText(Email);
                        phone.setText(Phone);
                        address.setText(Address);
                        email.setText(Email);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error
                    }
                });

                // Close form
                formContainer.setVisibility(View.GONE);
            }
        });


        return  view;
    }
}