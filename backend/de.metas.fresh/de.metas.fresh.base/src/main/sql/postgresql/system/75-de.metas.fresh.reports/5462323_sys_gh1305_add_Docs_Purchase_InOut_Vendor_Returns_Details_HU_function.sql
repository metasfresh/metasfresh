DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Details_HU(IN record_id numeric, IN AD_Language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Details_HU(IN record_id numeric, IN AD_Language Character Varying (6))
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

(
SELECT
	COALESCE(pt.Name, p.name) AS Name,
	iol.QtyEnteredTU AS HUQty,
	iol.MovementQty AS MovementQty,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
	iol.Description
	

FROM M_Inout io --vendor return

INNER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID

INNER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID =  bp.C_BPartner_ID AND bp.isActive = 'Y'
LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
				AND p.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'

-- Unit of measurement & its translation
INNER JOIN C_UOM uom			ON iol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
LEFT OUTER JOIN C_UOM_Trl uomt			ON iol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'

WHERE
		pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID) AND io.M_InOut_ID = $1
)iol


GROUP BY 
	Name, 
	UOMSymbol,
	iol.Description
$$
LANGUAGE sql STABLE;