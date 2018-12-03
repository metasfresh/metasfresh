-- Function: de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(numeric, numeric, character varying)

-- DROP FUNCTION de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(numeric, numeric, character varying);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(IN M_Product_id numeric, IN C_BPartner_ID numeric, IN attributes text)
  RETURNS TABLE(ProductNo character varying, ProductName character varying, C_BPartner_ID numeric) AS
$BODY$
	SELECT ProductNo,ProductName, C_BPartner_ID
	FROM
		(
		   SELECT bpp.ProductNo, bpp.ProductName, bpp.M_Product_ID, bpp.C_BPartner_ID, bpp.M_AttributeSetInstance_ID,seqno,
				String_agg ( ratt.ai_value, ', ' ORDER BY length(ratt.ai_value)) as bppAttributes
		   FROM C_BPartner_Product bpp 
		   LEFT OUTER JOIN Report.fresh_Attributes ratt ON bpp.M_AttributeSetInstance_ID = ratt.M_AttributeSetInstance_ID
		   WHERE bpp.isActive = 'Y'
		   GROUP BY bpp.ProductNo, bpp.ProductName, bpp.M_Product_ID, bpp.C_BPartner_ID, bpp.M_AttributeSetInstance_ID, seqno
		   ORDER by bpp.M_AttributeSetInstance_ID DESC, seqno
		) bpp
WHERE bpp.M_Product_ID = $1 AND bpp.C_BPartner_ID = $2 AND (bpp.bppAttributes = $3 OR bpp.bppAttributes is null)
Limit 1
$BODY$
LANGUAGE sql STABLE;
