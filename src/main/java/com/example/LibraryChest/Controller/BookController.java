package com.example.LibraryChest.Controller;

import com.example.LibraryChest.DataBaseOperations.BookDB;
import com.example.LibraryChest.Model.Book;
import org.hibernate.Session;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    @GetMapping("/book/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-add";
    }

    @PostMapping("/book/add")
    public String addBook(@ModelAttribute Book book) {
        Session session = BookDB.getSession();
        session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();
        return "redirect:/book/add";
    }

    @GetMapping("/book/details/{id}")
    public String showBookDetails(@PathVariable Long id, Model model) {
        Book book = BookDB.getBookById(id);
        model.addAttribute("book", book);
        return "book-details";
    }
    @GetMapping("/add-user-to-book")
    public String showAddUserToBookPage(@RequestParam("bookId") Long bookId, Model mode){
        mode.addAttribute("bookID",bookId);
        return "add-user-to-book";
    }
    @PostMapping("/add-user-to-book")
    public String addUserToBook(@RequestParam("bookId") Long bookId, @RequestParam("ownerName") String ownerName) {
        Session session = BookDB.getSession();
        session.beginTransaction();
        Book book = BookDB.getBookById(bookId);
        book.setUser(ownerName);
        book.setPopulation(book.getPopulation()+1);
        session.saveOrUpdate(book);
        session.getTransaction().commit();

        return "redirect:/book/details/" + bookId;
    }
}
