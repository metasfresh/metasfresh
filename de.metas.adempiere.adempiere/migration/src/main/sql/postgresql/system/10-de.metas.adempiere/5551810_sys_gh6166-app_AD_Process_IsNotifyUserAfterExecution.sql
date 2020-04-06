-- 2020-02-11T22:26:06.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577532,0,'IsNotifyUserAfterExecution',TO_TIMESTAMP('2020-02-11 23:26:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Nach Ausführung Nutzer benachrichtigen','Nach Ausführung Nutzer benachrichtigen',TO_TIMESTAMP('2020-02-11 23:26:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T22:26:06.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577532 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-11T22:26:10.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-11 23:26:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577532 AND AD_Language='de_CH'
;

-- 2020-02-11T22:26:10.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577532,'de_CH') 
;

-- 2020-02-11T22:26:14.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-11 23:26:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577532 AND AD_Language='de_DE'
;

-- 2020-02-11T22:26:14.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577532,'de_DE') 
;

-- 2020-02-11T22:26:14.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577532,'de_DE') 
;

-- 2020-02-11T22:26:50Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='The notification contains a link to the execution''s process instance record', IsTranslated='Y', Name='Notify user after execution', PrintName='Notify user after execution',Updated=TO_TIMESTAMP('2020-02-11 23:26:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577532 AND AD_Language='en_US'
;

-- 2020-02-11T22:26:50.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577532,'en_US') 
;

-- 2020-02-11T22:27:26.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz.',Updated=TO_TIMESTAMP('2020-02-11 23:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577532 AND AD_Language='de_DE'
;

-- 2020-02-11T22:27:26.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577532,'de_DE') 
;

-- 2020-02-11T22:27:26.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577532,'de_DE') 
;

-- 2020-02-11T22:27:26.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsNotifyUserAfterExecution', Name='Nach Ausführung Nutzer benachrichtigen', Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz.', Help=NULL WHERE AD_Element_ID=577532
;

-- 2020-02-11T22:27:26.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsNotifyUserAfterExecution', Name='Nach Ausführung Nutzer benachrichtigen', Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz.', Help=NULL, AD_Element_ID=577532 WHERE UPPER(ColumnName)='ISNOTIFYUSERAFTEREXECUTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-11T22:27:26.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsNotifyUserAfterExecution', Name='Nach Ausführung Nutzer benachrichtigen', Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz.', Help=NULL WHERE AD_Element_ID=577532 AND IsCentrallyMaintained='Y'
;

-- 2020-02-11T22:27:26.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nach Ausführung Nutzer benachrichtigen', Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577532) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577532)
;

-- 2020-02-11T22:27:26.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Nach Ausführung Nutzer benachrichtigen', Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577532
;

-- 2020-02-11T22:27:26.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Nach Ausführung Nutzer benachrichtigen', Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz.', Help=NULL WHERE AD_Element_ID = 577532
;

-- 2020-02-11T22:27:26.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Nach Ausführung Nutzer benachrichtigen', Description = 'Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577532
;

-- 2020-02-11T22:27:31.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz.',Updated=TO_TIMESTAMP('2020-02-11 23:27:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577532 AND AD_Language='de_CH'
;

-- 2020-02-11T22:27:31.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577532,'de_CH') 
;

-- 2020-02-11T22:27:45.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570000,577532,0,20,284,'IsNotifyUserAfterExecution',TO_TIMESTAMP('2020-02-11 23:27:45','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz.','D',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Nach Ausführung Nutzer benachrichtigen',0,0,TO_TIMESTAMP('2020-02-11 23:27:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-02-11T22:27:45.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570000 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-02-11T22:27:45.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577532) 
;

