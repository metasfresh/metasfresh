-- Fixes failure of: 5831250_sys_gh31560_Drop_Open_items_Document_Based_Process.sql
--
-- 5831250 deletes AD_Process 500174 "Offene Posten (Belegbasiert)" after removing only the two core menu entries.
-- On databases with further references to that process (an additional menu entry, archived report runs,
-- printing-queue entries, ...) its deferred FK check fails at COMMIT.
-- ad_process_delete handles every reference and deletes the process, so the statements of 5831250 then match no rows.
-- Does nothing where the process is already gone.

-- Edit of an already integrated script (immutability exception): a customer instance is stuck AT this script - 5831247 (the function) is
-- applied, and as 5831247/5831248 are consecutive no earlier-numbered follow-up script can fix the function before this call.
-- The installed (old) ad_process_delete refuses to delete a process that still has AD_Process_Access rows when the database
-- carries an FK from AD_Process_Access to AD_Process, although it would delete them itself.
-- Removing the role grants first lets the installed function succeed; harmless where the process is gone or has no such rows.
SELECT backup_table('ad_process_access', '_gh31560_500174')
;

DELETE FROM ad_process_access WHERE ad_process_id = 500174
;

SELECT ad_process_delete(500174)
;
