
-- Window: Vertrag, InternalName=C_Flatrate_Term
-- 2024-05-22T15:32:27.548Z
UPDATE AD_Window SET InternalName='C_Flatrate_Term',Updated=TO_TIMESTAMP('2024-05-22 17:32:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540359
;

-- Tab: Vertrag -> Vertrag
-- Table: C_Flatrate_Term
-- 2024-05-22T15:32:47.733Z
UPDATE AD_Tab SET InternalName='C_Flatrate_Term',Updated=TO_TIMESTAMP('2024-05-22 17:32:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540859
;



-- fix the window_ID of the table C_Flatrate_DataEntry
update ad_table set ad_Window_id=540359, updatedby=100, updated='2024-05-14 16:03' where ad_table_id=540309;


-- 2024-05-23T12:57:26.498Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Tab_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585389,540860,540320,541489,540359,TO_TIMESTAMP('2024-05-23 14:57:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2024-05-23 14:57:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','N','N','N')
;


---

-- 2024-05-27T15:05:40.460Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,585394,'N','de.metas.ui.web.contract.flatrate.process.WEBUI_C_Flatrate_DataEntry_ReactivateIt','N',TO_TIMESTAMP('2024-05-27 17:05:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Reaktivieren','json','N','N','xls','Available as related process within the WEBUI_C_Flatrate_DataEntry_Detail_Launcher''s modal','Java',TO_TIMESTAMP('2024-05-27 17:05:40','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_C_Flatrate_DataEntry_ReactivateIt')
;

-- 2024-05-27T15:05:40.467Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585394 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2024-05-27T15:06:50.700Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,585395,'N','de.metas.ui.web.contract.flatrate.process.WEBUI_C_Flatrate_DataEntry_CompleteIt','N',TO_TIMESTAMP('2024-05-27 17:06:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','N','N','N','N','N','N','Y','N','N',0,'Fertigstellen','json','N','N','xls','Available as related process within the WEBUI_C_Flatrate_DataEntry_Detail_Launcher''s modal','Java',TO_TIMESTAMP('2024-05-27 17:06:50','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_C_Flatrate_DataEntry_CompleteIt')
;

-- 2024-05-27T15:06:50.702Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585395 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2024-05-27T15:06:56.457Z
UPDATE AD_Process SET IsUseBPartnerLanguage='N',Updated=TO_TIMESTAMP('2024-05-27 17:06:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585394
;

-- 2024-05-27T15:10:16.532Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-27 17:10:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585394
;

-- 2024-05-27T15:10:19.186Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-27 17:10:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585394
;

-- 2024-05-27T15:10:40.002Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Re-activate',Updated=TO_TIMESTAMP('2024-05-27 17:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585394
;

-- 2024-05-27T15:11:21.141Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Complete',Updated=TO_TIMESTAMP('2024-05-27 17:11:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585395
;

-- 2024-05-27T15:11:21.782Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-27 17:11:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585395
;

-- 2024-05-27T15:11:23.502Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585395
;

