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
