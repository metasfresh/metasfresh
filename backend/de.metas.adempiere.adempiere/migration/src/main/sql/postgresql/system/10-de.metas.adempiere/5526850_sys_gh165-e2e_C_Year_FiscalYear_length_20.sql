-- 2019-07-08T11:33:51.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=20,Updated=TO_TIMESTAMP('2019-07-08 11:33:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1038
;

-- 2019-07-08T11:33:53.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_year','FiscalYear','VARCHAR(20)',null,null)
;

-- 2019-07-08T11:37:18.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='Changed lenght to 20 so when doing automatic e2e, we can have longer fiscal-year-names.',Updated=TO_TIMESTAMP('2019-07-08 11:37:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1038
;

