package uneti.edu.vn.unetituyensinhapp;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class KhoaCNTTActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoa_cntt);

        Intent intent = getIntent();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.khoacntt));

        imageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(getApplicationContext()).load(R.drawable.gtkhoacntt01).placeholder(R.drawable.gtkhoacntt01).error(R.drawable.gtkhoacntt01).into(imageView);

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
