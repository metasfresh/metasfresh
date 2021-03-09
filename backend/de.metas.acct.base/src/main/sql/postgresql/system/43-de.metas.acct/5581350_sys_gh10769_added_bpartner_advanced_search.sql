-- 2021-03-05T13:30:58.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,541352,0,541045,TO_TIMESTAMP('2021-03-05 15:30:58','YYYY-MM-DD HH24:MI:SS'),100,'D','advSearchBPartner','Y','N','N','N','Y','N','Y','Search','N',TO_TIMESTAMP('2021-03-05 15:30:58','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2021-03-05T13:30:58.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541045 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2021-03-05T13:30:58.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(541352) 
;

-- 2021-03-05T13:30:58.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541045
;

-- 2021-03-05T13:30:58.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(541045)
;

-- 2021-03-05T13:32:51.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578809,0,TO_TIMESTAMP('2021-03-05 15:32:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Business Partner Search','Business Partner Search',TO_TIMESTAMP('2021-03-05 15:32:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-05T13:32:51.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578809 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-05T13:33:26.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-05 15:33:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578809 AND AD_Language='en_US'
;

-- 2021-03-05T13:33:26.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578809,'en_US') 
;

-- 2021-03-05T13:33:32.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geschäftspartnersuche',Updated=TO_TIMESTAMP('2021-03-05 15:33:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578809 AND AD_Language='de_DE'
;

-- 2021-03-05T13:33:32.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578809,'de_DE') 
;

-- 2021-03-05T13:33:32.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578809,'de_DE') 
;

-- 2021-03-05T13:33:32.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Geschäftspartnersuche', Description=NULL, Help=NULL WHERE AD_Element_ID=578809
;

-- 2021-03-05T13:33:32.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Geschäftspartnersuche', Description=NULL, Help=NULL WHERE AD_Element_ID=578809 AND IsCentrallyMaintained='Y'
;

-- 2021-03-05T13:33:32.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geschäftspartnersuche', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578809) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578809)
;

-- 2021-03-05T13:33:32.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Business Partner Search', Name='Geschäftspartnersuche' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578809)
;

-- 2021-03-05T13:33:32.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geschäftspartnersuche', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578809
;

-- 2021-03-05T13:33:32.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geschäftspartnersuche', Description=NULL, Help=NULL WHERE AD_Element_ID = 578809
;

-- 2021-03-05T13:33:32.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geschäftspartnersuche', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578809
;

-- 2021-03-05T13:33:34.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geschäftspartnersuche',Updated=TO_TIMESTAMP('2021-03-05 15:33:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578809 AND AD_Language='de_CH'
;

-- 2021-03-05T13:33:34.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578809,'de_CH') 
;

-- 2021-03-05T13:33:37.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geschäftspartnersuche',Updated=TO_TIMESTAMP('2021-03-05 15:33:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578809 AND AD_Language='nl_NL'
;

-- 2021-03-05T13:33:37.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578809,'nl_NL') 
;

-- 2021-03-05T13:33:38.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Geschäftspartnersuche',Updated=TO_TIMESTAMP('2021-03-05 15:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578809 AND AD_Language='de_CH'
;

-- 2021-03-05T13:33:38.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578809,'de_CH') 
;

-- 2021-03-05T13:33:40.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Geschäftspartnersuche',Updated=TO_TIMESTAMP('2021-03-05 15:33:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578809 AND AD_Language='de_DE'
;

-- 2021-03-05T13:33:40.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578809,'de_DE') 
;

-- 2021-03-05T13:33:40.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578809,'de_DE') 
;

-- 2021-03-05T13:33:40.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geschäftspartnersuche', Name='Geschäftspartnersuche' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578809)
;

-- 2021-03-05T13:33:42.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Geschäftspartnersuche',Updated=TO_TIMESTAMP('2021-03-05 15:33:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578809 AND AD_Language='nl_NL'
;

