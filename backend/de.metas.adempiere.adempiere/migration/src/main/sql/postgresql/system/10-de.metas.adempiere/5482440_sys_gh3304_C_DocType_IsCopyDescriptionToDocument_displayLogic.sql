-- 2018-01-11T23:34:34.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@DocBaseType@=SOO | @DocBaseType@=POO | @DocBaseType@=ARI | @DocBaseType@=ARC | @DocBaseType@=API | @DocBaseType@=ARC',Updated=TO_TIMESTAMP('2018-01-11 23:34:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561403
;

