package com.nazirjon.mpvrxjavaretrofitjava.search;


import com.nazirjon.mpvrxjavaretrofitjava.models.MovieResponse;

public interface SearchViewInterface {

    void showToast(String str);
    void displayResult(MovieResponse movieResponse);
    void displayError(String s);
    void showProgressBar();
    void hideProgressBar();
}
