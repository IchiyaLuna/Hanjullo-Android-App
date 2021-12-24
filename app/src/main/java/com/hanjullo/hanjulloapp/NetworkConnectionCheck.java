package com.hanjullo.hanjulloapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;

import androidx.annotation.NonNull;

public class NetworkConnectionCheck extends ConnectivityManager.NetworkCallback {

    private boolean isConnected;
    //private Context context;
    private final NetworkRequest networkRequest;
    private ConnectivityManager connectivityManager;
    private static NetworkConnectionCheck INSTANCE;

    private NetworkConnectionCheck(Context context) {

        networkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();

        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static NetworkConnectionCheck getInstance() {
        return INSTANCE;
    }

    public static NetworkConnectionCheck getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NetworkConnectionCheck(context);
        }
        return INSTANCE;
    }

    public void changeContext(Context context) {
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void register() {
        this.connectivityManager.registerNetworkCallback(networkRequest, this);
    }

    public void unregister() {
        this.connectivityManager.unregisterNetworkCallback(this);
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        this.isConnected = true;
        Log.d("network", "onAvailable: true");
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        this.isConnected = false;
        Log.d("network", "onLost: false");
    }

}
