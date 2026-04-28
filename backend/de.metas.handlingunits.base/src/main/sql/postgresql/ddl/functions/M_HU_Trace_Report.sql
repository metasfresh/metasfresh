-- =====================================================================================
-- MODIFIED M_HU_Trace_Report Function
-- =====================================================================================
-- MODIFICATION SUMMARY:
-- This function has been extended to support TWO product flows:
-- 
-- 1. MANUFACTURED PRODUCTS (Original Flow):
--    Purchase → Material Receipt → Manufacturing (Production Issue/Receipt) → Shipment
--
-- 2. NON-MANUFACTURED PRODUCTS (New Flow - Added):
--    Purchase → Material Receipt (issotrx=N) → Shipment (issotrx=Y)
--
-- KEY CHANGES:
-- - Added new section "DIRECT_SALE_DETAIL" (similar to PRODUCTION_RECEIPT_DETAL)
-- - This new section traces non-manufactured products from purchase receipt to customer shipment
-- - Links purchase receipts (issotrx=N) to sales shipments (issotrx=Y) via lot numbers
-- - Shows vendor information from purchase and customer information from shipment
-- - Enhanced Material Receipts and Material Shipments sections to better handle both flows
-- =====================================================================================

drop function if exists m_hu_trace_report(numeric);

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
        detail_type                character varying,
        finished_product_no        character varying,
        finished_product_name      character varying,
        finished_product_qty       numeric,
        finished_product_uom       character varying,
        finished_product_lot       character varying,
        vendor_lot                 character varying,
        finished_product_mhd       character varying,
        finished_product_clearance character varying,
        customer_vendor_no         character varying,
        customer_vendor            character varying,
        shipmentqty                numeric,
        shipment_note              character varying,
        shipment_date              character varying,
        prod_stock                 numeric,
        traceid                    numeric,
        reportdate                 character varying
    )
    stable
    language sql
as
$$
-- =====================================================================================
-- SECTION 1: CURRENT STOCK
-- Shows current inventory levels for all products (manufactured and non-manufactured)
-- =====================================================================================
SELECT distinct
    t.LotNumber AS LotNumber,
    'Current Stock' AS HUTraceType,
    p.value || '_' || p.name AS Product,
    NULL AS InOut,
    NULL AS PPOrder,
    NULL AS Inventory,
    NOW() AS DocumentDate,
    getcurrentstoragestock(t.m_product_id, t.c_uom_id, 1000017, t.lotnumber, t.ad_client_id, t.ad_org_id) AS Qty,
    u.uomsymbol AS UOM,
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
    null::numeric AS prod_stock,
    null::numeric AS traceid,
    to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate
FROM M_HU_Trace t
JOIN M_Product p ON t.m_product_id = p.m_product_id
JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
LEFT JOIN PP_Order po ON t.pp_order_id = po.pp_order_id
LEFT JOIN M_Inventory i ON t.M_Inventory_ID = i.m_inventory_id
WHERE t.hutracetype IN ('PRODUCTION_ISSUE', 'PRODUCTION_RECEIPT', 'MATERIAL_RECEIPT', 'MATERIAL_SHIPMENT', 'MATERIAL_INVENTORY')
  AND EXISTS (SELECT 1 FROM T_Selection s WHERE s.AD_PInstance_ID = p_AD_PInstance_ID AND s.T_Selection_ID = t.m_hu_trace_id)

UNION ALL

-- =====================================================================================
-- SECTION 2: MATERIAL RECEIPTS
-- Handles incoming goods from vendors (issotrx=N)
-- Used for BOTH manufactured (raw materials) and non-manufactured (purchase for resale) products
-- =====================================================================================
SELECT DISTINCT ON (t.m_inout_ID)
    t.LotNumber AS LotNumber,
    'MATERIAL_RECEIPT' AS HUTraceType,
    p.value || '_' || p.name AS Product,
    io.documentno AS InOut,
    NULL AS PPOrder,
    NULL AS Inventory,
    io.movementdate AS DocumentDate,
    CASE WHEN dt.isSOTrx = 'Y' THEN -1 ELSE 1 END * (SELECT SUM(uomconvert(p.m_product_id, iol.c_uom_id, p.c_uom_id, iol.movementqty))
                                                     FROM m_inoutline iol
                                                     WHERE io.m_inout_id = iol.m_inout_id
                                                     AND iol.m_product_id = p.m_product_id
                                                     AND EXISTS (SELECT 1 FROM m_hu_assignment hua JOIN m_hu_attribute huat ON hua.m_hu_id = huat.m_hu_id
                                                                 WHERE hua.ad_table_id = get_table_id('M_InOutLine')
                                                                 AND hua.record_id = iol.m_inoutline_id
                                                                 AND huat.m_attribute_id = 1000017
                                                                 AND ((t.lotnumber IS NOT NULL AND huat.value = t.lotnumber)
                                                                      OR (t.lotnumber IS NULL AND huat.value IS NULL)
                                                                 ))) AS Qty,
    u.uomsymbol AS UOM,
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
    null::numeric AS prod_stock,
    null::numeric AS traceid,
    to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate
