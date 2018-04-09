package co.elisavet.musicplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Eli on 26-Mar-18.
 * Updated by Eli on 09-Apr-18
 * https://www.androidhive.info/2016/01/android-working-with-recycler-view/
 */

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.MyViewHolder> {

    private ArrayList<Audio> audioList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, albumTextView, artistTextView, durationTextView;
        public ImageView albumArtImageView;
        public MyViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.title);
            albumTextView = (TextView) view.findViewById(R.id.album);
            artistTextView = (TextView) view.findViewById(R.id.artist);
            durationTextView = (TextView) view.findViewById(R.id.duration);
            albumArtImageView = (ImageView) view.findViewById(R.id.album_art);
        }
    }

    public AudioAdapter(ArrayList<Audio> audioList) {
        this.audioList = audioList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Audio currentAudio = audioList.get(position);
        holder.titleTextView.setText(currentAudio.getTitle());
        holder.albumTextView.setText(currentAudio.getAlbum());
        holder.artistTextView.setText(currentAudio.getArtist());
        holder.durationTextView.setText(currentAudio.getDuration());
        String album_art_path = currentAudio.getAlbumArt();
        if (currentAudio.getAlbumArt() != null) {
            //https://developer.android.com/reference/android/graphics/BitmapFactory.html#decodeFile(java.lang.String)
            Bitmap bMap = BitmapFactory.decodeFile(album_art_path);
            if (bMap != null) {
                //https://stackoverflow.com/questions/4955268/how-to-set-a-bitmap-from-resource/4955441#4955441
                holder.albumArtImageView.setImageBitmap(bMap);
            } else {
                holder.albumArtImageView.setImageResource(R.drawable.default_album_art);
            }
        } else {
            holder.albumArtImageView.setImageResource(R.drawable.default_album_art);
        }
    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }
}
