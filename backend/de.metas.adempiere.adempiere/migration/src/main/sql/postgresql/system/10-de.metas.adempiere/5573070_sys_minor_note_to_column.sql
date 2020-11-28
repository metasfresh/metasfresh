-- 2020-11-23T18:15:13.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='C_DocTypeCounter',Updated=TO_TIMESTAMP('2020-11-23 19:15:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=327
;

-- 2020-11-23T18:17:19.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='The column is not shown and always true; there is an if-block in the code that requires it to be Y on order to create a counter-doc
I think we should sooner or later remove this',Updated=TO_TIMESTAMP('2020-11-23 19:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=12407
;
