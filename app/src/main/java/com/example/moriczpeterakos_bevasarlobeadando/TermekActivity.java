package com.example.moriczpeterakos_bevasarlobeadando;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermekActivity extends AppCompatActivity {

    private EditText editTextNameEdit;
    private EditText editTextPriceEdit;
    private EditText editTextCountEdit;
    private EditText editTextMeasureEdit;
    private Button editbutton;
    private Button deletebutton;
    private Button backbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_termek);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

    }
    public void init(){
        editTextNameEdit = findViewById(R.id.edittextNameEdit);
        editTextPriceEdit = findViewById(R.id.edittextPriceEdit);
        editTextCountEdit = findViewById(R.id.edittextCountEdit);
        editTextMeasureEdit = findViewById(R.id.edittextMeasureEdit);
        editbutton = findViewById(R.id.editButton);
        deletebutton = findViewById(R.id.deleteButton);
        backbutton = findViewById(R.id.backButton);
        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);



        Intent intent = getIntent();
        int termekid = (Integer) intent.getSerializableExtra("termekid");
        Intent intent1= new Intent(TermekActivity.this, ListActivity.class);
        loadTermekById(apiService, termekid);

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modositTermek(apiService, termekid, intent1);

            }
        });

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TermekActivity.this);
                builder.setMessage("Biztosan törölni akarja ezt a terméket?");
                builder.setTitle("Törlés megerősítése");
                builder.setNegativeButton("Mégse", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTermek(apiService, termekid);
                        startActivity(intent1);
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent1);
                finish();
            }
        });
    };

    public void loadTermekById(RetrofitApiService apiService, int termekId) {
        apiService.getTermekekById(termekId).enqueue(new Callback<Termekek>() {
            @Override
            public void onResponse(Call<Termekek> call, Response<Termekek> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Termekek termek = response.body();
                    editTextNameEdit.setText(termek.getName());
                    editTextCountEdit.setText(String.valueOf(termek.getCount()));
                    editTextPriceEdit.setText(String.valueOf(termek.getPrice()));
                    editTextMeasureEdit.setText(termek.getMeasure());
                } else {
                    Toast.makeText(getApplicationContext(), "Nem sikerült lekérni a terméket!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Termekek> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Hiba történt a termék lekérésekor!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void deleteTermek(RetrofitApiService apiService, int termekId){
        apiService.deleteTermek(termekId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TermekActivity.this, "Sikeres törlés!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(TermekActivity.this, "Nem sikerült törölni a terméket!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TermekActivity.this, "Hiba történt a törlés közben!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void modositTermek(RetrofitApiService apiService, int termekId, Intent intent1){
        String name = editTextNameEdit.getText().toString().trim();
        String price = editTextPriceEdit.getText().toString().trim();
        String count = editTextCountEdit.getText().toString().trim();
        String measure = editTextMeasureEdit.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "A név mező nem lehet üres!", Toast.LENGTH_SHORT).show();
            return;
        }


        if (price.isEmpty()) {
            Toast.makeText(this, "Az egységár mező nem lehet üres!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!price.matches("\\d+")) {
            Toast.makeText(this, "Az egységárnak pozitív egész számnak kell lennie!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (count.isEmpty()) {
            Toast.makeText(this, "A mennyiség mező nem lehet üres!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!count.matches("\\d+(\\.\\d+)?")) {
            Toast.makeText(this, "A mennyiségnek pozitív számnak kell lennie!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (measure.isEmpty()) {
            Toast.makeText(this, "A mértékegység mező nem lehet üres!", Toast.LENGTH_SHORT).show();
            return;
        }


        Termekek updatedTermek = new Termekek(
                name,
                Integer.parseInt(price),
                Float.parseFloat(count),
                measure
        );


        apiService.updateTermekek(termekId, updatedTermek).enqueue(new Callback<Termekek>() {
            @Override
            public void onResponse(Call<Termekek> call,Response<Termekek> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TermekActivity.this, "Sikeres modosítás!", Toast.LENGTH_SHORT).show();
                    startActivity(intent1);
                    finish();
                } else {
                    Toast.makeText(TermekActivity.this, "Nem sikerült modosítani a terméket!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Termekek> call, Throwable t) {
                Toast.makeText(TermekActivity.this, "Hiba történt a termék hozzáadása közben!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}