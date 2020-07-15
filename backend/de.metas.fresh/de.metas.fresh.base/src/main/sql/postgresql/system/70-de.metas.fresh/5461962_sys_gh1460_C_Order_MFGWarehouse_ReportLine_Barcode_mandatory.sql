-- 2017-05-05T15:53:05.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-05-05 15:53:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556739
;

-- 2017-05-05T15:53:06.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_order_mfgwarehouse_reportline','Barcode','VARCHAR(255)',null,null)
;

-- 2017-05-05T15:53:06.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_order_mfgwarehouse_reportline','Barcode',null,'NOT NULL',null)
;

