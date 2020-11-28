-- 30.05.2016 16:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=250,Updated=TO_TIMESTAMP('2016-05-30 16:46:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6649
;

commit;

-- 31.05.2016 09:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_image','ImageURL','VARCHAR(250)',null,'NULL')
;
commit;
