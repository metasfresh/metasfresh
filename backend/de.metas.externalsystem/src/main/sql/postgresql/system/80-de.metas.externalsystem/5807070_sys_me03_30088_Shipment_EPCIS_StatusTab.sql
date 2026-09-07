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

-- me03 30088: EPCIS Error-Handling & Retry — Phase R7 (Window UI)
-- Surfaces EPCIS export status on the Lieferung window (AD_Window_ID=169):
--   1. EPCIS_ExportStatus field (AD_Column 592790, virtual aggregate) on header tab (AD_Tab 257),
--      placed after EDI-Sendestatus (seqno=80, seqnogrid=100) in group 1000029, right column 1000015.
--      Read-only — the value is system-computed.
--   2. Read-only child tab over ExternalSystem_ScriptedExportConversion_Status (AD_Table 542617),
--      showing per-config status rows for the current shipment. WhereClause filters by
--      AD_Table_ID=319 + Record_ID=@M_InOut_ID@. Ordered newest-first (Updated DESC).
--      Columns: ExportStatus, Exportkonfiguration, HttpResponseCode, StatusMessage, IsResend,
--               AD_PInstance_ID, Updated.
--   3. Re-send action: AD_Process 585633 is already bound to M_InOut (AD_Table_Process 541648)
--      by migration 5806830 — confirmed present, no duplicate inserted here.
--   4. AD_SQLColumn_SourceTableColumn entry tying the virtual column 592790 (M_InOut.EPCIS_ExportStatus)
--      to its source table 542617 (ExternalSystem_ScriptedExportConversion_Status), so the WebUI
--      virtual-column cache is invalidated whenever a status row changes.
--
-- IDs allocated from idserver.metas.de on 2026-06-09:
--   AD_Field          780740  (M_InOut header tab: EPCIS_ExportStatus)
--   AD_UI_Element     652035  (M_InOut header tab: EPCIS_ExportStatus)
--   AD_Element        584966  (tab caption: EPCIS-Exportstatus)
--   AD_Tab            549295  (Status child tab)
--   AD_UI_Section     547812  (Status tab section)
--   AD_UI_Column      549544  (Status tab column)
--   AD_UI_ElementGroup 555432 (Status tab element group)
--   AD_Field          780741  (status tab: ExportStatus)
--   AD_UI_Element     652036  (status tab: ExportStatus)
--   AD_Field          780742  (status tab: ExternalSystem_Config_ScriptedExportConversion_ID)
--   AD_UI_Element     652037  (status tab: ExternalSystem_Config_ScriptedExportConversion_ID)
--   AD_Field          780743  (status tab: HttpResponseCode)
--   AD_UI_Element     652038  (status tab: HttpResponseCode)
--   AD_Field          780744  (status tab: StatusMessage)
--   AD_UI_Element     652039  (status tab: StatusMessage)
--   AD_Field          780745  (status tab: IsResend)
--   AD_UI_Element     652040  (status tab: IsResend)
--   AD_Field          780746  (status tab: AD_PInstance_ID)
--   AD_UI_Element     652041  (status tab: AD_PInstance_ID)
--   AD_Field          780747  (status tab: Updated)
--   AD_UI_Element     652042  (status tab: Updated)
--   AD_SQLColumn_SourceTableColumn 540199  (cache: EPCIS_ExportStatus → _Status table)

-- ===========================================================================
-- PART 1: EPCIS_ExportStatus field on Shipment header tab
-- ===========================================================================

-- 1a) AD_Field on tab 257 (Lieferung header), pointing at AD_Column 592790 (EPCIS_ExportStatus virtual)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592790 /*EPCIS_ExportStatus virtual column*/, 780740 /*From ID Server*/, 0, 257 /*Lieferung header tab*/,
     TO_TIMESTAMP('2026-06-09 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 14, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y' /*read-only — value is system-computed virtual column*/,
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
  AND t.AD_Field_ID = 780740
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 1c) Propagate element translations → field (pass AD_Element_ID 584959 = EPCIS_ExportStatus element)
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584959);

-- 1d) Rebuild element links
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780740;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780740);

-- 1e) AD_UI_Element — placed in 'dates' element group (1000029), right column (1000015)
--     after EDI-Sendestatus (seqno=80, seqnogrid=100); seqno=90, seqnogrid=110
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList,
     Updated, UpdatedBy)
VALUES
    (0, 780740 /*EPCIS_ExportStatus field*/, 0, 257 /*Lieferung header tab*/,
     1000029 /*dates group*/, 652035 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-09 10:10:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N',
     'Y', 'Y', 'N',
     'EPCIS-Exportstatus', 90, 110, 0,
     TO_TIMESTAMP('2026-06-09 10:10:10', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- ===========================================================================
-- PART 2: AD_Element for the child tab caption
-- ===========================================================================

-- 2a) AD_Element for the tab caption (AD_Tab.AD_Element_ID is NOT NULL in metasfresh)
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, EntityType, Name, PrintName)
VALUES
    (584966 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-09 10:10:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-09 10:10:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'EPCIS_ExportStatus_Tab', 'de.metas.externalsystem',
     'EPCIS-Exportstatus', 'EPCIS-Exportstatus')
