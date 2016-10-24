package com.geteventer.eventer;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.geteventer.eventer.Deck.DeckFragment;
import com.geteventer.eventer.SignIn.SignInSignUp;
import com.geteventer.eventer.util.ViewUtils;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.container)
    View mContainer;
    @BindView(R.id.ic_stack)
    ImageView mic_stack;
    @BindView(R.id.ic_user) ImageView mic_user;
    @BindView(R.id.stack) View mstack;
    @BindView(R.id.user) View muser;

    private final Map<String, Fragment> mFragments = new HashMap<>(3);
    private static final String TAG_DECK_FRAGMENT = "TAG_DECK_FRAGMENT";
    private static final String TAG_USER_FRAGMENT = "TAG_USER_FRAGMENT";
    private final Animation mAnimation = new AlphaAnimation(0, 1);{
        mAnimation.setDuration(200);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        mFragments.put(TAG_DECK_FRAGMENT, DeckFragment.newInstance());
        
        swapFragment(TAG_DECK_FRAGMENT);
        bottomBarIcons(mic_stack,mic_user,1);
        
    }


    @OnClick(R.id.user) public void onUserClicked() {
        startActivity(new Intent(this, SignInSignUp.class));
        bottomBarIcons(mic_stack,mic_user,2);
    }


    public static void bottomBarIcons(ImageView deck, ImageView user, int highlight) {
        switch(highlight)
        {
            case 1:
                user.setImageDrawable(new IconicsDrawable(user.getContext(), GoogleMaterial.Icon.gmd_account_box).actionBar().color(Color.LTGRAY));
                deck.setImageDrawable(new IconicsDrawable(deck.getContext(), GoogleMaterial.Icon.gmd_reorder).actionBar().color(Color.BLACK));
                break;
            case 2:
                user.setImageDrawable(new IconicsDrawable(user.getContext(), GoogleMaterial.Icon.gmd_account_box).actionBar().color(Color.BLACK));
                deck.setImageDrawable(new IconicsDrawable(deck.getContext(), GoogleMaterial.Icon.gmd_reorder).actionBar().color(Color.LTGRAY));
                break;
        }
    }
    private void swapFragment(String tag) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment targetFragment = manager.findFragmentByTag(tag);
        if (targetFragment != null && targetFragment.isVisible()) {
            return;
        }

        mContainer.setAnimation(mAnimation);
        mAnimation.start();

        // String otherTag = tag.equals(TAG_DECK_FRAGMENT) ? TAG_ARCHIVE_FRAGMENT : TAG_DECK_FRAGMENT;
        String otherTag = tag.equals(TAG_DECK_FRAGMENT) ? TAG_USER_FRAGMENT : TAG_DECK_FRAGMENT;
        if (manager.findFragmentByTag(tag) != null) {
            manager.beginTransaction()
                    .show(manager.findFragmentByTag(tag))
                    .commit();
            bottomBarIcons(mic_stack,mic_user,1);
        } else {
            manager.beginTransaction()
                    .add(R.id.container, mFragments.get(tag), tag)
                    .commit();
        }
        if (manager.findFragmentByTag(otherTag) != null) {
            manager.beginTransaction()
                    .hide(manager.findFragmentByTag(otherTag))
                    .commit();
        }
        //if (tag.equals(TAG_DECK_FRAGMENT)) showBottomBar(true);
    }
}
