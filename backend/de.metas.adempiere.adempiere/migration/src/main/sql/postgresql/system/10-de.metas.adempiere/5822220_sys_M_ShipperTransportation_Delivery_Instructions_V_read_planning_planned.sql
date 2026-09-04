-- Source DDL: backend/de.metas.adempiere.adempiere/migration/src/main/sql/postgresql/ddl/public/views/M_ShipperTransportation_Delivery_Instructions_V.sql
--
-- Task Q14 (delivery planning quantities): this view aliased the package's own actualloadqty/
-- actualdischargequantity AS plannedloadedquantity/planneddischargequantity - a pre-existing mismatch
-- between the exposed name and the physical source, made moot now that M_ShippingPackage no longer
-- carries these as physical columns. Read the planning (dp, already joined) directly under its own
-- column names instead - same values (the package's actual columns were themselves copies of the
-- planning's planned figures at generation time), correct source.
--
-- Also converts this view's DDL to the standard db_alter_view($new) pattern - it previously used a bare
-- DROP VIEW + CREATE OR REPLACE VIEW.

DROP VIEW IF EXISTS M_ShipperTransportation_Delivery_Instructions_V$new
;

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
       dp.plannedloadedquantity,
       dp.planneddischargequantity,
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
               'm_shippertransportation_delivery_instructions_v',
               (SELECT view_definition
                FROM information_schema.views
                WHERE lower(views.table_name) = lower('m_shippertransportation_delivery_instructions_v$new'))
           )
;

DROP VIEW IF EXISTS m_shippertransportation_delivery_instructions_v$new
;
