package com.codecool.si_practice;

public class Routes {

    @WebRoute(path = "/test1")
    public String test1() {
        return "called test1";
    }

    @WebRoute(path = "test2")
    public String test2() {
        return "called test2";
    }
}
