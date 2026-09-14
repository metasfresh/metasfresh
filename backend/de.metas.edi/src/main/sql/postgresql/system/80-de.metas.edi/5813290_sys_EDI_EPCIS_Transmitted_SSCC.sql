-- EPCIS transmission ledger — creates the physical table EDI_EPCIS_Transmitted_SSCC: one row
-- per (receiver-config, SSCC18) that has been transmitted, so a resend can be detected/prevented
-- ("exactly once" delivery semantics).
--
-- EntityType: 'de.metas.esb.edi' — every physical EDI_* table created in this exact
-- module/folder (EDI_Desadv, EDI_DesadvLine, EDI_Desadv_Pack, C_BPartner_EDI_Setting, ...)
-- uses this EntityType; verified against ad_entitytype and every existing AD_Table row before
-- choosing it (per the "never invent EntityType" rule).
--
-- Columns:
--   PK + 7 standard columns (AD_Client_ID, AD_Org_ID, IsActive, Created/By, Updated/By)
--   SSCC18                                             : mandatory VARCHAR(30) — the LU's SSCC18 attribute value (m_hu_attribute.value)
--   ExternalSystem_Config_ScriptedExportConversion_ID  : mandatory FK -> receiver/config that transmitted it
--   M_InOut_ID                                         : mandatory FK -> shipment that transmitted it
--   Transmitted                                        : mandatory TIMESTAMP WITH TIME ZONE — when it was sent
--
-- UNIQUE INDEX on (ExternalSystem_Config_ScriptedExportConversion_ID, SSCC18)
-- — one ledger row per (receiver-config, physical SSCC).
--
-- WebUI window / field placement: a read-only diagnostic child tab is added on the Lieferung /
-- shipment window (AD_Window_ID=169) by migration 5813400, mirroring the read-only diagnostic
-- tab already shipped for ExternalSystem_ScriptedExportConversion_Status (5806870/5807070).
--
-- Reused system AD_Element_IDs:
--   102 AD_Client_ID | 113 AD_Org_ID | 348 IsActive
--   245 Created      | 246 CreatedBy | 607 Updated     | 608 UpdatedBy
--   584101 ExternalSystem_Config_ScriptedExportConversion_ID (existing element)
--   1025   M_InOut_ID (existing element)
--
-- ID provenance (all from the central ID server):
--   AD_MigrationScript 5813290
--   AD_Table           542624
--   AD_Element         585083 (PK: EDI_EPCIS_Transmitted_SSCC_ID)
--   AD_Element         585084 (SSCC18)
--   AD_Element         585085 (Transmitted)
--   AD_Column          592926 (PK)
--                      592927 (AD_Client_ID),  592928 (AD_Org_ID),   592929 (IsActive)
--                      592930 (Created),       592931 (CreatedBy),   592932 (Updated), 592933 (UpdatedBy)
--                      592934 (SSCC18)
--                      592935 (ExternalSystem_Config_ScriptedExportConversion_ID)
--                      592936 (M_InOut_ID)
--                      592937 (Transmitted)

