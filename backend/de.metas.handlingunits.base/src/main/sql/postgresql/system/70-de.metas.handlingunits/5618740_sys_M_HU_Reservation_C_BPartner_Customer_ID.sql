-- 2021-12-14T11:59:19.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-12-14 13:59:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560371
;

-- 2021-12-14T11:59:20.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_hu_reservation','C_BPartner_Customer_ID','NUMERIC(10)',null,null)
;

-- 2021-12-14T11:59:20.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_hu_reservation','C_BPartner_Customer_ID',null,'NULL',null)
;

