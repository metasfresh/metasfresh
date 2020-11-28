-- 2018-04-17T12:02:13.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.revenue_PO_report (''@Base_Period_Start@''::date, ''@Base_Period_End@''::date, @Comp_Period_Start/quotedIfNotDefault/NULL@::date, @Comp_Period_End/quotedIfNotDefault/NULL@::date, ''N'', NULL, NULL, NULL, NULL, NULL, @AD_Org_ID@, NULL, @C_BP_Group_ID/NULL@);',Updated=TO_TIMESTAMP('2018-04-17 12:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540946
;

