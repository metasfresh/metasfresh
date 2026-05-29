
-- Run mode: WEBUI

-- Value: Zugeordnete Rechnungen und entsprechende Zahlungen (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-06-25T07:33:52.041Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585479,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess',TO_TIMESTAMP('2025-06-25 07:33:52.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y','Zugeordnete Rechnungen und entsprechende Zahlungen (Excel)','json','N','N','xls','Excel',TO_TIMESTAMP('2025-06-25 07:33:52.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugeordnete Rechnungen und entsprechende Zahlungen (Excel)')
;

-- 2025-06-25T07:33:52.043Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585479 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: Zugeordnete Rechnungen und entsprechende Zahlungen (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-06-25T07:55:22.215Z
UPDATE AD_Process SET SQLStatement='Select * from getInvoicePaymentAllocations_Report( ''2023-01-01'', ''2025-06-25'');
',Updated=TO_TIMESTAMP('2025-06-25 07:55:22.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585479
;

-- Value: InvoicePaymentAllocations_Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-06-25T07:55:32.297Z
UPDATE AD_Process SET Value='InvoicePaymentAllocations_Report (Excel)',Updated=TO_TIMESTAMP('2025-06-25 07:55:32.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585479
;

-- Value: InvoicePaymentAllocations_Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-06-25T07:55:32.313Z
UPDATE AD_Process SET Value='InvoicePaymentAllocations_Report (Excel)',Updated=TO_TIMESTAMP('2025-06-25 07:55:32.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585479
;

-- Value: InvoicePaymentAllocations_Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-06-25T07:56:01.252Z
UPDATE AD_Process SET SQLStatement='Select * from getInvoicePaymentAllocations_Report( @DateInvoiced_From@, ''2025-06-25'');
',Updated=TO_TIMESTAMP('2025-06-25 07:56:01.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585479
;

-- Value: InvoicePaymentAllocations_Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-06-25T07:56:37.159Z
UPDATE AD_Process SET SQLStatement='Select * from getInvoicePaymentAllocations_Report( @DateInvoiced_From/NULL@,  @DateInvoiced_To/NULL@);
',Updated=TO_TIMESTAMP('2025-06-25 07:56:37.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585479
;



-- Value: InvoicePaymentAllocations_Report_Excel
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-06-25T07:58:27.159Z
UPDATE AD_Process SET Value='InvoicePaymentAllocations_Report_Excel',Updated=TO_TIMESTAMP('2025-06-25 07:58:27.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585479
;

-- Process: InvoicePaymentAllocations_Report_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateInvoicedFrom
-- 2025-06-25T10:28:03.285Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583390,0,585479,542960,15,'DateInvoicedFrom',TO_TIMESTAMP('2025-06-25 10:28:03.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U',0,'Y','N','Y','N','N','N','Rechnungsdatum von',10,TO_TIMESTAMP('2025-06-25 10:28:03.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-25T10:28:03.287Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542960 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: InvoicePaymentAllocations_Report_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateInvoicedFrom
-- 2025-06-25T10:28:07.788Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2025-06-25 10:28:07.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542960
;

-- Process: InvoicePaymentAllocations_Report_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateInvoicedTo
-- 2025-06-25T10:28:27.407Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583391,0,585479,542961,15,'DateInvoicedTo',TO_TIMESTAMP('2025-06-25 10:28:27.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U',0,'Y','N','Y','N','N','N','Rechnungsdatum bis',20,TO_TIMESTAMP('2025-06-25 10:28:27.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-25T10:28:27.408Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542961 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: InvoicePaymentAllocations_Report_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateInvoicedTo
-- 2025-06-25T10:28:49.647Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2025-06-25 10:28:49.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542961
;

-- Value: InvoicePaymentAllocations_Report_Excel
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-06-25T10:29:05.036Z
UPDATE AD_Process SET SQLStatement='Select * from getInvoicePaymentAllocations_Report( @DateInvoicedFrom/NULL@,  @DateInvoicedTo/NULL@);
',Updated=TO_TIMESTAMP('2025-06-25 10:29:05.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585479
;



-- Element: PaymentDocumentNo
-- 2025-06-25T10:35:14.170Z
UPDATE AD_Element_Trl SET Name='Zahlungsbelegnummer',Updated=TO_TIMESTAMP('2025-06-25 10:35:14.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2298 AND AD_Language='de_DE'
;

-- 2025-06-25T10:35:14.171Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:35:14.469Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2298,'de_DE')
;

-- 2025-06-25T10:35:14.470Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2298,'de_DE')
;

-- Element: PaymentDocumentNo
-- 2025-06-25T10:35:16.489Z
UPDATE AD_Element_Trl SET PrintName='Zahlungsbelegnummer',Updated=TO_TIMESTAMP('2025-06-25 10:35:16.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2298 AND AD_Language='de_DE'
;

-- 2025-06-25T10:35:16.490Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:35:16.780Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2298,'de_DE')
;

-- 2025-06-25T10:35:16.781Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2298,'de_DE')
;

-- Element: PaymentDocumentNo
-- 2025-06-25T10:35:19.712Z
UPDATE AD_Element_Trl SET Description='Zahlungsbelegnummer',Updated=TO_TIMESTAMP('2025-06-25 10:35:19.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2298 AND AD_Language='de_DE'
;

-- 2025-06-25T10:35:19.713Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:35:19.990Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2298,'de_DE')
;

-- 2025-06-25T10:35:19.991Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2298,'de_DE')
;

-- Element: PaymentDocumentNo
-- 2025-06-25T10:35:32.985Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-25 10:35:32.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2298 AND AD_Language='de_DE'
;

-- 2025-06-25T10:35:32.985Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2298,'de_DE')
;

-- 2025-06-25T10:35:32.986Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2298,'de_DE')
;

-- Element: PaymentDocumentNo
-- 2025-06-25T10:35:41.778Z
UPDATE AD_Element_Trl SET Name='Zahlungsbelegnummer',Updated=TO_TIMESTAMP('2025-06-25 10:35:41.778000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2298 AND AD_Language='de_CH'
;

-- 2025-06-25T10:35:41.778Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:35:41.912Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2298,'de_CH')
;

-- Element: PaymentDocumentNo
-- 2025-06-25T10:35:42.626Z
UPDATE AD_Element_Trl SET PrintName='Zahlungsbelegnummer',Updated=TO_TIMESTAMP('2025-06-25 10:35:42.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2298 AND AD_Language='de_CH'
;

-- 2025-06-25T10:35:42.627Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:35:42.789Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2298,'de_CH')
;

-- Element: PaymentDocumentNo
-- 2025-06-25T10:35:43.851Z
UPDATE AD_Element_Trl SET Description='Zahlungsbelegnummer',Updated=TO_TIMESTAMP('2025-06-25 10:35:43.851000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2298 AND AD_Language='de_CH'
;

-- 2025-06-25T10:35:43.852Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:35:43.997Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2298,'de_CH')
;

-- Element: PaymentDocumentNo
-- 2025-06-25T10:35:44.005Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-25 10:35:44.005000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2298 AND AD_Language='de_CH'
;

-- 2025-06-25T10:35:44.006Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2298,'de_CH')
;





-- 2025-06-25T10:38:50.068Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583747,0,'InvoiceDocTypeName',TO_TIMESTAMP('2025-06-25 10:38:50.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Invoice Document Type','Invoice Document Type',TO_TIMESTAMP('2025-06-25 10:38:50.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-25T10:38:50.069Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583747 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-06-25T10:38:52.053Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-06-25 10:38:52.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583747
;

-- 2025-06-25T10:38:52.055Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583747,'de_DE')
;

-- Element: InvoiceDocTypeName
-- 2025-06-25T10:39:41.197Z
UPDATE AD_Element_Trl SET Name='Rechnungsbelegart',Updated=TO_TIMESTAMP('2025-06-25 10:39:41.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583747 AND AD_Language='de_CH'
;

-- 2025-06-25T10:39:41.198Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:39:41.330Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583747,'de_CH')
;

-- Element: InvoiceDocTypeName
-- 2025-06-25T10:39:42.828Z
UPDATE AD_Element_Trl SET PrintName='Rechnungsbelegart',Updated=TO_TIMESTAMP('2025-06-25 10:39:42.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583747 AND AD_Language='de_CH'
;

-- 2025-06-25T10:39:42.828Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:39:42.959Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583747,'de_CH')
;

-- Element: InvoiceDocTypeName
-- 2025-06-25T10:39:42.965Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-25 10:39:42.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583747 AND AD_Language='de_CH'
;

-- 2025-06-25T10:39:42.974Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583747,'de_CH')
;

-- Element: InvoiceDocTypeName
-- 2025-06-25T10:39:46.212Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-25 10:39:46.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583747 AND AD_Language='de_DE'
;

-- 2025-06-25T10:39:46.213Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583747,'de_DE')
;

-- 2025-06-25T10:39:46.213Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583747,'de_DE')
;

-- Element: InvoiceDocTypeName
-- 2025-06-25T10:39:48.140Z
UPDATE AD_Element_Trl SET Name='Rechnungsbelegart',Updated=TO_TIMESTAMP('2025-06-25 10:39:48.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583747 AND AD_Language='de_DE'
;

-- 2025-06-25T10:39:48.141Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:39:48.411Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583747,'de_DE')
;

-- 2025-06-25T10:39:48.411Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583747,'de_DE')
;

