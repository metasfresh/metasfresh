-- 2018-09-19T17:23:46.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManageDunnableDocGraceDate@=''Y''',Updated=TO_TIMESTAMP('2018-09-19 17:23:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551595
;

-- 2018-09-19T17:48:27.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2018-09-19 17:48:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=301
;

-- 2018-09-19T17:48:34.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_DunningDoc','ALTER TABLE public.C_DunningDoc ADD COLUMN DocAction CHAR(2) DEFAULT ''CO'' NOT NULL')
;

-- 2018-09-19T17:48:44.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_DunningDoc','ALTER TABLE public.C_DunningDoc ADD COLUMN DocStatus VARCHAR(2) DEFAULT ''DR'' NOT NULL')
;

