DROP VIEW IF EXISTS M_Picking_Job_Schedule_view
;

CREATE OR REPLACE VIEW M_Picking_Job_Schedule_view AS
WITH base_schedule AS (SELECT s.m_shipmentschedule_id,

                              s.c_bpartner_customer_id,
                              s.c_bpartner_location_id,
                              s.bpartnerlocationname,
                              s.bpartneraddress_override,
                              s.c_orderso_id,
                              s.poreference,
                              s.handover_partner_id,
                              s.handover_location_id,
                              s.setup_place_no,
                              s.dateordered,
                              s.c_orderlineso_id,
                              s.linenetamt,
                              s.c_currency_id,
                              s.m_warehouse_id,
                              s.preparationdate,
                              s.m_product_id,
                              s.c_uom_id,
                              s.m_attributesetinstance_id,
                              s.qtyordered,
                              s.qtytodeliver,
                              s.qtydelivered,
                              s.qtypickedanddelivered,
                              s.qtypickednotdelivered,
                              s.qtypickedplanned,
                              s.qtypickedordelivered,
                              s.qtyonhand,
                              s.qtyscheduledforpicking,
                              s.qtytodeliver - COALESCE(s.qtyscheduledforpicking, 0) AS QtyToScheduleForPicking,
                              s.qtyscheduledforpickingofprocessed,

                              s.deliveryviarule,
                              s.deliverydate,
                              s.priorityrule,
                              s.iscatchweight,
                              s.catch_uom_id,
                              s.m_shipper_id,
                              s.packto_hu_pi_item_product_id,
                              s.datepromised,
                              s.isfixeddatepromised,
                              s.isfixedpreparationdate,

                              s.ad_client_id,
                              s.ad_org_id,
                              s.created,
                              s.createdby,
                              s.updated,
                              s.updatedby,
                              s.isactive,

                              s.carrier_advising_status,
                              s.carrier_product_id,
                              s.carrier_goods_type_id
                       FROM m_packageable_v s
                       WHERE s.carrier_product_id > 0
                          OR get_sysconfig_value('de.metas.handlingunits.picking.job_schedule.RequireCarrierProductSet') = 'N')

-- Real picking job rows
SELECT b.*,
       j.m_picking_job_schedule_id, -- Composed Key together with m_shipmentschedule_id
       j.qtytopick,
       (SELECT COALESCE(SUM(sqp.qtypicked), 0)
        FROM m_shipmentschedule_qtypicked sqp
        WHERE sqp.m_picking_job_schedule_id = j.m_picking_job_schedule_id
          AND sqp.processed = 'Y') AS qtypicked,
       j.c_workplace_id,
       j.processed,
       'N'                         AS isreschedule,
       'Y'                         AS isassigned
FROM base_schedule b
         JOIN M_Picking_Job_Schedule j ON j.m_shipmentschedule_id = b.m_shipmentschedule_id

UNION ALL

-- Synthetic "to be scheduled" row
SELECT b.*,
       0    AS m_picking_job_schedule_id, -- Composed Key together with m_shipmentschedule_id
       NULL AS qtytopick,
       NULL AS qtypicked,
       NULL AS c_workplace_id,
       'N'  AS processed,
       CASE
           WHEN (b.qtydelivered <> b.qtyscheduledforpickingofprocessed) THEN 'Y'
                                                                        ELSE 'N'
       END  AS isreschedule,
       'N'  AS isassigned
FROM base_schedule b
WHERE b.qtytoscheduleforpicking > 0
;
