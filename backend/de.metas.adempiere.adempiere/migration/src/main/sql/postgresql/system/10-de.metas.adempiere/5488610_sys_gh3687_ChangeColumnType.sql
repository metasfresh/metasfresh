-- 2018-03-16T18:35:30.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=7,Updated=TO_TIMESTAMP('2018-03-16 18:35:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558029
;

-- 2018-03-16T18:35:33.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product','PackageSize','VARCHAR(7)',null,null)
;


-- 2018-03-16T18:51:39.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=7,Updated=TO_TIMESTAMP('2018-03-16 18:51:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558063
;

-- 2018-03-16T18:51:43.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_product','PackageSize','VARCHAR(7)',null,null)
;

