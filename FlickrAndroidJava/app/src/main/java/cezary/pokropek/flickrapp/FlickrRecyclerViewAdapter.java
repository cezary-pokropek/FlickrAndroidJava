package cezary.pokropek.flickrapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder> {
    public static final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> mPhotosList;
    private Context mContext;

    public FlickrRecyclerViewAdapter(Context context, List<Photo> photosList) {
        this.mContext = context;
        this.mPhotosList = photosList;
    }

    @NonNull
    @Override
    public FlickrImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // creating a view from the browse xml layout and then returning a view
        // Called by the layout manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder: new view requested ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);
        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrImageViewHolder holder, int position) {
        // method is called by the recyclerview when it wants new data to be stored in a
        //view holder so that it can display it now as item scrolled off the screen the
        //recycler view will provide a recycled viewholder object and tell us the
        //position of the data object that it needs to display what we have to do in
        //this method is get that item from the list and put its values into the
        //viewholder widgets

        if ((mPhotosList == null) || (mPhotosList.size() == 0 )) {
            holder.thumbnail.setImageResource(R.drawable.placeholder);
            holder.title.setText(R.string.empty_photo);
        } else {

            // we need to add Picasso as a dependency

            /* Called by the layout manager when it wants new data in an existing row */

            //firstly we retrieved the current photo object from the list
            Photo photoItem = mPhotosList.get(position);
            Log.d(TAG, "onBindViewHolder: " + photoItem.getTitle() + " --> " + position);

            //  use the Picasso.width method to retrieve or get a picasso object
            //sets the placeholder image to be used if there's an error it also has the
            //placeholder which is there while the images are downloading

            Picasso.with(mContext).load(photoItem.getImage())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.thumbnail);

            // where we store the downloaded image into the imageview widget in the viewholder so
            //picasso goes away and download the image from the URL on a background
            //thread and put it into the imageview once it's downloaded our method doesn't
            //wait for the download to finish

            holder.title.setText(photoItem.getTitle());

        }

    }

    @Override
    public int getItemCount() {
        //Log.d(TAG, "getItemCount: called");
        return ((mPhotosList != null) && (mPhotosList).size() !=0) ? mPhotosList.size() : 1;
    }

    // load a new data, this is when the querry changes and new data is downloaded we need to be able to provide the adapter with the new list
    void loadNewData(List<Photo> newPhotos) {
        mPhotosList = newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position) {
        return ((mPhotosList != null) && (mPhotosList.size() != 0 ) ? mPhotosList.get(position) : null);

    }


    static class FlickrImageViewHolder extends RecyclerView.ViewHolder {
        private static final String TEG = "FlickrRecyclerViewHolder";
        ImageView thumbnail = null;
        TextView title = null;

        public FlickrImageViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageViewHolder: starts");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
