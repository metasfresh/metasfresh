UPDATE m_picking_job_line l
SET c_order_id=(SELECT DISTINCT c_order_id FROM m_picking_job_step s WHERE s.m_picking_job_line_id = l.m_picking_job_line_id),
    c_orderline_id=(SELECT distinct c_orderline_id FROM m_picking_job_step s WHERE s.m_picking_job_line_id = l.m_picking_job_line_id)
WHERE l.c_order_id IS NULL
   OR l.c_orderline_id IS NULL
;

