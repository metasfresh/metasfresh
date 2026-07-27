-- Picking-replenishment aggregation — partial index for the product-group contributor lookup.
--
-- IDs allocated from idserver.metas.de on 2026-07-25:
--   AD_MigrationScript  5816210 (this script)
--
-- Without it, the group-contributor lookup scans the whole open-assignment set on every reconcile and every hourly rebuild pass.
-- No AD metadata change — this is a pure physical index, not an AD_Column/AD_Table change.
CREATE INDEX m_picking_job_schedule_c_workplace_id
    ON M_Picking_Job_Schedule (C_Workplace_ID) WHERE Processed = 'N';
