-- 2018-06-28T17:56:24.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540454,Updated=TO_TIMESTAMP('2018-06-28 17:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540999
;

-- 2018-06-28T17:57:09.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=565006
;

-- 2018-06-28T17:57:09.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=565006
;

-- 2018-06-28T17:57:09.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=565008
;

-- 2018-06-28T17:57:09.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=565008
;

-- 2018-06-28T17:57:09.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=565003
;

-- 2018-06-28T17:57:09.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=565003
;

-- 2018-06-28T17:57:09.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=565016
;

-- 2018-06-28T17:57:09.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=565016
;

-- 2018-06-28T17:57:17.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=560544
;

-- 2018-06-28T17:57:17.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=560544
;



/* DDL */ SELECT public.db_alter_table('I_Datev_Payment','ALTER TABLE public.I_Datev_Payment DROP COLUMN C_BP_BankAccount_ID')
;



/* DDL */ SELECT public.db_alter_table('I_Datev_Payment','ALTER TABLE public.I_Datev_Payment DROP COLUMN DocumentNo')
;


/* DDL */ SELECT public.db_alter_table('I_Datev_Payment','ALTER TABLE public.I_Datev_Payment DROP COLUMN BankAccountNo')
;



-- 2018-06-28T17:59:08.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=560565
;

-- 2018-06-28T17:59:08.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=560565
;

-- 2018-06-28T17:59:31.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=560542
;

-- 2018-06-28T17:59:31.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=560542
;



-- 2018-06-28T18:06:42.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540745, DefaultValue='N',Updated=TO_TIMESTAMP('2018-06-28 18:06:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560567
;

-- 2018-06-28T18:06:46.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_datev_payment','I_IsImported','CHAR(1)',null,'N')
;

-- 2018-06-28T18:06:46.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE I_Datev_Payment SET I_IsImported='N' WHERE I_IsImported IS NULL
;

