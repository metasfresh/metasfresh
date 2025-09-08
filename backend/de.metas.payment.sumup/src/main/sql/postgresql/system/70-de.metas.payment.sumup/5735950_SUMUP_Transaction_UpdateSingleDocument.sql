-- Value: SUMUP_Transaction_UpdateSingleDocument
-- Classname: de.metas.payment.sumup.webui.process.SUMUP_Transaction_UpdateSingleDocument
-- 2024-10-05T16:54:17.367Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585428,'Y','de.metas.payment.sumup.webui.process.SUMUP_Transaction_UpdateSingleDocument','N',TO_TIMESTAMP('2024-10-05 19:54:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Update','json','N','N','xls','Java',TO_TIMESTAMP('2024-10-05 19:54:17','YYYY-MM-DD HH24:MI:SS'),100,'SUMUP_Transaction_UpdateSingleDocument')
;

-- 2024-10-05T16:54:17.369Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585428 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: SUMUP_Transaction_UpdateSingleDocument(de.metas.payment.sumup.webui.process.SUMUP_Transaction_UpdateSingleDocument)
-- Table: SUMUP_Transaction
-- EntityType: de.metas.payment.sumup
-- 2024-10-05T16:54:35.507Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585428,542443,541526,TO_TIMESTAMP('2024-10-05 19:54:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y',TO_TIMESTAMP('2024-10-05 19:54:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

