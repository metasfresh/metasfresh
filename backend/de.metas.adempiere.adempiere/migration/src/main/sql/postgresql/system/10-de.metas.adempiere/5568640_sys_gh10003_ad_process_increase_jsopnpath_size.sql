
-- 2020-08-20T12:24:20.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=36, FieldLength=1661992960,Updated=TO_TIMESTAMP('2020-08-20 14:24:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568302
;

-- 2020-08-20T12:24:22.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_process','JSONPath','TEXT',null,null)
;
