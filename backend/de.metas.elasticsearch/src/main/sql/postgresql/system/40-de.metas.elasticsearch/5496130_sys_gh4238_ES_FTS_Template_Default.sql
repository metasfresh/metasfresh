-- 2018-06-19T15:50:46.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Template (ES_FTS_Template_ID,ES_FTS_Settings,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,Name) VALUES (540000,'{
	"number_of_shards": 1,
	"analysis" : {
            "filter": {
                "fts_ngram_filter": {
                    "type": "ngram",
                    "min_gram": 3,
                    "max_gram": 30
                }
            },
            "analyzer" : {
				"fts_analyzer" : {
					"type" : "custom",
					"tokenizer": "standard",
					"filter": [ "lowercase", "fts_ngram_filter" ]
				}
			}
	}
}
',0,0,TO_TIMESTAMP('2018-06-19 15:50:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-06-19 15:50:46','YYYY-MM-DD HH24:MI:SS'),100,'Default')
;

