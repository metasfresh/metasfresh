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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_sales_customs_invoice_footer(NUMERIC,
                                                                                             character varying)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_sales_customs_invoice_footer(p_c_customs_invoice_ID NUMERIC,
                                                                                     p_ad_language          character varying)
    RETURNS TABLE
            (
                nettoTotalWeight  numeric,
                uom               character varying,
                QtyPattern        text,
                bruttoTotalWeight numeric,
                country           character varying,
                containernumber   character varying,
                sealnumber        character varying,
                Incoterms         character varying,
                ModeOfTransport   character varying
            )
    LANGUAGE 'sql'
AS
$BODY$

SELECT SUM(catchweight) AS nettoTotalWeight,
       UOM,
       QtyPattern,
       SUM(bruttweight) AS bruttoTotalWeight,
       country,
       containernumber,
       sealnumber,
       Incoterms,
       ModeOfTransport
FROM (SELECT (CASE
                  WHEN il.c_uom_id = 540017 -- harcoded kg
                      THEN il.InvoicedQty
                      ELSE uomconvert(il.m_product_id, il.c_uom_id, 540017, il.InvoicedQty)
              END)                                                                                                     AS catchweight,
             COALESCE(uomt.UOMSymbol, uom.UOMSymbol)                                                                   AS UOM,
             CASE
                 WHEN uom.StdPrecision = 0
                     THEN '#,##0'
                     ELSE SUBSTRING('#,##0.0000' FROM 0 FOR 7 + uom.StdPrecision :: integer)
             END                                                                                                       AS QtyPattern,
             de_metas_endcustomer_fresh_reports.CalculateCustom_InvoiceLine_BruttoWeight(il.C_Customs_Invoice_Line_ID) AS bruttweight,
             CASE
                 WHEN report.IsHiddenReportElement(ci.C_DocType_ID, 'Customs_Invoice_Note') = 'N' THEN
                     (SELECT COALESCE(ct_sub.name, co_sub.name)
                      FROM M_InOutLine_To_C_Customs_Invoice_Line io_to_ci_sub
                               INNER JOIN M_InOut io_sub ON io_sub.M_InOut_ID = io_to_ci_sub.M_InOut_ID
                               LEFT JOIN c_bpartner_location ilbl_sub ON ilbl_sub.c_bpartner_location_id = io_sub.c_bpartner_location_id
                               LEFT JOIN C_Location loc_sub ON ilbl_sub.c_location_id = loc_sub.c_location_id
                               LEFT JOIN C_Country co_sub ON loc_sub.c_country_id = co_sub.c_country_id
                               LEFT JOIN C_Country_Trl ct_sub ON ct_sub.c_country_id = co_sub.c_country_id AND ct_sub.ad_language = p_AD_Language
                      WHERE io_to_ci_sub.C_Customs_Invoice_Line_ID = il.C_Customs_Invoice_Line_ID
                      LIMIT 1)
             END                                                                                                       AS country,
             ci.containernumber                                                                                        AS containernumber,
             ci.sealnumber                                                                                             AS sealnumber,
             COALESCE(inc_trl.name, inc.name)                                                                          AS Incoterms,
             COALESCE(modt_trl.name, modt.name)                                                                        AS ModeOfTransport

      FROM C_Customs_Invoice_Line il
               INNER JOIN C_Customs_Invoice ci ON ci.c_customs_invoice_ID = il.c_customs_invoice_ID
               LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = 540017 -- harcoded kg
               LEFT OUTER JOIN C_UOM_Trl uomt ON uomt.c_UOM_ID = uom.C_UOM_ID AND uomt.AD_Language = p_AD_Language
               LEFT OUTER JOIN C_Incoterms inc ON ci.c_incoterms_id = inc.c_incoterms_id
               LEFT OUTER JOIN C_Incoterms_trl inc_trl ON inc.c_incoterms_id = inc_trl.c_incoterms_id AND inc_trl.ad_language = p_AD_Language
               LEFT OUTER JOIN AD_Ref_List modt ON modt.AD_Reference_ID = 542040 AND modt.value = ci.ModeOfTransport
               LEFT OUTER JOIN AD_Ref_List_Trl modt_trl ON modt.ad_ref_list_id = modt_trl.ad_ref_list_id AND modt_trl.ad_language = p_AD_Language
      WHERE il.C_Customs_Invoice_Id = p_c_customs_invoice_ID) AS d
GROUP BY UOM, QtyPattern, country, containernumber, sealnumber, Incoterms, ModeOfTransport;
$BODY$
;
