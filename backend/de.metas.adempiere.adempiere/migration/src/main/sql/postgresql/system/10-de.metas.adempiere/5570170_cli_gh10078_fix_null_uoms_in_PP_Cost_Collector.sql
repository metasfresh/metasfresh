UPDATE pp_cost_collector cc
SET c_uom_id=(SELECT obl.c_uom_id FROM pp_order_bomline obl WHERE obl.pp_order_bomline_id = cc.pp_order_bomline_id)
WHERE cc.c_uom_id IS NULL
  AND cc.pp_order_bomline_id IS NOT NULL
-- AND cc.costcollectortype = '120' -- Usage Variance
;
