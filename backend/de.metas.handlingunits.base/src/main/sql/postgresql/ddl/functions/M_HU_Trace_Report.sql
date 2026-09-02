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
-- - Links purchase receipts (issotrx=N) to sales shipments (issotrx=Y) along the M_HU_Trace
--   transformation graph, falling back to labelled lot-level guesses where the graph is silent
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
        reportdate                 character varying,
        link_basis                 character varying
    )
    stable
    language sql
as
$$
-- =====================================================================================
-- SHARED CTE BLOCK
-- Used by SECTION 6 (DIRECT SALE DETAILS) only; the other sections do not reference it.
-- It derives the receipt-to-shipment pairing from the M_HU_Trace transformation graph
-- (VHU_Source_ID -> VHU_ID edges), and labels every emitted pair with the basis on which it was
-- paired (link_basis) so a proven link is distinguishable from a guess.
-- =====================================================================================
WITH RECURSIVE
-- Every descent edge: any trace row that records a source VHU, whatever its trace type -- the
-- transformation splits, and customer returns, which record the originally-shipped VHU as the
-- source and so still run forward along physical descent. The COLUMN defines the edge set, not
-- the type: adding a trace-type predicate here would silently drop return-chain traceability,
-- and no test would go red. On a production dataset every target VHU has exactly one source, so
-- the graph is a forest and attributing a shipped VHU back to one received VHU is unambiguous.
-- The walk below runs the other way -- forward, from a receipt to everything it became -- and may
-- legitimately fan out, one received pallet split into many shipped pieces. Deliberately not
-- restricted by trace type or by T_Selection: whether such a row is in the selection depends on
-- how the caller's recursion happened to reach it, so the walk must not depend on it.
vhu_edge AS (
    SELECT DISTINCT t.VHU_Source_ID AS src, t.VHU_ID AS dst
    FROM M_HU_Trace t
    WHERE t.VHU_Source_ID IS NOT NULL
      AND t.VHU_Source_ID <> t.VHU_ID
),
-- The MATERIAL_RECEIPT rows this report run is about, already narrowed to rows whose document
-- qualifies as a purchase receipt. Filtering here rather than in the final WHERE keeps the
-- traced and the candidate branch working off exactly the same set of eligible receipts, so a
-- pair that the final projection would discard can never suppress the candidates of its group.
receipt_trace AS (
    SELECT t.M_HU_Trace_ID, t.VHU_ID, t.M_HU_ID, t.M_Product_ID, t.LotNumber, t.M_InOut_ID, t.C_UOM_ID,
           t.AD_Client_ID, t.AD_Org_ID
    FROM M_HU_Trace t
    JOIN M_InOut receipt_io ON receipt_io.M_InOut_ID = t.M_InOut_ID
    JOIN C_DocType receipt_dt ON receipt_dt.C_DocType_ID = receipt_io.C_DocType_ID
    WHERE t.HUTraceType = 'MATERIAL_RECEIPT'
      AND receipt_dt.IsSOTrx = 'N'
      AND receipt_io.DocStatus IN ('CO', 'CL')
      AND EXISTS (SELECT 1 FROM T_Selection s
                   WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                     AND s.T_Selection_ID = t.M_HU_Trace_ID)
),
-- Forward walk from each receipt VHU. Depth 0 is the receipt's own VHU, which covers the
-- received-and-shipped-without-repacking case; deeper rows follow the transformation edges.
receipt_reach (M_HU_Trace_ID, VHU_ID, depth) AS (
    SELECT r.M_HU_Trace_ID, r.VHU_ID, 0
    FROM receipt_trace r
    UNION
    SELECT rr.M_HU_Trace_ID, e.dst, rr.depth + 1
    FROM receipt_reach rr
    JOIN vhu_edge e ON e.src = rr.VHU_ID
    -- Termination guard against a runaway or cyclic transformation chain: UNION alone cannot stop
    -- a cycle here because the depth column makes every revisit a distinct row. On a production
    -- dataset of 25 816 trace rows, of which 12 908 carry a source VHU (the distinct edge count is
    -- at most that, since vhu_edge de-duplicates and drops self-edges), the longest chain measured
    -- was 2 hops -- so 15 is far above anything real while still bounding a corrupt graph.
    WHERE rr.depth < 15
),
-- The MATERIAL_SHIPMENT rows this run is about, narrowed to rows whose document qualifies as a
-- customer shipment. Both sides are restricted to the report's selection, so a shipment outside
-- the run's scope can never be paired.
shipment_trace_sel AS (
    SELECT t.M_HU_Trace_ID, t.VHU_ID, t.M_HU_ID, t.M_Product_ID, t.LotNumber, t.M_InOut_ID, t.C_UOM_ID
    FROM M_HU_Trace t
    JOIN M_InOut shipment_io ON shipment_io.M_InOut_ID = t.M_InOut_ID
    JOIN C_DocType shipment_dt ON shipment_dt.C_DocType_ID = shipment_io.C_DocType_ID
    WHERE t.HUTraceType = 'MATERIAL_SHIPMENT'
      AND shipment_dt.IsSOTrx = 'Y'
      AND shipment_io.DocStatus IN ('CO', 'CL')
      AND EXISTS (SELECT 1 FROM T_Selection s
                   WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                     AND s.T_Selection_ID = t.M_HU_Trace_ID)
),
-- A pair is TRACED when the shipped VHU is reachable from the receipt VHU AND the two trace rows
-- agree on lot. Lot agreement is a consistency guard, not the pairing criterion: transformation
-- edges do connect trace rows carrying different lot numbers, and the report must not assert a
-- link across a relabelling it cannot explain.
-- The GROUP BY is the report row's identity: one row per (receipt document, shipment document,
-- product, lot), however many VHUs and trace rows the two documents carry for that product and
-- lot. This is de-duplication by construction, not by a DISTINCT over the projected columns.
traced_pair AS (
    SELECT r.M_InOut_ID AS receipt_inout_id, r.M_Product_ID, r.LotNumber,
           st.M_InOut_ID AS shipment_inout_id,
           min(st.M_HU_Trace_ID) AS shipment_trace_id,
           min(r.M_HU_ID)        AS receipt_hu_id,
           min(st.M_HU_ID)       AS shipment_hu_id,
           -- assumes every trace row of one document/product/lot shares a UOM. The quantities
           -- beside these are document-level sums, so a document that genuinely mixed UOMs would
           -- print the sum under only one of them.
           min(st.C_UOM_ID)      AS shipment_uom_id,
           min(r.C_UOM_ID)       AS receipt_uom_id,
           -- client/org of the receipt TRACE ROW, which is what prod_stock below filters on.
           -- Reducing them with min() is safe because the rows of one group agree: the writer of a
           -- MATERIAL_RECEIPT trace takes its org from the document's own M_HU_Assignment rows
           -- (HUTraceEventsService.createAndAddEvents), and never sets the client at all, so the
           -- row carries the installation's default. Were that ever violated, min() would pick the
           -- numerically smallest -- org '*' = 0 being the likely one -- and all three of product,
           -- client and org are equality filters inside getcurrentstoragestock, so prod_stock
           -- would silently read 0 instead of the real stock.
           min(r.AD_Client_ID)   AS receipt_client_id,
           min(r.AD_Org_ID)      AS receipt_org_id
    FROM receipt_trace r
    JOIN receipt_reach rr ON rr.M_HU_Trace_ID = r.M_HU_Trace_ID
    JOIN shipment_trace_sel st
           ON st.VHU_ID       = rr.VHU_ID
          AND st.M_Product_ID = r.M_Product_ID
          AND st.LotNumber IS NOT DISTINCT FROM r.LotNumber
    GROUP BY r.M_InOut_ID, r.M_Product_ID, r.LotNumber, st.M_InOut_ID
),
-- Lot-level guesses, emitted ONLY for the (shipment document, product, lot) groups the graph
-- leaves empty. A group with a traced receipt never also shows candidates.
-- The lot condition below is also what decides the fate of a graph-connected pair that the lot
-- guard above rejected: such a pair does not match here either, so it is DROPPED rather than
-- demoted to a candidate. Deliberate. A relabelling the data does not explain is not evidence of
-- a product-level link, and admitting one would re-open the cartesian at product granularity --
-- every receipt of the product against every shipment of it -- the very failure these pairing
-- rules exist to prevent.
-- Silence is the honest answer; PRODUCT_CANDIDATE keeps its single meaning of "neither side
-- carries a lot number".
-- The GROUP BY carries the same row identity as traced_pair above.
candidate_pair AS (
    SELECT r.M_InOut_ID AS receipt_inout_id, r.M_Product_ID, r.LotNumber,
           st.M_InOut_ID AS shipment_inout_id,
           min(st.M_HU_Trace_ID) AS shipment_trace_id,
           min(r.M_HU_ID)        AS receipt_hu_id,
           min(st.M_HU_ID)       AS shipment_hu_id,
           -- same UOM and client/org assumptions as traced_pair above
           min(st.C_UOM_ID)      AS shipment_uom_id,
           min(r.C_UOM_ID)       AS receipt_uom_id,
           min(r.AD_Client_ID)   AS receipt_client_id,
           min(r.AD_Org_ID)      AS receipt_org_id
    FROM receipt_trace r
    JOIN shipment_trace_sel st
           ON st.M_Product_ID = r.M_Product_ID
          AND st.LotNumber IS NOT DISTINCT FROM r.LotNumber
    WHERE NOT EXISTS (
        SELECT 1 FROM traced_pair tp
         WHERE tp.shipment_inout_id = st.M_InOut_ID
           AND tp.M_Product_ID      = st.M_Product_ID
           AND tp.LotNumber IS NOT DISTINCT FROM st.LotNumber
    )
    GROUP BY r.M_InOut_ID, r.M_Product_ID, r.LotNumber, st.M_InOut_ID
),
detail_pair AS (
    SELECT tp.*, 'TRACED'::varchar AS link_basis FROM traced_pair tp
    UNION ALL
    -- No lot on either side means the pair rests on product equality alone; a shared lot number
    -- is the stronger of the two guesses and is labelled apart from it.
    SELECT cp.*,
           (CASE WHEN cp.LotNumber IS NULL THEN 'PRODUCT_CANDIDATE' ELSE 'LOT_CANDIDATE' END)::varchar
      FROM candidate_pair cp
),
-- Document-level received quantity per (receipt document, product, lot). GROUP BY puts all NULL
-- lots in one group, which is what the IS NOT DISTINCT FROM join below matches; a combination
-- with no rows produces no join partner and therefore a NULL quantity, exactly as a correlated
-- SUM over no rows would.
-- The IN restricts the set of DOCUMENTS grouped here, never the rows summed for one of them, so
-- the quantity stays the document's full total. It is not a filter but a bound: the only document
-- ids this CTE is ever probed with come from receipt_trace, and without it every invocation of the
-- function would aggregate the whole of M_HU_Trace even when this section emits nothing.
receipt_doc_qty AS (
    SELECT rt.M_InOut_ID, rt.M_Product_ID, rt.LotNumber, SUM(rt.Qty) AS qty_sum
    FROM M_HU_Trace rt
    WHERE rt.HUTraceType = 'MATERIAL_RECEIPT'
      AND rt.M_InOut_ID IN (SELECT M_InOut_ID FROM receipt_trace)
    GROUP BY rt.M_InOut_ID, rt.M_Product_ID, rt.LotNumber
),
-- Document-level shipped quantity per (shipment document, product, lot). finished_product_qty
-- and shipmentqty are the same figure under two column names, so both read this one CTE.
-- Bounded to the shipment documents in scope for the same reason as receipt_doc_qty above.
shipment_doc_qty AS (
    SELECT stq.M_InOut_ID, stq.M_Product_ID, stq.LotNumber, SUM(stq.Qty) AS qty_sum
    FROM M_HU_Trace stq
    WHERE stq.HUTraceType = 'MATERIAL_SHIPMENT'
      AND stq.M_InOut_ID IN (SELECT M_InOut_ID FROM shipment_trace_sel)
    GROUP BY stq.M_InOut_ID, stq.M_Product_ID, stq.LotNumber
)
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
    to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate,
    null::varchar as link_basis
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
    to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate,
    null::varchar as link_basis
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
    to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate,
    null::varchar as link_basis
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
    to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate,
    null::varchar as link_basis
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
    to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate,
    null::varchar as link_basis
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
-- SECTION 6: DIRECT SALE DETAILS (Non-Manufactured Products)
-- Handles the direct purchase-to-sale flow: material receipts from vendors (issotrx=N) paired
-- with material shipments to customers (issotrx=Y).
-- The pairing is derived in the CTE block at the top of this function: a pair is TRACED when the
-- shipped VHU is reachable from the received VHU along the transformation graph and both ends
-- agree on lot; where the graph is silent for a (shipment document, product, lot) group, the
-- lot-level or product-level guesses are emitted for that group instead, and say so in link_basis.
-- One row per (receipt document, shipment document, product, lot) -- the aggregation that produces
-- that identity lives in traced_pair / candidate_pair, so no DISTINCT is needed or wanted here:
-- a duplicate would be a defect in the pairing and must stay visible rather than be swallowed.
-- =====================================================================================
SELECT
    dp.LotNumber AS LotNumber,
    'DIRECT_SALE_DETAIL' AS HUTraceType,
    p.value || '_' || p.name AS Product,
    receipt_io.documentno AS InOut,
    NULL AS PPOrder,
    NULL AS Inventory,
    receipt_io.movementdate AS DocumentDate,
    -- the receipt document's own quantity for this product and lot, summed over the document's
    -- VHUs -- so the received-versus-shipped proportion is readable from the row itself.
    ABS(ROUND(rq.qty_sum, COALESCE(ru.stdprecision, 0))) AS Qty,
    ru.uomsymbol AS UOM,
    'MATERIAL_SHIPMENT' as detail_type,
    p.value as finished_product_no,
    p.name as finished_product_name,
    ABS(ROUND(sq.qty_sum, COALESCE(su.stdprecision, 0))) as finished_product_qty,
    su.uomsymbol as finished_product_uom,
    dp.LotNumber as finished_product_lot,
    -- 1000029: M_Attribute (vendor lot) -- the FK m_hu_attribute.m_attribute_id, not the
    -- m_hu_attribute PK
    (select value from m_hu_attribute vendorlot
      where vendorlot.m_hu_id = dp.receipt_hu_id and vendorlot.m_attribute_id = 1000029::numeric) as vendorlot,
    -- 540020: M_Attribute HU_BestBeforeDate (Mindesthaltbarkeit)
    (select to_char(valuedate, 'DD.MM.YYYY') from m_hu_attribute mhd
      where mhd.m_hu_id = dp.shipment_hu_id and mhd.m_attribute_id = 540020::numeric) as finished_product_mhd,
    shipment_hulu_clearancestatus.name as finished_product_clearance,
    shipment_bp.value as customer_no,
    shipment_bp.name as customer,
    -- same figure as finished_product_qty above, under the column name the report prints as
    -- "2_Liefermenge"; both read the one shipment_doc_qty CTE
    ABS(ROUND(sq.qty_sum, COALESCE(su.stdprecision, 0))) as shipmentqty,
    shipment_io.documentno as shipment_note,
    to_char(shipment_io.movementdate, 'DD.MM.YYYY') as shipment_date,
    -- 1000017: M_Attribute Lot-Nummer. UOM and client/org come from the receipt trace row; all
    -- three are equality filters inside getcurrentstoragestock, so which row they come from matters.
    getcurrentstoragestock(dp.M_Product_ID, dp.receipt_uom_id, 1000017, dp.LotNumber,
                           dp.receipt_client_id, dp.receipt_org_id) AS prod_stock,
    dp.shipment_trace_id AS traceid,
    to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate,
    dp.link_basis
