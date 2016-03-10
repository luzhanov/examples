package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints.Required;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Gamebook extends Model {

    @Id
    private Long id;

    @Required
    private String name;

    public static Find<Long, Gamebook> find = new Find<Long, Gamebook>(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
