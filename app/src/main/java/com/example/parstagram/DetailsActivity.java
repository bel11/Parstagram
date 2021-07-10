package com.example.parstagram;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {
    private TextView tvUsername;
    private TextView tvDescription;
    private ImageView ivImage;
    private TextView tvRelativeTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.item_post);

        tvDescription = findViewById(R.id.tvDescription);
        tvUsername = findViewById(R.id.tvUsername);
        tvRelativeTime = findViewById(R.id.tvRelativeTime);
        ivImage = findViewById(R.id.ivImage);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        tvDescription.setText(post.getDescription());
        tvUsername.setText(post.getUser().getUsername());
        tvRelativeTime.setText(PostsAdapter.getRelativeTimeAgo(post.getCreatedAt().toString()));
        Log.i("postadaptertest", "relative time");
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this)
                    .load(image.getUrl())
                    .into(ivImage);
        }

    }
}
