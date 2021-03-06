package com.algonquintimes.algonquintimes.Articles;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.algonquintimes.algonquintimes.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class InnovationsFragment extends Fragment {
    public static String TAG = "postFrag";
    public List<Posts> mPosts;
    public Button btnGetPost;
    public RecyclerView recyclerView;
    public PostAdapter postAdapter;
    public int page = 1;
    public SwipeRefreshLayout swipeRefreshLayout;
    public File file;
    private InnovationsModel postModel;
    private Observer<List<Posts>> postsObserver;

    public InnovationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_articles, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        super.onActivityCreated(savedInstanceState);
        Config.base_url = "http://algonquintimes.com/wp-json/wp/v2/posts?_embed=true&page=1&per_page=20&categories=5";
        recyclerView = getActivity().findViewById(R.id.recyclerHome);
        postAdapter = new PostAdapter(mPosts, getContext(), false, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeResources(R.color.accent_green, R.color.md_red_800, R.color.md_blue_500, R.color.purple);
        mPosts = new ArrayList<Posts>();
        recyclerView.setAdapter(postAdapter);
        postModel = ViewModelProviders.of(getActivity()).get(InnovationsModel.class);
        swipeRefreshLayout.setEnabled(true);
        postModel.getPostsList().observe(this, new Observer<List<Posts>>() {
            @Override
            public void onChanged(@Nullable List<Posts> posts) {
                postAdapter.setData(posts);
                postAdapter.notifyItemChanged(postModel.getChangeIndex());


                swipeRefreshLayout.setRefreshing(false);
            }
        });
        postModel.getRefresh().observe(this, new Observer<Integer>() {

            @Override
            public void onChanged(@Nullable Integer integer) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                postModel.RefreshData();


            }
        });


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Config.base_url = "http://algonquintimes.com/wp-json/wp/v2/posts?_embed=true&page=1&per_page=20&categories=5";
        recyclerView = getActivity().findViewById(R.id.recyclerHome);
        postAdapter = new PostAdapter(mPosts, getContext(), false, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeResources(R.color.accent_green, R.color.md_red_800, R.color.md_blue_500, R.color.purple);
        mPosts = new ArrayList<Posts>();
        recyclerView.setAdapter(postAdapter);
        postModel = ViewModelProviders.of(getActivity()).get(InnovationsModel.class);
        swipeRefreshLayout.setEnabled(true);
        postModel.getPostsList().observe(this, new Observer<List<Posts>>() {
            @Override
            public void onChanged(@Nullable List<Posts> posts) {
                postAdapter.setData(posts);
                postAdapter.notifyItemChanged(postModel.getChangeIndex());

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        postModel.getRefresh().observe(this, new Observer<Integer>() {

            @Override
            public void onChanged(@Nullable Integer integer) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                postModel.RefreshData();


            }
        });


    }


    public void setData(List<Posts> posts) {
        recyclerView.setAdapter(postAdapter);
        postAdapter.setData(mPosts);
        swipeRefreshLayout.setRefreshing(false);
        postAdapter.notifyDataSetChanged();
    }


}
