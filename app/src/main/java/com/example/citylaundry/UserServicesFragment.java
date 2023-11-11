package com.example.citylaundry;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class UserServicesFragment extends Fragment {
    private EditText item;
    private CheckBox washing, cleaning, ironing;
    private Button save;
    public UserServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_services, container, false);

        item=view.findViewById(R.id.item);
        washing=view.findViewById(R.id.washing);
        cleaning=view.findViewById(R.id.cleaning);
        ironing=view.findViewById(R.id.ironing);
        save=view.findViewById(R.id.save);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Item=item.getText().toString().trim();

            }
        });

        return view;
    }

}