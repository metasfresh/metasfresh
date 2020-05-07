-- 2018-02-06T18:56:09.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='Y',Updated=TO_TIMESTAMP('2018-02-06 18:56:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540060
;

-- 2018-02-06T18:56:20.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=541866
;

-- 2018-02-06T18:56:20.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=541866
;

-- 2018-02-06T18:56:24.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2018-02-06 18:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540060
;

-- 2018-02-06T19:04:10.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='Y',Updated=TO_TIMESTAMP('2018-02-06 19:04:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540060
;

-- 2018-02-06T19:04:26.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=541868
;

-- 2018-02-06T19:04:26.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=541868
;

-- 2018-02-06T19:04:31.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2018-02-06 19:04:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540060
;



/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner DROP COLUMN SO_CreditLimit')
;

