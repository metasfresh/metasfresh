/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- Surfaces scripted-export status on the standard Sales-Invoice window (AD_Window_ID=167):
--   1. ScriptedExport_Status rollup field (AD_Column 592812, virtual aggregate) on header
--      tab 263 (Rechnung), placed in group 540027 ('dates') at seqno=36, adjacent to
--      Externes System (34) and Eingabequelle (35). Read-only — value is system-computed.
--   2. Read-only child tab over ExternalSystem_ScriptedExportConversion_Status (AD_Table 542617)
--      showing per-config status rows for the current invoice. WhereClause filters by
--      AD_Table_ID=318 + Record_ID=@C_Invoice_ID@. Ordered newest-first (Updated DESC).
--      Columns: ExportStatus, Exportkonfiguration, HttpResponseCode, StatusMessage, IsResend,
--               AD_PInstance_ID, Updated.
--   3. AD_SQLColumn_SourceTableColumn: NOT needed — already created for column 592812 by
--      the prior task.
--
-- IDs allocated from idserver.metas.de on 2026-06-15:
--   AD_Element        584996  (generic tab caption: ScriptedExport_Status_Tab)
--   AD_Element        584997  (dedicated field label: ScriptedExport_Config -- 'Exportkonfiguration'/'Export Configuration')
--   AD_Tab            549313  (Status child tab on window 167)
--   AD_UI_Section     547822  (Status tab section)
--   AD_UI_Column      549556  (Status tab column)
--   AD_UI_ElementGroup 555450 (Status tab element group)
--   AD_Field          781120  (status tab: ExportStatus)
--   AD_UI_Element     652266  (status tab: ExportStatus)
--   AD_Field          781121  (status tab: ExternalSystem_Config_ScriptedExportConversion_ID)
--   AD_UI_Element     652267  (status tab: ExternalSystem_Config_ScriptedExportConversion_ID)
--   AD_Field          781122  (status tab: HttpResponseCode)
--   AD_UI_Element     652268  (status tab: HttpResponseCode)
--   AD_Field          781123  (status tab: StatusMessage)
--   AD_UI_Element     652269  (status tab: StatusMessage)
--   AD_Field          781124  (status tab: IsResend)
--   AD_UI_Element     652270  (status tab: IsResend)
--   AD_Field          781125  (status tab: AD_PInstance_ID)
--   AD_UI_Element     652271  (status tab: AD_PInstance_ID)
--   AD_Field          781126  (status tab: Updated)
--   AD_UI_Element     652272  (status tab: Updated)
--   AD_Field          781127  (header tab 263: ScriptedExport_Status rollup)
--   AD_UI_Element     652273  (header tab 263: ScriptedExport_Status rollup)

-- ===========================================================================
-- PART 1: Generic tab caption AD_Element (window-agnostic, reusable)
-- ===========================================================================

-- 1a) AD_Element for tab caption — generic ColumnName so it can be reused by other windows
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, EntityType, Name, PrintName)
VALUES
    (584996 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'ScriptedExport_Status_Tab', 'de.metas.externalsystem',
     'Exportstatus', 'Exportstatus')
;

-- 1b) Seed _Trl rows for all active system languages (DE base text in every row)
INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Element_ID = 584996
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- 1c) en_US override: English translation
UPDATE AD_Element_Trl
SET Name         = 'Export Status',
    PrintName    = 'Export Status',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-15 09:00:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 584996
;

-- 1d) de_DE / de_CH: mark as actively translated (text matches base German)
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-15 09:00:13', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID = 584996
;

-- 1e) fr_CH: base text, IsTranslated='N' (no French translation provided)
-- Already seeded by 1b with IsTranslated='N' — no additional UPDATE needed.

-- ===========================================================================
-- PART 1b: Dedicated AD_Element for 'Exportkonfiguration' field label (AD_Element 584997)
-- Shared element 584101 (ExternalSystem_Config_ScriptedExportConversion_ID) carries the raw
-- technical column name and is used elsewhere. AD_Name_ID on field 781121 pins the label to
-- this dedicated element, surviving after_migration_sync_translations.
-- ===========================================================================

INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, EntityType, Name, PrintName)
VALUES
    (584997 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 09:03:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 09:03:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'ScriptedExport_Config', 'de.metas.externalsystem',
     'Exportkonfiguration', 'Exportkonfiguration')
;

-- Seed _Trl rows for all active system languages (DE base text)
INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Element_ID = 584997
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- en_US override
UPDATE AD_Element_Trl
SET Name         = 'Export Configuration',
    PrintName    = 'Export Configuration',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-15 09:03:31', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 584997
;

-- de_DE / de_CH: mark as actively translated (text matches base German)
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-15 09:03:32', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID = 584997
;

-- ===========================================================================
-- PART 2: Read-only child tab over ExternalSystem_ScriptedExportConversion_Status
-- ===========================================================================

-- 2a) AD_Tab on window 167, TabLevel=1, read-only, no insert, newest-first
--     Parent_Column_ID = 592783 (Record_ID in the _Status table)
--     WhereClause filters rows for the current C_Invoice record
--     SeqNo=70 — placed after the existing tabs (max existing SeqNo=50 for Zuordnung)
INSERT INTO AD_Tab
    (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID,
     AD_Element_ID,
     Created, CreatedBy, Description, EntityType,
     HasTree, IsActive, IsAdvancedTab, IsCheckParentsChanged,
     IsInfoTab, IsInsertRecord, IsReadOnly, IsRefreshAllOnActivate,
     IsSearchActive, IsSingleRow, IsSortTab, IsTranslationTab,
     Name, OrderByClause, SeqNo, TabLevel,
     Parent_Column_ID,
     Updated, UpdatedBy,
     WhereClause)
VALUES
    (0, 0, 549313 /*From ID Server*/, 542617 /*ExternalSystem_ScriptedExportConversion_Status*/, 167 /*Rechnung window*/,
     584996 /*From ID Server — generic tab caption element*/,
     TO_TIMESTAMP('2026-06-15 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 'de.metas.externalsystem',
     'N', 'Y', 'N', 'N',
     'N', 'N' /*no insert — status rows are system-written*/, 'Y' /*read-only*/, 'N',
     'N', 'N', 'N', 'N',
     'Exportstatus', 'Updated DESC', 70, 1,
     592783 /*Record_ID column in _Status table — polymorphic FK*/,
     TO_TIMESTAMP('2026-06-15 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'ExternalSystem_ScriptedExportConversion_Status.AD_Table_ID = 318 AND ExternalSystem_ScriptedExportConversion_Status.Record_ID = @C_Invoice_ID/0@')
;

-- 2b) AD_Tab_Trl skeleton rows
INSERT INTO AD_Tab_Trl
    (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Tab_ID = 549313
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;

-- 2c) en_US translation
UPDATE AD_Tab_Trl
SET Name         = 'Export Status',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-15 09:01:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US'
  AND AD_Tab_ID = 549313
;

-- 2d) de_DE / de_CH
UPDATE AD_Tab_Trl
SET Name         = 'Exportstatus',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-15 09:01:13', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language IN ('de_DE', 'de_CH')
  AND AD_Tab_ID = 549313
;

-- ===========================================================================
-- PART 3: UI layout for the status child tab (included tab — single section/column/group)
-- ===========================================================================

-- 3a) AD_UI_Section
INSERT INTO AD_UI_Section
    (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, AD_Tab_ID, SeqNo, Value)
VALUES
    (547822 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 09:01:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 09:01:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549313, 10, 'default')
;

-- 3b) Single UI column (included tabs use a single column)
INSERT INTO AD_UI_Column
    (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, AD_UI_Section_ID, SeqNo)
VALUES
    (549556 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 09:01:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 09:01:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547822, 10)
