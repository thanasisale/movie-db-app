package com.thanasis.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MovieListAdapter extends ArrayAdapter<Movie> {
    private Context mContext;
    private int mResource;

    static class ViewHolder {
        TextView id;
        TextView title;
        TextView director;
        TextView length;
        TextView type;
        TextView year;
    }

    public MovieListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Movie> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int mId = getItem(position).getMovieId();
        String mTitle = getItem(position).getMovieName();
        String mDirector = getItem(position).getMovieDirector();
        String mLength = getItem(position).getLengthMinutes();
        String mType = getItem(position).getMovieType();
        String mYear = getItem(position).getMovieYear();

        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            holder.id = convertView.findViewById(R.id.listId);
            holder.title = convertView.findViewById(R.id.listTitle);
            holder.year = convertView.findViewById(R.id.listYear);
            holder.director = convertView.findViewById(R.id.listDirector);
            holder.type = convertView.findViewById(R.id.listType);
            holder.length = convertView.findViewById(R.id.listMinutes);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.id.setText(String.valueOf(mId));
        holder.title.setText(mTitle);
        holder.year.setText(mYear);
        holder.director.setText(mDirector);
        holder.type.setText(mType);
        holder.length.setText(mLength);

        return convertView;
    }

}
