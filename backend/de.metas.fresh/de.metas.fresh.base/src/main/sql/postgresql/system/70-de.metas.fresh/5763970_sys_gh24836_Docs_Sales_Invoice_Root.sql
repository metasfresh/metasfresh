DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Root (IN p_record_id numeric, IN p_ad_language Character Varying (6));
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Root (IN p_record_id numeric, IN p_ad_language Character Varying (6))
RETURNS TABLE 
	(AD_Org_ID numeric,
	DocStatus character(2),
	PrintName character varying(60),
	countrycode character(2),
	C_Currency_ID numeric,
	displayhu text,
	isCreditMemo character(1)
	)
AS
$$	
SELECT
	i.AD_Org_ID,
	i.DocStatus,
	dt.PrintName,
	c.countrycode,
	i.C_Currency_ID,
	CASE
		WHEN
		EXISTS(
			SELECT il.M_HU_PI_Item_Product_ID
			FROM C_InvoiceLine il
			WHERE il.C_Invoice_ID = i.C_Invoice_ID
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu,
	CASE WHEN dt.docbasetype = 'ARC'
		THEN 'Y'
		ELSE 'N'
	END AS isCreditMemo
FROM
	C_Invoice i
	INNER JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID
	LEFT OUTER JOIN C_DocType_Trl dtt ON i.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_ad_language

	LEFT OUTER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_id = i.C_BPartner_Location_ID 
	LEFT OUTER JOIN C_Location l ON l.C_Location_ID = bpl.C_Location_ID 
	LEFT OUTER JOIN C_Country c ON c.C_Country_ID = l.C_Country_ID 
WHERE
	i.C_Invoice_ID = p_record_id
$$
LANGUAGE sql STABLE	
;
