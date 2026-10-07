-- Index M_ShipmentSchedule_QtyPicked.M_LU_HU_ID — the LU-target lookup for a top-level HU filters this column
-- (the sibling M_TU_HU_ID / VHU_ID indices already exist, added in 5504800; the LU one was missing). Resolving a
-- picked LU's shipment schedules now also runs on the mobile picking-display path (once per picking-job open for
-- an LU-target job), so an unindexed M_LU_HU_ID would sequential-scan the table on every such open.

CREATE INDEX IF NOT EXISTS m_shipmentschedule_qtypicked_m_lu_hu_id
  ON public.m_shipmentschedule_qtypicked
  USING btree
  (m_lu_hu_id);
