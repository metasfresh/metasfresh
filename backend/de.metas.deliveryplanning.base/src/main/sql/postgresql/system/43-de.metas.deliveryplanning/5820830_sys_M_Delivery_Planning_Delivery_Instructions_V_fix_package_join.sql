-- Source DDL: backend/de.metas.adempiere.adempiere/migration/src/main/sql/postgresql/ddl/public/views/M_Delivery_Planning_Delivery_Instructions_V.sql
--
-- Same cartesian-product bug as M_ShipperTransportation_Delivery_Instructions_V (which installs the
-- correlated join directly, in 5820920),
-- present in this sibling view since it was introduced: M_ShippingPackage AND M_Delivery_Planning
-- are both joined to M_ShipperTransportation by instruction id only, with no correlation to each
-- other. DeliveryPlanningRepository.createAllocation creates one distinct M_ShippingPackage per
-- allocation, and every planning combined into one instruction shares that instruction's
-- M_ShipperTransportation_ID (DeliveryPlanningRepository.updateDeliveryPlanningFromInstruction).
-- At one active allocation per instruction (N=1) both joins degenerate to a harmless 1x1 pairing;
-- at N active allocations they produce the full N x N cross product -- every planning paired with
-- every package's quantities, and the M_ShippingPackage_ID key repeats N times.
--
-- Fix: correlate both M_ShippingPackage and M_Delivery_Planning to the same allocation row
-- (M_Delivery_Planning_Alloc) instead of each independently to the instruction.

DROP VIEW IF EXISTS M_Delivery_Planning_Delivery_Instructions_V$new
;

CREATE OR REPLACE VIEW M_Delivery_Planning_Delivery_Instructions_V$new
AS
SELECT di.documentno,
       di.m_shippertransportation_id,
       dp.m_delivery_planning_id,
       di.docstatus,
       di.datedoc,
       di.c_bpartner_location_loading_id,
       di.etd AS etd,
       di.c_bpartner_location_delivery_id,
       di.eta AS eta,
       di.c_incoterms_id,
       di.incotermlocation,
       di.m_meansoftransportation_id,
       sp.M_Product_ID,
       sp.m_locator_id,
       sp.actualloadqty,
       sp.actualdischargequantity,
       sp.M_ShippingPackage_ID,
       di.created,
       di.createdby,
       sp.M_ShippingPackage_ID AS M_Delivery_Planning_Delivery_Instructions_V_ID,
       di.updated,
       di.updatedby,
       di.isactive,
       di.ad_org_id,
       di.ad_client_id
FROM M_ShipperTransportation di
         JOIN M_Delivery_Planning_Alloc dpa
              ON dpa.m_shippertransportation_id = di.m_shippertransportation_id AND dpa.isactive = 'Y'
         JOIN M_ShippingPackage sp ON sp.m_shippingpackage_id = dpa.m_shippingpackage_id
         JOIN M_Delivery_Planning dp ON dp.m_delivery_planning_id = dpa.m_delivery_planning_id
WHERE di.docstatus NOT IN ('VO', 'RE')
;

SELECT db_alter_view(
               'm_delivery_planning_delivery_instructions_v',
               (SELECT view_definition
                FROM information_schema.views
                WHERE lower(views.table_name) = lower('m_delivery_planning_delivery_instructions_v$new'))
           )
;

DROP VIEW IF EXISTS m_delivery_planning_delivery_instructions_v$new
;
