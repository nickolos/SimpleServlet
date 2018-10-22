package servlets;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class MirrorRequestServletTest {

    @Test
    public void doGet() throws IOException {
        MirrorRequestServlet ser = new MirrorRequestServlet();

        final int expectedCodeValue = 200;
        String address = "http://localhost:8080/mirror?key=po";
        URL url = new URL(address);
        final  String expectedKey = address.substring(address.indexOf("=")+1,address.length());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);

        int code = connection.getResponseCode();

        if (code == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder answer = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }

            reader.close();

            assertEquals(expectedCodeValue,code);

            assertEquals(expectedKey, answer.toString());
        }
    }


    @Test
    public void doGetWhenURLWithoutKey() throws IOException {

        final int expectedCodeValue = 404;
        String address = "http://localhost:8080/mirror";
        URL url = new URL(address);
        final  String expectedKey = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);

        int code = connection.getResponseCode();

        if (code == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf8"));
            String answer = "";
            String line = null;

            while ((line = reader.readLine()) != null) {
                answer += line;
            }

            reader.close();

            assertEquals(expectedCodeValue,code);
            assertEquals(expectedKey,answer);
        }
    }
}