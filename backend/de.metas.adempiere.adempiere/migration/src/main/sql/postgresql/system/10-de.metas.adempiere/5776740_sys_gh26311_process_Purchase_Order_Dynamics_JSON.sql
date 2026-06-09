-- Run mode: SWING_CLIENT

-- Value: Purchase_Order_Dynamics_JSON
-- Classname: de.metas.postgrest.process.json.Purchase_Order_Dynamics_JSON
-- 2025-11-13T15:55:59.465Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JSONPath,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585528,'Y','de.metas.postgrest.process.json.Purchase_Order_Dynamics_JSON','N',TO_TIMESTAMP('2025-11-13 15:55:59.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y','rpc/purchase_order_dynamics_json?p_order_id=@C_Order_ID/-1@',0,'Dynamics-Bestellung JSON','json','N','N','xls','PostgREST',TO_TIMESTAMP('2025-11-13 15:55:59.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Purchase_Order_Dynamics_JSON')
;

-- 2025-11-13T15:55:59.471Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585528 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Purchase_Order_Dynamics_JSON(de.metas.postgrest.process.json.Purchase_Order_Dynamics_JSON)
-- ParameterName: C_Order_ID
-- 2025-11-13T15:57:05.869Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585528,543032,30,'C_Order_ID',TO_TIMESTAMP('2025-11-13 15:57:05.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',0,'Y','N','Y','N','N','N','Bestellung',10,TO_TIMESTAMP('2025-11-13 15:57:05.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-13T15:57:05.870Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543032 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-11-13T15:57:15.586Z
UPDATE AD_Process_Para_Trl SET Name='Purchase Order',Updated=TO_TIMESTAMP('2025-11-13 15:57:15.585000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=543032
;

-- 2025-11-13T15:57:15.586Z
UPDATE AD_Process_Para base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Para_Trl trl  WHERE trl.AD_Process_Para_ID=base.AD_Process_Para_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

