-- me03 30088: EPCIS Error-Handling & Retry
-- Adds the physical table ExternalSystem_ScriptedExportConversion_Log plus its
-- AD_Table / AD_Element / AD_Column metadata. One row per scripted-export attempt.
--
-- Columns:
--   PK + 7 standard columns (AD_Client_ID, AD_Org_ID, IsActive, Created/By, Updated/By)
--   AD_Table_ID + Record_ID                                  : generic source-record (TableRecordReference)
--   ExternalSystem_Config_ScriptedExportConversion_ID        : FK -> the export config
--   AD_PInstance_ID                                          : correlation key
--   ExportStatus                                             : ref-list column -> AD_Reference 542104 (Task 1.1)
--   AD_Issue_ID                                              : FK -> AD_Issue (NULLable), linked error report
--   StatusMessage                                            : short outcome text (NULLable)
--   HttpResponseCode                                         : HTTP code (NULLable)
--   IsResend                                                 : auto vs manual re-send marker (default 'N')
--
-- Reused system AD_Element_IDs: 102 AD_Client_ID, 113 AD_Org_ID, 348 IsActive,
--   245 Created, 246 CreatedBy, 607 Updated, 608 UpdatedBy, 126 AD_Table_ID,
--   538 Record_ID, 584101 ExternalSystem_Config_ScriptedExportConversion_ID,
--   114 AD_PInstance_ID, 2887 AD_Issue_ID, 577791 ExportStatus (existing element, reused).
--
-- IDs allocated from idserver.metas.de on 2026-06-08:
--   AD_Table   542614
--   AD_Element 584953 (PK), 584955 (StatusMessage),
--              584956 (HttpResponseCode), 584957 (IsResend)
--   AD_Column  592734..592750
-- (ExportStatus reuses existing AD_Element 577791 'Export Status'; 584954 was allocated but
--  not used, since an element with ColumnName='ExportStatus' already exists.)

