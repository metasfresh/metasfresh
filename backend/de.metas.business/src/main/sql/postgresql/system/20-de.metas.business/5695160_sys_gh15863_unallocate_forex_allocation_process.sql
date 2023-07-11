-- Value: C_Order_UnallocateFromForexContract
-- Classname: de.metas.forex.webui.process.C_Order_UnallocateFromForexContract
-- 2023-07-10T14:10:45.518Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585282,'Y','de.metas.forex.webui.process.C_Order_UnallocateFromForexContract','N',TO_TIMESTAMP('2023-07-10 17:10:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Allocated amount from selected lines will be unallocated from FEC','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Unallocate from FEC','json','N','Y','xls','Java',TO_TIMESTAMP('2023-07-10 17:10:45','YYYY-MM-DD HH24:MI:SS'),100,'C_Order_UnallocateFromForexContract')
;

-- 2023-07-10T14:10:45.518Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585282 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: C_Order_UnallocateFromForexContract
-- Classname: de.metas.forex.webui.process.C_Order_UnallocateFromForexContract
-- 2023-07-11T06:57:05.591Z
UPDATE AD_Process SET Description='Allocated amount from selected lines will be unallocated from FEC',Updated=TO_TIMESTAMP('2023-07-11 09:57:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585282
;

-- 2023-07-11T06:57:05.600Z
UPDATE AD_Process_Trl trl SET Description='Allocated amount from selected lines will be unallocated from FEC' WHERE AD_Process_ID=585282 AND AD_Language='en_US'
;



-- Process: C_Order_UnallocateFromForexContract(de.metas.forex.webui.process.C_Order_UnallocateFromForexContract)
-- Table: C_ForeignExchangeContract
-- Window: Foreign Exchange Contract(541664,D)
-- EntityType: D
-- 2023-07-11T08:30:36.170Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585282,542281,541395,541664,TO_TIMESTAMP('2023-07-11 11:30:36','YYYY-MM-DD HH24:MI:SS'),100,'D','N',TO_TIMESTAMP('2023-07-11 11:30:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

