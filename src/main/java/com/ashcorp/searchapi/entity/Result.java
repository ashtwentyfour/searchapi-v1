package com.ashcorp.searchapi.entity;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Result {

    /**
     * Number of search results found.
     */
    private Long count;
    /**
     * List of documents in results.
     */
    private List<Index> index;
    /**
     * Facet query results.
     */
    private Map<String, Integer> facetQuery;
    /**
     * Facet field results.
     */
    private Map<String, Long> facetField;

    /**
     * All fields and values.
     */
    private List<Map<String, String>> valuesDump;

    /**
     * Get result count.
     * @return Result count
     */
    public Long getCount() {
	    return count;
    }
    /**
     * Set count.
     * @param docCount Count value
     */
    public void setCount(final Long docCount) {
        this.count = docCount;
    }
    /**
     * Get indexed documents from result.
     * @return List of documents
     */
    public List<Index> getIndex() {
        return index;
    }
    /**
     * Set index.
     * @param indexList Document result list
     */
    public void setIndex(final List<Index> indexList) {
        this.index = indexList;
    }
    /**
     * Get facet query results.
     * @return Facet query results
     */
    public Map<String, Integer> getFacetQuery() {
        return facetQuery;
    }
    /**
     * Set facet query results.
     * @param facetQueryMapping Facet query result mapping
     */
    public void setFacetQuery(final Map<String, Integer> facetQueryMapping) {
        this.facetQuery = facetQueryMapping;
    }
    /**
     * Get facet field results.
     * @return Facet field results
     */
    public Map<String, Long> getFacetField() {
        return facetField;
    }
    /**
     * Set facet field results.
     * @param facetFieldMapping Facet field result mapping
     */
    public void setFacetField(final Map<String, Long> facetFieldMapping) {
        this.facetField = facetFieldMapping;
    }
    /**
     * Get all fields and values.
     * @return All fields and values
     */
    public List<Map<String, String>> getValuesDump() {
        return valuesDump;
    }
    /**
     * Set values dump.
     * @param valuesDumpMap Field to value mapping
     */
    public void setValuesDump(final List<Map<String, String>> valuesDumpMap) {
        this.valuesDump = valuesDumpMap;
    }

}
