-- me03 30088: EPCIS Error-Handling & Retry
-- Adds a NEW nullable FK column Status_AD_Column_ID to the existing config table
-- ExternalSystem_Config_ScriptedExportConversion. It points to the column on the export's
-- source table (ExternalSystem_Config_ScriptedExportConversion.AD_Table_ID) into which the
-- framework writes the export status roll-up. Nullable => log-rows-only, no roll-up.
--
-- The picker is restricted (new SQL AD_Val_Rule) to columns of THAT config row's own target
-- table: AD_Column.AD_Table_ID=@AD_Table_ID@ AND AD_Column.IsActive='Y'
-- (@AD_Table_ID@ is the existing AD_Table_ID context column on the same tab).
--
-- Field is placed in the existing "matching" UI element group (left column) of the config's
-- main tab 548471 (window 541961), right after AD_Table_ID / WhereClause — it picks a column
-- of the table chosen in AD_Table_ID, so it belongs next to it.
--
-- IDs allocated from idserver.metas.de on 2026-06-08:
--   AD_Element    584958
--   AD_Column     592751
--   AD_Field      780727
--   AD_UI_Element 652022
--   AD_Val_Rule   540790
-- EntityType de.metas.externalsystem. Search(30) reference to AD_Column, mirroring
-- EXP_FormatLine / DATEV_ExportFormatColumn AD_Column pickers.

-- 1) AD_Val_Rule (SQL) — restrict AD_Column picker to the config row's own target table -----
INSERT INTO AD_Val_Rule (AD_Val_Rule_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
  Updated, UpdatedBy, Name, Type, Code, EntityType)
VALUES (540790 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-08 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-08 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'ScriptedExportConversion Status_AD_Column_ID in target table',
  'S', 'AD_Column.AD_Table_ID=@AD_Table_ID@ AND AD_Column.IsActive=''Y''', 'de.metas.externalsystem');

-- 2) AD_Element (German base; EN via _Trl) -------------------------------------------------
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
  Updated, UpdatedBy, ColumnName, EntityType, Name, PrintName)
VALUES (584958 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-08 14:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-08 14:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
  'Status_AD_Column_ID', 'de.metas.externalsystem', 'Status-Spalte', 'Status-Spalte');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=584958
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Status Column', PrintName='Status Column',
  Updated=TO_TIMESTAMP('2026-06-08 14:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Element_ID=584958;

UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-08 14:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language IN ('de_DE','de_CH') AND AD_Element_ID=584958;

-- 3) AD_Column — Search(30) FK to AD_Column, nullable, with the new val rule ---------------
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
  Updated, UpdatedBy, AD_Element_ID, AD_Table_ID, ColumnName, Name, Version,
  AD_Reference_ID, AD_Val_Rule_ID, EntityType, IsKey, IsParent, IsMandatory, IsUpdateable,
  IsIdentifier, IsTranslated, IsEncrypted, IsSelectionColumn, SeqNo, IsAllowLogging,
  IsAutocomplete, IsCalculated, PersonalDataCategory, FieldLength,
  IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsForceIncludeInGeneratedModel,
  IsLazyLoading, IsUseDocSequence, ColumnSql, IsStaleable, IsSyncDatabase)
VALUES (592751 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-08 14:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-08 14:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
  584958, 542541, 'Status_AD_Column_ID', 'Status-Spalte', 0,
  30, 540790, 'de.metas.externalsystem', 'N', 'N', 'N', 'Y',
  'N', 'N', 'N', 'N', 0, 'Y',
  'N', 'N', 'NP', 10,
  'N', 'N', 'N',
  'N', 'N', NULL, 'N', 'Y');

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, Description, IsTranslated,
  AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, NULL, 'N',
  t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Column_ID=592751
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

-- 4) Physical column (brand-new column → ADD COLUMN, then FK constraint) --------------------
ALTER TABLE ExternalSystem_Config_ScriptedExportConversion
  ADD COLUMN IF NOT EXISTS Status_AD_Column_ID NUMERIC(10);

ALTER TABLE ExternalSystem_Config_ScriptedExportConversion
  ADD CONSTRAINT adcolumn_externalsystemconfigscriptedexportconversion
  FOREIGN KEY (Status_AD_Column_ID) REFERENCES AD_Column(AD_Column_ID)
  DEFERRABLE INITIALLY DEFERRED;

-- 5) AD_Field on the config's main tab 548471 ---------------------------------------------
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
  Updated, UpdatedBy, AD_Column_ID, AD_Tab_ID, Name, IsDisplayed, IsDisplayedGrid,
  IsReadOnly, IsEncrypted, IsSameLine, IsHeading, IsFieldOnly,
  SeqNo, SeqNoGrid, EntityType, Included_Tab_ID, SortNo)
VALUES (780727 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-08 14:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-08 14:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
  592751, 548471, 'Status-Spalte', 'Y', 'N',
  'N', 'N', 'N', 'N', 'N',
  40, 0, 'de.metas.externalsystem', NULL, 0);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
  AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, NULL, NULL, 'N',
  t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Field_ID=780727
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

-- Propagate element translations → column + field (element id, not field id) ---------------
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584958);
SELECT update_FieldTranslation_From_AD_Name_Element(584958);

-- Rebuild element links for the new field --------------------------------------------------
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780727;
SELECT AD_Element_Link_Create_Missing_Field(780727);

-- 6) AD_UI_Element — pair the field into the existing "matching" group (553636) -------------
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created,
  CreatedBy, Updated, UpdatedBy, AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
  AD_UI_ElementType, Name, SeqNo, SeqNoGrid, SeqNo_SideList, IsDisplayed, IsDisplayedGrid,
  IsDisplayed_SideList, IsAdvancedField)
VALUES (652022 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-08 14:02:30','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-08 14:02:30','YYYY-MM-DD HH24:MI:SS'), 100,
  780727, 548471, 553636,
  'F', 'Status-Spalte', 40, 0, 0, 'Y', 'N',
  'N', 'N');
