DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_sales_customs_invoice_details(numeric, character varying);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_sales_customs_invoice_details(p_c_customs_invoice_id numeric,
                                                                                                 p_ad_language character varying)
    RETURNS TABLE
            (
                name          character varying,
                invoicedqty   numeric,
                uom           character varying,
                linenetamt    numeric,
                customstariff character varying,
                cursymbol     character varying,
                invoicedocno  character varying,
                QtyPattern    text
            )
    LANGUAGE 'sql'
AS
$BODY$

SELECT ct.Name,
       SUM(il.InvoicedQty),
       COALESCE(uomt.UOMSymbol, uom.UOMSymbol)                                        AS UOM,
       SUM(il.linenetamt)                                                             AS linenetamt,
       ct.value                                                                       AS CustomsTariff,
       c.cursymbol,
       i.DocumentNo                                                                   as CustomInvoiceDocNo,
       CASE
           WHEN uom.StdPrecision = 0
               THEN '#,##0'
           ELSE Substring('#,##0.0000' FROM 0 FOR 7 + uom.StdPrecision :: integer) END AS QtyPattern

FROM C_Customs_Invoice_Line il
         INNER JOIN C_Customs_Invoice i ON il.C_Customs_Invoice_ID = i.C_Customs_Invoice_ID
         INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID

    -- Get Product and its M_CustomsTariff
         LEFT OUTER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID
         LEFT OUTER JOIN M_CustomsTariff ct ON ct.M_CustomsTariff_ID = p.M_CustomsTariff_ID

    -- Get Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = il.c_uom_id
         LEFT OUTER JOIN C_UOM_Trl uomt
                         ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language AND uomt.isActive = 'Y'

         LEFT OUTER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID

WHERE il.C_Customs_Invoice_ID = p_C_Customs_Invoice_ID
GROUP BY ct.Name, ct.value, COALESCE(uomt.UOMSymbol, uom.UOMSymbol), uom.StdPrecision, c.cursymbol, i.DocumentNo,
         ct.Seqno
ORDER BY ct.Seqno, ct.value, ct.Name

$BODY$;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.docs_sales_customs_invoice_details(numeric, character varying)
    IS 'Groups and aggregates C_Customs_Invoice_Lines by customs tarif name';
