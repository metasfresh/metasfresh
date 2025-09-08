-- Value: Auswertung Produktkosten (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-11-22T12:02:54.722Z
UPDATE AD_Process SET SQLStatement='SELECT Date,param_organization AS AD_Org_ID, param_acctSchema AS C_AcctSchema_ID,ProductCategory,Product,CostElement,Cost FROM report.getCostsPerDate(''@DateAcct@''::date, @C_AcctSchema_ID@, @AD_Org_ID@, @M_Product_ID/null@, @M_Product_Category_ID/null@) order by productcategory, product',Updated=TO_TIMESTAMP('2023-11-21 13:02:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585339
;

