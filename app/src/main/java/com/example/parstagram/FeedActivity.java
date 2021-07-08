package com.example.parstagram;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
    private RecyclerView rvPosts;
    protected List<Post> allPosts;
    protected PostsAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
    public static final String TAG = "FeedActivity";
    ImageButton btnHome;
    ImageButton btnNewPost;
    ImageButton btnProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        btnHome = findViewById(R.id.btnHome);
        btnNewPost = findViewById(R.id.btnNewPost);
        btnProfile = findViewById(R.id.btnProfile);
        rvPosts = findViewById(R.id.rvPosts);

        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(this, allPosts);

        // set adapter and layout manager for RV
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        // query posts from Parstagram
        queryPosts();
    }

    private void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
        // Remember to CLEAR OUT old items before appending in the new ones
        adapter.clear();
        // ...the data has come back, add new items to your adapter...
        queryPosts();
        // Now we call setRefreshing(false) to signal refresh has finished
        swipeContainer.setRefreshing(false);
    }

    private void queryPosts() {
        // specify we want to query Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by userkey
        query.include(Post.KEY_USER);
        // limit num posts to latest 20
        query.setLimit(20);
        // order by newest first
        query.addDescendingOrder("createdAt");
        // start async call for posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "issue with getting posts", e);
                    return;
                }

                // to debug - print every post description
                for (Post post : posts) {
                    Log.i(TAG, "Posts: " + post.getDescription() + ", username " + post.getUser());
                }

                // save received posts to allPosts and notify adapter of new data
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
