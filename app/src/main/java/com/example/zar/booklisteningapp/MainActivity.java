package com.example.zar.booklisteningapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    EditText edt_category;
    Button btn_search;
    ListView listView;
    ArrayList<Book> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiComponent();




        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://www.googleapis.com/books/v1/volumes?q="+edt_category.getText().toString();
                AsynNetworkTaskJasonTwo asynNetworkTaskJasonTwo=new AsynNetworkTaskJasonTwo();
                asynNetworkTaskJasonTwo.execute(url);
             /*   AsyncNetworkTaskJasonTwo asyncNetworkTaskJason=new AsyncNetworkTaskJasonTwo();
                asyncNetworkTaskJason.execute(url);*/
             /*   arrayList=asyncNetworkTaskJason.getList();

                BookListeningAdapter bookListeningAdapter=new BookListeningAdapter(MainActivity.this,arrayList);
                listView.setAdapter(bookListeningAdapter);*/



            }
        });

    }
    public void intiComponent()
    {
        edt_category= (EditText) findViewById(R.id.edt_category);
        btn_search= (Button) findViewById(R.id.btn_search);
        listView= (ListView) findViewById(R.id.list_item);
        list=new ArrayList<Book>();
    }
    private String httpCalls(String inputUrl) {

        try {
            URL url = new URL(inputUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String data = "";
            while (true) {
                String temp = br.readLine();

                if (temp == null) {
                    break;
                }
                data += temp;
            }

            return data;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

    private ArrayList<Book> convertToJsonObject(String json)
    {

        final ArrayList<Book> bookArrayList=new ArrayList<Book>();
        try{
            JSONObject jsonObject=new JSONObject(json);

            JSONArray jsonArray=jsonObject.getJSONArray("items");
            Log.v("Item Arrays size",""+jsonArray.length());
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject objectFromArray = jsonArray.getJSONObject(i);
                JSONObject volumeInfo=objectFromArray.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                Log.v("Title :",title);

                JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                String[] authors = new String[]{"No Authors"};
                if (!volumeInfo.isNull("authors")) {
                    authors=new String[authorsArray.length()];
                    for (int j = 0; j < authorsArray.length(); j++) {
                        authors[j] = authorsArray.getString(j);
                        Log.v("authors :",authors[j]);
                    }
                    bookArrayList.add(new Book(title,authors));
                }

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bookArrayList;
    }

    public  class AsynNetworkTaskJasonTwo extends AsyncTask<String,Void,ArrayList<Book>>
    {

        @Override
        protected ArrayList<Book> doInBackground(String... params) {
        String jasonData=httpCalls(params[0]);
        list=convertToJsonObject(jasonData);
        return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> arrayList) {
            listView.setAdapter(new BookListeningAdapter(MainActivity.this,arrayList));
        }
    }





}
