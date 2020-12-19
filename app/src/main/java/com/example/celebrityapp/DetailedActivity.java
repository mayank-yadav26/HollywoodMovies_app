package com.example.celebrityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetailedActivity extends AppCompatActivity {
    TextView tvTitle,tvDetails;
    ImageView imageView;
    String detailString;
    ProgressBar d_progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        tvTitle=findViewById(R.id.d_textView);
        tvDetails = findViewById(R.id.tvDetails);
        imageView = findViewById(R.id.d_imageView);
        d_progressBar = findViewById(R.id.d_progressBar);

        tvTitle.setText((getIntent().getStringExtra("title")));
        Picasso.get().load(getIntent().getStringExtra("image")).into(imageView);

        Content content = new Content();
        content.execute();
    }
    private class Content extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            d_progressBar.setVisibility(View.VISIBLE);
            d_progressBar.startAnimation(AnimationUtils.loadAnimation(DetailedActivity.this,android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            d_progressBar.setVisibility(View.GONE);
            d_progressBar.startAnimation(AnimationUtils.loadAnimation(DetailedActivity.this,android.R.anim.fade_out));
            tvDetails.setText(detailString);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String baseUrl = "https://www.cinemaqatar.com/";
            String detailedUrl = getIntent().getStringExtra("detailedUrl");
            String url = baseUrl+detailedUrl;
            try {
                Document document = Jsoup.connect(url).get();
                Elements data = document.select("div.detailinfo");
                detailString = data.select("div.detailinfo")
                        .text();
                detailString = detailString.replace("Watch Trailer",".");
                Log.d("details",detailString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}