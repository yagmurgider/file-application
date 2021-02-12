package com.example.fileapplication.util;

public final class ApiPaths {

    private static final String BASE_PATH = "/api";

    public static final class FileCtrl {
        public static final String CTRL = BASE_PATH + "/file";
    }

    public static final class AccountCtrl {
        public static final String CTRL = BASE_PATH + "/token";
    }

    public static final class UserCtrl {
        public static final String CTRL = BASE_PATH + "/users";
    }
}