FROM M_HU_Trace t
JOIN M_Product p ON t.m_product_id = p.m_product_id
JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
LEFT JOIN C_DocType dt ON io.c_doctype_id = dt.c_doctype_id
LEFT JOIN c_bpartner bp ON io.c_bpartner_id=bp.c_bpartner_id
LEFT JOIN m_hu_attribute huattrib ON huattrib.m_hu_id=t.m_hu_id AND huattrib.m_hu_attribute_id=1000029
WHERE t.hutracetype IN ('MATERIAL_RECEIPT')
  AND io.docstatus IN ('CO', 'CL')
  AND EXISTS (SELECT 1 FROM T_Selection s WHERE s.AD_PInstance_ID = p_AD_PInstance_ID AND s.T_Selection_ID = t.m_hu_trace_id)

UNION ALL

-- =====================================================================================
-- SECTION 3: MATERIAL SHIPMENTS
-- Handles outgoing goods to customers (issotrx=Y)
-- Used for BOTH manufactured products and non-manufactured (direct resale) products
-- =====================================================================================
SELECT
    t.LotNumber AS LotNumber,
    'MATERIAL_SHIPMENT' AS HUTraceType,
    p.value || '_' || p.name AS Product,
    io.documentno AS InOut,
    NULL AS PPOrder,
    NULL AS Inventory,
    io.movementdate AS DocumentDate,
    CASE WHEN dt.isSOTrx = 'Y' THEN -1 ELSE 1 END * ROUND(t.qty, u.stdprecision) AS Qty,
    u.uomsymbol AS UOM,
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
    null::numeric AS prod_stock,
    null::numeric AS traceid,
    to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate
FROM M_HU_Trace t
JOIN M_Product p ON t.m_product_id = p.m_product_id
JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
LEFT JOIN C_DocType dt ON io.c_doctype_id = dt.c_doctype_id
WHERE t.hutracetype IN ('MATERIAL_SHIPMENT')
  AND io.docstatus IN ('CO', 'CL')
  AND EXISTS (SELECT 1 FROM T_Selection s WHERE s.AD_PInstance_ID = p_AD_PInstance_ID AND s.T_Selection_ID = t.m_hu_trace_id)

UNION ALL

-- =====================================================================================
-- SECTION 4: PRODUCTION ISSUE/RECEIPT (Original - Manufactured Products Only)
-- Handles manufacturing operations: raw materials consumed and finished goods produced
-- =====================================================================================
SELECT DISTINCT ON (t.pp_cost_collector_id)
    t.LotNumber AS LotNumber,
    t.hutracetype AS HUTraceType,
    p.value || '_' || p.name AS Product,
    io.documentno AS InOut,
    po.documentno AS PPOrder,
    i.documentno AS Inventory,
    COALESCE(io.movementdate, cc.movementdate, po.datepromised, i.movementdate) AS DocumentDate,
    CASE WHEN t.hutracetype = 'PRODUCTION_ISSUE' THEN -1 ELSE 1 END * ROUND(t.qty, u.stdprecision) AS Qty,
    u.uomsymbol AS UOM,
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
    null::numeric AS prod_stock,
    null::numeric AS traceid,
    to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate
