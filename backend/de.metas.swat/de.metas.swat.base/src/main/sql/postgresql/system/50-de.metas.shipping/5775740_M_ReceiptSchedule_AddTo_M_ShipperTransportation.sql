-- Run mode: SWING_CLIENT

-- Value: M_ReceiptSchedule_AddTo_M_ShipperTransportation
-- Classname: de.metas.inoutcandidate.process.M_ReceiptSchedule_AddTo_M_ShipperTransportation
-- 2025-11-05T17:09:34.472Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585522,'Y','de.metas.inoutcandidate.process.M_ReceiptSchedule_AddTo_M_ShipperTransportation','N',TO_TIMESTAMP('2025-11-05 17:09:34.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'M_ReceiptSchedule_AddTo_M_ShipperTransportation','json','N','N','xls','Java',TO_TIMESTAMP('2025-11-05 17:09:34.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_ReceiptSchedule_AddTo_M_ShipperTransportation')
;

-- 2025-11-05T17:09:34.475Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585522 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: M_ReceiptSchedule_AddTo_M_ShipperTransportation
-- Classname: de.metas.inoutcandidate.process.M_ReceiptSchedule_AddTo_M_ShipperTransportation
-- 2025-11-05T17:10:31.416Z
UPDATE AD_Process SET Name='Zum Transportauftrag hinzufügen',Updated=TO_TIMESTAMP('2025-11-05 17:10:31.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585522
;

-- 2025-11-05T17:10:31.419Z
UPDATE AD_Process_Trl trl SET Name='Zum Transportauftrag hinzufügen' WHERE AD_Process_ID=585522 AND AD_Language='de_DE'
;

-- Process: M_ReceiptSchedule_AddTo_M_ShipperTransportation(de.metas.inoutcandidate.process.M_ReceiptSchedule_AddTo_M_ShipperTransportation)
-- 2025-11-05T17:10:39.272Z
UPDATE AD_Process_Trl SET Name='Zum Transportauftrag hinzufügen',Updated=TO_TIMESTAMP('2025-11-05 17:10:39.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585522
;

-- 2025-11-05T17:10:39.273Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_ReceiptSchedule_AddTo_M_ShipperTransportation(de.metas.inoutcandidate.process.M_ReceiptSchedule_AddTo_M_ShipperTransportation)
-- 2025-11-05T17:10:45.094Z
UPDATE AD_Process_Trl SET Name='Zum Transportauftrag hinzufügen',Updated=TO_TIMESTAMP('2025-11-05 17:10:45.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585522
;

-- 2025-11-05T17:10:45.095Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_ReceiptSchedule_AddTo_M_ShipperTransportation(de.metas.inoutcandidate.process.M_ReceiptSchedule_AddTo_M_ShipperTransportation)
-- 2025-11-05T17:10:54.596Z
UPDATE AD_Process_Trl SET Name='Add to Transportation Order',Updated=TO_TIMESTAMP('2025-11-05 17:10:54.596000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585522
;

-- 2025-11-05T17:10:54.597Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_ReceiptSchedule_AddTo_M_ShipperTransportation(de.metas.inoutcandidate.process.M_ReceiptSchedule_AddTo_M_ShipperTransportation)
-- ParameterName: M_ShipperTransportation_ID
-- 2025-11-05T17:14:19.226Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,540089,0,585522,543019,30,540248,'M_ShipperTransportation_ID',TO_TIMESTAMP('2025-11-05 17:14:18.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'METAS_SHIPPING',0,'Y','N','Y','N','Y','N','Transport Auftrag',10,TO_TIMESTAMP('2025-11-05 17:14:18.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-05T17:14:19.230Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543019 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_ReceiptSchedule_AddTo_M_ShipperTransportation(de.metas.inoutcandidate.process.M_ReceiptSchedule_AddTo_M_ShipperTransportation)
-- Table: M_ReceiptSchedule
-- EntityType: METAS_SHIPPING
-- 2025-11-05T17:15:27.427Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585522,540524,541583,TO_TIMESTAMP('2025-11-05 17:15:27.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'METAS_SHIPPING','Y',TO_TIMESTAMP('2025-11-05 17:15:27.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','Y')
;

-- Process: M_ReceiptSchedule_AddTo_M_ShipperTransportation(de.metas.inoutcandidate.process.M_ReceiptSchedule_AddTo_M_ShipperTransportation)
-- Table: M_ReceiptSchedule
-- EntityType: METAS_SHIPPING
-- 2025-11-05T17:15:46.517Z
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction_Default='N',Updated=TO_TIMESTAMP('2025-11-05 17:15:46.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541583
;

