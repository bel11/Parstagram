package com.example.parstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parstagram.EndlessRecyclerViewScrollListener;
import com.example.parstagram.Post;
import com.example.parstagram.PostsAdapter;
import com.example.parstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostsFragment extends Fragment {

    RecyclerView rvPosts;
    public static final String TAG = "PostsFragment";
    protected List<Post> allPosts;
    protected PostsAdapter adapter;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);

        // set adapter and layout manager for RV
        rvPosts.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(linearLayoutManager);
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Date createdAt = allPosts.get(allPosts.size()-1).getCreatedAt();
                loadNextDataFromApi(createdAt);
            }
        };
        rvPosts.addOnScrollListener(endlessRecyclerViewScrollListener);
        queryPosts(0);
    }

    private void queryPosts(int page) {
        // specify we want to query Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by userkey
        query.include(Post.KEY_USER);
        // limit num posts to latest 20
        query.setLimit(5);
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

    private void loadNextDataFromApi(Date createdAt) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // skips already loaded posts
        query.include(Post.KEY_USER);

        query.addDescendingOrder("createdAt");
        query.setLimit(5);

        query.whereLessThan("createdAt", createdAt);
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
//                endlessRecyclerViewScrollListener.resetState();
            }
        });

    }
}