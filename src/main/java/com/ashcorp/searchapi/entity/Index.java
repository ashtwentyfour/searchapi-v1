package com.ashcorp.searchapi.entity;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

public class Index {
    /**
     * Document ID
     */
    private String id;
    /**
     * Document name
     */
    private String name;

    /**
     * Get document ID
     */
    public String getId() {
	    return id;
    }
    /**
     * Set document ID
     * @param id Document ID
     */
    @Field
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Get document name
     */
    public String getName() {
        return name;
    }
    /**
     * Set document name
     * @param name Document name field
     */
    @Field
    public void setName(String name) {
        this.name = name;
    }

}