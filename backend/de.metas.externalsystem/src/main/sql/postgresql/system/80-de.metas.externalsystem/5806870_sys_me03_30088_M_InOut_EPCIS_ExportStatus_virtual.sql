-- me03 30088: EPCIS Error-Handling & Retry
-- Adds M_InOut.EPCIS_ExportStatus as a VIRTUAL (ColumnSQL) AD_Column.
-- No physical column or CHECK constraint — IsSyncDatabase='N' makes it virtual.
-- AD_Element 584959 (EPCIS_ExportStatus) is created here (relocated from the now-deleted
-- 5806810 script) to make this script self-contained on a fresh DB apply.
-- AD_Column 592752 (physical) was dropped in R1.2; this is a fresh virtual column.
--
-- The ColumnSql is a ranked-aggregate over ExternalSystem_ScriptedExportConversion_Status:
--   Rank {E,I}→1 (error/invalid), {P,U,D}→2 (in-flight), {S,N}→3 (sent/skip);
--   tie-break by latest Updated; LIMIT 1.
--
-- IDs allocated from idserver.metas.de on 2026-06-09:
--   AD_Column  592790  (M_InOut.EPCIS_ExportStatus virtual)

-- ============================================================================
-- 1) AD_Element 584959 (EPCIS_ExportStatus) — relocated from deleted 5806810
--    script; idempotency guard ensures safe re-apply on a DB that already has it.
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
  Updated, UpdatedBy, ColumnName, EntityType, Name, PrintName)
VALUES (584959 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-08 15:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-08 15:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'EPCIS_ExportStatus', 'de.metas.externalsystem', 'EPCIS-Exportstatus', 'EPCIS-Exportstatus')
ON CONFLICT (AD_Element_ID) DO NOTHING;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=584959
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EPCIS Export Status', PrintName='EPCIS Export Status',
  Updated=TO_TIMESTAMP('2026-06-08 15:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Element_ID=584959;

UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-08 15:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language IN ('de_DE','de_CH') AND AD_Element_ID=584959;

-- ============================================================================
-- 2) AD_Column — virtual List(17) column, IsSyncDatabase='N', ColumnSql set
-- ============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
  Updated, UpdatedBy, AD_Element_ID, AD_Table_ID, ColumnName, Name, Version,
  AD_Reference_ID, AD_Reference_Value_ID, AD_Val_Rule_ID, EntityType, IsKey, IsParent,
  IsMandatory, IsUpdateable, IsIdentifier, IsTranslated, IsEncrypted, IsSelectionColumn,
  SeqNo, IsAllowLogging, IsAutocomplete, IsCalculated, PersonalDataCategory, FieldLength,
  IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsForceIncludeInGeneratedModel,
  IsLazyLoading, IsUseDocSequence, ColumnSql, IsStaleable, IsSyncDatabase)
VALUES (592790 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-09 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-09 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  584959 /*AD_Element EPCIS_ExportStatus*/, 319 /*M_InOut*/, 'EPCIS_ExportStatus', 'EPCIS-Exportstatus', 0,
  17 /*List*/, 542104 /*ExternalSystem_ExportStatus*/, NULL,
  'de.metas.externalsystem', 'N', 'N',
  'N', 'N' /*read-only virtual column*/,
  'N', 'N', 'N', 'N',
  0, 'Y',
  'N', 'N', 'NP', 1 /*fieldlength=1 like EDI counterpart*/,
  'N', 'N', 'Y' /*IsForceIncludeInGeneratedModel: must be Y so the model generator includes
                  this non-D entity-type column in the base I_M_InOut interface*/,
  'N', 'N',
  '(SELECT s.ExportStatus FROM ExternalSystem_ScriptedExportConversion_Status s
 WHERE s.AD_Table_ID=319 AND s.Record_ID=M_InOut.M_InOut_ID AND s.IsActive=''Y''
 ORDER BY CASE s.ExportStatus WHEN ''E'' THEN 1 WHEN ''I'' THEN 1 WHEN ''P'' THEN 2 WHEN ''U'' THEN 2 WHEN ''D'' THEN 2 WHEN ''S'' THEN 3 WHEN ''N'' THEN 3 ELSE 4 END, s.Updated DESC
 LIMIT 1)',
  'N', 'N' /*IsSyncDatabase=N → virtual, no physical column sync*/)
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, Description, IsTranslated,
  AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, NULL, 'N',
  t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Column_ID=592790
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

-- Propagate element translations → column (element id, not column id) ------------------
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584959);
