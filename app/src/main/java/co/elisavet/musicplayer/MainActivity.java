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
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

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
            // Permission is already available, start camera preview
            Snackbar.make(mLayout,
                    "Read External Storage permission is available. Loading music...",
                    Snackbar.LENGTH_SHORT).show();
            displayMusic();
        } else {
            // Permission is missing and must be requested.
            requestReadExternalStoragePermission();
        }
        // END_INCLUDE(startCamera)
    }

    private void displayMusic(){
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



    //https://github.com/googlesamples/android-RuntimePermissionsBasic/blob/master/Application/src/main/java/com/example/android/basicpermissions/MainActivity.java
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Snackbar.make(mLayout, "Read external storage permission was granted. Loading Music.",
                        Snackbar.LENGTH_SHORT)
                        .show();
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
    private void playMedia(String file) {
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
