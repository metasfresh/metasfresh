-- 2021-10-25T11:33:52.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Abgerufene Produkt-Preise werden der jeweils neuesten Preislistenversion der ausgewählten Preisliste zugeordnet.',Updated=TO_TIMESTAMP('2021-10-25 14:33:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580078 AND AD_Language='de_CH'
;

-- 2021-10-25T11:33:52.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580078,'de_CH') 
;

-- 2021-10-25T11:33:54.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Abgerufene Produkt-Preise werden der jeweils neuesten Preislistenversion der ausgewählten Preisliste zugeordnet.',Updated=TO_TIMESTAMP('2021-10-25 14:33:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580078 AND AD_Language='de_DE'
;

-- 2021-10-25T11:33:54.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580078,'de_DE') 
;

-- 2021-10-25T11:33:54.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580078,'de_DE') 
;

-- 2021-10-25T11:33:54.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Preisliste', Description='Abgerufene Produkt-Preise werden der jeweils neuesten Preislistenversion der ausgewählten Preisliste zugeordnet.', Help=NULL WHERE AD_Element_ID=580078
;

-- 2021-10-25T11:33:54.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Preisliste', Description='Abgerufene Produkt-Preise werden der jeweils neuesten Preislistenversion der ausgewählten Preisliste zugeordnet.', Help=NULL WHERE AD_Element_ID=580078 AND IsCentrallyMaintained='Y'
;

-- 2021-10-25T11:33:54.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preisliste', Description='Abgerufene Produkt-Preise werden der jeweils neuesten Preislistenversion der ausgewählten Preisliste zugeordnet.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580078) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580078)
;

-- 2021-10-25T11:33:54.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Preisliste', Description='Abgerufene Produkt-Preise werden der jeweils neuesten Preislistenversion der ausgewählten Preisliste zugeordnet.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580078
;

-- 2021-10-25T11:33:54.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Preisliste', Description='Abgerufene Produkt-Preise werden der jeweils neuesten Preislistenversion der ausgewählten Preisliste zugeordnet.', Help=NULL WHERE AD_Element_ID = 580078
;

-- 2021-10-25T11:33:54.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Preisliste', Description = 'Abgerufene Produkt-Preise werden der jeweils neuesten Preislistenversion der ausgewählten Preisliste zugeordnet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580078
;

-- 2021-10-25T11:33:57.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Abgerufene Produkt-Preise werden der jeweils neuesten Preislistenversion der ausgewählten Preisliste zugeordnet.',Updated=TO_TIMESTAMP('2021-10-25 14:33:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580078 AND AD_Language='nl_NL'
;

-- 2021-10-25T11:33:57.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580078,'nl_NL') 
;

-- 2021-10-25T11:36:04.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Ruft Produkte und Preise von Shopware ab. Die Preise werden der jeweils neuesten Preislisteversion der ausgewählten Preisliste zugeordnet.',Updated=TO_TIMESTAMP('2021-10-25 14:36:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542839
;

-- 2021-10-25T11:36:06.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Ruft Produkte und Preise von Shopware ab. Die Preise werden der jeweils neuesten Preislisteversion der ausgewählten Preisliste zugeordnet.',Updated=TO_TIMESTAMP('2021-10-25 14:36:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542839
;

-- 2021-10-25T11:36:08.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Ruft Produkte und Preise von Shopware ab. Die Preise werden der jeweils neuesten Preislisteversion der ausgewählten Preisliste zugeordnet.',Updated=TO_TIMESTAMP('2021-10-25 14:36:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542839
;

-- 2021-10-25T11:45:06.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='',Updated=TO_TIMESTAMP('2021-10-25 14:45:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542839
;

-- 2021-10-25T11:46:08.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Ruft Produkte und Preise von Shopware ab. Die Preise werden der jeweils neuesten Preislisteversion der ausgewählten Preisliste zugeordnet.',Updated=TO_TIMESTAMP('2021-10-25 14:46:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542839
;

