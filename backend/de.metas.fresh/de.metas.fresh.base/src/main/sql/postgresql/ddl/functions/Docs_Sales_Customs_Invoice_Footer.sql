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
             COALESCE(ct.name, co.name)                                                                                AS country,
             ci.containernumber                                                                                        AS containernumber,
             ci.sealnumber                                                                                             AS sealnumber,
             COALESCE(inc_trl.name, inc.name)                                                                          AS Incoterms,
             COALESCE(modt_trl.name, modt.name)                                                                        AS ModeOfTransport

      FROM C_Customs_Invoice_Line il
               INNER JOIN C_Customs_Invoice ci ON ci.c_customs_invoice_ID = il.c_customs_invoice_ID
               LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = 540017 -- harcoded kg
               LEFT OUTER JOIN C_UOM_Trl uomt ON uomt.c_UOM_ID = uom.C_UOM_ID AND uomt.AD_Language = p_AD_Language
               LEFT OUTER JOIN M_InOutLine_To_C_Customs_Invoice_Line io_to_ci ON io_to_ci.C_Customs_Invoice_Line_ID = il.C_Customs_Invoice_Line_ID
               LEFT OUTER JOIN M_InOut io ON io.M_InOut_ID = io_to_ci.M_InOut_ID
               LEFT OUTER JOIN c_bpartner_location ilbl ON ilbl.c_bpartner_location_id = io.c_bpartner_location_id
               LEFT OUTER JOIN C_Location loc ON ilbl.c_location_id = loc.c_location_id
               LEFT OUTER JOIN C_Country co ON loc.c_country_id = co.c_country_id
               LEFT OUTER JOIN C_Country_Trl ct ON ct.c_country_id = co.c_country_id AND ct.ad_language = p_AD_Language
               LEFT OUTER JOIN C_Incoterms inc ON ci.c_incoterms_id = inc.c_incoterms_id
               LEFT OUTER JOIN C_Incoterms_trl inc_trl ON inc.c_incoterms_id = inc_trl.c_incoterms_id AND inc_trl.ad_language = p_AD_Language
               LEFT OUTER JOIN AD_Ref_List modt ON modt.AD_Reference_ID = 542040 AND modt.value = ci.ModeOfTransport
               LEFT OUTER JOIN AD_Ref_List_Trl modt_trl ON modt.ad_ref_list_id = modt_trl.ad_ref_list_id AND modt_trl.ad_language = p_AD_Language
      WHERE il.C_Customs_Invoice_Id = p_c_customs_invoice_ID) AS d
GROUP BY UOM, QtyPattern, country, containernumber, sealnumber, Incoterms, ModeOfTransport;
$BODY$
;