;

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Element_ID = 584966
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- en_US override
UPDATE AD_Element_Trl
SET Name = 'EPCIS Export Status', PrintName = 'EPCIS Export Status',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-06-09 10:10:32', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 584966
;

-- de_DE / de_CH: mark as translated (name matches the base German text)
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-06-09 10:10:33', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID = 584966
;

-- ===========================================================================
-- PART 3: Read-only child tab over ExternalSystem_ScriptedExportConversion_Status
-- ===========================================================================

-- 3a) AD_Tab — read-only, TabLevel=1, no insert, ordered newest-first (Updated DESC)
--     Parent_Column_ID = 592783 (Record_ID in the _Status table, the FK into M_InOut via polymorphic link)
--     WhereClause filters rows for the current M_InOut record via AD_Table_ID=319 + Record_ID context
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
    (0, 0, 549295 /*From ID Server*/, 542617 /*ExternalSystem_ScriptedExportConversion_Status*/, 169 /*Lieferung window*/,
     584966 /*From ID Server*/,
     TO_TIMESTAMP('2026-06-09 10:11:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 'de.metas.externalsystem',
     'N', 'Y', 'N', 'N',
     'N', 'N' /*no insert — status rows are system-written*/, 'Y' /*read-only*/, 'N',
     'N', 'N', 'N', 'N',
     'EPCIS-Exportstatus', 'Updated DESC', 60, 1,
     592783 /*Record_ID column in _Status table — polymorphic FK to M_InOut*/,
     TO_TIMESTAMP('2026-06-09 10:11:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'ExternalSystem_ScriptedExportConversion_Status.AD_Table_ID = 319 AND ExternalSystem_ScriptedExportConversion_Status.Record_ID = @M_InOut_ID/0@')
;

-- 3b) AD_Tab_Trl skeleton rows
INSERT INTO AD_Tab_Trl
    (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Tab_ID = 549295
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;

-- en_US translation
UPDATE AD_Tab_Trl
SET Name         = 'EPCIS Export Status',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-09 10:11:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US'
  AND AD_Tab_ID = 549295
;

-- de_DE / de_CH
UPDATE AD_Tab_Trl
SET Name         = 'EPCIS-Exportstatus',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-09 10:11:13', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language IN ('de_DE', 'de_CH')
  AND AD_Tab_ID = 549295
;

-- ===========================================================================
-- PART 4: UI layout for the status child tab (included tab — single element group)
-- ===========================================================================

-- 4a) AD_UI_Section (one per tab)
INSERT INTO AD_UI_Section
    (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, AD_Tab_ID, SeqNo, Value)
VALUES
    (547812 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-09 10:11:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-09 10:11:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549295, 10, 'default')
;

-- 4b) Single UI column (included tabs use a single column)
INSERT INTO AD_UI_Column
    (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, AD_UI_Section_ID, SeqNo)
VALUES
    (549544 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-09 10:11:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-09 10:11:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547812, 10)
;

-- 4c) Single element group (included tabs must have exactly 1 element group, UIStyle=NULL)
INSERT INTO AD_UI_ElementGroup
    (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES
    (555432 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-09 10:11:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-09 10:11:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549544, 10, NULL, 'default')
;

-- ===========================================================================
-- PART 5: AD_Field + AD_UI_Element rows for each status column
-- Grid + form columns: ExportStatus, Exportkonfiguration, HttpResponseCode,
--                      StatusMessage, IsResend, AD_PInstance_ID, Updated
-- Updated shown last; sort indicator SortNo=-1 on Updated (Updated DESC per OrderByClause)
-- ===========================================================================

-- 5.1 ExportStatus (AD_Column 592784, AD_Element 577791)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592784, 780741 /*From ID Server*/, 0, 549295,
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
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780741
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(577791);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780741;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780741);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-09 10:12:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=780741 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780741, 0, 549295, 555432, 652036 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:02','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Export Status', 10, 10, 0, TO_TIMESTAMP('2026-06-09 10:12:02','YYYY-MM-DD HH24:MI:SS'), 100);

-- 5.2 ExternalSystem_Config_ScriptedExportConversion_ID (AD_Column 592781, AD_Element 584101)
--     Field-level label override: 'Exportkonfiguration' / 'Export Configuration' (shared element not touched)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592781, 780742 /*From ID Server*/, 0, 549295,
     TO_TIMESTAMP('2026-06-09 10:12:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 14, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Exportkonfiguration', 20, 20,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:12:03', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780742
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584101);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780742;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780742);
-- Field-level label override (shared element has raw technical name — override per-field only)
UPDATE AD_Field_Trl SET Name='Exportkonfiguration', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-09 10:12:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=780742 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Field_Trl SET Name='Export Configuration', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-09 10:12:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=780742 AND AD_Language='en_US';
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780742, 0, 549295, 555432, 652037 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:06','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Exportkonfiguration', 20, 20, 0, TO_TIMESTAMP('2026-06-09 10:12:06','YYYY-MM-DD HH24:MI:SS'), 100);

