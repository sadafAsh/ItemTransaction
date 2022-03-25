package com.soj.item.transaction.util;

public class Resource<T> {
    private long id;
    private String type;
    private T attribute;

    public Resource(long id, String type, T attribute) {
        this.id = id;
        this.type = type;
        this.attribute = attribute;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getAttribute() {
        return attribute;
    }

    public void setAttribute(T attribute) {
        this.attribute = attribute;
    }
}
