

-- 2019-07-29T14:10:06.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_orderline','InvoicableQtyBasedOn','VARCHAR(11)',null,'Nominal')
;

-- 2019-07-29T14:10:06.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OrderLine SET InvoicableQtyBasedOn='Nominal' WHERE InvoicableQtyBasedOn IS NULL
;

-- 2019-07-29T14:10:06.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_orderline','InvoicableQtyBasedOn',null,'NOT NULL',null)
;
