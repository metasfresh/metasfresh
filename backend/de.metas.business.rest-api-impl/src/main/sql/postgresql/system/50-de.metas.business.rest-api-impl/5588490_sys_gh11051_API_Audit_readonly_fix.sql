-- 2021-05-14T09:59:19.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-05-14 12:59:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573736
;

-- 2021-05-14T09:59:22.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','SeqNo','VARCHAR(255)',null,null)
;

-- 2021-05-14T10:00:48.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','SeqNo','VARCHAR(255)',null,null)
;

-- 2021-05-14T10:01:08.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-05-14 13:01:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573729
;

-- 2021-05-14T10:01:13.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','Method','VARCHAR(255)',null,null)
;

-- 2021-05-14T10:01:25.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-05-14 13:01:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573730
;

-- 2021-05-14T10:01:27.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','PathPrefix','VARCHAR(255)',null,null)
;

-- 2021-05-14T10:01:52.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-05-14 13:01:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573834
;

-- 2021-05-14T10:01:54.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepRequestBodyDays','NUMERIC(10)',null,'0')
;

-- 2021-05-14T10:01:54.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET KeepRequestBodyDays=0 WHERE KeepRequestBodyDays IS NULL
;

-- 2021-05-14T10:02:02.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-05-14 13:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573833
;

-- 2021-05-14T10:02:05.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepRequestDays','NUMERIC(10)',null,'0')
;

-- 2021-05-14T10:02:05.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET KeepRequestDays=0 WHERE KeepRequestDays IS NULL
;

-- 2021-05-14T10:02:12.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-05-14 13:02:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573836
;

-- 2021-05-14T10:02:14.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepResponseBodyDays','NUMERIC(10)',null,'0')
;

-- 2021-05-14T10:02:14.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET KeepResponseBodyDays=0 WHERE KeepResponseBodyDays IS NULL
;

-- 2021-05-14T10:02:23.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-05-14 13:02:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573835
;

-- 2021-05-14T10:02:25.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepResponseDays','NUMERIC(10)',null,'0')
;

-- 2021-05-14T10:02:25.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET KeepResponseDays=0 WHERE KeepResponseDays IS NULL
;

-- 2021-05-14T10:03:38.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-05-14 13:03:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573731
;

-- 2021-05-14T10:03:40.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','IsInvokerWaitsForResult','CHAR(1)',null,'N')
;

-- 2021-05-14T10:03:40.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET IsInvokerWaitsForResult='N' WHERE IsInvokerWaitsForResult IS NULL
;

-- 2021-05-14T10:04:13.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-05-14 13:04:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573791
;

-- 2021-05-14T10:04:17.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','AD_User_InCharge_ID','NUMERIC(10)',null,null)
;

-- 2021-05-14T10:04:38.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-05-14 13:04:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573843
;

-- 2021-05-14T10:04:41.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','NotifyUserInCharge','VARCHAR(255)',null,null)
;

-- 2021-05-14T10:13:01.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','NotifyUserInCharge','VARCHAR(255)',null,null)
;

