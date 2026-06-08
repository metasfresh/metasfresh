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

-- me03 30088: EPCIS Error-Handling & Retry — Phase 6.1
-- Surfaces EPCIS export status on the Shipment (Lieferung) window (AD_Window_ID=169):
--   1. EPCIS_ExportStatus field on the header tab (AD_Tab_ID=257), placed after EDI_ExportStatus
--      in the 'dates' element group (1000029), right column (1000015).
--   2. Read-only child tab over ExternalSystem_ScriptedExportConversion_Log (AD_Table_ID=542614),
--      filtered to the current M_InOut via Record_ID/@M_InOut_ID@ and AD_Table_ID=319.
--      Ordered newest-first (Created DESC). Users cannot insert/edit log rows.
--   3. Re-send button (AD_Process_ID=585633) was already bound to M_InOut as AD_Table_Process
--      541648 by migration 5806830 — confirmed existing, NOT duplicated here.
--
-- IDs allocated from idserver.metas.de on 2026-06-09:
--   AD_Element        584960  (tab caption: EPCIS Export Log)
--   AD_Field          780728  (M_InOut header tab: EPCIS_ExportStatus)
--   AD_UI_Element     652023  (M_InOut header tab: EPCIS_ExportStatus)
--   AD_Tab            549292  (log child tab)
--   AD_UI_Section     547811  (log tab section)
--   AD_UI_Column      549543  (log tab column)
--   AD_UI_ElementGroup 555430 (log tab element group)
--   AD_Field          780729  (log tab: ExportStatus)
--   AD_UI_Element     652024  (log tab: ExportStatus)
--   AD_Field          780730  (log tab: ExternalSystem_Config_ScriptedExportConversion_ID)
--   AD_UI_Element     652025  (log tab: ExternalSystem_Config_ScriptedExportConversion_ID)
--   AD_Field          780731  (log tab: AD_PInstance_ID)
--   AD_UI_Element     652026  (log tab: AD_PInstance_ID)
--   AD_Field          780732  (log tab: AD_Issue_ID)
--   AD_UI_Element     652027  (log tab: AD_Issue_ID)
--   AD_Field          780733  (log tab: StatusMessage)
--   AD_UI_Element     652028  (log tab: StatusMessage)
--   AD_Field          780734  (log tab: HttpResponseCode)
--   AD_UI_Element     652029  (log tab: HttpResponseCode)
--   AD_Field          780735  (log tab: IsResend)
--   AD_UI_Element     652030  (log tab: IsResend)
--   AD_Field          780736  (log tab: Created)
--   AD_UI_Element     652031  (log tab: Created)

-- ===========================================================================
-- PART 1: EPCIS_ExportStatus field on Shipment header tab
-- ===========================================================================

-- 1a) AD_Field on tab 257 (Lieferung header), pointing at AD_Column 592752
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592752 /*EPCIS_ExportStatus column*/, 780728 /*From ID Server*/, 0, 257 /*Lieferung tab*/,
     TO_TIMESTAMP('2026-06-09 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 14, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y' /*read-only — status set by the system*/,
     'N', 'EPCIS-Exportstatus', 560, 110,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 1b) AD_Field_Trl skeleton rows
INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Field_ID = 780728
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 1c) Propagate element translations → field (pass AD_Element_ID = 584959)
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584959);

-- 1d) Rebuild element links
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780728;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780728);

-- 1e) AD_UI_Element — placed in the 'dates' element group (1000029), right column (1000015)
--     after EDI_ExportStatus (seqno=80, seqnogrid=100)
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList,
     Updated, UpdatedBy)
VALUES
    (0, 780728 /*EPCIS_ExportStatus field*/, 0, 257 /*Lieferung tab*/,
     1000029 /*dates group*/, 652023 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-09 10:10:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N',
     'Y', 'Y', 'N',
     'EPCIS-Exportstatus', 90, 110, 0,
     TO_TIMESTAMP('2026-06-09 10:10:10', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- ===========================================================================
-- PART 2: Read-only child tab over ExternalSystem_ScriptedExportConversion_Log
-- ===========================================================================

-- 2a-pre) AD_Element for the tab caption (AD_Tab.AD_Element_ID is NOT NULL)
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, EntityType, Name, PrintName)
VALUES
    (584960 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-09 10:10:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-09 10:10:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'EPCIS_ExportLog', 'de.metas.externalsystem', 'EPCIS Export Log', 'EPCIS Export Log')
