-- 2020-10-02T14:56:07.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLength=10000, IsReadOnly='Y',Updated=TO_TIMESTAMP('2020-10-02 17:56:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=601764
;

-- 2020-10-02T15:15:41.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=36, FieldLength=999999,Updated=TO_TIMESTAMP('2020-10-02 18:15:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570611
;

-- 2020-10-02T15:15:44.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('s_failedtimebooking','ImportErrorMsg','TEXT',null,null)
;

-- 2020-10-02T15:16:07.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLength=999999,Updated=TO_TIMESTAMP('2020-10-02 18:16:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=601764
;

