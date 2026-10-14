-- Adds C_Invoice.ScriptedExport_Status as a VIRTUAL (ColumnSQL) AD_Column.
-- No physical column — IsSyncDatabase='N' makes it virtual.
-- The ColumnSql is a ranked-aggregate over ExternalSystem_ScriptedExportConversion_Status:
--   Rank {E,I}→1 (error/invalid), {P,U,D}→2 (in-flight), {S,N}→3 (sent/skip);
--   tie-break by latest Updated; LIMIT 1.
-- No AD_Field/AD_UI_Element added here: window placement is a separate task
-- (the DocuWare Invoice Status Tab) that places this column in the tab layout.
-- AD_SQLColumn_SourceTableColumn ties this column to source table 542617
-- so the WebUI virtual-column cache is invalidated when a status row changes.
--
-- IDs allocated from idserver.metas.de on 2026-06-15:
--   AD_Element                    584995  (ScriptedExport_Status)
--   AD_Column                     592812  (C_Invoice.ScriptedExport_Status virtual)
--   AD_SQLColumn_SourceTableColumn 540206 (cache-link to ExternalSystem_ScriptedExportConversion_Status)
--   AD_MigrationScript            5807900 (this script)

-- ============================================================================
-- 1) AD_Element — generic name, reusable across host tables
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
  Updated, UpdatedBy, ColumnName, EntityType, Name, PrintName)
VALUES (584995 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-15 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-15 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'ScriptedExport_Status', 'de.metas.externalsystem', 'Exportstatus', 'Exportstatus')
ON CONFLICT (AD_Element_ID) DO NOTHING;

-- Seed _Trl rows for all active system languages (copies base DE text)
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=584995
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

-- en_US override
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Export Status', PrintName='Export Status',
  Updated=TO_TIMESTAMP('2026-06-15 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Element_ID=584995;

-- de_DE and de_CH — same text as base, mark as translated
UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-15 10:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language IN ('de_DE','de_CH') AND AD_Element_ID=584995;

-- fr_CH — base text already copied (IsTranslated='N') — leave as-is

-- ============================================================================
-- 2) AD_Column — virtual List(17) column on C_Invoice (AD_Table_ID=318),
--    IsSyncDatabase='N', ColumnSql set
-- ============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
  Updated, UpdatedBy, AD_Element_ID, AD_Table_ID, ColumnName, Name, Version,
  AD_Reference_ID, AD_Reference_Value_ID, AD_Val_Rule_ID, EntityType, IsKey, IsParent,
  IsMandatory, IsUpdateable, IsIdentifier, IsTranslated, IsEncrypted, IsSelectionColumn,
  SeqNo, IsAllowLogging, IsAutocomplete, IsCalculated, PersonalDataCategory, FieldLength,
  IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsForceIncludeInGeneratedModel,
  IsLazyLoading, IsUseDocSequence, ColumnSql, IsStaleable, IsSyncDatabase)
VALUES (592812 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-15 10:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-15 10:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
  584995 /*AD_Element ScriptedExport_Status*/, 318 /*C_Invoice*/, 'ScriptedExport_Status', 'Exportstatus', 0,
  17 /*List*/, 542104 /*ExternalSystem_ExportStatus*/, NULL,
  'de.metas.externalsystem', 'N', 'N',
  'N', 'N' /*read-only virtual column*/,
  'N', 'N', 'N', 'N',
  0, 'Y',
  'N', 'N', 'NP', 1 /*FieldLength=1*/,
  'N', 'N', 'Y' /*IsForceIncludeInGeneratedModel: forces inclusion of this non-D entity-type column in I_C_Invoice*/,
  'N', 'N',
  '(select s.ExportStatus from ExternalSystem_ScriptedExportConversion_Status s
 where s.AD_Table_ID=318 and s.Record_ID=C_Invoice.C_Invoice_ID and s.IsActive=''Y''
 order by case s.ExportStatus when ''E'' then 1 when ''I'' then 1 when ''P'' then 2 when ''U'' then 2 when ''D'' then 2 when ''S'' then 3 when ''N'' then 3 else 4 end, s.Updated desc
 limit 1)',
  'N', 'N' /*IsSyncDatabase=N → virtual, no physical column sync*/)
ON CONFLICT (AD_Column_ID) DO NOTHING;

-- Seed AD_Column_Trl rows
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, Description, IsTranslated,
  AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, NULL, 'N',
  t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Column_ID=592812
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

-- Propagate element translations → column
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584995);

-- ============================================================================
-- 3) AD_SQLColumn_SourceTableColumn — cache-invalidation link
-- ============================================================================
INSERT INTO AD_SQLColumn_SourceTableColumn (
  AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive,
  Created, CreatedBy, Updated, UpdatedBy,
  AD_Column_ID, AD_Table_ID, Source_Table_ID,
  FetchTargetRecordsMethod,
  SQL_GetTargetRecordIdBySourceRecordId)
VALUES (
  540206 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-15 10:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-15 10:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
  592812 /*AD_Column ScriptedExport_Status on C_Invoice*/,
  318    /*C_Invoice — host table*/,
  542617 /*ExternalSystem_ScriptedExportConversion_Status — source table*/,
  'S',
  'select Record_ID from ExternalSystem_ScriptedExportConversion_Status where ExternalSystem_ScriptedExportConversion_Status_ID=@Record_ID/-1@ and AD_Table_ID=318')
ON CONFLICT (AD_SQLColumn_SourceTableColumn_ID) DO NOTHING;
