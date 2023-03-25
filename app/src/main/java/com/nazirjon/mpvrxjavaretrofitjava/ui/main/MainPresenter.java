package com.nazirjon.mpvrxjavaretrofitjava.ui.main;

import android.util.Log;

import com.nazirjon.mpvrxjavaretrofitjava.models.MovieResponse;
import com.nazirjon.mpvrxjavaretrofitjava.network.NetworkClient;
import com.nazirjon.mpvrxjavaretrofitjava.network.NetworkInterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainPresenterInterface {
    MainViewInterface mvi;
    private String TAG = "MainPresenter";

    public MainPresenter(MainViewInterface mvi) {
        this.mvi = mvi;
    }

    @Override
    public void getMovies() {
        getObservable().subscribeWith(getObserver());
    }

    public Observable<MovieResponse> getObservable() {
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getMovies("004cbaf19212094e32aa9ef6f6577f22")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<MovieResponse> getObserver() {
        return new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(@NonNull MovieResponse movieResponse) {
                Log.d(TAG, "OnNext" + movieResponse.getTotalResults());
                mvi.displayMovies(movieResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                mvi.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
                mvi.hideProgressBar();
            }
        };
    }
}
