-- 2021-03-04T09:17:58.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584806,'Y','de.metas.ui.web.payment_allocation.process.InvoicesView_MarkPreparedForAllocation','N',TO_TIMESTAMP('2021-03-04 11:17:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment','Y','N','N','N','N','N','N','Y','Y',0,'Auswahl für Zuordnung','json','N','N','Java',TO_TIMESTAMP('2021-03-04 11:17:58','YYYY-MM-DD HH24:MI:SS'),100,'InvoicesView_MarkPreparedForAllocation')
;

-- 2021-03-04T09:17:58.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584806 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-03-04T09:18:09.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-04 11:18:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584806
;

-- 2021-03-04T09:18:11.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-04 11:18:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584806
;

-- 2021-03-04T09:18:23.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Select for Allocation',Updated=TO_TIMESTAMP('2021-03-04 11:18:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584806
;

-- 2021-03-04T09:19:09.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584807,'Y','de.metas.ui.web.payment_allocation.process.InvoicesView_UnMarkPreparedForAllocation','N',TO_TIMESTAMP('2021-03-04 11:19:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment','Y','N','N','N','N','N','N','Y','Y',0,'Unselect for Allocation','json','N','N','Java',TO_TIMESTAMP('2021-03-04 11:19:08','YYYY-MM-DD HH24:MI:SS'),100,'InvoicesView_UnMarkPreparedForAllocation')
;

-- 2021-03-04T09:19:09.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584807 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-03-04T09:54:40.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541362,'S',TO_TIMESTAMP('2021-03-04 11:54:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment','Y','de.metas.ui.web.payment_allocation.EnablePreparedForAllocationFlag',TO_TIMESTAMP('2021-03-04 11:54:40','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2021-03-04T09:54:49.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_SysConfig SET Value='Y',Updated=TO_TIMESTAMP('2021-03-04 11:54:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541362
-- ;

