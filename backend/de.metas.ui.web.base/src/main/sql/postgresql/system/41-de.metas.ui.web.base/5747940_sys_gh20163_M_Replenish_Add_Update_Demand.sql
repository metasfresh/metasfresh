-- Run mode: SWING_CLIENT

-- Value: WEBUI_M_Replenish_Add_Update_Demand
-- Classname: de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand
-- 2025-02-26T18:10:42.808Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585453,'Y','de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand','N',TO_TIMESTAMP('2025-02-26 18:10:42.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Nachfrage einstellen','json','N','N','xls','Java',TO_TIMESTAMP('2025-02-26 18:10:42.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'WEBUI_M_Replenish_Add_Update_Demand')
;

-- 2025-02-26T18:10:42.814Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585453 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- 2025-02-26T18:10:49.998Z
UPDATE AD_Process_Trl SET Name='Set Demand',Updated=TO_TIMESTAMP('2025-02-26 18:10:49.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585453
;

-- 2025-02-26T18:10:49.999Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: M_Product_ID
-- 2025-02-26T18:13:46.383Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,585453,542923,30,'M_Product_ID',TO_TIMESTAMP('2025-02-26 18:13:46.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@M_Product_ID@','Produkt, Leistung, Artikel','U',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','Y','N','Produkt','@M_Product_ID/0@>0',10,TO_TIMESTAMP('2025-02-26 18:13:46.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-26T18:13:46.387Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542923 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- Table: M_Material_Needs_Planner_V
-- Window: Wiederauffüllung(541869,D)
-- EntityType: D
-- 2025-02-26T18:15:01.502Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585453,542466,541545,541869,TO_TIMESTAMP('2025-02-26 18:15:01.317000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-02-26 18:15:01.317000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Y','Y','Y')
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: M_Product_ID
-- 2025-02-26T18:29:08.277Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2025-02-26 18:29:08.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542923
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: M_Product_ID
-- 2025-02-26T18:32:47.782Z
UPDATE AD_Process_Para SET DefaultValue='', ReadOnlyLogic='1=0',Updated=TO_TIMESTAMP('2025-02-26 18:32:47.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542923
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: M_Product_ID
-- 2025-02-26T18:33:09.263Z
UPDATE AD_Process_Para SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2025-02-26 18:33:09.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542923
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: M_Product_ID
-- 2025-02-26T18:46:17.378Z
UPDATE AD_Process_Para SET ReadOnlyLogic='@M_Product_ID@ > 0',Updated=TO_TIMESTAMP('2025-02-26 18:46:17.378000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542923
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: M_Warehouse_ID
-- 2025-02-26T18:49:23.639Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,459,0,585453,542924,30,'M_Warehouse_ID',TO_TIMESTAMP('2025-02-26 18:49:22.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung','D',0,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','Y','N','Lager','@M_Warehouse_ID@ > 0',20,TO_TIMESTAMP('2025-02-26 18:49:22.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-26T18:49:23.641Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542924 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: Demand
-- 2025-02-26T18:50:45.889Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583509,0,585453,542925,12,'Demand',TO_TIMESTAMP('2025-02-26 18:50:45.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',0,'Y','N','Y','N','Y','N','Nachfrage',30,TO_TIMESTAMP('2025-02-26 18:50:45.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-26T18:50:45.891Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542925 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Run mode: SWING_CLIENT

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: M_Warehouse_ID
-- 2025-02-26T19:48:08.292Z
UPDATE AD_Process_Para SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2025-02-26 19:48:08.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542924
;
