-- 2021-05-14T13:46:24.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2021-05-14 16:46:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541636
;

-- 2021-05-14T13:47:20.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2021-05-14 16:47:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543896
;

-- 2021-05-14T13:47:49.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2021-05-14 16:47:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543898
;

-- 2021-05-14T13:52:41.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541314,TO_TIMESTAMP('2021-05-14 16:52:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','API_Request_Audit',TO_TIMESTAMP('2021-05-14 16:52:41','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-05-14T13:52:41.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541314 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-05-14T13:53:27.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,573744,0,541314,541636,TO_TIMESTAMP('2021-05-14 16:53:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-05-14 16:53:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-14T13:54:37.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=541314, IsAutocomplete='Y',Updated=TO_TIMESTAMP('2021-05-14 16:54:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573800
;

-- 2021-05-14T13:58:34.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2021-05-14 16:58:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541640
;

-- 2021-05-14T13:59:10.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2021-05-14 16:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543899
;

-- 2021-05-14T13:59:28.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2021-05-14 16:59:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541639
;

-- 2021-05-14T14:45:48.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-05-14 17:45:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573791
;

-- 2021-05-14T14:45:51.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','AD_User_InCharge_ID','NUMERIC(10)',null,null)
;

-- 2021-05-14T14:46:09.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-05-14 17:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573731
;

-- 2021-05-14T14:46:20.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-05-14 17:46:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573834
;

-- 2021-05-14T14:46:24.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-05-14 17:46:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573833
;

-- 2021-05-14T14:46:29.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-05-14 17:46:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573836
;

-- 2021-05-14T14:46:33.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-05-14 17:46:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573835
;

-- 2021-05-14T14:46:40.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-05-14 17:46:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573729
;

-- 2021-05-14T14:46:45.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-05-14 17:46:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573843
;

-- 2021-05-14T14:46:50.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-05-14 17:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573730
;

-- 2021-05-14T14:46:58.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-05-14 17:46:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573736
;

-- 2021-05-14T14:47:03.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2021-05-14T14:47:07.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2021-05-14T14:47:10.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','SeqNo','VARCHAR(255)',null,null)
;

-- 2021-05-14T14:47:12.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','PathPrefix','VARCHAR(255)',null,null)
;

-- 2021-05-14T14:47:15.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','NotifyUserInCharge','VARCHAR(255)',null,null)
;

-- 2021-05-14T14:47:18.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','Method','VARCHAR(255)',null,null)
;

-- 2021-05-14T14:47:20.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepResponseDays','NUMERIC(10)',null,'0')
;

-- 2021-05-14T14:47:20.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET KeepResponseDays=0 WHERE KeepResponseDays IS NULL
;

-- 2021-05-14T14:47:23.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepResponseBodyDays','NUMERIC(10)',null,'0')
;

-- 2021-05-14T14:47:23.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET KeepResponseBodyDays=0 WHERE KeepResponseBodyDays IS NULL
;

-- 2021-05-14T14:47:26.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepRequestDays','NUMERIC(10)',null,'0')
;

-- 2021-05-14T14:47:26.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET KeepRequestDays=0 WHERE KeepRequestDays IS NULL
;

-- 2021-05-14T14:47:28.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepRequestBodyDays','NUMERIC(10)',null,'0')
;

-- 2021-05-14T14:47:28.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET KeepRequestBodyDays=0 WHERE KeepRequestBodyDays IS NULL
;

-- 2021-05-14T14:47:32.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','IsInvokerWaitsForResult','CHAR(1)',null,'N')
;

-- 2021-05-14T14:47:32.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET IsInvokerWaitsForResult='N' WHERE IsInvokerWaitsForResult IS NULL
;

