package com.example.realestate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestate.network.MovieApi;
import com.example.realestate.network.MovieService;
import com.example.realestate.network.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MUAdapter extends RecyclerView.Adapter<MUAdapter.MyViewHolder> {

    private List<User> users;
    private Context ctx;

    public MUAdapter(Context ctx, List<User> users) {
        this.ctx = ctx;
        this.users = users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_m_u_adapter, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = users.get(position);
        holder.email.setText(user.getEmail());
        holder.name.setText(user.getUsername());
        holder.password.setText(user.getPassword());

        holder.delete.setOnClickListener(view -> {
            removeItem(position, user);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView email, name, password;
        private ImageView delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.txtEmail);
            name = itemView.findViewById(R.id.txtName);
            password = itemView.findViewById(R.id.txtPassword);
            delete = itemView.findViewById(R.id.btnDeleteMu);
        }
    }

    private void removeItem(int position, User user) {
        MovieService service = MovieApi.getClient().create(MovieService.class);
        service.deleteUser(user).enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                Response<String> data = response.body();
                if (data == null) {
                    Toast.makeText(ctx, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                }
                else {
                    Log.e("TAG", "onResponse: " + data.data);
                    users.remove(position);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Response<String>> call, Throwable t) {
                Log.e("TAG", "Error " + t.getMessage());
                Toast.makeText(ctx, "Không kết nối được đến server! Vui lòng kiểm tra Internet", Toast.LENGTH_LONG).show();
            }
        });
    }
}