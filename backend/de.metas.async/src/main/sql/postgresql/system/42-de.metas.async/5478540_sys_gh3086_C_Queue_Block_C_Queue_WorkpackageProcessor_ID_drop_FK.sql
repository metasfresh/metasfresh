-- 2017-11-27T14:43:32.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2017-11-27 14:43:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548862
;

ALTER TABLE public.c_queue_block DROP CONSTRAINT cqueuepackageprocessor_cqueueb;

