/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

-- Reference: Type
-- Value: SAP
-- ValueName: SAP
-- 2022-10-03T13:18:31.518Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543299,541255,TO_TIMESTAMP('2022-10-03 16:18:31','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','SAP',TO_TIMESTAMP('2022-10-03 16:18:31','YYYY-MM-DD HH24:MI:SS'),100,'SAP','SAP')
;

-- 2022-10-03T13:18:31.524Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543299 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Type
-- Value: SAP
-- ValueName: SAP
-- 2022-10-03T13:18:40.164Z
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2022-10-03 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543299
;

-- Table: ExternalSystem_Config_SAP
-- 2022-10-03T13:23:03.126Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('4',0,0,0,542238,'N',TO_TIMESTAMP('2022-10-03 16:23:02','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'ExternalSystem_Config_SAP','NP','L','ExternalSystem_Config_SAP','DTI',TO_TIMESTAMP('2022-10-03 16:23:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T13:23:03.139Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542238 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-10-03T13:23:03.256Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556032,TO_TIMESTAMP('2022-10-03 16:23:03','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table ExternalSystem_Config_SAP',1,'Y','N','Y','Y','ExternalSystem_Config_SAP','N',1000000,TO_TIMESTAMP('2022-10-03 16:23:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T13:23:03.272Z
CREATE SEQUENCE EXTERNALSYSTEM_CONFIG_SAP_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Table: ExternalSystem_Config_SAP
-- 2022-10-03T13:33:46.696Z
UPDATE AD_Table SET AccessLevel='3', EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-10-03 16:33:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542238
;

-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_ID
-- 2022-10-03T13:37:40.527Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584643,578728,0,19,542238,'ExternalSystem_Config_ID',TO_TIMESTAMP('2022-10-03 16:37:40','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'External System Config',0,0,TO_TIMESTAMP('2022-10-03 16:37:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-03T13:37:40.533Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584643 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-03T13:37:40.543Z
/* DDL */  select update_Column_Translation_From_AD_Element(578728) 
;

-- Column: ExternalSystem_Config_SAP.ExternalSystemValue
-- 2022-10-03T13:39:21.844Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584644,578788,0,10,542238,'ExternalSystemValue',TO_TIMESTAMP('2022-10-03 16:39:21','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Suchschlüssel',0,0,TO_TIMESTAMP('2022-10-03 16:39:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-03T13:39:21.850Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584644 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-03T13:39:21.858Z
/* DDL */  select update_Column_Translation_From_AD_Element(578788) 
;

-- 2022-10-03T13:45:43.905Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581528,0,TO_TIMESTAMP('2022-10-03 16:45:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','FTP_HostName','FTP Hostname',TO_TIMESTAMP('2022-10-03 16:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T13:45:43.911Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581528 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2022-10-03T13:46:16.298Z
UPDATE AD_Element_Trl SET PrintName='FTP-Hostname',Updated=TO_TIMESTAMP('2022-10-03 16:46:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581528 AND AD_Language='de_CH'
;

-- 2022-10-03T13:46:16.299Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581528,'de_CH') 
;

-- Element: null
-- 2022-10-03T13:46:24.500Z
UPDATE AD_Element_Trl SET PrintName='FTP-Hostname',Updated=TO_TIMESTAMP('2022-10-03 16:46:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581528 AND AD_Language='de_DE'
;

-- 2022-10-03T13:46:24.501Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581528,'de_DE') 
;

-- 2022-10-03T13:46:24.503Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581528,'de_DE') 
;

-- 2022-10-03T13:51:37.292Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2022-10-03 16:51:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581528
;

-- 2022-10-03T13:51:37.295Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581528,'de_DE') 
;

-- 2022-10-03T13:53:40.524Z
UPDATE AD_Element SET ColumnName='FTP_HostName', Name='FTP-Hostname',Updated=TO_TIMESTAMP('2022-10-03 16:53:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581528
;

-- 2022-10-03T13:53:40.525Z
UPDATE AD_Column SET ColumnName='FTP_HostName' WHERE AD_Element_ID=581528
;

-- 2022-10-03T13:53:40.526Z
UPDATE AD_Process_Para SET ColumnName='FTP_HostName' WHERE AD_Element_ID=581528
;

-- 2022-10-03T13:53:40.528Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581528,'de_DE') 
;

-- 2022-10-03T13:53:58.849Z
UPDATE AD_Element SET Name='FTP-Hostname',Updated=TO_TIMESTAMP('2022-10-03 16:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581528
;

-- 2022-10-03T13:53:58.851Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581528,'de_DE') 
;

-- Element: FTP_HostName
-- 2022-10-03T13:54:59.036Z
UPDATE AD_Element_Trl SET Name='FTP-Hostname',Updated=TO_TIMESTAMP('2022-10-03 16:54:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581528 AND AD_Language='de_CH'
;

-- 2022-10-03T13:54:59.038Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581528,'de_CH') 
;

-- Element: FTP_HostName
-- 2022-10-03T13:55:09.060Z
UPDATE AD_Element_Trl SET Name='FTP-Hostname',Updated=TO_TIMESTAMP('2022-10-03 16:55:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581528 AND AD_Language='de_DE'
;

-- 2022-10-03T13:55:09.062Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581528,'de_DE') 
;

-- 2022-10-03T13:55:09.064Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581528,'de_DE') 
;

-- Element: FTP_HostName
-- 2022-10-03T13:55:15.701Z
UPDATE AD_Element_Trl SET Name='FTP_Hostname',Updated=TO_TIMESTAMP('2022-10-03 16:55:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581528 AND AD_Language='en_US'
;

-- 2022-10-03T13:55:15.703Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581528,'en_US') 
;

-- Element: FTP_HostName
-- 2022-10-03T13:55:21.439Z
UPDATE AD_Element_Trl SET Name='FTP_Hostname',Updated=TO_TIMESTAMP('2022-10-03 16:55:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581528 AND AD_Language='nl_NL'
;

-- 2022-10-03T13:55:21.440Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581528,'nl_NL') 
;

-- Column: ExternalSystem_Config_SAP.FTP_HostName
-- 2022-10-03T13:56:44.326Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584648,581528,0,10,542238,'FTP_HostName',TO_TIMESTAMP('2022-10-03 16:56:44','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'FTP-Hostname',0,0,TO_TIMESTAMP('2022-10-03 16:56:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-03T13:56:44.331Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584648 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-03T13:56:44.337Z
/* DDL */  select update_Column_Translation_From_AD_Element(581528) 
;

-- 2022-10-03T13:58:01.474Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581529,0,'FTP_Port',TO_TIMESTAMP('2022-10-03 16:58:01','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','FTP-Port','FTP-Port',TO_TIMESTAMP('2022-10-03 16:58:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T13:58:01.476Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581529 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: FTP_Port
-- 2022-10-03T13:58:27.304Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='FTP port', PrintName='FTP port',Updated=TO_TIMESTAMP('2022-10-03 16:58:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581529 AND AD_Language='en_US'
;

-- 2022-10-03T13:58:27.305Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581529,'en_US') 
;

-- Element: FTP_HostName
-- 2022-10-03T13:58:48.794Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-03 16:58:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581528 AND AD_Language='en_US'
;

-- 2022-10-03T13:58:48.795Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581528,'en_US') 
;

-- Column: ExternalSystem_Config_SAP.FTP_Port
-- 2022-10-03T14:03:12.355Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584649,581529,0,22,542238,'FTP_Port',TO_TIMESTAMP('2022-10-03 17:03:12','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'FTP-Port',0,0,TO_TIMESTAMP('2022-10-03 17:03:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-03T14:03:12.358Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584649 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-03T14:03:12.364Z
/* DDL */  select update_Column_Translation_From_AD_Element(581529) 
;

-- 2022-10-03T14:04:11.162Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581530,0,'FTP_Username',TO_TIMESTAMP('2022-10-03 17:04:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','FTP username','FTP username',TO_TIMESTAMP('2022-10-03 17:04:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T14:04:11.164Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581530 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: FTP_Username
-- 2022-10-03T14:05:15.034Z
UPDATE AD_Element_Trl SET Name='FTP-Benutzername', PrintName='FTP-Benutzername',Updated=TO_TIMESTAMP('2022-10-03 17:05:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581530 AND AD_Language='de_DE'
;

-- 2022-10-03T14:05:15.035Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581530,'de_DE') 
;

-- 2022-10-03T14:05:15.036Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581530,'de_DE') 
;

-- Element: FTP_Username
-- 2022-10-03T14:05:31.656Z
UPDATE AD_Element_Trl SET Name='FTP-Benutzername', PrintName='FTP-Benutzername',Updated=TO_TIMESTAMP('2022-10-03 17:05:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581530 AND AD_Language='de_CH'
;

-- 2022-10-03T14:05:31.658Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581530,'de_CH') 
;

-- Element: FTP_Username
-- 2022-10-03T14:05:35.779Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-03 17:05:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581530 AND AD_Language='en_US'
;

-- 2022-10-03T14:05:35.781Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581530,'en_US') 
;

-- 2022-10-03T14:05:51.161Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2022-10-03 17:05:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581529
;

-- 2022-10-03T14:05:51.163Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581529,'de_DE') 
;

-- Column: ExternalSystem_Config_SAP.FTP_Username
-- 2022-10-03T14:06:07.228Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584650,581530,0,10,542238,'FTP_Username',TO_TIMESTAMP('2022-10-03 17:06:07','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'FTP-Benutzername',0,0,TO_TIMESTAMP('2022-10-03 17:06:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-03T14:06:07.231Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584650 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-03T14:06:07.235Z
/* DDL */  select update_Column_Translation_From_AD_Element(581530) 
;

-- 2022-10-03T14:07:30.357Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581531,0,'FTP_Password',TO_TIMESTAMP('2022-10-03 17:07:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','FTP password','FTP password',TO_TIMESTAMP('2022-10-03 17:07:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T14:07:30.359Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581531 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: FTP_Password
-- 2022-10-03T14:07:35.052Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-03 17:07:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581531 AND AD_Language='en_US'
;

-- 2022-10-03T14:07:35.054Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581531,'en_US') 
;

-- Element: FTP_Password
-- 2022-10-03T14:07:57.292Z
UPDATE AD_Element_Trl SET Name='FTP-Passwort', PrintName='FTP-Passwort',Updated=TO_TIMESTAMP('2022-10-03 17:07:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581531 AND AD_Language='de_DE'
;

-- 2022-10-03T14:07:57.294Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581531,'de_DE') 
;

-- 2022-10-03T14:07:57.295Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581531,'de_DE') 
;

-- Element: FTP_Password
-- 2022-10-03T14:08:06.971Z
UPDATE AD_Element_Trl SET Name='FTP-Passwort', PrintName='FTP-Passwort',Updated=TO_TIMESTAMP('2022-10-03 17:08:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581531 AND AD_Language='de_CH'
;

-- 2022-10-03T14:08:06.972Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581531,'de_CH') 
;

-- Column: ExternalSystem_Config_SAP.FTP_Password
-- 2022-10-03T14:08:42.209Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584651,581531,0,10,542238,'FTP_Password',TO_TIMESTAMP('2022-10-03 17:08:42','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'FTP-Passwort',0,0,TO_TIMESTAMP('2022-10-03 17:08:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-03T14:08:42.212Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584651 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-03T14:08:42.218Z
/* DDL */  select update_Column_Translation_From_AD_Element(581531) 
;

-- 2022-10-03T14:10:22.921Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581532,0,'ExternalSystem_Config_SAP_ID',TO_TIMESTAMP('2022-10-03 17:10:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','ExternalSystem_Config_SAP','ExternalSystem_Config_SAP',TO_TIMESTAMP('2022-10-03 17:10:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T14:10:22.923Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581532 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-10-03T14:10:23.371Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,584652,581532,0,13,542238,'ExternalSystem_Config_SAP_ID',TO_TIMESTAMP('2022-10-03 17:10:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','ExternalSystem_Config_SAP',TO_TIMESTAMP('2022-10-03 17:10:22','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-10-03T14:10:23.372Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584652 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-03T14:10:23.374Z
/* DDL */  select update_Column_Translation_From_AD_Element(581532) 
;

-- 2022-10-03T14:10:23.793Z
ALTER SEQUENCE EXTERNALSYSTEM_CONFIG_SAP_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;

-- 2022-10-03T14:10:42.767Z
/* DDL */ CREATE TABLE public.ExternalSystem_Config_SAP (ExternalSystem_Config_ID NUMERIC(10), ExternalSystem_Config_SAP_ID NUMERIC(10) NOT NULL, ExternalSystemValue VARCHAR(255), FTP_HostName VARCHAR(255), FTP_Password VARCHAR(255), FTP_Port NUMERIC, FTP_Username VARCHAR(255), CONSTRAINT ExternalSystemConfig_ExternalSystemConfigSAP FOREIGN KEY (ExternalSystem_Config_ID) REFERENCES public.ExternalSystem_Config DEFERRABLE INITIALLY DEFERRED, CONSTRAINT ExternalSystem_Config_SAP_Key PRIMARY KEY (ExternalSystem_Config_SAP_ID))
;

-- Column: ExternalSystem_Config_SAP.AD_Client_ID
-- 2022-10-03T14:34:49.447Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584664,102,0,19,542238,'AD_Client_ID',TO_TIMESTAMP('2022-10-03 17:34:49','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.externalsystem',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-10-03 17:34:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-03T14:34:49.451Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584664 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-03T14:34:49.477Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: ExternalSystem_Config_SAP.AD_Org_ID
-- 2022-10-03T14:34:50.499Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584665,113,0,30,542238,'AD_Org_ID',TO_TIMESTAMP('2022-10-03 17:34:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.externalsystem',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-10-03 17:34:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-03T14:34:50.503Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584665 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-03T14:34:50.506Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: ExternalSystem_Config_SAP.Created
-- 2022-10-03T14:34:51.243Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584666,245,0,16,542238,'Created',TO_TIMESTAMP('2022-10-03 17:34:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.externalsystem',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-10-03 17:34:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-03T14:34:51.246Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584666 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-03T14:34:51.251Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: ExternalSystem_Config_SAP.CreatedBy
-- 2022-10-03T14:34:51.967Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584667,246,0,18,110,542238,'CreatedBy',TO_TIMESTAMP('2022-10-03 17:34:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.externalsystem',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-10-03 17:34:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-03T14:34:51.969Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584667 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-03T14:34:51.970Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: ExternalSystem_Config_SAP.IsActive
-- 2022-10-03T14:34:52.689Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584668,348,0,20,542238,'IsActive',TO_TIMESTAMP('2022-10-03 17:34:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.externalsystem',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-10-03 17:34:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-03T14:34:52.692Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584668 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-03T14:34:52.696Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: ExternalSystem_Config_SAP.Updated
-- 2022-10-03T14:34:53.513Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584669,607,0,16,542238,'Updated',TO_TIMESTAMP('2022-10-03 17:34:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.externalsystem',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-10-03 17:34:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-03T14:34:53.514Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584669 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-03T14:34:53.516Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: ExternalSystem_Config_SAP.UpdatedBy
-- 2022-10-03T14:34:54.307Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584670,608,0,18,110,542238,'UpdatedBy',TO_TIMESTAMP('2022-10-03 17:34:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.externalsystem',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-10-03 17:34:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-03T14:34:54.309Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584670 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-03T14:34:54.310Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-10-03T14:35:10.671Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN AD_Client_ID NUMERIC(10) NOT NULL')
;

-- 2022-10-03T14:35:50.513Z
INSERT INTO t_alter_column values('externalsystem_config_sap','AD_Client_ID','NUMERIC(10)',null,null)
;

-- 2022-10-03T14:36:14.228Z
INSERT INTO t_alter_column values('externalsystem_config_sap','AD_Client_ID','NUMERIC(10)',null,null)
;

-- 2022-10-03T14:36:37.657Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN UpdatedBy NUMERIC(10) NOT NULL')
;

-- 2022-10-03T14:36:42.400Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN Updated TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2022-10-03T14:36:46.217Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN IsActive CHAR(1) CHECK (IsActive IN (''Y'',''N'')) NOT NULL')
;

-- 2022-10-03T14:36:50.563Z
INSERT INTO t_alter_column values('externalsystem_config_sap','FTP_Username','VARCHAR(255)',null,null)
;

-- 2022-10-03T14:36:55.533Z
INSERT INTO t_alter_column values('externalsystem_config_sap','FTP_Port','NUMERIC',null,null)
;

-- 2022-10-03T14:37:00.236Z
INSERT INTO t_alter_column values('externalsystem_config_sap','FTP_Password','VARCHAR(255)',null,null)
;

-- 2022-10-03T14:37:04.917Z
INSERT INTO t_alter_column values('externalsystem_config_sap','FTP_HostName','VARCHAR(255)',null,null)
;

-- 2022-10-03T14:37:09.231Z
INSERT INTO t_alter_column values('externalsystem_config_sap','ExternalSystemValue','VARCHAR(255)',null,null)
;

-- 2022-10-03T14:37:12.514Z
INSERT INTO t_alter_column values('externalsystem_config_sap','ExternalSystem_Config_SAP_ID','NUMERIC(10)',null,null)
;

-- 2022-10-03T14:37:16.129Z
INSERT INTO t_alter_column values('externalsystem_config_sap','ExternalSystem_Config_ID','NUMERIC(10)',null,null)
;

-- 2022-10-03T14:37:19.820Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN CreatedBy NUMERIC(10) NOT NULL')
;

-- 2022-10-03T14:37:25.670Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN Created TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2022-10-03T14:37:32.125Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN AD_Org_ID NUMERIC(10) NOT NULL')
;

-- 2022-10-03T14:37:37.938Z
INSERT INTO t_alter_column values('externalsystem_config_sap','AD_Client_ID','NUMERIC(10)',null,null)
;

-- 2022-10-03T14:39:40.115Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581533,0,'SAP',TO_TIMESTAMP('2022-10-03 17:39:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SAP','SAP',TO_TIMESTAMP('2022-10-03 17:39:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T14:39:40.120Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581533 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Tab: External System Config(541024,de.metas.externalsystem) -> ExternalSystem_Config_SAP
-- Table: ExternalSystem_Config_SAP
-- 2022-10-03T14:58:51.728Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581532,0,546647,542238,541024,'Y',TO_TIMESTAMP('2022-10-03 17:58:51','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','A','ExternalSystem_Config_SAP','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'ExternalSystem_Config_SAP','N',80,0,TO_TIMESTAMP('2022-10-03 17:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T14:58:51.738Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546647 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-10-03T14:58:51.742Z
/* DDL */  select update_tab_translation_from_ad_element(581532) 
;

-- 2022-10-03T14:58:51.757Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546647)
;

-- Tab: External System Config(541024,de.metas.externalsystem) -> SAP
-- Table: ExternalSystem_Config_SAP
-- 2022-10-03T14:59:54.689Z
UPDATE AD_Tab SET AD_Element_ID=581533, CommitWarning=NULL, Description=NULL, Help=NULL, Name='SAP',Updated=TO_TIMESTAMP('2022-10-03 17:59:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546647
;

-- 2022-10-03T14:59:54.691Z
/* DDL */  select update_tab_translation_from_ad_element(581533) 
;

-- 2022-10-03T14:59:54.693Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546647)
;

-- Tab: External System Config(541024,de.metas.externalsystem) -> SAP
-- Table: ExternalSystem_Config_SAP
-- 2022-10-03T15:00:11.032Z
UPDATE AD_Tab SET AD_Column_ID=584643, HasTree='Y',Updated=TO_TIMESTAMP('2022-10-03 18:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546647
;

-- Tab: External System Config(541024,de.metas.externalsystem) -> SAP
-- Table: ExternalSystem_Config_SAP
-- 2022-10-03T15:00:29.112Z
UPDATE AD_Tab SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-10-03 18:00:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546647
;

-- Tab: External System Config(541024,de.metas.externalsystem) -> SAP
-- Table: ExternalSystem_Config_SAP
-- 2022-10-03T15:00:55.827Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2022-10-03 18:00:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546647
;

-- Tab: External System Config(541024,de.metas.externalsystem) -> SAP
-- Table: ExternalSystem_Config_SAP
-- 2022-10-03T15:01:43.586Z
UPDATE AD_Tab SET HasTree='N',Updated=TO_TIMESTAMP('2022-10-03 18:01:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546647
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> Mandant
-- Column: ExternalSystem_Config_SAP.AD_Client_ID
-- 2022-10-03T15:02:31.642Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584664,707462,0,546647,0,TO_TIMESTAMP('2022-10-03 18:02:31','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',0,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',0,10,0,1,1,TO_TIMESTAMP('2022-10-03 18:02:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T15:02:31.646Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707462 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-03T15:02:31.649Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-10-03T15:02:32.413Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707462
;

-- 2022-10-03T15:02:32.414Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707462)
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> Mandant
-- Column: ExternalSystem_Config_SAP.AD_Client_ID
-- 2022-10-03T15:03:55.693Z
UPDATE AD_Field SET EntityType='de.metas.externalsystem', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-10-03 18:03:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707462
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> Sektion
-- Column: ExternalSystem_Config_SAP.AD_Org_ID
-- 2022-10-03T15:04:30.626Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584665,707463,0,546647,0,TO_TIMESTAMP('2022-10-03 18:04:30','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',0,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','N','Sektion',0,20,0,1,1,TO_TIMESTAMP('2022-10-03 18:04:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T15:04:30.630Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707463 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-03T15:04:30.638Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-10-03T15:04:31.026Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707463
;

-- 2022-10-03T15:04:31.030Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707463)
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> Aktiv
-- Column: ExternalSystem_Config_SAP.IsActive
-- 2022-10-03T15:05:46.978Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584668,707464,0,546647,0,TO_TIMESTAMP('2022-10-03 18:05:46','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',0,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',0,30,0,1,1,TO_TIMESTAMP('2022-10-03 18:05:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T15:05:46.982Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707464 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-03T15:05:46.989Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-10-03T15:05:47.356Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707464
;

-- 2022-10-03T15:05:47.357Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707464)
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> Suchschlüssel
-- Column: ExternalSystem_Config_SAP.ExternalSystemValue
-- 2022-10-03T15:06:25.363Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584644,707465,0,546647,0,TO_TIMESTAMP('2022-10-03 18:06:25','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Suchschlüssel',0,40,0,1,1,TO_TIMESTAMP('2022-10-03 18:06:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T15:06:25.364Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707465 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-03T15:06:25.366Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578788) 
;

-- 2022-10-03T15:06:25.370Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707465
;

-- 2022-10-03T15:06:25.370Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707465)
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> Aktiv
-- Column: ExternalSystem_Config_SAP.IsActive
-- 2022-10-03T15:07:57.882Z
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-10-03 18:07:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707464
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> Sektion
-- Column: ExternalSystem_Config_SAP.AD_Org_ID
-- 2022-10-03T15:10:01.962Z
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-10-03 18:10:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707463
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> Mandant
-- Column: ExternalSystem_Config_SAP.AD_Client_ID
-- 2022-10-03T15:10:31.438Z
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-10-03 18:10:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707462
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> External System Config
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_ID
-- 2022-10-03T15:11:26.331Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584643,707466,0,546647,0,TO_TIMESTAMP('2022-10-03 18:11:26','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','N','N','N','N','N','N','External System Config',0,50,0,1,1,TO_TIMESTAMP('2022-10-03 18:11:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T15:11:26.334Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707466 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-03T15:11:26.337Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728) 
;

-- 2022-10-03T15:11:26.343Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707466
;

-- 2022-10-03T15:11:26.348Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707466)
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> External System Config
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_ID
-- 2022-10-03T15:11:41.786Z
UPDATE AD_Field SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-10-03 18:11:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707466
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> ExternalSystem_Config_SAP
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-10-03T15:12:25.401Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584652,707467,0,546647,0,TO_TIMESTAMP('2022-10-03 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','ExternalSystem_Config_SAP',0,60,0,1,1,TO_TIMESTAMP('2022-10-03 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T15:12:25.404Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707467 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-03T15:12:25.406Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581532) 
;

-- 2022-10-03T15:12:25.412Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707467
;

-- 2022-10-03T15:12:25.412Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707467)
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> FTP-Hostname
-- Column: ExternalSystem_Config_SAP.FTP_HostName
-- 2022-10-03T15:13:48.300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584648,707468,0,546647,0,TO_TIMESTAMP('2022-10-03 18:13:48','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','FTP-Hostname',0,70,0,1,1,TO_TIMESTAMP('2022-10-03 18:13:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T15:13:48.302Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707468 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-03T15:13:48.305Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581528) 
;

-- 2022-10-03T15:13:48.310Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707468
;

-- 2022-10-03T15:13:48.310Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707468)
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> FTP-Port
-- Column: ExternalSystem_Config_SAP.FTP_Port
-- 2022-10-03T15:14:14.259Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584649,707469,0,546647,0,TO_TIMESTAMP('2022-10-03 18:14:14','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','FTP-Port',0,80,0,1,1,TO_TIMESTAMP('2022-10-03 18:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T15:14:14.261Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707469 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-03T15:14:14.266Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581529) 
;

-- 2022-10-03T15:14:14.272Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707469
;

-- 2022-10-03T15:14:14.272Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707469)
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> FTP-Benutzername
-- Column: ExternalSystem_Config_SAP.FTP_Username
-- 2022-10-03T15:14:42.031Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584650,707470,0,546647,0,TO_TIMESTAMP('2022-10-03 18:14:41','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','FTP-Benutzername',0,90,0,1,1,TO_TIMESTAMP('2022-10-03 18:14:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T15:14:42.033Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707470 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-03T15:14:42.033Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581530) 
;

-- 2022-10-03T15:14:42.035Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707470
;

-- 2022-10-03T15:14:42.036Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707470)
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> FTP-Passwort
-- Column: ExternalSystem_Config_SAP.FTP_Password
-- 2022-10-03T15:19:50.247Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584651,707471,0,546647,0,TO_TIMESTAMP('2022-10-03 18:19:50','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','N','Y','N','N','N','N','FTP-Passwort',0,100,0,1,1,TO_TIMESTAMP('2022-10-03 18:19:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T15:19:50.250Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707471 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-03T15:19:50.256Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581531) 
;

-- 2022-10-03T15:19:50.262Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707471
;

-- 2022-10-03T15:19:50.262Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707471)
;

-- Tab: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem)
-- UI Section: main
-- 2022-10-03T15:21:51.105Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546647,545271,TO_TIMESTAMP('2022-10-03 18:21:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-10-03 18:21:50','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-10-03T15:21:51.108Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545271 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main
-- UI Column: 10
-- 2022-10-03T15:22:16.884Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546411,545271,TO_TIMESTAMP('2022-10-03 18:22:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-10-03 18:22:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10
-- UI Element Group: main
-- 2022-10-03T15:22:33.589Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546411,549954,TO_TIMESTAMP('2022-10-03 18:22:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2022-10-03 18:22:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.Suchschlüssel
-- Column: ExternalSystem_Config_SAP.ExternalSystemValue
-- 2022-10-03T15:30:05.103Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy,WidgetSize) VALUES (0,707465,0,546647,613112,549954,'F',TO_TIMESTAMP('2022-10-03 18:30:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Suchschlüssel',10,0,0,TO_TIMESTAMP('2022-10-03 18:30:04','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.FTP-Hostname
-- Column: ExternalSystem_Config_SAP.FTP_HostName
-- 2022-10-03T15:31:16.423Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707468,0,546647,613113,549954,'F',TO_TIMESTAMP('2022-10-03 18:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'FTP-Hostname',20,0,0,TO_TIMESTAMP('2022-10-03 18:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.FTP-Port
-- Column: ExternalSystem_Config_SAP.FTP_Port
-- 2022-10-03T15:32:09.868Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707469,0,546647,613114,549954,'F',TO_TIMESTAMP('2022-10-03 18:32:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'FTP-Port',30,0,0,TO_TIMESTAMP('2022-10-03 18:32:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.FTP-Benutzername
-- Column: ExternalSystem_Config_SAP.FTP_Username
-- 2022-10-03T15:32:29.865Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707470,0,546647,613115,549954,'F',TO_TIMESTAMP('2022-10-03 18:32:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'FTP-Benutzername',40,0,0,TO_TIMESTAMP('2022-10-03 18:32:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.FTP-Passwort
-- Column: ExternalSystem_Config_SAP.FTP_Password
-- 2022-10-03T15:32:40.291Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707471,0,546647,613116,549954,'F',TO_TIMESTAMP('2022-10-03 18:32:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'FTP-Passwort',50,0,0,TO_TIMESTAMP('2022-10-03 18:32:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main
-- UI Column: 20
-- 2022-10-03T15:33:51.298Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546412,545271,TO_TIMESTAMP('2022-10-03 18:33:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-10-03 18:33:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 20
-- UI Element Group: flag
-- 2022-10-03T15:34:04.591Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546412,549955,TO_TIMESTAMP('2022-10-03 18:34:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','flag',10,TO_TIMESTAMP('2022-10-03 18:34:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 20 -> flag.Aktiv
-- Column: ExternalSystem_Config_SAP.IsActive
-- 2022-10-03T15:34:25.641Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707464,0,546647,613117,549955,'F',TO_TIMESTAMP('2022-10-03 18:34:25','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2022-10-03 18:34:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 20
-- UI Element Group: org
-- 2022-10-03T15:34:52.725Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546412,549956,TO_TIMESTAMP('2022-10-03 18:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2022-10-03 18:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 20 -> org.Mandant
-- Column: ExternalSystem_Config_SAP.AD_Client_ID
-- 2022-10-03T15:35:08.926Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707462,0,546647,613118,549956,'F',TO_TIMESTAMP('2022-10-03 18:35:08','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2022-10-03 18:35:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 20 -> org.Sektion
-- Column: ExternalSystem_Config_SAP.AD_Org_ID
-- 2022-10-03T15:35:19.544Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707463,0,546647,613119,549956,'F',TO_TIMESTAMP('2022-10-03 18:35:19','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2022-10-03 18:35:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Element: FTP_HostName
-- 2022-10-03T15:40:39.949Z
UPDATE AD_Element_Trl SET Name='FTP Hostname',Updated=TO_TIMESTAMP('2022-10-03 18:40:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581528 AND AD_Language='en_US'
;

-- 2022-10-03T15:40:39.953Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581528,'en_US') 
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.Suchschlüssel
-- Column: ExternalSystem_Config_SAP.ExternalSystemValue
-- 2022-10-03T15:43:34.772Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-10-03 18:43:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613112
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.FTP-Hostname
-- Column: ExternalSystem_Config_SAP.FTP_HostName
-- 2022-10-03T15:43:34.777Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-10-03 18:43:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613113
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.FTP-Passwort
-- Column: ExternalSystem_Config_SAP.FTP_Password
-- 2022-10-03T15:43:34.781Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-10-03 18:43:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613116
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.FTP-Port
-- Column: ExternalSystem_Config_SAP.FTP_Port
-- 2022-10-03T15:43:34.785Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-10-03 18:43:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613114
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.FTP-Benutzername
-- Column: ExternalSystem_Config_SAP.FTP_Username
-- 2022-10-03T15:43:34.789Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-10-03 18:43:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613115
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.FTP-Passwort
-- Column: ExternalSystem_Config_SAP.FTP_Password
-- 2022-10-03T15:49:17.881Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-10-03 18:49:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613116
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.FTP-Port
-- Column: ExternalSystem_Config_SAP.FTP_Port
-- 2022-10-03T15:49:17.885Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-10-03 18:49:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613114
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.FTP-Benutzername
-- Column: ExternalSystem_Config_SAP.FTP_Username
-- 2022-10-03T15:49:17.889Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-10-03 18:49:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613115
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> FTP-Passwort
-- Column: ExternalSystem_Config_SAP.FTP_Password
-- 2022-10-03T15:52:47.012Z
UPDATE AD_Field SET IsEncrypted='N',Updated=TO_TIMESTAMP('2022-10-03 18:52:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707471
;

-- 2022-10-03T15:55:49.646Z
UPDATE AD_Element SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-10-03 18:55:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581531
;

-- 2022-10-03T15:55:49.648Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581531,'de_DE') 
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> FTP-Passwort
-- Column: ExternalSystem_Config_SAP.FTP_Password
-- 2022-10-03T15:56:03.055Z
UPDATE AD_Field SET IsEncrypted='Y',Updated=TO_TIMESTAMP('2022-10-03 18:56:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707471
;

-- Column: ExternalSystem_Config_SAP.FTP_Port
-- 2022-10-03T16:01:01.184Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2022-10-03 19:01:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584649
;

-- 2022-10-03T16:03:18.390Z
INSERT INTO t_alter_column values('externalsystem_config_sap','FTP_Port','VARCHAR(14)',null,null)
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.FTP-Passwort
-- Column: ExternalSystem_Config_SAP.FTP_Password
-- 2022-10-03T16:41:44.883Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2022-10-03 19:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613116
;

-- Name: External_Request SAP
-- 2022-10-04T11:11:47.987Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541661,TO_TIMESTAMP('2022-10-04 14:11:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','External_Request SAP',TO_TIMESTAMP('2022-10-04 14:11:47','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-10-04T11:11:48Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541661 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: External_Request SAP
-- Value: importProducts
-- ValueName: import
-- 2022-10-04T11:24:11.198Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543300,541661,TO_TIMESTAMP('2022-10-04 14:24:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Import Products',TO_TIMESTAMP('2022-10-04 14:24:11','YYYY-MM-DD HH24:MI:SS'),100,'importProducts','import')
;

-- 2022-10-04T11:24:11.203Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543300 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: External_Request SAP
-- Value: importProducts
-- ValueName: import
-- 2022-10-04T11:25:31.132Z
UPDATE AD_Ref_List SET Description='Import material (product) data from SAP',Updated=TO_TIMESTAMP('2022-10-04 14:25:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> importProducts_import
-- 2022-10-04T11:25:41.036Z
UPDATE AD_Ref_List_Trl SET Description='Import material (product) data from SAP',Updated=TO_TIMESTAMP('2022-10-04 14:25:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> importProducts_import
-- 2022-10-04T11:26:16.753Z
UPDATE AD_Ref_List_Trl SET Description='Import von Material- (Produkt-) Daten aus SAP', Name='Materialdaten importieren',Updated=TO_TIMESTAMP('2022-10-04 14:26:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> importProducts_import
-- 2022-10-04T11:26:40.500Z
UPDATE AD_Ref_List_Trl SET Description='Import von Material- (Produkt-) Daten aus SAP', Name='Materialdaten importieren',Updated=TO_TIMESTAMP('2022-10-04 14:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> importProducts_import
-- 2022-10-04T11:26:43.794Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-04 14:26:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> importProducts_import
-- 2022-10-04T11:26:53.724Z
UPDATE AD_Ref_List_Trl SET Description='Import material (product) data from SAP',Updated=TO_TIMESTAMP('2022-10-04 14:26:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543300
;

-- Reference: External_Request SAP
-- Value: importProducts
-- ValueName: import
-- 2022-10-04T11:27:32.556Z
UPDATE AD_Ref_List SET Description='Import von Material- (Produkt-) Daten aus SAP', Name='Materialdaten importieren',Updated=TO_TIMESTAMP('2022-10-04 14:27:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543300
;

-- Value: Call_external_system_SAP
-- 2022-10-04T11:31:11.551Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585117,'Y','N',TO_TIMESTAMP('2022-10-04 14:31:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'SAP Aufrufen','json','N','N','xls','Java',TO_TIMESTAMP('2022-10-04 14:31:11','YYYY-MM-DD HH24:MI:SS'),100,'Call_external_system_SAP')
;

-- 2022-10-04T11:31:11.557Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585117 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Call_external_system_SAP
-- 2022-10-04T11:31:41.785Z
UPDATE AD_Process_Trl SET Name='Call SAP',Updated=TO_TIMESTAMP('2022-10-04 14:31:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585117
;

-- Process: Call_external_system_SAP
-- 2022-10-04T11:31:47.162Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-04 14:31:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585117
;

-- Process: Call_external_system_SAP
-- 2022-10-04T11:32:08.850Z
UPDATE AD_Process_Trl SET Name='Call external system SAP',Updated=TO_TIMESTAMP('2022-10-04 14:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585117
;

-- 2022-10-04T11:36:33.699Z
UPDATE AD_Reference_Trl SET Name='SAP accepted operations',Updated=TO_TIMESTAMP('2022-10-04 14:36:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541661
;

-- 2022-10-04T11:36:36.959Z
UPDATE AD_Reference_Trl SET Name='SAP accepted operations',Updated=TO_TIMESTAMP('2022-10-04 14:36:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541661
;

-- 2022-10-04T11:36:39.587Z
UPDATE AD_Reference_Trl SET Name='SAP accepted operations',Updated=TO_TIMESTAMP('2022-10-04 14:36:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541661
;

-- 2022-10-04T11:36:42.987Z
UPDATE AD_Reference_Trl SET Name='SAP accepted operations',Updated=TO_TIMESTAMP('2022-10-04 14:36:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541661
;

-- Process: Call_external_system_SAP
-- ParameterName: External Request
-- 2022-10-04T11:41:03.483Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585117,542310,17,541661,'External Request',TO_TIMESTAMP('2022-10-04 14:41:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference',0,'Y','N','Y','N','Y','N','Befehl',10,TO_TIMESTAMP('2022-10-04 14:41:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-04T11:41:03.489Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542310 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-10-04T11:41:45.544Z
UPDATE AD_Process_Para_Trl SET Name='External Request',Updated=TO_TIMESTAMP('2022-10-04 14:41:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542310
;

-- 2022-10-04T11:42:09.155Z
UPDATE AD_Process_Para_Trl SET Name='External Request',Updated=TO_TIMESTAMP('2022-10-04 14:42:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542310
;

-- 2022-10-03T16:46:45.330Z
INSERT INTO ExternalSystem_Config (AD_Client_ID,AD_Org_ID,AuditFileFolder,Created,CreatedBy,ExternalSystem_Config_ID,IsActive,Name,Type,Updated,UpdatedBy,WriteAudit) VALUES (1000000,1000000,'/app/data/audit',TO_TIMESTAMP('2022-10-03 19:46:45','YYYY-MM-DD HH24:MI:SS'),100,540013,'Y','SAP','SAP',TO_TIMESTAMP('2022-10-03 19:46:45','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2022-10-03T16:46:48.813Z
INSERT INTO ExternalSystem_Config_SAP (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Config_ID,ExternalSystem_Config_SAP_ID,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2022-10-03 19:46:48','YYYY-MM-DD HH24:MI:SS'),100,540013,540009,'Y',TO_TIMESTAMP('2022-10-03 19:46:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-03T16:47:13.970Z
UPDATE ExternalSystem_Config_SAP SET ExternalSystemValue='SAP-Konfig',Updated=TO_TIMESTAMP('2022-10-03 19:47:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_SAP_ID=540009
;

-- 2022-10-03T16:47:51.425Z
UPDATE ExternalSystem_Config_SAP SET FTP_HostName='FTP-Hostname',Updated=TO_TIMESTAMP('2022-10-03 19:47:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_SAP_ID=540009
;

-- 2022-10-03T16:47:56.575Z
UPDATE ExternalSystem_Config_SAP SET FTP_Port='FTP-port',Updated=TO_TIMESTAMP('2022-10-03 19:47:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_SAP_ID=540009
;

-- 2022-10-03T16:48:04.013Z
UPDATE ExternalSystem_Config_SAP SET FTP_Username='FTP-',Updated=TO_TIMESTAMP('2022-10-03 19:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_SAP_ID=540009
;

-- 2022-10-03T16:48:12.095Z
UPDATE ExternalSystem_Config_SAP SET FTP_Username='FTP-Benutzername',Updated=TO_TIMESTAMP('2022-10-03 19:48:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_SAP_ID=540009
;

-- 2022-10-05T12:55:41.866672Z
UPDATE ExternalSystem_Config_SAP SET FTP_Password='FTP-Passwort',Updated=TO_TIMESTAMP('2022-10-05 15:55:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_SAP_ID=540009
;

-- 2022-10-03T16:52:54.720Z
UPDATE ExternalSystem_Config SET IsActive='N',Updated=TO_TIMESTAMP('2022-10-03 19:52:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_ID=540013
;

-- Process: Call_external_system_SAP
-- ParameterName: ChildConfigId
-- 2022-10-04T11:47:44.431Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585117,542311,10,'ChildConfigId',TO_TIMESTAMP('2022-10-04 14:47:44','YYYY-MM-DD HH24:MI:SS'),100,'ID of the "child" config, i.e. ExternalSystem_Config_SAP_ID; used when this process is running from AD_Scheduler','0=1','de.metas.externalsystem',0,'Y','N','Y','N','N','N','ChildConfigId',20,TO_TIMESTAMP('2022-10-04 14:47:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-04T11:47:44.435Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542311 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Call_external_system_SAP
-- Table: ExternalSystem_Config
-- EntityType: de.metas.externalsystem
-- 2022-10-04T11:50:36.004Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585117,541576,541286,TO_TIMESTAMP('2022-10-04 14:50:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',TO_TIMESTAMP('2022-10-04 14:50:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Process: Call_external_system_SAP
-- Table: ExternalSystem_Config_SAP
-- EntityType: de.metas.externalsystem
-- 2022-10-04T11:51:46.018Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585117,542238,541287,TO_TIMESTAMP('2022-10-04 14:51:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',TO_TIMESTAMP('2022-10-04 14:51:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Value: Call_external_system_SAP
-- Classname: de.metas.externalsystem.process.InvokeSAPAction
-- 2022-10-04T11:55:41.604Z
UPDATE AD_Process SET Classname='de.metas.externalsystem.process.InvokeSAPAction',Updated=TO_TIMESTAMP('2022-10-04 14:55:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585117
;

-- Column: ExternalSystem_Config_SAP.FTP_HostName
-- 2022-10-04T12:07:21.480Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-10-04 15:07:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584648
;

-- Column: ExternalSystem_Config_SAP.FTP_Port
-- 2022-10-04T12:07:35.344Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-10-04 15:07:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584649
;

-- Column: ExternalSystem_Config_SAP.FTP_Password
-- 2022-10-05T12:59:29.032189900Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-10-05 15:59:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584651
;

-- Column: ExternalSystem_Config_SAP.FTP_Username
-- 2022-10-05T13:00:21.275191800Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-10-05 16:00:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584650
;

-- Column: ExternalSystem_Config_SAP.ExternalSystemValue
-- 2022-10-05T13:29:44.450382700Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-10-05 16:29:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584644
;


-- Reference: External_Request SAP
-- Value: startProductsSync
-- ValueName: import
-- 2022-10-06T16:11:00.816633600Z
UPDATE AD_Ref_List SET Description='startet die Produktsynchronisation mit dem externen SAP-System', Name='Start der Produktsynchronisation', Value='startProductsSync',Updated=TO_TIMESTAMP('2022-10-06 19:11:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSync_import
-- 2022-10-06T16:11:29.481620900Z
UPDATE AD_Ref_List_Trl SET Name='Start der Produktsynchronisation',Updated=TO_TIMESTAMP('2022-10-06 19:11:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSync_import
-- 2022-10-06T16:11:36.654470200Z
UPDATE AD_Ref_List_Trl SET Name='Start der Produktsynchronisation',Updated=TO_TIMESTAMP('2022-10-06 19:11:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSync_import
-- 2022-10-06T16:11:49.686542200Z
UPDATE AD_Ref_List_Trl SET Name='Start Products Synchronization',Updated=TO_TIMESTAMP('2022-10-06 19:11:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSync_import
-- 2022-10-06T16:12:20.274173Z
UPDATE AD_Ref_List_Trl SET Description='Starts the product sychronization with SAP external system', Name='Start Products Synchronization',Updated=TO_TIMESTAMP('2022-10-06 19:12:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSync_import
-- 2022-10-06T16:12:25.390998200Z
UPDATE AD_Ref_List_Trl SET Description='starts the product sychronization with SAP external system',Updated=TO_TIMESTAMP('2022-10-06 19:12:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSync_import
-- 2022-10-06T16:12:31.292451300Z
UPDATE AD_Ref_List_Trl SET Description='Starts the products sychronization with SAP external system',Updated=TO_TIMESTAMP('2022-10-06 19:12:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSync_import
-- 2022-10-06T16:12:42.298618900Z
UPDATE AD_Ref_List_Trl SET Description='Starts the products sychronization with SAP external system',Updated=TO_TIMESTAMP('2022-10-06 19:12:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543300
;

-- Reference: External_Request SAP
-- Value: startProductsSync
-- ValueName: import
-- 2022-10-06T16:12:52.988929800Z
UPDATE AD_Ref_List SET Description='Startet die Produktsynchronisation mit dem externen SAP-System',Updated=TO_TIMESTAMP('2022-10-06 19:12:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSync_import
-- 2022-10-06T16:13:00.057474Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Produktsynchronisation mit dem externen SAP-System',Updated=TO_TIMESTAMP('2022-10-06 19:13:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSync_import
-- 2022-10-06T16:13:03.491405400Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Produktsynchronisation mit dem externen SAP-System',Updated=TO_TIMESTAMP('2022-10-06 19:13:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543300
;

-- Reference: External_Request SAP
-- Value: stopProductsSyncConsumer
-- ValueName: import
-- 2022-10-06T16:15:18.316475300Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543301,541661,TO_TIMESTAMP('2022-10-06 19:15:18','YYYY-MM-DD HH24:MI:SS'),100,'Stoppt die Produktsynchronisation mit dem externen SAP-System','de.metas.externalsystem','Y','Produktsynchronisation stoppen',TO_TIMESTAMP('2022-10-06 19:15:18','YYYY-MM-DD HH24:MI:SS'),100,'stopProductsSyncConsumer','import')
;

-- 2022-10-06T16:15:18.320477900Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543301 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: External_Request SAP
-- Value: stopProductsSync
-- ValueName: import
-- 2022-10-06T16:15:32.800523200Z
UPDATE AD_Ref_List SET Value='stopProductsSync',Updated=TO_TIMESTAMP('2022-10-06 19:15:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543301
;

-- Reference Item: External_Request SAP -> stopProductsSync_import
-- 2022-10-06T16:16:57.414412900Z
UPDATE AD_Ref_List_Trl SET Description='Stops the product synchronization with SAP external system', Name='Stop Product Synchronization',Updated=TO_TIMESTAMP('2022-10-06 19:16:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543301
;

-- Reference Item: External_Request SAP -> stopProductsSync_import
-- 2022-10-06T16:17:16.269273400Z
UPDATE AD_Ref_List_Trl SET Description='Stops the product synchronization with SAP external system', Name='Stop Product Synchronization',Updated=TO_TIMESTAMP('2022-10-06 19:17:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543301
;

-- Tab: External System Service(541340,de.metas.externalsystem) -> ExternalSystem_Service
-- Table: ExternalSystem_Service
-- 2022-10-07T09:47:38.182658200Z
UPDATE AD_Tab SET IsInsertRecord='Y', IsReadOnly='N',Updated=TO_TIMESTAMP('2022-10-07 12:47:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544882
;

-- Tab: External System Service(541340,de.metas.externalsystem) -> ExternalSystem_Service
-- Table: ExternalSystem_Service
-- 2022-10-07T10:48:41.352265400Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-10-07 13:48:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544882
;

-- 2022-10-07T10:45:09.687966200Z
INSERT INTO ExternalSystem_Service (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Service_ID,IsActive,Name,Type,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2022-10-07 13:45:09','YYYY-MM-DD HH24:MI:SS'),100,540002,'Y','SFTP Sync-Products','SAP',TO_TIMESTAMP('2022-10-07 13:45:09','YYYY-MM-DD HH24:MI:SS'),100,'SFTPSyncProducts')
;

-- 2022-10-07T10:45:12.897805900Z
UPDATE ExternalSystem_Service SET Description='SFTP Sync-Products',Updated=TO_TIMESTAMP('2022-10-07 13:45:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540002
;

-- 2022-10-07T10:46:07.923288900Z
UPDATE ExternalSystem_Service SET EnableCommand='startProductsSync',Updated=TO_TIMESTAMP('2022-10-07 13:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540002
;

-- 2022-10-07T10:46:17.838934100Z
UPDATE ExternalSystem_Service SET DisableCommand='stopProductsSync',Updated=TO_TIMESTAMP('2022-10-07 13:46:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540002
;

-- Reference: ExternalSystem
-- Value: SAP
-- ValueName: SAP
-- 2022-10-11T12:13:32.552498400Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543308,541117,TO_TIMESTAMP('2022-10-11 15:13:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y','SAP',TO_TIMESTAMP('2022-10-11 15:13:32','YYYY-MM-DD HH24:MI:SS'),100,'SAP','SAP')
;

-- 2022-10-11T12:13:32.556497600Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543308 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: ExternalSystem
-- Value: SAP
-- ValueName: SAP
-- 2022-10-11T12:13:47.834493500Z
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2022-10-11 15:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543308
;

-- Process: Call_external_system_SAP(de.metas.externalsystem.process.InvokeSAPAction)
-- ParameterName: External_Request
-- 2022-10-11T17:35:04.708134300Z
UPDATE AD_Process_Para SET AD_Element_ID=578757, ColumnName='External_Request',Updated=TO_TIMESTAMP('2022-10-11 20:35:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542310
;

COMMIT;

CREATE UNIQUE INDEX idx_s_externalsystemsap_unique_parent_id
    ON ExternalSystem_Config_SAP (externalsystem_config_id)
;

CREATE UNIQUE INDEX idx_s_externalsystemsap_unique_value
    ON ExternalSystem_Config_SAP (externalsystemvalue)
;

-- 2022-10-12T09:56:01.359333300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581546,0,'FTP_TargetDirectory',TO_TIMESTAMP('2022-10-12 12:56:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','FTP-Zielverzeichnis','FTP-Zielverzeichnis',TO_TIMESTAMP('2022-10-12 12:56:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-12T09:56:01.373784900Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581546 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: FTP_TargetDirectory
-- 2022-10-12T09:56:26.048693Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='FTP Target Directory', PrintName='FTP Target Directory',Updated=TO_TIMESTAMP('2022-10-12 12:56:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581546 AND AD_Language='en_US'
;

-- 2022-10-12T09:56:26.107635600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581546,'en_US')
;

-- Element: FTP_TargetDirectory
-- 2022-10-12T09:56:38.873090700Z
UPDATE AD_Element_Trl SET Name='FTP Target Directory', PrintName='FTP Target Directory',Updated=TO_TIMESTAMP('2022-10-12 12:56:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581546 AND AD_Language='nl_NL'
;

-- 2022-10-12T09:56:38.876195300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581546,'nl_NL')
;

-- Column: ExternalSystem_Config_SAP.FTP_TargetDirectory
-- 2022-10-12T09:58:06.321056700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584682,581546,0,10,542238,'FTP_TargetDirectory',TO_TIMESTAMP('2022-10-12 12:58:06','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'FTP-Zielverzeichnis',0,0,TO_TIMESTAMP('2022-10-12 12:58:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-12T09:58:06.330160Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584682 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-12T09:58:06.340034200Z
/* DDL */  select update_Column_Translation_From_AD_Element(581546)
;

-- 2022-10-12T09:59:14.710652600Z
UPDATE AD_Element SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-10-12 12:59:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581546
;

-- 2022-10-12T09:59:14.718626200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581546,'de_DE')
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> FTP-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.FTP_TargetDirectory
-- 2022-10-12T10:00:41.631667600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584682,707760,0,546647,0,TO_TIMESTAMP('2022-10-12 13:00:41','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','N','Y','N','N','N','N','FTP-Zielverzeichnis',0,100,0,1,1,TO_TIMESTAMP('2022-10-12 13:00:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-12T10:00:41.639316900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707760 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-12T10:00:41.646311900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581546)
;

-- 2022-10-12T10:00:41.663299200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707760
;

-- 2022-10-12T10:00:41.674466300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707760)
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.FTP-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.FTP_TargetDirectory
-- 2022-10-12T10:01:34.122323800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707760,0,546647,613230,549954,'F',TO_TIMESTAMP('2022-10-12 13:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'FTP-Zielverzeichnis',60,0,0,TO_TIMESTAMP('2022-10-12 13:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-12T10:09:54.749411900Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN FTP_TargetDirectory VARCHAR(255)')
;

-- Field: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> FTP-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.FTP_TargetDirectory
-- 2022-10-12T10:12:08.624651900Z
UPDATE AD_Field SET IsEncrypted='N',Updated=TO_TIMESTAMP('2022-10-12 13:12:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707760
;

-- 2022-10-13T13:45:57.494111800Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581553,0,'SFTP_HostName',TO_TIMESTAMP('2022-10-13 16:45:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SFTP-Hostname','SFTP-Hostname',TO_TIMESTAMP('2022-10-13 16:45:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-13T13:45:57.503763500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581553 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SFTP_HostName
-- 2022-10-13T13:46:56.392168100Z
UPDATE AD_Element_Trl SET Name='SFTP Hostname', PrintName='SFTP Hostname',Updated=TO_TIMESTAMP('2022-10-13 16:46:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='en_US'
;

-- 2022-10-13T13:46:56.416420700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'en_US')
;

-- Element: SFTP_HostName
-- 2022-10-13T13:47:04.470274200Z
UPDATE AD_Element_Trl SET Name='SFTP Hostname', PrintName='SFTP Hostname',Updated=TO_TIMESTAMP('2022-10-13 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='nl_NL'
;

-- 2022-10-13T13:47:04.473278300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'nl_NL')
;

-- Column: ExternalSystem_Config_SAP.SFTP_HostName
-- 2022-10-13T13:47:22.966313600Z
UPDATE AD_Column SET AD_Element_ID=581553, ColumnName='SFTP_HostName', Description=NULL, Help=NULL, Name='SFTP-Hostname',Updated=TO_TIMESTAMP('2022-10-13 16:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584648
;

-- 2022-10-13T13:47:22.969253700Z
UPDATE AD_Field SET Name='SFTP-Hostname', Description=NULL, Help=NULL WHERE AD_Column_ID=584648
;

-- 2022-10-13T13:47:22.972350300Z
/* DDL */  select update_Column_Translation_From_AD_Element(581553)
;

-- Column: ExternalSystem_Config_SAP.SFTP_HostName
-- 2022-10-13T13:48:41.897190400Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-10-13 16:48:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584648
;

-- 2022-10-13T13:48:42.670694Z
/* DDL */ select db_alter_table('ExternalSystem_Config_SAP', 'ALTER TABLE ExternalSystem_Config_SAP RENAME COLUMN FTP_HostName to SFTP_HostName;');
;

-- 2022-10-13T13:49:50.735158100Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581554,0,'SFTP_Password',TO_TIMESTAMP('2022-10-13 16:49:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SFTP-Passwort','SFTP-Passwort',TO_TIMESTAMP('2022-10-13 16:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-13T13:49:50.738157900Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581554 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SFTP_Password
-- 2022-10-13T13:50:09.689473200Z
UPDATE AD_Element_Trl SET Name='SFTP Password', PrintName='SFTP Password',Updated=TO_TIMESTAMP('2022-10-13 16:50:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581554 AND AD_Language='en_US'
;

-- 2022-10-13T13:50:09.693520300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581554,'en_US')
;

-- Element: SFTP_Password
-- 2022-10-13T13:50:19.871347600Z
UPDATE AD_Element_Trl SET Name='SFTP Password', PrintName='SFTP Password',Updated=TO_TIMESTAMP('2022-10-13 16:50:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581554 AND AD_Language='nl_NL'
;

-- 2022-10-13T13:50:19.874354900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581554,'nl_NL')
;

-- Column: ExternalSystem_Config_SAP.SFTP_Password
-- 2022-10-13T13:50:40.415373500Z
UPDATE AD_Column SET AD_Element_ID=581554, ColumnName='SFTP_Password', Description=NULL, Help=NULL, IsMandatory='N', Name='SFTP-Passwort',Updated=TO_TIMESTAMP('2022-10-13 16:50:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584651
;

-- 2022-10-13T13:50:40.416453400Z
UPDATE AD_Field SET Name='SFTP-Passwort', Description=NULL, Help=NULL WHERE AD_Column_ID=584651
;

-- 2022-10-13T13:50:40.417449700Z
/* DDL */  select update_Column_Translation_From_AD_Element(581554)
;

-- 2022-10-13T13:52:12.641458100Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581555,0,'SFTP_Port',TO_TIMESTAMP('2022-10-13 16:52:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SFTP-Port','SFTP-Port',TO_TIMESTAMP('2022-10-13 16:52:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-13T13:52:12.645565200Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581555 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SFTP_Port
-- 2022-10-13T13:52:22.106414300Z
UPDATE AD_Element_Trl SET Name='SFTP Port', PrintName='SFTP Port',Updated=TO_TIMESTAMP('2022-10-13 16:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581555 AND AD_Language='en_US'
;

-- 2022-10-13T13:52:22.108406600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581555,'en_US')
;

-- Element: SFTP_Port
-- 2022-10-13T13:52:28.308123200Z
UPDATE AD_Element_Trl SET Name='SFTP Port', PrintName='SFTP Port',Updated=TO_TIMESTAMP('2022-10-13 16:52:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581555 AND AD_Language='nl_NL'
;

-- 2022-10-13T13:52:28.310087800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581555,'nl_NL')
;

-- Column: ExternalSystem_Config_SAP.SFTP_Port
-- 2022-10-13T13:52:54.154463700Z
UPDATE AD_Column SET AD_Element_ID=581555, ColumnName='SFTP_Port', Description=NULL, Help=NULL, Name='SFTP-Port',Updated=TO_TIMESTAMP('2022-10-13 16:52:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584649
;

-- 2022-10-13T13:52:54.156423500Z
UPDATE AD_Field SET Name='SFTP-Port', Description=NULL, Help=NULL WHERE AD_Column_ID=584649
;

-- 2022-10-13T13:52:54.157386800Z
/* DDL */  select update_Column_Translation_From_AD_Element(581555)
;

-- 2022-10-13T13:53:24.302485300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581556,0,'SFTP_Username',TO_TIMESTAMP('2022-10-13 16:53:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SFTP-Benutzername','SFTP-Benutzername',TO_TIMESTAMP('2022-10-13 16:53:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-13T13:53:24.304471800Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581556 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SFTP_Username
-- 2022-10-13T13:53:46.758273500Z
UPDATE AD_Element_Trl SET Name='SFTP Username', PrintName='SFTP Username',Updated=TO_TIMESTAMP('2022-10-13 16:53:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='en_US'
;

-- 2022-10-13T13:53:46.760273400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'en_US')
;

-- Element: SFTP_Username
-- 2022-10-13T13:53:55.791462700Z
UPDATE AD_Element_Trl SET Name='SFTP Username', PrintName='SFTP Username',Updated=TO_TIMESTAMP('2022-10-13 16:53:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='nl_NL'
;

-- 2022-10-13T13:53:55.793460100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'nl_NL')
;

-- Column: ExternalSystem_Config_SAP.SFTP_Username
-- 2022-10-13T13:54:12.284836400Z
UPDATE AD_Column SET AD_Element_ID=581556, ColumnName='SFTP_Username', Description=NULL, Help=NULL, IsMandatory='N', Name='SFTP-Benutzername',Updated=TO_TIMESTAMP('2022-10-13 16:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584650
;

-- 2022-10-13T13:54:12.285884100Z
UPDATE AD_Field SET Name='SFTP-Benutzername', Description=NULL, Help=NULL WHERE AD_Column_ID=584650
;

-- 2022-10-13T13:54:12.286853200Z
/* DDL */  select update_Column_Translation_From_AD_Element(581556)
;

-- 2022-10-13T13:48:42.670694Z
/* DDL */ select db_alter_table('ExternalSystem_Config_SAP', 'ALTER TABLE ExternalSystem_Config_SAP RENAME COLUMN FTP_Username to SFTP_Username;');
;

-- Column: ExternalSystem_Config_SAP.SFTP_Port
-- 2022-10-13T13:54:32.787444600Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-10-13 16:54:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584649
;

-- 2022-10-13T13:54:33.683976700Z
/* DDL */ select db_alter_table('ExternalSystem_Config_SAP', 'ALTER TABLE ExternalSystem_Config_SAP RENAME COLUMN FTP_Port to SFTP_Port;');
;

-- 2022-10-13T13:55:21.565825400Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581557,0,'SFTP_TargetDirectory',TO_TIMESTAMP('2022-10-13 16:55:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SFTP-Zielverzeichnis','SFTP-Zielverzeichnis',TO_TIMESTAMP('2022-10-13 16:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-13T13:55:21.571526900Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581557 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SFTP_TargetDirectory
-- 2022-10-13T13:55:43.825906200Z
UPDATE AD_Element_Trl SET Name='SFTP Target Directory', PrintName='SFTP Target Directory',Updated=TO_TIMESTAMP('2022-10-13 16:55:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581557 AND AD_Language='en_US'
;

-- 2022-10-13T13:55:43.827913500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581557,'en_US')
;

-- Element: SFTP_TargetDirectory
-- 2022-10-13T13:55:51.380262Z
UPDATE AD_Element_Trl SET Name='SFTP Target Directory', PrintName='SFTP Target Directory',Updated=TO_TIMESTAMP('2022-10-13 16:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581557 AND AD_Language='nl_NL'
;

-- 2022-10-13T13:55:51.382261300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581557,'nl_NL')
;

-- Column: ExternalSystem_Config_SAP.SFTP_TargetDirectory
-- 2022-10-13T13:56:03.490647100Z
UPDATE AD_Column SET AD_Element_ID=581557, ColumnName='SFTP_TargetDirectory', Description=NULL, Help=NULL, Name='SFTP-Zielverzeichnis',Updated=TO_TIMESTAMP('2022-10-13 16:56:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584682
;

-- 2022-10-13T13:56:03.493551700Z
UPDATE AD_Field SET Name='SFTP-Zielverzeichnis', Description=NULL, Help=NULL WHERE AD_Column_ID=584682
;

-- 2022-10-13T13:56:03.494549400Z
/* DDL */  select update_Column_Translation_From_AD_Element(581557)
;

-- 2022-10-13T13:48:42.670694Z
/* DDL */ select db_alter_table('ExternalSystem_Config_SAP', 'ALTER TABLE ExternalSystem_Config_SAP RENAME COLUMN FTP_TargetDirectory to SFTP_TargetDirectory;');
;

-- 2022-10-13T13:48:42.670694Z
/* DDL */ select db_alter_table('ExternalSystem_Config_SAP', 'ALTER TABLE ExternalSystem_Config_SAP RENAME COLUMN FTP_Password to SFTP_Password;');
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Hostname
-- Column: ExternalSystem_Config_SAP.SFTP_HostName
-- 2022-10-13T14:21:48.042533500Z
UPDATE AD_UI_Element SET Name='SFTP-Hostname',Updated=TO_TIMESTAMP('2022-10-13 17:21:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613113
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Port
-- Column: ExternalSystem_Config_SAP.SFTP_Port
-- 2022-10-13T14:21:53.748728Z
UPDATE AD_UI_Element SET Name='SFTP-Port',Updated=TO_TIMESTAMP('2022-10-13 17:21:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613114
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Benutzername
-- Column: ExternalSystem_Config_SAP.SFTP_Username
-- 2022-10-13T14:21:57.878109100Z
UPDATE AD_UI_Element SET Name='SFTP-Benutzername',Updated=TO_TIMESTAMP('2022-10-13 17:21:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613115
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Passwort
-- Column: ExternalSystem_Config_SAP.SFTP_Password
-- 2022-10-13T14:22:07.910579300Z
UPDATE AD_UI_Element SET Name='SFTP-Passwort',Updated=TO_TIMESTAMP('2022-10-13 17:22:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613116
;

-- UI Element: External System Config(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.SFTP_TargetDirectory
-- 2022-10-13T14:22:11.581870900Z
UPDATE AD_UI_Element SET Name='SFTP-Zielverzeichnis',Updated=TO_TIMESTAMP('2022-10-13 17:22:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613230
;

-- 2022-10-13T14:09:49.241327900Z
UPDATE ExternalSystem_Config_SAP SET SFTP_HostName='SFTP-Benutzername',Updated=TO_TIMESTAMP('2022-10-13 17:09:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_SAP_ID=540009
;

-- 2022-10-13T14:10:15.670461400Z
UPDATE ExternalSystem_Config_SAP SET SFTP_Port='SFTP-Hostname',Updated=TO_TIMESTAMP('2022-10-13 17:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_SAP_ID=540009
;

-- 2022-10-13T14:10:29.153710500Z
UPDATE ExternalSystem_Config_SAP SET SFTP_Port='SFTP-Port',Updated=TO_TIMESTAMP('2022-10-13 17:10:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_SAP_ID=540009
;

-- 2022-10-13T14:10:36.696421500Z
UPDATE ExternalSystem_Config_SAP SET SFTP_HostName='SFTP-Hostname',Updated=TO_TIMESTAMP('2022-10-13 17:10:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_SAP_ID=540009
;

-- 2022-10-13T14:10:51.367938600Z
UPDATE ExternalSystem_Config_SAP SET SFTP_Username='SFTP-Benutzername',Updated=TO_TIMESTAMP('2022-10-13 17:10:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_SAP_ID=540009
;

-- 2022-10-13T14:11:04.196174300Z
UPDATE ExternalSystem_Config_SAP SET SFTP_Password='SFTP-Passwort',Updated=TO_TIMESTAMP('2022-10-13 17:11:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_SAP_ID=540009
;

/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

-- Column: ExternalSystem_Config_SAP.SFTP_HostName
-- 2022-10-13T14:49:14.339149300Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-10-13 17:49:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584648
;

-- Column: ExternalSystem_Config_SAP.SFTP_Password
-- 2022-10-13T14:49:19.005366600Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-10-13 17:49:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584651
;

-- Column: ExternalSystem_Config_SAP.SFTP_Port
-- 2022-10-13T14:49:23.420999Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-10-13 17:49:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584649
;

-- Column: ExternalSystem_Config_SAP.SFTP_Username
-- 2022-10-13T14:49:30.737852300Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-10-13 17:49:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584650
;

