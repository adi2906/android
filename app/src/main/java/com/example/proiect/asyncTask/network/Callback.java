package com.example.proiect.asyncTask.network;

public interface Callback<R> {

    void runResultOnUiThread(R result);
}
