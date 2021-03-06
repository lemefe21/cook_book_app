package com.leme.cookbook.fragment;


import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.leme.cookbook.R;
import com.leme.cookbook.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment {

    public static final String LIST_INDEX = "step_list_index";
    public static final String AUTOPLAY = "autoplay";
    public static final String CURRENT_WINDOW_INDEX = "current_window_index";
    public static final String PLAYBACK_POSITION = "playback_position";

    private List<Step> steps;
    private int stepSequence = 0;
    private boolean autoPlay = true;
    private int currentWindow;
    private long playbackPosition;

    SimpleExoPlayer mExoPlayer;

    @BindView(R.id.step_player_view)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.step_position_sequence)
    TextView mStepPositionSequence;

    @BindView(R.id.step_short_description)
    TextView mStepShortDescription;

    @BindView(R.id.step_description)
    TextView mStepDescription;

    @BindView(R.id.step_video_url)
    TextView mStepVideoUrl;

    @BindView(R.id.step_btn_previous)
    Button mStepBtnPrevious;

    @BindView(R.id.step_btn_next)
    Button mStepBtnNext;

    public StepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            Log.i("teste", "Step savedInstanceState");
            int instanceStateInt = savedInstanceState.getInt(LIST_INDEX);
            stepSequence = instanceStateInt;

            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, 0);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW_INDEX, 0);
            autoPlay = savedInstanceState.getBoolean(AUTOPLAY, true);
        }

        View view = inflater.inflate(R.layout.fragment_steps, container, false);

        ButterKnife.bind(this, view);

        Bundle params = getArguments();
        if(params != null) {
            steps = params.getParcelableArrayList(getString(R.string.step_list_key));
        }

        mStepBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releasePlayer();
                stepSequence--;
                configStepFlow();
            }
        });

        mStepBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releasePlayer();
                stepSequence++;
                configStepFlow();
            }
        });

        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.ic_play_circle));

        return view;
    }

    public void setStepsData(List<Step> steps) {
        this.steps = steps;
    }

    private void configStepFlow() {
        String stepSequenceLabel = String.format(getResources().getString(R.string.baking_step_label), stepSequence);
        mStepPositionSequence.setText(stepSequenceLabel);

        Step step = steps.get(stepSequence);

        mStepShortDescription.setText(step.getShortDescription());
        mStepDescription.setText(step.getDescription());
        mStepVideoUrl.setText(step.getVideoURL());

        if(stepSequence == 0) {
            mStepBtnNext.setVisibility(View.VISIBLE);
            mStepBtnPrevious.setVisibility(View.INVISIBLE);
        } else if(stepSequence == (steps.size() - 1)) {
            mStepBtnNext.setVisibility(View.INVISIBLE);
            mStepBtnPrevious.setVisibility(View.VISIBLE);
        } else {
            mStepBtnNext.setVisibility(View.VISIBLE);
            mStepBtnPrevious.setVisibility(View.VISIBLE);
        }

        if(!step.getVideoURL().equalsIgnoreCase("")) {
            mPlayerView.setVisibility(View.VISIBLE);
            mStepVideoUrl.setVisibility(View.GONE);
            initializePlayer(Uri.parse(step.getVideoURL()));
        } else {
            mPlayerView.setVisibility(View.GONE);
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if(mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.setPlayWhenReady(autoPlay);
            mExoPlayer.seekTo(currentWindow, playbackPosition);
            mExoPlayer.prepare(mediaSource);
        }
    }

    private void releasePlayer() {
        if(mExoPlayer != null) {
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            autoPlay = mExoPlayer.getPlayWhenReady();

            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        configStepFlow();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putInt(LIST_INDEX, stepSequence);

        if (mExoPlayer == null) {
            currentState.putLong(PLAYBACK_POSITION, playbackPosition);
            currentState.putInt(CURRENT_WINDOW_INDEX, currentWindow);
            currentState.putBoolean(AUTOPLAY, autoPlay);
        }
    }
}
