-- Run mode: SWING_CLIENT

-- Value: M_ShipperTransportation_AddShipments
-- Classname: de.metas.handlingunits.shipping.process.M_ShipperTransportation_AddShipments
-- 2024-12-03T16:44:33.433Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585438,'N','de.metas.handlingunits.shipping.process.M_ShipperTransportation_AddShipments','N',TO_TIMESTAMP('2024-12-03 18:44:33.227','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Task 08743','Y','N','Y','N','Y','N','N','N','N','Y','N','Y',0,'Add shipments','json','N','Y','','Java',TO_TIMESTAMP('2024-12-03 18:44:33.227','YYYY-MM-DD HH24:MI:SS.US'),100,'M_ShipperTransportation_AddShipments')
;

-- 2024-12-03T16:44:33.447Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585438 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_ShipperTransportation_AddShipments(de.metas.handlingunits.shipping.process.M_ShipperTransportation_AddShipments)
-- 2024-12-03T16:44:38.108Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Lieferungen hinzufügen',Updated=TO_TIMESTAMP('2024-12-03 18:44:38.108','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585438
;

-- Process: M_ShipperTransportation_AddShipments(de.metas.handlingunits.shipping.process.M_ShipperTransportation_AddShipments)
-- 2024-12-03T16:44:44.095Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Lieferungen hinzufügen',Updated=TO_TIMESTAMP('2024-12-03 18:44:44.094','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585438
;

-- 2024-12-03T16:44:44.097Z
UPDATE AD_Process SET Name='Lieferungen hinzufügen' WHERE AD_Process_ID=585438
;

-- Process: M_ShipperTransportation_AddShipments(de.metas.handlingunits.shipping.process.M_ShipperTransportation_AddShipments)
-- Table: M_ShipperTransportation
-- EntityType: de.metas.inout
-- 2024-12-03T16:51:21.167Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585438,540030,541537,TO_TIMESTAMP('2024-12-03 18:51:20.981','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.inout','Y',TO_TIMESTAMP('2024-12-03 18:51:20.981','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

