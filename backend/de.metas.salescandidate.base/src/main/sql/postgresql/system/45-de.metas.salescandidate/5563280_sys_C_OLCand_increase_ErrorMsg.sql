-- 2020-07-13T09:26:26.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=36, FieldLength=268435456,Updated=TO_TIMESTAMP('2020-07-13 11:26:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546488
;

-- 2020-07-13T09:26:30.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_olcand','ErrorMsg','TEXT',null,null)
;

