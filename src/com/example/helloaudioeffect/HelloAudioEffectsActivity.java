package com.example.helloaudioeffect;

import com.example.helloequalizer.R;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Virtualizer;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class HelloAudioEffectsActivity extends Activity {

    private MediaPlayer mMediaPlayer = null;

    private TextView mBassBoostTextView = null;
    private SeekBar mBassBoostSeekBar = null;

    private TextView mVirtualizerTextView = null;
    private SeekBar mVirtualizerSeekBar = null;

    private BassBoost mBassBoost = null;

    private Virtualizer mVirtualizer = null;

    private AudioManager mAudioManager = null;
    private int mMaxVolume = 0;
    private int mCurrentVolume = 0;
    private TextView mCurrentVolumeTextView = null;
    private TextView mSetVolumeTextView = null;

    private SeekBar mVolumeBar = null;

    private SeekBar mLeftSeekBar = null;
    private SeekBar mRightSeekBar = null;
    private TextView mLeftTextView = null;
    private TextView mRightTextView = null;
    private int mLeftVolume = 0;
    private int mRightVolume = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(AppConstants.LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_audioeffect);

        setUpViews();

        playSong();

        mBassBoost = new BassBoost(0, mMediaPlayer.getAudioSessionId());
        mBassBoost.setEnabled(true);

        mVirtualizer = new Virtualizer(0, mMediaPlayer.getAudioSessionId());
        mVirtualizer.setEnabled(true);

        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mMaxVolume = mAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Log.d(AppConstants.LOG_TAG, "Max Volume: " + mMaxVolume);

        mVolumeBar.setMax(mMaxVolume);

        mLeftSeekBar.setMax(mMaxVolume);
        mRightSeekBar.setMax(mMaxVolume);

        updateCurrentVolume();

    }

    private void updateCurrentVolume() {
        mCurrentVolume = mAudioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        mCurrentVolumeTextView.setText("Current Volume: " + mCurrentVolume);
        mVolumeBar.setProgress(mCurrentVolume);

    }

    private void setLeftAndRight() {
        float leftRatio = mLeftVolume / (float) mMaxVolume;
        float rightRatio = mRightVolume / (float) mMaxVolume;
        mMediaPlayer.setVolume(leftRatio, rightRatio);
        mLeftTextView.setText("" + mLeftVolume + ", ratio:" + leftRatio);
        mRightTextView.setText("" + mRightVolume + ", ratio:" + rightRatio);

        updateCurrentVolume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (isFinishing() && null != mMediaPlayer) {

            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void playSong() {
        Log.d(AppConstants.LOG_TAG, "playSong");
        try {
            mMediaPlayer = MediaPlayer.create(this, R.raw.fang);
            mMediaPlayer.start();

        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(AppConstants.LOG_TAG, "Error when initialize MediaPlayer");
        }

    }

    private void setUpViews() {
        mBassBoostTextView = (TextView) findViewById(R.id.bassBoostTextView1);
        mBassBoostSeekBar = (SeekBar) findViewById(R.id.bassBoostSeekBar);
        mBassBoostSeekBar
                .setOnSeekBarChangeListener(mBassSeekBarChangeListener);

        mVirtualizerTextView = (TextView) findViewById(R.id.virtualizerTextView1);
        mVirtualizerSeekBar = (SeekBar) findViewById(R.id.virtualizerSeekBar);
        mVirtualizerSeekBar
                .setOnSeekBarChangeListener(mVirtualizerSeekBarChangeListener);

        mCurrentVolumeTextView = (TextView) findViewById(R.id.currentVolume);
        mVolumeBar = (SeekBar) findViewById(R.id.volumeSeekBar);
        mVolumeBar.setOnSeekBarChangeListener(mVolumeBarChangeListener);

        mSetVolumeTextView = (TextView) findViewById(R.id.currentVolumeSet);

        mLeftTextView = (TextView) findViewById(R.id.leftTextView);
        mRightTextView = (TextView) findViewById(R.id.rightTextView);
        mLeftSeekBar = (SeekBar) findViewById(R.id.leftSeekBar);
        mRightSeekBar = (SeekBar) findViewById(R.id.rightSeekBar);

        mLeftSeekBar.setOnSeekBarChangeListener(mLeftSeekBarChangeListener);
        mRightSeekBar.setOnSeekBarChangeListener(mRightSeekBarChangeListener);

    }

    private OnSeekBarChangeListener mBassSeekBarChangeListener = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                boolean fromUser) {

            if (null != mBassBoost) {
                mBassBoost.setStrength((short) (progress * 10));
            }
            short strength = mBassBoost.getRoundedStrength();
            boolean isSupported = mBassBoost.getStrengthSupported();
            mBassBoostTextView.setText(Integer.toString(progress) + ", "
                    + strength + ", " + isSupported);

        }
    };

    private OnSeekBarChangeListener mVirtualizerSeekBarChangeListener = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                boolean fromUser) {

            short strength = 0;
            boolean isSupported = false;

            if (null != mVirtualizer) {
                mVirtualizer.setStrength((short) (progress * 10));
                strength = mVirtualizer.getRoundedStrength();
                isSupported = mVirtualizer.getStrengthSupported();

            }
            mVirtualizerTextView.setText(Integer.toString(progress) + ", "
                    + strength + ", " + isSupported);

        }
    };

    private OnSeekBarChangeListener mVolumeBarChangeListener = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                boolean fromUser) {
            if (fromUser) {
                mSetVolumeTextView.setText(Integer.toString(progress));
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);
                updateCurrentVolume();

            }

        }
    };

    private OnSeekBarChangeListener mLeftSeekBarChangeListener = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                boolean fromUser) {
            mLeftVolume = progress;
            setLeftAndRight();

        }
    };

    private OnSeekBarChangeListener mRightSeekBarChangeListener = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                boolean fromUser) {
            mRightVolume = progress;
            setLeftAndRight();

        }
    };
}
