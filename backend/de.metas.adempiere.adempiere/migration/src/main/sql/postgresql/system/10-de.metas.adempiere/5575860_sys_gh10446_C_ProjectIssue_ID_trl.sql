-- 2021-01-11T08:01:58.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Projekt Zuteilung', PrintName='Projekt Zuteilung',Updated=TO_TIMESTAMP('2021-01-11 10:01:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2178 AND AD_Language='de_CH'
;

-- 2021-01-11T08:01:58.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2178,'de_CH') 
;

-- 2021-01-11T08:02:07.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Projekt Zuteilung', PrintName='Projekt Zuteilung',Updated=TO_TIMESTAMP('2021-01-11 10:02:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2178 AND AD_Language='de_DE'
;

-- 2021-01-11T08:02:07.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2178,'de_DE') 
;

-- 2021-01-11T08:02:07.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(2178,'de_DE') 
;

-- 2021-01-11T08:02:07.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_ProjectIssue_ID', Name='Projekt Zuteilung', Description='Project Issues (Material, Labor)', Help='Issues to the project initiated by the "Issue to Project" process. You can issue Receipts, Time and Expenses, or Stock.' WHERE AD_Element_ID=2178
;

-- 2021-01-11T08:02:07.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_ProjectIssue_ID', Name='Projekt Zuteilung', Description='Project Issues (Material, Labor)', Help='Issues to the project initiated by the "Issue to Project" process. You can issue Receipts, Time and Expenses, or Stock.', AD_Element_ID=2178 WHERE UPPER(ColumnName)='C_PROJECTISSUE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-01-11T08:02:07.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_ProjectIssue_ID', Name='Projekt Zuteilung', Description='Project Issues (Material, Labor)', Help='Issues to the project initiated by the "Issue to Project" process. You can issue Receipts, Time and Expenses, or Stock.' WHERE AD_Element_ID=2178 AND IsCentrallyMaintained='Y'
;

-- 2021-01-11T08:02:07.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Projekt Zuteilung', Description='Project Issues (Material, Labor)', Help='Issues to the project initiated by the "Issue to Project" process. You can issue Receipts, Time and Expenses, or Stock.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2178) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2178)
;

-- 2021-01-11T08:02:07.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Projekt Zuteilung', Name='Projekt Zuteilung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2178)
;

-- 2021-01-11T08:02:07.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Projekt Zuteilung', Description='Project Issues (Material, Labor)', Help='Issues to the project initiated by the "Issue to Project" process. You can issue Receipts, Time and Expenses, or Stock.', CommitWarning = NULL WHERE AD_Element_ID = 2178
;

-- 2021-01-11T08:02:07.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Projekt Zuteilung', Description='Project Issues (Material, Labor)', Help='Issues to the project initiated by the "Issue to Project" process. You can issue Receipts, Time and Expenses, or Stock.' WHERE AD_Element_ID = 2178
;

-- 2021-01-11T08:02:07.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Projekt Zuteilung', Description = 'Project Issues (Material, Labor)', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2178
;

-- 2021-01-11T08:02:21.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Project Issue', PrintName='Project Issue',Updated=TO_TIMESTAMP('2021-01-11 10:02:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2178 AND AD_Language='en_GB'
;

-- 2021-01-11T08:02:21.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2178,'en_GB') 
;

-- 2021-01-11T08:02:26.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Project Issue', PrintName='Project Issue',Updated=TO_TIMESTAMP('2021-01-11 10:02:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2178 AND AD_Language='it_CH'
;

-- 2021-01-11T08:02:26.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2178,'it_CH') 
;

-- 2021-01-11T08:02:31.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Project Issue', PrintName='Project Issue',Updated=TO_TIMESTAMP('2021-01-11 10:02:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2178 AND AD_Language='fr_CH'
;

-- 2021-01-11T08:02:31.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2178,'fr_CH') 
;

-- 2021-01-11T08:02:40.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Project Issue', PrintName='Project Issue',Updated=TO_TIMESTAMP('2021-01-11 10:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2178 AND AD_Language='nl_NL'
;

-- 2021-01-11T08:02:40.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2178,'nl_NL') 
;

-- 2021-01-11T08:02:48.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2021-01-11 10:02:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2178 AND AD_Language='it_CH'
;

-- 2021-01-11T08:02:48.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2178,'it_CH') 
;

-- 2021-01-11T08:02:51.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2021-01-11 10:02:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2178 AND AD_Language='fr_CH'
;

-- 2021-01-11T08:02:51.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2178,'fr_CH') 
;

