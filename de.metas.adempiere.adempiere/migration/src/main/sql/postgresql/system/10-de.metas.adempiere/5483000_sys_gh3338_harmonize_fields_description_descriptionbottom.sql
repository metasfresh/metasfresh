-- 2018-01-17T08:32:50.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=1024,Updated=TO_TIMESTAMP('2018-01-17 08:32:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1510
;

-- 2018-01-17T08:32:53.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_doctype','Description','VARCHAR(1024)',null,null)
;

-- 2018-01-17T08:33:42.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=1024,Updated=TO_TIMESTAMP('2018-01-17 08:33:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=501681
;

-- 2018-01-17T08:33:49.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice','DescriptionBottom','VARCHAR(1024)',null,null)
;

-- 2018-01-17T08:34:01.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=1024,Updated=TO_TIMESTAMP('2018-01-17 08:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3782
;

-- 2018-01-17T08:34:03.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice','Description','VARCHAR(1024)',null,null)
;

-- 2018-01-17T08:34:41.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=1024,Updated=TO_TIMESTAMP('2018-01-17 08:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=501661
;

-- 2018-01-17T08:34:44.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_order','DescriptionBottom','VARCHAR(1024)',null,null)
;

-- 2018-01-17T08:34:57.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAdvancedText='Y',Updated=TO_TIMESTAMP('2018-01-17 08:34:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=501661
;