-- Element: InvoiceDocTypeName
-- 2025-06-25T10:39:49.226Z
UPDATE AD_Element_Trl SET PrintName='Rechnungsbelegart',Updated=TO_TIMESTAMP('2025-06-25 10:39:49.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583747 AND AD_Language='de_DE'
;

-- 2025-06-25T10:39:49.227Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:39:49.507Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583747,'de_DE')
;

-- 2025-06-25T10:39:49.508Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583747,'de_DE')
;

-- 2025-06-25T10:43:09.228Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583748,0,'Bill_BPartner_Value',TO_TIMESTAMP('2025-06-25 10:43:09.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Billing Partner Value','Billing Partner Value',TO_TIMESTAMP('2025-06-25 10:43:09.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-25T10:43:09.228Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583748 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-06-25T10:44:14.232Z
UPDATE AD_Element SET Name='Billing Partner ',Updated=TO_TIMESTAMP('2025-06-25 10:44:14.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583748
;

-- 2025-06-25T10:44:14.233Z
UPDATE AD_Element_Trl trl SET Name='Billing Partner ' WHERE AD_Element_ID=583748 AND AD_Language='de_DE'
;

-- 2025-06-25T10:44:14.234Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583748,'de_DE')
;

-- 2025-06-25T10:44:16.006Z
UPDATE AD_Element SET PrintName='Billing Partner',Updated=TO_TIMESTAMP('2025-06-25 10:44:16.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583748
;

-- 2025-06-25T10:44:16.007Z
UPDATE AD_Element_Trl trl SET PrintName='Billing Partner' WHERE AD_Element_ID=583748 AND AD_Language='de_DE'
;

-- 2025-06-25T10:44:16.007Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583748,'de_DE')
;

-- 2025-06-25T10:44:20.230Z
UPDATE AD_Element SET Name='Value Billing Partner',Updated=TO_TIMESTAMP('2025-06-25 10:44:20.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583748
;

-- 2025-06-25T10:44:20.230Z
UPDATE AD_Element_Trl trl SET Name='Value Billing Partner' WHERE AD_Element_ID=583748 AND AD_Language='de_DE'
;

-- 2025-06-25T10:44:20.231Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583748,'de_DE')
;

-- 2025-06-25T10:44:22.854Z
UPDATE AD_Element SET PrintName='Value Billing Partner',Updated=TO_TIMESTAMP('2025-06-25 10:44:22.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583748
;

-- 2025-06-25T10:44:22.854Z
UPDATE AD_Element_Trl trl SET PrintName='Value Billing Partner' WHERE AD_Element_ID=583748 AND AD_Language='de_DE'
;

-- 2025-06-25T10:44:22.855Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583748,'de_DE')
;

-- Element: Bill_BPartner_Value
-- 2025-06-25T10:44:47.397Z
UPDATE AD_Element_Trl SET Name='Name Rechnungspartner',Updated=TO_TIMESTAMP('2025-06-25 10:44:47.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583748 AND AD_Language='de_CH'
;

-- 2025-06-25T10:44:47.397Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T10:44:47.527Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583748,'de_CH')
;

-- 2025-06-25T10:45:28.897Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=583748
;

-- 2025-06-25T10:45:28.903Z
DELETE FROM AD_Element WHERE AD_Element_ID=583748
;







-- 2025-06-25T12:13:43.173Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583749,0,'InvoiceCurrency',TO_TIMESTAMP('2025-06-25 12:13:43.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Invoice Currency','Invoice Currency',TO_TIMESTAMP('2025-06-25 12:13:43.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-25T12:13:43.175Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583749 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-06-25T12:13:45.605Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-06-25 12:13:45.605000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583749
;

-- 2025-06-25T12:13:45.615Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583749,'de_DE')
;





-- Element: InvoiceCurrency
-- 2025-06-25T12:14:41.939Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-25 12:14:41.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583749 AND AD_Language='de_CH'
;

-- 2025-06-25T12:14:41.940Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583749,'de_CH')
;

-- Element: InvoiceCurrency
-- 2025-06-25T12:14:43.420Z
UPDATE AD_Element_Trl SET Name='Rechnungswährung',Updated=TO_TIMESTAMP('2025-06-25 12:14:43.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583749 AND AD_Language='de_CH'
;

-- 2025-06-25T12:14:43.420Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:14:43.563Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583749,'de_CH')
;

-- Element: InvoiceCurrency
-- 2025-06-25T12:14:44.025Z
UPDATE AD_Element_Trl SET PrintName='Rechnungswährung',Updated=TO_TIMESTAMP('2025-06-25 12:14:44.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583749 AND AD_Language='de_CH'
;

-- 2025-06-25T12:14:44.026Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:14:44.135Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583749,'de_CH')
;

-- Element: InvoiceCurrency
-- 2025-06-25T12:14:49.404Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-25 12:14:49.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583749 AND AD_Language='de_DE'
;

-- 2025-06-25T12:14:49.404Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583749,'de_DE')
;

-- 2025-06-25T12:14:49.405Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583749,'de_DE')
;

