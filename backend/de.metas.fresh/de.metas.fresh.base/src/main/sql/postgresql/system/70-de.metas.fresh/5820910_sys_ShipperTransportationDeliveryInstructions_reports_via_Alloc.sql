-- Source DDL: backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/
--             docs_deliveryinstructions_{description,forwarder,productdetails}.sql
--
-- Re-point the three de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_* report
-- functions off M_ShipperTransportation.M_Delivery_Planning_ID and onto M_Delivery_Planning_Alloc:
-- an instruction can aggregate several M_Delivery_Planning rows, each with its own
-- M_ShippingPackage and possibly its own C_Order, C_UOM and M_Warehouse.
--
-- _description and _forwarder resolve exactly ONE planning per instruction, deterministically:
-- active allocations only, lowest LineNo, tiebreak lowest M_Delivery_Planning_ID. _description
-- drops orderno/referenceno: they came from the single C_Order behind that arbitrarily-picked
-- planning, which is not a meaningful header-level concept once several orders can stand behind
-- one instruction. The order reference lives in _productdetails' per-article detail band instead.
--
-- _productdetails returns one row per ARTICLE per UNIT, not one per allocation, and carries
-- orderno / referenceno:
--   * GROUP BY p.m_product_id, dp.c_uom_id, NOT by product alone. Two allocations for the same
--     product on one instruction can carry different C_UOM_ID (two purchase orders for the same
--     product in different units); the instruction's admissibility check constrains neither
--     C_UOM_ID nor M_Product_ID. Grouping by product alone SUMS QUANTITIES ACROSS DIFFERENT UNITS
--     and prints an arbitrary one of them -- 4 Ea + 6 kg would print as a single row reading
--     "10 Ea". A unit split renders as separate rows instead, each with its own correct sum.
--   * STRING_AGG over the warehouse name, NOT MIN(). Allocations for one product can come from
--     different M_Warehouse_ID (the admissibility check constrains the warehouse's resolved
--     ADDRESS, not the warehouse itself -- e.g. hub consolidation across two logical warehouses at
--     one address); MIN() would silently drop a genuine second pickup location. Both order columns
--     are STRING_AGG(DISTINCT ..., ', ' ORDER BY ...) for the same reason.
--   * LEFT JOIN on m_warehouse, C_UOM_trl and C_Order, NOT inner. M_Delivery_Planning's
--     M_Warehouse_ID and C_Order_ID are both nullable, and a C_UOM need not have a C_UOM_trl row
--     in the language the instruction is printed in; an INNER JOIN would silently drop the whole
--     article line of a printed customer document. The unit falls back to the untranslated
--     C_UOM.uomsymbol.

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_description(numeric);

CREATE FUNCTION de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_description(p_m_shippertransportation_id numeric)
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

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_forwarder(p_m_shippertransportation_id numeric)
 RETURNS TABLE(address text, transportdetails text)
 LANGUAGE sql
 STABLE
AS $function$
SELECT (COALESCE(bp.name || E'\n', '') || COALESCE(bpl.address, '')) AS address, dp.transportdetails
FROM M_ShipperTransportation st
         LEFT JOIN M_shipper sh ON st.m_shipper_id = sh.m_shipper_id
         LEFT JOIN c_bpartner bp ON sh.c_bpartner_id = bp.c_bpartner_id
         LEFT JOIN C_BPartner_location bpl ON bpl.c_bpartner_id = bp.c_bpartner_id AND (bpl.isshiptodefault = 'Y' OR bpl.isshipto = 'Y')
         LEFT JOIN LATERAL (
             SELECT dpa.m_delivery_planning_id
             FROM m_delivery_planning_alloc dpa
             WHERE dpa.m_shippertransportation_id = st.m_shippertransportation_id
               AND dpa.isactive = 'Y'
             ORDER BY dpa.lineno, dpa.m_delivery_planning_id
             LIMIT 1
         ) dpa ON TRUE
         LEFT JOIN m_delivery_planning dp ON dp.m_delivery_planning_id = dpa.m_delivery_planning_id
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
LIMIT 1
$function$
;

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
