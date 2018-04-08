package co.elisavet.musicplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;

//https://github.com/googlesamples/android-RuntimePermissionsBasic/
public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private ArrayList<Audio> libraryAudioList;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 0;
    private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.main_layout);

        showMusicContents();
    }

    private void showMusicContents() {
        // BEGIN_INCLUDE(displayMusic)
        // Check if the Read External Storage permission has been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, display music
            displayMusic();
        } else {
            // Permission is missing and must be requested.
            requestReadExternalStoragePermission();
        }
        // END_INCLUDE(displayMusic)
    }

    private void displayMusic() {
        ListView listView = (ListView) findViewById(R.id.library_song_list);

        //Load Audio and store it in the libraryAudioList ArrayList<Audio>
        libraryAudioList = loadAudio();
        //Create new custom AudioAdapter instance and store it in the audioAdapter
        AudioAdapter audioAdapter = new AudioAdapter(this, libraryAudioList);
        //Then set the adapter in the listView with the id library_song_list
        listView.setAdapter(audioAdapter);
        //https://stackoverflow.com/questions/3771568/showing-empty-view-when-listview-is-empty/28188185#28188185
        listView.setEmptyView(findViewById(R.id.empty_list_element));

        // register onClickListener to handle click events on each item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                Audio selectedAudio = libraryAudioList.get(position);
                playMedia(selectedAudio.getData());
            }
        });
    }

    //https://www.sitepoint.com/a-step-by-step-guide-to-building-an-android-audio-player-app/
    private ArrayList<Audio> loadAudio() {
        ContentResolver contentResolver = getContentResolver();
        ArrayList<Audio> audioList = new ArrayList<>();
        String album_art = "";

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor music_cursor = contentResolver.query(uri, null, selection, null, sortOrder);

        if (music_cursor != null && music_cursor.getCount() > 0) {
            while (music_cursor.moveToNext()) {
                //https://developer.android.com/reference/android/provider/MediaStore.Audio.AudioColumns.html
                String data = music_cursor.getString(music_cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String title = music_cursor.getString(music_cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = music_cursor.getString(music_cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String album_id = music_cursor.getString(music_cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String artist = music_cursor.getString(music_cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String duration = music_cursor.getString(music_cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

                //https://stackoverflow.com/questions/17573972/how-can-i-display-album-art-using-mediastore-audio-albums-album-art/17574629#17574629
                Cursor album_cursor = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID+ "=?",
                        new String[] {String.valueOf(album_id)},
                        null);

                if (album_cursor != null && album_cursor.getCount()>0) {
                    album_cursor.moveToFirst();
                    album_art = album_cursor.getString(album_cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                    album_cursor.close();
                }
                // Save to audioList
                audioList.add(new Audio(data, title, album, album_id, album_art, artist, duration));
            }
            music_cursor.close();
        }

        return audioList;
    }


    //https://github.com/googlesamples/android-RuntimePermissionsBasic/blob/master/Application/src/main/java/com/example/android/basicpermissions/MainActivity.java
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            // Request for read external storage permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Load audio from external storage and display it in a music list
                displayMusic();
            } else {
                // Permission request was denied.
                Snackbar.make(mLayout, "Read external storage permission was denied. Unable to load music.",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)
    }

    /**
     * Requests the {@link android.Manifest.permission#READ_EXTERNAL_STORAGE} permission.
     * If an additional rationale should be displayed, the user has to launch the request from
     * a SnackBar that includes additional information.
     */
    private void requestReadExternalStoragePermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            Snackbar.make(mLayout, "In order to load the music to the library you need to accept the permission!",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
                }
            }).show();

        } else {
            Snackbar.make(mLayout, "<b>External storage could not be opened.</b>\nThis occurs when the external storage is not available or if the system has denied access (for example when external storage access has been disabled).", Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    //https://developer.android.com/guide/components/intents-common.html#Music
    //https://stackoverflow.com/questions/38200282/android-os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed/38858040#38858040
    private void playMedia(String file) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        File fileToPlay = new File(file);
        Uri fileToPlayURI = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".co.elisavet.musicplayer.provider", fileToPlay);
        intent.setDataAndType(fileToPlayURI, "audio/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "It looks you don't have a music player installed!", Toast.LENGTH_LONG).show();
        }
    }

}
