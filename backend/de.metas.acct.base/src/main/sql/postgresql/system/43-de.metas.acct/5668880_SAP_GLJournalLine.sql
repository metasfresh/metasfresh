-- Table: SAP_GLJournalLine
-- 2022-12-15T15:28:27.917Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('1',0,0,0,542276,'N',TO_TIMESTAMP('2022-12-15 17:28:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','N','Y','N','N','Y','N','N','N','N','N',0,'GL Journal Line (SAP)','NP','L','SAP_GLJournalLine','DTI',TO_TIMESTAMP('2022-12-15 17:28:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:28:27.921Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542276 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-12-15T15:28:28.060Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556068,TO_TIMESTAMP('2022-12-15 17:28:27','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table SAP_GLJournalLine',1,'Y','N','Y','Y','SAP_GLJournalLine','N',1000000,TO_TIMESTAMP('2022-12-15 17:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T15:28:28.066Z
CREATE SEQUENCE SAP_GLJOURNALLINE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: SAP_GLJournalLine.AD_Client_ID
-- 2022-12-15T15:28:47.793Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585383,102,0,19,542276,'AD_Client_ID',TO_TIMESTAMP('2022-12-15 17:28:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','de.metas.acct',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Client',0,0,TO_TIMESTAMP('2022-12-15 17:28:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:28:47.795Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585383 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:28:47.797Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: SAP_GLJournalLine.AD_Org_ID
-- 2022-12-15T15:28:48.524Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585384,113,0,30,542276,'AD_Org_ID',TO_TIMESTAMP('2022-12-15 17:28:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','de.metas.acct',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2022-12-15 17:28:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:28:48.526Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585384 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:28:48.528Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: SAP_GLJournalLine.Created
-- 2022-12-15T15:28:49.225Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585385,245,0,16,542276,'Created',TO_TIMESTAMP('2022-12-15 17:28:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','de.metas.acct',0,29,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created',0,0,TO_TIMESTAMP('2022-12-15 17:28:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:28:49.227Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585385 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:28:49.229Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: SAP_GLJournalLine.CreatedBy
-- 2022-12-15T15:28:49.901Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585386,246,0,18,110,542276,'CreatedBy',TO_TIMESTAMP('2022-12-15 17:28:49','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','de.metas.acct',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created By',0,0,TO_TIMESTAMP('2022-12-15 17:28:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:28:49.902Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585386 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:28:49.904Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: SAP_GLJournalLine.IsActive
-- 2022-12-15T15:28:50.689Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585387,348,0,20,542276,'IsActive',TO_TIMESTAMP('2022-12-15 17:28:50','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','de.metas.acct',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Active',0,0,TO_TIMESTAMP('2022-12-15 17:28:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:28:50.690Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585387 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:28:50.693Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: SAP_GLJournalLine.Updated
-- 2022-12-15T15:28:51.436Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585388,607,0,16,542276,'Updated',TO_TIMESTAMP('2022-12-15 17:28:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','de.metas.acct',0,29,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated',0,0,TO_TIMESTAMP('2022-12-15 17:28:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:28:51.438Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585388 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:28:51.441Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: SAP_GLJournalLine.UpdatedBy
-- 2022-12-15T15:28:52.163Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585389,608,0,18,110,542276,'UpdatedBy',TO_TIMESTAMP('2022-12-15 17:28:51','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','de.metas.acct',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated By',0,0,TO_TIMESTAMP('2022-12-15 17:28:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:28:52.164Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585389 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:28:52.166Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-12-15T15:28:52.686Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581878,0,'SAP_GLJournalLine_ID',TO_TIMESTAMP('2022-12-15 17:28:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','GL Journal Line (SAP)','GL Journal Line (SAP)',TO_TIMESTAMP('2022-12-15 17:28:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T15:28:52.687Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581878 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SAP_GLJournalLine.SAP_GLJournalLine_ID
-- 2022-12-15T15:28:53.288Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585390,581878,0,13,542276,'SAP_GLJournalLine_ID',TO_TIMESTAMP('2022-12-15 17:28:52','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.acct',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','GL Journal Line (SAP)',0,0,TO_TIMESTAMP('2022-12-15 17:28:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:28:53.291Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585390 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:28:53.293Z
/* DDL */  select update_Column_Translation_From_AD_Element(581878) 
;

-- Column: SAP_GLJournalLine.SAP_GLJournal_ID
-- 2022-12-15T15:29:44.008Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585391,581870,0,30,542276,'SAP_GLJournal_ID',TO_TIMESTAMP('2022-12-15 17:29:42','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.acct',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'GL Journal (SAP)',0,0,TO_TIMESTAMP('2022-12-15 17:29:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:29:44.010Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585391 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:29:44.012Z
/* DDL */  select update_Column_Translation_From_AD_Element(581870) 
;

-- Column: SAP_GLJournalLine.C_ValidCombination_ID
-- 2022-12-15T15:30:08.694Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585392,222,0,30,542276,'C_ValidCombination_ID',TO_TIMESTAMP('2022-12-15 17:30:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Valid Account Combination','de.metas.acct',0,10,'The Combination identifies a valid combination of element which represent a GL account.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Combination',0,0,TO_TIMESTAMP('2022-12-15 17:30:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:30:08.695Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585392 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:30:08.698Z
/* DDL */  select update_Column_Translation_From_AD_Element(222) 
;

-- Name: PostingSign
-- 2022-12-15T15:31:52.661Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541699,TO_TIMESTAMP('2022-12-15 17:31:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','PostingSign',TO_TIMESTAMP('2022-12-15 17:31:52','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-12-15T15:31:52.663Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541699 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PostingSign
-- Value: D
-- ValueName: DR
-- 2022-12-15T15:33:40.238Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541699,543362,TO_TIMESTAMP('2022-12-15 17:33:40','YYYY-MM-DD HH24:MI:SS'),100,'Debit','de.metas.acct','Y','DR',TO_TIMESTAMP('2022-12-15 17:33:40','YYYY-MM-DD HH24:MI:SS'),100,'D','DR')
;

-- 2022-12-15T15:33:40.242Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543362 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PostingSign
-- Value: C
-- ValueName: CR
-- 2022-12-15T15:34:03.023Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541699,543363,TO_TIMESTAMP('2022-12-15 17:34:02','YYYY-MM-DD HH24:MI:SS'),100,'Credit','de.metas.acct','Y','CR',TO_TIMESTAMP('2022-12-15 17:34:02','YYYY-MM-DD HH24:MI:SS'),100,'C','CR')
;

-- 2022-12-15T15:34:03.025Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543363 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-12-15T15:34:28.318Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581879,0,'PostingSign',TO_TIMESTAMP('2022-12-15 17:34:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','DR/CR','DR/CR',TO_TIMESTAMP('2022-12-15 17:34:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T15:34:28.319Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581879 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SAP_GLJournalLine.PostingSign
-- 2022-12-15T15:35:29.193Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585393,581879,0,17,541699,542276,'PostingSign',TO_TIMESTAMP('2022-12-15 17:35:29','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.acct',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'DR/CR',0,0,TO_TIMESTAMP('2022-12-15 17:35:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:35:29.194Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585393 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:35:29.196Z
/* DDL */  select update_Column_Translation_From_AD_Element(581879) 
;

-- Column: SAP_GLJournalLine.AmtSource
-- 2022-12-15T15:36:00.744Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585394,2343,0,12,542276,'AmtSource',TO_TIMESTAMP('2022-12-15 17:36:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Amount Balance in Source Currency','de.metas.acct',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Source Amount',0,0,TO_TIMESTAMP('2022-12-15 17:36:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:36:00.745Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585394 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:36:00.747Z
/* DDL */  select update_Column_Translation_From_AD_Element(2343) 
;

-- Column: SAP_GLJournalLine.Amount
-- 2022-12-15T15:36:47.917Z
UPDATE AD_Column SET AD_Element_ID=1367, ColumnName='Amount', Description='Amount in a defined currency', Help='The Amount indicates the amount for this document line.', Name='Amount',Updated=TO_TIMESTAMP('2022-12-15 17:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585394
;

-- 2022-12-15T15:36:47.918Z
UPDATE AD_Field SET Name='Amount', Description='Amount in a defined currency', Help='The Amount indicates the amount for this document line.' WHERE AD_Column_ID=585394
;

-- 2022-12-15T15:36:47.919Z
/* DDL */  select update_Column_Translation_From_AD_Element(1367) 
;

-- Column: SAP_GLJournalLine.AmtAcct
-- 2022-12-15T15:37:17.671Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585395,2342,0,12,542276,'AmtAcct',TO_TIMESTAMP('2022-12-15 17:37:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Amount Balance in Currency of Accounting Schema','de.metas.acct',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Accounted Amount',0,0,TO_TIMESTAMP('2022-12-15 17:37:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:37:17.673Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585395 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:37:17.675Z
/* DDL */  select update_Column_Translation_From_AD_Element(2342) 
;

-- Column: SAP_GLJournalLine.Description
-- 2022-12-15T15:37:35.590Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585396,275,0,36,542276,'Description',TO_TIMESTAMP('2022-12-15 17:37:35','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.acct',0,2000,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Description',0,0,TO_TIMESTAMP('2022-12-15 17:37:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:37:35.592Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585396 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:37:35.594Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: SAP_GLJournalLine.C_Tax_ID
-- 2022-12-15T15:37:53.585Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585397,213,0,30,542276,'C_Tax_ID',TO_TIMESTAMP('2022-12-15 17:37:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Tax identifier','de.metas.acct',0,10,'The Tax indicates the type of tax used in document line.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Tax',0,0,TO_TIMESTAMP('2022-12-15 17:37:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:37:53.587Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585397 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:37:53.590Z
/* DDL */  select update_Column_Translation_From_AD_Element(213) 
;

-- Column: SAP_GLJournalLine.M_SectionCode_ID
-- 2022-12-15T15:38:04.474Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585398,581238,0,30,542276,'M_SectionCode_ID',TO_TIMESTAMP('2022-12-15 17:38:04','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.acct',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-12-15 17:38:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:38:04.475Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585398 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:38:04.478Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- Column: SAP_GLJournalLine.M_Product_ID
-- 2022-12-15T15:39:45.544Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585399,454,0,30,542276,'M_Product_ID',TO_TIMESTAMP('2022-12-15 17:39:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Product, Service, Item','de.metas.acct',0,10,'Identifies an item which is either purchased or sold in this organization.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Product',0,0,TO_TIMESTAMP('2022-12-15 17:39:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:39:45.547Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585399 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:39:45.550Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- Column: SAP_GLJournalLine.C_Activity_ID
-- 2022-12-15T15:39:57.586Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585400,1005,0,30,542276,'C_Activity_ID',TO_TIMESTAMP('2022-12-15 17:39:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Business Activity','de.metas.acct',0,10,'Activities indicate tasks that are performed and used to utilize Activity based Costing','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Activity',0,0,TO_TIMESTAMP('2022-12-15 17:39:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:39:57.587Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585400 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:39:57.589Z
/* DDL */  select update_Column_Translation_From_AD_Element(1005) 
;

-- Column: SAP_GLJournalLine.C_Order_ID
-- 2022-12-15T15:40:14.250Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585401,558,0,30,542276,'C_Order_ID',TO_TIMESTAMP('2022-12-15 17:40:13','YYYY-MM-DD HH24:MI:SS'),100,'N','Order','de.metas.acct',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sales order',0,0,TO_TIMESTAMP('2022-12-15 17:40:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:40:14.251Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585401 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:40:14.254Z
/* DDL */  select update_Column_Translation_From_AD_Element(558) 
;

-- Column: SAP_GLJournalLine.Line
-- 2022-12-15T15:41:10.379Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585402,439,0,11,542276,'Line',TO_TIMESTAMP('2022-12-15 17:41:10','YYYY-MM-DD HH24:MI:SS'),100,'N','','','de.metas.acct',0,10,'Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'SeqNo.',0,0,TO_TIMESTAMP('2022-12-15 17:41:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T15:41:10.381Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585402 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T15:41:10.383Z
/* DDL */  select update_Column_Translation_From_AD_Element(439) 
;

-- 2022-12-15T16:04:39.729Z
/* DDL */ CREATE TABLE public.SAP_GLJournalLine (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Amount NUMERIC NOT NULL, AmtAcct NUMERIC NOT NULL, C_Activity_ID NUMERIC(10), C_Order_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, C_Tax_ID NUMERIC(10), C_ValidCombination_ID NUMERIC(10) NOT NULL, Description TEXT, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Line NUMERIC(10) NOT NULL, M_Product_ID NUMERIC(10), M_SectionCode_ID NUMERIC(10), PostingSign CHAR(1) NOT NULL, SAP_GLJournal_ID NUMERIC(10) NOT NULL, SAP_GLJournalLine_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CActivity_SAPGLJournalLine FOREIGN KEY (C_Activity_ID) REFERENCES public.C_Activity DEFERRABLE INITIALLY DEFERRED, CONSTRAINT COrder_SAPGLJournalLine FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CTax_SAPGLJournalLine FOREIGN KEY (C_Tax_ID) REFERENCES public.C_Tax DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CValidCombination_SAPGLJournalLine FOREIGN KEY (C_ValidCombination_ID) REFERENCES public.C_ValidCombination DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MProduct_SAPGLJournalLine FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MSectionCode_SAPGLJournalLine FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED, CONSTRAINT SAPGLJournal_SAPGLJournalLine FOREIGN KEY (SAP_GLJournal_ID) REFERENCES public.SAP_GLJournal DEFERRABLE INITIALLY DEFERRED, CONSTRAINT SAP_GLJournalLine_Key PRIMARY KEY (SAP_GLJournalLine_ID))
;

-- Tab: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)
-- Table: SAP_GLJournalLine
-- 2022-12-15T16:08:01.992Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,585391,581878,0,546731,542276,541656,'Y',TO_TIMESTAMP('2022-12-15 18:08:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','N','N','A','SAP_GLJournalLine','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'GL Journal Line (SAP)',585351,'N',20,1,TO_TIMESTAMP('2022-12-15 18:08:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:01.994Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546731 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-12-15T16:08:01.996Z
/* DDL */  select update_tab_translation_from_ad_element(581878) 
;

-- 2022-12-15T16:08:02.003Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546731)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Client
-- Column: SAP_GLJournalLine.AD_Client_ID
-- 2022-12-15T16:08:07.807Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585383,710031,0,546731,TO_TIMESTAMP('2022-12-15 18:08:07','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'de.metas.acct','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2022-12-15 18:08:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:07.809Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710031 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:07.810Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-12-15T16:08:08.007Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710031
;

-- 2022-12-15T16:08:08.008Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710031)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Organisation
-- Column: SAP_GLJournalLine.AD_Org_ID
-- 2022-12-15T16:08:08.113Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585384,710032,0,546731,TO_TIMESTAMP('2022-12-15 18:08:08','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'de.metas.acct','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2022-12-15 18:08:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:08.114Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710032 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:08.115Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-12-15T16:08:08.271Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710032
;

-- 2022-12-15T16:08:08.272Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710032)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Active
-- Column: SAP_GLJournalLine.IsActive
-- 2022-12-15T16:08:08.388Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585387,710033,0,546731,TO_TIMESTAMP('2022-12-15 18:08:08','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'de.metas.acct','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2022-12-15 18:08:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:08.389Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710033 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:08.390Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-12-15T16:08:08.569Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710033
;

-- 2022-12-15T16:08:08.571Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710033)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> GL Journal Line (SAP)
-- Column: SAP_GLJournalLine.SAP_GLJournalLine_ID
-- 2022-12-15T16:08:08.695Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585390,710034,0,546731,TO_TIMESTAMP('2022-12-15 18:08:08','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.acct','Y','N','N','N','N','N','N','N','GL Journal Line (SAP)',TO_TIMESTAMP('2022-12-15 18:08:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:08.697Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710034 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:08.699Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581878) 
;

-- 2022-12-15T16:08:08.701Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710034
;

-- 2022-12-15T16:08:08.702Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710034)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> GL Journal (SAP)
-- Column: SAP_GLJournalLine.SAP_GLJournal_ID
-- 2022-12-15T16:08:08.808Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585391,710035,0,546731,TO_TIMESTAMP('2022-12-15 18:08:08','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.acct','Y','N','N','N','N','N','N','N','GL Journal (SAP)',TO_TIMESTAMP('2022-12-15 18:08:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:08.810Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710035 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:08.811Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581870) 
;

-- 2022-12-15T16:08:08.814Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710035
;

-- 2022-12-15T16:08:08.815Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710035)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Combination
-- Column: SAP_GLJournalLine.C_ValidCombination_ID
-- 2022-12-15T16:08:08.918Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585392,710036,0,546731,TO_TIMESTAMP('2022-12-15 18:08:08','YYYY-MM-DD HH24:MI:SS'),100,'Valid Account Combination',10,'de.metas.acct','The Combination identifies a valid combination of element which represent a GL account.','Y','N','N','N','N','N','N','N','Combination',TO_TIMESTAMP('2022-12-15 18:08:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:08.919Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710036 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:08.921Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(222) 
;

-- 2022-12-15T16:08:08.927Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710036
;

-- 2022-12-15T16:08:08.927Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710036)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> DR/CR
-- Column: SAP_GLJournalLine.PostingSign
-- 2022-12-15T16:08:09.042Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585393,710037,0,546731,TO_TIMESTAMP('2022-12-15 18:08:08','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.acct','Y','N','N','N','N','N','N','N','DR/CR',TO_TIMESTAMP('2022-12-15 18:08:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:09.044Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710037 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:09.045Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581879) 
;

-- 2022-12-15T16:08:09.047Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710037
;

-- 2022-12-15T16:08:09.048Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710037)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Amount
-- Column: SAP_GLJournalLine.Amount
-- 2022-12-15T16:08:09.148Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585394,710038,0,546731,TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100,'Amount in a defined currency',10,'de.metas.acct','The Amount indicates the amount for this document line.','Y','N','N','N','N','N','N','N','Amount',TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:09.149Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710038 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:09.151Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1367) 
;

-- 2022-12-15T16:08:09.156Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710038
;

-- 2022-12-15T16:08:09.156Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710038)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Accounted Amount
-- Column: SAP_GLJournalLine.AmtAcct
-- 2022-12-15T16:08:09.267Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585395,710039,0,546731,TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100,'Amount Balance in Currency of Accounting Schema',10,'de.metas.acct','Y','N','N','N','N','N','N','N','Accounted Amount',TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:09.269Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710039 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:09.270Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2342) 
;

-- 2022-12-15T16:08:09.273Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710039
;

-- 2022-12-15T16:08:09.273Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710039)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Description
-- Column: SAP_GLJournalLine.Description
-- 2022-12-15T16:08:09.376Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585396,710040,0,546731,TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100,'',2000,'de.metas.acct','','Y','N','N','N','N','N','N','N','Description',TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:09.378Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710040 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:09.379Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-12-15T16:08:09.419Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710040
;

-- 2022-12-15T16:08:09.421Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710040)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Tax
-- Column: SAP_GLJournalLine.C_Tax_ID
-- 2022-12-15T16:08:09.515Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585397,710041,0,546731,TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100,'Tax identifier',10,'de.metas.acct','The Tax indicates the type of tax used in document line.','Y','N','N','N','N','N','N','N','Tax',TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:09.516Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710041 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:09.518Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(213) 
;

-- 2022-12-15T16:08:09.523Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710041
;

-- 2022-12-15T16:08:09.524Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710041)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Section Code
-- Column: SAP_GLJournalLine.M_SectionCode_ID
-- 2022-12-15T16:08:09.628Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585398,710042,0,546731,TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.acct','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:09.630Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710042 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:09.631Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-12-15T16:08:09.635Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710042
;

-- 2022-12-15T16:08:09.636Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710042)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Product
-- Column: SAP_GLJournalLine.M_Product_ID
-- 2022-12-15T16:08:09.728Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585399,710043,0,546731,TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100,'Product, Service, Item',10,'de.metas.acct','Identifies an item which is either purchased or sold in this organization.','Y','N','N','N','N','N','N','N','Product',TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:09.729Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710043 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:09.730Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2022-12-15T16:08:09.757Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710043
;

-- 2022-12-15T16:08:09.758Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710043)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Activity
-- Column: SAP_GLJournalLine.C_Activity_ID
-- 2022-12-15T16:08:09.866Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585400,710044,0,546731,TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100,'Business Activity',10,'de.metas.acct','Activities indicate tasks that are performed and used to utilize Activity based Costing','Y','N','N','N','N','N','N','N','Activity',TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:09.867Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710044 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:09.869Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005) 
;

-- 2022-12-15T16:08:09.882Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710044
;

-- 2022-12-15T16:08:09.883Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710044)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Sales order
-- Column: SAP_GLJournalLine.C_Order_ID
-- 2022-12-15T16:08:09.976Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585401,710045,0,546731,TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100,'Order',10,'de.metas.acct','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Sales order',TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:09.977Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710045 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:09.978Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2022-12-15T16:08:09.990Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710045
;

-- 2022-12-15T16:08:09.991Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710045)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> SeqNo.
-- Column: SAP_GLJournalLine.Line
-- 2022-12-15T16:08:10.096Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585402,710046,0,546731,TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.acct','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','N','N','N','N','N','N','SeqNo.',TO_TIMESTAMP('2022-12-15 18:08:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T16:08:10.097Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T16:08:10.098Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(439) 
;

-- 2022-12-15T16:08:10.103Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710046
;

-- 2022-12-15T16:08:10.104Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710046)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Accounted Amount
-- Column: SAP_GLJournalLine.AmtAcct
-- 2022-12-15T16:08:37.623Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-12-15 18:08:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710039
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> SeqNo.
-- Column: SAP_GLJournalLine.Line
-- 2022-12-15T16:08:52.092Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2022-12-15 18:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710046
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> SeqNo.
-- Column: SAP_GLJournalLine.Line
-- 2022-12-15T16:21:38.863Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-12-15 18:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710046
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> DR/CR
-- Column: SAP_GLJournalLine.PostingSign
-- 2022-12-15T16:21:38.868Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-12-15 18:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710037
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Combination
-- Column: SAP_GLJournalLine.C_ValidCombination_ID
-- 2022-12-15T16:21:38.873Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-12-15 18:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710036
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Amount
-- Column: SAP_GLJournalLine.Amount
-- 2022-12-15T16:21:38.878Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-12-15 18:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710038
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Accounted Amount
-- Column: SAP_GLJournalLine.AmtAcct
-- 2022-12-15T16:21:38.883Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-12-15 18:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710039
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Tax
-- Column: SAP_GLJournalLine.C_Tax_ID
-- 2022-12-15T16:21:38.888Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-12-15 18:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710041
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Description
-- Column: SAP_GLJournalLine.Description
-- 2022-12-15T16:21:38.893Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-12-15 18:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710040
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Section Code
-- Column: SAP_GLJournalLine.M_SectionCode_ID
-- 2022-12-15T16:21:38.898Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-12-15 18:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710042
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Product
-- Column: SAP_GLJournalLine.M_Product_ID
-- 2022-12-15T16:21:38.902Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-12-15 18:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710043
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Sales order
-- Column: SAP_GLJournalLine.C_Order_ID
-- 2022-12-15T16:21:38.906Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-12-15 18:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710045
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Activity
-- Column: SAP_GLJournalLine.C_Activity_ID
-- 2022-12-15T16:21:38.911Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-12-15 18:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710044
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> SeqNo.
-- Column: SAP_GLJournalLine.Line
-- 2022-12-15T16:22:29.085Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-12-15 18:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710046
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> DR/CR
-- Column: SAP_GLJournalLine.PostingSign
-- 2022-12-15T16:22:29.089Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-12-15 18:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710037
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Combination
-- Column: SAP_GLJournalLine.C_ValidCombination_ID
-- 2022-12-15T16:22:29.094Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2022-12-15 18:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710036
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Amount
-- Column: SAP_GLJournalLine.Amount
-- 2022-12-15T16:22:29.098Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2022-12-15 18:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710038
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Accounted Amount
-- Column: SAP_GLJournalLine.AmtAcct
-- 2022-12-15T16:22:29.102Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2022-12-15 18:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710039
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Tax
-- Column: SAP_GLJournalLine.C_Tax_ID
-- 2022-12-15T16:22:29.107Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2022-12-15 18:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710041
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Description
-- Column: SAP_GLJournalLine.Description
-- 2022-12-15T16:22:29.112Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2022-12-15 18:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710040
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Section Code
-- Column: SAP_GLJournalLine.M_SectionCode_ID
-- 2022-12-15T16:22:29.116Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2022-12-15 18:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710042
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Product
-- Column: SAP_GLJournalLine.M_Product_ID
-- 2022-12-15T16:22:29.121Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2022-12-15 18:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710043
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Sales order
-- Column: SAP_GLJournalLine.C_Order_ID
-- 2022-12-15T16:22:29.125Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2022-12-15 18:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710045
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Activity
-- Column: SAP_GLJournalLine.C_Activity_ID
-- 2022-12-15T16:22:29.129Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2022-12-15 18:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710044
;

