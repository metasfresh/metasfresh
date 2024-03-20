-- Value: WEBUI_M_HU_CreateReceipt_MovementDateParam
-- Classname: de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_MovementDateParam
-- 2024-01-20T14:16:50.076Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585348,'N','de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_MovementDateParam','N',TO_TIMESTAMP('2024-01-20 16:16:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Wareneingang zu bestimmtem Datum erstellen','json','N','Y','xls','Java',TO_TIMESTAMP('2024-01-20 16:16:49','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_M_HU_CreateReceipt_MovementDateParam')
;

-- 2024-01-20T14:16:50.081Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585348 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: WEBUI_M_HU_CreateReceipt_MovementDateParam(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_MovementDateParam)
-- 2024-01-20T14:16:58.279Z
UPDATE AD_Process_Trl SET Name='Create material receipt for specific date',Updated=TO_TIMESTAMP('2024-01-20 16:16:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585348
;

-- Process: WEBUI_M_HU_CreateReceipt_MovementDateParam(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_MovementDateParam)
-- ParameterName: MovementDate
-- 2024-01-20T14:21:11.855Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1037,0,585348,542774,15,'MovementDate',TO_TIMESTAMP('2024-01-20 16:21:11','YYYY-MM-DD HH24:MI:SS'),100,'@#Date@','Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde','de.metas.ui.web',0,'"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.','Y','N','Y','N','Y','N','Bewegungsdatum',10,TO_TIMESTAMP('2024-01-20 16:21:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-20T14:21:11.858Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542774 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_M_HU_CreateReceipt_MovementDateParam(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_MovementDateParam)
-- Table: M_HU
-- EntityType: de.metas.ui.web
-- 2024-01-20T14:21:59.989Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585348,540516,541455,TO_TIMESTAMP('2024-01-20 16:21:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y',TO_TIMESTAMP('2024-01-20 16:21:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

