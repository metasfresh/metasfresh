
--
-- delete m_shipmentschedule records whose referenced C_SubscriptionProgress records are already gone
--
DELETE m_shipmentschedule 
WHERE m_shipmentschedule_id IN 
(
	select M_ShipmentSchedule_ID 
	from M_ShipmentSchedule sched
	from M_ShipmentSchedule sched
		left join C_SubscriptionProgress s ON 
			sched.AD_table_ID=540029 /*C_SubscriptionProgress*/
			and sched.Record_ID=s.C_SubscriptionProgress_ID
	where s is null
);
