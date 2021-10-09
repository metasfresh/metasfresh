-- 2021-08-11T07:20:28.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ES_FTS_Config SET ES_CreateIndexCommand='{
  "settings": {
    "analysis": {
      "filter": {
        "german_stop": {
          "type": "stop",
          "stopwords": "_german_"
        }
      },
      "analyzer": {
        "standard_analyzer": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "german_stop",
            "german_normalization"
          ]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "c_bpartner_id": {
        "type": "keyword"
      },
      "c_bpartner_location_id": {
        "type": "keyword"
      },
      "ad_user_id": {
        "type": "keyword"
      },
      "value": {
        "type": "text",
        "analyzer": "standard_analyzer",
        "term_vector": "with_positions_offsets"
      },
      "name": {
        "type": "text",
        "analyzer": "standard_analyzer",
        "term_vector": "with_positions_offsets"
      },
      "firstname": {
        "type": "text",
        "analyzer": "standard_analyzer",
        "term_vector": "with_positions_offsets"
      },
      "lastname": {
        "type": "text",
        "analyzer": "standard_analyzer",
        "term_vector": "with_positions_offsets"
      },
      "city": {
        "type": "text",
        "analyzer": "standard_analyzer",
        "term_vector": "with_positions_offsets"
      },
      "postal": {
        "type": "text",
        "analyzer": "standard_analyzer",
        "term_vector": "with_positions_offsets"
      },
      "address1": {
        "type": "text",
        "analyzer": "standard_analyzer",
        "term_vector": "with_positions_offsets"
      }
    }
  }
}
', ES_QueryCommand='{
  "_source": true,
  "query": {
    "multi_match": {
      "query": @query@,
      "type": "bool_prefix",
      "fields": [
        "value",
        "name",
        "firstname",
        "lastname",
        "city",
        "postal",
        "address1"
      ]
    }
  },
  "highlight": {
    "fields": {
      "value": {
        "number_of_fragments": 1,
        "no_match_size": 100
      },
      "name": {
        "number_of_fragments": 1,
        "no_match_size": 100
      },
      "firstname": {
        "number_of_fragments": 1,
        "no_match_size": 100
      },
      "lastname": {
        "number_of_fragments": 1,
        "no_match_size": 100
      },
      "city": {
        "number_of_fragments": 1,
        "no_match_size": 100
      },
      "postal": {
        "number_of_fragments": 1,
        "no_match_size": 100
      },
      "address1": {
        "number_of_fragments": 1,
        "no_match_size": 100
      }
    }
  }
}
',Updated=TO_TIMESTAMP('2021-08-11 10:20:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540000
;

