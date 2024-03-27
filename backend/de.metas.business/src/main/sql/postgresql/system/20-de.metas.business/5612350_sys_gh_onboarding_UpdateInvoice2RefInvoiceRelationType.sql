
-- 2021-11-03T14:16:09.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Target='Credit Memo',Updated=TO_TIMESTAMP('2021-11-03 15:16:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540184
;

-- 2021-11-03T14:17:02.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='Invoice',Updated=TO_TIMESTAMP('2021-11-03 15:17:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540184
;

-- 2021-11-03T14:25:51.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Credit Memo',Updated=TO_TIMESTAMP('2021-11-03 15:25:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=540105
;

-- 2021-11-03T14:26:02.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Credit Memo',Updated=TO_TIMESTAMP('2021-11-03 15:26:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=540105
;

-- 2021-11-03T14:49:01.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from C_Invoice i  where  i.Ref_Invoice_ID=@C_Invoice_ID/-1@) ',Updated=TO_TIMESTAMP('2021-11-03 15:49:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540740
;

