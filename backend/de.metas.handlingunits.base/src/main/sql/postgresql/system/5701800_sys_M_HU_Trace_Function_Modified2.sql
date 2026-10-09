drop function if exists  m_hu_trace_report(numeric);

create function m_hu_trace_report(p_ad_pinstance_id numeric)
    returns TABLE(
                     lotnumber                  character varying,
                     hutracetype                character varying,
                     product                    character varying,
                     "InOut"                    character varying,
                     pporder                    character varying,
                     inventory                  character varying,
                     documentdate               timestamp with time zone,
                     qty                        numeric,
                     uom                        character varying,
                     detail_type                        character varying,
                     finished_product_no        character varying,
                     finished_product_name      character varying,
                     finished_product_qty       numeric,
                     finished_product_uom       character varying,
                     finished_product_lot       character varying,
                     Vendor_lot                 character varying,
                     finished_product_mhd       character varying,
                     finished_product_clearance character varying,
                     customer_vendor_no                character varying,
                     customer_vendor                   character varying,
                     shipmentqty                numeric,
                     shipment_note              character varying,
                     shipment_date              character varying,
                     prod_stock                 numeric
        ,traceid numeric
        ,reportdate               character varying
                 )
    stable
    language sql
as
$$


SELECT distinct
    t.LotNumber              AS LotNumber,
    'Current Stock'          AS HUTraceType,
    p.value || '_' || p.name AS Product,
    NULL                     AS InOut,
    NULL                     AS PPOrder,
    NULL                     AS Inventory,
    NOW()                    AS DocumentDate,
    getcurrentstoragestock(t.m_product_id,
                           t.c_uom_id,
                           1000017, -- Lot-Nummer
                           t.lotnumber,
                           t.ad_client_id,
                           t.ad_org_id
        )                    AS Qty,
    u.uomsymbol              AS UOM,
    null as detail_type,
    null as finished_product_no,
    null as finished_product_name,
    null::numeric as finished_product_qty,
    null as finished_product_uom,
    null as finished_product_lot,
    null as vendorlot,
    null::varchar as finished_product_mhd,
    null as finished_product_clearance,
    null as customer_no,
    null as customer,
    null::numeric as shipmentqty,
    null as shipment_note,
    null::varchar as shipment_date,
    null::numeric AS prod_stock
        ,null::numeric AS traceid
        ,to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate

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



UNION ALL

(SELECT DISTINCT ON (t.m_inout_ID)
     t.LotNumber                                                      AS LotNumber,
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
     u.uomsymbol                                                      AS UOM,
     null as detail_type,
     null as finished_product_no,
     null as finished_product_name,
     null::numeric as finished_product_qty,
     null as finished_product_uom,
     null as finished_product_lot,
     null as vendorlot,
     null::varchar as finished_product_mhd,
     null as finished_product_clearance,
     null as customer_no,
     null as customer,
     null::numeric as shipmentqty,
     null as shipment_note,
     null::varchar as shipment_date,
     null::numeric AS prod_stock
         ,null::numeric AS traceid
         ,to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate

 FROM M_HU_Trace t
          JOIN M_Product p
               ON t.m_product_id = p.m_product_id
          JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
          LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
          LEFT JOIN C_DocType dt ON io.c_doctype_id = dt.c_doctype_id
          left join c_bpartner bp on io.c_bpartner_id=bp.c_bpartner_id
          left join m_hu_attribute huattrib on huattrib.m_hu_id=t.m_hu_id and huattrib.m_hu_attribute_id=1000029

 WHERE t.hutracetype IN ('MATERIAL_RECEIPT')
   AND io.docstatus IN ('CO', 'CL')
   AND EXISTS (SELECT 1
               FROM T_Selection s
               WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                 AND s.T_Selection_ID = t.m_hu_trace_id))


