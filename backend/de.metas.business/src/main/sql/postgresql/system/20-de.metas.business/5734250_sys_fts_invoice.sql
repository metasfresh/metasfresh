

-- 2024-09-20T14:41:28.847Z
INSERT INTO ES_FTS_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_CreateIndexCommand,ES_DocumentToIndexTemplate,ES_FTS_Config_ID,ES_Index,ES_QueryCommand,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:28.708','YYYY-MM-DD HH24:MI:SS.US'),100,'{
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
            "german_normalization",
            "asciifolding"
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
      }
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
','{
  "c_invoice_id" : @C_Invoice_ID@,
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
  "descriptionbottom": @DescriptionBottom@
}',540003,'fts_invoice','{
  "_source": true,
  "query": {
    "multi_match": {
      "query": @query@,
      "type": "bool_prefix",
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
      ]
    }
  }
}','Y',TO_TIMESTAMP('2024-09-20 17:41:28.708','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:29.008Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:28.886','YYYY-MM-DD HH24:MI:SS.US'),100,'c_invoice_id',540033,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:28.886','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:29.116Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:29.025','YYYY-MM-DD HH24:MI:SS.US'),100,'c_year_id',540034,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:29.025','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:29.227Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:29.133','YYYY-MM-DD HH24:MI:SS.US'),100,'calendarname',540035,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:29.133','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:29.336Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:29.242','YYYY-MM-DD HH24:MI:SS.US'),100,'description',540036,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:29.242','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:29.433Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:29.351','YYYY-MM-DD HH24:MI:SS.US'),100,'fiscalyear',540037,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:29.351','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:29.540Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:29.448','YYYY-MM-DD HH24:MI:SS.US'),100,'city',540038,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:29.448','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:29.648Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:29.567','YYYY-MM-DD HH24:MI:SS.US'),100,'bpartnervalue',540039,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:29.567','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:29.750Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:29.665','YYYY-MM-DD HH24:MI:SS.US'),100,'externalid',540040,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:29.665','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:29.846Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:29.766','YYYY-MM-DD HH24:MI:SS.US'),100,'firstname',540041,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:29.766','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:29.952Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:29.862','YYYY-MM-DD HH24:MI:SS.US'),100,'m_warehouse_id',540042,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:29.862','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:30.069Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:29.97','YYYY-MM-DD HH24:MI:SS.US'),100,'lastname',540043,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:29.97','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:30.169Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:30.086','YYYY-MM-DD HH24:MI:SS.US'),100,'c_bpartner_location_id',540044,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:30.086','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:30.269Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:30.186','YYYY-MM-DD HH24:MI:SS.US'),100,'descriptionbottom',540045,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:30.186','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:30.370Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:30.286','YYYY-MM-DD HH24:MI:SS.US'),100,'doctypename',540046,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:30.286','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:30.466Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:30.389','YYYY-MM-DD HH24:MI:SS.US'),100,'address1',540047,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:30.389','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:30.560Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:30.484','YYYY-MM-DD HH24:MI:SS.US'),100,'poreference',540048,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:30.484','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:30.658Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:30.578','YYYY-MM-DD HH24:MI:SS.US'),100,'c_calendar_id',540049,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:30.578','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:30.775Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:30.677','YYYY-MM-DD HH24:MI:SS.US'),100,'_id',540050,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:30.677','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:30.882Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:30.794','YYYY-MM-DD HH24:MI:SS.US'),100,'c_bpartner_id',540051,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:30.794','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:30.993Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:30.901','YYYY-MM-DD HH24:MI:SS.US'),100,'postal',540052,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:30.901','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:31.104Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:31.017','YYYY-MM-DD HH24:MI:SS.US'),100,'ad_user_id',540053,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:31.017','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:31.213Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:31.124','YYYY-MM-DD HH24:MI:SS.US'),100,'c_doctype_id',540054,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:31.124','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:31.326Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:31.234','YYYY-MM-DD HH24:MI:SS.US'),100,'warehousename',540055,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:31.234','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:31.429Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:31.347','YYYY-MM-DD HH24:MI:SS.US'),100,'documentno',540056,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:31.347','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:31.534Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:31.45','YYYY-MM-DD HH24:MI:SS.US'),100,'companyname',540057,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:31.45','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T14:41:31.645Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2024-09-20 17:41:31.556','YYYY-MM-DD HH24:MI:SS.US'),100,'bpName',540058,540003,'Y',TO_TIMESTAMP('2024-09-20 17:41:31.556','YYYY-MM-DD HH24:MI:SS.US'),100)
;







-- 2024-09-23T12:15:26.080Z
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
            "german_normalization",
            "asciifolding"
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
}',Updated=TO_TIMESTAMP('2024-09-23 15:15:26.08','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540003
;













