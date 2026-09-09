DROP INDEX IF EXISTS m_picking_job_schedule_uq
;

CREATE UNIQUE INDEX m_picking_job_schedule_uq
    ON m_picking_job_schedule (m_shipmentschedule_id, c_workplace_id)
    WHERE processed = 'N'
;
