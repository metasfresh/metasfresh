DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Details_HU ( IN C_Invoice_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Details_HU ( IN C_Invoice_ID numeric, IN AD_Language Character Varying (6) )

RETURNS TABLE 
(
	shipped numeric,
	retour numeric,
	Name character varying,
	Price numeric,
	LineNetAmt numeric,
	UOMSymbol character varying(10),
	rate numeric,
	IsPrintTax character(1)
)
AS
$$
SELECT
	SUM ( CASE
		WHEN il.QtyEntered > 0 THEN il.QtyEntered
		ELSE 0
	END )				AS shipped,
	SUM ( CASE
		WHEN il.QtyEntered < 0 THEN il.QtyEntered*-1
		ELSE 0
	END )				AS retour,
	COALESCE(pt.Name, p.Name)		AS Name,
	il.PriceEntered			AS Price,
	SUM(il.LineNetAmt)			AS LineNetAmt,
	COALESCE(uom.UOMSymbol, uomt.UOMSymbol)	AS UOMSymbol,
	t.rate,
	bpg.IsPrintTax
FROM
	C_InvoiceLine il
	INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
	INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y'
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON il.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON il.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON il.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON il.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'
	-- Tax rate
	LEFT OUTER JOIN C_Tax t			ON il.C_Tax_ID = t.C_Tax_ID AND t.isActive = 'Y'
WHERE
	il.C_Invoice_ID = $1 AND il.isActive = 'Y'
	AND pc.M_Product_Category_ID = (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID' AND isActive = 'Y')
GROUP BY
	COALESCE(pt.Name, p.Name), COALESCE(uom.UOMSymbol, uomt.UOMSymbol), il.PriceEntered,
	t.rate,
	bpg.IsPrintTax
$$
LANGUAGE sql STABLE
;