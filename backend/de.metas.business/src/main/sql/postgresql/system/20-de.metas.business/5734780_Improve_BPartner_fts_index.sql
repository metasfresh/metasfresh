-- Run mode: SWING_CLIENT

-- 2024-09-25T13:12:32.944Z
UPDATE ES_FTS_Config SET ES_CreateIndexCommand='{
    "settings":
    {
        "analysis":
        {
            "filter":
            {
                "german_stop":
                {
                    "type": "stop",
                    "stopwords": "_german_"
                }
            },
            "analyzer":
            {
                "standard_analyzer":
                {
                    "type": "custom",
                    "tokenizer": "standard",
                    "filter":
                    [
                        "lowercase",
                        "german_stop",
                        "german_normalization"
                    ]
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
            "ad_org_id":
            {
                "type": "keyword"
            },
            "value":
            {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "name":
            {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "firstname":
            {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "lastname":
            {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "city":
            {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "postal":
            {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "address1":
            {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            }
        }
    }
}',Updated=TO_TIMESTAMP('2024-09-25 16:12:32.944','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540000
;

-- 2024-09-25T13:13:29.879Z
UPDATE ES_FTS_Config SET ES_DocumentToIndexTemplate='{ "c_bpartner_id": @C_BPartner_ID@, "c_bpartner_location_id": @C_BPartner_Location_ID@, "ad_user_id": @C_BP_Contact_ID@, "value": @Value@, "name": @Name@, "city": @City@, "postal": @Postal@, "address1": @Address1@, "firstname": @Firstname@, "lastname": @Lastname@, "ad_org_id": @AD_Org_ID@ }
', ES_QueryCommand='{
  "_source": true,
  "query": {
    "bool": {
      "should": [
        {
          "wildcard": {
            "firstname": {
              "value": @queryStartsWith@
            }
          }
        },
        {
          "wildcard": {
            "lastname": {
              "value": @queryStartsWith@
            }
          }
        },
        {
          "wildcard": {
            "address1": {
              "value": @queryStartsWith@
            }
          }
        },
        {
          "multi_match": {
            "query": @query@,
            "type": "cross_fields",
            "fields": [
              "value",
              "name",
              "firstname",
              "lastname",
              "city",
              "postal",
              "address1"
            ],
            "operator": "and"
          }
        }
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
',Updated=TO_TIMESTAMP('2024-09-25 16:13:29.879','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540000
;

-- 2024-09-25T13:13:30.049Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-25 16:13:29.907','YYYY-MM-DD HH24:MI:SS.US'),100,'ad_org_id',540059,540000,'Y',TO_TIMESTAMP('2024-09-25 16:13:29.907','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-25T13:13:30.120Z
DELETE FROM ES_FTS_Config_Field WHERE ES_FTS_Config_Field_ID=540012
;



