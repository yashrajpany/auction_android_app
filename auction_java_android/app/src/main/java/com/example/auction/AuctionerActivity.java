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

public class AuctionerActivity extends AppCompatActivity {

    TextInputEditText textInputEditTextProductName , textInputEditTextDescription , textInputEditTextPrice;
    Button buttonSave, buttonCancel;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auctioner);

        textInputEditTextProductName = findViewById(R.id.productName);
        textInputEditTextDescription = findViewById(R.id.description);
        textInputEditTextPrice = findViewById(R.id.price);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);
        progressBar = findViewById(R.id.progress);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product, description, price, isHighBid, bidName;
                product = String.valueOf(textInputEditTextProductName.getText());
                description = String.valueOf(textInputEditTextDescription.getText());
                price = String.valueOf(textInputEditTextPrice.getText());
                isHighBid = "1";
                bidName = "Auctioneer";

                if (!product.equals("") && !description.equals("") && !price.equals("")) {

                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "product";
                            field[1] = "description";
                            field[2] = "price";
                            field[3] = "isHighBid";
                            field[4] = "bidName";

                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = product;
                            data[1] = description;
                            data[2] = price;
                            data[3] = isHighBid;
                            data[4] = bidName;

                            PutData putData = new PutData("http://192.168.29.87/auction/auctioner.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.trim().equals("Added Successfully")) {
                                        Toast.makeText(getApplicationContext(), "Added Successfully" , Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent (getApplicationContext(), HomeActivity.class);
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
                }
                else{
                    Toast.makeText(getApplicationContext(), "All fields are required" , Toast.LENGTH_SHORT).show();
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