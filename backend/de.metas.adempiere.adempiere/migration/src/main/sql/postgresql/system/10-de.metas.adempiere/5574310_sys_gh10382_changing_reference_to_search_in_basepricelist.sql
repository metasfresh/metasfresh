-- 2020-12-08T11:32:07.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2020-12-08 08:32:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4706
;

-- 2020-12-08T11:32:32.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_pricelist_version','M_Pricelist_Version_Base_ID','NUMERIC(10)',null,null)
;

