-- Fixes failure of: 5831250_sys_gh31560_Drop_Open_items_Document_Based_Process.sql
--
-- 5831250 deletes AD_Process 500174 "Offene Posten (Belegbasiert)" after removing only the two core menu entries.
-- On databases with further references to that process (an additional menu entry, archived report runs,
-- printing-queue entries, ...) its deferred FK check fails at COMMIT.
-- ad_process_delete handles every reference and deletes the process, so the statements of 5831250 then match no rows.
-- Does nothing where the process is already gone.

SELECT ad_process_delete(500174)
;
