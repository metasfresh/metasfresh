-- 2020-06-05T10:35:55.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-06-05 13:35:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570845
;

-- 2020-06-05T10:35:56.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_acctschema_default','PayBankFee_Acct','NUMERIC(10)',null,null)
;

-- 2020-06-05T10:35:56.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_acctschema_default','PayBankFee_Acct',null,'NOT NULL',null)
;

-- 2020-06-05T10:36:25.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-06-05 13:36:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570846
;

-- 2020-06-05T10:36:26.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bp_bankaccount_acct','PayBankFee_Acct','NUMERIC(10)',null,null)
;

-- 2020-06-05T10:36:26.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bp_bankaccount_acct','PayBankFee_Acct',null,'NOT NULL',null)
;

