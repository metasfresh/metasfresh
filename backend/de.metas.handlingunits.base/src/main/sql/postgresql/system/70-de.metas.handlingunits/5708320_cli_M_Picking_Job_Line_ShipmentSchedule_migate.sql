UPDATE m_picking_job_line l
SET m_shipmentschedule_id=(SELECT DISTINCT m_shipmentschedule_id FROM m_picking_job_step s WHERE s.m_picking_job_line_id = l.m_picking_job_line_id)
WHERE l.m_shipmentschedule_id IS NULL
;

