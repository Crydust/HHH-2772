package be.crydust;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Cat implements Serializable {

    private static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 255)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner")
    private Human owner;

    @OneToMany(mappedBy = "mother")
    private List<Cat> kittens;

    @ManyToOne
    private Cat mother;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Human getOwner() {
        return owner;
    }

    public void setOwner(Human owner) {
        this.owner = owner;
    }

    public List<Cat> getKittens() {
        return kittens;
    }

    public void setKittens(List<Cat> kittens) {
        this.kittens = kittens;
    }

    public Cat getMother() {
        return mother;
    }

    public void setMother(Cat mother) {
        this.mother = mother;
    }

    @Override
    public String toString() {
        return "Cat{" + "id=" + id + ", name=" + name + '}';
    }

}
