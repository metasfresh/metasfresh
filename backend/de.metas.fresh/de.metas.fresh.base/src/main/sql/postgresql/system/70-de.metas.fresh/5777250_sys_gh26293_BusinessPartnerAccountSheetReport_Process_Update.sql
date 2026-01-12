-- Run mode: SWING_CLIENT

-- Value: BusinessPartnerAccountSheetReport
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-11-14T15:17:16.070Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM BusinessPartnerAccountSheetReport_Union(p_c_bpartner_id => @C_BPartner_ID@,p_dateFrom => ''@DateFrom@''::date, p_dateTo => ''@DateTo@''::date, p_ad_client_id => @#AD_Client_ID@, p_ad_org_id => @AD_Org_ID@, p_isSoTrx => @IsSOTrx/quotedIfNotDefault/NULL@, p_ad_language => ''@#AD_Language@'')',Updated=TO_TIMESTAMP('2025-11-14 16:17:15.878','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=584661
;
