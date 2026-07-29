DROP INDEX IF EXISTS m_picking_job_step_shipmentschedule_uq
;

CREATE UNIQUE INDEX m_picking_job_step_shipmentschedule_uq
    ON m_picking_job_step (m_shipmentschedule_id)
    WHERE processed = 'N'
;

