DROP FUNCTION IF EXISTS public.M_HU_Trace_Report(numeric)
;

CREATE OR REPLACE FUNCTION public.M_HU_Trace_Report(p_AD_PInstance_ID numeric)
    RETURNS table
            (
                LotNumber    character varying,
                HUTraceType  character varying,
                Product      character varying,
                "InOut"      character varying,
                PPOrder      character varying,
                Inventory    character varying,
                DocumentDate timestamp WITH TIME ZONE,
                Qty          numeric,
                UOM          character varying
            )
AS
$BODY$


SELECT t.LotNumber              AS LotNumber,
       'Current Stock'          AS HUTraceType,
       p.value || '_' || p.name AS Product,
       NULL                     AS InOut,
       NULL                     AS PPOrder,
       NULL                     AS Inventory,
       NOW()                    AS DocumentDate,
       getCurrentStorageStock(t.m_product_id,
                              t.c_uom_id,
                              1000017, -- Lot-Nummer
                              t.lotnumber,
                              t.ad_client_id,
                              t.ad_org_id
           )                    AS Qty,
       u.uomsymbol              AS UOM
FROM M_HU_Trace t
         JOIN M_Product p ON t.m_product_id = p.m_product_id
         JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
         LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
         LEFT JOIN PP_Order po ON t.pp_order_id = po.pp_order_id
         LEFT JOIN M_Inventory i ON t.M_Inventory_ID = i.m_inventory_id
WHERE t.hutracetype IN ('PRODUCTION_ISSUE',
                        'PRODUCTION_RECEIPT',
                        'MATERIAL_RECEIPT',
                        'MATERIAL_SHIPMENT',
                        'MATERIAL_INVENTORY')
  AND EXISTS (SELECT 1
              FROM T_Selection s
              WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                AND s.T_Selection_ID = t.m_hu_trace_id)

UNION

(SELECT DISTINCT ON (t.m_inout_ID) t.LotNumber                                                      AS LotNumber,
                                   t.hutracetype                                                    AS HUTraceType,
                                   p.value || '_' || p.name                                         AS Product,
                                   io.documentno                                                    AS InOut,
                                   NULL                                                             AS PPOrder,
                                   NULL                                                             AS Inventory,
                                   io.movementdate                                                  AS DocumentDate,

                                   CASE WHEN dt.isSOTrx = 'Y' THEN -1 ELSE 1 END * (SELECT SUM(uomconvert(p_m_product_id := p.m_product_id, p_c_uom_from_id := iol.c_uom_id, p_c_uom_to_id := p.c_uom_id, p_qty := iol.movementqty))
                                                                              FROM metasfresh.public.m_inoutline iol
                                                                              WHERE io.m_inout_id = iol.m_inout_id
                                                                                AND iol.m_product_id = p.m_product_id
                                                                                AND EXISTS (SELECT 1
                                                                                            FROM m_hu_assignment hua
                                                                                                     JOIN M_HU_Attribute huat ON hua.m_hu_id = huat.m_hu_id
                                                                                            WHERE hua.ad_table_id = get_table_id('M_InOutLine')
                                                                                              AND hua.record_id = iol.m_inoutline_id
                                                                                              AND huat.m_attribute_id = 1000017
                                                                                              AND ((t.lotnumber IS NOT NULL AND huat.value = t.lotnumber)
                                                                                                OR (t.lotnumber IS NULL AND huat.value IS NULL)
                                                                                                ))) AS Qty,
                                   u.uomsymbol                                                      AS UOM
 FROM M_HU_Trace t
          JOIN M_Product p
               ON t.m_product_id = p.m_product_id
          JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
          LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
          LEFT JOIN C_DocType dt ON io.c_doctype_id = dt.c_doctype_id
 WHERE t.hutracetype IN ('MATERIAL_RECEIPT')
   AND io.docstatus IN ('CO', 'CL')
   AND EXISTS (SELECT 1
               FROM T_Selection s
               WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                 AND s.T_Selection_ID = t.m_hu_trace_id))


