-- 2019-12-11T12:08:49.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-11 14:08:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540565 AND AD_Language='de_CH'
;

-- 2019-12-11T12:08:51.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-11 14:08:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540565 AND AD_Language='de_DE'
;

-- 2019-12-11T12:09:03.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Create packages from Picking Slots',Updated=TO_TIMESTAMP('2019-12-11 14:09:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540565 AND AD_Language='en_GB'
;

-- 2019-12-11T12:09:06.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Create packages from Picking Slots',Updated=TO_TIMESTAMP('2019-12-11 14:09:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540565 AND AD_Language='en_US'
;

-- 2019-12-11T12:09:57.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-11 14:09:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540456 AND AD_Language='de_CH'
;

-- 2019-12-11T12:09:59.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-11 14:09:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540456 AND AD_Language='de_DE'
;

-- 2019-12-11T12:10:07.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Create packages from Tour Planning',Updated=TO_TIMESTAMP('2019-12-11 14:10:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540456 AND AD_Language='en_GB'
;

-- 2019-12-11T12:10:10.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Create packages from Tour Planning',Updated=TO_TIMESTAMP('2019-12-11 14:10:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540456 AND AD_Language='en_US'
;

-- 2019-12-12T07:01:14.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,AD_Org_ID,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,UpdatedBy,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,Type,IsTranslateExcelHeaders,Name,Classname,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-12-12 09:01:14','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-12-12 09:01:14','YYYY-MM-DD HH24:MI:SS'),'N',0,'N','7','N','N','N','N',100,541228,'PrintAllTransportationDocuments','Y','Y','N','N','N',0,'Java','Y','Print All Transportation Documents','de.metas.handlingunits.shipping.process.PrintAllTransportationDocuments','D')
;

-- 2019-12-12T07:01:14.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541228 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-12-12T07:02:55.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (Created,CreatedBy,IsActive,AD_Table_ID,Updated,UpdatedBy,AD_Client_ID,WEBUI_ViewQuickAction,AD_Process_ID,AD_Window_ID,WEBUI_ViewAction,WEBUI_IncludedTabTopAction,WEBUI_ViewQuickAction_Default,AD_Table_Process_ID,WEBUI_DocumentAction,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2019-12-12 09:02:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',540030,TO_TIMESTAMP('2019-12-12 09:02:55','YYYY-MM-DD HH24:MI:SS'),100,0,'N',541228,540020,'Y','N','N',540765,'Y',0,'D')
;

-- 2019-12-12T11:28:38.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='PrintAllShipmentDocuments', Name='Print all Shipment Documents', Classname='de.metas.handlingunits.shipping.process.PrintAllShipmentDocuments',Updated=TO_TIMESTAMP('2019-12-12 13:28:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541228
;

-- 2019-12-12T13:03:09.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Alle Lieferpapiere drucken',Updated=TO_TIMESTAMP('2019-12-12 15:03:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541228 AND AD_Language='de_CH'
;

-- 2019-12-12T13:03:12.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Alle Lieferpapiere drucken',Updated=TO_TIMESTAMP('2019-12-12 15:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541228 AND AD_Language='de_DE'
;

-- 2019-12-12T13:03:23.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Print all Shipment Documents',Updated=TO_TIMESTAMP('2019-12-12 15:03:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541228 AND AD_Language='en_US'
;