FROM detail_pair dp
JOIN M_Product p         ON p.m_product_id = dp.M_Product_ID
JOIN M_InOut receipt_io  ON receipt_io.m_inout_id = dp.receipt_inout_id
JOIN M_InOut shipment_io ON shipment_io.m_inout_id = dp.shipment_inout_id
LEFT JOIN c_bpartner shipment_bp ON shipment_io.c_bpartner_id = shipment_bp.c_bpartner_id
LEFT JOIN C_UOM su ON su.c_uom_id = dp.shipment_uom_id
LEFT JOIN C_UOM ru ON ru.c_uom_id = dp.receipt_uom_id
LEFT JOIN receipt_doc_qty rq
       ON rq.M_InOut_ID   = dp.receipt_inout_id
      AND rq.M_Product_ID = dp.M_Product_ID
      AND rq.LotNumber IS NOT DISTINCT FROM dp.LotNumber
LEFT JOIN shipment_doc_qty sq
       ON sq.M_InOut_ID   = dp.shipment_inout_id
      AND sq.M_Product_ID = dp.M_Product_ID
      AND sq.LotNumber IS NOT DISTINCT FROM dp.LotNumber
LEFT JOIN m_hu shipment_hu ON shipment_hu.m_hu_id = dp.shipment_hu_id
-- 541540: AD_Reference "Clearance", the HU clearance-status list
LEFT JOIN ad_ref_list shipment_hulu_clearancestatus
       ON shipment_hulu_clearancestatus.ad_reference_id = 541540::numeric
      AND shipment_hulu_clearancestatus.value::text = shipment_hu.clearancestatus::text
-- the receipt document's isSOTrx/docstatus and the shipment document's are already enforced in
-- receipt_trace / shipment_trace_sel, so that both pairing branches see the same eligible documents
WHERE NOT EXISTS (
      -- this section is for products that were not manufactured
      SELECT 1 FROM M_HU_Trace pt
       WHERE pt.LotNumber IS NOT DISTINCT FROM dp.LotNumber
         AND pt.M_Product_ID = dp.M_Product_ID
         AND pt.HUTraceType IN ('PRODUCTION_ISSUE', 'PRODUCTION_RECEIPT')
  )

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
    to_char(now(), 'DD.MM.YYYY HH24:MM') as reportdate,
    null::varchar as link_basis
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
