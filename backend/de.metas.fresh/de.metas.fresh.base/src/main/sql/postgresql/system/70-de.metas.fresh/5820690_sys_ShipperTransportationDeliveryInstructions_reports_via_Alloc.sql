-- Re-point the 3 de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_* report functions
-- off M_ShipperTransportation.M_Delivery_Planning_ID (being dropped) and onto M_Delivery_Planning_Alloc.
-- Grouped in one script because all three are the same atomic re-point of the same FK removal;
-- only docs_deliveryinstructions_description has a checked-in DDL source file (updated alongside):
-- Source DDL: backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/docs_deliveryinstructions_description.sql
-- docs_deliveryinstructions_forwarder / docs_deliveryinstructions_productdetails have no DDL source
-- file (they live only in migration history) -- none is created here, matching existing convention.
--
-- Every return type stays byte-identical to the pre-change function (verified against the live DB) --
-- CREATE OR REPLACE is safe. docs_deliveryinstructions_description / _forwarder resolve exactly ONE
-- planning per instruction, deterministically: active allocations only, lowest LineNo, tiebreak lowest
-- M_Delivery_Planning_ID -- this preserves today's single-row output for the current 1:1 case.
-- docs_deliveryinstructions_productdetails intentionally returns one row per active allocation
-- (fan-out is the correct reading for that function); no GROUP BY is added here.

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_description(p_m_shippertransportation_id numeric)
 RETURNS TABLE(forwarderaddress text, transportdetails text, deliveryaddress text, deliverycontactname character varying, deliverycontactphone character varying, loadingaddress text, loadingdate timestamp without time zone, loadingtime character varying, deliverydate timestamp without time zone, documentno character varying, creator character varying, creatorphone character varying, creatorfax character varying, creatoremail character varying, orderno character varying, referenceno character varying, incoterms character varying, incotermlocation character varying, meansoftransport text)
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
       o.documentno  AS orderno,
       o.poreference AS referenceno,
       ic.name       AS incoterms,
       st.incotermlocation,
       mt.name       AS meansoftransport

FROM de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_LoadingAddress(p_m_shippertransportation_id) AS l,
     de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_Forwarder(p_m_shippertransportation_id) AS f,
     de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_DeliveryAddress(p_m_shippertransportation_id) AS d,
     M_ShipperTransportation st
         JOIN ad_user u ON st.createdby = u.ad_user_id
         JOIN LATERAL (
             SELECT dpa.m_delivery_planning_id
             FROM m_delivery_planning_alloc dpa
             WHERE dpa.m_shippertransportation_id = st.m_shippertransportation_id
               AND dpa.isactive = 'Y'
             ORDER BY dpa.lineno, dpa.m_delivery_planning_id
             LIMIT 1
         ) dpa ON TRUE
         JOIN m_delivery_planning dp ON dp.m_delivery_planning_id = dpa.m_delivery_planning_id
         JOIN C_order o ON o.c_order_id = dp.c_order_id
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

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_productdetails(p_m_shippertransportation_id numeric, p_ad_language character varying)
 RETURNS TABLE(warehousename character varying, plannedloadedquantity numeric, qtyordered numeric, productvalue character varying, productname character varying, uom character varying)
 LANGUAGE sql
 STABLE
AS $function$
SELECT wh.name                                  AS warehouseName,
       dp.plannedloadedquantity,
       dp.qtyordered,
       p.value                                  AS productValue,
       p.name                                   AS productName,
       COALESCE(uomt.uomsymbol, uomt.uomsymbol) AS uom
FROM M_ShipperTransportation st
         JOIN m_delivery_planning_alloc dpa ON dpa.m_shippertransportation_id = st.m_shippertransportation_id AND dpa.isactive = 'Y'
         JOIN m_delivery_planning dp ON dp.m_delivery_planning_id = dpa.m_delivery_planning_id
         JOIN m_warehouse wh ON dp.m_warehouse_id = wh.m_warehouse_id
         JOIN M_product p ON dp.m_product_id = p.m_product_id
         JOIN C_UOM uom ON dp.c_uom_id = uom.c_uom_id
         JOIN C_UOM_trl uomt ON dp.c_uom_id = uomt.c_uom_id and uomt.ad_language=p_ad_language
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
$function$
;
