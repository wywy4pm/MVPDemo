package com.toryang.sampledemo.presenter;

import android.content.Context;

import com.toryang.sampledemo.api.InitRetrofit;
import com.toryang.sampledemo.api.NetService;
import com.toryang.sampledemo.entities.movieEntitiy.Movieinfo;
import com.toryang.sampledemo.ui.view.TopDataView;
import com.toryang.sampledemo.utils.Log;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by toryang on 5/24/16.
 */
public class Top250PresenterImpl extends BasePresenter<TopDataView> {

    Log log = Log.YLog();
    Context context;
    public Top250PresenterImpl(Context context){
        this.context = context;
    }

    @Override
    public void attachView(TopDataView topDataView) {
        super.attachView(topDataView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void getTop250(){
        InitRetrofit.createApi(NetService.class)
                .getTop250()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        getMvpView().startLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movieinfo>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.e(e.getStackTrace());
                    }

                    @Override
                    public void onNext(Movieinfo top250Entity) {
                        getMvpView().dataBack(top250Entity);
                    }
                });
    }
}
