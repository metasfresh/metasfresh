-- 2017-09-28T12:13:35.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-09-28 12:13:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557037
;

-- 2017-09-28T12:13:42.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_attachmententry','ContentType','VARCHAR(60)',null,null)
;

-- 2017-09-28T12:13:42.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_attachmententry','ContentType',null,'NULL',null)
;

