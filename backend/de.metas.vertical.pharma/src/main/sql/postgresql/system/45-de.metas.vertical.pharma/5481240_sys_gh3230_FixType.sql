-- 2017-12-21T18:48:19.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=22,Updated=TO_TIMESTAMP('2017-12-21 18:48:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558181
;

-- 2017-12-21T18:48:21.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_pharma_product','A01KAEP','NUMERIC',null,null)
;

-- 2017-12-21T18:49:13.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=22,Updated=TO_TIMESTAMP('2017-12-21 18:49:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558182
;

-- 2017-12-21T18:49:15.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_pharma_product','A01APU','NUMERIC',null,null)
;

-- 2017-12-21T18:49:26.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=22,Updated=TO_TIMESTAMP('2017-12-21 18:49:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558185
;

-- 2017-12-21T18:49:28.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_pharma_product','A01AEP','NUMERIC',null,null)
;

-- 2017-12-21T18:49:38.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=22,Updated=TO_TIMESTAMP('2017-12-21 18:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558186
;

-- 2017-12-21T18:49:39.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_pharma_product','A01AVP','NUMERIC',null,null)
;

-- 2017-12-21T18:50:10.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=22,Updated=TO_TIMESTAMP('2017-12-21 18:50:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558187
;

-- 2017-12-21T18:50:12.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_pharma_product','A01UVP','NUMERIC',null,null)
;

-- 2017-12-21T18:50:17.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=22,Updated=TO_TIMESTAMP('2017-12-21 18:50:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558188
;

-- 2017-12-21T18:50:19.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_pharma_product','A01ZBV','NUMERIC',null,null)
;

-- 2017-12-21T18:50:38.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=561232
;

-- 2017-12-21T18:50:38.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=561232
;

-- 2017-12-21T18:50:59.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=558400
;

-- 2017-12-21T18:50:59.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=558400
;


ALTER TABLE i_pharma_product DROP COLUMN C_TaxCategory_ID;

