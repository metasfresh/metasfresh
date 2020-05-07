DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Custom_Invoice_Description ( IN C_Invoice_ID numeric, IN AD_Language Character Varying (6) );
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Custom_Invoice_Root ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Custom_Invoice_Details ( IN p_C_Customs_Invoice_ID numeric, IN p_AD_Language Character Varying(6) );


DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Customs_Invoice_Details ( IN p_C_Customs_Invoice_ID numeric, IN p_AD_Language Character Varying(6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Customs_Invoice_Details(
  IN p_C_Customs_Invoice_ID numeric,
  IN p_AD_Language          Character Varying(6))
  RETURNS TABLE
  (lineno          numeric(10, 0),
   Name            character varying,
   InvoicedQty     numeric,
   UOM             character varying(10),
   linenetamt      numeric,
   bp_product_no   character varying(30),
   bp_product_name character varying(100),
   p_value         character varying(40),
   p_description   character varying(255),
   CustomsTariff   character varying,
   cursymbol       character varying(10),
   InvoiceDocNo    character varying
  )
AS
$$
SELECT
  il.lineno,
  COALESCE(pt.name, p.name)                              AS Name,
  il.InvoicedQty,
  COALESCE(uomt.UOMSymbol, uom.UOMSymbol)                AS UOM,
  il.linenetamt,
  -- in case there is no C_BPartner_Product, fallback to the default ones
  COALESCE(NULLIF(bpp.ProductNo, ''), p.value)           as bp_product_no,
  COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) as bp_product_name,
  p.value                                                AS p_value,
  p.description                                          AS p_description,
  p.CustomsTariff,
  c.cursymbol,
  i.DocumentNo                                           as CustomInvoiceDocNo
FROM
  C_Customs_Invoice_Line il
  INNER JOIN C_Customs_Invoice i ON il.C_Customs_Invoice_ID = i.C_Customs_Invoice_ID
  INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID
  LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID

  -- Get Product and its translation
  LEFT OUTER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID
  LEFT OUTER JOIN M_Product_Trl pt
    ON il.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_AD_Language AND pt.isActive = 'Y'

  -- Get Unit of measurement and its translation
  LEFT OUTER JOIN C_UOM uom ON il.C_UOM_ID = uom.C_UOM_ID
  LEFT OUTER JOIN C_UOM_Trl uomt
    ON il.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language AND uomt.isActive = 'Y'

  LEFT OUTER JOIN
      de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(p.M_Product_ID, bp.C_BPartner_ID, null) as bpp
    on 1 = 1

  LEFT OUTER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID
  
WHERE
  il.C_Customs_Invoice_ID = p_C_Customs_Invoice_ID

ORDER BY
  lineno

$$
LANGUAGE sql
STABLE;



CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Customs_Invoice_Description ( IN C_Invoice_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE 
	(documentno character varying(30),
	dateinvoiced timestamp without time zone,
	VATaxID character varying(60),
	bp_value character varying(40),
	cont_name text,
	cont_phone character varying(40),
	cont_fax character varying(40),
	cont_email character varying(60),
	PrintName character varying(60)
	)
AS
$$	
SELECT
	i.documentno 	as documentno,
	i.dateinvoiced	as dateinvoiced,
	bp.VATaxID,
	bp.value	as bp_value,
	Coalesce(cogr.name, '')||
	Coalesce(' ' || cont.title, '') ||
	Coalesce(' ' || cont.firstName, '') ||
	Coalesce(' ' || cont.lastName, '') as cont_name,
	cont.phone	as cont_phone,
	cont.fax	as cont_fax,
	cont.email	as cont_email,
	COALESCE(dtt.PrintName, dt.PrintName) AS PrintName
FROM
	C_Customs_Invoice i
	JOIN C_BPartner bp 		ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
		LEFT JOIN AD_User cont	ON i.AD_User_ID = cont.AD_User_ID AND cont.isActive = 'Y'
	LEFT JOIN C_Greeting cogr	ON cont.C_Greeting_ID = cogr.C_Greeting_ID AND cogr.isActive = 'Y'
	LEFT OUTER JOIN C_DocType dt ON i.c_doctype_id = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt ON i.c_doctype_id = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
	LEFT JOIN C_Customs_Invoice_Line il on il.C_Customs_Invoice_id=i.C_Customs_Invoice_ID
WHERE
	i.C_Customs_Invoice_id = $1
$$
LANGUAGE sql STABLE	
;


CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Customs_Invoice_Root ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE 
	(AD_Org_ID numeric,
	DocStatus character(2),
	PrintName character varying(60),
	countrycode character(2),
	displayhu text
	)
AS
$$	
SELECT
	i.AD_Org_ID,
	i.DocStatus,
	dt.PrintName,
	c.countrycode,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM C_Customs_Invoice_Line il
			INNER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID)
			AND il.C_Customs_Invoice_ID = i.C_Customs_Invoice_ID AND il.isActive = 'Y'
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu
FROM
	C_Customs_Invoice  i
	INNER JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt ON i.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'

	LEFT OUTER JOIN AD_OrgInfo orginfo ON orginfo.ad_org_id = i.ad_org_id AND orginfo.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner_Location org_loc ON orginfo.Orgbp_Location_ID = org_loc.C_BPartner_Location_ID AND org_loc.isActive = 'Y'
	LEFT OUTER JOIN C_Location org_l ON org_loc.C_Location_ID = org_l.C_Location_ID AND org_l.isActive = 'Y'
	LEFT OUTER JOIN C_Country c ON org_l.C_Country_ID = c.C_Country_ID AND c.isActive = 'Y'
WHERE
	i.C_Customs_Invoice_ID = $1 AND i.isActive = 'Y'
$$
LANGUAGE sql STABLE	
;