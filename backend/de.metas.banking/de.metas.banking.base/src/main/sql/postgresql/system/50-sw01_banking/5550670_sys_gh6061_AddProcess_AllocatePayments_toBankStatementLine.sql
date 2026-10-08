-- 2020-01-30T11:40:09.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,584646,'Y','de.metas.banking.process.C_BankStatement_PaymentAllocation','N',TO_TIMESTAMP('2020-01-30 13:40:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Y',0,'Payment Allocation','N','N','Java',TO_TIMESTAMP('2020-01-30 13:40:09','YYYY-MM-DD HH24:MI:SS'),100,'C_BankStatement_PaymentAllocation')
;

-- 2020-01-30T11:40:09.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584646 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-01-30T11:40:27.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584646,392,540785,TO_TIMESTAMP('2020-01-30 13:40:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2020-01-30 13:40:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2020-01-30T11:50:22.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-30 13:50:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574920 AND AD_Language='de_CH'
;

-- 2020-01-30T11:50:22.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574920,'de_CH') 
;

-- 2020-01-30T11:50:38.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Allocate Invoices and Payments', Name='Payment Allocation1', PrintName='Payment Allocation1',Updated=TO_TIMESTAMP('2020-01-30 13:50:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574920 AND AD_Language='en_US'
;

-- 2020-01-30T11:50:38.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574920,'en_US') 
;

-- 2020-01-30T11:50:43.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Payment Allocation', PrintName='Payment Allocation',Updated=TO_TIMESTAMP('2020-01-30 13:50:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574920 AND AD_Language='en_US'
;

-- 2020-01-30T11:50:43.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574920,'en_US') 
;

-- 2020-01-30T11:50:50.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-30 13:50:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574920 AND AD_Language='de_DE'
;

-- 2020-01-30T11:50:50.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574920,'de_DE') 
;

-- 2020-01-30T11:50:50.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574920,'de_DE') 
;

-- 2020-01-30T11:55:52.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Rechnungen und Zahlungen zuordnen', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-30 13:55:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541215
;

-- 2020-01-30T11:55:57.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Rechnungen und Zahlungen zuordnen', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-30 13:55:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541215
;

-- 2020-01-30T11:56:34.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Assign Invoices and Payments', IsTranslated='Y', Name='Allocate Payment',Updated=TO_TIMESTAMP('2020-01-30 13:56:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541215
;

-- 2020-01-30T11:56:44.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Assign Invoices and Payments',Updated=TO_TIMESTAMP('2020-01-30 13:56:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574920 AND AD_Language='en_US'
;

-- 2020-01-30T11:56:44.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574920,'en_US') 
;

-- 2020-01-30T11:59:50.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.banking.process.C_BankStatementLine_AllocatePayment', Name='Allocate Payment',Updated=TO_TIMESTAMP('2020-01-30 13:59:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584646
;

-- 2020-01-30T12:00:06.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Rechnungen und Zahlungen zuordnen', IsTranslated='Y', Name='Zahlung-Zuordnung',Updated=TO_TIMESTAMP('2020-01-30 14:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584646
;

-- 2020-01-30T12:00:16.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Rechnungen und Zahlungen zuordnen', IsTranslated='Y', Name='Zahlung-Zuordnung',Updated=TO_TIMESTAMP('2020-01-30 14:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584646
;

-- 2020-01-30T12:00:37.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Allocate Payment to Invoices', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-30 14:00:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584646
;

-- 2020-01-30T12:07:46.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Allocate Payment',Updated=TO_TIMESTAMP('2020-01-30 14:07:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584646
;

