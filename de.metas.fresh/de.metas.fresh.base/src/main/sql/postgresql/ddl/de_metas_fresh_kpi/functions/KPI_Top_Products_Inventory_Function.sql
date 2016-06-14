DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Top_Products_Inventory_Function (IN CurrentDate date, IN ComparisonDate date) ;


CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Top_Products_Inventory_Function (IN CurrentDate date, IN ComparisonDate date) 
RETURNS TABLE 
(
	p_value character varying,
	p_name character varying,
	CurrentQty numeric,
	ComparisonQty numeric
)
AS
$$

SELECT  p.value AS p_value, p.name AS p_name, x.Currentqty, x.comparisonQty
FROM

(

	SELECT 
		current.M_Product_ID, 
		current.Qty as CurrentQty, 
		comparison.qty as ComparisonQty

	FROM
	(
		(
			SELECT

				dat.m_product_ID,
				sum(dat.qty) as qty
			FROM
			(
				(
					SELECT
						hus.M_Product_ID,
						SUM( hutl.qty ) AS qty
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
						AND hutl.huStatus IN ('A', 'S') -- only display transactions if status is stocked, A = Active, S = Picked
					GROUP BY
						hus.M_Product_ID
					HAVING
						first_agg( hutl.huStatus ORDER BY hutl.created DESC) NOT IN ('P', 'D', 'E')
				)
				UNION ALL
				(
					SELECT
						p.M_Product_ID,
						SUM( t.Movementqty ) AS qty
					FROM
						M_Warehouse wh
						LEFT OUTER JOIN M_Locator l ON wh.M_Warehouse_ID = l.M_Warehouse_ID
						LEFT OUTER JOIN M_Transaction t ON l.M_Locator_ID = t.M_Locator_ID
						LEFT OUTER JOIN M_Product p ON t.M_Product_ID = p.M_Product_ID
						LEFT OUTER JOIN M_ProductPrice pp ON p.M_Product_ID = pp.M_Product_ID AND pp.M_PriceList_Version_ID = 2001277
						LEFT OUTER JOIN C_UOM uom ON p.C_UOM_ID = uom.C_UOM_ID
						LEFT OUTER JOIN M_Product_acct pa ON p.M_Product_ID = pa.M_Product_ID
					WHERE
						p.M_Product_Category_ID = (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
						AND t.MovementDate::date <= $1
					GROUP BY
						p.M_Product_ID
				)
			) dat	
			WHERE
				qty != 0
			GROUP BY m_Product_ID
		) current

	LEFT JOIN 
		(
			SELECT

				dat.m_product_ID,
				sum(dat.qty) as qty
			FROM
			(
				(
					SELECT
						hus.M_Product_ID,
						SUM( hutl.qty ) AS qty
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
						hutl.DateTrx::date <= $2
						AND hutl.huStatus IN ('A', 'S') -- only display transactions if status is stocked, A = Active, S = Picked
					GROUP BY
						hus.M_Product_ID
					HAVING
						first_agg( hutl.huStatus ORDER BY hutl.created DESC) NOT IN ('P', 'D', 'E')
				)
				UNION ALL
				(
					SELECT
						p.M_Product_ID,
						SUM( t.Movementqty ) AS qty
					FROM
						M_Warehouse wh
						LEFT OUTER JOIN M_Locator l ON wh.M_Warehouse_ID = l.M_Warehouse_ID
						LEFT OUTER JOIN M_Transaction t ON l.M_Locator_ID = t.M_Locator_ID
						LEFT OUTER JOIN M_Product p ON t.M_Product_ID = p.M_Product_ID
						LEFT OUTER JOIN M_ProductPrice pp ON p.M_Product_ID = pp.M_Product_ID AND pp.M_PriceList_Version_ID = 2001277
						LEFT OUTER JOIN C_UOM uom ON p.C_UOM_ID = uom.C_UOM_ID
						LEFT OUTER JOIN M_Product_acct pa ON p.M_Product_ID = pa.M_Product_ID
					WHERE
						p.M_Product_Category_ID = (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
						AND t.MovementDate::date <= $2
					GROUP BY
						p.M_Product_ID
				)
			) dat	
			WHERE
				qty != 0
			GROUP BY m_Product_ID
		) comparison  on current. m_Product_ID = comparison.M_Product_ID
	)

) x

JOIN M_Product p ON x.M_Product_ID = p.M_Product_ID


ORDER BY currentqty DESC

LIMIT 10

$$
LANGUAGE sql STABLE
;
