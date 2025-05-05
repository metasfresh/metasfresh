DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details_Sum ( IN c_order_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details_Sum ( IN c_order_id numeric)
RETURNS TABLE 
(
	nonhulines numeric,
	grandtotal numeric,
	iso_code character,
	isprinttax character(1),
	ishuline boolean,
	taxbaseamt numeric,
	taxrate numeric,
	taxamt numeric,
	cursymbol character varying(10),
	vattaxid character varying(60)
)
AS
$$

SELECT
	(
		SELECT SUM(ot.TaxBaseAmt) FROM C_OrderTax ot
		WHERE ot.C_Order_ID = o.C_Order_ID and ot.IsPackagingTax = 'N' AND ot.isActive = 'Y'
	) NonHULines,
	GrandTotal,
	cur.iso_code AS iso_code,
	isPrintTaxSales,
	isHULine,
	sum.TaxBaseAmt,
	CASE
		WHEN round(sum.TaxRate,0) = sum.TaxRate THEN floor(sum.TaxRate)
		WHEN round(sum.TaxRate,1) = sum.TaxRate THEN round(sum.TaxRate,1)
		WHEN round(sum.TaxRate,2) = sum.TaxRate THEN round(sum.TaxRate,2)
	ELSE round(sum.TaxRate,2)
	END AS TaxRate,
	sum.TaxAmt,
	cur.cursymbol,
	COALESCE(bp.VATaxID, '') as VATaxID
FROM
	C_Order o
	INNER JOIN
	(
		SELECT	o.C_Order_ID,
			ot.IsPackagingTax = 'Y' AS isHULine,
			SUM(ot.TaxBaseAmt) AS TaxBaseAmt,
			t.rate AS TaxRate,
			SUM(ot.taxamt) AS TaxAmt
		FROM 	C_Order o
			LEFT OUTER JOIN C_OrderTax ot ON o.C_Order_ID = ot.C_Order_ID AND ot.isActive = 'Y'
			LEFT OUTER JOIN C_Tax t ON ot.C_Tax_ID = t.C_Tax_ID AND t.isActive = 'Y'
		GROUP BY o.C_Order_ID, ot.IsPackagingTax = 'Y', t.rate
	) sum ON o.C_Order_ID = sum.C_Order_ID
	INNER JOIN C_Currency cur ON o.C_Currency_ID = cur.C_Currency_ID AND cur.isActive = 'Y'
	INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	INNER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y'
WHERE
	o.C_Order_ID = $1 AND o.isActive = 'Y'
ORDER BY
	sum.isHULine, -- show HUs last
	sum.TaxRate desc

$$
LANGUAGE sql STABLE;