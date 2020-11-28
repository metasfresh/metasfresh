--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.5
-- Dumped by pg_dump version 9.5.5

-- Started on 2017-04-05 20:59:06 CEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- TOC entry 10266 (class 0 OID 86827)
-- Dependencies: 2615
-- Data for Name: webui_dashboard; Type: TABLE DATA; Schema: public; Owner: metasfresh
--

-- DELETE old MVP Dashboard
delete from webui_dashboarditem where webui_dashboard_id = 540000;
delete from webui_dashboard where webui_dashboard_id = 540000;

-- Insert New KPIs using the old ID 540000
INSERT INTO webui_dashboard VALUES (1000000, 1000000, '2017-02-25 19:50:58+01', 2188223, 'Y', 'Y', 'Elasticsearch KPIs', '2017-02-25 19:50:58+01', 2188223, 540000);

--
-- TOC entry 10268 (class 0 OID 127670)
-- Dependencies: 2699
-- Data for Name: webui_kpi; Type: TABLE DATA; Schema: public; Owner: metasfresh
--

INSERT INTO webui_kpi VALUES (1000000, 1000000, 'M', '2017-02-25 20:03:34+01', 2188223, NULL, 'orders', '{
  "query": {
    "filtered": {
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
}', NULL, 'C_OrderLine', 'Y', 'Orders net amount', '2017-02-27 14:55:23+01', 100, 1000002, 10, 'N', NULL, NULL);
INSERT INTO webui_kpi VALUES (1000000, 1000000, 'BC', '2017-02-25 19:41:00+01', 2188223, NULL, 'orders', '{
  "size": 0,
  "aggs": {
    "agg": {
      "date_histogram": {
        "field": "DateOrdered",
        "interval": "1d",
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
', 'P-7D', 'C_OrderLine', 'Y', 'Order lines / day', '2017-03-10 10:24:00+01', 2188223, 1000000, 10, 'Y', 'P-7D', NULL);
INSERT INTO webui_kpi VALUES (1000000, 1000000, 'M', '2017-03-10 14:20:43+01', 2188223, NULL, 'orders', '{
  "size" : 0,
  "query": {
    "filtered": {
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
', 'P-7D', 'C_OrderLine', 'Y', 'Purchase order net amount', '2017-03-16 10:24:38+01', 100, 1000006, 10, 'N', NULL, NULL);
INSERT INTO webui_kpi VALUES (1000000, 1000000, 'AC', '2017-02-25 19:58:00+01', 2188223, 'wewwwwe', 'value', 'does not matererertrer now ;)', 'value', '', 'Y', 'test for issue 436', '2017-03-17 13:42:01+01', 100, 1000001, 10, 'N', NULL, 'tesereret value');
INSERT INTO webui_kpi VALUES (1000000, 1000000, 'PC', '2017-02-27 15:17:49+01', 100, '', 'orders', '{
  "size": 0,
  "query": {
    "filtered": {
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
}', NULL, 'C_OrderLine', 'Y', 'BP Group Revenue', '2017-03-14 14:03:54+01', 2188223, 1000004, 10, 'N', NULL, NULL);
INSERT INTO webui_kpi VALUES (1000000, 1000000, 'BC', '2017-03-10 13:20:56+01', 2188223, 'Top 5 product revenue in this week compared w/ their revenues last week', 'orders', '{
  "size": 0,
  "query": {
    "filtered": {
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
}', 'P-7D', 'C_OrderLine', 'Y', 'Top 5 product revenue in this week compared w/ their revenues last week', '2017-03-10 17:15:46+01', 2188223, 1000005, 10, 'Y', 'P-7D', NULL);
INSERT INTO webui_kpi VALUES (1000000, 1000000, 'M', '2017-03-10 15:33:34+01', 2188223, 'rtryurt', 'orders', '{
  "size" : 0,
  "query": {
    "filtered": {
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
', 'P-7D', 'C_OrderLine', 'Y', 'Sales order net amount', '2017-03-15 15:36:32+01', 100, 1000007, 10, 'N', NULL, NULL);
INSERT INTO webui_kpi VALUES (1000000, 1000000, 'BC', '2017-03-10 16:55:17+01', 2188223, NULL, 'orders', '{
  "size": 0,
  "query": {
    "filtered": {
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
}', 'P-7D', 'C_OrderLine', 'Y', 'Top 5 Product Category revenue in this week compared w/ their revenues last week', '2017-03-10 17:10:32+01', 2188223, 1000008, 10, 'Y', 'P-7D', NULL);
INSERT INTO webui_kpi VALUES (1000000, 1000000, 'BC', '2017-03-10 17:30:52+01', 2188223, NULL, 'orders', '{
  "size": 0,
  "query": {
    "filtered": {
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
}', 'P-7D', 'C_OrderLine', 'Y', 'Revenue per day ', '2017-03-10 18:22:43+01', 2188223, 1000009, 10, 'N', NULL, NULL);


--
-- TOC entry 10267 (class 0 OID 86835)
-- Dependencies: 2616
-- Data for Name: webui_dashboarditem; Type: TABLE DATA; Schema: public; Owner: metasfresh
--

INSERT INTO webui_dashboarditem VALUES (1000000, 1000000, '2017-03-10 13:26:45+01', 2188223, 'Y', 'Top 5 product revenue in this week', '2017-03-10 13:26:50+01', 2188223, NULL, 540000, 1000016, 30, 'K', 1000005, NULL, NULL);
INSERT INTO webui_dashboarditem VALUES (1000000, 1000000, '2017-03-10 14:29:12+01', 2188223, 'Y', 'Purchase net amt this week', '2017-03-10 14:29:38+01', 2188223, NULL, 540000, 1000017, 20, 'T', 1000006, NULL, NULL);
INSERT INTO webui_dashboarditem VALUES (1000000, 1000000, '2017-03-10 15:29:53+01', 2188223, 'Y', 'Purchase order net amt last week', '2017-03-10 15:31:44+01', 2188223, NULL, 540000, 1000018, 30, 'T', 1000006, NULL, 'P-7D');
INSERT INTO webui_dashboarditem VALUES (1000000, 1000000, '2017-03-10 15:35:09+01', 2188223, 'Y', 'Sales orders net amt this week', '2017-03-10 15:35:18+01', 2188223, NULL, 540000, 1000019, 40, 'T', 1000007, NULL, NULL);
INSERT INTO webui_dashboarditem VALUES (1000000, 1000000, '2017-03-10 15:35:47+01', 2188223, 'Y', 'Sales orders net amt last week', '2017-03-10 15:35:59+01', 2188223, NULL, 540000, 1000020, 50, 'T', 1000007, NULL, 'P-7D');
INSERT INTO webui_dashboarditem VALUES (1000000, 1000000, '2017-03-10 16:58:05+01', 2188223, 'Y', 'Top 5 Product Category revenue in this week', '2017-03-10 17:17:17+01', 2188223, NULL, 540000, 1000021, 40, 'K', 1000008, NULL, NULL);
INSERT INTO webui_dashboarditem VALUES (1000000, 1000000, '2017-02-27 15:28:03+01', 100, 'Y', 'BP Group Revenue Comparison', '2017-03-10 17:21:55+01', 2188223, NULL, 540000, 1000015, 3, 'K', 1000004, NULL, NULL);
INSERT INTO webui_dashboarditem VALUES (1000000, 1000000, '2017-03-10 17:32:18+01', 2188223, 'Y', 'Revenue per day this week', '2017-03-10 17:32:29+01', 2188223, NULL, 540000, 1000022, 50, 'K', 1000009, NULL, NULL);
INSERT INTO webui_dashboarditem VALUES (1000000, 1000000, '2017-03-10 17:33:00+01', 2188223, 'Y', 'Revenue per day (last week)', '2017-03-10 17:33:08+01', 2188223, NULL, 540000, 1000023, 60, 'K', 1000009, NULL, 'P-7D');


--
-- TOC entry 10269 (class 0 OID 127679)
-- Dependencies: 2700
-- Data for Name: webui_kpi_field; Type: TABLE DATA; Schema: public; Owner: metasfresh
--

INSERT INTO webui_kpi_field VALUES (1000000, 542221, 1000000, 10, '2017-03-10 16:56:22+01', 2188223, 'key', 'Y', 'Product category', '2017-03-14 16:18:54+01', 100, 1000075, 1000008, NULL, NULL, NULL, 'Y', NULL);
INSERT INTO webui_kpi_field VALUES (1000000, 441, 1000000, 12, '2017-02-27 15:21:54+01', 100, 'LineNetAmtSum.value', 'Y', 'LineNetAmt', '2017-03-14 14:00:25+01', 2188223, 1000070, 1000004, NULL, '', NULL, 'N', NULL);
INSERT INTO webui_kpi_field VALUES (1000000, 1997, 1000000, 11, '2017-02-25 19:47:28+01', 2188223, 'doc_count', 'Y', 'Number of docs', '2017-03-03 14:37:11+01', 2188223, 1000002, 1000000, NULL, NULL, NULL, 'N', 'Number of docs one week ago');
INSERT INTO webui_kpi_field VALUES (1000000, 2659, 1000000, 10, '2017-03-10 13:23:13+01', 2188223, 'key', 'Y', 'Produkt', '2017-03-10 13:23:43+01', 2188223, 1000071, 1000005, NULL, NULL, NULL, 'Y', NULL);
INSERT INTO webui_kpi_field VALUES (1000000, 441, 1000000, 11, '2017-03-10 14:21:34+01', 2188223, 'value', 'Y', 'Net amount', '2017-03-14 14:52:35+01', 100, 1000073, 1000006, NULL, 'EUR', NULL, 'N', NULL);
INSERT INTO webui_kpi_field VALUES (1000000, 441, 1000000, 12, '2017-03-10 16:57:20+01', 2188223, 'LineNetAmtSum.value', 'Y', 'Revenue', '2017-03-10 16:57:20+01', 2188223, 1000076, 1000008, NULL, 'EUR', NULL, 'N', 'Revenue last week');
INSERT INTO webui_kpi_field VALUES (1000000, 441, 1000000, 12, '2017-03-10 13:25:09+01', 2188223, 'LineNetAmtSum.value', 'Y', 'Revenue', '2017-03-10 17:13:37+01', 2188223, 1000072, 1000005, NULL, NULL, NULL, 'N', 'Revenue last week');
INSERT INTO webui_kpi_field VALUES (1000000, 268, 1000000, 15, '2017-03-10 17:31:09+01', 2188223, 'key', 'Y', 'Date ordered', '2017-03-10 17:31:09+01', 2188223, 1000077, 1000009, NULL, NULL, NULL, 'Y', NULL);
INSERT INTO webui_kpi_field VALUES (1000000, 441, 1000000, 12, '2017-02-25 20:04:45+01', 2188223, 'value', 'Y', 'LineNetAmt', '2017-03-13 16:29:44+01', 2188223, 1000005, 1000002, NULL, 'EUR', NULL, 'N', NULL);
INSERT INTO webui_kpi_field VALUES (1000000, 1383, 1000000, 10, '2017-02-27 15:19:47+01', 100, 'key', 'Y', 'BP Group', '2017-03-14 15:24:37+01', 100, 1000069, 1000004, NULL, NULL, NULL, 'Y', NULL);
INSERT INTO webui_kpi_field VALUES (1000000, 441, 1000000, 11, '2017-03-10 15:34:15+01', 2188223, 'value', 'Y', 'LineNetAmt', '2017-03-15 13:47:58+01', 100, 1000074, 1000007, NULL, 'EUR', NULL, 'N', NULL);
INSERT INTO webui_kpi_field VALUES (1000000, 441, 1000000, 12, '2017-03-10 17:34:01+01', 2188223, 'LineNetAmtSum.value', 'Y', 'Revenue', '2017-03-16 11:56:06+01', 2188223, 1000078, 1000009, NULL, 'EUR', NULL, 'N', NULL);
INSERT INTO webui_kpi_field VALUES (1000000, 53518, 1000000, 12, '2017-02-27 10:54:02+01', 100, '123', 'Y', 'ssdj', '2017-03-16 11:59:20+01', 2188223, 1000033, 1000001, NULL, NULL, NULL, 'N', NULL);
INSERT INTO webui_kpi_field VALUES (1000000, 2313, 1000000, 23, '2017-02-27 10:18:52+01', 100, 'sweweddserr', 'Y', 'ss', '2017-03-16 14:12:26+01', 100, 1000029, 1000001, NULL, NULL, NULL, 'N', NULL);
INSERT INTO webui_kpi_field VALUES (1000000, 53567, 1000000, 23, '2017-02-27 08:43:43+01', 100, 'aaarerer bdbb cce', 'Y', 'sssaasjskkjssdfdfgsd', '2017-03-17 13:22:06+01', 100, 1000006, 1000001, NULL, NULL, NULL, 'N', NULL);
INSERT INTO webui_kpi_field VALUES (1000000, 268, 1000000, 15, '2017-02-25 19:46:05+01', 2188223, 'key', 'Y', 'DateOrdered', '2017-03-14 16:13:37+01', 100, 1000001, 1000000, NULL, 'day', NULL, 'Y', NULL);


-- Completed on 2017-04-05 20:59:07 CEST

--
-- PostgreSQL database dump complete
--