-- 2021-03-05T13:33:42.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578809,'nl_NL') 
;

-- 2021-03-05T13:34:01.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET AD_Element_ID=578809, Description=NULL, Help=NULL, Name='Geschäftspartnersuche',Updated=TO_TIMESTAMP('2021-03-05 15:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541045
;

-- 2021-03-05T13:34:01.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(578809) 
;

-- 2021-03-05T13:34:01.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541045
;

-- 2021-03-05T13:34:01.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(541045)
;

-- 2021-03-05T13:35:01.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Window_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541588,541045,'N',TO_TIMESTAMP('2021-03-05 15:35:01','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','N','N','N','N','N','N',0,'Business Partner Advanced Search','NP','L','C_BPartner_Adv_Search_v','DTI',TO_TIMESTAMP('2021-03-05 15:35:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-05T13:35:01.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541588 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-03-05T13:35:01.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555255,TO_TIMESTAMP('2021-03-05 15:35:01','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_BPartner_Adv_Search_v',1,'Y','N','Y','Y','C_BPartner_Adv_Search_v','N',1000000,TO_TIMESTAMP('2021-03-05 15:35:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-05T13:35:01.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE SEQUENCE C_BPARTNER_ADV_SEARCH_V_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-03-05T13:35:41.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,ColumnName,Description,AD_Element_ID,Name,AD_Org_ID,EntityType) VALUES (30,10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-05 15:35:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N',TO_TIMESTAMP('2021-03-05 15:35:41','YYYY-MM-DD HH24:MI:SS'),100,541588,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',573055,'N','C_BPartner_ID','Bezeichnet einen Geschäftspartner',187,'Geschäftspartner',0,'D')
;

-- 2021-03-05T13:35:41.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573055 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-05T13:35:41.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- 2021-03-05T13:35:42.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,ColumnName,Description,AD_Element_ID,Name,AD_Org_ID,EntityType) VALUES (30,10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-05 15:35:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N',TO_TIMESTAMP('2021-03-05 15:35:42','YYYY-MM-DD HH24:MI:SS'),100,541588,'Identifiziert die Adresse des Geschäftspartners',573056,'N','C_BPartner_Location_ID','Identifiziert die (Liefer-) Adresse des Geschäftspartners',189,'Standort',0,'D')
;

-- 2021-03-05T13:35:42.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573056 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-05T13:35:42.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(189) 
;

-- 2021-03-05T13:35:42.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,ColumnName,Description,AD_Element_ID,Name,AD_Org_ID,EntityType) VALUES (10,255,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-05 15:35:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N',TO_TIMESTAMP('2021-03-05 15:35:42','YYYY-MM-DD HH24:MI:SS'),100,541588,573057,'N','Firstname','Vorname',540398,'Vorname',0,'D')
;

-- 2021-03-05T13:35:42.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573057 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-05T13:35:42.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(540398) 
;

-- 2021-03-05T13:35:42.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,ColumnName,AD_Element_ID,Name,AD_Org_ID,EntityType) VALUES (10,255,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-05 15:35:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N',TO_TIMESTAMP('2021-03-05 15:35:42','YYYY-MM-DD HH24:MI:SS'),100,541588,573058,'N','Lastname',540399,'Nachname',0,'D')
;

-- 2021-03-05T13:35:42.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573058 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-05T13:35:42.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(540399) 
;

-- 2021-03-05T13:35:42.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,ColumnName,Description,AD_Element_ID,Name,AD_Org_ID,EntityType) VALUES (10,60,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-05 15:35:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N',TO_TIMESTAMP('2021-03-05 15:35:42','YYYY-MM-DD HH24:MI:SS'),100,541588,'Bezeichnet einen einzelnen Ort in diesem Land oder dieser Region.',573059,'N','City','Name des Ortes',225,'Ort',0,'D')
;

-- 2021-03-05T13:35:42.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573059 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-05T13:35:42.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(225) 
;

-- 2021-03-05T13:38:45.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (Created,HasTree,AD_Window_ID,SeqNo,IsSingleRow,AD_Client_ID,Updated,IsActive,CreatedBy,UpdatedBy,IsInfoTab,IsTranslationTab,IsReadOnly,IsSortTab,ImportFields,TabLevel,IsInsertRecord,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsQueryOnLoad,IsGridModeOnly,AD_Table_ID,AD_Tab_ID,IsGenericZoomTarget,IsCheckParentsChanged,MaxQueryRecords,InternalName,AllowQuickInput,IsRefreshViewOnChangeEvents,AD_Element_ID,Processing,IsAdvancedTab,Name,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2021-03-05 15:38:45','YYYY-MM-DD HH24:MI:SS'),'N',541045,10,'N',0,TO_TIMESTAMP('2021-03-05 15:38:45','YYYY-MM-DD HH24:MI:SS'),'Y',100,100,'N','N','N','N','N',0,'N','N','Y','Y','Y','N',541588,543530,'N','Y',0,'C_BPartner_Adv_Search_v','Y','N',541352,'N','N','Search',0,'D')
;

-- 2021-03-05T13:38:45.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543530 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-03-05T13:38:45.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(541352) 
;

-- 2021-03-05T13:38:45.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543530)
;

-- 2021-03-05T13:41:35.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', IsShowFilterInline='Y', FilterOperator='E',Updated=TO_TIMESTAMP('2021-03-05 15:41:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573059
;

-- 2021-03-05T13:41:51.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', IsShowFilterInline='Y', FilterOperator='E',Updated=TO_TIMESTAMP('2021-03-05 15:41:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573057
;

-- 2021-03-05T13:42:13.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', IsShowFilterInline='Y', FilterOperator='E',Updated=TO_TIMESTAMP('2021-03-05 15:42:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573058
;

-- 2021-03-05T13:42:50.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2021-03-05 15:42:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573056
;

-- 2021-03-05T13:44:10.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (543530,'N',0,10.000000000000,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-05 15:44:10','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2021-03-05 15:44:10','YYYY-MM-DD HH24:MI:SS'),100,635057,'Y',10,10,1,1,573058,'Nachname',0,'D')
;

-- 2021-03-05T13:44:10.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635057 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-05T13:44:10.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540399) 
;

-- 2021-03-05T13:44:10.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635057
;

-- 2021-03-05T13:44:10.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635057)
;

-- 2021-03-05T13:44:30.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,Description,AD_Org_ID,EntityType) VALUES (543530,'N',0,20.000000000000,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-05 15:44:29','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2021-03-05 15:44:29','YYYY-MM-DD HH24:MI:SS'),100,635058,'Y',20,20,1,1,573057,'Vorname','Vorname',0,'U')
;

-- 2021-03-05T13:44:30.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635058 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-05T13:44:30.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540398) 
;

