-- Minimal System-Administration admin window for the generic WEBUI_ViewInvalidateOnChange config
-- table (created by 5816550). One single header tab over WEBUI_ViewInvalidateOnChange with the two
-- config fields (AD_Window_ID = window whose views get invalidated, AD_Table_ID = trigger table)
-- plus the standard IsActive / Org / Client fields. Reachable under the System-Administration menu.
--
-- Also corrects the en_US label of the shared element 585139 to "View Invalidation on Change"
-- (5816550 seeded the grammatically-off "View invalidate on change"). German (de_DE/de_CH) is kept
-- as-is. This re-runs on every DB — including one that already applied 5816550 with the old text —
-- so 5816550 stays immutable and the corrected label converges everywhere via this script.
--
-- EntityType matches the table's: de.metas.ui.web (NOT 'D').
--
-- IDs allocated from idserver.metas.de on 2026-07-27:
--   AD_MigrationScript prefix 5816570
--   AD_Window        542178
--   AD_Tab           549358 (header, WEBUI_ViewInvalidateOnChange)
--   AD_Menu          542350
--   AD_UI_Section    547863 ; AD_UI_Column 549610 (left), 549611 (right)
--   AD_UI_ElementGroup 555529 (primary/left), 555530 (flags/right), 555531 (org/right)
--   AD_Field         781850 AD_Window_ID, 781851 AD_Table_ID, 781852 IsActive, 781853 AD_Org_ID, 781854 AD_Client_ID
--   AD_UI_Element    652774..652778
--
-- Reused: AD_Table 542631 WEBUI_ViewInvalidateOnChange
--   cols  AD_Window_ID=593049 AD_Table_ID=593050 IsActive=593044 AD_Org_ID=593042 AD_Client_ID=593041
--   Elements: window/tab/menu caption=585139 ; field labels AD_Window_ID=143 AD_Table_ID=126
--             IsActive=348 AD_Org_ID=113 AD_Client_ID=102
--   Menu: AD_Tree_ID=10, parent 218 (System-Administration)

-- ============================================================
-- 0. Correct en_US wording of the reused caption element (finding: "View Invalidation on Change")
-- ============================================================
UPDATE AD_Element_Trl SET Name='View Invalidation on Change', PrintName='View Invalidation on Change', IsTranslated='Y',
     Updated=TO_TIMESTAMP('2026-07-27 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585139 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585139 /*propagate corrected en_US to column trl*/);

-- ============================================================
-- 1. AD_Window
-- ============================================================
INSERT INTO AD_Window (AD_Window_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, WindowType, IsSOTrx, IsDefault, IsBetaFunctionality, EntityType, AD_Element_ID, InternalName)
VALUES (542178 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'View-Invalidierung bei Änderung', 'M', 'Y', 'N', 'N', 'de.metas.ui.web', 585139, 'WEBUI_ViewInvalidateOnChange');

INSERT INTO AD_Window_Trl (AD_Language, AD_Window_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 542178 /*From ID Server*/, 'N', w.Name, w.Description, w.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Window w
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND w.AD_Window_ID=542178
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=542178);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585139 /*window element*/);

UPDATE AD_Table SET AD_Window_ID=542178 /*From ID Server*/,
     Updated=TO_TIMESTAMP('2026-07-27 12:01:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Table_ID=542631;

-- ============================================================
-- 2. AD_Tab (header, level 0)
-- ============================================================
INSERT INTO AD_Tab (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Window_ID, AD_Table_ID, TabLevel, SeqNo,
     IsSingleRow, IsInfoTab, IsTranslationTab, IsReadOnly, IsInsertRecord, IsAdvancedTab, IsSortTab, HasTree,
     EntityType, AD_Element_ID)
VALUES (549358 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'View-Invalidierung bei Änderung', 542178 /*From ID Server*/, 542631 /*WEBUI_ViewInvalidateOnChange*/, 0, 10,
     'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N',
     'de.metas.ui.web', 585139);

INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help, CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 549358 /*From ID Server*/, 'N', t.Name, t.Description, t.Help, NULL,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Tab t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Tab_ID=549358
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=549358);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585139 /*header tab element*/);

