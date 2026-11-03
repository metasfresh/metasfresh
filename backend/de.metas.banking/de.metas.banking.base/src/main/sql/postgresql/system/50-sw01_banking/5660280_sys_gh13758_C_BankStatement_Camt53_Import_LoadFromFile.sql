-- Value: C_BankStatement_Camt53_Import_LoadFromFile
-- Classname: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile
-- 2022-10-14T10:52:45.713Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585128,'N','de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile','N',TO_TIMESTAMP('2022-10-14 13:52:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Import Bank Statement File (camt53)','json','N','N','xls','Java',TO_TIMESTAMP('2022-10-14 13:52:45','YYYY-MM-DD HH24:MI:SS'),100,'C_BankStatement_Camt53_Import_LoadFromFile')
;

-- 2022-10-14T10:52:45.717Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585128 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_BankStatement_Camt53_Import_LoadFromFile(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile)
-- 2022-10-14T10:53:48.511Z
UPDATE AD_Process_Trl SET Name='Kontoauszugsdatei importieren (camt53)',Updated=TO_TIMESTAMP('2022-10-14 13:53:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585128
;

-- Value: C_BankStatement_Camt53_Import_LoadFromFile
-- Classname: de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile
-- 2022-10-14T10:53:50.033Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Kontoauszugsdatei importieren (camt53)',Updated=TO_TIMESTAMP('2022-10-14 13:53:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585128
;

-- Process: C_BankStatement_Camt53_Import_LoadFromFile(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile)
-- 2022-10-14T10:53:50.027Z
UPDATE AD_Process_Trl SET Name='Kontoauszugsdatei importieren (camt53)',Updated=TO_TIMESTAMP('2022-10-14 13:53:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585128
;

-- Process: C_BankStatement_Camt53_Import_LoadFromFile(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile)
-- 2022-10-14T10:53:58.467Z
UPDATE AD_Process_Trl SET Name='Kontoauszugsdatei importieren (camt53)',Updated=TO_TIMESTAMP('2022-10-14 13:53:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585128
;

-- Process: C_BankStatement_Camt53_Import_LoadFromFile(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile)
-- ParameterName: FileName
-- 2022-10-14T10:56:45.455Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2295,0,585128,542320,39,'FileName',TO_TIMESTAMP('2022-10-14 13:56:45','YYYY-MM-DD HH24:MI:SS'),100,'Name of the local file or URL','D',0,'Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','Y','N','Y','N','Y','N','Dateiname',10,TO_TIMESTAMP('2022-10-14 13:56:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-14T10:56:45.458Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542320 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_BankStatement_Camt53_Import_LoadFromFile(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile)
-- ParameterName: Name
-- 2022-10-14T10:58:15.958Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,469,0,585128,542321,10,'Name',TO_TIMESTAMP('2022-10-14 13:58:15','YYYY-MM-DD HH24:MI:SS'),100,'Bank Statement Import','D',60,'Y','N','Y','N','Y','N','Name',20,TO_TIMESTAMP('2022-10-14 13:58:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-14T10:58:15.961Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542321 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_BankStatement_Camt53_Import_LoadFromFile(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile)
-- Table: C_BankStatement
-- EntityType: D
-- 2022-10-14T10:59:14.767Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585128,392,541293,TO_TIMESTAMP('2022-10-14 13:59:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2022-10-14 13:59:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Process: C_BankStatement_Camt53_Import_LoadFromFile(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile)
-- ParameterName: Name
-- 2022-10-14T11:00:33.500Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542321
;

-- 2022-10-14T11:00:33.507Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542321
;

-- Process: C_BankStatement_Camt53_Import_LoadFromFile(de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile)
-- Table: C_BankStatement
-- EntityType: D
-- 2022-10-14T11:20:51.661Z
UPDATE AD_Table_Process SET WEBUI_DocumentAction='N',Updated=TO_TIMESTAMP('2022-10-14 14:20:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541293
;
