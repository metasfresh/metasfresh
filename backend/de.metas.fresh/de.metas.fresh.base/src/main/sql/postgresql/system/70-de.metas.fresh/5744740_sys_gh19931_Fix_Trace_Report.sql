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

SELECT a.DateOrdered,
       a.oDocumentNo,
       a.bp_value,
       a.bp_name,
       a.p_value,
       a.p_name,
       a.attributes,
       a.price,
       a.total,
       a.currency,
       a.uomsymbol,
       a.qty,

       --inout part
       a.movementdate,
       a.orderdocumentno,
       a.io_bp_value,
       a.io_bp_name,
       SUM(a.io_qty),
       a.io_uom,
       'N' AS isPOfromSO
FROM (SELECT DISTINCT o.DateOrdered,
                      o.DocumentNo                                                                                    AS ODocumentNo,
                      bp.Value                                                                                        AS bp_value,
                      bp.Name                                                                                         AS bp_name,
                      p.Value                                                                                         AS p_value,
                      COALESCE(pt.Name, p.Name)                                                                       AS p_name,
                      (SELECT attributes_value
                       FROM de_metas_endcustomer_fresh_reports.get_attributes_value(o_iol.M_AttributeSetInstance_ID)) AS attributes,
                      ol.PriceEntered                                                                                 AS price,
                      ol.linenetamt                                                                                   AS total,
                      c.iso_code                                                                                      AS currency,
                      COALESCE(uomt.uomsymbol, uom.uomsymbol)                                                         AS uomsymbol,
                      ol.qtyEntered                                                                                   AS qty,

                      --inout part
                      c_io.movementdate,
                      c_o.documentno                                                                                  AS orderdocumentno,
                      c_bp.value                                                                                      AS io_bp_value,
                      c_bp.name                                                                                       AS io_bp_name,
                      c_iol.qtyentered                                                                                AS io_qty,
                      COALESCE(c_uomt.uomsymbol, c_uom.uomsymbol)                                                     AS io_uom,

                      --order and sales inout docno (for tracing purpose). Not used in reports
                      c_io.DocumentNo                                                                                 AS counterdocno


      FROM C_Order o

               INNER JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
               INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID
               INNER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID
               LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_Language

               LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
               INNER JOIN C_UOM uom ON ol.Price_UOM_ID = uom.C_UOM_ID
               LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_Language
               INNER JOIN C_Currency c ON ol.C_Currency_ID = c.C_Currency_ID


               INNER JOIN C_Period per_st ON p_period_st_id = per_st.C_Period_ID
               INNER JOIN C_Period per_end ON p_period_end_id = per_end.C_Period_ID

          --order's inout and hus
               INNER JOIN M_InOutLine o_iol ON ol.C_OrderLine_ID = o_iol.C_OrderLine_ID
               INNER JOIN M_InOut o_io ON o_iol.M_InOut_ID = o_io.M_InOut_ID
               INNER JOIN M_HU_Assignment huas ON huas.ad_table_id = get_table_id('M_InOutLine') AND huas.Record_ID = o_iol.M_InOutLine_ID

          --counter inout's hus and inout

               INNER JOIN M_InOutLine c_iol ON c_iol.M_InOutLine_ID = ANY (ARRAY(SELECT Record_ID FROM "de.metas.handlingunits".hu_assigment_tracking(huas.m_hu_assignment_id)))
               INNER JOIN M_InOut c_io ON c_iol.M_InOut_ID = c_io.M_InOut_ID AND c_io.isSOTrx != o_io.isSOTrx
               INNER JOIN C_InvoiceCandidate_InOutLine iciol ON c_iol.M_InOutLine_ID = iciol.M_InOutLine_ID
               INNER JOIN C_Invoice_Candidate ic ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID


          --data for inout
               INNER JOIN C_OrderLine c_ol ON c_iol.C_OrderLine_ID = c_ol.C_OrderLine_ID
               INNER JOIN C_Order c_o ON c_ol.C_Order_ID = c_o.C_Order_ID
               INNER JOIN C_BPartner c_bp ON c_bp.C_BPartner_ID = c_io.C_BPartner_ID
               INNER JOIN C_UOM c_uom ON ic.price_UOM_ID = c_uom.C_UOM_ID
               LEFT OUTER JOIN C_UOM_Trl c_uomt ON c_uom.C_UOM_ID = c_uomt.C_UOM_ID AND c_uomt.AD_Language = p_Language


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
                 WHEN EXISTS (SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = p_attributesetinstance_id)
                     -- ... then apply following filter:
                     THEN (
                     -- Take lines where the attributes of the current InoutLine's asi are in the parameter asi and their Values Match
                     EXISTS (SELECT 0
                             FROM report.fresh_Attributes_ConcreteADR a -- a = Attributes from inout line, pa = Parameter Attributes
                                      INNER JOIN report.fresh_Attributes_ConcreteADR pa ON pa.M_AttributeSetInstance_ID = 6487321
                                 AND a.at_value = pa.at_value -- same attribute
                                 AND (CASE
                                          WHEN a.at_value = '1000015' THEN ('%' || SUBSTRING(a.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(pa.ai_value FROM 5) || '%' OR '%' || SUBSTRING(pa.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(a.ai_value FROM 5) || '%') --case of adr containing similar value
                                                                      ELSE a.ai_value = pa.ai_value
                                      END) --same value
                             WHERE a.M_AttributeSetInstance_ID = o_iol.M_AttributeSetInstance_ID)
                         -- Dismiss lines where the Attributes in the Parameter are not in the inoutline's asi
                         AND NOT EXISTS (SELECT 0
                                         FROM report.fresh_Attributes_ConcreteADR pa
                                                  LEFT OUTER JOIN report.fresh_Attributes_ConcreteADR a
                                                                  ON a.at_value = pa.at_value AND
                                                                     (CASE
                                                                          WHEN a.at_value = '1000015' THEN ('%' || SUBSTRING(a.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(pa.ai_value FROM 5) || '%' OR '%' || SUBSTRING(pa.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(a.ai_value FROM 5) || '%') --case of adr containing similar value
                                                                                                      ELSE a.ai_value = pa.ai_value
                                                                      END)
                                                                      AND a.M_AttributeSetInstance_ID = o_iol.M_AttributeSetInstance_ID
                                         WHERE pa.M_AttributeSetInstance_ID = p_attributesetinstance_id
                                           AND a.M_AttributeSetInstance_ID IS NULL)
                     )
                     ELSE TRUE
             END)) a

GROUP BY a.DateOrdered,
         a.oDocumentNo,
         a.bp_value,
         a.bp_name,
         a.p_value,
         a.p_name,
         a.attributes,
         a.price,
         a.currency,
         a.uomsymbol,
         a.total,
         a.qty,
         --inout part
         a.movementdate,
         a.orderdocumentno,
         a.io_bp_value,
         a.io_bp_name,
         a.io_uom


UNION

(SELECT o.DateOrdered,
        o.DocumentNo                                                                                                         AS ODocumentNo,
        bp.Value                                                                                                             AS bp_value,
        bp.Name                                                                                                              AS bp_name,
        p.Value                                                                                                              AS p_value,
        COALESCE(pt.Name, p.Name)                                                                                            AS p_name,
        (SELECT attributes_value FROM de_metas_endcustomer_fresh_reports.get_attributes_value(ol.M_AttributeSetInstance_ID)) AS attributes,
        ol.PriceEntered                                                                                                      AS price,
        ol.linenetamt                                                                                                        AS total,
        c.iso_code                                                                                                           AS currency,
        COALESCE(uomt.uomsymbol, uom.uomsymbol)                                                                              AS uomsymbol,
        ol.qtyEntered                                                                                                        AS qty,

        --inout part
        c_o.dateordered                                                                                                      AS movementdate,
        c_o.documentno                                                                                                       AS orderdocumentno,
        c_bp.value                                                                                                           AS io_bp_value,
        c_bp.name                                                                                                            AS io_bp_name,
        c_ol.qtyentered                                                                                                      AS io_qty,
        COALESCE(c_uomt.uomsymbol, c_uom.uomsymbol)                                                                          AS io_uom,
        'Y'                                                                                                                  AS isPOfromSO


 FROM C_Order o
          JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
          JOIN C_PO_OrderLine_Alloc ol_Alloc
               ON ((o.issotrx = 'Y' AND ol.C_OrderLine_ID = ol_Alloc.c_so_orderline_id)
                   OR (o.issotrx = 'N' AND ol.C_OrderLine_ID = ol_Alloc.c_po_orderline_id))
          JOIN C_OrderLine c_ol
               ON ((o.issotrx = 'Y' AND c_ol.C_OrderLine_ID = ol_Alloc.c_po_orderline_id)
                   OR (o.issotrx = 'N' AND c_ol.C_OrderLine_ID = ol_Alloc.c_so_orderline_id))
          JOIN C_Order c_o ON c_ol.C_Order_ID = c_o.C_Order_ID
          INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID
          INNER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID
          LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_Language
          LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
          INNER JOIN C_UOM uom ON ol.Price_UOM_ID = uom.C_UOM_ID
          LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_Language
          INNER JOIN C_Currency c ON ol.C_Currency_ID = c.C_Currency_ID


          INNER JOIN C_Period per_st ON p_period_st_id = per_st.C_Period_ID
          INNER JOIN C_Period per_end ON p_period_end_id = per_end.C_Period_ID

          INNER JOIN C_BPartner c_bp ON c_bp.C_BPartner_ID = c_o.C_BPartner_ID
          INNER JOIN C_UOM c_uom ON c_ol.price_UOM_ID = c_uom.C_UOM_ID
          LEFT OUTER JOIN C_UOM_Trl c_uomt ON c_uom.C_UOM_ID = c_uomt.C_UOM_ID AND c_uomt.AD_Language = p_Language

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
            WHEN EXISTS (SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = p_attributesetinstance_id)
                -- ... then apply following filter:
                THEN (
                -- Take lines where the attributes of the current InoutLine's asi are in the parameter asi and their Values Match
                EXISTS (SELECT 0
                        FROM report.fresh_Attributes_ConcreteADR a -- a = Attributes from order line, pa = Parameter Attributes
                                 INNER JOIN report.fresh_Attributes_ConcreteADR pa ON pa.M_AttributeSetInstance_ID = p_attributesetinstance_id
                            AND a.at_value = pa.at_value -- same attribute
                            AND (CASE
                                     WHEN a.at_value = '1000015' THEN ('%' || SUBSTRING(a.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(pa.ai_value FROM 5) || '%' OR '%' || SUBSTRING(pa.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(a.ai_value FROM 5) || '%') --case of adr containing similar value
                                                                 ELSE a.ai_value = pa.ai_value
                                 END) --same value
                        WHERE a.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID)
                    -- Dismiss lines where the Attributes in the Parameter are not in the inoutline's asi
                    AND NOT EXISTS (SELECT 0
                                    FROM report.fresh_Attributes_ConcreteADR pa
                                             LEFT OUTER JOIN report.fresh_Attributes_ConcreteADR a
                                                             ON a.at_value = pa.at_value AND
                                                                (CASE
                                                                     WHEN a.at_value = '1000015' THEN ('%' || SUBSTRING(a.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(pa.ai_value FROM 5) || '%' OR '%' || SUBSTRING(pa.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(a.ai_value FROM 5) || '%') --case of adr containing similar value
                                                                                                 ELSE a.ai_value = pa.ai_value
                                                                 END)
                                                                 AND a.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID
                                    WHERE pa.M_AttributeSetInstance_ID = p_attributesetinstance_id
                                      AND a.M_AttributeSetInstance_ID IS NULL)
                )
                ELSE TRUE
        END)


 ORDER BY DateOrdered,
          oDocumentNo,
          bp_value,
          p_value,
          movementdate,
          orderdocumentno,
          io_bp_value)

$$
    LANGUAGE sql STABLE
;
