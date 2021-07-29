-- 2021-05-15T08:58:39.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-05-15 11:58:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573738
;

-- 2021-05-15T09:03:23.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_request_audit','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2021-05-15T09:08:03.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-15 12:08:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645199
;

-- 2021-05-15T09:11:10.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-15 12:11:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645168
;

-- 2021-05-15T09:11:19.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-05-15 12:11:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645191
;

-- 2021-05-15T09:11:33.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-05-15 12:11:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573793
;

-- 2021-05-15T09:11:35.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_response_audit','AD_Org_ID','NUMERIC(10)',null,null)
;

