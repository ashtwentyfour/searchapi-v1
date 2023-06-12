package com.ashcorp.searchapi.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.auth.UsernamePasswordCredentials;
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
import org.springframework.data.solr.server.support.HttpSolrClientFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashcorp.searchapi.entity.Index;
import com.ashcorp.searchapi.entity.Result;

@RestController
@RequestMapping(path = "/search") // base path
public class MainController {

    /**
     * Logger declaration.
     */
    private Logger logger
        = LoggerFactory.getLogger(MainController.class);

    /**
     * Solr result object.
     */
    @Autowired
    private Result solrResult;

    /**
     * Solr cluster hostname.
     */
    @Value("${spring.solr.host}")
    private String solrHost;

    /**
     * Solr username.
     */
    @Value("${spring.solr.user}")
    private String solrUser;

    /**
     * Solr password.
     */
    @Value("${spring.solr.pass}")
    private String solrPass;

    /**
     * Generate solr client.
     * @param core Solr core
     * @return Solr client object
     */
    private SolrClient solrClient(final String core) {

        String solrUrl = "http://" + solrHost + ":8983/solr/" + core;
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                                                        solrUser, solrPass
                                                  );
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl).build();
        return new HttpSolrClientFactory(client, credentials, "BASIC").getSolrClient();

    }

    /**
     * Fetch query results from Solr.
     * @param field Query field
     * @param value Query field value
     * @param start Start row
     * @param stop Stop row
     * @param tag Sort field
     * @param order Sort order
     * @param facetField Facet field
     * @param facetQuery Facet query
     * @param core Solr core
     * @return Solr search results
     */
    @GetMapping(value = "/query/{core}")
    public ResponseEntity<Result> search(
            @RequestParam final String field,
            @RequestParam final String value,
            @RequestParam final String start,
            @RequestParam final String stop,
            @RequestParam final String tag,
            @RequestParam final String order,
            @RequestParam(required = false, defaultValue = "name") final String facetField,
            @RequestParam(required = false) final String facetQuery,
            @PathVariable final String core
        ) throws SolrServerException, IOException {

        SolrClient client = solrClient(core);
        SolrQuery query = new SolrQuery();
        if (field.equals("full_text")) {
            query.set("q", value);
        } else {
            query.set("q", field + ":" + value);
        }
        query.set("start", start);
        query.set("rows", stop);

        SortClause sortClause = new SortClause(tag, order);
        query.setSort(sortClause);
        query.setFacet(true);
        query.addFacetField(facetField);
        if (facetQuery != null) {
            query.addFacetQuery(facetQuery);
        }

        QueryResponse response = client.query(query);
        List<Index> docs = response.getBeans(Index.class);
        List<Count> facetFields = response.getFacetField(facetField)
                                  .getValues();
        Map<String, Long> facetFieldResults = new HashMap<String, Long>();
        for (Count c: facetFields) {
            facetFieldResults.put(c.getName(), c.getCount());
        }
        Map<String, Integer> facetQueryResults = response.getFacetQuery();
        SolrDocumentList res = response.getResults();
        long count = res.getNumFound();

        List<Map<String, String>> valuesDump = new ArrayList<>();
        for (int i = 0; i < docs.size(); i++) {
            Map<String, String> valuesMap = new HashMap<String, String>();
            Collection<String> fieldNames = res.get(i).getFieldNames();
            for (String s: fieldNames) {
                valuesMap.put(s, res.get(i).getFieldValue(s).toString());
            }
            valuesDump.add(valuesMap);
        }
        logger.info(Long.toString(count) + " results found for "
                    + field + ":" + value + " in core " + core);
        solrResult.setCount(count);
        solrResult.setIndex(docs);
        solrResult.setFacetField(facetFieldResults);
        solrResult.setFacetQuery(facetQueryResults);
        solrResult.setValuesDump(valuesDump);
        return new ResponseEntity<Result>(solrResult, HttpStatus.OK);

    }

}
