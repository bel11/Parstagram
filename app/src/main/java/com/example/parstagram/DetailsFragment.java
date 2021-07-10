package com.example.parstagram;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;


public class DetailsFragment extends Fragment {

    private TextView tvUsername;
    private TextView tvDescription;
    private ImageView ivImage;
    private TextView tvRelativeTime;
    private String username;
    private String description;
    private String time;
    private String imageUrl;

    public DetailsFragment() {
        // Required empty public constructor

    }

    public DetailsFragment(String username, String description, String time, String imageUrl) {
        this.username = username;
        this.description = description;
        this.time = time;
        this.imageUrl = imageUrl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvRelativeTime = view.findViewById(R.id.tvRelativeTime);
        ivImage = view.findViewById(R.id.ivImage);

        tvDescription.setText(description);
        tvUsername.setText(username);
        tvRelativeTime.setText(time);
        Log.i("postadaptertest", "relative time");
        Glide.with(getContext())
                .load(imageUrl)
                .into(ivImage);
    }
}