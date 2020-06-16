-- 2020-06-11T12:28:42.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='OTHER', IsMandatory='Y',Updated=TO_TIMESTAMP('2020-06-11 15:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570882
;

-- 2020-06-11T12:28:43.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_issue','IssueCategory','VARCHAR(10)',null,'OTHER')
;

COMMIT; -- Q&D way to avoid error with "pending trigger events" when mixing DDL and DML
UPDATE AD_Issue SET IssueCategory='OTHER' WHERE IssueCategory IS NULL;
COMMIT;

-- 2020-06-11T12:28:43.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_issue','IssueCategory',null,'NOT NULL',null)
;

