-- Run mode: SWING_CLIENT

-- Value: Historical_Sales_Orders_By_Ids_JSON
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2025-08-05T09:02:28.975Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JSONPath,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585484,'Y','de.metas.postgrest.process.PostgRESTProcessExecutor','N',TO_TIMESTAMP('2025-08-05 09:02:28.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y','historical_sales_orders_json_v?Order_ID=in.@C_Order_ID/0@&ExternalId=ilike.@ExternalId/%',0,'Historische Aufträge','json','N','N','xls','PostgREST',TO_TIMESTAMP('2025-08-05 09:02:28.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Historical_Sales_Orders_By_Ids_JSON')
;

-- 2025-08-05T09:02:28.981Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585484 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Historical_Sales_Orders_By_Ids_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- 2025-08-05T09:04:01.510Z
UPDATE AD_Process_Trl SET Name='Historical Sales Orders',Updated=TO_TIMESTAMP('2025-08-05 09:04:01.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585484
;

-- 2025-08-05T09:04:01.511Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Historical_Sales_Orders_By_Ids_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: C_Order_ID
-- 2025-08-05T09:04:49.126Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,558,0,585484,542962,30,'C_Order_ID',TO_TIMESTAMP('2025-08-05 09:04:48.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag','D',0,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','Auftrag',10,TO_TIMESTAMP('2025-08-05 09:04:48.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-05T09:04:49.133Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542962 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Historical_Sales_Orders_By_Ids_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: ExternalId
-- 2025-08-05T09:05:54.294Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543939,0,585484,542963,10,'ExternalId',TO_TIMESTAMP('2025-08-05 09:05:54.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U',255,'Y','N','Y','N','N','N','Externe ID',20,TO_TIMESTAMP('2025-08-05 09:05:54.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-05T09:05:54.295Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542963 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Historical_Sales_Orders_By_Ids_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: ExternalId
-- 2025-08-05T09:06:38.519Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2025-08-05 09:06:38.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542963
;

-- Process: Historical_Sales_Orders_By_Ids_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: AD_InputDataSource_ID
-- 2025-08-05T13:34:33.215Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541291,0,585484,542964,30,'AD_InputDataSource_ID',TO_TIMESTAMP('2025-08-05 13:34:32.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',10,'Y','N','Y','N','N','N','Eingabequelle',30,TO_TIMESTAMP('2025-08-05 13:34:32.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-05T13:34:33.217Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542964 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Historical_Sales_Orders_By_Ids_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: C_Order_ID
-- 2025-08-05T13:34:39.138Z
UPDATE AD_Process_Para SET FieldLength=10,Updated=TO_TIMESTAMP('2025-08-05 13:34:39.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542962
;

-- 2025-08-06T07:59:23.239Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583850,0,'DataSource',TO_TIMESTAMP('2025-08-06 07:59:23.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifier of the `AD_InputDataSource` record that tells where this record came from. An identifier means int-<AD_InputDataSource.InternalName>','D','Y','DataSource','DataSource',TO_TIMESTAMP('2025-08-06 07:59:23.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-06T07:59:23.246Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583850 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: Historical_Sales_Orders_By_Ids_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: DataSource
-- 2025-08-06T08:00:29.968Z
UPDATE AD_Process_Para SET AD_Element_ID=583850, AD_Reference_ID=10, ColumnName='DataSource', Description='Identifier of the `AD_InputDataSource` record that tells where this record came from. An identifier means int-<AD_InputDataSource.InternalName>', FieldLength=150, Name='DataSource',Updated=TO_TIMESTAMP('2025-08-06 08:00:29.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542964
;

-- 2025-08-06T08:00:29.969Z
UPDATE AD_Process_Para_Trl trl SET Description='Identifier of the `AD_InputDataSource` record that tells where this record came from. An identifier means int-<AD_InputDataSource.InternalName>',Name='DataSource' WHERE AD_Process_Para_ID=542964 AND AD_Language='de_DE'
;

UPDATE AD_Process SET JSONPath='/historical_sales_orders_json_v?or=(Order_ID.eq.@C_Order_ID/-1@,and(ExternalId.ilike.@ExternalId/-1@,DataSource.eq.@DataSource/-1@))&limit=@Limit/2000@&offset=@Offset/0@',Updated=TO_TIMESTAMP('2025-08-05 10:01:01.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585484
;