-- Element: InvoiceCurrency
-- 2025-06-25T12:14:51.882Z
UPDATE AD_Element_Trl SET Name='Rechnungswährung',Updated=TO_TIMESTAMP('2025-06-25 12:14:51.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583749 AND AD_Language='de_DE'
;

-- 2025-06-25T12:14:51.883Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:14:52.137Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583749,'de_DE')
;

-- 2025-06-25T12:14:52.137Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583749,'de_DE')
;

-- Element: InvoiceCurrency
-- 2025-06-25T12:14:52.572Z
UPDATE AD_Element_Trl SET PrintName='Rechnungswährung',Updated=TO_TIMESTAMP('2025-06-25 12:14:52.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583749 AND AD_Language='de_DE'
;

-- 2025-06-25T12:14:52.572Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:14:52.861Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583749,'de_DE')
;

-- 2025-06-25T12:14:52.861Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583749,'de_DE')
;

-- 2025-06-25T12:15:39.891Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583750,0,'PaymentCurrency',TO_TIMESTAMP('2025-06-25 12:15:39.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Payment Currency','Payment Currency',TO_TIMESTAMP('2025-06-25 12:15:39.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-25T12:15:39.892Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583750 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-06-25T12:15:41.689Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-06-25 12:15:41.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583750
;

-- 2025-06-25T12:15:41.690Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583750,'de_DE')
;

-- Element: PaymentCurrency
-- 2025-06-25T12:15:53.819Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-25 12:15:53.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583750 AND AD_Language='de_CH'
;

-- 2025-06-25T12:15:53.820Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583750,'de_CH')
;

