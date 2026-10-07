-- EDI location routing — create C_BPartner_EDI_Setting table
--
-- ID Provenance (all from central ID server):
--   AD_MigrationScript prefix : 5805760  (raw 580576 × 10)
--   AD_Table                  : 542610   (C_BPartner_EDI_Setting)
--   AD_Sequence               : 556604   (C_BPartner_EDI_Setting_seq)
--   AD_Element (new, PK only) : 584928   (C_BPartner_EDI_Setting_ID)
--   AD_Column IDs (in order)  : 592670 C_BPartner_EDI_Setting_ID (PK)
--                                592671 AD_Client_ID
--                                592672 AD_Org_ID
--                                592673 IsActive
--                                592674 Created
--                                592675 CreatedBy
--                                592676 Updated
--                                592677 UpdatedBy
--                                592678 C_BPartner_ID
--                                592679 C_BPartner_Location_ID
--                                592680 IsEdiDesadvRecipient
--                                592681 EdiDesadvRecipientGLN
--                                592682 EdiDESADVSendingMode
--                                592683 EdiDESADV_ExternalSystem_Config_ID
--                                592684 EdiDESADVDefaultItemCapacity
--                                592685 IsEdiInvoicRecipient
--                                592686 EdiInvoicRecipientGLN
--                                592687 EdiINVOICSendingMode
--                                592688 EdiINVOIC_ExternalSystem_Config_ID
--   Reused AD_Elements (existing):
--     AD_Client_ID=102, AD_Org_ID=113, Created=245, CreatedBy=246
--     IsActive=348, Updated=607, UpdatedBy=608
--     C_BPartner_ID=187, C_BPartner_Location_ID=189
--     IsEdiDesadvRecipient=577426, EdiDesadvRecipientGLN=542001
--     EdiDESADVSendingMode=584485, EdiDESADV_ExternalSystem_Config_ID=584488
--     EdiDESADVDefaultItemCapacity=542978
--     IsEdiInvoicRecipient=542000, EdiInvoicRecipientGLN=578054
--     EdiINVOICSendingMode=584486, EdiINVOIC_ExternalSystem_Config_ID=584487

