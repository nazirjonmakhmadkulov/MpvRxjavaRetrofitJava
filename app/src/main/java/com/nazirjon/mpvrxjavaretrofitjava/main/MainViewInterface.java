package com.nazirjon.mpvrxjavaretrofitjava.main;


import com.nazirjon.mpvrxjavaretrofitjava.models.MovieResponse;

public interface MainViewInterface {

    void showToast(String s);
    void showProgressBar();
    void hideProgressBar();
    void displayMovies(MovieResponse movieResponse);
    void displayError(String s);
}
