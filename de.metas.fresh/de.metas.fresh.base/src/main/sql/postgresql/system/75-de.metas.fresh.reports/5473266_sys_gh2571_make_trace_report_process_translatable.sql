
-- 2017-10-02T18:19:46.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.trace_report(@AD_Org_ID/NULL@, @C_Period_St_ID@, @C_Period_End_ID@,@C_Activity_ID/NULL@, @C_BPartner_ID/NULL@, @M_Product_ID/NULL@, ''N'', @M_AttributeSetInstance_ID/NULL@, ''@ad_language@'');',Updated=TO_TIMESTAMP('2017-10-02 18:19:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540711
;

-- 2017-10-02T18:19:59.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.trace_report(@AD_Org_ID/NULL@, @C_Period_St_ID@, @C_Period_End_ID@,@C_Activity_ID/NULL@, @C_BPartner_ID/NULL@, @M_Product_ID/NULL@, ''Y'', @M_AttributeSetInstance_ID/NULL@, ''@ad_language@'');',Updated=TO_TIMESTAMP('2017-10-02 18:19:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540710
;
