DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_Sum ( IN Record_ID numeric );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_Sum;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_Sum
(
	TotalEntered numeric,
	Discount numeric,
	TotalDiscountAmt numeric,
	NonHUTotal numeric,
	HUTotal numeric,
	GrandTotal numeric,
	ISOCode Character Varying,
	ShowDiscount Text
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_Sum ( IN Record_ID numeric )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_Sum AS
$$
SELECT
	sum.TotalEntered,
	round(sum.MaxDiscount, 0) AS Discount,
	sum.TotalDiscountAmt,
	sum.NonHuTotal,
	sum.HuTotal,
	sum.NonHuTotal + sum.HuTotal AS GrandTotal,
	COALESCE(cur.cursymbol,cur.iso_code) AS iso_code,
	CASE WHEN MaxDiscount = MinDiscount AND MaxDiscount != 0 AND COALESCE( MaxDiscount, MinDiscount ) IS NOT NULL
		THEN 'Y' ELSE 'N' END AS ShowDiscount
FROM
	M_InOut io
	INNER JOIN
	(
		SELECT 	presum.M_InOut_ID,
			SUM( CASE WHEN NOT isHULine THEN PriceEntered * QtyEntered ELSE 0 END ) as TotalEntered,
			MAX( CASE WHEN NOT isHULine THEN Discount ELSE NULL END ) AS MaxDiscount,
			MIN( CASE WHEN NOT isHULine THEN Discount ELSE NULL END ) AS MinDiscount,
			SUM( CASE WHEN NOT isHULine THEN (PriceEntered - PriceActual) * QtyEntered ELSE 0 END ) AS TotalDiscountAmt,
			SUM( CASE WHEN NOT isHULine THEN PriceActual * QtyEntered ELSE 0 END ) as NonHuTotal,
			SUM( CASE WHEN isHULine AND bp.isHidePackingMaterialInShipmentPrint = 'N'
				THEN PriceActual * QtyEntered ELSE 0 END ) as HuTotal,
			presum.C_Currency_ID
		FROM	(
				SELECT 	iol.M_InOut_ID,
					p.M_Product_Category_ID =
					(
						SELECT value::numeric FROM AD_SysConfig
						WHERE name = 'PackingMaterialProductCategoryID' AND isActive = 'Y'
					) as IsHULine,
					COALESCE(ic.PriceEntered_Override, ic.PriceEntered) AS PriceEntered,
					COALESCE(ic.Discount_Override, ic.Discount) AS Discount,
					COALESCE(ic.PriceActual_Override, ic.PriceActual) AS PriceActual,
					iol.QtyEntered * COALESCE (multiplyrate, 1) AS QtyEntered,
					ic.C_Currency_ID
				FROM 	M_InOutLine iol
					INNER JOIN C_InvoiceCandidate_InOutLine ic_iol ON ic_iol.M_InOutLine_ID=iol.M_InOutLine_ID AND ic_iol.isActive = 'Y'
					INNER JOIN C_Invoice_Candidate ic ON ic_iol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID AND ic.isActive = 'Y'
					INNER JOIN M_Product p ON iol.M_product_ID = p.M_Product_ID AND p.isActive = 'Y'
					LEFT OUTER JOIN C_UOM_Conversion conv ON conv.C_UOM_ID = iol.C_UOM_ID
						AND conv.C_UOM_To_ID = ic.Price_UOM_ID
						AND iol.M_Product_ID = conv.M_Product_ID
						AND conv.isActive = 'Y'
					WHERE iol.isActive = 'Y'
			) presum
			LEFT OUTER JOIN M_InOut io ON presum.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
			LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
		GROUP BY 	presum.M_InOut_ID, presum.C_Currency_ID
	) sum ON io.M_InOut_ID = sum.M_InOut_ID
	LEFT OUTER JOIN C_Currency cur ON sum.C_Currency_ID = cur.C_Currency_ID AND cur.isActive = 'Y'
WHERE
	io.M_InOut_ID = $1 AND io.isActive = 'Y'
$$
LANGUAGE sql STABLE
;