-- Run mode: WEBUI

-- 2024-09-23T12:24:58.547Z
INSERT INTO ES_FTS_Config_SourceModel (AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,ES_FTS_Config_ID,ES_FTS_Config_SourceModel_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,318,TO_TIMESTAMP('2024-09-23 15:24:58.508','YYYY-MM-DD HH24:MI:SS.US'),100,540003,540009,'Y',TO_TIMESTAMP('2024-09-23 15:24:58.508','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-23T12:25:18.355Z
INSERT INTO ES_FTS_Config_SourceModel (AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,ES_FTS_Config_ID,ES_FTS_Config_SourceModel_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,291,TO_TIMESTAMP('2024-09-23 15:25:18.319','YYYY-MM-DD HH24:MI:SS.US'),100,540003,540010,'Y',TO_TIMESTAMP('2024-09-23 15:25:18.319','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-23T12:25:26.196Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=2893,Updated=TO_TIMESTAMP('2024-09-23 15:25:26.196','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540010
;

-- 2024-09-23T12:25:44.596Z
INSERT INTO ES_FTS_Config_SourceModel (AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,ES_FTS_Config_ID,ES_FTS_Config_SourceModel_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,293,TO_TIMESTAMP('2024-09-23 15:25:44.558','YYYY-MM-DD HH24:MI:SS.US'),100,540003,540011,'Y',TO_TIMESTAMP('2024-09-23 15:25:44.558','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-23T12:25:48.367Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=3434,Updated=TO_TIMESTAMP('2024-09-23 15:25:48.367','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540011
;

-- 2024-09-23T12:26:04.814Z
INSERT INTO ES_FTS_Config_SourceModel (AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,ES_FTS_Config_ID,ES_FTS_Config_SourceModel_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,114,TO_TIMESTAMP('2024-09-23 15:26:04.774','YYYY-MM-DD HH24:MI:SS.US'),100,540003,540012,'Y',TO_TIMESTAMP('2024-09-23 15:26:04.774','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-23T12:26:10.617Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=212,Updated=TO_TIMESTAMP('2024-09-23 15:26:10.617','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540012
;

-- 2024-09-23T12:26:21.130Z
INSERT INTO ES_FTS_Config_SourceModel (AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,ES_FTS_Config_ID,ES_FTS_Config_SourceModel_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,217,TO_TIMESTAMP('2024-09-23 15:26:21.084','YYYY-MM-DD HH24:MI:SS.US'),100,540003,540013,'Y',TO_TIMESTAMP('2024-09-23 15:26:21.084','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-23T12:27:22.001Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=1501,Updated=TO_TIMESTAMP('2024-09-23 15:27:22.001','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540013
;

-- 2024-09-23T12:27:31.952Z
INSERT INTO ES_FTS_Config_SourceModel (AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,ES_FTS_Config_ID,ES_FTS_Config_SourceModel_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,190,TO_TIMESTAMP('2024-09-23 15:27:31.906','YYYY-MM-DD HH24:MI:SS.US'),100,540003,540014,'Y',TO_TIMESTAMP('2024-09-23 15:27:31.906','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-23T12:27:39.076Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 15:27:39.076','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540013
;

-- 2024-09-23T12:27:40.631Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 15:27:40.631','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540012
;

-- 2024-09-23T12:27:44.178Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 15:27:44.178','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540010
;

-- 2024-09-23T12:27:46.732Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 15:27:46.731','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540011
;

-- 2024-09-23T12:27:52.921Z
INSERT INTO ES_FTS_Config_SourceModel (AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,ES_FTS_Config_ID,ES_FTS_Config_SourceModel_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,139,TO_TIMESTAMP('2024-09-23 15:27:52.884','YYYY-MM-DD HH24:MI:SS.US'),100,540003,540015,'Y',TO_TIMESTAMP('2024-09-23 15:27:52.884','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-23T12:28:02.372Z
INSERT INTO ES_FTS_Config_SourceModel (AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,ES_FTS_Config_ID,ES_FTS_Config_SourceModel_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,177,TO_TIMESTAMP('2024-09-23 15:28:02.334','YYYY-MM-DD HH24:MI:SS.US'),100,540003,540016,'Y',TO_TIMESTAMP('2024-09-23 15:28:02.334','YYYY-MM-DD HH24:MI:SS.US'),100)
;













-- -- 2024-09-23T12:28:09.413Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 318, 1000001)
-- ;

-- -- 2024-09-23T12:28:09.414Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 318, 1000000)
-- ;

-- -- 2024-09-23T12:28:09.443Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 190, 540005)
-- ;

-- -- 2024-09-23T12:28:09.443Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 190, 540012)
-- ;

-- -- 2024-09-23T12:28:09.443Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 190, 540008)
-- ;

-- -- 2024-09-23T12:28:09.443Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 190, 1000109)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 0)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000043)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000039)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000041)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000042)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000000)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000004)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000007)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000017)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000018)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000022)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000024)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000025)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000034)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000035)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000010)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000019)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000029)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000030)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000003)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540899)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540843)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540844)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540845)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540900)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000014)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000044)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000021)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540942)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000016)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000005)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540937)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540902)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540943)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540901)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000027)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540945)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540946)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000002)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540940)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000008)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000009)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000038)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540938)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000036)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000037)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540941)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000013)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540965)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000028)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540962)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540958)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540957)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540961)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540960)
-- ;

