--
-- remove @...@ expression with a column that was dropped some time ago
--
-- 2018-08-31T15:33:14.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@PriceBase@ = P',Updated=TO_TIMESTAMP('2018-08-31 15:33:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559655
;

-- 2018-08-31T15:33:48.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='',Updated=TO_TIMESTAMP('2018-08-31 15:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559657
;

