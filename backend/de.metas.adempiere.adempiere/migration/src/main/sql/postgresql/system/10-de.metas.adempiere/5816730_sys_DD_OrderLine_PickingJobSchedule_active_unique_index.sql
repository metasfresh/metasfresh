-- IDs allocated from idserver.metas.de on 2026-07-28:
--   AD_MigrationScript  5816730 (this script)
--
-- Sequenced after 5816200 (creates DD_OrderLine_PickingJobSchedule) and after
-- 5816390 (backfills one row per still-open replenishment line), so the table and its
-- rows exist before the index is built. IF NOT EXISTS keeps re-application a no-op on a
-- database that already carries the index.

-- at most one contributing assignment row per line; partial, so a deactivated row never blocks its replacement
CREATE UNIQUE INDEX IF NOT EXISTS ddorderline_pjs_active_uidx
    ON DD_OrderLine_PickingJobSchedule (DD_OrderLine_ID, M_Picking_Job_Schedule_ID)
    WHERE IsActive = 'Y'
;
