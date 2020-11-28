-- 2020-02-29T14:39:15.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-02-29 16:39:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570074
;

-- 2020-02-29T14:39:15.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('sepa_export','SEPA_Protocol','VARCHAR(30)',null,null)
;

-- 2020-02-29T14:39:15.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('sepa_export','SEPA_Protocol',null,'NOT NULL',null)
;

