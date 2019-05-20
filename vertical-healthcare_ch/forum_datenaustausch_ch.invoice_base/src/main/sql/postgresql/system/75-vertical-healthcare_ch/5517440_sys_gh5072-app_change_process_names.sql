-- 2019-03-27T08:58:14.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='KK Rechnungsantworten importieren',Updated=TO_TIMESTAMP('2019-03-27 08:58:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541084
;

-- 2019-03-27T08:58:29.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='KK Rechnungsantworten importieren',Updated=TO_TIMESTAMP('2019-03-27 08:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541084
;

-- 2019-03-27T08:58:32.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='KK Rechnungsantworten importieren',Updated=TO_TIMESTAMP('2019-03-27 08:58:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541084
;

-- 2019-03-27T08:58:53.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Import health insurance invoice responses',Updated=TO_TIMESTAMP('2019-03-27 08:58:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541084
;

-- 2019-03-27T08:59:37.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Import health insurance invoice responses', PrintName='Import health insurance invoice responses',Updated=TO_TIMESTAMP('2019-03-27 08:59:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576298 AND AD_Language='en_US'
;

-- 2019-03-27T08:59:37.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576298,'en_US') 
;

-- 2019-03-27T08:59:58.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='KK Rechnungsantworten importieren', PrintName='KK Rechnungsantworten importieren',Updated=TO_TIMESTAMP('2019-03-27 08:59:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576298 AND AD_Language='de_DE'
;

-- 2019-03-27T08:59:58.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576298,'de_DE') 
;

-- 2019-03-27T08:59:58.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576298,'de_DE') 
;

-- 2019-03-27T08:59:58.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_ImportInvoiceResponse', Name='KK Rechnungsantworten importieren', Description=NULL, Help='http://www.forum-datenaustausch.ch' WHERE AD_Element_ID=576298
;

-- 2019-03-27T08:59:58.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_ImportInvoiceResponse', Name='KK Rechnungsantworten importieren', Description=NULL, Help='http://www.forum-datenaustausch.ch', AD_Element_ID=576298 WHERE UPPER(ColumnName)='C_INVOICE_IMPORTINVOICERESPONSE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-27T08:59:58.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_ImportInvoiceResponse', Name='KK Rechnungsantworten importieren', Description=NULL, Help='http://www.forum-datenaustausch.ch' WHERE AD_Element_ID=576298 AND IsCentrallyMaintained='Y'
;

-- 2019-03-27T08:59:58.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='KK Rechnungsantworten importieren', Description=NULL, Help='http://www.forum-datenaustausch.ch' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576298) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576298)
;

-- 2019-03-27T08:59:58.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='KK Rechnungsantworten importieren', Name='KK Rechnungsantworten importieren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576298)
;

-- 2019-03-27T08:59:58.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='KK Rechnungsantworten importieren', Description=NULL, Help='http://www.forum-datenaustausch.ch', CommitWarning = NULL WHERE AD_Element_ID = 576298
;

-- 2019-03-27T08:59:58.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='KK Rechnungsantworten importieren', Description=NULL, Help='http://www.forum-datenaustausch.ch' WHERE AD_Element_ID = 576298
;

-- 2019-03-27T08:59:58.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'KK Rechnungsantworten importieren', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576298
;

-- 2019-03-27T09:00:01.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='KK Rechnungsantworten importieren', PrintName='KK Rechnungsantworten importieren',Updated=TO_TIMESTAMP('2019-03-27 09:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576298 AND AD_Language='de_CH'
;

-- 2019-03-27T09:00:01.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576298,'de_CH') 
;

