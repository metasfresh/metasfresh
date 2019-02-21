-- 2019-02-21T08:05:11.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-02-21 08:05:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559657
;

-- 2019-02-21T08:05:14.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_discountschemabreak','PriceBase','CHAR(1)',null,'P')
;

-- 2019-02-21T08:05:14.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_discountschemabreak','PriceBase',null,'NULL',null)
;

