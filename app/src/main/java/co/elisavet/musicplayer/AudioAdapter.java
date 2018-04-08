package co.elisavet.musicplayer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Eli on 26-Mar-18.
 */

public class AudioAdapter extends ArrayAdapter<Audio> {

    ArrayList<Audio> audioList;

    public AudioAdapter(Activity context, ArrayList<Audio> audio){

        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for three TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, audio);
        audioList = new ArrayList<Audio>(audio);

    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Audio} object located at this position in the list
        Audio currentAudio = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID title
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        // Get the data from the current Audio object and
        // set this text on the dataTextView TextView
        titleTextView.setText(currentAudio.getTitle());

        // Find the TextView in the list_item.xml layout with the ID album
        TextView albumTextView = (TextView) listItemView.findViewById(R.id.album);
        // Get the album from the current Audio object and
        // set this text on the albumTextView TextView
        albumTextView.setText(currentAudio.getAlbum());

        // Find the TextView in the list_item.xml layout with the ID artist
        TextView artistTextView = (TextView) listItemView.findViewById(R.id.artist);
        // Get the data from the current Audio object and
        // set this text on the artistTextView TextView
        artistTextView.setText(currentAudio.getArtist());

        // Find the TextView in the list_item.xml layout with the ID duration
        TextView durationTextView = (TextView) listItemView.findViewById(R.id.duration);
        // Get the data from the current Audio object and
        // set this text on the durationTextView TextView
        durationTextView.setText(currentAudio.getDuration());

        // Find the ImageView in the list_item.xml layout with the ID album_art
         ImageView albumArtImageView = (ImageView) listItemView.findViewById(R.id.album_art);
        // Get the data from the current Audio object and
        // set this the image
         String album_art_path = currentAudio.getAlbumArt();
         if (album_art_path != null) {
             //https://developer.android.com/reference/android/graphics/BitmapFactory.html#decodeFile(java.lang.String)
             Bitmap bMap = BitmapFactory.decodeFile(album_art_path);
             if (bMap != null) {
                 //https://stackoverflow.com/questions/4955268/how-to-set-a-bitmap-from-resource/4955441#4955441
                 albumArtImageView.setImageBitmap(bMap);
             } else {
                 albumArtImageView.setImageResource(R.drawable.default_album_art);
             }
         } else {
             albumArtImageView.setImageResource(R.drawable.default_album_art);
         }

        // Return the whole list item layout (containing 3 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

    //@Override

    public int getCount() {
        return audioList.size();
    }
}
