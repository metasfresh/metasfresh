-- Run mode: SWING_CLIENT

-- Process: BusinessPartnerAccountSheetReport(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: p_IsSOTrx
-- 2025-11-13T13:47:06.953Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540528,Updated=TO_TIMESTAMP('2025-11-13 14:47:06.953','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=541756
;

-- Value: BusinessPartnerAccountSheetReport
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-11-14T15:17:16.070Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM BusinessPartnerAccountSheetReport_Union(p_c_bpartner_id => @C_BPartner_ID@,p_dateFrom => ''@DateFrom@''::date, p_dateTo => ''@DateTo@''::date, p_ad_client_id => @#AD_Client_ID@, p_ad_org_id => @AD_Org_ID@, p_isSoTrx => @p_IsSOTrx/quotedIfNotDefault/NULL@, p_ad_language => ''@#AD_Language@'')',Updated=TO_TIMESTAMP('2025-11-14 16:17:15.878','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=584661
;

-- 2025-11-14T15:18:14.905Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(1106)
;

-- Value: BusinessPartnerAccountSheetReport
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-11-14T15:21:01.444Z
UPDATE AD_Process SET Description='Verkaufstransaktion parameter: Legt fest, welche Transaktionsarten im Bericht berücksichtigt werden:
* Ja → Nur Verkaufstransaktionen
* Nein → Nur Einkaufstransaktionen
* Leer → Verkaufs- und Einkaufstransaktionen
',Updated=TO_TIMESTAMP('2025-11-14 16:21:01.258','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=584661
;

-- 2025-11-14T15:21:01.512Z
UPDATE AD_Process_Trl trl SET Description='Verkaufstransaktion parameter: Legt fest, welche Transaktionsarten im Bericht berücksichtigt werden:
* Ja → Nur Verkaufstransaktionen
* Nein → Nur Einkaufstransaktionen
* Leer → Verkaufs- und Einkaufstransaktionen
' WHERE AD_Process_ID=584661 AND AD_Language='de_DE'
;

-- Name: BusinessPartnerAccountSheetReport
-- Action Type: P
-- Process: BusinessPartnerAccountSheetReport(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-11-14T15:21:01.824Z
UPDATE AD_Menu SET Description='Verkaufstransaktion parameter: Legt fest, welche Transaktionsarten im Bericht berücksichtigt werden:
* Ja → Nur Verkaufstransaktionen
* Nein → Nur Einkaufstransaktionen
* Leer → Verkaufs- und Einkaufstransaktionen
', IsActive='Y', Name='BusinessPartnerAccountSheetReport',Updated=TO_TIMESTAMP('2025-11-14 16:21:01.824','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Menu_ID=541440
;

-- 2025-11-14T15:21:01.888Z
UPDATE AD_Menu_Trl trl SET Description='Verkaufstransaktion parameter: Legt fest, welche Transaktionsarten im Bericht berücksichtigt werden:
* Ja → Nur Verkaufstransaktionen
* Nein → Nur Einkaufstransaktionen
* Leer → Verkaufs- und Einkaufstransaktionen
',Name='BusinessPartnerAccountSheetReport' WHERE AD_Menu_ID=541440 AND AD_Language='de_DE'
;

-- Process: BusinessPartnerAccountSheetReport(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-11-14T15:21:43.319Z
UPDATE AD_Process_Trl SET Description='Sales transaction parameter: Determines which transaction types are included in the report:

* Yes → Only sales transactions
* No → Only purchase transactions
* Blank → Sales and purchase transactions',Updated=TO_TIMESTAMP('2025-11-14 16:21:43.319','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584661
;

-- 2025-11-13T10:17:45.774Z
UPDATE AD_Menu_Trl SET Name='Geschäftspartner Kontenblatt',Updated=TO_TIMESTAMP('2025-11-13 11:17:45.607','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Menu_ID=541440
;

-- 2025-11-13T10:17:45.830Z
UPDATE AD_Menu SET Name='Geschäftspartner Kontenblatt' WHERE AD_Menu_ID=541440
;

-- Process: BusinessPartnerAccountSheetReport(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: IsSOTrx
-- 2025-11-14T16:17:48.849Z
UPDATE AD_Process_Para SET DefaultValue='Y', IsMandatory='N',Updated=TO_TIMESTAMP('2025-11-14 17:17:48.849','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=541756
;
