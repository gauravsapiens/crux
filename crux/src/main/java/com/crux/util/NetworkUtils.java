package com.crux.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.crux.Crux;

/**
 * Utils for network related operations
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public class NetworkUtils {

    public static boolean checkInternetConnectivity() {
        Context context = Crux.getContext();
        if (context == null) {
            new IllegalArgumentException("Crux not initialized exception");
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] allNetworks = manager.getAllNetworkInfo();
        for (NetworkInfo networkInfo : allNetworks) {
            if (networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

}
