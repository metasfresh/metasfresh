-- Value: report_bank_in_transit
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-01-13T15:40:31.012Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585179,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2023-01-13 17:40:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N','N','Y','N','N','N','N','Y','Y',0,'Deposit in transit','json','N','N','xls','SELECT * FROM de_metas_acct.report_bank_in_transit(@C_ElementValue_ID/null@, @C_AcctSchema_ID/null@, @EndDate/quotedIfNotDefault/NULL@);','Excel',TO_TIMESTAMP('2023-01-13 17:40:30','YYYY-MM-DD HH24:MI:SS'),100,'report_bank_in_transit')
;

-- 2023-01-13T15:40:31.284Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585179 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: report_bank_in_transit(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_ElementValue_ID
-- 2023-01-13T15:41:54.850Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,198,0,585179,542440,30,182,'C_ElementValue_ID',TO_TIMESTAMP('2023-01-13 17:41:54','YYYY-MM-DD HH24:MI:SS'),100,'Kontenart','de.metas.acct',0,'Account Elements can be natural accounts or user defined values.','Y','N','Y','N','N','N','Kontenart',10,TO_TIMESTAMP('2023-01-13 17:41:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T15:41:54.892Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542440 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: report_bank_in_transit(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_AcctSchema_ID
-- 2023-01-13T15:42:31.180Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,181,0,585179,542441,18,'C_AcctSchema_ID',TO_TIMESTAMP('2023-01-13 17:42:30','YYYY-MM-DD HH24:MI:SS'),100,'Stammdaten für Buchhaltung','de.metas.acct',0,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','Y','N','N','N','Buchführungs-Schema',20,TO_TIMESTAMP('2023-01-13 17:42:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T15:42:31.221Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542441 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: report_bank_in_transit(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: EndDate
-- 2023-01-13T15:43:33.665Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,294,0,585179,542442,15,'EndDate',TO_TIMESTAMP('2023-01-13 17:43:32','YYYY-MM-DD HH24:MI:SS'),100,'Last effective date (inclusive)','de.metas.acct',0,'The End Date indicates the last date in this range.','Y','N','Y','N','N','N','Enddatum',30,TO_TIMESTAMP('2023-01-13 17:43:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T15:43:33.705Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542442 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: report_inventory_clearing
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-01-13T16:02:30.140Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585180,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2023-01-13 18:02:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N','N','Y','N','N','N','N','Y','Y',0,'In Rechnung gestellte Ware','json','N','N','xls','SELECT * FROM de_metas_acct.report_inventory_clearing(@C_ElementValue_ID/null@, @C_AcctSchema_ID/null@, @EndDate/quotedIfNotDefault/NULL@);','Excel',TO_TIMESTAMP('2023-01-13 18:02:29','YYYY-MM-DD HH24:MI:SS'),100,'report_inventory_clearing')
;

-- 2023-01-13T16:02:30.386Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585180 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: report_inventory_clearing(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2023-01-13T16:02:49.898Z
UPDATE AD_Process_Trl SET Name='Inventory Clearing',Updated=TO_TIMESTAMP('2023-01-13 18:02:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585180
;

-- Process: report_inventory_clearing(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_ElementValue_ID
-- 2023-01-13T16:04:18.234Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,198,0,585180,542443,30,182,'C_ElementValue_ID',TO_TIMESTAMP('2023-01-13 18:04:17','YYYY-MM-DD HH24:MI:SS'),100,'Kontenart','de.metas.acct',0,'Account Elements can be natural accounts or user defined values.','Y','N','Y','N','N','N','Kontenart',10,TO_TIMESTAMP('2023-01-13 18:04:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:04:18.274Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542443 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: report_inventory_clearing(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_AcctSchema_ID
-- 2023-01-13T16:04:58.810Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,181,0,585180,542444,18,'C_AcctSchema_ID',TO_TIMESTAMP('2023-01-13 18:04:58','YYYY-MM-DD HH24:MI:SS'),100,'Stammdaten für Buchhaltung','de.metas.acct',0,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','Y','N','N','N','Buchführungs-Schema',20,TO_TIMESTAMP('2023-01-13 18:04:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:04:58.850Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542444 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: report_inventory_clearing(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: EndDate
-- 2023-01-13T16:05:15.674Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,294,0,585180,542445,15,'EndDate',TO_TIMESTAMP('2023-01-13 18:05:15','YYYY-MM-DD HH24:MI:SS'),100,'Last effective date (inclusive)','de.metas.acct',0,'The End Date indicates the last date in this range.','Y','N','Y','N','N','N','Enddatum',30,TO_TIMESTAMP('2023-01-13 18:05:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:05:15.713Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542445 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: report_unallocated_payments
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-01-13T16:06:06.478Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585181,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2023-01-13 18:06:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N','N','Y','N','N','N','N','Y','Y',0,'Nicht zugeordnete Debitoren Zahlungen','json','N','N','xls','SELECT * FROM de_metas_acct.report_unallocated_payments(@C_ElementValue_ID/null@, @C_AcctSchema_ID/null@, @EndDate/quotedIfNotDefault/NULL@);','Excel',TO_TIMESTAMP('2023-01-13 18:06:06','YYYY-MM-DD HH24:MI:SS'),100,'report_unallocated_payments')
;

-- 2023-01-13T16:06:06.603Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585181 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: report_unallocated_payments(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2023-01-13T16:06:21.604Z
UPDATE AD_Process_Trl SET Name='Unallocated Payments',Updated=TO_TIMESTAMP('2023-01-13 18:06:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585181
;

-- Process: report_unallocated_payments(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_ElementValue_ID
-- 2023-01-13T16:07:11.393Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,198,0,585181,542446,30,182,'C_ElementValue_ID',TO_TIMESTAMP('2023-01-13 18:07:10','YYYY-MM-DD HH24:MI:SS'),100,'Kontenart','de.metas.acct',0,'Account Elements can be natural accounts or user defined values.','Y','N','Y','N','N','N','Kontenart',10,TO_TIMESTAMP('2023-01-13 18:07:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:07:11.433Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542446 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: report_unallocated_payments(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_AcctSchema_ID
-- 2023-01-13T16:07:37.412Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,181,0,585181,542447,18,'C_AcctSchema_ID',TO_TIMESTAMP('2023-01-13 18:07:36','YYYY-MM-DD HH24:MI:SS'),100,'Stammdaten für Buchhaltung','de.metas.acct',0,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','Y','N','N','N','Buchführungs-Schema',20,TO_TIMESTAMP('2023-01-13 18:07:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:07:37.452Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542447 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: report_unallocated_payments(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: EndDate
-- 2023-01-13T16:07:55.283Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,294,0,585181,542448,15,'EndDate',TO_TIMESTAMP('2023-01-13 18:07:54','YYYY-MM-DD HH24:MI:SS'),100,'Last effective date (inclusive)','de.metas.acct',0,'The End Date indicates the last date in this range.','Y','N','Y','N','N','N','Enddatum',30,TO_TIMESTAMP('2023-01-13 18:07:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:07:55.324Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542448 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: report_payment_select
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-01-13T16:08:34.325Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585182,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2023-01-13 18:08:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N','N','Y','N','N','N','N','Y','Y',0,'Ausgewählte Zahlungen','json','N','N','xls','SELECT * FROM de_metas_acct.report_payment_select(@C_ElementValue_ID/null@, @C_AcctSchema_ID/null@, @EndDate/quotedIfNotDefault/NULL@);','Excel',TO_TIMESTAMP('2023-01-13 18:08:33','YYYY-MM-DD HH24:MI:SS'),100,'report_payment_select')
;

-- 2023-01-13T16:08:34.450Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585182 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: report_payment_select(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2023-01-13T16:09:03.102Z
UPDATE AD_Process_Trl SET Name='Payment selection',Updated=TO_TIMESTAMP('2023-01-13 18:09:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585182
;

-- Process: report_payment_select(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_ElementValue_ID
-- 2023-01-13T16:09:34.297Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,198,0,585182,542449,30,182,'C_ElementValue_ID',TO_TIMESTAMP('2023-01-13 18:09:33','YYYY-MM-DD HH24:MI:SS'),100,'Kontenart','de.metas.acct',0,'Account Elements can be natural accounts or user defined values.','Y','N','Y','N','N','N','Kontenart',10,TO_TIMESTAMP('2023-01-13 18:09:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:09:34.336Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542449 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: report_payment_select(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_AcctSchema_ID
-- 2023-01-13T16:09:54.378Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,181,0,585182,542450,18,'C_AcctSchema_ID',TO_TIMESTAMP('2023-01-13 18:09:53','YYYY-MM-DD HH24:MI:SS'),100,'Stammdaten für Buchhaltung','de.metas.acct',0,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','Y','N','N','N','Buchführungs-Schema',20,TO_TIMESTAMP('2023-01-13 18:09:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:09:54.418Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542450 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: report_payment_select(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: EndDate
-- 2023-01-13T16:10:12.201Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,294,0,585182,542451,15,'EndDate',TO_TIMESTAMP('2023-01-13 18:10:11','YYYY-MM-DD HH24:MI:SS'),100,'Last effective date (inclusive)','de.metas.acct',0,'The End Date indicates the last date in this range.','Y','N','Y','N','N','N','Enddatum',30,TO_TIMESTAMP('2023-01-13 18:10:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:10:12.241Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542451 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-01-13T16:16:26.131Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581918,0,TO_TIMESTAMP('2023-01-13 18:16:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','Wertstellung ausstehend','Wertstellung ausstehend',TO_TIMESTAMP('2023-01-13 18:16:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:16:26.376Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581918 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-01-13T16:16:43.903Z
UPDATE AD_Element_Trl SET Name='Deposit in transit', PrintName='Deposit in transit',Updated=TO_TIMESTAMP('2023-01-13 18:16:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581918 AND AD_Language='en_US'
;

-- 2023-01-13T16:16:43.973Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581918,'en_US') 
;

-- 2023-01-13T16:16:58.723Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581919,0,TO_TIMESTAMP('2023-01-13 18:16:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','In Rechnung gestellte Ware','In Rechnung gestellte Ware',TO_TIMESTAMP('2023-01-13 18:16:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:16:58.803Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581919 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-01-13T16:17:09.158Z
UPDATE AD_Element_Trl SET Name='Inventory Clearing', PrintName='Inventory Clearing',Updated=TO_TIMESTAMP('2023-01-13 18:17:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581919 AND AD_Language='en_US'
;

-- 2023-01-13T16:17:09.198Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581919,'en_US') 
;

-- 2023-01-13T16:17:22.542Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581920,0,TO_TIMESTAMP('2023-01-13 18:17:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','Ausgewählte Zahlungen','Ausgewählte Zahlungen',TO_TIMESTAMP('2023-01-13 18:17:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:17:22.664Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581920 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-01-13T16:17:37.895Z
UPDATE AD_Element_Trl SET Name='Payment selection', PrintName='Payment selection',Updated=TO_TIMESTAMP('2023-01-13 18:17:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581920 AND AD_Language='en_US'
;

-- 2023-01-13T16:17:37.934Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581920,'en_US') 
;

-- 2023-01-13T16:17:57.313Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581921,0,TO_TIMESTAMP('2023-01-13 18:17:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','Nicht zugeordnete Debitoren Zahlungen','Nicht zugeordnete Debitoren Zahlungen',TO_TIMESTAMP('2023-01-13 18:17:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:17:57.394Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581921 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-01-13T16:18:17.260Z
UPDATE AD_Element_Trl SET Name='Unallocated Payments', PrintName='Unallocated Payments',Updated=TO_TIMESTAMP('2023-01-13 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581921 AND AD_Language='en_US'
;

-- 2023-01-13T16:18:17.300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581921,'en_US') 
;

-- Name: Wertstellung ausstehend
-- Action Type: P
-- Process: report_bank_in_transit(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2023-01-13T16:19:08.576Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,581918,542036,0,585179,TO_TIMESTAMP('2023-01-13 18:19:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','report_bank_in_transit','Y','N','N','N','N','Wertstellung ausstehend',TO_TIMESTAMP('2023-01-13 18:19:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:19:08.657Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542036 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-01-13T16:19:08.698Z
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542036, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542036)
;

-- 2023-01-13T16:19:08.746Z
/* DDL */  select update_menu_translation_from_ad_element(581918) 
;

-- 2023-01-13T16:19:11.405Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541440 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.445Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541437 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.485Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541434 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.524Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541433 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.563Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541427 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.603Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541432 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.642Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541439 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.682Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540665 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.722Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.761Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.801Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540945 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.840Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.879Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.919Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.958Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:11.998Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540946 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:12.038Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541461 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:12.077Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:12.116Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:12.156Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540948 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:12.195Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540959 AND AD_Tree_ID=10
;

-- 2023-01-13T16:19:12.234Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542036 AND AD_Tree_ID=10
;

-- Name: In Rechnung gestellte Ware
-- Action Type: P
-- Process: report_inventory_clearing(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2023-01-13T16:20:20.341Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,581919,542037,0,585180,TO_TIMESTAMP('2023-01-13 18:20:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','report_inventory_clearing','Y','N','N','N','N','In Rechnung gestellte Ware',TO_TIMESTAMP('2023-01-13 18:20:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:20:20.422Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542037 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-01-13T16:20:20.461Z
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542037, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542037)
;

-- 2023-01-13T16:20:20.502Z
/* DDL */  select update_menu_translation_from_ad_element(581919) 
;

-- 2023-01-13T16:20:23.193Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000066, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542037 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:32.888Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542037 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:32.927Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541440 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:32.967Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541437 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.006Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541434 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.045Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541433 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.085Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541427 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.124Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541432 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.164Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541439 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.204Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540665 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.244Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.284Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.323Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540945 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.363Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.403Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.442Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.482Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.522Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540946 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.562Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541461 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.601Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.640Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.679Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540948 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.719Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540959 AND AD_Tree_ID=10
;

-- 2023-01-13T16:20:33.758Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542036 AND AD_Tree_ID=10
;

-- Name: Ausgewählte Zahlungen
-- Action Type: P
-- Process: report_payment_select(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2023-01-13T16:21:30.481Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,581920,542038,0,585182,TO_TIMESTAMP('2023-01-13 18:21:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','report_payment_select','Y','N','N','N','N','Ausgewählte Zahlungen',TO_TIMESTAMP('2023-01-13 18:21:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:21:30.562Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542038 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-01-13T16:21:30.604Z
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542038, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542038)
;

-- 2023-01-13T16:21:30.644Z
/* DDL */  select update_menu_translation_from_ad_element(581920) 
;

-- 2023-01-13T16:21:33.290Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542037 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.329Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541440 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.369Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541437 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.408Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541434 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.448Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541433 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.487Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541427 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.527Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541432 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.566Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541439 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.605Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540665 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.645Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.685Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.724Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540945 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.764Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.803Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.843Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.882Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.922Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540946 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:33.961Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541461 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:34.001Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:34.040Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:34.079Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540948 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:34.119Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540959 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:34.157Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542036 AND AD_Tree_ID=10
;

-- 2023-01-13T16:21:34.197Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542038 AND AD_Tree_ID=10
;

-- Name: Nicht zugeordnete Debitoren Zahlungen
-- Action Type: P
-- Process: report_unallocated_payments(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2023-01-13T16:22:30.211Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,581921,542039,0,585181,TO_TIMESTAMP('2023-01-13 18:22:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','report_unallocated_payments','Y','N','N','N','N','Nicht zugeordnete Debitoren Zahlungen',TO_TIMESTAMP('2023-01-13 18:22:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-13T16:22:30.292Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542039 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-01-13T16:22:30.332Z
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542039, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542039)
;

-- 2023-01-13T16:22:30.373Z
/* DDL */  select update_menu_translation_from_ad_element(581921) 
;

-- 2023-01-13T16:22:33.063Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.102Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.142Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.181Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.220Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.260Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.298Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.338Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.377Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.416Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.456Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.495Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.534Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.573Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.612Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.652Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.691Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.731Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.770Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.809Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.849Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.887Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.927Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:33.967Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:34.006Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:34.045Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:34.084Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:34.124Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:34.162Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:34.202Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:34.242Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:34.281Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:34.320Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:34.360Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:34.399Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:34.438Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:34.479Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:34.518Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542039 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:54.885Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542039 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:54.925Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542037 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:54.964Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541440 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.004Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541437 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.043Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541434 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.082Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541433 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.122Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541427 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.161Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541432 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541439 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.240Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540665 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.279Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.319Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.358Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540945 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.397Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.436Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.476Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.516Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.555Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540946 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.594Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541461 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.634Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.674Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.714Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540948 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.753Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540959 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.792Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542036 AND AD_Tree_ID=10
;

-- 2023-01-13T16:22:55.833Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542038 AND AD_Tree_ID=10
;

-- Process: report_unallocated_payments(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_ElementValue_ID
-- 2023-01-13T16:25:00.219Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-13 18:25:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542446
;

-- Process: report_payment_select(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_ElementValue_ID
-- 2023-01-13T16:25:15.121Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-13 18:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542449
;

-- Process: report_inventory_clearing(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_ElementValue_ID
-- 2023-01-13T16:25:30.597Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-13 18:25:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542443
;

-- Process: report_bank_in_transit(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_ElementValue_ID
-- 2023-01-13T16:25:42.444Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-13 18:25:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542440
;

-- Process: report_bank_in_transit(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: EndDate
-- 2023-01-16T09:42:19.126Z
UPDATE AD_Process_Para SET DefaultValue='@#Date@', IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-16 11:42:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542442
;

-- Process: report_inventory_clearing(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: EndDate
-- 2023-01-16T09:42:35.525Z
UPDATE AD_Process_Para SET DefaultValue='@#Date@', IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-16 11:42:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542445
;

-- Process: report_payment_select(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: EndDate
-- 2023-01-16T09:42:49.899Z
UPDATE AD_Process_Para SET DefaultValue='@#Date@', IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-16 11:42:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542451
;

-- Process: report_unallocated_payments(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: EndDate
-- 2023-01-16T09:43:01.394Z
UPDATE AD_Process_Para SET DefaultValue='@#Date@', IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-16 11:43:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542448
;

