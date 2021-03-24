UPDATE pp_cost_collector cc
SET durationunit=(SELECT owf.durationunit
                  FROM pp_order_node a
                           INNER JOIN pp_order_workflow owf ON a.pp_order_workflow_id = owf.pp_order_workflow_id
                  WHERE a.pp_order_node_id = cc.pp_order_node_id)
WHERE durationunit IS NULL
  AND pp_order_node_id IS NOT NULL
;

