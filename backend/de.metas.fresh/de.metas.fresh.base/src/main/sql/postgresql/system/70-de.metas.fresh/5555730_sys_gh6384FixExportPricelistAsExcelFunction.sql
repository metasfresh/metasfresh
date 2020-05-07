-- 2020-03-30T11:39:51.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.Current_Vs_Previous_Pricelist_Comparison_Report();
',Updated=TO_TIMESTAMP('2020-03-30 14:39:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584676
;