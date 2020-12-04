package com.example.realestate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TimKiem extends AppCompatActivity {

    TextView txtWhere;
    Button btn;
    ListView listView;
    ArrayList<String> arrayPlace;
    public static int str=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        txtWhere = findViewById(R.id.txtWhere);
        btn = findViewById(R.id.button);
        listView = findViewById(R.id.lvPlace);

        arrayPlace = new ArrayList<String>();
        arrayPlace.add("Quận 1");
        arrayPlace.add("Quận 2");
        arrayPlace.add("Quận 3");
        arrayPlace.add("Quận 4");
        arrayPlace.add("Quận 5");
        arrayPlace.add("Quận 6");
        arrayPlace.add("Quận 7");
        arrayPlace.add("Quận 8");
        arrayPlace.add("Quận 9");
        arrayPlace.add("Quận 10");
        arrayPlace.add("Quận 11");
        arrayPlace.add("Quận 12");
        arrayPlace.add("Quận Tân Bình");
        arrayPlace.add("Quận Tân Phú");
        arrayPlace.add("Quận Phú Nhuận");
        arrayPlace.add("Huyện Bình Chánh");
        arrayPlace.add("Quận Bình Thạnh");
        arrayPlace.add("Quận Thủ Đức");
        arrayPlace.add("Huyện Củ Chi");
        arrayPlace.add("Huyện Cần Giờ");
        arrayPlace.add("Quận Bình Tân");
        arrayPlace.add("Huyện Hóc Môn");
        arrayPlace.add("Huyện Nhà Bè");
        arrayPlace.add("Quận Gò Vấp");

        ArrayAdapter arrayAdapter = new ArrayAdapter(TimKiem.this, android.R.layout.simple_list_item_1,arrayPlace);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                str = position;
                Intent intent = new Intent(TimKiem.this,Place.class);
                startActivity(intent);
            }
        });


    }
}