package com.example.zar.booklisteningapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Zar on 12/25/2016.
 */

public class BookListeningAdapter extends ArrayAdapter<Book> {


    public BookListeningAdapter(Activity context, ArrayList<Book> bookList)
    {
        super(context,0,bookList);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(convertView==null)
        {
            view= LayoutInflater.from(getContext()).inflate(R.layout.book_lists,parent,false);
        }

        Book currentBook=getItem(position);

        TextView title= (TextView) view.findViewById(R.id.txt_title);
        TextView authors= (TextView) view.findViewById(R.id.txt_authors);

        title.setText(currentBook.getTitle());
        authors.setText(Arrays.toString(currentBook.getAuthors()));

        return view;
    }
}
