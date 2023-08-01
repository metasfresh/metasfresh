-- Value: AcctSimulationView_AddRow
-- Classname: de.metas.acct.acct_simulation.AcctSimulationView_AddRow
-- 2023-08-01T08:14:42.514Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585295,'Y','de.metas.acct.acct_simulation.AcctSimulationView_AddRow','N',TO_TIMESTAMP('2023-08-01 11:14:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Add row','json','N','N','xls','Java',TO_TIMESTAMP('2023-08-01 11:14:41','YYYY-MM-DD HH24:MI:SS'),100,'AcctSimulationView_AddRow')
;

-- 2023-08-01T08:14:42.518Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585295 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: AcctSimulationView_RemoveRows
-- Classname: de.metas.acct.acct_simulation.AcctSimulationView_RemoveRows
-- 2023-08-01T08:18:17.436Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585296,'Y','de.metas.acct.acct_simulation.AcctSimulationView_RemoveRows','N',TO_TIMESTAMP('2023-08-01 11:18:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Remove selected rows','json','N','N','xls','Java',TO_TIMESTAMP('2023-08-01 11:18:17','YYYY-MM-DD HH24:MI:SS'),100,'AcctSimulationView_RemoveRows')
;

-- 2023-08-01T08:18:17.438Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585296 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

