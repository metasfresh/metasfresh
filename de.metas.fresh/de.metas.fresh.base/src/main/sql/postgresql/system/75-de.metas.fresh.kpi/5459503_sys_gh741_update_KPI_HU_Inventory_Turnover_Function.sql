--DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Inventory_Turnover(IN c_period_id numeric, IN noOfmonths integer);
CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Inventory_Turnover(IN c_period_id numeric, IN noOfmonths integer DEFAULT 12)
RETURNS TABLE
(
m_product_id numeric,
product_name character varying,
qty_stored numeric,
qty_delivered numeric,
qty_inv_turnover numeric
)
AS
$$
SELECT m_product_id, 
	product_name, 
	SUM(qty_stored)/($2+1) as qty_stored, 
	SUM(qty_delivered)/($2+1) as qty_delivered, 
	(CASE WHEN (SUM(qty_delivered))>0 THEN ((SUM(qty_stored)/($2+1)) / (SUM(qty_delivered)/($2+1))) ELSE 0 END ) as qty_inv_turnover
FROM(
SELECT
* FROM 
(
SELECT hutl.m_product_id as m_product_id ,pr.name AS product_name, sum(hutl.qty) as qty_stored, 0 as qty_delivered  
FROM m_hu_storage hust
 JOIN m_hu hu ON hu.m_hu_id = hust.m_hu_id
 JOIN M_HU_Item item ON item.M_HU_ID = hu.M_HU_ID
 JOIN M_HU_Trx_line hutl ON hutl.VHU_Item_ID = item.M_HU_Item_ID
 JOIN M_Locator l ON l.m_locator_id = hutl.m_locator_id
 JOIN M_Warehouse wh ON wh.m_Warehouse_ID = l.m_warehouse_ID
 JOIN C_Period p ON p.c_period_id = $1
 JOIN C_Period pp ON pp.c_period_id = report.get_predecessor_period_recursive($1, $2)
 JOIN m_product pr ON pr.m_product_id =  hutl.m_product_id AND pr.M_Product_Category_ID != getSysConfigAsNumeric('PackingMaterialProductCategoryID', hust.AD_Client_ID, hust.AD_Org_ID)
WHERE hutl.datetrx::date >= pp.startdate AND hutl.datetrx::date <= p.enddate  
 AND wh.m_warehouse_id !=(SELECT Value FROM AD_SysConfig WHERE Name LIKE 'de.metas.hANDlingunits.client.terminal.inventory.model.InventoryHUEditorModel#DirectMove_Warehouse_ID')::integer
group by hutl.m_product_id, pr.name

HAVING first_agg( hutl.huStatus ORDER BY hutl.created DESC) NOT IN ('P', 'D', 'E')  
)st

UNION ALL
SELECT * FROM
(

SELECT iol.m_product_id AS m_product_id, pr.name AS product_name, 0 as qty_stored, sum(iol.movementqty) as qty_delivered 
FROM m_inoutline iol
inner JOIN m_inout io ON io.m_inout_id = iol.m_inout_id
JOIN m_product pr ON pr.m_product_id =iol.m_product_id AND pr.M_Product_Category_ID != getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID)
JOIN C_Period p ON p.c_period_id = $1
 JOIN C_Period pp ON pp.c_period_id = report.get_predecessor_period_recursive($1, $2)
WHERE io.movementdate::date >= pp.startdate AND io.movementdate::date <= p.enddate 
AND iol.ispackagingmaterial='N' AND io.issotrx='Y' AND io.docstatus in ('CO','CL')
--
group by pr.name, iol.m_product_id 
union all
(

SELECT hutl.m_product_id AS m_product_id, pr.name product_name, 0 as qty_stored, sum(hutl.qty) as qty_delivered 
FROM m_hu_storage hust
 JOIN m_hu hu ON hu.m_hu_id = hust.m_hu_id
 JOIN M_HU_Item item ON item.M_HU_ID = hu.M_HU_ID
 JOIN M_HU_Trx_line hutl ON hutl.VHU_Item_ID = item.M_HU_Item_ID
 JOIN M_Locator l ON l.m_locator_id = hutl.m_locator_id
 JOIN M_Warehouse wh ON wh.m_Warehouse_ID = l.m_warehouse_ID
 JOIN C_Period p ON p.c_period_id = $1
 JOIN C_Period pp ON pp.c_period_id = report.get_predecessor_period_recursive($1, $2)
 JOIN m_product pr ON pr.m_product_id =  hutl.m_product_id AND pr.M_Product_Category_ID !=  getSysConfigAsNumeric('PackingMaterialProductCategoryID', hust.AD_Client_ID, hust.AD_Org_ID)
WHERE hutl.datetrx::date >= pp.startdate AND hutl.datetrx::date <= p.enddate  
 AND  wh.m_warehouse_id !=(SELECT Value FROM AD_SysConfig WHERE Name LIKE 'de.metas.hANDlingunits.client.terminal.inventory.model.InventoryHUEditorModel#DirectMove_Warehouse_ID')::integer --AND hutl.m_product_id=2001043
group by hutl.m_product_id,pr.name

HAVING first_agg( hutl.huStatus ORDER BY hutl.created DESC) NOT IN ('P', 'D', 'E')
--
)
)de

)rez
GROUP BY m_product_id, product_name
ORDER BY qty_inv_turnover desc
$$
LANGUAGE sql STABLE;