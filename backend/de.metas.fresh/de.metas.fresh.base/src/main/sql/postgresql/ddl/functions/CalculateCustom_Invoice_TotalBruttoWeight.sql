/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.CalculateCustom_Invoice_TotalBruttoWeight(numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.CalculateCustom_Invoice_TotalBruttoWeight(p_c_customs_invoice_id numeric)
    RETURNS TABLE
            (
                bruttoWeight numeric
            )
    LANGUAGE 'sql'
AS
$BODY$
select sum(productWeight + packageWeight)
from (
         select
             (case
                  when cil.c_uom_id = 540017 -- harcoded kg
                      then COALESCE(p.weight, 0) * iol.movementqty
                      else COALESCE(p.weight, 0) * il.invoicedqty end)
                                                                as productWeight,
             COALESCE(packingProd.weight * iol.qtyenteredtu, 0) as packageWeight

         from c_customs_invoice_line il
                  join m_inoutline_to_c_customs_invoice_line cil
                       on il.c_customs_invoice_line_id = cil.c_customs_invoice_line_id and il.m_product_id = cil.m_product_id
                  join m_inoutline iol on iol.m_inoutline_id=cil.m_inoutline_id and iol.m_product_id = cil.m_product_id
                  join M_product p on il.m_product_id = p.m_product_id
                  LEFT JOIN m_hu_pi_item_product pip on pip.m_hu_pi_item_product_id=iol.m_hu_pi_item_product_id
                  LEFT JOIN  m_hu_pi_item pii on pii.m_hu_pi_item_id = pip.m_hu_pi_item_id
                  LEFT JOIN m_hu_packingmaterial pp on pp.m_hu_packingmaterial_id=pii.m_hu_packingmaterial_id
                  LEFT JOIN m_product packingProd on packingProd.m_product_id=pp.m_product_id
         WHERE il.C_Customs_Invoice_ID = p_c_customs_invoice_id ) w
$BODY$;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.CalculateCustom_Invoice_TotalBruttoWeight(numeric)
    IS 'calculate total brutto weight of lines';