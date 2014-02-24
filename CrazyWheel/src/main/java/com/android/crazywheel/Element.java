package com.android.crazywheel;

public class Element {

    private String id;
    private String title;
    private String text;

    public Element() {}

    public String toString() {

        return this.title;

    }

    public boolean equals(Object object) {
        return object instanceof Element && this.id.equals(((Element) object).id);
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public void setId(String id) {

        this.id = id;

    }

    public void setTitle(String title) {

        this.title = title;

    }

    public void setText(String text) {

        this.text = text;

    }

    public String getId() {

        return this.id;

    }

    public String getTitle() {

        return this.title;

    }

    public String getText() {

        return this.text;

    }

}
