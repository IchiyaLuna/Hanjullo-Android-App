package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    private ArrayList<rankingData> arrayList;
    private rankingAdapter rankingAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        init();

        getData();
    }

    private void init(){


        recyclerView = (RecyclerView) findViewById(R.id.rankingScroll);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        arrayList = new ArrayList<>();

        rankingAdapter = new rankingAdapter(arrayList);
        recyclerView.setAdapter(rankingAdapter);
    }
   private void getData() {
       List<String> list_ranking = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8",
               "9", "10", "11", "12", "13", "14", "15", "16");
       List<String> list_ranking_name = Arrays.asList("박예림", "이송화", "나탈리", "나탈리","나탈리","나탈리","나탈리","나탈리","나탈리","나탈리",
               "나탈리","나탈리","나탈리","나탈리","나탈리","나탈리");
       List<String> list_ranking_point = Arrays.asList("1300pt", "800pt", "750pt", "700pt", "650pt","350pt","70pt","70pt","70pt",
               "70pt","70pt","70pt","70pt","70pt","70pt","70pt");
       for (int i = 0; i < list_ranking.size(); i++) {
           rankingData data = new rankingData();
           data.setRanking(list_ranking.get(i));
           data.setRanking_name(list_ranking_name.get(i));
           data.setRanking_point(list_ranking_point.get(i));

           rankingAdapter.addItem(data);
       }

       rankingAdapter.notifyDataSetChanged();



   }


}