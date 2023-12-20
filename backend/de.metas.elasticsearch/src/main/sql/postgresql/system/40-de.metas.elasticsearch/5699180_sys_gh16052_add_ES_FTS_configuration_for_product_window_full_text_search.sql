-- 2023-08-14T15:37:04.705218900Z
INSERT INTO ES_FTS_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_CreateIndexCommand,ES_DocumentToIndexTemplate,ES_FTS_Config_ID,ES_Index,ES_QueryCommand,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2023-08-14 18:37:04.705','YYYY-MM-DD HH24:MI:SS.US'),100,'{
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
      "m_product_id": {
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
      }
    }
  }
}','{ "m_product_id": @M_Product_ID@, "value": @Value@, "name": @Name@ }',540001,'fts_product','{
  "_source": true,
  "query": {
    "multi_match": {
      "query": @query@,
      "type": "bool_prefix",
      "fields": [
        "value",
        "name"
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
      }
    }
  }
}','Y',TO_TIMESTAMP('2023-08-14 18:37:04.705','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-14T15:37:04.851312700Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2023-08-14 18:37:04.705','YYYY-MM-DD HH24:MI:SS.US'),100,'value',540013,540001,'Y',TO_TIMESTAMP('2023-08-14 18:37:04.705','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-14T15:37:04.951734300Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2023-08-14 18:37:04.855','YYYY-MM-DD HH24:MI:SS.US'),100,'_id',540014,540001,'Y',TO_TIMESTAMP('2023-08-14 18:37:04.855','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-14T15:37:05.052070800Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2023-08-14 18:37:04.974','YYYY-MM-DD HH24:MI:SS.US'),100,'name',540015,540001,'Y',TO_TIMESTAMP('2023-08-14 18:37:04.974','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-14T15:37:05.174934300Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2023-08-14 18:37:05.085','YYYY-MM-DD HH24:MI:SS.US'),100,'m_product_id',540016,540001,'Y',TO_TIMESTAMP('2023-08-14 18:37:05.085','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-14T15:41:47.786215700Z
INSERT INTO ES_FTS_Config_SourceModel (AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,ES_FTS_Config_ID,ES_FTS_Config_SourceModel_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,208,TO_TIMESTAMP('2023-08-14 18:41:47.769','YYYY-MM-DD HH24:MI:SS.US'),100,540001,540004,'Y',TO_TIMESTAMP('2023-08-14 18:41:47.769','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-14T15:42:39.214719500Z
INSERT INTO ES_FTS_Filter (AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,ES_FTS_Config_ID,ES_FTS_Filter_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,208,TO_TIMESTAMP('2023-08-14 18:42:39.214','YYYY-MM-DD HH24:MI:SS.US'),100,540001,540002,'Y',TO_TIMESTAMP('2023-08-14 18:42:39.214','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-14T15:44:05.238378700Z
INSERT INTO ES_FTS_Filter_JoinColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,ES_FTS_Config_Field_ID,ES_FTS_Filter_ID,ES_FTS_Filter_JoinColumn_ID,IsActive,IsNullable,Updated,UpdatedBy) VALUES (0,1402,0,TO_TIMESTAMP('2023-08-14 18:44:05.235','YYYY-MM-DD HH24:MI:SS.US'),100,540016,540002,540005,'Y','N',TO_TIMESTAMP('2023-08-14 18:44:05.235','YYYY-MM-DD HH24:MI:SS.US'),100)
;

