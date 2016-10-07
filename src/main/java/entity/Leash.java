package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "LEASH")
public class Leash implements Serializable {

    @Id
    @GeneratedValue
    private Long ID;
    private String imageName;
    private String text;
    private String size;
    private String color;

    public Leash() {
    }

    public Leash(String imageName, String text, String size, String color) {
        this.imageName = imageName;
        this.text = text;
        this.size = size;
        this.color = color;
    }

    public Leash(Long ID, String imageName, String text, String size, String color) {
        this.ID = ID;
        this.imageName = imageName;
        this.text = text;
        this.size = size;
        this.color = color;
    }

    public Long getID() {
        return ID;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(final String imageName) {
        this.imageName = imageName;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getSize() {
        return size;
    }

    public void setSize(final String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format("%-5s|%-15s|%-30s|%-8s|%s", ID + ".", imageName, text, size, color);
    }
}