UNION ALL
--'MATERIAL_SHIPMENT'
(SELECT
     t.LotNumber                                                            AS LotNumber,
     t.hutracetype                                                          AS HUTraceType,
     p.value || '_' || p.name                                               AS Product,
     io.documentno                                                          AS InOut,
     NULL                                                                   AS PPOrder,
     NULL                                                                   AS Inventory,
     io.movementdate                                                        AS DocumentDate,

     CASE WHEN dt.isSOTrx = 'Y' THEN -1 ELSE 1 END * ROUND(t.qty, u.stdprecision) AS Qty,
     u.uomsymbol                                                            AS UOM,


     null as detail_type,
     null as finished_product_no,
     null as finished_product_name,
     null::numeric as finished_product_qty,
     null as finished_product_uom,
     null as finished_product_lot,
     null as vendorlot,
     null::varchar as finished_product_mhd,
     null as finished_product_clearance,
     null as customer_no,
     null as customer,
     null::numeric as shipmentqty,
     null as shipment_note,
     null::varchar as shipment_date,
     null::numeric AS prod_stock
         ,null::numeric AS traceid
         ,to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate
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
--'PRODUCTION_ISSUE', 'PRODUCTION_RECEIPT'
(SELECT DISTINCT ON (t.pp_cost_collector_id)
     t.LotNumber                                                                                    AS LotNumber,
     t.hutracetype                                                                                  AS HUTraceType,
     p.value || '_' || p.name                                                                       AS Product,
     io.documentno                                                                                  AS InOut,
     po.documentno                                                                                  AS PPOrder,
     i.documentno                                                                                   AS Inventory,
     COALESCE(io.movementdate, cc.movementdate, po.datepromised, i.movementdate)                    AS DocumentDate,
     CASE WHEN t.hutracetype = 'PRODUCTION_ISSUE' THEN -1 ELSE 1 END * ROUND(t.qty, u.stdprecision) AS Qty,
     u.uomsymbol                                                                                    AS UOM,
     null as detail_type,
     null as finished_product_no,
     null as finished_product_name,
     null::numeric as finished_product_qty,
     null as finished_product_uom,
     null as finished_product_lot,
     null as vendorlot,
     null::varchar as finished_product_mhd,
     null as finished_product_clearance,
     null as customer_no,
     null as customer,
     null::numeric as shipmentqty,
     null as shipment_note,
     null::varchar as shipment_date,
     null::numeric AS prod_stock
         ,null::numeric AS traceid
         ,to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate
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

--todo: Begin NEW

UNION ALL
--'PRODUCTION_ISSUE_DETAL'
(
    SELECT DISTINCT ON (t.pp_cost_collector_id)
        t.LotNumber                                                                                    AS LotNumber,
        'PRODUCTION_ISSUE_DETAL'                                                                       AS HUTraceType,
        p.value || '_' || p.name                                                                       AS Product,
        io.documentno                                                                                  AS InOut,
        po.documentno                                                                                  AS PPOrder,
        i.documentno                                                                                   AS Inventory,
        COALESCE(io.movementdate, cc.movementdate, po.datepromised, i.movementdate)                    AS DocumentDate,
        null::numeric AS Qty,
        u.uomsymbol                                                                                    AS UOM,


        prod_trace.HUTraceType as detail_type,
        prod_product.value as finished_product_no,
        prod_product.name as finished_product_name,
        po.qtyordered as finished_product_qty,
        prod_uom.uomsymbol as finished_product_uom,
        prod_trace.lotnumber as finished_product_lot,
        null as vendorlot,
        (select  to_char(valuedate, 'DD.MM.YYYY')  from  m_hu_attribute mhd
         where  mhd.m_hu_id =  prod_trace.m_hu_id
           AND mhd.m_attribute_id = 540020::numeric) as finished_product_mhd,

        hulu_clearancestatus.name as finished_product_clearance,
        bp.value as customer_no,
        bp.name as customer,
        prod_trace_shipment.qty as shipmentqty,
        inout.documentno as shipment_note,
        null::varchar as shipment_date, -- TODO: decide which should the shipment date be
        getcurrentstoragestock(t.m_product_id,
                               t.c_uom_id,
                               1000017, -- Lot-Nummer
                               prod_trace.lotnumber,
                               t.ad_client_id,
                               t.ad_org_id
            )                    AS prod_stock
            ,null::numeric AS traceid
            ,to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate


    FROM M_HU_Trace t
             JOIN M_Product p
                  ON t.m_product_id = p.m_product_id
             JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
             LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
             LEFT JOIN PP_cost_collector cc ON t.pp_cost_collector_id = cc.pp_cost_collector_id
             LEFT JOIN PP_Order po ON t.pp_order_id = po.pp_order_id
             left join c_bpartner bp on po.c_bpartner_id = bp.c_bpartner_id
             JOIN M_Product prod_product
                  ON po.m_product_id = prod_product.m_product_id
             LEFT JOIN C_UOM prod_uom ON po.c_uom_id = prod_uom.c_uom_id
             LEFT JOIN M_Inventory i ON t.M_Inventory_ID = i.m_inventory_id
             left join  M_HU_Trace as prod_trace on  prod_trace.pp_order_id=po.pp_order_id  and prod_trace.hutracetype='PRODUCTION_RECEIPT'
             left join  M_HU_Trace as prod_trace_shipment on prod_trace_shipment.m_hu_id=prod_trace.m_hu_id and prod_trace_shipment.hutracetype='MATERIAL_SHIPMENT'
             left join m_hu hu on prod_trace.m_hu_id = hu.m_hu_id
             left join m_inout inout on inout.m_inout_id = prod_trace_shipment.m_inout_id
             JOIN m_hu_attribute mhd ON mhd.m_hu_id =  prod_trace.m_hu_id AND mhd.m_attribute_id = 540020::numeric
             LEFT JOIN ad_ref_list hulu_clearancestatus ON hulu_clearancestatus.ad_reference_id = 541540::numeric AND hulu_clearancestatus.value::text = hu.clearancestatus::text


    WHERE t.hutracetype IN ('PRODUCTION_ISSUE')
      AND po.docstatus IN ('CO', 'CL')
      AND EXISTS (SELECT 1
                  FROM T_Selection s
                  WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                    AND s.T_Selection_ID = t.m_hu_trace_id)

)

--todo: end NEW

--todo: NEW 2 - 'PRODUCTION_RECEIPT'

UNION ALL
--'PRODUCTION_RECEIPT_DETAL'
(

    SELECT DISTINCT
        t.LotNumber                                                                                    AS LotNumber,
        'PRODUCTION_RECEIPT_DETAL'                                                                       AS HUTraceType,
        p.value || '_' || p.name                                                                       AS Product,
        io.documentno                                                                                  AS InOut,
        po.documentno                                                                                  AS PPOrder,
        i.documentno                                                                                   AS Inventory,
        COALESCE(io.movementdate, cc.movementdate, po.datepromised, i.movementdate)                    AS DocumentDate,
        null::numeric AS Qty,
        u.uomsymbol                                                                                    AS UOM,
        prod_trace.HUTraceType as detail_type,
        prod_product.value as finished_product_no,
        prod_product.name as finished_product_name,
        prod_trace.qty as finished_product_qty,
        prod_uom.uomsymbol as finished_product_uom,
        prod_trace.lotnumber as finished_product_lot,
        (select  value  from  m_hu_attribute vendorlot
         where  vendorlot.m_hu_id =  prod_trace.m_hu_id
           AND vendorlot.m_attribute_id = 1000029::numeric) as   vendorlot,
        (select to_char(valuedate, 'DD.MM.YYYY') from  m_hu_attribute mhd
         where  mhd.m_hu_id =  prod_trace.m_hu_id
           AND mhd.m_attribute_id = 540020::numeric) as finished_product_mhd,
        hulu_clearancestatus.name as finished_product_clearance,
        bp.value as customer_no,
        bp.name as customer,
        0 as receivedqty,
        (select STRING_AGG(distinct inout.documentno, ',')
         from M_HU_Trace as trc
                  left join  m_inout inout on trc.m_inout_id = inout.m_inout_id
         where trc.lotnumber=prod_trace.lotnumber and trc.hutracetype='MATERIAL_RECEIPT'
        ) as receipt_note,
        (select  to_char(inout.movementdate, 'DD.MM.YYYY')
         from M_HU_Trace as trc
                  left join  m_inout inout on trc.m_inout_id = inout.m_inout_id
         where trc.lotnumber=prod_trace.lotnumber and trc.hutracetype='MATERIAL_RECEIPT'
         limit 1
        )  as shipment_date, -- TODO: decide which should the shipment date be

        getcurrentstoragestock(t.m_product_id,
                               t.c_uom_id,
                               1000017, -- Lot-Nummer
                               prod_trace.lotnumber,
                               t.ad_client_id,
                               t.ad_org_id
            )                             AS prod_stock
            ,prod_trace.m_hu_trace_id
            ,to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate

    FROM M_HU_Trace t
             JOIN M_Product p
                  ON t.m_product_id = p.m_product_id
             JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
             LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
             LEFT JOIN PP_cost_collector cc ON t.pp_cost_collector_id = cc.pp_cost_collector_id
             LEFT JOIN PP_Order po ON t.pp_order_id = po.pp_order_id
             LEFT JOIN M_Inventory i ON t.M_Inventory_ID = i.m_inventory_id
             left join  M_HU_Trace as prod_trace on  prod_trace.pp_order_id=po.pp_order_id  and prod_trace.hutracetype='PRODUCTION_ISSUE'
        --left join  M_HU_Trace as prod_trace_shipment on prod_trace_shipment.m_hu_id=prod_trace.m_hu_id and prod_trace_shipment.hutracetype='MATERIAL_RECEIPT'
             left join  M_HU_Trace as prod_trace_shipment on prod_trace_shipment.lotnumber=prod_trace.lotnumber and prod_trace_shipment.hutracetype='MATERIAL_RECEIPT'
             JOIN M_Product prod_product
                  ON prod_trace.m_product_id = prod_product.m_product_id
             LEFT JOIN C_UOM prod_uom on prod_trace.c_uom_id = prod_uom.c_uom_id
             left join m_hu hu on prod_trace.m_hu_id = hu.m_hu_id
             left join m_inout inout on inout.m_inout_id = prod_trace_shipment.m_inout_id
             left join c_bpartner bp on inout.c_bpartner_id = bp.c_bpartner_id
             JOIN m_hu_attribute mhd ON mhd.m_hu_id =  prod_trace.m_hu_id AND mhd.m_attribute_id = 540020::numeric
             LEFT JOIN ad_ref_list hulu_clearancestatus ON hulu_clearancestatus.ad_reference_id = 541540::numeric AND hulu_clearancestatus.value::text = hu.clearancestatus::text

    WHERE t.hutracetype IN ('PRODUCTION_RECEIPT')
      AND po.docstatus IN ('CO', 'CL')
      AND EXISTS (SELECT 1
                  FROM T_Selection s
                  WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                    AND s.T_Selection_ID = t.m_hu_trace_id)
    order by prod_product.value
)
--todo: end teil 2


UNION ALL
-- 'MATERIAL_INVENTORY'
(SELECT DISTINCT
     t.LotNumber                                                                         AS LotNumber,
     t.hutracetype                                                                       AS HUTraceType,
     p.value || '_' || p.name                                                            AS Product,
     io.documentno                                                                       AS InOut,
     po.documentno                                                                       AS PPOrder,
     i.documentno                                                                        AS Inventory,
     COALESCE(io.movementdate, cc.movementdate, po.datepromised, i.movementdate)         AS DocumentDate,
     CASE WHEN i.C_Doctype_ID = 540948 THEN -1 ELSE 1 END * ROUND(t.qty, u.stdprecision) AS Qty,
     u.uomsymbol                                                                         AS UOM,


     null as detail_type,
     null as finished_product_no,
     null as finished_product_name,
     null::numeric as finished_product_qty,
     null as finished_product_uom,
     null as finished_product_lot,
     null as vendorlot,
     null::varchar as finished_product_mhd,

     null as finished_product_clearance,
     null as customer_no,
     null as customer,
     null::numeric as shipmentqty,
     null as shipment_note,
     null::varchar as shipment_date,
     null::numeric AS prod_stock
         ,null::numeric AS traceid
         ,to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate
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

ORDER BY LotNumber, pporder,HUTraceType, DocumentDate

    ;
$$;

alter function m_hu_trace_report(numeric) owner to metasfresh;