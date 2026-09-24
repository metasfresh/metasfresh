-- Gates Frequency as mandatory while TransportType=LOCAL_FILE, mirroring LocalRootLocation's own
-- MandatoryLogic (AD_Column 593641, script 5826070): a local-file endpoint whose polling frequency the
-- operator has cleared out is rejected by the window with "fill in mandatory fields" and cannot be saved.
--
-- What this does NOT cover is the 0 a transport switch leaves behind. Switching an endpoint away from
-- LOCAL_FILE clears Frequency to 0 rather than to NULL -- 0 is the only "empty" an integer column can be
-- set to on that path -- and mandatory validation rejects only an UNSET value, so 0 passes it and the
-- record stays valid. That round trip is closed on the application side instead, by re-defaulting the
-- frequency whenever an endpoint switches back to the local-file transport.
--
-- IDs allocated from idserver.metas.de on 2026-09-24:
--   AD_MigrationScript 5826120 (this script)

UPDATE AD_Column
SET MandatoryLogic = '@TransportType/X@=''LOCAL_FILE''',
    Updated         = TO_TIMESTAMP('2026-09-24 09:05:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy       = 100
WHERE AD_Column_ID = 593642;
