package com.ashcorp.searchapi.entity;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

public class Index {

    private String id;
    private String name;

    public String getId() {
	    return id;
    }
    @Field
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    @Field
    public void setName(String name) {
        this.name = name;
    }

}