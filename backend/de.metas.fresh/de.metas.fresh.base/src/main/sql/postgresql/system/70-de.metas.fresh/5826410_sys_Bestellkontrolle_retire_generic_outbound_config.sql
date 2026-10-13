-- Bestellkontrolle -- retire the generic outbound configuration 540002 (no document base type).
-- Every Bestellkontrolle report now has a document type, so it resolves one of the two specific
-- configurations 540022 (BKP) / 540023 (BKB); the generic one is only a fallback nothing reaches any more.
-- First the specific configurations take over the generic one's three flags, read from this instance's own
-- row (so each instance keeps its own values), then the generic configuration is deactivated.
-- The flag copy only runs while 540002 is still active: once it is retired, the specific configurations are
-- the maintained ones, and copying from the retired row would overwrite them with stale values.
-- Print formats stay untouched. No-op where any of the rows is missing.

SELECT backup_table('C_Doc_Outbound_Config', '_retire_generic')
;

UPDATE C_Doc_Outbound_Config c
SET IsDirectEnqueue=g.IsDirectEnqueue,
    IsDirectProcessQueueItem=g.IsDirectProcessQueueItem,
    IsAutoSendDocument=g.IsAutoSendDocument,
    Updated=TO_TIMESTAMP('2026-09-25 10:10:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=99
FROM C_Doc_Outbound_Config g
WHERE g.C_Doc_Outbound_Config_ID=540002
  AND g.IsActive='Y'
  AND c.C_Doc_Outbound_Config_ID IN (540022, 540023)
;

UPDATE C_Doc_Outbound_Config
SET IsActive='N',
    Updated=TO_TIMESTAMP('2026-09-25 10:10:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=99
WHERE C_Doc_Outbound_Config_ID=540002
  AND IsActive='Y'
;