-- ============================================================
-- 1. AD_Table
-- ============================================================
-- 2026-06-02T10:00:00.000Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy)
VALUES ('3',0,0,0,542610 /*From ID Server*/,'N',TO_TIMESTAMP('2026-06-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','N','Y','Y','Y','Y','N','N','N','N','N',0,'C_BPartner_EDI_Setting','NP','L','C_BPartner_EDI_Setting','DTI',TO_TIMESTAMP('2026-06-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-02T10:00:01.000Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Table t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Table_ID=542610
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- ============================================================
-- 2. AD_Sequence (native PK sequence)
-- ============================================================
-- 2026-06-02T10:00:02.000Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy)
VALUES (0,0,556604 /*From ID Server*/,TO_TIMESTAMP('2026-06-02 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_BPartner_EDI_Setting',1,'Y','N','Y','Y','C_BPartner_EDI_Setting',1000000,TO_TIMESTAMP('2026-06-02 10:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-02T10:00:03.000Z
CREATE SEQUENCE IF NOT EXISTS C_BPartner_EDI_Setting_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- ============================================================
-- 3. AD_Element for new PK column (C_BPartner_EDI_Setting_ID)
-- ============================================================
-- 2026-06-02T10:00:04.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584928 /*From ID Server*/,0,'C_BPartner_EDI_Setting_ID',TO_TIMESTAMP('2026-06-02 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','EDI-Einstellung Geschäftspartner','EDI-Einstellung Geschäftspartner',TO_TIMESTAMP('2026-06-02 10:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-02T10:00:05.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=584928
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-06-02T10:00:06.000Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='EDI Setting Business Partner', PrintName='EDI Setting Business Partner',
    Updated=TO_TIMESTAMP('2026-06-02 10:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584928 AND AD_Language='en_US'
;

-- 2026-06-02T10:00:07.000Z
UPDATE AD_Element base
SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Element_Trl trl
WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-02T10:00:08.000Z
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584928,'en_US')
;

-- ============================================================
-- 4. AD_Column entries (all 19 columns)
-- ============================================================

-- Column: C_BPartner_EDI_Setting.AD_Client_ID
-- 2026-06-02T10:01:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592671 /*From ID Server*/,102,0,19,542610,'AD_Client_ID',TO_TIMESTAMP('2026-06-02 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.esb.edi',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592671
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:02.000Z
SELECT update_Column_Translation_From_AD_Element(102)
;

-- Column: C_BPartner_EDI_Setting.AD_Org_ID
-- 2026-06-02T10:01:03.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592672 /*From ID Server*/,113,0,30,542610,'AD_Org_ID',TO_TIMESTAMP('2026-06-02 10:01:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.esb.edi',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:04.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592672
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:05.000Z
SELECT update_Column_Translation_From_AD_Element(113)
;

-- Column: C_BPartner_EDI_Setting.IsActive
-- 2026-06-02T10:01:06.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592673 /*From ID Server*/,348,0,20,542610,'IsActive',TO_TIMESTAMP('2026-06-02 10:01:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Der Eintrag ist im System aktiv','de.metas.esb.edi',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:07.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592673
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:08.000Z
SELECT update_Column_Translation_From_AD_Element(348)
;

-- Column: C_BPartner_EDI_Setting.Created
-- 2026-06-02T10:01:09.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592674 /*From ID Server*/,245,0,16,542610,'Created',TO_TIMESTAMP('2026-06-02 10:01:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.esb.edi',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:10.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592674
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:11.000Z
SELECT update_Column_Translation_From_AD_Element(245)
;

-- Column: C_BPartner_EDI_Setting.CreatedBy
-- 2026-06-02T10:01:12.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592675 /*From ID Server*/,246,0,18,110,542610,'CreatedBy',TO_TIMESTAMP('2026-06-02 10:01:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.esb.edi',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:13.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592675
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:14.000Z
SELECT update_Column_Translation_From_AD_Element(246)
;

-- Column: C_BPartner_EDI_Setting.Updated
-- 2026-06-02T10:01:15.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592676 /*From ID Server*/,607,0,16,542610,'Updated',TO_TIMESTAMP('2026-06-02 10:01:15','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.esb.edi',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:16.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592676
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:17.000Z
SELECT update_Column_Translation_From_AD_Element(607)
;

-- Column: C_BPartner_EDI_Setting.UpdatedBy
-- 2026-06-02T10:01:18.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592677 /*From ID Server*/,608,0,18,110,542610,'UpdatedBy',TO_TIMESTAMP('2026-06-02 10:01:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.esb.edi',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:19.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592677
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:20.000Z
SELECT update_Column_Translation_From_AD_Element(608)
;

-- Column: C_BPartner_EDI_Setting.C_BPartner_EDI_Setting_ID (PK)
-- 2026-06-02T10:01:21.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592670 /*From ID Server*/,584928,0,13,542610,'C_BPartner_EDI_Setting_ID',TO_TIMESTAMP('2026-06-02 10:01:21','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'Y','N','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','EDI-Einstellung Geschäftspartner','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:22.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592670
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:23.000Z
SELECT update_Column_Translation_From_AD_Element(584928)
;

-- Column: C_BPartner_EDI_Setting.C_BPartner_ID (mandatory parent FK → C_BPartner)
-- 2026-06-02T10:01:24.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592678 /*From ID Server*/,187,0,30,542610,'C_BPartner_ID',TO_TIMESTAMP('2026-06-02 10:01:24','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'Y','Y','N','N','N','N','N','N','N','Y','Y','N','N','N','Y','N','N','Geschäftspartner','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:25.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592678
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:26.000Z
SELECT update_Column_Translation_From_AD_Element(187)
;

-- Column: C_BPartner_EDI_Setting.C_BPartner_Location_ID (optional FK → C_BPartner_Location)
-- 2026-06-02T10:01:27.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592679 /*From ID Server*/,189,0,30,542610,'C_BPartner_Location_ID',TO_TIMESTAMP('2026-06-02 10:01:27','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Standort','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:28.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592679
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:29.000Z
SELECT update_Column_Translation_From_AD_Element(189)
;

-- Column: C_BPartner_EDI_Setting.IsEdiDesadvRecipient
-- 2026-06-02T10:01:30.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592680 /*From ID Server*/,577426,0,20,542610,'IsEdiDesadvRecipient',TO_TIMESTAMP('2026-06-02 10:01:30','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.esb.edi',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Erhält EDI-DESADV','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:31.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592680
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:32.000Z
SELECT update_Column_Translation_From_AD_Element(577426)
;

-- Column: C_BPartner_EDI_Setting.EdiDesadvRecipientGLN
-- mirror: C_BPartner.EdiDesadvRecipientGLN — String (10), FieldLength=255, PersonalDataCategory=P
-- 2026-06-02T10:01:33.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592681 /*From ID Server*/,542001,0,10,542610,'EdiDesadvRecipientGLN',TO_TIMESTAMP('2026-06-02 10:01:33','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','EDI-DESADV Empfänger-GLN','P',0,0,TO_TIMESTAMP('2026-06-02 10:01:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:34.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592681
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:35.000Z
SELECT update_Column_Translation_From_AD_Element(542001)
;

-- Column: C_BPartner_EDI_Setting.EdiDESADVSendingMode
-- mirror: C_BPartner.EdiDESADVSendingMode — List (17), AD_Reference_Value_ID=542047, default='R', NOT NULL
-- 2026-06-02T10:01:36.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592682 /*From ID Server*/,584485,0,17,542047,542610,'EdiDESADVSendingMode',TO_TIMESTAMP('2026-06-02 10:01:36','YYYY-MM-DD HH24:MI:SS'),100,'N','R','de.metas.esb.edi',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','EDI-DESADV Sendemodus','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:37.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592682
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:38.000Z
SELECT update_Column_Translation_From_AD_Element(584485)
;

-- Column: C_BPartner_EDI_Setting.EdiDESADV_ExternalSystem_Config_ID
-- mirror: C_BPartner column — Table (18), AD_Reference_Value_ID=541268, AD_Val_Rule_ID=540768, MandatoryLogic
-- 2026-06-02T10:01:39.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,MandatoryLogic,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592683 /*From ID Server*/,584488,0,18,541268,542610,540768,'EdiDESADV_ExternalSystem_Config_ID',TO_TIMESTAMP('2026-06-02 10:01:39','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','@EdiDESADVSendingMode@=''E''','EDI-DESADV Externes System Config','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:40.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592683
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:41.000Z
SELECT update_Column_Translation_From_AD_Element(584488)
;

-- Column: C_BPartner_EDI_Setting.EdiDESADVDefaultItemCapacity
-- mirror: C_BPartner.EdiDESADVDefaultItemCapacity — Quantity (29), FieldLength=14, default=1, PersonalDataCategory=NP
-- 2026-06-02T10:01:42.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592684 /*From ID Server*/,542978,0,29,542610,'EdiDESADVDefaultItemCapacity',TO_TIMESTAMP('2026-06-02 10:01:42','YYYY-MM-DD HH24:MI:SS'),100,'N','1','de.metas.esb.edi',0,14,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','EDI-DESADV Standard Verpackungskapazität','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:43.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592684
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:44.000Z
SELECT update_Column_Translation_From_AD_Element(542978)
;

-- Column: C_BPartner_EDI_Setting.IsEdiInvoicRecipient
-- mirror: C_BPartner.IsEdiInvoicRecipient — YesNo (20), default='N', PersonalDataCategory=NP
-- 2026-06-02T10:01:45.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592685 /*From ID Server*/,542000,0,20,542610,'IsEdiInvoicRecipient',TO_TIMESTAMP('2026-06-02 10:01:45','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.esb.edi',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Erhält EDI-INVOIC','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:46.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592685
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:47.000Z
SELECT update_Column_Translation_From_AD_Element(542000)
;

-- Column: C_BPartner_EDI_Setting.EdiInvoicRecipientGLN
-- mirror: C_BPartner.EdiInvoicRecipientGLN — String (10), FieldLength=255, PersonalDataCategory=P
-- 2026-06-02T10:01:48.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592686 /*From ID Server*/,578054,0,10,542610,'EdiInvoicRecipientGLN',TO_TIMESTAMP('2026-06-02 10:01:48','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','EDI-INVOIC Empfänger-GLN','P',0,0,TO_TIMESTAMP('2026-06-02 10:01:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:49.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592686
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:50.000Z
SELECT update_Column_Translation_From_AD_Element(578054)
;

-- Column: C_BPartner_EDI_Setting.EdiINVOICSendingMode
-- mirror: C_BPartner.EdiINVOICSendingMode — List (17), AD_Reference_Value_ID=542047, default='R', NOT NULL
-- 2026-06-02T10:01:51.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592687 /*From ID Server*/,584486,0,17,542047,542610,'EdiINVOICSendingMode',TO_TIMESTAMP('2026-06-02 10:01:51','YYYY-MM-DD HH24:MI:SS'),100,'N','R','de.metas.esb.edi',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','EDI-INVOIC Sendemodus','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:52.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592687
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:53.000Z
SELECT update_Column_Translation_From_AD_Element(584486)
;

-- Column: C_BPartner_EDI_Setting.EdiINVOIC_ExternalSystem_Config_ID
-- mirror: C_BPartner column — Table (18), AD_Reference_Value_ID=541268, AD_Val_Rule_ID=540768, MandatoryLogic
-- 2026-06-02T10:01:54.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,MandatoryLogic,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592688 /*From ID Server*/,584487,0,18,541268,542610,540768,'EdiINVOIC_ExternalSystem_Config_ID',TO_TIMESTAMP('2026-06-02 10:01:54','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','@EdiINVOICSendingMode@=''E''','EDI-INVOIC Externes System Config','NP',0,0,TO_TIMESTAMP('2026-06-02 10:01:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-02T10:01:55.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Column_ID=592688
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-02T10:01:56.000Z
SELECT update_Column_Translation_From_AD_Element(584487)
;

-- ============================================================
-- 5. Physical DDL — create the table
-- ============================================================
-- 2026-06-02T10:02:00.000Z
CREATE TABLE public.C_BPartner_EDI_Setting
(
    C_BPartner_EDI_Setting_ID  NUMERIC(10)                NOT NULL,
    AD_Client_ID               NUMERIC(10)                NOT NULL,
    AD_Org_ID                  NUMERIC(10)                NOT NULL,
    IsActive                   CHAR(1) CHECK (IsActive IN ('Y', 'N')) NOT NULL DEFAULT 'Y',
    Created                    TIMESTAMP WITH TIME ZONE   NOT NULL,
    CreatedBy                  NUMERIC(10)                NOT NULL,
    Updated                    TIMESTAMP WITH TIME ZONE   NOT NULL,
    UpdatedBy                  NUMERIC(10)                NOT NULL,
    -- parent FK
    C_BPartner_ID              NUMERIC(10)                NOT NULL,
    -- optional ship-to location
    C_BPartner_Location_ID     NUMERIC(10),
    -- DESADV settings
    IsEdiDesadvRecipient       CHAR(1) CHECK (IsEdiDesadvRecipient IN ('Y', 'N')) NOT NULL DEFAULT 'N',
    EdiDesadvRecipientGLN      VARCHAR(255),
    EdiDESADVSendingMode       CHAR(1)                    NOT NULL DEFAULT 'R',
    EdiDESADV_ExternalSystem_Config_ID NUMERIC(10),
    EdiDESADVDefaultItemCapacity NUMERIC(14),
    -- INVOIC settings
    IsEdiInvoicRecipient       CHAR(1) CHECK (IsEdiInvoicRecipient IN ('Y', 'N')) NOT NULL DEFAULT 'N',
    EdiInvoicRecipientGLN      VARCHAR(255),
    EdiINVOICSendingMode       CHAR(1)                    NOT NULL DEFAULT 'R',
    EdiINVOIC_ExternalSystem_Config_ID NUMERIC(10),
    CONSTRAINT C_BPartner_EDI_Setting_Key PRIMARY KEY (C_BPartner_EDI_Setting_ID)
)
;

-- ============================================================
-- 6. Foreign key constraints
-- ============================================================
-- 2026-06-02T10:02:01.000Z
ALTER TABLE C_BPartner_EDI_Setting
    ADD CONSTRAINT CBPartnerEdiSetting_CBPartner
    FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2026-06-02T10:02:02.000Z
ALTER TABLE C_BPartner_EDI_Setting
    ADD CONSTRAINT CBPartnerEdiSetting_CBPartnerLocation
    FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- 2026-06-02T10:02:03.000Z
ALTER TABLE C_BPartner_EDI_Setting
    ADD CONSTRAINT CBPartnerEdiSetting_EdiDESADVExternalSystemConfig
    FOREIGN KEY (EdiDESADV_ExternalSystem_Config_ID) REFERENCES public.ExternalSystem_Config DEFERRABLE INITIALLY DEFERRED
;

-- 2026-06-02T10:02:04.000Z
ALTER TABLE C_BPartner_EDI_Setting
    ADD CONSTRAINT CBPartnerEdiSetting_EdiINVOICExternalSystemConfig
    FOREIGN KEY (EdiINVOIC_ExternalSystem_Config_ID) REFERENCES public.ExternalSystem_Config DEFERRABLE INITIALLY DEFERRED
;

-- ============================================================
-- 7. Unique index: one row per (BPartner, Location-or-null)
-- ============================================================
-- 2026-06-02T10:02:05.000Z
CREATE UNIQUE INDEX c_bpartner_edi_setting_unique
    ON C_BPartner_EDI_Setting (C_BPartner_ID, COALESCE(C_BPartner_Location_ID, 0))
;

-- ============================================================
-- 8. Backfill missing translations
-- ============================================================
-- 2026-06-02T10:02:06.000Z
SELECT add_missing_translations()
;
