-- 2020-02-19T06:28:45.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='SummaryAndBalanceReport', SQLStatement='select * from SummaryAndBalanceReport(''@DateFrom@''::date, ''@DateTo@''::date, @C_AcctSchema_ID@, @AD_Org_ID@, @Account_ID/null@)', Value='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584655
;

-- 2020-02-19T06:28:45.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541437
;

-- 2020-02-19T06:29:12.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584655
;

-- 2020-02-19T06:29:15.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:29:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584655
;

-- 2020-02-19T06:29:17.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:29:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584655
;

-- 2020-02-19T06:29:19.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584655
;

-- 2020-02-19T06:30:12.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:30:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577545
;

-- 2020-02-19T06:30:12.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SummaryAndBalanceReport', Name='Summen- und Saldenliste', Description=NULL, Help=NULL WHERE AD_Element_ID=577545
;

-- 2020-02-19T06:30:12.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SummaryAndBalanceReport', Name='Summen- und Saldenliste', Description=NULL, Help=NULL, AD_Element_ID=577545 WHERE UPPER(ColumnName)='SUMMARYANDBALANCEREPORT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-19T06:30:12.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SummaryAndBalanceReport', Name='Summen- und Saldenliste', Description=NULL, Help=NULL WHERE AD_Element_ID=577545 AND IsCentrallyMaintained='Y'
;

-- 2020-02-19T06:30:22.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Summary and Balance Report', PrintName='Summary and Balance Report',Updated=TO_TIMESTAMP('2020-02-19 08:30:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577545 AND AD_Language='en_US'
;

-- 2020-02-19T06:30:22.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577545,'en_US') 
;

-- 2020-02-19T06:30:45.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET InternalName='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:30:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541437
;

