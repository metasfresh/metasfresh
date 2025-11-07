-- Run mode: SWING_CLIENT

-- Value: MoreThanOneShipperSelected
-- 2025-11-05T08:20:53.084Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545605,0,TO_TIMESTAMP('2025-11-05 08:20:52.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Mehr als ein oder kein Lieferweg ausgewählt','I',TO_TIMESTAMP('2025-11-05 08:20:52.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MoreThanOneOrNoShipperSelected')
;

-- 2025-11-05T08:20:53.116Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545605 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: MoreThanOneShipperSelected
-- 2025-11-05T08:21:29.629Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='More than one or no shipper selected',Updated=TO_TIMESTAMP('2025-11-05 08:21:29.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545605
;

-- 2025-11-05T08:21:29.631Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: MoreThanOneShipperSelected
-- 2025-11-05T08:21:31.643Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-05 08:21:31.643000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545605
;

-- Value: MoreThanOneShipperSelected
-- 2025-11-05T08:21:33.114Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-05 08:21:33.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545605
;

-- Run mode: SWING_CLIENT

-- 2025-11-06T12:12:19.938Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584199,0,'IsIncludeCarrierAdviseManual',TO_TIMESTAMP('2025-11-06 12:12:19.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Inklusive Lieferweg-Abfrage Status Manuell','Inklusive Lieferweg-Abfrage Status Manuell',TO_TIMESTAMP('2025-11-06 12:12:19.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-06T12:12:19.955Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584199 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-11-06T12:12:26.357Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-06 12:12:26.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584199
;

-- 2025-11-06T12:12:26.380Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584199,'de_DE')
;

-- Element: IsIncludeCarrierAdviseManual
-- 2025-11-06T12:13:02.226Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Including Carrier Advise Status Manual', PrintName='Including Carrier Advise Status Manual',Updated=TO_TIMESTAMP('2025-11-06 12:13:02.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584199 AND AD_Language='en_US'
;

-- 2025-11-06T12:13:02.227Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-06T12:13:02.410Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584199,'en_US')
;

-- Element: IsIncludeCarrierAdviseManual
-- 2025-11-06T12:13:03.755Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-06 12:13:03.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584199 AND AD_Language='de_CH'
;

-- 2025-11-06T12:13:03.760Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584199,'de_CH')
;

-- Element: IsIncludeCarrierAdviseManual
-- 2025-11-06T12:13:05.232Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-06 12:13:05.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584199 AND AD_Language='de_DE'
;

-- 2025-11-06T12:13:05.235Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584199,'de_DE')
;

-- 2025-11-06T12:13:05.237Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584199,'de_DE')
;