-- ============================================================================
-- 1) AD_Table
-- ============================================================================
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy)
VALUES ('4',0,0,0,542614 /*From ID Server*/,'N',TO_TIMESTAMP('2026-06-08 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','Y','N','N','Y','N','N','N','N','N',0,'ExternalSystem_ScriptedExportConversion_Log','NP','L','ExternalSystem_ScriptedExportConversion_Log','DTI',TO_TIMESTAMP('2026-06-08 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Table t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542614
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- ============================================================================
-- 2) New AD_Elements (PK + the four log-specific columns)
-- ============================================================================
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584953 /*From ID Server*/,0,'ExternalSystem_ScriptedExportConversion_Log_ID',TO_TIMESTAMP('2026-06-08 11:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','ExternalSystem_ScriptedExportConversion_Log','ExternalSystem_ScriptedExportConversion_Log',TO_TIMESTAMP('2026-06-08 11:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584953
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- (ExportStatus reuses existing AD_Element 577791 'Export Status' — no new element needed.)

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584955 /*From ID Server*/,0,'StatusMessage',TO_TIMESTAMP('2026-06-08 11:00:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Status-Meldung','Status-Meldung',TO_TIMESTAMP('2026-06-08 11:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584955
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Status Message', PrintName='Status Message',
  Updated=TO_TIMESTAMP('2026-06-08 11:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Element_ID=584955;

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584956 /*From ID Server*/,0,'HttpResponseCode',TO_TIMESTAMP('2026-06-08 11:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','HTTP-Antwortcode','HTTP-Antwortcode',TO_TIMESTAMP('2026-06-08 11:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584956
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='HTTP Response Code', PrintName='HTTP Response Code',
  Updated=TO_TIMESTAMP('2026-06-08 11:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Element_ID=584956;

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584957 /*From ID Server*/,0,'IsResend',TO_TIMESTAMP('2026-06-08 11:00:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Erneut gesendet','Erneut gesendet',TO_TIMESTAMP('2026-06-08 11:00:06','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584957
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Resend', PrintName='Resend',
  Updated=TO_TIMESTAMP('2026-06-08 11:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Element_ID=584957;

-- ============================================================================
-- 3) AD_Columns
-- ============================================================================

-- 3.1 PK ----------------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592734 /*From ID Server*/,584953,0,13,542614,'ExternalSystem_ScriptedExportConversion_Log_ID',TO_TIMESTAMP('2026-06-08 11:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','Y','Y','N','N','Y','N','N','ExternalSystem_ScriptedExportConversion_Log','NP',0,TO_TIMESTAMP('2026-06-08 11:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592734 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(584953);

-- 3.2 AD_Client_ID ------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592735 /*From ID Server*/,102,0,19,542614,'AD_Client_ID',TO_TIMESTAMP('2026-06-08 11:01:01','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant','NP',0,TO_TIMESTAMP('2026-06-08 11:01:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592735 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(102);

-- 3.3 AD_Org_ID ---------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592736 /*From ID Server*/,113,0,30,542614,'AD_Org_ID',TO_TIMESTAMP('2026-06-08 11:01:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion','NP',10,TO_TIMESTAMP('2026-06-08 11:01:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592736 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(113);

-- 3.4 IsActive ----------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592737 /*From ID Server*/,348,0,20,542614,'IsActive',TO_TIMESTAMP('2026-06-08 11:01:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',1,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv','NP',0,TO_TIMESTAMP('2026-06-08 11:01:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592737 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(348);

-- 3.5 Created -----------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592738 /*From ID Server*/,245,0,16,542614,'Created',TO_TIMESTAMP('2026-06-08 11:01:04','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',29,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt','NP',0,TO_TIMESTAMP('2026-06-08 11:01:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592738 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(245);

-- 3.6 CreatedBy ---------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592739 /*From ID Server*/,246,0,18,110,542614,'CreatedBy',TO_TIMESTAMP('2026-06-08 11:01:05','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch','NP',0,TO_TIMESTAMP('2026-06-08 11:01:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592739 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(246);

-- 3.7 Updated -----------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592740 /*From ID Server*/,607,0,16,542614,'Updated',TO_TIMESTAMP('2026-06-08 11:01:06','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',29,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert','NP',0,TO_TIMESTAMP('2026-06-08 11:01:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592740 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(607);

-- 3.8 UpdatedBy ---------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592741 /*From ID Server*/,608,0,18,110,542614,'UpdatedBy',TO_TIMESTAMP('2026-06-08 11:01:07','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch','NP',0,TO_TIMESTAMP('2026-06-08 11:01:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592741 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(608);

-- 3.9 AD_Table_ID (generic source-record, TableRecordReference) ----------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592742 /*From ID Server*/,126,0,30,542614,'AD_Table_ID',TO_TIMESTAMP('2026-06-08 11:01:08','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','N','N','N','Y','N','Y','DB-Tabelle','NP',0,TO_TIMESTAMP('2026-06-08 11:01:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592742 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(126);

-- 3.10 Record_ID --------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592743 /*From ID Server*/,538,0,11,542614,'Record_ID',TO_TIMESTAMP('2026-06-08 11:01:09','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Datensatz-ID','NP',0,TO_TIMESTAMP('2026-06-08 11:01:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592743 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(538);

-- 3.11 ExternalSystem_Config_ScriptedExportConversion_ID (FK -> config) --------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592744 /*From ID Server*/,584101,0,19,542614,'ExternalSystem_Config_ScriptedExportConversion_ID',TO_TIMESTAMP('2026-06-08 11:01:10','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','Y','ExternalSystem_Config_ScriptedExportConversion','NP',0,TO_TIMESTAMP('2026-06-08 11:01:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592744 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(584101);

-- 3.12 AD_PInstance_ID (correlation key) --------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592745 /*From ID Server*/,114,0,19,542614,'AD_PInstance_ID',TO_TIMESTAMP('2026-06-08 11:01:11','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Prozess-Instanz','NP',0,TO_TIMESTAMP('2026-06-08 11:01:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592745 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(114);

-- 3.13 ExportStatus (ref-list -> AD_Reference 542104) -------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592746 /*From ID Server*/,577791,0,17,542104,542614,'ExportStatus',TO_TIMESTAMP('2026-06-08 11:01:12','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',1,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','Y','Export Status','NP',0,TO_TIMESTAMP('2026-06-08 11:01:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592746 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(577791);

-- 3.14 AD_Issue_ID (FK -> AD_Issue, NULLable) ---------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592747 /*From ID Server*/,2887,0,19,542614,'AD_Issue_ID',TO_TIMESTAMP('2026-06-08 11:01:13','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Probleme','NP',0,TO_TIMESTAMP('2026-06-08 11:01:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592747 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(2887);

-- 3.15 StatusMessage (NULLable) -----------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592748 /*From ID Server*/,584955,0,10,542614,'StatusMessage',TO_TIMESTAMP('2026-06-08 11:01:14','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',255,'Y','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Status-Meldung','NP',0,TO_TIMESTAMP('2026-06-08 11:01:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592748 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(584955);

-- 3.16 HttpResponseCode (NULLable) --------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592749 /*From ID Server*/,584956,0,11,542614,'HttpResponseCode',TO_TIMESTAMP('2026-06-08 11:01:15','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','N','N','N','Y','N','Y','HTTP-Antwortcode','NP',0,TO_TIMESTAMP('2026-06-08 11:01:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592749 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(584956);

-- 3.17 IsResend (default 'N') -------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592750 /*From ID Server*/,584957,0,20,542614,'IsResend',TO_TIMESTAMP('2026-06-08 11:01:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.externalsystem',1,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','Y','Erneut gesendet','NP',0,TO_TIMESTAMP('2026-06-08 11:01:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592750 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(584957);

-- ============================================================================
-- 4) Physical table + columns
-- ============================================================================
/* DDL */ CREATE TABLE public.ExternalSystem_ScriptedExportConversion_Log (
  AD_Client_ID NUMERIC(10) NOT NULL,
  AD_Org_ID NUMERIC(10) NOT NULL,
  Created TIMESTAMP WITH TIME ZONE NOT NULL,
  CreatedBy NUMERIC(10) NOT NULL,
  ExternalSystem_ScriptedExportConversion_Log_ID NUMERIC(10) NOT NULL,
  IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL,
  Updated TIMESTAMP WITH TIME ZONE NOT NULL,
  UpdatedBy NUMERIC(10) NOT NULL,
  CONSTRAINT ExternalSystem_ScriptedExportConversion_Log_Key PRIMARY KEY (ExternalSystem_ScriptedExportConversion_Log_ID))
;

/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Log','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ADD COLUMN AD_Table_ID NUMERIC(10)');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Log','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ADD COLUMN Record_ID NUMERIC(10)');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Log','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ADD COLUMN ExternalSystem_Config_ScriptedExportConversion_ID NUMERIC(10) NOT NULL');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Log','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ADD COLUMN AD_PInstance_ID NUMERIC(10)');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Log','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ADD COLUMN ExportStatus VARCHAR(1) NOT NULL');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Log','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ADD COLUMN AD_Issue_ID NUMERIC(10)');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Log','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ADD COLUMN StatusMessage VARCHAR(255)');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Log','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ADD COLUMN HttpResponseCode NUMERIC(10)');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Log','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ADD COLUMN IsResend CHAR(1) DEFAULT ''N'' CHECK (IsResend IN (''Y'',''N''))');
UPDATE public.ExternalSystem_ScriptedExportConversion_Log SET IsResend='N' WHERE IsResend IS NULL;
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Log','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ALTER COLUMN IsResend SET NOT NULL');

-- ============================================================================
-- 5) Foreign-key constraints
-- ============================================================================
ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ADD CONSTRAINT ADTable_ExtSysScrExpConvLog FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ADD CONSTRAINT ExtSysCfgScrExpConv_ExtSysScrExpConvLog FOREIGN KEY (ExternalSystem_Config_ScriptedExportConversion_ID) REFERENCES public.ExternalSystem_Config_ScriptedExportConversion DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ADD CONSTRAINT ADPInstance_ExtSysScrExpConvLog FOREIGN KEY (AD_PInstance_ID) REFERENCES public.AD_PInstance DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ADD CONSTRAINT ADIssue_ExtSysScrExpConvLog FOREIGN KEY (AD_Issue_ID) REFERENCES public.AD_Issue DEFERRABLE INITIALLY DEFERRED;

-- ============================================================================
-- 6) ExportStatus ref-list check constraint
-- ============================================================================
ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Log ADD CONSTRAINT ExportStatus_ExtSysScrExpConvLog_Check CHECK (ExportStatus IN ('P','U','D','S','E','I','N'));