-- Element: PaymentCurrency
-- 2025-06-25T12:15:55.130Z
UPDATE AD_Element_Trl SET Name='Zahlungswährung',Updated=TO_TIMESTAMP('2025-06-25 12:15:55.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583750 AND AD_Language='de_CH'
;

-- 2025-06-25T12:15:55.130Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:15:55.262Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583750,'de_CH')
;

-- Element: PaymentCurrency
-- 2025-06-25T12:15:56.187Z
UPDATE AD_Element_Trl SET PrintName='Zahlungswährung',Updated=TO_TIMESTAMP('2025-06-25 12:15:56.187000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583750 AND AD_Language='de_CH'
;

-- 2025-06-25T12:15:56.187Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:15:56.322Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583750,'de_CH')
;

-- Element: PaymentCurrency
-- 2025-06-25T12:16:00.570Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-25 12:16:00.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583750 AND AD_Language='de_DE'
;

-- 2025-06-25T12:16:00.570Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583750,'de_DE')
;

-- 2025-06-25T12:16:00.571Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583750,'de_DE')
;

-- Element: PaymentCurrency
-- 2025-06-25T12:16:02.460Z
UPDATE AD_Element_Trl SET Name='Zahlungswährung',Updated=TO_TIMESTAMP('2025-06-25 12:16:02.460000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583750 AND AD_Language='de_DE'
;

-- 2025-06-25T12:16:02.460Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:16:02.722Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583750,'de_DE')
;

