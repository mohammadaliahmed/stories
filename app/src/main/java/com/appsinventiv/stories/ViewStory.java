package com.appsinventiv.stories;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.shts.android.storiesprogressview.StoriesProgressView;

public class ViewStory extends AppCompatActivity implements StoriesProgressView.StoriesListener {

    private StoriesProgressView storiesProgressView;
    private ProgressBar mProgressBar;
    private LinearLayout mVideoViewLayout;
    private int counter = 0;
    private List<StoryModel> mStoriesList = new ArrayList<>();

    private List<View> mediaPlayerArrayList = new ArrayList<>();

    long pressTime = 0L;
    long limit = 500L;
    TextView storyByName, time;
    CircleImageView storyByPic;
    boolean deleteClicked;


    TextView views, viewCount;
    ImageView delete, eye;

    //    HashMap<String, ArrayList<StoryViewsModel>> map = new HashMap<>();

    MediaPlayer mmmmedia;


    //Minimum Video you want to buffer while Playing
    public static final int MIN_BUFFER_DURATION = 3000;
    //Max Video you want to buffer during PlayBack
    public static final int MAX_BUFFER_DURATION = 5000;
    //Min Video you want to buffer before start Playing it
    public static final int MIN_PLAYBACK_START_BUFFER = 1500;
    //Min video You want to buffer when user resumes video
    public static final int MIN_PLAYBACK_RESUME_BUFFER = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_story);


        mProgressBar = findViewById(R.id.progressBar);
        storyByPic = findViewById(R.id.storyByPic);
        storyByName = findViewById(R.id.storyByName);
        time = findViewById(R.id.time);
        mVideoViewLayout = findViewById(R.id.videoView);
        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        storiesProgressView.setStoriesCount(MainActivity.myArrayLists.size());
        prepareStoriesList();
        storiesProgressView.setStoriesListener(this);
        for (int i = 0; i < mStoriesList.size(); i++) {
            if (mStoriesList.get(i).getUrl().contains("mp4")) {
                mediaPlayerArrayList.add(getVideoView(i));
            } else if (mStoriesList.get(i).getUrl().contains("jpg")) {
                mediaPlayerArrayList.add(getImageView(i));
            }
        }

//        adapter = new StoryViewsAdapter(this, viewsList);
//        views.setText(viewsList.size() + " views");


        setStoryView(counter);

        // bind reverse view
        View reverse = findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);

        // bind skip view
        View skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
        skip.setOnTouchListener(onTouchListener);


    }


    private void setStoryView(final int counter) {
        final View view = (View) mediaPlayerArrayList.get(counter);
        mVideoViewLayout.addView(view);

        if (view instanceof VideoView) {
            final VideoView video = (VideoView) view;
//            storiesProgressView.pause();

            video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mmmmedia = mediaPlayer;
                    mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                            Log.d("mediaStatus", "onInfo: =============>>>>>>>>>>>>>>>>>>>" + i);
                            switch (i) {
                                case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START: {
                                    mProgressBar.setVisibility(View.GONE);
                                    storiesProgressView.startanim(counter);
                                    storiesProgressView.resume();
                                    return true;
                                }
                                case MediaPlayer.MEDIA_INFO_BUFFERING_START: {
                                    mProgressBar.setVisibility(View.VISIBLE);
                                    storiesProgressView.pause();
                                    return true;
                                }
                                case MediaPlayer.MEDIA_INFO_BUFFERING_END: {
                                    mProgressBar.setVisibility(View.VISIBLE);
                                    storiesProgressView.pause();
                                    return true;

                                }
                                case MediaPlayer.MEDIA_ERROR_TIMED_OUT: {
                                    mProgressBar.setVisibility(View.VISIBLE);
                                    storiesProgressView.pause();
                                    return true;
                                }

                                case MediaPlayer.MEDIA_INFO_AUDIO_NOT_PLAYING: {
                                    mProgressBar.setVisibility(View.VISIBLE);
                                    storiesProgressView.pause();
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                    video.start();
                    mProgressBar.setVisibility(View.GONE);
                    storiesProgressView.setStoryDuration(mediaPlayer.getDuration());
                    storiesProgressView.startStories(counter);
                }
            });
        } else if (view instanceof ImageView) {
            final ImageView image = (ImageView) view;
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);

            mProgressBar.setVisibility(View.GONE);
            storiesProgressView.pause();
            Glide.with(this).load(mStoriesList.get(counter).getUrl()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                    image.setImageDrawable(resource);
                    mProgressBar.setVisibility(View.GONE);
//                    storiesProgressView.startanim(counter);

                    storiesProgressView.setStoryDuration(5000);
                    storiesProgressView.startStories(counter);
                    storiesProgressView.startanim(counter);
                    storiesProgressView.resume();


                    return false;
                }
            }).into(image);

        }

//        viewsList = map.get(mStoriesList.get(counter).getId());

    }

    private void prepareStoriesList() {
        mStoriesList = MainActivity.myArrayLists;

    }

    private VideoView getVideoView(int position) {
        VideoView video = new VideoView(this);
        video.setVideoPath(mStoriesList.get(position).getUrl());
//        video.setVideoPath(mStoriesList.get(position).getProxyUrl());
        video.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return video;
    }

    private ImageView getImageView(int position) {
        final ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return imageView;
    }

    @Override
    public void onNext() {
        storiesProgressView.destroy();
        mVideoViewLayout.removeAllViews();
        mProgressBar.setVisibility(View.VISIBLE);
        setStoryView(++counter);
    }

    @Override
    public void onPrev() {
        if ((counter - 1) < 0) return;
        storiesProgressView.destroy();
        mVideoViewLayout.removeAllViews();
        mProgressBar.setVisibility(View.VISIBLE);
        setStoryView(--counter);
    }

    @Override
    public void onComplete() {
        finish();
    }

    @Override
    protected void onDestroy() {
        // Very important !
        storiesProgressView.destroy();
        super.onDestroy();
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    if (mmmmedia != null) {
                        try {
                            mmmmedia.pause();

                        } catch (Exception e) {

                        }
                    }
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    if (mmmmedia != null) {
                        try {
                            mmmmedia.start();

                        } catch (Exception e) {

                        }
                    }
                    return limit < now - pressTime;
            }
            return false;
        }
    };

}
