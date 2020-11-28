DROP VIEW IF EXISTS de_metas_fresh_kpi.kpi_purchase_order_aging_v;
CREATE OR REPLACE VIEW de_metas_fresh_kpi.kpi_purchase_order_aging_v AS
SELECT DISTINCT
	o.C_Order_ID,
    COALESCE( 
    (SELECT Created FROM AD_ChangeLog 
	WHERE Record_ID = o.C_Order_ID 
		AND AD_Table_ID = get_Table_ID('C_Order') 
		AND AD_Column_ID=(SELECT AD_Column_ID FROM AD_Column WHERE columnname='DocStatus' AND AD_Table_ID = get_Table_ID('C_Order')) 
		AND newvalue='CO' 
	ORDER BY created DESC LIMIT 1)
	,o.Updated) AS completedH,
	o.grandTotal
FROM C_Order o
INNER JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
WHERE o.issotrx='N' 
	AND o.docStatus IN ('CO','CL')
	AND ol.isPackagingMaterial='N'
	AND ol.qtyordered > ol.qtydelivered
;
