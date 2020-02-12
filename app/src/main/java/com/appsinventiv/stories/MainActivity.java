package com.appsinventiv.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<StoryModel> myArrayLists = new ArrayList<>();
    Button viewStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewStory = findViewById(R.id.viewStory);
        viewStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ViewStory.class);
                startActivity(i);
            }
        });

        myArrayLists.add(new StoryModel(1, "https://spyguysandgals.com/images/covers/reacher_jack_mv1.jpg", System.currentTimeMillis()));
        myArrayLists.add(new StoryModel(1, "https://bestsimilar.com/img/movie/thumb/1e/50023.jpg", System.currentTimeMillis()));
        myArrayLists.add(new StoryModel(1, "https://images.squarespace-cdn.com/content/v1/564f8625e4b0f8afcd9bd71b/1561750894229-RMVR998P1QEEDKQFX79K/ke17ZwdGBToddI8pDm48kGUqfzN0ldWWtIaiukyL99tZw-zPPgdn4jUwVcJE1ZvWEtT5uBSRWt4vQZAgTJucoTqqXjS3CfNDSuuf31e0tVFe4m3WbKplAcIbuI9eWW8zVd8m3TQxYGbGKhzjhgmtIiMQLnzwpoLxUR41EqPR9Fo/81AoLFnNRdL._SL1500_.jpg", System.currentTimeMillis()));
        myArrayLists.add(new StoryModel(1, "https://image.tmdb.org/t/p/w300/c0Uje4tPPipTK58x2hNkJobnSHL.jpg", System.currentTimeMillis()));
        myArrayLists.add(new StoryModel(1, "https://m.media-amazon.com/images/M/MV5BNmVkN2Q1N2MtY2I5OC00MjA1LTk1YWEtMDMxMGE0YzFlNGRjXkEyXkFqcGdeQXVyNjA4NTk0NjU@._V1_SY500_SX300_AL_.jpg", System.currentTimeMillis()));
        myArrayLists.add(new StoryModel(1, "https://m.media-amazon.com/images/M/MV5BMTMzMmNkYmUtOTNiNC00NWE5LWE1YjMtM2E3NDdkMTUzYTczXkEyXkFqcGdeQXVyMTc2NDQ1MjY@._V1_.jpg", System.currentTimeMillis()));
        myArrayLists.add(new StoryModel(1, "https://s3.ap-south-1.amazonaws.com/tvwish-live/MoviePosters/Thumbs/166011.jpg", System.currentTimeMillis()));
        myArrayLists.add(new StoryModel(1, "https://1.bp.blogspot.com/-G1wlXND0wfA/WInnZ_6JOKI/AAAAAAAAIoA/SYiDG9zG0m0fIz4PNl3Hg9oY0zmsC4HjQCLcB/s1600/olanlar.jpg", System.currentTimeMillis()));
        myArrayLists.add(new StoryModel(1, "https://images-eu.ssl-images-amazon.com/images/I/51DnV6wgDtL.jpg", System.currentTimeMillis()));


    }
}
