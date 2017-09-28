package uneti.edu.vn.unetituyensinhapp;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.cosoHanoi));

        imageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(getApplicationContext()).load(R.drawable.uneti3).placeholder(R.drawable.logo_uneti).error(R.drawable.uneti3).into(imageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
