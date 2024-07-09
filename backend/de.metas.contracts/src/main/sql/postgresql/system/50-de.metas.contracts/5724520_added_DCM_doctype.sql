-- Run mode: SWING_CLIENT

-- Reference: Computing Methods
-- Value: ProForma
-- ValueName: ProForma
-- 2024-05-27T18:53:52.341Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=543664
;

-- 2024-05-27T18:53:52.345Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=543664
;

-- Reference: C_DocType SubType
-- Value: DS
-- ValueName: Definitive Schlussabrechnung
-- 2024-05-27T18:59:58.117Z
UPDATE AD_Ref_List SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-05-27 21:59:58.117','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543690
;

-- Reference: C_DocType SubType
-- Value: DCM
-- ValueName: DefinitiveCreditMemo
-- 2024-05-27T19:08:48.759Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,148,543691,TO_TIMESTAMP('2024-05-27 22:08:48.625','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Definitive Rechnungsgutschrift',TO_TIMESTAMP('2024-05-27 22:08:48.625','YYYY-MM-DD HH24:MI:SS.US'),100,'DCM','DefinitiveCreditMemo')
;

-- 2024-05-27T19:08:48.765Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543691 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: C_DocType SubType -> DCM_DefinitiveCreditMemo
-- 2024-05-27T19:11:40.347Z
UPDATE AD_Ref_List_Trl SET Name='Definitive Credit Memo',Updated=TO_TIMESTAMP('2024-05-27 22:11:40.347','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543691
;

-- 2024-05-27T19:13:54.022Z
UPDATE C_DocType SET DocSubType='DCM',Updated=TO_TIMESTAMP('2024-05-27 22:13:54.022','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541123
;

-- Value: C_ModularFinalInvoice
-- Classname: de.metas.contracts.finalinvoice.process.C_ModularFinalInvoice
-- 2024-05-27T19:30:50.444Z
UPDATE AD_Process SET Value='C_ModularFinalInvoice',Updated=TO_TIMESTAMP('2024-05-27 22:30:50.443','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585375
;

-- Value: C_ModularDefinitiveInvoice
-- Classname: de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice
-- 2024-05-27T19:33:17.262Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585396,'Y','de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice','N',TO_TIMESTAMP('2024-05-27 22:33:17.095','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Definitive Schlussrechnung erstellen','json','N','N','xls','Java',TO_TIMESTAMP('2024-05-27 22:33:17.095','YYYY-MM-DD HH24:MI:SS.US'),100,'C_ModularDefinitiveInvoice')
;

-- 2024-05-27T19:33:17.263Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585396 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- 2024-05-27T19:33:34.235Z
UPDATE AD_Process_Trl SET Name='Create definitive final invoice',Updated=TO_TIMESTAMP('2024-05-27 22:33:34.235','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585396
;

