-- View: support.shipment_schedule_duplicate_inoutlines_v
-- Purpose: Identifies duplicate M_InOutLine records created for the same M_ShipmentSchedule.
--          This detects the race condition bug where multiple async workpackages each create
--          shipment lines for the same schedule because locks are released too early and
--          deduplication is explicitly disabled (skipAlreadyProcessedItems=false).
--
-- Usage: SELECT * FROM support.shipment_schedule_duplicate_inoutlines_v;
--
-- A row with dup_count > 1 indicates a duplicate.
-- A row with total_qty > expected_qty indicates over-delivery.

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
        'See GenerateInOutFromShipmentSchedules draft-shipment allocation guard.';
