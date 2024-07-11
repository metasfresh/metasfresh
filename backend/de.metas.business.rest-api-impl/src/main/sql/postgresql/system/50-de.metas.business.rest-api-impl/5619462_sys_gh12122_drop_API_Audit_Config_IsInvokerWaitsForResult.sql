-- 2021-12-18T12:05:48.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584431
;

-- 2021-12-18T12:05:48.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645161
;

-- 2021-12-18T12:05:48.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=645161
;

-- 2021-12-18T12:05:48.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=645161
;

-- 2021-12-18T12:05:48.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('API_Audit_Config','ALTER TABLE API_Audit_Config DROP COLUMN IF EXISTS IsInvokerWaitsForResult')
;

-- 2021-12-18T12:05:48.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=573731
;

-- 2021-12-18T12:05:48.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=573731
;

-- 2021-12-18T12:08:02.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsForceProcessedAsync/''Y''@=''Y''',Updated=TO_TIMESTAMP('2021-12-18 14:08:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578921
;

-- 2021-12-18T12:09:56.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578967,676033,0,543895,TO_TIMESTAMP('2021-12-18 14:09:55','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Force async processing',TO_TIMESTAMP('2021-12-18 14:09:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-18T12:09:56.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=676033 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-18T12:09:56.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580404)
;

-- 2021-12-18T12:09:56.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=676033
;

-- 2021-12-18T12:09:56.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(676033)
;

-- 2021-12-18T12:10:23.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,676033,0,543895,598976,545769,'F',TO_TIMESTAMP('2021-12-18 14:10:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Force async processing',25,0,0,TO_TIMESTAMP('2021-12-18 14:10:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-18T12:10:27.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2021-12-18 14:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=598976
;

-- 2021-12-18T12:32:07.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Asynchrone Verarbeitung erzwinge', PrintName='Asynchrone Verarbeitung erzwinge',Updated=TO_TIMESTAMP('2021-12-18 14:32:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580404 AND AD_Language='de_CH'
;

-- 2021-12-18T12:32:07.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580404,'de_CH')
;

-- 2021-12-18T12:32:11.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Asynchrone Verarbeitung erzwinge', PrintName='Asynchrone Verarbeitung erzwinge',Updated=TO_TIMESTAMP('2021-12-18 14:32:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580404 AND AD_Language='de_DE'
;

-- 2021-12-18T12:32:11.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580404,'de_DE')
;

-- 2021-12-18T12:32:11.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580404,'de_DE')
;

-- 2021-12-18T12:32:11.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsForceProcessedAsync', Name='Asynchrone Verarbeitung erzwinge', Description=NULL, Help=NULL WHERE AD_Element_ID=580404
;

-- 2021-12-18T12:32:11.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsForceProcessedAsync', Name='Asynchrone Verarbeitung erzwinge', Description=NULL, Help=NULL, AD_Element_ID=580404 WHERE UPPER(ColumnName)='ISFORCEPROCESSEDASYNC' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-18T12:32:11.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsForceProcessedAsync', Name='Asynchrone Verarbeitung erzwinge', Description=NULL, Help=NULL WHERE AD_Element_ID=580404 AND IsCentrallyMaintained='Y'
;

-- 2021-12-18T12:32:11.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Asynchrone Verarbeitung erzwinge', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580404) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580404)
;

-- 2021-12-18T12:32:11.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Asynchrone Verarbeitung erzwinge', Name='Asynchrone Verarbeitung erzwinge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580404)
;

-- 2021-12-18T12:32:11.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Asynchrone Verarbeitung erzwinge', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580404
;

-- 2021-12-18T12:32:11.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Asynchrone Verarbeitung erzwinge', Description=NULL, Help=NULL WHERE AD_Element_ID = 580404
;

-- 2021-12-18T12:32:11.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Asynchrone Verarbeitung erzwinge', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580404
;

-- 2021-12-18T12:32:15.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Asynchrone Verarbeitung erzwinge', PrintName='Asynchrone Verarbeitung erzwinge',Updated=TO_TIMESTAMP('2021-12-18 14:32:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580404 AND AD_Language='nl_NL'
;

-- 2021-12-18T12:32:15.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580404,'nl_NL')
;

