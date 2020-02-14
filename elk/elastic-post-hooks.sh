#!/bin/bash
#
# /usr/local/bin/elastic-post-hooks.sh

echo "put customized logstash ilm policy"
curl -s -XPUT "http://localhost:9200/_ilm/policy/logstash-policy" -H 'Content-Type: application/json' -d'{
  "policy": {
    "phases": {
      "hot": {
        "min_age": "0ms",
        "actions": {
          "rollover": {
            "max_age": "30d",
            "max_size": "1GB"
          }
        }
      },
      "delete": {
        "min_age": "7d",
        "actions": {
          "delete": {}
        }
      }
    }
  }
}'
