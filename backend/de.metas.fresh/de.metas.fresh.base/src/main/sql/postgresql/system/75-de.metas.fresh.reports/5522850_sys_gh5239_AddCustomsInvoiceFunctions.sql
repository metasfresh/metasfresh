DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Custom_Invoice_Root ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Custom_Invoice_Root ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
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




DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Custom_Invoice_Details ( IN p_C_Customs_Invoice_ID numeric, IN p_AD_Language Character Varying(6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Custom_Invoice_Details(
  IN p_C_Customs_Invoice_ID numeric,
  IN p_AD_Language          Character Varying(6))
  RETURNS TABLE
  (docType         character varying,
   documentno      character varying,
   MovementDate    timestamp without time zone,
   lineno          numeric(10, 0),
   Name            character varying,
   InvoicedQty     numeric,
   UOM             character varying(10),
   linenetamt      numeric,
   bp_product_no   character varying(30),
   bp_product_name character varying(100),
   p_value         character varying(40),
   p_description   character varying(255),
   cursymbol       character varying(10),
   InvoiceDocNo    character varying,
   iol_movementqty numeric,
   ol_priceactual  numeric,
   m_inout_id      numeric
  )
AS
$$
SELECT
  COALESCE(dtt.Printname, dt.Printname)                  as docType,
  io.documentno,
  io.MovementDate,
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
  c.cursymbol,
  i.DocumentNo                                           as CustomInvoiceDocNo,
  iol.movementqty,
  iol.amt,
  iol.m_inout_id
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

  -- Get shipment details
  INNER JOIN
  (
    SELECT
      iol.c_customs_invoice_line_id,
      io.m_inout_id,
      sum(iol.movementqty)                  as movementqty,
      sum(ol.priceactual * iol.movementqty) as amt
    FROM M_InoutLine iol
      JOIN M_Inout io on io.M_InOut_id = iol.M_InOut_ID
      INNER JOIN c_orderline ol on iol.c_orderline_id = ol.c_orderline_id
    group by io.m_inout_id, iol.c_customs_invoice_line_id

  ) as iol on iol.c_customs_invoice_line_id = il.c_customs_invoice_line_id
  INNER JOIN M_Inout io on io.M_Inout_id = iol.M_Inout_id

  LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
  LEFT OUTER JOIN C_DocType_Trl dtt
    ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'

WHERE
  il.C_Customs_Invoice_ID = p_C_Customs_Invoice_ID

ORDER BY
  lineno

$$
LANGUAGE sql
STABLE;


DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Custom_Invoice_Description ( IN C_Invoice_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Custom_Invoice_Description ( IN C_Invoice_ID numeric, IN AD_Language Character Varying (6) )
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


-- Function: de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report(numeric, text, numeric, numeric)

-- DROP FUNCTION de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report(numeric, text, numeric, numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report(
    org_id numeric,
    doctype text,
    bp_loc_id numeric,
    record_id numeric)
  RETURNS SETOF de_metas_endcustomer_fresh_reports.docs_generics_bpartner_report AS
$BODY$
SELECT
	(
		SELECT
			trim(
				COALESCE ( org_bp.name || ', ', '' ) ||
				COALESCE ( loc.address1 || ' ', '' ) ||
				COALESCE ( loc.postal || ' ', '' ) ||
				COALESCE ( loc.city, '' )
			)as org_addressline
		FROM
			c_bpartner org_bp
			JOIN c_bpartner_location org_bpl 	ON org_bp.c_bpartner_id	= org_bpl.c_bpartner_id AND org_bpl.isActive = 'Y'
			JOIN c_location loc	 		ON org_bpl.c_location_id	= loc.c_location_id AND loc.isActive = 'Y'
		WHERE
			org_bp.ad_orgbp_id = $1 AND org_bp.isActive = 'Y'
			and org_bpl.isbillto = 'Y'
		LIMIT 1
	)as org_addressline,

	CASE
		WHEN $2 = 'o' THEN o.BPartnerAddress
		WHEN $2 = 'o_delivery' THEN o.DeliveryToAddress
		WHEN $2 = 'io' THEN io.BPartnerAddress
		WHEN $2 = 'freshio' THEN freshio.BPartnerAddress
		WHEN $2 = 'i' THEN i.BPartnerAddress
		WHEN $2 = 'l' THEN tl.BPartnerAddress
		WHEN $2 = 'lt' THEN COALESCE(bpg.name||' ', '' ) || COALESCE(bp.name||' ', '') || COALESCE(bp.name2||E'\n', E'\n') || COALESCE( letter.BPartnerAddress, '' )
		WHEN $2 = 'd' THEN d.BPartnerAddress
		WHEN $2 = 'rfqr' THEN COALESCE(bprfqr.name||E'\n', '') || COALESCE( bplrfqr.address, '' )
		WHEN $2 = 'ft' THEN COALESCE(bpft.name||E'\n', '') || COALESCE( bplft.address, '' )
		WHEN $2 = 'mkt' THEN COALESCE(mktbp.name||E'\n', '') || COALESCE( mktbpl.address, '' )
		WHEN $2 = 'ci' THEN COALESCE(cibp.name||E'\n', '') || COALESCE( cibpl.address, '' )
		WHEN $3 IS NOT NULL THEN
			COALESCE(bp.name||E'\n', '') || COALESCE( bpl.address, '' )
		ELSE 'Incompatible Parameter!'
	END || E'\n' AS addressblock
FROM
	(SELECT '') x
	LEFT JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = $3 AND bpl.isActive = 'Y'
	LEFT JOIN C_BPartner bp	ON bp.C_BPartner_ID = bpl.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT JOIN C_Greeting bpg ON bp.C_Greeting_id = bpg.C_Greeting_ID AND bpg.isActive = 'Y'
	LEFT JOIN C_Location l ON bpl.C_Location_id = l.C_Location_ID AND l.isActive = 'Y'
	LEFT JOIN C_Country lcou ON l.C_Country_id = lcou.C_Country_ID AND lcou.isActive = 'Y'
	LEFT JOIN C_Region r ON l.C_Region_id = r.C_Region_ID AND r.isActive = 'Y'

	LEFT JOIN C_Order o ON o.C_Order_id = $4 AND o.isActive = 'Y'
	LEFT JOIN C_Invoice i ON i.C_Invoice_id = $4 AND i.isActive = 'Y'
	LEFT JOIN T_Letter_Spool tl ON tl.AD_Pinstance_ID = $4 AND tl.isActive = 'Y'
	LEFT JOIN C_Letter letter ON letter.C_Letter_ID = $4 AND letter.isActive = 'Y'
	LEFT JOIN M_InOut io ON io.M_InOut_ID = $4 AND io.isActive = 'Y'

	LEFT JOIN C_Flatrate_Term ft ON ft.C_Flatrate_Term_ID = $4 AND ft.isActive = 'Y'
	LEFT JOIN C_BPartner_Location bplft ON bplft.C_BPartner_Location_ID = ft.Bill_location_ID AND bplft.isActive = 'Y'
	LEFT JOIN C_BPartner bpft ON bpft.C_BPartner_ID = ft.Bill_BPartner_ID AND bpft.isActive = 'Y'
	-- fresh specific: Retrieve an address for all receipt lines linked to an order line
	-- Note: This approach is used for purchase transactions only. Sales transactions are retrieved like always
	-- Note: Empties Receipts are also work the regular way
	LEFT JOIN C_Orderline ol ON ol.C_OrderLine_ID = $4 AND ol.isActive = 'Y'
	-- Retrieve 1 (random) in out linked to the given order line
	-- We assume that the the BPartner address is not changed in between. (backed with pomo)
	LEFT JOIN (
		SELECT 	rs.Record_ID, Max(iol.M_InOut_ID) AS M_InOut_ID
		FROM 	M_ReceiptSchedule rs
			JOIN M_ReceiptSchedule_Alloc rsa ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID AND rsa.isActive = 'Y'
			JOIN M_InOutLine iol ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
		WHERE	AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_OrderLine' AND isActive = 'Y') AND rs.isActive = 'Y'
		GROUP BY	rs.Record_ID
	) io_id ON io_id.Record_ID = ol.C_OrderLine_ID
	LEFT JOIN M_InOut freshio 		ON io_id.M_InOut_ID = freshio.M_InOut_ID AND freshio.isActive = 'Y'
	LEFT JOIN C_DunningDoc d ON d.C_DunningDoc_ID = $4 AND d.isActive = 'Y'
	LEFT JOIN C_RfQResponse rfqr ON rfqr.C_RfQResponse_ID = $4 AND rfqr.isActive = 'Y'
	LEFT JOIN C_BPartner_Location bplrfqr ON bplrfqr.C_BPartner_Location_ID = rfqr.C_bpartner_location_ID AND bplrfqr.isActive = 'Y'
	LEFT JOIN C_BPartner bprfqr ON bprfqr.C_BPartner_ID = rfqr.C_BPartner_ID AND bprfqr.isActive = 'Y'
	
    -- extract marketing contact address
	LEFT JOIN mktg_contactperson mkt  on mkt.mktg_contactperson_id = $4 AND mkt.isActive = 'Y'
    LEFT JOIN c_bpartner_location mktbpl on mkt.c_bpartner_location_id = mktbpl.c_bpartner_location_id AND mktbpl.isActive = 'Y'
	LEFT JOIN C_BPartner mktbp ON mktbp.C_BPartner_ID = mktbpl.C_BPartner_ID AND mktbp.isActive = 'Y'
	
	-- extract customs invoice address
	LEFT JOIN C_Customs_Invoice ci  on ci.C_Customs_Invoice_id = $4 AND ci.isActive = 'Y'
    LEFT JOIN c_bpartner_location cibpl on ci.c_bpartner_location_id = cibpl.c_bpartner_location_id AND cibpl.isActive = 'Y'
	LEFT JOIN C_BPartner cibp ON cibp.C_BPartner_ID = cibpl.C_BPartner_ID AND cibp.isActive = 'Y'
$BODY$
  LANGUAGE sql STABLE
;