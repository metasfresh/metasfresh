-- Table: C_Cost_Type
-- 2023-02-06T11:51:36.520Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542294,'N',TO_TIMESTAMP('2023-02-06 13:51:36','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Cost Type','NP','L','C_Cost_Type','DTI',TO_TIMESTAMP('2023-02-06 13:51:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T11:51:36.523Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542294 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-02-06T11:51:36.695Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556206,TO_TIMESTAMP('2023-02-06 13:51:36','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Cost_Type',1,'Y','N','Y','Y','C_Cost_Type','N',1000000,TO_TIMESTAMP('2023-02-06 13:51:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T11:51:36.706Z
CREATE SEQUENCE C_COST_TYPE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Cost_Type.AD_Client_ID
-- 2023-02-06T11:51:40.744Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585860,102,0,19,542294,'AD_Client_ID',TO_TIMESTAMP('2023-02-06 13:51:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','D',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Client',0,0,TO_TIMESTAMP('2023-02-06 13:51:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T11:51:40.746Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585860 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T11:51:40.780Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Cost_Type.AD_Org_ID
-- 2023-02-06T11:51:41.594Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585861,113,0,30,542294,'AD_Org_ID',TO_TIMESTAMP('2023-02-06 13:51:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','D',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-02-06 13:51:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T11:51:41.596Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585861 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T11:51:41.598Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Cost_Type.Created
-- 2023-02-06T11:51:42.304Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585862,245,0,16,542294,'Created',TO_TIMESTAMP('2023-02-06 13:51:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','D',0,29,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created',0,0,TO_TIMESTAMP('2023-02-06 13:51:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T11:51:42.306Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585862 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T11:51:42.309Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Cost_Type.CreatedBy
-- 2023-02-06T11:51:43.039Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585863,246,0,18,110,542294,'CreatedBy',TO_TIMESTAMP('2023-02-06 13:51:42','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','D',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created By',0,0,TO_TIMESTAMP('2023-02-06 13:51:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T11:51:43.040Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585863 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T11:51:43.044Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Cost_Type.IsActive
-- 2023-02-06T11:51:43.727Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585864,348,0,20,542294,'IsActive',TO_TIMESTAMP('2023-02-06 13:51:43','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','D',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Active',0,0,TO_TIMESTAMP('2023-02-06 13:51:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T11:51:43.729Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585864 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T11:51:43.731Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Cost_Type.Updated
-- 2023-02-06T11:51:44.441Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585865,607,0,16,542294,'Updated',TO_TIMESTAMP('2023-02-06 13:51:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','D',0,29,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated',0,0,TO_TIMESTAMP('2023-02-06 13:51:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T11:51:44.443Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585865 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T11:51:44.445Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Cost_Type.UpdatedBy
-- 2023-02-06T11:51:45.159Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585866,608,0,18,110,542294,'UpdatedBy',TO_TIMESTAMP('2023-02-06 13:51:44','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','D',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated By',0,0,TO_TIMESTAMP('2023-02-06 13:51:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T11:51:45.161Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585866 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T11:51:45.163Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-02-06T11:51:45.720Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582023,0,'C_Cost_Type_ID',TO_TIMESTAMP('2023-02-06 13:51:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Cost Type','Cost Type',TO_TIMESTAMP('2023-02-06 13:51:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T11:51:45.721Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582023 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Cost_Type.C_Cost_Type_ID
-- 2023-02-06T11:51:46.736Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585867,582023,0,13,542294,'C_Cost_Type_ID',TO_TIMESTAMP('2023-02-06 13:51:45','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Cost Type',0,0,TO_TIMESTAMP('2023-02-06 13:51:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T11:51:46.737Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585867 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T11:51:46.740Z
/* DDL */  select update_Column_Translation_From_AD_Element(582023) 
;

-- Column: C_Cost_Type.Value
-- 2023-02-06T11:52:39.924Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585868,620,0,10,542294,'Value',TO_TIMESTAMP('2023-02-06 13:52:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Search key for the record in the format required - must be unique','D',0,40,'E','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Search Key',0,0,TO_TIMESTAMP('2023-02-06 13:52:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T11:52:39.926Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585868 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T11:52:39.929Z
/* DDL */  select update_Column_Translation_From_AD_Element(620) 
;

-- Column: C_Cost_Type.Name
-- 2023-02-06T11:53:01.491Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585869,469,0,10,542294,'Name',TO_TIMESTAMP('2023-02-06 13:53:01','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,255,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2023-02-06 13:53:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T11:53:01.493Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585869 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T11:53:01.497Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- Column: C_Cost_Type.Description
-- 2023-02-06T11:53:21.088Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585870,275,0,36,542294,'Description',TO_TIMESTAMP('2023-02-06 13:53:19','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,2000,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Description',0,0,TO_TIMESTAMP('2023-02-06 13:53:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T11:53:21.090Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585870 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T11:53:21.093Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- 2023-02-06T11:55:23.128Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582024,0,'CostDistributionMethod',TO_TIMESTAMP('2023-02-06 13:55:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Distribution','Distribution',TO_TIMESTAMP('2023-02-06 13:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T11:55:23.130Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582024 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: CostDistributionMethod
-- 2023-02-06T11:56:00.932Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541712,TO_TIMESTAMP('2023-02-06 13:56:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','CostDistributionMethod',TO_TIMESTAMP('2023-02-06 13:56:00','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-02-06T11:56:00.934Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541712 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: CostDistributionMethod
-- Value: Q
-- ValueName: Quantity
-- 2023-02-06T11:56:14.967Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541712,543401,TO_TIMESTAMP('2023-02-06 13:56:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Quantity',TO_TIMESTAMP('2023-02-06 13:56:14','YYYY-MM-DD HH24:MI:SS'),100,'Q','Quantity')
;

-- 2023-02-06T11:56:14.970Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543401 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: CostDistributionMethod
-- Value: A
-- ValueName: Amount
-- 2023-02-06T11:56:24.254Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541712,543402,TO_TIMESTAMP('2023-02-06 13:56:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Amount',TO_TIMESTAMP('2023-02-06 13:56:24','YYYY-MM-DD HH24:MI:SS'),100,'A','Amount')
;

-- 2023-02-06T11:56:24.256Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543402 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_Cost_Type.CostDistributionMethod
-- 2023-02-06T11:56:40.284Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585871,582024,0,17,541712,542294,'CostDistributionMethod',TO_TIMESTAMP('2023-02-06 13:56:40','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Distribution',0,0,TO_TIMESTAMP('2023-02-06 13:56:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T11:56:40.286Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585871 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T11:56:40.288Z
/* DDL */  select update_Column_Translation_From_AD_Element(582024) 
;

-- 2023-02-06T11:58:05.819Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582025,0,'CostCalculationMethod',TO_TIMESTAMP('2023-02-06 13:58:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Calculation Method','Calculation Method',TO_TIMESTAMP('2023-02-06 13:58:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T11:58:05.822Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582025 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: CostCalculationMethod
-- 2023-02-06T11:58:50.181Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541713,TO_TIMESTAMP('2023-02-06 13:58:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','CostCalculationMethod',TO_TIMESTAMP('2023-02-06 13:58:50','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-02-06T11:58:50.183Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541713 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: CostCalculationMethod
-- Value: F
-- ValueName: FixedAmount
-- 2023-02-06T11:59:17.551Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541713,543403,TO_TIMESTAMP('2023-02-06 13:59:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fixed Amount',TO_TIMESTAMP('2023-02-06 13:59:17','YYYY-MM-DD HH24:MI:SS'),100,'F','FixedAmount')
;

-- 2023-02-06T11:59:17.552Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543403 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: CostCalculationMethod
-- Value: P
-- ValueName: PercentageOfAmount
-- 2023-02-06T11:59:52.364Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541713,543404,TO_TIMESTAMP('2023-02-06 13:59:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Percentage of amount',TO_TIMESTAMP('2023-02-06 13:59:52','YYYY-MM-DD HH24:MI:SS'),100,'P','PercentageOfAmount')
;

-- 2023-02-06T11:59:52.366Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543404 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_Cost_Type.CostCalculationMethod
-- 2023-02-06T12:00:10.315Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585872,582025,0,17,541713,542294,'CostCalculationMethod',TO_TIMESTAMP('2023-02-06 14:00:10','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Calculation Method',0,0,TO_TIMESTAMP('2023-02-06 14:00:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T12:00:10.317Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585872 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T12:00:10.320Z
/* DDL */  select update_Column_Translation_From_AD_Element(582025) 
;

-- Element: null
-- 2023-02-06T12:03:44.917Z
UPDATE AD_Element_Trl SET Name='Cost Type (accounting)',Updated=TO_TIMESTAMP('2023-02-06 14:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574067 AND AD_Language='en_US'
;

-- 2023-02-06T12:03:44.919Z
UPDATE AD_Element SET Name='Cost Type (accounting)' WHERE AD_Element_ID=574067
;

-- 2023-02-06T12:03:45.351Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(574067,'en_US') 
;

-- 2023-02-06T12:03:45.355Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574067,'en_US') 
;

-- Element: null
-- 2023-02-06T12:04:10.985Z
UPDATE AD_Element_Trl SET Name='Kostenkategorie (Buchführung)', PrintName='Kostenkategorie (Buchführung)',Updated=TO_TIMESTAMP('2023-02-06 14:04:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574067 AND AD_Language='de_CH'
;

-- 2023-02-06T12:04:10.988Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574067,'de_CH') 
;

-- Element: null
-- 2023-02-06T12:04:17.322Z
UPDATE AD_Element_Trl SET PrintName='Kostenkategorie',Updated=TO_TIMESTAMP('2023-02-06 14:04:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574067 AND AD_Language='de_CH'
;

-- 2023-02-06T12:04:17.326Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574067,'de_CH') 
;

-- Element: null
-- 2023-02-06T12:04:22.267Z
UPDATE AD_Element_Trl SET Name='Kostenkategorie (Buchführung)',Updated=TO_TIMESTAMP('2023-02-06 14:04:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574067 AND AD_Language='nl_NL'
;

-- 2023-02-06T12:04:22.271Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574067,'nl_NL') 
;

-- Element: null
-- 2023-02-06T12:04:25.200Z
UPDATE AD_Element_Trl SET Name='Kostenkategorie (Buchführung)',Updated=TO_TIMESTAMP('2023-02-06 14:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574067 AND AD_Language='de_DE'
;

-- 2023-02-06T12:04:25.205Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574067,'de_DE') 
;

-- Element: null
-- 2023-02-06T12:04:31.635Z
UPDATE AD_Element_Trl SET Name='Cost Type (accounting)',Updated=TO_TIMESTAMP('2023-02-06 14:04:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574067 AND AD_Language='fr_CH'
;

-- 2023-02-06T12:04:31.638Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574067,'fr_CH') 
;

-- Window: Cost Type, InternalName=null
-- 2023-02-06T12:04:57.368Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582023,0,541675,TO_TIMESTAMP('2023-02-06 14:04:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Cost Type','N',TO_TIMESTAMP('2023-02-06 14:04:57','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-02-06T12:04:57.372Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541675 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-02-06T12:04:57.377Z
/* DDL */  select update_window_translation_from_ad_element(582023) 
;

-- 2023-02-06T12:04:57.395Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541675
;

-- 2023-02-06T12:04:57.398Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541675)
;

-- Tab: Cost Type(541675,D) -> Cost Type
-- Table: C_Cost_Type
-- 2023-02-06T12:05:23.147Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582023,0,546807,542294,541675,'Y',TO_TIMESTAMP('2023-02-06 14:05:22','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Cost_Type','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Cost Type','N',10,0,TO_TIMESTAMP('2023-02-06 14:05:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T12:05:23.149Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546807 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-06T12:05:23.151Z
/* DDL */  select update_tab_translation_from_ad_element(582023) 
;

-- 2023-02-06T12:05:23.156Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546807)
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Client
-- Column: C_Cost_Type.AD_Client_ID
-- 2023-02-06T12:05:27.447Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585860,712180,0,546807,TO_TIMESTAMP('2023-02-06 14:05:27','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-02-06 14:05:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T12:05:27.450Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712180 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T12:05:27.452Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-06T12:05:27.728Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712180
;

-- 2023-02-06T12:05:27.730Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712180)
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Organisation
-- Column: C_Cost_Type.AD_Org_ID
-- 2023-02-06T12:05:27.849Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585861,712181,0,546807,TO_TIMESTAMP('2023-02-06 14:05:27','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-02-06 14:05:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T12:05:27.851Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712181 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T12:05:27.853Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-06T12:05:28.040Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712181
;

-- 2023-02-06T12:05:28.042Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712181)
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Active
-- Column: C_Cost_Type.IsActive
-- 2023-02-06T12:05:28.173Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585864,712182,0,546807,TO_TIMESTAMP('2023-02-06 14:05:28','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-02-06 14:05:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T12:05:28.175Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712182 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T12:05:28.177Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-06T12:05:28.397Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712182
;

-- 2023-02-06T12:05:28.408Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712182)
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Cost Type
-- Column: C_Cost_Type.C_Cost_Type_ID
-- 2023-02-06T12:05:28.558Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585867,712183,0,546807,TO_TIMESTAMP('2023-02-06 14:05:28','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Type',TO_TIMESTAMP('2023-02-06 14:05:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T12:05:28.560Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712183 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T12:05:28.562Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582023) 
;

-- 2023-02-06T12:05:28.566Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712183
;

-- 2023-02-06T12:05:28.568Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712183)
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Search Key
-- Column: C_Cost_Type.Value
-- 2023-02-06T12:05:28.683Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585868,712184,0,546807,TO_TIMESTAMP('2023-02-06 14:05:28','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required - must be unique',40,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Search Key',TO_TIMESTAMP('2023-02-06 14:05:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T12:05:28.685Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712184 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T12:05:28.689Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2023-02-06T12:05:28.713Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712184
;

-- 2023-02-06T12:05:28.715Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712184)
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Name
-- Column: C_Cost_Type.Name
-- 2023-02-06T12:05:28.839Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585869,712185,0,546807,TO_TIMESTAMP('2023-02-06 14:05:28','YYYY-MM-DD HH24:MI:SS'),100,'',255,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-02-06 14:05:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T12:05:28.840Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712185 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T12:05:28.842Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2023-02-06T12:05:28.888Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712185
;

-- 2023-02-06T12:05:28.889Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712185)
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Description
-- Column: C_Cost_Type.Description
-- 2023-02-06T12:05:29.032Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585870,712186,0,546807,TO_TIMESTAMP('2023-02-06 14:05:28','YYYY-MM-DD HH24:MI:SS'),100,'',2000,'D','','Y','N','N','N','N','N','N','N','Description',TO_TIMESTAMP('2023-02-06 14:05:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T12:05:29.035Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712186 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T12:05:29.037Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2023-02-06T12:05:29.102Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712186
;

-- 2023-02-06T12:05:29.103Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712186)
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Distribution
-- Column: C_Cost_Type.CostDistributionMethod
-- 2023-02-06T12:05:29.228Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585871,712187,0,546807,TO_TIMESTAMP('2023-02-06 14:05:29','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Distribution',TO_TIMESTAMP('2023-02-06 14:05:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T12:05:29.229Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712187 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T12:05:29.231Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582024) 
;

-- 2023-02-06T12:05:29.234Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712187
;

-- 2023-02-06T12:05:29.235Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712187)
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Calculation Method
-- Column: C_Cost_Type.CostCalculationMethod
-- 2023-02-06T12:05:29.363Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585872,712188,0,546807,TO_TIMESTAMP('2023-02-06 14:05:29','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Calculation Method',TO_TIMESTAMP('2023-02-06 14:05:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T12:05:29.366Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712188 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-06T12:05:29.368Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582025) 
;

-- 2023-02-06T12:05:29.371Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712188
;

-- 2023-02-06T12:05:29.373Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712188)
;

-- Tab: Cost Type(541675,D) -> Cost Type(546807,D)
-- UI Section: main
-- 2023-02-06T12:05:52.653Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546807,545434,TO_TIMESTAMP('2023-02-06 14:05:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-06 14:05:51','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-06T12:05:52.654Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545434 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Cost Type(541675,D) -> Cost Type(546807,D) -> main
-- UI Column: 10
-- 2023-02-06T12:06:01.315Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546630,545434,TO_TIMESTAMP('2023-02-06 14:06:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-06 14:06:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Cost Type(541675,D) -> Cost Type(546807,D) -> main
-- UI Column: 20
-- 2023-02-06T12:06:03.767Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546631,545434,TO_TIMESTAMP('2023-02-06 14:06:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-02-06 14:06:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 10
-- UI Element Group: primary
-- 2023-02-06T12:06:19.914Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546630,550341,TO_TIMESTAMP('2023-02-06 14:06:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','primary',10,'primary',TO_TIMESTAMP('2023-02-06 14:06:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 10 -> primary.Search Key
-- Column: C_Cost_Type.Value
-- 2023-02-06T12:06:35.611Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712184,0,546807,550341,615584,'F',TO_TIMESTAMP('2023-02-06 14:06:35','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required - must be unique','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','Search Key',10,0,0,TO_TIMESTAMP('2023-02-06 14:06:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 10 -> primary.Name
-- Column: C_Cost_Type.Name
-- 2023-02-06T12:06:43.004Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712185,0,546807,550341,615585,'F',TO_TIMESTAMP('2023-02-06 14:06:42','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Name',20,0,0,TO_TIMESTAMP('2023-02-06 14:06:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 10 -> primary.Description
-- Column: C_Cost_Type.Description
-- 2023-02-06T12:07:12.708Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712186,0,546807,550341,615586,'F',TO_TIMESTAMP('2023-02-06 14:07:12','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Description',30,0,0,TO_TIMESTAMP('2023-02-06 14:07:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 10
-- UI Element Group: calculation
-- 2023-02-06T12:07:44.225Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546630,550342,TO_TIMESTAMP('2023-02-06 14:07:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','calculation',20,TO_TIMESTAMP('2023-02-06 14:07:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 10 -> calculation.Distribution
-- Column: C_Cost_Type.CostDistributionMethod
-- 2023-02-06T12:08:02.012Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712187,0,546807,550342,615587,'F',TO_TIMESTAMP('2023-02-06 14:08:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Distribution',10,0,0,TO_TIMESTAMP('2023-02-06 14:08:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 10 -> calculation.Calculation Method
-- Column: C_Cost_Type.CostCalculationMethod
-- 2023-02-06T12:08:26.582Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712188,0,546807,550342,615588,'F',TO_TIMESTAMP('2023-02-06 14:08:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Calculation Method',20,0,0,TO_TIMESTAMP('2023-02-06 14:08:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 10
-- UI Element Group: distribution & calculation
-- 2023-02-06T12:08:33.477Z
UPDATE AD_UI_ElementGroup SET Name='distribution & calculation',Updated=TO_TIMESTAMP('2023-02-06 14:08:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550342
;

-- UI Column: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 20
-- UI Element Group: flags
-- 2023-02-06T12:08:43.413Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546631,550343,TO_TIMESTAMP('2023-02-06 14:08:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2023-02-06 14:08:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Process: AD_UI_ElementGroup_AddField(org.adempiere.ad.window.process.AD_UI_ElementGroup_AddField)
-- ParameterName: IsNewUIElementsOnly
-- 2023-02-06T12:09:32.946Z
UPDATE AD_Process_Para SET DefaultValue='Y',Updated=TO_TIMESTAMP('2023-02-06 14:09:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541483
;

