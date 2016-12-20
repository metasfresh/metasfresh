DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Details_HU ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Details_HU ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE 
(
	Name character varying, 
	HUQty numeric,
	MovementQty numeric,
	UOMSymbol character varying,
	Description character varying
)
AS
$$

SELECT
	Name, -- product
	SUM( HUQty ) AS HUQty,
	SUM( MovementQty ) AS MovementQty,
	UOMSymbol,
	iol.Description
FROM
	-- Note: This query is a copy of report_details and some part were removed for speed. So the grouping outside of
	-- the subquery is not really necessary anymore. But it was not changed to spare the effort. Original comment:
	-- Sub select to get all in out lines we need. They are in a subselect so we can neatly group by the attributes
	-- (Otherwise we'd have to copy the attributes sub select in the group by clause which. Hint: That would suck)
	(
	SELECT
		iol.line,
		COALESCE(pt.Name, p.name)		AS Name,
		iol.QtyEnteredTU			AS HUQty,
		iol.MovementQty,
		COALESCE(uomt.UOMSymbol, uom.UOMSymbol) 	AS UOMSymbol,
		iol.Description
	FROM
		-- All In Outs linked to the order
		(
		SELECT DISTINCT
			io.*
		FROM
			-- All In Out Lines directly linked to the order
			M_InOutLine iol
			INNER JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID AND ol.isActive = 'Y'
			INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
		WHERE
			ol.C_Order_ID = $1 
			AND io.DocStatus IN ('CO','CL') AND iol.isActive = 'Y'
		) io
		/*
		 * Now, join all in out lines of those in outs. Might be more than the in out lines selected in the previous
		 * sub select because not all in out lines are linked to the order (e.g Packing material). NOTE: Due to the
		 * process we assume, that all lines of one inout belong to only one order
		 */
		INNER JOIN M_InOutLine iol 			ON io.M_InOut_ID = iol.M_InOut_ID AND iol.isActive = 'Y'
		-- Product and its translation
		LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
		LEFT OUTER JOIN M_Product_Trl pt 		ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
		LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
		-- Unit of measurement & its translation
		LEFT OUTER JOIN C_UOM uom			ON iol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
		LEFT OUTER JOIN C_UOM_Trl uomt			ON iol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'
	WHERE
		pc.M_Product_Category_ID = (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID' AND isActive = 'Y')
	) iol
GROUP BY
	Name, -- product
	UOMSymbol,
	Description
ORDER BY
	Name
$$
LANGUAGE sql STABLE
;	
