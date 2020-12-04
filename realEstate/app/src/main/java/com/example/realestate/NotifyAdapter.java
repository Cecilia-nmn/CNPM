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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestate.network.MovieApi;
import com.example.realestate.network.MovieService;
import com.example.realestate.network.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.MyViewHolder> {
    private List<Report> reports;
    private Context ctx;

    public NotifyAdapter(Context ctx, List<Report> reports) {
        this.ctx = ctx;
        this.reports = reports;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notify_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Report report = reports.get(position);
        holder.title.setText(report.getEmail());
        holder.content.setText(report.getContent());

        holder.delete.setOnClickListener(view -> {
            removeItem(position, report);
        });
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, content;
        private ImageView delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);
            content = itemView.findViewById(R.id.txtContent);
            delete = itemView.findViewById(R.id.btnDelete);
        }
    }

    private void removeItem(int position, Report report) {
        MovieService service = MovieApi.getClient().create(MovieService.class);
        service.deleteReport(report).enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                Response<String> data = response.body();
                if (data == null) {
                    Toast.makeText(ctx, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                }
                else {
                    Log.e("TAG", "onResponse: " + data.data);
                    reports.remove(position);
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