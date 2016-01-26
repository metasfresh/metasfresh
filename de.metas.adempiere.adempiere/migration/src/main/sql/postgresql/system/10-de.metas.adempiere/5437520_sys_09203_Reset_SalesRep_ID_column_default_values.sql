-- Reset SalesRep_ID's column default value for C_Order, C_Invoice and C_BPartner table.


-- 25.01.2016 15:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner','SalesRep_ID','NUMERIC(10)',null,'NULL')
;

-- 25.01.2016 15:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue=NULL,Updated=TO_TIMESTAMP('2016-01-25 15:18:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2186
;

-- 25.01.2016 15:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_order','SalesRep_ID','NUMERIC(10)',null,'NULL')
;

-- 25.01.2016 15:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_order','SalesRep_ID',null,'NULL',null)
;

-- 25.01.2016 15:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue=NULL,Updated=TO_TIMESTAMP('2016-01-25 15:18:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3512
;

-- 25.01.2016 15:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice','SalesRep_ID','NUMERIC(10)',null,'NULL')
;

-- 25.01.2016 15:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@NULL@',Updated=TO_TIMESTAMP('2016-01-25 15:27:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4431
;

-- 25.01.2016 15:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner','SalesRep_ID','NUMERIC(10)',null,'NULL')
;

