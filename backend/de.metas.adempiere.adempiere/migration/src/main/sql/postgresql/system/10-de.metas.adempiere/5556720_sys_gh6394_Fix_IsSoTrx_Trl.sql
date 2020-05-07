-- 2020-04-09T12:40:25.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='This is a Sales Transaction',Updated=TO_TIMESTAMP('2020-04-09 15:40:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=1106
;

-- 2020-04-09T12:40:25.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1106,'en_US') 
;

-- 2020-04-09T12:40:44.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Sales Transaction',Updated=TO_TIMESTAMP('2020-04-09 15:40:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=1106
;

-- 2020-04-09T12:40:44.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1106,'en_US') 
;

-- 2020-04-09T12:42:39.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-04-09 15:42:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584680 AND AD_Language='de_DE'
;

-- 2020-04-09T13:31:03.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Verkaufstransaktion', IsTranslated='Y', Name='Verkaufstransaktion',Updated=TO_TIMESTAMP('2020-04-09 16:31:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=1106
;

-- 2020-04-09T13:31:03.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1106,'de_DE') 
;

-- 2020-04-09T13:31:03.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1106,'de_DE') 
;

-- 2020-04-09T13:31:04.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSOTrx', Name='Verkaufstransaktion', Description='This is a Sales Transaction', Help='The Sales Transaction checkbox indicates if this item is a Sales Transaction.' WHERE AD_Element_ID=1106
;

-- 2020-04-09T13:31:04.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSOTrx', Name='Verkaufstransaktion', Description='This is a Sales Transaction', Help='The Sales Transaction checkbox indicates if this item is a Sales Transaction.', AD_Element_ID=1106 WHERE UPPER(ColumnName)='ISSOTRX' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-04-09T13:31:04.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSOTrx', Name='Verkaufstransaktion', Description='This is a Sales Transaction', Help='The Sales Transaction checkbox indicates if this item is a Sales Transaction.' WHERE AD_Element_ID=1106 AND IsCentrallyMaintained='Y'
;

-- 2020-04-09T13:31:04.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verkaufstransaktion', Description='This is a Sales Transaction', Help='The Sales Transaction checkbox indicates if this item is a Sales Transaction.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1106) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1106)
;

-- 2020-04-09T13:31:04.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Verkaufstransaktion', Name='Verkaufstransaktion' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1106)
;

-- 2020-04-09T13:31:04.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verkaufstransaktion', Description='This is a Sales Transaction', Help='The Sales Transaction checkbox indicates if this item is a Sales Transaction.', CommitWarning = NULL WHERE AD_Element_ID = 1106
;

-- 2020-04-09T13:31:04.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verkaufstransaktion', Description='This is a Sales Transaction', Help='The Sales Transaction checkbox indicates if this item is a Sales Transaction.' WHERE AD_Element_ID = 1106
;

-- 2020-04-09T13:31:04.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verkaufstransaktion', Description = 'This is a Sales Transaction', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1106
;

-- 2020-04-09T13:31:19.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Verkaufstransaktion', IsTranslated='Y', Name='Verkaufstransaktion', Description='Dies ist eine Verkaufstransaktion.',Updated=TO_TIMESTAMP('2020-04-09 16:31:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=1106
;

-- 2020-04-09T13:31:19.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1106,'de_CH') 
;

-- 2020-04-09T13:31:32.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Dies ist eine Verkaufstransaktion',Updated=TO_TIMESTAMP('2020-04-09 16:31:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=1106
;

-- 2020-04-09T13:31:32.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1106,'de_DE') 
;

-- 2020-04-09T13:31:32.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1106,'de_DE') 
;

-- 2020-04-09T13:31:32.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSOTrx', Name='Verkaufstransaktion', Description='Dies ist eine Verkaufstransaktion', Help='The Sales Transaction checkbox indicates if this item is a Sales Transaction.' WHERE AD_Element_ID=1106
;

-- 2020-04-09T13:31:32.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSOTrx', Name='Verkaufstransaktion', Description='Dies ist eine Verkaufstransaktion', Help='The Sales Transaction checkbox indicates if this item is a Sales Transaction.', AD_Element_ID=1106 WHERE UPPER(ColumnName)='ISSOTRX' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-04-09T13:31:32.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSOTrx', Name='Verkaufstransaktion', Description='Dies ist eine Verkaufstransaktion', Help='The Sales Transaction checkbox indicates if this item is a Sales Transaction.' WHERE AD_Element_ID=1106 AND IsCentrallyMaintained='Y'
;

-- 2020-04-09T13:31:32.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verkaufstransaktion', Description='Dies ist eine Verkaufstransaktion', Help='The Sales Transaction checkbox indicates if this item is a Sales Transaction.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1106) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1106)
;

-- 2020-04-09T13:31:32.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verkaufstransaktion', Description='Dies ist eine Verkaufstransaktion', Help='The Sales Transaction checkbox indicates if this item is a Sales Transaction.', CommitWarning = NULL WHERE AD_Element_ID = 1106
;

-- 2020-04-09T13:31:32.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verkaufstransaktion', Description='Dies ist eine Verkaufstransaktion', Help='The Sales Transaction checkbox indicates if this item is a Sales Transaction.' WHERE AD_Element_ID = 1106
;

-- 2020-04-09T13:31:32.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verkaufstransaktion', Description = 'Dies ist eine Verkaufstransaktion', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1106
;

-- 2020-04-09T13:31:37.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Dies ist eine Verkaufstransaktion',Updated=TO_TIMESTAMP('2020-04-09 16:31:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=1106
;

-- 2020-04-09T13:31:37.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1106,'de_CH') 
;