-- 2025-06-25T12:16:02.722Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583750,'de_DE')
;

-- Element: PaymentCurrency
-- 2025-06-25T12:16:04.756Z
UPDATE AD_Element_Trl SET PrintName='Zahlungswährung',Updated=TO_TIMESTAMP('2025-06-25 12:16:04.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583750 AND AD_Language='de_DE'
;

-- 2025-06-25T12:16:04.756Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:16:05.058Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583750,'de_DE')
;

-- 2025-06-25T12:16:05.058Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583750,'de_DE')
;


-- 2025-06-25T12:23:36.620Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583751,0,'Payment_BPartner_Name',TO_TIMESTAMP('2025-06-25 12:23:36.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Payment Partner Name','Payment Partner Name',TO_TIMESTAMP('2025-06-25 12:23:36.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-25T12:23:36.621Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583751 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Payment_BPartner_Name
-- 2025-06-25T12:25:27.442Z
UPDATE AD_Element_Trl SET Name='Name Zahlungspartner',Updated=TO_TIMESTAMP('2025-06-25 12:25:27.441000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583751 AND AD_Language='de_CH'
;

-- 2025-06-25T12:25:27.442Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:25:27.575Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583751,'de_CH')
;

-- Element: Payment_BPartner_Name
-- 2025-06-25T12:25:28.861Z
UPDATE AD_Element_Trl SET PrintName='Name Zahlungspartner',Updated=TO_TIMESTAMP('2025-06-25 12:25:28.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583751 AND AD_Language='de_CH'
;

-- 2025-06-25T12:25:28.861Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:25:29.006Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583751,'de_CH')
;

-- Element: Payment_BPartner_Name
-- 2025-06-25T12:25:29.016Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-25 12:25:29.016000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583751 AND AD_Language='de_CH'
;

-- 2025-06-25T12:25:29.018Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583751,'de_CH')
;

-- Element: Payment_BPartner_Name
-- 2025-06-25T12:25:32.744Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-25 12:25:32.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583751 AND AD_Language='de_DE'
;

-- 2025-06-25T12:25:32.745Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583751,'de_DE')
;

-- 2025-06-25T12:25:32.745Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583751,'de_DE')
;

-- Element: Payment_BPartner_Name
-- 2025-06-25T12:25:34.488Z
UPDATE AD_Element_Trl SET Name='Name Zahlungspartner',Updated=TO_TIMESTAMP('2025-06-25 12:25:34.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583751 AND AD_Language='de_DE'
;

-- 2025-06-25T12:25:34.488Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:25:34.742Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583751,'de_DE')
;

-- 2025-06-25T12:25:34.742Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583751,'de_DE')
;

-- Element: Payment_BPartner_Name
-- 2025-06-25T12:25:36.209Z
UPDATE AD_Element_Trl SET PrintName='Name Zahlungspartner',Updated=TO_TIMESTAMP('2025-06-25 12:25:36.209000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583751 AND AD_Language='de_DE'
;

-- 2025-06-25T12:25:36.210Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:25:36.486Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583751,'de_DE')
;

-- 2025-06-25T12:25:36.487Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583751,'de_DE')
;

-- 2025-06-25T12:26:05.254Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583752,0,'Payment_BPartner_Value',TO_TIMESTAMP('2025-06-25 12:26:05.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Payment Partner Value','Payment Partner Value',TO_TIMESTAMP('2025-06-25 12:26:05.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-25T12:26:05.254Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583752 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-06-25T12:26:08.235Z
UPDATE AD_Element SET EntityType='de.schaeffer.pay',Updated=TO_TIMESTAMP('2025-06-25 12:26:08.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583752
;

-- 2025-06-25T12:26:08.236Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583752,'de_DE')
;

-- 2025-06-25T12:26:10.152Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-06-25 12:26:10.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583752
;

-- 2025-06-25T12:26:10.153Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583752,'de_DE')
;

-- Element: Payment_BPartner_Value
-- 2025-06-25T12:27:19.663Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-25 12:27:19.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583752 AND AD_Language='de_CH'
;

-- 2025-06-25T12:27:19.664Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583752,'de_CH')
;

