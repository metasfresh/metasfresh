-- 2020-06-15T06:04:20.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-06-15 08:04:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=553001
;

-- 2020-06-15T06:04:20.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_printerhw','OutputType','VARCHAR(10)',null,'Queue')
;

-- 2020-06-15T06:04:20.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_printerhw','OutputType',null,'NOT NULL',null)
;

