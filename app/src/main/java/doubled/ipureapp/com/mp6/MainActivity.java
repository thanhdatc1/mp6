package doubled.ipureapp.com.mp6;

import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    TextView txt_title, txt_time_song, txt_time_song_total;
    SeekBar seekBar;
    ImageView imgHinh;
    ImageButton btn_prev, btn_play, btn_stop, btn_next;

    ArrayList<Song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        AddSong();
        animation = AnimationUtils.loadAnimation(this, R.anim.disk_rotate);

        KhoiTaoMediaPlayer();



        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                btn_play.setImageResource(R.drawable.play);
                KhoiTaoMediaPlayer();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if(position > arraySong.size() - 1){
                    position = 0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btn_play.setImageResource(R.drawable.pause);
                SetTimeSongTotal();
                UpdateTimeSong();
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if(position < 0){
                    position = arraySong.size() - 1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btn_play.setImageResource(R.drawable.pause);
                SetTimeSongTotal();
                UpdateTimeSong();
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    //Ngừng bài hát -> pause -> Đổi thành hình play
                    mediaPlayer.pause();
                    btn_play.setImageResource(R.drawable.play);
                }else{
                    mediaPlayer.start();
                    btn_play.setImageResource(R.drawable.pause);
                }
                SetTimeSongTotal();
                UpdateTimeSong();
                imgHinh.startAnimation(animation);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void UpdateTimeSong(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                txt_time_song.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        position++;
                        if(position > arraySong.size() - 1){
                            position = 0;
                        }
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        KhoiTaoMediaPlayer();
                        mediaPlayer.start();
                        btn_play.setImageResource(R.drawable.pause);
                        SetTimeSongTotal();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void SetTimeSongTotal(){
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        txt_time_song_total.setText(dinhDangGio.format(mediaPlayer.getDuration()));
        //gián skSOng cho mediaPlayer.getDuration();
        seekBar.setMax(mediaPlayer.getDuration());
    }

    private void KhoiTaoMediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(position).getFile());
        txt_title.setText(arraySong.get(position).getTitle());
    }

    private void AddSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Điều Anh Biết", R.raw.dieu_anh_biet_chi_dan));
        arraySong.add(new Song("Đã lỡ yêu em nhiều", R.raw.daloyeuemnhieu));
        arraySong.add(new Song("Đông và anh", R.raw.dongvaanh));
        arraySong.add(new Song("Hôn Anh", R.raw.honanh));
        arraySong.add(new Song("Kém duyên", R.raw.kemduyen));
        arraySong.add(new Song("Từ ngày em đến", R.raw.tungayemden));
    }

    private void AnhXa(){
        txt_title = (TextView) findViewById(R.id.txtTitle);
        txt_time_song = (TextView) findViewById(R.id.txtTimeSong);
        txt_time_song_total = (TextView) findViewById(R.id.txtTimeSongTotal);
        seekBar = (SeekBar) findViewById(R.id.seekBarSong);
        btn_prev = (ImageButton) findViewById(R.id.btnPrev);
        btn_play = (ImageButton) findViewById(R.id.btnPlay);
        btn_stop = (ImageButton) findViewById(R.id.btnStop);
        btn_next = (ImageButton) findViewById(R.id.btnNext);
        imgHinh = (ImageView) findViewById(R.id.img_Hinh);
    }
}
