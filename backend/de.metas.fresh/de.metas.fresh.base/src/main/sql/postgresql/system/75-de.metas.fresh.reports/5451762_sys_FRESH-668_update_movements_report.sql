DROP FUNCTION IF EXISTS report.movements_report( numeric,  numeric,  numeric) ;

DROP FUNCTION IF EXISTS report.movements_report(c_period_id numeric, c_activityfrom_id numeric, c_activityto_id numeric, ad_org_id numeric(10,0)) ;
CREATE OR REPLACE FUNCTION report.movements_report (c_period_id numeric, c_activityfrom_id numeric, c_activityto_id numeric, ad_org_id numeric(10,0)) RETURNS TABLE
	(
	P_Value Character Varying, 
	P_Name Character Varying, 
	UOMSymbol Character Varying,
	MovementQty numeric,
	PeriodStartDate Date,
	PeriodEndDate Date,
	param_activity_from_value Character Varying,
	param_activity_from_name Character Varying,
	param_activity_to_value Character Varying,
	param_activity_to_name Character Varying,
	activity_to Character Varying,
	activity_from Character Varying,
	price numeric,
	account Character Varying,
	
	ad_org_id numeric
	)
AS 
$$

SELECT
dat.P_Value,
dat.P_Name,
dat.UOMSymbol,
SUM(dat.MovementQty),
dat.StartDate,
dat.EndDate,
dat.param_activity_from_value,
dat.param_activity_from_name,
dat.param_activity_to_value,
dat.param_activity_to_name,
dat.activity_to,
dat.activity_from,
dat.costprice,
dat.account,

