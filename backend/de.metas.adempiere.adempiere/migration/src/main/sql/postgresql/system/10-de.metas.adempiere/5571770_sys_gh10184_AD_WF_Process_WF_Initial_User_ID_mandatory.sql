-- 2020-11-08T13:39:46.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-11-08 15:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572080
;

-- 2020-11-08T13:39:46.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_wf_process','WF_Initial_User_ID','NUMERIC(10)',null,null)
;

-- 2020-11-08T13:39:46.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_wf_process','WF_Initial_User_ID',null,'NOT NULL',null)
;

