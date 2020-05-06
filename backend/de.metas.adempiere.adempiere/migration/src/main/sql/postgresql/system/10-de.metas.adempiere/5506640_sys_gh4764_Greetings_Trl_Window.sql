-- 2018-11-19T11:33:00.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 11:33:00','YYYY-MM-DD HH24:MI:SS'),Name='Anrede Übersetzung',PrintName='Anrede Übersetzung' WHERE AD_Element_ID=572858 AND AD_Language='de_DE'
;

-- 2018-11-19T11:33:00.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(572858,'de_DE') 
;

-- 2018-11-19T11:33:00.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(572858,'de_DE') 
;

-- 2018-11-19T11:33:00.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Anrede Übersetzung', Description=NULL, Help=NULL WHERE AD_Element_ID=572858
;

-- 2018-11-19T11:33:00.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Anrede Übersetzung', Description=NULL, Help=NULL WHERE AD_Element_ID=572858 AND IsCentrallyMaintained='Y'
;

-- 2018-11-19T11:33:00.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anrede Übersetzung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=572858) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 572858)
;

-- 2018-11-19T11:33:00.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anrede Übersetzung', Name='Anrede Übersetzung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=572858)
;

-- 2018-11-19T11:33:00.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anrede Übersetzung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 572858
;

-- 2018-11-19T11:33:00.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anrede Übersetzung', Description=NULL, Help=NULL WHERE AD_Element_ID = 572858
;

-- 2018-11-19T11:33:00.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Anrede Übersetzung', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 572858
;

-- 2018-11-19T11:33:18.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 11:33:18','YYYY-MM-DD HH24:MI:SS'),Name='Greetings Translation',PrintName='Greetings Translation' WHERE AD_Element_ID=572858 AND AD_Language='en_US'
;

-- 2018-11-19T11:33:18.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(572858,'en_US') 
;


