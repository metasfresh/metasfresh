-- 2018-10-11T09:42:03.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.pickingV2.productsToPick.process.ProductsToPick_4EyesReview_ApproveSelected', Value='ProductsToPick_4EyesReview_ApproveSelected',Updated=TO_TIMESTAMP('2018-10-11 09:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541018
;

-- 2018-10-11T09:42:41.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.pickingV2.productsToPick.process.ProductsToPick_4EyesReview_RejectSelected', Value='ProductsToPick_4EyesReview_RejectSelected',Updated=TO_TIMESTAMP('2018-10-11 09:42:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541019
;

-- 2018-10-11T09:46:33.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541021,'N','de.metas.ui.web.pickingV2.productsToPick.process.ProductsToPick_4EyesReview_ProcessAll','N',TO_TIMESTAMP('2018-10-11 09:46:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','N','N','N','N','N','N','Y',0,'Process','N','N','Java',TO_TIMESTAMP('2018-10-11 09:46:33','YYYY-MM-DD HH24:MI:SS'),100,'ProductsToPick_4EyesReview_ProcessAll')
;

-- 2018-10-11T09:46:33.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=541021 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

