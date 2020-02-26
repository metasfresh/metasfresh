#!/bin/bash
#
# /usr/local/bin/elastic-post-hooks.sh
# Invoked from start.sh right after elastic started
#


elastic_hostname=${ELK_ELASTIC_HOSTNAME:-localhost}
elastic_json_port=${ELK_ELASTIC_JSON_PORT:-9200}
elastic_protocol=${ELK_ELASTIC_PROTOCOL:-http}

policy_max_size=$(echo ${ELK_INDEX_ROLLOVER_SIZE:-1GB} | tr -d \")
policy_max_age=$(echo ${ELK_INDEX_ROLLOVER_AGE:-30d} | tr -d \")
policy_del_min_age=$(echo ${ELK_INDEX_DELETE_AGE:-7d} | tr -d \")

echo "[INFO] put customized logstash ilm policy"
echo "       - ${elastic_protocol}//${elastic_hostname}:${elastic_json_port}"
echo "       - ELK_INDEX_ROLLOVER_SIZE: ${policy_max_size}"
echo "       - ELK_INDEX_ROLLOVER_AGE: ${policy_max_age}"
echo "       - ELK_INDEX_DELETE_AGE: ${policy_del_min_age}"


curl -XPUT "${elastic_protocol}://${elastic_hostname}:${elastic_json_port}/_ilm/policy/logstash-policy" -H 'Content-Type: application/json' -d'{
  "policy": {
    "phases": {
      "hot": {
        "min_age": "0ms",
        "actions": {
          "rollover": {
            "max_age": "'"${policy_max_age}"'",
            "max_size": "'"${policy_max_size}"'"
          }
        }
      },
      "delete": {
        "min_age": "'"${policy_del_min_age}"'",
        "actions": {
          "delete": {}
        }
      }
    }
  }
}'
