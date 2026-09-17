/*
 * #%L
 * de.metas.handlingunits.base
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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.trace_report(numeric,
                                                                        numeric,
                                                                        numeric,
                                                                        numeric,
                                                                        numeric,
                                                                        numeric,
                                                                        character varying,
                                                                        numeric,
                                                                        character varying(3))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.trace_report(
    IN p_org_id                  numeric,
    IN p_period_st_id            numeric,
    IN p_period_end_id           numeric,
    IN p_activity_id             numeric,
    IN p_bpartner_id             numeric,
    IN p_product_id              numeric,
    IN p_issotrx                 character varying,
    IN p_attributesetinstance_id numeric,
    IN p_Language                Character Varying(6))
    RETURNS TABLE
            (
                dateordered     timestamp without time zone,
                documentno      character varying,
                o_bp_value      character varying,
                o_bp_name       character varying,
                o_p_value       character varying,
                o_p_name        character varying,
                attributes      text,
                price           numeric,
                total           numeric,
                currency        character,
                o_uom           character varying,
                o_qty           numeric,
                movementdate    timestamp without time zone,
                orderdocumentno character varying,
                io_bp_value     character varying,
                io_bp_name      character varying,
                io_qty          numeric,
                io_uom          character varying,
                isPOfromSO      character varying
            )
AS
$$
WITH RECURSIVE
    ---------------------------------------------------------------------------
    -- 1. TRACE MAP: Recursively find all related HU Transactions
    ---------------------------------------------------------------------------
    TraceMap AS (
        SELECT huas.Record_ID       AS start_inoutline_id,
               huas.M_TU_HU_ID      AS hu_id,
               trx.M_HU_Trx_Line_ID AS trx_id
        FROM M_HU_Assignment huas
                 LEFT JOIN M_HU_Trx_Line trx ON huas.M_TU_HU_ID = trx.M_HU_ID AND trx.isActive = 'Y'
        WHERE huas.ad_table_id = get_table_id('M_InOutLine')
          AND huas.isActive = 'Y'

        UNION

        SELECT map.start_inoutline_id,
               next_trx.M_HU_ID,
               next_trx.M_HU_Trx_Line_ID
        FROM M_HU_Trx_Line next_trx
                 INNER JOIN TraceMap map ON (
            next_trx.parent_hu_trx_line_id = map.trx_id
                OR
            (next_trx.M_HU_ID = map.hu_id AND next_trx.M_HU_Trx_Line_ID <> map.trx_id)
            )
        WHERE next_trx.qty <> 0
          AND next_trx.isActive = 'Y'),

    ResolvedAssignments AS (SELECT DISTINCT map.start_inoutline_id,
                                            target_as.Record_ID AS target_inoutline_id
                            FROM TraceMap map
                                     INNER JOIN M_HU_Assignment target_as ON target_as.M_TU_HU_ID = map.hu_id
                            WHERE target_as.ad_table_id = get_table_id('M_InOutLine')
                              AND target_as.isActive = 'Y')

SELECT a.DateOrdered, a.oDocumentNo, a.bp_value, a.bp_name, a.p_value, a.p_name, a.attributes,
       a.price, a.total, a.currency, a.uomsymbol, a.qty, a.movementdate, a.orderdocumentno,
       a.io_bp_value, a.io_bp_name, SUM(a.io_qty), a.io_uom, a.isPOfromSO
FROM (
         -- PART A: STANDARD TRACE (N) via HU History
         SELECT DISTINCT o.DateOrdered, o.DocumentNo AS ODocumentNo, bp.Value AS bp_value, bp.Name AS bp_name,
                         p.Value AS p_value, COALESCE(pt.Name, p.Name) AS p_name,
                         (SELECT attributes_value FROM de_metas_endcustomer_fresh_reports.get_attributes_value(o_iol.M_AttributeSetInstance_ID)) AS attributes,
                         ol.PriceEntered AS price, ol.linenetamt AS total, c.iso_code AS currency,
                         COALESCE(uomt.uomsymbol, uom.uomsymbol) AS uomsymbol, ol.qtyEntered AS qty,
                         c_io.movementdate, c_o.documentno AS orderdocumentno, c_bp.value AS io_bp_value,
                         c_bp.name AS io_bp_name, c_iol.qtyentered AS io_qty,
                         COALESCE(c_uomt.uomsymbol, c_uom.uomsymbol) AS io_uom, 'N'::varchar AS isPOfromSO
         FROM C_Order o
                  INNER JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
                  INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID
                  INNER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID
                  LEFT JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
                  LEFT JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_Language
                  INNER JOIN C_UOM uom ON ol.Price_UOM_ID = uom.C_UOM_ID
                  LEFT JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_Language
                  INNER JOIN C_Currency c ON ol.C_Currency_ID = c.C_Currency_ID
                  INNER JOIN C_Period per_st ON p_period_st_id = per_st.C_Period_ID
                  INNER JOIN C_Period per_end ON p_period_end_id = per_end.C_Period_ID
                  INNER JOIN M_InOutLine o_iol ON ol.C_OrderLine_ID = o_iol.C_OrderLine_ID
                  INNER JOIN M_InOut o_io ON o_iol.M_InOut_ID = o_io.M_InOut_ID
                  INNER JOIN ResolvedAssignments ra ON ra.start_inoutline_id = o_iol.M_InOutLine_ID
                  INNER JOIN M_InOutLine c_iol ON c_iol.M_InOutLine_ID = ra.target_inoutline_id
                  INNER JOIN M_InOut c_io ON c_iol.M_InOut_ID = c_io.M_InOut_ID AND c_io.isSOTrx != o_io.isSOTrx
                  INNER JOIN C_InvoiceCandidate_InOutLine iciol ON c_iol.M_InOutLine_ID = iciol.M_InOutLine_ID
                  INNER JOIN C_Invoice_Candidate ic ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
                  INNER JOIN C_OrderLine c_ol ON c_iol.C_OrderLine_ID = c_ol.C_OrderLine_ID
                  INNER JOIN C_Order c_o ON c_ol.C_Order_ID = c_o.C_Order_ID
                  INNER JOIN C_BPartner c_bp ON c_bp.C_BPartner_ID = c_io.C_BPartner_ID
                  INNER JOIN C_UOM c_uom ON ic.price_UOM_ID = c_uom.C_UOM_ID
                  LEFT JOIN C_UOM_Trl c_uomt ON c_uom.C_UOM_ID = c_uomt.C_UOM_ID AND c_uomt.AD_Language = p_Language
         WHERE o.AD_Org_ID = COALESCE(p_org_id, o.AD_Org_ID)
           AND per_st.startdate::date <= o.DateOrdered::date
           AND per_end.enddate::date >= o.DateOrdered::date
           AND (ol.C_Activity_ID = p_Activity_ID OR p_Activity_ID IS NULL)
           AND (o.C_BPartner_ID = p_bpartner_id OR p_bpartner_id IS NULL)
           AND (ol.M_Product_ID = p_product_id OR p_product_id IS NULL)
           AND o.isSOTrx = p_issotrx
           AND o.docStatus IN ('CO', 'CL')
           AND o_io.docStatus IN ('CO', 'CL')
           AND c_o.docStatus IN ('CO', 'CL')
           AND c_io.docStatus IN ('CO', 'CL')
           AND COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
           AND (CASE
                    WHEN EXISTS (SELECT 1 FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = p_attributesetinstance_id)
                          THEN (
                        EXISTS (SELECT 0 FROM report.fresh_Attributes_ConcreteADR a
                                                  INNER JOIN report.fresh_Attributes_ConcreteADR pa ON pa.M_AttributeSetInstance_ID = p_attributesetinstance_id
                            AND a.at_value = pa.at_value
                            AND (CASE WHEN a.at_value = '1000015' THEN ('%' || SUBSTRING(a.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(pa.ai_value FROM 5) || '%' OR '%' || SUBSTRING(pa.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(a.ai_value FROM 5) || '%') ELSE a.ai_value = pa.ai_value END)
                                WHERE a.M_AttributeSetInstance_ID = o_iol.M_AttributeSetInstance_ID)
                            AND NOT EXISTS (SELECT 0 FROM report.fresh_Attributes_ConcreteADR pa
                                                              LEFT JOIN report.fresh_Attributes_ConcreteADR a ON a.at_value = pa.at_value
                            AND (CASE WHEN a.at_value = '1000015' THEN ('%' || SUBSTRING(a.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(pa.ai_value FROM 5) || '%' OR '%' || SUBSTRING(pa.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(a.ai_value FROM 5) || '%') ELSE a.ai_value = pa.ai_value END)
                            AND a.M_AttributeSetInstance_ID = o_iol.M_AttributeSetInstance_ID
                                            WHERE pa.M_AttributeSetInstance_ID = p_attributesetinstance_id AND a.M_AttributeSetInstance_ID IS NULL)
                        ) ELSE TRUE END)

         UNION ALL

         -- PART B: PO FROM SO (Y) via Direct Allocation
         SELECT o.DateOrdered, o.DocumentNo, bp.Value, bp.Name, p.Value, COALESCE(pt.Name, p.Name),
                (SELECT attributes_value FROM de_metas_endcustomer_fresh_reports.get_attributes_value(ol.M_AttributeSetInstance_ID)),
                ol.PriceEntered, ol.linenetamt, c.iso_code, COALESCE(uomt.uomsymbol, uom.uomsymbol), ol.qtyEntered,
                c_o.dateordered, c_o.documentno, c_bp.value, c_bp.name, c_ol.qtyentered,
                COALESCE(c_uomt.uomsymbol, c_uom.uomsymbol), 'Y'::varchar
         FROM C_Order o
                  INNER JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
                  INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID
                  INNER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID
                  LEFT JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
                  LEFT JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_Language
                  INNER JOIN C_UOM uom ON ol.Price_UOM_ID = uom.C_UOM_ID
                  LEFT JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_Language
                  INNER JOIN C_Currency c ON ol.C_Currency_ID = c.C_Currency_ID
                  INNER JOIN C_Period per_st ON p_period_st_id = per_st.C_Period_ID
                  INNER JOIN C_Period per_end ON p_period_end_id = per_end.C_Period_ID
                  INNER JOIN C_PO_OrderLine_Alloc ol_Alloc ON (
             (o.issotrx = 'Y' AND ol.C_OrderLine_ID = ol_Alloc.c_so_orderline_id) OR
             (o.issotrx = 'N' AND ol.C_OrderLine_ID = ol_Alloc.c_po_orderline_id)
             )
                  INNER JOIN C_OrderLine c_ol ON (
             (o.issotrx = 'Y' AND c_ol.C_OrderLine_ID = ol_Alloc.c_po_orderline_id) OR
             (o.issotrx = 'N' AND c_ol.C_OrderLine_ID = ol_Alloc.c_so_orderline_id)
             )
                  INNER JOIN C_Order c_o ON c_ol.C_Order_ID = c_o.C_Order_ID
                  INNER JOIN C_BPartner c_bp ON c_bp.C_BPartner_ID = c_o.C_BPartner_ID
                  INNER JOIN C_UOM c_uom ON c_ol.price_UOM_ID = c_uom.C_UOM_ID
                  LEFT JOIN C_UOM_Trl c_uomt ON c_uom.C_UOM_ID = c_uomt.C_UOM_ID AND c_uomt.AD_Language = p_Language
         WHERE o.AD_Org_ID = COALESCE(p_org_id, o.AD_Org_ID)
           AND per_st.startdate::date <= o.DateOrdered::date
           AND per_end.enddate::date >= o.DateOrdered::date
           AND (ol.C_Activity_ID = p_Activity_ID OR p_Activity_ID IS NULL)
           AND (o.C_BPartner_ID = p_bpartner_id OR p_bpartner_id IS NULL)
           AND (ol.M_Product_ID = p_product_id OR p_product_id IS NULL)
           AND o.isSOTrx = p_issotrx
           AND o.docStatus IN ('CO', 'CL')
           AND c_o.docStatus IN ('CO', 'CL')
           AND COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
           AND (CASE
                    WHEN EXISTS (SELECT 1 FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = p_attributesetinstance_id)
                          THEN (
                        EXISTS (SELECT 0 FROM report.fresh_Attributes_ConcreteADR a
                                                  INNER JOIN report.fresh_Attributes_ConcreteADR pa ON pa.M_AttributeSetInstance_ID = p_attributesetinstance_id
                            AND a.at_value = pa.at_value
                            AND (CASE WHEN a.at_value = '1000015' THEN ('%' || SUBSTRING(a.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(pa.ai_value FROM 5) || '%' OR '%' || SUBSTRING(pa.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(a.ai_value FROM 5) || '%') ELSE a.ai_value = pa.ai_value END)
                                WHERE a.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID)
                            AND NOT EXISTS (SELECT 0 FROM report.fresh_Attributes_ConcreteADR pa
                                                              LEFT JOIN report.fresh_Attributes_ConcreteADR a ON a.at_value = pa.at_value
                            AND (CASE WHEN a.at_value = '1000015' THEN ('%' || SUBSTRING(a.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(pa.ai_value FROM 5) || '%' OR '%' || SUBSTRING(pa.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(a.ai_value FROM 5) || '%') ELSE a.ai_value = pa.ai_value END)
                            AND a.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID
                                            WHERE pa.M_AttributeSetInstance_ID = p_attributesetinstance_id AND a.M_AttributeSetInstance_ID IS NULL)
                        ) ELSE TRUE END)
     ) a
GROUP BY a.DateOrdered, a.oDocumentNo, a.bp_value, a.bp_name, a.p_value, a.p_name, a.attributes,
         a.price, a.total, a.currency, a.uomsymbol, a.qty, a.movementdate, a.orderdocumentno,
         a.io_bp_value, a.io_bp_name, a.io_uom, a.isPOfromSO
ORDER BY a.DateOrdered, a.oDocumentNo, a.bp_value, a.p_value, a.movementdate, a.orderdocumentno, a.io_bp_value;
$$
    LANGUAGE sql STABLE;

DROP FUNCTION IF EXISTS "de.metas.handlingunits".hu_assigment_tracking(in m_hu_assignment_id numeric);
DROP FUNCTION IF EXISTS "de.metas.handlingunits".recursive_hu_trace(trxline integer);
DROP FUNCTION IF EXISTS "de.metas.handlingunits".recursive_hu_trace_sub(trxline integer[]);
