package com.e.cmlive.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.e.cmlive.R;
import com.e.cmlive.adapter.FilterStickerAdapter;
import com.e.cmlive.interfaces.FilterStickerListener;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartLiveActivity extends AppCompatActivity implements FilterStickerListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_live);
        ButterKnife.bind(this);

        tabLayout.addTab(tabLayout.newTab()
                .setText("Filters")
                .setTag("filter"));
        tabLayout.addTab(tabLayout.newTab()
                .setText("Styles")
                .setTag("style"));

        setupRecyclerview();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getTag() != null) {
                    switch (tab.getTag().toString()) {
                        case "filter": {
                            setupRecyclerview();
                            break;
                        }
                        case "style": {
                            setupRecyclerview();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @OnClick({R.id.emoji, R.id.upper_layout})
    void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.emoji: {
                showBottomLayout();
                break;
            }
            case R.id.go_live: {

                break;
            }
            case R.id.upper_layout: {
                hideBottomLayout();
                break;
            }
        }
    }

    private void setupRecyclerview() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(new FilterStickerAdapter(this, this));
    }

    private void hideBottomLayout() {
        bottomLayout.setVisibility(View.GONE);
    }

    private void showBottomLayout() {
        bottomLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFilterSelect() {
        hideBottomLayout();
    }

    @Override
    public void onStickerSelect() {
        hideBottomLayout();
    }
}