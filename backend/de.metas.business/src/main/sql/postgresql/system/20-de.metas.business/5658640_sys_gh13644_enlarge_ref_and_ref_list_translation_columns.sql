-- 2022-09-23T12:33:02.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2022-09-29 15:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=339
;

-- 2022-09-23T12:33:03.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_ref_list_trl','Description','VARCHAR(2000)',null,null)
;

-- 2022-09-23T12:33:02.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=600,Updated=TO_TIMESTAMP('2022-09-29 15:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=338
;

-- 2022-09-23T12:33:03.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_ref_list_trl','Name','VARCHAR(600)',null,null)
;

-- 2022-09-23T12:33:02.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2022-09-29 15:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=281
;

-- 2022-09-23T12:33:03.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_reference_trl','Description','VARCHAR(2000)',null,null)
;

-- 2022-09-23T12:33:02.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=600,Updated=TO_TIMESTAMP('2022-09-29 15:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=280
;

-- 2022-09-23T12:33:03.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_reference_trl','Name','VARCHAR(600)',null,null)
;
