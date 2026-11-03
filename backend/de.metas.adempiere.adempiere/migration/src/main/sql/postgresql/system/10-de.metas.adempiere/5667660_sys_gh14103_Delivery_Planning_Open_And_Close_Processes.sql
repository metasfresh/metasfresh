-- Value: de.metas.deliveryplanning.DeliveryPlanningService.AllOpen
-- 2022-12-08T13:29:24.907Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545216,0,TO_TIMESTAMP('2022-12-08 15:29:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','All records are still open.','I',TO_TIMESTAMP('2022-12-08 15:29:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.deliveryplanning.DeliveryPlanningService.AllOpen')
;

-- 2022-12-08T13:29:24.910Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545216 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.AllOpen
-- 2022-12-08T13:29:38.881Z
UPDATE AD_Message_Trl SET MsgText='Alle Datensätze sind offen.',Updated=TO_TIMESTAMP('2022-12-08 15:29:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545216
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.AllOpen
-- 2022-12-08T13:29:42.317Z
UPDATE AD_Message_Trl SET MsgText='Alle Datensätze sind offen.',Updated=TO_TIMESTAMP('2022-12-08 15:29:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545216
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.AllOpen
-- 2022-12-08T13:29:45.485Z
UPDATE AD_Message_Trl SET MsgText='Alle Datensätze sind offen.',Updated=TO_TIMESTAMP('2022-12-08 15:29:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545216
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.AllClosed
-- 2022-12-08T13:30:44.188Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545217,0,TO_TIMESTAMP('2022-12-08 15:30:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','All records are already closed.','I',TO_TIMESTAMP('2022-12-08 15:30:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.deliveryplanning.DeliveryPlanningService.AllClosed')
;

-- 2022-12-08T13:30:44.189Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545217 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.AllClosed
-- 2022-12-08T13:30:57.171Z
UPDATE AD_Message_Trl SET MsgText='Alle Datensätze sind gesclossen.',Updated=TO_TIMESTAMP('2022-12-08 15:30:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545217
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.AllClosed
-- 2022-12-08T13:30:59.889Z
UPDATE AD_Message_Trl SET MsgText='Alle Datensätze sind gesclossen.',Updated=TO_TIMESTAMP('2022-12-08 15:30:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545217
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.AllClosed
-- 2022-12-08T13:31:01.909Z
UPDATE AD_Message_Trl SET MsgText='Alle Datensätze sind gesclossen.',Updated=TO_TIMESTAMP('2022-12-08 15:31:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545217
;

-- Value: M_Delivery_Planning_Close
-- Classname: de.metas.deliveryplanning.process.M_Delivery_Planning_Close
-- 2022-12-08T15:05:31.893Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585163,'Y','de.metas.deliveryplanning.process.M_Delivery_Planning_Close','N',TO_TIMESTAMP('2022-12-08 17:05:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Close','json','N','N','xls','Java',TO_TIMESTAMP('2022-12-08 17:05:31','YYYY-MM-DD HH24:MI:SS'),100,'M_Delivery_Planning_Close')
;

-- 2022-12-08T15:05:31.896Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585163 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_Delivery_Planning_Close(de.metas.deliveryplanning.process.M_Delivery_Planning_Close)
-- Table: M_Delivery_Planning
-- EntityType: D
-- 2022-12-08T15:06:09.022Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585163,542259,541315,TO_TIMESTAMP('2022-12-08 17:06:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2022-12-08 17:06:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Value: M_Delivery_Planning_ReOpen
-- Classname: de.metas.deliveryplanning.process.M_Delivery_Planning_ReOpen
-- 2022-12-08T15:09:16.280Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585164,'Y','de.metas.deliveryplanning.process.M_Delivery_Planning_ReOpen','N',TO_TIMESTAMP('2022-12-08 17:09:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Open','json','N','N','xls','Java',TO_TIMESTAMP('2022-12-08 17:09:16','YYYY-MM-DD HH24:MI:SS'),100,'M_Delivery_Planning_ReOpen')
;

-- 2022-12-08T15:09:16.281Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585164 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_Delivery_Planning_ReOpen(de.metas.deliveryplanning.process.M_Delivery_Planning_ReOpen)
-- Table: M_Delivery_Planning
-- EntityType: D
-- 2022-12-08T15:09:36.671Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585164,542259,541316,TO_TIMESTAMP('2022-12-08 17:09:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2022-12-08 17:09:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Process: M_Delivery_Planning_Close(de.metas.deliveryplanning.process.M_Delivery_Planning_Close)
-- 2022-12-08T15:10:46.032Z
UPDATE AD_Process_Trl SET Name='Auswahl - Zeilen schließen',Updated=TO_TIMESTAMP('2022-12-08 17:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585163
;

-- Value: M_Delivery_Planning_Close
-- Classname: de.metas.deliveryplanning.process.M_Delivery_Planning_Close
-- 2022-12-08T15:10:49.791Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Auswahl - Zeilen schließen',Updated=TO_TIMESTAMP('2022-12-08 17:10:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585163
;

-- Process: M_Delivery_Planning_Close(de.metas.deliveryplanning.process.M_Delivery_Planning_Close)
-- 2022-12-08T15:10:49.784Z
UPDATE AD_Process_Trl SET Name='Auswahl - Zeilen schließen',Updated=TO_TIMESTAMP('2022-12-08 17:10:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585163
;

-- Value: M_Delivery_Planning_ReOpen
-- Classname: de.metas.deliveryplanning.process.M_Delivery_Planning_ReOpen
-- 2022-12-08T15:11:30.531Z
UPDATE AD_Process SET Description='Auswahl - Zeilen reaktivieren', Help=NULL, Name='Open',Updated=TO_TIMESTAMP('2022-12-08 17:11:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585164
;

-- Process: M_Delivery_Planning_ReOpen(de.metas.deliveryplanning.process.M_Delivery_Planning_ReOpen)
-- 2022-12-08T15:11:30.525Z
UPDATE AD_Process_Trl SET Description='Auswahl - Zeilen reaktivieren',Updated=TO_TIMESTAMP('2022-12-08 17:11:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585164
;

-- Value: M_Delivery_Planning_ReOpen
-- Classname: de.metas.deliveryplanning.process.M_Delivery_Planning_ReOpen
-- 2022-12-08T15:11:40.568Z
UPDATE AD_Process SET Description='', Help=NULL, Name='Auswahl - Zeilen reaktivieren',Updated=TO_TIMESTAMP('2022-12-08 17:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585164
;

-- Process: M_Delivery_Planning_ReOpen(de.metas.deliveryplanning.process.M_Delivery_Planning_ReOpen)
-- 2022-12-08T15:11:40.560Z
UPDATE AD_Process_Trl SET Description='', Name='Auswahl - Zeilen reaktivieren',Updated=TO_TIMESTAMP('2022-12-08 17:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585164
;

-- Process: M_Delivery_Planning_ReOpen(de.metas.deliveryplanning.process.M_Delivery_Planning_ReOpen)
-- 2022-12-08T15:11:42.783Z
UPDATE AD_Process_Trl SET Name='Auswahl - Zeilen reaktivieren',Updated=TO_TIMESTAMP('2022-12-08 17:11:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585164
;

