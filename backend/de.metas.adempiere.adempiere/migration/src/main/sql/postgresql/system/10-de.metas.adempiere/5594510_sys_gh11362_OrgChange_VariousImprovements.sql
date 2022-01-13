-- 2021-06-24T09:16:28.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-06-24 12:16:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573233
;

-- 2021-06-24T09:16:30.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_orgchange_history','Date_OrgChange','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2021-06-24T09:16:30.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_orgchange_history','Date_OrgChange',null,'NOT NULL',null)
;

