-- ===========================================================================
-- ATOMIC PORT UNIT: 5820910 + 5821020 + 5821060 + 5821140
-- Port / cherry-pick / propagate ALL FOUR TOGETHER -- NEVER a subset.
-- ===========================================================================
-- These four scripts successively redefine the same two report functions
-- (de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_description and
-- ..._productdetails). Applied whole, the chain ends correct. Applied PARTIALLY, the instance gets
-- a silently WRONG delivery-instruction PDF -- no error, no failed migration, just wrong paper.
-- The intermediate states are genuinely wrong, not merely superseded:
--   * after 5821020 alone -- _productdetails groups by p.m_product_id only, with
--     MIN(uomt.uomsymbol) as the unit: it SUMS QUANTITIES ACROSS DIFFERENT UOMs and prints an
--     arbitrary one (4 Ea + 6 kg of one product print as a single row reading "10 Ea").
--     5821060 repairs it by adding dp.c_uom_id to the GROUP BY.
--   * after 5820910 / 5821020 / 5821060 -- _productdetails INNER JOINs m_warehouse and C_UOM_trl,
--     so a planning without a warehouse, or a product whose UOM has no translation in the report
--     language, VANISHES from the detail band. 5821140 converts both to LEFT JOIN (+ COALESCE onto
--     C_UOM.uomsymbol for the unit).
--   * after 5820910 alone -- _description INNER JOINs C_Order through the first allocation's
--     planning, so a planning with no C_Order_ID BLANKS THE WHOLE HEADER BAND. 5821020 drops that
--     join again.
-- This is not hypothetical here: the DoD propagates this issue's branch up to new_dawn_uat, and any
-- later hotfix cherry-pick faces the same choice. Move the four as one unit
-- (see skill metasfresh-patch-porter, "Propagation flow").
-- ===========================================================================

-- docs_deliveryinstructions_productdetails: fixes two aggregation defects introduced when the
-- function moved from one row per M_Delivery_Planning to one row per M_Product_ID.
--
-- Different M_Delivery_Planning_Alloc rows for the same product on one instruction can carry
-- different C_UOM_ID (e.g. two purchase orders for the same product in different units), because
-- the instruction's admissibility check does not constrain C_UOM_ID or M_Product_ID. Summing
-- quantities in different units into one total is meaningless, so C_UOM_ID is added to the
-- GROUP BY: a UOM split now renders as separate rows for that article, each with its own unit and
-- its own correct sum, instead of silently adding mismatched quantities together.
--
-- Different M_Delivery_Planning_Alloc rows for the same product can also come from different
-- M_Warehouse_ID (the admissibility check constrains only the warehouse's resolved address, not
-- the warehouse itself -- e.g. hub consolidation across two logical warehouses at one address).
-- MIN(wh.name) silently dropped a genuine second pickup location; STRING_AGG lists every distinct
-- warehouse name instead.
--
-- Column set and types are unchanged, so CREATE OR REPLACE is sufficient.

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_productdetails(p_m_shippertransportation_id numeric, p_ad_language character varying)
 RETURNS TABLE(warehousename character varying, plannedloadedquantity numeric, qtyordered numeric, productvalue character varying, productname character varying, uom character varying)
 LANGUAGE sql
 STABLE
AS $function$
SELECT STRING_AGG(DISTINCT wh.name, ', ' ORDER BY wh.name) AS warehouseName,
       SUM(dp.plannedloadedquantity)                       AS plannedloadedquantity,
       SUM(dp.qtyordered)                                  AS qtyordered,
       p.value                                             AS productValue,
       p.name                                              AS productName,
       MIN(uomt.uomsymbol)                                 AS uom
FROM M_ShipperTransportation st
         JOIN m_delivery_planning_alloc dpa ON dpa.m_shippertransportation_id = st.m_shippertransportation_id AND dpa.isactive = 'Y'
         JOIN m_delivery_planning dp ON dp.m_delivery_planning_id = dpa.m_delivery_planning_id
         JOIN m_warehouse wh ON dp.m_warehouse_id = wh.m_warehouse_id
         JOIN M_product p ON dp.m_product_id = p.m_product_id
         JOIN C_UOM uom ON dp.c_uom_id = uom.c_uom_id
         JOIN C_UOM_trl uomt ON dp.c_uom_id = uomt.c_uom_id and uomt.ad_language=p_ad_language
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
GROUP BY p.m_product_id, dp.c_uom_id
$function$
;
