-- gh30431: make the "Zahlung-Zuordnung" launcher available in the C_Payment single-record (document) view.

-- 2026-07-09T00:00:00.000Z
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,AD_Org_ID,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,CopyFromProcess,UpdatedBy,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,Type,IsTranslateExcelHeaders,Name,IsNotifyUserAfterExecution,IsServerProcess,Classname,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2026-07-09 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-07-09 00:00:00','YYYY-MM-DD HH24:MI:SS'),'N',0,'N','3','N','N','N',100,581294,'PaymentView_Launcher_FromPayment_SingleDocument','Y','Y','N','N','N',0,'Java','Y','Zahlung-Zuordnung','N','N','de.metas.ui.web.payment_allocation.process.PaymentView_Launcher_FromPayment_SingleDocument','D')
;

-- 2026-07-09T00:00:00.000Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=581294 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2026-07-09T00:00:00.000Z
-- C_Payment = AD_Table_ID 335. Single-document only: WEBUI_DocumentAction='Y', WEBUI_ViewAction='N'
-- (the grid view is already covered by AD_Process_ID=541214).
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,581294,335,581295,TO_TIMESTAMP('2026-07-09 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment','Y',TO_TIMESTAMP('2026-07-09 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;
