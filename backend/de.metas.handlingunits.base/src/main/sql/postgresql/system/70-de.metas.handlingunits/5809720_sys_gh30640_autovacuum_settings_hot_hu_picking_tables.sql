-- Tighten autovacuum on the small-but-high-churn HU / picking / shipment tables.
-- These tables turn over their whole row set many times a day (HUs, picking jobs,
-- shipments) but stay physically small, so at the default scale_factor 0.2 they
-- accumulate large dead-tuple ratios (observed up to ~300% dead) while bigger
-- log tables monopolise the autovacuum workers. A low scale_factor + low
-- threshold makes a worker reclaim them promptly and often.
-- See gh30640 (picking performance). Pattern follows
-- 5660150_sys_gh13753_adjust_autovacuum_settings_on_translation_tables.sql.

ALTER TABLE public.m_hu SET (autovacuum_vacuum_scale_factor = 0.05);
ALTER TABLE public.m_hu SET (autovacuum_vacuum_threshold = 50);
ALTER TABLE public.m_hu SET (autovacuum_analyze_scale_factor = 0.05);

ALTER TABLE public.m_hu_item SET (autovacuum_vacuum_scale_factor = 0.05);
ALTER TABLE public.m_hu_item SET (autovacuum_vacuum_threshold = 50);
ALTER TABLE public.m_hu_item SET (autovacuum_analyze_scale_factor = 0.05);

ALTER TABLE public.m_hu_trx_line SET (autovacuum_vacuum_scale_factor = 0.05);
ALTER TABLE public.m_hu_trx_line SET (autovacuum_vacuum_threshold = 50);
ALTER TABLE public.m_hu_trx_line SET (autovacuum_analyze_scale_factor = 0.05);

ALTER TABLE public.m_picking_job SET (autovacuum_vacuum_scale_factor = 0.05);
ALTER TABLE public.m_picking_job SET (autovacuum_vacuum_threshold = 50);
ALTER TABLE public.m_picking_job SET (autovacuum_analyze_scale_factor = 0.05);

ALTER TABLE public.m_picking_job_line SET (autovacuum_vacuum_scale_factor = 0.05);
ALTER TABLE public.m_picking_job_line SET (autovacuum_vacuum_threshold = 50);
ALTER TABLE public.m_picking_job_line SET (autovacuum_analyze_scale_factor = 0.05);

ALTER TABLE public.m_picking_job_step SET (autovacuum_vacuum_scale_factor = 0.05);
ALTER TABLE public.m_picking_job_step SET (autovacuum_vacuum_threshold = 50);
ALTER TABLE public.m_picking_job_step SET (autovacuum_analyze_scale_factor = 0.05);

ALTER TABLE public.m_inout SET (autovacuum_vacuum_scale_factor = 0.05);
ALTER TABLE public.m_inout SET (autovacuum_vacuum_threshold = 50);
ALTER TABLE public.m_inout SET (autovacuum_analyze_scale_factor = 0.05);

ALTER TABLE public.m_inoutline SET (autovacuum_vacuum_scale_factor = 0.05);
ALTER TABLE public.m_inoutline SET (autovacuum_vacuum_threshold = 50);
ALTER TABLE public.m_inoutline SET (autovacuum_analyze_scale_factor = 0.05);

ALTER TABLE public.m_shipmentschedule_qtypicked SET (autovacuum_vacuum_scale_factor = 0.05);
ALTER TABLE public.m_shipmentschedule_qtypicked SET (autovacuum_vacuum_threshold = 50);
ALTER TABLE public.m_shipmentschedule_qtypicked SET (autovacuum_analyze_scale_factor = 0.05);
