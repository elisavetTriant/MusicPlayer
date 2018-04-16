package co.elisavet.musicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class NowPlayingActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView albumTextView;
    private TextView artistTextView;
    private TextView durationTextView;
    private TextView backToPreviousActivityTextView;
    private ImageView albumArtImageView;
    private Button nextSongButton;
    private Button previousSongButton;
    private Button playPauseButton;
    private int currentlyPlayingAudioListPosition;
    private Audio currentlyPlayingAudio;
    private boolean nowPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        // Find the TextView in the activity_now_playing.xml layout with the ID back_to_previous_activity
        backToPreviousActivityTextView = (TextView) findViewById(R.id.back_to_previous_activity);
        // Find the TextView in the activity_now_playing.xml layout with the ID title
        titleTextView = (TextView) findViewById(R.id.title);
        // Find the TextView in the activity_now_playing.xml layout with the ID album
        albumTextView = (TextView) findViewById(R.id.album);
        // Find the TextView in the activity_now_playing.xml layout with the ID artist
        artistTextView = (TextView) findViewById(R.id.artist);
        // Find the TextView in the activity_now_playing.xml layout with the ID duration
        durationTextView = (TextView) findViewById(R.id.duration);
        // Find the ImageView in the activity_now_playing.xml layout with the ID album_art
        albumArtImageView = (ImageView) findViewById(R.id.album_art);
        // Find the Button in the activity_now_playing.xml layout with the ID next_song_button
        nextSongButton = (Button) findViewById(R.id.next_song_button);
        // Find the Button in the activity_now_playing.xml layout with the ID previous_song_button
        previousSongButton = (Button) findViewById(R.id.previous_song_button);
        // Find the Button in the activity_now_playing.xml layout with the ID play_pause_button
        playPauseButton = (Button) findViewById(R.id.play_pause_button);

        //We are coming from the Library Activity with an intent
        if(getIntent().getExtras() != null) {
            currentlyPlayingAudioListPosition = getIntent().getExtras().getInt("POSITION");
            AudioData.getInstance().setCurrentPosition(currentlyPlayingAudioListPosition);
            currentlyPlayingAudio = getIntent().getExtras().getParcelable("SELECTED_AUDIO");
            AudioData.getInstance().setCurrentAudio(currentlyPlayingAudio);
            updateUI();
            playMusic();
        }

        // Set a click listener on that View
        nextSongButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the next song button is clicked on.
            @Override
            public void onClick(View view) {
                if (currentlyPlayingAudioListPosition != AudioData.getInstance().getLibraryAudioListData().size()-1){
                    currentlyPlayingAudioListPosition++;
                } else {
                    currentlyPlayingAudioListPosition = 0;
                }
                Audio nextAudio = AudioData.getInstance().getLibraryAudioListData().get(currentlyPlayingAudioListPosition);
                AudioData.getInstance().setCurrentAudio(nextAudio);
                AudioData.getInstance().setCurrentPosition(currentlyPlayingAudioListPosition);
                updateUI();
                playMusic();
            }
        });

        // Set a click listener on that View
        previousSongButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the next song button is clicked on.
            @Override
            public void onClick(View view) {
                if (currentlyPlayingAudioListPosition != 0){
                    currentlyPlayingAudioListPosition--;
                } else {
                    currentlyPlayingAudioListPosition = AudioData.getInstance().getLibraryAudioListData().size()-1;
                }
                Audio previousAudio = AudioData.getInstance().getLibraryAudioListData().get(currentlyPlayingAudioListPosition);
                AudioData.getInstance().setCurrentAudio(previousAudio);
                AudioData.getInstance().setCurrentPosition(currentlyPlayingAudioListPosition);
                updateUI();
                playMusic();
            }
        });

        // Set a click listener on that View
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the next song button is clicked on.
            @Override
            public void onClick(View view) {
                if (nowPlaying){
                    //Pause
                    pauseMusic();
                } else {
                    //Play
                    playMusic();
                }
            }
        });

        backToPreviousActivityTextView.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the backToPreviousActivityTextView is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent libraryIntent = new Intent(NowPlayingActivity.this, MainActivity.class);
                // Start the new activity
                startActivity(libraryIntent);
            }
        });
    }

    private void updateUI(){

        Audio audioToShow = AudioData.getInstance().getCurrentAudio();

        // Get the data from the current Audio object and
        // set this text on the dataTextView TextView
        titleTextView.setText(audioToShow.getTitle());

        // Get the album from the current Audio object and
        // set this text on the albumTextView TextView
        albumTextView.setText(audioToShow.getAlbum());

        // Get the data from the current Audio object and
        // set this text on the artistTextView TextView
        artistTextView.setText(audioToShow.getArtist());

        // Get the data from the current Audio object and
        // set this text on the durationTextView TextView
        durationTextView.setText(audioToShow.getDuration());

        // Get the data from the current Audio object and
        // set this the image
        // Check if an image is provided for this word or not
        if (audioToShow.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            albumArtImageView.setImageResource(audioToShow.getAlbumArtResourceId());
        } else {
            // Otherwise set it to default art
            albumArtImageView.setImageResource(R.drawable.default_album_art);
        }
    }

    private void playMusic() {
        nowPlaying = true;
        playPauseButton.setText(R.string.play_button_label_pause);
        Toast.makeText(getApplicationContext(), getString(R.string.now_playing_song, AudioData.getInstance().getCurrentAudio().getTitle()), Toast.LENGTH_SHORT).show();
    }

    private void pauseMusic() {
        nowPlaying = false;
        playPauseButton.setText(getString(R.string.play_button_label_play));
    }
}
