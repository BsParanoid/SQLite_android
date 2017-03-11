package com.github.bsparanoid.the;

/**
 * Created by bsparanoid on 11/03/17.
 */

public class Comment {
    private long id;
    private String comment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {
        return comment;
    }
}