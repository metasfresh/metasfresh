-- sync terminology wrt AD_Message.Value MKTG_Campaign_NewsletterGroup_Missing_For_Org
-- 2019-07-23T04:02:13.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Organisation {0} wurde noch keine Default Marketing Kampagne zugeordnet.',Updated=TO_TIMESTAMP('2019-07-23 06:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544717
;

-- window for table C_PriceLimit_Restriction

-- 2019-01-09T10:42:19.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-09 10:42:19','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Mindestpreis',PrintName='Mindestpreis',Description='Erlaubt das Hinterlegen von Regeln zum Festlegen von Mindestpreisen' WHERE AD_Element_ID=574393 AND AD_Language='de_CH'
;

-- 2019-01-09T10:42:19.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574393,'de_CH') 
;

-- 2019-01-09T10:42:31.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-09 10:42:31','YYYY-MM-DD HH24:MI:SS'),Description='Erlaubt das Hinterlegen von Regeln, um einen minimalen Mindestpreis festzulegen.' WHERE AD_Element_ID=574393 AND AD_Language='de_DE'
;

-- 2019-01-09T10:42:31.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574393,'de_DE') 
;

-- 2019-01-09T10:42:31.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574393,'de_DE') 
;

-- 2019-01-09T10:42:31.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Price Limit Restriction', Description='Erlaubt das Hinterlegen von Regeln, um einen minimalen Mindestpreis festzulegen.', Help=NULL WHERE AD_Element_ID=574393
;

-- 2019-01-09T10:42:31.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Price Limit Restriction', Description='Erlaubt das Hinterlegen von Regeln, um einen minimalen Mindestpreis festzulegen.', Help=NULL WHERE AD_Element_ID=574393 AND IsCentrallyMaintained='Y'
;

-- 2019-01-09T10:42:31.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Price Limit Restriction', Description='Erlaubt das Hinterlegen von Regeln, um einen minimalen Mindestpreis festzulegen.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=574393) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 574393)
;

-- 2019-01-09T10:42:32.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Price Limit Restriction', Description='Erlaubt das Hinterlegen von Regeln, um einen minimalen Mindestpreis festzulegen.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 574393
;

-- 2019-01-09T10:42:32.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Price Limit Restriction', Description='Erlaubt das Hinterlegen von Regeln, um einen minimalen Mindestpreis festzulegen.', Help=NULL WHERE AD_Element_ID = 574393
;

-- 2019-01-09T10:42:32.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Price Limit Restriction', Description='Erlaubt das Hinterlegen von Regeln, um einen minimalen Mindestpreis festzulegen.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 574393
;

-- 2019-01-09T10:42:47.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-09 10:42:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Mindestpreis',PrintName='Mindestpreis' WHERE AD_Element_ID=574393 AND AD_Language='de_DE'
;

-- 2019-01-09T10:42:47.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574393,'de_DE') 
;

-- 2019-01-09T10:42:47.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574393,'de_DE') 
;

-- 2019-01-09T10:42:47.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Mindestpreis', Description='Erlaubt das Hinterlegen von Regeln, um einen minimalen Mindestpreis festzulegen.', Help=NULL WHERE AD_Element_ID=574393
;

-- 2019-01-09T10:42:47.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Mindestpreis', Description='Erlaubt das Hinterlegen von Regeln, um einen minimalen Mindestpreis festzulegen.', Help=NULL WHERE AD_Element_ID=574393 AND IsCentrallyMaintained='Y'
;

-- 2019-01-09T10:42:47.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mindestpreis', Description='Erlaubt das Hinterlegen von Regeln, um einen minimalen Mindestpreis festzulegen.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=574393) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 574393)
;

-- 2019-01-09T10:42:47.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Mindestpreis', Name='Mindestpreis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=574393)
;

-- 2019-01-09T10:42:47.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Mindestpreis', Description='Erlaubt das Hinterlegen von Regeln, um einen minimalen Mindestpreis festzulegen.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 574393
;

-- 2019-01-09T10:42:47.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Mindestpreis', Description='Erlaubt das Hinterlegen von Regeln, um einen minimalen Mindestpreis festzulegen.', Help=NULL WHERE AD_Element_ID = 574393
;

-- 2019-01-09T10:42:47.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Mindestpreis', Description='Erlaubt das Hinterlegen von Regeln, um einen minimalen Mindestpreis festzulegen.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 574393
;

-- 2019-01-09T10:42:51.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-09 10:42:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=574393 AND AD_Language='en_US'
;

-- 2019-01-09T10:42:51.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574393,'en_US') 
;

