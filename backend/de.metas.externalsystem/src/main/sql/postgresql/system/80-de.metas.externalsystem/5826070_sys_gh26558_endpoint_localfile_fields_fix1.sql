-- Two follow-up corrections to the LOCAL_FILE endpoint window fields added by
-- 5826060_sys_gh26558_endpoint_localfile_fields.sql (that script has already been applied, so an edit to
-- its statements would not re-run — this is an additive follow-up).
--
-- 1. LocalRootLocation (AD_Column 593641) had no MandatoryLogic, unlike the analogous
--    target-location field of every other transport (HttpEndPoint / SftpHost / SftpRemotePath all
--    require themselves once their transport is chosen) — an operator could save a LOCAL_FILE
--    endpoint with no directory to poll. MandatoryLogic lives on AD_Column, not AD_Field.
-- 2. Frequency (AD_Field 785060) surfaced the bare shared-element label "Häufigkeit" / "Häufigkeit
--    von Ereignissen" (element 1506, reused core-wide, left unchanged) with no unit and no polling
--    context. Direct precedent: AD_Field 726619 overrides the same shared element via a dedicated
--    AD_Name_ID element (583048, "Abfragefrequenz in Millisekunden") for exactly this reason. Same
--    pattern here: a new dedicated AD_Element (ColumnName NULL, used only via AD_Name_ID), never a
--    change to element 1506 itself (it is referenced by many other windows).
--
-- IDs allocated from idserver.metas.de on 2026-09-23:
--   AD_Element   585488 (dedicated Frequency-field-override element for tab 548506's LOCAL_FILE group)

-- ============================================================
-- 1. LocalRootLocation: MandatoryLogic when TransportType=LOCAL_FILE (AD_Column 593641)
-- ============================================================
UPDATE AD_Column
SET MandatoryLogic = '@TransportType/X@=''LOCAL_FILE''',
    Updated         = TO_TIMESTAMP('2026-09-23 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy       = 100
WHERE AD_Column_ID = 593641;

-- ============================================================
-- 2. Dedicated AD_Element (585488) for the Frequency field-level label override
-- ============================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, Name, PrintName, Description, EntityType)
VALUES (585488 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-09-23 12:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-23 12:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    NULL, 'Abfragefrequenz (ms)', 'Abfragefrequenz (ms)',
    'Wie oft das lokale Verzeichnis auf neue Dateien geprüft wird, in Millisekunden.',
    'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
    AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Element_ID = 585488
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- German trl rows (de_DE, de_CH) - mirror the base German text
UPDATE AD_Element_Trl
SET Name         = 'Abfragefrequenz (ms)',
    PrintName    = 'Abfragefrequenz (ms)',
    Description  = 'Wie oft das lokale Verzeichnis auf neue Dateien geprüft wird, in Millisekunden.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-23 12:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585488 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET Name         = 'Polling Frequency (ms)',
    PrintName    = 'Polling Frequency (ms)',
    Description  = 'How often the local directory is checked for new files, in milliseconds.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-23 12:00:30', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585488 AND AD_Language = 'en_US';

-- ============================================================
-- 3. Point AD_Field 785060 (Frequency, tab 548506) at the dedicated element via AD_Name_ID
-- ============================================================
UPDATE AD_Field
SET AD_Name_ID = 585488,
    Updated    = TO_TIMESTAMP('2026-09-23 12:00:40', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy  = 100
WHERE AD_Field_ID = 785060;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585488);

DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785060;
SELECT AD_Element_Link_Create_Missing_Field(785060);
