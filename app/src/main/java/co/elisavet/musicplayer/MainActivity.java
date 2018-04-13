package co.elisavet.musicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Audio> libraryAudioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayMusic();
    }


    private void displayMusic() {
        ListView listView = (ListView) findViewById(R.id.library_song_list);

        //Load Audio and store it in the libraryAudioList ArrayList<Audio>
        DummyData data = new DummyData();
        libraryAudioList = data.getLibraryAudioListDummyData();
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
                //https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application/35406288#35406288
                Audio selectedAudio = libraryAudioList.get(position);
                Intent intent = new Intent(getBaseContext(), NowPlayingActivity.class);
                intent.putExtra("SELECTED_AUDIO", selectedAudio);
                //intent.putExtra("POSITION", position);
                startActivity(intent);
            }
        });
    }

}