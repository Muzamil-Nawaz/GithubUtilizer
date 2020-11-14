package com.example.githubutilizer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SearchActivity extends AppCompatActivity {
    TextInputLayout layout,layout2;
    String language,keyword,resultOrder="desc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        layout = findViewById(R.id.language_layout);
        layout2 = findViewById(R.id.keyword_layout);



    }
    public void fetchResults(View v){
        // Checking if input are not empty or invalid
        if(checkValidInput()) {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            intent.putExtra("language", language);
            intent.putExtra("keyword", keyword);
            intent.putExtra("resultOrder",resultOrder);
            startActivity(intent);
        }
    }

    private boolean checkValidInput() {
        language = layout.getEditText().getText().toString();
        keyword = layout2.getEditText().getText().toString();
        if(language.equals("")) {
            layout.getEditText().setError("Field can't be empty.");
            return false;
        }
        else if(keyword.equals("")){
            layout2.getEditText().setError("Field can't be empty.");
            return false;
        }
        else
            return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.ascendingResult:
                resultOrder = "asc";
                return true;
            case R.id.descendingResult:
                resultOrder = "desc";
                return true;
            case R.id.quit:
                friendlyExit();
                return true;
        }
        return true;
    }

    private void showAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Don't Panic !!");
        dialog.setMessage("Just a regular alert here :)");
        dialog.setPositiveButton("Ok Got it.",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){

            }
        });
        dialog.show();
    }
    public void friendlyExit(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Want to Quit ??");
        dialog.setMessage("Please confirm if you want to quit the application.");
        dialog.setPositiveButton("Yes, I do.",new DialogInterface.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(DialogInterface dialog, int which){
                finishAndRemoveTask();
            }
        });
        dialog.setNegativeButton("No I don't.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();

    }
}

