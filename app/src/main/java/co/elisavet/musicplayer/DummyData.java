package co.elisavet.musicplayer;

import java.util.ArrayList;

/**
 * Created by Eli on 14-Apr-18.
 */

public class DummyData {

    private ArrayList<Audio> libraryAudioListDummyData;

    public DummyData(){
        this.libraryAudioListDummyData = loadDummyData();
    }

    public ArrayList<Audio> getLibraryAudioListDummyData(){
        return libraryAudioListDummyData;
    }

    public ArrayList<Audio> loadDummyData() {

        ArrayList<Audio> audioList = new ArrayList<>();

        // Save to audioList
        audioList.add(new Audio("Like I Do", "Like I Do", "David Guetta, Martin Garrix and Brooks", "3:22", R.drawable.david_guetta_martin_garrix_and_brooks_like_i_do));
        audioList.add(new Audio("Dancing", "Golden", "Kylie Minogue", "2:58", R.drawable.kylie_minogue_dancing));
        audioList.add(new Audio("Me and Michael", "Little Dark Age", "MGMT", "4:49", R.drawable.mgmt_little_dark_age));
        audioList.add(new Audio("Little Dark Age", "Little Dark Age", "MGMT", "4:59", R.drawable.mgmt_little_dark_age));
        audioList.add(new Audio("Believer", "Evolve", "Imaging Dragons", "3:24", R.drawable.imagine_dragons_evolve));
        audioList.add(new Audio("Thunder", "Evolve", "Imaging Dragons", "3:07", R.drawable.imagine_dragons_evolve));
        audioList.add(new Audio("No Excuses", "No Excuses Single", "Meghan Trainor", "2:32", R.drawable.no_excuses));
        audioList.add(new Audio("Say Something", "Man of the Woods", "Justin Timberlake", "4:39", R.drawable.justin_timberlake_man_of_the_woods));
        audioList.add(new Audio("Savior", "Surviving the Summer", "Iggy Azalea", "3:31"));

        return audioList;
    }
}
