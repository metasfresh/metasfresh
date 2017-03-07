-- Mar 6, 2017 11:41 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2017-03-06 23:41:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6534
;

-- Mar 6, 2017 11:42 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_inout','Posted','CHAR(1)',null,'N')
;

-- Mar 6, 2017 11:42 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_InOut SET Posted='N' WHERE Posted IS NULL
;

