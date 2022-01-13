-- 2021-06-23T15:01:07.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Material-Dispo-Datensatz, auf den sich die Material-Transaktion ursprünglich bezog. Wenn sich nicht Merkmale oder das Datum geändert hätte, wäre sie diesem Datensatz direkt zugeordnet.',Updated=TO_TIMESTAMP('2021-06-23 18:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578585 AND AD_Language='de_CH'
;

-- 2021-06-23T15:01:07.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578585,'de_CH') 
;

-- 2021-06-23T15:01:18.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Material-Dispo-Datensatz, auf den sich die Material-Transaktion ursprünglich bezog. Wenn sich nicht Merkmale oder das Datum geändert hätte, wäre sie diesem Datensatz direkt zugeordnet.',Updated=TO_TIMESTAMP('2021-06-23 18:01:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578585 AND AD_Language='de_DE'
;

-- 2021-06-23T15:01:18.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578585,'de_DE') 
;

-- 2021-06-23T15:01:18.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578585,'de_DE') 
;

-- 2021-06-23T15:01:18.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MD_Candidate_RebookedFrom_ID', Name='Umgebucht von', Description='Material-Dispo-Datensatz, auf den sich die Material-Transaktion ursprünglich bezog. Wenn sich nicht Merkmale oder das Datum geändert hätte, wäre sie diesem Datensatz direkt zugeordnet.', Help=NULL WHERE AD_Element_ID=578585
;

-- 2021-06-23T15:01:18.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MD_Candidate_RebookedFrom_ID', Name='Umgebucht von', Description='Material-Dispo-Datensatz, auf den sich die Material-Transaktion ursprünglich bezog. Wenn sich nicht Merkmale oder das Datum geändert hätte, wäre sie diesem Datensatz direkt zugeordnet.', Help=NULL, AD_Element_ID=578585 WHERE UPPER(ColumnName)='MD_CANDIDATE_REBOOKEDFROM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T15:01:18.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MD_Candidate_RebookedFrom_ID', Name='Umgebucht von', Description='Material-Dispo-Datensatz, auf den sich die Material-Transaktion ursprünglich bezog. Wenn sich nicht Merkmale oder das Datum geändert hätte, wäre sie diesem Datensatz direkt zugeordnet.', Help=NULL WHERE AD_Element_ID=578585 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T15:01:18.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Umgebucht von', Description='Material-Dispo-Datensatz, auf den sich die Material-Transaktion ursprünglich bezog. Wenn sich nicht Merkmale oder das Datum geändert hätte, wäre sie diesem Datensatz direkt zugeordnet.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578585) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578585)
;

-- 2021-06-23T15:01:18.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Umgebucht von', Description='Material-Dispo-Datensatz, auf den sich die Material-Transaktion ursprünglich bezog. Wenn sich nicht Merkmale oder das Datum geändert hätte, wäre sie diesem Datensatz direkt zugeordnet.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578585
;

-- 2021-06-23T15:01:18.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Umgebucht von', Description='Material-Dispo-Datensatz, auf den sich die Material-Transaktion ursprünglich bezog. Wenn sich nicht Merkmale oder das Datum geändert hätte, wäre sie diesem Datensatz direkt zugeordnet.', Help=NULL WHERE AD_Element_ID = 578585
;

-- 2021-06-23T15:01:18.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Umgebucht von', Description = 'Material-Dispo-Datensatz, auf den sich die Material-Transaktion ursprünglich bezog. Wenn sich nicht Merkmale oder das Datum geändert hätte, wäre sie diesem Datensatz direkt zugeordnet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578585
;

-- 2021-06-23T15:01:23.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Material-Dispo-Datensatz, auf den sich die Material-Transaktion ursprünglich bezog. Wenn sich nicht Merkmale oder das Datum geändert hätte, wäre sie diesem Datensatz direkt zugeordnet.',Updated=TO_TIMESTAMP('2021-06-23 18:01:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578585 AND AD_Language='nl_NL'
;

-- 2021-06-23T15:01:23.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578585,'nl_NL') 
;

-- 2021-06-23T15:01:31.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Material dispo record to which this material transaction originally related. If not attributes or date had been unchanged, then this material transaction would have been added to that record.',Updated=TO_TIMESTAMP('2021-06-23 18:01:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578585 AND AD_Language='en_US'
;

-- 2021-06-23T15:01:31.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578585,'en_US') 
;

-- 2021-06-23T15:01:47.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('md_candidate_transaction_detail','MD_Candidate_RebookedFrom_ID','NUMERIC(10)',null,null)
;

