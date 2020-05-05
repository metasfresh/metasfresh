-- Jan 12, 2017 6:49 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540750,'N','N',TO_TIMESTAMP('2017-01-12 18:49:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','N','N','N','N','N','Y',0,'Create material receipt','N','Y','Java',TO_TIMESTAMP('2017-01-12 18:49:35','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_M_HU_CreateMaterialReceipt')
;

-- Jan 12, 2017 6:49 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540750 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Jan 12, 2017 6:50 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540750,540516,TO_TIMESTAMP('2017-01-12 18:50:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y',TO_TIMESTAMP('2017-01-12 18:50:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Jan 12, 2017 7:09 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateMaterialReceipt',Updated=TO_TIMESTAMP('2017-01-12 19:09:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540750
;

