
-- 2018-11-29T11:55:47.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-11-29 11:55:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=52112
;

-- 2018-11-29T11:55:51.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_orginfo','ReceiptFooterMsg','VARCHAR(1024)',null,'1')
;

