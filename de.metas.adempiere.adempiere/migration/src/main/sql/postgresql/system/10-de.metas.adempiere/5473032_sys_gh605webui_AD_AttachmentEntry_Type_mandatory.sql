-- 2017-09-28T12:02:26.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2017-09-28 12:02:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557319
;

-- 2017-09-28T12:02:31.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_attachmententry','Type','CHAR(1)',null,null)
;

-- 2017-09-28T12:02:31.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_attachmententry','Type',null,'NOT NULL',null)
;

