-- me03 #29822: Factoring - show factoring bank account on customer-returns documents.
-- This root function is shared by the sales customer-returns report and the purchase vendor-returns report.
-- Only the customer-returns report passes IsFactoringPartner to the footer, so the vendor-returns report is unaffected.
-- Also fixes a pre-existing malformed definition (missing comma before io.docstatus).

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Root(IN p_record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Root(IN p_record_id numeric)
RETURNS TABLE
	(
	ad_org_id numeric,
	displayhu text,
	DocStatus Character (2),
	isFactoringPartner character(1)
	)
AS
$$
SELECT
	io.AD_Org_ID,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM M_InOutLine iol1
			INNER JOIN M_Product p ON iol1.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol1.AD_Client_ID, iol1.AD_Org_ID)
				AND io.M_InOut_ID = iol1.M_InOut_ID AND iol1.isActive = 'Y'
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu,
	io.docstatus,
	bp.IsFactoring AS isFactoringPartner
FROM
	M_InOut io
	LEFT OUTER JOIN C_BPartner bp ON bp.C_BPartner_ID = io.C_BPartner_ID
WHERE
	io.M_InOut_ID = $1 AND io.isActive = 'Y'
$$
LANGUAGE sql STABLE;
