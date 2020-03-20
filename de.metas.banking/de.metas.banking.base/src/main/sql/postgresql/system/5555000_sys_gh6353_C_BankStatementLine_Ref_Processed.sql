-- 2020-03-20T18:24:57.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL=null, IsLazyLoading='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2020-03-20 20:24:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54721
;

-- 2020-03-20T18:25:00.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','Processed','CHAR(1)',null,'N')
;

-- 2020-03-20T18:25:01.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_BankStatementLine_Ref SET Processed='N' WHERE Processed IS NULL
;

-- 2020-03-20T18:25:01.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','Processed',null,'NOT NULL',null)
;

