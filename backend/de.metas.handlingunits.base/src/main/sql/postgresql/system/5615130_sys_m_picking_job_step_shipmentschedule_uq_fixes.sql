DROP INDEX IF EXISTS m_picking_job_step_shipmentschedule_uq
;

-- NOTE: this index will prevent creating alternatives
-- CREATE UNIQUE INDEX m_picking_job_step_shipmentschedule_uq
--     ON m_picking_job_step (m_picking_job_id, m_shipmentschedule_id)
--     WHERE processed = 'N'
-- ;
--
