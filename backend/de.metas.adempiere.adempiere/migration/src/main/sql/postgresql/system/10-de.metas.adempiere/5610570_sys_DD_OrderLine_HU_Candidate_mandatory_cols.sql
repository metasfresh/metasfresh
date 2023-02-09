-- 2021-10-25T09:56:09.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-10-25 12:56:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577863
;

-- 2021-10-25T09:56:11.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2021-10-25T09:56:11.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','M_Product_ID',null,'NOT NULL',null)
;

-- 2021-10-25T09:56:34.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-10-25 12:56:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577864
;

-- 2021-10-25T09:56:36.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','PickFrom_Warehouse_ID','NUMERIC(10)',null,null)
;

-- 2021-10-25T09:56:36.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','PickFrom_Warehouse_ID',null,'NOT NULL',null)
;

-- 2021-10-25T09:56:44.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-10-25 12:56:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577865
;

-- 2021-10-25T09:56:45.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','PickFrom_Locator_ID','NUMERIC(10)',null,null)
;

-- 2021-10-25T09:56:45.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','PickFrom_Locator_ID',null,'NOT NULL',null)
;

-- 2021-10-25T09:56:53.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-10-25 12:56:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577866
;

-- 2021-10-25T09:56:54.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','DropTo_Warehouse_ID','NUMERIC(10)',null,null)
;

-- 2021-10-25T09:56:54.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','DropTo_Warehouse_ID',null,'NOT NULL',null)
;

-- 2021-10-25T09:57:00.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-10-25 12:57:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577867
;

-- 2021-10-25T09:57:01.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','DropTo_Locator_ID','NUMERIC(10)',null,null)
;

-- 2021-10-25T09:57:01.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline_hu_candidate','DropTo_Locator_ID',null,'NOT NULL',null)
;

