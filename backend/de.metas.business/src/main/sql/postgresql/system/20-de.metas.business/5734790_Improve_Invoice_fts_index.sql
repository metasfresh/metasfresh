-- 2024-09-25T13:38:50.094Z
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
      }
    }
  }
}', ES_DocumentToIndexTemplate='{   "c_invoice_id" : @C_Invoice_ID@,   "c_bpartner_id": @C_BPartner_ID@,   "c_bpartner_location_id": @C_BPartner_Location_ID@,   "ad_user_id": @AD_User_ID@,   "c_doctype_id": @C_DocType_ID@,   "m_warehouse_id": @M_Warehouse_ID@,   "c_calendar_id": @C_Calendar_ID@,   "c_year_id": @C_Year_ID@,   "documentno": @DocumentNo@,   "poreference": @POReference@,   "bpartnervalue": @BPartnerValue@,   "externalid": @ExternalId@,   "bpName": @BPName@,   "firstname": @Firstname@,   "lastname": @Lastname@,   "companyname": @Companyname@,   "address1": @Address1@,   "city": @City@,   "postal": @Postal@,   "doctypename": @DocTypeName@,   "warehousename": @WarehouseName@,   "calendarname": @CalendarName@,   "fiscalyear": @FiscalYear@,   "description": @Description@,   "descriptionbottom": @DescriptionBottom@ }',Updated=TO_TIMESTAMP('2024-09-25 16:38:50.094','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540003
;



-- 2024-09-25T13:57:58.281Z
UPDATE ES_FTS_Config SET ES_QueryCommand='{
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
            "bpname": {
              "value": @queryStartsWith@
            }
          }
        },
        {
          "wildcard": {
            "companyname": {
              "value": @queryStartsWith@
            }
          }
        },
        {
          "wildcard": {
            "doctypename": {
              "value": @queryStartsWith@
            }
          }
        },
        {
          "wildcard": {
            "calendarname": {
              "value": @queryStartsWith@
            }
          }
        },
        {
          "wildcard": {
            "warehousename": {
              "value": @queryStartsWith@
            }
          }
        },
        {
          "wildcard": {
            "postal": {
              "value": @queryStartsWith@
            }
          }
        },
        {
          "wildcard": {
            "city": {
              "value": @queryStartsWith@
            }
          }
        },
        {
          "wildcard": {
            "fiscalyear": {
              "value": @queryStartsWith@
            }
          }
        },
        {
          "wildcard": {
            "documentno": {
              "value": @queryStartsWith@
            }
          }
        },
        {
          "wildcard": {
            "poreference": {
              "value": @queryStartsWith@
            }
          }
        },
        {
          "multi_match": {
            "query": @query@,
            "type": "cross_fields",
            "fields": [
              "documentno",
              "poreference",
              "bpartnervalue",
              "externalid",
              "bpname",
              "firstname",
              "lastname",
              "companyname",
              "address1",
              "city",
              "postal",
              "doctypename",
              "warehousename",
              "calendarname",
              "fiscalyear",
              "description",
              "descriptionbottom"
            ],
            "operator": "and"
          }
        }
      ]
    }
  }
}',Updated=TO_TIMESTAMP('2024-09-25 16:57:58.281','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540003
;
