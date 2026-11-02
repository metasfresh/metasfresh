-- Fixes failure of: 5831250_sys_gh31560_Drop_Open_items_Document_Based_Process.sql
--
-- Placeholder AD_Process "Prozess wurde gelöscht" / "Process was deleted".
-- The DB function ad_process_delete(numeric) re-points references to a deleted process onto this record
-- wherever the reference must survive the deletion (archives, printing queue, async queue blocks,
-- print formats, boilerplates, reports, HU label configs, external-system and PLU configs).
-- It is inactive and has no menu entry, so it is never offered to a user.
-- Sorted before 5831250 because the function (5831247) and its call (5831248) must run before 5831250
-- on databases where 5831250 has not been applied yet.

INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, Created, CreatedBy, Description, EntityType, Help, IsActive, IsReport, Name, Type, Updated, UpdatedBy, Value)
VALUES ('7', 0, 0, 585692 /*From ID Server*/, TO_TIMESTAMP('2026-10-30 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Platzhalter für Verweise auf einen gelöschten Prozess, z.B. aus Archiven oder der Druckwarteschlange.', 'D',
        'Dieser Prozess ist inaktiv und kann nicht ausgeführt werden. Er ersetzt beim Löschen eines Prozesses dessen Verweise, die erhalten bleiben müssen.',
        'N', 'N', 'Prozess wurde gelöscht', 'Java', TO_TIMESTAMP('2026-10-30 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'ProcessWasDeleted')
;

INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585692
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

UPDATE AD_Process_Trl
SET IsTranslated = 'Y', Name = 'Process was deleted',
    Description = 'Placeholder for references to a deleted process, e.g. from archives or the printing queue.',
    Help = 'This process is inactive and cannot be run. When a process is deleted, it replaces those references to it that must be kept.',
    Updated = TO_TIMESTAMP('2026-10-30 22:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Process_ID = 585692 AND AD_Language IN ('en_US', 'en_GB')
;

UPDATE AD_Process_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-30 22:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Process_ID = 585692 AND AD_Language IN ('de_DE', 'de_CH')
;
