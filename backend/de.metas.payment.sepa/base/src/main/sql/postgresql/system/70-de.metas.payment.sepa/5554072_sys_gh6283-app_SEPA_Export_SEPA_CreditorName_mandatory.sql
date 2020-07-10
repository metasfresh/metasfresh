
UPDATE SEPA_Export SET Updated=TO_TIMESTAMP('2020-03-06 15:38:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99, SEPA_CreditorName=SEPA_CreditorIdentifier WHERE SEPA_CreditorName IS NULL;

COMMIT;

-- 2020-03-06T14:40:59.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-03-06 15:40:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570097
;

-- 2020-03-06T14:40:59.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('sepa_export','SEPA_CreditorName','VARCHAR(60)',null,null)
;

-- 2020-03-06T14:40:59.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('sepa_export','SEPA_CreditorName',null,'NOT NULL',null)
;

