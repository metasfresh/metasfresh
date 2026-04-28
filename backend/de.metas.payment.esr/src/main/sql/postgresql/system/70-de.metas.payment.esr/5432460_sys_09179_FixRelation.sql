-- 05.11.2015 13:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from x_esr_import_in_c_bankstatement_v v where  v.C_BankStatement_ID = C_BankStatement.C_BankStatement_ID)',Updated=TO_TIMESTAMP('2015-11-05 13:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540593
;

