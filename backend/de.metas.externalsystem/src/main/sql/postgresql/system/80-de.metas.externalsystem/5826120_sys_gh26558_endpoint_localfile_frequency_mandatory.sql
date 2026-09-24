-- Gates Frequency as mandatory while TransportType=LOCAL_FILE, mirroring LocalRootLocation's own
-- MandatoryLogic (AD_Column 593641, script 5826070): a local-file endpoint whose polling frequency the
-- operator has cleared out is rejected by the window with "fill in mandatory fields" and cannot be saved.
--
-- This covers the transport-switch path too: mandatory validation rejects an UNSET value, and switching an
-- endpoint away from LOCAL_FILE clears Frequency to SQL NULL rather than to 0 (which would pass). So the
-- endpoint stays invalid on the way back until the operator re-enters the interval, same as for
-- LocalRootLocation.
--
-- IDs allocated from idserver.metas.de on 2026-09-24:
--   AD_MigrationScript 5826120 (this script)

UPDATE AD_Column
SET MandatoryLogic = '@TransportType/X@=''LOCAL_FILE''',
    Updated         = TO_TIMESTAMP('2026-09-24 09:05:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy       = 100
WHERE AD_Column_ID = 593642;
