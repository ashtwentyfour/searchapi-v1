package com.ashcorp.searchapi.entity;

import org.apache.solr.client.solrj.beans.Field;

public class Index {
    /**
     * Document ID.
     */
    private String id;
    /**
     * Document name.
     */
    private String name;

    /**
     * Get document ID.
     * @return Document ID
     */
    public String getId() {
	    return id;
    }
    /**
     * Set document ID.
     * @param docId Document ID
     */
    @Field
    public void setId(final String docId) {
        this.id = docId;
    }
    /**
     * Get document name.
     * @return Name field value
     */
    public String getName() {
        return name;
    }
    /**
     * Set document name.
     * @param nameField Document name field
     */
    @Field
    public void setName(final String nameField) {
        this.name = nameField;
    }

}
