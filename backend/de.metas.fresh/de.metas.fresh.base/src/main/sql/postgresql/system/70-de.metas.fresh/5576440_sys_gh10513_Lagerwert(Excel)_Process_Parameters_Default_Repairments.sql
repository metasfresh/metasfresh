-- 2021-01-14T14:19:14.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from report.M_Cost_CostPrice_Function(''@keydate@''::date, @Parameter_M_Product_ID/NULL@, @parameter_M_Warehouse_ID/NULL@, ''Y'', ''@#AD_Language@'', @AD_Client_ID@, @AD_Org_ID@ )',Updated=TO_TIMESTAMP('2021-01-14 16:19:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584744
;

-- 2021-01-14T14:19:34.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from report.M_Cost_CostPrice_Function(''@keydate@''::date, @Parameter_M_Product_ID/-1@, @Parameter_M_Warehouse_ID/-1@, ''Y'', ''@#AD_Language@'', @AD_Client_ID@, @AD_Org_ID@ )',Updated=TO_TIMESTAMP('2021-01-14 16:19:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584744
;

