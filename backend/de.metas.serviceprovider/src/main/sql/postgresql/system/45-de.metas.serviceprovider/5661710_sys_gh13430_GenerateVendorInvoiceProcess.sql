-- Value: GenerateVendorInvoiceProcess
-- Classname: de.metas.serviceprovider.effortcontrol.process.GenerateVendorInvoiceProcess
-- 2022-10-25T05:25:27.325Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585136,'Y','de.metas.serviceprovider.effortcontrol.process.GenerateVendorInvoiceProcess','N',TO_TIMESTAMP('2022-10-25 08:25:26.945','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'GenerateVendorInvoiceProcess','json','N','N','xls','Java',TO_TIMESTAMP('2022-10-25 08:25:26.945','YYYY-MM-DD HH24:MI:SS.US'),100,'GenerateVendorInvoiceProcess')
;

-- 2022-10-25T05:25:27.328Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585136 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: GenerateVendorInvoiceProcess(de.metas.serviceprovider.effortcontrol.process.GenerateVendorInvoiceProcess)
-- Table: S_EffortControl
-- Window: Effort control(541615,de.metas.serviceprovider)
-- EntityType: D
-- 2022-10-25T05:52:18.370Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585136,542215,541298,541615,TO_TIMESTAMP('2022-10-25 08:52:18.103','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2022-10-25 08:52:18.103','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;


-- Process: GenerateVendorInvoiceProcess(de.metas.serviceprovider.effortcontrol.process.GenerateVendorInvoiceProcess)
-- 2022-10-25T11:43:36.238Z
UPDATE AD_Process_Trl SET Name='Interne Lieferantenrechnung bearbeiten',Updated=TO_TIMESTAMP('2022-10-25 14:43:36.237','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585136
;

-- Value: GenerateVendorInvoiceProcess
-- Classname: de.metas.serviceprovider.effortcontrol.process.GenerateVendorInvoiceProcess
-- 2022-10-25T11:43:38.724Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Interne Lieferantenrechnung bearbeiten',Updated=TO_TIMESTAMP('2022-10-25 14:43:38.724','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585136
;

-- Process: GenerateVendorInvoiceProcess(de.metas.serviceprovider.effortcontrol.process.GenerateVendorInvoiceProcess)
-- 2022-10-25T11:43:38.719Z
UPDATE AD_Process_Trl SET Name='Interne Lieferantenrechnung bearbeiten',Updated=TO_TIMESTAMP('2022-10-25 14:43:38.719','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585136
;

-- Process: GenerateVendorInvoiceProcess(de.metas.serviceprovider.effortcontrol.process.GenerateVendorInvoiceProcess)
-- 2022-10-25T11:43:43.445Z
UPDATE AD_Process_Trl SET Name='Interne Lieferantenrechnung bearbeiten',Updated=TO_TIMESTAMP('2022-10-25 14:43:43.444','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585136
;

-- Process: GenerateVendorInvoiceProcess(de.metas.serviceprovider.effortcontrol.process.GenerateVendorInvoiceProcess)
-- 2022-10-25T11:43:46.123Z
UPDATE AD_Process_Trl SET Name='Interne Lieferantenrechnung bearbeiten',Updated=TO_TIMESTAMP('2022-10-25 14:43:46.123','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585136
;

-- Process: GenerateVendorInvoiceProcess(de.metas.serviceprovider.effortcontrol.process.GenerateVendorInvoiceProcess)
-- 2022-10-25T11:44:00.616Z
UPDATE AD_Process_Trl SET Name='Process internal vendor invoice',Updated=TO_TIMESTAMP('2022-10-25 14:44:00.615','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585136
;

