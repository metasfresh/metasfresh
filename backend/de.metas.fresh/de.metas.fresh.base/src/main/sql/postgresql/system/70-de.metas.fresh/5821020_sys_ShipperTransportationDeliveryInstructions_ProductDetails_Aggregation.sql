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

-- Deliver-instruction report functions no longer assume exactly one M_Delivery_Planning per
-- M_ShipperTransportation: several plannings can now be aggregated onto one instruction via
-- M_Delivery_Planning_Alloc, each with its own M_ShippingPackage.
--
-- docs_deliveryinstructions_productdetails: aggregates one row per M_Product_ID across all active
-- allocations of the instruction (previously one row per allocation). Column set and types are
-- unchanged, so CREATE OR REPLACE is sufficient.
--
-- docs_deliveryinstructions_description: drops orderno/referenceno. Those came from the single
-- C_Order behind the arbitrarily-picked "first" planning, which is not a meaningful concept once
-- several orders/plannings can stand behind one instruction. Dropping the columns removes the whole
-- planning/order join from the function, so it changes the RETURNS TABLE signature -- this needs
-- DROP FUNCTION before CREATE because CREATE OR REPLACE cannot change a return type.

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_description(p_m_shippertransportation_id numeric);
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_description(p_m_shippertransportation_id numeric)
 RETURNS TABLE(forwarderaddress text, transportdetails text, deliveryaddress text, deliverycontactname character varying, deliverycontactphone character varying, loadingaddress text, loadingdate timestamp without time zone, loadingtime character varying, deliverydate timestamp without time zone, documentno character varying, creator character varying, creatorphone character varying, creatorfax character varying, creatoremail character varying, incoterms character varying, incotermlocation character varying, meansoftransport text)
 LANGUAGE sql
 STABLE
AS $function$
SELECT f.*,
       d.*,
       l.*,
       st.etd        AS loadingdate,
       st.loadingtime,
       st.eta        AS deliverydate,
       st.documentno,
       u.name        AS Creator,
       u.phone       AS CreatorPhone,
       u.fax         AS CreatorFax,
       u.email       AS CreatorEmail,
       ic.name       AS incoterms,
       st.incotermlocation,
       mt.name       AS meansoftransport

FROM de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_LoadingAddress(p_m_shippertransportation_id) AS l,
     de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_Forwarder(p_m_shippertransportation_id) AS f,
     de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_DeliveryAddress(p_m_shippertransportation_id) AS d,
     M_ShipperTransportation st
         JOIN ad_user u ON st.createdby = u.ad_user_id
         JOIN c_incoterms ic ON ic.c_incoterms_id = st.c_incoterms_id
         LEFT JOIN m_meansoftransportation mt ON mt.m_meansoftransportation_id = st.m_meansoftransportation_id
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
$function$
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_productdetails(p_m_shippertransportation_id numeric, p_ad_language character varying)
 RETURNS TABLE(warehousename character varying, plannedloadedquantity numeric, qtyordered numeric, productvalue character varying, productname character varying, uom character varying)
 LANGUAGE sql
 STABLE
AS $function$
SELECT MIN(wh.name)                  AS warehouseName,
       SUM(dp.plannedloadedquantity) AS plannedloadedquantity,
       SUM(dp.qtyordered)            AS qtyordered,
       p.value                       AS productValue,
       p.name                        AS productName,
       MIN(uomt.uomsymbol)           AS uom
FROM M_ShipperTransportation st
         JOIN m_delivery_planning_alloc dpa ON dpa.m_shippertransportation_id = st.m_shippertransportation_id AND dpa.isactive = 'Y'
         JOIN m_delivery_planning dp ON dp.m_delivery_planning_id = dpa.m_delivery_planning_id
         JOIN m_warehouse wh ON dp.m_warehouse_id = wh.m_warehouse_id
         JOIN M_product p ON dp.m_product_id = p.m_product_id
         JOIN C_UOM uom ON dp.c_uom_id = uom.c_uom_id
         JOIN C_UOM_trl uomt ON dp.c_uom_id = uomt.c_uom_id and uomt.ad_language=p_ad_language
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
GROUP BY p.m_product_id
$function$
;
