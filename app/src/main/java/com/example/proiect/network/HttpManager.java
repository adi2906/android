package com.example.proiect.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class HttpManager implements Callable<String> {
    private URL url;
    private HttpURLConnection connection;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader reader;

    private final String urlAdress;

    public HttpManager(String urlAdress) {
        this.urlAdress = urlAdress;
    }

    @Override
    public String call() throws Exception {
        try{
            return getHttpContent();
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }finally {
            CloseConnection();
        }
        return null;
    }

    private void CloseConnection() {

        try{
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try{
            inputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        connection.disconnect();
    }

    private String getHttpContent() throws IOException {

        AddOpenConnection();
        try{
            inputStream=connection.getInputStream();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        inputStreamReader=new InputStreamReader(inputStream);
        reader= new BufferedReader(inputStreamReader);

        StringBuilder result= new StringBuilder();
        String line;
        while((line=reader.readLine())!=null)
        {
            result.append(line);
        }
        return result.toString();
    }

    private void AddOpenConnection() throws IOException {
        url=new URL(urlAdress);
        connection=(HttpURLConnection) url.openConnection();
    }
}
