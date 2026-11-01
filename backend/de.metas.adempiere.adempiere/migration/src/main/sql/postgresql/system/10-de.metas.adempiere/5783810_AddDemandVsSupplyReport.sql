-- 2026-01-16T11:44:44.280Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584407,0,'CurrentVendorName',TO_TIMESTAMP('2026-01-16 11:44:43.214000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','CurrentVendorName','CurrentVendorName',TO_TIMESTAMP('2026-01-16 11:44:43.214000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-16T11:44:44.296Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584407 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CurrentVendorName
-- 2026-01-16T11:45:06.964Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Aktueller Lieferant', PrintName='Aktueller Lieferant',Updated=TO_TIMESTAMP('2026-01-16 11:45:06.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584407 AND AD_Language='de_CH'
;

-- 2026-01-16T11:45:06.966Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T11:45:07.217Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584407,'de_CH')
;

-- Element: CurrentVendorName
-- 2026-01-16T11:45:14.353Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Aktueller Lieferant', PrintName='Aktueller Lieferant',Updated=TO_TIMESTAMP('2026-01-16 11:45:14.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584407 AND AD_Language='de_DE'
;

-- 2026-01-16T11:45:14.354Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T11:45:15.955Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584407,'de_DE')
;

-- 2026-01-16T11:45:15.957Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584407,'de_DE')
;

-- Element: CurrentVendorName
-- 2026-01-16T11:45:26.797Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Current Vendor Name', PrintName='Current Vendor Name',Updated=TO_TIMESTAMP('2026-01-16 11:45:26.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584407 AND AD_Language='en_US'
;

-- 2026-01-16T11:45:26.799Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T11:45:27.002Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584407,'en_US')
;

-- 2026-01-16T11:46:25.888Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584408,0,'CurrentVendorPurchasePrice',TO_TIMESTAMP('2026-01-16 11:46:25.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','CurrentVendorPurchasePrice','CurrentVendorPurchasePrice',TO_TIMESTAMP('2026-01-16 11:46:25.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-16T11:46:25.891Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584408 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CurrentVendorPurchasePrice
-- 2026-01-16T11:46:40.391Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Current Vendor Purchase Price', PrintName='Current Vendor Purchase Price',Updated=TO_TIMESTAMP('2026-01-16 11:46:40.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584408 AND AD_Language='en_US'
;

-- 2026-01-16T11:46:40.393Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T11:46:40.596Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584408,'en_US')
;

-- Element: CurrentVendorPurchasePrice
-- 2026-01-16T11:46:46.295Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Aktueller Einkaufspreis', PrintName='Aktueller Einkaufspreis',Updated=TO_TIMESTAMP('2026-01-16 11:46:46.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584408 AND AD_Language='de_DE'
;

-- 2026-01-16T11:46:46.296Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T11:46:46.638Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584408,'de_DE')
;

-- 2026-01-16T11:46:46.640Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584408,'de_DE')
;

-- Element: CurrentVendorPurchasePrice
-- 2026-01-16T11:46:54.406Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Aktueller Einkaufspreis', PrintName='Aktueller Einkaufspreis',Updated=TO_TIMESTAMP('2026-01-16 11:46:54.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584408 AND AD_Language='de_CH'
;

-- 2026-01-16T11:46:54.408Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T11:46:54.644Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584408,'de_CH')
;

-- Value: SupplyVsDemand_Product_Info
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2026-01-16T11:52:06.898Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585558,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2026-01-16 11:52:06.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'SupplyVsDemand_Product_Info','json','N','N','xls','Excel',TO_TIMESTAMP('2026-01-16 11:52:06.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SupplyVsDemand_Product_Info')
;

-- 2026-01-16T11:52:06.906Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585558 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: SupplyVsDemand_Product_Info
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2026-01-16T11:53:22.708Z
UPDATE AD_Process SET SQLStatement='select * from report.SupplyVsDemand_Product_Info(p_date_from => ''@DateFrom@''::date, p_date_to => ''@DateTo@''::date);',Updated=TO_TIMESTAMP('2026-01-16 11:53:22.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585558
;

-- Process: SupplyVsDemand_Product_Info(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateFrom
-- 2026-01-16T11:54:04.472Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,585558,543094,15,'DateFrom',TO_TIMESTAMP('2026-01-16 11:54:04.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Startdatum eines Abschnittes','D',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','Y','N','Datum von',10,TO_TIMESTAMP('2026-01-16 11:54:04.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-16T11:54:04.478Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543094 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: SupplyVsDemand_Product_Info(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateFrom
-- 2026-01-16T11:54:11.505Z
UPDATE AD_Process_Para SET DefaultValue='@Date@',Updated=TO_TIMESTAMP('2026-01-16 11:54:11.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543094
;

-- Process: SupplyVsDemand_Product_Info(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateTo
-- 2026-01-16T11:54:59.735Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,585558,543097,15,'DateTo',TO_TIMESTAMP('2026-01-16 11:54:59.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Enddatum eines Abschnittes','D',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','Y','N','Datum bis',20,TO_TIMESTAMP('2026-01-16 11:54:59.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-16T11:54:59.738Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543097 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: SupplyVsDemand_Product_Info(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateTo
-- 2026-01-16T11:55:04.911Z
UPDATE AD_Process_Para SET DefaultValue='@Date@',Updated=TO_TIMESTAMP('2026-01-16 11:55:04.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543097
;

-- Value: SupplyVsDemand_Product_Info
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2026-01-16T11:57:44.344Z
UPDATE AD_Process SET Name='Supply vs. Demand - Product Info (Excel)',Updated=TO_TIMESTAMP('2026-01-16 11:57:44.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585558
;

-- 2026-01-16T11:57:44.345Z
UPDATE AD_Process_Trl trl SET Name='Supply vs. Demand - Product Info (Excel)' WHERE AD_Process_ID=585558 AND AD_Language='de_DE'
;

-- Process: SupplyVsDemand_Product_Info(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-01-16T11:58:08.859Z
UPDATE AD_Process_Trl SET Description='Comparative analysis of warehouse supply (Current Stock, Reserved, and Inbound) against actual sales demand (Qty Sold) for a defined period. Includes vendor-specific purchase prices and internal cost prices to support data-driven procurement decisions.', IsTranslated='Y', Name='Supply vs. Demand - Product Info (Excel)',Updated=TO_TIMESTAMP('2026-01-16 11:58:08.859000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585558
;

-- 2026-01-16T11:58:08.860Z
UPDATE AD_Process base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: SupplyVsDemand_Product_Info(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-01-16T11:58:37.560Z
UPDATE AD_Process_Trl SET Description='Vergleichende Analyse des Lagerangebots (aktueller Bestand, Reserviert und Zulauf) gegenüber dem tatsächlichen Verkaufsbedarf (verkaufte Menge) für einen definierten Zeitraum. Enthält lieferantenspezifische Einkaufspreise und interne Einstandspreise zur Unterstützung fundierter Beschaffungsentscheidungen.', IsTranslated='Y', Name='Bestand vs. Bedarf - Produktinfo (Excel)',Updated=TO_TIMESTAMP('2026-01-16 11:58:37.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language IN ('de_CH', 'de_DE') AND AD_Process_ID=585558
;

-- 2026-01-16T11:58:37.561Z
UPDATE AD_Process base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T11:58:37.561Z
UPDATE AD_Process base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language IN ('de_CH', 'de_DE') AND trl.AD_Language=getBaseLanguage()
;