;

-- 3c) Single element group (UIStyle=NULL — included tabs must have exactly 1 element group)
INSERT INTO AD_UI_ElementGroup
    (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES
    (555450 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 09:01:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 09:01:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549556, 10, NULL, 'default')
;

-- ===========================================================================
-- PART 4: AD_Field + AD_UI_Element rows for each status column (7 fields)
-- Order matches reference: ExportStatus, Exportkonfiguration, HttpResponseCode,
--                          StatusMessage, IsResend, AD_PInstance_ID, Updated
-- ===========================================================================

-- 4.1 ExportStatus (AD_Column 592784, shared AD_Element 577791)
--     The column's AD_Element 577791 is shared across 8 host tables and carries English
--     'Export Status' base text (its de_DE/de_CH trl is untranslated system-wide). To show the
--     German caption on this tab WITHOUT a system-wide shared-element mutation, pin the field
--     label via AD_Name_ID=584995 (the ScriptedExport_Status element, 'Exportstatus'/'Export Status').
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_Name_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592784, 781120 /*From ID Server*/, 0, 549313,
     584995 /*ScriptedExport_Status label element — 'Exportstatus'/'Export Status'*/,
     TO_TIMESTAMP('2026-06-15 09:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 14, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Exportstatus', 10, 10,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-15 09:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781120
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584995);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781120;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781120);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-15 09:02:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781120 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781120, 0, 549313, 555450, 652266 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-15 09:02:02','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Exportstatus', 10, 10, 0, TO_TIMESTAMP('2026-06-15 09:02:02','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.2 ExternalSystem_Config_ScriptedExportConversion_ID (AD_Column 592781, AD_Element 584101)
--     AD_Name_ID=584997 (ScriptedExport_Config) pins the label to 'Exportkonfiguration'/
--     'Export Configuration', surviving after_migration_sync_translations which would
--     otherwise overwrite AD_Field_Trl from the shared element 584101 (raw technical name).
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_Name_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592781, 781121 /*From ID Server*/, 0, 549313,
     584997 /*ScriptedExport_Config dedicated label element*/,
     TO_TIMESTAMP('2026-06-15 09:02:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 14, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Exportkonfiguration', 20, 20,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-15 09:03:33', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781121
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584997);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781121;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781121);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-15 09:03:34','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781121 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781121, 0, 549313, 555450, 652267 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-15 09:02:06','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Exportkonfiguration', 20, 20, 0, TO_TIMESTAMP('2026-06-15 09:02:06','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.3 HttpResponseCode (AD_Column 592787, AD_Element 584956)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592787, 781122 /*From ID Server*/, 0, 549313,
     TO_TIMESTAMP('2026-06-15 09:02:07', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 10, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'HTTP-Antwortcode', 30, 30,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-15 09:02:07', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781122
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584956);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781122;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781122);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-15 09:02:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781122 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781122, 0, 549313, 555450, 652268 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-15 09:02:09','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'HTTP-Antwortcode', 30, 30, 0, TO_TIMESTAMP('2026-06-15 09:02:09','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.4 StatusMessage (AD_Column 592788, AD_Element 584955)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592788, 781123 /*From ID Server*/, 0, 549313,
     TO_TIMESTAMP('2026-06-15 09:02:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 255, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Status-Meldung', 40, 40,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-15 09:02:10', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781123
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584955);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781123;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781123);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-15 09:02:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781123 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781123, 0, 549313, 555450, 652269 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-15 09:02:12','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Status-Meldung', 40, 40, 0, TO_TIMESTAMP('2026-06-15 09:02:12','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.5 IsResend (AD_Column 592789, AD_Element 584957)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592789, 781124 /*From ID Server*/, 0, 549313,
     TO_TIMESTAMP('2026-06-15 09:02:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 1, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Erneut gesendet', 50, 50,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-15 09:02:13', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781124
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584957);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781124;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781124);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-15 09:02:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781124 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781124, 0, 549313, 555450, 652270 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-15 09:02:15','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Erneut gesendet', 50, 50, 0, TO_TIMESTAMP('2026-06-15 09:02:15','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.6 AD_PInstance_ID (AD_Column 592785, AD_Element 114)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592785, 781125 /*From ID Server*/, 0, 549313,
     TO_TIMESTAMP('2026-06-15 09:02:16', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 14, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Prozess-Instanz', 60, 60,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-15 09:02:16', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781125
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(114);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781125;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781125);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-15 09:02:17','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781125 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781125, 0, 549313, 555450, 652271 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-15 09:02:18','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Prozess-Instanz', 60, 60, 0, TO_TIMESTAMP('2026-06-15 09:02:18','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.7 Updated (AD_Column 592779, AD_Element 607)
--     SortNo=-1: grid sort indicator matches OrderByClause='Updated DESC' on the tab
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592779, 781126 /*From ID Server*/, 0, 549313,
     TO_TIMESTAMP('2026-06-15 09:02:19', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 29, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Aktualisiert', 70, 70,
     -1 /*DESC sort indicator — matches OrderByClause='Updated DESC' on the tab*/, 1, 1,
     TO_TIMESTAMP('2026-06-15 09:02:19', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781126
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(607);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781126;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781126);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-15 09:02:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781126 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781126, 0, 549313, 555450, 652272 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-15 09:02:21','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Aktualisiert', 70, 70, 0, TO_TIMESTAMP('2026-06-15 09:02:21','YYYY-MM-DD HH24:MI:SS'), 100);

-- ===========================================================================
-- PART 5: Rollup field on C_Invoice header tab 263
-- AD_Column 592812 (ScriptedExport_Status virtual), AD_Element 584995
-- Placement: group 540027 ('dates', section seqno=10), seqno=36 (free slot between
--   Eingabequelle=35 and Belegstatus=40), adjacent to Externes System (seqno=34).
-- Not in grid (seqnogrid=0) — summary status field, not a grid column
-- ===========================================================================

-- 5a) AD_Field on tab 263 (Rechnung header)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592812 /*ScriptedExport_Status virtual column*/, 781127 /*From ID Server*/, 0, 263 /*Rechnung header tab*/,
     TO_TIMESTAMP('2026-06-15 09:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 14, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'N' /*not in grid — summary rollup*/,
     'N', 'N', 'N', 'N', 'Y' /*read-only — system-computed virtual column*/,
     'N', 'Exportstatus', 560, 0,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-15 09:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 5b) AD_Field_Trl skeleton rows
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781127
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

