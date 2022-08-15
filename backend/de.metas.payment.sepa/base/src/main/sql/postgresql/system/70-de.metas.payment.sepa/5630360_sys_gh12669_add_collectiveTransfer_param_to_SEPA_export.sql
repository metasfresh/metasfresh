-- 2022-03-16T16:07:24.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580715,0,'collectiveTransfer',TO_TIMESTAMP('2022-03-16 18:07:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Sammelüberweisung','Sammelüberweisung',TO_TIMESTAMP('2022-03-16 18:07:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-16T16:07:24.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580715 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-03-16T16:07:35.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen',Updated=TO_TIMESTAMP('2022-03-16 18:07:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580715 AND AD_Language='de_CH'
;

-- 2022-03-16T16:07:35.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580715,'de_CH') 
;

-- 2022-03-16T16:07:36.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen',Updated=TO_TIMESTAMP('2022-03-16 18:07:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580715 AND AD_Language='de_DE'
;

-- 2022-03-16T16:07:36.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580715,'de_DE') 
;

-- 2022-03-16T16:07:36.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580715,'de_DE') 
;

-- 2022-03-16T16:07:36.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='collectiveTransfer', Name='Sammelüberweisung', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL WHERE AD_Element_ID=580715
;

-- 2022-03-16T16:07:36.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='collectiveTransfer', Name='Sammelüberweisung', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL, AD_Element_ID=580715 WHERE UPPER(ColumnName)='COLLECTIVETRANSFER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-16T16:07:36.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='collectiveTransfer', Name='Sammelüberweisung', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL WHERE AD_Element_ID=580715 AND IsCentrallyMaintained='Y'
;

-- 2022-03-16T16:07:36.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sammelüberweisung', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580715) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580715)
;

-- 2022-03-16T16:07:36.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Sammelüberweisung', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580715
;

-- 2022-03-16T16:07:36.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Sammelüberweisung', Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', Help=NULL WHERE AD_Element_ID = 580715
;

-- 2022-03-16T16:07:36.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Sammelüberweisung', Description = 'Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580715
;

-- 2022-03-16T16:07:44.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen',Updated=TO_TIMESTAMP('2022-03-16 18:07:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580715 AND AD_Language='nl_NL'
;

-- 2022-03-16T16:07:44.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580715,'nl_NL') 
;

-- 2022-03-16T16:07:53Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If set, a unique identifier will be generated for the EndToEndId. Otherwise, it will be left blank', Name='Collective Transfer', PrintName='Collective Transfer',Updated=TO_TIMESTAMP('2022-03-16 18:07:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580715 AND AD_Language='en_US'
;

-- 2022-03-16T16:07:53.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580715,'en_US') 
;

-- 2022-03-16T16:08:46.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,580715,0,540461,542229,20,'collectiveTransfer',TO_TIMESTAMP('2022-03-16 18:08:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','Falls gesetzt, wird ein eindeutiger Bezeichner für die EndToEndId generiert. Andernfalls wird sie leer gelassen','de.metas.payment.sepa',0,'Y','N','Y','N','N','N','Sammelüberweisung',10,TO_TIMESTAMP('2022-03-16 18:08:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-16T16:08:46.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542229 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

