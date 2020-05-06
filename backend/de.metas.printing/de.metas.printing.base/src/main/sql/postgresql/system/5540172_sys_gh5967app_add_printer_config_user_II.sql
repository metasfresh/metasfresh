
UPDATE AD_Printer_Config c SET AD_User_PrinterMatchingConfig_ID=CreatedBy WHERE c.AD_User_PrinterMatchingConfig_ID IS NULL AND EXISTS (select 1 from AD_User u where u.AD_User_ID=c.CreatedBy);
UPDATE AD_Printer_Config c SET AD_User_PrinterMatchingConfig_ID=99 WHERE c.AD_User_PrinterMatchingConfig_ID IS NULL;
COMMIT;

-- 2020-01-02T15:36:30.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-01-02 16:36:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569796
;

-- 2020-01-02T15:36:30.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_printer_config','AD_User_PrinterMatchingConfig_ID','NUMERIC(10)',null,null)
;

-- 2020-01-02T15:36:30.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_printer_config','AD_User_PrinterMatchingConfig_ID',null,'NOT NULL',null)
;

-- 2020-01-02T15:39:32.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='AD_Printer_Config',Updated=TO_TIMESTAMP('2020-01-02 16:39:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540169
;

-- 2020-01-02T15:40:29.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569796,593827,0,540652,0,TO_TIMESTAMP('2020-01-02 16:40:29','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.printing',0,'Y','Y','Y','N','N','N','N','N','Nutzer',60,90,0,1,1,TO_TIMESTAMP('2020-01-02 16:40:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-02T15:40:29.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=593827 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-01-02T15:40:29.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577447) 
;

-- 2020-01-02T15:40:29.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=593827
;

-- 2020-01-02T15:40:29.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(593827)
;

-- 2020-01-02T15:41:34.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_Field_ID=593827, Name='AD_User_PrinterMatchingConfig_ID',Updated=TO_TIMESTAMP('2020-01-02 16:41:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547845
;

-- 2020-01-02T15:43:51.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Nutzer, für den die betreffende Zuordnung gilt. Druckanweisugen werden für den betreffenden Nutzer erstellt.',Updated=TO_TIMESTAMP('2020-01-02 16:43:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577447 AND AD_Language='de_CH'
;

-- 2020-01-02T15:43:51.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577447,'de_CH') 
;

-- 2020-01-02T15:43:56.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Nutzer, für den die betreffende Zuordnung gilt. Druckanweisugen werden für den betreffenden Nutzer erstellt.',Updated=TO_TIMESTAMP('2020-01-02 16:43:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577447 AND AD_Language='de_DE'
;

-- 2020-01-02T15:43:56.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577447,'de_DE') 
;

-- 2020-01-02T15:43:56.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577447,'de_DE') 
;

-- 2020-01-02T15:43:56.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_User_PrinterMatchingConfig_ID', Name='Nutzer', Description='Nutzer, für den die betreffende Zuordnung gilt. Druckanweisugen werden für den betreffenden Nutzer erstellt.', Help=NULL WHERE AD_Element_ID=577447
;

-- 2020-01-02T15:43:56.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_User_PrinterMatchingConfig_ID', Name='Nutzer', Description='Nutzer, für den die betreffende Zuordnung gilt. Druckanweisugen werden für den betreffenden Nutzer erstellt.', Help=NULL, AD_Element_ID=577447 WHERE UPPER(ColumnName)='AD_USER_PRINTERMATCHINGCONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-02T15:43:56.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_User_PrinterMatchingConfig_ID', Name='Nutzer', Description='Nutzer, für den die betreffende Zuordnung gilt. Druckanweisugen werden für den betreffenden Nutzer erstellt.', Help=NULL WHERE AD_Element_ID=577447 AND IsCentrallyMaintained='Y'
;

-- 2020-01-02T15:43:56.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nutzer', Description='Nutzer, für den die betreffende Zuordnung gilt. Druckanweisugen werden für den betreffenden Nutzer erstellt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577447) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577447)
;

-- 2020-01-02T15:43:56.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Nutzer', Description='Nutzer, für den die betreffende Zuordnung gilt. Druckanweisugen werden für den betreffenden Nutzer erstellt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577447
;

-- 2020-01-02T15:43:56.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Nutzer', Description='Nutzer, für den die betreffende Zuordnung gilt. Druckanweisugen werden für den betreffenden Nutzer erstellt.', Help=NULL WHERE AD_Element_ID = 577447
;

-- 2020-01-02T15:43:56.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Nutzer', Description = 'Nutzer, für den die betreffende Zuordnung gilt. Druckanweisugen werden für den betreffenden Nutzer erstellt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577447
;

