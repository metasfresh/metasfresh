-- 2019-04-05T11:07:12.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576561,0,TO_TIMESTAMP('2019-04-05 11:07:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Firmenverzeichnis','Firmenverzeichnis',TO_TIMESTAMP('2019-04-05 11:07:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:07:12.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576561 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-05T11:07:19.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-05 11:07:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576561 AND AD_Language='de_CH'
;

-- 2019-04-05T11:07:19.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576561,'de_CH') 
;

-- 2019-04-05T11:07:23.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-05 11:07:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576561 AND AD_Language='de_DE'
;

-- 2019-04-05T11:07:23.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576561,'de_DE') 
;

-- 2019-04-05T11:07:23.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576561,'de_DE') 
;

-- 2019-04-05T11:07:45.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Company Phone Book', PrintName='Company Phone Book',Updated=TO_TIMESTAMP('2019-04-05 11:07:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576561 AND AD_Language='en_US'
;

-- 2019-04-05T11:07:45.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576561,'en_US') 
;

-- 2019-04-05T11:07:57.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Company Phone Book', PrintName='Company Phone Book',Updated=TO_TIMESTAMP('2019-04-05 11:07:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576561
;

-- 2019-04-05T11:07:57.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Company Phone Book', Description=NULL, Help=NULL WHERE AD_Element_ID=576561
;

-- 2019-04-05T11:07:57.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Company Phone Book', Description=NULL, Help=NULL WHERE AD_Element_ID=576561 AND IsCentrallyMaintained='Y'
;

-- 2019-04-05T11:07:57.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Company Phone Book', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576561) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576561)
;

-- 2019-04-05T11:07:57.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Company Phone Book', Name='Company Phone Book' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576561)
;

-- 2019-04-05T11:07:57.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Company Phone Book', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576561
;

-- 2019-04-05T11:07:57.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Company Phone Book', Description=NULL, Help=NULL WHERE AD_Element_ID = 576561
;

-- 2019-04-05T11:07:57.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Company Phone Book', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576561
;