FROM M_HU_Trace t
JOIN M_Product p ON t.m_product_id = p.m_product_id
JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
LEFT JOIN PP_cost_collector cc ON t.pp_cost_collector_id = cc.pp_cost_collector_id
LEFT JOIN PP_Order po ON t.pp_order_id = po.pp_order_id
LEFT JOIN M_Inventory i ON t.M_Inventory_ID = i.m_inventory_id
WHERE t.hutracetype IN ('PRODUCTION_ISSUE', 'PRODUCTION_RECEIPT')
  AND po.docstatus IN ('CO', 'CL')
  AND EXISTS (SELECT 1 FROM T_Selection s WHERE s.AD_PInstance_ID = p_AD_PInstance_ID AND s.T_Selection_ID = t.m_hu_trace_id)

UNION ALL

-- =====================================================================================
-- SECTION 5: PRODUCTION RECEIPT DETAILS (Original - Manufactured Products Only)
-- Shows detailed traceability from raw materials to finished manufactured products
-- Links production receipts to the raw materials used (production issues)
-- =====================================================================================
SELECT DISTINCT
    t.LotNumber AS LotNumber,
    'PRODUCTION_RECEIPT_DETAL' AS HUTraceType,
    p.value || '_' || p.name AS Product,
    io.documentno AS InOut,
    po.documentno AS PPOrder,
    i.documentno AS Inventory,
    COALESCE(io.movementdate, cc.movementdate, po.datepromised, i.movementdate) AS DocumentDate,
    null::numeric AS Qty,
    u.uomsymbol AS UOM,
    prod_trace.HUTraceType as detail_type,
    prod_product.value as finished_product_no,
    prod_product.name as finished_product_name,
    po.qtyordered as finished_product_qty,
    prod_uom.uomsymbol as finished_product_uom,
    prod_trace.lotnumber as finished_product_lot,
    (select  value from m_hu_attribute vendorlot where m_hu_id = prod_trace.m_hu_id and m_attribute_id = 1000029::numeric) as   vendorlot,
    (select to_char(valuedate, 'DD.MM.YYYY') from m_hu_attribute mhd where mhd.m_hu_id = prod_trace.m_hu_id and mhd.m_attribute_id = 540020::numeric) as finished_product_mhd,
    hulu_clearancestatus.name as finished_product_clearance,
    bp.value as customer_no,
    bp.name as customer,
    0 as receivedqty,
    (select STRING_AGG(distinct inout.documentno, ',') from M_HU_Trace as trc left join m_inout inout on trc.m_inout_id = inout.m_inout_id where trc.lotnumber=prod_trace.lotnumber and trc.hutracetype='MATERIAL_RECEIPT') as receipt_note,
    (select to_char(inout.movementdate, 'DD.MM.YYYY') from M_HU_Trace as trc left join m_inout inout on trc.m_inout_id = inout.m_inout_id where trc.lotnumber=prod_trace.lotnumber and trc.hutracetype='MATERIAL_RECEIPT' limit 1) as shipment_date,
    getcurrentstoragestock(t.m_product_id, t.c_uom_id, 1000017, prod_trace.lotnumber, t.ad_client_id, t.ad_org_id) AS prod_stock,
    prod_trace.m_hu_trace_id,
    to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate
FROM M_HU_Trace t
JOIN M_Product p ON t.m_product_id = p.m_product_id
JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
LEFT JOIN PP_cost_collector cc ON t.pp_cost_collector_id = cc.pp_cost_collector_id
LEFT JOIN PP_Order po ON t.pp_order_id = po.pp_order_id
LEFT JOIN M_Inventory i ON t.M_Inventory_ID = i.m_inventory_id
LEFT JOIN M_HU_Trace as prod_trace ON prod_trace.pp_order_id=po.pp_order_id and prod_trace.hutracetype='PRODUCTION_ISSUE'
JOIN M_Product prod_product ON prod_trace.m_product_id = prod_product.m_product_id
LEFT JOIN C_UOM prod_uom on prod_trace.c_uom_id = prod_uom.c_uom_id
LEFT JOIN m_hu hu ON prod_trace.m_hu_id = hu.m_hu_id
LEFT JOIN m_inout inout on inout.m_inout_id = prod_trace.m_inout_id
LEFT JOIN c_bpartner bp ON inout.c_bpartner_id = bp.c_bpartner_id
LEFT JOIN m_hu_attribute mhd ON mhd.m_hu_id = prod_trace.m_hu_id AND mhd.m_attribute_id = 540020::numeric
LEFT JOIN ad_ref_list hulu_clearancestatus ON hulu_clearancestatus.ad_reference_id = 541540::numeric AND hulu_clearancestatus.value::text = hu.clearancestatus::text
WHERE t.hutracetype IN ('PRODUCTION_RECEIPT')
  AND po.docstatus IN ('CO', 'CL')
  AND EXISTS (SELECT 1 FROM T_Selection s WHERE s.AD_PInstance_ID = p_AD_PInstance_ID AND s.T_Selection_ID = t.m_hu_trace_id)

