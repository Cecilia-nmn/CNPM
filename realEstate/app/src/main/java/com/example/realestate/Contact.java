package com.example.realestate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.realestate.network.MovieApi;
import com.example.realestate.network.MovieService;
import com.example.realestate.network.Response;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Contact extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private List<ContactRequest> requests;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        recyclerView = findViewById(R.id.recyclerViewCon);
        requests = new ArrayList<>();
        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        initReport();
    }

    private void initReport() {
        MovieService service = MovieApi.getClient().create(MovieService.class);
        service.getContact(user).enqueue(new Callback<Response<List<ContactRequest>>>() {
            @Override
            public void onResponse(Call<Response<List<ContactRequest>>> call, retrofit2.Response<Response<List<ContactRequest>>> response) {
                Response<List<ContactRequest>> data = response.body();
                if (data == null) {
                    Toast.makeText(Contact.this, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                }
                else {
                    if (data.data != null) {
                        requests = data.data;
                        //set adapter
                        adapter = new ContactAdapter(Contact.this, requests);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Contact.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);

                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(Contact.this, R.drawable.divider));
                        recyclerView.addItemDecoration(dividerItemDecoration);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<List<ContactRequest>>> call, Throwable t) {
                Log.e("TAG", "Error " + t.getMessage());
                Toast.makeText(Contact.this, "Không kết nối được đến server! Vui lòng kiểm tra Internet", Toast.LENGTH_LONG).show();

            }
        });
    }
}