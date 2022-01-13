-- 2021-07-30T12:32:34.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_CreateIndexCommand,ES_DocumentToIndexTemplate,ES_FTS_Config_ID,ES_Index,ES_QueryCommand,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2021-07-30 15:32:34','YYYY-MM-DD HH24:MI:SS'),100,'{
  "settings": {
    "analysis": {
      "analyzer": {
        "partial_words" : {
          "type": "custom",
          "tokenizer": "ngrams",
          "filter": ["lowercase"]
        }
      },
      "tokenizer": {
        "ngrams": {
          "type": "ngram",
          "min_gram": 3,
          "max_gram": 4
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "value": {
        "type": "text",
        "fields": {
          "shingles": {
            "type": "search_as_you_type"
          },
          "ngrams": {
            "type": "text",
            "analyzer": "partial_words",
            "search_analyzer": "standard",
            "term_vector": "with_positions_offsets"
          }
        }
      },
      "firstname": {
        "type": "text",
        "fields": {
          "shingles": {
            "type": "search_as_you_type"
          },
          "ngrams": {
            "type": "text",
            "analyzer": "partial_words",
            "search_analyzer": "standard",
            "term_vector": "with_positions_offsets"
          }
        }
      },
      "lastname": {
        "type": "text",
        "fields": {
          "shingles": {
            "type": "search_as_you_type"
          },
          "ngrams": {
            "type": "text",
            "analyzer": "partial_words",
            "search_analyzer": "standard",
            "term_vector": "with_positions_offsets"
          }
        }
      },
      "companyname": {
        "type": "text",
        "fields": {
          "shingles": {
            "type": "search_as_you_type"
          },
          "ngrams": {
            "type": "text",
            "analyzer": "partial_words",
            "search_analyzer": "standard",
            "term_vector": "with_positions_offsets"
          }
        }
      },
      "city": {
        "type": "text",
        "fields": {
          "shingles": {
            "type": "search_as_you_type"
          },
          "ngrams": {
            "type": "text",
            "analyzer": "partial_words",
            "search_analyzer": "standard",
            "term_vector": "with_positions_offsets"
          }
        }
      },
      "postal": {
        "type": "text",
        "fields": {
          "shingles": {
            "type": "search_as_you_type"
          },
          "ngrams": {
            "type": "text",
            "analyzer": "partial_words",
            "search_analyzer": "standard",
            "term_vector": "with_positions_offsets"
          }
        }
      },
      "address1": {
        "type": "text",
        "fields": {
          "shingles": {
            "type": "search_as_you_type"
          },
          "ngrams": {
            "type": "text",
            "analyzer": "partial_words",
            "search_analyzer": "standard",
            "term_vector": "with_positions_offsets"
          }
        }
      }
    }
  }
}
','{
	"value": @C_BPartner.Value@,
	"name": @C_BPartner.Name@,
	"city": @C_Location.City@,
	"postal": @C_Location.Postal@,
	"address1": @C_Location.Address1@"
}
',540000,'fts_bpartner','QUERY_TODO','Y',TO_TIMESTAMP('2021-07-30 15:32:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-30T12:32:49.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ES_FTS_Config SET ES_CreateIndexCommand='{
  "settings": {
    "analysis": {
      "analyzer": {
        "partial_words" : {
          "type": "custom",
          "tokenizer": "ngrams",
          "filter": ["lowercase"]
        }
      },
      "tokenizer": {
        "ngrams": {
          "type": "ngram",
          "min_gram": 3,
          "max_gram": 4
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "value": {
        "type": "text",
        "fields": {
          "shingles": {
            "type": "search_as_you_type"
          },
          "ngrams": {
            "type": "text",
            "analyzer": "partial_words",
            "search_analyzer": "standard",
            "term_vector": "with_positions_offsets"
          }
        }
      },
      "name": {
        "type": "text",
        "fields": {
          "shingles": {
            "type": "search_as_you_type"
          },
          "ngrams": {
            "type": "text",
            "analyzer": "partial_words",
            "search_analyzer": "standard",
            "term_vector": "with_positions_offsets"
          }
        }
      },
      "city": {
        "type": "text",
        "fields": {
          "shingles": {
            "type": "search_as_you_type"
          },
          "ngrams": {
            "type": "text",
            "analyzer": "partial_words",
            "search_analyzer": "standard",
            "term_vector": "with_positions_offsets"
          }
        }
      },
      "postal": {
        "type": "text",
        "fields": {
          "shingles": {
            "type": "search_as_you_type"
          },
          "ngrams": {
            "type": "text",
            "analyzer": "partial_words",
            "search_analyzer": "standard",
            "term_vector": "with_positions_offsets"
          }
        }
      },
      "address1": {
        "type": "text",
        "fields": {
          "shingles": {
            "type": "search_as_you_type"
          },
          "ngrams": {
            "type": "text",
            "analyzer": "partial_words",
            "search_analyzer": "standard",
            "term_vector": "with_positions_offsets"
          }
        }
      }
    }
  }
}
',Updated=TO_TIMESTAMP('2021-07-30 15:32:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540000
;


-- 2021-07-30T12:49:06.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config_SourceModel (AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,ES_FTS_Config_ID,ES_FTS_Config_SourceModel_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,291,TO_TIMESTAMP('2021-07-30 15:49:06','YYYY-MM-DD HH24:MI:SS'),100,540000,540000,'Y',TO_TIMESTAMP('2021-07-30 15:49:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-30T12:52:06.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config_SourceModel (AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,ES_FTS_Config_ID,ES_FTS_Config_SourceModel_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,293,TO_TIMESTAMP('2021-07-30 15:52:06','YYYY-MM-DD HH24:MI:SS'),100,540000,540001,'Y',TO_TIMESTAMP('2021-07-30 15:52:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-30T12:52:43.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config_SourceModel (AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,ES_FTS_Config_ID,ES_FTS_Config_SourceModel_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,114,TO_TIMESTAMP('2021-07-30 15:52:43','YYYY-MM-DD HH24:MI:SS'),100,540000,540002,'Y',TO_TIMESTAMP('2021-07-30 15:52:43','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2021-08-02T08:12:56.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ES_FTS_Config SET ES_DocumentToIndexTemplate='{ "value": @C_BPartner.Value@, "name": @C_BPartner.Name@, "city": @C_Location.City@, "postal": @C_Location.Postal@, "address1": @C_Location.Address1@   }',Updated=TO_TIMESTAMP('2021-08-02 11:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540000
;

