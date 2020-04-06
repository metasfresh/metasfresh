-- 2019-07-26T13:48:13.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-07-26 16:48:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568433
;

-- 2019-07-26T13:48:18.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('paypal_config','PayPal_PaymentApprovedCallbackUrl','VARCHAR(255)',null,null)
;

-- 2019-07-26T13:48:18.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('paypal_config','PayPal_PaymentApprovedCallbackUrl',null,'NULL',null)
;

-- 2019-07-26T14:03:03.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-07-26 17:03:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560173
;

-- 2019-07-26T14:03:37.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@PayPal_Sandbox/N@=N',Updated=TO_TIMESTAMP('2019-07-26 17:03:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582114
;

