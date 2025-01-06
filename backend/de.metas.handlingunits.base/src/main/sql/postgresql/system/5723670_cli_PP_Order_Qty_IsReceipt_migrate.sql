UPDATE pp_order_qty q
SET isreceipt = (CASE
                     WHEN pp_order_bomline_id IS NULL                                                                                                          THEN 'Y'
                     WHEN EXISTS (SELECT 1 FROM pp_order_bomline bl WHERE bl.pp_order_bomline_id = q.pp_order_bomline_id AND bl.componentType IN ('CP', 'BY')) THEN 'Y'
                                                                                                                                                               ELSE 'N'
                 END)
;
