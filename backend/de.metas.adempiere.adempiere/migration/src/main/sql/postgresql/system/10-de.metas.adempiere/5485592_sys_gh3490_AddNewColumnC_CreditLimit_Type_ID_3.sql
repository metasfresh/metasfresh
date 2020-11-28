-- 2018-02-16T12:13:14.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-02-16 12:13:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558978
;

-- 2018-02-16T12:13:16.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_creditlimit','C_CreditLimit_Type_ID','NUMERIC(10)',null,null)
;

-- 2018-02-16T12:13:16.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_creditlimit','C_CreditLimit_Type_ID',null,'NOT NULL',null)
;

