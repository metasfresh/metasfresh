-- 2021-08-04T16:22:55.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ES_FTS_Config SET ES_CreateIndexCommand='{
    "settings":
    {
        "analysis":
        {
            "analyzer":
            {
                "partial_words":
                {
                    "type": "custom",
                    "tokenizer": "ngrams",
                    "filter":
                    [
                        "lowercase"
                    ]
                }
            },
            "tokenizer":
            {
                "ngrams":
                {
                    "type": "ngram",
                    "min_gram": 3,
                    "max_gram": 4
                }
            }
        }
    },
    "mappings":
    {
        "properties":
        {
            "c_bpartner_id":
            {
                "type": "keyword"
            },
            "c_bpartner_location_id":
            {
                "type": "keyword"
            },
            "ad_user_id":
            {
                "type": "keyword"
            },
            "value":
            {
                "type": "text",
                "fields":
                {
                    "shingles":
                    {
                        "type": "search_as_you_type"
                    },
                    "ngrams":
                    {
                        "type": "text",
                        "analyzer": "partial_words",
                        "search_analyzer": "standard",
                        "term_vector": "with_positions_offsets"
                    }
                }
            },
            "name":
            {
                "type": "text",
                "fields":
                {
                    "shingles":
                    {
                        "type": "search_as_you_type"
                    },
                    "ngrams":
                    {
                        "type": "text",
                        "analyzer": "partial_words",
                        "search_analyzer": "standard",
                        "term_vector": "with_positions_offsets"
                    }
                }
            },
            "firstname":
            {
                "type": "text",
                "fields":
                {
                    "shingles":
                    {
                        "type": "search_as_you_type"
                    },
                    "ngrams":
                    {
                        "type": "text",
                        "analyzer": "partial_words",
                        "search_analyzer": "standard",
                        "term_vector": "with_positions_offsets"
                    }
                }
            },
            "lastname":
            {
                "type": "text",
                "fields":
                {
                    "shingles":
                    {
                        "type": "search_as_you_type"
                    },
                    "ngrams":
                    {
                        "type": "text",
                        "analyzer": "partial_words",
                        "search_analyzer": "standard",
                        "term_vector": "with_positions_offsets"
                    }
                }
            },
            "city":
            {
                "type": "text",
                "fields":
                {
                    "shingles":
                    {
                        "type": "search_as_you_type"
                    },
                    "ngrams":
                    {
                        "type": "text",
                        "analyzer": "partial_words",
                        "search_analyzer": "standard",
                        "term_vector": "with_positions_offsets"
                    }
                }
            },
            "postal":
            {
                "type": "text",
                "fields":
                {
                    "shingles":
                    {
                        "type": "search_as_you_type"
                    },
                    "ngrams":
                    {
                        "type": "text",
                        "analyzer": "partial_words",
                        "search_analyzer": "standard",
                        "term_vector": "with_positions_offsets"
                    }
                }
            },
            "address1":
            {
                "type": "text",
                "fields":
                {
                    "shingles":
                    {
                        "type": "search_as_you_type"
                    },
                    "ngrams":
                    {
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
', ES_DocumentToIndexTemplate='{ 
"c_bpartner_id": @C_BPartner.C_BPartner_ID@, 
"c_bpartner_location_id": @C_BPartner_Location.C_BPartner_Location_ID@, 
"ad_user_id": @AD_User.AD_User_ID@, 
"value": @C_BPartner.Value@, 
"name": @C_BPartner.Name@, 
"city": @C_Location.City@, 
"postal": @C_Location.Postal@, 
"address1": @C_Location.Address1@,
"firstname": @AD_User.Firstname@,
"lastname": @AD_User.Lastname@
}
', ES_QueryCommand='{
    "query":
    {
        "multi_match":
        {
            "query": @query@,
            "type": "bool_prefix",
            "fields":
            [
                "value",
                "value._2gram",
                "value._3gram",
                "value._index_prefix",
                "name",
                "name._2gram",
                "name._3gram",
                "name._index_prefix",
                "city",
                "city._2gram",
                "city._3gram",
                "city._index_prefix",
                "postal",
                "postal._2gram",
                "postal._3gram",
                "postal._index_prefix",
                "address1",
                "address1._2gram",
                "address1._3gram",
                "address1._index_prefix",
                "firstname",
                "firstname._2gram",
                "firstname._3gram",
                "firstname._index_prefix",
                "lastname",
                "lastname._2gram",
                "lastname._3gram",
                "lastname._index_prefix"
            ]
        }
    },
    "highlight":
    {
        "fields":
        {
            "value":
            {
                "number_of_fragments": 1,
                "no_match_size": 100
            },
            "name":
            {
                "number_of_fragments": 1,
                "no_match_size": 100
            },
            "city":
            {
                "number_of_fragments": 1,
                "no_match_size": 100
            },
            "postal":
            {
                "number_of_fragments": 1,
                "no_match_size": 100
            },
            "address1":
            {
                "number_of_fragments": 1,
                "no_match_size": 100
            },
            "firstname":
            {
                "number_of_fragments": 1,
                "no_match_size": 100
            },
            "lastname":
            {
                "number_of_fragments": 1,
                "no_match_size": 100
            }
        }
    }
}
',Updated=TO_TIMESTAMP('2021-08-04 19:22:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540000
;

