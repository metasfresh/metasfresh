
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.CalculateCustom_InvoiceLine_BruttoWeight(NUMERIC)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.CalculateCustom_InvoiceLine_BruttoWeight(p_c_customs_invoice_line_ID NUMERIC)
    RETURNS numeric
    LANGUAGE 'sql'
AS
$BODY$

SELECT SUM(packageWeight +
           (CASE
                WHEN c_uom_id = 540017 -- harcoded kg
                    THEN (invoicedqty + computedProductWeight) -- GROSS FOR WEIGHT ARTICLE = CATCH WEIGHT + (DELIVERED_QUANTITY * (GROSS WEIGHT - NET WEIGHT))
                    ELSE COALESCE(weight, 0) * invoicedqty
            END)
           ) AS totalWeight

FROM (
         SELECT SUM(packageWeight)         AS packageWeight,
                SUM(computedProductWeight) AS computedProductWeight,
                invoicedqty,
                weight,
                c_uom_id
         FROM (
                  SELECT (CASE
                              WHEN il.c_uom_id = 540017 -- harcoded kg
                                  THEN iol.movementqty * (COALESCE(p.weight, 0) - COALESCE(p.netweight, 0))
                                  ELSE 0
                          END)
                                                                            AS computedProductWeight,
                         COALESCE(packingProd.weight, 0) * iol.qtyenteredtu AS packageWeight,
                         il.c_uom_id,
                         il.invoicedqty,
                         p.weight

                  FROM c_customs_invoice_line il
                           JOIN m_inoutline_to_c_customs_invoice_line cil
                                ON il.c_customs_invoice_line_id = cil.c_customs_invoice_line_id AND il.m_product_id = cil.m_product_id
                           JOIN m_inoutline iol ON iol.m_inoutline_id = cil.m_inoutline_id AND iol.m_product_id = cil.m_product_id
                           JOIN M_product p ON il.m_product_id = p.m_product_id
                           LEFT JOIN m_hu_pi_item_product pip ON pip.m_hu_pi_item_product_id = iol.m_hu_pi_item_product_id
                           LEFT JOIN m_hu_pi_item pii ON pii.m_hu_pi_item_id = pip.m_hu_pi_item_id
                           LEFT JOIN m_hu_packingmaterial pp ON pp.m_hu_packingmaterial_id = pii.m_hu_packingmaterial_id
                           LEFT JOIN m_product packingProd ON packingProd.m_product_id = pp.m_product_id
                  WHERE il.c_customs_invoice_line_id = p_c_customs_invoice_line_ID) w
         GROUP BY invoicedqty,
                  weight,
                  c_uom_id
     ) AS d
$BODY$
;


COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.CalculateCustom_InvoiceLine_BruttoWeight(numeric)
    IS 'calculate brutto weight of the given line'
;
