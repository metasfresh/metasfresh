-- 2018-02-02T16:42:35.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='M_ProductPlanning_Create_Default_ProductPlanningData',Updated=TO_TIMESTAMP('2018-02-02 16:42:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540918
;

-- 2018-02-02T16:45:23.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.product',Updated=TO_TIMESTAMP('2018-02-02 16:45:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540918
;

-- 2018-02-02T16:46:14.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540919,'N','de.metas.product.process.M_ProductPlanning_CreateDefaultForSchema','N',TO_TIMESTAMP('2018-02-02 16:46:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N','N','N','N','N','N','Y',0,'Create Default Product Planning Data ForSchema','N','Y','Java',TO_TIMESTAMP('2018-02-02 16:46:14','YYYY-MM-DD HH24:MI:SS'),100,'M_ProductPlanning_Create_Default_ProductPlanningData_ForSchema')
;

-- 2018-02-02T16:46:14.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540919 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-02-02T16:56:30.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540919,540928,540398,TO_TIMESTAMP('2018-02-02 16:56:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y',TO_TIMESTAMP('2018-02-02 16:56:30','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2018-02-02T16:56:47.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_QuickAction='Y',Updated=TO_TIMESTAMP('2018-02-02 16:56:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540919 AND AD_Table_ID=540928
;

