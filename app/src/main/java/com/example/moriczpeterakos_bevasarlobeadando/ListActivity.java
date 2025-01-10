package com.example.moriczpeterakos_bevasarlobeadando;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private ListView termekekListView;
    private Button visszaButton;
    private List<Termekek> termekekList;
    private TermekekAdapter termekekAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

    }
    public void init(){
        termekekListView = findViewById(R.id.termekekListView);
        visszaButton = findViewById(R.id.back2Button);
        termekekList =  new ArrayList<>();
        termekekAdapter = new TermekekAdapter(termekekList, this);;
        termekekListView.setAdapter(termekekAdapter);
        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);
        loadTermekek(apiService);

        termekekListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Termekek clickedTermek = termekekList.get(position);
                Intent intent = new Intent(ListActivity.this, TermekActivity.class);
                intent.putExtra("termekid", clickedTermek.getId());
                startActivity(intent);
                finish();

            }
        });
        visszaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void loadTermekek(RetrofitApiService apiService) {
        apiService.getAllTermekek().enqueue(new Callback<List<Termekek>>() {
            @Override
            public void onResponse(Call<List<Termekek>> call, Response<List<Termekek>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    termekekList.clear();
                    termekekList.addAll(response.body());
                    termekekAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ListActivity.this, "Nem sikerült betölteni a terméklistát", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Termekek>> call, Throwable t) {
                Toast.makeText(ListActivity.this, "Hiba történt a terméklista betöltésekor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}