-- 2020-02-11T04:50:48.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,584652,'Y','de.metas.contracts.commission.commissioninstance.process.C_Invoice_Candidate_CreateOrUpdateCommissionInstance','N',TO_TIMESTAMP('2020-02-11 05:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Erstellt Provisionsvorgang- und Buchauszug-Daten zu allen ausgewählten Rechnungskandidaten, zu denen zur Zeit solche Daten fehlen.
Der Prozess kann benutzt werden, wenn rückwirkend Provisionsdaten zu bereits bestehenden Rechnungskandidaten erstellt wurden.','de.metas.contracts.commission','Y','N','N','N','N','N','N','Y','Y',0,'Provisionsdaten aktualisieren','N','Y','Java',TO_TIMESTAMP('2020-02-11 05:50:48','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_Candidate_CreateOrUpdateCommissionInstance')
;

-- 2020-02-11T04:50:48.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584652 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-02-11T04:50:56.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AllowProcessReRun='N',Updated=TO_TIMESTAMP('2020-02-11 05:50:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584652
;

-- 2020-02-11T04:51:02.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-11 05:51:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584652
;

-- 2020-02-11T04:52:09.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Creates commission instance- and deed-data for all selected invoice candidates where such data is missing.', IsTranslated='Y', Name='Update commission data',Updated=TO_TIMESTAMP('2020-02-11 05:52:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584652
;

-- 2020-02-11T04:52:50.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584652,540270,540791,TO_TIMESTAMP('2020-02-11 05:52:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y',TO_TIMESTAMP('2020-02-11 05:52:50','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N')
;