-- 2020-02-11T22:28:05.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570000,597954,0,245,TO_TIMESTAMP('2020-02-11 23:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz.',1,'D','Y','N','N','N','N','N','N','N','Nach Ausführung Nutzer benachrichtigen',TO_TIMESTAMP('2020-02-11 23:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T22:28:05.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=597954 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-02-11T22:28:05.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577532) 
;

-- 2020-02-11T22:28:05.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=597954
;

-- 2020-02-11T22:28:05.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(597954)
;

-- 2020-02-11T22:28:16.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-02-11 23:28:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=597954
;

-- 2020-02-11T22:29:06.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,597954,0,245,541395,566344,TO_TIMESTAMP('2020-02-11 23:29:06','YYYY-MM-DD HH24:MI:SS'),100,'Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz.','Y','N','Y','N','N','Nach Ausführung Nutzer benachrichtigen',90,0,0,TO_TIMESTAMP('2020-02-11 23:29:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T22:57:25.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsNotifyUserAfterExecution='Y',Updated=TO_TIMESTAMP('2020-02-11 23:57:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584652
;

-- 2020-02-11T22:57:48.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsNotifyUserAfterExecution='Y',Updated=TO_TIMESTAMP('2020-02-11 23:57:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540460
;

-- 2020-02-11T23:05:29.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type/X@=''Java''',Updated=TO_TIMESTAMP('2020-02-12 00:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=597954
;

-- 2020-02-11T23:06:50.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz. Zur Zeit nur für Java-Prozesse implementiert. Wird ignoriert wenn der Prozess durch die Ablaufsteuerung gestartet wird.',Updated=TO_TIMESTAMP('2020-02-12 00:06:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577532 AND AD_Language='de_CH'
;

-- 2020-02-11T23:06:50.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577532,'de_CH') 
;

-- 2020-02-11T23:06:58.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz. Zur Zeit nur für Java-Prozesse implementiert. Wird ignoriert wenn der Prozess durch die Ablaufsteuerung gestartet wird.',Updated=TO_TIMESTAMP('2020-02-12 00:06:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577532 AND AD_Language='de_DE'
;

-- 2020-02-11T23:06:58.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577532,'de_DE') 
;

-- 2020-02-11T23:06:58.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577532,'de_DE') 
;

-- 2020-02-11T23:06:58.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsNotifyUserAfterExecution', Name='Nach Ausführung Nutzer benachrichtigen', Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz. Zur Zeit nur für Java-Prozesse implementiert. Wird ignoriert wenn der Prozess durch die Ablaufsteuerung gestartet wird.', Help=NULL WHERE AD_Element_ID=577532
;

-- 2020-02-11T23:06:58.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsNotifyUserAfterExecution', Name='Nach Ausführung Nutzer benachrichtigen', Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz. Zur Zeit nur für Java-Prozesse implementiert. Wird ignoriert wenn der Prozess durch die Ablaufsteuerung gestartet wird.', Help=NULL, AD_Element_ID=577532 WHERE UPPER(ColumnName)='ISNOTIFYUSERAFTEREXECUTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-11T23:06:58.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsNotifyUserAfterExecution', Name='Nach Ausführung Nutzer benachrichtigen', Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz. Zur Zeit nur für Java-Prozesse implementiert. Wird ignoriert wenn der Prozess durch die Ablaufsteuerung gestartet wird.', Help=NULL WHERE AD_Element_ID=577532 AND IsCentrallyMaintained='Y'
;

-- 2020-02-11T23:06:58.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nach Ausführung Nutzer benachrichtigen', Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz. Zur Zeit nur für Java-Prozesse implementiert. Wird ignoriert wenn der Prozess durch die Ablaufsteuerung gestartet wird.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577532) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577532)
;

-- 2020-02-11T23:06:58.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Nach Ausführung Nutzer benachrichtigen', Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz. Zur Zeit nur für Java-Prozesse implementiert. Wird ignoriert wenn der Prozess durch die Ablaufsteuerung gestartet wird.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577532
;

-- 2020-02-11T23:06:58.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Nach Ausführung Nutzer benachrichtigen', Description='Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz. Zur Zeit nur für Java-Prozesse implementiert. Wird ignoriert wenn der Prozess durch die Ablaufsteuerung gestartet wird.', Help=NULL WHERE AD_Element_ID = 577532
;

-- 2020-02-11T23:06:58.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Nach Ausführung Nutzer benachrichtigen', Description = 'Die Benachrichtigung enthält einen Link zum betreffenden Prozess-Revisonsdatensatz. Zur Zeit nur für Java-Prozesse implementiert. Wird ignoriert wenn der Prozess durch die Ablaufsteuerung gestartet wird.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577532
;

-- 2020-02-11T23:07:24.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Only for java processes. Ignored if the process is started via scheduler. The notification contains a link to the execution''s process instance record.',Updated=TO_TIMESTAMP('2020-02-12 00:07:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577532 AND AD_Language='en_US'
;

-- 2020-02-11T23:07:24.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577532,'en_US') 
;

-- 2020-02-11T23:11:51.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.report.jasper.client.process.JasperReportStarter',Updated=TO_TIMESTAMP('2020-02-12 00:11:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584652
;

-- 2020-02-11T23:14:36.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.contracts.commission.commissioninstance.process.C_Invoice_Candidate_CreateOrUpdateCommissionInstance',Updated=TO_TIMESTAMP('2020-02-12 00:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584652
;

