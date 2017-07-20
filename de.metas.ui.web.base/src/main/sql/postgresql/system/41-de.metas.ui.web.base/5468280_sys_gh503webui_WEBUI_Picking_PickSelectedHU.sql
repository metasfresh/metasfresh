-- 2017-07-20T17:57:59.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540811,'N','de.metas.ui.web.picking.process.WEBUI_Picking_PickSelectedHU','N',TO_TIMESTAMP('2017-07-20 17:57:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','N','N','N','N','N','Y',0,'Select HU to pick','N','Y','Java',TO_TIMESTAMP('2017-07-20 17:57:58','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_PickSelectedHU')
;

-- 2017-07-20T17:57:59.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540811 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-07-20T17:58:07.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Pick selected HU',Updated=TO_TIMESTAMP('2017-07-20 17:58:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540811
;

-- 2017-07-20T17:58:16.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 17:58:16','YYYY-MM-DD HH24:MI:SS'),Name='Pick selected HU' WHERE AD_Process_ID=540811 AND AD_Language='de_CH'
;

-- 2017-07-20T17:58:20.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 17:58:20','YYYY-MM-DD HH24:MI:SS'),Name='Pick selected HU' WHERE AD_Process_ID=540811 AND AD_Language='en_US'
;

-- 2017-07-20T17:58:26.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 17:58:26','YYYY-MM-DD HH24:MI:SS'),Name='Pick selected HU' WHERE AD_Process_ID=540811 AND AD_Language='nl_NL'
;

