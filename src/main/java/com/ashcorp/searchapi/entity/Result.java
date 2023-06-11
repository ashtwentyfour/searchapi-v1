package com.ashcorp.searchapi.entity;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Result {

    /**
     * Number of search results found
     */
    private Long count;
    /**
     * List of documents in results
     */
    private List<Index> index;
    /**
     * Facet query results
     */
    private Map<String,Integer> facetQuery;
    /**
     * Facet field results
     */
    private Map<String,Long> facetField;

    /**
     * Get result count
     */
    public Long getCount() {
	    return count;
    }
    /**
     * Set count
     * @param count Count value
     */
    public void setCount(Long count) {
        this.count = count;
    }
    /**
     * Get indexed documents from result
     */
    public List<Index> getIndex() {
        return index;
    }
    /**
     * Set index
     * @param index Document result list
     */
    public void setIndex(List<Index> index) {
        this.index = index;
    }
    /**
     * Get facet query results
     */
    public Map<String,Integer> getFacetQuery() {
        return facetQuery;
    }
    /**
     * Set facet query results
     * @param facetQuery Facet query result mapping
     */
    public void setFacetQuery(Map<String,Integer> facetQuery) {
        this.facetQuery = facetQuery;
    }
    /**
     * Get facet field results
     */
    public Map<String,Long> getFacetField() {
        return facetField;
    }
    /**
     * Set facet field results
     * @param facetField Facet field result mapping
     */
    public void setFacetField(Map<String,Long> facetField) {
        this.facetField = facetField;
    }

}