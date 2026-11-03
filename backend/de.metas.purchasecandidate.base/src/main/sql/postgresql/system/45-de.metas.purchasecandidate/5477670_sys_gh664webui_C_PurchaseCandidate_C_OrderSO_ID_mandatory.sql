-- 2017-11-20T16:48:57.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-11-20 16:48:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557870
;

-- 2017-11-20T16:48:59.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_purchasecandidate','C_OrderSO_ID','NUMERIC(10)',null,null)
;

-- 2017-11-20T16:48:59.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_purchasecandidate','C_OrderSO_ID',null,'NOT NULL',null)
;

