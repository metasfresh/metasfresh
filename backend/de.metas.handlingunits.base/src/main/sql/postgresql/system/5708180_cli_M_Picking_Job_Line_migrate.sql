UPDATE m_picking_job_line l
SET c_uom_id=(SELECT DISTINCT c_uom_id FROM m_picking_job_step s WHERE s.m_picking_job_line_id = l.m_picking_job_line_id),
    qtytopick=(SELECT SUM(qtytopick) FROM m_picking_job_step s WHERE s.m_picking_job_line_id = l.m_picking_job_line_id)
WHERE l.c_uom_id IS NULL
   OR l.qtytopick IS NULL
;





