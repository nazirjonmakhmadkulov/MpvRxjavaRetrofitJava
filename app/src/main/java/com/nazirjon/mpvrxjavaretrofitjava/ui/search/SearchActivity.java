package com.nazirjon.mpvrxjavaretrofitjava.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nazirjon.mpvrxjavaretrofitjava.R;
import com.nazirjon.mpvrxjavaretrofitjava.adapters.MoviesAdapter;
import com.nazirjon.mpvrxjavaretrofitjava.models.MovieResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
public class SearchActivity extends AppCompatActivity implements SearchViewInterface {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.rvQueryResult)
    RecyclerView rvQueryResult;

    private SearchView searchView;
    SearchPresenter searchPresenter;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setupViews();
        setupMVP();
    }

    private void setupViews() {
        setSupportActionBar(toolbar);
        rvQueryResult.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupMVP(){
        searchPresenter = new SearchPresenter(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     getMenuInflater().inflate(R.menu.menu_search,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Enter Movie name..");
        searchPresenter.getResultsBasedOnQuery(searchView);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(SearchActivity.this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayResult(MovieResponse movieResponse) {
        adapter = new MoviesAdapter(movieResponse.getResults(),SearchActivity.this);
        rvQueryResult.setAdapter(adapter);
    }

    @Override
    public void displayError(String s) {
        showToast(s);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