-- 2019-04-05T11:09:07.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,576561,0,540617,TO_TIMESTAMP('2019-04-05 11:09:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','Y','Company Phone Book','N',TO_TIMESTAMP('2019-04-05 11:09:07','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-04-05T11:09:07.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540617 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-04-05T11:09:07.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(576561) 
;

-- 2019-04-05T11:09:48.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,138,0,541699,114,540617,'Y',TO_TIMESTAMP('2019-04-05 11:09:48','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','U','N','The User identifies a unique user in the system. This could be an internal user or a business partner contact','N','AD_User','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Ansprechpartner','N',10,0,TO_TIMESTAMP('2019-04-05 11:09:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:09:48.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541699 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-04-05T11:09:48.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(138) 
;

-- 2019-04-05T11:09:54.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET EntityType='D',Updated=TO_TIMESTAMP('2019-04-05 11:09:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541699
;

-- 2019-04-05T11:10:10.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,212,578447,0,541699,TO_TIMESTAMP('2019-04-05 11:10:09','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',22,'D','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','N','N','N','N','N','Ansprechpartner',TO_TIMESTAMP('2019-04-05 11:10:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:10.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578447 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:10.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,213,578448,0,541699,TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100,'',60,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:10.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578448 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:10.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,214,578449,0,541699,TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:10.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578449 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:10.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,417,578450,0,541699,TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100,'',255,'D','The Password for this User.  Passwords are required to identify authorized users.  For metasfresh Users, you can change the password via the Process "Reset Password".','Y','N','N','N','N','N','N','N','Kennwort',TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:10.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578450 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:10.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,422,578451,0,541699,TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',22,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','N','N','Mandant',TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:10.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578451 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:10.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,423,578452,0,541699,TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:10.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578452 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:10.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,622,578453,0,541699,TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:10.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578453 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:10.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,5396,578454,0,541699,TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse',200,'D','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','N','N','N','N','N','eMail',TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:10.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578454 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:10.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,5397,578455,0,541699,TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100,'Supervisor for this user/organization - used for escalation and approval',22,'D','The Supervisor indicates who will be used for forwarding and escalating issues for this user - or for approvals.','Y','N','N','N','N','N','N','N','Vorgesetzter',TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:10.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578455 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:11.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,5844,578456,0,541699,TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100,'Identifies a Business Partner',22,'D','A Business Partner is anyone with whom you transact.  This can include Vendor, Customer, Employee or Salesperson','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2019-04-05 11:10:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:11.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578456 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:11.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,6314,578457,0,541699,TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Verarbeiten',TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:11.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578457 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:11.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,7793,578458,0,541699,TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100,'User Name (ID) in the Mail System',60,'D','The user name in the mail system is usually the string before the @ of your email address.  Required if the mail server requires authentification to send emails.','Y','N','N','N','N','N','N','N','EMail Nutzer-ID',TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:11.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578458 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:11.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,7794,578459,0,541699,TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100,'Passwort Ihrer EMail Nutzer-ID',20,'D','Notwendig, wenn der EMail-Server eine Anmeldung vor dem Versenden von EMails verlangt.','Y','N','N','N','N','N','N','N','Passwort EMail-Nutzer',TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:11.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578459 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:11.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8743,578460,0,541699,TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz',22,'D','Anrede, die beim Druck auf Korrespondenz verwendet werden soll.','Y','N','N','N','N','N','N','N','Anrede (ID)',TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:11.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578460 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:11.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8744,578461,0,541699,TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100,'Alternative Mobile Telefonnummer',40,'D','"Telfon (alternativ)" gibt eine weitere Telefonnummer an.','Y','N','N','N','N','N','N','N','Mobil',TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:11.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578461 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:11.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8745,578462,0,541699,TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100,'Birthday or Anniversary day',7,'D','Birthday or Anniversary day','Y','N','N','N','N','N','N','N','Geburtstag',TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:11.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578462 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:11.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8746,578463,0,541699,TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100,'Identifies the (ship to) address for this Business Partner',22,'D','The Partner address indicates the location of a Business Partner','Y','N','N','N','N','N','N','N','Standort',TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:11.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578463 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:11.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8747,578464,0,541699,TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer',40,'D','Beschreibt eine Telefon Nummer','Y','N','N','N','N','N','N','N','Telefon',TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:11.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578464 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:11.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8748,578465,0,541699,TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100,'Facsimile number',40,'D','The Fax identifies a facsimile number for this Business Partner or  Location','Y','N','N','N','N','N','N','N','Fax',TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:11.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578465 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:12.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8751,578466,0,541699,TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100,'Name this entity is referred to as',40,'D','The Title indicates the name that an entity is referred to as.','Y','N','N','N','N','N','N','N','Titel',TO_TIMESTAMP('2019-04-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:12.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578466 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:12.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8752,578467,0,541699,TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100,'Comments or additional information',2000,'D','The Comments field allows for free form entry of additional information.','Y','N','N','N','N','N','N','N','Bemerkungen',TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:12.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578467 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:12.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8976,578468,0,541699,TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100,'Performing or initiating organization',22,'D','The organization which performs or initiates this transaction (for another organization).  The owning Organization may not be the transaction organization in a service bureau environment, with centralized services, and inter-organization transactions.','Y','N','N','N','N','N','N','N','Buchende Organisation',TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:12.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578468 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:12.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,9884,578469,0,541699,TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100,'Verification information of EMail Address',40,'D','The field contains additional information how the EMail Address has been verified','Y','N','N','N','N','N','N','N','Überprüfung EMail',TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:12.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578469 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:12.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,13600,578470,0,541699,TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100,'Date Email was verified',7,'D','Y','N','N','N','N','N','N','N','EMail überprüft',TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:12.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578470 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:12.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,13773,578471,0,541699,TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100,'Type of Notifications',1,'D','Emails or Notification sent out for Request Updates, etc.','Y','N','N','N','N','N','N','N','Benachrichtigungs-Art',TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:12.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578471 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:12.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,14336,578472,0,541699,TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100,'The user/contact has full access to Business Partner information and resources',1,'D','If selected, the user has full access to the Business Partner (BP) information (Business Documents like Orders, Invoices - Requests) or resources (Assets, Downloads). If you deselet it, the user has no access rights unless, you explicitly grant it in tab "BP Access"','Y','N','N','N','N','N','N','N','Full BP Access',TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:12.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578472 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:12.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,14396,578473,0,541699,TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100,'Job Position',1,'D','Y','N','N','N','N','N','N','N','Position',TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:12.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578473 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:12.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,14619,578474,0,541699,TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100,'How a Java Client connects to the server(s)',1,'D','Depending on the connection profile, different protocols are used and tasks are performed on the server rather then the client. Usually the user can select different profiles, unless it is enforced by the User or Role definition. The User level profile overwrites the Role based profile.','Y','N','N','N','N','N','N','N','Verbindungsart',TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:12.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578474 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:12.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,15975,578475,0,541699,TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required - must be unique',40,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:12.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578475 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:13.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,52066,578476,0,541699,TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100,20,'D','Y','N','N','N','N','N','N','N','Benutzer PIN',TO_TIMESTAMP('2019-04-05 11:10:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:13.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578476 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:13.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,56294,578477,0,541699,TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100,'Defined if any User Contact will be used for Calculate Payroll',1,'D','Y','N','N','N','N','N','N','N','Is In Payroll',TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:13.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578477 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:13.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,59918,578478,0,541699,TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100,40,'D','Y','N','N','N','N','N','N','N','Code für Passwort-Änderung',TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:13.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578478 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:13.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,500036,578479,0,541699,TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Standard-Ansprechpartner',TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:13.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578479 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:13.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541639,578480,0,541699,TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100,'Included Tab in this Tab (Master Dateail)',1,'D','You can include a Tab in a Tab. If displayed in single row record, the included tab is displayed as multi-row table.','Y','N','N','N','N','N','N','N','Included Tab',TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:13.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578480 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:13.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541683,578481,0,541699,TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Nachname',TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:13.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578481 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:13.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541684,578482,0,541699,TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100,'Vorname',255,'D','Y','N','N','N','N','N','N','N','Vorname',TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:13.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578482 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:13.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541897,578483,0,541699,TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','IsSubjectMatterContact',TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:13.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578483 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:13.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,541898,578484,0,541699,TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','IsSalesContact',TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:13.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578484 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:13.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542511,578485,0,541699,TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100,100,'D','Y','N','N','N','N','N','N','N','Handynummer',TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:13.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578485 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:14.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542512,578486,0,541699,TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Kontakt Einschränkung',TO_TIMESTAMP('2019-04-05 11:10:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:14.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578486 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:14.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542513,578487,0,541699,TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Begründung',TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:14.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578487 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:14.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542515,578488,0,541699,TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,40,'D','Y','N','N','N','N','N','N','N','Portalpasswort',TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:14.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578488 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:14.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,543402,578489,0,541699,TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Löschdatum',TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:14.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578489 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:14.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,543407,578490,0,541699,TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Handelsregister',255,'D','Y','N','N','N','N','N','N','N','Handelsregister',TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:14.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578490 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:14.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,543410,578491,0,541699,TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl Login Fehlversuche',2,'D','Y','N','N','N','N','N','N','N','Anzahl Login Fehlversuche',TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:14.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578491 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:14.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,543411,578492,0,541699,TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Kennzeichen das anzeigt ob der Zugang gesperrt wurde',1,'D','Y','N','N','N','N','N','N','N','Zugang gesperrt',TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:14.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578492 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:14.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,543412,578493,0,541699,TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Client IP address that was used when this account was locked ',20,'D','Y','N','N','N','N','N','N','N','Sperrung von IP',TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:14.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578493 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:14.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,543413,578494,0,541699,TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Datum Login Fehler',20,'D','Y','N','N','N','N','N','N','N','Datum Login Fehler',TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:14.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578494 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:14.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,543414,578495,0,541699,TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Button that will call a process to unlock current selected account',15,'D','Y','N','N','N','N','N','N','N','Zugang entsperren',TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:14.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578495 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:15.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,548983,578496,0,541699,TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Used for login. See Help.',255,'D','task 04259','Y','N','N','N','N','N','N','N','Login',TO_TIMESTAMP('2019-04-05 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:15.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578496 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:15.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549001,578497,0,541699,TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Systembenutzer',TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:15.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578497 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:15.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549129,578498,0,541699,TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'When data is imported from a an external datasource, this element can be used to identify the data record',255,'D','Y','N','N','N','N','N','N','N','Migration_Key',TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:15.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578498 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:15.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549866,578499,0,541699,TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Weihnachtsgeschenk',TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:15.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578499 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:15.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550221,578500,0,541699,TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Einkaufskontakt',TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:15.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578500 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:15.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551164,578501,0,541699,TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Weihnachtsgeschenk',TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:15.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578501 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:15.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552868,578502,0,541699,TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Person, die bei einem fachlichen Problem vom System informiert wird.',10,'D','Y','N','N','N','N','N','N','N','Betreuer',TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:15.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578502 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:15.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554318,578503,0,541699,TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann',1,'D','Y','N','N','N','N','N','N','N','Mengenmeldung App',TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:15.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578503 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:15.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554399,578504,0,541699,TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Login As HostKey ',TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:15.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578504 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:16.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555109,578505,0,541699,TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Rechnung per eMail',TO_TIMESTAMP('2019-04-05 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:16.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578505 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:16.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556073,578506,0,541699,TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','IsSalesContact_Default',TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:16.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578506 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:16.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556074,578507,0,541699,TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','IsPurchaseContact_Default',TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:16.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578507 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:16.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556333,578508,0,541699,TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.',10,'D','Der eigenentlich druckende Nutzer braucht somit keine Druck-Konfiguration.','Y','N','N','N','N','N','N','N','Druck-Empfänger',TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:16.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578508 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:16.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556869,578509,0,541699,TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag',6,'D','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','N','N','N','N','N','N','Sprache',TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:16.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578509 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:16.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556870,578510,0,541699,TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Avatar',TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:16.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578510 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:16.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557164,578511,0,541699,TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Rechnungskontakt',TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:16.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578511 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:16.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557165,578512,0,541699,TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Lieferkontakt',TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:16.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578512 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:16.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559263,578513,0,541699,TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Template or container uses news channels',1,'D','This content (container or template) uses news channels','Y','N','N','N','N','N','N','N','Uses News',TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:16.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578513 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:16.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559598,578514,0,541699,TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,100,'D','Y','N','N','N','N','N','N','N','Position',TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:16.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578514 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:17.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560058,578515,0,541699,TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Newsletter',TO_TIMESTAMP('2019-04-05 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:17.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578515 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:17.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560071,578516,0,541699,TO_TIMESTAMP('2019-04-05 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Entscheider',TO_TIMESTAMP('2019-04-05 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:17.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578516 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:17.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560072,578517,0,541699,TO_TIMESTAMP('2019-04-05 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Geschäftsführer ',TO_TIMESTAMP('2019-04-05 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:17.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578517 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:17.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560073,578518,0,541699,TO_TIMESTAMP('2019-04-05 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Multiplikator',TO_TIMESTAMP('2019-04-05 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:17.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578518 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:17.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560150,578519,0,541699,TO_TIMESTAMP('2019-04-05 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Memo Text',5000,'D','Y','N','N','N','N','N','N','N','Memo',TO_TIMESTAMP('2019-04-05 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:17.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578519 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:17.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560677,578520,0,541699,TO_TIMESTAMP('2019-04-05 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Passwort in Mengenmeldung App',TO_TIMESTAMP('2019-04-05 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:17.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578520 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:10:17.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560990,578521,0,541699,TO_TIMESTAMP('2019-04-05 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','External ID',TO_TIMESTAMP('2019-04-05 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:10:17.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578521 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:13:53.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578447
;

-- 2019-04-05T11:13:53.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578447
;

-- 2019-04-05T11:13:53.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578448
;

-- 2019-04-05T11:13:53.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578448
;

-- 2019-04-05T11:13:53.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578450
;

-- 2019-04-05T11:13:53.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578450
;

-- 2019-04-05T11:13:53.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578451
;

-- 2019-04-05T11:13:53.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578451
;

-- 2019-04-05T11:13:53.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578453
;

-- 2019-04-05T11:13:53.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578453
;

-- 2019-04-05T11:13:53.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578455
;

-- 2019-04-05T11:13:53.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578455
;

-- 2019-04-05T11:13:54.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578457
;

-- 2019-04-05T11:13:54.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578457
;

-- 2019-04-05T11:13:54.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578459
;

-- 2019-04-05T11:13:54.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578459
;

-- 2019-04-05T11:13:54.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578460
;

-- 2019-04-05T11:13:54.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578460
;

-- 2019-04-05T11:13:54.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578461
;

-- 2019-04-05T11:13:54.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578461
;

-- 2019-04-05T11:13:54.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578462
;

-- 2019-04-05T11:13:54.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578462
;

-- 2019-04-05T11:13:54.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578463
;

-- 2019-04-05T11:13:54.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578463
;

-- 2019-04-05T11:13:54.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578465
;

-- 2019-04-05T11:13:54.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578465
;

-- 2019-04-05T11:13:54.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578466
;

-- 2019-04-05T11:13:54.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578466
;

-- 2019-04-05T11:13:54.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578467
;

-- 2019-04-05T11:13:54.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578467
;

-- 2019-04-05T11:13:54.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578468
;

-- 2019-04-05T11:13:54.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578468
;

-- 2019-04-05T11:13:54.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578469
;

-- 2019-04-05T11:13:54.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578469
;

-- 2019-04-05T11:13:54.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578470
;

-- 2019-04-05T11:13:54.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578470
;

-- 2019-04-05T11:13:54.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578471
;

-- 2019-04-05T11:13:54.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578471
;

-- 2019-04-05T11:13:54.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578472
;

-- 2019-04-05T11:13:54.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578472
;

-- 2019-04-05T11:13:54.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578473
;

-- 2019-04-05T11:13:54.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578473
;

-- 2019-04-05T11:13:54.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578474
;

-- 2019-04-05T11:13:54.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578474
;

-- 2019-04-05T11:13:54.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578475
;

-- 2019-04-05T11:13:54.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578475
;

-- 2019-04-05T11:13:54.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578476
;

-- 2019-04-05T11:13:54.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578476
;

-- 2019-04-05T11:13:54.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578477
;

-- 2019-04-05T11:13:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578477
;

-- 2019-04-05T11:13:55.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578478
;

-- 2019-04-05T11:13:55.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578478
;

-- 2019-04-05T11:13:55.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578480
;

-- 2019-04-05T11:13:55.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578480
;

-- 2019-04-05T11:13:55.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578483
;

-- 2019-04-05T11:13:55.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578483
;

-- 2019-04-05T11:13:55.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578484
;

-- 2019-04-05T11:13:55.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578484
;

-- 2019-04-05T11:13:55.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578486
;

-- 2019-04-05T11:13:55.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578486
;

-- 2019-04-05T11:13:55.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578487
;

-- 2019-04-05T11:13:55.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578487
;

-- 2019-04-05T11:13:55.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578488
;

-- 2019-04-05T11:13:55.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578488
;

-- 2019-04-05T11:13:55.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578489
;

-- 2019-04-05T11:13:55.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578489
;

-- 2019-04-05T11:13:55.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578490
;

-- 2019-04-05T11:13:55.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578490
;

-- 2019-04-05T11:13:55.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578491
;

-- 2019-04-05T11:13:55.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578491
;

-- 2019-04-05T11:13:55.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578492
;

-- 2019-04-05T11:13:55.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578492
;

-- 2019-04-05T11:13:55.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578493
;

-- 2019-04-05T11:13:55.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578493
;

-- 2019-04-05T11:13:55.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578494
;

-- 2019-04-05T11:13:55.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578494
;

-- 2019-04-05T11:13:55.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578495
;

-- 2019-04-05T11:13:55.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578495
;

-- 2019-04-05T11:13:55.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578496
;

-- 2019-04-05T11:13:55.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578496
;

-- 2019-04-05T11:13:55.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578497
;

-- 2019-04-05T11:13:55.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578497
;

-- 2019-04-05T11:13:55.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578498
;

-- 2019-04-05T11:13:55.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578498
;

-- 2019-04-05T11:13:55.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578499
;

-- 2019-04-05T11:13:55.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578499
;

-- 2019-04-05T11:13:55.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578500
;

-- 2019-04-05T11:13:55.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578500
;

-- 2019-04-05T11:13:55.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578501
;

-- 2019-04-05T11:13:55.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578501
;

-- 2019-04-05T11:13:56.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578502
;

-- 2019-04-05T11:13:56.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578502
;

-- 2019-04-05T11:13:56.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578503
;

-- 2019-04-05T11:13:56.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578503
;

-- 2019-04-05T11:13:56.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578504
;

-- 2019-04-05T11:13:56.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578504
;

-- 2019-04-05T11:13:56.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578505
;

-- 2019-04-05T11:13:56.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578505
;

-- 2019-04-05T11:13:56.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578506
;

-- 2019-04-05T11:13:56.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578506
;

-- 2019-04-05T11:13:56.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578507
;

-- 2019-04-05T11:13:56.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578507
;

-- 2019-04-05T11:13:56.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578508
;

-- 2019-04-05T11:13:56.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578508
;

-- 2019-04-05T11:13:56.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578509
;

-- 2019-04-05T11:13:56.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578509
;

-- 2019-04-05T11:13:56.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578510
;

-- 2019-04-05T11:13:56.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578510
;

-- 2019-04-05T11:13:56.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578511
;

-- 2019-04-05T11:13:56.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578511
;

-- 2019-04-05T11:13:56.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578512
;

-- 2019-04-05T11:13:56.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578512
;

-- 2019-04-05T11:13:56.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578513
;

-- 2019-04-05T11:13:56.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578513
;

-- 2019-04-05T11:13:56.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578514
;

-- 2019-04-05T11:13:56.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578514
;

-- 2019-04-05T11:13:56.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578515
;

-- 2019-04-05T11:13:56.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578515
;

-- 2019-04-05T11:13:56.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578516
;

-- 2019-04-05T11:13:56.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578516
;

-- 2019-04-05T11:13:56.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578517
;

-- 2019-04-05T11:13:56.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578517
;

-- 2019-04-05T11:13:56.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578518
;

-- 2019-04-05T11:13:56.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578518
;

-- 2019-04-05T11:13:56.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578519
;

-- 2019-04-05T11:13:56.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578519
;

-- 2019-04-05T11:13:56.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578520
;

-- 2019-04-05T11:13:56.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578520
;

-- 2019-04-05T11:13:56.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578521
;

-- 2019-04-05T11:13:56.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578521
;

-- 2019-04-05T11:14:02.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578449
;

-- 2019-04-05T11:14:02.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578449
;

-- 2019-04-05T11:15:18.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541699,541255,TO_TIMESTAMP('2019-04-05 11:15:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2019-04-05 11:15:18','YYYY-MM-DD HH24:MI:SS'),100) RETURNING Value
;

-- 2019-04-05T11:15:18.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541255 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-04-05T11:15:26.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541607,541255,TO_TIMESTAMP('2019-04-05 11:15:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-04-05 11:15:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:15:38.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541607,542436,TO_TIMESTAMP('2019-04-05 11:15:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-04-05 11:15:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:17:42.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578456,0,541699,558229,542436,'F',TO_TIMESTAMP('2019-04-05 11:17:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Geschäftspartner',10,0,0,TO_TIMESTAMP('2019-04-05 11:17:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:18:17.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578482,0,541699,558230,542436,'F',TO_TIMESTAMP('2019-04-05 11:18:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Vorname',20,0,0,TO_TIMESTAMP('2019-04-05 11:18:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:18:25.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578481,0,541699,558231,542436,'F',TO_TIMESTAMP('2019-04-05 11:18:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Nachname',30,0,0,TO_TIMESTAMP('2019-04-05 11:18:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:18:42.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578479,0,541699,558232,542436,'F',TO_TIMESTAMP('2019-04-05 11:18:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Standard-Ansprechpartner',40,0,0,TO_TIMESTAMP('2019-04-05 11:18:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:18:59.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578464,0,541699,558233,542436,'F',TO_TIMESTAMP('2019-04-05 11:18:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Telefon',50,0,0,TO_TIMESTAMP('2019-04-05 11:18:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:19:11.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578485,0,541699,558234,542436,'F',TO_TIMESTAMP('2019-04-05 11:19:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Handynummer',60,0,0,TO_TIMESTAMP('2019-04-05 11:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:19:24.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578454,0,541699,558235,542436,'F',TO_TIMESTAMP('2019-04-05 11:19:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'eMail',70,0,0,TO_TIMESTAMP('2019-04-05 11:19:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:20:08.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541608,541255,TO_TIMESTAMP('2019-04-05 11:20:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-04-05 11:20:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:20:20.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541608,542437,TO_TIMESTAMP('2019-04-05 11:20:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2019-04-05 11:20:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:20:48.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558233
;

-- 2019-04-05T11:20:50.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558234
;

-- 2019-04-05T11:20:53.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558235
;

-- 2019-04-05T11:21:26.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578464,0,541699,558236,542437,'F',TO_TIMESTAMP('2019-04-05 11:21:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Telefon',10,0,0,TO_TIMESTAMP('2019-04-05 11:21:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:21:41.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578485,0,541699,558237,542437,'F',TO_TIMESTAMP('2019-04-05 11:21:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Handynummer',20,0,0,TO_TIMESTAMP('2019-04-05 11:21:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:22:01.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578454,0,541699,558238,542437,'F',TO_TIMESTAMP('2019-04-05 11:22:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'eMail',30,0,0,TO_TIMESTAMP('2019-04-05 11:22:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:22:11.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578458
;

-- 2019-04-05T11:22:11.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578458
;

-- 2019-04-05T11:23:07.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse,WEBUI_NameNew) VALUES ('W',0,576561,541234,0,540617,TO_TIMESTAMP('2019-04-05 11:23:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Company Phone Book','Y','Y','N','N','N','Company Phone Book',TO_TIMESTAMP('2019-04-05 11:23:07','YYYY-MM-DD HH24:MI:SS'),100,'Company Phone Book','Company Phone Book')
;

-- 2019-04-05T11:23:07.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541234 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-04-05T11:23:07.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541234, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541234)
;

-- 2019-04-05T11:23:07.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(576561) 
;

-- 2019-04-05T11:23:16.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541234 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:16.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541234 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:23.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541234 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:27.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541234 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:35.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:41.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000023 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:41.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541045 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:41.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541234 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:41.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:41.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:41.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:41.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:41.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:41.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541178 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:41.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:41.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541217 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:41.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541218 AND AD_Tree_ID=10
;

-- 2019-04-05T11:23:41.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541219 AND AD_Tree_ID=10
;

-- 2019-04-05T11:24:41.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DefaultWhereClause='exists (select 1 from C_BPartner bp where bp.C_BPartner_ID = AD_User.C_BPartner_ID and bp.IsCompany=''Y'')',Updated=TO_TIMESTAMP('2019-04-05 11:24:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541699
;

-- 2019-04-05T11:25:27.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET IsCreateNew='N',Updated=TO_TIMESTAMP('2019-04-05 11:25:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541234
;

-- 2019-04-05T11:29:05.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558236
;

-- 2019-04-05T11:29:05.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558237
;

-- 2019-04-05T11:29:05.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558238
;

-- 2019-04-05T11:29:10.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542437
;

-- 2019-04-05T11:29:13.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541608
;

-- 2019-04-05T11:29:22.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541607,542438,TO_TIMESTAMP('2019-04-05 11:29:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','second',20,TO_TIMESTAMP('2019-04-05 11:29:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:29:42.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578464,0,541699,558239,542438,'F',TO_TIMESTAMP('2019-04-05 11:29:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Telefon',10,0,0,TO_TIMESTAMP('2019-04-05 11:29:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:29:53.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578485,0,541699,558240,542438,'F',TO_TIMESTAMP('2019-04-05 11:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Handynummer',20,0,0,TO_TIMESTAMP('2019-04-05 11:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:30:02.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578454,0,541699,558241,542438,'F',TO_TIMESTAMP('2019-04-05 11:30:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'eMail',30,0,0,TO_TIMESTAMP('2019-04-05 11:30:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:30:27.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2019-04-05 11:30:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542438
;

-- 2019-04-05T11:30:50.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='default',Updated=TO_TIMESTAMP('2019-04-05 11:30:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542438
;

-- 2019-04-05T11:31:35.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='first',Updated=TO_TIMESTAMP('2019-04-05 11:31:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542436
;

-- 2019-04-05T11:31:40.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='second',Updated=TO_TIMESTAMP('2019-04-05 11:31:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542438
;

-- 2019-04-05T11:33:49.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541609,541255,TO_TIMESTAMP('2019-04-05 11:33:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-04-05 11:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:33:59.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541609,542439,TO_TIMESTAMP('2019-04-05 11:33:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2019-04-05 11:33:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:34:41.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578479,0,541699,558242,542439,'F',TO_TIMESTAMP('2019-04-05 11:34:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Standard-Ansprechpartner',10,0,0,TO_TIMESTAMP('2019-04-05 11:34:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:35:01.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558240
;

-- 2019-04-05T11:35:01.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558241
;

-- 2019-04-05T11:35:10.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,422,578522,0,541699,0,TO_TIMESTAMP('2019-04-05 11:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',0,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',10,10,0,1,1,TO_TIMESTAMP('2019-04-05 11:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:35:10.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578522 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T11:35:40.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541609,542440,TO_TIMESTAMP('2019-04-05 11:35:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','second',20,'primary',TO_TIMESTAMP('2019-04-05 11:35:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:35:55.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578452,0,541699,558243,542440,'F',TO_TIMESTAMP('2019-04-05 11:35:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2019-04-05 11:35:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:36:08.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578522,0,541699,558244,542440,'F',TO_TIMESTAMP('2019-04-05 11:36:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2019-04-05 11:36:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:36:32.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558232
;

-- 2019-04-05T11:36:43.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578464,0,541699,558245,542436,'F',TO_TIMESTAMP('2019-04-05 11:36:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Telefon',40,0,0,TO_TIMESTAMP('2019-04-05 11:36:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:36:53.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578485,0,541699,558246,542436,'F',TO_TIMESTAMP('2019-04-05 11:36:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Handynummer',50,0,0,TO_TIMESTAMP('2019-04-05 11:36:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:37:05.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578454,0,541699,558247,542436,'F',TO_TIMESTAMP('2019-04-05 11:37:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'eMail',60,0,0,TO_TIMESTAMP('2019-04-05 11:37:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T11:37:15.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558239
;

-- 2019-04-05T11:37:19.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542438
;

-- 2019-04-05T11:37:55.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='',Updated=TO_TIMESTAMP('2019-04-05 11:37:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542439
;

-- 2019-04-05T11:37:57.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='',Updated=TO_TIMESTAMP('2019-04-05 11:37:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542440
;

-- 2019-04-05T12:25:55.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576562,0,'IsActivePartner',TO_TIMESTAMP('2019-04-05 12:25:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Aktiv','Aktiv',TO_TIMESTAMP('2019-04-05 12:25:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T12:25:55.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576562 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-05T12:26:08.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,ColumnSQL,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,EntityType,ColumnName,IsAutoApplyValidationRule,Name) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-05 12:26:08','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-05 12:26:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',114,'N',567635,'N','N','N','N','N','N','(select IsActive from C_BPartner bp where bp.C_BPartner_ID = AD_User.C_BPartner_ID)','N','N',0,0,576562,'N','N','D','IsActivePartner','N','Aktiv')
;

-- 2019-04-05T12:26:08.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567635 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-05T12:26:33.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,567635,578523,0,541699,0,TO_TIMESTAMP('2019-04-05 12:26:33','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Aktiv',20,20,0,1,1,TO_TIMESTAMP('2019-04-05 12:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T12:26:33.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578523 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T12:27:19.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578523,0,541699,558248,542439,'F',TO_TIMESTAMP('2019-04-05 12:27:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Aktiv',8,0,0,TO_TIMESTAMP('2019-04-05 12:27:18','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2019-04-05T16:36:51.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET WEBUI_NameBrowse='Firmenverzeichnis',Updated=TO_TIMESTAMP('2019-04-05 16:36:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541234
;
