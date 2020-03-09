-- 2020-01-29T12:21:59.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AccessLevel='1',Updated=TO_TIMESTAMP('2020-01-29 13:21:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540244
;

-- 2020-01-29T12:22:41.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-29 13:22:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=187 AND AD_Language='de_DE'
;

-- 2020-01-29T12:22:41.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(187,'de_DE') 
;

-- 2020-01-29T12:22:41.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(187,'de_DE') 
;

-- 2020-01-29T12:22:48.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=NULL, Description='Bezeichnet einen Geschäftspartner', Help='Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.', Name='Geschäftspartner',Updated=TO_TIMESTAMP('2020-01-29 13:22:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547052
;

-- 2020-01-29T12:22:48.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2020-01-29T12:22:48.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=547052
;

-- 2020-01-29T12:22:48.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(547052)
;

-- 2020-01-29T12:24:40.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=NULL, Description='Bezeichnet einen Geschäftspartner', Help='Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.', Name='Geschäftspartner abw.',Updated=TO_TIMESTAMP('2020-01-29 13:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554064
;

-- 2020-01-29T12:24:40.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541407) 
;

-- 2020-01-29T12:24:40.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554064
;

-- 2020-01-29T12:24:40.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(554064)
;

-- 2020-01-29T12:25:00.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-29 13:25:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541407 AND AD_Language='de_CH'
;

-- 2020-01-29T12:25:00.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541407,'de_CH') 
;

-- 2020-01-29T12:25:06.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-29 13:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541407 AND AD_Language='de_DE'
;

-- 2020-01-29T12:25:06.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541407,'de_DE') 
;

-- 2020-01-29T12:25:06.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541407,'de_DE') 
;

-- 2020-01-29T12:25:52.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geschäftspartner eff.', PrintName='Geschäftspartner eff.',Updated=TO_TIMESTAMP('2020-01-29 13:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542420 AND AD_Language='de_CH'
;

-- 2020-01-29T12:25:52.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542420,'de_CH') 
;

-- 2020-01-29T12:26:13.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Eff. Business Partner', PrintName='Eff. Business Partner',Updated=TO_TIMESTAMP('2020-01-29 13:26:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542420 AND AD_Language='en_US'
;

-- 2020-01-29T12:26:13.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542420,'en_US') 
;

-- 2020-01-29T12:26:22.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geschäftspartner eff.', PrintName='Geschäftspartner eff.',Updated=TO_TIMESTAMP('2020-01-29 13:26:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542420 AND AD_Language='de_DE'
;

-- 2020-01-29T12:26:22.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542420,'de_DE') 
;

-- 2020-01-29T12:26:22.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542420,'de_DE') 
;

-- 2020-01-29T12:26:22.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BPartner_Effective_ID', Name='Geschäftspartner eff.', Description=NULL, Help=NULL WHERE AD_Element_ID=542420
;

-- 2020-01-29T12:26:22.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Effective_ID', Name='Geschäftspartner eff.', Description=NULL, Help=NULL, AD_Element_ID=542420 WHERE UPPER(ColumnName)='C_BPARTNER_EFFECTIVE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-29T12:26:22.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Effective_ID', Name='Geschäftspartner eff.', Description=NULL, Help=NULL WHERE AD_Element_ID=542420 AND IsCentrallyMaintained='Y'
;

-- 2020-01-29T12:26:22.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geschäftspartner eff.', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542420) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542420)
;

-- 2020-01-29T12:26:22.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geschäftspartner eff.', Name='Geschäftspartner eff.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542420)
;

-- 2020-01-29T12:26:22.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geschäftspartner eff.', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542420
;

-- 2020-01-29T12:26:22.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geschäftspartner eff.', Description=NULL, Help=NULL WHERE AD_Element_ID = 542420
;

-- 2020-01-29T12:26:22.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geschäftspartner eff.', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542420
;

