-- 2019-10-15T13:38:38.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN ExternalHeaderId VARCHAR(15)')
;

-- 2019-10-15T13:39:00.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=100,Updated=TO_TIMESTAMP('2019-10-15 16:39:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569217
;

-- 2019-10-15T13:39:02.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice_candidate','ExternalLineId','VARCHAR(100)',null,null)
;

-- 2019-10-15T13:39:14.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=100,Updated=TO_TIMESTAMP('2019-10-15 16:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569216
;

-- 2019-10-15T13:39:16.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice_candidate','ExternalHeaderId','VARCHAR(100)',null,null)
;

