-- 2020-09-03T07:04:53.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,ReadOnlyLogic,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,454,0,542860,208,540376,'N',TO_TIMESTAMP('2020-09-03 10:04:52','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','D','N','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','N','M_Product','Y','N','N','N','N','N','Y','N','N','N','N','N','Y','N','N','N',0,'Produkt','N','',10,0,TO_TIMESTAMP('2020-09-03 10:04:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-03T07:04:53.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=542860 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2020-09-03T07:04:53.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(454) 
;

-- 2020-09-03T07:04:53.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542860)
;

-- 2020-09-03T07:11:45.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Element_ID=543488, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Materialcockpit',Updated=TO_TIMESTAMP('2020-09-03 10:11:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542860
;

-- 2020-09-03T07:11:45.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(543488) 
;

-- 2020-09-03T07:11:45.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542860)
;

-- 2020-09-03T07:12:03.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Help='*** placeholder tab ***',Updated=TO_TIMESTAMP('2020-09-03 10:12:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542860
;

