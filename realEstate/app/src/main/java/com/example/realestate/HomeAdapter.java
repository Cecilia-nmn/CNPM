package com.example.realestate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context ctx;
    private List<Post> posts;
    private User user;
    public HomeAdapter(Context ctx, List<Post> posts, User user){
        this.ctx = ctx;
        this.posts = posts;
        this.user = user;
    }

    @NonNull
    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.post_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.MyViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.title.setText(post.getTitle());
        holder.address.setText(post.getAddress());
        holder.area.setText(post.getArea());
        holder.content.setText(post.getDescript());

        String imagePath = "http://10.0.2.2:8888/movie_api/movie_poster/" + post.getImage();
        Picasso.with(ctx).load(imagePath).into(holder.postImg);

        holder.itemView.setOnClickListener(view->{
            Intent intent = new Intent(ctx, XemBaiViet.class);
            intent.putExtra("user", user);
            intent.putExtra("post", post);
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView postImg;
        TextView title, address, area, content;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            postImg = itemView.findViewById(R.id.postImage);
            title = itemView.findViewById(R.id.txtTitleH);
            address = itemView.findViewById(R.id.txtAddressH);
            area = itemView.findViewById(R.id.txtAreaH);
            content = itemView.findViewById(R.id.txtContentH);
        }
    }
}
