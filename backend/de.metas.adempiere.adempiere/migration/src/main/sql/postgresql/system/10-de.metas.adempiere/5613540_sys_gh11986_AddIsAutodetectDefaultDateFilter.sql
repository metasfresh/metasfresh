-- 2021-11-15T13:19:04.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580226,0,'IsAutodetectDefaultDateFilter',TO_TIMESTAMP('2021-11-15 15:19:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Autodetect Default Date Filter ','Autodetect Default Date Filter ',TO_TIMESTAMP('2021-11-15 15:19:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T13:19:04.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580226 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-11-15T13:19:16.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-11-15 15:19:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580226 AND AD_Language='de_CH'
;

-- 2021-11-15T13:19:16.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580226,'de_CH') 
;

-- 2021-11-15T13:19:28.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Standard-Datumsfilter automatisch erkennen', PrintName='Standard-Datumsfilter automatisch erkennen',Updated=TO_TIMESTAMP('2021-11-15 15:19:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580226 AND AD_Language='de_DE'
;

-- 2021-11-15T13:19:28.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580226,'de_DE') 
;

-- 2021-11-15T13:19:28.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580226,'de_DE') 
;

-- 2021-11-15T13:19:28.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAutodetectDefaultDateFilter', Name='Standard-Datumsfilter automatisch erkennen', Description=NULL, Help=NULL WHERE AD_Element_ID=580226
;

-- 2021-11-15T13:19:28.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutodetectDefaultDateFilter', Name='Standard-Datumsfilter automatisch erkennen', Description=NULL, Help=NULL, AD_Element_ID=580226 WHERE UPPER(ColumnName)='ISAUTODETECTDEFAULTDATEFILTER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-15T13:19:29.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutodetectDefaultDateFilter', Name='Standard-Datumsfilter automatisch erkennen', Description=NULL, Help=NULL WHERE AD_Element_ID=580226 AND IsCentrallyMaintained='Y'
;

-- 2021-11-15T13:19:29.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Standard-Datumsfilter automatisch erkennen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580226) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580226)
;

-- 2021-11-15T13:19:29.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Standard-Datumsfilter automatisch erkennen', Name='Standard-Datumsfilter automatisch erkennen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580226)
;

-- 2021-11-15T13:19:29.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Standard-Datumsfilter automatisch erkennen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580226
;

-- 2021-11-15T13:19:29.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Standard-Datumsfilter automatisch erkennen', Description=NULL, Help=NULL WHERE AD_Element_ID = 580226
;

-- 2021-11-15T13:19:29.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Standard-Datumsfilter automatisch erkennen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580226
;

-- 2021-11-15T13:19:31.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Standard-Datumsfilter automatisch erkennen', PrintName='Standard-Datumsfilter automatisch erkennen',Updated=TO_TIMESTAMP('2021-11-15 15:19:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580226 AND AD_Language='de_CH'
;

-- 2021-11-15T13:19:31.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580226,'de_CH') 
;

-- 2021-11-15T13:19:56.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Autodetect Default Date Filter ', PrintName='Autodetect Default Date Filter ',Updated=TO_TIMESTAMP('2021-11-15 15:19:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580226
;

-- 2021-11-15T13:19:56.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAutodetectDefaultDateFilter', Name='Autodetect Default Date Filter ', Description=NULL, Help=NULL WHERE AD_Element_ID=580226
;

-- 2021-11-15T13:19:56.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutodetectDefaultDateFilter', Name='Autodetect Default Date Filter ', Description=NULL, Help=NULL, AD_Element_ID=580226 WHERE UPPER(ColumnName)='ISAUTODETECTDEFAULTDATEFILTER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-15T13:19:56.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutodetectDefaultDateFilter', Name='Autodetect Default Date Filter ', Description=NULL, Help=NULL WHERE AD_Element_ID=580226 AND IsCentrallyMaintained='Y'
;

-- 2021-11-15T13:19:56.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Autodetect Default Date Filter ', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580226) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580226)
;

-- 2021-11-15T13:19:56.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Autodetect Default Date Filter ', Name='Autodetect Default Date Filter ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580226)
;

-- 2021-11-15T13:19:56.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Autodetect Default Date Filter ', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580226
;

-- 2021-11-15T13:19:56.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Autodetect Default Date Filter ', Description=NULL, Help=NULL WHERE AD_Element_ID = 580226
;

-- 2021-11-15T13:19:56.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Autodetect Default Date Filter ', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580226
;

-- 2021-11-15T13:20:13.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578410,580226,0,15,106,'IsAutodetectDefaultDateFilter',TO_TIMESTAMP('2021-11-15 15:20:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Autodetect Default Date Filter ',0,0,TO_TIMESTAMP('2021-11-15 15:20:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-11-15T13:20:13.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578410 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-11-15T13:20:13.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580226) 
;

-- 2021-11-15T13:20:27.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='''Y''',Updated=TO_TIMESTAMP('2021-11-15 15:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578410
;

-- 2021-11-15T13:20:43.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=20, DefaultValue='Y', FieldLength=1,Updated=TO_TIMESTAMP('2021-11-15 15:20:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578410
;

-- 2021-11-15T13:20:48.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Tab','ALTER TABLE public.AD_Tab ADD COLUMN IsAutodetectDefaultDateFilter CHAR(1) DEFAULT ''Y'' CHECK (IsAutodetectDefaultDateFilter IN (''Y'',''N'')) NOT NULL')
;

-- 2021-11-15T15:46:22.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,578410,669113,0,106,0,TO_TIMESTAMP('2021-11-15 17:46:22','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Autodetect Default Date Filter ',0,440,0,1,1,TO_TIMESTAMP('2021-11-15 17:46:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T15:46:22.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=669113 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-11-15T15:46:22.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580226) 
;

-- 2021-11-15T15:46:22.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=669113
;

-- 2021-11-15T15:46:22.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(669113)
;

-- 2021-11-19T12:15:57.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=440, SeqNoGrid=NULL,Updated=TO_TIMESTAMP('2021-11-19 14:15:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669113
;

-- 2021-11-19T12:16:03.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=440,Updated=TO_TIMESTAMP('2021-11-19 14:16:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669113
;


