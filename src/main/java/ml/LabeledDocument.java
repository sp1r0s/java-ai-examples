package ml;

import java.io.Serializable;

/**
 * Simple class that represents a labeled document
 */
public class LabeledDocument extends Document implements Serializable {
    private double label;

    public double getLabel() {
        return label;
    }

    public void setLabel(double label) {
        this.label = label;
    }

    public LabeledDocument(long id, String text, double label) {
        super(id, text);
        this.label = label;
    }
}