
-- 16.07.2015 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='EXISTS (SELECT 1 FROM C_BP_BankAccount ba, C_Bank b WHERE ba.C_BP_BankAccount_ID=C_BankStatement.C_BP_BankAccount_ID AND b.C_Bank_ID=ba.C_Bank_ID AND b.IsCashBank=''Y'')',Updated=TO_TIMESTAMP('2015-07-16 14:41:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53392
;



-- 16.07.2015 15:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2015-07-16 15:05:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=10780
;



-- 16.07.2015 15:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-07-16 15:29:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540069
;

-- 16.07.2015 15:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_Value_ID=540069,Updated=TO_TIMESTAMP('2015-07-16 15:29:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=61500
;

-- 16.07.2015 15:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Val_Rule_ID=540276,Updated=TO_TIMESTAMP('2015-07-16 15:29:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=61500
;



-- 16.07.2015 15:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-07-16 15:35:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540069
;

-- 16.07.2015 15:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2015-07-16 15:42:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4917
;

-- 16.07.2015 15:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2015-07-16 15:43:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4917
;


-- 16.07.2015 15:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2015-07-16 15:44:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=61500
;

