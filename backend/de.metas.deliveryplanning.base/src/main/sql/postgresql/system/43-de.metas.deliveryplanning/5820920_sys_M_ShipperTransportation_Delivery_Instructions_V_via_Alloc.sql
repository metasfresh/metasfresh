-- Source DDL: backend/de.metas.adempiere.adempiere/migration/src/main/sql/postgresql/ddl/public/views/M_ShipperTransportation_Delivery_Instructions_V.sql
--
-- Re-point M_ShipperTransportation_Delivery_Instructions_V off M_ShipperTransportation.M_Delivery_Planning_ID
-- (being dropped) and onto M_Delivery_Planning_Alloc. The view still exposes m_delivery_planning_id --
-- under aggregation it now legitimately returns one row per (instruction, planning) pair instead of
-- one row per instruction. (AD_Tab 546754, which used to filter on this column, is re-parented onto
-- the instruction itself -- an aggregated header has N plannings and no single M_Delivery_Planning_ID
-- to filter on.)
--
-- M_ShippingPackage is correlated to its own allocation row (M_Delivery_Planning_Alloc), not re-joined
-- to the instruction independently of M_Delivery_Planning: DeliveryPlanningRepository.createAllocation
-- creates one distinct M_ShippingPackage per allocation, so an instruction with N active allocations has
-- N packages sharing that instruction id. Joining M_ShippingPackage to the instruction id alone would
-- degenerate to a harmless 1x1 pairing at N=1 but produce the full N x N cross product at N>1 -- every
-- planning paired with every package's quantities, making plannedloadedquantity/planneddischargequantity
-- wrong on most rows. Correlating both to the same M_Delivery_Planning_Alloc row keeps the join 1:1 per
-- allocation regardless of N.

DROP VIEW IF EXISTS M_ShipperTransportation_Delivery_Instructions_V$new;

CREATE OR REPLACE VIEW M_ShipperTransportation_Delivery_Instructions_V$new
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
       sp.actualloadqty as plannedloadedquantity,
       sp.actualdischargequantity as planneddischargequantity,
       di.created,
       di.createdby,
       sp.m_shippertransportation_id AS M_Delivery_Planning_Delivery_Instructions_V_ID,
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
;

SELECT db_alter_view(
    'M_ShipperTransportation_Delivery_Instructions_V',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(table_name) = lower('M_ShipperTransportation_Delivery_Instructions_V$new'))
);

DROP VIEW IF EXISTS M_ShipperTransportation_Delivery_Instructions_V$new;
