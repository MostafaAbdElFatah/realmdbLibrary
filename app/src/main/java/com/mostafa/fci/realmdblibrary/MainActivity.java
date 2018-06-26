package com.mostafa.fci.realmdblibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {


    EditText name , author , search;
    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        author = findViewById(R.id.author);
        details = findViewById(R.id.details);
        search  = findViewById(R.id.search);

        Realm.init(MainActivity.this);
    }

    long id = System.currentTimeMillis() + (int )(Math.random() * 100 + 1);

    public void savebtn_Clicked(View view) {

        Book book = new Book(++id , name.getText().toString(),author.getText().toString());
        RealmHelper.insertOnlySingleBook(book);
        Toast.makeText(MainActivity.this , "Saved" ,Toast.LENGTH_LONG).show();

    }

    public void getDatabtn_Clicked(View view) {

        details.setText("Details : \n");

        String text = search.getText().toString().trim();
        if ( !text.isEmpty() ) {
            long id = Long.valueOf(search.getText().toString()).longValue();
            Book book = RealmHelper.fetchOneBooksbyBookId(id);
            if (book != null)
                details.setText(details.getText() + "\nID : "+book.getId()+"  , name : "+book.getName()+" " +
                        ", author : "+book.getAuthor() +"\n");
            else
                Toast.makeText(MainActivity.this , "not Found " ,Toast.LENGTH_LONG).show();
        }else {
            RealmResults<Book> books = RealmHelper.fetchAllBooks();
            for (Book book : books) {
                details.setText(details.getText() +"\nID : "+book.getId()+"  , name : "+book.getName()+" " +
                        ", author : "+book.getAuthor() +"\n");
            }
        }

    }

    public void deleteDatabtn_Clicked(View view) {

        String text = search.getText().toString().trim();
        if ( !text.isEmpty() ) {
            long id = Long.valueOf(search.getText().toString()).longValue();

            RealmHelper.deleteBook(id);
            Toast.makeText(MainActivity.this, "Deleted ", Toast.LENGTH_LONG).show();
        }else {
            RealmHelper.deleteAllBook();
            Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_LONG).show();
        }

    }

    public void updateDatabtn_Clicked(View view) {

        Realm realm= Realm.getDefaultInstance();
        long id = Long.valueOf(search.getText().toString()).longValue();
        Book book = realm.where(Book.class)
                .equalTo("id", id)
                .findFirst();
        realm.beginTransaction();
        if (book != null) {
            book.setName(name.getText().toString());
            book.setAuthor(author.getText().toString());
            Toast.makeText(MainActivity.this , "updated " ,Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this , "not Found " ,Toast.LENGTH_LONG).show();
        }
        realm.commitTransaction();
    }
}
