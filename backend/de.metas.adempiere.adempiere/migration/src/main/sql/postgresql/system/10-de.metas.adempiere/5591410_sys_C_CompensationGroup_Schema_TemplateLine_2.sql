-- 2021-06-08T21:01:02.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=NULL, SeqNo=25,Updated=TO_TIMESTAMP('2021-06-09 00:01:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544005
;

-- 2021-06-08T21:01:27.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2021-06-09 00:01:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647472
;

-- 2021-06-08T21:03:01.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo), 0) + 10 FROM C_CompensationGroup_Schema_TemplateLine WHERE C_CompensationGroup_Schema_ID=@C_CompensationGroup_Schema_ID@',Updated=TO_TIMESTAMP('2021-06-09 00:03:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574258
;

-- 2021-06-08T21:04:57.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=15,Updated=TO_TIMESTAMP('2021-06-09 00:04:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544005
;

-- 2021-06-08T21:11:02.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=559231,Updated=TO_TIMESTAMP('2021-06-09 00:11:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544005
;

