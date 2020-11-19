
-- 2020-11-17T09:37:36.100Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584763,'N','de.metas.product.process.M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process','N',TO_TIMESTAMP('2020-11-17 11:37:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','N','Y','Y',0,'Apply discontinued flag to product prices','json','N','N','Java',TO_TIMESTAMP('2020-11-17 11:37:35','YYYY-MM-DD HH24:MI:SS'),100,'M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process')
;

-- 2020-11-17T09:37:36.336Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584763 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-11-17T09:45:40.795Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,617,0,584763,541883,15,'ValidFrom',TO_TIMESTAMP('2020-11-17 11:45:40','YYYY-MM-DD HH24:MI:SS'),100,'@#Date@','Gültig ab inklusiv (erster Tag)','D',0,'"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','Y','N','Y','N','Gültig ab',10,TO_TIMESTAMP('2020-11-17 11:45:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-17T09:45:40.840Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541883 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-11-17T09:49:59.171Z
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584763,208,540870,TO_TIMESTAMP('2020-11-17 11:49:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2020-11-17 11:49:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2020-11-18T11:15:26.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Produktpreise (de)aktivieren', Help='Produktpreise (de)aktivieren', Name='Produktpreise (de)aktivieren',Updated=TO_TIMESTAMP('2020-11-18 13:15:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584763
;

-- 2020-11-18T11:15:39.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='(De)Activate product prices', Help='(De)Activate product prices', Name='(De)Activate product prices',Updated=TO_TIMESTAMP('2020-11-18 13:15:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584763
;

-- 2020-11-19T14:16:43.734Z
-- URL zum Konzept
UPDATE AD_Process SET IsActive='Y',Updated=TO_TIMESTAMP('2020-11-19 16:16:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584763
;

-- 2020-11-19T14:17:14.727Z
-- URL zum Konzept
UPDATE AD_Table_Process SET IsActive='Y',Updated=TO_TIMESTAMP('2020-11-19 16:17:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540870
;

-- 2020-11-19T14:24:08.766Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Description='Produktpreise (de)aktivieren', Help='Produktpreise (de)aktivieren', IsTranslated='Y', Name='Produktpreise (de)aktivieren',Updated=TO_TIMESTAMP('2020-11-19 16:24:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584763
;

-- 2020-11-18T14:11:03.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET IsActive='N',Updated=TO_TIMESTAMP('2020-11-18 16:11:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540870
;

-- 2020-11-18T13:51:17.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2020-11-18 15:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584763
;

