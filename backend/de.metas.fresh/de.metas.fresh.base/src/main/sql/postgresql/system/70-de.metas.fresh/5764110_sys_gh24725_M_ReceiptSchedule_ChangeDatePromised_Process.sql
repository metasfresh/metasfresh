-- Run mode: SWING_CLIENT

-- Value: M_ReceiptSchedule_ChangeDatePromised
-- Classname: de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised
-- 2025-08-27T09:05:36.947Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585492,'N','de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised',TO_TIMESTAMP('2025-08-27 09:05:36.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','Y','N','Y','N','N','N','N','Y','N','Y','Zug. Termin ändern','json','Y','N','Java',TO_TIMESTAMP('2025-08-27 09:05:36.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_ReceiptSchedule_ChangeDatePromised')
;

-- 2025-08-27T09:05:37.020Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585492 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_ReceiptSchedule_ChangeDatePromised(de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised)
-- 2025-08-27T09:06:23.795Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Change Date Promised',Updated=TO_TIMESTAMP('2025-08-27 09:06:23.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585492
;

-- 2025-08-27T09:06:23.864Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_ReceiptSchedule_ChangeDatePromised(de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised)
-- ParameterName: DatePromised
-- 2025-08-27T09:08:13.853Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,269,0,585492,542972,16,'DatePromised',TO_TIMESTAMP('2025-08-27 09:08:13.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','U',0,'Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','Y','N','Y','N','Zugesagter Termin',10,TO_TIMESTAMP('2025-08-27 09:08:13.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-27T09:08:13.927Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542972 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_ReceiptSchedule_ChangeDatePromised(de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised)
-- Table: M_ReceiptSchedule
-- EntityType: D
-- 2025-08-27T09:12:13.798Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585492,540524,541564,TO_TIMESTAMP('2025-08-27 09:12:12.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-08-27 09:12:12.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Y','Y','N')
;

-- Process: M_ReceiptSchedule_ChangeDatePromised(de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised)
-- Table: M_ReceiptSchedule
-- EntityType: D
-- 2025-08-27T09:12:50.084Z
UPDATE AD_Table_Process SET WEBUI_DocumentAction='Y',Updated=TO_TIMESTAMP('2025-08-27 09:12:50.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541564
;

-- Value: M_ReceiptSchedule_ChangeDatePromised
-- Classname: de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised
-- 2025-08-27T09:20:23.320Z
UPDATE AD_Process SET AccessLevel='7',Updated=TO_TIMESTAMP('2025-08-27 09:20:23.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585492
;

