package controllers;


import models.Gamebook;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.bookslist;
import views.html.index;

import java.util.Arrays;
import java.util.List;

public class GamebookController extends Controller {

    static Form<Gamebook> gamebookForm = new Form<>(Gamebook.class);

    public Result index() {
        return ok(index.render("Gamebook application"));
    }

    public Result listAllBooks() {
        List<Gamebook> gamebooks = Gamebook.find.all();
        return ok(bookslist.render(gamebooks, gamebookForm));
    }

    public Result createGamebook() {
        Form<Gamebook> form = gamebookForm.bindFromRequest(request());

        if (!form.hasErrors()) {
            form.get().save();
            return redirect(routes.GamebookController.listAllBooks());
        } else {
            return badRequest(bookslist.render(Gamebook.find.all(), form));
        }
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

    public Result getBook() {
        return TODO;
    }

}
