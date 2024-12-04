-- 2022-08-11T14:44:09.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-08-11 17:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584019
;

-- 2022-08-11T14:44:10.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_costdetail','DateAcct','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2022-08-11T14:44:10.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_costdetail','DateAcct',null,'NOT NULL',null)
;

-- 2022-08-11T14:44:14.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2022-08-11 17:44:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584019
;

