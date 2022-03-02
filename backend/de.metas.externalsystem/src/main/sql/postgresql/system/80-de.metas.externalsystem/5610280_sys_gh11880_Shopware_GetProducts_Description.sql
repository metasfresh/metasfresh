-- 2021-10-21T10:54:49.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Get products and prices from shopware (prices will be added to the latest price list version of the specified price list)',Updated=TO_TIMESTAMP('2021-10-21 13:54:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542839
;

-- 2021-10-21T11:03:53.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580078,0,TO_TIMESTAMP('2021-10-21 14:03:53','YYYY-MM-DD HH24:MI:SS'),100,'Prices will be added to the latest price list version of the specified price list','D','Y','Price List','Price List',TO_TIMESTAMP('2021-10-21 14:03:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-21T11:03:53.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580078 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-10-21T11:04:11.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Preisliste', PrintName='Preisliste',Updated=TO_TIMESTAMP('2021-10-21 14:04:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580078 AND AD_Language='de_CH'
;

-- 2021-10-21T11:04:11.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580078,'de_CH') 
;

-- 2021-10-21T11:04:15.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Preisliste', PrintName='Preisliste',Updated=TO_TIMESTAMP('2021-10-21 14:04:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580078 AND AD_Language='de_DE'
;

-- 2021-10-21T11:04:15.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580078,'de_DE') 
;

-- 2021-10-21T11:04:15.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580078,'de_DE') 
;

-- 2021-10-21T11:04:15.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Preisliste', Description='Prices will be added to the latest price list version of the specified price list', Help=NULL WHERE AD_Element_ID=580078
;

-- 2021-10-21T11:04:15.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Preisliste', Description='Prices will be added to the latest price list version of the specified price list', Help=NULL WHERE AD_Element_ID=580078 AND IsCentrallyMaintained='Y'
;

-- 2021-10-21T11:04:15.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preisliste', Description='Prices will be added to the latest price list version of the specified price list', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580078) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580078)
;

-- 2021-10-21T11:04:15.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Preisliste', Name='Preisliste' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580078)
;

-- 2021-10-21T11:04:15.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Preisliste', Description='Prices will be added to the latest price list version of the specified price list', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580078
;

-- 2021-10-21T11:04:15.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Preisliste', Description='Prices will be added to the latest price list version of the specified price list', Help=NULL WHERE AD_Element_ID = 580078
;

-- 2021-10-21T11:04:15.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Preisliste', Description = 'Prices will be added to the latest price list version of the specified price list', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580078
;

-- 2021-10-21T11:04:19.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Preisliste', PrintName='Preisliste',Updated=TO_TIMESTAMP('2021-10-21 14:04:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580078 AND AD_Language='nl_NL'
;

-- 2021-10-21T11:04:19.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580078,'nl_NL') 
;

-- 2021-10-21T11:05:23.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=580078, Description='Prices will be added to the latest price list version of the specified price list', Help=NULL, Name='Preisliste',Updated=TO_TIMESTAMP('2021-10-21 14:05:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=660997
;

-- 2021-10-21T11:05:23.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580078) 
;

-- 2021-10-21T11:05:23.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=660997
;

-- 2021-10-21T11:05:23.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(660997)
;

-- 2021-10-21T11:06:31.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=580078, Description='Prices will be added to the latest price list version of the specified price list', Help=NULL, Name='Preisliste',Updated=TO_TIMESTAMP('2021-10-21 14:06:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=660989
;

-- 2021-10-21T11:06:31.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580078) 
;

-- 2021-10-21T11:06:31.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=660989
;

-- 2021-10-21T11:06:31.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(660989)
;

-- 2021-10-21T11:08:18.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Get products and prices from shopware (prices will be added to the latest price list version of the specified price list)',Updated=TO_TIMESTAMP('2021-10-21 14:08:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542839
;

