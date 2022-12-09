-- 2021-11-15T13:07:13.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu GRS gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Name='Geschäftspartner senden', PrintName='Geschäftspartner senden',Updated=TO_TIMESTAMP('2021-11-15 15:07:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580213 AND AD_Language='de_CH'
;

-- 2021-11-15T13:07:13.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580213,'de_CH') 
;

-- 2021-11-15T13:07:26.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu GRS gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Name='Geschäftspartner senden', PrintName='Geschäftspartner senden',Updated=TO_TIMESTAMP('2021-11-15 15:07:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580213 AND AD_Language='de_DE'
;

-- 2021-11-15T13:07:26.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580213,'de_DE') 
;

-- 2021-11-15T13:07:26.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580213,'de_DE') 
;

-- 2021-11-15T13:07:26.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSyncBPartnersToRestEndpoint', Name='Geschäftspartner senden', Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu GRS gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL WHERE AD_Element_ID=580213
;

-- 2021-11-15T13:07:26.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncBPartnersToRestEndpoint', Name='Geschäftspartner senden', Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu GRS gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL, AD_Element_ID=580213 WHERE UPPER(ColumnName)='ISSYNCBPARTNERSTORESTENDPOINT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-15T13:07:26.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncBPartnersToRestEndpoint', Name='Geschäftspartner senden', Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu GRS gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL WHERE AD_Element_ID=580213 AND IsCentrallyMaintained='Y'
;

-- 2021-11-15T13:07:26.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geschäftspartner senden', Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu GRS gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580213) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580213)
;

-- 2021-11-15T13:07:26.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geschäftspartner senden', Name='Geschäftspartner senden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580213)
;

-- 2021-11-15T13:07:26.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geschäftspartner senden', Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu GRS gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580213
;

-- 2021-11-15T13:07:26.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geschäftspartner senden', Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu GRS gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL WHERE AD_Element_ID = 580213
;

-- 2021-11-15T13:07:26.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geschäftspartner senden', Description = 'Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu GRS gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580213
;

-- 2021-11-15T13:07:50.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If checked, then business selected partners can be initially send to GRS via an action in the business partner window. Once initially send, they will from there onwards be automatically send whenever changed in metasfresh.', Name='Send business partners', PrintName='Send business partners',Updated=TO_TIMESTAMP('2021-11-15 15:07:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580213 AND AD_Language='en_US'
;

-- 2021-11-15T13:07:50.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580213,'en_US') 
;

-- 2021-11-15T13:08:02.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu GRS gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Name='Geschäftspartner senden', PrintName='Geschäftspartner senden',Updated=TO_TIMESTAMP('2021-11-15 15:08:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580213 AND AD_Language='nl_NL'
;

-- 2021-11-15T13:08:02.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580213,'nl_NL') 
;

-- 2021-11-15T13:11:42.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580225,0,TO_TIMESTAMP('2021-11-15 15:11:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Tenant-ID (MID)','Tenant-ID (MID)',TO_TIMESTAMP('2021-11-15 15:11:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-15T13:11:42.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580225 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-11-15T13:11:50.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Mandanten-ID (MID)', PrintName='Mandanten-ID (MID)',Updated=TO_TIMESTAMP('2021-11-15 15:11:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580225 AND AD_Language='de_CH'
;

-- 2021-11-15T13:11:50.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580225,'de_CH') 
;

-- 2021-11-15T13:11:54.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Mandanten-ID (MID)', PrintName='Mandanten-ID (MID)',Updated=TO_TIMESTAMP('2021-11-15 15:11:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580225 AND AD_Language='de_DE'
;

-- 2021-11-15T13:11:54.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580225,'de_DE') 
;

-- 2021-11-15T13:11:54.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580225,'de_DE') 
;

-- 2021-11-15T13:11:54.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Mandanten-ID (MID)', Description=NULL, Help=NULL WHERE AD_Element_ID=580225
;

-- 2021-11-15T13:11:54.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Mandanten-ID (MID)', Description=NULL, Help=NULL WHERE AD_Element_ID=580225 AND IsCentrallyMaintained='Y'
;

-- 2021-11-15T13:11:54.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mandanten-ID (MID)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580225) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580225)
;

-- 2021-11-15T13:11:54.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Mandanten-ID (MID)', Name='Mandanten-ID (MID)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580225)
;

-- 2021-11-15T13:11:54.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Mandanten-ID (MID)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580225
;

-- 2021-11-15T13:11:54.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Mandanten-ID (MID)', Description=NULL, Help=NULL WHERE AD_Element_ID = 580225
;


-- 2021-11-15T13:11:54.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Mandanten-ID (MID)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580225
;

-- 2021-11-15T13:11:58.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Mandanten-ID (MID)', PrintName='Mandanten-ID (MID)',Updated=TO_TIMESTAMP('2021-11-15 15:11:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580225 AND AD_Language='nl_NL'
;

-- 2021-11-15T13:11:58.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580225,'nl_NL') 
;

-- 2021-11-15T13:12:27.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=580225, Description=NULL, Help=NULL, Name='Mandanten-ID (MID)',Updated=TO_TIMESTAMP('2021-11-15 15:12:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=661592
;

-- 2021-11-15T13:12:27.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580225) 
;

-- 2021-11-15T13:12:27.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=661592
;

-- 2021-11-15T13:12:27.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(661592)
;

