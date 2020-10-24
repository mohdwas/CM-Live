package com.e.cmlive.activities;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.e.cmlive.R;
import com.e.cmlive.adapter.GiftsAdapter;
import com.e.cmlive.adapter.LiveCommentsAdapter;
import com.e.cmlive.base.BaseActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WatchLiveActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.comment_field)
    EditText commentField;
    @BindView(R.id.send_button)
    TextView send;
    @BindView(R.id.other_options)
    LinearLayout otherOptions;
    @BindView(R.id.tab_layout)
    TabLayout giftTabLayout;
    @BindView(R.id.gifts_recycler_view)
    RecyclerView giftRecyclerview;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;

    private BottomSheetDialog sendDialog;
    private View sendView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_live);
        ButterKnife.bind(this);

        init();
        setupRecyclerview();
        attachKeyboardListeners();

        giftTabLayout.addTab(giftTabLayout.newTab()
                .setText("My Bag")
                .setTag("my_bag"));
        giftTabLayout.addTab(giftTabLayout.newTab()
                .setText("Popular")
                .setTag("popular"));
        giftTabLayout.addTab(giftTabLayout.newTab()
                .setText("Birthday")
                .setTag("birthday"));
        giftTabLayout.addTab(giftTabLayout.newTab()
                .setText("Hot")
                .setTag("hot"));

        setupGiftsRecyclerview();

        giftTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tag = tab.getTag().toString();

                if (tag != null) {
                    switch (tag) {
                        case "my_bag": {
                            setupGiftsRecyclerview();
                            break;
                        }
                        case "popular": {
                            setupGiftsRecyclerview();
                            break;
                        }
                        case "birthday": {
                            setupGiftsRecyclerview();
                            break;
                        }
                        case "hot": {
                            setupGiftsRecyclerview();
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

    private void init() {
        sendDialog = new BottomSheetDialog(this);
        sendView = LayoutInflater.from(this).inflate(R.layout.dialog_send_comment, null);
        sendDialog.setContentView(sendView);
    }

    private void setupRecyclerview() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new LiveCommentsAdapter(this));
    }

    private void setupGiftsRecyclerview() {
        giftRecyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        giftRecyclerview.setAdapter(new GiftsAdapter(this));
    }

    @OnClick({R.id.send_button, R.id.gifts, R.id.upper_layout})
    void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.send_button: {
                sendDialog.show();
                break;
            }
            case R.id.gifts: {
                showBottomLayout();
                break;
            }
            case R.id.upper_layout: {
                hideBottomLayout();
                break;
            }
        }
    }

    @Override
    protected void onShowKeyboard(int keyboardHeight) {
        if (keyboardHeight > 100) {
            //show when value 953
            showSendCommentUI();
        } else {
            //hide when value 72
            hideSendCommentUI();
        }
    }

    @Override
    protected void onHideKeyboard() {
        hideSendCommentUI();
    }

    private void hideBottomLayout() {
        bottomLayout.setVisibility(View.GONE);
    }

    private void showBottomLayout() {
        bottomLayout.setVisibility(View.VISIBLE);
    }

    private void showSendCommentUI() {
        otherOptions.setVisibility(View.GONE);
        send.setVisibility(View.VISIBLE);
    }

    private void hideSendCommentUI() {
        otherOptions.setVisibility(View.VISIBLE);
        send.setVisibility(View.GONE);
    }
}