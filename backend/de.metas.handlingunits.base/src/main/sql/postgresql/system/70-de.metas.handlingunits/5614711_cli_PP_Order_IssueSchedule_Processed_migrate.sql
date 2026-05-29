UPDATE pp_order_issueschedule
SET processed=(CASE WHEN qtyissued != 0 OR rejectreason IS NOT NULL THEN 'Y' ELSE 'N' END)
WHERE 1 = 1
;