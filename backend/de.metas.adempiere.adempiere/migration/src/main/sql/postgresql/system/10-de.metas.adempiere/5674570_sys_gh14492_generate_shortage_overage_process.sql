-- Value: M_Delivery_Planning_GenerateShortageOverage
-- Classname: de.metas.deliveryplanning.process.M_Delivery_Planning_GenerateShortageOverage
-- 2023-01-31T13:58:01.299Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585199,'Y','de.metas.deliveryplanning.process.M_Delivery_Planning_GenerateShortageOverage','N',TO_TIMESTAMP('2023-01-31 14:58:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Generate Shortage / Overage Document','json','Y','N','xls','Java',TO_TIMESTAMP('2023-01-31 14:58:01','YYYY-MM-DD HH24:MI:SS'),100,'M_Delivery_Planning_GenerateShortageOverage')
;

-- 2023-01-31T13:58:01.302Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585199 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_Delivery_Planning_GenerateShortageOverage(de.metas.deliveryplanning.process.M_Delivery_Planning_GenerateShortageOverage)
-- Table: M_Delivery_Planning
-- Window: Delivery Planning(541632,D)
-- EntityType: D
-- 2023-02-02T13:37:35.342Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585199,542259,541351,541632,TO_TIMESTAMP('2023-02-02 14:37:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-02-02 14:37:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Process: M_Delivery_Planning_GenerateShortageOverage(de.metas.deliveryplanning.process.M_Delivery_Planning_GenerateShortageOverage)
-- ParameterName: Quantity
-- 2023-02-02T14:18:53.268Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585199,542532,12,'Quantity',TO_TIMESTAMP('2023-02-02 15:18:53','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','Y','N','Quantity',10,TO_TIMESTAMP('2023-02-02 15:18:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T14:18:53.270Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542532 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: Event_ShortageGenerated
-- 2023-02-03T15:10:56.820Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545232,0,TO_TIMESTAMP('2023-02-03 16:10:56','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Shortage {0} was generated','I',TO_TIMESTAMP('2023-02-03 16:10:56','YYYY-MM-DD HH24:MI:SS'),100,'Event_ShortageGenerated')
;

-- 2023-02-03T15:10:56.823Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545232 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: Event_ShortageGenerated
-- 2023-02-03T15:11:09.816Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-02-03 16:11:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545232
;

-- Value: Event_OverageGenerated
-- 2023-02-03T15:23:24.115Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545233,0,TO_TIMESTAMP('2023-02-03 16:23:23','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Overage {0} was generated','I',TO_TIMESTAMP('2023-02-03 16:23:23','YYYY-MM-DD HH24:MI:SS'),100,'Event_OverageGenerated')
;

-- 2023-02-03T15:23:24.121Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545233 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: Event_OverageGenerated
-- 2023-02-03T15:23:45.438Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-02-03 16:23:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545233
;

-- 2023-02-06T14:57:01.133Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541595,'S',TO_TIMESTAMP('2023-02-06 15:57:01','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','de.metas.deliveryplanning.shortageOverageDocument.TargetWindow',TO_TIMESTAMP('2023-02-06 15:57:01','YYYY-MM-DD HH24:MI:SS'),100,'168')
;

-- Value: InventoryLineQuantityNegativeError
-- 2023-02-08T11:54:42.629Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545234,0,TO_TIMESTAMP('2023-02-08 12:54:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Resulting inventoryLine quantity can''t be negative','E',TO_TIMESTAMP('2023-02-08 12:54:42','YYYY-MM-DD HH24:MI:SS'),100,'InventoryLineQuantityNegativeError')
;

-- 2023-02-08T11:54:42.634Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545234 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ShortageOverageQuantityZeroError
-- 2023-02-08T12:34:02.041Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545235,0,TO_TIMESTAMP('2023-02-08 13:34:01','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Quantity 0 is not allowed','E',TO_TIMESTAMP('2023-02-08 13:34:01','YYYY-MM-DD HH24:MI:SS'),100,'ShortageOverageQuantityZeroError')
;

-- 2023-02-08T12:34:02.044Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545235 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;