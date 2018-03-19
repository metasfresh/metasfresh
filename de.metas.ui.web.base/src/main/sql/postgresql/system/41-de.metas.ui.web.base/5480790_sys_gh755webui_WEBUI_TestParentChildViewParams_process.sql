-- 2017-12-16T19:04:09.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Description,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,EntityType,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','WEBUI_TestParentChildViewParams','3','Y','N','N','N',540900,'Y','Y','N','Process used to test parent and child views informations provided by webui frontend.','Java','N','N',0,0,'WEBUI_TestParentChildViewParams','de.metas.ui.web.process.adprocess.WEBUI_TestParentChildViewParams','de.metas.ui.web',100,TO_TIMESTAMP('2017-12-16 19:04:09','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-16 19:04:09','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-16T19:04:09.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540900 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-12-16T19:12:12.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Process used to test parent and child views informations provided by webui frontend.
See task https://github.com/metasfresh/metasfresh-webui-api/issues/755.',Updated=TO_TIMESTAMP('2017-12-16 19:12:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540900
;

