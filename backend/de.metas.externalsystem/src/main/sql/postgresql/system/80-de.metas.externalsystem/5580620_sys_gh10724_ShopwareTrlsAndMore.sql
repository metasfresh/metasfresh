-- 2021-02-26T07:22:39.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='since',Updated=TO_TIMESTAMP('2021-02-26 09:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541941
;

-- 2021-02-26T07:43:13.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zugangs-ID', PrintName='Zugangs-ID',Updated=TO_TIMESTAMP('2021-02-26 09:43:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578777 AND AD_Language='de_CH'
;

-- 2021-02-26T07:43:13.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578777,'de_CH') 
;

-- 2021-02-26T07:43:17.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zugangs-ID', PrintName='Zugangs-ID',Updated=TO_TIMESTAMP('2021-02-26 09:43:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578777 AND AD_Language='de_DE'
;

-- 2021-02-26T07:43:17.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578777,'de_DE') 
;

-- 2021-02-26T07:43:17.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578777,'de_DE') 
;

-- 2021-02-26T07:43:17.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Client_Id', Name='Zugangs-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=578777
;

-- 2021-02-26T07:43:17.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Client_Id', Name='Zugangs-ID', Description=NULL, Help=NULL, AD_Element_ID=578777 WHERE UPPER(ColumnName)='CLIENT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-26T07:43:17.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Client_Id', Name='Zugangs-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=578777 AND IsCentrallyMaintained='Y'
;

-- 2021-02-26T07:43:17.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugangs-ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578777) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578777)
;

-- 2021-02-26T07:43:17.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zugangs-ID', Name='Zugangs-ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578777)
;

-- 2021-02-26T07:43:17.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zugangs-ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578777
;

-- 2021-02-26T07:43:17.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zugangs-ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 578777
;

-- 2021-02-26T07:43:17.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zugangs-ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578777
;

-- 2021-02-26T07:43:23.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zugangs-ID', PrintName='Zugangs-ID',Updated=TO_TIMESTAMP('2021-02-26 09:43:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578777 AND AD_Language='nl_NL'
;

-- 2021-02-26T07:43:23.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578777,'nl_NL') 
;

-- 2021-02-26T07:43:30.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Access key ID', PrintName='Access key ID',Updated=TO_TIMESTAMP('2021-02-26 09:43:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578777 AND AD_Language='en_US'
;

-- 2021-02-26T07:43:30.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578777,'en_US') 
;

-- 2021-02-26T07:43:54.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Sicherheitsschlüssel', PrintName='Sicherheitsschlüssel',Updated=TO_TIMESTAMP('2021-02-26 09:43:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578778 AND AD_Language='de_CH'
;

-- 2021-02-26T07:43:54.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578778,'de_CH') 
;

-- 2021-02-26T07:43:58.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Sicherheitsschlüssel', PrintName='Sicherheitsschlüssel',Updated=TO_TIMESTAMP('2021-02-26 09:43:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578778 AND AD_Language='de_DE'
;

-- 2021-02-26T07:43:58.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578778,'de_DE') 
;

-- 2021-02-26T07:43:58.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578778,'de_DE') 
;

-- 2021-02-26T07:43:58.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Client_Secret', Name='Sicherheitsschlüssel', Description=NULL, Help=NULL WHERE AD_Element_ID=578778
;

-- 2021-02-26T07:43:58.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Client_Secret', Name='Sicherheitsschlüssel', Description=NULL, Help=NULL, AD_Element_ID=578778 WHERE UPPER(ColumnName)='CLIENT_SECRET' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-26T07:43:58.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Client_Secret', Name='Sicherheitsschlüssel', Description=NULL, Help=NULL WHERE AD_Element_ID=578778 AND IsCentrallyMaintained='Y'
;

-- 2021-02-26T07:43:58.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sicherheitsschlüssel', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578778) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578778)
;

-- 2021-02-26T07:43:58.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Sicherheitsschlüssel', Name='Sicherheitsschlüssel' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578778)
;

-- 2021-02-26T07:43:58.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Sicherheitsschlüssel', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578778
;

-- 2021-02-26T07:43:58.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Sicherheitsschlüssel', Description=NULL, Help=NULL WHERE AD_Element_ID = 578778
;

-- 2021-02-26T07:43:58.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Sicherheitsschlüssel', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578778
;

-- 2021-02-26T07:44:08.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Sicherheitsschlüssel', PrintName='Sicherheitsschlüssel',Updated=TO_TIMESTAMP('2021-02-26 09:44:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578778 AND AD_Language='nl_NL'
;

-- 2021-02-26T07:44:08.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578778,'nl_NL') 
;

-- 2021-02-26T07:44:17.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Secret access key', PrintName='Secret access key',Updated=TO_TIMESTAMP('2021-02-26 09:44:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578778 AND AD_Language='en_US'
;

-- 2021-02-26T07:44:17.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578778,'en_US') 
;

-- 2021-02-26T07:45:20.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542290,541117,TO_TIMESTAMP('2021-02-26 09:45:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Shopware6',TO_TIMESTAMP('2021-02-26 09:45:20','YYYY-MM-DD HH24:MI:SS'),100,'Shopware6','Shopware6')
;

-- 2021-02-26T07:45:20.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542290 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-02-26T07:46:01.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542291,541127,TO_TIMESTAMP('2021-02-26 09:46:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','BPartnerLocation',TO_TIMESTAMP('2021-02-26 09:46:01','YYYY-MM-DD HH24:MI:SS'),100,'BPartnerLocation','BPartnerLocation')
;

-- 2021-02-26T07:46:01.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542291 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-02-26T07:46:45.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='D',Updated=TO_TIMESTAMP('2021-02-26 09:46:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541262
;

-- 2021-02-26T07:46:56.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='getOrders', Value='getOrders',Updated=TO_TIMESTAMP('2021-02-26 09:46:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542284
;

