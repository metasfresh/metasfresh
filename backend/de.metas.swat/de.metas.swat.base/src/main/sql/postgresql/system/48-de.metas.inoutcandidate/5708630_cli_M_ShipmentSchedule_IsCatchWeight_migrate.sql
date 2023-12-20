UPDATE m_shipmentschedule
SET iscatchweight=(CASE WHEN COALESCE(catch_uom_id, 0) > 0 THEN 'Y' ELSE 'N' END)
WHERE 1 = 1
;


