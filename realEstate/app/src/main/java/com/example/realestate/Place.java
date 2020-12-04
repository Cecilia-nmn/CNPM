package com.example.realestate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Place extends AppCompatActivity {

    TextView txt;
    ListView lvPlaceDone;
    ArrayList<ArrayList<String>> arr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        txt = findViewById(R.id.txtPlaceDone);
        lvPlaceDone = findViewById(R.id.lvPlaceDone);

        String[] a = {"A","B","C","D","E","F","G","H","I","J","K","L","M","O","P","Q","R","S","T","U","V","W","X","Y","Z","K"};

        arr = new ArrayList<>();
        for (int i = 0;i< 24 ; i++){
            ArrayList<String> arr1 = new ArrayList<String>();
            for (int j=i;j<i+3;j++){
                arr1.add(a[j]);

            }
            arr.add(arr1);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(Place.this, android.R.layout.simple_list_item_1,arr.get(TimKiem.str));
        lvPlaceDone.setAdapter(arrayAdapter);

    }
}