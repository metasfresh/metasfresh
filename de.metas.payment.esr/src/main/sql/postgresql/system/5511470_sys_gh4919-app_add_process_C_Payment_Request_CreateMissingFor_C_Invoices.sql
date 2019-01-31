-- 2019-01-31T11:58:00.286
-- #298 changing anz. stellen
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541041,'Y','de.metas.payment.esr.process.C_Payment_Request_CreateMissingFor_C_Invoices','N',TO_TIMESTAMP('2019-01-31 11:57:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N','N','N','N','N','N','Y',0,'ESR-Zahldaten aktualisieren','N','Y','Java',TO_TIMESTAMP('2019-01-31 11:57:59','YYYY-MM-DD HH24:MI:SS'),100,'C_Payment_Request_CreateMissingFor_C_Invoices')
;

-- 2019-01-31T11:58:00.327
-- #298 changing anz. stellen
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541041 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-01-31T11:59:01.126
-- #298 changing anz. stellen
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,541041,318,TO_TIMESTAMP('2019-01-31 11:59:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y',TO_TIMESTAMP('2019-01-31 11:59:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2019-01-31T13:16:38.246
-- #298 changing anz. stellen
UPDATE AD_Process SET Description='Erzeugt ESR Zahldaten und PDF-Druckstücke für die ausgewählten Rechnungen, sofern diese fertig gestellt sind, aber noch keine ESR-Referenznummer haben.', Name='ESR-Zahldaten erstellen',Updated=TO_TIMESTAMP('2019-01-31 13:16:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541041
;

