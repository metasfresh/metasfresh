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

(SELECT DISTINCT ON (t.m_inout_ID) t.LotNumber                                                                                                                                                      AS LotNumber,
                                   t.hutracetype                                                                                                                                                    AS HUTraceType,
                                   p.value || '_' || p.name                                                                                                                                         AS Product,
                                   io.documentno                                                                                                                                                    AS InOut,
                                   NULL                                                                                                                                                             AS PPOrder,
                                   NULL                                                                                                                                                             AS Inventory,
                                   io.movementdate                                                                                                                                                  AS DocumentDate,

                                   (CASE WHEN t.hutracetype = 'MATERIAL_SHIPMENT' THEN -1 ELSE 1 END) * (SELECT SUM(uomconvert(p_m_product_id := p.m_product_id, p_c_uom_from_id := iol.c_uom_id, p_c_uom_to_id := p.c_uom_id, p_qty := iol.movementqty))
                                                                                                         FROM metasfresh.public.m_inoutline iol
                                                                                                                  LEFT JOIN m_attributesetinstance asi ON iol.m_attributesetinstance_id = asi.m_attributesetinstance_id
                                                                                                                  LEFT JOIN M_AttributeInstance ai ON asi.m_attributesetinstance_id = ai.m_attributesetinstance_id
                                                                                                         WHERE io.m_inout_id = iol.m_inout_id
                                                                                                           AND iol.m_product_id = p.m_product_id
                                                                                                           AND ((t.lotnumber IS NULL) OR (ai.m_attribute_id = 1000017 AND ai.value = t.lotnumber))) AS Qty,
                                   u.uomsymbol                                                                                                                                                      AS UOM
 FROM M_HU_Trace t
          JOIN M_Product p
               ON t.m_product_id = p.m_product_id
          JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
          LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
          LEFT JOIN PP_Order po ON t.pp_order_id = po.pp_order_id
          LEFT JOIN M_Inventory i ON t.M_Inventory_ID = i.m_inventory_id
 WHERE t.hutracetype IN ('MATERIAL_RECEIPT', 'MATERIAL_SHIPMENT')
   AND EXISTS (SELECT 1
               FROM T_Selection s
               WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                 AND s.T_Selection_ID = t.m_hu_trace_id))


UNION ALL
(SELECT t.LotNumber                                                                 AS LotNumber,
        t.hutracetype                                                               AS HUTraceType,
        p.value || '_' || p.name                                                    AS Product,
        io.documentno                                                               AS InOut,
        po.documentno                                                               AS PPOrder,
        i.documentno                                                                AS Inventory,
        COALESCE(io.movementdate, cc.movementdate, po.datepromised, i.movementdate) AS DocumentDate,
        (CASE WHEN t.hutracetype = 'PRODUCTION_ISSUE' THEN -1 ELSE 1 END) * t.qty   AS Qty,
        u.uomsymbol                                                                 AS UOM
 FROM M_HU_Trace t
          JOIN M_Product p
               ON t.m_product_id = p.m_product_id
          JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
          LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
          LEFT JOIN PP_cost_collector cc ON t.pp_cost_collector_id = cc.pp_cost_collector_id
          LEFT JOIN PP_Order po ON t.pp_order_id = po.pp_order_id
          LEFT JOIN M_Inventory i ON t.M_Inventory_ID = i.m_inventory_id
 WHERE t.hutracetype IN ('PRODUCTION_ISSUE', 'PRODUCTION_RECEIPT', 'MATERIAL_INVENTORY')
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

