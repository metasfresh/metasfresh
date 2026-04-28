-- Run mode: SWING_CLIENT

-- Value: M_InOut_EDI_Export_JSON
-- Classname: de.metas.edi.process.export.json.M_InOut_EDI_Export_JSON
-- 2025-05-26T11:51:33.803Z
UPDATE AD_Process SET Classname='de.metas.edi.process.export.json.M_InOut_EDI_Export_JSON',Updated=TO_TIMESTAMP('2025-05-26 11:51:33.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585473
;

-- Value: M_InOut_EDI_Export_JSON
-- Classname: de.metas.edi.process.export.json.M_InOut_EDI_Export_JSON
-- 2025-05-26T11:51:47.029Z
UPDATE AD_Process SET JSONPath='m_inout_export_edi_desadv_json_v?select=embedded_json->metasfresh_DESADV&m_inout_id=eq.@M_InOut_ID/0@',Updated=TO_TIMESTAMP('2025-05-26 11:51:47.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585473
;

-- Value: de.metas.edi.process.export.json.M_InOut_Selection_Export_JSON_ProcessingError
-- 2025-05-26T11:57:23.927Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545551,0,TO_TIMESTAMP('2025-05-26 11:57:23.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y','Es wurden {0} Lieferungen exportiert, {1} davon mit Fehler.','E',TO_TIMESTAMP('2025-05-26 11:57:23.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.edi.process.export.json.M_InOut_Selection_Export_JSON_ProcessingError')
;

-- 2025-05-26T11:57:23.932Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545551 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.edi.process.export.json.M_InOut_Selection_Export_JSON_ProcessingError
-- 2025-05-26T11:57:29.149Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-26 11:57:29.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545551
;

-- Value: de.metas.edi.process.export.json.M_InOut_Selection_Export_JSON_ProcessingError
-- 2025-05-26T11:57:34.050Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-26 11:57:34.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545551
;

-- Value: de.metas.edi.process.export.json.M_InOut_Selection_Export_JSON_ProcessingError
-- 2025-05-26T11:57:56.426Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='There were {0} shipments exported, {1} of them with an error.',Updated=TO_TIMESTAMP('2025-05-26 11:57:56.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545551
;

-- 2025-05-26T11:57:56.428Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.edi.process.export.json.M_InOut_Selection_Export_JSON_InOutNotReady
-- 2025-05-26T11:59:05.680Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545552,0,TO_TIMESTAMP('2025-05-26 11:59:05.531000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y','Lieferung muss fertiggestellt, "Soll per EDI übermittelt werden"=Ja und den EDI-Status "Noch nicht gesendet" haben.','I',TO_TIMESTAMP('2025-05-26 11:59:05.531000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.edi.process.export.json.M_InOut_Selection_Export_JSON_InOutNotReady')
;

-- 2025-05-26T11:59:05.681Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545552 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.edi.process.export.json.M_InOut_Selection_Export_JSON_InOutNotReady
-- 2025-05-26T11:59:12.320Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-26 11:59:12.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545552
;

-- Value: de.metas.edi.process.export.json.M_InOut_Selection_Export_JSON_InOutNotReady
-- 2025-05-26T11:59:16.720Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-26 11:59:16.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545552
;

-- Value: de.metas.edi.process.export.json.C_Invoice_Selection_Export_JSON_InvoiceNotReady
-- 2025-05-26T11:59:40.833Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Invoice must be completed, “To be transmitted via EDI”=Yes and have the EDI status “Not yet sent”.',Updated=TO_TIMESTAMP('2025-05-26 11:59:40.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545549
;

-- 2025-05-26T11:59:40.835Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.edi.process.export.json.M_InOut_Selection_Export_JSON_InOutNotReady
-- 2025-05-26T11:59:52.732Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Shipment must be completed, “To be transmitted via EDI”=Yes and have the EDI status “Not yet sent”.',Updated=TO_TIMESTAMP('2025-05-26 11:59:52.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545552
;

-- 2025-05-26T11:59:52.733Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: M_InOut_Selection_Export_JSON
-- Classname: de.metas.edi.process.export.json.M_InOut_Selection_Export_JSON
-- 2025-05-26T12:01:09.481Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,585474,'Y','de.metas.edi.process.export.json.M_InOut_Selection_Export_JSON','N',TO_TIMESTAMP('2025-05-26 12:01:09.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ausgewählte EDI-Lieferungen werden as EDI-JSON exportiert, sofern sie fertiggestellt sind.
Sonstige Datensätze werden übersprungen.','de.metas.esb.edi','Y','N','N','N','N','N','N','N','N','Y','N','Y',0,'Auswahl als EDI-JSON Exportieren','json','N','N','xls','Leans on M_InOut_EDI_Export_JSON to genrate the actual JSON','Java',TO_TIMESTAMP('2025-05-26 12:01:09.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOut_Selection_Export_JSON')
;

-- 2025-05-26T12:01:09.483Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585474 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_InOut_Selection_Export_JSON(de.metas.edi.process.export.json.M_InOut_Selection_Export_JSON)
-- Table: M_InOut
-- Window: Lieferung(541874,D)
-- EntityType: de.metas.esb.edi
-- 2025-05-26T12:02:24.696Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585474,319,541558,169,TO_TIMESTAMP('2025-05-26 12:02:24.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2025-05-26 12:02:24.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

