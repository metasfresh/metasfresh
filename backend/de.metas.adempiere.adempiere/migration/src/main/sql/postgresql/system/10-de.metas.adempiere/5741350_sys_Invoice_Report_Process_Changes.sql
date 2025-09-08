
-- 2024-12-10T08:57:16.324156395Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583390,0,TO_TIMESTAMP('2024-12-10 09:57:16.323','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Rechnungsdatum von','Rechnungsdatum von',TO_TIMESTAMP('2024-12-10 09:57:16.323','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-10T08:57:16.326532637Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583390 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-12-10T08:57:23.225252535Z
UPDATE AD_Element SET ColumnName='DateInvoicedFrom',Updated=TO_TIMESTAMP('2024-12-10 09:57:23.225','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583390
;

-- 2024-12-10T08:57:23.226033163Z
UPDATE AD_Column SET ColumnName='DateInvoicedFrom' WHERE AD_Element_ID=583390
;

-- 2024-12-10T08:57:23.226446416Z
UPDATE AD_Process_Para SET ColumnName='DateInvoicedFrom' WHERE AD_Element_ID=583390
;

-- 2024-12-10T08:57:23.228401135Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583390,'de_DE') 
;

-- 2024-12-10T08:57:29.675987316Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2024-12-10 09:57:29.675','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583390
;

-- 2024-12-10T08:57:29.677589141Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583390,'de_DE') 
;

-- 2024-12-10T08:58:07.207422334Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583391,0,TO_TIMESTAMP('2024-12-10 09:58:07.206','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Rechnungsdatum','Rechnungsdatum bis',TO_TIMESTAMP('2024-12-10 09:58:07.206','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-10T08:58:07.208264782Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583391 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-12-10T08:58:08.614425840Z
UPDATE AD_Element SET Name='Rechnungsdatum bis',Updated=TO_TIMESTAMP('2024-12-10 09:58:08.614','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583391
;

-- 2024-12-10T08:58:08.614821444Z
UPDATE AD_Element_Trl trl SET Name='Rechnungsdatum bis' WHERE AD_Element_ID=583391 AND AD_Language='de_DE'
;

-- 2024-12-10T08:58:08.615781713Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583391,'de_DE') 
;

-- 2024-12-10T08:58:15.263390473Z
UPDATE AD_Element SET ColumnName='DateInvoicedTo',Updated=TO_TIMESTAMP('2024-12-10 09:58:15.263','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583391
;

-- 2024-12-10T08:58:15.263911848Z
UPDATE AD_Column SET ColumnName='DateInvoicedTo' WHERE AD_Element_ID=583391
;

-- 2024-12-10T08:58:15.264271362Z
UPDATE AD_Process_Para SET ColumnName='DateInvoicedTo' WHERE AD_Element_ID=583391
;

-- 2024-12-10T08:58:15.264951918Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583391,'de_DE') 
;

-- 2024-12-10T08:58:17.770586704Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2024-12-10 09:58:17.77','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583391
;

-- 2024-12-10T08:58:17.771310921Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583391,'de_DE') 
;

-- 2024-12-10T08:59:19.109727981Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583392,0,'DeliveryDateFrom',TO_TIMESTAMP('2024-12-10 09:59:19.108','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Lieferdatum von','Lieferdatum von',TO_TIMESTAMP('2024-12-10 09:59:19.108','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-10T08:59:19.110708560Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583392 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-12-10T08:59:21.136387943Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2024-12-10 09:59:21.136','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583392
;

-- 2024-12-10T08:59:21.137222620Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583392,'de_DE') 
;

-- 2024-12-10T08:59:38.257425369Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583393,0,'DeliveryDateTo',TO_TIMESTAMP('2024-12-10 09:59:38.256','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Lieferdatum bis','Lieferdatum bis',TO_TIMESTAMP('2024-12-10 09:59:38.256','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-10T08:59:38.257954254Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583393 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-12-10T08:59:40.412610773Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2024-12-10 09:59:40.412','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583393
;

-- 2024-12-10T08:59:40.413518621Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583393,'de_DE') 
;

-- Element: DeliveryDateTo
-- 2024-12-10T09:00:00.769996433Z
UPDATE AD_Element_Trl SET Name='Delivery Date To',Updated=TO_TIMESTAMP('2024-12-10 10:00:00.769','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583393 AND AD_Language='en_US'
;

-- 2024-12-10T09:00:00.770933602Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583393,'en_US') 
;

-- Element: DeliveryDateTo
-- 2024-12-10T09:00:02.141790447Z
UPDATE AD_Element_Trl SET PrintName='Delivery Date To',Updated=TO_TIMESTAMP('2024-12-10 10:00:02.141','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583393 AND AD_Language='en_US'
;

-- 2024-12-10T09:00:02.142444973Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583393,'en_US') 
;

-- Element: DeliveryDateTo
-- 2024-12-10T09:00:02.215334289Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-10 10:00:02.215','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583393 AND AD_Language='en_US'
;

-- 2024-12-10T09:00:02.215904785Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583393,'en_US') 
;

-- Element: DeliveryDateFrom
-- 2024-12-10T09:00:23.055211081Z
UPDATE AD_Element_Trl SET Name='Delivery Date From',Updated=TO_TIMESTAMP('2024-12-10 10:00:23.055','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583392 AND AD_Language='en_US'
;

-- 2024-12-10T09:00:23.056499403Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583392,'en_US') 
;

-- Element: DeliveryDateFrom
-- 2024-12-10T09:00:23.864739574Z
UPDATE AD_Element_Trl SET PrintName='Delivery Date From',Updated=TO_TIMESTAMP('2024-12-10 10:00:23.864','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583392 AND AD_Language='en_US'
;

-- 2024-12-10T09:00:23.865512432Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583392,'en_US') 
;

-- Element: DateInvoicedTo
-- 2024-12-10T09:00:39.972581614Z
UPDATE AD_Element_Trl SET Name='Date Invoiced To',Updated=TO_TIMESTAMP('2024-12-10 10:00:39.972','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583391 AND AD_Language='en_US'
;

-- 2024-12-10T09:00:39.973784376Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583391,'en_US') 
;

-- Element: DateInvoicedTo
-- 2024-12-10T09:00:40.681306203Z
UPDATE AD_Element_Trl SET PrintName='Date Invoiced To',Updated=TO_TIMESTAMP('2024-12-10 10:00:40.681','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583391 AND AD_Language='en_US'
;

-- 2024-12-10T09:00:40.681984219Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583391,'en_US') 
;

-- Element: DateInvoicedTo
-- 2024-12-10T09:00:42.227505945Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-10 10:00:42.227','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583391 AND AD_Language='en_US'
;

-- 2024-12-10T09:00:42.228321732Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583391,'en_US') 
;

-- Element: DateInvoicedTo
-- 2024-12-10T09:00:50.516944480Z
UPDATE AD_Element_Trl SET Name='Rechnungsdatum bis',Updated=TO_TIMESTAMP('2024-12-10 10:00:50.516','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583391 AND AD_Language='de_CH'
;

-- 2024-12-10T09:00:50.517583436Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583391,'de_CH') 
;

-- Element: DateInvoicedFrom
-- 2024-12-10T09:01:02.126176749Z
UPDATE AD_Element_Trl SET Name='DateInvoicedFrom',Updated=TO_TIMESTAMP('2024-12-10 10:01:02.126','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583390 AND AD_Language='en_US'
;

-- 2024-12-10T09:01:02.127413410Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583390,'en_US') 
;

-- Element: DateInvoicedFrom
-- 2024-12-10T09:01:05.311369654Z
UPDATE AD_Element_Trl SET Name='Date Invoiced From',Updated=TO_TIMESTAMP('2024-12-10 10:01:05.311','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583390 AND AD_Language='en_US'
;

-- 2024-12-10T09:01:05.312295843Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583390,'en_US') 
;

-- Element: DateInvoicedFrom
-- 2024-12-10T09:01:08.694344962Z
UPDATE AD_Element_Trl SET PrintName='Date Invoiced From',Updated=TO_TIMESTAMP('2024-12-10 10:01:08.694','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583390 AND AD_Language='en_US'
;

-- 2024-12-10T09:01:08.695089569Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583390,'en_US') 
;

-- Element: DateInvoicedFrom
-- 2024-12-10T09:01:08.762201781Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-10 10:01:08.762','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583390 AND AD_Language='en_US'
;

-- 2024-12-10T09:01:08.763137550Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583390,'en_US') 
;





-- Value: Sales Invoice Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-10T09:19:19.832319927Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM sales_invoice_excel_report(''@DateInvoicedFrom/1900-01-01@''::date, @DeliveryDate/quotedIfNotDefault/NULL@)',Updated=TO_TIMESTAMP('2024-12-10 10:19:19.832','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;

-- Value: Sales Invoice Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-10T09:20:01.627898287Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM sales_invoice_excel_report(''@DateInvoicedFrom/1900-01-01@''::date, ''@DateInvoicedTo/9999-12-31@''::date,@DeliveryDate/quotedIfNotDefault/NULL@)',Updated=TO_TIMESTAMP('2024-12-10 10:20:01.627','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;

-- Value: Sales Invoice Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-10T09:20:45.879516710Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM sales_invoice_excel_report(''@DateInvoicedFrom/1900-01-01@''::date, ''@DateInvoicedTo/9999-12-31@''::date, ''@DeliveryDate/1900-01-01@''::date, ''@DeliveryDate/9999-12-31@''::date ',Updated=TO_TIMESTAMP('2024-12-10 10:20:45.879','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;











-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateInvoicedFrom
-- 2024-12-10T09:44:23.272627199Z
UPDATE AD_Process_Para SET AD_Element_ID=583390, ColumnName='DateInvoicedFrom', Description=NULL, Help=NULL, Name='Rechnungsdatum von',Updated=TO_TIMESTAMP('2024-12-10 10:44:23.272','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542908
;

-- 2024-12-10T09:44:23.273346756Z
UPDATE AD_Process_Para_Trl trl SET Description=NULL,Help=NULL,Name='Rechnungsdatum von' WHERE AD_Process_Para_ID=542908 AND AD_Language='de_DE'
;

-- 2024-12-10T09:44:23.273729490Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583390) 
;

-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DeliveryDate
-- 2024-12-10T09:44:34.504247034Z
UPDATE AD_Process_Para SET AD_Element_ID=NULL,Updated=TO_TIMESTAMP('2024-12-10 10:44:34.504','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542909
;

-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateInvoicedTo
-- 2024-12-10T09:44:40.920651116Z
UPDATE AD_Process_Para SET AD_Element_ID=583391, ColumnName='DateInvoicedTo', Name='Rechnungsdatum bis',Updated=TO_TIMESTAMP('2024-12-10 10:44:40.92','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542909
;

-- 2024-12-10T09:44:40.921183401Z
UPDATE AD_Process_Para_Trl trl SET Name='Rechnungsdatum bis' WHERE AD_Process_Para_ID=542909 AND AD_Language='de_DE'
;

-- 2024-12-10T09:44:40.921720586Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583391) 
;

-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DeliveryDateFrom
-- 2024-12-10T09:44:58.082972184Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583392,0,585439,542910,15,'DeliveryDateFrom',TO_TIMESTAMP('2024-12-10 10:44:58.082','YYYY-MM-DD HH24:MI:SS.US'),100,'U',0,'Y','N','Y','N','N','N','Lieferdatum von',30,TO_TIMESTAMP('2024-12-10 10:44:58.082','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-10T09:44:58.083445099Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542910 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-12-10T09:44:58.085215835Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583392) 
;

-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DeliveryDateTo
-- 2024-12-10T09:45:16.263288820Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583393,0,585439,542911,15,'DeliveryDateTo',TO_TIMESTAMP('2024-12-10 10:45:16.262','YYYY-MM-DD HH24:MI:SS.US'),100,'U',0,'Y','N','Y','N','N','N','Lieferdatum bis',40,TO_TIMESTAMP('2024-12-10 10:45:16.262','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-10T09:45:16.263895156Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542911 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-12-10T09:45:16.264674303Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583393) 
;

-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DeliveryDateTo
-- 2024-12-10T09:45:21.091677015Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2024-12-10 10:45:21.091','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542911
;

-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DeliveryDateFrom
-- 2024-12-10T09:45:29.194324245Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2024-12-10 10:45:29.194','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542910
;











-- Value: Sales Invoice Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-10T09:51:27.990179121Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM sales_invoice_excel_report(''@DateInvoicedFrom/1900-01-01@''::date, ''@DateInvoicedTo/9999-12-31@''::date, ''@DeliveryDateFrom/1900-01-01@''::date, ''@DeliveryDateTo/9999-12-31@''::date) ',Updated=TO_TIMESTAMP('2024-12-10 10:51:27.99','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;






-- Value: Sales Invoice Report (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2024-12-10T09:54:23.044689572Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM sales_invoice_excel_report(''@DateInvoicedFrom/1900-01-01@''::date, ''@DateInvoicedTo/9999-12-31@''::date, ''@DeliveryDateFrom/1900-01-01@''::date, ''@DeliveryDateTo/9999-12-31@''::date )',Updated=TO_TIMESTAMP('2024-12-10 10:54:23.044','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585439
;