-- Value: C_Order_Generate_Shipping_Notification_Proforma
-- Classname: de.metas.order.process.C_Order_Generate_Shipping_Notification_Proforma
-- 2024-10-30T15:32:50.813Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585432,'Y','de.metas.order.process.C_Order_Generate_Shipping_Notification_Proforma','N',TO_TIMESTAMP('2024-10-30 16:32:50.66','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Proforma Lieferavis generieren','json','N','N','xls','Java',TO_TIMESTAMP('2024-10-30 16:32:50.66','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Order_Generate_Shipping_Notification_Proforma')
;

-- 2024-10-30T15:32:50.820Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585432 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Order_Generate_Shipping_Notification_Proforma(de.metas.order.process.C_Order_Generate_Shipping_Notification_Proforma)
-- 2024-10-30T15:33:29.131Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Generate Proforma Shipping Notification',Updated=TO_TIMESTAMP('2024-10-30 16:33:29.131','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585432
;

-- Process: C_Order_Generate_Shipping_Notification_Proforma(de.metas.order.process.C_Order_Generate_Shipping_Notification_Proforma)
-- 2024-10-30T15:33:29.706Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-30 16:33:29.706','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585432
;

-- Process: C_Order_Generate_Shipping_Notification_Proforma(de.metas.order.process.C_Order_Generate_Shipping_Notification_Proforma)
-- 2024-10-30T15:33:35.520Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-30 16:33:35.52','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585432
;

-- Process: C_Order_Generate_Shipping_Notification_Proforma(de.metas.order.process.C_Order_Generate_Shipping_Notification_Proforma)
-- ParameterName: PhysicalClearanceDate
-- 2024-10-30T15:35:46.383Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582696,0,585432,542900,15,'PhysicalClearanceDate',TO_TIMESTAMP('2024-10-30 16:35:46.259','YYYY-MM-DD HH24:MI:SS.US'),100,'@#Date@','de.metas.shippingnotification',0,'Y','N','Y','N','N','N','Datum der physischen Freigabe',10,TO_TIMESTAMP('2024-10-30 16:35:46.259','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-30T15:35:46.395Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542900 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-10-30T15:35:46.412Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582696)
;

-- Process: C_Order_Generate_Shipping_Notification_Proforma(de.metas.order.process.C_Order_Generate_Shipping_Notification_Proforma)
-- Table: C_Order
-- EntityType: de.metas.swat
-- 2024-10-30T15:36:54.499Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585432,259,541527,TO_TIMESTAMP('2024-10-30 16:36:54.282','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y',TO_TIMESTAMP('2024-10-30 16:36:54.282','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Value: C_Order_GenerateShipment_Proforma
-- Classname: de.metas.order.process.C_Order_GenerateShipment_Proforma
-- 2024-10-30T15:38:50.049Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585433,'Y','de.metas.order.process.C_Order_GenerateShipment_Proforma','N',TO_TIMESTAMP('2024-10-30 16:38:49.909','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Proforma Lieferung erstellen','json','N','N','xls','Java',TO_TIMESTAMP('2024-10-30 16:38:49.909','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Order_GenerateShipment_Proforma')
;

-- 2024-10-30T15:38:50.050Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585433 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Order_GenerateShipment_Proforma(de.metas.order.process.C_Order_GenerateShipment_Proforma)
-- 2024-10-30T15:39:08.273Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Generate Proforma Shipment',Updated=TO_TIMESTAMP('2024-10-30 16:39:08.273','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585433
;

-- Process: C_Order_GenerateShipment_Proforma(de.metas.order.process.C_Order_GenerateShipment_Proforma)
-- 2024-10-30T15:39:08.834Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-30 16:39:08.834','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585433
;

-- Process: C_Order_GenerateShipment_Proforma(de.metas.order.process.C_Order_GenerateShipment_Proforma)
-- 2024-10-30T15:39:09.687Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-30 16:39:09.687','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585433
;

-- Process: C_Order_GenerateShipment_Proforma(de.metas.order.process.C_Order_GenerateShipment_Proforma)
-- ParameterName: MovementDate
-- 2024-10-30T15:41:55.277Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1037,0,585433,542901,15,'MovementDate',TO_TIMESTAMP('2024-10-30 16:41:55.137','YYYY-MM-DD HH24:MI:SS.US'),100,'@#Date@','Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde','de.metas.swat',0,'"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.','Y','N','Y','N','N','N','Bewegungsdatum',10,TO_TIMESTAMP('2024-10-30 16:41:55.137','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-30T15:41:55.282Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542901 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-10-30T15:41:55.284Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(1037)
;

-- Process: C_Order_GenerateShipment_Proforma(de.metas.order.process.C_Order_GenerateShipment_Proforma)
-- Table: C_Order
-- EntityType: de.metas.swat
-- 2024-10-30T15:42:24.893Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585433,259,541528,TO_TIMESTAMP('2024-10-30 16:42:24.502','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','Y',TO_TIMESTAMP('2024-10-30 16:42:24.502','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

