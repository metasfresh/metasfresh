-- 2019-02-20T15:33:04.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2019-02-20 15:33:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=50181
;

-- 2019-02-20T15:33:24.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='AD_Process_ShowHelp',Updated=TO_TIMESTAMP('2019-02-20 15:33:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=50007
;

-- 2019-02-20T15:34:37.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=50037
;

-- 2019-02-20T15:34:37.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=50037
;

-- 2019-02-20T15:35:13.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Don''t show help (if there are no parameters)',Updated=TO_TIMESTAMP('2019-02-20 15:35:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=50038
;

-- 2019-02-20T15:37:57.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_process','ShowHelp','CHAR(1)',null,'N')
;