-- -- 2024-09-23T12:28:09.524Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540964)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540963)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540948)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541005)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540973)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540972)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540970)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540967)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000020)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540974)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540975)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541007)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541009)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541008)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540944)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541010)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000012)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000011)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000015)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541019)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541017)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541018)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000040)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541034)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 540971)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000023)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541024)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541047)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541025)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541027)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541026)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541080)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541092)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541091)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541100)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541061)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541059)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541085)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541062)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541057)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541104)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 1000006)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541122)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541123)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541109)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541107)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541121)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541112)
-- ;

-- -- 2024-09-23T12:28:09.525Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541124)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 99)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 0)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 2188002)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 540011)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 540010)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 540012)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 150)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 160)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 540057)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 2188223)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 540179)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 1000000)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 100)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 2188225)
-- ;

-- -- 2024-09-23T12:28:09.546Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 114, 2188224)
-- ;

-- -- 2024-09-23T12:28:09.576Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 291, 2156425)
-- ;

-- -- 2024-09-23T12:28:09.577Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 291, 2156423)
-- ;

-- -- 2024-09-23T12:28:09.577Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 291, 2155894)
-- ;

-- -- 2024-09-23T12:28:09.577Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 291, 2156426)
-- ;

-- -- 2024-09-23T12:28:09.596Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 293, 2205175)
-- ;

-- -- 2024-09-23T12:28:09.596Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 293, 2202690)
-- ;

-- -- 2024-09-23T12:28:09.596Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 293, 2205173)
-- ;

-- -- 2024-09-23T12:28:09.596Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 293, 2205176)
-- ;

-- -- 2024-09-23T12:28:09.655Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 139, 1000001)
-- ;

-- -- 2024-09-23T12:28:09.655Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 139, 1000000)
-- ;

-- -- 2024-09-23T12:28:09.677Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 177, 1000001)
-- ;

-- -- 2024-09-23T12:28:09.677Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 177, 1000003)
-- ;

-- -- 2024-09-23T12:28:09.677Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 177, 1000008)
-- ;

-- -- 2024-09-23T12:28:09.677Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 177, 540005)
-- ;

-- -- 2024-09-23T12:28:09.677Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 177, 540006)
-- ;

-- -- 2024-09-23T12:28:09.677Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 177, 540014)
-- ;

-- -- 2024-09-23T12:28:09.677Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 177, 540015)
-- ;

-- -- 2024-09-23T12:28:09.677Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 177, 540016)
-- ;

-- -- 2024-09-23T12:28:09.677Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 177, 540017)
-- ;

-- -- 2024-09-23T12:28:09.677Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 177, 540018)
-- ;

-- -- 2024-09-23T12:28:09.677Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 177, 540029)
-- ;

-- -- 2024-09-23T12:28:09.677Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 177, 540030)
-- ;

-- -- 2024-09-23T12:28:09.677Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 177, 540031)
-- ;