-- 2018-11-19T11:36:33.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,572858,0,540552,TO_TIMESTAMP('2018-11-19 11:36:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','Y','Anrede Übersetzung','N',TO_TIMESTAMP('2018-11-19 11:36:33','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2018-11-19T11:36:33.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540552 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2018-11-19T11:36:33.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(572858) 
;

-- 2018-11-19T11:37:06.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,572858,0,541395,347,540552,TO_TIMESTAMP('2018-11-19 11:37:05','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Anrede Übersetzung','N',10,0,TO_TIMESTAMP('2018-11-19 11:37:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:37:06.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541395 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2018-11-19T11:37:06.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(572858) 
;

-- 2018-11-19T11:37:11.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2018-11-19 11:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541395
;

-- 2018-11-19T11:37:24.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,4082,570681,0,541395,TO_TIMESTAMP('2018-11-19 11:37:24','YYYY-MM-DD HH24:MI:SS'),100,'Language for this entity',6,'D','The Language identifies the language to use for display and formatting','Y','N','N','N','N','N','N','N','Sprache',TO_TIMESTAMP('2018-11-19 11:37:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:37:24.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570681 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-19T11:37:24.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,4083,570682,0,541395,TO_TIMESTAMP('2018-11-19 11:37:24','YYYY-MM-DD HH24:MI:SS'),100,'Greeting to print on correspondence',22,'D','The Greeting identifies the greeting to print on correspondence.','Y','N','N','N','N','N','N','N','Anrede',TO_TIMESTAMP('2018-11-19 11:37:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:37:24.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570682 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-19T11:37:24.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,4084,570683,0,541395,TO_TIMESTAMP('2018-11-19 11:37:24','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',22,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','N','N','Mandant',TO_TIMESTAMP('2018-11-19 11:37:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:37:24.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570683 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-19T11:37:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,4085,570684,0,541395,TO_TIMESTAMP('2018-11-19 11:37:24','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2018-11-19 11:37:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:37:25.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570684 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-19T11:37:25.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,4086,570685,0,541395,TO_TIMESTAMP('2018-11-19 11:37:25','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2018-11-19 11:37:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:37:25.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570685 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-19T11:37:25.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,4091,570686,0,541395,TO_TIMESTAMP('2018-11-19 11:37:25','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity',60,'D','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2018-11-19 11:37:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:37:25.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570686 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-19T11:37:25.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,4092,570687,0,541395,TO_TIMESTAMP('2018-11-19 11:37:25','YYYY-MM-DD HH24:MI:SS'),100,'For letters, e.g. "Dear {0}" or "Dear Mr. {0}" - At runtime, "{0}" is replaced by the name',60,'D','The Greeting indicates what will print on letters sent to a Business Partner.','Y','N','N','N','N','N','N','N','Anrede',TO_TIMESTAMP('2018-11-19 11:37:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:37:25.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570687 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-19T11:37:25.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,4093,570688,0,541395,TO_TIMESTAMP('2018-11-19 11:37:25','YYYY-MM-DD HH24:MI:SS'),100,'This column is translated',1,'D','The Translated checkbox indicates if this column is translated.','Y','N','N','N','N','N','N','N','Übersetzt',TO_TIMESTAMP('2018-11-19 11:37:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:37:25.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570688 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-19T11:37:25.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,548775,570689,0,541395,TO_TIMESTAMP('2018-11-19 11:37:25','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','N','N','N','N','N','N','N','Letter salutation',TO_TIMESTAMP('2018-11-19 11:37:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:37:25.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570689 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-19T11:38:17.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541395,540948,TO_TIMESTAMP('2018-11-19 11:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-11-19 11:38:17','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-11-19T11:38:17.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=540948 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-11-19T11:38:32.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541232,540948,TO_TIMESTAMP('2018-11-19 11:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-11-19 11:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:38:48.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541232,541944,TO_TIMESTAMP('2018-11-19 11:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-11-19 11:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:40:13.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570682,0,541395,554385,541944,'F',TO_TIMESTAMP('2018-11-19 11:40:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Anrede',10,0,0,TO_TIMESTAMP('2018-11-19 11:40:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:40:34.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570681,0,541395,554386,541944,'F',TO_TIMESTAMP('2018-11-19 11:40:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sprache',20,0,0,TO_TIMESTAMP('2018-11-19 11:40:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:40:58.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570686,0,541395,554387,541944,'F',TO_TIMESTAMP('2018-11-19 11:40:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',30,0,0,TO_TIMESTAMP('2018-11-19 11:40:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:43:34.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Anrede ID',Updated=TO_TIMESTAMP('2018-11-19 11:43:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554385
;

-- 2018-11-19T11:43:46.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570687,0,541395,554388,541944,'F',TO_TIMESTAMP('2018-11-19 11:43:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Anrede',40,0,0,TO_TIMESTAMP('2018-11-19 11:43:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:44:09.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570689,0,541395,554389,541944,'F',TO_TIMESTAMP('2018-11-19 11:44:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Letter salutation',50,0,0,TO_TIMESTAMP('2018-11-19 11:44:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:44:29.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541233,540948,TO_TIMESTAMP('2018-11-19 11:44:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-11-19 11:44:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:44:41.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541233,541945,TO_TIMESTAMP('2018-11-19 11:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2018-11-19 11:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:44:58.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570685,0,541395,554390,541945,'F',TO_TIMESTAMP('2018-11-19 11:44:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2018-11-19 11:44:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:45:16.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570688,0,541395,554391,541945,'F',TO_TIMESTAMP('2018-11-19 11:45:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Übersetzt',20,0,0,TO_TIMESTAMP('2018-11-19 11:45:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:45:26.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541233,541946,TO_TIMESTAMP('2018-11-19 11:45:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2018-11-19 11:45:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:45:49.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570684,0,541395,554392,541946,'F',TO_TIMESTAMP('2018-11-19 11:45:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2018-11-19 11:45:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:45:58.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570683,0,541395,554393,541946,'F',TO_TIMESTAMP('2018-11-19 11:45:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2018-11-19 11:45:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:47:05.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-11-19 11:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554385
;

-- 2018-11-19T11:47:05.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-11-19 11:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554386
;

-- 2018-11-19T11:47:05.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-11-19 11:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554387
;

-- 2018-11-19T11:47:05.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-11-19 11:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554388
;

-- 2018-11-19T11:47:05.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-11-19 11:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554389
;

-- 2018-11-19T11:47:05.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-11-19 11:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554390
;

-- 2018-11-19T11:47:05.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-11-19 11:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554391
;

-- 2018-11-19T11:47:05.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2018-11-19 11:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554392
;





-- 2018-11-19T11:50:38.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,572858,541168,0,540552,TO_TIMESTAMP('2018-11-19 11:50:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','C_Greeting_Trl','Y','N','N','N','N','Anrede Übersetzung',TO_TIMESTAMP('2018-11-19 11:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-19T11:50:38.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541168 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-11-19T11:50:38.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541168, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541168)
;

-- 2018-11-19T11:50:38.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(572858) 
;

-- 2018-11-19T11:50:38.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000028 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:38.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540816 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:38.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000032 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:38.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541102 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:38.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000090 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:38.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000029 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:38.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000091 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:38.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541135 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:38.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540850 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:38.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540848 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:38.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541168 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:43.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000028 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:43.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541168 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:43.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540816 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:43.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000032 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:43.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541102 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:43.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000090 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:43.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000029 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:43.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000091 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:43.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541135 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:43.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540850 AND AD_Tree_ID=10
;

-- 2018-11-19T11:50:43.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000025, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540848 AND AD_Tree_ID=10
;

-- 2018-11-19T11:53:01.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2018-11-19 11:53:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548775
;

-- 2018-11-19T11:53:05.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2018-11-19 11:53:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4091
;

-- 2018-11-19T11:53:21.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2018-11-19 11:53:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4092
;

