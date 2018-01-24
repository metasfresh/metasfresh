-- 2018-01-19T17:35:58.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-01-19 17:35:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558605
;

-- 2018-01-19T17:36:12.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540397,Updated=TO_TIMESTAMP('2018-01-19 17:36:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540898
;

-- 2018-01-19T17:36:44.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2018-01-19 17:36:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561507
;

-- 2018-01-19T17:37:02.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=15, SeqNoGrid=15,Updated=TO_TIMESTAMP('2018-01-19 17:37:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561504
;

-- 2018-01-23T15:07:18.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544632,0,TO_TIMESTAMP('2018-01-23 15:07:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','Verfügbar','I',TO_TIMESTAMP('2018-01-23 15:07:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate.AvailabilityResult_AVAILABLE')
;

-- 2018-01-23T15:07:18.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544632 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-01-23T15:07:27.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-23 15:07:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544632 AND AD_Language='de_CH'
;

-- 2018-01-23T15:07:40.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-23 15:07:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Available' WHERE AD_Message_ID=544632 AND AD_Language='en_US'
;

-- 2018-01-23T15:11:06.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544633,0,TO_TIMESTAMP('2018-01-23 15:11:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','Nicht Lieferbar','I',TO_TIMESTAMP('2018-01-23 15:11:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate.AvailabilityResult_NOT_AVAILABLE')
;

-- 2018-01-23T15:11:06.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544633 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-01-23T15:11:11.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-23 15:11:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544633 AND AD_Language='en_US'
;

-- 2018-01-23T15:11:18.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-23 15:11:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544633 AND AD_Language='de_CH'
;

-- 2018-01-23T15:11:24.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-23 15:11:24','YYYY-MM-DD HH24:MI:SS'),MsgText='Not available' WHERE AD_Message_ID=544633 AND AD_Language='en_US'
;

-- 2018-01-23T15:11:32.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Lieferbar',Updated=TO_TIMESTAMP('2018-01-23 15:11:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544632
;

-- 2018-01-23T15:11:44.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-23 15:11:44','YYYY-MM-DD HH24:MI:SS'),MsgText='Lieferbar' WHERE AD_Message_ID=544632 AND AD_Language='de_CH'
;

-- 2018-01-23T17:08:52.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Bestellungen disponieren', Value='WEBUI_SalesOrder_PurchaseView_Launcher',Updated=TO_TIMESTAMP('2018-01-23 17:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540887
;

-- 2018-01-23T17:09:43.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540916,'N','de.metas.ui.web.order.sales.purchasePlanning.process.WEBUI_SalesOrder_Apply_Availability_Row','N',TO_TIMESTAMP('2018-01-23 17:09:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','N','Y','N','N','N','N','Y',0,'Zusagezeile übernehmen','N','N','Java',TO_TIMESTAMP('2018-01-23 17:09:43','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_SalesOrder_Apply_Availability_Row')
;

-- 2018-01-23T17:09:43.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540916 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-01-23T17:09:57.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.purchasecandidate',Updated=TO_TIMESTAMP('2018-01-23 17:09:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540887
;

