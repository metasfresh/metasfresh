-- Value: SUMUP_Config_DeleteCardReader
-- Classname: de.metas.payment.sumup.webui.process.SUMUP_Config_DeleteCardReader
-- 2024-10-03T20:37:21.797Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585425,'Y','de.metas.payment.sumup.webui.process.SUMUP_Config_DeleteCardReader','N',TO_TIMESTAMP('2024-10-03 23:37:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Delete selected card readers','json','N','N','xls','Java',TO_TIMESTAMP('2024-10-03 23:37:21','YYYY-MM-DD HH24:MI:SS'),100,'SUMUP_Config_DeleteCardReader')
;

-- 2024-10-03T20:37:21.800Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585425 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: SUMUP_Config_DeleteCardReader(de.metas.payment.sumup.webui.process.SUMUP_Config_DeleteCardReader)
-- Table: SUMUP_Config
-- EntityType: de.metas.payment.sumup
-- 2024-10-03T20:38:24.415Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585425,542440,541523,TO_TIMESTAMP('2024-10-03 23:38:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y',TO_TIMESTAMP('2024-10-03 23:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Process: SUMUP_Config_DeleteCardReader(de.metas.payment.sumup.webui.process.SUMUP_Config_DeleteCardReader)
-- Table: SUMUP_Config
-- EntityType: de.metas.payment.sumup
-- 2024-10-03T20:38:27.438Z
UPDATE AD_Table_Process SET WEBUI_ViewAction='N',Updated=TO_TIMESTAMP('2024-10-03 23:38:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541523
;

