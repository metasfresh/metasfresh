-- 2021-03-27T13:41:54.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET ES_Query='{
  "size" : 0,
  "query": {
    "bool": {
      "filter": {
        "bool": {
          "must": [
            { "match": { "C_Order_ID.IsSOTrx" : false } },
            { "range" : { "DateOrdered": { "gte": @FromMillis@, "lte": @ToMillis@ } } }
          ]
        }
      }
    }
  },
  "aggs": {
    "LineNetAmtSum": { "sum": { "field": "LineNetAmt" } }
  }
}
',Updated=TO_TIMESTAMP('2021-03-27 15:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=1000006
;

-- 2021-03-28T23:03:00.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET ES_Query='{
  "size" : 0,
  "query": {
    "bool": {
      "filter": {
        "bool": {
          "must": [
            { "match": { "C_Order_ID.IsSOTrx" : true } },
            { "range" : { "DateOrdered": { "gte": @FromMillis@, "lte": @ToMillis@ } } }
          ]
        }
      }
    }
  },
  "aggs": {
    "LineNetAmtSum": { "sum": { "field": "LineNetAmt" } }
  }
}
',Updated=TO_TIMESTAMP('2021-03-29 02:03:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=1000007
;

-- 2021-03-28T23:04:14.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET ES_Query='{
  "size": 0,
  "query": {
    "bool": {
      "filter": {
        "bool": {
          "must": [
            { "match": { "C_Order_ID.IsSOTrx" : true } },
            { "range": { "DateOrdered": { "gte": @FromMillis@, "lte": @ToMillis@ } } }
          ]
        }
      }
    }
  },
  "aggs": {
    "RevenuePerDay": {
      "date_histogram": {
        "field": "DateOrdered",
        "interval": "1d",
        "min_doc_count": 0,
        "extended_bounds": { "min": @FromMillis@, "max": @ToMillis@ }
      },
      "aggs": {
        "LineNetAmtSum": { "sum": { "field": "LineNetAmt" } }
      }
    }
  }
}',Updated=TO_TIMESTAMP('2021-03-29 02:04:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=1000009
;

-- 2021-03-28T23:06:14.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET ES_Query='{
  "query": {
    "bool": {
      "query": {
        "query_string": {
          "analyze_wildcard": true,
          "query": "*"
        }
      },
      "filter": {
        "bool": {
          "must": [
            {
              "range": {
                "DateOrdered": {
                  "gte": @FromMillis@,
                  "lte": @ToMillis@,
                  "format": "epoch_millis"
                }
              }
            }
          ],
          "must_not": []
        }
      }
    }
  },
  "size": 0,
  "aggs": {
    "agg": {
      "sum": {
        "field": "LineNetAmt"
      }
    }
  }
}',Updated=TO_TIMESTAMP('2021-03-29 02:06:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=1000002
;

-- 2021-03-28T23:06:23.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET ES_Query='{
  "size": 0,
  "query": {
    "bool": {
      "filter": {
        "bool": {
          "must": [
            { "match": { "C_Order_ID.IsSOTrx" : true } },
            { "range": { "DateOrdered": { "gte": @FromMillis@, "lte": @ToMillis@ } } }
          ]
        }
      }
    }
  },
  "aggs": {
    "Businesspartner Group Revenue": {
      "terms": {
        "field": "C_BPartner_ID.C_BP_Group_ID.Name",
        "size": 10,
        "order": {
          "LineNetAmtSum": "desc"
        }
      }
      , "aggs": {
        "LineNetAmtSum": {
          "sum": {
            "field": "LineNetAmt"
          }
        }
      }
    }
  }
}',Updated=TO_TIMESTAMP('2021-03-29 02:06:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=1000004
;

-- 2021-03-28T23:06:31.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET ES_Query='{
  "size": 0,
  "query": {
    "bool": {
      "filter": {
        "bool": {
          "must": [
            { "match": { "C_Order_ID.IsSOTrx" : true } },
            { "range": { "DateOrdered": { "gte": @FromMillis@, "lte": @ToMillis@ } } }
          ],
          "must_not": []
        }
      }
    }
  },
  "aggs": {
    "TopProducts": {
      "terms": {
        "field": "M_Product_ID.Name",
        "size": 5,
        "order": {
          "LineNetAmtSum": "desc"
        }
      },
      "aggs": {
        "LineNetAmtSum": {
          "sum": {
            "field": "LineNetAmt"
          }
        }
      }
    }
  }
}',Updated=TO_TIMESTAMP('2021-03-29 02:06:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=1000005
;

-- 2021-03-28T23:06:49.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET ES_Query='{
  "size": 0,
  "query": {
    "bool": {
      "filter": {
        "bool": {
          "must": [
            { "match": { "C_Order_ID.IsSOTrx" : true } },
            { "range": { "DateOrdered": { "gte": @FromMillis@, "lte": @ToMillis@ } } }
          ]
        }
      }
    }
  },
  "aggs": {
    "TopProducts": {
      "terms": {
        "field": "M_Product_ID.M_Product_Category_ID.Name",
        "size": 5,
        "order": {
          "LineNetAmtSum": "desc"
        }
      },
      "aggs": {
        "LineNetAmtSum": {
          "sum": {
            "field": "LineNetAmt"
          }
        }
      }
    }
  }
}',Updated=TO_TIMESTAMP('2021-03-29 02:06:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=1000008
;

