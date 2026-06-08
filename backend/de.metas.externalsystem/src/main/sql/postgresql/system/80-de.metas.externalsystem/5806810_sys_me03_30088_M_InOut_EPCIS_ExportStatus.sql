-- me03 30088: EPCIS Error-Handling & Retry
-- Adds a NEW nullable ref-list column EPCIS_ExportStatus to M_InOut.
-- This is the EPCIS export config's roll-up target column on the shipment;
-- stores the export status using AD_Reference 542104 (ExternalSystem_ExportStatus).
-- Nullable: existing shipments carry no value; no default.
-- Mirrors EDI_ExportStatus (AD_Column 549871) in shape (List/17, Value ref 542104 instead of 540381),
-- but nullable + non-mandatory + no default (EDI counterpart is mandatory with default 'N').
-- EntityType de.metas.externalsystem — column belongs to the externalsystem feature even on M_InOut.
--
-- IDs allocated from idserver.metas.de on 2026-06-08:
--   AD_Element  584959  (EPCIS_ExportStatus)
--   AD_Column   592752  (M_InOut.EPCIS_ExportStatus)

-- 1) AD_Element (German base; EN via _Trl) ---------------------------------------------------
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
  Updated, UpdatedBy, ColumnName, EntityType, Name, PrintName)
VALUES (584959 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-08 15:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-08 15:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'EPCIS_ExportStatus', 'de.metas.externalsystem', 'EPCIS-Exportstatus', 'EPCIS-Exportstatus');

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

-- 2) AD_Column — List(17) ref-list column, nullable, no default ------------------------------
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
  Updated, UpdatedBy, AD_Element_ID, AD_Table_ID, ColumnName, Name, Version,
  AD_Reference_ID, AD_Reference_Value_ID, AD_Val_Rule_ID, EntityType, IsKey, IsParent,
  IsMandatory, IsUpdateable, IsIdentifier, IsTranslated, IsEncrypted, IsSelectionColumn,
  SeqNo, IsAllowLogging, IsAutocomplete, IsCalculated, PersonalDataCategory, FieldLength,
  IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsForceIncludeInGeneratedModel,
  IsLazyLoading, IsUseDocSequence, ColumnSql, IsStaleable, IsSyncDatabase)
VALUES (592752 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-08 15:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-08 15:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
  584959, 319 /*M_InOut*/, 'EPCIS_ExportStatus', 'EPCIS-Exportstatus', 0,
  17 /*List*/, 542104 /*ExternalSystem_ExportStatus*/, NULL,
  'de.metas.externalsystem', 'N', 'N',
  'N' /*nullable — existing shipments have no value*/, 'Y',
  'N', 'N', 'N', 'N',
  0, 'Y',
  'N', 'N', 'NP', 1 /*fieldlength=1 like EDI counterpart*/,
  'N', 'N', 'N',
  'N', 'N', NULL, 'N', 'Y');

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, Description, IsTranslated,
  AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, NULL, 'N',
  t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Column_ID=592752
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

-- Propagate element translations → column (element id, not column id) -----------------------
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584959);

-- 3) Physical column (brand-new → ADD COLUMN, nullable, no default) -------------------------
ALTER TABLE M_InOut ADD COLUMN IF NOT EXISTS EPCIS_ExportStatus CHAR(1);

-- 4) Check constraint (ref-list column pattern: <ColumnName>_Check) -------------------------
ALTER TABLE M_InOut DROP CONSTRAINT IF EXISTS EPCIS_ExportStatus_Check;
ALTER TABLE M_InOut ADD CONSTRAINT EPCIS_ExportStatus_Check
  CHECK (EPCIS_ExportStatus IN ('P','U','D','S','E','I','N'));