dat.ad_org_id
FROM
(

SELECT
	p.value AS P_Value, 
	p.name AS P_Name, 
	uom.UOMSymbol AS UOMSymbol, 
	ml.MovementQty AS MovementQty,
	per.StartDate::DATE AS StartDate,
	per.EndDate::DATE AS EndDate,
	(SELECT value FROM C_Activity WHERE C_Activity_ID = $2 AND isActive = 'Y') AS param_activity_from_value,
	(SELECT name FROM C_Activity WHERE C_Activity_ID = $2 AND isActive = 'Y') AS param_activity_from_name,
	(SELECT value FROM C_Activity WHERE C_Activity_ID = $3 AND isActive = 'Y') AS param_activity_to_value,
	(SELECT name FROM C_Activity WHERE C_Activity_ID = $3 AND isActive = 'Y') AS param_activity_to_name,
	ac_to.value||' - '|| ac_to.name AS activity_to,
	ac_from.value AS activity_from,
	COALESCE( hua.ValueNumber, 0::numeric ) AS costprice,
	ev.value||' - '|| ev.name AS account
	,fa.line_ID,
	
	fa.ad_org_id
	
FROM 
	M_MovementLine ml 
	LEFT OUTER JOIN M_Movement mov ON mov.M_Movement_ID = ml.M_Movement_ID AND mov.isActive = 'Y'
	--use this movementline to find the warehouses linked by the transit warehouse
	LEFT OUTER JOIN M_Movementline ml_before ON ml.dd_orderline_ID = ml_before.dd_orderLine_ID AND ml_before.isActive = 'Y'
	LEFT OUTER JOIN M_Locator l_from ON COALESCE(ml_before.M_Locator_ID,ml.M_Locator_ID) = l_from.M_Locator_ID AND l_from.isActive = 'Y'
	LEFT OUTER JOIN M_Warehouse wh_from ON l_from.M_Warehouse_ID=wh_from.M_Warehouse_ID AND wh_from.isActive = 'Y'
	LEFT OUTER JOIN M_Locator l_to ON ml.M_LocatorTo_ID = l_to.M_Locator_ID AND l_to.isActive = 'Y'
	LEFT OUTER JOIN M_Warehouse wh_to ON l_to.M_Warehouse_ID=wh_to.m_warehouse_ID AND wh_to.isActive = 'Y'
	LEFT OUTER JOIN M_Product p ON ml.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc ON pc.M_Product_Category_ID = p.M_Product_Category_ID AND pc.isActive = 'Y'
	LEFT OUTER JOIN C_UOM uom ON p.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	
	

	LEFT OUTER JOIN Fact_Acct fa ON fa.Record_ID = mov.M_Movement_ID AND fa.AD_Table_ID = (SELECT Get_Table_ID('M_Movement')) AND fa.Line_ID = ml.M_MovementLine_ID  AND fa.isActive = 'Y'
	LEFT OUTER JOIN C_Period per ON fa.C_Period_ID = per.C_Period_ID AND per.isActive = 'Y'
	LEFT OUTER JOIN C_ElementValue ev ON fa.Account_ID = ev.C_ElementValue_ID AND ev.isActive = 'Y'
	
	LEFT OUTER JOIN M_HU_Assignment huas ON huas.AD_Table_ID = get_table_id('M_MovementLine') AND huas.Record_ID = ml.M_MovementLine_ID AND huas.isActive = 'Y'
	LEFT OUTER JOIN M_HU lu ON huas.M_HU_ID = lu.M_HU_ID
	LEFT OUTER JOIN M_HU tu ON lu.m_hu_id = (SELECT M_HU_ID FROM M_HU_Item WHERE tu.m_hu_item_parent_id = M_HU_Item_ID AND isActive = 'Y')
	LEFT OUTER JOIN M_HU vhu ON tu.m_hu_id = (SELECT M_HU_ID FROM M_HU_Item WHERE vhu.m_hu_item_parent_id = M_HU_Item_ID AND isActive = 'Y')
	LEFT OUTER JOIN m_hu_pi_version vlu ON vlu.m_hu_pi_version_id = lu.m_hu_pi_version_id AND vlu.isActive = 'Y'
	LEFT OUTER JOIN m_hu_pi_version vtu ON vtu.m_hu_pi_version_id = tu.m_hu_pi_version_id AND vtu.isActive = 'Y'
	LEFT OUTER JOIN m_hu_pi_version vvhu ON vvhu.m_hu_pi_version_id = vhu.m_hu_pi_version_id AND vvhu.isActive = 'Y'
	
	LEFT OUTER JOIN M_HU_Attribute hua ON 
	(CASE WHEN vlu.hu_unittype::text = 'V'::text THEN lu.M_HU_ID 
	WHEN vtu.hu_unittype::text = 'V'::text THEN tu.M_HU_ID 
	WHEN vvhu.hu_unittype::text = 'V'::text THEN vhu.M_HU_ID 
	end)
	= hua.M_HU_ID
		AND hua.M_Attribute_ID = ((SELECT M_Attribute_ID FROM M_Attribute WHERE Value='HU_CostPrice' AND isActive = 'Y')) AND hua.isActive = 'Y'

	LEFT OUTER JOIN C_Activity ac_to on wh_to.c_activity_ID = ac_to.c_activity_ID AND ac_to.isActive = 'Y'
	LEFT OUTER JOIN C_Activity ac_from on wh_from.c_activity_ID = ac_from.c_activity_ID AND ac_from.isActive = 'Y'

WHERE 
	COALESCE(wh_from.C_Activity_ID,0) != COALESCE(wh_to.C_Activity_ID,0) 
	AND fa.C_Period_ID = $1 AND ml.isActive = 'Y'
	AND (CASE WHEN $2 IS NOT NULL THEN wh_from.C_Activity_ID = $2 ELSE TRUE END)
	AND (CASE WHEN $3 IS NOT NULL THEN wh_to.C_Activity_ID = $3 ELSE TRUE END) 
	--we don't want gebinde and verpackungs 09746
	AND pc.ispackagingmaterial = 'N' AND pc.isTradingUnit = 'N'
	--we don't want movements which are "Materialentnahme" 09746
	AND wh_to.m_warehouse_ID != (SELECT Value FROM AD_SysConfig WHERE Name LIKE 'de.metas.handlingunits.client.terminal.inventory.model.InventoryHUEditorModel#DirectMove_Warehouse_ID')::integer
	--no quality warehouses 09779
	AND wh_to.isissuewarehouse='N'
	--no transit warehouses 09779
	AND wh_to.isintransit='N' AND wh_from.isintransit='N'
	AND fa.ad_org_id = $4
GROUP BY 
	p.value, 
	p.name, 
	uom.UOMSymbol,
	per.StartDate::DATE,
	per.EndDate::DATE,
	ac_to.value||' - '|| ac_to.name,
	ac_from.value,
	COALESCE( hua.ValueNumber, 0::numeric ),
	ev.value||' - '|| ev.name
	,ml.MovementQty
	, fa.line_ID,
	
	fa.ad_org_id
)dat

WHERE dat.ad_org_ID = $4

GROUP BY 
dat.P_Value,
dat.P_Name,
dat.UOMSymbol,
dat.StartDate,       
dat.EndDate,
dat.param_activity_from_value,
dat.param_activity_from_name,
dat.param_activity_to_value,
dat.param_activity_to_name,
dat.activity_to,
dat.activity_from,
dat.costprice,
dat.account,

dat.ad_org_id

ORDER BY 
dat.activity_to,
dat.activity_from,
dat.P_Value

			
$$
  LANGUAGE sql STABLE