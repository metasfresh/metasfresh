-- Table: AD_ViewSource
-- 2023-03-24T10:38:17.002Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('4',0,0,0,542319,'N',TO_TIMESTAMP('2023-03-24 12:38:16','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','Y','Y','N','N','N','N','N',0,'View Source','NP','L','AD_ViewSource','DTI',TO_TIMESTAMP('2023-03-24 12:38:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:38:17.004Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542319 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-03-24T10:38:17.485Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556254,TO_TIMESTAMP('2023-03-24 12:38:17','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table AD_ViewSource',1,'Y','N','Y','Y','AD_ViewSource','N',1000000,TO_TIMESTAMP('2023-03-24 12:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T10:38:17.521Z
CREATE SEQUENCE AD_VIEWSOURCE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: AD_ViewSource.AD_Client_ID
-- 2023-03-24T10:38:23.110Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586329,102,0,19,542319,'AD_Client_ID',TO_TIMESTAMP('2023-03-24 12:38:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','D',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Client',0,0,TO_TIMESTAMP('2023-03-24 12:38:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:38:23.112Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586329 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:38:23.621Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: AD_ViewSource.AD_Org_ID
-- 2023-03-24T10:38:24.563Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586330,113,0,30,542319,'AD_Org_ID',TO_TIMESTAMP('2023-03-24 12:38:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','D',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-03-24 12:38:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:38:24.566Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586330 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:38:25.071Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: AD_ViewSource.Created
-- 2023-03-24T10:38:25.839Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586331,245,0,16,542319,'Created',TO_TIMESTAMP('2023-03-24 12:38:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','D',0,29,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created',0,0,TO_TIMESTAMP('2023-03-24 12:38:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:38:25.841Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586331 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:38:26.549Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: AD_ViewSource.CreatedBy
-- 2023-03-24T10:38:27.340Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586332,246,0,18,110,542319,'CreatedBy',TO_TIMESTAMP('2023-03-24 12:38:27','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','D',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created By',0,0,TO_TIMESTAMP('2023-03-24 12:38:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:38:27.342Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586332 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:38:27.887Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: AD_ViewSource.IsActive
-- 2023-03-24T10:38:28.601Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586333,348,0,20,542319,'IsActive',TO_TIMESTAMP('2023-03-24 12:38:28','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','D',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Active',0,0,TO_TIMESTAMP('2023-03-24 12:38:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:38:28.603Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586333 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:38:29.116Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: AD_ViewSource.Updated
-- 2023-03-24T10:38:29.906Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586334,607,0,16,542319,'Updated',TO_TIMESTAMP('2023-03-24 12:38:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','D',0,29,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated',0,0,TO_TIMESTAMP('2023-03-24 12:38:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:38:29.908Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586334 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:38:30.431Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: AD_ViewSource.UpdatedBy
-- 2023-03-24T10:38:31.175Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586335,608,0,18,110,542319,'UpdatedBy',TO_TIMESTAMP('2023-03-24 12:38:30','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','D',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated By',0,0,TO_TIMESTAMP('2023-03-24 12:38:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:38:31.176Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586335 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:38:31.707Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-03-24T10:38:32.272Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582157,0,'AD_ViewSource_ID',TO_TIMESTAMP('2023-03-24 12:38:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','View Source','View Source',TO_TIMESTAMP('2023-03-24 12:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T10:38:32.277Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582157 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_ViewSource.AD_ViewSource_ID
-- 2023-03-24T10:38:32.929Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586336,582157,0,13,542319,'AD_ViewSource_ID',TO_TIMESTAMP('2023-03-24 12:38:32','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','View Source',0,0,TO_TIMESTAMP('2023-03-24 12:38:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:38:32.931Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586336 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:38:33.550Z
/* DDL */  select update_Column_Translation_From_AD_Element(582157) 
;

-- Column: AD_ViewSource.AD_Table_ID
-- 2023-03-24T10:39:23.527Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586337,126,0,30,542319,'AD_Table_ID',TO_TIMESTAMP('2023-03-24 12:39:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Database Table information','D',0,10,'The Database Table provides the information of the table definition','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Table',0,0,TO_TIMESTAMP('2023-03-24 12:39:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:39:23.529Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586337 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:39:24.017Z
/* DDL */  select update_Column_Translation_From_AD_Element(126) 
;

-- Column: AD_ViewSource.AD_Column_ID
-- 2023-03-24T10:41:10.647Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586338,104,0,30,542319,'AD_Column_ID',TO_TIMESTAMP('2023-03-24 12:41:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Link Column for Multi-Parent tables','D',0,10,'The Link Column indicates which column is the primary key for those situations where there is more than one parent.  Only define it, if the table has more than one parent column (e.g. AD_User_Roles).','Y','Y','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','Link Column',0,0,TO_TIMESTAMP('2023-03-24 12:41:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:41:10.649Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586338 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:41:11.132Z
/* DDL */  select update_Column_Translation_From_AD_Element(104) 
;

-- Column: AD_ViewSource.FetchTargetRecordsMethod
-- 2023-03-24T10:41:11.899Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586339,577635,0,17,541123,542319,'FetchTargetRecordsMethod',TO_TIMESTAMP('2023-03-24 12:41:11','YYYY-MM-DD HH24:MI:SS'),100,'N','L','D',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Fetch Target Records Method',0,0,TO_TIMESTAMP('2023-03-24 12:41:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:41:11.901Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586339 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:41:12.415Z
/* DDL */  select update_Column_Translation_From_AD_Element(577635) 
;

-- Column: AD_ViewSource.Link_Column_ID
-- 2023-03-24T10:41:13.076Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586340,577636,0,18,540748,542319,540494,'Link_Column_ID',TO_TIMESTAMP('2023-03-24 12:41:12','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Link Column',0,0,TO_TIMESTAMP('2023-03-24 12:41:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:41:13.078Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586340 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:41:13.579Z
/* DDL */  select update_Column_Translation_From_AD_Element(577636) 
;

-- Column: AD_ViewSource.Source_Column_ID
-- 2023-03-24T10:41:14.359Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586341,577633,0,18,540748,542319,540494,'Source_Column_ID',TO_TIMESTAMP('2023-03-24 12:41:13','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Source Column',0,0,TO_TIMESTAMP('2023-03-24 12:41:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:41:14.360Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586341 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:41:14.880Z
/* DDL */  select update_Column_Translation_From_AD_Element(577633) 
;

-- Column: AD_ViewSource.Source_Table_ID
-- 2023-03-24T10:41:15.673Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586342,577632,0,30,540750,542319,'Source_Table_ID',TO_TIMESTAMP('2023-03-24 12:41:15','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Source Table',0,0,TO_TIMESTAMP('2023-03-24 12:41:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:41:15.675Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586342 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:41:16.234Z
/* DDL */  select update_Column_Translation_From_AD_Element(577632) 
;

-- Column: AD_ViewSource.SQL_GetTargetRecordIdBySourceRecordId
-- 2023-03-24T10:41:16.873Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586343,577634,0,36,542319,'SQL_GetTargetRecordIdBySourceRecordId',TO_TIMESTAMP('2023-03-24 12:41:16','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,4000,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','SQL to get Target Record IDs by Source Record IDs',0,0,TO_TIMESTAMP('2023-03-24 12:41:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:41:16.874Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586343 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:41:17.400Z
/* DDL */  select update_Column_Translation_From_AD_Element(577634) 
;

-- Column: AD_ViewSource.AD_Table_ID
-- 2023-03-24T10:41:48.303Z
UPDATE AD_Column SET DefaultValue='@AD_Table_ID@', IsMandatory='Y', IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-03-24 12:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586337
;

-- 2023-03-24T10:45:39.527Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582158,0,'Source_LinkColumn_ID',TO_TIMESTAMP('2023-03-24 12:45:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Source Link column','Source Link column',TO_TIMESTAMP('2023-03-24 12:45:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T10:45:39.528Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582158 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-24T10:45:50.426Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582159,0,'Parent_LinkColumn_ID',TO_TIMESTAMP('2023-03-24 12:45:50','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Parent Link column','Parent Link column',TO_TIMESTAMP('2023-03-24 12:45:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T10:45:50.428Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582159 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-24T10:45:52.841Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2023-03-24 12:45:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582159
;

-- 2023-03-24T10:45:52.844Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582159,'en_US') 
;

-- Column: AD_ViewSource.Source_LinkColumn_ID
-- 2023-03-24T10:46:08.179Z
UPDATE AD_Column SET AD_Element_ID=582158, AD_Reference_ID=30, ColumnName='Source_LinkColumn_ID', Description=NULL, Help=NULL, Name='Source Link column',Updated=TO_TIMESTAMP('2023-03-24 12:46:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586340
;

-- 2023-03-24T10:46:08.180Z
UPDATE AD_Column_Trl trl SET Name='Source Link column' WHERE AD_Column_ID=586340 AND AD_Language='en_US'
;

-- 2023-03-24T10:46:08.182Z
UPDATE AD_Field SET Name='Source Link column', Description=NULL, Help=NULL WHERE AD_Column_ID=586340
;

-- 2023-03-24T10:46:08.183Z
/* DDL */  select update_Column_Translation_From_AD_Element(582158) 
;

-- Column: AD_ViewSource.Parent_LinkColumn_ID
-- 2023-03-24T10:46:41.953Z
UPDATE AD_Column SET AD_Element_ID=582159, AD_Reference_Value_ID=540748, AD_Val_Rule_ID=100, ColumnName='Parent_LinkColumn_ID', Description=NULL, Help=NULL, IsUpdateable='N', Name='Parent Link column',Updated=TO_TIMESTAMP('2023-03-24 12:46:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586338
;

-- 2023-03-24T10:46:41.955Z
UPDATE AD_Column_Trl trl SET Name='Parent Link column' WHERE AD_Column_ID=586338 AND AD_Language='en_US'
;

-- 2023-03-24T10:46:41.956Z
UPDATE AD_Field SET Name='Parent Link column', Description=NULL, Help=NULL WHERE AD_Column_ID=586338
;

-- 2023-03-24T10:46:42.455Z
/* DDL */  select update_Column_Translation_From_AD_Element(582159) 
;

-- Column: AD_ViewSource.Parent_LinkColumn_ID
-- 2023-03-24T10:46:46.406Z
UPDATE AD_Column SET IsMandatory='N', IsParent='N',Updated=TO_TIMESTAMP('2023-03-24 12:46:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586338
;

-- Column: AD_ViewSource.Source_Column_ID
-- 2023-03-24T10:49:11.508Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586341
;

-- 2023-03-24T10:49:11.514Z
DELETE FROM AD_Column WHERE AD_Column_ID=586341
;

-- Column: AD_ViewSource.SQL_GetTargetRecordIdBySourceRecordId
-- 2023-03-24T10:50:07.385Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586343
;

-- 2023-03-24T10:50:07.391Z
DELETE FROM AD_Column WHERE AD_Column_ID=586343
;

-- Column: AD_ViewSource.FetchTargetRecordsMethod
-- 2023-03-24T10:50:13.304Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586339
;

-- 2023-03-24T10:50:13.312Z
DELETE FROM AD_Column WHERE AD_Column_ID=586339
;

-- Column: AD_ViewSource.Parent_LinkColumn_ID
-- 2023-03-24T10:50:31.939Z
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='Y',Updated=TO_TIMESTAMP('2023-03-24 12:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586338
;

-- Column: AD_ViewSource.Source_LinkColumn_ID
-- 2023-03-24T10:53:19.550Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-03-24 12:53:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586340
;

-- Column: AD_ViewSource.TechnicalNote
-- 2023-03-24T10:53:42.461Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586344,543921,0,36,542319,'TechnicalNote',TO_TIMESTAMP('2023-03-24 12:53:42','YYYY-MM-DD HH24:MI:SS'),100,'N','A note that is not indended for the user documentation, but for developers, customizers etc','D',0,4000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'TechnicalNote',0,0,TO_TIMESTAMP('2023-03-24 12:53:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:53:42.464Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586344 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:53:42.962Z
/* DDL */  select update_Column_Translation_From_AD_Element(543921) 
;

-- 2023-03-24T10:55:03.229Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582160,0,'IsInvalidateOnBeforeNew',TO_TIMESTAMP('2023-03-24 12:55:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invalidate on Before New','Invalidate on Before New',TO_TIMESTAMP('2023-03-24 12:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T10:55:03.231Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582160 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-24T10:55:16.303Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582161,0,'IsInvalidateOnAfterNew',TO_TIMESTAMP('2023-03-24 12:55:16','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Invalidate on After New','Invalidate on After New',TO_TIMESTAMP('2023-03-24 12:55:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T10:55:16.304Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582161 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-24T10:55:20.208Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2023-03-24 12:55:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582161
;

-- 2023-03-24T10:55:20.211Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582161,'en_US') 
;

-- 2023-03-24T10:55:57.172Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582162,0,'IsInvalidateOnBeforeChange',TO_TIMESTAMP('2023-03-24 12:55:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invalidate on Before Change','Invalidate on Before Change',TO_TIMESTAMP('2023-03-24 12:55:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T10:55:57.173Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582162 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-24T10:56:16.646Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582163,0,'IsInvalidateOnAfterChange',TO_TIMESTAMP('2023-03-24 12:56:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invalidate on After Change','Invalidate on After Change',TO_TIMESTAMP('2023-03-24 12:56:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T10:56:16.647Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582163 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-24T10:56:43.882Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582164,0,'IsInvalidateOnAfterDelete',TO_TIMESTAMP('2023-03-24 12:56:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invalidate on After Delete','Invalidate on After Delete',TO_TIMESTAMP('2023-03-24 12:56:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T10:56:43.885Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582164 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_ViewSource.IsInvalidateOnBeforeNew
-- 2023-03-24T10:57:21.056Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586345,582160,0,20,542319,'IsInvalidateOnBeforeNew',TO_TIMESTAMP('2023-03-24 12:57:20','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Invalidate on Before New',0,0,TO_TIMESTAMP('2023-03-24 12:57:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:57:21.057Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586345 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:57:21.582Z
/* DDL */  select update_Column_Translation_From_AD_Element(582160) 
;

-- Column: AD_ViewSource.IsInvalidateOnAfterNew
-- 2023-03-24T10:57:32.607Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586346,582161,0,20,542319,'IsInvalidateOnAfterNew',TO_TIMESTAMP('2023-03-24 12:57:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Invalidate on After New',0,0,TO_TIMESTAMP('2023-03-24 12:57:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:57:32.609Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586346 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:57:33.123Z
/* DDL */  select update_Column_Translation_From_AD_Element(582161) 
;

-- Column: AD_ViewSource.IsInvalidateOnBeforeChange
-- 2023-03-24T10:57:42.581Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586347,582162,0,20,542319,'IsInvalidateOnBeforeChange',TO_TIMESTAMP('2023-03-24 12:57:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Invalidate on Before Change',0,0,TO_TIMESTAMP('2023-03-24 12:57:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:57:42.582Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586347 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:57:43.109Z
/* DDL */  select update_Column_Translation_From_AD_Element(582162) 
;

-- Column: AD_ViewSource.IsInvalidateOnAfterChange
-- 2023-03-24T10:57:54.861Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586348,582163,0,20,542319,'IsInvalidateOnAfterChange',TO_TIMESTAMP('2023-03-24 12:57:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Invalidate on After Change',0,0,TO_TIMESTAMP('2023-03-24 12:57:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:57:54.863Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586348 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:57:55.373Z
/* DDL */  select update_Column_Translation_From_AD_Element(582163) 
;

-- Column: AD_ViewSource.IsInvalidateOnAfterDelete
-- 2023-03-24T10:58:07.025Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586349,582164,0,20,542319,'IsInvalidateOnAfterDelete',TO_TIMESTAMP('2023-03-24 12:58:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Invalidate on After Delete',0,0,TO_TIMESTAMP('2023-03-24 12:58:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T10:58:07.027Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586349 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T10:58:07.544Z
/* DDL */  select update_Column_Translation_From_AD_Element(582164) 
;

-- Table: AD_ViewSource_Column
-- 2023-03-24T11:00:40.544Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('4',0,0,0,542320,'N',TO_TIMESTAMP('2023-03-24 13:00:40','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','Y','Y','N','N','N','N','N',0,'View Source Column','NP','L','AD_ViewSource_Column','DTI',TO_TIMESTAMP('2023-03-24 13:00:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T11:00:40.545Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542320 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-03-24T11:00:41.046Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556255,TO_TIMESTAMP('2023-03-24 13:00:40','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table AD_ViewSource_Column',1,'Y','N','Y','Y','AD_ViewSource_Column','N',1000000,TO_TIMESTAMP('2023-03-24 13:00:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T11:00:41.056Z
CREATE SEQUENCE AD_VIEWSOURCE_COLUMN_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: AD_ViewSource_Column.AD_Client_ID
-- 2023-03-24T11:00:53.925Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586350,102,0,19,542320,'AD_Client_ID',TO_TIMESTAMP('2023-03-24 13:00:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','D',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Client',0,0,TO_TIMESTAMP('2023-03-24 13:00:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T11:00:53.927Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586350 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T11:00:54.425Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: AD_ViewSource_Column.AD_Org_ID
-- 2023-03-24T11:00:55.198Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586351,113,0,30,542320,'AD_Org_ID',TO_TIMESTAMP('2023-03-24 13:00:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','D',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-03-24 13:00:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T11:00:55.199Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586351 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T11:00:55.734Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: AD_ViewSource_Column.Created
-- 2023-03-24T11:00:56.412Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586352,245,0,16,542320,'Created',TO_TIMESTAMP('2023-03-24 13:00:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','D',0,29,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created',0,0,TO_TIMESTAMP('2023-03-24 13:00:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T11:00:56.413Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586352 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T11:00:56.918Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: AD_ViewSource_Column.CreatedBy
-- 2023-03-24T11:00:57.605Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586353,246,0,18,110,542320,'CreatedBy',TO_TIMESTAMP('2023-03-24 13:00:57','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','D',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created By',0,0,TO_TIMESTAMP('2023-03-24 13:00:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T11:00:57.607Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586353 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T11:00:58.187Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: AD_ViewSource_Column.IsActive
-- 2023-03-24T11:00:58.862Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586354,348,0,20,542320,'IsActive',TO_TIMESTAMP('2023-03-24 13:00:58','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','D',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Active',0,0,TO_TIMESTAMP('2023-03-24 13:00:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T11:00:58.864Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586354 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T11:00:59.394Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: AD_ViewSource_Column.Updated
-- 2023-03-24T11:01:00.094Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586355,607,0,16,542320,'Updated',TO_TIMESTAMP('2023-03-24 13:00:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','D',0,29,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated',0,0,TO_TIMESTAMP('2023-03-24 13:00:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T11:01:00.097Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586355 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T11:01:00.622Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: AD_ViewSource_Column.UpdatedBy
-- 2023-03-24T11:01:01.332Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586356,608,0,18,110,542320,'UpdatedBy',TO_TIMESTAMP('2023-03-24 13:01:01','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','D',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated By',0,0,TO_TIMESTAMP('2023-03-24 13:01:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T11:01:01.334Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586356 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T11:01:01.860Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-03-24T11:01:02.369Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582165,0,'AD_ViewSource_Column_ID',TO_TIMESTAMP('2023-03-24 13:01:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','View Source Column','View Source Column',TO_TIMESTAMP('2023-03-24 13:01:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T11:01:02.372Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582165 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_ViewSource_Column.AD_ViewSource_Column_ID
-- 2023-03-24T11:01:03.021Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586357,582165,0,13,542320,'AD_ViewSource_Column_ID',TO_TIMESTAMP('2023-03-24 13:01:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','View Source Column',0,0,TO_TIMESTAMP('2023-03-24 13:01:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T11:01:03.022Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586357 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T11:01:03.529Z
/* DDL */  select update_Column_Translation_From_AD_Element(582165) 
;

-- Table: AD_ViewSource_Column
-- 2023-03-24T11:01:06.047Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-03-24 13:01:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542320
;

-- Table: AD_ViewSource
-- 2023-03-24T11:01:07.717Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-03-24 13:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542319
;

-- Column: AD_ViewSource_Column.AD_Column_ID
-- 2023-03-24T11:02:27.273Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586358,104,0,30,542320,540494,'AD_Column_ID',TO_TIMESTAMP('2023-03-24 13:02:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Link Column for Multi-Parent tables','D',0,10,'The Link Column indicates which column is the primary key for those situations where there is more than one parent.  Only define it, if the table has more than one parent column (e.g. AD_User_Roles).','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Link Column',0,0,TO_TIMESTAMP('2023-03-24 13:02:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T11:02:27.274Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586358 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T11:02:27.796Z
/* DDL */  select update_Column_Translation_From_AD_Element(104) 
;

-- Column: AD_ViewSource_Column.AD_Column_ID
-- 2023-03-24T11:02:30.177Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-03-24 13:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586358
;

-- Column: AD_ViewSource_Column.AD_Column_ID
-- 2023-03-24T11:02:41.551Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2023-03-24 13:02:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586358
;

-- Column: AD_ViewSource.AD_Table_ID
-- 2023-03-24T11:03:34.449Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2023-03-24 13:03:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586337
;

-- Column: AD_ViewSource.Source_Table_ID
-- 2023-03-24T11:03:44.904Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2023-03-24 13:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586342
;

-- Column: AD_ViewSource_Column.AD_ViewSource_ID
-- 2023-03-24T11:04:06.252Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586359,582157,0,30,542320,'AD_ViewSource_ID',TO_TIMESTAMP('2023-03-24 13:04:06','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'View Source',0,10,TO_TIMESTAMP('2023-03-24 13:04:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T11:04:06.254Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586359 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T11:04:06.932Z
/* DDL */  select update_Column_Translation_From_AD_Element(582157) 
;

-- Column: AD_ViewSource_Column.TechnicalNote
-- 2023-03-24T11:04:30.988Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586360,543921,0,36,542320,'TechnicalNote',TO_TIMESTAMP('2023-03-24 13:04:30','YYYY-MM-DD HH24:MI:SS'),100,'N','A note that is not indended for the user documentation, but for developers, customizers etc','D',0,4000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'TechnicalNote',0,0,TO_TIMESTAMP('2023-03-24 13:04:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-24T11:04:30.990Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586360 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-24T11:04:31.509Z
/* DDL */  select update_Column_Translation_From_AD_Element(543921) 
;

-- Tab: Table and Column(100,D) -> View Source
-- Table: AD_ViewSource
-- 2023-03-24T12:23:11.250Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,586337,582157,0,546851,542319,100,'Y',TO_TIMESTAMP('2023-03-24 14:23:10','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','AD_ViewSource','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'View Source',100,'N',160,1,TO_TIMESTAMP('2023-03-24 14:23:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:11.257Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546851 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-03-24T12:23:11.263Z
/* DDL */  select update_tab_translation_from_ad_element(582157) 
;

-- 2023-03-24T12:23:11.273Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546851)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Client
-- Column: AD_ViewSource.AD_Client_ID
-- 2023-03-24T12:23:19.459Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586329,712923,0,546851,TO_TIMESTAMP('2023-03-24 14:23:19','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-03-24 14:23:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:19.468Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712923 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:23:19.475Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-03-24T12:23:21.437Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712923
;

-- 2023-03-24T12:23:21.440Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712923)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Organisation
-- Column: AD_ViewSource.AD_Org_ID
-- 2023-03-24T12:23:21.634Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586330,712924,0,546851,TO_TIMESTAMP('2023-03-24 14:23:21','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-03-24 14:23:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:21.637Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712924 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:23:21.641Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-03-24T12:23:22.443Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712924
;

-- 2023-03-24T12:23:22.445Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712924)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Active
-- Column: AD_ViewSource.IsActive
-- 2023-03-24T12:23:22.579Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586333,712925,0,546851,TO_TIMESTAMP('2023-03-24 14:23:22','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-03-24 14:23:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:22.587Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712925 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:23:22.591Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-03-24T12:23:24.043Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712925
;

-- 2023-03-24T12:23:24.045Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712925)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> View Source
-- Column: AD_ViewSource.AD_ViewSource_ID
-- 2023-03-24T12:23:24.177Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586336,712926,0,546851,TO_TIMESTAMP('2023-03-24 14:23:24','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','View Source',TO_TIMESTAMP('2023-03-24 14:23:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:24.183Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712926 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:23:24.187Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582157) 
;

-- 2023-03-24T12:23:24.194Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712926
;

-- 2023-03-24T12:23:24.195Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712926)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Table
-- Column: AD_ViewSource.AD_Table_ID
-- 2023-03-24T12:23:24.339Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586337,712927,0,546851,TO_TIMESTAMP('2023-03-24 14:23:24','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',10,'D','The Database Table provides the information of the table definition','Y','N','N','N','N','N','N','N','Table',TO_TIMESTAMP('2023-03-24 14:23:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:24.347Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712927 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:23:24.355Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2023-03-24T12:23:24.515Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712927
;

-- 2023-03-24T12:23:24.517Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712927)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Parent Link column
-- Column: AD_ViewSource.Parent_LinkColumn_ID
-- 2023-03-24T12:23:24.649Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586338,712928,0,546851,TO_TIMESTAMP('2023-03-24 14:23:24','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Parent Link column',TO_TIMESTAMP('2023-03-24 14:23:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:24.651Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712928 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:23:24.654Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582159) 
;

-- 2023-03-24T12:23:24.666Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712928
;

-- 2023-03-24T12:23:24.668Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712928)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Source Link column
-- Column: AD_ViewSource.Source_LinkColumn_ID
-- 2023-03-24T12:23:24.795Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586340,712929,0,546851,TO_TIMESTAMP('2023-03-24 14:23:24','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Source Link column',TO_TIMESTAMP('2023-03-24 14:23:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:24.799Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712929 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:23:24.803Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582158) 
;

-- 2023-03-24T12:23:24.810Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712929
;

-- 2023-03-24T12:23:24.812Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712929)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Source Table
-- Column: AD_ViewSource.Source_Table_ID
-- 2023-03-24T12:23:24.968Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586342,712930,0,546851,TO_TIMESTAMP('2023-03-24 14:23:24','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Source Table',TO_TIMESTAMP('2023-03-24 14:23:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:24.974Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712930 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:23:24.982Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577632) 
;

-- 2023-03-24T12:23:25.003Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712930
;

-- 2023-03-24T12:23:25.008Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712930)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> TechnicalNote
-- Column: AD_ViewSource.TechnicalNote
-- 2023-03-24T12:23:25.137Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586344,712931,0,546851,TO_TIMESTAMP('2023-03-24 14:23:25','YYYY-MM-DD HH24:MI:SS'),100,'A note that is not indended for the user documentation, but for developers, customizers etc',4000,'D','Y','N','N','N','N','N','N','N','TechnicalNote',TO_TIMESTAMP('2023-03-24 14:23:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:25.140Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712931 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:23:25.143Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543921) 
;

-- 2023-03-24T12:23:25.152Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712931
;

-- 2023-03-24T12:23:25.155Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712931)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on Before New
-- Column: AD_ViewSource.IsInvalidateOnBeforeNew
-- 2023-03-24T12:23:25.308Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586345,712932,0,546851,TO_TIMESTAMP('2023-03-24 14:23:25','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Invalidate on Before New',TO_TIMESTAMP('2023-03-24 14:23:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:25.313Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712932 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:23:25.316Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582160) 
;

-- 2023-03-24T12:23:25.323Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712932
;

-- 2023-03-24T12:23:25.326Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712932)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on After New
-- Column: AD_ViewSource.IsInvalidateOnAfterNew
-- 2023-03-24T12:23:25.466Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586346,712933,0,546851,TO_TIMESTAMP('2023-03-24 14:23:25','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Invalidate on After New',TO_TIMESTAMP('2023-03-24 14:23:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:25.473Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712933 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:23:25.481Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582161) 
;

-- 2023-03-24T12:23:25.489Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712933
;

-- 2023-03-24T12:23:25.492Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712933)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on Before Change
-- Column: AD_ViewSource.IsInvalidateOnBeforeChange
-- 2023-03-24T12:23:25.627Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586347,712934,0,546851,TO_TIMESTAMP('2023-03-24 14:23:25','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Invalidate on Before Change',TO_TIMESTAMP('2023-03-24 14:23:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:25.631Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712934 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:23:25.635Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582162) 
;

-- 2023-03-24T12:23:25.643Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712934
;

-- 2023-03-24T12:23:25.645Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712934)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on After Change
-- Column: AD_ViewSource.IsInvalidateOnAfterChange
-- 2023-03-24T12:23:25.800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586348,712935,0,546851,TO_TIMESTAMP('2023-03-24 14:23:25','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Invalidate on After Change',TO_TIMESTAMP('2023-03-24 14:23:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:25.803Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712935 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:23:25.806Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582163) 
;

-- 2023-03-24T12:23:25.812Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712935
;

-- 2023-03-24T12:23:25.813Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712935)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on After Delete
-- Column: AD_ViewSource.IsInvalidateOnAfterDelete
-- 2023-03-24T12:23:25.974Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586349,712936,0,546851,TO_TIMESTAMP('2023-03-24 14:23:25','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Invalidate on After Delete',TO_TIMESTAMP('2023-03-24 14:23:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:23:25.977Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712936 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:23:25.981Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582164) 
;

-- 2023-03-24T12:23:25.986Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712936
;

-- 2023-03-24T12:23:25.988Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712936)
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Table
-- Column: AD_ViewSource.AD_Table_ID
-- 2023-03-24T12:24:33.582Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2023-03-24 14:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712927
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Source Table
-- Column: AD_ViewSource.Source_Table_ID
-- 2023-03-24T12:24:33.595Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2023-03-24 14:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712930
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Source Link column
-- Column: AD_ViewSource.Source_LinkColumn_ID
-- 2023-03-24T12:24:33.606Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2023-03-24 14:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712929
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Parent Link column
-- Column: AD_ViewSource.Parent_LinkColumn_ID
-- 2023-03-24T12:24:33.616Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2023-03-24 14:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712928
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Active
-- Column: AD_ViewSource.IsActive
-- 2023-03-24T12:24:33.626Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2023-03-24 14:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712925
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on Before New
-- Column: AD_ViewSource.IsInvalidateOnBeforeNew
-- 2023-03-24T12:24:33.635Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2023-03-24 14:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712932
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on After New
-- Column: AD_ViewSource.IsInvalidateOnAfterNew
-- 2023-03-24T12:24:33.644Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2023-03-24 14:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712933
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on Before Change
-- Column: AD_ViewSource.IsInvalidateOnBeforeChange
-- 2023-03-24T12:24:33.652Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2023-03-24 14:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712934
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on After Change
-- Column: AD_ViewSource.IsInvalidateOnAfterChange
-- 2023-03-24T12:24:33.661Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2023-03-24 14:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712935
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on After Delete
-- Column: AD_ViewSource.IsInvalidateOnAfterDelete
-- 2023-03-24T12:24:33.670Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2023-03-24 14:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712936
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> TechnicalNote
-- Column: AD_ViewSource.TechnicalNote
-- 2023-03-24T12:24:33.678Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2023-03-24 14:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712931
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Client
-- Column: AD_ViewSource.AD_Client_ID
-- 2023-03-24T12:24:38.158Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-03-24 14:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712923
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Organisation
-- Column: AD_ViewSource.AD_Org_ID
-- 2023-03-24T12:24:38.167Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-03-24 14:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712924
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Active
-- Column: AD_ViewSource.IsActive
-- 2023-03-24T12:24:38.177Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-24 14:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712925
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> View Source
-- Column: AD_ViewSource.AD_ViewSource_ID
-- 2023-03-24T12:24:38.186Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-03-24 14:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712926
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Table
-- Column: AD_ViewSource.AD_Table_ID
-- 2023-03-24T12:24:38.195Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-03-24 14:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712927
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Parent Link column
-- Column: AD_ViewSource.Parent_LinkColumn_ID
-- 2023-03-24T12:24:38.203Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-24 14:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712928
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Source Link column
-- Column: AD_ViewSource.Source_LinkColumn_ID
-- 2023-03-24T12:24:38.211Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-24 14:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712929
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Source Table
-- Column: AD_ViewSource.Source_Table_ID
-- 2023-03-24T12:24:38.218Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-03-24 14:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712930
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> TechnicalNote
-- Column: AD_ViewSource.TechnicalNote
-- 2023-03-24T12:24:38.225Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-03-24 14:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712931
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on Before New
-- Column: AD_ViewSource.IsInvalidateOnBeforeNew
-- 2023-03-24T12:24:38.233Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-24 14:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712932
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on After New
-- Column: AD_ViewSource.IsInvalidateOnAfterNew
-- 2023-03-24T12:24:38.240Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-24 14:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712933
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on Before Change
-- Column: AD_ViewSource.IsInvalidateOnBeforeChange
-- 2023-03-24T12:24:38.247Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-24 14:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712934
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on After Change
-- Column: AD_ViewSource.IsInvalidateOnAfterChange
-- 2023-03-24T12:24:38.254Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-03-24 14:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712935
;

-- Field: Table and Column(100,D) -> View Source(546851,D) -> Invalidate on After Delete
-- Column: AD_ViewSource.IsInvalidateOnAfterDelete
-- 2023-03-24T12:24:38.261Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-03-24 14:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712936
;

-- Tab: Table and Column(100,D) -> View Source Column
-- Table: AD_ViewSource_Column
-- 2023-03-24T12:25:12.067Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582165,0,546852,542320,100,'Y',TO_TIMESTAMP('2023-03-24 14:25:11','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','AD_ViewSource_Column','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'View Source Column','N',170,2,TO_TIMESTAMP('2023-03-24 14:25:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:25:12.070Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546852 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-03-24T12:25:12.075Z
/* DDL */  select update_tab_translation_from_ad_element(582165) 
;

-- 2023-03-24T12:25:12.086Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546852)
;

-- Tab: Table and Column(100,D) -> View Source Column
-- Table: AD_ViewSource_Column
-- 2023-03-24T12:25:35.252Z
UPDATE AD_Tab SET AD_Column_ID=586359, Parent_Column_ID=586336,Updated=TO_TIMESTAMP('2023-03-24 14:25:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546852
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> Client
-- Column: AD_ViewSource_Column.AD_Client_ID
-- 2023-03-24T12:25:39.629Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586350,712937,0,546852,TO_TIMESTAMP('2023-03-24 14:25:39','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-03-24 14:25:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:25:39.632Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712937 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:25:39.635Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-03-24T12:25:39.769Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712937
;

-- 2023-03-24T12:25:39.772Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712937)
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> Organisation
-- Column: AD_ViewSource_Column.AD_Org_ID
-- 2023-03-24T12:25:40.028Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586351,712938,0,546852,TO_TIMESTAMP('2023-03-24 14:25:39','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-03-24 14:25:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:25:40.030Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712938 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:25:40.034Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-03-24T12:25:40.140Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712938
;

-- 2023-03-24T12:25:40.142Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712938)
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> Active
-- Column: AD_ViewSource_Column.IsActive
-- 2023-03-24T12:25:40.288Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586354,712939,0,546852,TO_TIMESTAMP('2023-03-24 14:25:40','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-03-24 14:25:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:25:40.291Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712939 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:25:40.294Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-03-24T12:25:40.444Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712939
;

-- 2023-03-24T12:25:40.447Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712939)
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> View Source Column
-- Column: AD_ViewSource_Column.AD_ViewSource_Column_ID
-- 2023-03-24T12:25:40.585Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586357,712940,0,546852,TO_TIMESTAMP('2023-03-24 14:25:40','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','View Source Column',TO_TIMESTAMP('2023-03-24 14:25:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:25:40.589Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712940 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:25:40.592Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582165) 
;

-- 2023-03-24T12:25:40.600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712940
;

-- 2023-03-24T12:25:40.603Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712940)
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> Link Column
-- Column: AD_ViewSource_Column.AD_Column_ID
-- 2023-03-24T12:25:40.753Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586358,712941,0,546852,TO_TIMESTAMP('2023-03-24 14:25:40','YYYY-MM-DD HH24:MI:SS'),100,'Link Column for Multi-Parent tables',10,'D','The Link Column indicates which column is the primary key for those situations where there is more than one parent.  Only define it, if the table has more than one parent column (e.g. AD_User_Roles).','Y','N','N','N','N','N','N','N','Link Column',TO_TIMESTAMP('2023-03-24 14:25:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:25:40.756Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712941 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:25:40.758Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(104) 
;

-- 2023-03-24T12:25:40.771Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712941
;

-- 2023-03-24T12:25:40.773Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712941)
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> View Source
-- Column: AD_ViewSource_Column.AD_ViewSource_ID
-- 2023-03-24T12:25:40.920Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586359,712942,0,546852,TO_TIMESTAMP('2023-03-24 14:25:40','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','View Source',TO_TIMESTAMP('2023-03-24 14:25:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:25:40.923Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712942 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:25:40.927Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582157) 
;

-- 2023-03-24T12:25:40.940Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712942
;

-- 2023-03-24T12:25:40.942Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712942)
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> TechnicalNote
-- Column: AD_ViewSource_Column.TechnicalNote
-- 2023-03-24T12:25:41.097Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586360,712943,0,546852,TO_TIMESTAMP('2023-03-24 14:25:40','YYYY-MM-DD HH24:MI:SS'),100,'A note that is not indended for the user documentation, but for developers, customizers etc',4000,'D','Y','N','N','N','N','N','N','N','TechnicalNote',TO_TIMESTAMP('2023-03-24 14:25:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-24T12:25:41.104Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712943 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-24T12:25:41.109Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543921) 
;

-- 2023-03-24T12:25:41.117Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712943
;

-- 2023-03-24T12:25:41.120Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712943)
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> Link Column
-- Column: AD_ViewSource_Column.AD_Column_ID
-- 2023-03-24T12:26:12.831Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2023-03-24 14:26:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712941
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> Active
-- Column: AD_ViewSource_Column.IsActive
-- 2023-03-24T12:26:12.843Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2023-03-24 14:26:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712939
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> TechnicalNote
-- Column: AD_ViewSource_Column.TechnicalNote
-- 2023-03-24T12:26:12.855Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2023-03-24 14:26:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712943
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> Client
-- Column: AD_ViewSource_Column.AD_Client_ID
-- 2023-03-24T12:26:17.154Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-03-24 14:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712937
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> Organisation
-- Column: AD_ViewSource_Column.AD_Org_ID
-- 2023-03-24T12:26:17.163Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-03-24 14:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712938
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> Active
-- Column: AD_ViewSource_Column.IsActive
-- 2023-03-24T12:26:17.172Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-03-24 14:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712939
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> View Source Column
-- Column: AD_ViewSource_Column.AD_ViewSource_Column_ID
-- 2023-03-24T12:26:17.180Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-03-24 14:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712940
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> Link Column
-- Column: AD_ViewSource_Column.AD_Column_ID
-- 2023-03-24T12:26:17.189Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-03-24 14:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712941
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> View Source
-- Column: AD_ViewSource_Column.AD_ViewSource_ID
-- 2023-03-24T12:26:17.197Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-03-24 14:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712942
;

-- Field: Table and Column(100,D) -> View Source Column(546852,D) -> TechnicalNote
-- Column: AD_ViewSource_Column.TechnicalNote
-- 2023-03-24T12:26:17.206Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-24 14:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712943
;

-- 2023-03-24T12:26:33.682Z
/* DDL */ CREATE TABLE public.AD_ViewSource (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Table_ID NUMERIC(10) NOT NULL, AD_ViewSource_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsInvalidateOnAfterChange CHAR(1) DEFAULT 'Y' CHECK (IsInvalidateOnAfterChange IN ('Y','N')) NOT NULL, IsInvalidateOnAfterDelete CHAR(1) DEFAULT 'Y' CHECK (IsInvalidateOnAfterDelete IN ('Y','N')) NOT NULL, IsInvalidateOnAfterNew CHAR(1) DEFAULT 'Y' CHECK (IsInvalidateOnAfterNew IN ('Y','N')) NOT NULL, IsInvalidateOnBeforeChange CHAR(1) DEFAULT 'Y' CHECK (IsInvalidateOnBeforeChange IN ('Y','N')) NOT NULL, IsInvalidateOnBeforeNew CHAR(1) DEFAULT 'N' CHECK (IsInvalidateOnBeforeNew IN ('Y','N')) NOT NULL, Parent_LinkColumn_ID NUMERIC(10) NOT NULL, Source_LinkColumn_ID NUMERIC(10) NOT NULL, Source_Table_ID NUMERIC(10) NOT NULL, TechnicalNote TEXT, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADTable_ADViewSource FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED, CONSTRAINT AD_ViewSource_Key PRIMARY KEY (AD_ViewSource_ID), CONSTRAINT ParentLinkColumn_ADViewSource FOREIGN KEY (Parent_LinkColumn_ID) REFERENCES public.AD_Column DEFERRABLE INITIALLY DEFERRED, CONSTRAINT SourceLinkColumn_ADViewSource FOREIGN KEY (Source_LinkColumn_ID) REFERENCES public.AD_Column DEFERRABLE INITIALLY DEFERRED, CONSTRAINT SourceTable_ADViewSource FOREIGN KEY (Source_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED)
;

-- 2023-03-24T12:26:56.987Z
/* DDL */ CREATE TABLE public.AD_ViewSource_Column (AD_Client_ID NUMERIC(10) NOT NULL, AD_Column_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_ViewSource_Column_ID NUMERIC(10) NOT NULL, AD_ViewSource_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, TechnicalNote TEXT, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADColumn_ADViewSourceColumn FOREIGN KEY (AD_Column_ID) REFERENCES public.AD_Column DEFERRABLE INITIALLY DEFERRED, CONSTRAINT AD_ViewSource_Column_Key PRIMARY KEY (AD_ViewSource_Column_ID), CONSTRAINT ADViewSource_ADViewSourceColumn FOREIGN KEY (AD_ViewSource_ID) REFERENCES public.AD_ViewSource DEFERRABLE INITIALLY DEFERRED)
;

