DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Root(IN record_id numeric, IN ad_language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Root(IN record_id numeric, IN ad_language Character Varying (6))
RETURNS TABLE 
	(
	ad_org_id numeric(10,0),
	docstatus character(2),
	printname character varying(60),
	C_Currency_ID numeric,
	poreference varchar(60),
	displayhu text,
	isoffer character(1),
	isprepay character(1)
	)
AS
$$	

SELECT
	o.AD_Org_ID,
	o.DocStatus,
	dt.PrintName,
	o.C_Currency_ID,
	poreference,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM C_OrderLine ol
			INNER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
			AND ol.C_Order_ID = o.C_Order_ID AND ol.isActive = 'Y'
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu,
		CASE WHEN dt.docbasetype = 'SOO' AND dt.docsubtype IN ('ON', 'OB')
		THEN 'Y'
		ELSE 'N'
	END AS isoffer,
	CASE WHEN dt.docbasetype = 'SOO' AND dt.docsubtype ='PR'
		THEN 'Y'
		ELSE 'N'
	END AS isprepay
FROM
	C_Order o
	INNER JOIN C_DocType dt ON o.C_DocTypeTarget_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt ON o.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
WHERE
	o.C_Order_ID = $1 AND o.isActive = 'Y'
$$
LANGUAGE sql STABLE
;