-- Function DDL: 5831690_sys_Product_Statistics_Year_Comparison_report_function_product_org.sql
-- Report process "Artikelstatistik Jahresvergleich" / "Product Statistics Year Comparison": the Product parameter offers only
-- the products of the chosen organisation or of organisation * (existing AD_Val_Rule 540377 "M_Product_of_@AD_Org_ID@_or_0"),
-- the same products the report lists when no product is chosen

UPDATE AD_Process_Para SET AD_Val_Rule_ID=540377,Updated=TO_TIMESTAMP('2026-10-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=543363
;
