-- Value: C_Order_UnallocateFromForexContract
-- Classname: de.metas.forex.webui.process.C_Order_UnallocateFromForexContract
-- 2023-07-10T14:10:45.518Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585282,'Y','de.metas.forex.webui.process.C_Order_UnallocateFromForexContract','N',TO_TIMESTAMP('2023-07-10 17:10:45','YYYY-MM-DD HH24:MI:SS'),100,'D','The allocated amount of the selected lines will be removed from the Foreign Exchange Contract (FEC).','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Remove allocation from FEC','json','N','Y','xls','Java',TO_TIMESTAMP('2023-07-10 17:10:45','YYYY-MM-DD HH24:MI:SS'),100,'C_Order_UnallocateFromForexContract')
;

-- 2023-07-10T14:10:45.518Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585282 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: C_Order_UnallocateFromForexContract
-- Classname: de.metas.forex.webui.process.C_Order_UnallocateFromForexContract
-- 2023-07-11T06:57:05.591Z
UPDATE AD_Process SET Description='The allocated amount of the selected lines will be removed from the Foreign Exchange Contract (FEC).',Updated=TO_TIMESTAMP('2023-07-11 09:57:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585282
;

-- 2023-07-11T06:57:05.600Z
UPDATE AD_Process_Trl trl SET Description='The allocated amount of the selected lines will be removed from the Foreign Exchange Contract (FEC).' WHERE AD_Process_ID=585282 AND AD_Language='en_US'
;



-- Process: C_Order_UnallocateFromForexContract(de.metas.forex.webui.process.C_Order_UnallocateFromForexContract)
-- Table: C_ForeignExchangeContract
-- Window: Foreign Exchange Contract(541664,D)
-- EntityType: D
-- 2023-07-11T08:30:36.170Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585282,542281,541395,541664,TO_TIMESTAMP('2023-07-11 11:30:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-07-11 11:30:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;



-- Process: C_Order_UnallocateFromForexContract(de.metas.forex.webui.process.C_Order_UnallocateFromForexContract)
-- 2023-07-11T09:17:47.633Z
UPDATE AD_Process_Trl SET Description='Der zugeordnete Betrag der ausgewählten Positionen wird aus dem Devisenvertrag entfernt (engl.: Foreign Exchange Contract, FEC).', Help='Der zugeordnete Betrag der ausgewählten Positionen wird aus dem Devisenvertrag entfernt (engl.: Foreign Exchange Contract, FEC).', Name='Zuordnung zum Devisenvertrag (FEC) aufheben',Updated=TO_TIMESTAMP('2023-07-11 12:17:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585282
;

-- Process: C_Order_UnallocateFromForexContract(de.metas.forex.webui.process.C_Order_UnallocateFromForexContract)
-- 2023-07-11T09:17:58.691Z
UPDATE AD_Process_Trl SET Description='Der zugeordnete Betrag der ausgewählten Positionen wird aus dem Devisenvertrag entfernt (engl.: Foreign Exchange Contract, FEC).', Help='Der zugeordnete Betrag der ausgewählten Positionen wird aus dem Devisenvertrag entfernt (engl.: Foreign Exchange Contract, FEC).', Name='Zuordnung zum Devisenvertrag (FEC) aufheben',Updated=TO_TIMESTAMP('2023-07-11 12:17:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585282
;

-- Process: C_Order_UnallocateFromForexContract(de.metas.forex.webui.process.C_Order_UnallocateFromForexContract)
-- 2023-07-11T09:18:09.400Z
UPDATE AD_Process_Trl SET Description='Der zugeordnete Betrag der ausgewählten Positionen wird aus dem Devisenvertrag entfernt (engl.: Foreign Exchange Contract, FEC).', Help='Der zugeordnete Betrag der ausgewählten Positionen wird aus dem Devisenvertrag entfernt (engl.: Foreign Exchange Contract, FEC).', Name='Zuordnung zum Devisenvertrag (FEC) aufheben',Updated=TO_TIMESTAMP('2023-07-11 12:18:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585282
;

-- Process: C_Order_UnallocateFromForexContract(de.metas.forex.webui.process.C_Order_UnallocateFromForexContract)
-- 2023-07-11T09:18:19.609Z
UPDATE AD_Process_Trl SET Description='Der zugeordnete Betrag der ausgewählten Positionen wird aus dem Devisenvertrag entfernt (engl.: Foreign Exchange Contract, FEC).', Help='Der zugeordnete Betrag der ausgewählten Positionen wird aus dem Devisenvertrag entfernt (engl.: Foreign Exchange Contract, FEC).', Name='Zuordnung zum Devisenvertrag (FEC) aufheben',Updated=TO_TIMESTAMP('2023-07-11 12:18:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585282
;


-- Process: C_Order_UnallocateFromForexContract(de.metas.forex.webui.process.C_Order_UnallocateFromForexContract)
-- Table: C_Order
-- EntityType: D
-- 2023-07-12T08:18:46.391Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585282,259,541398,TO_TIMESTAMP('2023-07-12 11:18:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-07-12 11:18:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;


