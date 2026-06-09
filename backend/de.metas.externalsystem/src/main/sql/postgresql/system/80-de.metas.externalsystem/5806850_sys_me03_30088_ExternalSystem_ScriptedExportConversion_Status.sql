-- me03 30088: EPCIS Error-Handling & Retry
-- Adds the physical table ExternalSystem_ScriptedExportConversion_Status plus its
-- AD_Table / AD_Element / AD_Column metadata.
-- One row per (config, AD_Table_ID, Record_ID) — the authoritative per-record export state.
-- Grain is different from the Log table (one attempt per row): Status has exactly one row
-- per source record and is upserted on every export attempt.
--
-- Columns:
--   PK + 7 standard columns (AD_Client_ID, AD_Org_ID, IsActive, Created/By, Updated/By)
--   ExternalSystem_Config_ScriptedExportConversion_ID : mandatory FK -> config
--   AD_Table_ID                                       : mandatory FK -> AD_Table
--   Record_ID                                         : mandatory integer
--   ExportStatus                                      : ref-list -> AD_Reference 542104 (codes P/U/D/S/E/I/N, existing)
--   AD_PInstance_ID                                   : nullable FK -> AD_PInstance (in-flight run / callback correlation)
--   AD_Issue_ID                                       : nullable FK -> AD_Issue
--   HttpResponseCode                                  : nullable integer
--   StatusMessage                                     : nullable text
--   IsResend                                          : boolean, default 'N', mandatory
--
-- UNIQUE constraint on (ExternalSystem_Config_ScriptedExportConversion_ID, AD_Table_ID, Record_ID)
-- uses short index name ExtSysScriptedExpConv_Status_uq to stay within Postgres's 63-char limit.
--
-- WebUI window / child tab: intentionally deferred to phase R7 of the 30088 rework
-- (see ai-work/30088/PLAN-REWORK.md). This table is framework-internal until then.
--
-- Reused system AD_Element_IDs:
--   102  AD_Client_ID  | 113  AD_Org_ID     | 348 IsActive
--   245  Created       | 246  CreatedBy      | 607 Updated  | 608 UpdatedBy
--   126  AD_Table_ID   | 538  Record_ID
--   584101 ExternalSystem_Config_ScriptedExportConversion_ID
--   114  AD_PInstance_ID  | 2887 AD_Issue_ID
--   577791 ExportStatus (existing element)
--   584955 StatusMessage  | 584956 HttpResponseCode | 584957 IsResend
--
-- IDs allocated from idserver.metas.de on 2026-06-09:
--   AD_Table   542617
--   AD_Element 584965 (PK: ExternalSystem_ScriptedExportConversion_Status_ID)
--   AD_Column  592773 (PK)
--              592774 (AD_Client_ID),  592775 (AD_Org_ID),  592776 (IsActive)
--              592777 (Created),       592778 (CreatedBy),  592779 (Updated), 592780 (UpdatedBy)
--              592781 (ExternalSystem_Config_ScriptedExportConversion_ID)
--              592782 (AD_Table_ID),   592783 (Record_ID)
--              592784 (ExportStatus),  592785 (AD_PInstance_ID), 592786 (AD_Issue_ID)
--              592787 (HttpResponseCode), 592788 (StatusMessage)
-- (IsResend reuses AD_Column for the new table; a fresh AD_Column ID is included above via 592780..588)

