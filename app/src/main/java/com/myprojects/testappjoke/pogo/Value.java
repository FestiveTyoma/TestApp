package com.myprojects.testappjoke.pogo;

import java.util.List;

//POJO class generated by https://www.jsonschema2pojo.org for https://api.icndb.com/jokes/random/3
public class Value {

    @com.squareup.moshi.Json(name = "id")
    private int id;
    @com.squareup.moshi.Json(name = "joke")
    private String joke;
    @com.squareup.moshi.Json(name = "categories")
    private List<Object> categories = null;


    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public List<Object> getCategories() {
        return categories;
    }

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

}
