-- 2020-06-02T08:56:17.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,MsgText,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Message_ID,Value,AD_Org_ID,EntityType) VALUES ('I','Keine Zahlungen verfügbar',0,'Y',TO_TIMESTAMP('2020-06-02 11:56:16','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-06-02 11:56:16','YYYY-MM-DD HH24:MI:SS'),100,544984,'de.metas.ui.web.payment_allocation.PaymentRow.NoPaymentsAvailable',0,'D')
;

-- 2020-06-02T08:56:17.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544984 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-06-02T08:56:19.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-02 11:56:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544984
;

-- 2020-06-02T08:56:29.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='No payments available', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-02 11:56:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544984
;

-- 2020-06-02T09:05:57.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-02 12:05:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584699 AND AD_Language='de_CH'
;

-- 2020-06-02T09:05:59.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-02 12:05:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584699 AND AD_Language='de_DE'
;

-- 2020-06-02T09:06:05.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Allocate Payments',Updated=TO_TIMESTAMP('2020-06-02 12:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584699 AND AD_Language='en_US'
;

-- 2020-06-02T09:15:02.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,AD_Org_ID,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,CopyFromProcess,UpdatedBy,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,Type,IsTranslateExcelHeaders,Name,IsNotifyUserAfterExecution,IsServerProcess,Classname,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2020-06-02 12:15:02','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-06-02 12:15:02','YYYY-MM-DD HH24:MI:SS'),'N',0,'N','7','N','N','N',100,584708,'PaymentView_Launcher_From_C_Invoice_View','Y','Y','N','N','N',0,'Java','Y','Zahlung-Zuordnung','N','N','de.metas.ui.web.payment_allocation.process.PaymentView_Launcher_From_C_Invoice_View','D')
;

-- 2020-06-02T09:15:02.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584708 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-06-02T09:15:05.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-02 12:15:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584708 AND AD_Language='de_CH'
;

-- 2020-06-02T09:15:13.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Allocate Payments',Updated=TO_TIMESTAMP('2020-06-02 12:15:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584708 AND AD_Language='en_US'
;

-- 2020-06-02T09:21:39.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (Created,CreatedBy,IsActive,AD_Table_ID,Updated,UpdatedBy,AD_Client_ID,WEBUI_ViewQuickAction,AD_Process_ID,WEBUI_DocumentAction,WEBUI_ViewAction,WEBUI_IncludedTabTopAction,WEBUI_ViewQuickAction_Default,AD_Table_Process_ID,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2020-06-02 12:21:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',318,TO_TIMESTAMP('2020-06-02 12:21:39','YYYY-MM-DD HH24:MI:SS'),100,0,'Y',584708,'N','Y','N','N',540833,0,'D')
;

