package com.android.crazywheel;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements ItemsListFragment.TaskCallbacks{

    private ImageButton refreshButton;
    private ImageButton backButton;
    private ItemsListFragment itemsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        this.getSupportActionBar().setCustomView(R.layout.actionbar);

        this.refreshButton = (ImageButton) this.getSupportActionBar().getCustomView()
                .findViewById(R.id.refresh);
        this.backButton = (ImageButton) this.getSupportActionBar().getCustomView()
                .findViewById(R.id.back);

        this.refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsListFragment.refresh();
            }
        });

        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        this.itemsListFragment = (ItemsListFragment) fm.findFragmentByTag(ItemsListFragment.TAG);

        if (this.itemsListFragment == null) {
            this.itemsListFragment = new ItemsListFragment();
            fm.beginTransaction().add(R.id.container, this.itemsListFragment,
                    ItemsListFragment.TAG).commit();
        }

        HandlerThread hThread = new HandlerThread("HandlerThread");
        hThread.start();
        final Handler handler = new Handler(hThread.getLooper());
        final long oneMinuteMs = 40 * 1000;

        Runnable eachMinute = new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        itemsListFragment.refresh();
                    }
                });

                handler.postDelayed(this, oneMinuteMs);
            }
        };

        handler.postDelayed(eachMinute, oneMinuteMs);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        ItemsListFragment fragment = (ItemsListFragment) this.getSupportFragmentManager().findFragmentByTag(ItemsListFragment.TAG);
        if (fragment != null && fragment.isVisible()) {
            this.hideBackButton();
            this.showRefreshButton();
            this.setCustomTitle(this.getString(R.string.app_name));
        } else {
            this.hideRefreshButton();
            this.showBackButton();

            ItemDetailFragment itemDetailFragment = (ItemDetailFragment) this.getSupportFragmentManager()
                    .findFragmentByTag(ItemDetailFragment.TAG);
            if (itemDetailFragment != null)
                this.setCustomTitle(itemDetailFragment.getElement().getTitle());
        }

        getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setCustomTitle(String title) {

        ((TextView) this.getSupportActionBar().getCustomView().findViewById(R.id.title))
                .setText(title);

    }

    @Override
    public void onPreExecute() {

        this.animateRefreshButton();

    }

    @Override
    public void onProgressUpdate(int percent) {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPostExecute() {

        this.clearRefreshButtonAnimation();
        ((ElementsArrayAdapter) this.itemsListFragment.getListAdapter()).notifyDataSetChanged();

    }

    @Override
    public void onListItemClick(Element element) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new ItemDetailFragment(element), ItemDetailFragment.TAG);
        ft.addToBackStack(ItemsListFragment.TAG);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.supportInvalidateOptionsMenu();
    }

    public void showRefreshButton() {
        this.refreshButton.setVisibility(View.VISIBLE);
    }

    public void hideRefreshButton() {
        this.refreshButton.setVisibility(View.GONE);
    }

    public void showBackButton() {
        this.backButton.setVisibility(View.VISIBLE);
    }

    public void hideBackButton() {
        this.backButton.setVisibility(View.GONE);
    }

    public void animateRefreshButton() {

        RotateAnimation rAnim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rAnim.setRepeatCount(Animation.INFINITE);
        rAnim.setInterpolator(new LinearInterpolator());
        rAnim.setDuration(700);

        this.getSupportActionBar().getCustomView().findViewById(R.id.refresh).startAnimation(rAnim);

    }

    public void clearRefreshButtonAnimation() {

        this.getSupportActionBar().getCustomView().findViewById(R.id.refresh).clearAnimation();

    }
}
