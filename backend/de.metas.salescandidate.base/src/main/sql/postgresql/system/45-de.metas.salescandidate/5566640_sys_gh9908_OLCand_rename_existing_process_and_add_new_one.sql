-- 2020-09-07T09:14:03.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='org.adempiere.server.rpl.trx.process.C_OLCand_Update_IsImportedWithIssues_SingleRplTrx', Value='C_OLCand_Update_IsImportedWithIssues_SingleRplTrx',Updated=TO_TIMESTAMP('2020-09-07 11:14:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540429
;

-- 2020-09-07T10:43:10.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584741,'N','org.adempiere.server.rpl.trx.process.C_OLCand_Update_IsImportedWithIssues_Selection','N',TO_TIMESTAMP('2020-09-07 12:43:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','Y','N','N','N','N','N','Y','Y',0,'Auswahl Transaktionen Freigeben','Y','Y','Java',TO_TIMESTAMP('2020-09-07 12:43:10','YYYY-MM-DD HH24:MI:SS'),100,'C_OLCand_Update_IsImportedWithIssues_Selection')
;

-- 2020-09-07T10:43:10.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584741 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-09-07T10:47:27.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsRangeFilter='Y', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2020-09-07 12:47:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550369
;

-- 2020-09-07T10:53:02.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584741,540244,540848,TO_TIMESTAMP('2020-09-07 12:53:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2020-09-07 12:53:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2020-09-07T10:53:19.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2020-09-07 12:53:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540848
;

