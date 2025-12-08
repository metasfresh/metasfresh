UPDATE dd_order_candidate c
SET demanddate=supplydate
    - COALESCE((SELECT GREATEST(pp.DeliveryTime_Promised, 0) FROM pp_product_planning pp WHERE pp.pp_product_planning_id = c.pp_product_planning_id), 0)
    - COALESCE(
                       NULLIF((SELECT GREATEST(nl.transferttime, 0) FROM dd_networkdistributionline nl WHERE nl.dd_networkdistributionline_id = c.dd_networkdistributionline_id), 0),
                       NULLIF((SELECT GREATEST(pp.TransfertTime, 0) FROM pp_product_planning pp WHERE pp.pp_product_planning_id = c.pp_product_planning_id), 0),
                       0
      )
WHERE demandDate IS NULL
;
