-- Run mode: SWING_CLIENT

-- Value: C_Doc_Outbound_Log_Selection_Export_JSON
-- Classname: de.metas.edi.process.export.json.C_Doc_Outbound_Log_Selection_Export_JSON
-- 2025-05-26T11:08:09.973Z
UPDATE AD_Process SET TechnicalNote='Leans on C_Invoice_EDI_Export_JSON to generate the actual JSON',Updated=TO_TIMESTAMP('2025-05-26 11:08:09.970000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585469
;

-- Value: C_Invoice_Selection_Export_JSON
-- Classname: de.metas.edi.process.export.json.C_Invoice_Selection_Export_JSON
-- 2025-05-26T11:08:18.513Z
UPDATE AD_Process SET TechnicalNote='Leans on C_Invoice_EDI_Export_JSON to generate the actual JSON',Updated=TO_TIMESTAMP('2025-05-26 11:08:18.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585470
;

-- Value: M_InOut_EDI_Export_JSON
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2025-05-26T11:23:40.563Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JSONPath,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,585473,'Y','de.metas.postgrest.process.PostgRESTProcessExecutor','N',TO_TIMESTAMP('2025-05-26 11:23:40.417000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y','N','N','N','Y','N','N','N','N','Y','N','Y','m_inout_export_edi_desadv_json_v?select=data&m_inout_id=eq.@C_Invoice_ID/0@',0,'Lieferung als JSON exportieren (postgREST)','json','N','N','xls','PostgREST',TO_TIMESTAMP('2025-05-26 11:23:40.417000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOut_EDI_Export_JSON')
;

-- 2025-05-26T11:23:40.566Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585473 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_InOut_EDI_Export_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: M_InOut_ID
-- 2025-05-26T11:24:25.418Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1025,0,585473,542958,19,'M_InOut_ID',TO_TIMESTAMP('2025-05-26 11:24:25.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Material Shipment Document','de.metas.esb.edi',0,'The Material Shipment / Receipt ','Y','N','Y','N','Y','N','Lieferung/Wareneingang',10,TO_TIMESTAMP('2025-05-26 11:24:25.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-26T11:24:25.422Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542958 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_InOut_EDI_Export_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: M_InOut_ID
-- 2025-05-26T11:24:48.620Z
UPDATE AD_Process_Para SET DefaultValue='@M_InOut_ID/0@',Updated=TO_TIMESTAMP('2025-05-26 11:24:48.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542958
;