-- Value: M_ShipmentSchedule_Advise
-- Classname: de.metas.shipper.gateway.commons.process.M_ShipmentSchedule_Advise
-- 2025-11-06T12:16:16.373Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,585523,'Y','de.metas.shipper.gateway.commons.process.M_ShipmentSchedule_Advise','N',TO_TIMESTAMP('2025-11-06 12:16:16.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.shipper.gateway.commons','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Lieferweg-Abfrage','json','N','N','xls','Java',TO_TIMESTAMP('2025-11-06 12:16:16.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_ShipmentSchedule_Advise')
;

-- 2025-11-06T12:16:16.379Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585523 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_ShipmentSchedule_Advise(de.metas.shipper.gateway.commons.process.M_ShipmentSchedule_Advise)
-- 2025-11-06T12:16:33.710Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Carrier Advise',Updated=TO_TIMESTAMP('2025-11-06 12:16:33.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585523
;

-- 2025-11-06T12:16:33.711Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_ShipmentSchedule_Advise(de.metas.shipper.gateway.commons.process.M_ShipmentSchedule_Advise)
-- 2025-11-06T12:16:34.593Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-06 12:16:34.593000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585523
;

-- Process: M_ShipmentSchedule_Advise(de.metas.shipper.gateway.commons.process.M_ShipmentSchedule_Advise)
-- 2025-11-06T12:16:39.660Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-06 12:16:39.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585523
;

-- Process: M_ShipmentSchedule_Advise(de.metas.shipper.gateway.commons.process.M_ShipmentSchedule_Advise)
-- ParameterName: IsIncludeCarrierAdviseManual
-- 2025-11-06T12:17:47.563Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584199,0,585523,543020,20,'IsIncludeCarrierAdviseManual',TO_TIMESTAMP('2025-11-06 12:17:47.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.shipper.gateway.commons',1,'Y','N','Y','N','Y','N','Inklusive Lieferweg-Abfrage Status Manuell',10,TO_TIMESTAMP('2025-11-06 12:17:47.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-06T12:17:47.569Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543020 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_ShipmentSchedule_Advise(de.metas.shipper.gateway.commons.process.M_ShipmentSchedule_Advise)
-- Table: M_ShipmentSchedule
-- EntityType: de.metas.shipper.gateway.commons
-- 2025-11-06T12:27:37.410Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585523,500221,541584,TO_TIMESTAMP('2025-11-06 12:27:37.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.shipper.gateway.commons','Y',TO_TIMESTAMP('2025-11-06 12:27:37.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Name: Carrier_Service_ID_for_M_Shipper_ID
-- 2025-11-06T12:20:49.953Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540757,'Carrier_Service_ID IN (SELECT cs.Carrier_Service_ID FROM Carrier_Service cs WHERE cgt.M_Shipper_ID = @M_Shipper_ID/-1@)',TO_TIMESTAMP('2025-11-06 12:20:49.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Carrier_Service_ID_for_M_Shipper_ID','S',TO_TIMESTAMP('2025-11-06 12:20:49.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: Carrier_Service_ID_for_M_Shipper_ID
-- 2025-11-06T12:26:19.976Z
UPDATE AD_Val_Rule SET EntityType='de.metas.shipper.gateway.commons',Updated=TO_TIMESTAMP('2025-11-06 12:26:19.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540757
;

-- Value: M_ShipmentSchedule_Advise
-- Classname: de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise
-- 2025-11-06T15:09:28.301Z
UPDATE AD_Process SET Classname='de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise',Updated=TO_TIMESTAMP('2025-11-06 15:09:28.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585523
;

-- Value: M_ShipmentSchedule_Advise_Manual
-- Classname: de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual
-- 2025-11-06T15:29:10.158Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,585524,'Y','de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual','N',TO_TIMESTAMP('2025-11-06 15:29:10.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Lieferweg-Abfrage Manuell','json','N','N','xls','Java',TO_TIMESTAMP('2025-11-06 15:29:10.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_ShipmentSchedule_Advise_Manual')
;

-- 2025-11-06T15:29:10.162Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585524 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- 2025-11-06T15:29:34.375Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Carrier Advise Manual',Updated=TO_TIMESTAMP('2025-11-06 15:29:34.375000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585524
;

-- 2025-11-06T15:29:34.376Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- 2025-11-06T15:29:34.957Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-06 15:29:34.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585524
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- 2025-11-06T15:29:36.550Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-06 15:29:36.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585524
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- ParameterName: IsIncludeCarrierAdviseManual
-- 2025-11-06T15:34:07.565Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584199,0,585524,543021,20,'IsIncludeCarrierAdviseManual',TO_TIMESTAMP('2025-11-06 15:34:07.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','U',1,'Y','N','Y','N','Y','N','Inklusive Lieferweg-Abfrage Status Manuell',10,TO_TIMESTAMP('2025-11-06 15:34:07.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-06T15:34:07.575Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543021 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- ParameterName: M_Shipper_ID
-- 2025-11-06T15:37:44.101Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,455,0,585524,543022,13,'M_Shipper_ID',TO_TIMESTAMP('2025-11-06 15:37:43.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung','0=1','U',0,'"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','Y','N','Y','N','Lieferweg','',20,TO_TIMESTAMP('2025-11-06 15:37:43.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-06T15:37:44.115Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543022 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- ParameterName: Carrier_Product_ID
-- 2025-11-06T15:40:33.020Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584116,0,585524,543023,13,542003,540751,'Carrier_Product_ID',TO_TIMESTAMP('2025-11-06 15:40:32.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U',10,'Y','N','Y','N','Y','N','Lieferweg-Produkt',30,TO_TIMESTAMP('2025-11-06 15:40:32.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-06T15:40:33.023Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543023 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- ParameterName: Carrier_Goods_Type_ID
-- 2025-11-06T15:41:13.810Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584112,0,585524,543024,13,541997,540750,'Carrier_Goods_Type_ID',TO_TIMESTAMP('2025-11-06 15:41:13.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U',10,'Y','N','Y','N','Y','N','Materialzuordnung je Lieferweg',40,TO_TIMESTAMP('2025-11-06 15:41:13.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-06T15:41:13.814Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543024 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Name: Carrier_Service
-- 2025-11-06T15:42:35.120Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542018,TO_TIMESTAMP('2025-11-06 15:42:34.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','N','Carrier_Service',TO_TIMESTAMP('2025-11-06 15:42:34.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-11-06T15:42:35.124Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542018 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Carrier_Service
-- Table: Carrier_Service
-- Key: Carrier_Service.Carrier_Service_ID
-- 2025-11-06T15:42:59.975Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,591323,0,542018,542543,TO_TIMESTAMP('2025-11-06 15:42:59.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','N','N',TO_TIMESTAMP('2025-11-06 15:42:59.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- ParameterName: Carrier_Service_ID
-- 2025-11-06T15:43:16.677Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584113,0,585524,543025,13,540757,'Carrier_Service_ID',TO_TIMESTAMP('2025-11-06 15:43:16.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U',0,'Y','N','Y','N','N','N','Lieferweg-Servicekatalog',50,TO_TIMESTAMP('2025-11-06 15:43:16.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-06T15:43:16.678Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543025 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- ParameterName: Carrier_Service_ID
-- 2025-11-06T15:43:28.335Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=542018,Updated=TO_TIMESTAMP('2025-11-06 15:43:28.335000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543025
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- Table: M_ShipmentSchedule
-- EntityType: de.metas.shipper.gateway.commons
-- 2025-11-06T15:46:09.797Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585524,500221,541585,TO_TIMESTAMP('2025-11-06 15:46:09.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.shipper.gateway.commons','Y',TO_TIMESTAMP('2025-11-06 15:46:09.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- ParameterName: M_Shipper_ID
-- 2025-11-06T16:25:18.410Z
UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2025-11-06 16:25:18.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543022
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- ParameterName: Carrier_Product_ID
-- 2025-11-06T16:25:27.390Z
UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2025-11-06 16:25:27.390000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543023
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- ParameterName: M_Shipper_ID
-- 2025-11-06T16:25:38.519Z
UPDATE AD_Process_Para SET FieldLength=10,Updated=TO_TIMESTAMP('2025-11-06 16:25:38.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543022
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- ParameterName: Carrier_Goods_Type_ID
-- 2025-11-06T16:26:01.567Z
UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2025-11-06 16:26:01.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543024
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- ParameterName: Carrier_Service_ID
-- 2025-11-06T16:26:13.484Z
UPDATE AD_Process_Para SET AD_Reference_ID=30, FieldLength=10,Updated=TO_TIMESTAMP('2025-11-06 16:26:13.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543025
;

-- Name: Carrier_Service_ID_for_M_Shipper_ID
-- 2025-11-06T16:35:56.722Z
UPDATE AD_Val_Rule SET Code='Carrier_Service_ID IN (SELECT cs.Carrier_Service_ID FROM Carrier_Service cs WHERE cs.M_Shipper_ID = @M_Shipper_ID/-1@)',Updated=TO_TIMESTAMP('2025-11-06 16:35:56.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540757
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- ParameterName: Carrier_Service_ID2
-- 2025-11-07T07:03:07.342Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584113,0,585524,543027,30,542018,540757,'Carrier_Service_ID2',TO_TIMESTAMP('2025-11-07 07:03:07.224000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@Carrier_Service_ID/-1@>0','U',10,'Y','N','Y','N','N','N','Lieferweg-Servicekatalog',60,TO_TIMESTAMP('2025-11-07 07:03:07.224000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-07T07:03:07.345Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543027 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_ShipmentSchedule_Advise_Manual(de.metas.shipper.gateway.commons.webui.M_ShipmentSchedule_Advise_Manual)
-- ParameterName: Carrier_Service_ID3
-- 2025-11-07T07:03:25.029Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584113,0,585524,543028,30,542018,540757,'Carrier_Service_ID3',TO_TIMESTAMP('2025-11-07 07:03:24.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@Carrier_Service_ID2/-1@>0','U',10,'Y','N','Y','N','N','N','Lieferweg-Servicekatalog',70,TO_TIMESTAMP('2025-11-07 07:03:24.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-07T07:03:25.039Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543028 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: M_ShipmentSchedule_Advise_Schedule
-- Classname: de.metas.shipper.gateway.commons.process.M_ShipmentSchedule_Advise_Schedule
-- 2025-11-07T08:09:39.703Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,585526,'Y','de.metas.shipper.gateway.commons.process.M_ShipmentSchedule_Advise_Schedule','N',TO_TIMESTAMP('2025-11-07 08:09:39.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Lieferweg-Abfrage Bereitstellungsdatum','json','N','N','xls','Java',TO_TIMESTAMP('2025-11-07 08:09:39.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_ShipmentSchedule_Advise_Schedule')
;

-- 2025-11-07T08:09:39.714Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585526 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_ShipmentSchedule_Advise_Schedule(de.metas.shipper.gateway.commons.process.M_ShipmentSchedule_Advise_Schedule)
-- 2025-11-07T08:10:02.325Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Carrier Advise Preparation Date',Updated=TO_TIMESTAMP('2025-11-07 08:10:02.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585526
;

-- 2025-11-07T08:10:02.328Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_ShipmentSchedule_Advise_Schedule(de.metas.shipper.gateway.commons.process.M_ShipmentSchedule_Advise_Schedule)
-- 2025-11-07T08:10:02.865Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-07 08:10:02.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585526
;

-- Process: M_ShipmentSchedule_Advise_Schedule(de.metas.shipper.gateway.commons.process.M_ShipmentSchedule_Advise_Schedule)
-- 2025-11-07T08:10:03.654Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-07 08:10:03.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585526
;

-- 2025-11-07T08:17:20.575Z
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,1000000,585526,540024,550120,TO_TIMESTAMP('2025-11-07 08:17:20.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'0 6 * * *','de.metas.shipper.gateway.commons',0,'D','Y','N',7,'Daily Update of Carrier Shipment Advise for Preperation Date','N','P','C','NEW',100,TO_TIMESTAMP('2025-11-07 08:17:20.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
