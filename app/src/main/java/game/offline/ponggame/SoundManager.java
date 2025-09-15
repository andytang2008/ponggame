package game.offline.ponggame;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundManager {
    private SoundPool soundPool;
    private int soundIdPaddleHit;
    private int soundIdBrickHit;
    private int soundIdGameOver;
    private int soundIdWallHit;
    private int soundIdCoinCollect;

    private int soundIdBunCollect;

    private int soundIdSausageCollect;
    private int soundIdBeerCollect;

    private int soundIdShortboardCollect;

    private int soundIdLongboardCollect;
    private int soundIdSizeupCollect;
    private int soundIdGrenadeCollect;
    private int soundIdBallTripleCollect;
    private int soundIdBallFireCollect;
    public SoundManager(Context context) {
        initSound(context);
    }

    private void initSound(Context context) {
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(3);
        AudioAttributes.Builder audioAttributesBuilder = new AudioAttributes.Builder();
        audioAttributesBuilder.setUsage(AudioAttributes.USAGE_GAME);
        audioAttributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
        builder.setAudioAttributes(audioAttributesBuilder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .build();
        } else {
            // Initialization for API level below 21
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }

        soundIdPaddleHit = soundPool.load(context, R.raw.paddle_hit, 1);
        soundIdBrickHit = soundPool.load(context, R.raw.brick_hit, 1);
        soundIdGameOver = soundPool.load(context, R.raw.game_over, 1);
        soundIdWallHit = soundPool.load(context, R.raw.wall_hit, 1);
        soundIdCoinCollect = soundPool.load(context, R.raw.coin_collect, 1);
        soundIdBunCollect = soundPool.load(context, R.raw.bun_collect, 1);
        soundIdSausageCollect = soundPool.load(context, R.raw.sausage_collect, 1);
        soundIdBeerCollect = soundPool.load(context, R.raw.beer_collect, 1);
        soundIdShortboardCollect = soundPool.load(context, R.raw.shortboard_collect, 1);
        soundIdLongboardCollect = soundPool.load(context, R.raw.longboard_collect, 1);
        soundIdSizeupCollect = soundPool.load(context, R.raw.sizeup_collect, 1);
        soundIdGrenadeCollect = soundPool.load(context, R.raw.grenade_collect, 1);
        soundIdBallTripleCollect = soundPool.load(context, R.raw.ball_triple, 1);
        soundIdBallFireCollect = soundPool.load(context, R.raw.ball_fire, 1);
    }

    public void playPaddleHitSound() {
        soundPool.play(soundIdPaddleHit, 1, 1, 1, 0, 1f);
    }

    public void playBrickHitSound() {
        soundPool.play(soundIdBrickHit, 1, 1, 1, 0, 1f);
    }

    public void playGameOverSound() {
        soundPool.play(soundIdGameOver, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playWallHitSound() {
        soundPool.play(soundIdWallHit, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playCoinCollectSound() {
        soundPool.play(soundIdCoinCollect, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playBunCollectSound() {
        soundPool.play(soundIdBunCollect, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playSausageCollectSound() {
        soundPool.play(soundIdSausageCollect, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playBeerCollectSound() {
        soundPool.play(soundIdBeerCollect, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playShortboardCollectSound() {
        soundPool.play(soundIdShortboardCollect, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playLongboardCollectSound() {
        soundPool.play(soundIdLongboardCollect, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playSizeupCollectSound() {
        soundPool.play(soundIdSizeupCollect, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playGrenadeCollectSound() {
        soundPool.play(soundIdGrenadeCollect, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playBallTripleCollectSound() {
        soundPool.play(soundIdBallTripleCollect, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playBallFireCollectSound() {
        soundPool.play(soundIdBallFireCollect, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}