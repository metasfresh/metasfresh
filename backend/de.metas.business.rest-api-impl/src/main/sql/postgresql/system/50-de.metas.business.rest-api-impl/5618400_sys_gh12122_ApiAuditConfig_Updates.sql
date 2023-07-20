-- 2021-12-11T12:39:28.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=598408
;

-- 2021-12-11T12:39:28.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=675204
;

-- 2021-12-11T12:39:28.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=675204
;

-- 2021-12-11T12:39:28.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=675204
;

-- 2021-12-11T12:39:28.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('API_Audit_Config','ALTER TABLE API_Audit_Config DROP COLUMN IF EXISTS WrapApiResponse')
;

-- 2021-12-11T12:39:29.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=578897
;

-- 2021-12-11T12:39:29.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=578897
;

-- 2021-12-11T12:39:44.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsWrapApiResponse',Updated=TO_TIMESTAMP('2021-12-11 14:39:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580371
;

-- 2021-12-11T12:39:44.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsWrapApiResponse', Name='Wrap response', Description=NULL, Help=NULL WHERE AD_Element_ID=580371
;

-- 2021-12-11T12:39:44.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsWrapApiResponse', Name='Wrap response', Description=NULL, Help=NULL, AD_Element_ID=580371 WHERE UPPER(ColumnName)='ISWRAPAPIRESPONSE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-11T12:39:44.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsWrapApiResponse', Name='Wrap response', Description=NULL, Help=NULL WHERE AD_Element_ID=580371 AND IsCentrallyMaintained='Y'
;

-- 2021-12-11T12:40:28.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578920,580371,0,20,541635,'IsWrapApiResponse',TO_TIMESTAMP('2021-12-11 14:40:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Wrap response',0,0,TO_TIMESTAMP('2021-12-11 14:40:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-12-11T12:40:28.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578920 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-12-11T12:40:28.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580371) 
;

-- 2021-12-11T12:40:30.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('API_Audit_Config','ALTER TABLE public.API_Audit_Config ADD COLUMN IsWrapApiResponse CHAR(1) DEFAULT ''Y'' CHECK (IsWrapApiResponse IN (''Y'',''N'')) NOT NULL')
;

-- 2021-12-11T12:41:06.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=598407
;

-- 2021-12-11T12:41:06.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=675203
;

-- 2021-12-11T12:41:06.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=675203
;

-- 2021-12-11T12:41:06.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=675203
;

-- 2021-12-11T12:41:06.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('API_Audit_Config','ALTER TABLE API_Audit_Config DROP COLUMN IF EXISTS BypassAudit')
;

-- 2021-12-11T12:41:06.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=578896
;

-- 2021-12-11T12:41:06.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=578896
;

-- 2021-12-11T12:41:16.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsSynchronousAuditLoggingEnabled',Updated=TO_TIMESTAMP('2021-12-11 14:41:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370
;

-- 2021-12-11T12:41:16.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Bypass audit', Description=NULL, Help=NULL WHERE AD_Element_ID=580370
;

-- 2021-12-11T12:41:16.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Bypass audit', Description=NULL, Help=NULL, AD_Element_ID=580370 WHERE UPPER(ColumnName)='ISSYNCHRONOUSAUDITLOGGINGENABLED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-11T12:41:16.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Bypass audit', Description=NULL, Help=NULL WHERE AD_Element_ID=580370 AND IsCentrallyMaintained='Y'
;

-- 2021-12-11T12:41:42.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Synchronous audit enabled', PrintName='Synchronous audit enabled',Updated=TO_TIMESTAMP('2021-12-11 14:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='de_CH'
;

-- 2021-12-11T12:41:42.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'de_CH') 
;

-- 2021-12-11T12:41:48.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Synchronous audit enabled', PrintName='Synchronous audit enabled',Updated=TO_TIMESTAMP('2021-12-11 14:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='de_DE'
;

-- 2021-12-11T12:41:48.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'de_DE') 
;

-- 2021-12-11T12:41:48.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580370,'de_DE') 
;

-- 2021-12-11T12:41:48.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Synchronous audit enabled', Description=NULL, Help=NULL WHERE AD_Element_ID=580370
;

-- 2021-12-11T12:41:48.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Synchronous audit enabled', Description=NULL, Help=NULL, AD_Element_ID=580370 WHERE UPPER(ColumnName)='ISSYNCHRONOUSAUDITLOGGINGENABLED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-11T12:41:48.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSynchronousAuditLoggingEnabled', Name='Synchronous audit enabled', Description=NULL, Help=NULL WHERE AD_Element_ID=580370 AND IsCentrallyMaintained='Y'
;

