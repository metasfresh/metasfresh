-- Value: Commission Forecast (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-03-14T14:06:26.275Z
UPDATE AD_Process SET SQLStatement='select * from de_metas_endcustomer_fresh_reports.Docs_Purchase_Commission_Forecast(@C_BPartner_SalesRep_ID/NULL@,''@CommissionDate_From/1900-01-01@''::date,''@CommissionDate_To/9999-12-31@''::date, ''@#AD_Language@'')',Updated=TO_TIMESTAMP('2025-03-14 15:06:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585456
;

