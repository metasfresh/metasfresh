-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: M_Department_ID
-- 2023-02-13T14:18:09.390Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581944,0,585212,542540,30,'M_Department_ID',TO_TIMESTAMP('2023-02-13 15:18:09','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','N','N','Department',10,TO_TIMESTAMP('2023-02-13 15:18:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T14:18:09.391Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542540 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: M_SectionCode_ID
-- 2023-02-13T14:19:07.479Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581238,0,585212,542541,30,'M_SectionCode_ID',TO_TIMESTAMP('2023-02-13 15:19:07','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','N','N','Section Code',20,TO_TIMESTAMP('2023-02-13 15:19:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T14:19:07.480Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542541 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_BPartner_ID
-- 2023-02-13T14:19:32.847Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,585212,542542,30,'C_BPartner_ID',TO_TIMESTAMP('2023-02-13 15:19:32','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','N','N','Business Partner',30,TO_TIMESTAMP('2023-02-13 15:19:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T14:19:32.848Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542542 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PlannedLoadingDate
-- 2023-02-13T14:20:24.589Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581688,0,585212,542543,15,'PlannedLoadingDate',TO_TIMESTAMP('2023-02-13 15:20:24','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','N','N','Plan Load Date (ETD)',40,TO_TIMESTAMP('2023-02-13 15:20:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T14:20:24.590Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542543 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PlannedLoadingDate
-- 2023-02-13T14:20:29.726Z
UPDATE AD_Process_Para SET IsRange='Y',Updated=TO_TIMESTAMP('2023-02-13 15:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542543
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PlannedLoadingDate
-- 2023-02-13T14:20:38.014Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-02-13 15:20:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542543
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_BPartner_ID
-- 2023-02-13T14:20:48.953Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-02-13 15:20:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542542
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: M_SectionCode_ID
-- 2023-02-13T14:20:56.178Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-02-13 15:20:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542541
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PlannedDeliveryDate
-- 2023-02-13T14:22:38.563Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581686,0,585212,542544,15,'PlannedDeliveryDate',TO_TIMESTAMP('2023-02-13 15:22:38','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','N','Y','Plan Delivery Date (ETA)',50,TO_TIMESTAMP('2023-02-13 15:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T14:22:38.564Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542544 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PlannedDeliveryDate
-- 2023-02-13T14:22:42.546Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-02-13 15:22:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542544
;

-- Value: C_Invoice_Overdue_With_Open_Order_Excel
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-02-13T14:44:12.944Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM getOverdueInvoicesWithOpenOrders(@M_Department_ID/NULL@, @M_SectionCode_ID/NULL@, @C_BPartner_ID/NULL@ ,NULL, NULL , NULL , NULL);',Updated=TO_TIMESTAMP('2023-02-13 15:44:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585212
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PlannedLoadingDate
-- 2023-02-13T15:01:19.583Z
UPDATE AD_Process_Para SET IsRange='N',Updated=TO_TIMESTAMP('2023-02-13 16:01:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542543
;

-- 2023-02-13T15:01:49.226Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582060,0,'PlannedLoadingDateFrom',TO_TIMESTAMP('2023-02-13 16:01:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Plan Load Date From (ETD)','Plan Load Date From (ETD)',TO_TIMESTAMP('2023-02-13 16:01:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T15:01:49.228Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582060 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-02-13T15:02:09.067Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582061,0,'PlannedLoadingDateTo',TO_TIMESTAMP('2023-02-13 16:02:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Plan Load Date To (ETD)','Plan Load Date FromTo(ETD)',TO_TIMESTAMP('2023-02-13 16:02:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T15:02:09.068Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582061 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PlannedLoadingDateFrom
-- 2023-02-13T15:03:03.337Z
UPDATE AD_Process_Para SET AD_Element_ID=582060, ColumnName='PlannedLoadingDateFrom', Name='Plan Load Date From (ETD)',Updated=TO_TIMESTAMP('2023-02-13 16:03:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542543
;

-- 2023-02-13T15:03:03.338Z
UPDATE AD_Process_Para_Trl trl SET Name='Plan Load Date From (ETD)' WHERE AD_Process_Para_ID=542543 AND AD_Language='en_US'
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PlannedLoadingDateTo
-- 2023-02-13T15:04:12.686Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582061,0,585212,542546,15,'PlannedLoadingDateTo',TO_TIMESTAMP('2023-02-13 16:04:12','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','N','N','Plan Load Date To (ETD)',60,TO_TIMESTAMP('2023-02-13 16:04:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T15:04:12.687Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542546 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PlannedDeliveryDate
-- 2023-02-13T15:04:37.691Z
UPDATE AD_Process_Para SET SeqNo=70,Updated=TO_TIMESTAMP('2023-02-13 16:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542544
;

-- 2023-02-13T15:05:01.268Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582062,0,'PlannedDeliveryDateFrom',TO_TIMESTAMP('2023-02-13 16:05:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Plan Delivery Date From (ETA)','Plan Delivery Date From (ETA)',TO_TIMESTAMP('2023-02-13 16:05:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T15:05:01.269Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582062 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-02-13T15:05:36.189Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582063,0,'PlannedDeliveryDateTo',TO_TIMESTAMP('2023-02-13 16:05:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Plan Delivery Date To (ETA) ','Plan Delivery Date To (ETA)',TO_TIMESTAMP('2023-02-13 16:05:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T15:05:36.190Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582063 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PlannedDeliveryDateFrom
-- 2023-02-13T15:05:50.755Z
UPDATE AD_Process_Para SET AD_Element_ID=582062, ColumnName='PlannedDeliveryDateFrom', Name='Plan Delivery Date From (ETA)',Updated=TO_TIMESTAMP('2023-02-13 16:05:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542544
;

-- 2023-02-13T15:05:50.756Z
UPDATE AD_Process_Para_Trl trl SET Name='Plan Delivery Date From (ETA)' WHERE AD_Process_Para_ID=542544 AND AD_Language='en_US'
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PlannedDeliveryDateTo
-- 2023-02-13T15:06:11.736Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582063,0,585212,542547,15,'PlannedDeliveryDateTo',TO_TIMESTAMP('2023-02-13 16:06:11','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','N','N','Plan Delivery Date To (ETA) ',80,TO_TIMESTAMP('2023-02-13 16:06:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T15:06:11.736Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542547 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: C_Invoice_Overdue_With_Open_Order_Excel
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-02-13T15:09:12.857Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM getOverdueInvoicesWithOpenOrders(@M_Department_ID/NULL@, @M_SectionCode_ID/NULL@, @C_BPartner_ID/NULL@, @PlannedLoadingDateFrom/NULL@, @PlannedLoadingDateTo/NULL@, @PlannedDeliveryDateFrom/NULL@, @PlannedDeliveryDateTo/NULL@);',Updated=TO_TIMESTAMP('2023-02-13 16:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585212
;

-- Process: C_Invoice_Overdue_With_Open_Order_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: PlannedDeliveryDateFrom
-- 2023-02-13T15:11:55.926Z
UPDATE AD_Process_Para SET IsRange='N',Updated=TO_TIMESTAMP('2023-02-13 16:11:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542544
;

-- Value: C_Invoice_Overdue_With_Open_Order_Excel
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-02-13T20:59:21.487Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM getOverdueInvoicesWithOpenOrders(@M_Department_ID/NULL@, @M_SectionCode_ID/NULL@, @C_BPartner_ID/NULL@, ''@PlannedLoadingDateFrom/1900-01-01@''::date, ''@PlannedLoadingDateTo/9999-12-31@''::date, ''@PlannedDeliveryDateFrom/1900-01-01@''::date, ''@PlannedDeliveryDateTo/9999-12-31@''::date);',Updated=TO_TIMESTAMP('2023-02-13 21:59:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585212
;
