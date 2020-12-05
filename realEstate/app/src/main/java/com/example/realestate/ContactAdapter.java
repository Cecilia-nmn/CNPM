package com.example.realestate;

import android.content.Context;
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

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder>{
    private List<ContactRequest> requests;
    private Context ctx;

    public ContactAdapter(Context ctx, List<ContactRequest> requests) {
        this.ctx = ctx;
        this.requests = requests;
    }

    @NonNull
    @Override
    public ContactAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contact_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.MyViewHolder holder, int position) {
        ContactRequest request = requests.get(position);
        holder.title.setText(request.getUsername());
        holder.content.setText(request.getContacInf());

        holder.delete.setOnClickListener(view -> {
            removeItem(position, request);
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, content;
        private ImageView delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitleCnt);
            content = itemView.findViewById(R.id.txtContentCtn);
            delete = itemView.findViewById(R.id.btnDeleteCnt);
        }
    }

    private void removeItem(int position, ContactRequest report) {
        MovieService service = MovieApi.getClient().create(MovieService.class);
        service.deleteRequest(report).enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                Response<String> data = response.body();
                if (data == null) {
                    Toast.makeText(ctx, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                }
                else {
                    Log.e("TAG", "onResponse: " + data.data);
                    requests.remove(position);
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
