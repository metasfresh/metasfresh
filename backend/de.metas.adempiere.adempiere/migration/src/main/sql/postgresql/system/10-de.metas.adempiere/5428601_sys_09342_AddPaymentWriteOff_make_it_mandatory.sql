-- 29.09.2015 10:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='Y',Updated=TO_TIMESTAMP('2015-09-29 10:23:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552756
;

-- 29.09.2015 10:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_allocationline','PaymentWriteOffAmt','NUMERIC',null,'0')
;

-- 29.09.2015 10:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_AllocationLine SET PaymentWriteOffAmt=0 WHERE PaymentWriteOffAmt IS NULL
;

-- 29.09.2015 10:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_allocationline','PaymentWriteOffAmt',null,'NOT NULL',null)
;

