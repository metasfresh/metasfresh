--
-- Script: /tmp/webui_migration_scripts_2025-11-05_1503290801917172136/10000050_migration_2025-11-06_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- 2025-11-06T07:56:58.027Z
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
            "c_invoice_id": {
                "type": "keyword"
            },
            "c_bpartner_id": {
                "type": "keyword"
            },
            "c_bpartner_location_id": {
                "type": "keyword"
            },
            "ad_user_id": {
                "type": "keyword"
            },
            "c_doctype_id": {
                "type": "keyword"
            },
            "m_warehouse_id": {
                "type": "keyword"
            },
            "c_calendar_id": {
                "type": "keyword"
            },
            "c_year_id": {
                "type": "keyword"
            },
            "documentno": {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "poreference": {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "bpartnervalue": {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "externalid": {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "bpname": {
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
            "companyname": {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "address1": {
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
            "doctypename": {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "warehousename": {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "calendarname": {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "fiscalyear": {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "description": {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "descriptionbottom": {
                "type": "text",
                "analyzer": "standard_analyzer",
                "term_vector": "with_positions_offsets"
            },
            "ispaid": {
                "type": "boolean"
            },
            "ispartiallypaid": {
                "type": "boolean"
            },
            "dateinvoiced": {
                "type": "date"
            }
        }
    }
}',Updated=TO_TIMESTAMP('2025-11-06 08:56:58.027','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540003
;

-- 2025-11-06T07:57:07.313Z
UPDATE ES_FTS_Config SET ES_DocumentToIndexTemplate='{
    "c_invoice_id": @C_Invoice_ID@,
    "c_bpartner_id": @C_BPartner_ID@,
    "c_bpartner_location_id": @C_BPartner_Location_ID@,
    "ad_user_id": @AD_User_ID@,
    "c_doctype_id": @C_DocType_ID@,
    "m_warehouse_id": @M_Warehouse_ID@,
    "c_calendar_id": @C_Calendar_ID@,
    "c_year_id": @C_Year_ID@,
    "documentno": @DocumentNo@,
    "poreference": @POReference@,
    "bpartnervalue": @BPartnerValue@,
    "externalid": @ExternalId@,
    "bpName": @BPName@,
    "firstname": @Firstname@,
    "lastname": @Lastname@,
    "companyname": @Companyname@,
    "address1": @Address1@,
    "city": @City@,
    "postal": @Postal@,
    "doctypename": @DocTypeName@,
    "warehousename": @WarehouseName@,
    "calendarname": @CalendarName@,
    "fiscalyear": @FiscalYear@,
    "description": @Description@,
    "descriptionbottom": @DescriptionBottom@,
    "ispaid": @IsPaid@,
    "ispartiallypaid":@isPartiallyPaid@,
    "dateinvoiced":@DateInvoiced@
}',Updated=TO_TIMESTAMP('2025-11-06 08:57:07.313','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540003
;

-- 2025-11-06T07:57:07.371Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2025-11-06 08:57:07.35','YYYY-MM-DD HH24:MI:SS.US'),100,'dateinvoiced',1000000,540003,'Y',TO_TIMESTAMP('2025-11-06 08:57:07.35','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-11-06T07:59:26.283Z
UPDATE ES_FTS_Config
SET ES_QueryCommand='{
    "_source": true,
    "query": {
        "bool": {
            "should": [
                { "wildcard": { "firstname": { "value": @queryStartsWith@ } } },
                { "wildcard": { "lastname": { "value": @queryStartsWith@ } } },
                { "wildcard": { "address1": { "value": @queryStartsWith@ } } },
                { "wildcard": { "externalid": { "value": @queryStartsWith@ } } },
                {
                    "multi_match": {
                        "query": @query@,
                        "type": "cross_fields",
                        "fields": [
                            "value","name","firstname","lastname",
                            "city","postal","address1","externalid","dateinvoiced"
                        ],
                        "operator": "and",
                        "lenient": true
                    }
                }
            ]
        }
    },"sort": [
        {
            "dateinvoiced": {
                "order": "asc",
                "missing": "_last"
            }
        }
    ],
    "highlight": {
        "fields": {
            "value": { "number_of_fragments": 1, "no_match_size": 100 },
            "name": { "number_of_fragments": 1, "no_match_size": 100 },
            "firstname": { "number_of_fragments": 1, "no_match_size": 100 },
            "lastname": { "number_of_fragments": 1, "no_match_size": 100 },
            "city": { "number_of_fragments": 1, "no_match_size": 100 },
            "postal": { "number_of_fragments": 1, "no_match_size": 100 },
            "address1": { "number_of_fragments": 1, "no_match_size": 100 },
            "externalid": { "number_of_fragments": 1, "no_match_size": 100 }
        }
    }
}',
    Updated=TO_TIMESTAMP('2025-11-06 08:59:26.283','YYYY-MM-DD HH24:MI:SS.US'),
    UpdatedBy=100
WHERE ES_FTS_Config_ID=540003;

-- 2025-11-10T10:13:48.020Z
UPDATE ES_FTS_Config SET Description='!! Columns used in this config need to be present in the table C_Invoice_Adv_Search and C_Invoice_Adv_Search_V view !!',Updated=TO_TIMESTAMP('2025-11-10 11:13:48.02','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540003
;

-- 2025-11-10T10:14:23.729Z
UPDATE ES_FTS_Config SET Description='!! Columns used in this config need to be present in the table C_BPartner_Adv_Search and C_BPartner_Adv_Search_V view !!',Updated=TO_TIMESTAMP('2025-11-10 11:14:23.729','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540000
;

-- 2025-11-10T10:17:49.945Z
UPDATE ES_FTS_Config SET Description='!! Columns used in this config need to be present in the table M_Product !!',Updated=TO_TIMESTAMP('2025-11-10 11:17:49.945','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540001
;
