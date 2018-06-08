

-- 2018-04-19T16:16:57.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsExcludedFromSale@ = ''Y''',Updated=TO_TIMESTAMP('2018-04-19 16:16:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559662
;

-- 2018-04-19T16:21:28.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsExcludedFromSale/N@=Y',Updated=TO_TIMESTAMP('2018-04-19 16:21:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563427
;