-- Element: Payment_BPartner_Value
-- 2025-06-25T12:27:21.346Z
UPDATE AD_Element_Trl SET Name='Schlüssel des Zahlungs-Geschäftspartners',Updated=TO_TIMESTAMP('2025-06-25 12:27:21.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583752 AND AD_Language='de_CH'
;

-- 2025-06-25T12:27:21.347Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:27:21.478Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583752,'de_CH')
;

-- Element: Payment_BPartner_Value
-- 2025-06-25T12:27:22.953Z
UPDATE AD_Element_Trl SET PrintName='Schlüssel des Zahlungs-Geschäftspartners',Updated=TO_TIMESTAMP('2025-06-25 12:27:22.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583752 AND AD_Language='de_CH'
;

-- 2025-06-25T12:27:22.953Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:27:23.077Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583752,'de_CH')
;

-- Element: Payment_BPartner_Value
-- 2025-06-25T12:27:26.415Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-25 12:27:26.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583752 AND AD_Language='de_DE'
;

-- 2025-06-25T12:27:26.415Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583752,'de_DE')
;

-- 2025-06-25T12:27:26.416Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583752,'de_DE')
;

-- Element: Payment_BPartner_Value
-- 2025-06-25T12:27:28.192Z
UPDATE AD_Element_Trl SET Name='Schlüssel des Zahlungs-Geschäftspartners',Updated=TO_TIMESTAMP('2025-06-25 12:27:28.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583752 AND AD_Language='de_DE'
;

-- 2025-06-25T12:27:28.192Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:27:28.460Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583752,'de_DE')
;

-- 2025-06-25T12:27:28.460Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583752,'de_DE')
;

-- Element: Payment_BPartner_Value
-- 2025-06-25T12:27:29.343Z
UPDATE AD_Element_Trl SET PrintName='Schlüssel des Zahlungs-Geschäftspartners',Updated=TO_TIMESTAMP('2025-06-25 12:27:29.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583752 AND AD_Language='de_DE'
;

-- 2025-06-25T12:27:29.344Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:27:29.605Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583752,'de_DE')
;

-- 2025-06-25T12:27:29.606Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583752,'de_DE')
;












-- 2025-06-25T12:52:32.435Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583753,0,TO_TIMESTAMP('2025-06-25 12:52:32.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','InvoicePaymentAllocations_Report_Excel','InvoicePaymentAllocations_Report_Excel',TO_TIMESTAMP('2025-06-25 12:52:32.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-25T12:52:32.453Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583753 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-06-25T12:52:33.679Z
UPDATE AD_Element SET ColumnName='InvoicePaymentAllocations_Report_Excel',Updated=TO_TIMESTAMP('2025-06-25 12:52:33.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583753
;

-- 2025-06-25T12:52:33.680Z
UPDATE AD_Column SET ColumnName='InvoicePaymentAllocations_Report_Excel' WHERE AD_Element_ID=583753
;

-- 2025-06-25T12:52:33.681Z
UPDATE AD_Process_Para SET ColumnName='InvoicePaymentAllocations_Report_Excel' WHERE AD_Element_ID=583753
;

-- 2025-06-25T12:52:33.682Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583753,'de_DE')
;

-- 2025-06-25T12:52:41.520Z
UPDATE AD_Element SET Name='Zugeordnete Rechnungen und entsprechende Zahlungen (Excel)',Updated=TO_TIMESTAMP('2025-06-25 12:52:41.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583753
;

-- 2025-06-25T12:52:41.528Z
UPDATE AD_Element_Trl trl SET Name='Zugeordnete Rechnungen und entsprechende Zahlungen (Excel)' WHERE AD_Element_ID=583753 AND AD_Language='de_DE'
;

-- 2025-06-25T12:52:41.529Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583753,'de_DE')
;

-- 2025-06-25T12:52:46.036Z
UPDATE AD_Element SET PrintName='Zugeordnete Rechnungen und entsprechende Zahlungen (Excel)',Updated=TO_TIMESTAMP('2025-06-25 12:52:46.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583753
;

-- 2025-06-25T12:52:46.036Z
UPDATE AD_Element_Trl trl SET PrintName='Zugeordnete Rechnungen und entsprechende Zahlungen (Excel)' WHERE AD_Element_ID=583753 AND AD_Language='de_DE'
;

-- 2025-06-25T12:52:46.038Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583753,'de_DE')
;

-- 2025-06-25T12:52:55.061Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-06-25 12:52:55.061000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583753
;

-- 2025-06-25T12:52:55.062Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583753,'de_DE')
;

-- Element: InvoicePaymentAllocations_Report_Excel
-- 2025-06-25T12:54:03.539Z
UPDATE AD_Element_Trl SET Name='Zugeordnete Rechnungen und entsprechende Zahlungen (Excel)',Updated=TO_TIMESTAMP('2025-06-25 12:54:03.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583753 AND AD_Language='de_CH'
;

-- 2025-06-25T12:54:03.540Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:54:03.681Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583753,'de_CH')
;

-- Element: InvoicePaymentAllocations_Report_Excel
-- 2025-06-25T12:54:05.323Z
UPDATE AD_Element_Trl SET PrintName='Zugeordnete Rechnungen und entsprechende Zahlungen (Excel)',Updated=TO_TIMESTAMP('2025-06-25 12:54:05.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583753 AND AD_Language='de_CH'
;

-- 2025-06-25T12:54:05.324Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:54:05.439Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583753,'de_CH')
;

-- Element: InvoicePaymentAllocations_Report_Excel
-- 2025-06-25T12:54:05.447Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-25 12:54:05.447000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583753 AND AD_Language='de_CH'
;

-- 2025-06-25T12:54:05.448Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583753,'de_CH')
;

