-- 2021-10-22T09:01:56.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541420,'S',TO_TIMESTAMP('2021-10-22 12:01:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.ui.web.address.AllowPOBoxAddress',TO_TIMESTAMP('2021-10-22 12:01:56','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2021-10-22T09:02:42.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='When set to true, allows P.O. boxes to be used as shipping locations.',Updated=TO_TIMESTAMP('2021-10-22 12:02:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541420
;

-- 2021-10-22T09:07:17.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580091,0,'IsPOBoxNum',TO_TIMESTAMP('2021-10-22 12:07:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Is P.O. box','Is P.O. box',TO_TIMESTAMP('2021-10-22 12:07:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-22T09:07:17.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580091 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-10-22T09:07:49.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,577845,580091,0,20,162,'IsPOBoxNum',TO_TIMESTAMP('2021-10-22 12:07:48','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Is P.O. Box',0,0,TO_TIMESTAMP('2021-10-22 12:07:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-10-22T09:07:49.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=577845 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-10-22T09:07:49.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580091) 
;

-- 2021-10-22T09:08:03.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Location','ALTER TABLE public.C_Location ADD COLUMN IsPOBoxNum CHAR(1) DEFAULT ''N'' CHECK (IsPOBoxNum IN (''Y'',''N'')) NOT NULL')
;

-- 2021-10-22T09:15:37.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Postfach', PrintName='Postfach',Updated=TO_TIMESTAMP('2021-10-22 12:15:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580091 AND AD_Language='de_CH'
;

-- 2021-10-22T09:15:37.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580091,'de_CH') 
;

-- 2021-10-22T09:15:40.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Postfach', PrintName='Postfach',Updated=TO_TIMESTAMP('2021-10-22 12:15:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580091 AND AD_Language='de_DE'
;

-- 2021-10-22T09:15:40.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580091,'de_DE') 
;

-- 2021-10-22T09:15:40.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580091,'de_DE') 
;

-- 2021-10-22T09:15:40.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPOBoxNum', Name='Postfach', Description=NULL, Help=NULL WHERE AD_Element_ID=580091
;

-- 2021-10-22T09:15:40.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPOBoxNum', Name='Postfach', Description=NULL, Help=NULL, AD_Element_ID=580091 WHERE UPPER(ColumnName)='ISPOBOXNUM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-22T09:15:40.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPOBoxNum', Name='Postfach', Description=NULL, Help=NULL WHERE AD_Element_ID=580091 AND IsCentrallyMaintained='Y'
;

-- 2021-10-22T09:15:40.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Postfach', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580091) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580091)
;

-- 2021-10-22T09:15:40.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Postfach', Name='Postfach' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580091)
;

-- 2021-10-22T09:15:40.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Postfach', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580091
;

-- 2021-10-22T09:15:40.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Postfach', Description=NULL, Help=NULL WHERE AD_Element_ID = 580091
;

-- 2021-10-22T09:15:40.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Postfach', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580091
;

-- 2021-10-22T09:15:43.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Postfach', PrintName='Postfach',Updated=TO_TIMESTAMP('2021-10-22 12:15:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580091 AND AD_Language='nl_NL'
;

-- 2021-10-22T09:15:43.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580091,'nl_NL') 
;

-- 2021-10-22T09:29:27.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Postfach Nummer',Updated=TO_TIMESTAMP('2021-10-22 12:29:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=55445 AND AD_Language='fr_CH'
;

-- 2021-10-22T09:29:27.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(55445,'fr_CH') 
;

-- 2021-10-22T09:29:29.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Postfach Nummer',Updated=TO_TIMESTAMP('2021-10-22 12:29:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=55445 AND AD_Language='it_CH'
;

-- 2021-10-22T09:29:29.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(55445,'it_CH') 
;

-- 2021-10-22T09:29:32.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Postfach Nummer', PrintName='Postfach Nummer',Updated=TO_TIMESTAMP('2021-10-22 12:29:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=55445 AND AD_Language='de_CH'
;

-- 2021-10-22T09:29:32.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(55445,'de_CH') 
;

-- 2021-10-22T09:29:33.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Postfach Nummer',Updated=TO_TIMESTAMP('2021-10-22 12:29:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=55445 AND AD_Language='it_CH'
;

-- 2021-10-22T09:29:33.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(55445,'it_CH') 
;

-- 2021-10-22T09:29:35.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Postfach Nummer',Updated=TO_TIMESTAMP('2021-10-22 12:29:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=55445 AND AD_Language='fr_CH'
;

-- 2021-10-22T09:29:35.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(55445,'fr_CH') 
;

-- 2021-10-22T09:29:37.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Postfach Nummer', PrintName='Postfach Nummer',Updated=TO_TIMESTAMP('2021-10-22 12:29:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=55445 AND AD_Language='nl_NL'
;

