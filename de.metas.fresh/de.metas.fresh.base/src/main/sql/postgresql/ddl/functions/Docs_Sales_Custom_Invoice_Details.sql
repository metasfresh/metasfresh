DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Customs_Invoice_Details (IN p_C_Customs_Invoice_ID numeric, IN p_AD_Language Character Varying(6));
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Customs_Invoice_Details(IN p_C_Customs_Invoice_ID numeric,
                                                                                                 IN p_AD_Language Character Varying(6))
    RETURNS TABLE
            (
                Name          character varying,
                InvoicedQty   numeric,
                UOM           character varying(10),
                linenetamt    numeric,
                CustomsTariff character varying,
                cursymbol     character varying(10),
                InvoiceDocNo  character varying
            )
AS
$$
SELECT ct.Name,
       sum(
               coalesce((case
                             when qtydeliveredcatch is not null
                                 then qtydeliveredcatch
                             else uomConvert(iol.M_Product_ID, iol.C_UOM_ID, iol.catch_uom_id, iol.qtyentered)
                   end), 0))                   AS InvoicedQty,
       COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOM,
       sum(il.linenetamt)                      AS linenetamt,
       ct.value                                AS CustomsTariff,
       c.cursymbol,
       i.DocumentNo                            as CustomInvoiceDocNo


FROM C_Customs_Invoice_Line il
         INNER JOIN C_Customs_Invoice i ON il.C_Customs_Invoice_ID = i.C_Customs_Invoice_ID
         INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID

         INNER JOIN M_Inoutline iol on iol.c_customs_invoice_line_id = il.c_customs_invoice_line_id

    -- Get Product and its M_CustomsTariff
         LEFT OUTER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID
         LEFT OUTER JOIN M_CustomsTariff ct ON ct.M_CustomsTariff_ID = p.M_CustomsTariff_ID

    -- Get Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = iol.catch_uom_id
         LEFT OUTER JOIN C_UOM_Trl uomt
                         ON iol.catch_uom_id = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language AND uomt.isActive = 'Y'

         LEFT OUTER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID


WHERE il.C_Customs_Invoice_ID = p_C_Customs_Invoice_ID
GROUP BY ct.Name, ct.value, COALESCE(uomt.UOMSymbol, uom.UOMSymbol), c.cursymbol, i.DocumentNo, ct.Seqno
ORDER BY ct.Seqno, ct.value, ct.Name

$$
    LANGUAGE sql
    STABLE;