UNION ALL
(SELECT t.LotNumber                                                            AS LotNumber,
        t.hutracetype                                                          AS HUTraceType,
        p.value || '_' || p.name                                               AS Product,
        io.documentno                                                          AS InOut,
        NULL                                                                   AS PPOrder,
        NULL                                                                   AS Inventory,
        io.movementdate                                                        AS DocumentDate,

        CASE WHEN dt.isSOTrx = 'Y' THEN -1 ELSE 1 END * ROUND(t.qty, u.stdprecision) AS Qty,
        u.uomsymbol                                                            AS UOM
 FROM M_HU_Trace t
          JOIN M_Product p
               ON t.m_product_id = p.m_product_id
          JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
          LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
          LEFT JOIN C_DocType dt ON io.c_doctype_id = dt.c_doctype_id

 WHERE t.hutracetype IN ('MATERIAL_SHIPMENT')
   AND io.docstatus IN ('CO', 'CL')
   AND EXISTS (SELECT 1
               FROM T_Selection s
               WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                 AND s.T_Selection_ID = t.m_hu_trace_id))


UNION ALL
(SELECT DISTINCT ON (t.pp_cost_collector_id) t.LotNumber                                                                                    AS LotNumber,
                                             t.hutracetype                                                                                  AS HUTraceType,
                                             p.value || '_' || p.name                                                                       AS Product,
                                             io.documentno                                                                                  AS InOut,
                                             po.documentno                                                                                  AS PPOrder,
                                             i.documentno                                                                                   AS Inventory,
                                             COALESCE(io.movementdate, cc.movementdate, po.datepromised, i.movementdate)                    AS DocumentDate,
                                             CASE WHEN t.hutracetype = 'PRODUCTION_ISSUE' THEN -1 ELSE 1 END * ROUND(t.qty, u.stdprecision) AS Qty,
                                             u.uomsymbol                                                                                    AS UOM
 FROM M_HU_Trace t
          JOIN M_Product p
               ON t.m_product_id = p.m_product_id
          JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
          LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
          LEFT JOIN PP_cost_collector cc ON t.pp_cost_collector_id = cc.pp_cost_collector_id
          LEFT JOIN PP_Order po ON t.pp_order_id = po.pp_order_id
          LEFT JOIN M_Inventory i ON t.M_Inventory_ID = i.m_inventory_id
 WHERE t.hutracetype IN ('PRODUCTION_ISSUE', 'PRODUCTION_RECEIPT')
   AND po.docstatus IN ('CO', 'CL')
   AND EXISTS (SELECT 1
               FROM T_Selection s
               WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                 AND s.T_Selection_ID = t.m_hu_trace_id))


UNION ALL
(SELECT t.LotNumber                                                                         AS LotNumber,
        t.hutracetype                                                                       AS HUTraceType,
        p.value || '_' || p.name                                                            AS Product,
        io.documentno                                                                       AS InOut,
        po.documentno                                                                       AS PPOrder,
        i.documentno                                                                        AS Inventory,
        COALESCE(io.movementdate, cc.movementdate, po.datepromised, i.movementdate)         AS DocumentDate,
        CASE WHEN i.C_Doctype_ID = 540948 THEN -1 ELSE 1 END * ROUND(t.qty, u.stdprecision) AS Qty,
        u.uomsymbol                                                                         AS UOM
 FROM M_HU_Trace t
          JOIN M_Product p
               ON t.m_product_id = p.m_product_id
          JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
          LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
          LEFT JOIN PP_cost_collector cc ON t.pp_cost_collector_id = cc.pp_cost_collector_id
          LEFT JOIN PP_Order po ON t.pp_order_id = po.pp_order_id
          LEFT JOIN M_Inventory i ON t.M_Inventory_ID = i.m_inventory_id
 WHERE t.hutracetype = 'MATERIAL_INVENTORY'
   AND i.docstatus IN ('CO', 'CL')
   AND EXISTS (SELECT 1
               FROM T_Selection s
               WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                 AND s.T_Selection_ID = t.m_hu_trace_id))

ORDER BY LotNumber, HUTraceType, DocumentDate

    ;
$BODY$
    LANGUAGE sql STABLE
                 COST 100
;


--
COMMENT ON FUNCTION public.M_HU_Trace_Report(numeric) IS 'Returns all M_HU_Traces for an AD_Pinstance_ID.'
;

--
-- Test it
-- select * from M_HU_Trace_Report(1000000) ;

