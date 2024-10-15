-- Value: M_Inventory_Mark_As_Counted
-- Classname: de.metas.inventory.process.M_Inventory_Mark_As_Counted
-- 2023-06-26T15:10:17.680Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585274,'Y','de.metas.inventory.process.M_Inventory_Mark_As_Counted','N',TO_TIMESTAMP('2023-06-26 18:10:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Mark lines as counted','json','N','N','xls','Java',TO_TIMESTAMP('2023-06-26 18:10:17','YYYY-MM-DD HH24:MI:SS'),100,'M_Inventory_Mark_As_Counted')
;

-- 2023-06-26T15:10:17.683Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585274 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_Inventory_Mark_As_Counted(de.metas.inventory.process.M_Inventory_Mark_As_Counted)
-- 2023-06-26T15:10:24.467Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Markieren Sie die Zeilen als gezählt',Updated=TO_TIMESTAMP('2023-06-26 18:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585274
;

-- Value: M_Inventory_Mark_As_Counted
-- Classname: de.metas.inventory.process.M_Inventory_Mark_As_Counted
-- 2023-06-26T15:10:27.569Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Markieren Sie die Zeilen als gezählt',Updated=TO_TIMESTAMP('2023-06-26 18:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585274
;

-- Process: M_Inventory_Mark_As_Counted(de.metas.inventory.process.M_Inventory_Mark_As_Counted)
-- 2023-06-26T15:10:27.561Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Markieren Sie die Zeilen als gezählt',Updated=TO_TIMESTAMP('2023-06-26 18:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585274
;

-- Process: M_Inventory_Mark_As_Counted(de.metas.inventory.process.M_Inventory_Mark_As_Counted)
-- Table: M_Inventory
-- EntityType: D
-- 2023-06-26T15:11:16.063Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585274,321,541387,TO_TIMESTAMP('2023-06-26 18:11:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-06-26 18:11:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

