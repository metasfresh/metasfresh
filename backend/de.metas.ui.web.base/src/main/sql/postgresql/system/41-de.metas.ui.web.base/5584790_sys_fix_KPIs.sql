-- 2021-04-05T22:36:35.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET ES_Query='{
  "size": 0,
  "aggs": {
    "agg": {
      "date_histogram": {
        "field": "DateOrdered",
        "calendar_interval": "day",
        "time_zone": "Europe/Berlin",
        "min_doc_count": 1,
        "extended_bounds": {
          "min": @FromMillis@,
          "max": @ToMillis@
        }
      }
    }
  },
  "query": {
    "filtered": {
      "query": {
        "query_string": {
          "query": "*",
          "analyze_wildcard": true
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
  }
}
',Updated=TO_TIMESTAMP('2021-04-06 01:36:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=1000000
;

-- 2021-04-05T22:37:53.589Z
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
        "calendar_interval": "day",
        "min_doc_count": 0,
        "extended_bounds": { "min": @FromMillis@, "max": @ToMillis@ }
      },
      "aggs": {
        "LineNetAmtSum": { "sum": { "field": "LineNetAmt" } }
      }
    }
  }
}',Updated=TO_TIMESTAMP('2021-04-06 01:37:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=1000009
;

