package com.example.celebrityapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ParseAdapter adapter;
    private ArrayList<ParseItem> parseItems = new ArrayList<>();
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ParseAdapter(parseItems,this);
        recyclerView.setAdapter(adapter);
        Content content = new Content();
        content.execute();
    }

    private class Content extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_out));
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            String url;
            url = "https://www.cinemaqatar.com/";
            try {
                Document document = Jsoup.connect(url).get();
                Elements data = document.select("span.thumbnail");
                int size = data.size();
                int j=0;
                for(int i=0;i<size;i++)
                {
                    String imgUrl;
                    do {
                         imgUrl = data.select("span.thumbnail")
                                .select("img")
                                .eq(j)
                                .attr("src");
                         j++;
                    }while (imgUrl.trim().length()<=0);
                    String title = data.select("h4.gridminfotitle")
                            .select("span")
                            .eq(i)
                            .text();
                    String detailedUrl = data.select("h4.gridminfotitle")
                            .select("a")
                            .eq(i)
                            .attr("href");
                    if((imgUrl.trim().length()>0) && (title.trim().length()>0) && (detailedUrl.trim().length()>0))
                    {
                        parseItems.add(new ParseItem(imgUrl,title,detailedUrl));
                        Log.d("items","img"+imgUrl+". title"+title+"detailedUrl"+detailedUrl);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}