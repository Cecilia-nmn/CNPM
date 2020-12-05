package com.example.realestate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestate.network.MovieApi;
import com.example.realestate.network.MovieService;
import com.example.realestate.network.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AddPost extends AppCompatActivity {

    TextView txtTD,txtND,txtKV,txtSP,txtKT;
    EditText edtTD,edtND,edtSP,edtCD,edtHinh,edtDiaChi;
    RadioGroup rdg;
    Button btnDB;
    RadioButton radioQ1,radioQ2,radioQ3,radioQ4,radioQ5;
    public static String string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bai_viet);

        txtTD = findViewById(R.id.txtTieuDe);
        txtND = findViewById(R.id.txtNoiDung);
        txtKV = findViewById(R.id.txtKhuVuc);
        txtSP = findViewById(R.id.txtSoPhong);
        txtKT = findViewById(R.id.txtKichThuoc);
        edtTD = findViewById(R.id.edtTieuDe);
        edtND = findViewById(R.id.edtNoiDung);
        edtSP = findViewById(R.id.edtSoPhong);
        edtCD = findViewById(R.id.edtCD);
        edtHinh = findViewById(R.id.edtHinh);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        rdg = findViewById(R.id.rdg);
        btnDB = findViewById(R.id.buttonDB);
        radioQ1 = findViewById(R.id.radioQ1);
        radioQ2 = findViewById(R.id.radioQ2);
        radioQ3 =findViewById(R.id.radioQ3);
        radioQ4 =findViewById(R.id.radioQ4);
        radioQ5 = findViewById(R.id.radioQ5);


        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user");

        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                doIt(group,checkedId);
            }
        });

        btnDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtCD.getText().toString().equals("") ||
                    edtND.getText().toString().equals("") ||
                    edtSP.getText().toString().equals("") ||
                    edtTD.getText().toString().equals("") ||
                    edtHinh.getText().toString().equals("") ||
                    edtDiaChi.getText().toString().equals("")) {
                    Toast.makeText(AddPost.this, "Vui lòng nhập ddầy đủ thông tin", Toast.LENGTH_LONG).show();
                }
                else {
                    String title = edtTD.getText().toString();
                    String img = edtHinh.getText().toString();
                    String address = edtDiaChi.getText().toString();
                    String size = edtSP.getText().toString();
                    String rooms = edtSP.getText().toString();
                    String descript = edtND.getText().toString();
                    Post post = new Post(title, img, address, string, size, rooms, descript, user.getUsername());
                    MovieService service = MovieApi.getClient().create(MovieService.class);
                    service.sendPost(post).enqueue(new Callback<Response<String>>() {
                        @Override
                        public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                            Response<String> data = response.body();
                            if (data == null) {
                                Toast.makeText(AddPost.this, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Log.e("TAG", "onResponse: " + data.data);
                            }
                        }

                        @Override
                        public void onFailure(Call<Response<String>> call, Throwable t) {
                            Log.e("TAG", "Error " + t.getMessage());
                            Toast.makeText(AddPost.this, "Không kết nối được đến server! Vui lòng kiểm tra Internet", Toast.LENGTH_LONG).show();

                        }
                    });

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddPost.this);
                    builder.setCancelable(false);
                    builder.setTitle("Gửi thành công");
                    builder.setPositiveButton("OK", (dialogInterface, i) -> {
                        finish();
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
    private void doIt(RadioGroup group,int checkedId){
        int checked = rdg.getCheckedRadioButtonId();
        if(checked == R.id.radioQ1){
            string = (String) radioQ1.getText();
        }
        else if(checked == R.id.radioQ2){
            string = (String) radioQ2.getText();
        }
        else if(checked == R.id.radioQ3){
            string = (String) radioQ3.getText();
        }
        else if(checked == R.id.radioQ4){
            string = (String) radioQ4.getText();
        }
        else if(checked == R.id.radioQ5){
            string = (String) radioQ5.getText();
        }
    }
}