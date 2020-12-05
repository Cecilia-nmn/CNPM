package com.example.realestate;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestate.network.MovieApi;
import com.example.realestate.network.MovieService;
import com.example.realestate.network.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder>{
    private Context ctx;
    private List<Post> posts;

    public PostAdapter(Context ctx, List<Post> posts){
        this.ctx = ctx;
        this.posts = posts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.post_adapter_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.title.setText(post.getTitle());
        holder.username.setText(post.getUsername());
        holder.address.setText(post.getAddress());
        holder.area.setText(post.getArea());

        holder.delete.setOnClickListener(view->{
            removeItem(position, post);
        });
        holder.change.setOnClickListener(view->{
            Intent intent = new Intent(ctx, ChangePost.class);
            intent.putExtra("post", post);
            ctx.startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, address, area, username;
        private ImageView delete, change;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);
            address = itemView.findViewById(R.id.txtAddress);
            area = itemView.findViewById(R.id.txtArea);
            username = itemView.findViewById(R.id.txtUsername);
            delete = itemView.findViewById(R.id.btnDeleteP);
            change = itemView.findViewById(R.id.btnChangeP);
        }
    }

    private void removeItem(int position, Post post) {
        MovieService service = MovieApi.getClient().create(MovieService.class);
        service.deletePost(post).enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                Response<String> data = response.body();
                if (data == null) {
                    Toast.makeText(ctx, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                }
                else {
                    Log.e("TAG", "onResponse: " + data.data);
                    posts.remove(position);
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
