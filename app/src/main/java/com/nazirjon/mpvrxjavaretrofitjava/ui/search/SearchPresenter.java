package com.nazirjon.mpvrxjavaretrofitjava.ui.search;

import android.annotation.SuppressLint;
import android.util.Log;
import androidx.appcompat.widget.SearchView;
import com.nazirjon.mpvrxjavaretrofitjava.models.MovieResponse;
import com.nazirjon.mpvrxjavaretrofitjava.network.NetworkClient;
import com.nazirjon.mpvrxjavaretrofitjava.network.NetworkInterface;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;


public class SearchPresenter implements SearchPresenterInterface {
    private String TAG = "SearchPresenter";
    SearchViewInterface searchviewInterface;

    public SearchPresenter(SearchViewInterface searchViewInterface) {
        this.searchviewInterface = searchViewInterface;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getResultsBasedOnQuery(SearchView searchView) {
        getObservableQuery(searchView).filter(s -> !s.equals("")).debounce(2, TimeUnit.SECONDS).distinctUntilChanged().switchMap((Function<String, ObservableSource<MovieResponse>>) s -> NetworkClient.getRetrofit().create(NetworkInterface.class).getMoviesBasedOnQuery("004cbaf19212094e32aa9ef6f6577f22", s)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(getObserver());
    }

    private Observable<String> getObservableQuery(SearchView searchView) {
        final PublishSubject<String> publishSubject = PublishSubject.create();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                publishSubject.onNext(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                publishSubject.onNext(newText);
                return true;
            }
        });

        return publishSubject;
    }

    public DisposableObserver<MovieResponse> getObserver() {
        return new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(@NonNull MovieResponse MovieResponse) {
                Log.d(TAG, "OnNext" + MovieResponse.getTotalResults());
                searchviewInterface.displayResult(MovieResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                searchviewInterface.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
            }
        };
    }
}
