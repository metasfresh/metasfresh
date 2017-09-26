-- 2017-09-22T16:01:10.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.contracts.subscription.process.C_SubscriptionProgress_InsertPause', Value='C_SubscriptionProgress_InsertPause',Updated=TO_TIMESTAMP('2017-09-22 16:01:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540010
;

-- 2017-09-22T16:01:50.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=540007
;

-- 2017-09-22T16:01:50.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=540007
;

-- 2017-09-22T16:02:35.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=NULL, ColumnName='Pause', IsRange='Y', Name='Pause',Updated=TO_TIMESTAMP('2017-09-22 16:02:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540006
;

-- 2017-09-22T16:19:18.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.contracts.subscription', Name='Zeitraum',Updated=TO_TIMESTAMP('2017-09-22 16:19:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540006
;

-- 2017-09-22T16:19:34.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2017-09-22 16:19:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540010
;

-- 2017-09-22T17:18:16.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540010,540320,TO_TIMESTAMP('2017-09-22 17:18:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.subscription','Y',TO_TIMESTAMP('2017-09-22 17:18:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N')
;

-- 2017-09-22T17:18:17.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_QuickAction_Default='N',Updated=TO_TIMESTAMP('2017-09-22 17:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540010 AND AD_Table_ID=540029
;