-- ============================================================================
-- 1) AD_Table
-- ============================================================================
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy)
VALUES ('4',0,0,0,542617 /*From ID Server*/,'N',TO_TIMESTAMP('2026-06-09 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','Y','N','N','Y','N','N','N','N','N',0,'ExternalSystem_ScriptedExportConversion_Status','NP','L','ExternalSystem_ScriptedExportConversion_Status','DTI',TO_TIMESTAMP('2026-06-09 09:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Table t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542617
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- ============================================================================
-- 2) New AD_Element for the PK column (all other columns reuse existing elements)
-- ============================================================================
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584965 /*From ID Server*/,0,'ExternalSystem_ScriptedExportConversion_Status_ID',TO_TIMESTAMP('2026-06-09 09:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','ExternalSystem_ScriptedExportConversion_Status','ExternalSystem_ScriptedExportConversion_Status',TO_TIMESTAMP('2026-06-09 09:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Element_ID=584965
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
-- Mark de_DE / de_CH as actively translated (base German text is correct)
UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-09 09:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language IN ('de_DE','de_CH')
    AND AD_Element_ID=584965;
-- Set proper en_US English translation (R1-cleanup: fix missing English name)
UPDATE AD_Element_Trl
  SET IsTranslated='Y',
      Name='ExternalSystem Scripted Export Conversion Status',
      PrintName='ExternalSystem Scripted Export Conversion Status',
      Updated=TO_TIMESTAMP('2026-06-09 09:00:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US'
    AND AD_Element_ID=584965;
-- Propagate translations to AD_Column_Trl and any other _Trl tables linked to this element
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584965);

-- ============================================================================
-- 3) AD_Columns
-- ============================================================================

-- 3.1 PK ----------------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592773 /*From ID Server*/,584965,0,13,542617,'ExternalSystem_ScriptedExportConversion_Status_ID',TO_TIMESTAMP('2026-06-09 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','Y','Y','N','N','Y','N','N','ExternalSystem_ScriptedExportConversion_Status','NP',0,TO_TIMESTAMP('2026-06-09 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592773 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(584965);

-- 3.2 AD_Client_ID ------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592774 /*From ID Server*/,102,0,19,542617,'AD_Client_ID',TO_TIMESTAMP('2026-06-09 09:01:01','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant','NP',0,TO_TIMESTAMP('2026-06-09 09:01:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592774 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(102);

-- 3.3 AD_Org_ID ---------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592775 /*From ID Server*/,113,0,30,542617,'AD_Org_ID',TO_TIMESTAMP('2026-06-09 09:01:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion','NP',10,TO_TIMESTAMP('2026-06-09 09:01:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592775 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(113);

-- 3.4 IsActive ----------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592776 /*From ID Server*/,348,0,20,542617,'IsActive',TO_TIMESTAMP('2026-06-09 09:01:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',1,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv','NP',0,TO_TIMESTAMP('2026-06-09 09:01:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592776 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(348);

-- 3.5 Created -----------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592777 /*From ID Server*/,245,0,16,542617,'Created',TO_TIMESTAMP('2026-06-09 09:01:04','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',29,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt','NP',0,TO_TIMESTAMP('2026-06-09 09:01:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592777 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(245);

-- 3.6 CreatedBy ---------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592778 /*From ID Server*/,246,0,18,110,542617,'CreatedBy',TO_TIMESTAMP('2026-06-09 09:01:05','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch','NP',0,TO_TIMESTAMP('2026-06-09 09:01:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592778 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(246);

-- 3.7 Updated -----------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592779 /*From ID Server*/,607,0,16,542617,'Updated',TO_TIMESTAMP('2026-06-09 09:01:06','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',29,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert','NP',0,TO_TIMESTAMP('2026-06-09 09:01:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592779 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(607);

-- 3.8 UpdatedBy ---------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592780 /*From ID Server*/,608,0,18,110,542617,'UpdatedBy',TO_TIMESTAMP('2026-06-09 09:01:07','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch','NP',0,TO_TIMESTAMP('2026-06-09 09:01:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592780 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(608);

-- 3.9 ExternalSystem_Config_ScriptedExportConversion_ID (mandatory FK -> config) --
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592781 /*From ID Server*/,584101,0,19,542617,'ExternalSystem_Config_ScriptedExportConversion_ID',TO_TIMESTAMP('2026-06-09 09:01:08','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','Y','ExternalSystem_Config_ScriptedExportConversion','NP',0,TO_TIMESTAMP('2026-06-09 09:01:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592781 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(584101);

-- 3.10 AD_Table_ID (mandatory, part of unique grain) --------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592782 /*From ID Server*/,126,0,30,542617,'AD_Table_ID',TO_TIMESTAMP('2026-06-09 09:01:09','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','Y','DB-Tabelle','NP',0,TO_TIMESTAMP('2026-06-09 09:01:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592782 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(126);

-- 3.11 Record_ID (mandatory, part of unique grain) ----------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592783 /*From ID Server*/,538,0,11,542617,'Record_ID',TO_TIMESTAMP('2026-06-09 09:01:10','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','Y','Datensatz-ID','NP',0,TO_TIMESTAMP('2026-06-09 09:01:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592783 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(538);

-- 3.12 ExportStatus (ref-list -> AD_Reference 542104) -------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592784 /*From ID Server*/,577791,0,17,542104,542617,'ExportStatus',TO_TIMESTAMP('2026-06-09 09:01:11','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',1,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','Y','Export Status','NP',0,TO_TIMESTAMP('2026-06-09 09:01:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592784 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(577791);

-- 3.13 AD_PInstance_ID (nullable FK -> AD_PInstance) --------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592785 /*From ID Server*/,114,0,19,542617,'AD_PInstance_ID',TO_TIMESTAMP('2026-06-09 09:01:12','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Prozess-Instanz','NP',0,TO_TIMESTAMP('2026-06-09 09:01:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592785 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(114);

-- 3.14 AD_Issue_ID (nullable FK -> AD_Issue) ----------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592786 /*From ID Server*/,2887,0,19,542617,'AD_Issue_ID',TO_TIMESTAMP('2026-06-09 09:01:13','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Probleme','NP',0,TO_TIMESTAMP('2026-06-09 09:01:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592786 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(2887);

-- 3.15 HttpResponseCode (nullable integer) ------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592787 /*From ID Server*/,584956,0,11,542617,'HttpResponseCode',TO_TIMESTAMP('2026-06-09 09:01:14','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','N','N','N','N','N','Y','N','Y','HTTP-Antwortcode','NP',0,TO_TIMESTAMP('2026-06-09 09:01:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592787 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(584956);

-- 3.16 StatusMessage (nullable text) ------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592788 /*From ID Server*/,584955,0,10,542617,'StatusMessage',TO_TIMESTAMP('2026-06-09 09:01:15','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',255,'Y','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Status-Meldung','NP',0,TO_TIMESTAMP('2026-06-09 09:01:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592788 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(584955);

-- NOTE: IsResend column reuses element 584957 (already created by sibling script 5806790_sys_ExternalSystem_ScriptedExportConversion_Log.sql)
-- A fresh AD_Column ID is needed for the column on THIS table; see section 4.17 below.

-- ============================================================================
-- 4) Physical table + columns
-- ============================================================================
/* DDL */ CREATE TABLE public.ExternalSystem_ScriptedExportConversion_Status (
  AD_Client_ID       NUMERIC(10)  NOT NULL,
  AD_Org_ID          NUMERIC(10)  NOT NULL,
  Created            TIMESTAMP WITH TIME ZONE NOT NULL,
  CreatedBy          NUMERIC(10)  NOT NULL,
  ExternalSystem_ScriptedExportConversion_Status_ID NUMERIC(10) NOT NULL,
  IsActive           CHAR(1)      CHECK (IsActive IN ('Y','N')) NOT NULL,
  Updated            TIMESTAMP WITH TIME ZONE NOT NULL,
  UpdatedBy          NUMERIC(10)  NOT NULL,
  CONSTRAINT ExternalSystem_ScriptedExportConversion_Status_Key
    PRIMARY KEY (ExternalSystem_ScriptedExportConversion_Status_ID)
);

/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Status','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ADD COLUMN ExternalSystem_Config_ScriptedExportConversion_ID NUMERIC(10) NOT NULL');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Status','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ADD COLUMN AD_Table_ID NUMERIC(10) NOT NULL');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Status','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ADD COLUMN Record_ID NUMERIC(10) NOT NULL');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Status','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ADD COLUMN ExportStatus VARCHAR(1) NOT NULL');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Status','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ADD COLUMN AD_PInstance_ID NUMERIC(10)');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Status','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ADD COLUMN AD_Issue_ID NUMERIC(10)');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Status','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ADD COLUMN HttpResponseCode NUMERIC(10)');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Status','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ADD COLUMN StatusMessage VARCHAR(255)');
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Status','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ADD COLUMN IsResend CHAR(1) DEFAULT ''N'' CHECK (IsResend IN (''Y'',''N''))');
UPDATE public.ExternalSystem_ScriptedExportConversion_Status SET IsResend='N' WHERE IsResend IS NULL;
/* DDL */ SELECT public.db_alter_table('ExternalSystem_ScriptedExportConversion_Status','ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ALTER COLUMN IsResend SET NOT NULL');

-- ============================================================================
-- 4.17) AD_Column for IsResend — reuses AD_Element 584957 (created by 5806790_Log script)
-- ============================================================================
-- ID 592789 allocated from idserver.metas.de on 2026-06-09
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592789 /*From ID Server*/,584957,0,20,542617,'IsResend',TO_TIMESTAMP('2026-06-09 09:01:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.externalsystem',1,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','Y','Erneut gesendet','NP',0,TO_TIMESTAMP('2026-06-09 09:01:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592789 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(584957);

-- ============================================================================
-- 5) Foreign-key constraints
-- ============================================================================
ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ADD CONSTRAINT ADTable_ExtSysScrExpConvStatus FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ADD CONSTRAINT ExtSysCfgScrExpConv_ExtSysScrExpConvStatus FOREIGN KEY (ExternalSystem_Config_ScriptedExportConversion_ID) REFERENCES public.ExternalSystem_Config_ScriptedExportConversion DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ADD CONSTRAINT ADPInstance_ExtSysScrExpConvStatus FOREIGN KEY (AD_PInstance_ID) REFERENCES public.AD_PInstance DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ADD CONSTRAINT ADIssue_ExtSysScrExpConvStatus FOREIGN KEY (AD_Issue_ID) REFERENCES public.AD_Issue DEFERRABLE INITIALLY DEFERRED;

-- ============================================================================
-- 6) ExportStatus ref-list check constraint
-- ============================================================================
ALTER TABLE public.ExternalSystem_ScriptedExportConversion_Status ADD CONSTRAINT ExportStatus_ExtSysScrExpConvStatus_Check CHECK (ExportStatus IN ('P','U','D','S','E','I','N'));

-- ============================================================================
-- 7) UNIQUE index on (config, AD_Table_ID, Record_ID)
--    Explicit short name to stay under Postgres's 63-char identifier limit.
-- ============================================================================
CREATE UNIQUE INDEX ExtSysScriptedExpConv_Status_uq
  ON public.ExternalSystem_ScriptedExportConversion_Status
    (ExternalSystem_Config_ScriptedExportConversion_ID, AD_Table_ID, Record_ID);
