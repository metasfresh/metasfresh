-- 2021-08-02T10:04:58.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ES_FTS_Config SET ES_QueryCommand='{
  "query": {
    "multi_match": {
      "query": @query@,
      "type": "bool_prefix",
      "fields": [
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
        "address1._index_prefix"
      ]
    }
  },
  "highlight" : {
    "fields" :
    {
      "value": {
        "number_of_fragments": 1,
        "no_match_size": 100
      },
      "name": {
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
',Updated=TO_TIMESTAMP('2021-08-02 13:04:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540000
;

