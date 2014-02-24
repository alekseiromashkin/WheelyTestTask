package com.android.crazywheel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpController {

    public static final String GET = "http://crazy-dev.wheely.com";

    public static String get() throws IOException {

        HttpURLConnection connection = (HttpURLConnection) new URL(GET).openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();

        InputStream stream = connection.getInputStream();
        InputStreamReader isReader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(isReader);

        StringBuilder builder = new StringBuilder();
        String aux;
        while ((aux = br.readLine()) != null) {
            builder.append(aux);
        }

        return builder.toString();
    }

}
