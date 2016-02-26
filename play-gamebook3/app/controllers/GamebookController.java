package controllers;


import play.mvc.Controller;
import play.mvc.Result;

import views.html.bookslist;
import views.html.index;

import java.util.Arrays;
import java.util.List;

public class GamebookController extends Controller {

    public Result index() {
        return ok(index.render("Gamebook application"));
    }

    public Result createGamebook() {
        return TODO;
    }

    public Result getGamebook(Long id) {
        return TODO;
    }

    public Result updateGamebook(Long id) {
        return TODO;
    }

    public Result deleteGamebook(Long id) {
        return TODO;
    }

    public Result listAllBooks() {
        String username = session("username");

        List<String> bookNames = Arrays.asList("Book1", "Book2", "Book3");
        return ok(bookslist.render(bookNames));
    }

    public Result getBook() {
        return TODO;
    }

}