-- 2021-03-05T13:44:30.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635058
;

-- 2021-03-05T13:44:30.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635058)
;

-- 2021-03-05T13:44:47.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,Description,AD_Org_ID,EntityType) VALUES (543530,'N',0,30.000000000000,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-05 15:44:47','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2021-03-05 15:44:47','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen einzelnen Ort in diesem Land oder dieser Region.',635059,'Y',30,30,1,1,573059,'Ort','Name des Ortes',0,'D')
;

-- 2021-03-05T13:44:47.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635059 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-05T13:44:47.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(225) 
;

-- 2021-03-05T13:44:47.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635059
;

-- 2021-03-05T13:44:47.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635059)
;

-- 2021-03-05T13:44:57.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2021-03-05 15:44:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635058
;

-- 2021-03-05T13:57:09.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,Description,AD_Org_ID,EntityType) VALUES (543530,'N',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-05 15:57:09','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2021-03-05 15:57:09','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die Adresse des Geschäftspartners',635060,'N',40,40,1,1,573056,'Standort','Identifiziert die (Liefer-) Adresse des Geschäftspartners',0,'D')
;

-- 2021-03-05T13:57:09.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-05T13:57:09.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2021-03-05T13:57:09.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635060
;

-- 2021-03-05T13:57:09.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635060)
;

