UPDATE m_picking_job j
SET deliveryDate=(SELECT MIN(COALESCE(sched.deliverydate_override, sched.deliveryDate))
                  FROM m_picking_job_step s
                           INNER JOIN m_shipmentschedule sched ON (sched.m_shipmentschedule_id = s.m_shipmentschedule_id)
                  WHERE s.m_picking_job_id = j.m_picking_job_id)
WHERE j.deliveryDate IS NULL
;

UPDATE m_picking_job j
SET deliveryDate=j.preparationdate
WHERE j.deliveryDate IS NULL
;

