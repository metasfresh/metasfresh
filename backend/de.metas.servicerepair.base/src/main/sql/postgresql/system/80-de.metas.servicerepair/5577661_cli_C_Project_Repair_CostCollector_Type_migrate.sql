DROP TABLE IF EXISTS tmp_repair_cost_collector_type
;

CREATE TEMPORARY TABLE tmp_repair_cost_collector_type AS
SELECT cc.c_project_id,
       cc.c_project_repair_costcollector_id,
       t.type          AS task_type,
       t.m_product_id  AS task_product_id,
       cc.m_product_id AS cc_product_id,
       cc.qtyreserved,
       cc.qtyconsumed,
       cc.isownedbycustomer,
       (CASE
            WHEN t.type = 'W' THEN (CASE WHEN t.m_product_id = cc.m_product_id THEN 'RPC' ELSE 'RP+' END)
            WHEN t.type = 'P' THEN (CASE WHEN cc.isownedbycustomer = 'Y' THEN 'SPC' ELSE 'SP+' END)
        END)           AS cc_type
FROM c_project_repair_costcollector cc
         INNER JOIN c_project_repair_task t ON cc.c_project_repair_task_id = t.c_project_repair_task_id
WHERE cc.type IS NULL
ORDER BY cc.c_project_repair_costcollector_id
;

-- select * from tmp_repair_cost_collector_type;

UPDATE c_project_repair_costcollector cc
SET type=t.cc_type
FROM tmp_repair_cost_collector_type t
WHERE t.c_project_repair_costcollector_id = cc.c_project_repair_costcollector_id
;