-- 2021-03-05T14:09:43.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsKey='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-03-05 16:09:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573056
;

-- 2021-03-05T14:24:47.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2021-03-05 16:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573058
;

-- 2021-03-05T14:24:47.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2021-03-05 16:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573057
;

-- 2021-03-05T14:24:47.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2021-03-05 16:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573059
;

-- 2021-03-09T14:53:55.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578812,0,TO_TIMESTAMP('2021-03-09 16:53:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Search Asistant','Search Asistant',TO_TIMESTAMP('2021-03-09 16:53:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-09T14:53:55.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578812 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-09T14:54:12.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Suchassistent',Updated=TO_TIMESTAMP('2021-03-09 16:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578812 AND AD_Language='de_CH'
;

-- 2021-03-09T14:54:12.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578812,'de_CH') 
;

-- 2021-03-09T14:54:13.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Suchassistent',Updated=TO_TIMESTAMP('2021-03-09 16:54:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578812 AND AD_Language='de_DE'
;

-- 2021-03-09T14:54:13.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578812,'de_DE') 
;

-- 2021-03-09T14:54:13.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578812,'de_DE') 
;

-- 2021-03-09T14:54:13.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Suchassistent', Description=NULL, Help=NULL WHERE AD_Element_ID=578812
;

-- 2021-03-09T14:54:13.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Suchassistent', Description=NULL, Help=NULL WHERE AD_Element_ID=578812 AND IsCentrallyMaintained='Y'
;

-- 2021-03-09T14:54:13.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Suchassistent', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578812) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578812)
;

-- 2021-03-09T14:54:13.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Search Asistant', Name='Suchassistent' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578812)
;

-- 2021-03-09T14:54:13.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Suchassistent', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578812
;

-- 2021-03-09T14:54:13.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Suchassistent', Description=NULL, Help=NULL WHERE AD_Element_ID = 578812
;

-- 2021-03-09T14:54:13.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Suchassistent', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578812
;

-- 2021-03-09T14:54:16.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Suchassistent',Updated=TO_TIMESTAMP('2021-03-09 16:54:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578812 AND AD_Language='nl_NL'
;

-- 2021-03-09T14:54:16.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578812,'nl_NL') 
;

-- 2021-03-09T14:54:17.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-09 16:54:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578812 AND AD_Language='en_US'
;

-- 2021-03-09T14:54:17.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578812,'en_US') 
;

-- 2021-03-09T14:54:18.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Suchassistent',Updated=TO_TIMESTAMP('2021-03-09 16:54:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578812 AND AD_Language='de_DE'
;

-- 2021-03-09T14:54:18.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578812,'de_DE') 
;

-- 2021-03-09T14:54:18.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578812,'de_DE') 
;

-- 2021-03-09T14:54:18.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Suchassistent', Name='Suchassistent' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578812)
;

-- 2021-03-09T14:54:19.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Suchassistent',Updated=TO_TIMESTAMP('2021-03-09 16:54:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578812 AND AD_Language='de_CH'
;

-- 2021-03-09T14:54:19.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578812,'de_CH') 
;

-- 2021-03-09T14:54:22.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Suchassistent',Updated=TO_TIMESTAMP('2021-03-09 16:54:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578812 AND AD_Language='nl_NL'
;

-- 2021-03-09T14:54:22.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578812,'nl_NL') 
;

-- 2021-03-09T14:55:33.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Element_ID=578812, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Suchassistent',Updated=TO_TIMESTAMP('2021-03-09 16:55:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543530
;

-- 2021-03-09T14:55:33.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(578812) 
;

-- 2021-03-09T14:55:33.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543530)
;
 