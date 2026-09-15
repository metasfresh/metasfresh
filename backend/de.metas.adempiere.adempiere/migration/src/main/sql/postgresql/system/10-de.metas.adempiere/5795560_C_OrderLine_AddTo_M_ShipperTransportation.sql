-- Run mode: SWING_CLIENT

-- Value: C_OrderLine_AddTo_M_ShipperTransportation
-- Classname: de.metas.shipping.process.C_OrderLine_AddTo_M_ShipperTransportation
-- 2026-03-25T09:43:27.125Z
INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, CopyFromProcess, Created, CreatedBy, CSVFieldQuote, EntityType, IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsIncludeCSVHeaderRow, IsLogWarning, IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders, IsUpdateExportDate,
                        IsUseBPartnerLanguage, LockWaitTimeout, Name, PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, Type, Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585602, 'Y', 'de.metas.shipping.process.C_OrderLine_AddTo_M_ShipperTransportation', 'N', TO_TIMESTAMP('2026-03-25 09:43:26.027000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, '"', 'D', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'Y', 0, 'Position in Transportauftrag übernehmen', 'json', 'N', 'N', 'Java',
        TO_TIMESTAMP('2026-03-25 09:43:26.027000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'C_OrderLine_AddTo_M_ShipperTransportation')
;

-- 2026-03-25T09:43:27.133Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585602 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_OrderLine_AddTo_M_ShipperTransportation(de.metas.shipping.process.C_OrderLine_AddTo_M_ShipperTransportation)
-- ParameterName: M_ShipperTransportation_ID
-- 2026-03-25T09:44:56.692Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,540089,0,585602,543171,30,540248,'M_ShipperTransportation_ID',TO_TIMESTAMP('2026-03-25 09:44:56.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'METAS_SHIPPING',0,'Y','N','Y','N','Y','N','Transport Auftrag',10,'N',TO_TIMESTAMP('2026-03-25 09:44:56.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-25T09:44:56.694Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543171 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_OrderLine_AddTo_M_ShipperTransportation(de.metas.shipping.process.C_OrderLine_AddTo_M_ShipperTransportation)
-- 2026-03-25T09:46:15.815Z
UPDATE AD_Process_Trl SET Name='Add Orderline to Transportation Order',Updated=TO_TIMESTAMP('2026-03-25 09:46:15.815000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585602
;

-- 2026-03-25T09:46:15.817Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: C_OrderLine_AddTo_M_ShipperTransportation(de.metas.shipping.process.C_OrderLine_AddTo_M_ShipperTransportation)
-- Table: C_OrderLine
-- EntityType: D
-- 2026-03-25T09:54:46.925Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585602,260,541636,TO_TIMESTAMP('2026-03-25 09:54:46.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2026-03-25 09:54:46.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Process: C_OrderLine_AddTo_M_ShipperTransportation(de.metas.shipping.process.C_OrderLine_AddTo_M_ShipperTransportation)
-- Table: C_OrderLine
-- Tab: Bestellung(181,D) -> Bestellposition(293,D)
-- Window: Bestellung(181,D)
-- EntityType: D
-- 2026-03-25T13:25:04.989Z
UPDATE AD_Table_Process SET AD_Tab_ID=293, AD_Window_ID=181,Updated=TO_TIMESTAMP('2026-03-25 13:25:04.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541636
;

-- Value: OrderLineAssignedToProcessedTransportationOrder
-- 2026-03-25T14:08:21.517Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545648,0,TO_TIMESTAMP('2026-03-25 14:08:20.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Mindestens eine Bestellposition ist einem bereits verarbeiteten Transportauftrag zugeordnet.','E',TO_TIMESTAMP('2026-03-25 14:08:20.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'OrderLineAssignedToProcessedTransportationOrder')
;

-- 2026-03-25T14:08:21.521Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545648 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: OrderLineAssignedToProcessedTransportationOrder
-- 2026-03-25T14:08:36.900Z
UPDATE AD_Message_Trl SET MsgText='At least one purchase order line is assigned to a processed transportation order.',Updated=TO_TIMESTAMP('2026-03-25 14:08:36.900000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545648
;

-- 2026-03-25T14:08:36.901Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: OrderLineAssignedToProcessedTransportationOrder
-- 2026-03-25T14:09:38.273Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-25 14:09:38.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545648
;

-- Value: OrderAssignedToDifferentTransportationOrder
-- 2026-03-25T16:45:16.448Z
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=545607
;

-- 2026-03-25T16:45:16.457Z
DELETE FROM AD_Message WHERE AD_Message_ID=545607
;

