-- 2018-04-25T12:47:38.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2018-04-25 12:47:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556509
;

ALTER TABLE public.md_candidate DROP CONSTRAINT mattributesetinstance_mdcandid;
 
-- 2018-04-25T16:25:17.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='We have no FK-constraint, because this column is only here to be displayed (the real stuff is StorageAttributesKey).',Updated=TO_TIMESTAMP('2018-04-25 16:25:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556509
;