-- ============================================================
-- 3. Header tab fields (AD_Tab_ID=549358)
-- ============================================================
-- 3a. AD_Window_ID
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781850 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Fenster', 549358 /*From ID Server*/, 593049 /*AD_Window_ID*/, 'Y', 10, 'Y', 10,
     'N', 'N', 'N', 'N', 'de.metas.ui.web');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781850 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781850
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781850);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(143 /*AD_Window_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781850;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781850);

-- 3b. AD_Table_ID
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781851 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'DB-Tabelle', 549358 /*From ID Server*/, 593050 /*AD_Table_ID*/, 'Y', 20, 'Y', 20,
     'N', 'N', 'N', 'N', 'de.metas.ui.web');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781851 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:03:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:03:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781851
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781851);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(126 /*AD_Table_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781851;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781851);

-- 3c. IsActive (flags group)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781852 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:03:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:03:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Aktiv', 549358 /*From ID Server*/, 593044 /*IsActive*/, 'Y', 30, 'N', 0,
     'N', 'N', 'N', 'N', 'de.metas.ui.web');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781852 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:03:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:03:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781852
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781852);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(348 /*IsActive element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781852;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781852);

-- 3d. AD_Org_ID (org group)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781853 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:03:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:03:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Sektion', 549358 /*From ID Server*/, 593042 /*AD_Org_ID*/, 'Y', 40, 'Y', 30,
     'N', 'N', 'N', 'N', 'de.metas.ui.web');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781853 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:03:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:03:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781853
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781853);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(113 /*AD_Org_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781853;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781853);

-- 3e. AD_Client_ID (org group, last)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781854 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:03:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:03:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Mandant', 549358 /*From ID Server*/, 593041 /*AD_Client_ID*/, 'Y', 50, 'N', 0,
     'N', 'N', 'N', 'N', 'de.metas.ui.web');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781854 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:03:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:03:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781854
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781854);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(102 /*AD_Client_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781854;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781854);

-- ============================================================
-- 4. UI structure — 2-column header layout
-- ============================================================
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Tab_ID, SeqNo, Name, Value)
VALUES (547863 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549358, 10, 'main', 'main');

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES (549610 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:05:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:05:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547863, 10);

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES (549611 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:05:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:05:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547863, 20);

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555529 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:05:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:05:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549610 /*left*/, 10, 'primary', 'default');

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555530 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:05:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:05:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549611 /*right*/, 10, NULL, 'flags');

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555531 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:05:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:05:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549611 /*right*/, 20, NULL, 'default');

-- ============================================================
-- 5. AD_UI_Elements
-- ============================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781850, 0, 549358, 555529, 652774 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-07-27 12:06:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Fenster', 10, 10, 0, TO_TIMESTAMP('2026-07-27 12:06:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781851, 0, 549358, 555529, 652775 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-07-27 12:06:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'DB-Tabelle', 20, 20, 0, TO_TIMESTAMP('2026-07-27 12:06:01', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781852, 0, 549358, 555530, 652776 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-07-27 12:06:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N',
     'Aktiv', 10, 0, 0, TO_TIMESTAMP('2026-07-27 12:06:02', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781853, 0, 549358, 555531, 652777 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-07-27 12:06:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Sektion', 10, 30, 0, TO_TIMESTAMP('2026-07-27 12:06:03', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781854, 0, 549358, 555531, 652778 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-07-27 12:06:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N',
     'Mandant', 20, 0, 0, TO_TIMESTAMP('2026-07-27 12:06:04', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- ============================================================
-- 6. Filter config — AD_Window_ID + AD_Table_ID default filters
-- ============================================================
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,
     Updated=TO_TIMESTAMP('2026-07-27 12:07:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=593049 /*WEBUI_ViewInvalidateOnChange.AD_Window_ID*/;

UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,
     Updated=TO_TIMESTAMP('2026-07-27 12:07:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=593050 /*WEBUI_ViewInvalidateOnChange.AD_Table_ID*/;

-- ============================================================
-- 7. AD_Menu + tree placement (under System-Administration, node 218)
-- ============================================================
INSERT INTO AD_Menu (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, Action, AD_Window_ID, IsSummary, EntityType, AD_Element_ID, InternalName)
VALUES (542350 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:08:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:08:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'View-Invalidierung bei Änderung', 'W', 542178 /*From ID Server*/, 'N', 'de.metas.ui.web', 585139, 'WEBUI_ViewInvalidateOnChange');

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, IsTranslated, Name, Description,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 542350 /*From ID Server*/, 'N', m.Name, m.Description,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:08:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:08:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Menu m
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND m.AD_Menu_ID=542350
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=542350);

SELECT update_menu_translation_from_ad_element(585139 /*element id*/, NULL /*all languages*/);

INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
VALUES (0, 0, 'Y',
     TO_TIMESTAMP('2026-07-27 12:08:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-27 12:08:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     10 /*menu tree*/, 542350 /*new menu*/, 218 /*System-Administration*/, 13);
