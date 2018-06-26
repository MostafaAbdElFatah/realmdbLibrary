package com.mostafa.fci.realmdblibrary;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by FCI on 2018-06-22.
 */

public class RealmHelper {

    static Realm realm = getDatabase();

    public static void insertOnlySingleBook(final Book book) {

        realm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                Book book1 = getDatabase().copyToRealm(book);
            }
        });
    }

    public void insertMultipleBooks(final List<Book> books) {

        realm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                List<Book> booksList = getDatabase().copyToRealm(books);
            }
        });
    }

    public static Book fetchOneBooksbyBookId(Long id) {
        return  realm.where(Book.class).equalTo("id", id).findFirst();
    }

    public static RealmResults<Book> fetchAllBooks() {

        ArrayList<Book> books = new ArrayList<>();
        RealmResults<Book> booksList = realm.where(Book.class).findAll();
        return  booksList;
    }

    public static void updateBook(final long id) {

    }

    public static void deleteBook(final long id) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Book> rows = realm.where(Book.class).equalTo("id",id).findAll();
                rows.deleteAllFromRealm();
            }
        });

    }

    public static void deleteAllBook() {

        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

    }



    private static Realm INSTANCE;

    static Realm getDatabase() {
        if (INSTANCE == null)
            synchronized (RealmHelper.class) {
                if (INSTANCE == null)
                    INSTANCE = Realm.getDefaultInstance();
            }
        return INSTANCE;
    }

}
