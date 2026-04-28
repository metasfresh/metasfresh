-- 2018-03-23T18:39:29.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,EntityType,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','de.metas.vertical.pharma.msv3.server.process.MSV3_Customer_Config_PublishAll','7','de.metas.vertical.pharma.msv3.server','Y','N','N','N',540939,'N','Y','N','Java','N','N',0,0,'Publish all to MSV3 server','de.metas.vertical.pharma.msv3.server.process.MSV3_Customer_Config_PublishAll',100,TO_TIMESTAMP('2018-03-23 18:39:29','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-23 18:39:29','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-23T18:39:29.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540939 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-03-23T18:39:52.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (EntityType,CreatedBy,IsActive,AD_Table_ID,AD_Client_ID,WEBUI_QuickAction,WEBUI_QuickAction_Default,AD_Process_ID,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('de.metas.vertical.pharma',100,'Y',540955,0,'N','N',540939,0,100,TO_TIMESTAMP('2018-03-23 18:39:52','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-23 18:39:52','YYYY-MM-DD HH24:MI:SS'))
;

