
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.CalculateCustom_InvoiceLine_BruttoWeight( NUMERIC);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.CalculateCustom_InvoiceLine_BruttoWeight(p_c_customs_invoice_line_ID NUMERIC)
    RETURNS  numeric
    LANGUAGE 'sql'
AS
$BODY$

select sum(productWeight + packageWeight)
from (
         select
             (case
                  when il.c_uom_id = 540017 -- harcoded kg
                      then (il.invoicedqty + iol.movementqty * (COALESCE(p.weight, 0) - COALESCE(p.netweight, 0)  ) ) -- GROSS FOR WEIGHT ARTICLE = CATCH WEIGHT + (DELIVERED_QUANTITY * (GROSS WEIGHT - NET WEIGHT))
                      else COALESCE(p.weight, 0) * il.invoicedqty end)
                                                               as productWeight,
             COALESCE(packingProd.weight,0) * iol.qtyenteredtu as packageWeight

         from c_customs_invoice_line il
                  join m_inoutline_to_c_customs_invoice_line cil
                       on il.c_customs_invoice_line_id = cil.c_customs_invoice_line_id and il.m_product_id = cil.m_product_id
                  join m_inoutline iol on iol.m_inoutline_id=cil.m_inoutline_id and iol.m_product_id = cil.m_product_id
                  join M_product p on il.m_product_id = p.m_product_id
                  LEFT JOIN m_hu_pi_item_product pip on pip.m_hu_pi_item_product_id=iol.m_hu_pi_item_product_id
                  LEFT JOIN  m_hu_pi_item pii on pii.m_hu_pi_item_id = pip.m_hu_pi_item_id
                  LEFT JOIN m_hu_packingmaterial pp on pp.m_hu_packingmaterial_id=pii.m_hu_packingmaterial_id
                  LEFT JOIN m_product packingProd on packingProd.m_product_id=pp.m_product_id
         WHERE  il.c_customs_invoice_line_id = p_c_customs_invoice_line_ID) w
$BODY$;


COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.CalculateCustom_InvoiceLine_BruttoWeight(numeric)
    IS 'calculate brutto weight of the given line';