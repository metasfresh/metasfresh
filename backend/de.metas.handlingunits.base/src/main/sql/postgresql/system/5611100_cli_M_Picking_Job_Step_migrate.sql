UPDATE M_Picking_Job_Step ps
SET c_order_id=sched.c_order_id,
    c_orderline_id=sched.c_orderline_id
FROM M_ShipmentSchedule sched
WHERE ps.m_shipmentschedule_id = sched.m_shipmentschedule_id
  AND ps.c_orderline_id IS NULL
;

