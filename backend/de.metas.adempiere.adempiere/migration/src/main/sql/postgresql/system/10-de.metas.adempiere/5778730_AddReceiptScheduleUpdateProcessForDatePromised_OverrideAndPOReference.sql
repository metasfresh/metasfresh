-- Run mode: SWING_CLIENT

-- Value: M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference
-- 2025-11-26T18:31:49.829Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585538,'Y','N',TO_TIMESTAMP('2025-11-26 18:31:48.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Change Date Promised Override and POReference','json','N','N','xls','Java',TO_TIMESTAMP('2025-11-26 18:31:48.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference')
;

-- 2025-11-26T18:31:49.844Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585538 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference
-- 2025-11-26T18:32:10.344Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zugesagten Termin und Referenz ändern',Updated=TO_TIMESTAMP('2025-11-26 18:32:10.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585538
;

-- 2025-11-26T18:32:10.346Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference
-- Classname: de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference
-- 2025-11-26T18:37:09.698Z
UPDATE AD_Process SET Classname='de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference',Updated=TO_TIMESTAMP('2025-11-26 18:37:09.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585538
;



-- Process: M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference(de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference)
-- 2025-11-26T18:37:43.406Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zugesagten Termin und Referenz ändern', Updated=TO_TIMESTAMP('2025-11-26 18:37:43.406000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585538
;


-- Process: M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference(de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference)
-- ParameterName: DatePromised_Override
-- 2025-11-26T18:40:24.470Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542184,0,585538,543058,15,'DatePromised_Override',TO_TIMESTAMP('2025-11-26 18:40:24.318000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@#Date@','Zugesagter Termin für diesen Auftrag','de.metas.swat',0,'Y','N','Y','N','N','N','Zugesagter Termin abw.',10,TO_TIMESTAMP('2025-11-26 18:40:24.318000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-26T18:40:24.480Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543058 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference(de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference)
-- ParameterName: POReference
-- 2025-11-26T18:40:45.168Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,952,0,585538,543059,10,'POReference',TO_TIMESTAMP('2025-11-26 18:40:45.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Referenz-Nummer des Kunden','U',0,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','N','N','N','Referenz',20,TO_TIMESTAMP('2025-11-26 18:40:45.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-26T18:40:45.172Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543059 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference(de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference)
-- ParameterName: POReference
-- 2025-11-26T18:40:48.629Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-26 18:40:48.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543059
;

-- Process: M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference(de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference)
-- Table: M_ReceiptSchedule
-- EntityType: D
-- 2025-11-26T18:41:43.065Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585538,540524,541590,TO_TIMESTAMP('2025-11-26 18:41:42.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-11-26 18:41:42.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N')
;



---


-- Value: receiptschedule.ChangeDatePromised_OverrideAndPOReference.paramsValidationError
-- 2025-11-27T15:52:31.509Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545617,0,TO_TIMESTAMP('2025-11-27 15:52:31.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','At least one of “Date Promised Override” or “PO Reference” must be provided.','E',TO_TIMESTAMP('2025-11-27 15:52:31.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'receiptschedule.ChangeDatePromised_OverrideAndPOReference.paramsValidationError')
;

-- 2025-11-27T15:52:31.518Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545617 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: receiptschedule.ChangeDatePromised_OverrideAndPOReference.paramsValidationError
-- 2025-11-27T15:52:49.709Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Mindestens eines von „Lieferdatum (Überschreibung)“ oder „Bestellreferenz“ muss angegeben werden.',Updated=TO_TIMESTAMP('2025-11-27 15:52:49.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545617
;

-- 2025-11-27T15:52:49.711Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: receiptschedule.ChangeDatePromised_OverrideAndPOReference.paramsValidationError
-- 2025-11-27T15:53:08.847Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-27 15:53:08.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545617
;

-- Value: receiptschedule.ChangeDatePromised_OverrideAndPOReference.paramsValidationError
-- 2025-11-27T15:53:13.496Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Mindestens eines von „Lieferdatum (Überschreibung)“ oder „Bestellreferenz“ muss angegeben werden.',Updated=TO_TIMESTAMP('2025-11-27 15:53:13.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545617
;

-- 2025-11-27T15:53:13.497Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

