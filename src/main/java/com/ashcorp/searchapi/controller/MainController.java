package com.ashcorp.searchapi.controller;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashcorp.searchapi.entity.Index;
import com.ashcorp.searchapi.entity.Result;

@RestController
@RequestMapping(path = "/search") // path
public class MainController {

    private Logger logger
        = LoggerFactory.getLogger(MainController.class);

    @Autowired
    Result solrResult;

    @Value("${spring.solr.host}")
    private String solrHost;

    @GetMapping(value="/query/{core}")
    public ResponseEntity<Result> search(
            @RequestParam String field, 
            @RequestParam String value, 
            @RequestParam String start, 
            @RequestParam String stop,
            @RequestParam String tag, 
            @RequestParam String order,
            @RequestParam(required = false, defaultValue = "name") String facetField,
            @RequestParam(required = false) String facetQuery, 
            @PathVariable String core
        ) throws SolrServerException, IOException {

        String solrUrl = "http://"+solrHost+":8983/solr/"+core;
        SolrClient client = new HttpSolrClient.Builder(solrUrl).build();
        SolrQuery query = new SolrQuery();
        if(field.equals("full_text")) 
            query.set("q", value);
        else
            query.set("q", field+":"+value);
        query.set("start", start);
        query.set("rows", stop);
        
        SortClause sortClause = new SortClause(tag, order);
        query.setSort(sortClause);
        query.setFacet(true);
        query.addFacetField(facetField);
        if(facetQuery != null)
            query.addFacetQuery(facetQuery);
        
        QueryResponse response = client.query(query);
        List<Index> docs = response.getBeans(Index.class);
        List<Count> facetFields = response.getFacetField(facetField).getValues();
        Map<String,Long> facetFieldResults = new HashMap<String,Long>();    
        for(Count c: facetFields) {
            facetFieldResults.put(c.getName(), c.getCount());
        }
        Map<String,Integer> facetQueryResults = response.getFacetQuery();
        SolrDocumentList numdocs = response.getResults();
        response.getDebugMap();
        long count = numdocs.getNumFound();
        logger.info(Long.toString(count)+" results not found for "+field+":"
                    +value+" in core "+core);
        solrResult.setCount(count);
        solrResult.setIndex(docs);
        solrResult.setFacetField(facetFieldResults);
        solrResult.setFacetQuery(facetQueryResults);
        return new ResponseEntity<Result>(solrResult,HttpStatus.OK);
    
    }

}