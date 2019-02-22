package com.codemate.booklibrary.ui;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.codemate.booklibrary.data.Book;
import com.codemate.booklibrary.data.Library;
import com.codemate.booklibrary.R;
import com.codemate.booklibrary.data.RandomBookGenerator;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, SearchView.OnQueryTextListener {
    @VisibleForTesting
    MainPresenter presenter;

    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        presenter = new MainPresenter(this, new Library(), new RandomBookGenerator());
        presenter.fetchBooks();
    }

    private void initializeViews() {
        bookAdapter = new BookAdapter();

        RecyclerView bookRecycler = (RecyclerView) findViewById(R.id.bookRecycler);
        bookRecycler.setLayoutManager(new LinearLayoutManager(this));
        bookRecycler.setAdapter(bookAdapter);

        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        presenter.searchForBooks(newText);
        return false;
    }

    @Override
    public void showBooks(List<Book> books) {
        bookAdapter.updateItems(books);
    }
}
