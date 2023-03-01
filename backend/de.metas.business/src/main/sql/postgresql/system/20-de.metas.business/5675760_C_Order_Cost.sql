-- Table: C_Order_Cost
-- 2023-02-06T15:08:01.813Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('1',0,0,0,542296,'N',TO_TIMESTAMP('2023-02-06 17:08:01','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Order Cost','NP','L','C_Order_Cost','DTI',TO_TIMESTAMP('2023-02-06 17:08:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:08:01.820Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542296 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-02-06T15:08:01.940Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556207,TO_TIMESTAMP('2023-02-06 17:08:01','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Order_Cost',1,'Y','N','Y','Y','C_Order_Cost','N',1000000,TO_TIMESTAMP('2023-02-06 17:08:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:08:01.948Z
CREATE SEQUENCE C_ORDER_COST_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Order_Cost.AD_Client_ID
-- 2023-02-06T15:08:19.107Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585892,102,0,19,542296,'AD_Client_ID',TO_TIMESTAMP('2023-02-06 17:08:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','D',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Client',0,0,TO_TIMESTAMP('2023-02-06 17:08:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:08:19.110Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585892 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:08:19.117Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Order_Cost.AD_Org_ID
-- 2023-02-06T15:08:20.267Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585893,113,0,30,542296,'AD_Org_ID',TO_TIMESTAMP('2023-02-06 17:08:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','D',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-02-06 17:08:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:08:20.269Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585893 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:08:20.272Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Order_Cost.Created
-- 2023-02-06T15:08:21.106Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585894,245,0,16,542296,'Created',TO_TIMESTAMP('2023-02-06 17:08:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','D',0,29,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created',0,0,TO_TIMESTAMP('2023-02-06 17:08:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:08:21.108Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585894 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:08:21.113Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Order_Cost.CreatedBy
-- 2023-02-06T15:08:21.908Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585895,246,0,18,110,542296,'CreatedBy',TO_TIMESTAMP('2023-02-06 17:08:21','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','D',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created By',0,0,TO_TIMESTAMP('2023-02-06 17:08:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:08:21.910Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585895 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:08:21.913Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Order_Cost.IsActive
-- 2023-02-06T15:08:22.651Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585896,348,0,20,542296,'IsActive',TO_TIMESTAMP('2023-02-06 17:08:22','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','D',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Active',0,0,TO_TIMESTAMP('2023-02-06 17:08:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:08:22.653Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585896 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:08:22.655Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Order_Cost.Updated
-- 2023-02-06T15:08:23.432Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585897,607,0,16,542296,'Updated',TO_TIMESTAMP('2023-02-06 17:08:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','D',0,29,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated',0,0,TO_TIMESTAMP('2023-02-06 17:08:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:08:23.434Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585897 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:08:23.438Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Order_Cost.UpdatedBy
-- 2023-02-06T15:08:24.242Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585898,608,0,18,110,542296,'UpdatedBy',TO_TIMESTAMP('2023-02-06 17:08:23','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','D',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated By',0,0,TO_TIMESTAMP('2023-02-06 17:08:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:08:24.244Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585898 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:08:24.246Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-02-06T15:08:24.827Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582030,0,'C_Order_Cost_ID',TO_TIMESTAMP('2023-02-06 17:08:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Order Cost','Order Cost',TO_TIMESTAMP('2023-02-06 17:08:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:08:24.829Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582030 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Order_Cost.C_Order_Cost_ID
-- 2023-02-06T15:08:25.444Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585899,582030,0,13,542296,'C_Order_Cost_ID',TO_TIMESTAMP('2023-02-06 17:08:24','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Order Cost',0,0,TO_TIMESTAMP('2023-02-06 17:08:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:08:25.447Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585899 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:08:25.450Z
/* DDL */  select update_Column_Translation_From_AD_Element(582030) 
;

-- Column: C_Order_Cost.C_Cost_Type_ID
-- 2023-02-06T15:08:44.436Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585900,582023,0,30,542296,'C_Cost_Type_ID',TO_TIMESTAMP('2023-02-06 17:08:44','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cost Type',0,0,TO_TIMESTAMP('2023-02-06 17:08:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:08:44.438Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585900 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:08:44.441Z
/* DDL */  select update_Column_Translation_From_AD_Element(582023) 
;

-- Column: C_Order_Cost.CostCalculationMethod
-- 2023-02-06T15:09:04.398Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585901,582025,0,17,541713,542296,'CostCalculationMethod',TO_TIMESTAMP('2023-02-06 17:09:04','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Calculation Method',0,0,TO_TIMESTAMP('2023-02-06 17:09:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:09:04.399Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585901 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:09:04.402Z
/* DDL */  select update_Column_Translation_From_AD_Element(582025) 
;

-- Column: C_Order_Cost.CostDistributionMethod
-- 2023-02-06T15:09:29.554Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585902,582024,0,17,541712,542296,'CostDistributionMethod',TO_TIMESTAMP('2023-02-06 17:09:29','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Distribution',0,0,TO_TIMESTAMP('2023-02-06 17:09:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:09:29.556Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585902 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:09:29.559Z
/* DDL */  select update_Column_Translation_From_AD_Element(582024) 
;

-- 2023-02-06T15:10:18.476Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582031,0,'CostAmount',TO_TIMESTAMP('2023-02-06 17:10:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Cost Amount','Cost Amount',TO_TIMESTAMP('2023-02-06 17:10:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:10:18.477Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582031 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Order_Cost.CostAmount
-- 2023-02-06T15:10:25.078Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585903,582031,0,12,542296,'CostAmount',TO_TIMESTAMP('2023-02-06 17:10:24','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cost Amount',0,0,TO_TIMESTAMP('2023-02-06 17:10:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:10:25.080Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585903 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:10:25.083Z
/* DDL */  select update_Column_Translation_From_AD_Element(582031) 
;

-- Column: C_Order_Cost.C_Currency_ID
-- 2023-02-06T15:10:39.970Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585904,193,0,30,542296,'C_Currency_ID',TO_TIMESTAMP('2023-02-06 17:10:39','YYYY-MM-DD HH24:MI:SS'),100,'N','The Currency for this record','D',0,10,'Indicates the Currency to be used when processing or reporting on this record','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Currency',0,0,TO_TIMESTAMP('2023-02-06 17:10:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:10:39.972Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585904 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:10:39.974Z
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- Table: C_Order_Cost_Detail
-- 2023-02-06T15:13:35.888Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('1',0,0,0,542297,'N',TO_TIMESTAMP('2023-02-06 17:13:35','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','Y','Y','N','N','N','N','N',0,'Order Cost Detail','NP','L','C_Order_Cost_Detail','DTI',TO_TIMESTAMP('2023-02-06 17:13:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:13:35.889Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542297 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-02-06T15:13:35.996Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556208,TO_TIMESTAMP('2023-02-06 17:13:35','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Order_Cost_Detail',1,'Y','N','Y','Y','C_Order_Cost_Detail','N',1000000,TO_TIMESTAMP('2023-02-06 17:13:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:13:36.001Z
CREATE SEQUENCE C_ORDER_COST_DETAIL_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Order_Cost_Detail.AD_Client_ID
-- 2023-02-06T15:13:40.513Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585905,102,0,19,542297,'AD_Client_ID',TO_TIMESTAMP('2023-02-06 17:13:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','D',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Client',0,0,TO_TIMESTAMP('2023-02-06 17:13:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:13:40.515Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585905 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:13:40.518Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Order_Cost_Detail.AD_Org_ID
-- 2023-02-06T15:13:41.332Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585906,113,0,30,542297,'AD_Org_ID',TO_TIMESTAMP('2023-02-06 17:13:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','D',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-02-06 17:13:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:13:41.333Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585906 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:13:41.335Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Order_Cost_Detail.Created
-- 2023-02-06T15:13:42.065Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585907,245,0,16,542297,'Created',TO_TIMESTAMP('2023-02-06 17:13:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','D',0,29,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created',0,0,TO_TIMESTAMP('2023-02-06 17:13:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:13:42.067Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585907 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:13:42.070Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Order_Cost_Detail.CreatedBy
-- 2023-02-06T15:13:42.791Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585908,246,0,18,110,542297,'CreatedBy',TO_TIMESTAMP('2023-02-06 17:13:42','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','D',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created By',0,0,TO_TIMESTAMP('2023-02-06 17:13:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:13:42.792Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585908 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:13:42.795Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Order_Cost_Detail.IsActive
-- 2023-02-06T15:13:43.502Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585909,348,0,20,542297,'IsActive',TO_TIMESTAMP('2023-02-06 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','D',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Active',0,0,TO_TIMESTAMP('2023-02-06 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:13:43.503Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585909 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:13:43.506Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Order_Cost_Detail.Updated
-- 2023-02-06T15:13:44.230Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585910,607,0,16,542297,'Updated',TO_TIMESTAMP('2023-02-06 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','D',0,29,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated',0,0,TO_TIMESTAMP('2023-02-06 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:13:44.232Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585910 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:13:44.235Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Order_Cost_Detail.UpdatedBy
-- 2023-02-06T15:13:44.961Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585911,608,0,18,110,542297,'UpdatedBy',TO_TIMESTAMP('2023-02-06 17:13:44','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','D',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated By',0,0,TO_TIMESTAMP('2023-02-06 17:13:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:13:44.963Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585911 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:13:44.965Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-02-06T15:13:45.515Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582032,0,'C_Order_Cost_Detail_ID',TO_TIMESTAMP('2023-02-06 17:13:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Order Cost Detail','Order Cost Detail',TO_TIMESTAMP('2023-02-06 17:13:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:13:45.517Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582032 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Order_Cost_Detail.C_Order_Cost_Detail_ID
-- 2023-02-06T15:13:46.152Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585912,582032,0,13,542297,'C_Order_Cost_Detail_ID',TO_TIMESTAMP('2023-02-06 17:13:45','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Order Cost Detail',0,0,TO_TIMESTAMP('2023-02-06 17:13:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:13:46.154Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585912 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:13:46.157Z
/* DDL */  select update_Column_Translation_From_AD_Element(582032) 
;

-- Column: C_Order_Cost_Detail.C_OrderLine_ID
-- 2023-02-06T15:17:43.709Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585913,561,0,30,542297,'C_OrderLine_ID',TO_TIMESTAMP('2023-02-06 17:17:43','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Orderline',0,0,TO_TIMESTAMP('2023-02-06 17:17:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:17:43.711Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585913 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:17:43.714Z
/* DDL */  select update_Column_Translation_From_AD_Element(561) 
;

-- Column: C_Order_Cost.C_Order_ID
-- 2023-02-06T15:18:31.221Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585914,558,0,30,542296,'C_Order_ID',TO_TIMESTAMP('2023-02-06 17:18:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Order','D',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Sales order',0,0,TO_TIMESTAMP('2023-02-06 17:18:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:18:31.223Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585914 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:18:31.227Z
/* DDL */  select update_Column_Translation_From_AD_Element(558) 
;

-- Column: C_Order_Cost.C_Order_ID
-- 2023-02-06T15:18:51.336Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2023-02-06 17:18:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585914
;

-- Column: C_Order_Cost.C_Cost_Type_ID
-- 2023-02-06T15:19:00.768Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2023-02-06 17:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585900
;

-- Column: C_Order_Cost.C_Order_ID
-- 2023-02-06T15:19:12.929Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-02-06 17:19:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585914
;

-- Column: C_Order_Cost.C_Cost_Type_ID
-- 2023-02-06T15:19:28.750Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-06 17:19:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585900
;

-- Column: C_Order_Cost_Detail.C_Order_Cost_ID
-- 2023-02-06T15:19:53.775Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585915,582030,0,30,542297,'C_Order_Cost_ID',TO_TIMESTAMP('2023-02-06 17:19:53','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Order Cost',0,0,TO_TIMESTAMP('2023-02-06 17:19:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:19:53.776Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585915 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:19:53.779Z
/* DDL */  select update_Column_Translation_From_AD_Element(582030) 
;

-- Column: C_Order_Cost_Detail.M_Product_ID
-- 2023-02-06T15:20:40.276Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585916,454,0,30,542297,'M_Product_ID',TO_TIMESTAMP('2023-02-06 17:20:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Product, Service, Item','D',0,10,'Identifies an item which is either purchased or sold in this organization.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Product',0,0,TO_TIMESTAMP('2023-02-06 17:20:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:20:40.278Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585916 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:20:40.281Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- Column: C_Order_Cost_Detail.C_Currency_ID
-- 2023-02-06T15:20:56.319Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585917,193,0,30,542297,'C_Currency_ID',TO_TIMESTAMP('2023-02-06 17:20:56','YYYY-MM-DD HH24:MI:SS'),100,'N','The Currency for this record','D',0,10,'Indicates the Currency to be used when processing or reporting on this record','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Currency',0,0,TO_TIMESTAMP('2023-02-06 17:20:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:20:56.320Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585917 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:20:56.323Z
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- Column: C_Order_Cost_Detail.LineNetAmt
-- 2023-02-06T15:21:14.515Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585918,441,0,12,542297,'LineNetAmt',TO_TIMESTAMP('2023-02-06 17:21:14','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,10,'Indicates the extended line amount based on the quantity and the actual price.  Any additional charges or freight are not included.  The Amount may or may not include tax.  If the price list is inclusive tax, the line amount is the same as the line total.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Line Net Amount',0,0,TO_TIMESTAMP('2023-02-06 17:21:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:21:14.516Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585918 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:21:14.520Z
/* DDL */  select update_Column_Translation_From_AD_Element(441) 
;

-- Column: C_Order_Cost_Detail.C_UOM_ID
-- 2023-02-06T15:21:33.223Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585919,215,0,30,542297,'C_UOM_ID',TO_TIMESTAMP('2023-02-06 17:21:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Unit of Measure','D',0,10,'The UOM defines a unique non monetary Unit of Measure','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'UOM',0,0,TO_TIMESTAMP('2023-02-06 17:21:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:21:33.225Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585919 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:21:33.228Z
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- Column: C_Order_Cost_Detail.QtyOrdered
-- 2023-02-06T15:22:07.751Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585920,531,0,29,542297,'QtyOrdered',TO_TIMESTAMP('2023-02-06 17:22:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Qty Ordered','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Qty Ordered',0,0,TO_TIMESTAMP('2023-02-06 17:22:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T15:22:07.752Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585920 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T15:22:07.754Z
/* DDL */  select update_Column_Translation_From_AD_Element(531) 
;

-- Table: C_Order_Cost_Detail
-- 2023-02-06T15:23:22.725Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-02-06 17:23:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542297
;

-- Table: C_Order_Cost
-- 2023-02-06T15:23:25.036Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-02-06 17:23:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542296
;

-- Table: C_Order_Cost_Detail
-- 2023-02-06T15:23:28.571Z
UPDATE AD_Table SET IsChangeLog='N',Updated=TO_TIMESTAMP('2023-02-06 17:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542297
;

-- 2023-02-06T15:23:37.754Z
/* DDL */ CREATE TABLE public.C_Order_Cost (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Cost_Type_ID NUMERIC(10) NOT NULL, C_Currency_ID NUMERIC(10) NOT NULL, C_Order_Cost_ID NUMERIC(10) NOT NULL, C_Order_ID NUMERIC(10) NOT NULL, CostAmount NUMERIC NOT NULL, CostCalculationMethod CHAR(1) NOT NULL, CostDistributionMethod CHAR(1) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CCostType_COrderCost FOREIGN KEY (C_Cost_Type_ID) REFERENCES public.C_Cost_Type DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CCurrency_COrderCost FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Order_Cost_Key PRIMARY KEY (C_Order_Cost_ID), CONSTRAINT COrder_COrderCost FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED)
;

-- 2023-02-06T15:23:43.731Z
/* DDL */ CREATE TABLE public.C_Order_Cost_Detail (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Currency_ID NUMERIC(10) NOT NULL, C_Order_Cost_Detail_ID NUMERIC(10) NOT NULL, C_Order_Cost_ID NUMERIC(10) NOT NULL, C_OrderLine_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, C_UOM_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, LineNetAmt NUMERIC NOT NULL, M_Product_ID NUMERIC(10) NOT NULL, QtyOrdered NUMERIC NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CCurrency_COrderCostDetail FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Order_Cost_Detail_Key PRIMARY KEY (C_Order_Cost_Detail_ID), CONSTRAINT COrderCost_COrderCostDetail FOREIGN KEY (C_Order_Cost_ID) REFERENCES public.C_Order_Cost DEFERRABLE INITIALLY DEFERRED, CONSTRAINT COrderLine_COrderCostDetail FOREIGN KEY (C_OrderLine_ID) REFERENCES public.C_OrderLine DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CUOM_COrderCostDetail FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MProduct_COrderCostDetail FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED)
;

-- Window: Order Cost, InternalName=null
-- 2023-02-06T15:24:10.042Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582030,0,541676,TO_TIMESTAMP('2023-02-06 17:24:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Order Cost','N',TO_TIMESTAMP('2023-02-06 17:24:09','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-02-06T15:24:10.043Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541676 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-02-06T15:24:10.047Z
/* DDL */  select update_window_translation_from_ad_element(582030) 
;

-- 2023-02-06T15:24:10.049Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541676
;

-- 2023-02-06T15:24:10.051Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541676)
;

-- Window: Order Cost, InternalName=orderCosts
-- 2023-02-06T15:24:20.134Z
UPDATE AD_Window SET InternalName='orderCosts',Updated=TO_TIMESTAMP('2023-02-06 17:24:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541676
;

-- Tab: Order Cost(541676,D) -> Order Cost
-- Table: C_Order_Cost
-- 2023-02-06T15:24:47.356Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582030,0,546808,542296,541676,'Y',TO_TIMESTAMP('2023-02-06 17:24:47','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Order_Cost','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Order Cost','N',10,0,TO_TIMESTAMP('2023-02-06 17:24:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:24:47.358Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546808 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-06T15:24:47.361Z
/* DDL */  select update_tab_translation_from_ad_element(582030) 
;

-- 2023-02-06T15:24:47.380Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546808)
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Client
-- Column: C_Order_Cost.AD_Client_ID
-- 2023-02-06T15:24:52.521Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585892,712189,0,546808,TO_TIMESTAMP('2023-02-06 17:24:52','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-02-06 17:24:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:24:52.526Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712189 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:24:52.530Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-06T15:24:53.730Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712189
;

-- 2023-02-06T15:24:53.733Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712189)
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Organisation
-- Column: C_Order_Cost.AD_Org_ID
-- 2023-02-06T15:24:53.849Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585893,712190,0,546808,TO_TIMESTAMP('2023-02-06 17:24:53','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-02-06 17:24:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:24:53.850Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712190 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:24:53.851Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-06T15:24:54.348Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712190
;

-- 2023-02-06T15:24:54.350Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712190)
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Active
-- Column: C_Order_Cost.IsActive
-- 2023-02-06T15:24:54.481Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585896,712191,0,546808,TO_TIMESTAMP('2023-02-06 17:24:54','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-02-06 17:24:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:24:54.483Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712191 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:24:54.485Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-06T15:24:54.998Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712191
;

-- 2023-02-06T15:24:55Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712191)
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Order Cost
-- Column: C_Order_Cost.C_Order_Cost_ID
-- 2023-02-06T15:24:55.108Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585899,712192,0,546808,TO_TIMESTAMP('2023-02-06 17:24:55','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Order Cost',TO_TIMESTAMP('2023-02-06 17:24:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:24:55.110Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712192 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:24:55.111Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582030) 
;

-- 2023-02-06T15:24:55.116Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712192
;

-- 2023-02-06T15:24:55.117Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712192)
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Cost Type
-- Column: C_Order_Cost.C_Cost_Type_ID
-- 2023-02-06T15:24:55.228Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585900,712193,0,546808,TO_TIMESTAMP('2023-02-06 17:24:55','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Type',TO_TIMESTAMP('2023-02-06 17:24:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:24:55.230Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712193 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:24:55.231Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582023) 
;

-- 2023-02-06T15:24:55.234Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712193
;

-- 2023-02-06T15:24:55.236Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712193)
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Calculation Method
-- Column: C_Order_Cost.CostCalculationMethod
-- 2023-02-06T15:24:55.352Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585901,712194,0,546808,TO_TIMESTAMP('2023-02-06 17:24:55','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Calculation Method',TO_TIMESTAMP('2023-02-06 17:24:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:24:55.353Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712194 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:24:55.354Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582025) 
;

-- 2023-02-06T15:24:55.361Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712194
;

-- 2023-02-06T15:24:55.361Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712194)
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Distribution
-- Column: C_Order_Cost.CostDistributionMethod
-- 2023-02-06T15:24:55.464Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585902,712195,0,546808,TO_TIMESTAMP('2023-02-06 17:24:55','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Distribution',TO_TIMESTAMP('2023-02-06 17:24:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:24:55.466Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712195 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:24:55.468Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582024) 
;

-- 2023-02-06T15:24:55.470Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712195
;

-- 2023-02-06T15:24:55.471Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712195)
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Cost Amount
-- Column: C_Order_Cost.CostAmount
-- 2023-02-06T15:24:55.568Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585903,712196,0,546808,TO_TIMESTAMP('2023-02-06 17:24:55','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Amount',TO_TIMESTAMP('2023-02-06 17:24:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:24:55.570Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712196 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:24:55.572Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582031) 
;

-- 2023-02-06T15:24:55.575Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712196
;

-- 2023-02-06T15:24:55.576Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712196)
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Currency
-- Column: C_Order_Cost.C_Currency_ID
-- 2023-02-06T15:24:55.680Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585904,712197,0,546808,TO_TIMESTAMP('2023-02-06 17:24:55','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record',10,'D','Indicates the Currency to be used when processing or reporting on this record','Y','N','N','N','N','N','N','N','Currency',TO_TIMESTAMP('2023-02-06 17:24:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:24:55.681Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712197 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:24:55.683Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-02-06T15:24:55.756Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712197
;

-- 2023-02-06T15:24:55.759Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712197)
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Sales order
-- Column: C_Order_Cost.C_Order_ID
-- 2023-02-06T15:24:55.858Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585914,712198,0,546808,TO_TIMESTAMP('2023-02-06 17:24:55','YYYY-MM-DD HH24:MI:SS'),100,'Order',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Sales order',TO_TIMESTAMP('2023-02-06 17:24:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:24:55.860Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712198 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:24:55.861Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2023-02-06T15:24:55.908Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712198
;

-- 2023-02-06T15:24:55.910Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712198)
;

-- Tab: Order Cost(541676,D) -> Order Cost Detail
-- Table: C_Order_Cost_Detail
-- 2023-02-06T15:25:22.344Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582032,0,546809,542297,541676,'Y',TO_TIMESTAMP('2023-02-06 17:25:22','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Order_Cost_Detail','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Order Cost Detail','N',20,1,TO_TIMESTAMP('2023-02-06 17:25:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:25:22.346Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546809 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-06T15:25:22.349Z
/* DDL */  select update_tab_translation_from_ad_element(582032) 
;

-- 2023-02-06T15:25:22.353Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546809)
;

-- Field: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> Client
-- Column: C_Order_Cost_Detail.AD_Client_ID
-- 2023-02-06T15:25:25.055Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585905,712199,0,546809,TO_TIMESTAMP('2023-02-06 17:25:24','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-02-06 17:25:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:25:25.056Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712199 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:25:25.058Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-06T15:25:25.437Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712199
;

-- 2023-02-06T15:25:25.438Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712199)
;

-- Field: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> Organisation
-- Column: C_Order_Cost_Detail.AD_Org_ID
-- 2023-02-06T15:25:25.551Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585906,712200,0,546809,TO_TIMESTAMP('2023-02-06 17:25:25','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-02-06 17:25:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:25:25.553Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712200 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:25:25.554Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-06T15:25:25.811Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712200
;

-- 2023-02-06T15:25:25.813Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712200)
;

-- Field: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> Active
-- Column: C_Order_Cost_Detail.IsActive
-- 2023-02-06T15:25:25.924Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585909,712201,0,546809,TO_TIMESTAMP('2023-02-06 17:25:25','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-02-06 17:25:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:25:25.925Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712201 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:25:25.926Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-06T15:25:26.217Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712201
;

-- 2023-02-06T15:25:26.218Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712201)
;

-- Field: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> Order Cost Detail
-- Column: C_Order_Cost_Detail.C_Order_Cost_Detail_ID
-- 2023-02-06T15:25:26.329Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585912,712202,0,546809,TO_TIMESTAMP('2023-02-06 17:25:26','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Order Cost Detail',TO_TIMESTAMP('2023-02-06 17:25:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:25:26.330Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712202 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:25:26.332Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582032) 
;

-- 2023-02-06T15:25:26.335Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712202
;

-- 2023-02-06T15:25:26.335Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712202)
;

-- Field: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> Orderline
-- Column: C_Order_Cost_Detail.C_OrderLine_ID
-- 2023-02-06T15:25:26.448Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585913,712203,0,546809,TO_TIMESTAMP('2023-02-06 17:25:26','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Orderline',TO_TIMESTAMP('2023-02-06 17:25:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:25:26.450Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712203 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:25:26.451Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(561) 
;

-- 2023-02-06T15:25:26.462Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712203
;

-- 2023-02-06T15:25:26.463Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712203)
;

-- Field: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> Order Cost
-- Column: C_Order_Cost_Detail.C_Order_Cost_ID
-- 2023-02-06T15:25:26.563Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585915,712204,0,546809,TO_TIMESTAMP('2023-02-06 17:25:26','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Order Cost',TO_TIMESTAMP('2023-02-06 17:25:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:25:26.564Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712204 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:25:26.565Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582030) 
;

-- 2023-02-06T15:25:26.571Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712204
;

-- 2023-02-06T15:25:26.572Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712204)
;

-- Field: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> Product
-- Column: C_Order_Cost_Detail.M_Product_ID
-- 2023-02-06T15:25:26.685Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585916,712205,0,546809,TO_TIMESTAMP('2023-02-06 17:25:26','YYYY-MM-DD HH24:MI:SS'),100,'Product, Service, Item',10,'D','Identifies an item which is either purchased or sold in this organization.','Y','N','N','N','N','N','N','N','Product',TO_TIMESTAMP('2023-02-06 17:25:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:25:26.686Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712205 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:25:26.688Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2023-02-06T15:25:26.790Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712205
;

-- 2023-02-06T15:25:26.792Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712205)
;

-- Field: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> Currency
-- Column: C_Order_Cost_Detail.C_Currency_ID
-- 2023-02-06T15:25:26.911Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585917,712206,0,546809,TO_TIMESTAMP('2023-02-06 17:25:26','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record',10,'D','Indicates the Currency to be used when processing or reporting on this record','Y','N','N','N','N','N','N','N','Currency',TO_TIMESTAMP('2023-02-06 17:25:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:25:26.913Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712206 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:25:26.914Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-02-06T15:25:26.935Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712206
;

-- 2023-02-06T15:25:26.937Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712206)
;

-- Field: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> Line Net Amount
-- Column: C_Order_Cost_Detail.LineNetAmt
-- 2023-02-06T15:25:27.048Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585918,712207,0,546809,TO_TIMESTAMP('2023-02-06 17:25:26','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','Indicates the extended line amount based on the quantity and the actual price.  Any additional charges or freight are not included.  The Amount may or may not include tax.  If the price list is inclusive tax, the line amount is the same as the line total.','Y','N','N','N','N','N','N','N','Line Net Amount',TO_TIMESTAMP('2023-02-06 17:25:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:25:27.050Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712207 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:25:27.052Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(441) 
;

-- 2023-02-06T15:25:27.059Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712207
;

-- 2023-02-06T15:25:27.059Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712207)
;

-- Field: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> UOM
-- Column: C_Order_Cost_Detail.C_UOM_ID
-- 2023-02-06T15:25:27.155Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585919,712208,0,546809,TO_TIMESTAMP('2023-02-06 17:25:27','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Measure',10,'D','The UOM defines a unique non monetary Unit of Measure','Y','N','N','N','N','N','N','N','UOM',TO_TIMESTAMP('2023-02-06 17:25:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:25:27.156Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712208 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:25:27.158Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2023-02-06T15:25:27.206Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712208
;

-- 2023-02-06T15:25:27.208Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712208)
;

-- Field: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> Qty Ordered
-- Column: C_Order_Cost_Detail.QtyOrdered
-- 2023-02-06T15:25:27.327Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585920,712209,0,546809,TO_TIMESTAMP('2023-02-06 17:25:27','YYYY-MM-DD HH24:MI:SS'),100,'Qty Ordered',10,'D','','Y','N','N','N','N','N','N','N','Qty Ordered',TO_TIMESTAMP('2023-02-06 17:25:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T15:25:27.329Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712209 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T15:25:27.331Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(531) 
;

-- 2023-02-06T15:25:27.338Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712209
;

-- 2023-02-06T15:25:27.338Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712209)
;

-- Tab: Order Cost(541676,D) -> Order Cost Detail
-- Table: C_Order_Cost_Detail
-- 2023-02-06T15:26:27.451Z
UPDATE AD_Tab SET Parent_Column_ID=585899,Updated=TO_TIMESTAMP('2023-02-06 17:26:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546809
;

-- Tab: Order Cost(541676,D) -> Order Cost Detail
-- Table: C_Order_Cost_Detail
-- 2023-02-06T15:26:40.350Z
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2023-02-06 17:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546809
;

-- Tab: Order Cost(541676,D) -> Order Cost Detail
-- Table: C_Order_Cost_Detail
-- 2023-02-06T15:26:49.786Z
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-02-06 17:26:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546809
;

-- Tab: Order Cost(541676,D) -> Order Cost(546808,D)
-- UI Section: main
-- 2023-02-06T15:27:31.070Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546808,545435,TO_TIMESTAMP('2023-02-06 17:27:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-06 17:27:30','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-06T15:27:31.072Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545435 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Order Cost(541676,D) -> Order Cost(546808,D) -> main
-- UI Column: 10
-- 2023-02-06T15:27:35.098Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546632,545435,TO_TIMESTAMP('2023-02-06 17:27:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-06 17:27:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Order Cost(541676,D) -> Order Cost(546808,D) -> main
-- UI Column: 20
-- 2023-02-06T15:27:37.252Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546633,545435,TO_TIMESTAMP('2023-02-06 17:27:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-02-06 17:27:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10
-- UI Element Group: primary
-- 2023-02-06T15:27:49.884Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546632,550345,TO_TIMESTAMP('2023-02-06 17:27:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','primary',10,'primary',TO_TIMESTAMP('2023-02-06 17:27:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> primary.Sales order
-- Column: C_Order_Cost.C_Order_ID
-- 2023-02-06T15:28:04.979Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712198,0,546808,550345,615592,'F',TO_TIMESTAMP('2023-02-06 17:28:04','YYYY-MM-DD HH24:MI:SS'),100,'Order','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','Sales order',10,0,0,TO_TIMESTAMP('2023-02-06 17:28:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> primary.Cost Type
-- Column: C_Order_Cost.C_Cost_Type_ID
-- 2023-02-06T15:28:11.117Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712193,0,546808,550345,615593,'F',TO_TIMESTAMP('2023-02-06 17:28:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cost Type',20,0,0,TO_TIMESTAMP('2023-02-06 17:28:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> primary.Calculation Method
-- Column: C_Order_Cost.CostCalculationMethod
-- 2023-02-06T15:28:20.858Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712194,0,546808,550345,615594,'F',TO_TIMESTAMP('2023-02-06 17:28:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Calculation Method',30,0,0,TO_TIMESTAMP('2023-02-06 17:28:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> primary.Distribution
-- Column: C_Order_Cost.CostDistributionMethod
-- 2023-02-06T15:28:27.182Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712195,0,546808,550345,615595,'F',TO_TIMESTAMP('2023-02-06 17:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Distribution',40,0,0,TO_TIMESTAMP('2023-02-06 17:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10
-- UI Element Group: amounts
-- 2023-02-06T15:28:44.306Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546632,550346,TO_TIMESTAMP('2023-02-06 17:28:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','amounts',20,TO_TIMESTAMP('2023-02-06 17:28:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> amounts.Cost Amount
-- Column: C_Order_Cost.CostAmount
-- 2023-02-06T15:28:55.871Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712196,0,546808,550346,615596,'F',TO_TIMESTAMP('2023-02-06 17:28:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cost Amount',10,0,0,TO_TIMESTAMP('2023-02-06 17:28:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> amounts.Currency
-- Column: C_Order_Cost.C_Currency_ID
-- 2023-02-06T15:29:01.736Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712197,0,546808,550346,615597,'F',TO_TIMESTAMP('2023-02-06 17:29:01','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record','Indicates the Currency to be used when processing or reporting on this record','Y','N','Y','N','N','Currency',20,0,0,TO_TIMESTAMP('2023-02-06 17:29:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 20
-- UI Element Group: org and client
-- 2023-02-06T15:29:12.467Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546633,550347,TO_TIMESTAMP('2023-02-06 17:29:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','org and client',10,TO_TIMESTAMP('2023-02-06 17:29:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 20 -> org and client.Organisation
-- Column: C_Order_Cost.AD_Org_ID
-- 2023-02-06T15:29:21.886Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712190,0,546808,550347,615598,'F',TO_TIMESTAMP('2023-02-06 17:29:21','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','Y','N','N','Organisation',10,0,0,TO_TIMESTAMP('2023-02-06 17:29:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 20 -> org and client.Client
-- Column: C_Order_Cost.AD_Client_ID
-- 2023-02-06T15:29:28.194Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712189,0,546808,550347,615599,'F',TO_TIMESTAMP('2023-02-06 17:29:28','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','Y','N','N','Client',20,0,0,TO_TIMESTAMP('2023-02-06 17:29:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Order Cost(541676,D) -> Order Cost Detail
-- Table: C_Order_Cost_Detail
-- 2023-02-06T15:39:02.009Z
UPDATE AD_Tab SET AD_Column_ID=585915,Updated=TO_TIMESTAMP('2023-02-06 17:39:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546809
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> primary.Sales order
-- Column: C_Order_Cost.C_Order_ID
-- 2023-02-06T15:39:42.396Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-06 17:39:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615592
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> primary.Cost Type
-- Column: C_Order_Cost.C_Cost_Type_ID
-- 2023-02-06T15:39:42.403Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-06 17:39:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615593
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> amounts.Currency
-- Column: C_Order_Cost.C_Currency_ID
-- 2023-02-06T15:39:42.409Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-06 17:39:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615597
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> amounts.Cost Amount
-- Column: C_Order_Cost.CostAmount
-- 2023-02-06T15:39:42.417Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-06 17:39:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615596
;

-- Tab: Order Cost(541676,D) -> Order Cost Detail(546809,D)
-- UI Section: main
-- 2023-02-06T15:39:54.332Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546809,545436,TO_TIMESTAMP('2023-02-06 17:39:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-06 17:39:54','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-06T15:39:54.333Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545436 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main
-- UI Column: 10
-- 2023-02-06T15:39:58.225Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546634,545436,TO_TIMESTAMP('2023-02-06 17:39:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-06 17:39:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10
-- UI Element Group: primary
-- 2023-02-06T15:40:08.667Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546634,550348,TO_TIMESTAMP('2023-02-06 17:40:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','primary',10,TO_TIMESTAMP('2023-02-06 17:40:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Orderline
-- Column: C_Order_Cost_Detail.C_OrderLine_ID
-- 2023-02-06T15:40:35.787Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712203,0,546809,550348,615600,'F',TO_TIMESTAMP('2023-02-06 17:40:35','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Orderline',10,0,0,TO_TIMESTAMP('2023-02-06 17:40:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Product
-- Column: C_Order_Cost_Detail.M_Product_ID
-- 2023-02-06T15:40:41.453Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712205,0,546809,550348,615601,'F',TO_TIMESTAMP('2023-02-06 17:40:41','YYYY-MM-DD HH24:MI:SS'),100,'Product, Service, Item','Identifies an item which is either purchased or sold in this organization.','Y','N','Y','N','N','Product',20,0,0,TO_TIMESTAMP('2023-02-06 17:40:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.UOM
-- Column: C_Order_Cost_Detail.C_UOM_ID
-- 2023-02-06T15:40:54.530Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712208,0,546809,550348,615602,'F',TO_TIMESTAMP('2023-02-06 17:40:54','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Measure','The UOM defines a unique non monetary Unit of Measure','Y','N','Y','N','N','UOM',30,0,0,TO_TIMESTAMP('2023-02-06 17:40:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Qty Ordered
-- Column: C_Order_Cost_Detail.QtyOrdered
-- 2023-02-06T15:41:00.621Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712209,0,546809,550348,615603,'F',TO_TIMESTAMP('2023-02-06 17:41:00','YYYY-MM-DD HH24:MI:SS'),100,'Qty Ordered','','Y','N','Y','N','N','Qty Ordered',40,0,0,TO_TIMESTAMP('2023-02-06 17:41:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Currency
-- Column: C_Order_Cost_Detail.C_Currency_ID
-- 2023-02-06T15:41:06.695Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712206,0,546809,550348,615604,'F',TO_TIMESTAMP('2023-02-06 17:41:06','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record','Indicates the Currency to be used when processing or reporting on this record','Y','N','Y','N','N','Currency',50,0,0,TO_TIMESTAMP('2023-02-06 17:41:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Line Net Amount
-- Column: C_Order_Cost_Detail.LineNetAmt
-- 2023-02-06T15:41:12.807Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712207,0,546809,550348,615605,'F',TO_TIMESTAMP('2023-02-06 17:41:12','YYYY-MM-DD HH24:MI:SS'),100,'','Indicates the extended line amount based on the quantity and the actual price.  Any additional charges or freight are not included.  The Amount may or may not include tax.  If the price list is inclusive tax, the line amount is the same as the line total.','Y','N','Y','N','N','Line Net Amount',60,0,0,TO_TIMESTAMP('2023-02-06 17:41:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Orderline
-- Column: C_Order_Cost_Detail.C_OrderLine_ID
-- 2023-02-06T15:41:36.290Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-06 17:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615600
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Product
-- Column: C_Order_Cost_Detail.M_Product_ID
-- 2023-02-06T15:41:36.297Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-06 17:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615601
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.UOM
-- Column: C_Order_Cost_Detail.C_UOM_ID
-- 2023-02-06T15:41:36.303Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-06 17:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615602
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Qty Ordered
-- Column: C_Order_Cost_Detail.QtyOrdered
-- 2023-02-06T15:41:36.309Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-06 17:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615603
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Currency
-- Column: C_Order_Cost_Detail.C_Currency_ID
-- 2023-02-06T15:41:36.314Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-06 17:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615604
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Line Net Amount
-- Column: C_Order_Cost_Detail.LineNetAmt
-- 2023-02-06T15:41:36.319Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-06 17:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615605
;

