package com.example.query;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.query.database.AppDatabase;
import com.example.query.database.Searches;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static List<Searches> dataset;
    private static Context ct;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.frameText);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Searches s = dataset.get(getAdapterPosition());
            ConnectDB db = new ConnectDB();
            db.setAsyncItem(s);
            db.execute();
            ResultsActivity.queryText = s.searchItem;
            Intent intent = new Intent(ct,ResultsActivity.class);
            ct.startActivity(intent);
        }
    }

    public MyAdapter(Context ct, List<Searches> data) {
        dataset = data;
        MyAdapter.ct = ct;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct)
                .inflate(R.layout.recycler_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Searches s = dataset.get(position);
        holder.textView.setText(s.searchItem);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    static class ConnectDB extends AsyncTask<Void, Void, Void>{
        Searches s;
        public void setAsyncItem(Searches item){
            s = item;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase appDB = AppDatabase.getInstance(ct);
            appDB.searchDao().delete(s);
            Searches updated_s = new Searches();
            updated_s.setItem(s.searchItem);
            appDB.searchDao().insert(updated_s);
            return null;
        }
    }
}
