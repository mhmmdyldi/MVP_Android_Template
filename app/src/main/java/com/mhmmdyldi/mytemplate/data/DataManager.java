package com.mhmmdyldi.mytemplate.data;

import com.mhmmdyldi.mytemplate.data.network.ApiHelper;

public interface DataManager extends ApiHelper {

    void updateApiHeader(Long userID, String accessToken);

    void setUserAsLoggedOut();

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_SERVER(1);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
