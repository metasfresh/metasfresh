
-- 2022-03-22T13:01:20.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Override Cost Price', PrintName='Override Cost Price',Updated=TO_TIMESTAMP('2022-03-22 15:01:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580714 AND AD_Language='en_US'
;

-- 2022-03-22T13:01:20.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580714,'en_US') 
;

-- 2022-03-22T13:01:39.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kostenpreis überschreiben', PrintName='Kostenpreis überschreiben',Updated=TO_TIMESTAMP('2022-03-22 15:01:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580714 AND AD_Language='de_CH'
;

-- 2022-03-22T13:01:39.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580714,'de_CH') 
;

-- 2022-03-22T13:01:43.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kostenpreis überschreiben', PrintName='Kostenpreis überschreiben',Updated=TO_TIMESTAMP('2022-03-22 15:01:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580714 AND AD_Language='de_DE'
;

-- 2022-03-22T13:01:43.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580714,'de_DE') 
;

-- 2022-03-22T13:01:43.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580714,'de_DE') 
;

-- 2022-03-22T13:01:43.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsExplicitCostPrice', Name='Kostenpreis überschreiben', Description=NULL, Help=NULL WHERE AD_Element_ID=580714
;

-- 2022-03-22T13:01:43.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExplicitCostPrice', Name='Kostenpreis überschreiben', Description=NULL, Help=NULL, AD_Element_ID=580714 WHERE UPPER(ColumnName)='ISEXPLICITCOSTPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-22T13:01:43.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExplicitCostPrice', Name='Kostenpreis überschreiben', Description=NULL, Help=NULL WHERE AD_Element_ID=580714 AND IsCentrallyMaintained='Y'
;

-- 2022-03-22T13:01:43.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kostenpreis überschreiben', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580714) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580714)
;

-- 2022-03-22T13:01:43.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kostenpreis überschreiben', Name='Kostenpreis überschreiben' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580714)
;

-- 2022-03-22T13:01:43.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kostenpreis überschreiben', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580714
;

-- 2022-03-22T13:01:43.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kostenpreis überschreiben', Description=NULL, Help=NULL WHERE AD_Element_ID = 580714
;

-- 2022-03-22T13:01:43.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kostenpreis überschreiben', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580714
;

-- 2022-03-22T13:02:15.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kostenpreis überschreiben', PrintName='Kostenpreis überschreiben',Updated=TO_TIMESTAMP('2022-03-22 15:02:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580718 AND AD_Language='de_CH'
;

-- 2022-03-22T13:02:15.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580718,'de_CH') 
;

-- 2022-03-22T13:02:19.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kostenpreis überschreiben', PrintName='Kostenpreis überschreiben',Updated=TO_TIMESTAMP('2022-03-22 15:02:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580718 AND AD_Language='de_DE'
;

-- 2022-03-22T13:02:19.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580718,'de_DE') 
;

-- 2022-03-22T13:02:19.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580718,'de_DE') 
;

-- 2022-03-22T13:02:19.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExplicitCostPrice', Name='Kostenpreis überschreiben', Description=NULL, Help=NULL WHERE AD_Element_ID=580718
;

-- 2022-03-22T13:02:19.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExplicitCostPrice', Name='Kostenpreis überschreiben', Description=NULL, Help=NULL, AD_Element_ID=580718 WHERE UPPER(ColumnName)='EXPLICITCOSTPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-22T13:02:19.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExplicitCostPrice', Name='Kostenpreis überschreiben', Description=NULL, Help=NULL WHERE AD_Element_ID=580718 AND IsCentrallyMaintained='Y'
;

-- 2022-03-22T13:02:19.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kostenpreis überschreiben', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580718) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580718)
;

-- 2022-03-22T13:02:19.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kostenpreis überschreiben', Name='Kostenpreis überschreiben' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580718)
;

-- 2022-03-22T13:02:19.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kostenpreis überschreiben', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580718
;

-- 2022-03-22T13:02:19.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kostenpreis überschreiben', Description=NULL, Help=NULL WHERE AD_Element_ID = 580718
;

-- 2022-03-22T13:02:19.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kostenpreis überschreiben', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580718
;

-- 2022-03-22T13:02:47.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Override Cost Price', PrintName='Override Cost Price',Updated=TO_TIMESTAMP('2022-03-22 15:02:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580718 AND AD_Language='en_US'
;

-- 2022-03-22T13:02:47.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580718,'en_US') 
;

