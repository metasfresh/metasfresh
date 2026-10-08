DROP INDEX IF EXISTS m_picking_job_schedule_m_shipmentschedule_id
;

CREATE INDEX m_picking_job_schedule_m_shipmentschedule_id ON m_picking_job_schedule (m_shipmentschedule_id)
;

DROP INDEX IF EXISTS m_picking_job_schedule_uq
;

CREATE INDEX m_picking_job_schedule_uq ON m_picking_job_schedule (m_shipmentschedule_id, c_workplace_id)
;
