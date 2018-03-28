package co.elisavet.musicplayer;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Audio> libraryAudioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.library_song_list);

        libraryAudioList = loadAudio();

        AudioAdapter audioAdapter = new AudioAdapter(this, libraryAudioList);

        listView.setAdapter(audioAdapter);
        //https://stackoverflow.com/questions/3771568/showing-empty-view-when-listview-is-empty/28188185#28188185
        listView.setEmptyView(findViewById(R.id.emptyElement));

        // register onClickListener to handle click events on each item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
            {
                Audio selectedAudio = libraryAudioList.get(position);
                playMedia(selectedAudio.getData());
            }
        });


    }

    //https://www.sitepoint.com/a-step-by-step-guide-to-building-an-android-audio-player-app/
    private ArrayList<Audio> loadAudio() {
        ContentResolver contentResolver = getContentResolver();
        ArrayList<Audio> audioList = new ArrayList<Audio>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                //https://developer.android.com/reference/android/provider/MediaStore.Audio.AudioColumns.html
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

                // Save to audioList
                audioList.add(new Audio(data, title, album, artist, duration));
            }
            cursor.close();
        }

        return audioList;
    }

    //https://developer.android.com/guide/components/intents-common.html#Music
    public void playMedia(String file) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        File fileToPlay = new File(file);
        intent.setDataAndType(Uri.fromFile(fileToPlay), "audio/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "It looks you don't have a music player installed!", Toast.LENGTH_LONG).show();
        }
    }


}
