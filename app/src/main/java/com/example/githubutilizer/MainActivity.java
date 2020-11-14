package com.example.githubutilizer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.util.Locale.filter;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    CustomAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    public ArrayList<RepoDataModel> repoData;
    String language,keyword,resultOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getMenuInflater().inflate(R.menu.options_menu,);

        // Setting up floor for RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        repoData = new ArrayList<>();

        // Getting query parameters passed by the Search Activtiy
        Intent intent = getIntent();
        language = intent.getStringExtra("language");
        keyword = intent.getStringExtra("keyword");
        resultOrder = intent.getStringExtra("resultOrder");

        // Loading user into the repoData List
        loadUsers();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar,menu);
        final MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    public void loadUsers(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.github.com/search/repositories?q="+keyword+"language:"+language+"&sort=stars&order="+resultOrder;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray items = response.getJSONArray("items");
                            for(int i = 0; i<items.length(); i++){
                                JSONObject item = items.getJSONObject(i);
                                String username = item.getJSONObject("owner").get("login").toString();
                                String desc = item.getString("description");
                                String profilePic = item.getJSONObject("owner").get("avatar_url").toString();
                                String repoLink = item.getJSONObject("owner").get("html_url").toString();
                                System.out.println(username+"\t"+desc+"\t"+profilePic);
                                repoData.add(new RepoDataModel(profilePic,username,desc,repoLink));
                                System.out.println("Repo size:"+repoData.size());

                            }

                            adapter = new CustomAdapter(getApplicationContext(),repoData);
                            recyclerView.setAdapter(adapter);

                        }
                        catch(Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        adapter.filter(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.getFilter().filter(s);
        return true;
    }
}
