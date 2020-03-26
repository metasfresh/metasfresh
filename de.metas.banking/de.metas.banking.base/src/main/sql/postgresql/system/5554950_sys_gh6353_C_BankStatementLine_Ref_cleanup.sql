-- 2020-03-20T11:48:29.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-03-20 13:48:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54388
;

-- 2020-03-20T11:48:29.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','C_Payment_ID','NUMERIC(10)',null,null)
;

-- 2020-03-20T11:48:29.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','C_Payment_ID',null,'NOT NULL',null)
;

-- 2020-03-20T11:49:11.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=NULL, DefaultValue='', IsMandatory='Y', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2020-03-20 13:49:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54384
;

-- 2020-03-20T11:49:11.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2020-03-20T11:49:11.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','C_BPartner_ID',null,'NOT NULL',null)
;

-- 2020-03-20T11:49:46.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, DefaultValue='', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2020-03-20 13:49:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54386
;

-- 2020-03-20T11:49:49.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','C_Currency_ID','NUMERIC(10)',null,null)
;

-- 2020-03-20T11:49:49.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','C_Currency_ID',null,'NOT NULL',null)
;

-- 2020-03-20T11:51:03.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-03-20 13:51:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54381
;

-- 2020-03-20T11:51:03.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','AD_Client_ID','NUMERIC(10)',null,null)
;

-- 2020-03-20T11:51:03.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','AD_Client_ID',null,'NOT NULL',null)
;

-- 2020-03-20T11:51:08.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-03-20 13:51:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54382
;

-- 2020-03-20T11:51:09.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2020-03-20T11:51:09.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','AD_Org_ID',null,'NOT NULL',null)
;

-- 2020-03-20T11:51:22.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-03-20 13:51:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54390
;

-- 2020-03-20T11:51:23.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2020-03-20T11:51:23.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','Created',null,'NOT NULL',null)
;

-- 2020-03-20T11:51:28.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-03-20 13:51:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54391
;

-- 2020-03-20T11:51:28.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','CreatedBy','NUMERIC(10)',null,null)
;

-- 2020-03-20T11:51:28.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','CreatedBy',null,'NOT NULL',null)
;

-- 2020-03-20T11:51:47.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=54427
;

-- 2020-03-20T11:51:47.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=54427
;

-- 2020-03-20T11:51:47.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=54427
;

-- 2020-03-20T11:51:47.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=61577
;

-- 2020-03-20T11:51:47.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=61577
;

-- 2020-03-20T11:51:47.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=61577
;

-- 2020-03-20T11:51:47.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=559490
;

-- 2020-03-20T11:51:47.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=559490
;

-- 2020-03-20T11:51:47.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=559490
;

-- 2020-03-20T11:51:47.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589361
;

-- 2020-03-20T11:51:47.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589361
;

-- 2020-03-20T11:51:47.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=589361
;

-- 2020-03-20T11:51:47.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BankStatementLine_Ref DROP COLUMN IF EXISTS CreatePayment
;

-- 2020-03-20T11:51:47.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=54389
;

-- 2020-03-20T11:51:47.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=54389
;

-- 2020-03-20T11:52:20.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-03-20 13:52:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54392
;

-- 2020-03-20T11:52:22.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','IsActive','CHAR(1)',null,'Y')
;

-- 2020-03-20T11:52:22.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_BankStatementLine_Ref SET IsActive='Y' WHERE IsActive IS NULL
;

-- 2020-03-20T11:52:22.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','IsActive',null,'NOT NULL',null)
;

-- 2020-03-20T11:52:43.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-03-20 13:52:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54393
;

-- 2020-03-20T11:52:43.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2020-03-20T11:52:43.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','Updated',null,'NOT NULL',null)
;

-- 2020-03-20T11:52:47.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-03-20 13:52:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54394
;

-- 2020-03-20T11:52:48.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2020-03-20T11:52:48.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bankstatementline_ref','UpdatedBy',null,'NOT NULL',null)
;

-- 2020-03-20T11:52:55.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2020-03-20 13:52:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54394
;

-- 2020-03-20T11:53:04.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2020-03-20 13:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54391
;