-- Element: InvoicePaymentAllocations_Report_Excel
-- 2025-06-25T12:54:22.997Z
UPDATE AD_Element_Trl SET Name='Invoice Payment Allocations Report (Excel)',Updated=TO_TIMESTAMP('2025-06-25 12:54:22.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583753 AND AD_Language='en_US'
;

-- 2025-06-25T12:54:22.997Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:54:23.142Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583753,'en_US')
;

-- Element: InvoicePaymentAllocations_Report_Excel
-- 2025-06-25T12:54:24.226Z
UPDATE AD_Element_Trl SET PrintName='Invoice Payment Allocations Report (Excel)',Updated=TO_TIMESTAMP('2025-06-25 12:54:24.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583753 AND AD_Language='en_US'
;

-- 2025-06-25T12:54:24.227Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-25T12:54:24.351Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583753,'en_US')
;

-- Element: InvoicePaymentAllocations_Report_Excel
-- 2025-06-25T12:54:24.359Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-25 12:54:24.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583753 AND AD_Language='en_US'
;

-- 2025-06-25T12:54:24.367Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583753,'en_US')
;








-- Name: Zugeordnete Rechnungen und entsprechende Zahlungen (Excel)
-- Action Type: P
-- Process: HU_Labels_SSCC_LU(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-06-25T12:56:34.254Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,583753,542227,0,540412,TO_TIMESTAMP('2025-06-25 12:56:34.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.fresh','HU_Labels_SSCC_LU','Y','N','N','N','N','Zugeordnete Rechnungen und entsprechende Zahlungen (Excel)',TO_TIMESTAMP('2025-06-25 12:56:34.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-25T12:56:34.255Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542227 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-06-25T12:56:34.256Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542227, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542227)
;

-- 2025-06-25T12:56:34.259Z
/* DDL */  select update_menu_translation_from_ad_element(583753)
;

-- Name: Zugeordnete Rechnungen und entsprechende Zahlungen (Excel)
-- Action Type: P
-- Process: InvoicePaymentAllocations_Report_Excel(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-06-25T12:56:44.979Z
UPDATE AD_Menu SET AD_Process_ID=585479, InternalName='InvoicePaymentAllocations_Report_Excel',Updated=TO_TIMESTAMP('2025-06-25 12:56:44.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=542227
;



-- Run mode: SWING_CLIENT

-- Reordering children of `Reports`
-- Node name: `Invoice Payment Allocations Report (Excel)`
-- 2025-06-25T13:39:25.993Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000067, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542227 AND AD_Tree_ID=10
;

-- Reordering children of `Billing`
-- Node name: `Provisionsberechnung`
-- 2025-06-25T13:39:44.007Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542203 AND AD_Tree_ID=10
;

-- Node name: `Invoice verifcation`
-- 2025-06-25T13:39:44.047Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541712 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice Candidates`
-- 2025-06-25T13:39:44.088Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000104 AND AD_Tree_ID=10
;

-- Node name: `Purchase Invoice Candidates`
-- 2025-06-25T13:39:44.129Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541537 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice`
-- 2025-06-25T13:39:44.169Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000085 AND AD_Tree_ID=10
;

-- Node name: `Purchase Invoice`
-- 2025-06-25T13:39:44.209Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000086 AND AD_Tree_ID=10
;

-- Node name: `Customs Invoice`
-- 2025-06-25T13:39:44.249Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541271 AND AD_Tree_ID=10
;

-- Node name: `Shipment Line to Customs Invoice Line`
-- 2025-06-25T13:39:44.297Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541418 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-06-25T13:39:44.338Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000059 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-06-25T13:39:44.395Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000067 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-06-25T13:39:44.435Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000077 AND AD_Tree_ID=10
;

-- Node name: `Invoicing Pool`
-- 2025-06-25T13:39:44.476Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542033 AND AD_Tree_ID=10
;

-- Node name: `Enqueue selection for invoicing and pdf printing`
-- 2025-06-25T13:39:44.515Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541886 AND AD_Tree_ID=10
;

-- Node name: `Commission Forecast`
-- 2025-06-25T13:39:44.576Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542214 AND AD_Tree_ID=10
;

-- Node name: `Invoice Payment Allocations Report (Excel)`
-- 2025-06-25T13:39:44.615Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542227 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice Report (Excel)`
-- 2025-06-25T13:39:44.655Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542186 AND AD_Tree_ID=10
;

-- Reordering children of `Billing`
-- Node name: `Provisionsberechnung`
-- 2025-06-25T13:39:47.180Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542203 AND AD_Tree_ID=10
;

-- Node name: `Invoice verifcation`
-- 2025-06-25T13:39:47.221Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541712 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice Candidates`
-- 2025-06-25T13:39:47.262Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000104 AND AD_Tree_ID=10
;

-- Node name: `Purchase Invoice Candidates`
-- 2025-06-25T13:39:47.302Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541537 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice`
-- 2025-06-25T13:39:47.342Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000085 AND AD_Tree_ID=10
;

-- Node name: `Purchase Invoice`
-- 2025-06-25T13:39:47.382Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000086 AND AD_Tree_ID=10
;

-- Node name: `Customs Invoice`
-- 2025-06-25T13:39:47.422Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541271 AND AD_Tree_ID=10
;

-- Node name: `Shipment Line to Customs Invoice Line`
-- 2025-06-25T13:39:47.462Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541418 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-06-25T13:39:47.502Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000059 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-06-25T13:39:47.543Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000067 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-06-25T13:39:47.587Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000077 AND AD_Tree_ID=10
;

-- Node name: `Invoicing Pool`
-- 2025-06-25T13:39:47.627Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542033 AND AD_Tree_ID=10
;

-- Node name: `Enqueue selection for invoicing and pdf printing`
-- 2025-06-25T13:39:47.674Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541886 AND AD_Tree_ID=10
;

-- Node name: `Commission Forecast`
-- 2025-06-25T13:39:47.716Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542214 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice Report (Excel)`
-- 2025-06-25T13:39:47.758Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542186 AND AD_Tree_ID=10
;

-- Node name: `Invoice Payment Allocations Report (Excel)`
-- 2025-06-25T13:39:47.798Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542227 AND AD_Tree_ID=10
;

-- Value: InvoicePaymentAllocations_Report_Excel
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-06-25T13:42:16.080Z
UPDATE AD_Process SET SQLStatement='Select * from getInvoicePaymentAllocations_Report( ''@DateInvoicedFrom/NULL@'', ''@DateInvoicedTo/NULL@)'';',Updated=TO_TIMESTAMP('2025-06-25 13:42:15.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585479
;

-- Value: InvoicePaymentAllocations_Report_Excel
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-06-25T13:43:08.275Z
UPDATE AD_Process SET SQLStatement='Select * from getInvoicePaymentAllocations_Report( ''@DateInvoicedFrom/NULL@'', ''@DateInvoicedTo/NULL@'')',Updated=TO_TIMESTAMP('2025-06-25 13:43:08.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585479
;

-- Value: InvoicePaymentAllocations_Report_Excel
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-06-25T13:47:03.079Z
UPDATE AD_Process SET SQLStatement='Select * from getInvoicePaymentAllocations_Report( ''@DateInvoicedFrom/1993-01-01@'', ''@DateInvoicedTo/2992-01-01@'')',Updated=TO_TIMESTAMP('2025-06-25 13:47:02.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585479
;