-- 5c) Propagate element translations → field (pass AD_Element_ID 584995 = ScriptedExport_Status)
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584995);

-- 5d) Rebuild element links
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781127;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781127);

-- 5e) AD_UI_Element — placed in group 540027 ('dates', section seqno=10) on tab 263
--     Adjacent to Externes System (seqno=34) and Eingabequelle (seqno=35); free slot seqno=36.
--     IsAdvancedField='N' so it is visible on the normal form (not hidden behind Alt+E).
--     seqnogrid=0 — not in grid (summary rollup, not a grid column)
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList,
     Updated, UpdatedBy)
VALUES
    (0, 781127 /*ScriptedExport_Status rollup field*/, 0, 263 /*Rechnung header tab*/,
     540027 /*dates group — contains Externes System (34), Eingabequelle (35), Belegstatus (40)*/,
     652273 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 09:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N' /*IsAdvancedField=N — visible on normal form*/,
     'Y', 'N' /*not in grid — summary rollup*/, 'N',
     'Exportstatus', 36, 0, 0,
     TO_TIMESTAMP('2026-06-15 09:03:20', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 5f) Mark field translations as actively translated (element 584995 already has correct DE+EN translations)
UPDATE AD_Field_Trl SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-15 09:03:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781127 AND AD_Language IN ('de_DE','de_CH','en_US');
