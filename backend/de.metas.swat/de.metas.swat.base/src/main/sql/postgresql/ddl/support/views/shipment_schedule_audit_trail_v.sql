-- View: support.shipment_schedule_audit_trail_v
-- Purpose: Complete audit trail for a shipment schedule: order → schedule → picked → shipment → invoice.
--          Adapted from analysis SQL for duplicate shipment line investigation.
--
-- Usage:
--   SELECT * FROM support.shipment_schedule_audit_trail_v WHERE m_shipmentschedule_id = 1020989;
--   SELECT * FROM support.shipment_schedule_audit_trail_v WHERE c_order_id = 1001287;
--   SELECT * FROM support.shipment_schedule_audit_trail_v WHERE m_inout_id = 1000407;

CREATE OR REPLACE VIEW support.shipment_schedule_audit_trail_v AS
SELECT
    -- Order
    ol.c_order_id,
    o.documentno                              AS order_documentno,
    o.dateordered,
    ol.c_orderline_id,
    ol.line                                   AS order_line,
    -- Schedule
    ss.m_shipmentschedule_id,
    ss.qtyordered                             AS sched_qtyordered,
    ss.qtydelivered                           AS sched_qtydelivered,
    ss.qtytodeliver                          AS sched_qtytodeliver,
    ss.qtyordered - ss.qtydelivered           AS sched_open_qty,
    ss.headeraggregationkey,
    ss.isactive                               AS sched_isactive,
    -- Product
    ss.m_product_id,
    p.value                                   AS product_value,
    p.name                                    AS product_name,
    -- Picked (via M_ShipmentSchedule_QtyPicked)
    qp.m_shipmentschedule_qtypicked_id,
    qp.qtypicked,
    qp.qtydeliveredcatch,
    qp.processed                              AS qp_processed,
    qp.m_inoutline_id                         AS qp_inoutline_id,
    qp.created                                AS qp_created,
    -- Shipment Line
    iol.m_inoutline_id,
    iol.line                                  AS shipment_line,
    iol.movementqty                           AS shipped_qty,
    iol.created                               AS shipment_line_created,
    -- Shipment Header
    io.m_inout_id,
    io.documentno                             AS shipment_documentno,
    io.docstatus                              AS shipment_docstatus,
    io.movementdate                           AS shipment_date,
    -- BPartner
    o.c_bpartner_id,
    bp.name                                   AS bpartner_name
FROM m_shipmentschedule ss
         JOIN c_orderline ol ON ol.c_orderline_id = ss.c_orderline_id
         JOIN c_order o      ON o.c_order_id = ol.c_order_id
         JOIN m_product p    ON p.m_product_id = ss.m_product_id
         JOIN c_bpartner bp  ON bp.c_bpartner_id = o.c_bpartner_id
         LEFT JOIN m_shipmentschedule_qtypicked qp ON qp.m_shipmentschedule_id = ss.m_shipmentschedule_id
         LEFT JOIN m_inoutline iol ON iol.m_inoutline_id = qp.m_inoutline_id
         LEFT JOIN m_inout io     ON io.m_inout_id = iol.m_inout_id
ORDER BY
    ss.m_shipmentschedule_id,
    qp.created NULLS LAST;

COMMENT ON VIEW support.shipment_schedule_audit_trail_v IS
    'Complete audit trail: Order → ShipmentSchedule → QtyPicked → InOutLine → InOut. '
        'Use to investigate shipment schedule processing issues. '
        'Helps diagnose shipment schedule processing issues.';
