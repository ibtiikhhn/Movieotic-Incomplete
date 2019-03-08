package com.codeiatic.movieotic.Views.Activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.codeiatic.movieotic.R;
import com.codeiatic.movieotic.Views.MoviesFragments.InCinemaMovies;
import com.codeiatic.movieotic.Views.MoviesFragments.PopularMovies;
import com.codeiatic.movieotic.Views.MoviesFragments.TopRatedMovies;
import com.codeiatic.movieotic.Views.MoviesFragments.UpCommingMovies;
import com.codeiatic.movieotic.Views.TvFragments.PopularTV;
import com.codeiatic.movieotic.Views.TvFragments.TopRatedTV;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import de.cketti.mailto.EmailIntentBuilder;

public class MainActivity extends AppCompatActivity {

    ActionBar actionbar;
    NavigationView navigationView;
    DrawerLayout mDrawerLayout;
    FloatingActionButton searchBt;

    UpCommingMovies upCommingMovies;
    PopularMovies popularMovies;
    TopRatedMovies topRatedMovies;
    InCinemaMovies inCinemaMovies;

    TopRatedTV topRatedTV;
    PopularTV popularTV;

    BottomAppBar bottomAppBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        bottomAppBar = findViewById(R.id.bar);
        setSupportActionBar(bottomAppBar);

        searchBt = findViewById(R.id.fab);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        textView = findViewById(R.id.your_title);

        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setCheckedItem(R.id.upComingMovies);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {

                            case R.id.inCinemaMovies:
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, inCinemaMovies).commit();
                                textView.setText("In Cinemas");
                                break;

                            case R.id.topRatedMovies:
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, topRatedMovies).commit();
                                textView.setText("Top Rated");
                                break;

                            case R.id.popularMovies:
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, popularMovies).commit();
                                textView.setText("Popular Now");
                                break;

                            case R.id.upComingMovies:
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, upCommingMovies).commit();
                                textView.setText("Up Coming");
                                break;

                            case R.id.topRatedTV:
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, topRatedTV).commit();
                                textView.setText("Top Rated");
                                break;

                            case R.id.popularTV:
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, popularTV).commit();
                                textView.setText("Popular");
                                break;

                            case R.id.contactDeveloper:
                                EmailIntentBuilder.from(getApplicationContext())
                                        .to("ikhhn313@gmail.com")
                                        .subject("Message from Movieotic Android app")
                                        .body("Such a great app")
                                        .start();
                                break;

                            case R.id.shareApp:
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT,
                                        "Do you watch Movies? Be a Movieotic, Check this out now at : http://play.google.com/store/apps/details?id=" + getApplication().getPackageName());
                                sendIntent.setType("text/plain");
                                startActivity(sendIntent);
                                break;
                            case R.id.rateApp:
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
                                } catch (android.content.ActivityNotFoundException e) {
                                    startActivity(new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                                }
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, upCommingMovies).commit();

        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });


        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchAcitivity.class);
                startActivity(intent);
            }
        });
    }

    public void initFragments() {
        upCommingMovies = UpCommingMovies.getInstance();
        popularMovies = PopularMovies.getInstance();
        inCinemaMovies = InCinemaMovies.getInstance();
        topRatedMovies = TopRatedMovies.getInstance();

        topRatedTV = TopRatedTV.getInstance();
        popularTV = PopularTV.getInstance();
    }
}
