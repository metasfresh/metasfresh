-- Run mode: SWING_CLIENT

-- Process: Sales Trace Excel (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-02-01T12:11:05.527Z
DELETE FROM AD_Process_Trl WHERE AD_Language='fr_CH' AND AD_Process_ID=540710
;

-- Process: Sales Trace Excel (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-02-01T12:11:31.398Z
UPDATE AD_Process_Trl SET Description='Traces sold products back to their procurement source. Links sales orders to vendor receipts via handling unit tracking or direct SO-PO allocations. 
Shows customer order details alongside the corresponding supplier/purchase information for supply chain traceability.', IsTranslated='Y', Name='Sales-to-Purchase Trace (Excel)',Updated=TO_TIMESTAMP('2026-02-01 12:11:31.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=540710
;

-- 2026-02-01T12:11:31.399Z
UPDATE AD_Process base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Sales Trace Excel (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-02-01T12:12:08.524Z
UPDATE AD_Process_Trl SET Description='Verfolgt verkaufte Produkte zurück zu deren Beschaffungsquelle. Verknüpft Kundenaufträge mit Wareneingängen via Handling-Unit-Tracking oder direkten AB-BB-Zuordnungen. 
Zeigt Auftragsdetails zusammen mit Lieferanten-/Einkaufsinformationen für Supply-Chain-Rückverfolgbarkeit.', IsTranslated='Y', Name='Verkauf-Einkauf-Rückverfolgung (Excel)',Updated=TO_TIMESTAMP('2026-02-01 12:12:08.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540710
;

-- 2026-02-01T12:12:08.525Z
UPDATE AD_Process base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: Sales_To_Purchase_Trace_Excel
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/sales_trace/report.xls
-- 2026-02-01T12:12:26.585Z
UPDATE AD_Process SET Value='Sales_To_Purchase_Trace_Excel',Updated=TO_TIMESTAMP('2026-02-01 12:12:26.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540710
;

-- Value: Sales_To_Purchase_Trace_Excel
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/sales_trace/report.xls
-- 2026-02-01T12:12:37.238Z
UPDATE AD_Process SET Name='Verkauf-Einkauf-Rückverfolgung (Excel)',Updated=TO_TIMESTAMP('2026-02-01 12:12:37.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540710
;

-- 2026-02-01T12:12:37.240Z
UPDATE AD_Process_Trl trl SET Name='Verkauf-Einkauf-Rückverfolgung (Excel)' WHERE AD_Process_ID=540710 AND AD_Language='de_DE'
;

-- Name: Verkauf-Einkauf-Rückverfolgung (Excel)
-- Action Type: R
-- Report: Sales_To_Purchase_Trace_Excel(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-02-01T12:12:37.251Z
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Verkauf-Einkauf-Rückverfolgung (Excel)',Updated=TO_TIMESTAMP('2026-02-01 12:12:37.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540722
;

-- 2026-02-01T12:12:37.253Z
UPDATE AD_Menu_Trl trl SET Name='Verkauf-Einkauf-Rückverfolgung (Excel)' WHERE AD_Menu_ID=540722 AND AD_Language='de_DE'
;

-- Name: Verkauf-Einkauf-Rückverfolgung (Excel)
-- Action Type: R
-- Report: Sales_To_Purchase_Trace_Excel(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-02-01T12:12:37.260Z
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Verkauf-Einkauf-Rückverfolgung (Excel)',Updated=TO_TIMESTAMP('2026-02-01 12:12:37.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540957
;

-- 2026-02-01T12:12:37.261Z
UPDATE AD_Menu_Trl trl SET Name='Verkauf-Einkauf-Rückverfolgung (Excel)' WHERE AD_Menu_ID=540957 AND AD_Language='de_DE'
;

-- Value: Sales_To_Purchase_Trace_Excel
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/sales_trace/report.xls
-- 2026-02-01T12:12:53.657Z
UPDATE AD_Process SET Description='Verfolgt verkaufte Produkte zurück zu deren Beschaffungsquelle. Verknüpft Kundenaufträge mit Wareneingängen via Handling-Unit-Tracking oder direkten AB-BB-Zuordnungen. 
Zeigt Auftragsdetails zusammen mit Lieferanten-/Einkaufsinformationen für Supply-Chain-Rückverfolgbarkeit.',Updated=TO_TIMESTAMP('2026-02-01 12:12:53.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540710
;

-- 2026-02-01T12:12:53.658Z
UPDATE AD_Process_Trl trl SET Description='Verfolgt verkaufte Produkte zurück zu deren Beschaffungsquelle. Verknüpft Kundenaufträge mit Wareneingängen via Handling-Unit-Tracking oder direkten AB-BB-Zuordnungen. 
Zeigt Auftragsdetails zusammen mit Lieferanten-/Einkaufsinformationen für Supply-Chain-Rückverfolgbarkeit.' WHERE AD_Process_ID=540710 AND AD_Language='de_DE'
;

-- Name: Verkauf-Einkauf-Rückverfolgung (Excel)
-- Action Type: R
-- Report: Sales_To_Purchase_Trace_Excel(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-02-01T12:12:53.664Z
UPDATE AD_Menu SET Description='Verfolgt verkaufte Produkte zurück zu deren Beschaffungsquelle. Verknüpft Kundenaufträge mit Wareneingängen via Handling-Unit-Tracking oder direkten AB-BB-Zuordnungen. 
Zeigt Auftragsdetails zusammen mit Lieferanten-/Einkaufsinformationen für Supply-Chain-Rückverfolgbarkeit.', IsActive='Y', Name='Verkauf-Einkauf-Rückverfolgung (Excel)',Updated=TO_TIMESTAMP('2026-02-01 12:12:53.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540722
;

-- 2026-02-01T12:12:53.665Z
UPDATE AD_Menu_Trl trl SET Description='Verfolgt verkaufte Produkte zurück zu deren Beschaffungsquelle. Verknüpft Kundenaufträge mit Wareneingängen via Handling-Unit-Tracking oder direkten AB-BB-Zuordnungen. 
Zeigt Auftragsdetails zusammen mit Lieferanten-/Einkaufsinformationen für Supply-Chain-Rückverfolgbarkeit.' WHERE AD_Menu_ID=540722 AND AD_Language='de_DE'
;

-- Name: Verkauf-Einkauf-Rückverfolgung (Excel)
-- Action Type: R
-- Report: Sales_To_Purchase_Trace_Excel(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-02-01T12:12:53.670Z
UPDATE AD_Menu SET Description='Verfolgt verkaufte Produkte zurück zu deren Beschaffungsquelle. Verknüpft Kundenaufträge mit Wareneingängen via Handling-Unit-Tracking oder direkten AB-BB-Zuordnungen. 
Zeigt Auftragsdetails zusammen mit Lieferanten-/Einkaufsinformationen für Supply-Chain-Rückverfolgbarkeit.', IsActive='Y', Name='Verkauf-Einkauf-Rückverfolgung (Excel)',Updated=TO_TIMESTAMP('2026-02-01 12:12:53.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540957
;

-- 2026-02-01T12:12:53.671Z
UPDATE AD_Menu_Trl trl SET Description='Verfolgt verkaufte Produkte zurück zu deren Beschaffungsquelle. Verknüpft Kundenaufträge mit Wareneingängen via Handling-Unit-Tracking oder direkten AB-BB-Zuordnungen. 
Zeigt Auftragsdetails zusammen mit Lieferanten-/Einkaufsinformationen für Supply-Chain-Rückverfolgbarkeit.' WHERE AD_Menu_ID=540957 AND AD_Language='de_DE'
;

-- Process: Sales_To_Purchase_Trace_Excel(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_Year_ID
-- 2026-02-01T12:14:38.315Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=198,Updated=TO_TIMESTAMP('2026-02-01 12:14:38.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=540983
;

-- Name: C_Year Active
-- 2026-02-01T12:17:40.739Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540767,'IsActive=''Y''',TO_TIMESTAMP('2026-02-01 12:17:40.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'displays years with IsActive=''Y''','D','Y','C_Year Active','S',TO_TIMESTAMP('2026-02-01 12:17:40.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Process: Sales_To_Purchase_Trace_Excel(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_Year_ID
-- 2026-02-01T12:18:09.111Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540767, IsMandatory='Y',Updated=TO_TIMESTAMP('2026-02-01 12:18:09.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=540983
;

-- Element: C_Period_St_ID
-- 2026-02-01T12:20:12.684Z
UPDATE AD_Element_Trl SET Name='Ab Periode (inklusiv)', PrintName='Ab Periode (inklusiv)',Updated=TO_TIMESTAMP('2026-02-01 12:20:12.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584470 AND AD_Language='de_CH'
;

-- 2026-02-01T12:20:12.686Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:20:13.121Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584470,'de_CH')
;

-- Element: C_Period_St_ID
-- 2026-02-01T12:20:24.375Z
UPDATE AD_Element_Trl SET Name='Ab Periode (inklusiv)', PrintName='Ab Periode (inklusiv)',Updated=TO_TIMESTAMP('2026-02-01 12:20:24.375000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584470 AND AD_Language='de_DE'
;

-- 2026-02-01T12:20:24.377Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:20:25.735Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584470,'de_DE')
;

-- 2026-02-01T12:20:25.738Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584470,'de_DE')
;

-- Element: C_Period_St_ID
-- 2026-02-01T12:20:41.268Z
UPDATE AD_Element_Trl SET Name='From period (inclusive)', PrintName='From period (inclusive)',Updated=TO_TIMESTAMP('2026-02-01 12:20:41.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584470 AND AD_Language='en_US'
;

-- 2026-02-01T12:20:41.270Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:20:41.633Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584470,'en_US')
;

-- Element: C_Period_End_ID
-- 2026-02-01T12:21:10.597Z
UPDATE AD_Element_Trl SET Name='Bis Periode (inklusiv)', PrintName='Bis Periode (inklusiv)',Updated=TO_TIMESTAMP('2026-02-01 12:21:10.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584471 AND AD_Language='de_CH'
;

-- 2026-02-01T12:21:10.598Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:21:11.008Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584471,'de_CH')
;

-- Element: C_Period_End_ID
-- 2026-02-01T12:21:15.372Z
UPDATE AD_Element_Trl SET Name='Bis Periode (inklusiv)', PrintName='Bis Periode (inklusiv)',Updated=TO_TIMESTAMP('2026-02-01 12:21:15.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584471 AND AD_Language='de_DE'
;

-- 2026-02-01T12:21:15.375Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:21:16.115Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584471,'de_DE')
;

-- 2026-02-01T12:21:16.116Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584471,'de_DE')
;

-- Element: C_Period_End_ID
-- 2026-02-01T12:21:26.979Z
UPDATE AD_Element_Trl SET Name='To period (inclusive)', PrintName='To period (inclusive)',Updated=TO_TIMESTAMP('2026-02-01 12:21:26.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584471 AND AD_Language='en_US'
;

-- 2026-02-01T12:21:26.981Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:21:27.343Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584471,'en_US')
;

-- Value: Sales_To_Purchase_Trace (Excel)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/sales_trace/report.xls
-- 2026-02-01T12:23:07.476Z
UPDATE AD_Process SET Value='Sales_To_Purchase_Trace (Excel)',Updated=TO_TIMESTAMP('2026-02-01 12:23:07.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540710
;

-- Element: DeliveryDateTo
-- 2026-02-01T12:24:00.937Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferdatum bis (inklusiv)', PrintName='Lieferdatum bis (inklusiv)',Updated=TO_TIMESTAMP('2026-02-01 12:24:00.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583393 AND AD_Language='de_CH'
;

-- 2026-02-01T12:24:00.938Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:24:01.524Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583393,'de_CH')
;

-- Element: DeliveryDateTo
-- 2026-02-01T12:24:05.376Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferdatum bis (inklusiv)', PrintName='Lieferdatum bis (inklusiv)',Updated=TO_TIMESTAMP('2026-02-01 12:24:05.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583393 AND AD_Language='de_DE'
;

-- 2026-02-01T12:24:05.379Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:24:05.929Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583393,'de_DE')
;

-- 2026-02-01T12:24:05.931Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583393,'de_DE')
;

-- Element: DeliveryDateTo
-- 2026-02-01T12:24:17.683Z
UPDATE AD_Element_Trl SET Name='Delivery date until (inclusive)', PrintName='Delivery date until (inclusive)',Updated=TO_TIMESTAMP('2026-02-01 12:24:17.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583393 AND AD_Language='en_US'
;

-- 2026-02-01T12:24:17.686Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:24:18.055Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583393,'en_US')
;

-- Element: DeliveryDateFrom
-- 2026-02-01T12:24:35.886Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferdatum ab (inklusiv)', PrintName='Lieferdatum ab (inklusiv)',Updated=TO_TIMESTAMP('2026-02-01 12:24:35.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583392 AND AD_Language='de_CH'
;

-- 2026-02-01T12:24:35.887Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:24:36.212Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583392,'de_CH')
;

-- Element: DeliveryDateFrom
-- 2026-02-01T12:24:41.837Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferdatum ab (inklusiv)', PrintName='Lieferdatum ab (inklusiv)',Updated=TO_TIMESTAMP('2026-02-01 12:24:41.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583392 AND AD_Language='de_DE'
;

-- 2026-02-01T12:24:41.839Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:24:42.328Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583392,'de_DE')
;

-- 2026-02-01T12:24:42.329Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583392,'de_DE')
;

-- Element: DeliveryDateFrom
-- 2026-02-01T12:24:56.806Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Delivery date from (inclusive)', PrintName='Delivery date from (inclusive)',Updated=TO_TIMESTAMP('2026-02-01 12:24:56.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583392 AND AD_Language='en_US'
;

-- 2026-02-01T12:24:56.808Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:24:57.130Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583392,'en_US')
;

-- Element: DateInvoicedFrom
-- 2026-02-01T12:25:22.149Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnungsdatum ab (inklusiv)', PrintName='Rechnungsdatum ab (inklusiv)',Updated=TO_TIMESTAMP('2026-02-01 12:25:22.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583390 AND AD_Language='de_CH'
;

-- 2026-02-01T12:25:22.150Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:25:22.446Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583390,'de_CH')
;

-- Element: DateInvoicedFrom
-- 2026-02-01T12:25:25.661Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnungsdatum ab (inklusiv)', PrintName='Rechnungsdatum ab (inklusiv)',Updated=TO_TIMESTAMP('2026-02-01 12:25:25.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583390 AND AD_Language='de_DE'
;

-- 2026-02-01T12:25:25.662Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:25:26.183Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583390,'de_DE')
;

-- 2026-02-01T12:25:26.184Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583390,'de_DE')
;

-- Element: DateInvoicedFrom
-- 2026-02-01T12:25:43.708Z
UPDATE AD_Element_Trl SET Name=' Invoice date from (inclusive)', PrintName=' Invoice date from (inclusive)',Updated=TO_TIMESTAMP('2026-02-01 12:25:43.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583390 AND AD_Language='en_US'
;

-- 2026-02-01T12:25:43.709Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:25:44.157Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583390,'en_US')
;

-- Element: DateInvoicedTo
-- 2026-02-01T12:26:17.227Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnungsdatum bis (inklusiv)', PrintName='Rechnungsdatum bis (inklusiv)',Updated=TO_TIMESTAMP('2026-02-01 12:26:17.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583391 AND AD_Language='de_CH'
;

-- 2026-02-01T12:26:17.229Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:26:17.560Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583391,'de_CH')
;

-- Element: DateInvoicedTo
-- 2026-02-01T12:26:21.249Z
UPDATE AD_Element_Trl SET Name='Rechnungsdatum bis (inklusiv)', PrintName='Rechnungsdatum bis (inklusiv)',Updated=TO_TIMESTAMP('2026-02-01 12:26:21.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583391 AND AD_Language='de_DE'
;

-- 2026-02-01T12:26:21.251Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:26:21.760Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583391,'de_DE')
;

-- 2026-02-01T12:26:21.762Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583391,'de_DE')
;

-- Element: DateInvoicedTo
-- 2026-02-01T12:26:32.059Z
UPDATE AD_Element_Trl SET Name='Invoice date until (inclusive)', PrintName='Invoice date until (inclusive)',Updated=TO_TIMESTAMP('2026-02-01 12:26:32.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583391 AND AD_Language='en_US'
;

-- 2026-02-01T12:26:32.061Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-01T12:26:32.389Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583391,'en_US')
;

