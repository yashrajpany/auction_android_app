package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ProductInfoActivity extends AppCompatActivity {

    TextInputEditText textInputEditTextPrice, textInputEditTextBidName;
    Button buttonUpdate, buttonCancel;
    TextView textViewProductName, textInputLayoutDescription;
    ProgressBar progressBar;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        String productName = getIntent().getStringExtra("productName");
        String productDescription = getIntent().getStringExtra("productDescription");
        double productPrice = getIntent().getDoubleExtra("productPrice", 0);
        id = getIntent().getIntExtra("id", 0);

        textInputEditTextPrice = findViewById(R.id.price);
        textInputEditTextBidName = findViewById(R.id.bidName);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonCancel = findViewById(R.id.buttonCancel);
        textInputLayoutDescription = findViewById(R.id.textInputLayoutDescription);
        textViewProductName = findViewById(R.id.textViewProductName);
        progressBar = findViewById(R.id.progress);


        textInputEditTextPrice.setText(String.valueOf(productPrice));
        textInputLayoutDescription.setText(productDescription);
        textViewProductName.setText(productName);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product, description, price, isHighBid, bidName;

                product = String.valueOf(textViewProductName.getText());
                description = String.valueOf(textInputLayoutDescription.getText());
                price = String.valueOf(textInputEditTextPrice.getText());
                isHighBid = "2";
                bidName = String.valueOf(textInputEditTextBidName.getText());

                if (!product.equals("") && !description.equals("") && !price.equals("")) {

                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];

                            field[0] = "product";
                            field[1] = "description";
                            field[2] = "price";
                            field[3] = "isHighBid";
                            field[4] = "bidName";
                            field[5] = "id";

                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = product;
                            data[1] = description;
                            data[2] = price;
                            data[3] = isHighBid;
                            data[4] = bidName;
                            data[5] = String.valueOf(id);

                            PutData putData = new PutData("http://192.168.29.87/auction/product.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.trim().equals("Bid Successful")) {
                                        Toast.makeText(getApplicationContext(), "Bid Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}