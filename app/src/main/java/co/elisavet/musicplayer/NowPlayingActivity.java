package co.elisavet.musicplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class NowPlayingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        Audio currentAudio = getIntent().getExtras().getParcelable("SELECTED_AUDIO");
        int current_position = getIntent().getExtras().getInt("POSITION");

        // Find the TextView in the list_item.xml layout with the ID title
        TextView titleTextView = (TextView) findViewById(R.id.title);
        // Get the data from the current Audio object and
        // set this text on the dataTextView TextView
        titleTextView.setText(currentAudio.getTitle());

        // Find the TextView in the list_item.xml layout with the ID album
        TextView albumTextView = (TextView) findViewById(R.id.album);
        // Get the album from the current Audio object and
        // set this text on the albumTextView TextView
        albumTextView.setText(currentAudio.getAlbum());

        // Find the TextView in the list_item.xml layout with the ID artist
        TextView artistTextView = (TextView) findViewById(R.id.artist);
        // Get the data from the current Audio object and
        // set this text on the artistTextView TextView
        artistTextView.setText(currentAudio.getArtist());

        // Find the TextView in the list_item.xml layout with the ID duration
        TextView durationTextView = (TextView) findViewById(R.id.duration);
        // Get the data from the current Audio object and
        // set this text on the durationTextView TextView
        durationTextView.setText(currentAudio.getDuration());

        // Find the ImageView in the list_item.xml layout with the ID album_art
        ImageView albumArtImageView = (ImageView) findViewById(R.id.album_art);
        // Get the data from the current Audio object and
        // set this the image
        // Check if an image is provided for this word or not
        if (currentAudio.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            albumArtImageView.setImageResource(currentAudio.getAlbumArtResourceId());
        } else {
            // Otherwise set it to default art
            albumArtImageView.setImageResource(R.drawable.default_album_art);
        }
    }
}
