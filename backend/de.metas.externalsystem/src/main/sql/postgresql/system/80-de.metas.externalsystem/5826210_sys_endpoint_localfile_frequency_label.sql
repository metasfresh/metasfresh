-- Renames the local-file polling field's label from "Abfragefrequenz (ms)" / "Polling Frequency (ms)"
-- to "Abfrageintervall (ms)" / "Polling Interval (ms)".
--
-- The value is a delay in milliseconds BETWEEN two polls, i.e. an interval, not a frequency: read as a
-- frequency, a larger number would mean polling more often, while it actually means polling less often.
-- "Abfrageintervall (ms)" / "Polling Interval (ms)" also matches the SFTP transport's sibling field one
-- column over (AD_Element 584678, SftpPollingIntervalMs: "SFTP Abfrageintervall (ms)" / "SFTP Polling
-- Interval (ms)"), so the two transports now name the same concept the same way.
--
-- Only the LABEL changes. The column stays ExternalSystem_Endpoint.Frequency -- renaming it would diverge
-- from the sibling table and from the Java/REST surface that already reads it under that name.
--
-- Scope: AD_Element 585488 is the dedicated field-label element created by
-- 5826070_sys_gh26558_endpoint_localfile_fields_fix1.sql. It has no ColumnName and is referenced by exactly
-- one row, AD_Field 785060 (the Frequency field on tab 548506) via AD_Name_ID -- verified against the live
-- dictionary, so this rename reaches that one field and nothing else. It deliberately does NOT touch the
-- shared core element 1506 ("Häufigkeit"), which eleven Frequency columns across eleven tables still use.
--
-- The de_DE/de_CH and en_US rows carry the real translations; fr_CH was seeded with the German text and
-- IsTranslated='N', and keeps that placeholder role here rather than acquiring a half-French label.
-- AD_Element itself and the AD_Field/AD_Field_Trl rows are updated by the propagation call at the end.
--
-- IDs allocated from idserver.metas.de on 2026-09-24:
--   AD_MigrationScript 5826210 (this script)

UPDATE AD_Element_Trl
SET Name      = 'Abfrageintervall (ms)',
    PrintName = 'Abfrageintervall (ms)',
    Updated   = TO_TIMESTAMP('2026-09-24 11:00:10', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Element_ID = 585488 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET Name      = 'Polling Interval (ms)',
    PrintName = 'Polling Interval (ms)',
    Updated   = TO_TIMESTAMP('2026-09-24 11:00:20', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Element_ID = 585488 AND AD_Language = 'en_US';

-- fr_CH is an untranslated placeholder mirroring the German text (IsTranslated stays 'N').
UPDATE AD_Element_Trl
SET Name      = 'Abfrageintervall (ms)',
    PrintName = 'Abfrageintervall (ms)',
    Updated   = TO_TIMESTAMP('2026-09-24 11:00:30', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Element_ID = 585488 AND AD_Language = 'fr_CH';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585488);