-- 5.3 HttpResponseCode (AD_Column 592787, AD_Element 584956)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592787, 780743 /*From ID Server*/, 0, 549295,
     TO_TIMESTAMP('2026-06-09 10:12:07', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 10, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'HTTP-Antwortcode', 30, 30,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:12:07', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780743
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584956);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780743;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780743);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-09 10:12:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=780743 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780743, 0, 549295, 555432, 652038 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:09','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'HTTP-Antwortcode', 30, 30, 0, TO_TIMESTAMP('2026-06-09 10:12:09','YYYY-MM-DD HH24:MI:SS'), 100);

-- 5.4 StatusMessage (AD_Column 592788, AD_Element 584955)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592788, 780744 /*From ID Server*/, 0, 549295,
     TO_TIMESTAMP('2026-06-09 10:12:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 255, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Status-Meldung', 40, 40,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:12:10', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780744
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584955);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780744;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780744);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-09 10:12:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=780744 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780744, 0, 549295, 555432, 652039 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:12','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Status-Meldung', 40, 40, 0, TO_TIMESTAMP('2026-06-09 10:12:12','YYYY-MM-DD HH24:MI:SS'), 100);

-- 5.5 IsResend (AD_Column 592789, AD_Element 584957)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592789, 780745 /*From ID Server*/, 0, 549295,
     TO_TIMESTAMP('2026-06-09 10:12:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 1, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Erneut gesendet', 50, 50,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:12:13', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780745
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584957);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780745;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780745);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-09 10:12:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=780745 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780745, 0, 549295, 555432, 652040 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:15','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Erneut gesendet', 50, 50, 0, TO_TIMESTAMP('2026-06-09 10:12:15','YYYY-MM-DD HH24:MI:SS'), 100);

-- 5.6 AD_PInstance_ID (AD_Column 592785, AD_Element 114)
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592785, 780746 /*From ID Server*/, 0, 549295,
     TO_TIMESTAMP('2026-06-09 10:12:16', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 14, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Prozess-Instanz', 60, 60,
     0, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:12:16', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780746
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(114);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780746;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780746);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-09 10:12:17','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=780746 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780746, 0, 549295, 555432, 652041 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:18','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Prozess-Instanz', 60, 60, 0, TO_TIMESTAMP('2026-06-09 10:12:18','YYYY-MM-DD HH24:MI:SS'), 100);

-- 5.7 Updated (AD_Column 592779, AD_Element 607)
--     SortNo=-1: grid sort indicator matches OrderByClause='Updated DESC' on the tab
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592779, 780747 /*From ID Server*/, 0, 549295,
     TO_TIMESTAMP('2026-06-09 10:12:19', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 29, 'de.metas.externalsystem',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Aktualisiert', 70, 70,
     -1 /*DESC sort indicator — matches OrderByClause='Updated DESC' on the tab*/, 1, 1,
     TO_TIMESTAMP('2026-06-09 10:12:19', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780747
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(607);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780747;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780747);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-09 10:12:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=780747 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780747, 0, 549295, 555432, 652042 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-06-09 10:12:21','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Aktualisiert', 70, 70, 0, TO_TIMESTAMP('2026-06-09 10:12:21','YYYY-MM-DD HH24:MI:SS'), 100);

-- ===========================================================================
-- PART 6: AD_SQLColumn_SourceTableColumn — cache invalidation
-- Ties virtual column M_InOut.EPCIS_ExportStatus (592790) to its source table
-- ExternalSystem_ScriptedExportConversion_Status (542617), so the WebUI virtual-column
-- cache is invalidated when a status row is inserted/updated for a given M_InOut record.
-- ===========================================================================
INSERT INTO AD_SQLColumn_SourceTableColumn
    (AD_Client_ID, AD_Column_ID, AD_Org_ID, AD_SQLColumn_SourceTableColumn_ID,
     AD_Table_ID,
     Created, CreatedBy, IsActive,
     Source_Table_ID,
     FetchTargetRecordsMethod, SQL_GetTargetRecordIdBySourceRecordId,
     Updated, UpdatedBy)
VALUES
    (0, 592790 /*M_InOut.EPCIS_ExportStatus virtual column*/, 0, 540199 /*From ID Server*/,
     319 /*M_InOut — host table of the virtual column*/,
     TO_TIMESTAMP('2026-06-09 10:13:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
     542617 /*ExternalSystem_ScriptedExportConversion_Status — source of the aggregate*/,
     -- polymorphic link (source carries AD_Table_ID+Record_ID, no direct FK): use SQL method
     -- to map a changed status row back to its host M_InOut_ID (its Record_ID, filtered to this table)
     'S', 'select Record_ID from ExternalSystem_ScriptedExportConversion_Status where ExternalSystem_ScriptedExportConversion_Status_ID=@Record_ID/-1@ and AD_Table_ID=319',
     TO_TIMESTAMP('2026-06-09 10:13:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
