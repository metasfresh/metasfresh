-- Run mode: SWING_CLIENT

-- Value: EDI_Desadv_Close
-- Classname: de.metas.edi.process.EDI_Desadv_Close
-- 2026-02-27T08:47:15.209Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585585,'Y','de.metas.edi.process.EDI_Desadv_Close','N',TO_TIMESTAMP('2026-02-27 08:47:15.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kann verwendet werden, wenn die DESADV nicht vollständig geliefert wird. Der EdiExportStatus wird wenn mindestens eine Lieferung gesendet wurde auf gesendet gesetzt, ansonsten auf soll nicht gesendet werden.','de.metas.esb.edi','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Schließen','json','N','N','xls','Java',TO_TIMESTAMP('2026-02-27 08:47:15.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EDI_Desadv_Close')
;

-- 2026-02-27T08:47:15.225Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585585 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: EDI_Desadv_Close(de.metas.edi.process.EDI_Desadv_Close)
-- 2026-02-27T08:47:33.781Z
UPDATE AD_Process_Trl SET Description='Can be used if the DESADV is not delivered in full. The EdiExportStatus is set to sent if at least one delivery has been sent, otherwise it is set to should not be sent.', IsTranslated='Y', Name='Close',Updated=TO_TIMESTAMP('2026-02-27 08:47:33.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585585
;

-- 2026-02-27T08:47:33.784Z
UPDATE AD_Process base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: EDI_Desadv_Close(de.metas.edi.process.EDI_Desadv_Close)
-- 2026-02-27T08:47:34.638Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-27 08:47:34.638000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585585
;

-- Process: EDI_Desadv_Close(de.metas.edi.process.EDI_Desadv_Close)
-- 2026-02-27T08:47:35.753Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-27 08:47:35.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585585
;

-- Process: EDI_Desadv_Close(de.metas.edi.process.EDI_Desadv_Close)
-- Table: EDI_Desadv
-- EntityType: de.metas.esb.edi
-- 2026-02-27T08:52:12.867Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585585,540644,541624,TO_TIMESTAMP('2026-02-27 08:52:12.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2026-02-27 08:52:12.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Value: EDI_Desadv_Close_UnresolvedInOuts
-- 2026-02-27T08:49:54.963Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545637,0,TO_TIMESTAMP('2026-02-27 08:49:54.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y','DESADV kann nicht geschlossen werden — die folgenden Lieferungen erfordern noch eine Entscheidung. Bitte lösen Sie jede Lieferung einzeln über den Prozess "EDI-Exportstatus ändern" (auf "Soll nicht gesendet werden" setzen oder den Fehler beheben und erneut senden): {}','E',TO_TIMESTAMP('2026-02-27 08:49:54.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EDI_Desadv_Close_UnresolvedInOuts')
;

-- 2026-02-27T08:49:54.969Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545637 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: EDI_Desadv_Close_UnresolvedInOuts
-- 2026-02-27T08:50:39.018Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Cannot close DESADV — the following shipments still require a decision. Please resolve each shipment individually first using the "Change EDI Export Status" process (set to DontSend or fix the error and resend): {}',Updated=TO_TIMESTAMP('2026-02-27 08:50:39.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545637
;

-- 2026-02-27T08:50:39.020Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: EDI_Desadv_Close_UnresolvedInOuts
-- 2026-02-27T08:50:40.962Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-27 08:50:40.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545637
;

-- Value: EDI_Desadv_Close_UnresolvedInOuts
-- 2026-02-27T08:50:42.557Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-27 08:50:42.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545637
;

-- Value: EDI_Desadv_Close_UnresolvedInOuts
-- 2026-02-27T13:12:55.004Z
UPDATE AD_Message SET ErrorCode='EDI_Desadv_Close_UnresolvedInOuts',Updated=TO_TIMESTAMP('2026-02-27 13:12:55.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545637
;
