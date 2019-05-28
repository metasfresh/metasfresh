

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