-- 2021-07-22T12:42:22.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-07-22 15:42:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575124
;

-- 2021-07-22T12:42:23.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('revolut_payment_export','RecipientCountryId','NUMERIC(10)',null,'0')
;

-- 2021-07-22T12:42:23.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('revolut_payment_export','RecipientCountryId',null,'NULL',null)
;

