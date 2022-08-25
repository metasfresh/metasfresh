-- Table: C_BPartner_Forwarder
-- 2022-08-24T14:09:58.953Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Window_ID,CopyColumnsFromTable,Created,CreatedBy,Description,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TechnicalNote,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542208,541584,'N',TO_TIMESTAMP('2022-08-24 15:09:54','YYYY-MM-DD HH24:MI:SS'),100,'Define forwarder relations for the business partner','de.metas.ab182','N','Y','N','Y','Y','N','N','N','N','N',0,'Forwarder','NP','L','C_BPartner_Forwarder','','DTI',TO_TIMESTAMP('2022-08-24 15:09:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T14:09:59.113Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542208 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-08-24T14:10:00.594Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556005,TO_TIMESTAMP('2022-08-24 15:09:59','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_BPartner_Forwarder',1,'Y','N','Y','Y','C_BPartner_Forwarder','N',1000000,TO_TIMESTAMP('2022-08-24 15:09:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T14:10:00.882Z
CREATE SEQUENCE C_BPARTNER_FORWARDER_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_BPartner_Forwarder.AD_Client_ID
-- 2022-08-24T14:13:07.214Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584208,102,0,19,542208,'AD_Client_ID',TO_TIMESTAMP('2022-08-24 15:13:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','de.metas.ab182',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Client',0,0,TO_TIMESTAMP('2022-08-24 15:13:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-24T14:13:07.316Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584208 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-24T14:13:07.546Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_BPartner_Forwarder.AD_Org_ID
-- 2022-08-24T14:13:20.707Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584209,113,0,30,542208,'AD_Org_ID',TO_TIMESTAMP('2022-08-24 15:13:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','de.metas.ab182',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2022-08-24 15:13:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-24T14:13:20.808Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584209 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-24T14:13:21.020Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_BPartner_Forwarder.Created
-- 2022-08-24T14:13:31.175Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584210,245,0,16,542208,'Created',TO_TIMESTAMP('2022-08-24 15:13:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','de.metas.ab182',0,29,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created',0,0,TO_TIMESTAMP('2022-08-24 15:13:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-24T14:13:31.277Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584210 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-24T14:13:31.476Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_BPartner_Forwarder.CreatedBy
-- 2022-08-24T14:13:41.683Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584211,246,0,18,110,542208,'CreatedBy',TO_TIMESTAMP('2022-08-24 15:13:40','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','de.metas.ab182',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created By',0,0,TO_TIMESTAMP('2022-08-24 15:13:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-24T14:13:41.784Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584211 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-24T14:13:41.984Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_BPartner_Forwarder.IsActive
-- 2022-08-24T14:13:52.403Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584212,348,0,20,542208,'IsActive',TO_TIMESTAMP('2022-08-24 15:13:51','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','de.metas.ab182',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Active',0,0,TO_TIMESTAMP('2022-08-24 15:13:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-24T14:13:52.519Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584212 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-24T14:13:52.720Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_BPartner_Forwarder.Updated
-- 2022-08-24T14:14:03.798Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584213,607,0,16,542208,'Updated',TO_TIMESTAMP('2022-08-24 15:14:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','de.metas.ab182',0,29,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated',0,0,TO_TIMESTAMP('2022-08-24 15:14:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-24T14:14:03.899Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584213 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-24T14:14:04.089Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_BPartner_Forwarder.UpdatedBy
-- 2022-08-24T14:14:17.785Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584214,608,0,18,110,542208,'UpdatedBy',TO_TIMESTAMP('2022-08-24 15:14:16','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','de.metas.ab182',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated By',0,0,TO_TIMESTAMP('2022-08-24 15:14:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-24T14:14:17.889Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584214 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-24T14:14:18.078Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-08-24T14:14:29.205Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581378,0,'C_BPartner_Forwarder_ID',TO_TIMESTAMP('2022-08-24 15:14:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ab182','Y','Forwarder','Forwarder',TO_TIMESTAMP('2022-08-24 15:14:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T14:14:29.311Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581378 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_BPartner_Forwarder.C_BPartner_Forwarder_ID
-- 2022-08-24T14:14:38.118Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584215,581378,0,13,542208,'C_BPartner_Forwarder_ID',TO_TIMESTAMP('2022-08-24 15:14:27','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ab182',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Forwarder',0,0,TO_TIMESTAMP('2022-08-24 15:14:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-24T14:14:38.234Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584215 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-24T14:14:38.435Z
/* DDL */  select update_Column_Translation_From_AD_Element(581378) 
;

-- Column: C_BPartner_Forwarder.C_BPartner_ID
-- 2022-08-24T14:19:36.102Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584216,187,0,19,542208,'C_BPartner_ID',TO_TIMESTAMP('2022-08-24 15:19:34','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.ab182',0,10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Business Partner',0,0,TO_TIMESTAMP('2022-08-24 15:19:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-24T14:19:36.205Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584216 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-24T14:19:36.404Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- Column: C_BPartner_Forwarder.C_BPartner_ID
-- 2022-08-24T14:20:04.854Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-08-24 15:20:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584216
;

-- 2022-08-24T14:29:04.640Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581379,0,'Forwarder_ID',TO_TIMESTAMP('2022-08-24 15:29:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ab182','Y','Forwarder','Forwarder',TO_TIMESTAMP('2022-08-24 15:29:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T14:29:04.736Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581379 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_BPartner_Forwarder.Forwarder_ID
-- 2022-08-24T14:31:24.581Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584217,581379,0,30,138,542208,'Forwarder_ID',TO_TIMESTAMP('2022-08-24 15:31:23','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ab182',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Forwarder',0,0,TO_TIMESTAMP('2022-08-24 15:31:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-24T14:31:24.683Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584217 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-24T14:31:24.885Z
/* DDL */  select update_Column_Translation_From_AD_Element(581379) 
;

-- Column: C_BPartner_Forwarder.IsDefaultForwarder
-- 2022-08-24T14:32:49.457Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584218,581327,0,20,542208,'IsDefaultForwarder',TO_TIMESTAMP('2022-08-24 15:32:48','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.ab182',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Default Forwarder',0,0,TO_TIMESTAMP('2022-08-24 15:32:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-24T14:32:49.561Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584218 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-24T14:32:49.752Z
/* DDL */  select update_Column_Translation_From_AD_Element(581327) 
;

-- 2022-08-24T14:33:31.753Z
/* DDL */ CREATE TABLE public.C_BPartner_Forwarder (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_BPartner_Forwarder_ID NUMERIC(10) NOT NULL, C_BPartner_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Forwarder_ID NUMERIC(10), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsDefaultForwarder CHAR(1) DEFAULT 'N' CHECK (IsDefaultForwarder IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_BPartner_Forwarder_Key PRIMARY KEY (C_BPartner_Forwarder_ID), CONSTRAINT CBPartner_CBPartnerForwarder FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED, CONSTRAINT Forwarder_CBPartnerForwarder FOREIGN KEY (Forwarder_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED)
;

-- Tab: Business Partner -> Forwarder
-- Table: C_BPartner_Forwarder
-- 2022-08-24T14:34:23.433Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581378,0,546599,542208,541584,'Y',TO_TIMESTAMP('2022-08-24 15:34:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ab182','N','N','A','C_BPartner_Forwarder','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Forwarder','N',300,0,TO_TIMESTAMP('2022-08-24 15:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T14:34:23.534Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546599 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-08-24T14:34:23.637Z
/* DDL */  select update_tab_translation_from_ad_element(581378) 
;

-- 2022-08-24T14:34:23.738Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546599)
;

-- Tab: Business Partner -> Forwarder
-- Table: C_BPartner_Forwarder
-- 2022-08-24T14:34:35.788Z
UPDATE AD_Tab SET SeqNo=45,Updated=TO_TIMESTAMP('2022-08-24 15:34:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546599
;

-- Tab: Business Partner -> Forwarder
-- Table: C_BPartner_Forwarder
-- 2022-08-24T14:35:00.448Z
UPDATE AD_Tab SET AD_Column_ID=584216,Updated=TO_TIMESTAMP('2022-08-24 15:35:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546599
;

-- Field: Business Partner -> Forwarder -> Client
-- Column: C_BPartner_Forwarder.AD_Client_ID
-- 2022-08-24T14:35:16.107Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584208,705521,0,546599,TO_TIMESTAMP('2022-08-24 15:35:14','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'de.metas.ab182','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2022-08-24 15:35:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T14:35:16.206Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705521 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-24T14:35:16.302Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-08-24T14:35:16.498Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705521
;

-- 2022-08-24T14:35:16.593Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705521)
;

-- Field: Business Partner -> Forwarder -> Organisation
-- Column: C_BPartner_Forwarder.AD_Org_ID
-- 2022-08-24T14:35:18.053Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584209,705522,0,546599,TO_TIMESTAMP('2022-08-24 15:35:16','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'de.metas.ab182','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2022-08-24 15:35:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T14:35:18.153Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705522 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-24T14:35:18.250Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-08-24T14:35:18.450Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705522
;

-- 2022-08-24T14:35:18.542Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705522)
;

-- Field: Business Partner -> Forwarder -> Active
-- Column: C_BPartner_Forwarder.IsActive
-- 2022-08-24T14:35:20.024Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584212,705523,0,546599,TO_TIMESTAMP('2022-08-24 15:35:18','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'de.metas.ab182','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2022-08-24 15:35:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T14:35:20.126Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705523 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-24T14:35:20.222Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-08-24T14:35:20.442Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705523
;

-- 2022-08-24T14:35:20.534Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705523)
;

-- Field: Business Partner -> Forwarder -> Forwarder
-- Column: C_BPartner_Forwarder.C_BPartner_Forwarder_ID
-- 2022-08-24T14:35:22.008Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584215,705524,0,546599,TO_TIMESTAMP('2022-08-24 15:35:20','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ab182','Y','N','N','N','N','N','N','N','Forwarder',TO_TIMESTAMP('2022-08-24 15:35:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T14:35:22.104Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705524 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-24T14:35:22.199Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581378) 
;

-- 2022-08-24T14:35:22.293Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705524
;

-- 2022-08-24T14:35:22.385Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705524)
;

-- Field: Business Partner -> Forwarder -> Business Partner
-- Column: C_BPartner_Forwarder.C_BPartner_ID
-- 2022-08-24T14:35:23.830Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584216,705525,0,546599,TO_TIMESTAMP('2022-08-24 15:35:22','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.ab182','','Y','N','N','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2022-08-24 15:35:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T14:35:23.928Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705525 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-24T14:35:24.024Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2022-08-24T14:35:24.133Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705525
;

-- 2022-08-24T14:35:24.224Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705525)
;

-- Field: Business Partner -> Forwarder -> Forwarder
-- Column: C_BPartner_Forwarder.Forwarder_ID
-- 2022-08-24T14:35:25.684Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584217,705526,0,546599,TO_TIMESTAMP('2022-08-24 15:35:24','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ab182','Y','N','N','N','N','N','N','N','Forwarder',TO_TIMESTAMP('2022-08-24 15:35:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T14:35:25.779Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705526 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-24T14:35:25.878Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581379) 
;

-- 2022-08-24T14:35:25.972Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705526
;

-- 2022-08-24T14:35:26.064Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705526)
;

-- Field: Business Partner -> Forwarder -> Default Forwarder
-- Column: C_BPartner_Forwarder.IsDefaultForwarder
-- 2022-08-24T14:35:27.596Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584218,705527,0,546599,TO_TIMESTAMP('2022-08-24 15:35:26','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.ab182','Y','N','N','N','N','N','N','N','Default Forwarder',TO_TIMESTAMP('2022-08-24 15:35:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T14:35:27.694Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705527 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-24T14:35:27.792Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581327) 
;

-- 2022-08-24T14:35:27.888Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705527
;

-- 2022-08-24T14:35:27.982Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705527)
;

-- 2022-08-24T14:36:36.965Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546599,545224,TO_TIMESTAMP('2022-08-24 15:36:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-24 15:36:35','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-08-24T14:36:37.062Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545224 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-08-24T14:36:51.695Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546350,545224,TO_TIMESTAMP('2022-08-24 15:36:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-08-24 15:36:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T14:37:31.485Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546350,549836,TO_TIMESTAMP('2022-08-24 15:37:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','Forwarder',10,'primary',TO_TIMESTAMP('2022-08-24 15:37:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner -> Forwarder.Forwarder
-- Column: C_BPartner_Forwarder.Forwarder_ID
-- 2022-08-24T14:39:27.040Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,705526,0,546599,549836,612201,'F',TO_TIMESTAMP('2022-08-24 15:39:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Forwarder',10,0,0,TO_TIMESTAMP('2022-08-24 15:39:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner -> Forwarder.Default Forwarder
-- Column: C_BPartner_Forwarder.IsDefaultForwarder
-- 2022-08-24T14:40:40.913Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,705527,0,546599,549836,612202,'F',TO_TIMESTAMP('2022-08-24 15:40:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Default Forwarder',20,0,0,TO_TIMESTAMP('2022-08-24 15:40:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner -> Forwarder.Default Forwarder
-- Column: C_BPartner_Forwarder.IsDefaultForwarder
-- 2022-08-24T14:41:24.686Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612202
;

-- 2022-08-24T14:41:44.599Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705527
;

-- Field: Business Partner -> Forwarder -> Default Forwarder
-- Column: C_BPartner_Forwarder.IsDefaultForwarder
-- 2022-08-24T14:41:44.878Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=705527
;

-- 2022-08-24T14:41:45.442Z
DELETE FROM AD_Field WHERE AD_Field_ID=705527
;

-- Column: C_BPartner_Forwarder.IsDefault
-- 2022-08-24T14:42:16.073Z
UPDATE AD_Column SET AD_Element_ID=1103, ColumnName='IsDefault', Description='Default value', Help='The Default Checkbox indicates if this record will be used as a default value.', Name='Default',Updated=TO_TIMESTAMP('2022-08-24 15:42:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584218
;

-- 2022-08-24T14:42:16.170Z
UPDATE AD_Field SET Name='Default', Description='Default value', Help='The Default Checkbox indicates if this record will be used as a default value.' WHERE AD_Column_ID=584218
;

-- 2022-08-24T14:42:16.265Z
/* DDL */  select update_Column_Translation_From_AD_Element(1103) 
;

-- 2022-08-24T14:42:38.813Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_Forwarder','ALTER TABLE public.C_BPartner_Forwarder ADD COLUMN IsDefault CHAR(1) DEFAULT ''N'' CHECK (IsDefault IN (''Y'',''N'')) NOT NULL')
;

/* DDL */ SELECT public.db_alter_table('C_BPartner_Forwarder','ALTER TABLE public.C_BPartner_Forwarder DROP COLUMN IsDefaultForwarder')
;
-- Field: Business Partner -> Forwarder -> Default
-- Column: C_BPartner_Forwarder.IsDefault
-- 2022-08-24T14:47:30.523Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584218,705528,0,546599,0,TO_TIMESTAMP('2022-08-24 15:47:28','YYYY-MM-DD HH24:MI:SS'),100,'Default value',0,'de.metas.ab182','The Default Checkbox indicates if this record will be used as a default value.',0,'Y','Y','Y','N','N','N','N','N','Default',0,10,0,1,1,TO_TIMESTAMP('2022-08-24 15:47:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T14:47:30.621Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705528 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-24T14:47:30.719Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1103) 
;

-- 2022-08-24T14:47:30.820Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705528
;

-- 2022-08-24T14:47:30.913Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705528)
;

-- UI Element: Business Partner -> Forwarder.Default
-- Column: C_BPartner_Forwarder.IsDefault
-- 2022-08-24T14:48:08.233Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,705528,0,546599,549836,612203,'F',TO_TIMESTAMP('2022-08-24 15:48:07','YYYY-MM-DD HH24:MI:SS'),100,'Default value','The Default Checkbox indicates if this record will be used as a default value.','Y','N','N','Y','N','N','N',0,'Default',20,0,0,TO_TIMESTAMP('2022-08-24 15:48:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Business Partner -> Forwarder
-- Table: C_BPartner_Forwarder
-- 2022-08-24T14:51:25.579Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2022-08-24 15:51:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546599
;

