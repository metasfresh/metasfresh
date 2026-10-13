-- migration: 5788200_sys_gh28143_support_shipment_schedule_views.sql
-- task: https://github.com/metasfresh/me03/issues/28143
-- purpose: Create support-schema views for analyzing duplicate shipment line issues
--          caused by race condition in GenerateInOutFromShipmentSchedules.

CREATE SCHEMA IF NOT EXISTS support;

-- View 1: Detect duplicate shipment lines per schedule within the same shipment
CREATE OR REPLACE VIEW support.shipment_schedule_duplicate_inoutlines_v AS
SELECT
    qp.m_shipmentschedule_id,
    ss.m_product_id,
    p.value                                    AS product_value,
    p.name                                     AS product_name,
    io.m_inout_id,
    io.documentno                              AS shipment_documentno,
    io.docstatus                               AS shipment_docstatus,
    COUNT(DISTINCT iol.m_inoutline_id)         AS line_count,
    array_agg(DISTINCT iol.m_inoutline_id ORDER BY iol.m_inoutline_id) AS inoutline_ids,
    SUM(iol.movementqty)                       AS total_movementqty,
    MIN(iol.created)                           AS first_line_created,
    MAX(iol.created)                           AS last_line_created,
    MAX(iol.created) - MIN(iol.created)        AS creation_span
FROM m_shipmentschedule_qtypicked qp
         JOIN m_inoutline iol ON iol.m_inoutline_id = qp.m_inoutline_id
         JOIN m_inout io      ON io.m_inout_id = iol.m_inout_id
         JOIN m_shipmentschedule ss ON ss.m_shipmentschedule_id = qp.m_shipmentschedule_id
         JOIN m_product p     ON p.m_product_id = ss.m_product_id
GROUP BY
    qp.m_shipmentschedule_id,
    ss.m_product_id,
    p.value,
    p.name,
    io.m_inout_id,
    io.documentno,
    io.docstatus
HAVING COUNT(DISTINCT iol.m_inoutline_id) > 1
ORDER BY
    io.m_inout_id,
    qp.m_shipmentschedule_id;

COMMENT ON VIEW support.shipment_schedule_duplicate_inoutlines_v IS
    'Detects duplicate shipment lines for the same M_ShipmentSchedule within the same shipment (M_InOut). '
        'Caused by race condition: multiple GenerateInOutFromShipmentSchedules workpackages process the same schedule. '
        'Helps diagnose duplicate shipment line race conditions.';

-- View 2: Workpackage processing timeline for shipment generation
CREATE OR REPLACE VIEW support.shipment_schedule_wp_timeline_v AS
SELECT
    wp.c_queue_workpackage_id                      AS wp_id,
    wp.created                                     AS wp_created,
    wp.updated                                     AS wp_processed_at,
    wp.updated - wp.created                        AS processing_duration,
    wp.processed                                   AS is_processed,
    wp.iserror                                     AS is_error,
    wp.errormsg,
    wp.skipped_count,
    wp.ad_pinstance_id,
    pp.classname                                   AS processor_classname,
    (SELECT COUNT(*) FROM c_queue_element qe
     WHERE qe.c_queue_workpackage_id = wp.c_queue_workpackage_id) AS element_count,
    (SELECT COUNT(*) FROM c_queue_workpackage wp2
     WHERE wp2.ad_pinstance_id = wp.ad_pinstance_id
       AND wp2.c_queue_workpackage_id != wp.c_queue_workpackage_id) AS sibling_wp_count
FROM c_queue_workpackage wp
         JOIN c_queue_packageprocessor pp ON pp.c_queue_packageprocessor_id = wp.c_queue_packageprocessor_id
WHERE pp.classname LIKE '%GenerateInOutFromShipmentSchedules%'
ORDER BY wp.created DESC;

COMMENT ON VIEW support.shipment_schedule_wp_timeline_v IS
    'Timeline of GenerateInOutFromShipmentSchedules workpackage processing. '
        'Use to identify multiple WP batches processing the same shipment schedules. '
        'Helps diagnose duplicate shipment line race conditions.';

-- View 3: Complete audit trail for shipment schedules
CREATE OR REPLACE VIEW support.shipment_schedule_audit_trail_v AS
SELECT
    ol.c_order_id,
    o.documentno                              AS order_documentno,
    o.dateordered,
    ol.c_orderline_id,
    ol.line                                   AS order_line,
    ss.m_shipmentschedule_id,
    ss.qtyordered                             AS sched_qtyordered,
    ss.qtydelivered                           AS sched_qtydelivered,
    ss.qtytodeliver                          AS sched_qtytodeliver,
    ss.qtyordered - ss.qtydelivered           AS sched_open_qty,
    ss.headeraggregationkey,
    ss.isactive                               AS sched_isactive,
    ss.m_product_id,
    p.value                                   AS product_value,
    p.name                                    AS product_name,
    qp.m_shipmentschedule_qtypicked_id,
    qp.qtypicked,
    qp.qtydeliveredcatch,
    qp.processed                              AS qp_processed,
    qp.m_inoutline_id                         AS qp_inoutline_id,
    qp.created                                AS qp_created,
    iol.m_inoutline_id,
    iol.line                                  AS shipment_line,
    iol.movementqty                           AS shipped_qty,
    iol.created                               AS shipment_line_created,
    io.m_inout_id,
    io.documentno                             AS shipment_documentno,
    io.docstatus                              AS shipment_docstatus,
    io.movementdate                           AS shipment_date,
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
    'Complete audit trail: Order -> ShipmentSchedule -> QtyPicked -> InOutLine -> InOut. '
        'Use to investigate shipment schedule processing issues. '
        'Helps diagnose duplicate shipment line race conditions.';
