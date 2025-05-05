-- Table: ExternalSystem_Config_SAP_Acct_Export
-- 2023-02-27T10:22:46.313Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542316,'N',TO_TIMESTAMP('2023-02-27 12:22:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','Y','N','N','Y','N','N','N','N','N',0,'SAP Config - accounting Export','NP','L','ExternalSystem_Config_SAP_Acct_Export','DTI',TO_TIMESTAMP('2023-02-27 12:22:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-27T10:22:46.319Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542316 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-02-27T10:22:46.435Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556251,TO_TIMESTAMP('2023-02-27 12:22:46','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table ExternalSystem_Config_SAP_Acct_Export',1,'Y','N','Y','Y','ExternalSystem_Config_SAP_Acct_Export','N',1000000,TO_TIMESTAMP('2023-02-27 12:22:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T10:22:46.457Z
CREATE SEQUENCE EXTERNALSYSTEM_CONFIG_SAP_ACCT_EXPORT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: ExternalSystem_Config_SAP_Acct_Export.AD_Client_ID
-- 2023-02-27T10:23:30.216Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586219,102,0,19,542316,'AD_Client_ID',TO_TIMESTAMP('2023-02-27 12:23:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','de.metas.externalsystem',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Client',0,0,TO_TIMESTAMP('2023-02-27 12:23:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-27T10:23:30.226Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586219 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-27T10:23:30.272Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: ExternalSystem_Config_SAP_Acct_Export.AD_Org_ID
-- 2023-02-27T10:23:31.853Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586220,113,0,30,542316,'AD_Org_ID',TO_TIMESTAMP('2023-02-27 12:23:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','de.metas.externalsystem',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-02-27 12:23:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-27T10:23:31.855Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586220 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-27T10:23:31.859Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: ExternalSystem_Config_SAP_Acct_Export.Created
-- 2023-02-27T10:23:32.877Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586221,245,0,16,542316,'Created',TO_TIMESTAMP('2023-02-27 12:23:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','de.metas.externalsystem',0,29,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created',0,0,TO_TIMESTAMP('2023-02-27 12:23:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-27T10:23:32.880Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586221 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-27T10:23:32.883Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: ExternalSystem_Config_SAP_Acct_Export.CreatedBy
-- 2023-02-27T10:23:33.993Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586222,246,0,18,110,542316,'CreatedBy',TO_TIMESTAMP('2023-02-27 12:23:33','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','de.metas.externalsystem',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created By',0,0,TO_TIMESTAMP('2023-02-27 12:23:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-27T10:23:33.994Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586222 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-27T10:23:33.996Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: ExternalSystem_Config_SAP_Acct_Export.IsActive
-- 2023-02-27T10:23:34.995Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586223,348,0,20,542316,'IsActive',TO_TIMESTAMP('2023-02-27 12:23:34','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','de.metas.externalsystem',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Active',0,0,TO_TIMESTAMP('2023-02-27 12:23:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-27T10:23:34.999Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586223 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-27T10:23:35.003Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: ExternalSystem_Config_SAP_Acct_Export.Updated
-- 2023-02-27T10:23:36.002Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586224,607,0,16,542316,'Updated',TO_TIMESTAMP('2023-02-27 12:23:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','de.metas.externalsystem',0,29,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated',0,0,TO_TIMESTAMP('2023-02-27 12:23:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-27T10:23:36.006Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586224 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-27T10:23:36.009Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: ExternalSystem_Config_SAP_Acct_Export.UpdatedBy
-- 2023-02-27T10:23:37.026Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586225,608,0,18,110,542316,'UpdatedBy',TO_TIMESTAMP('2023-02-27 12:23:36','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','de.metas.externalsystem',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated By',0,0,TO_TIMESTAMP('2023-02-27 12:23:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-27T10:23:37.027Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586225 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-27T10:23:37.029Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-02-27T10:23:37.937Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582101,0,'ExternalSystem_Config_SAP_Acct_Export_ID',TO_TIMESTAMP('2023-02-27 12:23:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','SAP Config - accounting Export','SAP Config - accounting Export',TO_TIMESTAMP('2023-02-27 12:23:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T10:23:37.945Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582101 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_SAP_Acct_Export.ExternalSystem_Config_SAP_Acct_Export_ID
-- 2023-02-27T10:23:38.744Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586226,582101,0,13,542316,'ExternalSystem_Config_SAP_Acct_Export_ID',TO_TIMESTAMP('2023-02-27 12:23:37','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','SAP Config - accounting Export',0,0,TO_TIMESTAMP('2023-02-27 12:23:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-27T10:23:38.746Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586226 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-27T10:23:38.749Z
/* DDL */  select update_Column_Translation_From_AD_Element(582101) 
;

-- 2023-02-27T10:23:39.292Z
/* DDL */ CREATE TABLE public.ExternalSystem_Config_SAP_Acct_Export (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ExternalSystem_Config_SAP_Acct_Export_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ExternalSystem_Config_SAP_Acct_Export_Key PRIMARY KEY (ExternalSystem_Config_SAP_Acct_Export_ID))
;

-- 2023-02-27T10:23:39.333Z
INSERT INTO t_alter_column values('externalsystem_config_sap_acct_export','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2023-02-27T10:23:39.347Z
INSERT INTO t_alter_column values('externalsystem_config_sap_acct_export','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-02-27T10:23:39.360Z
INSERT INTO t_alter_column values('externalsystem_config_sap_acct_export','CreatedBy','NUMERIC(10)',null,null)
;

-- 2023-02-27T10:23:39.369Z
INSERT INTO t_alter_column values('externalsystem_config_sap_acct_export','IsActive','CHAR(1)',null,null)
;

-- 2023-02-27T10:23:39.401Z
INSERT INTO t_alter_column values('externalsystem_config_sap_acct_export','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-02-27T10:23:39.410Z
INSERT INTO t_alter_column values('externalsystem_config_sap_acct_export','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2023-02-27T10:23:39.424Z
INSERT INTO t_alter_column values('externalsystem_config_sap_acct_export','ExternalSystem_Config_SAP_Acct_Export_ID','NUMERIC(10)',null,null)
;

-- Column: ExternalSystem_Config_SAP_Acct_Export.ExternalSystem_Config_SAP_ID
-- 2023-02-27T10:25:26.464Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586227,581532,0,30,541692,542316,'ExternalSystem_Config_SAP_ID',TO_TIMESTAMP('2023-02-27 12:25:26','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'ExternalSystem_Config_SAP',0,0,TO_TIMESTAMP('2023-02-27 12:25:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-27T10:25:26.471Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586227 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-27T10:25:26.488Z
/* DDL */  select update_Column_Translation_From_AD_Element(581532) 
;

-- Column: ExternalSystem_Config_SAP_Acct_Export.AD_Process_ID
-- 2023-02-27T10:26:56.740Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586228,117,0,30,540706,542316,'AD_Process_ID',TO_TIMESTAMP('2023-02-27 12:26:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Process or Report','de.metas.externalsystem',0,10,'The Process field identifies a unique Process or Report in the system.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Process',0,0,TO_TIMESTAMP('2023-02-27 12:26:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-27T10:26:56.743Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586228 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-27T10:26:56.747Z
/* DDL */  select update_Column_Translation_From_AD_Element(117) 
;

-- Column: ExternalSystem_Config_SAP_Acct_Export.C_DocType_ID
-- 2023-02-27T10:27:18.548Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586229,196,0,30,170,542316,'C_DocType_ID',TO_TIMESTAMP('2023-02-27 12:27:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Document type or rules','de.metas.externalsystem',0,10,'The Document Type determines document sequence and processing rules','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Document Type',0,0,TO_TIMESTAMP('2023-02-27 12:27:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-27T10:27:18.551Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586229 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-27T10:27:18.555Z
/* DDL */  select update_Column_Translation_From_AD_Element(196) 
;

-- 2023-02-27T10:27:21.099Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_Acct_Export','ALTER TABLE public.ExternalSystem_Config_SAP_Acct_Export ADD COLUMN C_DocType_ID NUMERIC(10) NOT NULL')
;

-- 2023-02-27T10:27:21.115Z
ALTER TABLE ExternalSystem_Config_SAP_Acct_Export ADD CONSTRAINT CDocType_ExternalSystemConfigSAPAcctExport FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED
;

-- 2023-02-27T10:27:25.092Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_Acct_Export','ALTER TABLE public.ExternalSystem_Config_SAP_Acct_Export ADD COLUMN AD_Process_ID NUMERIC(10) NOT NULL')
;

/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2023-02-27T10:27:25.100Z
ALTER TABLE ExternalSystem_Config_SAP_Acct_Export ADD CONSTRAINT ADProcess_ExternalSystemConfigSAPAcctExport FOREIGN KEY (AD_Process_ID) REFERENCES public.AD_Process DEFERRABLE INITIALLY DEFERRED
;

-- 2023-02-27T10:27:42.954Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_Acct_Export','ALTER TABLE public.ExternalSystem_Config_SAP_Acct_Export ADD COLUMN ExternalSystem_Config_SAP_ID NUMERIC(10) NOT NULL')
;

-- 2023-02-27T10:27:42.961Z
ALTER TABLE ExternalSystem_Config_SAP_Acct_Export ADD CONSTRAINT ExternalSystemConfigSAP_ExternalSystemConfigSAPAcctExport FOREIGN KEY (ExternalSystem_Config_SAP_ID) REFERENCES public.ExternalSystem_Config_SAP DEFERRABLE INITIALLY DEFERRED
;

-- Column: ExternalSystem_Config_SAP_Acct_Export.C_DocType_ID
-- 2023-02-28T11:34:25.297Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-02-28 13:34:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586229
;

-- Column: ExternalSystem_Config_SAP_Acct_Export.C_DocType_ID
-- 2023-02-28T11:34:35.754Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-02-28 13:34:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586229
;

