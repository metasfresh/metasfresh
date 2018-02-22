
CREATE INDEX IF NOT EXISTS m_shipmentschedule_ad_table_id
  ON public.m_shipmentschedule
  USING btree
  (ad_table_id);

--
-- Update m_shipmentschedule records whose referenced C_SubscriptionProgress records are already gone
-- Note: we can't delete them, because they are already referenced from e.g. ShipmentScheduleQtyPicked, M_HU_Trace etc
--
UPDATE m_shipmentschedule 
SET 
	IsActive='N', Updated=now(), updatedby=99,
	Status='the referenced C_SubscriptionProgress is gone; see https://github.com/metasfresh/metasfresh/issues/3520'
WHERE m_shipmentschedule_id IN 
(
	select sched.M_ShipmentSchedule_ID 
	from M_ShipmentSchedule sched
		left join C_SubscriptionProgress s ON 
			sched.AD_table_ID=540029 /*C_SubscriptionProgress*/
			and sched.Record_ID=s.C_SubscriptionProgress_ID
	where s is null
);
