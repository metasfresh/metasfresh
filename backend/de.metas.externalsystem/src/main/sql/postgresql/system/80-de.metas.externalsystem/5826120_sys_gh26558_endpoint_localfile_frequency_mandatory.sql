-- Gates Frequency as mandatory while TransportType=LOCAL_FILE, mirroring LocalRootLocation's own
-- MandatoryLogic (AD_Column 593641, script 5826070): a local-file endpoint whose polling frequency the
-- operator has cleared out is rejected by the window with "fill in mandatory fields" and cannot be saved.
--
-- The path this gate actually catches is the operator blanking the field by hand, which persists SQL NULL.
-- It does NOT fire on a transport switch: the endpoint interceptor resets a hidden Frequency to this
-- column's DefaultValue (60000, script 5826110), so an endpoint switched away from LOCAL_FILE and back is
-- already valid. What the interceptor must never write there is 0 -- mandatory validation accepts it while
-- ExternalSystemEndpointRepository reads a frequency <= 0 as no frequency at all, which would make the
-- window call a poll-less endpoint valid.
--
-- IDs allocated from idserver.metas.de on 2026-09-24:
--   AD_MigrationScript 5826120 (this script)

UPDATE AD_Column
SET MandatoryLogic = '@TransportType/X@=''LOCAL_FILE''',
    Updated         = TO_TIMESTAMP('2026-09-24 09:05:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy       = 100
WHERE AD_Column_ID = 593642;
