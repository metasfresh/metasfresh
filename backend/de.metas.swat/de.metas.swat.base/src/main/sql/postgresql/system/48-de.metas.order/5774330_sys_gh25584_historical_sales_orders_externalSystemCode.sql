-- Run mode: SWING_CLIENT

-- 2025-10-23T14:43:16.409Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584145,0,'ExternalSystemCode',TO_TIMESTAMP('2025-10-23 14:43:16.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifier of the `ExternalSystem` record that tells where this record came from. This translates to ''ExternalSystem.value''','D','Y','ExternalSystemCode','ExternalSystemCode',TO_TIMESTAMP('2025-10-23 14:43:16.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:43:16.441Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584145 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: Historical_Sales_Orders_By_Ids_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: DataSource
-- 2025-10-23T14:44:09.282Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542964
;

-- 2025-10-23T14:44:09.297Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542964
;

-- Process: Historical_Sales_Orders_By_Ids_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: ExternalSystemCode
-- 2025-10-23T14:45:07.251Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584145,0,585484,543015,10,'ExternalSystemCode',TO_TIMESTAMP('2025-10-23 14:45:07.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifier of the `ExternalSystem` record that tells where this record came from. This translates to ''ExternalSystem.value''','D',40,'Y','N','Y','N','N','N','ExternalSystemCode',30,TO_TIMESTAMP('2025-10-23 14:45:07.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:45:07.251Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543015 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: Historical_Sales_Orders_By_Ids_JSON
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2025-10-23T14:46:11.097Z
UPDATE AD_Process SET JSONPath='/historical_sales_orders_json_v?or=(Order_ID.eq.@C_Order_ID/-1@,and(ExternalId.ilike.@ExternalId/-1@,ExternalSystemCode.ilike.@ExternalSystemCode/-1@))&limit=@Limit/2000@&offset=@Offset/0@',Updated=TO_TIMESTAMP('2025-10-23 14:46:11.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585484
;

-- Process: Historical_Shipments_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: DataSource
-- 2025-10-23T15:54:45.938Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542971
;

-- 2025-10-23T15:54:45.940Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542971
;

-- Process: Historical_Shipments_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: ExternalSystemCode
-- 2025-10-23T15:55:09.378Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584145,0,585488,543016,10,'ExternalSystemCode',TO_TIMESTAMP('2025-10-23 15:55:09.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifier of the `ExternalSystem` record that tells where this record came from. This translates to ''ExternalSystem.value''','D',40,'Y','N','Y','N','N','N','ExternalSystemCode',30,TO_TIMESTAMP('2025-10-23 15:55:09.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T15:55:09.378Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543016 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: Historical_Shipments_JSON
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2025-10-23T15:55:34.025Z
UPDATE AD_Process SET JSONPath='historical_m_inout_json_v?and=(ExternalSystemCode.ilike.@ExternalSystemCode/%%@,or(Updated.gte.@UpdatedGE/9999-01-01T00:00:00@,ExternalId.eq.@ExternalId/-1@))',Updated=TO_TIMESTAMP('2025-10-23 15:55:34.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585488
;

-- Process: Historical_Invoices_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: DataSource
-- 2025-10-23T16:03:20.878Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542968
;

-- 2025-10-23T16:03:20.886Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542968
;

-- Process: Historical_Invoices_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: ExternalSystemCode
-- 2025-10-23T16:03:39.703Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584145,0,585485,543017,10,'ExternalSystemCode',TO_TIMESTAMP('2025-10-23 16:03:39.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifier of the `ExternalSystem` record that tells where this record came from. This translates to ''ExternalSystem.value''','D',40,'Y','N','Y','N','N','N','ExternalSystemCode',30,TO_TIMESTAMP('2025-10-23 16:03:39.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T16:03:39.703Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543017 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: Historical_Invoices_JSON
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2025-10-23T16:03:56.182Z
UPDATE AD_Process SET JSONPath='historical_invoices_json_v?and=(ExternalSystemCode.ilike.@ExternalSystemCode/%%@,or(Updated.gte.@UpdatedGE/9999-01-01T00:00:00@,ExternalId.eq.@ExternalId/-1@))',Updated=TO_TIMESTAMP('2025-10-23 16:03:56.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585485
;

