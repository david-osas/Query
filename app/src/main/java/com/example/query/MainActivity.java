package com.example.query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.query.database.AppDatabase;
import com.example.query.database.Searches;

public class MainActivity extends AppCompatActivity {
    EditText query;

    public void startSearch(View view){
        ResultsActivity.queryText = query.getText().toString();
        ConnectDB db = new ConnectDB();
        db.execute();

        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
        query.getText().clear();

    }
    class ConnectDB extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase appDB = AppDatabase.getInstance(getApplicationContext());
            Searches s;
            String qs = query.getText().toString();
            if(qs.length() > 0){
                if (!appDB.searchDao().isSearchPresent(qs)) {
                    s = new Searches();
                    s.setItem(qs);
                    appDB.searchDao().insert(s);

                    int size = appDB.searchDao().getSize();
                    if (size > 10) {
                        Searches last = appDB.searchDao().getLast();
                        appDB.searchDao().delete(last);
                    }
                }else{
                    s = appDB.searchDao().findSearch(qs);
                    appDB.searchDao().delete(s);
                    Searches updated_s = new Searches();
                    updated_s.setItem(s.searchItem);
                    appDB.searchDao().insert(updated_s);

                }
            }


            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        query = findViewById(R.id.queryText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.historyItem){
            Intent intent = new Intent(this,HistoryActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}