-- 2019-02-04T17:26:38.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='540003', IsMandatory='Y', TechnicalNote='Out default AD_DataDestination_ID=540003 is "DEST.de.metas.ordercandidate"',Updated=TO_TIMESTAMP('2019-02-04 17:26:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547734
;

-- 2019-02-04T17:26:42.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_olcand','AD_DataDestination_ID','NUMERIC(10)',null,'540003')
;

-- 2019-02-04T17:26:42.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OLCand SET AD_DataDestination_ID=540003 WHERE AD_DataDestination_ID IS NULL
;

-- 2019-02-04T17:26:42.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_olcand','AD_DataDestination_ID',null,'NOT NULL',null)
;

-- 2019-02-04T17:27:13.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='', TechnicalNote='',Updated=TO_TIMESTAMP('2019-02-04 17:27:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547734
;

