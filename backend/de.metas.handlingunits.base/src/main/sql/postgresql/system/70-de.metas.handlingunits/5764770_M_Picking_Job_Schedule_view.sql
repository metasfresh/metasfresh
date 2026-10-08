DROP VIEW IF EXISTS M_Picking_Job_Schedule_view
;

CREATE OR REPLACE VIEW M_Picking_Job_Schedule_view AS
SELECT -- Composed Key:
       shipment_sched.m_shipmentschedule_id,
       COALESCE(job_sched.m_picking_job_schedule_id, 0) AS m_picking_job_schedule_id,
       --
       shipment_sched.c_bpartner_customer_id,
       shipment_sched.c_bpartner_location_id,
       shipment_sched.bpartnerlocationname,
       shipment_sched.bpartneraddress_override,
       shipment_sched.c_orderso_id,
       shipment_sched.poreference,
       shipment_sched.handover_partner_id,
       shipment_sched.handover_location_id,
       shipment_sched.setup_place_no,
       shipment_sched.dateordered,
       shipment_sched.c_orderlineso_id,
       shipment_sched.linenetamt,
       shipment_sched.c_currency_id,
       shipment_sched.m_warehouse_id,
       shipment_sched.preparationdate,
       shipment_sched.m_product_id,
       shipment_sched.c_uom_id,
       shipment_sched.m_attributesetinstance_id,
       shipment_sched.qtyordered,
       shipment_sched.qtytodeliver,
       shipment_sched.qtydelivered,
       shipment_sched.qtypickedanddelivered,
       shipment_sched.qtypickednotdelivered,
       shipment_sched.qtypickedplanned,
       shipment_sched.qtypickedordelivered,
       shipment_sched.deliveryviarule,
       shipment_sched.deliverydate,
       shipment_sched.priorityrule,
       shipment_sched.iscatchweight,
       shipment_sched.catch_uom_id,
       shipment_sched.m_shipper_id,
       shipment_sched.packto_hu_pi_item_product_id,
       shipment_sched.datepromised,
       shipment_sched.isfixeddatepromised,
       shipment_sched.isfixedpreparationdate,
       --
       shipment_sched.ad_client_id,
       shipment_sched.ad_org_id,
       shipment_sched.created,
       shipment_sched.createdby,
       shipment_sched.updated,
       shipment_sched.updatedby,
       shipment_sched.isactive,
       --
       job_sched.qtytopick,
       job_sched.c_workplace_id
FROM m_packageable_v shipment_sched
         LEFT OUTER JOIN M_Picking_Job_Schedule job_sched ON job_sched.m_shipmentschedule_id = shipment_sched.m_shipmentschedule_id
;
