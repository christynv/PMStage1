package com.example.pmstage1;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    public static String getMovieURL(String request) throws IOException {

        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {

            connection.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(connection.getInputStream());

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            connection.disconnect();
        }
    }
}
