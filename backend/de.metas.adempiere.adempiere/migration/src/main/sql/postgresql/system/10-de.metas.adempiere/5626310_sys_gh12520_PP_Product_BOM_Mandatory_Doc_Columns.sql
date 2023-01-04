-- 2022-02-15T10:59:40.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-02-15 12:59:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579251
;

-- 2022-02-15T10:59:40.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-02-15 12:59:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579255
;

-- 2022-02-15T10:59:40.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-02-15 12:59:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53323
;

-- 2022-02-15T10:59:40.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-02-15 12:59:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579250
;

-- 2022-02-15T10:59:40.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-02-15 12:59:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579256
;

-- 2022-02-15T10:59:40.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-02-15 12:59:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579249
;

-- 2022-02-16T15:22:15.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_product_bom','C_DocType_ID','NUMERIC(10)',null,null)
;

-- 2022-02-16T15:22:15.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_product_bom','C_DocType_ID',null,'NOT NULL',null)
;

-- 2022-02-16T15:22:30.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_product_bom','DateDoc','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2022-02-16T15:22:30.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_product_bom','DateDoc',null,'NOT NULL',null)
;

-- 2022-02-16T15:22:41.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_product_bom','DocAction','CHAR(2)',null,'CO')
;

-- 2022-02-16T15:22:42.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE PP_Product_BOM SET DocAction='CO' WHERE DocAction IS NULL
;

-- 2022-02-16T15:22:52.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_product_bom','DocStatus','VARCHAR(2)',null,'DR')
;

-- 2022-02-16T15:22:52.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE PP_Product_BOM SET DocStatus='DR' WHERE DocStatus IS NULL
;

-- 2022-02-16T15:22:59.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_product_bom','DocumentNo','VARCHAR(40)',null,null)
;

-- 2022-02-16T15:23:11.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_product_bom','Processed','CHAR(1)',null,'N')
;

-- 2022-02-16T15:23:11.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE PP_Product_BOM SET Processed='N' WHERE Processed IS NULL
;

