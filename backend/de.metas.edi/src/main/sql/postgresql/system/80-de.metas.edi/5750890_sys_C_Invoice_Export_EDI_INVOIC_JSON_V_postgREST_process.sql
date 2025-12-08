-- Run mode: SWING_CLIENT

--
-- Unrelated fix for an ugly stacktrace i had in the webapi
--
-- Field: Rechnung(541875,D) -> Rechnung(547938,D) -> Beleg soll per EDI übermittelt werden
-- Column: C_Invoice.IsEdiEnabled
-- 2025-04-03T14:07:34.912Z
UPDATE AD_Field SET DisplayLogic='@DocBaseType/NULL@=''ARC'' & @DocSubType/NULL@=''CS''',Updated=TO_TIMESTAMP('2025-04-03 14:07:34.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=741636
;

-- Value: C_Invoice_EDI_Json
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2025-04-03T14:23:56.399Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JSONPath,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,585460,'Y','de.metas.postgrest.process.PostgRESTProcessExecutor','N',TO_TIMESTAMP('2025-04-03 14:23:56.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y','N','N','N','Y','N','N','N','N','Y','N','Y','c_invoice_export_edi_invoic_json_v?select=embedded_json->metasfresh_INVOIC&c_invoice_id=eq.@RECORD_ID/0@',0,'Als JSON exportieren','json','N','N','xls','PostgREST',TO_TIMESTAMP('2025-04-03 14:23:56.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_Invoice_EDI_Json')
;

-- 2025-04-03T14:23:56.405Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585460 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Invoice_EDI_Json(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- Table: C_Invoice
-- Window: Rechnung(541875,D)
-- EntityType: de.metas.esb.edi
-- 2025-04-03T14:24:47.307Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585460,318,541550,541875,TO_TIMESTAMP('2025-04-03 14:24:47.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2025-04-03 14:24:47.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Process: C_Invoice_EDI_Json(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: C_Invoice_ID
-- 2025-04-03T14:33:28.961Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,1008,0,585460,542940,30,'C_Invoice_ID',TO_TIMESTAMP('2025-04-03 14:33:28.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@C_Invoice_ID/0@','Invoice Identifier','de.metas.esb.edi',0,'The Invoice Document.','Y','N','Y','N','Y','N','Rechnung','false',10,TO_TIMESTAMP('2025-04-03 14:33:28.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T14:33:28.966Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542940 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Invoice_EDI_Json(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: C_Invoice_ID
-- 2025-04-03T14:35:08.260Z
UPDATE AD_Process_Para SET DisplayLogic='0=1', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2025-04-03 14:35:08.259000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542940
;

-- Value: C_Invoice_EDI_Json
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2025-04-03T14:35:23.134Z
UPDATE AD_Process SET JSONPath='c_invoice_export_edi_invoic_json_v?select=embedded_json->metasfresh_INVOIC&c_invoice_id=eq.@C_INVOICE_ID/0@',Updated=TO_TIMESTAMP('2025-04-03 14:35:23.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585460
;

-- Value: C_Invoice_EDI_Json
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2025-04-03T14:37:16.207Z
UPDATE AD_Process SET JSONPath='c_invoice_export_edi_invoic_json_v?select=embedded_json->metasfresh_INVOIC&c_invoice_id=eq.@C_Invoice_ID/0@',Updated=TO_TIMESTAMP('2025-04-03 14:37:16.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585460
;

-- Process: C_Invoice_EDI_Json(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: C_Invoice_ID
-- 2025-04-03T14:37:26.829Z
UPDATE AD_Process_Para SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-04-03 14:37:26.829000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542940
;

-- Process: C_Invoice_EDI_Json(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- Table: C_Invoice
-- Window: Rechnung(541875,D)
-- EntityType: de.metas.esb.edi
-- 2025-04-03T14:41:31.604Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541550
;

-- Value: C_Invoice_EDI_Export_JSON
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2025-04-03T14:41:58.952Z
UPDATE AD_Process SET Name='Rechnung als JSON exportieren', Value='C_Invoice_EDI_Export_JSON',Updated=TO_TIMESTAMP('2025-04-03 14:41:58.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585460
;

-- 2025-04-03T14:41:58.964Z
UPDATE AD_Process_Trl trl SET Name='Rechnung als JSON exportieren' WHERE AD_Process_ID=585460 AND AD_Language='de_DE'
;