-- 2021-12-11T12:41:48.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Synchronous audit enabled', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580370) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580370)
;

-- 2021-12-11T12:41:48.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Synchronous audit enabled', Name='Synchronous audit enabled' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580370)
;

-- 2021-12-11T12:41:48.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Synchronous audit enabled', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580370
;

-- 2021-12-11T12:41:48.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Synchronous audit enabled', Description=NULL, Help=NULL WHERE AD_Element_ID = 580370
;

-- 2021-12-11T12:41:48.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Synchronous audit enabled', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580370
;

-- 2021-12-11T12:41:52.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Synchronous audit enabled', PrintName='Synchronous audit enabled',Updated=TO_TIMESTAMP('2021-12-11 14:41:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='en_US'
;

-- 2021-12-11T12:41:52.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'en_US') 
;

-- 2021-12-11T12:41:55.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Synchronous audit enabled', PrintName='Synchronous audit enabled',Updated=TO_TIMESTAMP('2021-12-11 14:41:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580370 AND AD_Language='nl_NL'
;

-- 2021-12-11T12:41:55.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580370,'nl_NL') 
;

-- 2021-12-11T12:42:27.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578921,580370,0,20,541635,'IsSynchronousAuditLoggingEnabled',TO_TIMESTAMP('2021-12-11 14:42:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Synchronous audit enabled',0,0,TO_TIMESTAMP('2021-12-11 14:42:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-12-11T12:42:27.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578921 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-12-11T12:42:27.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580370) 
;

-- 2021-12-11T12:42:29.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('API_Audit_Config','ALTER TABLE public.API_Audit_Config ADD COLUMN IsSynchronousAuditLoggingEnabled CHAR(1) DEFAULT ''Y'' CHECK (IsSynchronousAuditLoggingEnabled IN (''Y'',''N'')) NOT NULL')
;

-- 2021-12-11T12:42:43.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','IsWrapApiResponse','CHAR(1)',null,'Y')
;

-- 2021-12-11T12:42:43.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET IsWrapApiResponse='Y' WHERE IsWrapApiResponse IS NULL
;

-- 2021-12-11T12:43:50.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsSynchronousAuditLoggingEnabled/''N''@=''N''',Updated=TO_TIMESTAMP('2021-12-11 14:43:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578920
;

-- 2021-12-11T12:44:02.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsSynchronousAuditLoggingEnabled/''N''@=''N''',Updated=TO_TIMESTAMP('2021-12-11 14:44:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573731
;

-- 2021-12-11T12:44:35.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578920,675216,0,543895,TO_TIMESTAMP('2021-12-11 14:44:35','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Wrap response',TO_TIMESTAMP('2021-12-11 14:44:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-11T12:44:35.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=675216 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-11T12:44:35.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580371) 
;

-- 2021-12-11T12:44:35.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=675216
;

-- 2021-12-11T12:44:35.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(675216)
;

-- 2021-12-11T12:44:35.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578921,675217,0,543895,TO_TIMESTAMP('2021-12-11 14:44:35','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Synchronous audit enabled',TO_TIMESTAMP('2021-12-11 14:44:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-11T12:44:35.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=675217 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-11T12:44:35.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580370) 
;

-- 2021-12-11T12:44:35.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=675217
;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

-- 2021-12-11T12:44:35.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(675217)
;

-- 2021-12-11T12:45:02.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,675217,0,543895,598421,545769,'F',TO_TIMESTAMP('2021-12-11 14:45:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Synchronous audit enabled',20,0,0,TO_TIMESTAMP('2021-12-11 14:45:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-11T12:45:08.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,675216,0,543895,598422,545769,'F',TO_TIMESTAMP('2021-12-11 14:45:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Wrap response',30,0,0,TO_TIMESTAMP('2021-12-11 14:45:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-11T12:45:20.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2021-12-11 14:45:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584431
;

-- 2021-12-11T12:45:26.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2021-12-11 14:45:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=598422
;

-- 2021-12-11T12:45:29.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2021-12-11 14:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=598421
;

-- 2021-12-11T12:45:34.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-12-11 14:45:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584431
;

-- 2021-12-11T12:45:55.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2021-12-11 14:45:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=598422
;

-- 2021-12-11T12:45:58.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2021-12-11 14:45:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=598421
;

-- 2021-12-11T13:21:40.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2021-12-11 15:21:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578921
;

