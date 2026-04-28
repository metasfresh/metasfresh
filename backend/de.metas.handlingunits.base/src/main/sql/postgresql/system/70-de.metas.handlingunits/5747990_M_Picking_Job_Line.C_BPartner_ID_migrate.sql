UPDATE m_picking_job_line l
SET c_bpartner_id=o.c_bpartner_id, c_bpartner_location_id=o.c_bpartner_location_id
FROM c_order o
WHERE o.c_order_id = l.c_order_id
  AND l.c_bpartner_id IS NULL
;

