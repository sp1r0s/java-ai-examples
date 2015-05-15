package ml;

import java.io.Serializable;

/**
 * Simple class that represents an unlabeled document
 */
public class Document implements Serializable {
    private long id;
    private String text;

    public Document(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}