UNION ALL

-- =====================================================================================
-- SECTION 6: DIRECT SALE DETAILS (NEW - Non-Manufactured Products)
-- This is the NEW section that handles the direct purchase-to-sale flow
-- Links material receipts from vendors (issotrx=N) to material shipments to customers (issotrx=Y)
-- Provides complete traceability for non-manufactured products from purchase to sale
-- =====================================================================================
SELECT DISTINCT
    t.LotNumber AS LotNumber,
    'DIRECT_SALE_DETAIL' AS HUTraceType,
    p.value || '_' || p.name AS Product,
    receipt_io.documentno AS InOut,
    NULL AS PPOrder,
    NULL AS Inventory,
    receipt_io.movementdate AS DocumentDate,
    null::numeric AS Qty,
    u.uomsymbol AS UOM,
    shipment_trace.HUTraceType as detail_type,
    shipment_product.value as finished_product_no,
    shipment_product.name as finished_product_name,
    ABS(ROUND(shipment_trace.qty, shipment_uom.stdprecision)) as finished_product_qty,
    shipment_uom.uomsymbol as finished_product_uom,
    shipment_trace.lotnumber as finished_product_lot,
    (select value from m_hu_attribute vendorlot where m_hu_id = t.m_hu_id and m_attribute_id = 1000029::numeric) as vendorlot,
    (select to_char(valuedate, 'DD.MM.YYYY') from m_hu_attribute mhd where mhd.m_hu_id = shipment_trace.m_hu_id and mhd.m_attribute_id = 540020::numeric) as finished_product_mhd,
    shipment_hulu_clearancestatus.name as finished_product_clearance,
    shipment_bp.value as customer_no,
    shipment_bp.name as customer,
    ABS(ROUND(shipment_trace.qty, shipment_uom.stdprecision)) as shipmentqty,
    shipment_io.documentno as shipment_note,
    to_char(shipment_io.movementdate, 'DD.MM.YYYY') as shipment_date,
    getcurrentstoragestock(t.m_product_id, t.c_uom_id, 1000017, shipment_trace.lotnumber, t.ad_client_id, t.ad_org_id) AS prod_stock,
    shipment_trace.m_hu_trace_id,
    to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate
FROM M_HU_Trace t
JOIN M_Product p ON t.m_product_id = p.m_product_id
JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
-- Join to the material receipt (purchase from vendor, issotrx=N)
LEFT JOIN M_InOut receipt_io ON t.m_inout_id = receipt_io.m_inout_id
LEFT JOIN C_DocType receipt_dt ON receipt_io.c_doctype_id = receipt_dt.c_doctype_id
LEFT JOIN c_bpartner receipt_bp ON receipt_io.c_bpartner_id = receipt_bp.c_bpartner_id
-- Join to the material shipment (sale to customer, issotrx=Y) via lot number and product
LEFT JOIN M_HU_Trace as shipment_trace ON
    shipment_trace.lotnumber IS NOT DISTINCT FROM t.lotnumber
    AND shipment_trace.m_product_id = t.m_product_id
    AND shipment_trace.hutracetype = 'MATERIAL_SHIPMENT'
LEFT JOIN M_InOut shipment_io ON shipment_trace.m_inout_id = shipment_io.m_inout_id
LEFT JOIN C_DocType shipment_dt ON shipment_io.c_doctype_id = shipment_dt.c_doctype_id
LEFT JOIN c_bpartner shipment_bp ON shipment_io.c_bpartner_id = shipment_bp.c_bpartner_id
JOIN M_Product shipment_product ON shipment_trace.m_product_id = shipment_product.m_product_id
LEFT JOIN C_UOM shipment_uom on shipment_trace.c_uom_id = shipment_uom.c_uom_id
LEFT JOIN m_hu shipment_hu ON shipment_trace.m_hu_id = shipment_hu.m_hu_id
LEFT JOIN ad_ref_list shipment_hulu_clearancestatus ON 
    shipment_hulu_clearancestatus.ad_reference_id = 541540::numeric 
    AND shipment_hulu_clearancestatus.value::text = shipment_hu.clearancestatus::text
