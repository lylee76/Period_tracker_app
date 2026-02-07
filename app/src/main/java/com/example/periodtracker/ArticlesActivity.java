package com.example.periodtracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ArticlesActivity extends AppCompatActivity {

    String[] titles = {
            "Understanding Menstrual Cycle",
            "Irregular Periods Causes",
            "Menstrual Hygiene Tips",
            "When to See a Doctor"
    };

    String[] content = {
            "The menstrual cycle is a monthly process...",
            "Irregular periods can be caused by stress...",
            "Maintaining hygiene during periods is important...",
            "Consult a doctor if periods are very irregular..."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        ListView listView = findViewById(R.id.listArticles);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, ArticleDetailActivity.class);
            i.putExtra("title", titles[position]);
            i.putExtra("content", content[position]);
            startActivity(i);
        });
    }
}
