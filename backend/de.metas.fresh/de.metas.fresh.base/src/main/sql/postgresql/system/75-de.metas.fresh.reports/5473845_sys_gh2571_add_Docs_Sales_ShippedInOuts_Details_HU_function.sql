

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_ShippedInOuts_Details_HU(numeric, numeric, date, numeric, character varying(6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_ShippedInOuts_Details_HU
	(
		IN C_BPartner_ID numeric, 
		IN C_BPartner_Location_ID numeric, 
		IN Date date, 
		IN AD_Org_ID numeric, 
		IN ad_language character varying(6)
	)
RETURNS TABLE
	(
		Name character varying,
		Qty numeric,
		UOMSymbol character varying,
		ad_org_id numeric
	)	
AS
$$	

SELECT
	COALESCE(pt.Name, p.Name) AS Name,
	SUM( iol.QtyEntered ) AS Qty,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
	io.ad_org_id
FROM
	M_InOut io
	LEFT OUTER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID AND iol.isActive = 'Y'
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.ad_language = $5 AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON iol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.ad_language = $5 AND uomt.isActive = 'Y'
WHERE
	io.C_BPartner_ID = $1
	AND io.C_BPartner_Location_ID =
		CASE WHEN $2 IS NULL
			THEN io.C_BPartner_Location_ID
			ELSE $2
		END
	AND io.MovementDate::date = $3::date
	AND io.isSOTrx = 'Y'
	AND io.isActive = 'Y'
	AND io.DocStatus = 'CO'
	AND p.M_Product_Category_ID =  getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID)
	AND io.ad_org_id = $4
GROUP BY
	COALESCE(pt.Name, p.Name),
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol),
	io.ad_org_id
ORDER BY
	COALESCE(pt.Name, p.Name)

$$ 
LANGUAGE sql STABLE
;