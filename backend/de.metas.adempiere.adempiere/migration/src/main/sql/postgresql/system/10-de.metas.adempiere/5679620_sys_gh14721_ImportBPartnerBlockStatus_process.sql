-- Value: Process
-- Classname: de.metas.bpartner.impexp.blockStatus.ImportBPartnerBlockStatus
-- 2023-02-28T09:12:08.667Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585228,'Y','de.metas.bpartner.impexp.blockstatus.ImportBPartnerBlockStatus','N',TO_TIMESTAMP('2023-02-28 11:12:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Import BPartner Block Status','json','N','N','xls','Java',TO_TIMESTAMP('2023-02-28 11:12:08','YYYY-MM-DD HH24:MI:SS'),100,'ImportBPartnerBlockStatus')
;

-- 2023-02-28T09:12:08.670Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585228 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: ImportBPartnerBlockStatus(de.metas.bpartner.impexp.blockStatus.ImportBPartnerBlockStatus)
-- Table: I_BPartner_BlockStatus
-- EntityType: D
-- 2023-02-28T09:55:44.547Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585228,542318,541365,TO_TIMESTAMP('2023-02-28 11:55:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-02-28 11:55:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2023-02-28T09:56:48.568Z
UPDATE AD_Table_Trl SET Name='Import BPartner Block Status',Updated=TO_TIMESTAMP('2023-02-28 11:56:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542318
;

