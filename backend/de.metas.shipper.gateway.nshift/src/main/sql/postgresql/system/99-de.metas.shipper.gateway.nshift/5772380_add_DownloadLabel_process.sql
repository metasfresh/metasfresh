-- Run mode: SWING_CLIENT

-- Table: Carrier_ShipmentOrder
-- 2025-10-03T14:43:11.518Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2025-10-03 14:43:11.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542532
;

-- Table: Carrier_ShipmentOrder_Item
-- 2025-10-03T14:43:32.532Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2025-10-03 14:43:32.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542536
;

-- Table: Carrier_ShipmentOrder_Parcel
-- 2025-10-03T14:43:42.276Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2025-10-03 14:43:42.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542535
;

-- Tab: Versandauftrag(541956,D) -> Versandauftragspaket
-- Table: Carrier_ShipmentOrder_Parcel
-- 2025-10-03T14:49:09.997Z
UPDATE AD_Tab SET AD_Column_ID=591137, Parent_Column_ID=591069,Updated=TO_TIMESTAMP('2025-10-03 14:49:09.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548457
;

-- Tab: Versandauftragspaket(541957,D) -> Shipment Order Item
-- Table: Carrier_ShipmentOrder_Item
-- 2025-10-03T14:49:35.956Z
UPDATE AD_Tab SET AD_Column_ID=591158, Parent_Column_ID=591136,Updated=TO_TIMESTAMP('2025-10-03 14:49:35.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548459
;

-- Value: Carrier_ShipmentOrder_Parcel_Label_Download
-- Classname: de.metas.shipper.gateway.commons.model.ShipmentOrderRepository
-- 2025-10-03T15:07:55.422Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,585507,'Y','de.metas.shipper.gateway.commons.model.ShipmentOrderRepository','N',TO_TIMESTAMP('2025-10-03 15:07:55.209000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Download Versandetikett','json','N','N','xls','Java',TO_TIMESTAMP('2025-10-03 15:07:55.209000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Carrier_ShipmentOrder_Parcel_Label_Download')
;

-- 2025-10-03T15:07:55.425Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585507 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Carrier_ShipmentOrder_Parcel_Label_Download(de.metas.shipper.gateway.commons.model.ShipmentOrderRepository)
-- 2025-10-03T15:08:15.054Z
UPDATE AD_Process_Trl SET Name='Download Label',Updated=TO_TIMESTAMP('2025-10-03 15:08:15.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585507
;

-- 2025-10-03T15:08:15.055Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Carrier_ShipmentOrder_Parcel_Label_Download(de.metas.shipper.gateway.commons.model.ShipmentOrderRepository)
-- Table: Carrier_ShipmentOrder_Parcel
-- EntityType: D
-- 2025-10-03T15:09:00.657Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585507,542535,541573,TO_TIMESTAMP('2025-10-03 15:09:00.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-10-03 15:09:00.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Value: Carrier_ShipmentOrder_Parcel_Label_Download
-- Classname: de.metas.shipper.gateway.commons.process.Carrier_ShipmentOrder_Parcel_Label_Download
-- 2025-10-03T15:09:46.754Z
UPDATE AD_Process SET Classname='de.metas.shipper.gateway.commons.process.Carrier_ShipmentOrder_Parcel_Label_Download',Updated=TO_TIMESTAMP('2025-10-03 15:09:46.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585507
;

