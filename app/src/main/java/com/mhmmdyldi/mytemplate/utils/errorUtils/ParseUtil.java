package com.mhmmdyldi.mytemplate.utils.errorUtils;

import com.google.gson.Gson;

public class ParseUtil {
    private static Parser.Factory mParserFactory;

    public static void setParserFactory(Parser.Factory parserFactory) {
        mParserFactory = parserFactory;
    }

    public static Parser.Factory getParserFactory() {
        if (mParserFactory == null) {
            mParserFactory = new GsonParserFactory(new Gson());
        }
        return mParserFactory;
    }

    public static void shutDown() {
        mParserFactory = null;
    }
}
