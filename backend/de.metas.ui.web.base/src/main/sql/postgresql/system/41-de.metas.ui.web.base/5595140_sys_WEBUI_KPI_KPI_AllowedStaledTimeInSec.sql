-- 2021-06-26T04:33:13.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579396,0,'KPI_AllowedStaledTimeInMin',TO_TIMESTAMP('2021-06-26 07:33:13','YYYY-MM-DD HH24:MI:SS'),100,'For how long time this KPI is allowed to be staled so no computation is needed','D','Y','Allow to be staled time (min)','Allow to be staled time (min)',TO_TIMESTAMP('2021-06-26 07:33:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-26T04:33:13.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579396 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-26T04:33:28.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='KPI_AllowedStaledTimeInSec',Updated=TO_TIMESTAMP('2021-06-26 07:33:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579396
;

-- 2021-06-26T04:33:28.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='KPI_AllowedStaledTimeInSec', Name='Allow to be staled time (min)', Description='For how long time this KPI is allowed to be staled so no computation is needed', Help=NULL WHERE AD_Element_ID=579396
;

-- 2021-06-26T04:33:28.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='KPI_AllowedStaledTimeInSec', Name='Allow to be staled time (min)', Description='For how long time this KPI is allowed to be staled so no computation is needed', Help=NULL, AD_Element_ID=579396 WHERE UPPER(ColumnName)='KPI_ALLOWEDSTALEDTIMEINSEC' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-26T04:33:28.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='KPI_AllowedStaledTimeInSec', Name='Allow to be staled time (min)', Description='For how long time this KPI is allowed to be staled so no computation is needed', Help=NULL WHERE AD_Element_ID=579396 AND IsCentrallyMaintained='Y'
;

-- 2021-06-26T04:33:36.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Allow to be staled time (sec)', PrintName='Allow to be staled time (sec)',Updated=TO_TIMESTAMP('2021-06-26 07:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579396 AND AD_Language='de_CH'
;

-- 2021-06-26T04:33:36.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579396,'de_CH') 
;

-- 2021-06-26T04:33:41.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Allow to be staled time (sec)', PrintName='Allow to be staled time (sec)',Updated=TO_TIMESTAMP('2021-06-26 07:33:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579396 AND AD_Language='de_DE'
;

-- 2021-06-26T04:33:41.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579396,'de_DE') 
;

-- 2021-06-26T04:33:41.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579396,'de_DE') 
;

-- 2021-06-26T04:33:41.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='KPI_AllowedStaledTimeInSec', Name='Allow to be staled time (sec)', Description='For how long time this KPI is allowed to be staled so no computation is needed', Help=NULL WHERE AD_Element_ID=579396
;

-- 2021-06-26T04:33:41.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='KPI_AllowedStaledTimeInSec', Name='Allow to be staled time (sec)', Description='For how long time this KPI is allowed to be staled so no computation is needed', Help=NULL, AD_Element_ID=579396 WHERE UPPER(ColumnName)='KPI_ALLOWEDSTALEDTIMEINSEC' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-26T04:33:41.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='KPI_AllowedStaledTimeInSec', Name='Allow to be staled time (sec)', Description='For how long time this KPI is allowed to be staled so no computation is needed', Help=NULL WHERE AD_Element_ID=579396 AND IsCentrallyMaintained='Y'
;

-- 2021-06-26T04:33:41.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Allow to be staled time (sec)', Description='For how long time this KPI is allowed to be staled so no computation is needed', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579396) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579396)
;

-- 2021-06-26T04:33:42.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Allow to be staled time (sec)', Name='Allow to be staled time (sec)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579396)
;

-- 2021-06-26T04:33:42.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Allow to be staled time (sec)', Description='For how long time this KPI is allowed to be staled so no computation is needed', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579396
;

-- 2021-06-26T04:33:42.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Allow to be staled time (sec)', Description='For how long time this KPI is allowed to be staled so no computation is needed', Help=NULL WHERE AD_Element_ID = 579396
;

-- 2021-06-26T04:33:42.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Allow to be staled time (sec)', Description = 'For how long time this KPI is allowed to be staled so no computation is needed', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579396
;

-- 2021-06-26T04:33:50.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Allow to be staled time (sec)', PrintName='Allow to be staled time (sec)',Updated=TO_TIMESTAMP('2021-06-26 07:33:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579396 AND AD_Language='en_US'
;

-- 2021-06-26T04:33:50.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579396,'en_US') 
;

-- 2021-06-26T04:33:53.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-26 07:33:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579396 AND AD_Language='en_US'
;

-- 2021-06-26T04:33:53.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579396,'en_US') 
;

-- 2021-06-26T04:33:59.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Allow to be staled time (sec)', PrintName='Allow to be staled time (sec)',Updated=TO_TIMESTAMP('2021-06-26 07:33:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579396 AND AD_Language='nl_NL'
;

-- 2021-06-26T04:33:59.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579396,'nl_NL') 
;

-- 2021-06-26T04:34:37.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574688,579396,0,11,540801,'KPI_AllowedStaledTimeInSec',TO_TIMESTAMP('2021-06-26 07:34:37','YYYY-MM-DD HH24:MI:SS'),100,'N','1','For how long time this KPI is allowed to be staled so no computation is needed','de.metas.ui.web',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Allow to be staled time (sec)',0,0,TO_TIMESTAMP('2021-06-26 07:34:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-26T04:34:37.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574688 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-26T04:34:37.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579396) 
;

-- 2021-06-26T04:34:38.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('WEBUI_KPI','ALTER TABLE public.WEBUI_KPI ADD COLUMN KPI_AllowedStaledTimeInSec NUMERIC(10) DEFAULT 1 NOT NULL')
;

-- 2021-06-26T04:34:54.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574688,649836,0,540788,TO_TIMESTAMP('2021-06-26 07:34:53','YYYY-MM-DD HH24:MI:SS'),100,'For how long time this KPI is allowed to be staled so no computation is needed',14,'de.metas.ui.web','Y','N','N','N','N','N','N','N','Allow to be staled time (sec)',TO_TIMESTAMP('2021-06-26 07:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-26T04:34:54.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649836 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-26T04:34:54.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579396) 
;

-- 2021-06-26T04:34:54.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649836
;

-- 2021-06-26T04:34:54.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(649836)
;

-- 2021-06-26T04:35:11.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2021-06-26 07:35:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649836
;

-- 2021-06-26T04:35:45.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540656
;

-- 2021-06-26T04:36:10.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540381,546160,TO_TIMESTAMP('2021-06-26 07:36:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','Performance tunning',20,TO_TIMESTAMP('2021-06-26 07:36:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-26T04:36:24.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,649836,0,540788,586873,546160,'F',TO_TIMESTAMP('2021-06-26 07:36:24','YYYY-MM-DD HH24:MI:SS'),100,'For how long time this KPI is allowed to be staled so no computation is needed','Y','N','Y','N','N','Allow to be staled time (sec)',10,0,0,TO_TIMESTAMP('2021-06-26 07:36:24','YYYY-MM-DD HH24:MI:SS'),100)
;

