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

-- docs_deliveryinstructions_productdetails: adds the order document number and PO reference,
-- aggregated per article now that the printed instruction can carry several plannings and
-- therefore several source orders. A single header-level order reference would be misleading
-- once more than one order is behind an instruction, so the reference moves to this per-article
-- detail band instead, narrowing the aggregation from the whole document to one article.
--
-- Each article's plannings can come from different orders (or from purchase orders with
-- different references), so both new columns are STRING_AGG(DISTINCT ..., ', ' ORDER BY ...) --
-- deterministic order, no duplicate when several plannings share one order. The order is
-- LEFT JOINed because M_Delivery_Planning.C_Order_ID is nullable and an unmatched planning must
-- not drop its row out of the other aggregated columns (warehouse, quantities).
--
-- The return type gains two columns, so DROP FUNCTION is required before CREATE -- Postgres
-- refuses a CREATE OR REPLACE that changes the return type.
--
-- The warehouse and the UOM translation are LEFT JOINed for the same reason as the order: this
-- feeds a printed customer document, where an INNER JOIN drops the whole article line silently -
-- undetectable at the point of failure. M_Delivery_Planning.M_Warehouse_ID is nullable, and a
-- C_UOM need not have a C_UOM_trl row in the language the instruction is printed in; neither may
-- cost the reader the line. The UOM symbol therefore falls back to the untranslated
-- C_UOM.uomsymbol (COALESCE) rather than printing nothing.

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_productdetails(numeric, character varying);

CREATE FUNCTION de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_productdetails(p_m_shippertransportation_id numeric, p_ad_language character varying)
 RETURNS TABLE(warehousename character varying, plannedloadedquantity numeric, qtyordered numeric, productvalue character varying, productname character varying, uom character varying, orderno character varying, referenceno character varying)
 LANGUAGE sql
 STABLE
AS $function$
SELECT STRING_AGG(DISTINCT wh.name, ', ' ORDER BY wh.name)         AS warehouseName,
       SUM(dp.plannedloadedquantity)                               AS plannedloadedquantity,
       SUM(dp.qtyordered)                                          AS qtyordered,
       p.value                                                     AS productValue,
       p.name                                                      AS productName,
       COALESCE(MIN(uomt.uomsymbol), MIN(uom.uomsymbol))           AS uom,
       STRING_AGG(DISTINCT o.documentno, ', ' ORDER BY o.documentno)   AS orderno,
       STRING_AGG(DISTINCT o.poreference, ', ' ORDER BY o.poreference) AS referenceno
FROM M_ShipperTransportation st
         JOIN m_delivery_planning_alloc dpa ON dpa.m_shippertransportation_id = st.m_shippertransportation_id AND dpa.isactive = 'Y'
         JOIN m_delivery_planning dp ON dp.m_delivery_planning_id = dpa.m_delivery_planning_id
         LEFT JOIN m_warehouse wh ON dp.m_warehouse_id = wh.m_warehouse_id
         JOIN M_product p ON dp.m_product_id = p.m_product_id
         JOIN C_UOM uom ON dp.c_uom_id = uom.c_uom_id
         LEFT JOIN C_UOM_trl uomt ON dp.c_uom_id = uomt.c_uom_id and uomt.ad_language=p_ad_language
         LEFT JOIN C_Order o ON o.c_order_id = dp.c_order_id
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
GROUP BY p.m_product_id, dp.c_uom_id
$function$
;
