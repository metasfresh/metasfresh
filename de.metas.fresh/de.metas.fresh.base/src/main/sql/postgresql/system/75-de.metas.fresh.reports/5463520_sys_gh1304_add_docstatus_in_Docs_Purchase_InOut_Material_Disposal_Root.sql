DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Root(IN record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Root(IN record_id numeric)
RETURNS TABLE 
	(
	ad_org_id numeric,
	m_inout_id integer,
	displayhu text,
	DocStatus Character (2)
	)
AS
$$	
SELECT 
	i.ad_org_id,
	MIN(io.M_inOut_ID)::int,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM M_InventoryLine il1
			WHERE m_hu_pi_item_product_id IS NOT NULL AND il1.M_Inventory_ID = $1
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu,
	i.docstatus
	
FROM M_Inventory i


INNER JOIN M_InventoryLine il ON i.M_Inventory_ID = il.M_Inventory_ID
INNER JOIN M_InOutLine iol ON il.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'


WHERE i.M_Inventory_ID = $1 AND i.isActive = 'Y'

GROUP BY
	i.ad_org_id,i.docstatus

$$
LANGUAGE sql STABLE;
