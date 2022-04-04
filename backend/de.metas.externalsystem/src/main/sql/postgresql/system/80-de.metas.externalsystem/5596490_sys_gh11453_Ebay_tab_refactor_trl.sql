-- 2021-07-02T13:05:09.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=586975
;

-- 2021-07-02T13:05:09.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650033
;

-- 2021-07-02T13:05:09.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=650033
;

-- 2021-07-02T13:05:09.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=650033
;

-- 2021-07-02T13:05:09.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Ebay','ALTER TABLE ExternalSystem_Config_Ebay DROP COLUMN IF EXISTS ExternalSystemValue')
;

-- 2021-07-02T13:05:09.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=574948
;

-- 2021-07-02T13:05:09.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=574948
;

-- 2021-07-02T13:05:22.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_ebay','IsActive','CHAR(1)',null,null)
;

-- 2021-07-02T13:07:10.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ebay', PrintName='Ebay',Updated=TO_TIMESTAMP('2021-07-02 16:07:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579444 AND AD_Language='de_CH'
;

-- 2021-07-02T13:07:10.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579444,'de_CH') 
;

-- 2021-07-02T13:07:14.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ebay', PrintName='Ebay',Updated=TO_TIMESTAMP('2021-07-02 16:07:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579444 AND AD_Language='de_DE'
;

-- 2021-07-02T13:07:14.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579444,'de_DE') 
;

-- 2021-07-02T13:07:14.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579444,'de_DE') 
;

-- 2021-07-02T13:07:14.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalSystem_Config_Ebay_ID', Name='Ebay', Description=NULL, Help=NULL WHERE AD_Element_ID=579444
;

-- 2021-07-02T13:07:14.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_Ebay_ID', Name='Ebay', Description=NULL, Help=NULL, AD_Element_ID=579444 WHERE UPPER(ColumnName)='EXTERNALSYSTEM_CONFIG_EBAY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-02T13:07:14.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_Ebay_ID', Name='Ebay', Description=NULL, Help=NULL WHERE AD_Element_ID=579444 AND IsCentrallyMaintained='Y'
;

-- 2021-07-02T13:07:14.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ebay', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579444) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579444)
;

-- 2021-07-02T13:07:14.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ebay', Name='Ebay' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579444)
;

-- 2021-07-02T13:07:14.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ebay', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579444
;

-- 2021-07-02T13:07:14.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ebay', Description=NULL, Help=NULL WHERE AD_Element_ID = 579444
;

-- 2021-07-02T13:07:14.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ebay', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579444
;

-- 2021-07-02T13:07:17.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ebay', PrintName='Ebay',Updated=TO_TIMESTAMP('2021-07-02 16:07:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579444 AND AD_Language='en_US'
;

-- 2021-07-02T13:07:17.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579444,'en_US') 
;

-- 2021-07-02T13:07:20.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ebay', PrintName='Ebay',Updated=TO_TIMESTAMP('2021-07-02 16:07:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579444 AND AD_Language='nl_NL'
;

-- 2021-07-02T13:07:20.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579444,'nl_NL') 
;

-- 2021-07-02T13:09:10.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2021-07-02 16:09:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586977
;

-- 2021-07-02T13:09:15.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2021-07-02 16:09:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586976
;

-- 2021-07-02T13:09:30.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,650027,0,544131,586989,546175,'F',TO_TIMESTAMP('2021-07-02 16:09:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'App ID',10,0,0,TO_TIMESTAMP('2021-07-02 16:09:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-02T13:09:44.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,650028,0,544131,586990,546175,'F',TO_TIMESTAMP('2021-07-02 16:09:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Cert ID',20,0,0,TO_TIMESTAMP('2021-07-02 16:09:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-02T13:09:54.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,650029,0,544131,586991,546175,'F',TO_TIMESTAMP('2021-07-02 16:09:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Dev ID',30,0,0,TO_TIMESTAMP('2021-07-02 16:09:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-02T13:10:31.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-07-02 16:10:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586977
;

-- 2021-07-02T13:10:31.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-07-02 16:10:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586989
;

-- 2021-07-02T13:10:31.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-07-02 16:10:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586990
;

-- 2021-07-02T13:10:31.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-07-02 16:10:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586991
;

-- 2021-07-02T13:10:31.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-07-02 16:10:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586976
;

-- 2021-07-02T13:12:06.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='App ID',Updated=TO_TIMESTAMP('2021-07-02 16:12:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579445 AND AD_Language='de_CH'
;

-- 2021-07-02T13:12:06.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579445,'de_CH') 
;

-- 2021-07-02T13:12:09.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='App ID',Updated=TO_TIMESTAMP('2021-07-02 16:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579445 AND AD_Language='de_DE'
;

-- 2021-07-02T13:12:09.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579445,'de_DE') 
;

-- 2021-07-02T13:12:09.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579445,'de_DE') 
;

-- 2021-07-02T13:12:09.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AppId', Name='App ID', Description=NULL, Help=NULL WHERE AD_Element_ID=579445
;

-- 2021-07-02T13:12:09.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AppId', Name='App ID', Description=NULL, Help=NULL, AD_Element_ID=579445 WHERE UPPER(ColumnName)='AppId' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-02T13:12:09.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AppId', Name='App ID', Description=NULL, Help=NULL WHERE AD_Element_ID=579445 AND IsCentrallyMaintained='Y'
;

-- 2021-07-02T13:12:09.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='App ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579445) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579445)
;

-- 2021-07-02T13:12:09.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='App ID', Name='App ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579445)
;

-- 2021-07-02T13:12:09.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='App ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579445
;

-- 2021-07-02T13:12:09.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='App ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 579445
;

-- 2021-07-02T13:12:09.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'App ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579445
;

-- 2021-07-02T13:12:11.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='App ID',Updated=TO_TIMESTAMP('2021-07-02 16:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579445 AND AD_Language='en_US'
;

-- 2021-07-02T13:12:11.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579445,'en_US') 
;

-- 2021-07-02T13:12:16.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='App ID',Updated=TO_TIMESTAMP('2021-07-02 16:12:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579445 AND AD_Language='nl_NL'
;

-- 2021-07-02T13:12:16.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579445,'nl_NL') 
;

