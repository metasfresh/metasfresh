/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_sales_customs_invoice_details(numeric,
                                                                                              character varying)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_sales_customs_invoice_details(p_c_customs_invoice_id numeric,
                                                                                                 p_ad_language          character varying)
    RETURNS TABLE
            (
                name          character varying,
                invoicedqty   numeric,
                uom           character varying,
                linenetamt    numeric,
                customstariff character varying,
                cursymbol     character varying,
                invoicedocno  character varying,
                QtyPattern    text,
                bruttoweight  numeric,
                description   text,
                sscc18        character varying,
                PriceActual   numeric
            )
    LANGUAGE 'sql'
AS
$BODY$
SELECT data.Name,
       SUM(data.InvoicedQty)  AS InvoicedQty,
       data.UOM,
       SUM(data.linenetamt)   AS linenetamt,
       data.CustomsTariff,
       data.cursymbol,
       data.CustomInvoiceDocNo,
       data.QtyPattern,
       SUM(data.bruttoweight) AS bruttoweight,
       data.Description,
       data.SSCC18,
       ROUND(SUM(data.linenetamt) / NULLIF(SUM(data.InvoicedQty), 0), 2)
FROM (SELECT COALESCE(ct.Name, ct.Value, p.Value)                                                                      AS Name,
             il.InvoicedQty,
             COALESCE(uomt.UOMSymbol, uom.UOMSymbol)                                                                   AS UOM,
             il.linenetamt,
             ct.value                                                                                                  AS CustomsTariff,
             c.cursymbol,
             i.DocumentNo                                                                                              AS CustomInvoiceDocNo,
             CASE
                 WHEN uom.StdPrecision = 0
                     THEN '#,##0'
                     ELSE SUBSTRING('#,##0.0000' FROM 0 FOR 7 + uom.StdPrecision :: integer)
             END                                                                                                       AS QtyPattern,
             de_metas_endcustomer_fresh_reports.CalculateCustom_InvoiceLine_BruttoWeight(il.c_customs_invoice_line_id) AS bruttoweight,
             ct.description                                                                                            AS Description,
             COALESCE(il.SSCC18_Override, getCustoms_Invoice_Line_SSCC18(il.C_Customs_Invoice_Line_ID))                AS SSCC18

      FROM C_Customs_Invoice_Line il
               INNER JOIN C_Customs_Invoice i ON il.C_Customs_Invoice_ID = i.C_Customs_Invoice_ID
               INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID

          -- Get Product and its M_CustomsTariff
               LEFT OUTER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID
               LEFT OUTER JOIN M_CustomsTariff ct ON ct.M_CustomsTariff_ID = p.M_CustomsTariff_ID

          -- Get Unit of measurement and its translation
               LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = il.c_uom_id
               LEFT OUTER JOIN C_UOM_Trl uomt
                               ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language

               LEFT OUTER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID
      WHERE il.C_Customs_Invoice_ID = p_C_Customs_Invoice_ID
      GROUP BY ct.Name, ct.value, COALESCE(uomt.UOMSymbol, uom.UOMSymbol), uom.StdPrecision, c.cursymbol, i.DocumentNo,
               ct.Seqno, il.c_customs_invoice_line_id, p.Value, ct.description
      ORDER BY ct.Seqno, ct.value, ct.Name) AS data
GROUP BY data.Name, data.CustomsTariff, data.UOM, data.QtyPattern, data.cursymbol, data.CustomInvoiceDocNo, data.Description, data.SSCC18
ORDER BY data.CustomsTariff, data.Name

$BODY$
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.docs_sales_customs_invoice_details(numeric, character varying)
    IS 'Groups and aggregates C_Customs_Invoice_Lines by customs tarif name'
;