-- 2019-07-23T04:33:59.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='C_PriceLimit_Restriction',Updated=TO_TIMESTAMP('2019-07-23 06:33:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540962
;

-- 2019-07-23T04:38:28.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576929,0,TO_TIMESTAMP('2019-07-23 06:38:28','YYYY-MM-DD HH24:MI:SS'),100,'Der Endpreis darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten','D','Y','Mindestaufschlag auf Standardpreis','Mindestufschlag auf Standardpreis',TO_TIMESTAMP('2019-07-23 06:38:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T04:38:28.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576929 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-23T04:38:34.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-23 06:38:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576929 AND AD_Language='de_CH'
;

-- 2019-07-23T04:38:34.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576929,'de_CH') 
;

-- 2019-07-23T04:38:36.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-23 06:38:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576929 AND AD_Language='de_DE'
;

-- 2019-07-23T04:38:36.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576929,'de_DE') 
;

-- 2019-07-23T04:38:36.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576929,'de_DE') 
;

-- 2019-07-23T04:41:00.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='The actual price may not be less than the price list''s price plus this surcharge', IsTranslated='Y', Name='Min surcharge', PrintName='Min surcharge',Updated=TO_TIMESTAMP('2019-07-23 06:41:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576929 AND AD_Language='en_US'
;

-- 2019-07-23T04:41:00.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576929,'en_US') 
;

-- 2019-07-23T04:41:38.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten', Name='Mindestaufschlag', PrintName='Mindesaufschlag',Updated=TO_TIMESTAMP('2019-07-23 06:41:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576929 AND AD_Language='de_DE'
;

-- 2019-07-23T04:41:38.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576929,'de_DE') 
;

-- 2019-07-23T04:41:38.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576929,'de_DE') 
;

-- 2019-07-23T04:41:38.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Mindestaufschlag', Description='Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten', Help=NULL WHERE AD_Element_ID=576929
;

-- 2019-07-23T04:41:38.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Mindestaufschlag', Description='Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten', Help=NULL WHERE AD_Element_ID=576929 AND IsCentrallyMaintained='Y'
;

-- 2019-07-23T04:41:38.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mindestaufschlag', Description='Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576929) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576929)
;

-- 2019-07-23T04:41:38.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Mindesaufschlag', Name='Mindestaufschlag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576929)
;

-- 2019-07-23T04:41:38.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Mindestaufschlag', Description='Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576929
;

-- 2019-07-23T04:41:38.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Mindestaufschlag', Description='Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten', Help=NULL WHERE AD_Element_ID = 576929
;

-- 2019-07-23T04:41:38.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Mindestaufschlag', Description = 'Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576929
;

-- 2019-07-23T04:41:48.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='The actual price may not be less than the price list''s price plus this surcharge.',Updated=TO_TIMESTAMP('2019-07-23 06:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576929 AND AD_Language='en_US'
;

-- 2019-07-23T04:41:48.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576929,'en_US') 
;

-- 2019-07-23T04:41:53.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Mindestaufschlag auf Standardpreis',Updated=TO_TIMESTAMP('2019-07-23 06:41:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576929 AND AD_Language='nl_NL'
;

-- 2019-07-23T04:41:53.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576929,'nl_NL') 
;

-- 2019-07-23T04:42:08.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Mindestaufschlag', PrintName='Mindestaufschlag',Updated=TO_TIMESTAMP('2019-07-23 06:42:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576929 AND AD_Language='de_CH'
;

-- 2019-07-23T04:42:08.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576929,'de_CH') 
;

-- 2019-07-23T04:42:14.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten.',Updated=TO_TIMESTAMP('2019-07-23 06:42:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576929 AND AD_Language='de_DE'
;

-- 2019-07-23T04:42:14.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576929,'de_DE') 
;

-- 2019-07-23T04:42:14.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576929,'de_DE') 
;

-- 2019-07-23T04:42:14.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Mindestaufschlag', Description='Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten.', Help=NULL WHERE AD_Element_ID=576929
;

-- 2019-07-23T04:42:14.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Mindestaufschlag', Description='Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten.', Help=NULL WHERE AD_Element_ID=576929 AND IsCentrallyMaintained='Y'
;

-- 2019-07-23T04:42:14.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mindestaufschlag', Description='Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576929) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576929)
;

-- 2019-07-23T04:42:14.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Mindestaufschlag', Description='Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576929
;

-- 2019-07-23T04:42:14.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Mindestaufschlag', Description='Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten.', Help=NULL WHERE AD_Element_ID = 576929
;

-- 2019-07-23T04:42:14.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Mindestaufschlag', Description = 'Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576929
;

-- 2019-07-23T04:42:22.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Der Preis pro Einheit darf den Listenstandardpreis plus Mindestaufschlag nicht unterschreiten.',Updated=TO_TIMESTAMP('2019-07-23 06:42:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576929 AND AD_Language='de_CH'
;

-- 2019-07-23T04:42:22.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576929,'de_CH') 
;

