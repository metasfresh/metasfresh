-- 2018-07-05T19:55:11.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-07-05 19:55:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560663
;

-- 2018-07-05T19:55:13.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_purchasecandidate','PurchaseDateOrdered','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2018-07-05T19:55:13.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_purchasecandidate','PurchaseDateOrdered',null,'NOT NULL',null)
;

