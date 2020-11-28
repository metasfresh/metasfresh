-- 2018-04-16T11:16:06.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-04-16 11:16:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559658
;

-- 2018-04-16T11:16:09.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_discountschemabreak','Std_AddAmt','NUMERIC',null,null)
;

-- 2018-04-16T11:16:09.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_discountschemabreak','Std_AddAmt',null,'NULL',null)
;

