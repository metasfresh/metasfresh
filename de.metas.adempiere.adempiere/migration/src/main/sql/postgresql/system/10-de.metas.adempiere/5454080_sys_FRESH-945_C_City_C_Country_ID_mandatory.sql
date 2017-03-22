-- Dec 2, 2016 12:56 AM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2016-12-02 00:56:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7057
;

-- Dec 2, 2016 12:56 AM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_city','C_Country_ID','NUMERIC(10)',null,null)
;

-- Dec 2, 2016 12:56 AM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_city','C_Country_ID',null,'NOT NULL',null)
;

