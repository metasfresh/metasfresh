-- Run mode: SWING_CLIENT

-- Value: WEBUI_M_HU_CreateReceipt_MovementAndAcctDateParams
-- Classname: de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_MovementAndAcctDateParams
-- 2024-04-30T12:51:51.315Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585388,'N','de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_MovementAndAcctDateParams','N',TO_TIMESTAMP('2024-04-30 15:51:51.092','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.ui.web','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Wareneingang am Datum erst.','json','N','N','xls','Java',TO_TIMESTAMP('2024-04-30 15:51:51.092','YYYY-MM-DD HH24:MI:SS.US'),100,'WEBUI_M_HU_CreateReceipt_MovementAndAcctDateParams')
;

-- 2024-04-30T12:51:51.335Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585388 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: WEBUI_M_HU_CreateReceipt_MovementAndAcctDateParams(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_MovementAndAcctDateParams)
-- Table: M_HU
-- EntityType: de.metas.ui.web
-- 2024-04-30T12:52:28.067Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585388,540516,541485,TO_TIMESTAMP('2024-04-30 15:52:27.922','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.ui.web','Y',TO_TIMESTAMP('2024-04-30 15:52:27.922','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','Y','N')
;

-- Process: WEBUI_M_HU_CreateReceipt_MovementAndAcctDateParams(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_MovementAndAcctDateParams)
-- ParameterName: MovementDate
-- 2024-04-30T12:54:59.282Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1037,0,585388,542818,15,'MovementDate',TO_TIMESTAMP('2024-04-30 15:54:59.171','YYYY-MM-DD HH24:MI:SS.US'),100,'@#Date@','Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde','de.metas.ui.web',0,'"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.','Y','N','Y','N','Y','N','Bewegungsdatum',10,TO_TIMESTAMP('2024-04-30 15:54:59.171','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-30T12:54:59.286Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542818 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-30T12:54:59.321Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(1037)
;

-- Process: WEBUI_M_HU_CreateReceipt_MovementAndAcctDateParams(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_MovementAndAcctDateParams)
-- ParameterName: DateAcct
-- 2024-04-30T12:55:24.120Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,263,0,585388,542819,15,'DateAcct',TO_TIMESTAMP('2024-04-30 15:55:24.001','YYYY-MM-DD HH24:MI:SS.US'),100,'@#Date@','Accounting Date','de.metas.ui.web',0,'The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','Y','N','Y','N','Buchungsdatum',20,TO_TIMESTAMP('2024-04-30 15:55:24.001','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-30T12:55:24.121Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542819 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-30T12:55:24.122Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(263)
;

-- Run mode: SWING_CLIENT

-- Process: WEBUI_M_HU_CreateReceipt_MovementAndAcctDateParams(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_MovementAndAcctDateParams)
-- 2024-04-30T13:11:39.024Z
UPDATE AD_Process_Trl SET Name='Create material receipt with dates',Updated=TO_TIMESTAMP('2024-04-30 16:11:39.024','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585388
;

