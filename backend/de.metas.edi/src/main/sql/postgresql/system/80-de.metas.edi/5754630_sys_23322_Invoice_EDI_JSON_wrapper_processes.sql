-- Run mode: SWING_CLIENT


-- Value: C_Invoice_EDI_Export_JSON
-- Classname: de.metas.edi.process.export.json.C_Invoice_EDI_Export_JSON
-- 2025-05-14T11:56:17.067Z
UPDATE AD_Process SET Classname='de.metas.edi.process.export.json.C_Invoice_EDI_Export_JSON',Updated=TO_TIMESTAMP('2025-05-14 11:56:17.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585460
;


-- Value: C_Invoice_EDI_Export_JSON
-- Classname: de.metas.edi.process.export.json.C_Invoice_EDI_Export_JSON
-- 2025-05-14T14:58:36.839Z
UPDATE AD_Process SET Classname='de.metas.edi.process.export.json.C_Invoice_EDI_Export_JSON',Updated=TO_TIMESTAMP('2025-05-14 14:58:36.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585460
;

-- Process: C_Invoice_EDI_Export_JSON(de.metas.edi.process.export.json.C_Invoice_EDI_Export_JSON)
-- Table: C_Invoice
-- Window: Rechnung(167,D)
-- EntityType: de.metas.esb.edi
-- 2025-05-14T14:58:48.171Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541554
;

-- Value: C_Invoice_EDI_Export_JSON
-- Classname: de.metas.edi.process.export.json.C_Invoice_EDI_Export_JSON
-- 2025-05-14T14:59:07.998Z
UPDATE AD_Process SET Name='Rechnung als JSON exportieren (postgREST)',Updated=TO_TIMESTAMP('2025-05-14 14:59:07.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585460
;

-- 2025-05-14T14:59:07.999Z
UPDATE AD_Process_Trl trl SET Name='Rechnung als JSON exportieren (postgREST)' WHERE AD_Process_ID=585460 AND AD_Language='de_DE'
;

-- Value: C_Doc_Outbound_Log_Selection_Export_JSON
-- Classname: de.metas.edi.process.export.json.C_Doc_Outbound_Log_Selection_Export_JSON
-- 2025-05-14T15:02:11.422Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,585469,'Y','de.metas.edi.process.export.json.C_Doc_Outbound_Log_Selection_Export_JSON','N',TO_TIMESTAMP('2025-05-14 15:02:11.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ausgewählte EDI-Rechnungen werden as EDI-JSON exportiert, sofern sie fertiggestellt sind.','de.metas.esb.edi','Y','N','N','N','N','N','N','N','N','Y','N','Y',0,'Auswahl als EDI-JSON Exportieren','json','N','N','xls','Java',TO_TIMESTAMP('2025-05-14 15:02:11.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_Doc_Outbound_Log_Selection_Export_JSON')
;

-- 2025-05-14T15:02:11.427Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585469 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Doc_Outbound_Log_Selection_Export_JSON(de.metas.edi.process.export.json.C_Doc_Outbound_Log_Selection_Export_JSON)
-- 2025-05-14T15:02:18.272Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-14 15:02:18.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585469
;

-- Process: C_Doc_Outbound_Log_Selection_Export_JSON(de.metas.edi.process.export.json.C_Doc_Outbound_Log_Selection_Export_JSON)
-- 2025-05-14T15:02:21.163Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-14 15:02:21.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585469
;

-- Process: C_Doc_Outbound_Log_Selection_Export_JSON(de.metas.edi.process.export.json.C_Doc_Outbound_Log_Selection_Export_JSON)
-- 2025-05-14T15:02:42.495Z
UPDATE AD_Process_Trl SET Description='', Name='Export selection as EDI-JSON',Updated=TO_TIMESTAMP('2025-05-14 15:02:42.495000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585469
;

-- 2025-05-14T15:02:42.496Z
UPDATE AD_Process base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: C_Doc_Outbound_Log_Selection_Export_JSON(de.metas.edi.process.export.json.C_Doc_Outbound_Log_Selection_Export_JSON)
-- 2025-05-14T15:02:45.426Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-14 15:02:45.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585469
;

-- Value: C_Doc_Outbound_Log_Selection_Export_JSON
-- Classname: de.metas.edi.process.export.json.C_Doc_Outbound_Log_Selection_Export_JSON
-- 2025-05-14T15:03:03.201Z
UPDATE AD_Process SET Description='Ausgewählte EDI-Rechnungen werden as EDI-JSON exportiert, sofern sie fertiggestellt sind.
Sonstige Datensätze werden übersprungen.',Updated=TO_TIMESTAMP('2025-05-14 15:03:03.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585469
;

-- 2025-05-14T15:03:03.203Z
UPDATE AD_Process_Trl trl SET Description='Ausgewählte EDI-Rechnungen werden as EDI-JSON exportiert, sofern sie fertiggestellt sind.
Sonstige Datensätze werden übersprungen.' WHERE AD_Process_ID=585469 AND AD_Language='de_DE'
;

-- Process: C_Doc_Outbound_Log_Selection_Export_JSON(de.metas.edi.process.export.json.C_Doc_Outbound_Log_Selection_Export_JSON)
-- 2025-05-14T15:03:12.482Z
UPDATE AD_Process_Trl SET Description='Ausgewählte EDI-Rechnungen werden as EDI-JSON exportiert, sofern sie fertiggestellt sind.
Sonstige Datensätze werden übersprungen.',Updated=TO_TIMESTAMP('2025-05-14 15:03:12.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585469
;

-- 2025-05-14T15:03:12.483Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: C_Doc_Outbound_Log_Selection_Export_JSON(de.metas.edi.process.export.json.C_Doc_Outbound_Log_Selection_Export_JSON)
-- Table: C_Doc_Outbound_Log
-- EntityType: de.metas.esb.edi
-- 2025-05-14T15:04:22.640Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585469,540453,541555,TO_TIMESTAMP('2025-05-14 15:04:22.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2025-05-14 15:04:22.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Y','Y','N')
;

-- Value: C_Invoice_Selection_Export_JSON
-- Classname: de.metas.edi.process.export.json.C_Invoice_Selection_Export_JSON
-- 2025-05-15T09:30:26.879Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,585470,'Y','de.metas.edi.process.export.json.C_Invoice_Selection_Export_JSON','N',TO_TIMESTAMP('2025-05-15 09:30:26.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ausgewählte EDI-Rechnungen werden as EDI-JSON exportiert, sofern sie fertiggestellt sind.
Sonstige Datensätze werden übersprungen.','de.metas.esb.edi','Y','N','N','N','N','N','N','N','N','Y','N','Y',0,'Auswahl als EDI-JSON Exportieren','json','N','N','xls','Java',TO_TIMESTAMP('2025-05-15 09:30:26.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_Invoice_Selection_Export_JSON')
;

-- 2025-05-15T09:30:26.885Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585470 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Invoice_Selection_Export_JSON(de.metas.edi.process.export.json.C_Invoice_Selection_Export_JSON)
-- Table: C_Invoice
-- EntityType: de.metas.esb.edi
-- 2025-05-15T09:31:20.059Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585470,318,541556,TO_TIMESTAMP('2025-05-15 09:31:19.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2025-05-15 09:31:19.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

