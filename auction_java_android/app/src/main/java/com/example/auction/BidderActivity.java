package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BidderActivity extends AppCompatActivity {


    private static final String URL_PRODUCTS = "http://192.168.29.87/auction/bidder.php";

    List<Product> productList;


    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidder);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();
        loadProducts();
    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new Product(
                                        product.getInt("id"),
                                        product.getString("product"),
                                        product.getString("description"),
                                        product.getDouble("price"),
                                        product.getString("bidName")

                                ));
                            }


                            ProductAdapter adapter = new ProductAdapter(BidderActivity.this, productList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Product product) {

                                    Intent intent = new Intent(BidderActivity.this, ProductInfoActivity.class);

                                    intent.putExtra("productName", product.getTitle());
                                    intent.putExtra("productPrice", product.getPrice());
                                    intent.putExtra("productDescription", product.getDescription());
                                    intent.putExtra("id", product.getId());
                                    startActivity(intent);

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BidderActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
        );

        Volley.newRequestQueue(this).add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}