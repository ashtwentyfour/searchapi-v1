package com.ashcorp.searchapi.entity;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Result {

    private Long count;
    private List<Index> index;
    private Map<String,Integer> facetQuery;
    private Map<String,Long> facetField;

    public Long getCount() {
	    return count;
    }
    public void setCount(Long count) {
        this.count = count;
    }
    public List<Index> getIndex() {
        return index;
    }
    public void setIndex(List<Index> index) {
        this.index = index;
    }
    public Map<String,Integer> getFacetQuery() {
        return facetQuery;
    }
    public void setFacetQuery(Map<String,Integer> facetQuery) {
        this.facetQuery = facetQuery;
    }
    public Map<String,Long> getFacetField() {
        return facetField;
    }
    public void setFacetField(Map<String,Long> facetField) {
        this.facetField = facetField;
    }

}