WHERE t.hutracetype IN ('MATERIAL_RECEIPT')
  -- Ensure this is a purchase receipt (from vendor)
  AND receipt_dt.isSOTrx = 'N'
  AND receipt_io.docstatus IN ('CO', 'CL')
  -- Ensure there is a corresponding shipment (to customer)
  AND shipment_io.m_inout_id IS NOT NULL
  AND shipment_dt.isSOTrx = 'Y'
  AND shipment_io.docstatus IN ('CO', 'CL')
  -- Ensure this product was NOT manufactured (no production order)
  AND NOT EXISTS (
      SELECT 1 FROM M_HU_Trace pt
      WHERE pt.lotnumber IS NOT DISTINCT FROM t.lotnumber
      AND pt.m_product_id = t.m_product_id
      AND pt.hutracetype IN ('PRODUCTION_ISSUE', 'PRODUCTION_RECEIPT')
  )
  AND EXISTS (SELECT 1 FROM T_Selection s WHERE s.AD_PInstance_ID = p_AD_PInstance_ID AND s.T_Selection_ID = t.m_hu_trace_id)

UNION ALL

-- =====================================================================================
-- SECTION 7: MATERIAL INVENTORY (Original - Both Product Types)
-- Handles inventory adjustments for all products
-- =====================================================================================
SELECT
    t.LotNumber AS LotNumber,
    'MATERIAL_INVENTORY' AS HUTraceType,
    p.value || '_' || p.name AS Product,
    io.documentno AS InOut,
    po.documentno AS PPOrder,
    i.documentno AS Inventory,
    COALESCE(io.movementdate, cc.movementdate, po.datepromised, i.movementdate) AS DocumentDate,
    CASE WHEN i.C_Doctype_ID = 540948 THEN -1 ELSE 1 END * ROUND(t.qty, u.stdprecision) AS Qty,
    u.uomsymbol AS UOM,
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
    null::numeric AS prod_stock,
    null::numeric AS traceid,
    to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate
FROM M_HU_Trace t
JOIN M_Product p ON t.m_product_id = p.m_product_id
JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
LEFT JOIN PP_cost_collector cc ON t.pp_cost_collector_id = cc.pp_cost_collector_id
LEFT JOIN PP_Order po ON t.pp_order_id = po.pp_order_id
LEFT JOIN M_Inventory i ON t.M_Inventory_ID = i.m_inventory_id
WHERE t.hutracetype = 'MATERIAL_INVENTORY'
  AND i.docstatus IN ('CO', 'CL')
  AND EXISTS (SELECT 1 FROM T_Selection s WHERE s.AD_PInstance_ID = p_AD_PInstance_ID AND s.T_Selection_ID = t.m_hu_trace_id)

ORDER BY LotNumber, pporder, HUTraceType, DocumentDate
$$;

alter function m_hu_trace_report(numeric) owner to metasfresh;

-- =====================================================================================
-- END OF MODIFIED FUNCTION
-- =====================================================================================
-- USAGE NOTES:
-- 
-- The modified function now returns results for both flows:
-- 
-- For MANUFACTURED products, the result set includes:
-- - Current Stock
-- - Material Receipt (raw materials from vendor)
-- - Production Issue (raw materials consumed in manufacturing)
-- - Production Receipt (finished goods produced)
-- - Production Receipt Detail (traceability from raw materials to finished goods)
-- - Material Shipment (finished goods shipped to customer)
-- - Material Inventory (inventory adjustments)
--
-- For NON-MANUFACTURED products, the result set includes:
-- - Current Stock
-- - Material Receipt (products purchased from vendor, issotrx=N)
-- - Direct Sale Detail (NEW - traceability from purchase to sale with customer info)
-- - Material Shipment (products shipped to customer, issotrx=Y)
-- - Material Inventory (inventory adjustments)
--
-- The new "DIRECT_SALE_DETAIL" section provides similar detailed traceability for
-- non-manufactured products as "PRODUCTION_RECEIPT_DETAL" does for manufactured ones.
-- =====================================================================================
