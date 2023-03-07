package kz.springcourse.demo.service;

import kz.springcourse.demo.model.Book;
import kz.springcourse.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book getBook(Integer id){
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addBook(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(Book book){
        Book bookToBeUpdated = bookRepository.findById(book.getId()).orElse(null);

        if(bookToBeUpdated != null){
            bookToBeUpdated.setYear(book.getYear());
            bookToBeUpdated.setName(book.getName());
            bookToBeUpdated.setAuthor(book.getAuthor());
        }
    }

    @Transactional
    public void personTakeBook(Book book, Integer user_id){
        Book bookToBeUpdated = bookRepository.findById(book.getId()).orElse(null);

        if(bookToBeUpdated != null){
            bookToBeUpdated.setTookUser(user_id);
        }
    }
    @Transactional
    public void deleteBook(Integer id){
        bookRepository.delete(bookRepository.findById(id).orElse(null));
    }

    @Transactional
    public List<Book> getBooksByUserId(Integer id){
        return bookRepository.findByTookUser(id);
    }


}
