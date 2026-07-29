DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.getLabelCostForInvoiceLine(p_C_InvoiceLine_ID NUMERIC)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.getLabelCostForInvoiceLine(p_C_InvoiceLine_ID NUMERIC)
    RETURNS TABLE
            (
                C_InvoiceLine_ID NUMERIC,
                M_Product_ID     NUMERIC,
                label            VARCHAR,
                labelAmount      NUMERIC,
                labelUOM         VARCHAR,
                currencyLabel    VARCHAR,
                qty              NUMERIC,
                total            NUMERIC
            )
    LANGUAGE sql
    STABLE
AS
$$
SELECT base.C_InvoiceLine_ID,
       base.M_Product_ID,
       base.label,
       base.labelAmount,
       base.labelUOM,
       base.currencyLabel,
       base.qty,
       base.qty * base.labelAmount AS total
FROM (SELECT il.C_InvoiceLine_ID,
             il.M_Product_ID,
             pcl.label,
             pcl.amount                                                                                                           AS labelAmount,
             u.UOMSymbol                                                                                                          AS labelUOM,
             c.CurSymbol                                                                                                          AS currencyLabel,
             COALESCE(uomConvert(il.M_Product_ID, il.C_UOM_ID, pcl.c_uom_id, il.qtyinvoicedinpriceuom), il.qtyinvoicedinpriceuom) AS qty
      FROM c_invoiceline il
               JOIN M_Product_Labelcost pcl ON il.M_Product_ID = pcl.M_Product_ID
               JOIN C_UOM u ON pcl.C_UOM_ID = u.C_UOM_ID
               JOIN C_Currency c ON pcl.C_Currency_ID = c.C_Currency_ID
               JOIN report.fresh_Attributes att ON pcl.m_attributesetinstance_id = att.m_attributesetinstance_id
                JOIN (SELECT DISTINCT il.C_InvoiceLine_ID, att.ai_value as Attribute
                      FROM c_invoiceline il
                               JOIN m_matchinv mi ON mi.c_invoiceline_id = il.c_invoiceline_id AND mi.isActive = 'Y'
                               JOIN report.fresh_Attributes att ON mi.m_attributesetinstance_id = att.m_attributesetinstance_id
                      WHERE att.IsPrintedInDocument = 'Y'
                         AND il.C_InvoiceLine_ID = p_C_InvoiceLine_ID
                        AND il.isActive = 'Y'
      ) il_att ON att.ai_value = il_att.Attribute
      WHERE pcl.IsActive = 'Y'
        AND il.C_InvoiceLine_ID = p_C_InvoiceLine_ID) base
$$
;