-- 2020-02-06T16:33:22.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='In den Nutzerdaten ist keine eMail Adresse hinterlegt.',Updated=TO_TIMESTAMP('2020-02-06 17:33:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=541134
;

-- 2020-02-06T16:33:35.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='eMail Adresse',Updated=TO_TIMESTAMP('2020-02-06 17:33:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=541134
;

-- 2020-02-06T16:33:40.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='In den Nutzerdaten ist keine eMail Adresse hinterlegt.',Updated=TO_TIMESTAMP('2020-02-06 17:33:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=541134
;

-- 2020-02-06T16:34:10.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The user record doesn''t contain a mail address',Updated=TO_TIMESTAMP('2020-02-06 17:34:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=541134
;

-- 2020-02-06T16:34:24.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The user record doesn''t contain a mail address',Updated=TO_TIMESTAMP('2020-02-06 17:34:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Message_ID=541134
;

-- 2020-02-06T16:34:28.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='In den Nutzerdaten ist keine eMail Adresse hinterlegt',Updated=TO_TIMESTAMP('2020-02-06 17:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=541134
;

-- 2020-02-06T16:34:32.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='In den Nutzerdaten ist keine eMail Adresse hinterlegt',Updated=TO_TIMESTAMP('2020-02-06 17:34:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=541134
;

-- 2020-02-06T16:34:34.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-06 17:34:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=541134
;

-- 2020-02-06T16:34:38.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='In den Nutzerdaten ist keine eMail Adresse hinterlegt',Updated=TO_TIMESTAMP('2020-02-06 17:34:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=541134
;

-- 2020-02-06T16:46:40.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577509,0,'EMail_Login',TO_TIMESTAMP('2020-02-06 17:46:40','YYYY-MM-DD HH24:MI:SS'),100,'Kann alternativ zu "Login" zum Anmelden an metasfresh benutzt werden; muss hinterlegt sein, damit die "Passwort vergessen" Funktion genutzt werden kann.','D','Y','eMail','eMail',TO_TIMESTAMP('2020-02-06 17:46:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-06T16:46:40.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577509 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-06T16:47:10.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Kann von Syntembenutzern alternativ zu "Login" zum Anmelden an metasfresh benutzt werden; muss hinterlegt sein, damit die "Passwort vergessen" Funktion benutzt werden kann.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-06 17:47:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577509 AND AD_Language='de_CH'
;

-- 2020-02-06T16:47:10.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577509,'de_CH') 
;

-- 2020-02-06T16:47:19.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Kann von Syntembenutzern alternativ zu "Login" zum Anmelden an metasfresh benutzt werden; muss hinterlegt sein, damit die "Passwort vergessen" Funktion benutzt werden kann.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-06 17:47:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577509 AND AD_Language='de_DE'
;

-- 2020-02-06T16:47:19.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577509,'de_DE') 
;

-- 2020-02-06T16:47:19.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577509,'de_DE') 
;

-- 2020-02-06T16:47:19.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='EMail_Login', Name='eMail', Description='Kann von Syntembenutzern alternativ zu "Login" zum Anmelden an metasfresh benutzt werden; muss hinterlegt sein, damit die "Passwort vergessen" Funktion benutzt werden kann.', Help=NULL WHERE AD_Element_ID=577509
;

-- 2020-02-06T16:47:19.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EMail_Login', Name='eMail', Description='Kann von Syntembenutzern alternativ zu "Login" zum Anmelden an metasfresh benutzt werden; muss hinterlegt sein, damit die "Passwort vergessen" Funktion benutzt werden kann.', Help=NULL, AD_Element_ID=577509 WHERE UPPER(ColumnName)='EMAIL_LOGIN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-06T16:47:19.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EMail_Login', Name='eMail', Description='Kann von Syntembenutzern alternativ zu "Login" zum Anmelden an metasfresh benutzt werden; muss hinterlegt sein, damit die "Passwort vergessen" Funktion benutzt werden kann.', Help=NULL WHERE AD_Element_ID=577509 AND IsCentrallyMaintained='Y'
;

-- 2020-02-06T16:47:19.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='eMail', Description='Kann von Syntembenutzern alternativ zu "Login" zum Anmelden an metasfresh benutzt werden; muss hinterlegt sein, damit die "Passwort vergessen" Funktion benutzt werden kann.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577509) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577509)
;

-- 2020-02-06T16:47:19.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='eMail', Description='Kann von Syntembenutzern alternativ zu "Login" zum Anmelden an metasfresh benutzt werden; muss hinterlegt sein, damit die "Passwort vergessen" Funktion benutzt werden kann.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577509
;

-- 2020-02-06T16:47:19.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='eMail', Description='Kann von Syntembenutzern alternativ zu "Login" zum Anmelden an metasfresh benutzt werden; muss hinterlegt sein, damit die "Passwort vergessen" Funktion benutzt werden kann.', Help=NULL WHERE AD_Element_ID = 577509
;

-- 2020-02-06T16:47:19.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'eMail', Description = 'Kann von Syntembenutzern alternativ zu "Login" zum Anmelden an metasfresh benutzt werden; muss hinterlegt sein, damit die "Passwort vergessen" Funktion benutzt werden kann.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577509
;

-- 2020-02-06T16:48:06.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='System users may use this mail address to login as an alternative to "Login"; has to be set in order to use the "password forgotten" feature.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-06 17:48:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577509 AND AD_Language='en_US'
;

-- 2020-02-06T16:48:06.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577509,'en_US') 
;

-- 2020-02-06T16:48:26.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=577509, Description='Kann von Syntembenutzern alternativ zu "Login" zum Anmelden an metasfresh benutzt werden; muss hinterlegt sein, damit die "Passwort vergessen" Funktion benutzt werden kann.', Help=NULL, Name='eMail',Updated=TO_TIMESTAMP('2020-02-06 17:48:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4260
;

-- 2020-02-06T16:48:26.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577509) 
;

-- 2020-02-06T16:48:26.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=4260
;

-- 2020-02-06T16:48:26.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(4260)
;

