package com.mhmmdyldi.mytemplate.ui.base;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mhmmdyldi.mytemplate.R;
import com.mhmmdyldi.mytemplate.data.DataManager;
import com.mhmmdyldi.mytemplate.data.network.model.ApiError;
import com.mhmmdyldi.mytemplate.utils.AppConstants;
import com.mhmmdyldi.mytemplate.utils.errorUtils.ANConstants;
import com.mhmmdyldi.mytemplate.utils.errorUtils.ANError;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpActivityView> implements MvpPresenter<V>{

    private static final String TAG = BasePresenter.class.getName();
    private V mMvpActivityView;
    private CompositeDisposable compositeDisposable;
    private DataManager dataManager;

    @Inject
    public BasePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
        this.dataManager = dataManager;
    }

    public BasePresenter(){}

    @Override
    public void onAttach(V mvpView) {
        mMvpActivityView = mvpView;
    }

    @Override
    public void onDetach() {
        compositeDisposable.dispose();
        mMvpActivityView = null;
    }

    public V getmMvpActivityView() {
        return mMvpActivityView;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    @Override
    public void handleApiError(ANError error) {

        if (error == null || error.getErrorBody() == null) {
            getmMvpActivityView().onError(R.string.api_default_error);
            return;
        }

        if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
                && error.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
            getmMvpActivityView().onError(R.string.connection_error);
            return;
        }

        if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
                && error.getErrorDetail().equals(ANConstants.REQUEST_CANCELLED_ERROR)) {
            getmMvpActivityView().onError(R.string.api_retry_error);
            return;
        }

        final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();

        try {
            ApiError apiError = gson.fromJson(error.getErrorBody(), ApiError.class);

            if (apiError == null || apiError.getMessage() == null) {
                getmMvpActivityView().onError(R.string.api_default_error);
                return;
            }

            switch (error.getErrorCode()) {
                case HttpsURLConnection.HTTP_UNAUTHORIZED:
                case HttpsURLConnection.HTTP_FORBIDDEN:
                    setUserAsLoggedOut();
                    getmMvpActivityView().openActivityOnTokenExpire();
                case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                case HttpsURLConnection.HTTP_NOT_FOUND:
                default:
                    getmMvpActivityView().onError(apiError.getMessage());
            }
        } catch (JsonSyntaxException | NullPointerException e) {
            Log.e(TAG, "handleApiError", e);
            getmMvpActivityView().onError(R.string.api_default_error);
        }
    }

    @Override
    public void setUserAsLoggedOut() {

    }
}