-- ============================================================
-- 1. AD_Table
-- ============================================================
-- 2026-07-11T09:00:00Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy)
VALUES ('3',0,0,0,542624 /*From ID Server*/,'N',TO_TIMESTAMP('2026-07-11 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','N','Y','N','Y','N','Y','N','N','N','N',0,'EDI_EPCIS_Transmitted_SSCC','NP','L','EDI_EPCIS_Transmitted_SSCC','DTI',TO_TIMESTAMP('2026-07-11 09:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-07-11T09:00:01Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Table t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Table_ID=542624
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- ============================================================
-- 2. New AD_Elements
-- ============================================================

-- 2.1 PK column element
-- 2026-07-11T09:00:02Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585083 /*From ID Server*/,0,'EDI_EPCIS_Transmitted_SSCC_ID',TO_TIMESTAMP('2026-07-11 09:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','EDI EPCIS Übertragene SSCC','EDI EPCIS Übertragene SSCC',TO_TIMESTAMP('2026-07-11 09:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2026-07-11T09:00:03Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=585083
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
-- 2026-07-11T09:00:04Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI EPCIS Transmitted SSCC', PrintName='EDI EPCIS Transmitted SSCC',
  Updated=TO_TIMESTAMP('2026-07-11 09:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Element_ID=585083;
-- 2026-07-11T09:00:05Z
UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-07-11 09:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language IN ('de_DE','de_CH') AND AD_Element_ID=585083;
-- 2026-07-11T09:00:06Z
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585083);

-- 2.2 SSCC18 element
-- 2026-07-11T09:00:07Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585084 /*From ID Server*/,0,'SSCC18',TO_TIMESTAMP('2026-07-11 09:00:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','SSCC18','SSCC18',TO_TIMESTAMP('2026-07-11 09:00:07','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2026-07-11T09:00:08Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=585084
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
-- 2026-07-11T09:00:09Z
UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-07-11 09:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language IN ('de_DE','de_CH','en_US') AND AD_Element_ID=585084;
-- 2026-07-11T09:00:10Z
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585084);

-- 2.3 Transmitted element
-- 2026-07-11T09:00:11Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585085 /*From ID Server*/,0,'Transmitted',TO_TIMESTAMP('2026-07-11 09:00:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','Übertragen am','Übertragen am',TO_TIMESTAMP('2026-07-11 09:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2026-07-11T09:00:12Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=585085
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
-- 2026-07-11T09:00:13Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Transmitted', PrintName='Transmitted',
  Updated=TO_TIMESTAMP('2026-07-11 09:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Element_ID=585085;
-- 2026-07-11T09:00:14Z
UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-07-11 09:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language IN ('de_DE','de_CH') AND AD_Element_ID=585085;
-- 2026-07-11T09:00:15Z
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585085);

-- ============================================================
-- 3. AD_Columns
-- ============================================================

-- 3.1 PK ------------------------------------------------------
-- 2026-07-11T09:01:00Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592926 /*From ID Server*/,585083,0,13,542624,'EDI_EPCIS_Transmitted_SSCC_ID',TO_TIMESTAMP('2026-07-11 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',10,'Y','N','N','N','N','N','N','Y','Y','N','N','Y','N','N','EDI EPCIS Übertragene SSCC','NP',0,TO_TIMESTAMP('2026-07-11 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592926 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(585083);

-- 3.2 AD_Client_ID ---------------------------------------------
-- 2026-07-11T09:01:01Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592927 /*From ID Server*/,102,0,19,542624,'AD_Client_ID',TO_TIMESTAMP('2026-07-11 09:01:01','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',10,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant','NP',0,TO_TIMESTAMP('2026-07-11 09:01:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592927 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(102);

-- 3.3 AD_Org_ID --------------------------------------------------
-- 2026-07-11T09:01:02Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592928 /*From ID Server*/,113,0,30,542624,'AD_Org_ID',TO_TIMESTAMP('2026-07-11 09:01:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',10,'Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion','NP',10,TO_TIMESTAMP('2026-07-11 09:01:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592928 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(113);

-- 3.4 IsActive -----------------------------------------------------
-- 2026-07-11T09:01:03Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592929 /*From ID Server*/,348,0,20,542624,'IsActive',TO_TIMESTAMP('2026-07-11 09:01:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',1,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv','NP',0,TO_TIMESTAMP('2026-07-11 09:01:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592929 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(348);

-- 3.5 Created -----------------------------------------------------
-- 2026-07-11T09:01:04Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592930 /*From ID Server*/,245,0,16,542624,'Created',TO_TIMESTAMP('2026-07-11 09:01:04','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',29,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt','NP',0,TO_TIMESTAMP('2026-07-11 09:01:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592930 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(245);

-- 3.6 CreatedBy -----------------------------------------------------
-- 2026-07-11T09:01:05Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592931 /*From ID Server*/,246,0,18,110,542624,'CreatedBy',TO_TIMESTAMP('2026-07-11 09:01:05','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',10,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch','NP',0,TO_TIMESTAMP('2026-07-11 09:01:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592931 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(246);

-- 3.7 Updated -----------------------------------------------------
-- 2026-07-11T09:01:06Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592932 /*From ID Server*/,607,0,16,542624,'Updated',TO_TIMESTAMP('2026-07-11 09:01:06','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',29,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert','NP',0,TO_TIMESTAMP('2026-07-11 09:01:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592932 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(607);

-- 3.8 UpdatedBy -----------------------------------------------------
-- 2026-07-11T09:01:07Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592933 /*From ID Server*/,608,0,18,110,542624,'UpdatedBy',TO_TIMESTAMP('2026-07-11 09:01:07','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',10,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch','NP',0,TO_TIMESTAMP('2026-07-11 09:01:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592933 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(608);

-- 3.9 SSCC18 (mandatory, VARCHAR(30)) -------------------------------
-- 2026-07-11T09:01:08Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592934 /*From ID Server*/,585084,0,10,542624,'SSCC18',TO_TIMESTAMP('2026-07-11 09:01:08','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',30,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','Y','SSCC18','NP',0,TO_TIMESTAMP('2026-07-11 09:01:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592934 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(585084);

-- 3.10 ExternalSystem_Config_ScriptedExportConversion_ID (mandatory FK -> receiver/config) --
-- 2026-07-11T09:01:09Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592935 /*From ID Server*/,584101,0,19,542624,'ExternalSystem_Config_ScriptedExportConversion_ID',TO_TIMESTAMP('2026-07-11 09:01:09','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',10,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','Y','ExternalSystem_Config_ScriptedExportConversion','NP',0,TO_TIMESTAMP('2026-07-11 09:01:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592935 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(584101);

-- 3.11 M_InOut_ID (mandatory FK -> shipment) --------------------------
-- 2026-07-11T09:01:10Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592936 /*From ID Server*/,1025,0,30,542624,'M_InOut_ID',TO_TIMESTAMP('2026-07-11 09:01:10','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',10,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','Y','Lieferung/Wareneingang','NP',0,TO_TIMESTAMP('2026-07-11 09:01:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592936 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(1025);

-- 3.12 Transmitted (mandatory TIMESTAMP WITH TIME ZONE) ----------------
-- 2026-07-11T09:01:11Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592937 /*From ID Server*/,585085,0,16,542624,'Transmitted',TO_TIMESTAMP('2026-07-11 09:01:11','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',29,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','Y','Übertragen am','NP',0,TO_TIMESTAMP('2026-07-11 09:01:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592937 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);
/* DDL */ select update_Column_Translation_From_AD_Element(585085);

-- ============================================================
-- 4. Physical DDL — create the table
-- ============================================================
-- 2026-07-11T09:02:00Z
CREATE TABLE public.EDI_EPCIS_Transmitted_SSCC
(
    EDI_EPCIS_Transmitted_SSCC_ID NUMERIC(10)              NOT NULL,
    AD_Client_ID                  NUMERIC(10)              NOT NULL,
    AD_Org_ID                     NUMERIC(10)              NOT NULL,
    IsActive                      CHAR(1) CHECK (IsActive IN ('Y', 'N')) NOT NULL DEFAULT 'Y',
    Created                       TIMESTAMP WITH TIME ZONE NOT NULL,
    CreatedBy                     NUMERIC(10)              NOT NULL,
    Updated                       TIMESTAMP WITH TIME ZONE NOT NULL,
    UpdatedBy                     NUMERIC(10)              NOT NULL,
    SSCC18                        VARCHAR(30)              NOT NULL,
    ExternalSystem_Config_ScriptedExportConversion_ID NUMERIC(10) NOT NULL,
    M_InOut_ID                    NUMERIC(10)              NOT NULL,
    Transmitted                   TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT EDI_EPCIS_Transmitted_SSCC_Key PRIMARY KEY (EDI_EPCIS_Transmitted_SSCC_ID)
)
;

-- ============================================================
-- 5. Foreign key constraints
-- ============================================================
-- 2026-07-11T09:02:01Z
ALTER TABLE public.EDI_EPCIS_Transmitted_SSCC
    ADD CONSTRAINT ExtSysCfgScrExpConv_EPCIS_TSSCC
    FOREIGN KEY (ExternalSystem_Config_ScriptedExportConversion_ID) REFERENCES public.ExternalSystem_Config_ScriptedExportConversion DEFERRABLE INITIALLY DEFERRED
;

-- 2026-07-11T09:02:02Z
ALTER TABLE public.EDI_EPCIS_Transmitted_SSCC
    ADD CONSTRAINT MInOut_EDI_EPCIS_TransmSSCC
    FOREIGN KEY (M_InOut_ID) REFERENCES public.M_InOut DEFERRABLE INITIALLY DEFERRED
;

-- ============================================================
-- 6. UNIQUE index: one ledger row per (receiver-config, physical SSCC)
-- ============================================================
-- 2026-07-11T09:02:03Z
CREATE UNIQUE INDEX EDI_EPCIS_Transmitted_SSCC_uq
    ON public.EDI_EPCIS_Transmitted_SSCC (ExternalSystem_Config_ScriptedExportConversion_ID, SSCC18)
;

-- ============================================================
-- 7. Backfill missing translations
-- ============================================================
-- 2026-07-11T09:02:04Z
SELECT add_missing_translations();
