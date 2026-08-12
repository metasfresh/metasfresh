
-- 18.04.2016 12:19:58 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@ErrorMsg@ =''''  | @C_Payment_ID@ > 0 | @ESR_Payment_Action@ = ''R''',Updated=TO_TIMESTAMP('2016-04-18 12:19:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547646
;

-- 18.04.2016 12:20:21 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@Processed@ = ''Y'' | @ESR_Payment_Action@ = ''R''',Updated=TO_TIMESTAMP('2016-04-18 12:20:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547647
;

