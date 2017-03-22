


DROP FUNCTION IF EXISTS HU_CostPrice_Function (IN keydate timestamp with time zone, IN M_Product_ID numeric(10,0), IN M_Warehouse_ID numeric(10,0), showDetails character varying);

CREATE OR REPLACE FUNCTION HU_CostPrice_Function (IN keydate timestamp with time zone, IN M_Product_ID numeric(10,0), IN M_Warehouse_ID numeric(10,0), showDetails character varying)

RETURNS TABLE
(
	combination character varying,
	description character varying,
	activity character varying,
	wh_name character varying,
	p_name character varying,
	p_value character varying,
	qty numeric,
	linesum numeric, 
	uomsymbol character varying,
	costprice numeric
)


AS
$$


SELECT
	vc.combination,
	vc.description,
	a.name AS Activity,
	wh.name AS WH_Name,
	p.name AS P_Name,
	p.value AS P_Value,
	qty,
	qty * CostPrice AS linesum,
	uom.UOMSymbol,
	CostPrice
FROM
	(
	SELECT
		pa.P_Asset_acct AS C_ValidCombination_ID,
		wh.C_Activity_ID,
		wh.M_Warehouse_ID,
		hus.M_Product_ID,
		hus.C_UOM_ID,
		SUM( hutl.qty ) AS qty,
		COALESCE( hua.ValueNumber, 0::numeric ) AS CostPrice
	FROM
		M_Warehouse wh
		LEFT OUTER JOIN M_Locator l ON wh.M_Warehouse_ID = l.M_Warehouse_ID
		LEFT OUTER JOIN M_HU_Trx_line hutl ON l.M_Locator_ID = hutl.M_locator_ID
		LEFT OUTER JOIN M_HU_Item item ON hutl.VHU_Item_ID = item.M_HU_Item_ID
		LEFT OUTER JOIN M_HU hu ON item.M_HU_ID = hu.M_HU_ID
		LEFT OUTER JOIN M_HU_Storage hus ON hu.M_HU_ID = hus.M_HU_ID
		LEFT OUTER JOIN M_HU_Attribute hua ON hu.M_HU_ID = hua.M_HU_ID
			AND hua.M_Attribute_ID = ((SELECT M_Attribute_ID FROM M_Attribute WHERE Value='HU_CostPrice'))
		LEFT OUTER JOIN M_Product_acct pa ON hus.M_Product_ID = pa.M_Product_ID
	WHERE
		hutl.DateTrx::date <= $1
		AND hutl.huStatus IN ('A', 'S') -- qonly display transactions if status is stocked, A = Active, S = Picked
	GROUP BY
		pa.P_Asset_acct,
		wh.C_Activity_ID,
		wh.M_Warehouse_ID,
		hus.M_Product_ID,
		hus.C_UOM_ID,
		COALESCE( hua.ValueNumber, 0::numeric )
	HAVING
		first_agg( hutl.huStatus ORDER BY hutl.created DESC) NOT IN ('P', 'D', 'E')
	UNION
	SELECT
		pa.P_Asset_acct AS C_ValidCombination_ID,
		wh.C_Activity_ID,
		wh.M_Warehouse_ID,
		p.M_Product_ID,
		p.C_UOM_ID,
		SUM( t.Movementqty ) AS qty,
		COALESCE( pp.PriceStd, 0::numeric ) AS CostPrice
	FROM
		M_Warehouse wh
		LEFT OUTER JOIN M_Locator l ON wh.M_Warehouse_ID = l.M_Warehouse_ID
		LEFT OUTER JOIN M_Transaction t ON l.M_Locator_ID = t.M_Locator_ID
		LEFT OUTER JOIN M_Product p ON t.M_Product_ID = p.M_Product_ID
		LEFT OUTER JOIN M_ProductPrice pp ON p.M_Product_ID = pp.M_Product_ID AND pp.M_PriceList_Version_ID = (SELECT value::numeric FROM AD_SysConfig WHERE name = 'de.metas.fresh.report.jasper.hu_costprice.packingmaterialPricelistVersionForInventoryValue' AND isActive = 'Y')
		LEFT OUTER JOIN C_UOM uom ON p.C_UOM_ID = uom.C_UOM_ID
		LEFT OUTER JOIN M_Product_acct pa ON p.M_Product_ID = pa.M_Product_ID
	WHERE
		p.M_Product_Category_ID = (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
		AND t.MovementDate::date <= $1
	GROUP BY
		pa.P_Asset_acct,
		wh.C_Activity_ID,
		wh.M_Warehouse_ID,
		p.M_Product_ID,
		p.C_UOM_ID,
		COALESCE( pp.PriceStd, 0::numeric )
	) dat
	LEFT OUTER JOIN M_Product p ON dat.M_Product_ID = p.M_Product_ID
	LEFT OUTER JOIN M_Warehouse wh ON dat.M_Warehouse_ID = wh.M_Warehouse_ID
	LEFT OUTER JOIN C_Activity a ON dat.C_Activity_ID = a.C_Activity_ID
	LEFT OUTER JOIN C_ValidCombination vc ON dat.C_ValidCombination_ID = vc.C_ValidCombination_ID
	LEFT OUTER JOIN C_UOM uom ON dat.C_UOM_ID = uom.C_UOM_ID
WHERE
	qty != 0
	AND CASE WHEN $2 IS NULL THEN p.M_Product_ID ELSE $2 END = p.M_Product_ID
	AND CASE WHEN $3 IS NULL THEN wh.M_Warehouse_ID ELSE $3 END = wh.M_Warehouse_ID
ORDER BY
	vc.combination,
	vc.description,
	a.name,
	Wh.name,
	p.value
;



$$
LANGUAGE sql STABLE
;