-- 2024-09-23T12:29:28.151Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=8768,Updated=TO_TIMESTAMP('2024-09-23 15:29:28.151','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540010
;

-- 2024-09-23T12:29:35.756Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=2893,Updated=TO_TIMESTAMP('2024-09-23 15:29:35.756','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540010
;

-- 2024-09-23T12:29:39.583Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=3434,Updated=TO_TIMESTAMP('2024-09-23 15:29:39.583','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540011
;

-- 2024-09-23T12:29:42.999Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=212,Updated=TO_TIMESTAMP('2024-09-23 15:29:42.999','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540012
;

-- 2024-09-23T12:29:46.330Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=1501,Updated=TO_TIMESTAMP('2024-09-23 15:29:46.33','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540013
;

-- 2024-09-23T12:29:55.486Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=1151,Updated=TO_TIMESTAMP('2024-09-23 15:29:55.486','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540014
;

-- 2024-09-23T12:29:58.967Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=445,Updated=TO_TIMESTAMP('2024-09-23 15:29:58.967','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540015
;

-- 2024-09-23T12:30:03.301Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=1031,Updated=TO_TIMESTAMP('2024-09-23 15:30:03.301','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540016
;

-- -- 2024-09-23T12:30:31.367Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 318, 1000001)
-- ;

-- -- 2024-09-23T12:30:31.367Z
-- INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 318, 1000000)
-- ;

-- -- 2024-09-23T12:30:48.223Z
-- INSERT INTO T_ES_FTS_Search_Result (Search_UUID,Line,JSON, IntKey1, IntKey2, IntKey3, IntKey4, IntKey5, IntKey6, IntKey7, IntKey8, IntKey9, IntKey10, IntKey11, IntKey12, IntKey13, IntKey14, IntKey15, IntKey16, IntKey17, IntKey18, IntKey19, IntKey20, StringKey1, StringKey2, StringKey3, StringKey4, StringKey5, StringKey6, StringKey7, StringKey8, StringKey9, StringKey10) VALUES ('test-search-247ea72f-f397-43ee-918c-d27c1e28f9b6', 1, '{
  -- "_index" : "fts_invoice",
  -- "_type" : "_doc",
  -- "_id" : "1000000-2156426-2205176-2188224-10000041000109-1000000-X",
  -- "_score" : 1.0,
  -- "_source" : {
    -- "c_invoice_id" : 1000000,
    -- "c_bpartner_id" : 2156426,
    -- "c_bpartner_location_id" : 2205176,
    -- "ad_user_id" : 2188224,
    -- "c_doctype_id" : 1000004,
    -- "m_warehouse_id" : 1000109,
    -- "c_calendar_id" : 1000000,
    -- "c_year_id" : -1,
    -- "documentno" : "170241",
    -- "poreference" : "123456",
    -- "bpartnervalue" : "1000000",
    -- "externalid" : null,
    -- "bpName" : "maria",
    -- "firstname" : "george",
    -- "lastname" : "mimimimi",
    -- "companyname" : null,
    -- "address1" : "streetstreet",
    -- "city" : null,
    -- "postal" : null,
    -- "doctypename" : "Gutschrift",
    -- "warehousename" : "Materialentnahmelager",
    -- "calendarname" : "Ernte und Geschäftsjahr Kalender",
    -- "fiscalyear" : null,
    -- "description" : null,
    -- "descriptionbottom" : null
  -- }
-- }', 1000000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)
-- ;
















-- 2024-09-23T16:21:22.135Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 19:21:22.135','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540010
;

-- 2024-09-23T16:21:24.984Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 19:21:24.984','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540011
;

-- 2024-09-23T16:21:27.524Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 19:21:27.524','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540012
;

-- 2024-09-23T16:21:30.670Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 19:21:30.67','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540013
;

-- 2024-09-23T16:21:34.196Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 19:21:34.196','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540014
;

-- 2024-09-23T16:21:36.294Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 19:21:36.294','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540015
;

-- 2024-09-23T16:21:39.415Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 19:21:39.415','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540016
;

-- 2024-09-23T17:13:30.979Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 20:13:30.979','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540010
;

-- 2024-09-23T17:13:42.605Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 20:13:42.605','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540011
;

-- 2024-09-23T17:13:48.599Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 20:13:48.599','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540012
;

-- 2024-09-23T17:13:54.311Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=2893,Updated=TO_TIMESTAMP('2024-09-23 20:13:54.311','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540010
;

-- 2024-09-23T17:14:02.251Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=2958,Updated=TO_TIMESTAMP('2024-09-23 20:14:02.251','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540011
;

-- 2024-09-23T17:14:07.500Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=5844,Updated=TO_TIMESTAMP('2024-09-23 20:14:07.5','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540012
;

-- 2024-09-23T17:14:09.817Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=3914,Updated=TO_TIMESTAMP('2024-09-23 20:14:09.817','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540013
;

-- 2024-09-23T17:14:13.113Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 20:14:13.113','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540013
;

-- 2024-09-23T17:14:15.238Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=1248,Updated=TO_TIMESTAMP('2024-09-23 20:14:15.238','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540014
;

-- 2024-09-23T17:14:18.576Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 20:14:18.576','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540014
;

-- 2024-09-23T17:14:21.180Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=771,Updated=TO_TIMESTAMP('2024-09-23 20:14:21.18','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540015
;

-- 2024-09-23T17:14:25.345Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 20:14:25.345','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540015
;

-- 2024-09-23T17:14:31.412Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=1032,Updated=TO_TIMESTAMP('2024-09-23 20:14:31.412','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540016
;

-- 2024-09-23T17:14:35.611Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 20:14:35.611','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540016
;







-- 2024-09-23T18:12:00.925Z
UPDATE ES_FTS_Config_SourceModel SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2024-09-23 21:12:00.925','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_SourceModel_ID=540010
;