-- 2021-10-22T09:29:37.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(55445,'nl_NL') 
;

-- 2021-10-22T09:29:43.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Postfach Nummer', PrintName='Postfach Nummer',Updated=TO_TIMESTAMP('2021-10-22 12:29:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=55445 AND AD_Language='de_DE'
;

-- 2021-10-22T09:29:43.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(55445,'de_DE') 
;

-- 2021-10-22T09:29:43.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(55445,'de_DE') 
;

-- 2021-10-22T09:29:43.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='POBox', Name='Postfach Nummer', Description=NULL, Help=NULL WHERE AD_Element_ID=55445
;

-- 2021-10-22T09:29:43.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='POBox', Name='Postfach Nummer', Description=NULL, Help=NULL, AD_Element_ID=55445 WHERE UPPER(ColumnName)='POBOX' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-22T09:29:43.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='POBox', Name='Postfach Nummer', Description=NULL, Help=NULL WHERE AD_Element_ID=55445 AND IsCentrallyMaintained='Y'
;

-- 2021-10-22T09:29:43.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Postfach Nummer', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=55445) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 55445)
;

-- 2021-10-22T09:29:43.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Postfach Nummer', Name='Postfach Nummer' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=55445)
;

-- 2021-10-22T09:29:43.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Postfach Nummer', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 55445
;

-- 2021-10-22T09:29:43.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Postfach Nummer', Description=NULL, Help=NULL WHERE AD_Element_ID = 55445
;

-- 2021-10-22T09:29:43.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Postfach Nummer', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 55445
;

-- 2021-10-22T09:29:45.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='P.O. box number', PrintName='P.O. box number',Updated=TO_TIMESTAMP('2021-10-22 12:29:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=55445 AND AD_Language='en_US'
;

-- 2021-10-22T09:29:45.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(55445,'en_US') 
;

-- 2021-10-22T09:29:48.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='P.O. box number', PrintName='P.O. box number',Updated=TO_TIMESTAMP('2021-10-22 12:29:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=55445 AND AD_Language='en_GB'
;

-- 2021-10-22T09:29:48.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(55445,'en_GB') 
;

-- 2021-10-22T09:30:20.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ist Postfach', PrintName='Ist Postfach',Updated=TO_TIMESTAMP('2021-10-22 12:30:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580091 AND AD_Language='nl_NL'
;

-- 2021-10-22T09:30:20.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580091,'nl_NL') 
;

-- 2021-10-22T09:30:22.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ist Postfach', PrintName='Ist Postfach',Updated=TO_TIMESTAMP('2021-10-22 12:30:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580091 AND AD_Language='de_DE'
;

-- 2021-10-22T09:30:22.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580091,'de_DE') 
;

-- 2021-10-22T09:30:22.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580091,'de_DE') 
;

-- 2021-10-22T09:30:22.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPOBoxNum', Name='Ist Postfach', Description=NULL, Help=NULL WHERE AD_Element_ID=580091
;

-- 2021-10-22T09:30:22.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPOBoxNum', Name='Ist Postfach', Description=NULL, Help=NULL, AD_Element_ID=580091 WHERE UPPER(ColumnName)='ISPOBOXNUM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-22T09:30:22.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPOBoxNum', Name='Ist Postfach', Description=NULL, Help=NULL WHERE AD_Element_ID=580091 AND IsCentrallyMaintained='Y'
;

-- 2021-10-22T09:30:22.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ist Postfach', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580091) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580091)
;

-- 2021-10-22T09:30:22.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ist Postfach', Name='Ist Postfach' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580091)
;

-- 2021-10-22T09:30:22.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ist Postfach', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580091
;

-- 2021-10-22T09:30:22.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ist Postfach', Description=NULL, Help=NULL WHERE AD_Element_ID = 580091
;

-- 2021-10-22T09:30:22.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ist Postfach', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580091
;

-- 2021-10-22T09:30:24.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ist Postfach', PrintName='Ist Postfach',Updated=TO_TIMESTAMP('2021-10-22 12:30:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580091 AND AD_Language='de_CH'
;

-- 2021-10-22T09:30:24.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580091,'de_CH') 
;

-- 2021-10-22T14:02:55.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Is P.O. box', PrintName='Is P.O. box',Updated=TO_TIMESTAMP('2021-10-22 17:02:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580091 AND AD_Language='en_US'
;

-- 2021-10-22T14:02:55.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580091,'en_US') 
;

-- 2021-10-25T09:05:57.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545071,0,TO_TIMESTAMP('2021-10-25 12:05:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Postfach','I',TO_TIMESTAMP('2021-10-25 12:05:57','YYYY-MM-DD HH24:MI:SS'),100,'MSG_POBox')
;

-- 2021-10-25T09:05:57.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545071 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-10-25T09:06:06.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='P.O. box',Updated=TO_TIMESTAMP('2021-10-25 12:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545071
;
 