;

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Element_ID = 584960
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

UPDATE AD_Element_Trl
SET Name = 'EPCIS Export Log', PrintName = 'EPCIS Export Log',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-06-09 10:10:32', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 584960
;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-06-09 10:10:33', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID = 584960
;

-- 2a) AD_Tab — read-only, TabLevel=1, no insert allowed, ordered newest-first
--     WhereClause: filter log rows for this M_InOut via polymorphic Record_ID/AD_Table_ID
INSERT INTO AD_Tab
    (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID,
     AD_Element_ID,
     Created, CreatedBy, Description, EntityType,
     HasTree, IsActive, IsAdvancedTab, IsCheckParentsChanged,
     IsInfoTab, IsInsertRecord, IsReadOnly, IsRefreshAllOnActivate,
     IsSearchActive, IsSingleRow, IsSortTab, IsTranslationTab,
     Name, OrderByClause, SeqNo, TabLevel, Updated, UpdatedBy,
     WhereClause)
VALUES
    (0, 0, 549292 /*From ID Server*/, 542614 /*ExternalSystem_ScriptedExportConversion_Log*/, 169 /*Lieferung window*/,
     584960 /*From ID Server*/,
     TO_TIMESTAMP('2026-06-09 10:11:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 'de.metas.externalsystem',
     'N', 'Y', 'N', 'N',
     'N', 'N' /*no insert — log is system-written*/, 'Y' /*read-only*/, 'N',
     'N', 'N', 'N', 'N',
     'EPCIS Export Log', 'Created DESC', 50, 1,
     TO_TIMESTAMP('2026-06-09 10:11:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'ExternalSystem_ScriptedExportConversion_Log.AD_Table_ID = 319 AND ExternalSystem_ScriptedExportConversion_Log.Record_ID = @M_InOut_ID/0@')
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
  AND t.AD_Tab_ID = 549292
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;

-- Set en_US translation
UPDATE AD_Tab_Trl
SET Name         = 'EPCIS Export Log',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-09 10:11:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US'
  AND AD_Tab_ID = 549292
;

-- Set de_DE / de_CH
UPDATE AD_Tab_Trl
SET Name         = 'EPCIS Export Log',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-09 10:11:13', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language IN ('de_DE', 'de_CH')
  AND AD_Tab_ID = 549292
;

-- ===========================================================================
-- PART 3: UI layout for the log child tab
-- ===========================================================================

-- 3a) AD_UI_Section (one per tab)
INSERT INTO AD_UI_Section
    (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, AD_Tab_ID, SeqNo, Value)
VALUES
    (547811 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-09 10:11:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-09 10:11:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549292, 10, 'default')
;

-- 3b) Single UI column (log tab is a detail/included tab → 1 column only)
INSERT INTO AD_UI_Column
    (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, AD_UI_Section_ID, SeqNo)
VALUES
    (549543 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-09 10:11:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-09 10:11:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547811, 10)
;

-- 3c) Single element group (included tabs must have exactly 1 element group, white/NULL UIStyle)
INSERT INTO AD_UI_ElementGroup
    (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES
    (555430 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-09 10:11:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-09 10:11:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549543, 10, NULL, 'default')
;

-- ===========================================================================
-- PART 4: AD_Field + AD_UI_Element rows for each log column
-- Columns shown (grid + form): ExportStatus, Config, PInstance, Issue,
--                               StatusMessage, HttpResponseCode, IsResend, Created
-- Grid seqno drives column order; SeqNo drives form order (same sequence here).
-- Created shown last (grid col 8) — newest-first ordering is via OrderByClause on the tab.
-- ===========================================================================

-- 4.1 ExportStatus (AD_Column_ID=592746, AD_Element_ID=577791)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592746, 780729 /*From ID Server*/, 0, 549292,
     TO_TIMESTAMP('2026-06-09 10:12:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 14, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Export Status', 10, 10,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:12:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780729
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(577791);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780729;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780729);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780729, 0, 549292, 555430, 652024 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:01','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Export Status', 10, 10, 0, TO_TIMESTAMP('2026-06-09 10:12:01','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.2 ExternalSystem_Config_ScriptedExportConversion_ID (AD_Column_ID=592744, AD_Element_ID=584101)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592744, 780730 /*From ID Server*/, 0, 549292,
     TO_TIMESTAMP('2026-06-09 10:12:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 14, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'ExternalSystem_Config_ScriptedExportConversion', 20, 20,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:12:02', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780730
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584101);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780730;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780730);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780730, 0, 549292, 555430, 652025 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:03','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'ExternalSystem_Config_ScriptedExportConversion', 20, 20, 0, TO_TIMESTAMP('2026-06-09 10:12:03','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.3 AD_PInstance_ID (AD_Column_ID=592745, AD_Element_ID=114)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592745, 780731 /*From ID Server*/, 0, 549292,
     TO_TIMESTAMP('2026-06-09 10:12:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 14, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Prozess-Instanz', 30, 30,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:12:04', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780731
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(114);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780731;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780731);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780731, 0, 549292, 555430, 652026 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:05','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Prozess-Instanz', 30, 30, 0, TO_TIMESTAMP('2026-06-09 10:12:05','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.4 AD_Issue_ID (AD_Column_ID=592747, AD_Element_ID=2887)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592747, 780732 /*From ID Server*/, 0, 549292,
     TO_TIMESTAMP('2026-06-09 10:12:06', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 14, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Probleme', 40, 40,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:12:06', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780732
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2887);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780732;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780732);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780732, 0, 549292, 555430, 652027 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:07','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Probleme', 40, 40, 0, TO_TIMESTAMP('2026-06-09 10:12:07','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.5 StatusMessage (AD_Column_ID=592748, AD_Element_ID=584955)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592748, 780733 /*From ID Server*/, 0, 549292,
     TO_TIMESTAMP('2026-06-09 10:12:08', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 255, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Status-Meldung', 50, 50,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:12:08', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780733
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584955);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780733;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780733);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780733, 0, 549292, 555430, 652028 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:09','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Status-Meldung', 50, 50, 0, TO_TIMESTAMP('2026-06-09 10:12:09','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.6 HttpResponseCode (AD_Column_ID=592749, AD_Element_ID=584956)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592749, 780734 /*From ID Server*/, 0, 549292,
     TO_TIMESTAMP('2026-06-09 10:12:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 10, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'HTTP-Antwortcode', 60, 60,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:12:10', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780734
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584956);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780734;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780734);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780734, 0, 549292, 555430, 652029 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:11','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'HTTP-Antwortcode', 60, 60, 0, TO_TIMESTAMP('2026-06-09 10:12:11','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.7 IsResend (AD_Column_ID=592750, AD_Element_ID=584957)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592750, 780735 /*From ID Server*/, 0, 549292,
     TO_TIMESTAMP('2026-06-09 10:12:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 1, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Erneut gesendet', 70, 70,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:12:12', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780735
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584957);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780735;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780735);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780735, 0, 549292, 555430, 652030 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:13','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Erneut gesendet', 70, 70, 0, TO_TIMESTAMP('2026-06-09 10:12:13','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.8 Created (AD_Column_ID=592738, AD_Element_ID=245)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592738, 780736 /*From ID Server*/, 0, 549292,
     TO_TIMESTAMP('2026-06-09 10:12:14', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 29, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Erstellt', 80, 80,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:12:14', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780736
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(245);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780736;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780736);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780736, 0, 549292, 555430, 652031 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:15','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Erstellt', 80, 80, 0, TO_TIMESTAMP('2026-06-09 10:12:15','YYYY-MM-DD HH24:MI:SS'), 100);
