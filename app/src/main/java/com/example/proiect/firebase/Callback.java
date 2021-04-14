package com.example.proiect.firebase;

public interface Callback<R> {
    void runResultOnUiThread(R result);
}
