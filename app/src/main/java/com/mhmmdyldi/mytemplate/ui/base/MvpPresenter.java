package com.mhmmdyldi.mytemplate.ui.base;

import com.mhmmdyldi.mytemplate.utils.errorUtils.ANError;

public interface MvpPresenter<V extends MvpActivityView> {
    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(ANError error);

    void setUserAsLoggedOut();
}
